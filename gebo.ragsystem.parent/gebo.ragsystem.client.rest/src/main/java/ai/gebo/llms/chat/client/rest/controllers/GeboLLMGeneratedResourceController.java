package ai.gebo.llms.chat.client.rest.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.llms.chat.abstraction.layer.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRagChatService;
import ai.gebo.llms.chat.abstraction.layer.services.IGUserUploadContentHandler;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/users/GeboLLMGeneratedResourceController")
@AllArgsConstructor
public class GeboLLMGeneratedResourceController {
	final IGChatStorageAreaService chatAreaStorage;

	@GetMapping(value = "serveLLMGeneratedContent/{userSessionCode}/{generatedResourceCode}")
	public void serveLLMGeneratedContent(@PathVariable("userSessionCode") String userSessionCode,
			@PathVariable("generatedResourceCode") String generatedResourceCode, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LLMGeneratedResource generated = this.chatAreaStorage.getGeneratedContent(userSessionCode,
				generatedResourceCode);
		String contentType = generated.getContentType();
		if (contentType != null) {
			response.setContentType(contentType);
		}
		
		try (InputStream inputStream = this.chatAreaStorage.streamContent(generated);ServletOutputStream os = response.getOutputStream()) {
			IOUtils.copy(inputStream, os);
		}
	}

}
