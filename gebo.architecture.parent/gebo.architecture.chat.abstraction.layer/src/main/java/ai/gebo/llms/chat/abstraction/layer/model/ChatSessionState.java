package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.llms.abstraction.layer.model.RagDocumentsCachedDaoResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatSessionState {
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SimplefiedInteraction {
		private String user = null;
		private Integer userTokenSize = null;
		private String assistant = null;
		private Integer assistantTokenSize = null;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SimplifiedChatHistory {
		private List<SimplefiedInteraction> interactions = new ArrayList();
		private GUserChatConsolidationData consolidation = null;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class InteractionReferredInfo<T> {
		private int interactionIndex = 0;
		private T data = null;
	}

	private TokenLimitedContent<SimplifiedChatHistory> chatHistory = new TokenLimitedContent<SimplifiedChatHistory>();
	private TokenLimitedContent<List<InteractionReferredInfo<RagDocumentsCachedDaoResult>>> ragResultsHistory = new TokenLimitedContent<List<InteractionReferredInfo<RagDocumentsCachedDaoResult>>>();
	private TokenLimitedContent<List<InteractionReferredInfo<RagDocumentsCachedDaoResult>>> uploadsHistory = new TokenLimitedContent<List<InteractionReferredInfo<RagDocumentsCachedDaoResult>>>();
	private TokenLimitedContent<RagDocumentsCachedDaoResult> currentRequestUploads = new TokenLimitedContent<RagDocumentsCachedDaoResult>();
}
