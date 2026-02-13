package ai.gebo.llms.chat.pipelines.service.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatProfileChatModel;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRuntimeChatProfileChatModelDao;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineService;
import ai.gebo.llms.chat.pipelines.service.IChatPipelinesExecutor;
import ai.gebo.security.services.IGSecurityService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class ChatPipelineServiceImpl implements IChatPipelineService {
	protected final IChatPipelinesExecutor executor;
	protected final IGChatModelRuntimeConfigurationDao chatModelsDao;
	protected final IGPersistentObjectManager persistentObjectManager;
	protected final IGSecurityService securityService;
	protected final IGRuntimeChatProfileChatModelDao chatProfileDao;
	protected final IGChatSessionLifeCycleService chatSessionLifecycleService;

	@Override
	public GeboChatResponse chat(String pipelineCode, @NotNull GeboChatRequest request) throws ChatPipelineException, GeboChatSessionLifecycleException {
		try {
			GUserChatSession context = null;
			if (request.getUserChatContextCode() != null) {

				context = persistentObjectManager.findById(GUserChatSession.class, request.getUserChatContextCode());

				if (context == null) {
					throw new ChatPipelineException("Referred context is not found");
				}
				securityService.checkBeingCreator(context);
			} else {
				context = new GUserChatSession();
				context.setUsername(securityService.getCurrentUser().getUsername());
				context.setChatProfileCode(request.getChatProfileCode());
				context.setChatModelCode(request.getChatModelCode());
				context.setRagChat(
						request.getChatProfileCode() != null && request.getChatProfileCode().trim().length() > 0);
				context.setCode(UUID.randomUUID().toString());
				context.setDescription("New chat");
				context = persistentObjectManager.insert(context);

			}
			IGConfigurableChatModel chatModel = getContextSpecifiedModelOrDefault(context);
			IGConfigurableChatModel serviceModel = chatModelsDao
					.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
			
			String chatProfileCode = context.getChatProfileCode();
			if (chatProfileCode != null) {
				IGChatProfileChatModel chatProfile = chatProfileDao.findByCode(chatProfileCode);
				if (chatProfile != null && chatProfile.getChatModel() != null) {
					chatModel = chatProfile.getChatModel();
				}
			}
			this.chatSessionLifecycleService.ensureChatSessionExists(context, chatModel);
			return executor.execute(request, context, chatModel, serviceModel, pipelineCode);
		} catch (GeboPersistenceException | IOException | LLMConfigException e) {
			String msg = "Exception applying chat pipeline";
			throw new ChatPipelineException(msg, e);
		}
	}

	@Override
	public Flux<GeboChatMessageEnvelope> streamingChat(String pipelineCode, @NotNull GeboChatRequest request)
			throws ChatPipelineException, GeboChatSessionLifecycleException {
		try {

			GUserChatSession context = null;
			if (request.getUserChatContextCode() != null) {
				context = persistentObjectManager.findById(GUserChatSession.class, request.getUserChatContextCode());
				if (context == null) {
					throw new ChatPipelineException("Referred context is not found");
				}
				securityService.checkBeingCreator(context);
			} else {
				context = new GUserChatSession();
				context.setUsername(securityService.getCurrentUser().getUsername());
				context.setChatProfileCode(request.getChatProfileCode());
				context.setChatModelCode(request.getChatModelCode());
				context.setRagChat(
						request.getChatProfileCode() != null && request.getChatProfileCode().trim().length() > 0);
				context.setCode(UUID.randomUUID().toString());
				context.setDescription("New chat");
				context = persistentObjectManager.insert(context);

			}
			IGConfigurableChatModel chatModel = getContextSpecifiedModelOrDefault(context);
			IGConfigurableChatModel serviceModel = chatModelsDao
					.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
			String chatProfileCode = context.getChatProfileCode();
			if (chatProfileCode != null) {
				IGChatProfileChatModel chatProfile = chatProfileDao.findByCode(chatProfileCode);
				if (chatProfile != null && chatProfile.getChatModel() != null) {
					chatModel = chatProfile.getChatModel();
				}
			}
			this.chatSessionLifecycleService.ensureChatSessionExists(context, chatModel);
			return executor.streamingExecute(request, context, chatModel, serviceModel, pipelineCode);
		} catch (GeboPersistenceException | IOException | LLMConfigException e) {
			String msg = "Exception applying chat pipeline (streaming)";
			throw new ChatPipelineException(msg, e);
		}
	}

	private IGConfigurableChatModel getContextSpecifiedModelOrDefault(GUserChatSession context) {
		String modelCode = context.getChatModelCode();
		IGConfigurableChatModel model = chatModelsDao.findByCode(modelCode);
		if (model == null)
			model = chatModelsDao.defaultHandler();
		return model;
	}
}
