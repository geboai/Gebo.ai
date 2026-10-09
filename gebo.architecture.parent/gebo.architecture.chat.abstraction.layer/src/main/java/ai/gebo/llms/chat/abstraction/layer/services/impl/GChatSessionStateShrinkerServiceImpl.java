package ai.gebo.llms.chat.abstraction.layer.services.impl;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentFragment;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingAndProvidingService;
import ai.gebo.llms.abstraction.layer.services.ClientChatCallUtil;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.LLMInputDocument;
import ai.gebo.llms.chat.abstraction.layer.config.GeboChatConfigs;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.model.MinimalChatContextCacheItem;
import ai.gebo.llms.chat.abstraction.layer.model.TokensContainer;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatFullSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.MinimalChatContextCacheItemRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.ShrinkedChatSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionStateShrinkerService;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSConsolidatedChatHistory;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSInteractionReferredContent;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSReferredContentList;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSRelevantShrinkedDocument;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSSimplefiedInteraction;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSSimplifiedChatHistory;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSfRelevantShrinkedDocumentList;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatFullSessionState;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.abstraction.layer.session.model.ShrinkedChatSessionState;
import ai.gebo.llms.chat.abstraction.layer.session.model.ShrinkedDocumentOrigin;
import ai.gebo.model.DocumentMetaInfos;
import lombok.Data;

