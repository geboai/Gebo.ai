package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentFragment;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.services.BaseLlmsInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboChatConfigs;
import ai.gebo.llms.chat.abstraction.layer.config.GeboChatPromptsConfigs;
import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInteractionsConsolidationData;
import ai.gebo.llms.chat.abstraction.layer.model.TokensContainer;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSInteractionReferredContent;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSReferredContentList;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSRelevantShrinkedDocument;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSSimplefiedInteraction;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSSimplifiedChatHistory;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSfRelevantShrinkedDocumentList;
import ai.gebo.llms.chat.abstraction.layer.model.session.ChatFullSessionState;
import ai.gebo.llms.chat.abstraction.layer.model.session.ShrinkedChatSessionState;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionStateShrinkerService;
import ai.gebo.model.DocumentMetaInfos;

@Service
public class GChatSessionStateShrinkerServiceImpl extends BaseLlmsInvokingService
		implements IGChatSessionStateShrinkerService {
	private static final String NEWLINE = "\r\n";
	final GeboChatPromptsConfigs chatPromptsConfig;
	final GeboChatConfigs chatConfig;
	private final static JTokkitTokenCountEstimator tokensEstimator = new JTokkitTokenCountEstimator();
	public static final String ASSISTANT_MSG = "assistant:";
	public static final String USER_MSG = "user:";
	public static final String NEW_MESSAGES = "new_messages";
	public static final String EXISTING_SUMMARY = "existing_summary";
	public static final String HISTORY_SIZE_TARGET = "historySizeTarget";

	public GChatSessionStateShrinkerServiceImpl(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao, GeboChatPromptsConfigs chatPromptsConfig,
			GeboChatConfigs chatConfig) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
		this.chatPromptsConfig = chatPromptsConfig;
		this.chatConfig = chatConfig;
	}

	@Override
	public ShrinkedChatSessionState shrink(ChatFullSessionState fullSessionState, int tokensBudget)
			throws LLMConfigException, IOException {
		ShrinkedChatSessionState out = new ShrinkedChatSessionState();
		out.setUserChatContextCode(fullSessionState.getUserChatContextCode());
		IGConfigurableChatModel usedChatModel = chatModelsConfigDao
				.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
		if (usedChatModel == null)
			throw new LLMConfigException("No Internal services or default chat model present");
		out.setConsolidatedInteractions(
				consolidateHistory(fullSessionState.getChatHistory().getValue(), tokensBudget / 4, usedChatModel));

		out.setRelevantRagRetrievedDocuments(shrinkDocumentList(fullSessionState.getHistoricallyRetrievedDocuments(),
				fullSessionState.getChatHistory().getValue(), out.getConsolidatedInteractions(), tokensBudget / 4,
				usedChatModel));
		out.setRelevantUploadedDocuments(shrinkDocumentList(fullSessionState.getHistoricallyUploadedDocuments(),
				fullSessionState.getChatHistory().getValue(), out.getConsolidatedInteractions(), tokensBudget / 4,
				usedChatModel));
		out.setRelevantLlmGeneratedDocuments(shrinkDocumentList(fullSessionState.getLlmGeneratedDocuments(),
				fullSessionState.getChatHistory().getValue(), out.getConsolidatedInteractions(), tokensBudget / 4,
				usedChatModel));
		return out;
	}

	private CSSfRelevantShrinkedDocumentList shrinkDocumentList(TokensContainer<? extends CSSReferredContentList> docs,
			CSSSimplifiedChatHistory chatHistory, GUserChatInteractionsConsolidationData consolidated, int tokensBudget,
			IGConfigurableChatModel usedChatModel) throws LLMConfigException, IOException {
		CSSfRelevantShrinkedDocumentList outList = new CSSfRelevantShrinkedDocumentList();

		if (docs != null && docs.getValue() != null && !docs.getValue().isEmpty()) {
			Map<String, AIDocumentReferenceItem> refsMap = new HashMap<String, AIDocumentReferenceItem>();
			StringBuffer lastTurns = new StringBuffer();
			int leaveLastInteractionsOnHistoryConsolidation = this.chatConfig
					.getLeaveLastInteractionsOnHistoryConsolidation();
			for (int i = chatHistory.getInteractions().size() - 1; i >= 0
					&& i >= chatHistory.getInteractions().size() - leaveLastInteractionsOnHistoryConsolidation; i++) {
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
			String prompt = this.chatPromptsConfig.getHistoryDocumentsConsolidationPrompt().getPrompt();
			String question = lastTurns.toString();
			String pastConsolidation = consolidated != null ? consolidated.getConsolidationText() : "";
			List<ConsolidationInput> toBeConsolidated = new ArrayList<ConsolidationInput>();
			for (int i = 0; i < docs.getValue().size(); i++) {
				CSSInteractionReferredContent content = (CSSInteractionReferredContent) docs.getValue().get(i);
				if (content.getData().getFragments().isEmpty())
					continue;
				refsMap.put(content.getData().getCode(), content.getData());
				StringBuffer buffer = new StringBuffer();
				for (AIDocumentFragment fragment : content.getData().getFragments()) {
					buffer.append(fragment.getDocumentContent());
				}
				if (!buffer.isEmpty()) {
					ConsolidationInput input = new ConsolidationInput(content.getData().getCode(),
							content.getData().getOriginalUrl(),
							(String) content.getData().getFragments().get(0).getMetaData().get(DocumentMetaInfos.TITLE),
							buffer.toString());
					toBeConsolidated.add(input);
				}
			}
			if (!toBeConsolidated.isEmpty()) {
				CSSfRelevantShrinkedDocumentList _consolidated = callLLMConsolidateStructuredReturn(usedChatModel,
						prompt, question, pastConsolidation, CSSfRelevantShrinkedDocumentList.class, this::joiner,
						toBeConsolidated);
				outList.addAll(_consolidated);
			}
			for (CSSRelevantShrinkedDocument cssRelevantShrinkedDocument : outList) {
				cssRelevantShrinkedDocument.setId(UUID.randomUUID().toString());
				cssRelevantShrinkedDocument
						.setTokensSize(tokensEstimator.estimate(cssRelevantShrinkedDocument.getSummarizedContent()));
				if (cssRelevantShrinkedDocument.getDocumentReference() != null) {
					AIDocumentReferenceItem doc = refsMap.get(cssRelevantShrinkedDocument.getDocumentReference());
					if (doc != null) {
						AIDocumentFragment fragment = doc.getFragments().get(0);
						cssRelevantShrinkedDocument.setMetaData(new HashMap<String, Object>(fragment.getMetaData()));
						cssRelevantShrinkedDocument.getMetaData().remove(DocumentMetaInfos.CONTENT_PAGE);
					}
				}
			}
		}
		return outList;
	}

	private GUserChatInteractionsConsolidationData consolidateHistory(CSSSimplifiedChatHistory value,
			int historySizeTarget, IGConfigurableChatModel usedChatModel) {
		List<ConsolidationInput> inputs = new ArrayList<BaseLlmsInvokingService.ConsolidationInput>();
		GPromptConfig _prompt = chatPromptsConfig.getHistoryConsolidationPrompt();
		String prompt = _prompt.getPrompt();

		String existingSummary = "";

		int leaveLastInteractionsOnHistoryConsolidation = this.chatConfig
				.getLeaveLastInteractionsOnHistoryConsolidation();
		int lastIndex = value.getInteractions().size() - leaveLastInteractionsOnHistoryConsolidation;

		for (int i = 0; i < lastIndex; i++) {
			StringBuffer new_messages = new StringBuffer();
			CSSSimplefiedInteraction interaction = value.getInteractions().get(i);
			if (interaction.getUser() != null) {
				new_messages.append(USER_MSG + interaction.getUser() + NEWLINE);
			}
			if (interaction.getAssistant() != null) {
				new_messages.append(ASSISTANT_MSG + interaction.getAssistant() + NEWLINE);
			}
			ConsolidationInput input = new ConsolidationInput(null, null, null, new_messages.toString());
			inputs.add(input);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(HISTORY_SIZE_TARGET, "" + historySizeTarget);
		String consolidated = callLLMConsolidateText(usedChatModel, prompt, "", existingSummary, params, inputs);
		GUserChatInteractionsConsolidationData newConsolidation = new GUserChatInteractionsConsolidationData();
		newConsolidation.setConsolidationText(consolidated);
		newConsolidation.setLastInteractionPointer(value.getInteractions().size());
		newConsolidation.setTokensSize(tokensEstimator.estimate(newConsolidation.getConsolidationText()));
		return newConsolidation;
	}

	private CSSfRelevantShrinkedDocumentList joiner(CSSfRelevantShrinkedDocumentList t1,
			CSSfRelevantShrinkedDocumentList t2) {
		CSSfRelevantShrinkedDocumentList out = new CSSfRelevantShrinkedDocumentList();
		if (t1 != null)
			out.addAll(t1);

		if (t2 != null) {
			for (CSSRelevantShrinkedDocument t : t2) {
				t.setId(UUID.randomUUID().toString());
				if (t.getSummarizedContent() != null) {
					t.setTokensSize(this.tokensEstimator.estimate(t.getSummarizedContent()));
				}
			}
			out.addAll(t2);
		}
		return out;
	}

}
