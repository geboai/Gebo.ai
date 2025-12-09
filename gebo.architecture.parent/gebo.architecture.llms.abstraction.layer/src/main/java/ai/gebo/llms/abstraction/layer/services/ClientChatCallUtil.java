package ai.gebo.llms.abstraction.layer.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;

import ai.gebo.llms.abstraction.layer.model.IChatContext;
import ai.gebo.llms.abstraction.layer.model.IQuestionAnswerEntry;
import ai.gebo.model.DocumentMetaInfos;

public class ClientChatCallUtil {
	private static final String END_CONTENT = "====END-CONTENT====";
	private static final String BEGIN_CONTENT = "====BEGIN-CONTENT====";
	private static final String END_META_BLOCK = "===ENDMETA===";
	private static final String META_BLOCK = "===META===";
	private static final String END_DOCUMENTS = "END DOCUMENTS";
	private static final String BEGIN_DOCUMENTS = "BEGIN DOCUMENTS";
	private static final String NEWLINE = "\r\n";
	public static String createPromptAndContext(Prompt prompt, IChatContext chatContext) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(prompt.toString());
		List<Document> documents = chatContext.getDocuments();
		if (documents != null && documents.size() > 0) {
			buffer.append(BEGIN_DOCUMENTS);
			buffer.append(NEWLINE);
			for (Document document : documents) {
				String text = renderDocument(document);
				buffer.append(text);
			}
	
			buffer.append(END_DOCUMENTS);
			buffer.append(NEWLINE);
		}
		String consolidated = chatContext.getConsolidatedHistory();
		if (consolidated != null) {
			buffer.append(CONVERSATION_SUMMARY_SO_FAR);
			buffer.append(consolidated);
			buffer.append(NEWLINE);
		}
		return buffer.toString();
	}

	public static List<Message> getChatHistory(IChatContext chatContext) {
		List<Message> message_list = new ArrayList<>();
		List<IQuestionAnswerEntry> interactions = chatContext.getInteractions();
		if (interactions != null) {
			for (IQuestionAnswerEntry chatInteraction : interactions) {
				String request = chatInteraction.getUser();
				String assistant = chatInteraction.getAssistant();
				if (request != null) {
					UserMessage _request = new UserMessage(request);
					message_list.add(_request);
				}
	
				if (assistant != null) {
					AssistantMessage _response = new AssistantMessage(assistant);
					message_list.add(_response);
				}
			}
		}
		return message_list;
	}

	/**
	 * Renders document data into a string format suitable for a message.
	 *
	 * @param document The document to be rendered
	 * @return A string representation of the document's data
	 */
	public static String renderDocument(Document document) {
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
				data.append(META_BLOCK);
				data.append(NEWLINE);
				meta.forEach((k, v) -> {
					if (v != null && !v.toString().isBlank())
						data.append(k).append(": ").append(v).append(NEWLINE);
				});
				data.append(END_META_BLOCK); // Blank line separates the body
				data.append(NEWLINE);
			}
		}
		if (document.getText() != null && document.getText().trim().length() > 0) {
			data.append(BEGIN_CONTENT);
			data.append(NEWLINE);
			data.append(document.getText());
			data.append(NEWLINE);
			data.append(END_CONTENT);
			data.append(NEWLINE);
		}
		return data.toString();
	}

	private static final String CONVERSATION_SUMMARY_SO_FAR = "Conversation summary so far:";

	

}
