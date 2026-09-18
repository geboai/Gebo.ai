package ai.gebo.llms.chat.abstraction.layer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphSearchService;
import ai.gebo.architecture.rag.support.layer.services.IGFullTextSearchDocumentsCachedDao;
import ai.gebo.architecture.rag.support.layer.services.IGSemanticSearchDocumentsCachedDao;
import ai.gebo.architecture.rag_threasholds_autotune.service.IRagThreasholdAutotuneService;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatProfilesRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.LLMGeneratedResourceRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRankerService;
import ai.gebo.llms.chat.abstraction.layer.services.impl.GDocumentsSearchServiceImpl;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.service.IInternalKnowledgeLLMAssistedRetrieveService;
import ai.gebo.llms.chat.pipelines.service.impl.InternalKnowledgeLLMAssistedRetrieveServiceImpl;
import ai.gebo.security.services.IGSecurityService;
import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.ragsearch.config")
@Data
public class GeboRagSearchConfig {
	private final IRagThreasholdAutotuneService semanticRagThreasholdAutotuneService;
	private final IGSemanticSearchDocumentsCachedDao semanticSearchDao;
	private final IGFullTextSearchDocumentsCachedDao fullTextSearch;
	private final IKnowledgeGraphSearchService knowledgeGraphSearchService;
	private final IGKnowledgebaseVisibilityService knowledgeBaseVisibilityService;
	private final ChatProfilesRepository chatProfilesRepository;
	private final IGSecurityService securityService;
	private final IGEmbeddingModelRuntimeConfigurationDao embeddingModelsDao;
	private final GUserChatSessionRepository sessionRepo;
	// Default number of top elements to be considered
	private int defaultTopK = 15;
	private int pureSearchRankedTopK = 30;
	private int pureSearchSingleDataSourceTopK = 60;
	private int deepSearchSingleDataSourceTopK = 50;
	private int deepSearchGlobalTopK = 30;
	private int pureSearchMaximumChunkSize = 512;
	// Default similarity threshold for comparisons
	private double defaultSimilarityThreshold = 0.50;
	// Second stage of the ranking: the ranked fragments are submitted, worst ranked
	// first, to the internal services chat model, that drops the ones completely
	// useless for the query. Disabling it leaves the ranker output untouched.
	private boolean rankerIrrelevanceFilterEnabled = true;
	// Fraction of the filtering chat model context window used to size each batch of
	// ranked fragments submitted to it
	private double rankerIrrelevanceFilterContextFraction = 0.5;
	// Upper bound on the fragments of a single batch, on top of the context window
	// budget. It keeps the batches small enough for the model to judge them one by one
	// and makes the walk advance in steps instead of deciding everything in one call.
	private int rankerIrrelevanceFilterMaxFragmentsPerBatch = 15;
	// The filter does not run on result sets smaller than this: on a handful of
	// fragments the extra LLM round trip is not worth its latency
	private int rankerIrrelevanceFilterMinDocuments = 5;
	/**
	 * Fraction of ranked fragments discarded by the irrelevance filter above which the
	 * event is logged at WARN instead of DEBUG. The filter dropping a lot is not a fault
	 * of the filter - it removes only what the service model judged useless on every one
	 * of its pieces - but a sustained high rate means the retrieval probes are off
	 * target, which is worth surfacing without enabling TRACE.
	 */
	private double rankerIrrelevanceFilterWarnRate = 0.6;
	// The best ranked fragments are never submitted to the filter: the filtering model
	// is non deterministic and can occasionally judge as useless a fragment the ranker
	// scored among the most relevant, so the top of the ranked list is protected from
	// it. Set to 0 to let the filter judge the whole list.
	private int rankerIrrelevanceFilterProtectedTopFragments = 2;
	// Hybrid retrieval. When enabled the lexical (full text) leg ALWAYS runs beside
	// the semantic one and the two share the requested topK, instead of the lexical
	// leg running only as a fallback when the semantic one under delivers. With a
	// populated vector store the semantic leg always filled the quota, so the
	// lexical index was never queried and queries carrying rare literal terms - the
	// ones lexical search is best at - could not be answered. Set to false to get
	// the previous semantic-first/lexical-fallback behaviour back.
	private boolean hybridSearchEnabled = true;
	// Share of the requested topK reserved for the lexical leg when hybrid retrieval
	// is enabled. The remainder goes to the semantic leg. 0.5 splits the budget
	// evenly; lower it to favour semantic recall, raise it to favour literal matches.
	private double hybridFullTextShare = 0.5;

	public GeboRagSearchConfig(@Autowired IRagThreasholdAutotuneService semanticRagThreasholdAutotuneService,
			@Autowired IGSemanticSearchDocumentsCachedDao semanticSearchDao,
			@Autowired(required = false) IGFullTextSearchDocumentsCachedDao fullTextSearch,
			@Autowired(required = false) IKnowledgeGraphSearchService knowledgeGraphSearchService,
			@Autowired IGKnowledgebaseVisibilityService knowledgeBaseVisibilityService,
			@Autowired ChatProfilesRepository chatProfilesRepository, @Autowired IGSecurityService securityService,
			@Autowired IGEmbeddingModelRuntimeConfigurationDao embeddingModelsDao,
			@Autowired GUserChatSessionRepository sessionRepo) {
		this.semanticRagThreasholdAutotuneService = semanticRagThreasholdAutotuneService;
		this.semanticSearchDao = semanticSearchDao;
		this.fullTextSearch = fullTextSearch;
		this.knowledgeBaseVisibilityService = knowledgeBaseVisibilityService;
		this.chatProfilesRepository = chatProfilesRepository;
		this.securityService = securityService;
		this.embeddingModelsDao = embeddingModelsDao;
		this.sessionRepo = sessionRepo;
		this.knowledgeGraphSearchService = knowledgeGraphSearchService;
	}

	@ConditionalOnMissingBean(IGDocumentsSearchService.class)
	@Bean
	@Scope("singleton")
	public IGDocumentsSearchService searchService() {
		return new GDocumentsSearchServiceImpl(semanticRagThreasholdAutotuneService, this, semanticSearchDao,
				fullTextSearch, knowledgeGraphSearchService, knowledgeBaseVisibilityService, chatProfilesRepository,
				securityService, embeddingModelsDao, sessionRepo);
	}

	@ConditionalOnMissingBean(IInternalKnowledgeLLMAssistedRetrieveService.class)
	@Bean
	@Scope("singleton")
	public IInternalKnowledgeLLMAssistedRetrieveService internalKnowledgeLLMAssistedRetrieveService(

			LLMGeneratedResourceRepository generatedRepo, IGChatSessionLifeCycleService chatSessionLifecycleService,
			ChatPipelinesConfiguration configuration, IGPromptConfigDao promptsDao,
			IGDocumentsSearchService searchesService, IGRankerService rankerService) {
		return new InternalKnowledgeLLMAssistedRetrieveServiceImpl(chatSessionLifecycleService, configuration,
				promptsDao, searchesService, securityService, rankerService);
	}
}
