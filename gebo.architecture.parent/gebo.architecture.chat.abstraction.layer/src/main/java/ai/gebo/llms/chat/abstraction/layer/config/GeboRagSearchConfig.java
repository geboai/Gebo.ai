package ai.gebo.llms.chat.abstraction.layer.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.fulltext.service.IGFullTextSearchService;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphSearchService;
import ai.gebo.architecture.rag.support.layer.services.IGAIDocumentsCacheService;
import ai.gebo.architecture.rag.support.layer.services.IGFullTextSearchDocumentsCachedDao;
import ai.gebo.architecture.rag.support.layer.services.IGSemanticSearchDocumentsCachedDao;
import ai.gebo.architecture.rag_threasholds_autotune.service.IRagThreasholdAutotuneService;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatProfilesRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.LLMGeneratedResourceRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.UserUploadContentServerSideRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
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
	// Default similarity threshold for comparisons
	private double defaultSimilarityThreshold = 0.50;

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
			IGDocumentsSearchService searchesService) {
		return new InternalKnowledgeLLMAssistedRetrieveServiceImpl(chatSessionLifecycleService, configuration,
				promptsDao, searchesService, securityService);
	}
}
