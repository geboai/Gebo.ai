package ai.gebo.llms.chat.abstraction.layer.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.web.multipart.MultipartFile;

import ai.gebo.llms.chat.abstraction.layer.model.UserUploadedContent;
import ai.gebo.model.OperationStatus;

public interface IGUserUploadContentHandler {

	public OperationStatus<List<UserUploadedContent>> chatSessionUpload(String userSessionCode, List<MultipartFile> files);
	public OperationStatus<List<UserUploadedContent>> deleteSessionUploads(String userSessionCode, List<String> id);
	public InputStream getUploadContent(UserUploadedContent content) throws IOException;
	public UserUploadedContent findByUserSessionCodeAndUploadedContentId(String userSessionCode,
			String uploadedContentId) throws IOException;
	public InputStream streamContent(UserUploadedContent content) throws IOException;
	
	

}
