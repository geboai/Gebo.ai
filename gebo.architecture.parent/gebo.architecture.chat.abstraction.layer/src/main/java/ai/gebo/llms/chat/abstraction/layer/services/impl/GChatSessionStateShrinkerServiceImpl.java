package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentFragment;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingAndProvidingService;
import ai.gebo.llms.abstraction.layer.services.ClientChatCallUtil;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboChatConfigs;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import ai.gebo.llms.chat.abstraction.layer.model.TokensContainer;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatFullSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.ShrinkedChatSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionStateShrinkerService;
import ai.gebo.llms.chat.abstraction.layer.services.IGPromptConfigDao;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSInteractionReferredContent;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSReferredContentList;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSRelevantShrinkedDocument;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSSimplefiedInteraction;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSSimplifiedChatHistory;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSfRelevantShrinkedDocumentList;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatFullSessionState;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatInteractionsConsolidationData;
import ai.gebo.llms.chat.abstraction.layer.session.model.ShrinkedChatSessionState;
import ai.gebo.llms.chat.abstraction.layer.session.model.ShrinkedDocumentOrigin;
import ai.gebo.model.DocumentMetaInfos;
import lombok.Data;

@Service
public class GChatSessionStateShrinkerServiceImpl extends BaseLLMSInvokingAndProvidingService
		implements IGChatSessionStateShrinkerService {
	private static final String NEWLINE = "\r";
	final GeboChatConfigs chatConfig;
	final IGPromptConfigDao promptsDao;
	final ShrinkedChatSessionStateRepository shrinkedStateRepository;
	final ChatFullSessionStateRepository fullStateRepository;
	private final static JTokkitTokenCountEstimator tokensEstimator = new JTokkitTokenCountEstimator();
	public static final String ASSISTANT_MSG = "assistant:";
	public static final String USER_MSG = "user:";
	public static final String HISTORY_SIZE_TARGET = "historySizeTarget";
	private static Logger LOGGER = LoggerFactory.getLogger(GChatSessionStateShrinkerServiceImpl.class);

	public GChatSessionStateShrinkerServiceImpl(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao, GeboChatConfigs chatConfig,
			IGPromptConfigDao promptsDao, ShrinkedChatSessionStateRepository shrinkedStateRepository,
			ChatFullSessionStateRepository fullStateRepository) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);

		this.chatConfig = chatConfig;
		this.promptsDao = promptsDao;
		this.shrinkedStateRepository = shrinkedStateRepository;
		this.fullStateRepository = fullStateRepository;

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
				LOGGER.debug("Full session size=" + size + " shrinked=" + oldShrinkedSize + " tokens");
			}
			if (doShrink) {
				LOGGER.info("Shrinked session size = " + oldShrinkedSize + ", so running shrinker");
				ShrinkedChatSessionState out = new ShrinkedChatSessionState();
				out.setUserChatContextCode(sessionCode);
				IGConfigurableChatModel usedChatModel = chatModelsConfigDao
						.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
				if (usedChatModel == null)
					throw new LLMConfigException("No Internal services or default chat model present");
				out.setConsolidatedInteractions(consolidateHistory(full.getChatHistory().getValue(), tokensBudget / 4,
						oldVersion, usedChatModel));
				out.setChatHistory(copyLatest(full.getChatHistory().getValue()));
				out.setRelevantChatWithDocuments(shrinkDocumentList(untillLatest(full, full.getChatWithDocuments()),
						full.getChatHistory().getValue(), out.getConsolidatedInteractions(),
						oldVersion.getRelevantChatWithDocuments(), tokensBudget, usedChatModel,
						ShrinkedDocumentOrigin.CHAT_WITH_SELECTED));
				out.setRelevantRetrievedDocuments(shrinkDocumentList(untillLatest(full, full.getRetrievedDocuments()),
						full.getChatHistory().getValue(), out.getConsolidatedInteractions(),
						oldVersion.getRelevantRetrievedDocuments(), tokensBudget, usedChatModel,
						ShrinkedDocumentOrigin.RETRIEVED));
				out.setRelevantLlmGeneratedDocuments(shrinkDocumentList(
						untillLatest(full, full.getLlmGeneratedDocuments()), full.getChatHistory().getValue(),
						out.getConsolidatedInteractions(), oldVersion.getRelevantLlmGeneratedDocuments(), tokensBudget,
						usedChatModel, ShrinkedDocumentOrigin.GENERATED));
				out.setRelevantUploadedDocuments(shrinkDocumentList(untillLatest(full, full.getUploadedDocuments()),
						full.getChatHistory().getValue(), out.getConsolidatedInteractions(),
						oldVersion.getRelevantUploadedDocuments(), tokensBudget, usedChatModel,
						ShrinkedDocumentOrigin.UPLOADED));
				int tokensSize = out.getTokensSize();
				CSSReferredContentList latestRequestsChatWithDocuments = afterLatest(full, full.getChatWithDocuments());
				if (tokensBudget < (tokensSize + latestRequestsChatWithDocuments.getTokensSize())) {
					TokensContainer<CSSReferredContentList> lrcwd = new TokensContainer<CSSReferredContentList>(
							latestRequestsChatWithDocuments, latestRequestsChatWithDocuments.getTokensSize());
					CSSfRelevantShrinkedDocumentList shrinkedDocs = shrinkDocumentList(lrcwd, out.getChatHistory(),
							out.getConsolidatedInteractions(), new CSSfRelevantShrinkedDocumentList(), tokensBudget,
							usedChatModel, ShrinkedDocumentOrigin.CHAT_WITH_SELECTED);
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
							out.getConsolidatedInteractions(), new CSSfRelevantShrinkedDocumentList(), tokensBudget,
							usedChatModel, ShrinkedDocumentOrigin.GENERATED);
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
							out.getConsolidatedInteractions(), new CSSfRelevantShrinkedDocumentList(), tokensBudget,
							usedChatModel, ShrinkedDocumentOrigin.RETRIEVED);
					out.getRelevantRetrievedDocuments().addAll(shrinkedDocs);
				} else {
					out.setLatestRequestsRetrievedDocuments(latestRequestsRetrievedDocuments);
				}
				tokensSize = out.getTokensSize();
				CSSReferredContentList latestRequestsUploadedDocuments = afterLatest(full, full.getUploadedDocuments());
				if (tokensBudget < (tokensSize + latestRequestsRetrievedDocuments.getTokensSize())) {
					TokensContainer<CSSReferredContentList> lrcwd = new TokensContainer<CSSReferredContentList>(
							latestRequestsUploadedDocuments, latestRequestsUploadedDocuments.getTokensSize());
					CSSfRelevantShrinkedDocumentList shrinkedDocs = shrinkDocumentList(lrcwd, out.getChatHistory(),
							out.getConsolidatedInteractions(), new CSSfRelevantShrinkedDocumentList(), tokensBudget,
							usedChatModel, ShrinkedDocumentOrigin.GENERATED);
					out.getRelevantUploadedDocuments().addAll(shrinkedDocs);
				} else {
					out.setLatestRequestsUploadedDocuments(latestRequestsUploadedDocuments);
				}

				int afterSize = out.getTokensSize();
				LOGGER.info("Shrinked to:" + afterSize + " tokens");
				shrinkedStateRepository.save(out);
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End shrink(" + sessionCode + ")");
		}

	}

	private CSSSimplifiedChatHistory copyLatest(CSSSimplifiedChatHistory value) {
		int leaveLastInteractionsOnHistoryConsolidation = this.chatConfig
				.getLeaveLastInteractionsOnHistoryConsolidation();
		int lastIndex = value.getInteractions().size() - leaveLastInteractionsOnHistoryConsolidation;
		lastIndex = Math.max(lastIndex, 0);
		CSSSimplifiedChatHistory newHistory = new CSSSimplifiedChatHistory();
		for (int i = lastIndex; i < value.getInteractions().size(); i++) {
			newHistory.getInteractions().add(value.getInteractions().get(i));
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
			CSSSimplifiedChatHistory chatHistory, GUserChatInteractionsConsolidationData consolidated,
			CSSfRelevantShrinkedDocumentList alreadyElaborated, int tokensBudget, IGConfigurableChatModel usedChatModel,
			ShrinkedDocumentOrigin origin) throws LLMConfigException, IOException {
		CSSfRelevantShrinkedDocumentList outList = new CSSfRelevantShrinkedDocumentList();

		if (docs != null && docs.getValue() != null && !docs.getValue().getData().isEmpty()) {
			Map<String, AIDocumentReferenceItem> refsMap = new HashMap<String, AIDocumentReferenceItem>();
			StringBuffer lastTurns = new StringBuffer();
			int leaveLastInteractionsOnHistoryConsolidation = this.chatConfig
					.getLeaveLastInteractionsOnHistoryConsolidation();
			for (int i = chatHistory.getInteractions().size() - 1; i >= 0
					&& i >= (chatHistory.getInteractions().size() - leaveLastInteractionsOnHistoryConsolidation); i--) {
				CSSSimplefiedInteraction interaction = chatHistory.getInteractions().get(i);
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
			GPromptConfig _prompt = promptsDao.findByPromptUse(GeboPromptsLibrary.CHAT_HISTORY_DOCUMENTS_CONSOLIDATION);
			String prompt = _prompt.getPrompt();
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
							String text = callLLMConsolidateText(usedChatModel, prompt, question, pastConsolidation,
									null, toBeConsolidated);
							if (usedChatModel.isApplyThinkingMarkupHandling() && text != null) {
								text = ClientChatCallUtil.removeThinking(text);
							}
							if (text != null && text.trim().length() > 0) {
								out.setSummarizedContent(text);
								outList.add(out);
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

	private GUserChatInteractionsConsolidationData consolidateHistory(CSSSimplifiedChatHistory value,
			int historySizeTarget, ShrinkedChatSessionState oldVersion, IGConfigurableChatModel usedChatModel) {
		List<LLMInputDocument> inputs = new ArrayList<BaseLLMSInvokingAndProvidingService.LLMInputDocument>();
		GPromptConfig _prompt = promptsDao.findByPromptUse(GeboPromptsLibrary.HISTORY_CONSOLIDATION_PROMPT);
		String prompt = _prompt.getPrompt();

		String existingSummary = "";

		int leaveLastInteractionsOnHistoryConsolidation = this.chatConfig
				.getLeaveLastInteractionsOnHistoryConsolidation();
		int lastIndex = value.getInteractions().size() - leaveLastInteractionsOnHistoryConsolidation;
		if (lastIndex > 0) {
			for (int i = 0; i < lastIndex; i++) {
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
			String consolidated = callLLMConsolidateText(usedChatModel, prompt, "", existingSummary, params, inputs);
			GUserChatInteractionsConsolidationData newConsolidation = new GUserChatInteractionsConsolidationData();
			newConsolidation.setConsolidationText(consolidated);
			newConsolidation.setLastInteractionPointer(lastIndex);
			newConsolidation.setTokensSize(tokensEstimator.estimate(newConsolidation.getConsolidationText()));
			return newConsolidation;
		} else
			return null;
	}

}