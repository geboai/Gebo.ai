/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
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
import ai.gebo.llms.chat.abstraction.layer.model.ChatModelLimitedRequest;
import ai.gebo.llms.chat.abstraction.layer.model.ChatModelRequestContextWindowStats;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.TokenLimitedContent;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatRequestResourcesUsePolicy;
import ai.gebo.llms.models.metainfos.IGModelsLibraryDao;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
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
	protected TokenLimitedContent<List<ChatInteractions>> completeHistoryTokenEstimationAndComputeTotalNTokens(
			GUserChatContext userContext) {
		TokenLimitedContent<List<ChatInteractions>> _history = new TokenLimitedContent<List<ChatInteractions>>();
		int nhistoryTokens = 0;
		List<ChatInteractions> history = new ArrayList<ChatInteractions>();
		if (userContext.getInteractions() != null) {
			for (ChatInteractions interaction : userContext.getInteractions()) {
				if (interaction.requestNTokens == null && interaction.request != null) {
					interaction.requestNTokens = tokenEstimator.estimate(interaction.request.getQuery());
				}

				if (interaction.responseNTokens == null && interaction.response != null
						&& interaction.response.getQueryResponse() != null) {
					interaction.responseNTokens = tokenEstimator
							.estimate(interaction.response.getQueryResponse().toString());
				}
				if (interaction.requestNTokens != null) {
					nhistoryTokens += interaction.requestNTokens;
				}

				if (interaction.responseNTokens != null) {
					nhistoryTokens += interaction.responseNTokens;
				}
				if (interaction.response.getQueryResponse() != null) {
					history.add(interaction);
				}
			}
		}
		_history.setValue(history);
		_history.setNToken(nhistoryTokens);
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
	 */
	@Override
	public ChatModelLimitedRequest manageRequest(GChatProfileConfiguration chatProfile, GUserChatContext userContext,
			UserInfos user, GeboChatRequest request, IGConfigurableEmbeddingModel embeddingHandler,
			IGConfigurableChatModel chatHandler, List<String> visibleKnowledgeBaseCodes) throws LLMConfigException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin manageRequest(.....)");
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
		lrequest.setHistory(completeHistoryTokenEstimationAndComputeTotalNTokens(userContext));
		ChatModelRequestContextWindowStats stats = lrequest.getStats();
		int availableTokensForDocuments = 0;
		boolean historyToBeShrinked = false;
		// compute actual history size
		int historySizeTarget = lrequest.getHistory().getNToken();
		if (stats.historySharePerc > settings.historyLimitPercent) {
			// if history is taking more than its maximum potential share than recompute a
			// maximum available tokens for documents theorizing to shring the history to
			// its maximum share
			// but leaving space for tool calling
			historyToBeShrinked = true;
			historySizeTarget = (int) (((double) contextWindowNToken) * stats.historySharePerc / 100.0);
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

		RagDocumentsCachedDaoResult extractedDocuments = null;
		RagQueryOptions ragQueryOptions = null;
		ragQueryOptions = new RagQueryOptions();
		ragQueryOptions.setMaxTokens(availableTokensForDocuments);
		ragQueryOptions.setSimilarityThreashold(this.ragResourcesConfig.getDefaultSimilarityThreshold());
		if (chatProfile.getSimilaritySearchThreshold() != null) {
			if (chatProfile.getSimilaritySearchThreshold() > 0.0 && chatProfile.getSimilaritySearchThreshold() < 1.00) {
				ragQueryOptions.setSimilarityThreashold(chatProfile.getSimilaritySearchThreshold());
			} else {
				LOGGER.warn("Wrong chat profile similaritySearchThreshold=>"
						+ chatProfile.getSimilaritySearchThreshold() + " for code=" + chatProfile.getCode());
			}
		}
		if (request.getForcedRequestDocuments() != null && !request.getForcedRequestDocuments().isEmpty()) {
			// CHAT WITH DOCUMENTS OPTIONS

			ragQueryOptions.setCompleteness(CompletenessLevel.FULL_DOCUMENTS);
			extractedDocuments = ragDocumentsCachedDao.chatWithDocumentsSearch(request.getQuery(), ragQueryOptions,
					request.getForcedRequestDocuments(), visibleKnowledgeBaseCodes, embeddingHandler, user);
		} else {
			int topK = chatProfile.getTopK() != null ? chatProfile.getTopK() : ragResourcesConfig.getDefaultTopK();
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
		lrequest.setDocuments(new TokenLimitedContent<RagDocumentsCachedDaoResult>());
		lrequest.getDocuments().setValue(extractedDocuments);
		lrequest.getDocuments().setNToken((int) extractedDocuments.getNTokens());
		stats = lrequest.getStats();
		if (stats.availableNTokens < toolsTokensSpaceReservation) {
			// now check real documents size allocations and recalculate
			lrequest.setHistory(shrinkHistory(userContext, historySizeTarget, settings.historyStrategy, chatHandler));
		}
		stats = lrequest.getStats();

		// Put here conditions and optimizations to fix free tokens space
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End manageRequest(.....) stats=>" + logObject(stats));
		}
		return lrequest;
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
	private TokenLimitedContent<List<ChatInteractions>> shrinkHistory(GUserChatContext userContext, int tokensBudget,
			HistoryStrategy historyStrategy, IGConfigurableChatModel chatHandler) {
		if (historyStrategy == null)
			historyStrategy = HistoryStrategy.SHORTENQUEUE;
		TokenLimitedContent<List<ChatInteractions>> out = new TokenLimitedContent<List<ChatInteractions>>();
		List<ChatInteractions> documents = new ArrayList<ChatInteractions>();
		out.setValue(documents);

		// going further from last to before data until lower than tokens budget
		if (userContext.getInteractions() != null) {
			switch (historyStrategy) {
			case SHORTENQUEUE: {
				int tokensCount = 0;
				boolean goingFurther = true;
				for (int i = userContext.getInteractions().size() - 1; i >= 0 && goingFurther; i--) {
					ChatInteractions interaction = userContext.getInteractions().get(i);
					int reqRespSize = (interaction.requestNTokens != null ? interaction.requestNTokens : 0)
							+ (interaction.responseNTokens != null ? interaction.responseNTokens : 0);
					if ((tokensCount + reqRespSize) <= tokensBudget) {
						tokensCount += reqRespSize;
						documents.add(interaction);
					} else {
						goingFurther = false;
					}
				}
				out.setNToken(tokensCount);
			}
				break;
			case SUMMARIZE: {
				throw new RuntimeException("Summarization strategy not yet implemented");
			}
			}
		}
		return out;
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
}