package ai.gebo.llms.chat.abstraction.layer.model.session;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.ITokensCountable;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.model.TokensContainer;
import lombok.Data;

/**************************************************
 * Data structure to rappresent all information exchanged between assistant and
 * user inside a chat context
 */
@Data
@Document
public class ChatFullSessionState implements ITokensCountable, IChatRequestFactory {
	@Id
	protected String userChatContextCode = null;
	int targetTokenBudget = 0;
	private TokensContainer<GeboChatRequest> currentRequest = new TokensContainer<GeboChatRequest>();
	private TokensContainer<CSSSimplifiedChatHistory> chatHistory = new TokensContainer<CSSSimplifiedChatHistory>();
	private TokensContainer<CSSReferredContentList<GDocumentReference>> historicallyRetrievedDocuments = new TokensContainer<CSSReferredContentList<GDocumentReference>>();
	private TokensContainer<CSSReferredContentList<UserUploadedContent>> historicallyUploadedDocuments = new TokensContainer<CSSReferredContentList<UserUploadedContent>>();
	private TokensContainer<CSSReferredContentList<UserUploadedContent>> latestRequestsUploadedDocuments = new TokensContainer<CSSReferredContentList<UserUploadedContent>>();
	private TokensContainer<CSSReferredContentList<GDocumentReference>> latestRequestsChatWithDocuments = new TokensContainer<CSSReferredContentList<GDocumentReference>>();
	private TokensContainer<CSSReferredContentList<GDocumentReference>> latestRequestsRetrievedDocuments = new TokensContainer<CSSReferredContentList<GDocumentReference>>();
	private TokensContainer<CSSReferredContentList<LLMGeneratedResource>> llmGeneratedDocuments = new TokensContainer<CSSReferredContentList<LLMGeneratedResource>>();

	public ChatFullSessionState() {
		getChatHistory().setValue(new CSSSimplifiedChatHistory());
		getHistoricallyRetrievedDocuments().setValue(new CSSReferredContentList<GDocumentReference>());
		getHistoricallyUploadedDocuments().setValue(new CSSReferredContentList<UserUploadedContent>());
		getLlmGeneratedDocuments().setValue(new CSSReferredContentList<LLMGeneratedResource>());
		getLatestRequestsChatWithDocuments().setValue(new CSSReferredContentList<GDocumentReference>());
		getLatestRequestsUploadedDocuments().setValue(new CSSReferredContentList<UserUploadedContent>());
		getLatestRequestsRetrievedDocuments().setValue(new CSSReferredContentList<GDocumentReference>());
	}

	public int getTokensSize() {
		return ITokensCountable.tokensSize(chatHistory, historicallyRetrievedDocuments, historicallyUploadedDocuments,
				latestRequestsUploadedDocuments, latestRequestsChatWithDocuments, llmGeneratedDocuments,
				latestRequestsRetrievedDocuments, currentRequest);
	}

	private static AIDocumentsSet toDocsSet(TokensContainer<? extends CSSReferredContentList> container) {
		if (container != null && container.getValue() != null) {
			return container.getValue().toAIDocumentsSet();
		} else
			return null;
	}

	@Override
	public LLMChatRequestResources createChatRequestResources() {
		return new LLMChatRequestResources(toDocsSet(latestRequestsChatWithDocuments),
				toDocsSet(latestRequestsRetrievedDocuments), toDocsSet(latestRequestsUploadedDocuments),
				toDocsSet(historicallyRetrievedDocuments), toDocsSet(historicallyUploadedDocuments),
				toDocsSet(llmGeneratedDocuments), null, chatHistory.getValue().getInteractions(),
				currentRequest.getValue());
	}

}
