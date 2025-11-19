package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.llms.chat.abstraction.layer.model.UserUploadContentServerSide;
import ai.gebo.llms.chat.abstraction.layer.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.repository.UserUploadContentServerSideRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.abstraction.layer.services.IGUserUploadContentHandler;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GUserUploadContentHandlerImpl implements IGUserUploadContentHandler {
	final IGChatStorageAreaService storageAreaService;
	final IGSecurityService securityService;
	final UserUploadContentServerSideRepository repository;
	final static Logger LOGGER = LoggerFactory.getLogger(GUserUploadContentHandlerImpl.class);

	@Override
	public OperationStatus<List<UserUploadedContent>> chatSessionUpload(String userSessionCode,
			List<MultipartFile> files) {
		List<GUserMessage> messages = new ArrayList<>();
		List<UserUploadedContent> data = new ArrayList<>();
		for (MultipartFile file : files) {
			try {
				UserUploadContentServerSide ssFile = storageAreaService.addUploadedFile(userSessionCode, file);
				if (ssFile == null) {
					GUserMessage message = GUserMessage.errorMessage("Unsupported file type",
							"The file " + file.getOriginalFilename() + " cannot be read");
					messages.add(message);
				} else {
					data.add(new UserUploadedContent(ssFile));
				}
			} catch (Throwable th) {
				LOGGER.error("Handling file problem", th);
				messages.add(GUserMessage.errorMessage("File cannot be handled or corrupt",
						"The file " + file.getOriginalFilename() + " cannot be opened/read or is corrupt"));
			}

		}
		OperationStatus<List<UserUploadedContent>> status = OperationStatus.of(data.isEmpty() ? null : data);
		if (!messages.isEmpty())
			status.setMessages(messages);
		return status;
	}

	@Override
	public OperationStatus<List<UserUploadedContent>> deleteSessionUploads(String userSessionCode, List<String> id) {

		return storageAreaService.deleteUploadedContents(userSessionCode, id);
	}

	@Override
	public InputStream getUploadContent(UserUploadedContent content) throws IOException {

		return storageAreaService.getContent(content);
	}

	@Override
	public UserUploadedContent findByUserSessionCodeAndUploadedContentId(String userSessionCode,
			String uploadedContentId) throws IOException {
		UserInfos user = this.securityService.getCurrentUser();
		Optional<UserUploadContentServerSide> data = this.repository.findById(uploadedContentId);
		if (data.isPresent()) {
			UserUploadContentServerSide returnedServerSide = data.get();
			if (returnedServerSide.getUserContextCode() != null && userSessionCode != null
					&& returnedServerSide.getUserContextCode().equals(userSessionCode)
					&& (returnedServerSide.getUserCreated().equals(user.getUsername()))) {
				return new UserUploadedContent(returnedServerSide);
			} else
				throw new SecurityException("User not allowed");
		}
		return null;
	}

	@Override
	public InputStream streamContent(UserUploadedContent content) throws IOException {
		if (content == null)
			return InputStream.nullInputStream();
		return storageAreaService.getContent(content);
	}

}
