package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.StepEnvironmentParameter;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.llms.deepsearch.repository.DeepSearchRequestRepository;
import ai.gebo.llms.deepsearch.service.IGDeepSearchConfigProvider;
import ai.gebo.llms.deepsearch.service.IGInternalKnlowledgeBaseRagDeepSearchService;
import ai.gebo.llms.deepsearch.service.IGInternalKnowledgeBaseDeepSearchExecutor;
import ai.gebo.llms.deepsearch.service.impl.DeepSearchServiceImpl;
import ai.gebo.model.GUserMessage;
import ai.gebo.security.services.IGSecurityService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;


@AllArgsConstructor
public class OLDDefaultDeepInternalKnowledgeBaseDeepSearchStreamOutputChatPipelineServiceImpl2 extends BaseLLMSInvokingService
		implements IStreamingOutputChatPipelineService {

	public static final String DEFAULT_DEEPRAG_STREAMING = "default-deeprag-streaming";
	private final static Logger LOGGER = LoggerFactory
			.getLogger(OLDDefaultDeepInternalKnowledgeBaseDeepSearchStreamOutputChatPipelineServiceImpl2.class);
	private final IGInternalKnlowledgeBaseRagDeepSearchService internalKnowledgeBaseRagStepDeepSearchService;
	private final IGInternalKnowledgeBaseDeepSearchExecutor executor;
	private final IGDeepSearchConfigProvider deepSearchConfigProvider;
	private final IGChatSessionLifeCycleService sessionLifecycleService;
	private final IGSecurityService securityService;
	private final IDocumentsChunkService chunkingService;
	private final IGeboThreadManager threadManager;
	private final DeepSearchServiceImpl deepSearchServiceImpl;
	private final IGPromptConfigDao promptsDao;
	private final DeepSearchRequestRepository deepSearchRequestRepository;

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return DEFAULT_DEEPRAG_STREAMING;
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws ChatPipelineException, GeboChatSessionLifecycleException, LLMConfigException {
		try {
			return executor.execute(runtimeData.getRequestResources(), runtimeData.getMinimalChatContext(),
					runtimeData.getChatResponse(), chatModel, serviceModel);
		} catch (Throwable e) {
			LOGGER.error("Error calling internal knowledge base executor", e);
			GUserMessage errorMessage = GUserMessage.errorMessage("Cannot execute internal knowledge base deep search",
					e);
			return Flux.just(new GeboChatMessageEnvelope(errorMessage));
		}

	}

	@Override
	public List<StepEnvironmentParameter> getRequiredParameters() {

		return List.of();
	}

}
