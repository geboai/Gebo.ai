package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.ContentAccessPolicy;
import ai.gebo.architecture.fulltext.model.FullTextChunkSearchHit;
import ai.gebo.architecture.fulltext.model.FullTextSearchMetaDataFilter;
import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.fulltext.service.IGFullTextSearchService;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeGraphSearchResult;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphSearchService;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.RagQueryOptions;
import ai.gebo.architecture.rag.support.layer.model.RagQueryOptions.CompletenessLevel;
import ai.gebo.architecture.rag.support.layer.model.SemanticSearchMetaDataFilter;
import ai.gebo.architecture.rag.support.layer.services.IGFullTextSearchDocumentsCachedDao;
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
import ai.gebo.llms.chat.abstraction.layer.config.GeboRagSearchConfig;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatProfilesRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.security.services.IGSecurityService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GDocumentsSearchServiceImpl implements IGDocumentsSearchService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GDocumentsSearchServiceImpl.class);

	@Override
	public AIDocumentsSet search(GeboChatRequest chatRequest, SemanticSearchMetaDataFilter semanticSearchMetaDataFilter, FullTextSearchMetaDataFilter fullTextSearchMetaDataFilter, int tokensBudget)
			throws FullTextException, LLMConfigException {
		List<String> semanticSearches = new ArrayList<String>();
		List<String> fullTextSearches = new ArrayList<String>();
		String userQuery = GeboChatRequest.actualQuery(chatRequest);
		return this.search(chatRequest, semanticSearches, semanticSearchMetaDataFilter, fullTextSearches, fullTextSearchMetaDataFilter, userQuery, tokensBudget, tokensBudget);
	}

	final IRagThreasholdAutotuneService semanticRagThreasholdAutotuneService;
	final GeboRagSearchConfig chatConfigs;
	final IGSemanticSearchDocumentsCachedDao semanticSearchDao;
	final IGFullTextSearchDocumentsCachedDao fullTextSearch;
	final IKnowledgeGraphSearchService knowledgeGraphSearchService;
	final IGKnowledgebaseVisibilityService knowledgeBaseVisibilityService;
	final ChatProfilesRepository chatProfilesRepository;
	final IGSecurityService securityService;
	final IGEmbeddingModelRuntimeConfigurationDao embeddingModelsDao;
	final GUserChatSessionRepository sessionRepo;

	@Override
	public AIDocumentsSet search(GeboChatRequest request, List<String> semanticSearches, SemanticSearchMetaDataFilter semanticSearchMetaDataFilter,
			List<String> fullTextSearches, FullTextSearchMetaDataFilter fullTextSearchMetaDataFilter, String userQuery, int globalTopK, int tokensBudget) throws FullTextException, LLMConfigException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin search(..)");
		}

		String chatProfileCode = request.getChatProfileCode();
		if (chatProfileCode == null || chatProfileCode.trim().length() == 0) {
			String id = request.getUserChatContextCode();
			if (id == null)
				throw new RuntimeException("The chat session have to be initialized");
			GUserChatSession session = this.sessionRepo.findById(id).orElseThrow(() -> {
				throw new RuntimeException("The chat session do not exist");
			});
			chatProfileCode = session.getChatProfileCode();
		}
		List<String> knowledgeBases = new ArrayList<String>();
		List<Integer> aclAliases = null;
		if (securityService.getPlatformContentAccessPolicy() == ContentAccessPolicy.ACL_BASED
				&& !securityService.isCurrentUserAdmin()) {
			aclAliases = securityService.getCurrentAclGrantedAccessor(AclGrantType.READ).getAllOwnedAclAliases();
		}
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
			if (semanticSearches != null) {
				semanticSearchedQuery.addAll(semanticSearches);
			}
			if (userQuery != null) {
				semanticSearchedQuery.add(userQuery);
			}
			if (fullTextSearches != null) {
				fullTextSearchedQuery.addAll(fullTextSearches);
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
						AIDocumentsSet data = this.semanticSearchDao.multiHopSemanticSearch(query, semanticSearchMetaDataFilter,
								options, em, _firstHopThreashold, _secondHopThreashold,
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
				FullTextSearchMetaDataFilter metaDataFilter = new FullTextSearchMetaDataFilter();
				metaDataFilter.setKnowledgebaseCodes(knowledgeBases);
				boolean filterWithAcl = !securityService.isCurrentUserAdmin()
						&& securityService.getPlatformContentAccessPolicy() == ContentAccessPolicy.ACL_BASED;
				metaDataFilter.setAclAliases(filterWithAcl ? aclAliases : null);
				AIDocumentsSet fullTextDocSet = fullTextSearch.search(fullTextSearchedQuery,
						globalTopK - out.countFragments(), metaDataFilter);
				if (fullTextDocSet != null) {
					out = AIDocumentsSet.join(fullTextDocSet, out);
				}
				endSearch = out.countFragments() >= globalTopK || out.getTokensSize() >= tokensBudget;
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
