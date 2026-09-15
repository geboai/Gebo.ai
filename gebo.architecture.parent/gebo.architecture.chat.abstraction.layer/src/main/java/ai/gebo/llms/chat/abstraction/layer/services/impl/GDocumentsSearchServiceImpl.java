package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.ContentAccessPolicy;
import ai.gebo.architecture.fulltext.model.FullTextChunkSearchHit;
import ai.gebo.architecture.fulltext.model.FullTextSearchMetaDataFilter;
import ai.gebo.architecture.fulltext.service.FullTextException;
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
		return executeResolvedSearch(knowledgeBases, embeddingModels, aclAliases, threashold, firstHopThreashold,
				secondHopThreashold, semanticRagTopK, manualThreasholdsConfiguration, semanticSearches,
				semanticSearchMetaDataFilter, fullTextSearches, fullTextSearchMetaDataFilter, userQuery, globalTopK,
				tokensBudget);
	}

	@Override
	public AIDocumentsSet search(String request, List<String> semanticSearches,
			SemanticSearchMetaDataFilter semanticSearchMetaDataFilter, List<String> fullTextSearches,
			FullTextSearchMetaDataFilter fullTextSearchMetaDataFilter, String userQuery, int globalTopK, int tokensBudget)
			throws FullTextException, LLMConfigException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin search(String request,..)");
		}
		List<Integer> aclAliases = null;
		if (securityService.getPlatformContentAccessPolicy() == ContentAccessPolicy.ACL_BASED
				&& !securityService.isCurrentUserAdmin()) {
			aclAliases = securityService.getCurrentAclGrantedAccessor(AclGrantType.READ).getAllOwnedAclAliases();
		}
		// No chat profile/session: scope to all knowledge bases visible to the current user.
		List<GKnowledgeBase> visibles = knowledgeBaseVisibilityService.allVisibleKnowledgebases();
		List<String> knowledgeBases = visibles != null ? visibles.stream().map(x -> x.getCode()).toList()
				: new ArrayList<String>();
		List<IGConfigurableEmbeddingModel> embeddingModels = new ArrayList<IGConfigurableEmbeddingModel>();
		IGConfigurableEmbeddingModel defaultModel = embeddingModelsDao.defaultHandler();
		if (defaultModel != null) {
			embeddingModels.add(defaultModel);
		}
		double threashold = chatConfigs.getDefaultSimilarityThreshold();
		int semanticRagTopK = chatConfigs.getDefaultTopK();
		return executeResolvedSearch(knowledgeBases, embeddingModels, aclAliases, threashold, threashold, threashold,
				semanticRagTopK, false, semanticSearches, semanticSearchMetaDataFilter, fullTextSearches,
				fullTextSearchMetaDataFilter, userQuery, globalTopK, tokensBudget);
	}

	private AIDocumentsSet executeResolvedSearch(List<String> knowledgeBases,
			List<IGConfigurableEmbeddingModel> embeddingModels, List<Integer> aclAliases, double threashold,
			double firstHopThreashold, double secondHopThreashold, int semanticRagTopK,
			boolean manualThreasholdsConfiguration, List<String> semanticSearches,
			SemanticSearchMetaDataFilter semanticSearchMetaDataFilter, List<String> fullTextSearches,
			FullTextSearchMetaDataFilter fullTextSearchMetaDataFilter, String userQuery, int globalTopK,
			int tokensBudget) throws FullTextException, LLMConfigException {
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
			// Hybrid retrieval budget. The lexical leg gets a reserved share of globalTopK
			// and the semantic leg is bounded by the remainder, so filling the semantic
			// quota can no longer starve the lexical one. See GeboRagSearchConfig.
			final boolean lexicalLegAvailable = fullTextSearch != null && !fullTextSearchedQuery.isEmpty();
			final boolean hybrid = chatConfigs.isHybridSearchEnabled() && lexicalLegAvailable;
			final int fullTextQuota = hybrid
					? Math.max(1, (int) Math.ceil(globalTopK * chatConfigs.getHybridFullTextShare()))
					: 0;
			final int semanticQuota = Math.max(1, globalTopK - fullTextQuota);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieval budget globalTopK:" + globalTopK + " hybrid:" + hybrid + " semanticQuota:"
						+ semanticQuota + " fullTextQuota:" + fullTextQuota + " semanticQueries:"
						+ semanticSearchedQuery.size() + " fullTextQueries:" + fullTextSearchedQuery.size());
			}
			// Every semantic (embedding model x query) probe, flattened so the leg can be
			// suspended at its quota and RESUMED later to back fill whatever the optional
			// legs did not deliver.
			final List<SemanticProbe> semanticProbes = new ArrayList<SemanticProbe>();
			if (!semanticSearchedQuery.isEmpty() && !embeddingModels.isEmpty()) {
				for (IGConfigurableEmbeddingModel em : embeddingModels) {
					double _threashold = threashold;
					double _firstHopThreashold = firstHopThreashold;
					double _secondHopThreashold = secondHopThreashold;
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
						// _semanticRagTopK, not semanticRagTopK: the latter is the global chat
						// default (15) and ignoring the Math.min above let a single semantic
						// query overshoot the caller's budget and end the whole search.
						semanticProbes.add(new SemanticProbe(em, query, _threashold, _firstHopThreashold,
								_secondHopThreashold, _semanticRagTopK));
					}
				}
			}
			SemanticLegOutcome semanticLeg = runSemanticProbes(semanticProbes, 0, semanticQuota, out,
					semanticSearchMetaDataFilter, tokensBudget);
			out = semanticLeg.out();
			boolean endSearch = out.countFragments() >= globalTopK || out.getTokensSize() >= tokensBudget;
			// The lexical leg runs whenever hybrid retrieval is on. Only when hybrid is
			// disabled does it fall back to its historical role of filling the gap left
			// by an under delivering semantic leg.
			if (lexicalLegAvailable && (hybrid || !endSearch)) {
				FullTextSearchMetaDataFilter metaDataFilter = new FullTextSearchMetaDataFilter();
				metaDataFilter.setKnowledgebaseCodes(knowledgeBases);
				boolean filterWithAcl = !securityService.isCurrentUserAdmin()
						&& securityService.getPlatformContentAccessPolicy() == ContentAccessPolicy.ACL_BASED;
				metaDataFilter.setAclAliases(filterWithAcl ? aclAliases : null);
				// With hybrid retrieval the lexical leg keeps its reserved quota even when
				// the semantic leg already returned globalTopK fragments, otherwise the
				// remainder would be zero or negative and the call would be pointless.
				final int fullTextTopK = hybrid ? Math.max(fullTextQuota, globalTopK - out.countFragments())
						: globalTopK - out.countFragments();
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Running the lexical leg with topK:" + fullTextTopK + " over "
							+ fullTextSearchedQuery.size() + " quer(ies), semantic leg contributed "
							+ out.countFragments() + " fragment(s)");
				}
				try {
					AIDocumentsSet fullTextDocSet = fullTextSearch.search(fullTextSearchedQuery, fullTextTopK,
							metaDataFilter);
					if (fullTextDocSet != null) {
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Lexical leg contributed " + fullTextDocSet.countFragments() + " fragment(s)");
						}
						out = AIDocumentsSet.join(fullTextDocSet, out);
					}
				} catch (FullTextException | RuntimeException e) {
					// A configured but unreachable or empty lexical index must not fail the
					// whole retrieval: the semantic leg already produced usable evidence and
					// the back fill below recovers the reserved quota.
					LOGGER.warn("Lexical search leg failed, continuing with the other legs", e);
				}
				endSearch = out.countFragments() >= globalTopK || out.getTokensSize() >= tokensBudget;
			}
			if (knowledgeGraphSearchService != null && !endSearch) {
				try {
					List<KnowledgeGraphSearchResult> hits = knowledgeGraphSearchService.knowledgeGraphSearch(userQuery,
							knowledgeBases, globalTopK - out.countFragments());
					AIDocumentsSet graphRagDocSet = knowledgeGraphSearchService.toRagDocumentsCachedDaoResult(hits);
					if (graphRagDocSet != null) {
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Knowledge graph leg contributed " + graphRagDocSet.countFragments()
									+ " fragment(s)");
						}
						out = AIDocumentsSet.join(graphRagDocSet, out);
					}
				} catch (RuntimeException e) {
					LOGGER.warn("Knowledge graph search leg failed, continuing with the other legs", e);
				}
				endSearch = out.countFragments() >= globalTopK || out.getTokensSize() >= tokensBudget;
			}
			// Back fill. The lexical and knowledge graph legs are optional: the beans are
			// @Autowired(required = false), their index may be empty and the call may
			// fail. Without this, reserving a share of globalTopK for a leg that then
			// delivers nothing would return FEWER fragments than before hybrid retrieval
			// existed. Whatever they left unused goes back to the semantic leg, which
			// resumes from the probe it was suspended on rather than repeating itself.
			if (!endSearch && semanticLeg.cursor() < semanticProbes.size()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Back filling from the semantic leg: " + out.countFragments() + " of " + globalTopK
							+ " fragment(s) so far, resuming at probe " + semanticLeg.cursor() + " of "
							+ semanticProbes.size());
				}
				out = runSemanticProbes(semanticProbes, semanticLeg.cursor(), globalTopK, out,
						semanticSearchMetaDataFilter, tokensBudget).out();
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

	/**
	 * One semantic retrieval probe: an embedding model paired with a query and the
	 * thresholds/topK resolved for that model. Flattening the model x query matrix
	 * lets the semantic leg stop at a quota and resume exactly where it left off.
	 */
	private record SemanticProbe(IGConfigurableEmbeddingModel model, String query, double threashold,
			double firstHopThreashold, double secondHopThreashold, int topK) {
	}

	/**
	 * What the semantic leg produced and the probe it stopped on, so a later pass
	 * can resume without repeating queries already issued.
	 */
	private record SemanticLegOutcome(AIDocumentsSet out, int cursor) {
	}

	/**
	 * Runs semantic probes from {@code from} until the accumulated set reaches
	 * {@code quota} fragments or exhausts the token budget, and reports where it
	 * stopped.
	 */
	private SemanticLegOutcome runSemanticProbes(List<SemanticProbe> probes, int from, int quota, AIDocumentsSet out,
			SemanticSearchMetaDataFilter semanticSearchMetaDataFilter, int tokensBudget) {
		int cursor = from;
		while (cursor < probes.size()) {
			final SemanticProbe probe = probes.get(cursor);
			cursor++;
			RagQueryOptions options = new RagQueryOptions(tokensBudget, CompletenessLevel.MAX_TOKENS);
			options.setSimilarityThreashold(probe.threashold());
			options.setTopK(probe.topK());
			AIDocumentsSet data = this.semanticSearchDao.multiHopSemanticSearch(probe.query(),
					semanticSearchMetaDataFilter, options, probe.model(), probe.firstHopThreashold(),
					probe.secondHopThreashold(), securityService.getCurrentUser());
			out = AIDocumentsSet.join(data, out);
			if (out.countFragments() >= quota || out.getTokensSize() >= tokensBudget) {
				break;
			}
		}
		return new SemanticLegOutcome(out, cursor);
	}

	private AIDocumentsSet toAIDocumentsSet(List<FullTextChunkSearchHit> fullTextResult) {
		List<Document> documents = fullTextResult.stream()
				.map(x -> new Document(x.getChunk().getId(), x.getChunk().getContent(), x.getChunk().getMetaData()))
				.toList();
		return AIDocumentsSet.from(documents);
	}

}
