package ai.gebo.architecture.agents.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.AssistantMessage.ToolCall;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.acl.AclGrantType;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.repository.AgentConfigRepository;
import ai.gebo.architecture.agents.services.impl.AgentToolCallingManagerFactory;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel.ChatModelConfigOptions;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class GAbstractGenericalAgentService extends BaseLLMSInvokingService implements IGGenericAgentService {
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	protected final IGChatModelRuntimeConfigurationDao chatModelsDao;
	protected final IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern;
	protected final IGPromptConfigDao promptsDao;
	protected final IAgentConfigDao configsDao;
	protected final IGSecurityService securityService;
	protected final IAgentRoleDao agentRoleDao;

	@Override
	public List<GAgentConfig> getAccessibleConfigurations() {
		List<GAgentConfig> configs = this.configsDao.findByAgentServiceId(getId());
		return securityService.filterCanDoAction(configs, true, AclGrantType.EXECUTE);
	}

	protected IGConfigurableChatModel getAgentModel(GAgentConfig agentConfig, ToolCallsListener callBacksListener,
			ReactiveIdentityUtil runAs) throws LLMConfigException {
		IGConfigurableChatModel copiedModel = null;
		if (agentConfig.getUseDefaultChatModel() != null && agentConfig.getUseDefaultChatModel()) {
			copiedModel = chatModelsDao.defaultHandler();
		} else {
			copiedModel = chatModelsDao.findByModelReference(agentConfig.getChatModelReference());
		}
		if (copiedModel == null) {
			LOGGER.warn("Setting backup default chat model for actual Agent");
			copiedModel = chatModelsDao.defaultHandler();
			if (copiedModel == null)
				throw new LLMConfigException("Default chat model not set in the system");
		}

		List<String> allFunctions = agentConfig.getEnabledFunctions();
		if (agentConfig.getSubscribeAllTools() != null && agentConfig.getSubscribeAllTools()) {
			List<ToolCallback> toolsList = toolsRepositoryPattern.getTools();
			if (toolsList != null) {
				allFunctions = toolsList.stream().map(x -> x.getToolDefinition().name()).toList();
			}
		}
		ChatModelConfigOptions configOptions = new ChatModelConfigOptions(agentConfig.getTemperature(),
				agentConfig.getTopP(), agentConfig.getThinking(), allFunctions,
				createToolCallingManager(callBacksListener, allFunctions, runAs));
		IGConfigurableChatModel agentModel = copiedModel.cloneWithOptions(getId(), configOptions);
		return agentModel;
	} 

	protected ToolCallingManager createToolCallingManager(ToolCallsListener callBacksListener,
			List<String> allFunctions, ReactiveIdentityUtil runAs) {
		final List<ToolCallback> wrapped = GAbstractConfigurableChatModel.wrapTools(runAs, callBacksListener,
				allFunctions, toolsRepositoryPattern);
		final Map<String, ToolCallback> map = new HashMap<>();
		for (ToolCallback toolCallback : wrapped) {
			map.put(toolCallback.getToolDefinition().name(), toolCallback);
		}
		return new AgentToolCallingManagerFactory(callBacksListener, allFunctions, wrapped, map).create();
	}

	protected static String extractContent(ChatResponse chatResponse) {
		if (chatResponse == null) {
			return "";
		}

		Generation result = chatResponse.getResult();
		if (result == null || result.getOutput() == null) {
			return "";
		}

		AssistantMessage output = result.getOutput();

		String text = output.getText();
		return text != null ? text : "";
	}

	protected static void inspectMetadata(ChatResponse chatResponse, Logger logger) {
		if (chatResponse == null) {
			return;
		}

		ChatResponseMetadata metadata = chatResponse.getMetadata();

		if (metadata != null) {
			Usage usage = metadata.getUsage();

			if (usage != null) {
				logger.debug("LLM token usage: promptTokens={}, completionTokens={}, totalTokens={}",
						usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());
			}

			Object model = metadata.get("model");
			if (model != null) {
				logger.debug("LLM model: {}", model);
			}

			Object id = metadata.get("id");
			if (id != null) {
				logger.debug("LLM response id: {}", id);
			}
		}

		Generation result = chatResponse.getResult();
		if (result != null) {
			ChatGenerationMetadata generationMetadata = result.getMetadata();

			if (generationMetadata != null) {
				String finishReason = generationMetadata.getFinishReason();

				if (finishReason == null) {
					Object rawFinishReason = generationMetadata.get("FINISH_REASON");
					finishReason = Objects.toString(rawFinishReason, null);
				}

				if (finishReason != null) {
					logger.debug("LLM finish reason: {}", finishReason);
				}
			}
		}
	}

	protected static void inspectToolCalls(ChatResponse chatResponse, Vector<Object> rawToolCallsCumulator) {
		if (chatResponse == null) {
			return;
		}

		Generation result = chatResponse.getResult();
		if (result == null || result.getOutput() == null) {
			return;
		}

		AssistantMessage output = result.getOutput();

		/*
		 * Nota: con tool execution gestita internamente da Spring AI, spesso le
		 * tool-call intermedie non sono esposte nello stream applicativo. Spring AI
		 * documenta che, nel framework-controlled tool execution, i messaggi interni di
		 * tool execution non sono esposti all’utente.
		 */
		List<AssistantMessage.ToolCall> toolCalls = output.getToolCalls();

		if (!org.springframework.util.CollectionUtils.isEmpty(toolCalls)) {
			for (AssistantMessage.ToolCall toolCall : toolCalls) {

				rawToolCallsCumulator.add(toolCall);
			}
		}

		Map<String, Object> metadata = output.getMetadata();
		if (metadata != null && !metadata.isEmpty()) {
			Object rawToolCalls = metadata.get("tool_calls");
			if (rawToolCalls == null) {
				rawToolCalls = metadata.get("toolCalls");
			}
			if (rawToolCalls != null)
				rawToolCallsCumulator.add(rawToolCalls);

		}
	}

	protected GPromptTemplateConfig resolvePrompt(GPromptTemplateConfig prompt, String useCode, boolean nullable)
			throws AgentException {
		GPromptTemplateConfig resolved = prompt != null ? prompt
				: useCode != null ? promptsDao.findByPromptUse(useCode) : null;
		if (resolved == null && !nullable)
			throw new AgentException("Mandatory prompt not present");
		return resolved;
	}

}
