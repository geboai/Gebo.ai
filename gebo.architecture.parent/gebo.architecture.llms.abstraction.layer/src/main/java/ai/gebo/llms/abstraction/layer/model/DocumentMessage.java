package ai.gebo.llms.abstraction.layer.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.document.Document;

import ai.gebo.model.DocumentMetaInfos;

/**
 * Inner class representing a system message containing document data.
 */
public class DocumentMessage extends SystemMessage {

	/**
	 * Constructs a DocumentMessage based on the provided Document.
	 *
	 * @param document The document containing data to be rendered in a message
	 */
	public DocumentMessage(Document document) {
		super(renderData(document));
	}

	/**
	 * Renders document data into a string format suitable for a message.
	 *
	 * @param document The document to be rendered
	 * @return A string representation of the document's data
	 */
	private static String renderData(Document document) {
		StringBuffer data = new StringBuffer();
		if (document.getMetadata() != null) {
			String id = document.getId();
			String title = (String) document.getMetadata().get(DocumentMetaInfos.TITLE);
			String subtitle = (String) document.getMetadata().get(DocumentMetaInfos.SUBTITLE);
			// Metadata includes categorization and cataloging criteria
			String metadata = (String) document.getMetadata().get(DocumentMetaInfos.GEBO_EMBEDDING_METADATA);
			String code = (String) document.getMetadata().get(DocumentMetaInfos.CONTENT_CODE);
			Object page = document.getMetadata().get(DocumentMetaInfos.CONTENT_PAGE);

			Map<String, Object> meta = new LinkedHashMap<>();
			if (id != null)
				meta.put("fragment-id", id); // Always useful
			if (code != null)
				meta.put("content-code", code);
			if (title != null)
				meta.put("title", title);
			if (subtitle != null)
				meta.put("subtitle", subtitle);
			if (metadata != null)
				meta.put("tags", metadata);
			if (page != null)
				meta.put("page_hint", page);

			// Emit a block that starts with ===META=== and ends with ===ENDMETA===.
			if (!meta.isEmpty()) {
				data.append("===META===\n");
				meta.forEach((k, v) -> {
					if (v != null && !v.toString().isBlank())
						data.append(k).append(": ").append(v).append('\n');
				});
				data.append("===ENDMETA===\n\n"); // Blank line separates the body
			}
		}
		if (document.getText() != null) {
			data.append("====BEGIN-CONTENT====\n");
			data.append(document.getText());
			data.append("\n");
			data.append("====END-CONTENT====\n\n");
		}
		return data.toString();
	}
}