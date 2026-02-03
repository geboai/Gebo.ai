package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.ai.document.Document;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentFragment;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.ITokensCountable;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry;
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
		size += ITokensCountable.tokensSize(historicallyRetrievedDocuments, historicallyUploadedDocuments,
				llmGeneratedDocuments, latestRequestsChatWithDocuments, latestRequestsUploadedDocuments,
				retrievedDocuments, lastRequest);
		size += ITokensCountable.tokensSize(lastInteractions);
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

			return getAllDocuments().aiDocumentsList();
		}

		@Override
		public String getActualUserRequest() {

			return GeboChatRequest.actualQuery(lastRequest);
		}

		@Override
		public Map<String, Object> getToolsContext() {

			return new HashMap<String, Object>();
		}
	}

	public IChatRequestContext createChatRequestContext() {
		return new NestedChatRequestContext();
	}

	public AIDocumentsSet getAllDocuments() {
		return AIDocumentsSet.join(
				(lastRequest != null && lastRequest.getDocuments() != null ? lastRequest.getDocuments() : null),
				latestRequestsChatWithDocuments, retrievedDocuments, latestRequestsUploadedDocuments,
				historicallyRetrievedDocuments, historicallyUploadedDocuments, llmGeneratedDocuments);
	}

	public AIDocumentReferenceItem findAIDocumentReferenceByCode(String docId) {
		AIDocumentsSet allDocs = getAllDocuments();
		List<AIDocumentReferenceItem> optdoc = allDocs.getDocumentItems().stream()
				.filter(x -> x.getCode().equals(docId)).toList();
		Map<String, AIDocumentFragment> fragments = new HashMap<String, AIDocumentFragment>();

		optdoc.forEach(x -> {
			List<AIDocumentFragment> localFragments = x.getFragments();
			if (localFragments != null) {
				localFragments.forEach(y -> {
					fragments.put(y.getDocumentId(), y);
				});
			}
		});
		if (!optdoc.isEmpty()) {
			AIDocumentReferenceItem doc = optdoc.get(0);
			doc.setFragments(new ArrayList<AIDocumentFragment>(fragments.values()));
			doc.recalculateSize();
			doc.reorderFragmentsByPosition();
			return doc;
		}
		return null;
	}

	public void removeAIDocumentReferenceByCode(String docId) {
		AIDocumentsSet.removeAIDocumentReferenceByCode(docId,
				(lastRequest != null && lastRequest.getDocuments() != null ? lastRequest.getDocuments() : null),
				latestRequestsChatWithDocuments, retrievedDocuments, latestRequestsUploadedDocuments,
				historicallyRetrievedDocuments, historicallyUploadedDocuments, llmGeneratedDocuments);
	}
}
