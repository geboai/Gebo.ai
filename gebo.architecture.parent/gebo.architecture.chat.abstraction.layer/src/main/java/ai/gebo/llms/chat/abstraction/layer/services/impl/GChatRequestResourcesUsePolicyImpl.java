/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ai.gebo.architecture.graphrag.persistence.model.KnowledgeGraphSearchResult;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphSearchService;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.RagDocumentFragment;
import ai.gebo.llms.abstraction.layer.model.RagDocumentReferenceItem;
import ai.gebo.llms.abstraction.layer.model.RagDocumentsCachedDaoResult;
import ai.gebo.llms.abstraction.layer.model.RagQueryOptions;
import ai.gebo.llms.abstraction.layer.model.RagQueryOptions.CompletenessLevel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGRagDocumentsCachedDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.ContextWindowLengthRangeSettings;
import ai.gebo.llms.chat.abstraction.layer.config.GeboRagConfigs;
import ai.gebo.llms.chat.abstraction.layer.config.HistoryStrategy;
import ai.gebo.llms.chat.abstraction.layer.model.ChatHistoryData;
import ai.gebo.llms.chat.abstraction.layer.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.model.ChatModelLimitedRequest;
import ai.gebo.llms.chat.abstraction.layer.model.RagChatModelLimitedRequest;
import ai.gebo.llms.chat.abstraction.layer.model.ChatModelRequestContextWindowStats;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatConsolidationData;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.TokenLimitedContent;
import ai.gebo.llms.chat.abstraction.layer.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatRequestResourcesUsePolicy;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.models.metainfos.IGModelsLibraryDao;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.model.ExtractedDocumentMetaData;
import ai.gebo.security.repository.UserRepository.UserInfos;

/**
 * Service implementation for managing chat request resource policies. This
 * class is responsible for managing and optimizing chat requests, including
 * token estimation and history management.
 * 
 * AI generated comments
 */
@Service
public class GChatRequestResourcesUsePolicyImpl implements IGChatRequestResourcesUsePolicy {
	private static final String GRAPH_RAG = "GRAPH-RAG";

	private static final String SEMANTIC_RAG = "SEMANTIC-RAG";

	private static final ObjectMapper mapper4logging = new ObjectMapper();
	static {
		mapper4logging.registerModule(new JavaTimeModule());
		mapper4logging.enable(SerializationFeature.INDENT_OUTPUT);
	}
	private static final Logger LOGGER = LoggerFactory.getLogger(GChatRequestResourcesUsePolicyImpl.class);

	@Autowired
	protected IGRagDocumentsCachedDao ragDocumentsCachedDao;

	@Autowired
	protected GeboRagConfigs ragResourcesConfig;

	@Autowired
	IGModelsLibraryDao modelsLibraryDao;
	@Autowired(required = false)
	IKnowledgeGraphSearchService knowledgeGraphSearch;
	@Autowired
	IGChatStorageAreaService storageAreaService;
	protected JTokkitTokenCountEstimator tokenEstimator = new JTokkitTokenCountEstimator();

	/**
	 * Default constructor.
	 */
	public GChatRequestResourcesUsePolicyImpl() {
	}

