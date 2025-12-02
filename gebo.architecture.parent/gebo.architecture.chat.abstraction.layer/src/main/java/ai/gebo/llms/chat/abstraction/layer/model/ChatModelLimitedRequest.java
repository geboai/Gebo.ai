package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.List;

import ai.gebo.llms.abstraction.layer.model.RagDocumentsCachedDaoResult;
import lombok.Data;

@Data
public class ChatModelLimitedRequest {

	/** The maximum number of tokens allowed for the context window */
	private int contextWindowNToken;
	private boolean historyConsolidationRequired = false;
	private int historySizeTarget = 0;
	/** The chat history limited by tokens */
	private TokenLimitedContent<ChatHistoryData> history;
	/** The query string limited by tokens */
	private TokenLimitedContent<String> query;
	private TokenLimitedContent<RagDocumentsCachedDaoResult> uploadedDocuments;
	/** The remaining token space available */
	private int residualTokenSpace;

	public ChatModelRequestContextWindowStats getStats() {

		ChatModelRequestContextWindowStats stats = new ChatModelRequestContextWindowStats();
		stats.contextWindowLengthNTokens = contextWindowNToken;
		stats.documentsNTokens = 0;
		stats.historyNTokens = history != null ? history.getNToken() : 0;
		stats.queryNTokens = query != null ? query.getNToken() : 0;
		stats.uploadedDocumentsNTokens = uploadedDocuments != null ? uploadedDocuments.getNToken() : 0;
		stats.contextDocumentsNTokens = 0;

		if (stats.contextWindowLengthNTokens > 0.0) {
			// Calculate available tokens and percentage shares for each component
			stats.availableNTokens = stats.contextWindowLengthNTokens - (stats.documentsNTokens + stats.queryNTokens
					+ stats.documentsNTokens + stats.contextDocumentsNTokens);
			stats.documentsSharePerc = 100.0 * stats.documentsNTokens / stats.contextWindowLengthNTokens;
			stats.historySharePerc = 100.0 * stats.historyNTokens / stats.contextWindowLengthNTokens;
			stats.queryNTokens = 100.0 * stats.queryNTokens / stats.contextWindowLengthNTokens;
			stats.historySharePerc = 100.0 * stats.historyNTokens / stats.contextWindowLengthNTokens;
			stats.availableSharePerc = 100.0 * stats.availableNTokens / stats.contextWindowLengthNTokens;
			stats.contextDocumentsSharePerc = 100.0 * stats.contextDocumentsNTokens / stats.contextWindowLengthNTokens;
			stats.uploadedDocumentsSharePerc = 100.0 * stats.uploadedDocumentsNTokens
					/ stats.contextWindowLengthNTokens;
		}
		return stats;
	}
}
