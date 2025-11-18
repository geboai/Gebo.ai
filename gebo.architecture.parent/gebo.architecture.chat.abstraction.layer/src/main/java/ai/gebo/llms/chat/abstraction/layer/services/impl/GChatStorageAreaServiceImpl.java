package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.UserUploadContentServerSide;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatContextRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.UserUploadContentServerSideRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
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

	@Override
	public Path getSessionPath(GUserChatContext context) throws IOException {
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

	@Override
	public UserUploadContentServerSide addUploadedFile(String userSessionCode, MultipartFile file) throws IOException {
		Optional<GUserChatContext> contextData = userChatContextRepository.findById(userSessionCode);
		if (contextData.isEmpty())
			throw new RuntimeException("Cannot access this chat context because it does not exist");
		Path path = getSessionPath(contextData.get());
		UserUploadContentServerSide serverSide=new UserUploadContentServerSide();
		serverSide.setContentType(file.getContentType());
		serverSide.setFileName(file.getOriginalFilename());
		serverSide.setFileSize(file.getSize());
		
		return null;
	}

	@Override
	public void deleteUploadedFile(UserUploadContentServerSide ss) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSessionContents(GUserChatContext context) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public Path getUploadedFilePath(UserUploadContentServerSide ss) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Document> getIngestedContentsOf(UserUploadContentServerSide ss) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
