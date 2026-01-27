package ai.gebo.llms.chat.abstraction.layer.model.session;

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
public class ChatFullSessionState implements ITokensCountable {
	protected String userChatContextCode = null;

	public ChatFullSessionState() {
		getChatHistory().setValue(new CSSSimplifiedChatHistory());
		getHistoricallyRetrievedDocuments().setValue(new CSSReferredContentList<GDocumentReference>());
		getHistoricallyUploadedDocuments().setValue(new CSSReferredContentList<UserUploadedContent>());
		getLlmGeneratedDocuments().setValue(new CSSReferredContentList<LLMGeneratedResource>());
		getLatestRequestsChatWithDocuments().setValue(new CSSReferredContentList<GDocumentReference>());
		getLatestRequestsUploadedDocuments().setValue(new CSSReferredContentList<UserUploadedContent>());
		getRetrievedDocuments().setValue(new CSSReferredContentList<GDocumentReference>());
	}

	private TokensContainer<GeboChatRequest> currentRequest = new TokensContainer<GeboChatRequest>();
	private TokensContainer<CSSSimplifiedChatHistory> chatHistory = new TokensContainer<CSSSimplifiedChatHistory>();
	private TokensContainer<CSSReferredContentList<GDocumentReference>> historicallyRetrievedDocuments = new TokensContainer<CSSReferredContentList<GDocumentReference>>();
	private TokensContainer<CSSReferredContentList<UserUploadedContent>> historicallyUploadedDocuments = new TokensContainer<CSSReferredContentList<UserUploadedContent>>();
	private TokensContainer<CSSReferredContentList<UserUploadedContent>> latestRequestsUploadedDocuments = new TokensContainer<CSSReferredContentList<UserUploadedContent>>();
	private TokensContainer<CSSReferredContentList<GDocumentReference>> latestRequestsChatWithDocuments = new TokensContainer<CSSReferredContentList<GDocumentReference>>();
	private TokensContainer<CSSReferredContentList<LLMGeneratedResource>> llmGeneratedDocuments = new TokensContainer<CSSReferredContentList<LLMGeneratedResource>>();
	private TokensContainer<CSSReferredContentList<GDocumentReference>> retrievedDocuments = new TokensContainer<CSSReferredContentList<GDocumentReference>>();

	public int getTokensSize() {
		return ITokensCountable.tokensSize(chatHistory, historicallyRetrievedDocuments, historicallyUploadedDocuments,
				latestRequestsUploadedDocuments, latestRequestsChatWithDocuments, llmGeneratedDocuments,
				retrievedDocuments, currentRequest);
	}

	private static AIDocumentsSet toDocsSet(TokensContainer<? extends CSSReferredContentList> container) {
		if (container != null && container.getValue() != null) {
			return container.getValue().toAIDocumentsSet();
		} else
			return null;
	}

	public LLMChatRequestResources toChatRequestResources() {
		return new LLMChatRequestResources(toDocsSet(latestRequestsChatWithDocuments), toDocsSet(retrievedDocuments),
				toDocsSet(latestRequestsUploadedDocuments), toDocsSet(historicallyRetrievedDocuments),
				toDocsSet(historicallyUploadedDocuments), toDocsSet(llmGeneratedDocuments), null,
				chatHistory.getValue().getInteractions(), currentRequest.getValue());
	}
}
