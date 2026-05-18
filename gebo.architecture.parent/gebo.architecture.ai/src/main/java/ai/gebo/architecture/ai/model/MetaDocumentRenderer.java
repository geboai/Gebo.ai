package ai.gebo.architecture.ai.model;

import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class MetaDocumentRenderer {
	private final String id;
	private final String documentCode;
	private final String title;
	private final String name;
	private final String url;
	private final String project;
	private final String knowledgeBase;
	private final String content;
	private final List<MetaDocumentCategory> categories;

	public String render() {
		StringBuilder buffer = new StringBuilder(4096);

		buffer.append("<document>\n");

		appendField(buffer, "id", id);
		appendField(buffer, "documentCode", documentCode);
		appendField(buffer, "title", title);
		appendField(buffer, "name", name);
		appendField(buffer, "url", url);
		appendField(buffer, "knowledge-base", knowledgeBase);
		appendField(buffer, "project", project);
		appendCategories(buffer, categories);

		buffer.append("<content>\n");
		appendText(buffer, content!=null?content:"");
		buffer.append("\n</content>\n");

		buffer.append("</document>");

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