	/**
	 * Estimation and computation of the total token count for the user's chat
	 * history.
	 *
	 * @param userContext The user chat context containing interactions.
	 * @return A TokenLimitedContent object containing the chat history and
	 *         corresponding token count.
	 */
	private TokenLimitedContent<ChatHistoryData> completeHistoryTokenEstimationOnlyRequestTextAndResponseTextNTokens(
			GUserChatContext userContext) {
		TokenLimitedContent<ChatHistoryData> _history = new TokenLimitedContent<ChatHistoryData>();
		int nhistoryTokens = 0;
		ChatHistoryData out = new ChatHistoryData();
		GUserChatConsolidationData consolidated = userContext.getConsolidation();
		out.setConsolidated(consolidated);
		List<ChatInteractions> history = new ArrayList<ChatInteractions>();
		int consolidatedTokensSize = consolidated != null ? consolidated.getTokensSize() : 0;
		int firstInteractionToInclude = consolidated != null ? consolidated.getLastInteractionPointer() : 0;
		if (userContext.getInteractions() != null) {
			for (int i = firstInteractionToInclude; i < userContext.getInteractions().size(); i++) {
				ChatInteractions interaction = userContext.getInteractions().get(i);
				if (interaction.getRequestNTokens() == null && interaction.getRequest() != null) {
					interaction.setRequestNTokens(tokenEstimator.estimate(interaction.getRequest().getQuery()));
				}

				if (interaction.getResponseNTokens() == null && interaction.getResponse() != null
						&& interaction.getResponse().getQueryResponse() != null) {
					interaction.setResponseNTokens(
							tokenEstimator.estimate(interaction.getResponse().getQueryResponse().toString()));
				}
				if (interaction.getRequestNTokens() != null) {
					nhistoryTokens += interaction.getRequestNTokens();
				}

				if (interaction.getResponseNTokens() != null) {
					nhistoryTokens += interaction.getResponseNTokens();
				}

				history.add(interaction);

			}
		}
		_history.setValue(out);
		_history.setNToken(nhistoryTokens + consolidatedTokensSize);
		return _history;
	}

	/**
	 * Get the context length from the chat handler's configuration.
	 *
	 * @param chatHandler The chat handler from which to retrieve context length.
	 * @return The context length in terms of token count.
	 */
	private int getContextLength(IGConfigurableChatModel chatHandler) {
		int contextWindowNToken = chatHandler.getContextLength();
		GBaseChatModelConfig<?> config = (GBaseChatModelConfig<?>) chatHandler.getConfig();
		String modelCode = config.getChoosedModel() != null ? config.getChoosedModel().getCode() : null;
		String modelProvider = config.getModelTypeCode();

		ModelMetaInfo meta = null;
		if (modelCode != null) {
			if (modelProvider != null) {
				meta = modelsLibraryDao.findByProviderIdAndModelId(modelProvider, modelCode);
			}
			if (meta == null) {
				meta = modelsLibraryDao.findByModelId(modelCode);
			}
		}
		return meta != null && meta.getContextLength() != null ? meta.getContextLength() : contextWindowNToken;
	}

