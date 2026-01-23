package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.stereotype.Service;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.RagDocumentFragment;
import ai.gebo.llms.abstraction.layer.services.BaseLlmsInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboChatPromptsConfigs;
import ai.gebo.llms.chat.abstraction.layer.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInteractionsConsolidationData;
import ai.gebo.llms.chat.abstraction.layer.model.TokenLimitedContent;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSInteractionReferredContent;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSReferredContentList;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSRelevantShrinkedDocument;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSSimplefiedInteraction;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSSimplifiedChatHistory;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSfRelevantShrinkedDocumentList;
import ai.gebo.llms.chat.abstraction.layer.model.session.ChatSessionState;
import ai.gebo.llms.chat.abstraction.layer.model.session.ShrinkedChatSessionState;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionStateShrinker;
import ai.gebo.model.DocumentMetaInfos;
import lombok.AllArgsConstructor;

@Service

public class GChatSessionStateShrinkerImpl extends BaseLlmsInvokingService implements IGChatSessionStateShrinker {
	private static final String NEWLINE = "\r\n";
	final ChatHistoryConsolidationService historyConsolidationService;
	final GeboChatPromptsConfigs chatPromptsConfig;
	private final static JTokkitTokenCountEstimator tokensEstimator = new JTokkitTokenCountEstimator();
	public static final String ASSISTANT_MSG = "assistant:";
	public static final String USER_MSG = "user:";
	public static final String NEW_MESSAGES = "new_messages";
	public static final String EXISTING_SUMMARY = "existing_summary";
	public static final String HISTORY_SIZE_TARGET = "historySizeTarget";

	public GChatSessionStateShrinkerImpl(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao,
			ChatHistoryConsolidationService historyConsolidationService, GeboChatPromptsConfigs chatPromptsConfig) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
		this.historyConsolidationService = historyConsolidationService;
		this.chatPromptsConfig = chatPromptsConfig;
	}

	@Override
	public ShrinkedChatSessionState shrink(ChatSessionState fullSessionState, int tokensBudget)
			throws LLMConfigException, IOException {
		ShrinkedChatSessionState out = new ShrinkedChatSessionState();

		IGConfigurableChatModel usedChatModel = chatModelsConfigDao
				.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
		if (usedChatModel == null)
			throw new LLMConfigException("No Internal services or default chat model present");
		out.setConsolidatedInteractions(
				consolidateHistory(fullSessionState.getChatHistory().getValue(), tokensBudget / 4, usedChatModel));

		out.setRelevantRagRetrievedDocuments(shrinkDocumentList(fullSessionState.getRagResultsHistory(),
				fullSessionState.getChatHistory().getValue(), tokensBudget / 4, usedChatModel));
		out.setRelevantUploadedDocuments(shrinkDocumentList(fullSessionState.getUploadsHistory(),
				fullSessionState.getChatHistory().getValue(), tokensBudget / 4, usedChatModel));
		out.setRelevantLlmGeneratedDocuments(shrinkDocumentList(fullSessionState.getGeneratedArtifacts(),
				fullSessionState.getChatHistory().getValue(), tokensBudget / 4, usedChatModel));
		return out;
	}

	private CSSfRelevantShrinkedDocumentList shrinkDocumentList(
			TokenLimitedContent<? extends CSSReferredContentList> docs, CSSSimplifiedChatHistory chatHistory,
			int tokensBudget, IGConfigurableChatModel usedChatModel) throws LLMConfigException, IOException {
		CSSfRelevantShrinkedDocumentList outList = new CSSfRelevantShrinkedDocumentList();
		if (docs != null && docs.getValue() != null && !docs.getValue().isEmpty()) {
			List<ConsolidationInput> toBeConsolidated = new ArrayList<ConsolidationInput>();
			for (int i = 0; i < docs.getValue().size(); i++) {
				CSSInteractionReferredContent content = (CSSInteractionReferredContent) docs.getValue().get(i);
				StringBuffer buffer = new StringBuffer();
				for (RagDocumentFragment fragment : content.getData().getFragments()) {
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
			StringBuffer lastTurns = new StringBuffer();
			for (int i = chatHistory.getInteractions().size() - 1; i >= 0
					&& i >= chatHistory.getInteractions().size() - 3; i++) {
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
			String pastConsolidation = chatHistory.getConsolidation().getConsolidationText();

			CSSfRelevantShrinkedDocumentList consolidated = callLLMConsolidateStructuredReturn(usedChatModel, prompt,
					question, pastConsolidation, CSSfRelevantShrinkedDocumentList.class, this::joiner,
					toBeConsolidated);
			outList.addAll(consolidated);
		}
		return outList;
	}

	private GUserChatInteractionsConsolidationData consolidateHistory(CSSSimplifiedChatHistory value,
			int historySizeTarget, IGConfigurableChatModel usedChatModel) {
		GPromptConfig prompt = chatPromptsConfig.getHistoryConsolidationPrompt();
		PromptTemplate promptTemplate = new PromptTemplate(prompt.getPrompt());
		promptTemplate.add(HISTORY_SIZE_TARGET, "" + historySizeTarget);
		StringBuffer existing_summary = new StringBuffer();
		StringBuffer new_messages = new StringBuffer();
		GUserChatInteractionsConsolidationData data = value.getConsolidation();
		int minimumInteractionIndex = 0;
		if (data != null) {
			existing_summary.append(data.getConsolidationText());
			minimumInteractionIndex = data.getLastInteractionPointer() != null
					? data.getLastInteractionPointer().intValue()
					: 0;
		}
		promptTemplate.add(EXISTING_SUMMARY, existing_summary.toString());
		int leaveLastInteractionsOnHistoryConsolidation = this.chatPromptsConfig
				.getLeaveLastInteractionsOnHistoryConsolidation();
		int lastIndex = value.getInteractions().size() - leaveLastInteractionsOnHistoryConsolidation;
		if (minimumInteractionIndex >= lastIndex) {
			lastIndex = value.getInteractions().size() - leaveLastInteractionsOnHistoryConsolidation / 2;
			if (minimumInteractionIndex >= lastIndex) {
				lastIndex = value.getInteractions().size();
			}
		}
		for (int i = minimumInteractionIndex; i < lastIndex; i++) {
			CSSSimplefiedInteraction interaction = value.getInteractions().get(i);
			if (interaction.getUser() != null) {
				new_messages.append(USER_MSG + interaction.getUser() + NEWLINE);
			}
			if (interaction.getAssistant() != null) {
				new_messages.append(ASSISTANT_MSG + interaction.getAssistant() + NEWLINE);
			}
		}

		promptTemplate.add(NEW_MESSAGES, new_messages.toString());
		Generation result = usedChatModel.getChatModel().call(promptTemplate.create()).getResult();
		GUserChatInteractionsConsolidationData newConsolidation = new GUserChatInteractionsConsolidationData();
		newConsolidation.setConsolidationText(result.getOutput().getText());
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
				if (t.getSummarizedContent() != null) {
					t.setTokensSize(this.tokensEstimator.estimate(t.getSummarizedContent()));
				}
			}
			out.addAll(t2);
		}
		return out;
	}

}
