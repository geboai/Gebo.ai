package ai.gebo.llms.chat.abstraction.layer.model.session;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.model.TokensContainer;
import lombok.Data;

/**************************************************
 * Data structure to rappresent all information exchanged between assistant and
 * user inside a chat context
 */
@Data
public class ChatFullSessionState {
	protected String userChatContextCode = null;

	public ChatFullSessionState() {
		getChatHistory().setValue(new CSSSimplifiedChatHistory());
		getRagResultsHistory().setValue(new CSSReferredContentList<GDocumentReference>());
		getUploadsHistory().setValue(new CSSReferredContentList<UserUploadedContent>());
		getGeneratedArtifacts().setValue(new CSSReferredContentList<LLMGeneratedResource>());
		getCurrentRequestChatWithDocuments().setValue(new CSSReferredContentList<GDocumentReference>());
		
	}

	private TokensContainer<CSSSimplifiedChatHistory> chatHistory = new TokensContainer<CSSSimplifiedChatHistory>();
	private TokensContainer<CSSReferredContentList<GDocumentReference>> ragResultsHistory = new TokensContainer<CSSReferredContentList<GDocumentReference>>();
	private TokensContainer<CSSReferredContentList<UserUploadedContent>> uploadsHistory = new TokensContainer<CSSReferredContentList<UserUploadedContent>>();
	private TokensContainer<CSSReferredContentList<UserUploadedContent>> currentRequestUploads = new TokensContainer<CSSReferredContentList<UserUploadedContent>>();
	private TokensContainer<CSSReferredContentList<GDocumentReference>> currentRequestChatWithDocuments = new TokensContainer<CSSReferredContentList<GDocumentReference>>();
	private TokensContainer<CSSReferredContentList<LLMGeneratedResource>> generatedArtifacts = new TokensContainer<CSSReferredContentList<LLMGeneratedResource>>();
	private int totalTokensSize = 0;
}
