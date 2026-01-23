package ai.gebo.llms.chat.abstraction.layer.model.session;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.llms.chat.abstraction.layer.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.model.TokenLimitedContent;
import ai.gebo.llms.chat.abstraction.layer.model.UserUploadedContent;
import lombok.Data;

/**************************************************
 * Data structure to rappresent all information exchanged between assistant and
 * user inside a chat context
 */
@Data
public class ChatSessionState {
	public ChatSessionState() {
		getChatHistory().setValue(new CSSSimplifiedChatHistory());
		getRagResultsHistory().setValue(new CSSReferredContentList<GDocumentReference>());
		getUploadsHistory().setValue(new CSSReferredContentList<UserUploadedContent>());
		getGeneratedArtifacts().setValue(new CSSReferredContentList<LLMGeneratedResource>());
	}
	private TokenLimitedContent<CSSSimplifiedChatHistory> chatHistory = new TokenLimitedContent<CSSSimplifiedChatHistory>();
	private TokenLimitedContent<CSSReferredContentList<GDocumentReference>> ragResultsHistory = new TokenLimitedContent<CSSReferredContentList<GDocumentReference>>();
	private TokenLimitedContent<CSSReferredContentList<UserUploadedContent>> uploadsHistory = new TokenLimitedContent<CSSReferredContentList<UserUploadedContent>>();
	private TokenLimitedContent<CSSReferredContentList<UserUploadedContent>> currentRequestUploads = new TokenLimitedContent<CSSReferredContentList<UserUploadedContent>>();
	private TokenLimitedContent<CSSReferredContentList<LLMGeneratedResource>> generatedArtifacts = new TokenLimitedContent<CSSReferredContentList<LLMGeneratedResource>>();
	private int totalTokensSize = 0;
}
