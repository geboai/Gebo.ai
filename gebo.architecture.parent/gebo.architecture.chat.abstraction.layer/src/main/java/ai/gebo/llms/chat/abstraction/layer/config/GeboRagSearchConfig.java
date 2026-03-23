package ai.gebo.llms.chat.abstraction.layer.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ai.gebo.architecture.fulltext.service.IGFullTextSearchService;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphSearchService;
import ai.gebo.architecture.rag.support.layer.services.IGSemanticSearchDocumentsCachedDao;
import ai.gebo.architecture.rag_threasholds_autotune.service.IRagThreasholdAutotuneService;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatProfilesRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
import ai.gebo.llms.chat.abstraction.layer.services.impl.GDocumentsSearchServiceImpl;
import ai.gebo.security.services.IGSecurityService;
import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.ragsearch.config")
@Data
public class GeboRagSearchConfig {
	@Autowired
	IRagThreasholdAutotuneService semanticRagThreasholdAutotuneService;

	@Autowired
	IGSemanticSearchDocumentsCachedDao semanticSearchDao;
	@Autowired(required = false)
	IGFullTextSearchService fullTextSearch;
	@Autowired(required = false)
	IKnowledgeGraphSearchService knowledgeGraphSearchService;
	@Autowired
	IGKnowledgebaseVisibilityService knowledgeBaseVisibilityService;
	@Autowired
	ChatProfilesRepository chatProfilesRepository;
	@Autowired
	IGSecurityService securityService;
	@Autowired
	IGEmbeddingModelRuntimeConfigurationDao embeddingModelsDao;
	@Autowired
	GUserChatSessionRepository sessionRepo;
	// Default number of top elements to be considered
	private int defaultTopK = 15;
	// Default similarity threshold for comparisons
	private double defaultSimilarityThreshold = 0.50;

	@ConditionalOnMissingBean(IGDocumentsSearchService.class)
	@Bean
	@Scope("singleton")
	public IGDocumentsSearchService searchService() {
		return new GDocumentsSearchServiceImpl(semanticRagThreasholdAutotuneService, this, semanticSearchDao,
				fullTextSearch, knowledgeGraphSearchService, knowledgeBaseVisibilityService, chatProfilesRepository,
				securityService, embeddingModelsDao, sessionRepo);
	}
}
