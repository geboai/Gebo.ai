package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentFragment;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSConsolidatedChatHistory;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSSimplefiedInteraction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	private CSSConsolidatedChatHistory chathistory = null;
	private GeboChatRequest currentRequest = null;
	private LLMRequestGenerationPolicy generationPolicy;
	/**
	 * The recorder of every tool called while answering the current request, handed to
	 * the model calls through the request contexts; set by the owner of the response
	 * (the pipeline executor), never persisted.
	 */
	@JsonIgnore
	private transient ToolCallsListener toolCallsListener = null;

	// Request id -> note appended to that answer in the history shown to the model.
	private Map<String, String> answerFeedbackNotes = new HashMap<String, String>();
	private List<String> rulesToFollow = new ArrayList<String>();

	public LLMChatRequestResources(AIDocumentsSet chatWithDocuments, AIDocumentsSet retrievedDocuments,
			AIDocumentsSet uploadedDocuments, AIDocumentsSet llmGeneratedDocuments,
			CSSConsolidatedChatHistory chathistory, GeboChatRequest currentRequest,
			LLMRequestGenerationPolicy generationPolicy) {
		this.chatWithDocuments = chatWithDocuments;
		this.retrievedDocuments = retrievedDocuments;
		this.uploadedDocuments = uploadedDocuments;
		this.llmGeneratedDocuments = llmGeneratedDocuments;
		this.chathistory = chathistory;
		this.currentRequest = currentRequest;
		this.generationPolicy = generationPolicy;
	}

	@AllArgsConstructor
	static final class InteractionWrapper implements IChatSessionEntry {
		CSSSimplefiedInteraction interaction = null;
		String feedbackNote = null;

		@Override
		public String getUser() {

			return interaction.getUser() != null ? interaction.getUser() : "";
		}

		@Override
		public String getAssistant() {

			String assistant = interaction.getAssistant() != null ? interaction.getAssistant() : "";
			return feedbackNote != null ? assistant + feedbackNote : assistant;
		}
	}

	@Override
	public int getTokensSize() {
		int size = 0;
		size += ITokensCountable.tokensSize(llmGeneratedDocuments, chatWithDocuments, uploadedDocuments,
				retrievedDocuments, currentRequest);
		size += ITokensCountable.tokensSize(chathistory);
		return size;
	}

	final class NestedChatRequestContext implements IChatRequestContext {
		@Override
		public String getRequestID() {

			return currentRequest != null ? currentRequest.getId() : "No current request";
		}

		@Override
		public String getSessionID() {

			return currentRequest != null ? currentRequest.getUserChatContextCode() : "No current context code";
		}

		@Override
		public String getConsolidatedHistory() {

			return chathistory != null && chathistory.getConsolidationText() != null
					? chathistory.getConsolidationText()
					: "";
		}
		@Override
		public ToolCallsListener getToolCallListener() {
			return toolCallsListener;
		}

		@Override
		public List<String> getRulesToFollow() {
			return rulesToFollow != null ? rulesToFollow : List.of();
		}

		@Override
		public List<IChatSessionEntry> getInteractions() {
			List<IChatSessionEntry> entries = new ArrayList<IChatSessionEntry>();
			if (chathistory.getLatestEntries() != null) {
				for (CSSSimplefiedInteraction i : chathistory.getLatestEntries().getInteractions()) {
					String note = answerFeedbackNotes != null && i.getRequestId() != null
							? answerFeedbackNotes.get(i.getRequestId())
							: null;
					entries.add(new InteractionWrapper(i, note));
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

			// No current request outside of a request, e.g. when the history is summarized in background.
			return currentRequest != null ? GeboChatRequest.actualQuery(currentRequest) : "";
		}

		@Override
		public Map<String, Object> getToolsContext() {
			// The request id lets the tools keep request-scoped state across their calls
			// (e.g. the search tools not returning twice the same content in one answer).
			Map<String, Object> toolsContext = new HashMap<String, Object>();
			if (currentRequest != null && currentRequest.getId() != null) {
				toolsContext.put(ToolCallbackDeclarationUtil.REQUEST_ID_CONTEXT_KEY, currentRequest.getId());
			}
			return toolsContext;
		}

		@Override
		public Map<String, Object> getPipelineInfos() {
			Map<String, Object> pipelineInfos = new HashMap<>();
			if (currentRequest != null) {

				if (currentRequest.getChatPipelineProcessId() != null) {
					pipelineInfos.put("user-choosed-pipelineId", currentRequest.getChatPipelineProcessId());
				}
				if (currentRequest.getUserIntent() != null) {
					pipelineInfos.put("user-intent", currentRequest.getUserIntent().name());
				}

			}
			return pipelineInfos; 
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
