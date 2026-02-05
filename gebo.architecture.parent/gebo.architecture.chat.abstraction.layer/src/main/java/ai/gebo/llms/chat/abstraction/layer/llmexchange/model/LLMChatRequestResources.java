package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentFragment;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.ITokensCountable;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSSimplefiedInteraction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LLMChatRequestResources implements ITokensCountable {
	// documents that are inherently choosed to chat with from the user or in the
	// last not consolidated turns
	private AIDocumentsSet chatWithDocuments = null;
	// retrieved documents in the last request
	private AIDocumentsSet retrievedDocuments = null;
	// documents specifically uploaded from the user in the last not consolidated
	// turns
	private AIDocumentsSet uploadedDocuments = null;
	private AIDocumentsSet llmGeneratedDocuments = null;
	private String chatConsolidation = null;
	private List<CSSSimplefiedInteraction> lastInteractions = null;
	private GeboChatRequest lastRequest = null;
	private LLMRequestGenerationPolicy generationPolicy;
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
		size += ITokensCountable.tokensSize(llmGeneratedDocuments, chatWithDocuments, uploadedDocuments,
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

			return allDocuments().aiDocumentsList();
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

	public AIDocumentsSet allDocuments() {
		return AIDocumentsSet.join(chatWithDocuments, retrievedDocuments, uploadedDocuments, llmGeneratedDocuments);
	}

	public AIDocumentReferenceItem findAIDocumentReferenceByCode(String docId) {
		AIDocumentsSet allDocs = allDocuments();
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
		AIDocumentsSet.removeAIDocumentReferenceByCode(docId, chatWithDocuments, retrievedDocuments, uploadedDocuments,
				 llmGeneratedDocuments);
	}
}
