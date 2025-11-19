package ai.gebo.llms.chat.client.rest.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ai.gebo.llms.chat.abstraction.layer.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.services.IGUserUploadContentHandler;
import ai.gebo.model.OperationStatus;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/users/GeboUserChatUploadsController")
@AllArgsConstructor
public class GeboUserChatUploadsController {
	final IGUserUploadContentHandler uploadsHandler;

	@PostMapping(value = "chatSessionUpload/{userSessionCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public OperationStatus<List<UserUploadedContent>> chatSessionUpload(
			@PathVariable("userSessionCode") String userSessionCode, @RequestParam("files[]") List<MultipartFile> files)
			throws IOException {
		return uploadsHandler.chatSessionUpload(userSessionCode, files);
	}

	@DeleteMapping(value = "deleteSessionUploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
	public OperationStatus<List<UserUploadedContent>> deleteSessionUploads(
			@PathVariable("userSessionCode") String userSessionCode,
			@NotNull @Valid @RequestBody List<UserUploadedContent> contents) {
		return uploadsHandler.deleteSessionUploads(userSessionCode, null);
	}

	@GetMapping(value = "serveContent/{userSessionCode}/{uploadedContentId}")
	public void serveContent(@PathVariable("userSessionCode") String userSessionCode,
			@PathVariable("uploadedContentId") String uploadedContentId, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		UserUploadedContent content = this.uploadsHandler.findByUserSessionCodeAndUploadedContentId(userSessionCode,
				uploadedContentId);
		response.setContentLengthLong(content.getFileSize());
		String contentType = response.getContentType();
		if (contentType != null) {
			response.setContentType(contentType);
		}
		ServletOutputStream os = response.getOutputStream();
		try (InputStream inputStream = this.uploadsHandler.streamContent(content)) {
			IOUtils.copy(inputStream, os);
		}
	}
}
