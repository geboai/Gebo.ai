package ai.gebo.llms.chat.pipelines.service.impl;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRuntimeChatProfileChatModelDao;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.llms.chat.pipelines.model.ui.PipelineChatMenu;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineService;
import ai.gebo.llms.chat.pipelines.service.IChatPipelinesExecutor;
import ai.gebo.llms.chat.pipelines.service.IPipelineUserMenuProviderService;
import ai.gebo.llms.chat.pipelines.service.IPipelineUserMenuProviderServiceRepositoryPattern;
import ai.gebo.security.services.IGSecurityService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class ChatPipelineServiceImpl implements IChatPipelineService {
	private static Logger LOGGER = LoggerFactory.getLogger(ChatPipelineServiceImpl.class);
	protected final IChatPipelinesExecutor executor;
	protected final IGChatModelRuntimeConfigurationDao chatModelsDao;
	protected final IGPersistentObjectManager persistentObjectManager;
	protected final IGSecurityService securityService;
	protected final IGRuntimeChatProfileChatModelDao chatProfileDao;
	protected final IGChatSessionLifeCycleService chatSessionLifecycleService;
	protected final IPipelineUserMenuProviderServiceRepositoryPattern pipelineUserMenuProviderServiceRepoPattern;

	@Override
	public GeboChatResponse chat(String pipelineCode, @NotNull GeboChatRequest request,
			LinkedHashMap<String, Object> environment) throws ChatPipelineException, GeboChatException {
		GeboChatResponse response = null;
		try {
			this.chatSessionLifecycleService.ensureChatSessionExists(request);
			IGConfigurableChatModel chatModel = this.chatSessionLifecycleService.getSessionChatModel(request);
			IGConfigurableChatModel serviceModel = chatModelsDao
					.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
			Flux<GeboChatMessageEnvelope> flux = executor.streamingExecute(request, environment, chatModel,
					serviceModel, pipelineCode);
			Vector<GeboChatMessageEnvelope> responsesList = new Vector<GeboChatMessageEnvelope>();
			Flux<GeboChatMessageEnvelope> lastMessageFlux = flux.filter(x -> {
				responsesList.add(x);
				return x.isLastMessage() && x.getContent() instanceof GeboChatResponse;
			});
			GeboChatMessageEnvelope last = lastMessageFlux.blockLast();
			if (last != null && last.getContent() instanceof GeboChatResponse _response) {
				response = _response;
			} else {
				LOGGER.warn("No GeboChatResponse as output");
			}
			return response;
		} catch (GeboPersistenceException | IOException | LLMConfigException e) {
			String msg = "Exception applying chat pipeline";
			throw new ChatPipelineException(msg, e);
		}
	}

	@Override
	public Flux<GeboChatMessageEnvelope> streamingChat(String pipelineCode, @NotNull GeboChatRequest request,
			LinkedHashMap<String, Object> environment) throws ChatPipelineException, GeboChatException {
		try {
			this.chatSessionLifecycleService.ensureChatSessionExists(request);
			IGConfigurableChatModel chatModel = this.chatSessionLifecycleService.getSessionChatModel(request);
			IGConfigurableChatModel serviceModel = chatModelsDao
					.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
			return executor.streamingExecute(request, environment, chatModel, serviceModel, pipelineCode);
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

	@Override
	public List<PipelineChatMenu> getPersonalPipelinesChatMenu(String pipelineCode, String chatProfileCode)
			throws ChatPipelineException {
		IPipelineUserMenuProviderService handler = pipelineUserMenuProviderServiceRepoPattern.findByCode(pipelineCode);
		if (handler == null)
			throw new ChatPipelineException(
					"Pipeline => " + pipelineCode + " does not have an PipelineUserMenuProviderService");
		return handler.getUIMenu(chatProfileCode);
	}
}