@Service
public class GChatSessionStateShrinkerServiceImpl extends BaseLLMSInvokingAndProvidingService
		implements IGChatSessionStateShrinkerService {

	private final MinimalChatContextCacheItemRepository minimalChatContextCacheItemRepository;
	private static final String NEWLINE = "\r";
	final GeboChatConfigs chatConfig;
	final IGPromptConfigDao promptsDao;
	final ShrinkedChatSessionStateRepository shrinkedStateRepository;
	final ChatFullSessionStateRepository fullStateRepository;
	final MongoOperations mongo;
	private final static JTokkitTokenCountEstimator tokensEstimator = new JTokkitTokenCountEstimator();
	public static final String ASSISTANT_MSG = "assistant:";
	public static final String USER_MSG = "user:";
	public static final String HISTORY_SIZE_TARGET = "historySizeTarget";
	private static Logger LOGGER = LoggerFactory.getLogger(GChatSessionStateShrinkerServiceImpl.class);

	public GChatSessionStateShrinkerServiceImpl(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao, GeboChatConfigs chatConfig,
			IGPromptConfigDao promptsDao, ShrinkedChatSessionStateRepository shrinkedStateRepository,
			ChatFullSessionStateRepository fullStateRepository,
			MinimalChatContextCacheItemRepository minimalChatContextCacheItemRepository, MongoOperations mongo) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
		this.mongo = mongo;

		this.chatConfig = chatConfig;
		this.promptsDao = promptsDao;
		this.shrinkedStateRepository = shrinkedStateRepository;
		this.fullStateRepository = fullStateRepository;
		this.minimalChatContextCacheItemRepository = minimalChatContextCacheItemRepository;

	}

	@Override

	public void shrink(String sessionCode, int tokensBudget) throws LLMConfigException, IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin shrink(" + sessionCode + ")");
		}
		boolean doShrink = false;
		ChatFullSessionState full = null;
		ShrinkedChatSessionState oldVersion = new ShrinkedChatSessionState();
		Optional<ChatFullSessionState> f = this.fullStateRepository.findById(sessionCode);
		Optional<ShrinkedChatSessionState> s = this.shrinkedStateRepository.findById(sessionCode);
		if (f.isPresent()) {
			full = f.get();

			if (s.isPresent()) {
				oldVersion = s.get();

			} else {
				doShrink = true;
			}
			int size = full.getTokensSize();
			int oldShrinkedSize = oldVersion.getTokensSize();
			doShrink = oldShrinkedSize > tokensBudget;
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Chat " + sessionCode + ": full state " + size + " tokens, compact state " + oldShrinkedSize
						+ " tokens, target " + tokensBudget + " tokens: shrink " + (oldShrinkedSize > tokensBudget));
			}
			if (doShrink) {
				LOGGER.info("Shrinked session size = " + oldShrinkedSize + ", so running shrinker");
				ShrinkedChatSessionState out = new ShrinkedChatSessionState();
				out.setUserChatContextCode(sessionCode);
				IGConfigurableChatModel usedChatModel = chatModelsConfigDao
						.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
				if (usedChatModel == null)
					throw new LLMConfigException("No Internal services or default chat model present");
				IChatRequestContext shrinkRequestContext = full
						.createChatRequestResources(LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET)
						.createChatRequestContext();
				out.setChatHistory(consolidateHistory(full.getChatHistory().getValue(), tokensBudget / 4,
						this.chatConfig.getLeaveLastInteractionsOnHistoryConsolidation(),
						oldVersion != null ? oldVersion.getChatHistory() : null, shrinkRequestContext, usedChatModel,
						true));
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Chat {}: history summary of {} tokens covers interactions up to {}, {} kept verbatim",
							sessionCode, out.getChatHistory().getConsolidationTextTokenSize(),
							out.getChatHistory().getLastInteractionPointer(),
							out.getChatHistory().getLatestEntries().getInteractions().size());
				}
				this.minimalChatContextCacheItemRepository.deleteByUserChatContextCode(out.getUserChatContextCode());
				MinimalChatContext minimalChatContext = new MinimalChatContext();
				minimalChatContext.setChatHistory(out.getChatHistory());
				MinimalChatContextCacheItem item = new MinimalChatContextCacheItem();
				item.setItem(minimalChatContext);
				item.setLastRequestId(out.getChatHistory().getLatestEntries() != null
						&& !out.getChatHistory().getLatestEntries().getInteractions().isEmpty()
								? out.getChatHistory().getLatestEntries().getInteractions()
										.get(out.getChatHistory().getLatestEntries().getInteractions().size() - 1)
										.getRequestId()
								: "EMPTY");
				item.setUserChatContextCode(out.getUserChatContextCode());
				item.setTokensBudget(tokensBudget);
				item.recalculateId();
				this.minimalChatContextCacheItemRepository.save(item);
				IGConfigurableChatModel serviceModel = this.chatModelsConfigDao
						.findByUses(ChatModelsUses.INTERNAL_SERVICES);
				if (serviceModel != null) {
					int serviceBudget = IGChatSessionStateShrinkerService.serviceModelContextBudget(serviceModel);
					if (serviceBudget < tokensBudget) {
						MinimalChatContext newValue = this.shrinkedMinimalContext(out.getUserChatContextCode(),
								minimalChatContext, serviceBudget);
						item = new MinimalChatContextCacheItem();
						item.setItem(newValue);
						item.setLastRequestId(out.getChatHistory().getLatestEntries() != null
								&& !out.getChatHistory().getLatestEntries().getInteractions().isEmpty()
										? out.getChatHistory().getLatestEntries().getInteractions()
												.get(out.getChatHistory().getLatestEntries().getInteractions().size()
														- 1)
												.getRequestId()
										: "EMPTY");
						item.setUserChatContextCode(out.getUserChatContextCode());
						item.setTokensBudget(serviceBudget);
						item.recalculateId();
						this.minimalChatContextCacheItemRepository.save(item);
					}
				}
				out.setRelevantChatWithDocuments(shrinkDocumentList(untillLatest(full, full.getChatWithDocuments()),
						out.getChatHistory(), oldVersion.getRelevantChatWithDocuments(), tokensBudget, usedChatModel,
						ShrinkedDocumentOrigin.CHAT_WITH_SELECTED));
				out.setRelevantRetrievedDocuments(shrinkDocumentList(untillLatest(full, full.getRetrievedDocuments()),
						out.getChatHistory(), oldVersion.getRelevantRetrievedDocuments(), tokensBudget, usedChatModel,
						ShrinkedDocumentOrigin.RETRIEVED));
				out.setRelevantLlmGeneratedDocuments(
						shrinkDocumentList(untillLatest(full, full.getLlmGeneratedDocuments()), out.getChatHistory(),
								oldVersion.getRelevantLlmGeneratedDocuments(), tokensBudget, usedChatModel,
								ShrinkedDocumentOrigin.GENERATED));
				out.setRelevantUploadedDocuments(shrinkDocumentList(untillLatest(full, full.getUploadedDocuments()),
						out.getChatHistory(), oldVersion.getRelevantUploadedDocuments(), tokensBudget, usedChatModel,
						ShrinkedDocumentOrigin.UPLOADED));
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(
							"Chat {}: summarized documents of older interactions chat-with:{} retrieved:{} generated:{} uploaded:{}",
							sessionCode, out.getRelevantChatWithDocuments().size(),
							out.getRelevantRetrievedDocuments().size(), out.getRelevantLlmGeneratedDocuments().size(),
							out.getRelevantUploadedDocuments().size());
				}
				int tokensSize = out.getTokensSize();
				CSSReferredContentList latestRequestsChatWithDocuments = afterLatest(full, full.getChatWithDocuments());
				if (tokensBudget < (tokensSize + latestRequestsChatWithDocuments.getTokensSize())) {
					TokensContainer<CSSReferredContentList> lrcwd = new TokensContainer<CSSReferredContentList>(
							latestRequestsChatWithDocuments, latestRequestsChatWithDocuments.getTokensSize());
					CSSfRelevantShrinkedDocumentList shrinkedDocs = shrinkDocumentList(lrcwd, out.getChatHistory(),
							new CSSfRelevantShrinkedDocumentList(), tokensBudget, usedChatModel,
							ShrinkedDocumentOrigin.CHAT_WITH_SELECTED);
					out.getRelevantChatWithDocuments().addAll(shrinkedDocs);
				} else {
					out.setLatestRequestsChatWithDocuments(latestRequestsChatWithDocuments);
				}
				tokensSize = out.getTokensSize();
				CSSReferredContentList latestRequestsLlmGeneratedDocuments = afterLatest(full,
						full.getLlmGeneratedDocuments());
				if (tokensBudget < (tokensSize + latestRequestsLlmGeneratedDocuments.getTokensSize())) {
					TokensContainer<CSSReferredContentList> lrcwd = new TokensContainer<CSSReferredContentList>(
							latestRequestsLlmGeneratedDocuments, latestRequestsLlmGeneratedDocuments.getTokensSize());
					CSSfRelevantShrinkedDocumentList shrinkedDocs = shrinkDocumentList(lrcwd, out.getChatHistory(),
							new CSSfRelevantShrinkedDocumentList(), tokensBudget, usedChatModel,
							ShrinkedDocumentOrigin.GENERATED);
					out.getRelevantLlmGeneratedDocuments().addAll(shrinkedDocs);
				} else {
					out.setLatestRequestsLlmGeneratedDocuments(latestRequestsLlmGeneratedDocuments);
				}
				tokensSize = out.getTokensSize();
				CSSReferredContentList latestRequestsRetrievedDocuments = afterLatest(full,
						full.getRetrievedDocuments());
				if (tokensBudget < (tokensSize + latestRequestsRetrievedDocuments.getTokensSize())) {
					TokensContainer<CSSReferredContentList> lrcwd = new TokensContainer<CSSReferredContentList>(
							latestRequestsRetrievedDocuments, latestRequestsRetrievedDocuments.getTokensSize());
					CSSfRelevantShrinkedDocumentList shrinkedDocs = shrinkDocumentList(lrcwd, out.getChatHistory(),
							new CSSfRelevantShrinkedDocumentList(), tokensBudget, usedChatModel,
							ShrinkedDocumentOrigin.RETRIEVED);
					out.getRelevantRetrievedDocuments().addAll(shrinkedDocs);
				} else {
					out.setLatestRequestsRetrievedDocuments(latestRequestsRetrievedDocuments);
				}
				tokensSize = out.getTokensSize();
				CSSReferredContentList latestRequestsUploadedDocuments = afterLatest(full, full.getUploadedDocuments());
				if (tokensBudget < (tokensSize + latestRequestsUploadedDocuments.getTokensSize())) {
					TokensContainer<CSSReferredContentList> lrcwd = new TokensContainer<CSSReferredContentList>(
							latestRequestsUploadedDocuments, latestRequestsUploadedDocuments.getTokensSize());
					CSSfRelevantShrinkedDocumentList shrinkedDocs = shrinkDocumentList(lrcwd, out.getChatHistory(),
							new CSSfRelevantShrinkedDocumentList(), tokensBudget, usedChatModel,
							ShrinkedDocumentOrigin.UPLOADED);
					out.getRelevantUploadedDocuments().addAll(shrinkedDocs);
				} else {
					out.setLatestRequestsUploadedDocuments(latestRequestsUploadedDocuments);
				}

				int afterSize = out.getTokensSize();
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(
							"Chat {}: latest interactions' documents kept whole chat-with:{} retrieved:{} generated:{} uploaded:{}",
							sessionCode, out.getLatestRequestsChatWithDocuments().getData().size(),
							out.getLatestRequestsRetrievedDocuments().getData().size(),
							out.getLatestRequestsLlmGeneratedDocuments().getData().size(),
							out.getLatestRequestsUploadedDocuments().getData().size());
				}
				LOGGER.info("Shrinked to:" + afterSize + " tokens");
				// A request ending meanwhile saved a newer state: keep it, it stays flagged for a later shrink.
				long startedFrom = oldVersion.getRevision();
				out.setRevision(startedFrom + 1);
				Criteria sameRevision = startedFrom == 0
						? new Criteria().orOperator(Criteria.where("revision").is(0L),
								Criteria.where("revision").exists(false))
						: Criteria.where("revision").is(startedFrom);
				ShrinkedChatSessionState replaced = mongo.findAndReplace(
						Query.query(Criteria.where("_id").is(sessionCode)).addCriteria(sameRevision), out);
				if (replaced == null) {
					LOGGER.info("Chat " + sessionCode + " changed while it was being shrunk (revision " + startedFrom
							+ "): this shrink is discarded, the next completed request queues a new one");
				}
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End shrink(" + sessionCode + ")");
		}

	}

	private CSSSimplifiedChatHistory copyLatest(CSSSimplifiedChatHistory value,
			int leaveLastInteractionsOnHistoryConsolidation) {
		int lastIndex = value.getInteractions().size() - leaveLastInteractionsOnHistoryConsolidation;
		lastIndex = Math.max(lastIndex, 0);
		CSSSimplifiedChatHistory newHistory = new CSSSimplifiedChatHistory();
		for (int i = lastIndex; i < value.getInteractions().size(); i++) {
			CSSSimplefiedInteraction entry = value.getInteractions().get(i);
			entry = (CSSSimplefiedInteraction) entry.clone();
			entry.setInteractionIndex(i);
			newHistory.getInteractions().add(entry);
		}
		return newHistory;
	}

	private CSSReferredContentList afterLatest(ChatFullSessionState fullSessionState,
			TokensContainer<? extends CSSReferredContentList> data) {
		int leaveLastInteractionsOnHistoryConsolidation = this.chatConfig
				.getLeaveLastInteractionsOnHistoryConsolidation();
		int lastIndex = fullSessionState.getChatHistory().getValue().getInteractions().size()
				- leaveLastInteractionsOnHistoryConsolidation;
		CSSReferredContentList in = data.getValue();
		final CSSReferredContentList<?> bag = new CSSReferredContentList();
		for (int index = 0; index < in.getData().size(); index++) {
			CSSInteractionReferredContent entry = in.getData().get(index);
			if (entry.getInteractionIndex() >= lastIndex) {
				bag.getData().add(entry);
			}
		}
		return bag;
	}

	private TokensContainer<? extends CSSReferredContentList> untillLatest(ChatFullSessionState fullSessionState,
			TokensContainer<? extends CSSReferredContentList> data) {
		int leaveLastInteractionsOnHistoryConsolidation = this.chatConfig
				.getLeaveLastInteractionsOnHistoryConsolidation();
		int lastIndex = fullSessionState.getChatHistory().getValue().getInteractions().size()
				- leaveLastInteractionsOnHistoryConsolidation;
		CSSReferredContentList in = data.getValue();
		final CSSReferredContentList bag = new CSSReferredContentList();
		for (int index = 0; index < in.getData().size(); index++) {
			CSSInteractionReferredContent entry = in.getData().get(index);
			if (entry.getInteractionIndex() < lastIndex) {
				bag.getData().add(entry);
			}
		}
		TokensContainer<CSSReferredContentList> _out = new TokensContainer<CSSReferredContentList>();
		_out.setValue(bag);
		return _out;
	}

	@Data
	public static class CSSData {
		private CSSfRelevantShrinkedDocumentList data = new CSSfRelevantShrinkedDocumentList();
	}

	private CSSfRelevantShrinkedDocumentList shrinkDocumentList(TokensContainer<? extends CSSReferredContentList> docs,
			CSSConsolidatedChatHistory consolidated, CSSfRelevantShrinkedDocumentList alreadyElaborated,
			int tokensBudget, IGConfigurableChatModel usedChatModel, ShrinkedDocumentOrigin origin)
			throws LLMConfigException, IOException {
		CSSfRelevantShrinkedDocumentList outList = new CSSfRelevantShrinkedDocumentList();

		if (docs != null && docs.getValue() != null && !docs.getValue().getData().isEmpty()) {
			Map<String, AIDocumentReferenceItem> refsMap = new HashMap<String, AIDocumentReferenceItem>();
			StringBuffer lastTurns = new StringBuffer();

			for (int i = consolidated.getLatestEntries().getInteractions().size() - 1; i >= 0; i--) {
				CSSSimplefiedInteraction interaction = consolidated.getLatestEntries().getInteractions().get(i);
				if (interaction.getUser() != null && interaction.getUser().trim().length() > 0) {
					lastTurns.append(USER_MSG);
					lastTurns.append(interaction.getUser());
					lastTurns.append(NEWLINE);
				}
				if (interaction.getAssistant() != null && interaction.getAssistant().trim().length() > 0) {
					lastTurns.append(ASSISTANT_MSG);
					lastTurns.append(interaction.getAssistant());
					lastTurns.append(NEWLINE);
				}
			}
			GPromptTemplateConfig _prompt = promptsDao
					.findByPromptUse(GeboPromptsLibrary.CHAT_HISTORY_DOCUMENTS_CONSOLIDATION);

			String question = lastTurns.toString();
			String pastConsolidation = consolidated != null ? consolidated.getConsolidationText() : "";

			for (int i = 0; i < docs.getValue().getData().size(); i++) {
				CSSInteractionReferredContent content = docs.getValue().getData().get(i);
				Optional<CSSRelevantShrinkedDocument> matching = alreadyElaborated.stream().filter(x -> {
					boolean isSameDoc = (content.getAiDocument() != null && content.getAiDocument().getCode() != null
							&& content.getAiDocument().getCode().equals(x.getDocumentReference())
							&& x.getInteractionIndex() == content.getInteractionIndex()
							&& x.getDocumentOrigin() == origin);

					return isSameDoc;
				}).findFirst();
				if (matching.isPresent()) {
					outList.add(matching.get());
					if (LOGGER.isTraceEnabled()) {
						LOGGER.trace("{} document {} of interaction {}: reusing its summary", origin,
								content.getAiDocument().getCode(), content.getInteractionIndex());
					}
				} else {
					List<LLMInputDocument> toBeConsolidated = new ArrayList<LLMInputDocument>();
					if (content.getAiDocument().getFragments().isEmpty())
						continue;
					refsMap.put(content.getAiDocument().getCode(), content.getAiDocument());
					StringBuffer buffer = new StringBuffer();
					for (AIDocumentFragment fragment : content.getAiDocument().getFragments()) {
						buffer.append(fragment.getDocumentContent());
					}
					if (!buffer.isEmpty()) {
						LLMInputDocument input = new LLMInputDocument(content.getAiDocument().getCode(),
								content.getAiDocument().getOriginalUrl(), (String) content.getAiDocument()
										.getFragments().get(0).getMetaData().get(DocumentMetaInfos.TITLE),
								buffer.toString());
						toBeConsolidated.add(input);
						if (!toBeConsolidated.isEmpty()) {
							CSSRelevantShrinkedDocument out = new CSSRelevantShrinkedDocument();
							out.setId(null);
							out.setDocumentName(content.getAiDocument().getName());
							out.setDocumentReference(content.getAiDocument().getCode());
							out.setDocumentUrl(content.getAiDocument().getOriginalUrl());
							out.setDocumentOrigin(origin);
							out.setInteractionIndex(content.getInteractionIndex());
							out.setMetaData(new HashMap<String, Object>(
									content.getAiDocument().getFragments().get(0).getMetaData()));
							String text = callLLMConsolidateText(usedChatModel, _prompt,
									IChatRequestContext.of(question), pastConsolidation, null, toBeConsolidated);
							if (usedChatModel.isApplyThinkingMarkupHandling() && text != null) {
								text = ClientChatCallUtil.removeThinking(text);
							}
							if (text != null && text.trim().length() > 0) {
								out.setSummarizedContent(text);
								outList.add(out);
							}
							if (LOGGER.isTraceEnabled()) {
								LOGGER.trace("{} document {} of interaction {}: summarized {} characters into {}", origin,
										content.getAiDocument().getCode(), content.getInteractionIndex(),
										buffer.length(), text != null ? text.length() : 0);
							}

						}
					}
				}
			}

			for (CSSRelevantShrinkedDocument cssRelevantShrinkedDocument : outList) {
				if (cssRelevantShrinkedDocument.getId() == null) {
					cssRelevantShrinkedDocument.setId(UUID.randomUUID().toString());
					cssRelevantShrinkedDocument.setTokensSize(
							tokensEstimator.estimate(cssRelevantShrinkedDocument.getSummarizedContent()));
					if (cssRelevantShrinkedDocument.getDocumentReference() != null) {
						AIDocumentReferenceItem doc = refsMap.get(cssRelevantShrinkedDocument.getDocumentReference());
						if (doc != null) {
							AIDocumentFragment fragment = doc.getFragments().get(0);
							cssRelevantShrinkedDocument
									.setMetaData(new HashMap<String, Object>(fragment.getMetaData()));
							cssRelevantShrinkedDocument.getMetaData().remove(DocumentMetaInfos.CONTENT_PAGE);
						}
					}
				}
			}
		}
		return outList;
	}

	// incremental: oldVersion summarizes value's interactions before its pointer, so only the later
	// ones are sent with it; otherwise every interaction of value is new to oldVersion's summary.
	private CSSConsolidatedChatHistory consolidateHistory(CSSSimplifiedChatHistory value, int historySizeTarget,
			int leaveLastInteractionsOnHistoryConsolidation, CSSConsolidatedChatHistory oldVersion,
			IChatRequestContext context, IGConfigurableChatModel usedChatModel, boolean incremental)
			throws LLMConfigException {
		List<LLMInputDocument> inputs = new ArrayList<LLMInputDocument>();
		GPromptTemplateConfig _prompt = promptsDao.findByPromptUse(GeboPromptsLibrary.HISTORY_CONSOLIDATION_PROMPT);

		String existingSummary = oldVersion != null && oldVersion.getConsolidationText() != null
				? oldVersion.getConsolidationText()
				: "";
		if (existingSummary == null)
			existingSummary = "";

		int lastIndex = value.getInteractions().size() - leaveLastInteractionsOnHistoryConsolidation;
		int firstIndex = 0;
		if (incremental) {
			Integer pointer = oldVersion != null ? oldVersion.getLastInteractionPointer() : null;
			if (!existingSummary.isEmpty() && pointer != null && pointer >= 0 && pointer <= lastIndex) {
				firstIndex = pointer;
			} else {
				existingSummary = "";
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(
					"Consolidating history of {} interactions: summarizing {} to {} ({}), previous summary {} tokens, keeping {} verbatim, target {} tokens",
					value.getInteractions().size(), firstIndex, lastIndex - 1,
					incremental ? "incremental" : "whole", existingSummary.isEmpty() ? 0 : tokensEstimator.estimate(existingSummary),
					leaveLastInteractionsOnHistoryConsolidation, historySizeTarget);
		}
		CSSConsolidatedChatHistory newConsolidation = new CSSConsolidatedChatHistory();
		newConsolidation.setLatestEntries(copyLatest(value, leaveLastInteractionsOnHistoryConsolidation));
		if (lastIndex > firstIndex) {
			for (int i = firstIndex; i < lastIndex; i++) {
				StringBuffer new_messages = new StringBuffer();
				CSSSimplefiedInteraction interaction = value.getInteractions().get(i);
				if (interaction.getUser() != null) {
					new_messages.append(USER_MSG);
					new_messages.append(interaction.getUser());
					new_messages.append(NEWLINE);
				}
				if (interaction.getAssistant() != null) {
					new_messages.append(ASSISTANT_MSG);
					new_messages.append(interaction.getAssistant());
					new_messages.append(NEWLINE);
				}
				LLMInputDocument input = new LLMInputDocument(null, null, null, new_messages.toString());
				inputs.add(input);
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(HISTORY_SIZE_TARGET, "" + historySizeTarget);
			// The messages to summarize travel as inputs only: the caller's context would add the whole
			// history again as chat messages.
			IChatRequestContext bare = IChatRequestContext.builder().sessionID(context.getSessionID())
					.actualUserRequest("").interactions(List.of()).build();
			String consolidated = callLLMConsolidateText(usedChatModel, _prompt, bare, existingSummary, params,
					inputs);

			newConsolidation.setConsolidationText(consolidated);
			newConsolidation.setLastInteractionPointer(lastIndex);
			int tokens = (tokensEstimator.estimate(newConsolidation.getConsolidationText()));
			newConsolidation.setConsolidationTextTokenSize(tokens);
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("New history summary ({} tokens): {}", tokens, consolidated);
			}
		} else if (!existingSummary.isEmpty()) {
			LOGGER.debug("Nothing new to summarize: the previous summary is kept");
			newConsolidation.setConsolidationText(oldVersion.getConsolidationText());
			newConsolidation.setConsolidationTextTokenSize(oldVersion.getConsolidationTextTokenSize());
			newConsolidation.setLastInteractionPointer(oldVersion.getLastInteractionPointer());
		}
		return newConsolidation;
	}

	@Override
	public MinimalChatContext shrinkedMinimalContext(String sessionCode, MinimalChatContext mc, int tokensBudget)
			throws LLMConfigException, IOException {
		if (!mc.getChatHistory().getLatestEntries().getInteractions().isEmpty()) {
			String lastInteractionId = mc.getChatHistory().getLatestEntries().getInteractions()
					.get(mc.getChatHistory().getLatestEntries().getInteractions().size() - 1).getRequestId();
			List<MinimalChatContextCacheItem> items = this.minimalChatContextCacheItemRepository
					.findByUserChatContextCodeAndLastRequestIdAndTokensBudgetLessThanEqual(sessionCode,
							lastInteractionId, (Integer) tokensBudget);
			LOGGER.debug("Minimal context of chat {} for {} tokens: {} cached", sessionCode, tokensBudget,
					items.isEmpty() ? "not" : "found");
			if (!items.isEmpty()) {
				MinimalChatContextCacheItem item = items.get(0);
				MinimalChatContext entry = item.getItem();
				entry.setCurrentRequest(mc.getCurrentRequest());
				return entry;
			}
		}
		MinimalChatContext out = this.doShrinking(sessionCode, mc, tokensBudget);
		return out;
	}

	@Override
	public void prepareMinimalContext(String sessionCode, int tokensBudget) throws LLMConfigException, IOException {
		final Optional<ShrinkedChatSessionState> shrinked = this.shrinkedStateRepository.findById(sessionCode);
		if (shrinked.isEmpty() || shrinked.get().getChatHistory() == null) {
			return;
		}
		final MinimalChatContext mc = new MinimalChatContext();
		mc.setChatHistory(shrinked.get().getChatHistory());
		if (mc.getTokensSize() <= tokensBudget) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Minimal context of chat {} fits {} tokens: none prepared", sessionCode, tokensBudget);
			}
			return;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Preparing the minimal context of chat {} for {} tokens", sessionCode, tokensBudget);
		}
		shrinkedMinimalContext(sessionCode, mc, tokensBudget);
	}

	/**
	 * The chat's history in the budget: the latest exchanges verbatim (up to the ones the
	 * chat's own consolidation keeps, as many as fit three quarters of the budget) and a
	 * summary of the others in the remaining quarter, as the chat's own consolidation
	 * does. The summary built for the chat's previous request, when it still matches the
	 * history, is carried forward: only the exchanges it does not cover are summarized.
	 */
	private MinimalChatContext doShrinking(String sessionCode, MinimalChatContext mc, int tokensBudget)
			throws LLMConfigException {

		IGConfigurableChatModel serviceModel = this.chatModelsConfigDao
				.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
		final CSSSimplifiedChatHistory entries = mc.getChatHistory().getLatestEntries();
		final int summaryTarget = tokensBudget / 4;
		final int verbatim = verbatimInteractions(entries,
				this.chatConfig.getLeaveLastInteractionsOnHistoryConsolidation(), tokensBudget - summaryTarget);
		final CSSConsolidatedChatHistory previous = previousMinimalSummary(sessionCode, entries, tokensBudget);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Minimal context of chat {} for {} tokens: {} exchange(s) verbatim, previous summary {}",
					sessionCode, tokensBudget, verbatim,
					previous != null ? "covering " + previous.getLastInteractionPointer() + " exchange(s)" : "none");
		}
		CSSConsolidatedChatHistory consolidated = previous != null
				? this.consolidateHistory(entries, summaryTarget, verbatim, previous, mc.createChatRequestContext(),
						serviceModel, true)
				: this.consolidateHistory(entries, summaryTarget, verbatim, mc.getChatHistory(),
						mc.createChatRequestContext(), serviceModel, false);
		MinimalChatContext newMinimized = new MinimalChatContext();
		newMinimized.setChatHistory(consolidated);
		MinimalChatContextCacheItem item = new MinimalChatContextCacheItem();
		item.setItem(newMinimized);
		List<CSSSimplefiedInteraction> latest = entries.getInteractions();
		item.setLastRequestId(!latest.isEmpty() ? latest.get(latest.size() - 1).getRequestId() : "EMPTY");
		item.setUserChatContextCode(sessionCode);
		item.setTokensBudget(tokensBudget);
		item.recalculateId();
		this.minimalChatContextCacheItemRepository.save(item);
		removeOlderMinimalContexts(sessionCode, tokensBudget, item.getId());
		return newMinimized;
	}

	/**
	 * How many of the latest exchanges stay verbatim: at most {@code maxVerbatim}, as many
	 * as fit {@code room} tokens.
	 */
	static int verbatimInteractions(CSSSimplifiedChatHistory entries, int maxVerbatim, int room) {
		final List<CSSSimplefiedInteraction> interactions = entries.getInteractions();
		int count = 0;
		int size = 0;
		for (int i = interactions.size() - 1; i >= 0 && count < maxVerbatim; i--) {
			size += interactions.get(i).getTokensSize();
			if (size > room) {
				break;
			}
			count++;
		}
		return count;
	}

	/**
	 * The summary the chat's previous minimal context for this budget made, when it still
	 * summarizes the first exchanges of the history: the exchange following it (its first
	 * verbatim one, or the one its request ended) is the same request. Null when none
	 * matches: the history is summarized from its start.
	 */
	private CSSConsolidatedChatHistory previousMinimalSummary(String sessionCode, CSSSimplifiedChatHistory entries,
			int tokensBudget) {
		final List<CSSSimplefiedInteraction> interactions = entries.getInteractions();
		CSSConsolidatedChatHistory best = null;
		for (MinimalChatContextCacheItem item : this.minimalChatContextCacheItemRepository
				.findByUserChatContextCode(sessionCode)) {
			if (!Objects.equals(item.getTokensBudget(), tokensBudget) || item.getItem() == null
					|| item.getItem().getChatHistory() == null) {
				continue;
			}
			final CSSConsolidatedChatHistory history = item.getItem().getChatHistory();
			final Integer pointer = history.getLastInteractionPointer();
			if (history.getConsolidationText() == null || history.getConsolidationText().isEmpty() || pointer == null
					|| pointer <= 0 || pointer > interactions.size()) {
				continue;
			}
			final List<CSSSimplefiedInteraction> verbatim = history.getLatestEntries() != null
					? history.getLatestEntries().getInteractions()
					: List.of();
			final boolean matches = !verbatim.isEmpty() && pointer < interactions.size()
					? Objects.equals(verbatim.get(0).getRequestId(), interactions.get(pointer).getRequestId())
					: Objects.equals(item.getLastRequestId(), interactions.get(pointer - 1).getRequestId());
			if (matches && (best == null || pointer > best.getLastInteractionPointer())) {
				best = history;
			}
		}
		return best;
	}

	/** Removes the chat's minimal contexts for the budget older than the given one. */
	private void removeOlderMinimalContexts(String sessionCode, int tokensBudget, String keptId) {
		int removed = 0;
		for (MinimalChatContextCacheItem item : this.minimalChatContextCacheItemRepository
				.findByUserChatContextCode(sessionCode)) {
			if (Objects.equals(item.getTokensBudget(), tokensBudget) && !Objects.equals(item.getId(), keptId)) {
				this.minimalChatContextCacheItemRepository.delete(item);
				removed++;
			}
		}
		if (removed > 0 && LOGGER.isDebugEnabled()) {
			LOGGER.debug("Removed {} older minimal context(s) of chat {} for {} tokens", removed, sessionCode,
					tokensBudget);
		}
	}

}