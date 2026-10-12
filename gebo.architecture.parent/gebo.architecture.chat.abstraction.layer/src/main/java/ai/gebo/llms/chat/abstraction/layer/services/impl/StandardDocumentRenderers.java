package ai.gebo.llms.chat.abstraction.layer.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ai.gebo.knowledgebase.repositories.uniqueid.VirtualFilesystemUniqueIds;
import ai.gebo.architecture.ai.model.MetaDocumentRenderer;
import ai.gebo.architecture.ai.model.MetaDocumentRenderer.MetaDocumentRendererBuilder;
import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentFragment;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.model.DocumentMetaInfos;
import lombok.AllArgsConstructor;

public class StandardDocumentRenderers {
	@Service
	@Qualifier(IGDocumentContentRenderer.STANDARD_RENDERER)
	public static class StringDocumentContentRenderer implements IGDocumentContentRenderer<String> {
		@Override
		public String getId() {
			return "StringDocumentContentRenderer";
		}

		@Override
		public Class<String> getRenderedType() {

			return String.class;
		}

		@Override
		public boolean isCanRender(Object document) {

			return document == null || document instanceof String;
		}

		@Override
		public String render(String document) {

			return document != null ? document : "";
		}
	}

	@Service
	@Qualifier(IGDocumentContentRenderer.STANDARD_RENDERER)
	public static class DocumentDocumentContentRenderer implements IGDocumentContentRenderer<Document> {

		private VirtualFilesystemUniqueIds uniqueIds = null;

		@Autowired(required = false)
		public void setUniqueIds(VirtualFilesystemUniqueIds uniqueIds) {
			this.uniqueIds = uniqueIds;
		}

		@Override
		public String getId() {

			return "DocumentDocumentContentRenderer";
		}

		@Override
		public Class<Document> getRenderedType() {
			return Document.class;
		}

		@Override
		public boolean isCanRender(Object document) {

			return document instanceof Document;
		}

		@Override
		public String render(Document document) {
			MetaDocumentRendererBuilder builder = MetaDocumentRenderer.builder();
			builder.id(document.getId());
			builder.name(get(document, DocumentMetaInfos.GEBO_FILE_NAME));
			builder.title(get(document, DocumentMetaInfos.TITLE));
			builder.documentCode(get(document, DocumentMetaInfos.CONTENT_CODE));
			builder.documentUniqueId(documentUniqueId(document.getMetadata(), get(document, DocumentMetaInfos.CONTENT_CODE),
					uniqueIds));
			builder.knowledgeBase(get(document, DocumentMetaInfos.KNOWLEDGEBASE_CODE));
			builder.project(get(document, DocumentMetaInfos.PROJECT_CODE));
			builder.url(get(document, DocumentMetaInfos.CONTENT_ORIGINAL_URL));
			builder.content(document.getText());
			return builder.build().render();
		}

		private String get(Document document, String constant) {

			return document.getMetadata() != null && document.getMetadata().containsKey(constant)
					? document.getMetadata().get(constant).toString()
					: null;
		}

	}

	@Service
	@Qualifier(IGDocumentContentRenderer.STANDARD_RENDERER)
	public static class AIDocumentFragmentDocumentContentRenderer
			implements IGDocumentContentRenderer<AIDocumentFragment> {

		private VirtualFilesystemUniqueIds uniqueIds = null;

		@Autowired(required = false)
		public void setUniqueIds(VirtualFilesystemUniqueIds uniqueIds) {
			this.uniqueIds = uniqueIds;
		}

		@Override
		public String getId() {

			return "AIDocumentFragmentDocumentContentRenderer";
		}

		@Override
		public Class<AIDocumentFragment> getRenderedType() {

			return AIDocumentFragment.class;
		}

		@Override
		public boolean isCanRender(Object document) {

			return document instanceof AIDocumentFragment;
		}

		@Override
		public String render(AIDocumentFragment document) {

			MetaDocumentRendererBuilder builder = MetaDocumentRenderer.builder();
			builder.id(document.getDocumentId());
			builder.name(get(document, DocumentMetaInfos.GEBO_FILE_NAME));
			builder.title(get(document, DocumentMetaInfos.TITLE));
			builder.documentCode(document.getCode());
			builder.documentUniqueId(documentUniqueId(document.getMetaData(), document.getCode(), uniqueIds));
			builder.knowledgeBase(get(document, DocumentMetaInfos.KNOWLEDGEBASE_CODE));
			builder.project(get(document, DocumentMetaInfos.PROJECT_CODE));
			builder.url(get(document, DocumentMetaInfos.CONTENT_ORIGINAL_URL));
			builder.content(document.getDocumentContent());
			return builder.build().render();
		}

		private String get(AIDocumentFragment document, String field) {

			return document.getMetaData() != null && document.getMetaData().containsKey(field)
					? document.getMetaData().get(field).toString()
					: null;
		}

	}

