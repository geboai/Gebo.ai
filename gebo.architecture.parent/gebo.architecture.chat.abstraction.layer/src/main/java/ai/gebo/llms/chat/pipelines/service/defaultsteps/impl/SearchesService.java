package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.fulltext.model.FullTextChunkSearchHit;
import ai.gebo.architecture.fulltext.model.MetaDataFilter;
import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.fulltext.service.IGFullTextSearchService;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeGraphSearchResult;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphSearchService;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.RagQueryOptions;
import ai.gebo.architecture.rag.support.layer.model.RagQueryOptions.CompletenessLevel;
import ai.gebo.architecture.rag.support.layer.services.IGSemanticSearchDocumentsCachedDao;
import ai.gebo.architecture.rag_threasholds_autotune.model.OptimizedThreashold;
import ai.gebo.architecture.rag_threasholds_autotune.service.IRagThreasholdAutotuneService;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboChatConfigs;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.model.session.GUserChatSession;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatProfilesRepository;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.model.SearchesSuggestions;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.security.services.IGSecurityService;

@Component
public class SearchesService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchesService.class);
	@Autowired
	IRagThreasholdAutotuneService semanticRagThreasholdAutotuneService;
	@Autowired
	GeboChatConfigs chatConfigs;
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

	public AIDocumentsSet search(SearchesSuggestions rewritings, String userQuery, int globalTopK,
			GUserChatSession context, int tokensBudget) throws FullTextException, LLMConfigException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin search(..)");
		}

		String chatProfileCode = context.getChatProfileCode();
		List<String> knowledgeBases = new ArrayList<String>();
		double threashold = chatConfigs.getDefaultSimilarityThreshold();
		double firstHopThreashold = chatConfigs.getDefaultSimilarityThreshold();
		double secondHopThreashold = chatConfigs.getDefaultSimilarityThreshold();
		boolean multiHopEnabled = true;

		int semanticRagTopK = chatConfigs.getDefaultTopK();
		boolean manualThreasholdsConfiguration = false;
		List<IGConfigurableEmbeddingModel> embeddingModels = new ArrayList<IGConfigurableEmbeddingModel>();
		if (chatProfileCode != null) {
			Optional<GChatProfileConfiguration> chatProfileOpt = this.chatProfilesRepository.findById(chatProfileCode);
			if (chatProfileOpt.isPresent()) {
				GChatProfileConfiguration chatProfile = chatProfileOpt.get();
				if (chatProfile.getEmbeddingModelReference() != null) {
					IGConfigurableEmbeddingModel em = embeddingModelsDao
							.findByModelReference(chatProfile.getEmbeddingModelReference());
					if (em != null)
						embeddingModels.add(em);
				}
				if (chatProfile.getTopK() != null && chatProfile.getTopK() > 0) {
					semanticRagTopK = chatProfile.getTopK();
				}
				if (chatProfile.getManualThreasholdsConfiguration() != null
						&& chatProfile.getManualThreasholdsConfiguration()) {
					manualThreasholdsConfiguration = true;
					multiHopEnabled = chatProfile.getDisableMultiHopRag() == null
							|| !chatProfile.getDisableMultiHopRag();
					if (chatProfile.getSimilaritySearchThreshold() != null
							&& chatProfile.getSimilaritySearchThreshold() > 0) {
						threashold = chatProfile.getSimilaritySearchThreshold();
						firstHopThreashold = threashold;
					}
					if (chatProfile.getOtherSearchSimilarityThreshold() != null
							&& chatProfile.getOtherSearchSimilarityThreshold() > 0) {
						secondHopThreashold = chatProfile.getOtherSearchSimilarityThreshold();
					}
				}
				boolean canAccess = securityService.isCanAccess(chatProfile, true);
				if (!canAccess)
					throw new RuntimeException(
							"The actual user cannot acces the indicated chat profile " + chatProfileCode);
				List<GKnowledgeBase> visibles = null;
				if (chatProfile.getUserChoosesKnowledgeBases() != null && chatProfile.getUserChoosesKnowledgeBases()) {
					visibles = knowledgeBaseVisibilityService.allVisibleKnowledgebases();
				} else {
					List<String> cpk = chatProfile.getKnowledgeBaseCodes();
					if (cpk != null && !cpk.isEmpty()) {
						visibles = knowledgeBaseVisibilityService.visiblesAndChildKnowledgebases(cpk);
					}
				}
				if (visibles != null) {
					knowledgeBases = visibles.stream().map(x -> x.getCode()).toList();
					for (GKnowledgeBase kb : visibles) {
						List<GObjectRef> ems = kb.getEmbeddingModelReferences();
						if (ems != null) {
							for (GObjectRef<GBaseEmbeddingModelConfig> emConfigRef : ems) {
								IGConfigurableEmbeddingModel em = embeddingModelsDao
										.findByModelReference(chatProfile.getEmbeddingModelReference());
								if (em != null && !embeddingModels.contains(em)) {
									embeddingModels.add(em);
								}
							}
						}
					}
				}
			}
		}
		IGConfigurableEmbeddingModel defaultModel = embeddingModelsDao.defaultHandler();
		if (defaultModel != null && !embeddingModels.contains(defaultModel)) {
			embeddingModels.add(defaultModel);
		}
		AIDocumentsSet out = new AIDocumentsSet();
		if (!knowledgeBases.isEmpty()) {
			List<String> semanticSearchedQuery = new ArrayList<String>();
			List<String> fullTextSearchedQuery = new ArrayList<String>();
			if (rewritings != null && rewritings.getRewrittenSemanticSearchSentences() != null) {
				semanticSearchedQuery.addAll(rewritings.getRewrittenSemanticSearchSentences());
			}
			if (userQuery != null) {
				semanticSearchedQuery.add(userQuery);
			}
			if (rewritings != null && rewritings.getRewrittenFullTextSearchSentences() != null) {
				fullTextSearchedQuery.addAll(rewritings.getRewrittenFullTextSearchSentences());
			}
			boolean endSearch = false;
			if (!semanticSearchedQuery.isEmpty() && !embeddingModels.isEmpty()) {
				for (IGConfigurableEmbeddingModel em : embeddingModels) {
					double _threashold = threashold;
					double _firstHopThreashold = firstHopThreashold;
					double _secondHopThreashold = secondHopThreashold;
					boolean _multiHopEnabled = multiHopEnabled;
					int _semanticRagTopK = Math.min(globalTopK, semanticRagTopK);
					if (!manualThreasholdsConfiguration) {
						OptimizedThreashold autotunedSettings = this.semanticRagThreasholdAutotuneService
								.findByEmbeddingModelCode(em.getCode());
						if (autotunedSettings != null) {
							_threashold = autotunedSettings.getOptimizedThreashold();
							_firstHopThreashold = autotunedSettings.getFirstHopOptimizedThreashold();
							_secondHopThreashold = autotunedSettings.getSecondHopOptimizedThreashold();
						}
					}
					for (String query : semanticSearchedQuery) {
						RagQueryOptions options = new RagQueryOptions(tokensBudget, CompletenessLevel.MAX_TOKENS);
						options.setSimilarityThreashold(threashold);
						options.setTopK(semanticRagTopK);
						AIDocumentsSet data = this.semanticSearchDao.multiHopSemanticSearch(query, options,
								knowledgeBases, em, _firstHopThreashold, _secondHopThreashold,
								securityService.getCurrentUser());
						out = AIDocumentsSet.join(data, out);
						endSearch = ((out.countFragments() >= globalTopK) || out.getTokensSize() >= tokensBudget);
						if (!endSearch) {
							
						}
						if (endSearch)
							break;
					}
					if (endSearch)
						break;
				}
			}
			if (fullTextSearch != null && !endSearch && !fullTextSearchedQuery.isEmpty()) {
				MetaDataFilter metaDataFilter = new MetaDataFilter();
				List<FullTextChunkSearchHit> fullTextResult = fullTextSearch.search(fullTextSearchedQuery,
						globalTopK - out.countFragments(), metaDataFilter);
				if (fullTextResult != null && !fullTextResult.isEmpty()) {
					AIDocumentsSet fullTextDocSet = toAIDocumentsSet(fullTextResult);
					out = AIDocumentsSet.join(fullTextDocSet, out);
				}
				endSearch = out.countFragments() >= globalTopK  || out.getTokensSize() >= tokensBudget;
			}
			if (knowledgeGraphSearchService != null && !endSearch) {
				List<KnowledgeGraphSearchResult> hits = knowledgeGraphSearchService.knowledgeGraphSearch(userQuery,
						knowledgeBases, globalTopK - out.countFragments());
				AIDocumentsSet graphRagDocSet = knowledgeGraphSearchService.toRagDocumentsCachedDaoResult(hits);
				if (graphRagDocSet != null) {
					out = AIDocumentsSet.join(graphRagDocSet, out);
				}
			}
			out.recalculateSize();
			for (AIDocumentReferenceItem doc : out.getDocumentItems()) {
				doc.reorderFragmentsByPosition();
			}
		} else {
			LOGGER.warn("No visible knowledge bases for rag");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End search(..) found --> " + out.getDocumentItems().size() + " documents/fragments");
		}
		return out;

	}

	private AIDocumentsSet toAIDocumentsSet(List<FullTextChunkSearchHit> fullTextResult) {
		List<Document> documents = fullTextResult.stream()
				.map(x -> new Document(x.getChunk().getId(), x.getChunk().getContent(), x.getChunk().getMetaData()))
				.toList();
		return AIDocumentsSet.from(documents);
	}

}
