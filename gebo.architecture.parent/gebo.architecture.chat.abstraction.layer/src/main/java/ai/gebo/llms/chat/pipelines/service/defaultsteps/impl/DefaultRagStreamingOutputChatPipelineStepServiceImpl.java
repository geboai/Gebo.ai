package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import org.springframework.stereotype.Component;

import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.services.IGAIDocumentsCacheService;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import ai.gebo.llms.chat.abstraction.layer.repository.LLMGeneratedResourceRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.UserUploadContentServerSideRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
import ai.gebo.llms.chat.abstraction.layer.services.IGPromptConfigDao;
import ai.gebo.llms.chat.abstraction.layer.services.IGRagChatService;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.SearchesSuggestions;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import reactor.core.publisher.Flux;

@Component
public class DefaultRagStreamingOutputChatPipelineStepServiceImpl extends BaseOutputChatPipelineService
		implements IStreamingOutputChatPipelineService {
	private final IGRagChatService ragChatService;
	private final IGPromptConfigDao promptsDao;

	public static final String DEFAULT_RAG_STEP = "default-rag-step";

	public DefaultRagStreamingOutputChatPipelineStepServiceImpl(IGAIDocumentsCacheService documentsCacheService,
			IGChatStorageAreaService chatStorageAreaService, DocumentReferenceRepository docreferenceRepo,
			UserUploadContentServerSideRepository uploadsRepo, LLMGeneratedResourceRepository generatedRepo,
			IGRagChatService ragChatService, ChatPipelinesConfiguration configuration,
			IGDocumentsSearchService searchesService, IGPromptConfigDao promptsDao,
			IGChatSessionLifeCycleService chatSessionLifecycleService) {
		super(documentsCacheService, chatStorageAreaService, docreferenceRepo, uploadsRepo, generatedRepo,
				chatSessionLifecycleService, configuration, searchesService);
		this.ragChatService = ragChatService;
		this.promptsDao = promptsDao;

	}

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
			DocumentsEnrichDecision enrichDecision = super.doDocumentsRetrieve(runtimeData, chatModel,
					LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);

			GPromptConfig prompt = promptsDao.findByPromptUse(GeboPromptsLibrary.DEFAULT_PIPELINE_RAG_OUTPUT_PROMPT);
			return ragChatService.streamChat(prompt.getPrompt(), enrichDecision.getRequestResources(),
					runtimeData.getUserChatContext(), runtimeData.getChatResponse(), chatModel);
		} catch (GeboChatException | LLMConfigException | FullTextException e) {
			throw new ChatPipelineException("Exception in finalizing rag chat", e);
		}

	}

}
