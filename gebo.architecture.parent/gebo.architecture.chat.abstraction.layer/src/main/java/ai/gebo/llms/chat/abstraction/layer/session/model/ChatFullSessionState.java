package ai.gebo.llms.chat.abstraction.layer.session.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
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
	private TokensContainer<CSSReferredContentList<UserUploadedContentSTO>> uploadedDocuments = new TokensContainer<CSSReferredContentList<UserUploadedContentSTO>>();
	private TokensContainer<CSSReferredContentList<GDocumentReferenceSTO>> chatWithDocuments = new TokensContainer<CSSReferredContentList<GDocumentReferenceSTO>>();
	private TokensContainer<CSSReferredContentList<GDocumentReferenceSTO>> retrievedDocuments = new TokensContainer<CSSReferredContentList<GDocumentReferenceSTO>>();
	private TokensContainer<CSSReferredContentList<LLMGeneratedResourceSTO>> llmGeneratedDocuments = new TokensContainer<CSSReferredContentList<LLMGeneratedResourceSTO>>();

	public ChatFullSessionState() {
		getChatHistory().setValue(new CSSSimplifiedChatHistory());
		getLlmGeneratedDocuments().setValue(new CSSReferredContentList<LLMGeneratedResourceSTO>());
		getChatWithDocuments().setValue(new CSSReferredContentList<GDocumentReferenceSTO>());
		getUploadedDocuments().setValue(new CSSReferredContentList<UserUploadedContentSTO>());
		getRetrievedDocuments().setValue(new CSSReferredContentList<GDocumentReferenceSTO>());
	}

	public int getTokensSize() {
		return ITokensCountable.tokensSize(chatHistory, uploadedDocuments, chatWithDocuments, llmGeneratedDocuments,
				retrievedDocuments, currentRequest);
	}

	private static AIDocumentsSet toDocsSet(TokensContainer<? extends CSSReferredContentList> container) {
		if (container != null && container.getValue() != null) {
			return container.getValue().toAIDocumentsSet();
		} else
			return null;
	}

	@Override
	public LLMChatRequestResources createChatRequestResources(LLMRequestGenerationPolicy pol) {
		return new LLMChatRequestResources(toDocsSet(chatWithDocuments), toDocsSet(retrievedDocuments),
				toDocsSet(uploadedDocuments), toDocsSet(llmGeneratedDocuments), adaptHistory(),
				currentRequest.getValue(), pol);
	}

	private CSSConsolidatedChatHistory adaptHistory() {
		CSSConsolidatedChatHistory history = new CSSConsolidatedChatHistory();
		List<CSSSimplefiedInteraction> interactions = this.getChatHistory().getValue().getInteractions();
		for (CSSSimplefiedInteraction cssSimplefiedInteraction : interactions) {
			history.getLatestEntries().getInteractions()
					.add((CSSSimplefiedInteraction) cssSimplefiedInteraction.clone());
		}
		return history;
	}

}
