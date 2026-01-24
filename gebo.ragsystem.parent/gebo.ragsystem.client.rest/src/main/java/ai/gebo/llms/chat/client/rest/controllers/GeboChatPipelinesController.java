package ai.gebo.llms.chat.client.rest.controllers;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatContextRepository;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IChatPipelinesExecutor;
import ai.gebo.security.services.IGSecurityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(path = "api/users/GeboChatPipelinesController")
@AllArgsConstructor
public class GeboChatPipelinesController {
	protected final IChatPipelinesExecutor executor;
	protected final IGChatModelRuntimeConfigurationDao chatModelsDao;
	protected final IGPersistentObjectManager persistentObjectManager;
	protected final IGSecurityService securityService;
	@PostMapping(value = "streamDefaultChatPipeline", produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Flux<GeboChatMessageEnvelope> streamDefaultChatPipeline(@RequestBody @NotNull @Valid GeboChatRequest request)
			throws ChatPipelineException, GeboPersistenceException, IOException {

		return this.streamChatPipeline(null, request);
	}
	@PostMapping(value = "streamChatPipeline", produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Flux<GeboChatMessageEnvelope> streamChatPipeline(
			@RequestParam(name = "pipelineCode", required = false) String pipelineCode,
			@RequestBody @NotNull @Valid GeboChatRequest request)
			throws ChatPipelineException, GeboPersistenceException, IOException {
		IGConfigurableChatModel chatModel = chatModelsDao.defaultHandler();
		IGConfigurableChatModel serviceModel = chatModelsDao.findByUses(ChatModelsUses.INTERNAL_SERVICES);
		GUserChatContext context = null;
		if (request.getUserChatContextCode() != null) {
			context = persistentObjectManager.findById(GUserChatContext.class, request.getUserChatContextCode());
			if (context == null) {
				throw new ChatPipelineException("Referred context is not found");
			}
			securityService.checkBeingCreator(context);
		} else {
			context = new GUserChatContext();
			context.setUsername(securityService.getCurrentUser().getUsername());
			context.setChatProfileCode(request.getChatProfileCode());
			context.setChatModelCode(request.getChatModelCode());
			context.setRagChat(
					request.getChatProfileCode() != null && request.getChatProfileCode().trim().length() > 0);
			context.setCode(UUID.randomUUID().toString());
			context.setDescription("New chat");
			context = persistentObjectManager.insert(context);

		}
		return executor.streamingExecute(request, context, chatModel, serviceModel, pipelineCode);
	}
	@PostMapping(value = "executeDefaultChatPipeline", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GeboChatResponse executeDefaultChatPipeline(@RequestBody @NotNull @Valid GeboChatRequest request)
			throws ChatPipelineException, GeboPersistenceException, IOException {
		return this.executeChatPipeline(null, request);
	}
	@PostMapping(value = "executeChatPipeline", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GeboChatResponse executeChatPipeline(
			@RequestParam(name = "pipelineCode", required = false) String pipelineCode,
			@RequestBody @NotNull @Valid GeboChatRequest request)
			throws ChatPipelineException, GeboPersistenceException, IOException {
		IGConfigurableChatModel chatModel = chatModelsDao.defaultHandler();
		IGConfigurableChatModel serviceModel = chatModelsDao.findByUses(ChatModelsUses.INTERNAL_SERVICES);
		GUserChatContext context = null;
		if (request.getUserChatContextCode() != null) {
			context = persistentObjectManager.findById(GUserChatContext.class, request.getUserChatContextCode());
			if (context == null) {
				throw new ChatPipelineException("Referred context is not found");
			}
			securityService.checkBeingCreator(context);
		} else {
			context = new GUserChatContext();
			context.setUsername(securityService.getCurrentUser().getUsername());
			context.setChatProfileCode(request.getChatProfileCode());
			context.setChatModelCode(request.getChatModelCode());
			context.setRagChat(
					request.getChatProfileCode() != null && request.getChatProfileCode().trim().length() > 0);
			context.setCode(UUID.randomUUID().toString());
			context.setDescription("New chat");
			context = persistentObjectManager.insert(context);

		}
		return executor.execute(request, context, chatModel, serviceModel, pipelineCode);
	}
}
