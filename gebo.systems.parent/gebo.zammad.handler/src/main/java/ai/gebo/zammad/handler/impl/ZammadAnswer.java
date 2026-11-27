/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.impl;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A knowledge base answer as {@code GET /knowledge_bases/{kb}/answers/{id}}
 * answers it: untyped assets by type and id, read here into the answer's own
 * dates and attachments and its translations with their bodies.
 */
public class ZammadAnswer {
	/** One translation of the answer: its title, its HTML body and its locale. */
	public record Translation(Long id, Long kbLocaleId, String title, String body) {
	}

	private final Long id;
	private final Map<String, Object> answer;
	private final List<Translation> translations = new ArrayList<Translation>();

	public ZammadAnswer(Long id, Map<String, Map<String, Object>> assets) {
		this.id = id;
		Map<String, Map<String, Object>> safe = assets != null ? assets : Map.of();
		Map<String, Object> self = entries(safe.get("KnowledgeBaseAnswer")).get(String.valueOf(id));
		this.answer = self != null ? self : Map.of();
		Map<String, Map<String, Object>> contents = entries(safe.get("KnowledgeBaseAnswerTranslationContent"));
		for (Map<String, Object> translation : entries(safe.get("KnowledgeBaseAnswerTranslation")).values()) {
			if (translation != null && Objects.equals(ZammadKnowledgeBaseIndex.longValue(translation.get("answer_id")), id)) {
				Map<String, Object> content = contents.get(String.valueOf(translation.get("content_id")));
				translations.add(new Translation(ZammadKnowledgeBaseIndex.longValue(translation.get("id")),
						ZammadKnowledgeBaseIndex.longValue(translation.get("kb_locale_id")), string(translation.get("title")),
						content != null ? string(content.get("body")) : null));
			}
		}
		translations.sort(Comparator.comparing(Translation::id, Comparator.nullsLast(Comparator.naturalOrder())));
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Map<String, Object>> entries(Object value) {
		return value instanceof Map<?, ?> map ? (Map<String, Map<String, Object>>) map : Map.of();
	}

	private static String string(Object value) {
		return value != null ? value.toString() : null;
	}

	public Long getId() {
		return id;
	}

	public Long categoryId() {
		return ZammadKnowledgeBaseIndex.longValue(answer.get("category_id"));
	}

	public List<Translation> translations() {
		return translations;
	}

	public String publishedAt() {
		return string(answer.get("published_at"));
	}

	public String internalAt() {
		return string(answer.get("internal_at"));
	}

	public String archivedAt() {
		return string(answer.get("archived_at"));
	}

	public OffsetDateTime updatedAt() {
		String value = string(answer.get("updated_at"));
		try {
			return value != null ? OffsetDateTime.parse(value) : null;
		} catch (RuntimeException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> attachments() {
		List<Map<String, Object>> attachments = new ArrayList<Map<String, Object>>();
		if (answer.get("attachments") instanceof List<?> list) {
			for (Object x : list) {
				if (x instanceof Map<?, ?> map && map.get("id") != null) {
					attachments.add((Map<String, Object>) map);
				}
			}
		}
		return attachments;
	}

	@Override
	public String toString() {
		return "{id=" + id + ", category=" + categoryId() + ", published=" + publishedAt() + ", internal="
				+ internalAt() + ", archived=" + archivedAt() + ", translations="
				+ translations.stream().map(Translation::title).toList() + ", attachments=" + attachments().size() + "}";
	}
}
