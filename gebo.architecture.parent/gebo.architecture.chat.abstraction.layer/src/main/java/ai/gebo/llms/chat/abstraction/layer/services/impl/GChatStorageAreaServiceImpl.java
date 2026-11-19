package ai.gebo.llms.chat.abstraction.layer.services.impl;

import ai.gebo.llms.abstraction.layer.model.GModelInputModality;
import ai.gebo.llms.chat.abstraction.layer.config.ChatMediaConfig;
import ai.gebo.llms.abstraction.layer.model.IChatRequestMedia;
import ai.gebo.llms.abstraction.layer.model.GModelCarriedMediaTypes;
import ai.gebo.acl.AclGrantType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.content.Media;
import org.springframework.ai.document.Document;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tools.jackson.databind.ObjectMapper;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadContentServerSide;
import ai.gebo.knlowledgebase.model.contents.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.model.SerializedDocumentContent;
import ai.gebo.llms.chat.abstraction.layer.model.SerializedDocumentsContent;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.LLMGeneratedResourceRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.UserUploadContentServerSideRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatUploadFileTypesService;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.model.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler.IngestionHandlerData;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GChatStorageAreaServiceImpl implements IGChatStorageAreaService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GChatStorageAreaServiceImpl.class);
	final IGGeboConfigService configurationService;
	final UserUploadContentServerSideRepository uploadContentsRepository;
	final GUserChatSessionRepository userChatContextRepository;
	final IGSecurityService securityService;
	final IGDocumentReferenceIngestionHandler ingestionHandler;
	final IGDocumentReferenceFactory documentReferenceFactory;
	final LLMGeneratedResourceRepository llmGeneratedResourceRepository;
	final IGChatUploadFileTypesService uploadFileTypes;
	final ChatMediaConfig mediaConfig;
	final static String SESSIONS_PATH_PREFIX = "SESSIONS_AREA";
	final static ObjectMapper objectMapper = new ObjectMapper();
	final static JTokkitTokenCountEstimator tokenCountEstimator = new JTokkitTokenCountEstimator();

	@Override
	public Path getSessionPath(String userChatContextCode) throws IOException {
		this.checkBeingOwnerAndExists(userChatContextCode);
		UserInfos currentUser = securityService.getCurrentUser();

		String workDirectory = configurationService.getGeboWorkDirectory();
		Path parentPath = Path.of(workDirectory, SESSIONS_PATH_PREFIX);
		if (!Files.exists(parentPath)) {
			Files.createDirectories(parentPath);
		}
		Path path = Path.of(workDirectory, SESSIONS_PATH_PREFIX, userChatContextCode);
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
		return path;

	}

	private void checkBeingOwnerAndExists(String userChatContextCode) {
		Optional<GUserChatSession> opt = this.userChatContextRepository.findById(userChatContextCode);
		if (opt.isEmpty())
			throw new RuntimeException("The session " + userChatContextCode + " does not exist");
		this.securityService.checkBeingCreatorOrGranted(opt.get(), AclGrantType.WRITE);
	}

	private String getExtension(MultipartFile file) {
		int index = file.getOriginalFilename() != null ? file.getOriginalFilename().lastIndexOf(".") : -1;
		if (index >= 0) {
			return file.getOriginalFilename().substring(index).toLowerCase();
		} else
			return null;
	}

	@Override
	public UserUploadContentServerSide addUploadedFile(String userSessionCode, MultipartFile file)
			throws IOException, GeboContentHandlerSystemException, GeboIngestionException {
		this.checkBeingOwnerAndExists(userSessionCode);
		UserInfos actualUser = securityService.getCurrentUser();
		Path path = getSessionPath(userSessionCode);
		UserUploadContentServerSide serverSide = new UserUploadContentServerSide();
		serverSide.setContentType(file.getContentType());
		serverSide.setFileName(file.getOriginalFilename());
		serverSide.setFileSize(file.getSize());
		serverSide.setDescription(file.getOriginalFilename());
		serverSide.setUserContextCode(userSessionCode);
		serverSide.setExtension(getExtension(file));
		serverSide.setUserCreated(actualUser.getUsername());
		serverSide.setUserModified(actualUser.getUsername());
		serverSide.setDateCreated(new Date());
		serverSide.setDateModified(new Date());
		serverSide.setRelativeFilePath(
				serverSide.getCode() + (serverSide.getExtension() != null ? serverSide.getExtension() : ""));
		Path newPath = Path.of(path.toString(), serverSide.getRelativeFilePath());

		file.transferTo(newPath);
		GDocumentReference reference = documentReferenceFactory.createReference(newPath);
		final boolean forTheModel = GModelCarriedMediaTypes
				.matchesAny(uploadFileTypes.modelMimeTypesOfSession(userSessionCode), mimeTypeOf(serverSide));
		// a picture, a recording or a video the chat's model takes as it is goes to the model
		// itself: it is not described or transcribed first, the model sees or hears it
		final GModelInputModality modality = GModelInputModality.ofMimeType(mimeTypeOf(serverSide));
		final boolean straightToTheModel = forTheModel && (modality == GModelInputModality.IMAGE
				|| modality == GModelInputModality.AUDIO || modality == GModelInputModality.VIDEO);
		final InputStream content = Files.newInputStream(newPath);
		IngestionHandlerData data = straightToTheModel ? null : ingestionHandler.handleContent(reference, content);
		if (data == null || data.isUnmanagedContent()) {
			content.close();
			// no content handler reads it: it is kept when the chat's model takes it as it is
			// (a picture, a PDF, a recording) or when the chat's agents' tools work on it
			final boolean forTheTools = serverSide.getExtension() != null
					&& uploadFileTypes.toolsFileTypesOfSession(userSessionCode).contains(serverSide.getExtension());
			if (!forTheModel && !forTheTools) {
				// refused: it does not stay behind in the session's folder
				Files.deleteIfExists(newPath);
				return null;
			}
			// it has no ingested text: it reaches a model as a file of the request
			// (getSessionMedia) when that model takes it, and the tools by its location
			serverSide.setTokensCount(0L);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("addUploadedFile(...) session:" + userSessionCode + " file:" + file.getOriginalFilename()
						+ " kept for the agents' tools only");
			}
			return uploadContentsRepository.insert(serverSide);
		} else {
			HashMap<String, Object> commonMetaData = this.createCommonMetaData(serverSide);

			serverSide.setIngestedJsonPath(serverSide.getCode() + "-ingest.json");
			Path jsonPath = Path.of(path.toString(), serverSide.getIngestedJsonPath());
			List<SerializedDocumentContent> documents = data.getStream().map(x -> {
				SerializedDocumentContent doc = new SerializedDocumentContent(x.getId(), x.getText(), x.getMetadata());
				if (doc.getMetaData() == null) {
					doc.setMetaData(new HashMap<>(commonMetaData));
				} else {
					// the handler's own metadata, with the upload's common ones over them
					HashMap newMeta = new HashMap<>(doc.getMetaData());
					newMeta.putAll(commonMetaData);
					doc.setMetaData(newMeta);
				}
				if (doc.getContent() != null && !doc.getContent().trim().isEmpty()) {
					int tokens = this.tokenCountEstimator.estimate(doc.getContent());
					doc.setMetaData(new HashMap<String, Object>(doc.getMetaData()));
					doc.getMetaData().put(DocumentMetaInfos.GEBO_TOKEN_LENGTH, tokens);
				}
				return doc;
			}).toList();
			SerializedDocumentsContent serialized = new SerializedDocumentsContent(documents);
			objectMapper.writeValue(jsonPath.toFile(), serialized);
			content.close();
		}
		serverSide = uploadContentsRepository.insert(serverSide);
		return serverSide;
	}

	private HashMap<String, Object> createCommonMetaData(UserUploadContentServerSide serverSide) {
		HashMap<String, Object> commonMetaData = new HashMap<>();
		if (serverSide.getContentType() != null)
			commonMetaData.put(DocumentMetaInfos.CONTENT_TYPE, serverSide.getContentType());
		if (serverSide.getExtension() != null)
			commonMetaData.put(DocumentMetaInfos.CONTENT_EXTENSION, serverSide.getExtension());
		commonMetaData.put(DocumentMetaInfos.CONTENT_CODE, serverSide.getCode());
		commonMetaData.put(DocumentMetaInfos.CONTENT_DESCRIPTION, "User uploaded file " + serverSide.getDescription());
		commonMetaData.put(DocumentMetaInfos.GEBO_FILE_NAME, serverSide.getFileName());
		return commonMetaData;
	}

	@Override
	public void deleteUploadedFile(UserUploadContentServerSide ss) throws IOException {
		this.securityService.checkBeingCreator(ss);
		Optional<GUserChatSession> contextData = userChatContextRepository.findById(ss.getUserContextCode());
		if (contextData.isEmpty())
			throw new RuntimeException("Cannot access this chat context because it does not exist");
		uploadContentsRepository.delete(ss);
		Path path = getSessionPath(ss.getUserContextCode());
		Path filePath = Path.of(path.toString(), ss.getRelativeFilePath());
		Path jsonPath = ss.getIngestedJsonPath() != null ? Path.of(path.toString(), ss.getIngestedJsonPath()) : null;
		if (jsonPath != null) {
			Files.deleteIfExists(jsonPath);
		}
		Files.deleteIfExists(filePath);

	}

	@Override
	public void deleteSessionContents(String userSessionCode) throws IOException {
		this.checkBeingOwnerAndExists(userSessionCode);
		Path path = getSessionPath(userSessionCode);
		DirectoryStream<Path> data = Files.newDirectoryStream(path);
		data.forEach(x -> {
			try {
				Files.deleteIfExists(x);
			} catch (IOException e) {
				LOGGER.error("Error deleting session content file " + x, e);
			}
		});
		Files.deleteIfExists(path);
		this.llmGeneratedResourceRepository.deleteByUserContextCode(userSessionCode);
		this.uploadContentsRepository.deleteByUserContextCode(userSessionCode);
	}

	@Override
	public Path getUploadedFilePath(UserUploadContentServerSide ss) throws IOException {
		this.securityService.checkBeingCreator(ss);
		this.checkBeingOwnerAndExists(ss.getUserContextCode());
		Path path = getSessionPath(ss.getUserContextCode());
		Path filePath = Path.of(path.toString(), ss.getRelativeFilePath());
		return filePath;
	}

	@Override
	public List<Document> getIngestedContentsOf(UserUploadContentServerSide ss) throws IOException {
		this.securityService.checkBeingCreator(ss);
		this.checkBeingOwnerAndExists(ss.getUserContextCode());
		Path path = getSessionPath(ss.getUserContextCode());
		if (!Files.exists(path))
			return null;
		Path jsonPath = ss.getIngestedJsonPath() != null ? Path.of(path.toString(), ss.getIngestedJsonPath()) : null;
		if (jsonPath != null) {

			SerializedDocumentsContent data = objectMapper.readValue(jsonPath.toFile(),
					SerializedDocumentsContent.class);
			return data.getDocuments() != null ? data.getDocuments().stream()
					.map(x -> new Document(x.getId(), x.getContent(), x.getMetaData())).toList() : null;
		}
		return null;
	}

	@Override
	public OperationStatus<List<UserUploadedContent>> deleteUploadedContents(String userSessionCode, List<String> id) {
		Optional<GUserChatSession> contextData = userChatContextRepository.findById(userSessionCode);
		if (contextData.isEmpty())
			throw new RuntimeException("Cannot access this chat context because it does not exist");
		this.securityService.checkBeingCreatorOrGranted(contextData.get(), AclGrantType.WRITE);
		List<UserUploadedContent> list = new ArrayList<>();
		List<GUserMessage> messages = new ArrayList<>();
		List<UserUploadContentServerSide> data = uploadContentsRepository.findAllById(id);
		for (UserUploadContentServerSide userUploadContentServerSide : data) {
			this.securityService.checkBeingCreator(userUploadContentServerSide);
			if (!userSessionCode.equals(userUploadContentServerSide.getUserContextCode())) {
				throw new SecurityException("The uploaded content " + userUploadContentServerSide.getCode()
						+ " does not belong to the chat " + userSessionCode);
			}
		}
		for (UserUploadContentServerSide userUploadContentServerSide : data) {
			try {
				deleteUploadedFile(userUploadContentServerSide);
				list.add(new UserUploadedContent(userUploadContentServerSide));
			} catch (Throwable t) {
				LOGGER.error("Error deleting uploaded content "
						+ (userUploadContentServerSide != null ? userUploadContentServerSide.getCode() : "Null object"),
						t);
			}
		}
		OperationStatus<List<UserUploadedContent>> status = OperationStatus.of(list.isEmpty() ? null : list);
		if (!messages.isEmpty()) {
			status.setMessages(messages);
		}
		return status;
	}

	@Override
	public InputStream getContent(UserUploadedContent content) throws IOException {
		Optional<GUserChatSession> contextData = userChatContextRepository.findById(content.getUserContextCode());
		Optional<UserUploadContentServerSide> serverSideContent = this.uploadContentsRepository
				.findById(content.getCode());
		if (contextData.isPresent() && serverSideContent.isPresent()) {
			GUserChatSession ctx = contextData.get();
			UserUploadContentServerSide ssc = serverSideContent.get();
			this.securityService.checkBeingCreatorOrGranted(ctx, AclGrantType.READ);
			this.securityService.checkBeingCreator(ssc);
			Path path = this.getUploadedFilePath(ssc);
			return Files.newInputStream(path);
		}
		return null;
	}

	@Override
	public List<Document> getIngestedContentsOf(UserUploadedContent uploaded) throws IOException {
		Optional<UserUploadContentServerSide> data = this.uploadContentsRepository.findById(uploaded.getCode());
		if (data.isPresent()) {
			return this.getIngestedContentsOf(data.get());
		}
		return null;
	}

	@Override
	public LLMGeneratedResource addMedia(Media media, String userSessionCode) throws IOException {
		this.checkBeingOwnerAndExists(userSessionCode);
		LLMGeneratedResource resource = new LLMGeneratedResource();
		resource.setCode(UUID.randomUUID().toString());
		resource.setDescription(media.getName());
		resource.setFileName(media.getName());
		resource.setUserContextCode(userSessionCode);
		// the whole type, image/png and not image: it is the Content-Type the file is served with
		resource.setContentType(media.getMimeType() != null
				? media.getMimeType().getType() + "/" + media.getMimeType().getSubtype()
				: null);
		String extension = getExtension(media);
		resource.setExtension(extension);
		Path path = getSessionPath(userSessionCode);
		Path out = Path.of(path.toString(), getGeneratedResourceRelativeFilePath(resource));
		final byte[] bytes = media.getDataAsByteArray();
		try (OutputStream os = Files.newOutputStream(out)) {
			IOUtils.write(bytes, os);
			os.flush();
		}
		resource.setFileSize(bytes != null ? (long) bytes.length : null);
		resource = llmGeneratedResourceRepository.insert(resource);
		return resource;
	}

	// LLMGeneratedResource is stored on disk with its extension so that it can
	// later be re-read as a properly typed file (content-type probing, ingestion) -
	// mirroring UserUploadContentServerSide.getRelativeFilePath().
	private String getGeneratedResourceRelativeFilePath(LLMGeneratedResource resource) {
		return resource.getCode() + (resource.getExtension() != null ? resource.getExtension() : "");
	}

	private String getExtension(Media media) {
		int index = media.getName() != null ? media.getName().lastIndexOf(".") : -1;
		if (index >= 0) {
			return media.getName().substring(index).toLowerCase();
		} else {

			return null;
		}
	}

	@Override
	public LLMGeneratedResource getGeneratedContent(String userSessionCode, String generatedResourceCode)
			throws IOException {
		Optional<GUserChatSession> contextData = userChatContextRepository.findById(userSessionCode);
		if (contextData.isPresent()) {
			securityService.checkBeingCreatorOrGranted(contextData.get(), AclGrantType.READ);
			Optional<LLMGeneratedResource> data = this.llmGeneratedResourceRepository.findById(generatedResourceCode);
			if (data.isPresent())
				return data.get();
		}
		return null;
	}

	@Override
	public InputStream streamContent(LLMGeneratedResource generated) throws IOException {
		this.checkBeingOwnerAndExists(generated.getUserContextCode());
		Path outPath = getGeneratedResourceFilePath(generated);
		return Files.newInputStream(outPath);
	}

	private Path getGeneratedResourceFilePath(LLMGeneratedResource generated) throws IOException {
		Path path = getSessionPath(generated.getUserContextCode());
		return Path.of(path.toString(), getGeneratedResourceRelativeFilePath(generated));
	}

	@Override
	public List<Document> getIngestedContentsOf(LLMGeneratedResource generated)
			throws IOException, GeboContentHandlerSystemException, GeboIngestionException {
		Path filePath = getGeneratedResourceFilePath(generated);
		if (!Files.exists(filePath))
			return List.of();
		// The reference must be built from the real on-disk path (with its
		// extension) so createReference() can stat the file and probe its
		// content type - generated.getFileName() is only a display label, not
		// a resolvable path, and was previously (wrongly) passed here directly,
		// which is what threw NoSuchFileException for generated images.
		GDocumentReference doc = documentReferenceFactory.createReference(filePath);
		try (InputStream is = Files.newInputStream(filePath)) {
			IngestionHandlerData ingested = ingestionHandler.handleContent(doc, is);
			if (ingested.isUnmanagedContent())
				return List.of();
			return ingested.getStream().toList();
		}
	}


	/**
	 * The MIME type of an upload: the one it was sent with, else the one its name tells.
	 */
	static String mimeTypeOf(UserUploadedContent upload) {
		String type = GModelCarriedMediaTypes.essence(upload.getContentType());
		if (type == null || "application/octet-stream".equals(type)) {
			final String byName = upload.getFileName() != null
					? java.net.URLConnection.guessContentTypeFromName(upload.getFileName())
					: null;
			if (byName != null) {
				type = GModelCarriedMediaTypes.essence(byName);
			}
		}
		return type;
	}

	/** A file of a conversation that may go to a model as it is. */
	private record MediaFile(String name, String mimeType, long size, Path file, Date created) {
	}

	@Override
	public List<IChatRequestMedia> getSessionMedia(String userSessionCode) {
		if (userSessionCode == null) {
			return List.of();
		}
		final List<MediaFile> files = new ArrayList<>();
		try {
			final Path sessionPath = getSessionPath(userSessionCode);
			// the uploads with no ingested text
			try (java.util.stream.Stream<UserUploadContentServerSide> all = uploadContentsRepository
					.findByUserContextCode(userSessionCode)) {
				all.filter(x -> x.getIngestedJsonPath() == null && x.getRelativeFilePath() != null)
						.forEach(x -> files.add(new MediaFile(x.getFileName(), mimeTypeOf(x),
								x.getFileSize() != null ? x.getFileSize() : -1,
								Path.of(sessionPath.toString(), x.getRelativeFilePath()), x.getDateCreated())));
			}
			// the pictures, recordings and videos the models generated in the conversation, so
			// that a later question about one reaches a model that can look at it
			try (java.util.stream.Stream<LLMGeneratedResource> all = llmGeneratedResourceRepository
					.findByUserContextCode(userSessionCode)) {
				all.forEach(x -> {
					final String mimeType = mimeTypeOf(x);
					final GModelInputModality modality = GModelInputModality.ofMimeType(mimeType);
					if (modality == GModelInputModality.IMAGE || modality == GModelInputModality.AUDIO
							|| modality == GModelInputModality.VIDEO) {
						files.add(new MediaFile(x.getFileName(), mimeType, x.getFileSize() != null ? x.getFileSize() : -1,
								Path.of(sessionPath.toString(), getGeneratedResourceRelativeFilePath(x)),
								x.getDateCreated()));
					}
				});
			}
		} catch (IOException | RuntimeException e) {
			LOGGER.error("Cannot read the files of session " + userSessionCode + ", its requests go without them", e);
			return List.of();
		}
		// newest first: the limits keep the latest files
		files.sort(java.util.Comparator
				.comparing(MediaFile::created, java.util.Comparator.nullsFirst(java.util.Comparator.naturalOrder()))
				.reversed());
		final List<IChatRequestMedia> media = new ArrayList<>();
		long total = 0;
		for (MediaFile candidate : files) {
			final String mimeType = candidate.mimeType();
			final Path file = candidate.file();
			long known = candidate.size();
			if (mimeType == null || !Files.exists(file)) {
				continue;
			}
			if (known < 0) {
				try {
					known = Files.size(file);
				} catch (IOException e) {
					continue;
				}
			}
			final long size = known;
			if (media.size() >= mediaConfig.getMaxFiles() || size > mediaConfig.getMaxFileBytes()
					|| total + size > mediaConfig.getMaxTotalBytes()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("getSessionMedia(...) session:" + userSessionCode + " file:" + candidate.name() + " ("
							+ size + " bytes) is beyond the limits of ai.gebo.chat.media, it stays with the tools");
				}
				continue;
			}
			total += size;
			final String name = candidate.name();
			media.add(new IChatRequestMedia() {
				@Override
				public String getName() {
					return name;
				}

				@Override
				public String getMimeType() {
					return mimeType;
				}

				@Override
				public long getSize() {
					return size;
				}

				@Override
				public byte[] readBytes() throws IOException {
					return Files.readAllBytes(file);
				}
			});
		}
		return media;
	}

	/**
	 * The MIME type of a generated file: its stored one when whole, else the one its
	 * name or extension tells (the files stored before the whole type was kept have the
	 * family only, as "image").
	 */
	static String mimeTypeOf(LLMGeneratedResource generated) {
		String type = GModelCarriedMediaTypes.essence(generated.getContentType());
		if (type == null) {
			final String name = generated.getExtension() != null ? "file" + generated.getExtension()
					: generated.getFileName();
			final String byName = name != null ? java.net.URLConnection.guessContentTypeFromName(name) : null;
			type = GModelCarriedMediaTypes.essence(byName);
		}
		return type;
	}
}
