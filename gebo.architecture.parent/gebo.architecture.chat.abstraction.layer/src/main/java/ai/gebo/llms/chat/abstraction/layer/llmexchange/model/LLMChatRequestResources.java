package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.ITokensCountable;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry;
import ai.gebo.llms.chat.abstraction.layer.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSSimplefiedInteraction;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LLMChatRequestResources implements ITokensCountable {
	private static final JTokkitTokenCountEstimator tokensEstimator = new JTokkitTokenCountEstimator();
	// documents that are inherently choosed to chat with from the user or in the
	// last not consolidated turns
	private final AIDocumentsSet latestRequestsChatWithDocuments;
	// retrieved documents in the last request
	private final AIDocumentsSet retrievedDocuments;
	// documents specifically uploaded from the user in the last not consolidated
	// turns
	private final AIDocumentsSet latestRequestsUploadedDocuments;
	// Rag retrieved contents storically or in the current request
	private final AIDocumentsSet historicallyRetrievedDocuments;
	// Uploaded historical contents
	private final AIDocumentsSet historicallyUploadedDocuments;
	// LLM Generated artifacts/documents
	private final AIDocumentsSet llmGeneratedDocuments;
	private final String chatConsolidation;
	private final List<CSSSimplefiedInteraction> lastInteractions;
	private final GeboChatRequest lastRequest;

	@AllArgsConstructor
	static final class InteractionWrapper implements IChatSessionEntry {
		CSSSimplefiedInteraction interaction = null;

		@Override
		public String getUser() {

			return interaction.getUser() != null ? interaction.getUser() : "";
		}

		@Override
		public String getAssistant() {

			return interaction.getAssistant() != null ? interaction.getAssistant() : "";
		}
	}

	@Override
	public int getTokensSize() {
		int size = 0;
		if (lastRequest != null && lastRequest.getQuery() != null) {
			size += tokensEstimator.estimate(lastRequest.getQuery());
		}
		size += tokensSize(historicallyRetrievedDocuments, historicallyUploadedDocuments, llmGeneratedDocuments,
				latestRequestsChatWithDocuments, latestRequestsUploadedDocuments);
		size += tokensSize(lastInteractions);
		return size;
	}

	final class NestedChatRequestContext implements IChatRequestContext {

		@Override
		public String getConsolidatedHistory() {

			return chatConsolidation != null ? chatConsolidation : "";
		}

		@Override
		public List<IChatSessionEntry> getInteractions() {
			List<IChatSessionEntry> entries = new ArrayList<IChatSessionEntry>();
			if (lastInteractions != null) {
				for (CSSSimplefiedInteraction i : lastInteractions) {
					entries.add(new InteractionWrapper(i));
				}
			}
			return entries;
		}

		@Override
		public List<Document> getDocuments() {

			return AIDocumentsSet.join(historicallyRetrievedDocuments, historicallyUploadedDocuments,
					llmGeneratedDocuments, latestRequestsChatWithDocuments, latestRequestsUploadedDocuments)
					.aiDocumentsList();
		}

		@Override
		public String getActualUserRequest() {

			return lastRequest.getQuery();
		}

		@Override
		public Map<String, Object> getToolsContext() {

			return new HashMap<String, Object>();
		}
	}

	public IChatRequestContext createChatRequestContext() {
		return new NestedChatRequestContext();
	}
}
