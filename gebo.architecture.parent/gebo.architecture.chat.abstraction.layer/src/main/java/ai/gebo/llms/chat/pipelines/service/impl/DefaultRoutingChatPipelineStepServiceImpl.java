package ai.gebo.llms.chat.pipelines.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ai.gebo.llms.abstraction.layer.services.BaseLlmsInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IChatPipelineStepRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IStepContribution;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IRoutingChatPipelineStepService;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Component
public class DefaultRoutingChatPipelineStepServiceImpl extends BaseLlmsInvokingService
		implements IRoutingChatPipelineStepService {
	private final static Logger LOGGER = LoggerFactory.getLogger(DefaultRoutingChatPipelineStepServiceImpl.class);
	private final ChatPipelinesConfiguration chatPipelinesConfig;

	public DefaultRoutingChatPipelineStepServiceImpl(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao,
			DefaultRagStreamingOutputChatPipelineStepServiceImpl defaultRagStreamingOutputChatPipelineStepServiceImpl,
			ChatPipelinesConfiguration chatPipelinesConfig) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
		this.chatPipelinesConfig = chatPipelinesConfig;
	}

	public static final String DEFAULT_ROUTING_STEP = "default-routing-step";

	public static enum RespondingWith {
		PURE_LLM_RESPONSE, RAG_LLM_RESPONSE, DEEP_SEARCH_RESPONSE, TOOLS_USE_RESPONSE
	}

	@Data
	public static class RoutingDecisionResponse {
		@NotNull
		private RespondingWith responseRouting = null;
	}

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return DEFAULT_ROUTING_STEP;
	}

	@Override
	public RoutingDecision execute(ChatPipelineExecutionRuntimeData runtimeData, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel) throws ChatPipelineException {
		String candidateOutput = runtimeData.isStreamingOutput()
				? DefaultStreamingOutputChatPipelineServiceImpl.DEFAULT_STREAMING_OUTPUT
				: DefaultOutputChatPipelineServiceImpl.DEFAULT_OUTPUT_STEP;
		String prompt = chatPipelinesConfig.getDefaultPipelineRoutingDecisionPrompt();

		RoutingDecision rd = null;
		if (runtimeData.getRequestResources().getLastRequest() != null
				&& runtimeData.getRequestResources().getLastRequest().getChatPipelineProcessId() != null) {
			rd = new RoutingDecision(
					List.of(runtimeData.getRequestResources().getLastRequest().getChatPipelineProcessId()),
					IChatPipelineStepRuntimeData.VoidRetun(DEFAULT_ROUTING_STEP));

		} else {
			try {
				Map<String, Object> templateParams = new HashMap<String, Object>();
				RoutingDecisionResponse llmRoutingDecision = callLLMStructuredReturn(serviceModel, prompt,
						runtimeData.getRequestResources().getLastRequest().getQuery(), templateParams,
						RoutingDecisionResponse.class);
				List<String> routes = futureRoutes(llmRoutingDecision.getResponseRouting(),
						RespondingWith.PURE_LLM_RESPONSE, runtimeData.isStreamingOutput());
				final IChatPipelineStepRuntimeData routingEntry = new IChatPipelineStepRuntimeData() {

					@Override
					public String getStepId() {

						return DefaultRoutingChatPipelineStepServiceImpl.this.getStepId();
					}

					@Override
					public List<IStepContribution> getContextEnrichingContribution() {

						return nextStepContribution(llmRoutingDecision, runtimeData, chatModel, serviceModel);
					}
				};
				rd = new RoutingDecision(routes, routingEntry);
			} catch (Throwable th) {
				LOGGER.error("Exception in chat pipeline routing falling back to PURE_LLM_RESPONSE", th);
				rd = new RoutingDecision(
						List.of(DefaultStreamingOutputChatPipelineServiceImpl.DEFAULT_STREAMING_OUTPUT),
						IChatPipelineStepRuntimeData.VoidRetun(DEFAULT_ROUTING_STEP));
			}
		}
		return rd;

	}

	protected List<IStepContribution> nextStepContribution(RoutingDecisionResponse llmRoutingDecision,
			ChatPipelineExecutionRuntimeData runtimeData, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel) {
		// TODO Auto-generated method stub
		return null;
	}

	protected List<String> futureRoutes(RespondingWith llmRoutingDecision, RespondingWith defaultRoute,
			boolean streaming) {
		RespondingWith considered = llmRoutingDecision != null ? llmRoutingDecision : defaultRoute;
		switch (considered) {
		case RAG_LLM_RESPONSE: {
			return List.of(DefaultRagStreamingOutputChatPipelineStepServiceImpl.DEFAULT_RAG_STEP);
		}
		case DEEP_SEARCH_RESPONSE: {
			return List.of(DefaultDeepSearchStreamingOutputChatPipelineStepServiceImpl.DEFAULT_DEEPSEARCH_STREAMING);
		}
		case TOOLS_USE_RESPONSE: {
			return List.of(DefaultToolUsingStreamingOutputChatPipelineServiceImpl.DEFAULT_TOOL_USING_STREAMING);
		}
		case PURE_LLM_RESPONSE:
		default:
			return List.of(DefaultStreamingOutputChatPipelineServiceImpl.DEFAULT_STREAMING_OUTPUT);
		}
	}
}
