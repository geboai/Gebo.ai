package ai.gebo.llms.chat.abstraction.layer.services.impl;

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
import org.springframework.ai.content.Media;
import org.springframework.ai.document.Document;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.model.SerializedDocumentContent;
import ai.gebo.llms.chat.abstraction.layer.model.SerializedDocumentsContent;
import ai.gebo.llms.chat.abstraction.layer.model.UserUploadContentServerSide;
import ai.gebo.llms.chat.abstraction.layer.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatContextRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.LLMGeneratedResourceRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.UserUploadContentServerSideRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler.IngestionHandlerData;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GChatStorageAreaServiceImpl implements IGChatStorageAreaService {
	final IGGeboConfigService configurationService;
	final UserUploadContentServerSideRepository uploadContentsRepository;
	final GUserChatContextRepository userChatContextRepository;
	final IGSecurityService securityService;
	final IGDocumentReferenceIngestionHandler ingestionHandler;
	final IGDocumentReferenceFactory documentReferenceFactory;
	final LLMGeneratedResourceRepository llmGeneratedResourceRepository;
	final static String SESSIONS_PATH_PREFIX = "SESSIONS_AREA";
	final static ObjectMapper objectMapper = new ObjectMapper();
	final static JTokkitTokenCountEstimator tokenCountEstimator = new JTokkitTokenCountEstimator();

	@Override
	public Path getSessionPath(GUserChatContext context) throws IOException {
		this.securityService.checkBeingCreator(context);
		UserInfos currentUser = securityService.getCurrentUser();
		if (context.getUsername() != null && currentUser.getUsername() != null
				&& currentUser.getUsername().equals(context.getUsername())) {
			String workDirectory = configurationService.getGeboWorkDirectory();
			Path parentPath = Path.of(workDirectory, SESSIONS_PATH_PREFIX);
			if (!Files.exists(parentPath)) {
				Files.createDirectories(parentPath);
			}
			Path path = Path.of(workDirectory, SESSIONS_PATH_PREFIX, context.getCode());
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}
			return path;
		} else
			throw new RuntimeException("Cannot access this chat context");
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
		Optional<GUserChatContext> contextData = userChatContextRepository.findById(userSessionCode);
		UserInfos actualUser = this.securityService.getCurrentUser();
		if (contextData.isEmpty())
			throw new RuntimeException("Cannot access this chat context because it does not exist");
		Path path = getSessionPath(contextData.get());
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
		IngestionHandlerData data = ingestionHandler.handleContent(reference, Files.newInputStream(newPath));
		if (data.isUnmanagedContent())
			return null;
		else {
			HashMap<String, Object> commonMetaData = this.createCommonMetaData(serverSide);

			serverSide.setIngestedJsonPath(serverSide.getCode() + "-ingest.json");
			Path jsonPath = Path.of(path.toString(), serverSide.getIngestedJsonPath());
			List<SerializedDocumentContent> documents = data.getStream().map(x -> {
				SerializedDocumentContent doc = new SerializedDocumentContent(x.getId(), x.getText(), x.getMetadata());
				if (doc.getMetaData() == null) {
					doc.setMetaData(new HashMap<>(commonMetaData));
				} else {
					HashMap newMeta = new HashMap<>(commonMetaData);
					newMeta.putAll(newMeta);
					doc.setMetaData(newMeta);
				}
				if (doc.getContent() != null && !doc.getContent().trim().isEmpty()) {
					int tokens = this.tokenCountEstimator.estimate(doc.getContent());
					doc.setMetaData(new HashMap<String, Object>(doc.getMetaData()));
					doc.getMetaData().put(DocumentMetaInfos.GEBO_TOKEN_LENGTH, tokens);
				}
				return doc;
			}).toList();
			SerializedDocumentsContent content = new SerializedDocumentsContent(documents);
			objectMapper.writeValue(jsonPath.toFile(), content);
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
		Optional<GUserChatContext> contextData = userChatContextRepository.findById(ss.getUserContextCode());
		if (contextData.isEmpty())
			throw new RuntimeException("Cannot access this chat context because it does not exist");
		uploadContentsRepository.delete(ss);
		Path path = getSessionPath(contextData.get());
		Path filePath = Path.of(path.toString(), ss.getRelativeFilePath());
		Path jsonPath = ss.getIngestedJsonPath() != null ? Path.of(path.toString(), ss.getIngestedJsonPath()) : null;
		if (jsonPath != null) {
			Files.deleteIfExists(jsonPath);
		}
		Files.deleteIfExists(filePath);

	}

	@Override
	public void deleteSessionContents(GUserChatContext context) throws IOException {
		this.securityService.checkBeingCreator(context);
		Path path = getSessionPath(context);
		DirectoryStream<Path> data = Files.newDirectoryStream(path);
		data.forEach(x -> {
			try {
				Files.deleteIfExists(x);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		Files.deleteIfExists(path);
		this.llmGeneratedResourceRepository.deleteByUserContextCode(context.getCode());
		this.uploadContentsRepository.deleteByUserContextCode(context.getCode());
	}

	@Override
	public Path getUploadedFilePath(UserUploadContentServerSide ss) throws IOException {
		this.securityService.checkBeingCreator(ss);
		Optional<GUserChatContext> contextData = userChatContextRepository.findById(ss.getUserContextCode());
		if (contextData.isEmpty())
			throw new RuntimeException("Cannot access this chat context because it does not exist");
		Path path = getSessionPath(contextData.get());
		Path filePath = Path.of(path.toString(), ss.getRelativeFilePath());
		return filePath;
	}

	@Override
	public List<Document> getIngestedContentsOf(UserUploadContentServerSide ss) throws IOException {
		this.securityService.checkBeingCreator(ss);
		Optional<GUserChatContext> contextData = userChatContextRepository.findById(ss.getUserContextCode());
		if (contextData.isEmpty())
			throw new RuntimeException("Cannot access this chat context because it does not exist");
		this.securityService.checkBeingCreator(contextData.get());
		Path path = getSessionPath(contextData.get());
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
		Optional<GUserChatContext> contextData = userChatContextRepository.findById(userSessionCode);
		if (contextData.isEmpty())
			throw new RuntimeException("Cannot access this chat context because it does not exist");
		this.securityService.checkBeingCreator(contextData.get());
		List<UserUploadedContent> list = new ArrayList<>();
		List<GUserMessage> messages = new ArrayList<>();
		List<UserUploadContentServerSide> data = uploadContentsRepository.findAllById(id);
		for (UserUploadContentServerSide userUploadContentServerSide : data) {
			this.securityService.checkBeingCreator(userUploadContentServerSide);
		}
		for (UserUploadContentServerSide userUploadContentServerSide : data) {
			try {
				deleteUploadedFile(userUploadContentServerSide);
				list.add(new UserUploadedContent(userUploadContentServerSide));
			} catch (Throwable t) {

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
		Optional<GUserChatContext> contextData = userChatContextRepository.findById(content.getUserContextCode());
		Optional<UserUploadContentServerSide> serverSideContent = this.uploadContentsRepository
				.findById(content.getCode());
		if (contextData.isPresent() && serverSideContent.isPresent()) {
			GUserChatContext ctx = contextData.get();
			UserUploadContentServerSide ssc = serverSideContent.get();
			this.securityService.checkBeingCreator(ctx);
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
	public LLMGeneratedResource addMedia(Media media, GUserChatContext userContext) throws IOException {
		securityService.checkBeingCreator(userContext);
		LLMGeneratedResource resource = new LLMGeneratedResource();
		resource.setCode(UUID.randomUUID().toString());
		resource.setDescription(media.getName());
		resource.setFileName(media.getName());
		resource.setUserContextCode(userContext.getCode());
		resource.setContentType(media.getMimeType() != null ? media.getMimeType().getType() : null);
		Path path = getSessionPath(userContext);
		Path out = Path.of(path.toString(), resource.getCode());
		try (OutputStream os = Files.newOutputStream(out)) {
			IOUtils.write(media.getDataAsByteArray(), os);
			os.flush();
		}
		resource = llmGeneratedResourceRepository.insert(resource);
		return resource;
	}

	@Override
	public LLMGeneratedResource getGeneratedContent(String userSessionCode, String generatedResourceCode)
			throws IOException {
		Optional<GUserChatContext> contextData = userChatContextRepository.findById(userSessionCode);
		if (contextData.isPresent()) {
			securityService.checkBeingCreator(contextData.get());
			Optional<LLMGeneratedResource> data = this.llmGeneratedResourceRepository.findById(generatedResourceCode);
			if (data.isPresent())
				return data.get();
		}
		return null;
	}

	@Override
	public InputStream streamContent(LLMGeneratedResource generated) throws IOException {
		Optional<GUserChatContext> contextData = userChatContextRepository.findById(generated.getUserContextCode());
		if (contextData.isPresent()) {
			securityService.checkBeingCreator(contextData.get());
			Path path = getSessionPath(contextData.get());
			Path outPath = Path.of(path.toString(), generated.getCode());

			return Files.newInputStream(outPath);
		}
		return null;
	}

}
