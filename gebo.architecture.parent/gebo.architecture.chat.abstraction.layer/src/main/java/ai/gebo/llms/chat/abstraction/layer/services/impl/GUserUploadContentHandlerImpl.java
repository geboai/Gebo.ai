package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.List;
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
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GUserUploadContentHandlerImpl implements IGUserUploadContentHandler {
	final IGChatStorageAreaService storageAreaService;
	final static Logger LOGGER = LoggerFactory.getLogger(GUserUploadContentHandlerImpl.class);
	
	@Override
	public OperationStatus<List<UserUploadedContent>> chatSessionUpload(String userSessionCode,
			List<MultipartFile> files) {
		List<GUserMessage> messages = new ArrayList<>();
		List<UserUploadedContent> data = new ArrayList<>();
		for (MultipartFile file : files) {
			try {
				UserUploadContentServerSide ssFile = storageAreaService.addUploadedFile(userSessionCode, file);
				data.add(new UserUploadedContent(ssFile));
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
	public Document chatSessionUploadContentById(String code) {

		return null;
	}

	@Override
	public OperationStatus<List<UserUploadedContent>> deleteSessionUploads(String userSessionCode, List<String> id) {
		// TODO Auto-generated method stub
		return null;
	}

}
