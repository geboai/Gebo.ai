/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */




package ai.gebo.uploads.content.handler.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.uploads.content.handler.GUploadsProjectEndpoint;
import ai.gebo.uploads.content.handler.IGUploadsContentManagementSystemHandler;
import ai.gebo.uploads.content.handler.TmpUploadedContents;
import ai.gebo.uploads.content.handler.UploadedFileInfo;
import ai.gebo.uploads.content.handler.UploadedFileNode;
import ai.gebo.uploads.content.handler.repositories.TmpUploadedContentsRepository;

/**
 * AI generated comments
 *
 * This service manages the uploading and handling of file content in the Gebo system.
 * It provides functionality to temporarily store uploaded files and then process them
 * when needed by the system.
 *
 * <p>
 * The contents of an uploads data source are not frozen at creation time: files
 * can be added to and removed from an already persisted endpoint. Additions
 * reach the endpoint folder either through the handshake staging area (used
 * while the endpoint has no code yet, i.e. during creation) or directly
 * ({@link #uploadToEndpoint(String, List)}) once the endpoint exists. Removals
 * ({@link #deleteUploadedFiles(String, List)}) only touch the filesystem, which is
 * what the ingestion reads (the folder of the data source, walked whole): the
 * knowledge base is reconciled by the standard
 * ingestion pipeline, whose {@code checkUpdatedOrDeleted} step marks documents
 * whose file disappeared as deleted and hands their codes to the vectorization
 * dispose component. Deleting therefore takes full effect at the next publish of
 * the data source.
 * </p>
 */
