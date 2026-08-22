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
 * ({@link #deleteUploadedFiles(String, List)}) only touch the filesystem and the
 * tracked contents list: the knowledge base is reconciled by the standard
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
	 * @return the updated endpoint, with the new names tracked in its uploaded
	 *         contents.
	 * @throws IOException                       If there's an error during file
	 *                                           operations
	 * @throws GeboContentHandlerSystemException If the endpoint folder cannot be
	 *                                           resolved
	 * @throws GeboPersistenceException          If the endpoint cannot be updated
	 */
	public GUploadsProjectEndpoint uploadToEndpoint(String endpointCode, List<MultipartFile> files)
			throws IOException, GeboContentHandlerSystemException, GeboPersistenceException {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		GUploadsProjectEndpoint endpoint = findEndpoint(endpointCode);
		try {
			Path folder = resolveContentsFolder(endpoint, true);
			List<String> added = new ArrayList<String>();
			for (MultipartFile entry : files) {
				String fileName = safeFileName(entry.getOriginalFilename());
				if (fileName == null)
					continue;
				copy(entry, folder.resolve(fileName));
				added.add(fileName);
			}
			GUploadsProjectEndpoint updated = trackContents(endpoint, added, List.of());
			logContentEvent(event, SecurityAuditTaxonomy.Action.INTEGRATION_CONTENT_UPLOAD, endpoint, added,
					SecurityAuditTaxonomy.Outcome.SUCCESS);
			return updated;
		} catch (RuntimeException | IOException | GeboContentHandlerSystemException | GeboPersistenceException e) {
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
		final Set<String> tracked = endpoint.getUploadedContents() != null
				? new LinkedHashSet<String>(endpoint.getUploadedContents())
				: Set.of();
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
					info.tracked = tracked.contains(info.name);
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
	 * Removes files from the persistent folder of an uploads endpoint.
	 *
	 * <p>
	 * Only the filesystem and the tracked contents list are touched here: the
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
					// Only the leaf name is tracked in the uploaded contents list, nested entries
					// were never tracked there in the first place.
					String trackedName = target.getFileName().toString();
					try {
						if (Files.isDirectory(target)) {
							deleteRecursively(target);
							removed.add(trackedName);
						} else if (Files.deleteIfExists(target)) {
							removed.add(trackedName);
						} else {
							// Already gone on disk: still untrack it, the state the admin asked for is the
							// one we end up with.
							removed.add(trackedName);
						}
					} catch (IOException ioException) {
						LOGGER.error("Error deleting uploaded content:" + target, ioException);
						messages.add(GUserMessage.errorMessage("Cannot delete " + trackedName, ioException));
					}
				}
			}
			GUploadsProjectEndpoint updated = trackContents(endpoint, List.of(), removed);
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
	 * Keeps {@link GUploadsProjectEndpoint#getUploadedContents()} aligned with what
	 * the folder holds after an addition or a removal, preserving insertion order
	 * and never duplicating a name.
	 */
	private GUploadsProjectEndpoint trackContents(GUploadsProjectEndpoint endpoint, List<String> added,
			List<String> removed) throws GeboPersistenceException {
		Set<String> contents = new LinkedHashSet<String>(
				endpoint.getUploadedContents() != null ? endpoint.getUploadedContents() : List.of());
		contents.addAll(added);
		contents.removeAll(removed);
		endpoint.setUploadedContents(new ArrayList<String>(contents));
		return persistentObjectManager.update(endpoint);
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
	 * files were already uploaded can receive further batches. The staged names are
	 * merged into the tracked contents and the handshake code is cleared once
	 * consumed, so the same code is never applied twice.
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
				returned = trackContents(endpoint, staged, List.of());
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