	/**
	 * Manage the chat request by optimizing the allocation of resources such as
	 * tokens.
	 *
	 * @param chatProfile               The chat profile configuration.
	 * @param userContext               The user's chat context.
	 * @param request                   The chat request.
	 * @param embeddingHandler          The embedding model handler.
	 * @param chatHandler               The chat model handler.
	 * @param visibleKnowledgeBaseCodes List of visible knowledge base codes.
	 * @return A ChatModelLimitedRequest object representing the optimized request.
	 * @throws LLMConfigException In case of configuration errors.
	 * @throws IOException
	 */
	@Override
	public RagChatModelLimitedRequest manageRagChatRequest(GChatProfileConfiguration chatProfile,
			GUserChatContext userContext, UserInfos user, GeboChatRequest request,
			IGConfigurableEmbeddingModel embeddingHandler, IGConfigurableChatModel chatHandler,
			List<String> visibleKnowledgeBaseCodes) throws LLMConfigException, IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin manageRagChatRequest(.....)");
		}
		RagChatModelLimitedRequest lrequest = new RagChatModelLimitedRequest();
		// considering the context window in Nr of tokens
		int contextWindowNToken = getContextLength(chatHandler);

		// retrieve percentage of allocations settings and optimizations
		ContextWindowLengthRangeSettings settings = findOptimizationSettings(contextWindowNToken);
		lrequest.setContextWindowNToken(contextWindowNToken);
		// check if tooling is enabled
		GBaseChatModelConfig<?> chatModelConfig = (GBaseChatModelConfig<?>) chatHandler.getConfig();
		boolean hasToolingEnabled = chatModelConfig.getEnabledFunctions() != null
				&& !chatModelConfig.getEnabledFunctions().isEmpty();
		// if tooling is enabled calculate a residual (or important) space that will
		// remain free for tools use
		int toolsTokensSpaceReservation = 0;
		if (hasToolingEnabled) {
			toolsTokensSpaceReservation = (int) (((double) contextWindowNToken)
					* (settings.minimumToolAvailablePercent / 100.0));
		}
		// compute query tokens size
		lrequest.setQuery(new TokenLimitedContent<String>());
		lrequest.getQuery().setValue(request.getQuery());
		lrequest.getQuery().setNToken(tokenEstimator.estimate(request.getQuery()));
		int requestSize = lrequest.getQuery().getNToken();
		// adding history history and the
		// available tokens for documents will be already decreased from this allocation
		lrequest.setHistory(completeHistoryTokenEstimationOnlyRequestTextAndResponseTextNTokens(userContext));
		ChatModelRequestContextWindowStats stats = lrequest.getStats();
		int availableTokensForDocuments = 0;

		// compute actual history size
		int historySizeTarget = lrequest.getHistory().getNToken();
		boolean historyToBeShrinked = false;
		if (stats.historySharePerc > settings.historyLimitPercent) {
			// if history is taking more than its maximum potential share than recompute a
			// maximum available tokens for documents theorizing to shring the history to
			// its maximum share
			// but leaving space for tool calling
			historyToBeShrinked = true;
			historySizeTarget = (int) (((double) contextWindowNToken) * settings.historyLimitPercent / 100.0);
			availableTokensForDocuments = contextWindowNToken - historySizeTarget - toolsTokensSpaceReservation
					- requestSize;
		} else {
			// if history space is less than its maximum share compute maximum available
			// tokens for documents by
			// subtracting actual amount of delta from the full context window
			// (availableNTokens) minus the
			// tool calls free space
			availableTokensForDocuments = (int) stats.availableNTokens;
			availableTokensForDocuments -= toolsTokensSpaceReservation;
		}
		boolean preemptiveHistoryShrinked = (stats.historySharePerc + 10.0) > settings.historyLimitPercent;
		int preemptiveHistorySizeTarget = (int) (((double) contextWindowNToken) * settings.historyLimitPercent / 100.0);
		lrequest.setHistoryConsolidationRequired(preemptiveHistoryShrinked);
		lrequest.setHistorySizeTarget(preemptiveHistorySizeTarget);
		// first add explicitly uploaded documents
		lrequest.setUploadedDocuments(
				createLimitedUploadedDocumentsOnRequests(userContext, request, availableTokensForDocuments));
		availableTokensForDocuments -= lrequest.getUploadedDocuments().getNToken();
		RagDocumentsCachedDaoResult extractedDocuments = null;
		RagQueryOptions ragQueryOptions = null;
		ragQueryOptions = new RagQueryOptions();
		ragQueryOptions.setMaxTokens(availableTokensForDocuments);
		int topK = chatProfile.getTopK() != null ? chatProfile.getTopK() : ragResourcesConfig.getDefaultTopK();
		ragQueryOptions.setSimilarityThreashold(this.ragResourcesConfig.getDefaultSimilarityThreshold());
		if (chatProfile.getSimilaritySearchThreshold() != null) {
			if (chatProfile.getSimilaritySearchThreshold() > 0.0 && chatProfile.getSimilaritySearchThreshold() < 1.00) {
				ragQueryOptions.setSimilarityThreashold(chatProfile.getSimilaritySearchThreshold());
			} else {
				LOGGER.warn("Wrong chat profile similaritySearchThreshold=>"
						+ chatProfile.getSimilaritySearchThreshold() + " for code=" + chatProfile.getCode());
			}
		}
		boolean forcedChatWithDocuments = request.getForcedRequestDocuments() != null
				&& !request.getForcedRequestDocuments().isEmpty();
		if (forcedChatWithDocuments) {
			// CHAT WITH DOCUMENTS OPTIONS

			ragQueryOptions.setCompleteness(CompletenessLevel.FULL_DOCUMENTS);
			extractedDocuments = ragDocumentsCachedDao.chatWithDocumentsSearch(request.getQuery(), ragQueryOptions,
					request.getForcedRequestDocuments(), visibleKnowledgeBaseCodes, embeddingHandler, user);
		} else {

			ragQueryOptions.setTopK(topK);
			ragQueryOptions.setCompleteness(CompletenessLevel.MAX_TOKENS);
			if (chatProfile.getDisableMultiHopRag() != null && chatProfile.getDisableMultiHopRag()) {
				extractedDocuments = ragDocumentsCachedDao.semanticSearch(request.getQuery(), ragQueryOptions,
						visibleKnowledgeBaseCodes, embeddingHandler, user);
			} else {
				extractedDocuments = ragDocumentsCachedDao.multiHopSemanticSearch(request.getQuery(), ragQueryOptions,
						visibleKnowledgeBaseCodes, embeddingHandler, chatProfile.getSimilaritySearchThreshold(),
						chatProfile.getOtherSearchSimilarityThreshold(), user);
			}
		}
		extractedDocuments.getDocumentItems().forEach(x -> x.getFragments().forEach(y -> {
			y.setOrigin(SEMANTIC_RAG);
		}));
		lrequest.setDocuments(new TokenLimitedContent<RagDocumentsCachedDaoResult>());
		lrequest.getDocuments().setValue(extractedDocuments);
		lrequest.getDocuments().setNToken((int) extractedDocuments.getNTokens());
		if (!forcedChatWithDocuments && this.knowledgeGraphSearch != null) {
			// Run graph rag after semantic rag to provide context on more
			// factual/entity/events based approach
			if (availableTokensForDocuments > lrequest.getDocuments().getNToken()
					&& this.knowledgeGraphSearch.isConfigured(null)) {
				try {
					List<KnowledgeGraphSearchResult> graphRagResults = this.knowledgeGraphSearch
							.knowledgeGraphSearch(request.getQuery(), visibleKnowledgeBaseCodes, topK);
					mergeGraphRagResults(lrequest.getDocuments(), graphRagResults, availableTokensForDocuments);
					lrequest.getDocuments().setNToken((int) extractedDocuments.getNTokens());
				} catch (Throwable throwable) {
					LOGGER.error("Error invoking graphrag", throwable);
				}
			}

		}
		int historicDocumentsBudget = availableTokensForDocuments - lrequest.getDocuments().getNToken();
		// i add all documents from the history untill fitting the maximum docs size
		if (historicDocumentsBudget > 0) {
			lrequest.setContextDocuments(new TokenLimitedContent<RagDocumentsCachedDaoResult>());
			try {
				lrequest.getContextDocuments().setValue(
						createHistoricContextDocuments(userContext, extractedDocuments, historicDocumentsBudget));
			} catch (Throwable t) {
				LOGGER.error("Error in createHistoricContextDocuments", t);
			}
		}
		stats = lrequest.getStats();
		if (stats.availableNTokens < toolsTokensSpaceReservation) {
			// now check real documents size allocations and recalculate
			lrequest.setHistory(shrinkHistory(userContext, historySizeTarget, settings.historyStrategy, chatHandler));
		}
		stats = lrequest.getStats();

		// Put here conditions and optimizations to fix free tokens space
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End manageRagChatRequest(.....) stats=>" + logObject(stats));
		}
		return lrequest;
	}

	private TokenLimitedContent<RagDocumentsCachedDaoResult> createLimitedUploadedDocumentsOnRequests(
			GUserChatContext userContext, GeboChatRequest request, int availableTokensForDocuments) throws IOException {
		int budget = availableTokensForDocuments;
		TokenLimitedContent<RagDocumentsCachedDaoResult> outValue = new TokenLimitedContent<>();
		RagDocumentsCachedDaoResult value = new RagDocumentsCachedDaoResult();
		boolean reachedBudgetLimit = false;
		if (request.getUserUploadedContents() != null && !request.getUserUploadedContents().isEmpty()) {
			for (UserUploadedContent uploaded : request.getUserUploadedContents()) {
				List<Document> documents = this.storageAreaService.getIngestedContentsOf(uploaded);
				if (documents != null && !documents.isEmpty()) {
					RagDocumentReferenceItem data = new RagDocumentReferenceItem();
					data.setCode(uploaded.getCode());
					data.setContentType(uploaded.getContentType());
					data.setExtension(uploaded.getExtension());
					data.setName(uploaded.getFileName());
					List<RagDocumentFragment> fragments = new ArrayList<>();
					for (Document document : documents) {
						RagDocumentFragment fragment = new RagDocumentFragment(document,
								ExtractedDocumentMetaData.of(document.getMetadata()));
						fragment.recalculateSize();
						if (fragment.getNTokens() <= budget) {
							fragments.add(fragment);
							budget -= fragment.getNTokens();
						} else {
							reachedBudgetLimit = true;
							break;
						}
					}
					data.setFragments(fragments);
					data.recalculateSize();
					if (!fragments.isEmpty()) {
						value.getDocumentItems().add(data);
						value.recalculateSize();
					}
					if (reachedBudgetLimit) {
						break;
					}
				}
			}
		}
		if (userContext.getInteractions() != null && !userContext.getInteractions().isEmpty()) {
			for (int index = userContext.getInteractions().size() - 1; index >= 0; index--) {
				ChatInteractions interaction = userContext.getInteractions().get(index);
				if (interaction.getRequest() != null && interaction.getRequest().getUserUploadedContents() != null
						&& !interaction.getRequest().getUserUploadedContents().isEmpty()) {
					for (UserUploadedContent uploaded : interaction.getRequest().getUserUploadedContents()) {
						List<Document> documents = this.storageAreaService.getIngestedContentsOf(uploaded);
						if (documents != null && !documents.isEmpty()) {
							RagDocumentReferenceItem data = new RagDocumentReferenceItem();
							data.setCode(uploaded.getCode());
							data.setContentType(uploaded.getContentType());
							data.setExtension(uploaded.getExtension());
							data.setName(uploaded.getFileName());
							List<RagDocumentFragment> fragments = new ArrayList<>();
							for (Document document : documents) {
								RagDocumentFragment fragment = new RagDocumentFragment(document,
										ExtractedDocumentMetaData.of(document.getMetadata()));
								fragment.recalculateSize();
								if (fragment.getNTokens() <= budget) {
									fragments.add(fragment);
									budget -= fragment.getNTokens();
								} else {
									reachedBudgetLimit = true;
									break;
								}
							}
							data.setFragments(fragments);
							data.recalculateSize();
							if (!fragments.isEmpty()) {
								value.getDocumentItems().add(data);
								value.recalculateSize();
							}
							if (reachedBudgetLimit) {
								break;
							}
						}
					}
				}
			}
		}
		outValue.setValue(value);
		outValue.setNToken((int) value.getNTokens());
		return outValue;
	}

	private RagDocumentsCachedDaoResult createHistoricContextDocuments(GUserChatContext userContext,
			RagDocumentsCachedDaoResult actualDocuments, int historicDocumentsBudget)
			throws CloneNotSupportedException {
		Map<String, Boolean> alreadyInFragments = new HashMap<>();
		if (actualDocuments != null && actualDocuments.getDocumentItems() != null) {
			List<Document> docs = actualDocuments.aiDocumentsList();
			docs.forEach((d) -> {
				alreadyInFragments.put(d.getId(), true);
			});
		}
		int budget = historicDocumentsBudget;
		RagDocumentsCachedDaoResult delta = new RagDocumentsCachedDaoResult();
		if (userContext.getInteractions() != null) {
			List<ChatInteractions> interactionsList = userContext.getInteractions();
			for (int i = interactionsList.size() - 1; i >= 0; i--) {
				ChatInteractions interaction = interactionsList.get(i);
				budget = this.integrateDeltaContent(delta, interaction, alreadyInFragments, budget);
				if (budget <= 0)
					break;
			}
		}
		return delta;
	}

	private int integrateDeltaContent(RagDocumentsCachedDaoResult delta, ChatInteractions interaction,
			Map<String, Boolean> alreadyInFragments, int budget) throws CloneNotSupportedException {
		int remainingBudget = budget;
		if (interaction.getRequest() != null && interaction.getRequest().getDocuments() != null) {
			RagDocumentsCachedDaoResult current = interaction.getRequest().getDocuments();
			for (RagDocumentReferenceItem doc : current.getDocumentItems()) {
				// shallow clone the docreference to recompose one doc with fragments not yet in
				// the out contents
				RagDocumentReferenceItem outDoc = (RagDocumentReferenceItem) doc.clone();
				// clear its fragments list
				outDoc.setFragments(new ArrayList<>());
				outDoc.recalculateSize();
				for (RagDocumentFragment fragment : doc.getFragments()) {
					// if the fragment is not yet in the list
					if (!alreadyInFragments.containsKey(fragment.getCode())) {

						if (fragment.getNTokens() <= remainingBudget) {
							// if this fragment is coherent with the actual budget
							// i add this to the actual document
							remainingBudget -= fragment.getNTokens();
							outDoc.getFragments().add(fragment);
							outDoc.recalculateSize();
						} else {
							// if it is not then the remaining budget is too low for a single
							// fragment to be included, so there is no reason to continue
							remainingBudget = 0;
						}
						alreadyInFragments.put(fragment.getCode(), true);
					}
					if (remainingBudget <= 0) {
						break;
					}
				}
				if (!outDoc.getFragments().isEmpty()) {
					delta.getDocumentItems().add(outDoc);
					delta.recalculateSize();
				}
				if (remainingBudget <= 0) {
					break;
				}
			}
		}
		return remainingBudget;
	}

	private void mergeGraphRagResults(TokenLimitedContent<RagDocumentsCachedDaoResult> documents,
			List<KnowledgeGraphSearchResult> graphRagResults, int availableTokensForDocuments) {
		final Map<String, List<RagDocumentFragment>> fragments = new HashMap<String, List<RagDocumentFragment>>();
		final Map<String, RagDocumentReferenceItem> alreadyExisting = new HashMap<String, RagDocumentReferenceItem>();
		documents.getValue().getDocumentItems().forEach(x -> {
			alreadyExisting.put(x.getCode(), x);
		});
		graphRagResults.stream().forEach(x -> {
			String documentCode = x.getExtractedDocumentMetaData().getCode();
			if (documentCode == null)
				return;
			if (!fragments.containsKey(documentCode)) {
				fragments.put(documentCode, new ArrayList<RagDocumentFragment>());
			}
			RagDocumentFragment fragment = new RagDocumentFragment(x.getDocument(), x.getExtractedDocumentMetaData());
			fragment.setOrigin(GRAPH_RAG);
			RagDocumentReferenceItem existingDoc = alreadyExisting.get(documentCode);
			boolean fragmentAlreadyFound = existingDoc != null && existingDoc.getFragments().stream()
					.anyMatch(f -> f.getCode() != null && f.getCode().equals(fragment.getCode()));
			if ((!fragmentAlreadyFound)
					&& (documents.getNToken() + fragment.getNTokens()) <= availableTokensForDocuments) {
				if (existingDoc == null) {
					existingDoc = new RagDocumentReferenceItem(x.getExtractedDocumentMetaData());
					alreadyExisting.put(documentCode, existingDoc);
					documents.getValue().getDocumentItems().add(existingDoc);
				}
				existingDoc.getFragments().add(fragment);
			}
			documents.getValue().recalculateSize();

		});

	}

	/**
	 * Serialize an object to a JSON string for logging purposes.
	 *
	 * @param object The object to serialize.
	 * @return The JSON string representation of the object.
	 */
	private String logObject(Object object) {
		try {
			return mapper4logging.writeValueAsString(object);
		} catch (Throwable th) {
			// Log error silently
		}
		return "<cannot serialize object>";
	}

	/**
	 * Shrinks the chat history according to the specified token budget and
	 * strategy.
	 *
	 * @param userContext     The user chat context.
	 * @param tokensBudget    The token budget to fit the history into.
	 * @param historyStrategy The strategy used for shrinking the history.
	 * @param chatHandler     The chat model handler used.
	 * @return The token-limited version of the chat history.
	 */
	private TokenLimitedContent<ChatHistoryData> shrinkHistory(GUserChatContext userContext, int tokensBudget,
			HistoryStrategy historyStrategy, IGConfigurableChatModel chatHandler) {
		if (historyStrategy == null)
			historyStrategy = HistoryStrategy.SHORTENQUEUE;
		TokenLimitedContent<ChatHistoryData> _history = new TokenLimitedContent<ChatHistoryData>();
		int nhistoryTokens = 0;
		ChatHistoryData out = new ChatHistoryData();
		GUserChatConsolidationData consolidated = userContext.getConsolidation();
		out.setConsolidated(consolidated);
		List<ChatInteractions> history = new ArrayList<ChatInteractions>();
		int consolidatedTokensSize = consolidated != null ? consolidated.getTokensSize() : 0;
		int firstInteractionToInclude = consolidated != null ? consolidated.getLastInteractionPointer() : 0;
		if (userContext.getInteractions() != null) {
			for (int i = userContext.getInteractions().size() - 1; i >= firstInteractionToInclude; i--) {
				ChatInteractions interaction = userContext.getInteractions().get(i);
				if (interaction.getRequestNTokens() == null && interaction.getRequest() != null) {
					interaction.setRequestNTokens(tokenEstimator.estimate(interaction.getRequest().getQuery()));
				}

				if (interaction.getResponseNTokens() == null && interaction.getResponse() != null
						&& interaction.getResponse().getQueryResponse() != null) {
					interaction.setResponseNTokens(
							tokenEstimator.estimate(interaction.getResponse().getQueryResponse().toString()));
				}
				if (interaction.getRequestNTokens() != null) {
					nhistoryTokens += interaction.getRequestNTokens();
				}

				if (interaction.getResponseNTokens() != null) {
					nhistoryTokens += interaction.getResponseNTokens();
				}

				history.add(interaction);

			}
		}
		_history.setValue(out);
		_history.setNToken(nhistoryTokens + consolidatedTokensSize);
		return _history;
	}

	/**
	 * Finds the optimization settings for a given context window token count based
	 * on defined range settings.
	 *
	 * @param contextWindowNToken The context window token count.
	 * @return The corresponding ContextWindowLengthRangeSettings for the given
	 *         token count.
	 */
	private ContextWindowLengthRangeSettings findOptimizationSettings(int contextWindowNToken) {
		if (contextWindowNToken <= 0) {
			LOGGER.warn("Context window length is zero!");
			contextWindowNToken = 4192;
		}
		if (this.ragResourcesConfig == null || this.ragResourcesConfig.getRanges().isEmpty())
			throw new RuntimeException("LLMS request resources optimizations configuration not present");
		ContextWindowLengthRangeSettings lastRange = null;
		for (int i = 0; i < this.ragResourcesConfig.getRanges().size(); i++) {
			ContextWindowLengthRangeSettings range = this.ragResourcesConfig.getRanges().get(i);
			if (range.minimumContextWindowLength > contextWindowNToken) {
				return lastRange;
			}
			lastRange = range;
		}
		return lastRange;
	}

	@Override
	public ChatModelLimitedRequest manageChatRequest(GUserChatContext userContext, UserInfos user,
			GeboChatRequest request, IGConfigurableChatModel chatHandler) throws LLMConfigException, IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin manageChatRequest(.....)");
		}
		ChatModelLimitedRequest lrequest = new ChatModelLimitedRequest();
		// considering the context window in Nr of tokens
		int contextWindowNToken = getContextLength(chatHandler);

		// retrieve percentage of allocations settings and optimizations
		ContextWindowLengthRangeSettings settings = findOptimizationSettings(contextWindowNToken);
		lrequest.setContextWindowNToken(contextWindowNToken);
		// check if tooling is enabled
		GBaseChatModelConfig<?> chatModelConfig = (GBaseChatModelConfig<?>) chatHandler.getConfig();
		boolean hasToolingEnabled = chatModelConfig.getEnabledFunctions() != null
				&& !chatModelConfig.getEnabledFunctions().isEmpty();
		// if tooling is enabled calculate a residual (or important) space that will
		// remain free for tools use
		int toolsTokensSpaceReservation = 0;
		if (hasToolingEnabled) {
			toolsTokensSpaceReservation = (int) (((double) contextWindowNToken)
					* (settings.minimumToolAvailablePercent / 100.0));
		}
		// compute query tokens size
		lrequest.setQuery(new TokenLimitedContent<String>());
		lrequest.getQuery().setValue(request.getQuery());
		lrequest.getQuery().setNToken(tokenEstimator.estimate(request.getQuery()));
		int requestSize = lrequest.getQuery().getNToken();
		// adding history history and the
		// available tokens for documents will be already decreased from this allocation
		lrequest.setHistory(completeHistoryTokenEstimationOnlyRequestTextAndResponseTextNTokens(userContext));
		ChatModelRequestContextWindowStats stats = lrequest.getStats();
		int availableTokensForDocuments = 0;

		// compute actual history size
		boolean historyToBeShrinked = false;
		int historySizeTarget = lrequest.getHistory().getNToken();
		if (stats.historySharePerc > settings.historyLimitPercent) {
			// if history is taking more than its maximum potential share than recompute a
			// maximum available tokens for documents theorizing to shring the history to
			// its maximum share
			// but leaving space for tool calling
			historyToBeShrinked = true;
			historySizeTarget = (int) (((double) contextWindowNToken) * settings.historyLimitPercent / 100.0);
			availableTokensForDocuments = contextWindowNToken - historySizeTarget - toolsTokensSpaceReservation
					- requestSize;
		} else {
			// if history space is less than its maximum share compute maximum available
			// tokens for documents by
			// subtracting actual amount of delta from the full context window
			// (availableNTokens) minus the
			// tool calls free space
			availableTokensForDocuments = (int) stats.availableNTokens;
			availableTokensForDocuments -= toolsTokensSpaceReservation;
		}
		boolean preemptiveHistoryShrinked = (stats.historySharePerc + 10.0) > settings.historyLimitPercent;
		int preemptiveHistorySizeTarget = (int) (((double) contextWindowNToken) * settings.historyLimitPercent / 100.0);
		lrequest.setHistoryConsolidationRequired(preemptiveHistoryShrinked);
		lrequest.setHistorySizeTarget(preemptiveHistorySizeTarget);
		// first add explicitly uploaded documents
		lrequest.setUploadedDocuments(
				createLimitedUploadedDocumentsOnRequests(userContext, request, availableTokensForDocuments));
		stats = lrequest.getStats();
		if (stats.availableNTokens < toolsTokensSpaceReservation) {
			// now check real documents size allocations and recalculate
			lrequest.setHistory(shrinkHistory(userContext, historySizeTarget, settings.historyStrategy, chatHandler));
		}
		stats = lrequest.getStats();

		// Put here conditions and optimizations to fix free tokens space
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End manageChatRequest(.....) stats=>" + logObject(stats));
		}
		return lrequest;

	}

}