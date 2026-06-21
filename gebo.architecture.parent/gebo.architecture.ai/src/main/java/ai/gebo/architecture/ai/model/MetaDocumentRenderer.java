package ai.gebo.architecture.ai.model;

import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class MetaDocumentRenderer {
	public static final String END_CONTENT = "\n</content>\n";
	public static final String BEGIN_CONTENT = "<content>\n";
	public static final String PUBLISHED_DATE = "publishedDate";
	public static final String AUTHOR = "author";
	public static final String PROJECT = "project";
	public static final String KNOWLEDGE_BASE = "knowledge-base";
	public static final String URL = "url";
	public static final String NAME = "name";
	public static final String TITLE = "title";
	public static final String DOCUMENT_CODE = "documentCode";
	public static final String FRAGMENT_ID = "fragmentId";
	public static final String END_DOCUMENT = "</document>";
	public static final String BEGIN_DOCUMENT = "<document>\n";
	private final String id;
	private final String documentCode;
	private final String title;
	private final String name;
	private final String url;
	private final String project;
	private final String knowledgeBase;
	private final String content;
	private final String author;
	private final String publishedDate;
	private final List<MetaDocumentCategory> categories;
	private final TreeMap<String, String> customFields = new TreeMap<>();

	public void addCustomField(String field, String value) {
		customFields.put(field, value);
	}

	public String render() {
		StringBuilder buffer = new StringBuilder(4096);

		buffer.append(BEGIN_DOCUMENT);

		appendField(buffer, FRAGMENT_ID, id);
		appendField(buffer, DOCUMENT_CODE, documentCode);
		appendField(buffer, TITLE, title);
		appendField(buffer, NAME, name);
		appendField(buffer, URL, url);
		appendField(buffer, KNOWLEDGE_BASE, knowledgeBase);
		appendField(buffer, PROJECT, project);
		appendField(buffer, AUTHOR, author);
		appendField(buffer, PUBLISHED_DATE, publishedDate);
		for (Entry<String, String> entry : customFields.entrySet()) {
			appendField(buffer, entry.getKey(), entry.getValue());
		}
		appendCategories(buffer, categories);

		buffer.append(BEGIN_CONTENT);
		appendText(buffer, content != null ? content : "");
		buffer.append(END_CONTENT);
		buffer.append(END_DOCUMENT);

		return buffer.toString();
	}

	private static void appendCategories(StringBuilder buffer, List<MetaDocumentCategory> categories) {
		if (categories == null || categories.isEmpty()) {
			return;
		}

		boolean opened = false;

		for (MetaDocumentCategory category : categories) {
			if (category == null) {
				continue;
			}

			String categoryName = safeTrim(category.getCategoryName());
			String categoryValue = safeTrim(category.getCategoryValue());

			if (categoryName.isBlank() && categoryValue.isBlank()) {
				continue;
			}

			if (!opened) {
				buffer.append("<categories>\n");
				opened = true;
			}

			buffer.append("- ");

			if (!categoryName.isBlank()) {
				buffer.append(categoryName);
			} else {
				buffer.append("unknown");
			}

			buffer.append(": ");

			if (!categoryValue.isBlank()) {
				buffer.append(categoryValue);
			}

			buffer.append("\n");
		}

		if (opened) {
			buffer.append("</categories>\n");
		}
	}

	private static void appendField(StringBuilder buffer, String fieldName, String value) {
		String safeValue = safeTrim(value);

		if (!safeValue.isBlank()) {
			buffer.append(fieldName).append(": ").append(safeValue).append("\n");
		}
	}

	private static void appendText(StringBuilder buffer, String value) {
		String safeValue = safeTrim(value);

		if (!safeValue.isBlank()) {
			buffer.append(safeValue);
		}
	}

	private static String safeTrim(String value) {
		return Objects.toString(value, "").trim();
	}
}