package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import org.springframework.stereotype.Component;

import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.ITokensCountable;
import ai.gebo.architecture.rag.support.layer.services.IGAIDocumentsCacheService;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.repository.LLMGeneratedResourceRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.UserUploadContentServerSideRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.abstraction.layer.services.IGPromptConfigDao;
import ai.gebo.llms.chat.abstraction.layer.services.IGRagChatService;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.model.SearchesSuggestions;
import reactor.core.publisher.Flux;

@Component
public class DefaultRagStreamingOutputChatPipelineStepServiceImpl extends BaseOutputChatPipelineService
		implements IStreamingOutputChatPipelineService {
	private final IGRagChatService ragChatService;
	private final IGPromptConfigDao promptsDao;
	private final ChatPipelinesConfiguration configuration;
	private final SearchesService searchesService;

	public static final String DEFAULT_RAG_STEP = "default-rag-step";

	public DefaultRagStreamingOutputChatPipelineStepServiceImpl(IGAIDocumentsCacheService documentsCacheService,
			IGChatStorageAreaService chatStorageAreaService, DocumentReferenceRepository docreferenceRepo,
			UserUploadContentServerSideRepository uploadsRepo, LLMGeneratedResourceRepository generatedRepo,
			IGRagChatService ragChatService, ChatPipelinesConfiguration configuration, SearchesService searchesService,
			IGPromptConfigDao promptsDao, IGChatSessionLifeCycleService chatSessionLifecycleService) {
		super(documentsCacheService, chatStorageAreaService, docreferenceRepo, uploadsRepo, generatedRepo,
				chatSessionLifecycleService);
		this.ragChatService = ragChatService;
		this.configuration = configuration;
		this.searchesService = searchesService;
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
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel) throws ChatPipelineException, GeboChatSessionLifecycleException {
		LLMChatRequestResources request = super.integrateWithAISuggestedDocuments(runtimeData, chatModel);
		SearchesSuggestions searchRewritings = DefaultPipelineSharedEnvironmentUtil
				.getAISuggestedSearchRewritings(runtimeData);

		try {
			int contextLength = chatModel.getContextLength();
			contextLength = Math.max(contextLength, 8192);
			request = integrateWithSearches(searchRewritings, runtimeData, chatModel, contextLength);
			GPromptConfig prompt = promptsDao.findByPromptUse(GeboPromptsLibrary.DEFAULT_PIPELINE_RAG_OUTPUT_PROMPT);
			return ragChatService.streamChat(prompt.getPrompt(), request, runtimeData.getUserChatContext(),
					runtimeData.getChatResponse(), chatModel);
		} catch (GeboChatException | LLMConfigException | FullTextException e) {
			throw new ChatPipelineException("Exception in finalizing rag chat", e);
		}

	}

	private LLMChatRequestResources integrateWithSearches(SearchesSuggestions searchRewritings,
			ChatPipelineExecutionRuntimeData runtimeData, IGConfigurableChatModel targetChatModel,
			int contextWindowLength) throws FullTextException, LLMConfigException, GeboChatSessionLifecycleException {
		LLMChatRequestResources request = runtimeData.getRequestResources();
		int tokensBudget = contextWindowLength - request.getTokensSize();
		boolean returnCleanRequest = false;
		if (tokensBudget < contextWindowLength / 2) {
			returnCleanRequest = true;
			tokensBudget = contextWindowLength - (request.getLastRequest().getTokensSize()
					+ ITokensCountable.tokensSize(request.getLastInteractions()));
		}
		AIDocumentsSet documentSet = searchesService.search(searchRewritings,
				GeboChatRequest.actualQuery(request.getLastRequest()), configuration.getGlobalRagTopK(),
				runtimeData.getUserChatContext(), tokensBudget);
		request = chatSessionLifecycleService.addRetrievedDocumentsToState(documentSet,
				runtimeData.getUserChatContext(), targetChatModel);
		return request;
	}

}
