package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.llms.chat.abstraction.layer.model.UserUploadContentServerSide;
import ai.gebo.llms.chat.abstraction.layer.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.services.IGUserUploadContentHandler;
import ai.gebo.model.OperationStatus;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GUserUploadContentHandlerImpl implements IGUserUploadContentHandler {
	final IGGeboConfigService configService;

	@Override
	public OperationStatus<List<UserUploadedContent>> chatSessionUpload(String userSessionCode,
			List<MultipartFile> files) {
		List<UserUploadedContent> data = new ArrayList<>();
		for (MultipartFile file : files) {
			UserUploadContentServerSide ssFile = new UserUploadContentServerSide();
			ssFile.setCode(UUID.randomUUID().toString());
			ssFile.setFileName(file.getOriginalFilename());
			ssFile.setContentType(file.getContentType());
			ssFile.setDescription(file.getOriginalFilename());
			ssFile.setFileSize(file.getSize());
			data.add(ssFile);

		}
		return OperationStatus.of(data);
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
