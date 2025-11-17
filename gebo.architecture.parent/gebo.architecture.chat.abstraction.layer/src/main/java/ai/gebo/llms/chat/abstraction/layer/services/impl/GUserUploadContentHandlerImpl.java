package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ai.gebo.llms.chat.abstraction.layer.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.services.IGUserUploadContentHandler;
import ai.gebo.model.OperationStatus;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class GUserUploadContentHandlerImpl implements IGUserUploadContentHandler {

	

	@Override
	public OperationStatus<UserUploadedContent> chatSessionUpload(String userSessionCode, List<MultipartFile> files) {
		
		return null;
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