	@Service
	@Qualifier(IGDocumentContentRenderer.STANDARD_RENDERER)
	public static class AIDocumentDocumentContentRenderer
			implements IGDocumentContentRenderer<AIDocumentReferenceItem> {

		private VirtualFilesystemUniqueIds uniqueIds = null;

		@Autowired(required = false)
		public void setUniqueIds(VirtualFilesystemUniqueIds uniqueIds) {
			this.uniqueIds = uniqueIds;
		}

		private static final String NEWLINE = "\r\n";

		@Override
		public String getId() {

			return "AIDocumentDocumentContentRenderer";
		}

		@Override
		public Class<AIDocumentReferenceItem> getRenderedType() {
			return AIDocumentReferenceItem.class;
		}

		@Override
		public boolean isCanRender(Object document) {

			return document instanceof AIDocumentReferenceItem;
		}

		@Override
		public String render(AIDocumentReferenceItem document) {
			MetaDocumentRendererBuilder builder = MetaDocumentRenderer.builder();
			builder.id(document.getCode());
			builder.name(document.getName());
			builder.title(get(document, DocumentMetaInfos.TITLE));
			builder.documentCode(document.getCode());
			builder.documentUniqueId(documentUniqueId(null, document.getCode(), uniqueIds));
			builder.knowledgeBase(get(document, DocumentMetaInfos.KNOWLEDGEBASE_CODE));
			builder.project(get(document, DocumentMetaInfos.PROJECT_CODE));
			builder.url(get(document, DocumentMetaInfos.CONTENT_ORIGINAL_URL));
			StringBuffer buffer = new StringBuffer();
			for (AIDocumentFragment doc : document.getFragments()) {
				if (doc.getDocumentContent() != null) {
					buffer.append(doc.getDocumentContent());
					buffer.append(NEWLINE);
				}
			}
			builder.content(buffer.toString());
			return builder.build().render();
		}

		private String get(AIDocumentReferenceItem document, String field) {

			return document.getFragments() != null && !document.getFragments().isEmpty()
					&& document.getFragments().get(0).getMetaData() != null
					&& document.getFragments().get(0).getMetaData().containsKey(field)
							? document.getFragments().get(0).getMetaData().get(field).toString()
							: null;
		}

	}

	@Service
	@Qualifier(IGDocumentContentRenderer.STANDARD_RENDERER)
	@AllArgsConstructor
	public static class AIDocumentsSetDocumentContentRenderer implements IGDocumentContentRenderer<AIDocumentsSet> {
		private static final String NEWLINE = "\r\n";
		private final AIDocumentDocumentContentRenderer docRenderer;
		@Override
		public String getId() {
			
			return "AIDocumentsSetDocumentContentRenderer";
		}

		@Override
		public Class<AIDocumentsSet> getRenderedType() {
			return AIDocumentsSet.class;
		}

		@Override
		public boolean isCanRender(Object document) {
			
			return document instanceof AIDocumentsSet;
		}

		@Override
		public String render(AIDocumentsSet document) {
			StringBuffer buffer=new StringBuffer();
			for(AIDocumentReferenceItem doc:document.getDocumentItems()) {
				buffer.append(docRenderer.render(doc));
				buffer.append(NEWLINE);
			}
			return buffer.toString();
		}

	}

	/**
	 * The uniqueId of the document a content comes from: the one ingested with it, else
	 * the one stored for its document code (contents ingested before the uniqueId
	 * existed); null when neither is known.
	 */
	static Long documentUniqueId(java.util.Map<String, Object> metadata, String documentCode,
			VirtualFilesystemUniqueIds uniqueIds) {
		final Object value = metadata != null ? metadata.get(DocumentMetaInfos.GEBO_UNIQUE_ID) : null;
		if (value instanceof Number number) {
			return number.longValue();
		}
		if (value instanceof String text && !text.isBlank()) {
			try {
				return (long) Double.parseDouble(text.trim());
			} catch (NumberFormatException e) {
				// resolved from the document code below
			}
		}
		if (uniqueIds == null || documentCode == null) {
			return null;
		}
		try {
			return uniqueIds.documentUniqueId(documentCode);
		} catch (RuntimeException e) {
			return null;
		}
	}
}
