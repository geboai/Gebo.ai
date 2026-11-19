package ai.gebo.llms.chat.client.rest.controllers;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import ai.gebo.knlowledgebase.model.contents.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatService;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInfo;
import ai.gebo.llms.chat.abstraction.layer.services.IGRagChatService;
import ai.gebo.llms.chat.abstraction.layer.services.IGUserUploadContentHandler;
import ai.gebo.llms.chat.abstraction.layer.model.GChatUploadFileTypes;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatUploadFileTypesService;
import ai.gebo.model.OperationStatus;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@PreAuthorize("hasAnyRole('USER','ADMIN','APPLICATION')")
@RestController
@RequestMapping(path = "api/users/GeboUserChatUploadsController")
@AllArgsConstructor
public class GeboUserChatUploadsController {
	final IGUserUploadContentHandler uploadsHandler;
	final IGChatService chatService;
	final IGRagChatService ragChatService;
	final IGChatUploadFileTypesService uploadFileTypes;
	final IGChatSessionLifeCycleService sessionLifeCycle;

	/**
	 * The files a chat accepts as uploads: the ones read into the chat, then the ones
	 * kept only for the tools of its agents' libraries. By the chat session when there
	 * is one, else by the profile a chat is being created with (none for a free chat).
	 */
	@GetMapping(value = "acceptedFileTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public GChatUploadFileTypes acceptedFileTypes(
			@RequestParam(value = "userSessionCode", required = false) String userSessionCode,
			@RequestParam(value = "chatProfileCode", required = false) String chatProfileCode) {
		return uploadFileTypes.acceptedFileTypes(userSessionCode, chatProfileCode);
	}

	@PostMapping(value = "chatSessionUpload/{userSessionCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<UserUploadedContent>> chatSessionUpload(
			@PathVariable("userSessionCode") String userSessionCode, @RequestParam("files[]") List<MultipartFile> files)
			throws IOException {
		return uploadsHandler.chatSessionUpload(userSessionCode, files);
	}

	/** The files uploaded to a chat created for them, and the chat. */
	@lombok.Data
	@lombok.NoArgsConstructor
	@lombok.AllArgsConstructor
	public static class UploadsWithNewChat {
		private List<UserUploadedContent> uploads = null;
		private GUserChatInfo chatInfo = null;
	}

	/**
	 * Uploads files to a chat with a model that does not exist yet - the first thing a
	 * user does in a new chat may be an upload: the chat is created, then the files are
	 * uploaded to it. A chat that got no file is kept: the user is in it already.
	 */
	@PostMapping(value = "chatSessionCreateWithUpload/{chatModelCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<UploadsWithNewChat> chatSessionCreateWithUpload(
			@PathVariable("chatModelCode") String chatModelCode, @RequestParam("files[]") List<MultipartFile> files)
			throws IOException, GeboPersistenceException {
		return uploadedToNewChat(sessionLifeCycle.createCleanChatByModelCode(chatModelCode), files);
	}

	/** As {@link #chatSessionCreateWithUpload(String, List)}, for a chat with a chat profile. */
	@PostMapping(value = "ragChatSessionCreateWithUpload/{chatProfileCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<UploadsWithNewChat> ragChatSessionCreateWithUpload(
			@PathVariable("chatProfileCode") String chatProfileCode,
			@RequestParam("files[]") List<MultipartFile> files) throws IOException, GeboPersistenceException {
		return uploadedToNewChat(sessionLifeCycle.createCleanChatByChatProfileCode(chatProfileCode), files);
	}

	private OperationStatus<UploadsWithNewChat> uploadedToNewChat(GUserChatInfo chat, List<MultipartFile> files)
			throws IOException {
		final OperationStatus<List<UserUploadedContent>> uploaded = uploadsHandler.chatSessionUpload(chat.getCode(),
				files);
		return OperationStatus.of(new UploadsWithNewChat(uploaded.getResult(), chat), uploaded.getMessages());
	}

	@DeleteMapping(value = "deleteSessionUploads/{userSessionCode}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<UserUploadedContent>> deleteSessionUploads(
			@PathVariable("userSessionCode") String userSessionCode,
			@NotNull @RequestBody List<UserUploadedContent> contents) {
		return uploadsHandler.deleteSessionUploads(userSessionCode,
				contents.stream().map(UserUploadedContent::getCode).toList());
	}

	@GetMapping(value = "serveContent/{userSessionCode}/{uploadedContentId}")
	public void serveContent(@PathVariable("userSessionCode") String userSessionCode,
			@PathVariable("uploadedContentId") String uploadedContentId, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		UserUploadedContent content = this.uploadsHandler.findByUserSessionCodeAndUploadedContentId(userSessionCode,
				uploadedContentId);
		response.setContentLengthLong(content.getFileSize());
		String contentType = content.getContentType();
		if (contentType != null) {
			response.setContentType(contentType);
		}

		try (InputStream inputStream = this.uploadsHandler.streamContent(content);
				ServletOutputStream os = response.getOutputStream();) {
			IOUtils.copy(inputStream, os);
		}
	}
}
