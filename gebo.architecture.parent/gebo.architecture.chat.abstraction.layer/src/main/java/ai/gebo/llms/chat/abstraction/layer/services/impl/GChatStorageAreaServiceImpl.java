package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.SerializedDocumentContent;
import ai.gebo.llms.chat.abstraction.layer.model.SerializedDocumentsContent;
import ai.gebo.llms.chat.abstraction.layer.model.UserUploadContentServerSide;
import ai.gebo.llms.chat.abstraction.layer.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatContextRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.UserUploadContentServerSideRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
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
	final static String SESSIONS_PATH_PREFIX = "SESSIONS_AREA";
	final static ObjectMapper objectMapper = new ObjectMapper();

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
			serverSide.setIngestedJsonPath(serverSide.getCode() + "-ingest.json");
			Path jsonPath = Path.of(path.toString(), serverSide.getIngestedJsonPath());
			List<SerializedDocumentContent> documents = data.getStream()
					.map(x -> new SerializedDocumentContent(x.getId(), x.getText(), x.getMetadata())).toList();
			SerializedDocumentsContent content = new SerializedDocumentsContent(documents);
			objectMapper.writeValue(jsonPath.toFile(), content);
		}
		serverSide = uploadContentsRepository.insert(serverSide);
		return serverSide;
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
		for(UserUploadContentServerSide userUploadContentServerSide : data) {
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
		Optional<UserUploadContentServerSide> serverSideContent = this.uploadContentsRepository.findById(content.getCode());
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

}
