package ai.gebo.llms.abstraction.layer.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.ai.document.Document;

import ai.gebo.llms.abstraction.layer.model.IChatRequestContext.ChatRequestContextImpl.ChatRequestContextImplBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public interface IChatRequestContext {
	public static final String USER_QUESTION_PROMPT_PLACEHOLDER = "question";
	public static final String CONSOLIDATED_HISTORY_PROMPT_PLACEHOLDER = "consolidatedChatHistory";
	public static final String DOCUMENTS_PROMPT_PLACEHOLDER = "documents";

	public static interface IDocument {
		public String getId();

		public String getUrl();

		public String getTitle();

		public String getName();

		public String getContent();

		public Map<String, Object> getMetaData();

		public boolean isEmpty();

		public Document toDocument();
	}

	public String getConsolidatedHistory();

	public List<IChatSessionEntry> getInteractions();

	public List<Document> getDocuments();

	public String getActualUserRequest();

	public Map<String, Object> getToolsContext();

	public default IChatRequestContext integrateWithDocuments(Object documents) {
		if (documents == null) {
			return this;
		} else if (documents instanceof String text) {
			if (text.trim().length() == 0) {
				return this;
			}
			List<Document> docs = getDocuments();
			if (docs == null)
				docs = new ArrayList<>();
			else
				docs = new ArrayList<>(docs);
			Document doc = new Document(UUID.randomUUID().toString(), text, new HashMap<>());
			docs.add(doc);
			return this.cloneWithNewDocumentsList(docs);
		} else if (documents instanceof Collection collection) {
			List<Document> docs = getDocuments();
			if (docs == null)
				docs = new ArrayList<>();
			else
				docs = new ArrayList<>(docs);
			for (Object intern : collection) {
				if (intern instanceof Document doc) {
					docs.add(doc);
				} else if (intern instanceof String text && text.trim().length() > 0) {
					Document doc = new Document(UUID.randomUUID().toString(), text, new HashMap<>());
					docs.add(doc);
				} else if (intern instanceof IDocument doc) {
					if (!doc.isEmpty()) {
						docs.add(doc.toDocument());
					}
				} else
					throw new RuntimeException(
							"Tye type: " + intern.getClass().getName() + " is not supported as document param");
			}
			return cloneWithNewDocumentsList(docs);
		} else if (documents instanceof Document doc) {
			List<Document> docs = getDocuments();
			if (docs == null)
				docs = new ArrayList<>();
			else
				docs = new ArrayList<>(docs);
			docs.add(doc);
			return cloneWithNewDocumentsList(docs);
		} else if (documents instanceof IDocument doc) {
			if (doc.isEmpty())
				return this;
			List<Document> docs = getDocuments();
			if (docs == null)
				docs = new ArrayList<>();
			else
				docs = new ArrayList<>(docs);
			docs.add(doc.toDocument());
			return cloneWithNewDocumentsList(docs);
		}
		throw new RuntimeException(
				"Tye type: " + documents.getClass().getName() + " is not supported as document param");
	}

	public default IChatRequestContext cloneWithNewDocumentsList(List<Document> docs) {
		final List<Document> sampledList = docs != null ? new ArrayList<>(docs) : new ArrayList<>();
		return new IChatRequestContext() {
			@Override
			public String getActualUserRequest() {

				return IChatRequestContext.this.getActualUserRequest();
			}

			@Override
			public String getConsolidatedHistory() {

				return IChatRequestContext.this.getConsolidatedHistory();
			}

			@Override
			public List<Document> getDocuments() {

				return sampledList;
			}

			@Override
			public List<IChatSessionEntry> getInteractions() {

				return IChatRequestContext.this.getInteractions();
			}

			@Override
			public Map<String, Object> getToolsContext() {

				return IChatRequestContext.this.getToolsContext();
			}
		};
	}

	public static IChatRequestContext of(String userQuery) {
		return ChatRequestContextImpl.builder().actualUserRequest(userQuery).build();
	}

	@AllArgsConstructor
	@Getter
	@Builder
	public static class ChatRequestContextImpl implements IChatRequestContext {
		private final String actualUserRequest;
		private final String consolidatedHistory;
		private final List<IChatSessionEntry> interactions;
		private final List<Document> documents;
		private final Map<String, Object> toolsContext;
	}

	public static ChatRequestContextImplBuilder builder() {
		return ChatRequestContextImpl.builder();
	}

}
