package ai.gebo.llms.chat.abstraction.layer.services;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.web.multipart.MultipartFile;

import ai.gebo.llms.chat.abstraction.layer.model.UserUploadedContent;
import ai.gebo.model.OperationStatus;

public interface IGUserUploadContentHandler {

	public OperationStatus<List<UserUploadedContent>> chatSessionUpload(String userSessionCode, List<MultipartFile> files);

	public default Document chatSessionUploadContent(UserUploadedContent content) {
		return this.chatSessionUploadContentById(content.getCode());
	}

	public Document chatSessionUploadContentById(String code);

	public OperationStatus<List<UserUploadedContent>> deleteSessionUploads(String userSessionCode, List<String> id);
	
	

}