@Service
public class UploadsSystemsManagementServiceImpl {
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadsSystemsManagementServiceImpl.class);

	@Autowired
	IGPersistentObjectManager persistentObjectManager;
	@Autowired
	TmpUploadedContentsRepository uploadedContentsRepository;
	@Autowired
	IGGeboConfigService geboConfig;
	@Autowired
	IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService;
	@Autowired
	IGUploadsContentManagementSystemHandler handler;
	@Autowired
	DocumentReferenceRepository documentReferenceRepository;
	@Autowired
	IGSecurityAuditLoggerService securityAuditLoggerService;

	/**
	 * Default constructor for UploadsSystemsManagementServiceImpl
	 */
	public UploadsSystemsManagementServiceImpl() {

	}

	/**
	 * Relative path to the temporary upload folder
	 */
	static final String relativeUploadFolder = "DEFAULT.UPLOADS.CONTENT.HANDLER.TMP";

	/**
	 * Manages the uploading of files by storing them in a temporary location
	 * and creating a record in the database.
	 *
	 * <p>
	 * Several batches may share the same handshake code (the editor keeps the code
	 * for the whole editing session and the user can browse for files more than
	 * once), so an existing staging record is extended rather than duplicated.
	 * </p>
	 *
	 * @param handshakeCode Unique identifier for this upload session
	 * @param files List of files to be uploaded
	 * @throws IOException If there's an error during file operations
	 */
	public void manageUpload(String handshakeCode, List<MultipartFile> files) throws IOException {
		Optional<TmpUploadedContents> existing = uploadedContentsRepository.findById(handshakeCode);
		TmpUploadedContents tmpUpload = existing.orElseGet(() -> {
			TmpUploadedContents created = new TmpUploadedContents();
			created.setCode(handshakeCode);
			created.setDescription("Uploaded content");
			return created;
		});

		if (geboConfig.getGeboWorkDirectory() == null)
			throw new RuntimeException("Gebo working directory is not set");
		Path path = Path.of(geboConfig.getGeboWorkDirectory(), relativeUploadFolder, handshakeCode);
		File file = path.toFile();
		if (!file.exists())
			file.mkdirs();
		for (MultipartFile entry : files) {
			String fileName = safeFileName(entry.getOriginalFilename());
			if (fileName == null)
				continue;
			if (!tmpUpload.getUploadedContents().contains(fileName)) {
				tmpUpload.getUploadedContents().add(fileName);
			}
			Path movedPath = Path.of(path.toAbsolutePath().toString(), fileName);
			copy(entry, movedPath);
		}
		if (existing.isPresent()) {
			uploadedContentsRepository.save(tmpUpload);
		} else {
			uploadedContentsRepository.insert(tmpUpload);
		}
	}

	/**
	 * Uploads files straight into the persistent folder of an already existing
	 * uploads endpoint, which is the "add more files" path of the editor.
	 *
	 * <p>
	 * Unlike {@link #manageUpload(String, List)} nothing is staged: the endpoint
	 * already owns a folder, so the files are their own final destination and are
	 * immediately visible to browsing and to the next ingestion run.
	 * </p>
	 *
	 * @param endpointCode code of the target uploads endpoint.
	 * @param files        files to store.
	 * @return the endpoint.
	 * @throws IOException                       If there's an error during file
	 *                                           operations
	 * @throws GeboContentHandlerSystemException If the endpoint folder cannot be
	 *                                           resolved
	 * @throws GeboPersistenceException          If the endpoint cannot be updated
	 */
	public GUploadsProjectEndpoint uploadToEndpoint(String endpointCode, List<MultipartFile> files)
			throws IOException, GeboContentHandlerSystemException, GeboPersistenceException {
		return uploadToEndpoint(endpointCode, files, null, null);
	}

	/**
	 * Adds files to an uploads data source that already exists, into one of its
	 * folders, keeping the folders they come from when a whole folder is uploaded.
	 *
	 * <p>
	 * Every path comes from the browser: the target folder and each file's path
	 * must resolve inside the folder of the data source, a file's path being
	 * relative and without "." or ".." parts; a file whose path does not is
	 * skipped. The missing folders are created.
	 * </p>
	 *
	 * @param endpointCode  code of the uploads endpoint.
	 * @param files         the uploaded files.
	 * @param targetFolder  the folder receiving the files, relative to the data
	 *                      source folder (absolute paths inside it are accepted
	 *                      too); its root when null or blank.
	 * @param relativePaths the path of each file, relative to the target folder, in
	 *                      the order of the files (the folders a whole folder
	 *                      upload keeps); the file name when missing.
	 * @return the updated endpoint.
	 * @throws IOException                       If a file cannot be written
	 * @throws GeboContentHandlerSystemException If the target folder is not one of
	 *                                           the data source
	 * @throws GeboPersistenceException          If the endpoint cannot be updated
	 */
	public GUploadsProjectEndpoint uploadToEndpoint(String endpointCode, List<MultipartFile> files,
			String targetFolder, List<String> relativePaths)
			throws IOException, GeboContentHandlerSystemException, GeboPersistenceException {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		GUploadsProjectEndpoint endpoint = findEndpoint(endpointCode);
		try {
			Path folder = resolveContentsFolder(endpoint, true);
			Path target = resolveTargetFolder(folder, targetFolder);
			if (target == null)
				throw new GeboContentHandlerSystemException(
						"The folder " + targetFolder + " is not part of the data source " + endpointCode);
			Files.createDirectories(target);
			final List<String> added = new ArrayList<String>();
			for (int i = 0; i < files.size(); i++) {
				MultipartFile entry = files.get(i);
				String path = relativePaths != null && i < relativePaths.size() && relativePaths.get(i) != null
						&& !relativePaths.get(i).isBlank() ? relativePaths.get(i) : entry.getOriginalFilename();
				Path relative = safeRelativePath(path);
				if (relative == null) {
					LOGGER.warn("Upload to " + endpointCode + ": the path " + path + " is not accepted, skipped");
					continue;
				}
				Path destination = target.resolve(relative).toAbsolutePath().normalize();
				if (!destination.startsWith(target) || destination.equals(target)) {
					LOGGER.warn("Upload to " + endpointCode + ": the path " + path + " leaves the folder, skipped");
					continue;
				}
				Files.createDirectories(destination.getParent());
				copy(entry, destination);
				final Path fromRoot = folder.relativize(destination);
				added.add(fromRoot.toString().replace(File.separatorChar, '/'));
			}
			GUploadsProjectEndpoint updated = endpoint;
			logContentEvent(event, SecurityAuditTaxonomy.Action.INTEGRATION_CONTENT_UPLOAD, endpoint, added,
					SecurityAuditTaxonomy.Outcome.SUCCESS);
			return updated;
		} catch (RuntimeException | IOException | GeboContentHandlerSystemException e) {
			logContentEvent(event, SecurityAuditTaxonomy.Action.INTEGRATION_CONTENT_UPLOAD, endpoint, List.of(),
					SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	/**
	 * Lists the files physically present in the persistent folder of an uploads
	 * endpoint, enriched with the ingestion state of the matching documents.
	 *
	 * @param endpointCode code of the uploads endpoint.
	 * @return the files of the data source, sorted by name.
	 * @throws GeboContentHandlerSystemException If the endpoint folder cannot be
	 *                                           resolved
	 * @throws IOException                       If the folder cannot be listed
	 */
	public List<UploadedFileInfo> listUploadedFiles(String endpointCode)
			throws GeboContentHandlerSystemException, IOException {
		GUploadsProjectEndpoint endpoint = findEndpoint(endpointCode);
		Path folder = resolveContentsFolder(endpoint, false);
		final TreeMap<String, UploadedFileInfo> listing = new TreeMap<String, UploadedFileInfo>();
		if (folder != null && Files.exists(folder) && Files.isDirectory(folder) && Files.isReadable(folder)) {
			try (Stream<Path> paths = Files.list(folder)) {
				paths.forEach(entry -> {
					Path fileName = entry.getFileName();
					if (fileName == null)
						return;
					UploadedFileInfo info = new UploadedFileInfo();
					info.name = fileName.toString();
					info.absolutePath = entry.toAbsolutePath().toString();
					int lastDot = info.name.lastIndexOf(".");
					info.extension = lastDot >= 0 ? info.name.substring(lastDot).toLowerCase(Locale.ROOT) : null;
					info.folder = Files.isDirectory(entry);
					File file = entry.toFile();
					info.size = file.length();
					info.modificationTime = file.lastModified() > 0 ? new Date(file.lastModified()) : null;
					listing.put(info.name, info);
				});
			}
		}
		// Join on the ingested documents so the editor can tell apart "uploaded" from
		// "already part of the knowledge base" and open the latter in the viewer.
		final Map<String, UploadedFileInfo> byAbsolutePath = new HashMap<String, UploadedFileInfo>();
		listing.values().forEach(x -> {
			if (x.absolutePath != null) {
				byAbsolutePath.put(x.absolutePath, x);
			}
		});
		try (Stream<GDocumentReference> documents = documentReferenceRepository.findByProjectEndpoint(endpoint)) {
			documents.forEach(doc -> {
				if (doc.getDeleted() != null && doc.getDeleted())
					return;
				UploadedFileInfo info = doc.getAbsolutePath() != null ? byAbsolutePath.get(doc.getAbsolutePath())
						: null;
				if (info == null && doc.getName() != null) {
					info = listing.get(doc.getName());
				}
				if (info != null) {
					info.ingested = true;
					info.documentCode = doc.getCode();
				}
			});
		}
		return new ArrayList<UploadedFileInfo>(listing.values());
	}

	/**
	 * The tree of the files and folders physically present in the persistent folder
	 * of an uploads endpoint, each file told published when a not deleted document
	 * reference exists for it (for a zip file: for one of the files it holds).
	 *
	 * @param endpointCode code of the uploads endpoint.
	 * @return the root of the tree: the data source folder, its entries as children.
	 * @throws GeboContentHandlerSystemException If the endpoint folder cannot be
	 *                                           resolved
	 * @throws IOException                       If the folder cannot be read
	 */
	public UploadedFileNode listUploadedFilesTree(String endpointCode)
			throws GeboContentHandlerSystemException, IOException {
		GUploadsProjectEndpoint endpoint = findEndpoint(endpointCode);
		Path folder = resolveContentsFolder(endpoint, false);
		// the documents of the data source by the file they come from
		final Map<String, String> documentByPath = new HashMap<String, String>();
		final Map<String, String> documentByArchive = new HashMap<String, String>();
		try (Stream<GDocumentReference> documents = documentReferenceRepository.findByProjectEndpoint(endpoint)) {
			documents.forEach(doc -> {
				if (doc.getDeleted() != null && doc.getDeleted())
					return;
				if (doc.getAbsolutePath() != null) {
					documentByPath.putIfAbsent(normalized(doc.getAbsolutePath()), doc.getCode());
				}
				if (doc.getAbsoluteArchivePath() != null) {
					documentByArchive.putIfAbsent(normalized(doc.getAbsoluteArchivePath()), doc.getCode());
				}
			});
		}
		UploadedFileNode root = new UploadedFileNode();
		root.name = "";
		root.relativePath = "";
		root.folder = true;
		if (folder != null && Files.isDirectory(folder) && Files.isReadable(folder)) {
			fillFolder(root, folder, folder, documentByPath, documentByArchive);
		}
		return root;
	}

	private void fillFolder(UploadedFileNode node, Path directory, Path root, Map<String, String> documentByPath,
			Map<String, String> documentByArchive) throws IOException {
		final List<Path> entries;
		try (Stream<Path> listed = Files.list(directory)) {
			entries = listed.sorted((a, b) -> {
				final boolean aFolder = Files.isDirectory(a), bFolder = Files.isDirectory(b);
				return aFolder != bFolder ? (aFolder ? -1 : 1)
						: a.getFileName().toString().compareToIgnoreCase(b.getFileName().toString());
			}).toList();
		}
		for (Path entry : entries) {
			if (entry.getFileName() == null || Files.isSymbolicLink(entry))
				continue;
			UploadedFileNode child = new UploadedFileNode();
			child.name = entry.getFileName().toString();
			child.relativePath = root.relativize(entry).toString().replace(File.separatorChar, '/');
			child.folder = Files.isDirectory(entry);
			File file = entry.toFile();
			child.modificationTime = file.lastModified() > 0 ? new Date(file.lastModified()) : null;
			if (child.folder) {
				fillFolder(child, entry, root, documentByPath, documentByArchive);
			} else {
				int lastDot = child.name.lastIndexOf(".");
				child.extension = lastDot >= 0 ? child.name.substring(lastDot).toLowerCase(Locale.ROOT) : null;
				child.size = file.length();
				child.filesCount = 1;
				final String path = normalized(entry.toAbsolutePath().toString());
				child.documentCode = documentByPath.containsKey(path) ? documentByPath.get(path)
						: documentByArchive.get(path);
				child.published = child.documentCode != null;
				child.publishedFilesCount = child.published ? 1 : 0;
			}
			node.children.add(child);
			node.size += child.size;
			node.filesCount += child.filesCount;
			node.publishedFilesCount += child.publishedFilesCount;
		}
	}

	private static String normalized(String path) {
		try {
			return Path.of(path).toAbsolutePath().normalize().toString();
		} catch (Throwable invalidPath) {
			return path;
		}
	}

	/**
	 * Creates a folder in the persistent folder of an uploads endpoint, with the
	 * missing ones above it.
	 *
	 * @param endpointCode code of the uploads endpoint.
	 * @param folderPath   the folder to create, relative to the data source folder,
	 *                     without "." or ".." parts.
	 * @return the outcome, with the user messages.
	 */
	public OperationStatus<UploadedFileNode> createFolder(String endpointCode, String folderPath) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		GUploadsProjectEndpoint endpoint = null;
		try {
			endpoint = findEndpoint(endpointCode);
			Path folder = resolveContentsFolder(endpoint, true);
			Path relative = safeRelativePath(folderPath);
			Path target = relative != null ? folder.resolve(relative).toAbsolutePath().normalize() : null;
			if (target == null || !target.startsWith(folder) || target.equals(folder)) {
				return OperationStatus.ofError("Cannot create the folder",
						"The folder " + folderPath + " is not a valid folder of this data source");
			}
			if (Files.exists(target) && !Files.isDirectory(target)) {
				return OperationStatus.ofError("Cannot create the folder",
						"A file named " + target.getFileName() + " already exists there");
			}
			Files.createDirectories(target);
			UploadedFileNode created = new UploadedFileNode();
			created.name = target.getFileName().toString();
			created.relativePath = folder.relativize(target).toString().replace(File.separatorChar, '/');
			created.folder = true;
			logContentEvent(event, SecurityAuditTaxonomy.Action.INTEGRATION_CONTENT_UPLOAD, endpoint,
					List.of(created.relativePath), SecurityAuditTaxonomy.Outcome.SUCCESS);
			return OperationStatus.of(created, List.of());
		} catch (Throwable exc) {
			LOGGER.error("Error creating the folder " + folderPath + " of:" + endpointCode, exc);
			logContentEvent(event, SecurityAuditTaxonomy.Action.INTEGRATION_CONTENT_UPLOAD, endpoint, List.of(),
					SecurityAuditTaxonomy.Outcome.FAILURE);
			return OperationStatus.of(exc);
		}
	}

	/**
	 * Removes files from the persistent folder of an uploads endpoint.
	 *
	 * <p>
	 * Only the filesystem is touched here: the
	 * documents already ingested from the removed files are reconciled by the next
	 * publish, when the ingestion pipeline detects the missing paths, flags the
	 * documents as deleted and asks the vectorization module to dispose of their
	 * embeddings. The returned status carries that expectation as a user message so
	 * the editor can surface it.
	 * </p>
	 *
	 * @param endpointCode code of the uploads endpoint.
	 * @param names        names of the files to remove, relative to the endpoint
	 *                     folder.
	 * @return the updated endpoint together with the outcome messages.
	 */
	public OperationStatus<GUploadsProjectEndpoint> deleteUploadedFiles(String endpointCode, List<String> names) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		GUploadsProjectEndpoint endpoint = null;
		try {
			endpoint = findEndpoint(endpointCode);
			Path folder = resolveContentsFolder(endpoint, false);
			if (folder == null) {
				return OperationStatus.ofError("Cannot delete contents",
						"The contents folder of the data source " + endpointCode + " does not exist");
			}
			List<String> removed = new ArrayList<String>();
			List<GUserMessage> messages = new ArrayList<GUserMessage>();
			if (names != null) {
				for (String name : names) {
					Path target = resolveDeletionTarget(folder, name);
					if (target == null) {
						messages.add(GUserMessage.errorMessage("Cannot delete " + name,
								"The entry is not part of the contents of this data source"));
						continue;
					}
					// named by its path in the data source, for the messages and the audit
					String removedName = folder.relativize(target).toString().replace(File.separatorChar, '/');
					try {
						if (Files.isDirectory(target)) {
							deleteRecursively(target);
							removed.add(removedName);
						} else if (Files.deleteIfExists(target)) {
							removed.add(removedName);
						} else {
							// Already gone on disk: still untrack it, the state the admin asked for is the
							// one we end up with.
							removed.add(removedName);
						}
					} catch (IOException ioException) {
						LOGGER.error("Error deleting uploaded content:" + target, ioException);
						messages.add(GUserMessage.errorMessage("Cannot delete " + removedName, ioException));
					}
				}
			}
			GUploadsProjectEndpoint updated = endpoint;
			logContentEvent(event, SecurityAuditTaxonomy.Action.INTEGRATION_CONTENT_DELETE, endpoint, removed,
					SecurityAuditTaxonomy.Outcome.SUCCESS);
			if (!removed.isEmpty()) {
				messages.add(GUserMessage.successMessage("Removed " + removed.size() + " file(s)",
						"Publish this data source to remove the corresponding contents from the knowledge base"));
			}
			return OperationStatus.of(updated, messages);
		} catch (Throwable exc) {
			LOGGER.error("Error deleting uploaded contents of:" + endpointCode, exc);
			logContentEvent(event, SecurityAuditTaxonomy.Action.INTEGRATION_CONTENT_DELETE, endpoint, List.of(),
					SecurityAuditTaxonomy.Outcome.FAILURE);
			return OperationStatus.of(exc);
		}
	}

	/**
	 * Resolves the persistent contents folder of an uploads endpoint by code.
	 *
	 * @param endpointCode    code of the uploads endpoint.
	 * @param createIfMissing when true the folder is created if it does not exist
	 *                        yet, so browsing an endpoint whose files were never
	 *                        uploaded shows an empty root rather than failing.
	 * @return the absolute contents folder.
	 * @throws GeboContentHandlerSystemException if the endpoint or its folder
	 *                                           cannot be resolved.
	 */
	public Path resolveContentsFolder(String endpointCode, boolean createIfMissing)
			throws GeboContentHandlerSystemException {
		return resolveContentsFolder(findEndpoint(endpointCode), createIfMissing);
	}

	/**
	 * Resolves a single file physically present in the contents folder of an
	 * uploads data source, so it can be streamed to the editor.
	 *
	 * <p>
	 * The path comes from the contents browser of the editor, hence from outside:
	 * it is accepted only when it resolves inside the folder owned by the data
	 * source, which is the boundary of what this data source may expose. Folders
	 * and entries that are not readable regular files are refused as well, a
	 * directory having nothing to serve.
	 * </p>
	 *
	 * @param endpointCode code of the uploads data source.
	 * @param path         absolute path, or simple name, of the file to serve.
	 * @return the file to serve, or {@code null} when it is not a readable file of
	 *         this data source.
	 * @throws GeboContentHandlerSystemException if the data source or its folder
	 *                                           cannot be resolved.
	 */
	public Path resolveServableFile(String endpointCode, String path) throws GeboContentHandlerSystemException {
		GUploadsProjectEndpoint endpoint = findEndpoint(endpointCode);
		Path folder = resolveContentsFolder(endpoint, false);
		if (folder == null)
			return null;
		Path target = resolveContainedEntry(folder, path);
		if (target == null)
			return null;
		if (!Files.exists(target) || Files.isDirectory(target) || !Files.isReadable(target))
			return null;
		logContentEvent(securityAuditLoggerService.newSecurityEvent(),
				SecurityAuditTaxonomy.Action.INTEGRATION_DATA_READ, endpoint,
				List.of(target.getFileName().toString()), SecurityAuditTaxonomy.Outcome.SUCCESS);
		return target;
	}

	/**
	 * Returns a human readable name for an uploads endpoint, falling back to the
	 * given default when the endpoint carries no description.
	 *
	 * @param endpointCode    code of the uploads endpoint.
	 * @param defaultValue    value to use when no description is available.
	 * @return the description to show.
	 */
	public String describeEndpoint(String endpointCode, String defaultValue) {
		try {
			GUploadsProjectEndpoint endpoint = findEndpoint(endpointCode);
			String description = endpoint.getDescription();
			return description != null && !description.trim().isEmpty() ? description : defaultValue;
		} catch (Throwable exc) {
			return defaultValue;
		}
	}

	private Path resolveContentsFolder(GUploadsProjectEndpoint endpoint, boolean createIfMissing)
			throws GeboContentHandlerSystemException {
		String baseFolder = localFolderDiscoveryService.getLocalPersistentFolder(handler.getSystem(endpoint), endpoint);
		if (baseFolder == null)
			throw new GeboContentHandlerSystemException(
					"Cannot resolve the contents folder of the data source " + endpoint.getCode());
		Path folder = Path.of(baseFolder).toAbsolutePath().normalize();
		if (createIfMissing && !Files.exists(folder)) {
			folder.toFile().mkdirs();
		}
		return folder;
	}

	private GUploadsProjectEndpoint findEndpoint(String endpointCode) throws GeboContentHandlerSystemException {
		if (endpointCode == null || endpointCode.trim().isEmpty())
			throw new GeboContentHandlerSystemException("A data source code is required");
		GUploadsProjectEndpoint endpoint = null;
		try {
			endpoint = persistentObjectManager.findById(GUploadsProjectEndpoint.class, endpointCode);
		} catch (GeboPersistenceException persistenceException) {
			throw new GeboContentHandlerSystemException("Cannot read the uploads data source " + endpointCode,
					persistenceException);
		}
		if (endpoint == null)
			throw new GeboContentHandlerSystemException("Cannot find the uploads data source " + endpointCode);
		return endpoint;
	}


	/**
	 * Resolves an entry the caller asked to delete against the contents folder of
	 * the data source.
	 *
	 * <p>
	 * The editor browses the data source as a tree, so an entry can be nested: it
	 * therefore sends the absolute path of what it signed, and a bare file name is
	 * still accepted for the flat case. Resolving a nested entry by its leaf name
	 * alone would address a different file sitting at the root of the folder, which
	 * is why the absolute form is resolved as such. Whatever the form, the result
	 * must stay inside the folder, which is the boundary of what this data source
	 * owns.
	 * </p>
	 *
	 * @param folder the contents folder of the data source.
	 * @param name   the absolute path or the simple file name of the entry.
	 * @return the entry to delete, or {@code null} when it does not belong to this
	 *         data source.
	 */
	private Path resolveDeletionTarget(Path folder, String name) {
		return resolveContainedEntry(folder, name);
	}

	/**
	 * Resolves an entry addressed by the editor against the contents folder of a
	 * data source, refusing anything that does not live inside it.
	 *
	 * <p>
	 * The containment check is the security boundary of every operation addressing
	 * a single entry: the caller hands over a path coming from the browser, so a
	 * path walking out of the folder - or the folder itself - has to be rejected
	 * before the entry is read or removed.
	 * </p>
	 *
	 * @param folder the contents folder of the data source.
	 * @param name   the absolute path or the simple file name of the entry.
	 * @return the entry, or {@code null} when it does not belong to this data
	 *         source.
	 */
	private Path resolveContainedEntry(Path folder, String name) {
		if (name == null)
			return null;
		String trimmed = name.trim();
		if (trimmed.isEmpty())
			return null;
		Path target = null;
		try {
			Path candidate = Path.of(trimmed);
			target = (candidate.isAbsolute() ? candidate : folder.resolve(candidate)).toAbsolutePath().normalize();
		} catch (Throwable invalidPath) {
			return null;
		}
		if (target.getFileName() == null)
			return null;
		// The folder itself is not deletable, only what it contains.
		if (target.equals(folder) || !target.startsWith(folder))
			return null;
		return target;
	}

	private void deleteRecursively(Path root) throws IOException {
		try (Stream<Path> walk = Files.walk(root)) {
			List<Path> entries = walk.sorted((a, b) -> b.getNameCount() - a.getNameCount()).toList();
			for (Path entry : entries) {
				Files.deleteIfExists(entry);
			}
		}
	}

	private void logContentEvent(SecurityEvent event, String action, GUploadsProjectEndpoint endpoint,
			List<String> files, String outcome) {
		try {
			event.setEventType(SecurityAuditTaxonomy.EventType.INTEGRATION_CONFIGURATION);
			event.setCategory(SecurityAuditTaxonomy.Category.INTEGRATION_CONFIGURATION);
			event.setAction(action);
			event.setResourceType(GUploadsProjectEndpoint.class.getName());
			event.setResourceId(endpoint != null ? endpoint.getCode() : null);
			event.setOutcome(outcome);
			event.getDetails().put("files", files);
			securityAuditLoggerService.log(event);
		} catch (Throwable exc) {
			LOGGER.error("Error logging the security audit event", exc);
		}
	}

	/**
	 * The folder an upload targets: the data source folder when none is given, else
	 * the given one when it resolves inside the data source folder.
	 */
	private Path resolveTargetFolder(Path folder, String targetFolder) {
		if (targetFolder == null || targetFolder.isBlank() || "/".equals(targetFolder.trim()))
			return folder;
		Path target = resolveContainedEntry(folder, targetFolder);
		return target != null && (!Files.exists(target) || Files.isDirectory(target)) ? target : null;
	}

	/**
	 * A path coming from the browser, accepted only when it is relative and made of
	 * plain names ("/" or "\\" separated, none being "." or ".."), so it can never
	 * address a location outside the folder it is resolved against.
	 *
	 * @param path the candidate path.
	 * @return the path, or {@code null} when it is not accepted.
	 */
	static Path safeRelativePath(String path) {
		if (path == null)
			return null;
		final String trimmed = path.trim().replace('\\', '/');
		if (trimmed.isEmpty() || trimmed.startsWith("/") || trimmed.contains(":"))
			return null;
		Path relative = null;
		for (String part : trimmed.split("/")) {
			if (part.isEmpty())
				continue;
			if (".".equals(part) || "..".equals(part) || part.isBlank())
				return null;
			relative = relative == null ? Path.of(part) : relative.resolve(part);
		}
		return relative;
	}

	/**
	 * Rejects anything that is not a simple file name, so an upload or a deletion
	 * can never address a location outside the endpoint folder.
	 *
	 * @param name the candidate name.
	 * @return the file name, or {@code null} when it is empty or carries a path.
	 */
	private String safeFileName(String name) {
		if (name == null)
			return null;
		String trimmed = name.trim();
		if (trimmed.isEmpty())
			return null;
		Path candidate = Path.of(trimmed).getFileName();
		if (candidate == null)
			return null;
		String fileName = candidate.toString();
		if (fileName.isEmpty() || ".".equals(fileName) || "..".equals(fileName))
			return null;
		return fileName.equals(trimmed) ? fileName : null;
	}

	/**
	 * Processes an upload by moving files from temporary storage to their final location
	 * and updating the endpoint with the uploaded content information.
	 *
	 * <p>
	 * Contrary to the original behaviour this runs whenever a handshake code is
	 * present, not only when the endpoint has no contents yet: an endpoint whose
	 * files were already uploaded can receive further batches. The staged files are
	 * moved into the folder of the data source and the handshake code is cleared
	 * once consumed, so the same code is never applied twice.
	 * </p>
	 *
	 * @param endpoint The endpoint associated with the upload
	 * @return Updated endpoint with file information
	 * @throws GeboPersistenceException If there's an error with persistence
	 * @throws GeboContentHandlerSystemException If there's an error in the content handler
	 * @throws IOException If there's an error during file operations
	 */
	private GUploadsProjectEndpoint handleUpload(GUploadsProjectEndpoint endpoint)
			throws GeboPersistenceException, GeboContentHandlerSystemException, IOException {
		GUploadsProjectEndpoint returned = endpoint;
		String uploadCode = endpoint.getUploadHandshakeCode();
		if (uploadCode != null) {
			Optional<TmpUploadedContents> entry = uploadedContentsRepository.findById(uploadCode);
			if (entry.isPresent()) {
				TmpUploadedContents value = entry.get();
				if (geboConfig.getGeboWorkDirectory() == null)
					throw new RuntimeException("Gebo working directory is not set");
				Path baseFolder = resolveContentsFolder(endpoint, true);
				List<File> toRemove = new ArrayList<File>();
				List<String> staged = new ArrayList<String>();
				if (value.getUploadedContents() != null) {
					for (String fileName : value.getUploadedContents()) {
						Path path = Path.of(geboConfig.getGeboWorkDirectory(), relativeUploadFolder, uploadCode,
								fileName);
						File file = path.toFile();
						File out = baseFolder.resolve(fileName).toFile();
						FileCopyUtils.copy(file, out);
						toRemove.add(file);
						staged.add(fileName);
					}
				}
				// The handshake code is consumed here: leaving it on the endpoint would make a
				// later save re-apply a staging area that no longer exists.
				endpoint.setUploadHandshakeCode(null);
				returned = persistentObjectManager.update(endpoint);
				for (File file : toRemove) {
					file.delete();
				}
				uploadedContentsRepository.deleteById(uploadCode);
			}
		}
		return returned;
	}

	/**
	 * Updates an existing uploads project endpoint and handles any associated file uploads.
	 *
	 * @param endpoint The endpoint to be updated
	 * @return The updated endpoint
	 * @throws GeboPersistenceException If there's an error with persistence
	 * @throws GeboContentHandlerSystemException If there's an error in the content handler
	 * @throws IOException If there's an error during file operations
	 */
	public GUploadsProjectEndpoint update(GUploadsProjectEndpoint endpoint)
			throws GeboPersistenceException, GeboContentHandlerSystemException, IOException {

		return handleUpload(endpoint);

	}

	/**
	 * Copies a MultipartFile to a specified path.
	 *
	 * @param entry The MultipartFile to be copied
	 * @param movedPath The destination path
	 * @throws IOException If there's an error during file operations
	 */
	private void copy(MultipartFile entry, Path movedPath) throws IOException {
		File file = movedPath.toFile();
		entry.transferTo(file);
	}

}
