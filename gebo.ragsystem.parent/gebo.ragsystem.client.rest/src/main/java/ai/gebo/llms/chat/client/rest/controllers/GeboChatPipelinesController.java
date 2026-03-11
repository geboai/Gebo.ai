package ai.gebo.llms.chat.client.rest.controllers;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.client.rest.model.PipelineRequestBody;
import ai.gebo.llms.chat.client.rest.model.PipelineRequestBody.PipelineEnvironment;
import ai.gebo.llms.chat.pipelines.model.ui.PipelineChatMenu;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(path = "api/users/GeboChatPipelinesController")
@AllArgsConstructor
public class GeboChatPipelinesController {
	protected final IChatPipelineService chatPipelineService;

	@PostMapping(value = "streamDefaultChatPipeline", produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Flux<GeboChatMessageEnvelope> streamDefaultChatPipeline(
			@RequestBody @NotNull @Valid PipelineRequestBody data) throws ChatPipelineException,
			GeboPersistenceException, IOException, LLMConfigException, GeboChatSessionLifecycleException {

		return chatPipelineService.streamingChat(data.getRequest(), toLinkedHashMap(data.getEnvironment()));
	}

	@PostMapping(value = "streamChatPipeline", produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Flux<GeboChatMessageEnvelope> streamChatPipeline(
			@RequestParam(name = "pipelineCode", required = false) String pipelineCode,
			@RequestBody @NotNull @Valid PipelineRequestBody data)
			throws ChatPipelineException, GeboChatSessionLifecycleException {

		return chatPipelineService.streamingChat(pipelineCode, data.getRequest(),
				toLinkedHashMap(data.getEnvironment()));
	}

	@PostMapping(value = "executeDefaultChatPipeline", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GeboChatResponse executeDefaultChatPipeline(@RequestBody @NotNull @Valid PipelineRequestBody data)
			throws ChatPipelineException, GeboChatSessionLifecycleException {
		return chatPipelineService.chat(data.getRequest(), toLinkedHashMap(data.getEnvironment()));
	}

	private LinkedHashMap<String, Object> toLinkedHashMap(PipelineEnvironment environment) {
		LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();
		if (environment != null && environment.getValues() != null) {
			data.putAll(environment.getValues());
		}
		return data;
	}

	@PostMapping(value = "executeChatPipeline", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GeboChatResponse executeChatPipeline(
			@RequestParam(name = "pipelineCode", required = false) String pipelineCode,
			@RequestBody @NotNull @Valid PipelineRequestBody data) throws ChatPipelineException,
			GeboPersistenceException, IOException, LLMConfigException, GeboChatSessionLifecycleException {
		return chatPipelineService.chat(pipelineCode, data.getRequest(), toLinkedHashMap(data.getEnvironment()));
	}

	@GetMapping(value = "defaultPersonalPipelinesChatMenu", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PipelineChatMenu> getDefaultPersonalPipelinesChatMenu(
			@RequestParam(name = "chatProfileCode", required = true) String chatProfileCode)
			throws ChatPipelineException {
		String pipelineCode = null;
		return chatPipelineService.getPersonalPipelinesChatMenu(pipelineCode, chatProfileCode);
	}

	@GetMapping(value = "personalPipelinesChatMenu", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PipelineChatMenu> getPersonalPipelinesChatMenu(
			@RequestParam(name = "pipelineCode", required = false) String pipelineCode,
			@RequestParam(name = "chatProfileCode", required = true) String chatProfileCode)
			throws ChatPipelineException {
		return chatPipelineService.getPersonalPipelinesChatMenu(pipelineCode, chatProfileCode);
	}
}
