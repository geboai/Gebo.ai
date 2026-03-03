package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import org.springframework.stereotype.Component;

import ai.gebo.architecture.ai.model.GPromptConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.rag.support.layer.services.IGAIDocumentsCacheService;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.repository.LLMGeneratedResourceRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.UserUploadContentServerSideRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRagChatService;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.DocumentsEnrichDecision;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IInternalKnowledgeLLMAssistedRetrieveService;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Component
@AllArgsConstructor
public class DefaultRagStreamingOutputChatPipelineStepServiceImpl implements IStreamingOutputChatPipelineService {
	private final IInternalKnowledgeLLMAssistedRetrieveService internalKnowledgeLLMAssistedRetriever;
	private final IGRagChatService ragChatService;
	private final IGPromptConfigDao promptsDao;
	public static final String DEFAULT_RAG_STEP = "default-rag-step";

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return DEFAULT_RAG_STEP;
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws ChatPipelineException, GeboChatSessionLifecycleException {

		try {
			Flux<DocumentsEnrichDecision> enrichDecision = internalKnowledgeLLMAssistedRetriever.doDocumentsRetrieve(
					runtimeData.getRequestResources(), runtimeData.getMinimalChatContext(), serviceModel,
					LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
			Flux<GeboChatMessageEnvelope> flux = enrichDecision.concatMap(ed -> {
				GPromptConfig prompt = promptsDao
						.findByPromptUse(GeboPromptsLibrary.DEFAULT_PIPELINE_RAG_OUTPUT_PROMPT);
				try {
					return ragChatService.streamChat(prompt.getPrompt(), ed.getRequestResources(),
							runtimeData.getChatResponse(), chatModel);
				} catch (GeboChatException | LLMConfigException e) {
					String msg = "Nested exception in deferred stream creation";
					throw new RuntimeException(msg, e);
				}
			});
			return flux;
		} catch (GeboChatException | LLMConfigException | FullTextException e) {
			throw new ChatPipelineException("Exception in finalizing rag chat", e);
		}

	}

}
