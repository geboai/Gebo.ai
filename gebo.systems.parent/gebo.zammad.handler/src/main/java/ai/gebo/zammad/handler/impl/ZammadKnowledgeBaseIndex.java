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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ai.gebo.zammad.client.model.Attachment;
import ai.gebo.zammad.client.model.KnowledgeBase;
import ai.gebo.zammad.client.model.KnowledgeBaseAnswer;
import ai.gebo.zammad.client.model.KnowledgeBaseCategory;
import ai.gebo.zammad.client.model.KnowledgeBaseInitResult;

/**
 * The knowledge bases of a Zammad server as its knowledge base overview
 * ({@code POST /knowledge_bases/init}) describes them: the typed knowledge
 * bases, categories and answers of the client, with the translations (titles)
 * the client keeps as additional properties. Titles are those of the primary
 * locale of each knowledge base, else of any locale.
 * <p>
 * Only the published and the internal answers are listed: drafts and archived
 * answers are not.
 */
public class ZammadKnowledgeBaseIndex {
	private final Map<Long, KnowledgeBase> knowledgeBases = new LinkedHashMap<Long, KnowledgeBase>();
	private final Map<Long, KnowledgeBaseCategory> categories = new LinkedHashMap<Long, KnowledgeBaseCategory>();
	private final Map<Long, KnowledgeBaseAnswer> answers = new LinkedHashMap<Long, KnowledgeBaseAnswer>();
	private final Map<String, Map<String, Object>> knowledgeBaseTranslations;
	private final Map<String, Map<String, Object>> categoryTranslations;
	private final Map<String, Map<String, Object>> answerTranslations;
	private final Map<String, Map<String, Object>> locales;

	public ZammadKnowledgeBaseIndex(KnowledgeBaseInitResult init) {
		if (init != null) {
			putAll(init.getKnowledgeBase(), knowledgeBases, KnowledgeBase::getId);
			putAll(init.getKnowledgeBaseCategory(), categories, KnowledgeBaseCategory::getId);
			putAll(init.getKnowledgeBaseAnswer(), answers, KnowledgeBaseAnswer::getId);
		}
		knowledgeBaseTranslations = assets(init, "KnowledgeBaseTranslation");
		categoryTranslations = assets(init, "KnowledgeBaseCategoryTranslation");
		answerTranslations = assets(init, "KnowledgeBaseAnswerTranslation");
		locales = assets(init, "KnowledgeBaseLocale");
	}

	private static <T> void putAll(Map<String, T> source, Map<Long, T> target, java.util.function.Function<T, Long> id) {
		if (source != null) {
			source.values().stream().filter(Objects::nonNull).filter(x -> id.apply(x) != null)
					.forEach(x -> target.put(id.apply(x), x));
		}
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Map<String, Object>> assets(KnowledgeBaseInitResult init, String type) {
		Object value = init != null && init.getAdditionalProperties() != null ? init.getAdditionalProperty(type) : null;
		return value instanceof Map<?, ?> map ? (Map<String, Map<String, Object>>) map : Map.of();
	}

	static Long longValue(Object value) {
		if (value instanceof Number n) {
			return n.longValue();
		}
		if (value != null) {
			try {
				return Long.valueOf(value.toString());
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}

	public List<KnowledgeBase> getKnowledgeBases() {
		return new ArrayList<KnowledgeBase>(knowledgeBases.values());
	}

	public boolean hasKnowledgeBase(Long knowledgeBaseId) {
		return knowledgeBases.containsKey(knowledgeBaseId);
	}

	/** Whether a kb_locale is the primary locale of its knowledge base. */
	private boolean isPrimaryLocale(Object kbLocaleId) {
		Map<String, Object> locale = locales.get(String.valueOf(kbLocaleId));
		return locale != null && Boolean.TRUE.equals(locale.get("primary"));
	}

	/** The title of the primary locale's translation among those matching, else the first one's. */
	private String title(Map<String, Map<String, Object>> translations, String ownerKey, Long ownerId) {
		String any = null;
		for (Map<String, Object> translation : translations.values()) {
			if (translation != null && Objects.equals(longValue(translation.get(ownerKey)), ownerId)) {
				Object title = translation.get("title");
				if (title != null) {
					if (isPrimaryLocale(translation.get("kb_locale_id"))) {
						return title.toString();
					}
					if (any == null) {
						any = title.toString();
					}
				}
			}
		}
		return any;
	}

	public String knowledgeBaseTitle(Long knowledgeBaseId) {
		return title(knowledgeBaseTranslations, "knowledge_base_id", knowledgeBaseId);
	}

	public String categoryTitle(Long categoryId) {
		return title(categoryTranslations, "category_id", categoryId);
	}

	public String answerTitle(Long answerId) {
		return title(answerTranslations, "answer_id", answerId);
	}

	public KnowledgeBaseCategory category(Long categoryId) {
		return categories.get(categoryId);
	}

	public KnowledgeBaseAnswer answerOf(Long answerId) {
		return answers.get(answerId);
	}

	private static final Comparator<Integer> POSITIONS = Comparator.nullsLast(Comparator.naturalOrder());

	/**
	 * The categories of a knowledge base below a parent category, the top ones
	 * for a null parent, by position.
	 */
	public List<KnowledgeBaseCategory> categoriesOf(Long knowledgeBaseId, Long parentCategoryId) {
		return categories.values().stream().filter(c -> Objects.equals(c.getKnowledgeBaseId(), knowledgeBaseId))
				.filter(c -> Objects.equals(c.getParentId(), parentCategoryId))
				.sorted(Comparator.comparing(KnowledgeBaseCategory::getPosition, POSITIONS)
						.thenComparing(KnowledgeBaseCategory::getId))
				.toList();
	}

	/** Published or internal, not archived: the answers that are part of the tree. */
	public static boolean isListed(KnowledgeBaseAnswer answer) {
		return answer != null && answer.getArchivedAt() == null
				&& (answer.getPublishedAt() != null || answer.getInternalAt() != null);
	}

	/**
	 * The listed answers of a category, by position.
	 */
	public List<KnowledgeBaseAnswer> answersOf(Long categoryId) {
		return answers.values().stream().filter(a -> Objects.equals(a.getCategoryId(), categoryId))
				.filter(ZammadKnowledgeBaseIndex::isListed)
				.sorted(Comparator.comparing(KnowledgeBaseAnswer::getPosition, POSITIONS)
						.thenComparing(KnowledgeBaseAnswer::getId))
				.toList();
	}

	/** The ids of the translations of an answer (one per locale). */
	public List<Long> answerTranslationIds(Long answerId) {
		KnowledgeBaseAnswer answer = answers.get(answerId);
		List<Long> ids = answer != null && answer.getTranslationIds() != null
				? new ArrayList<Long>(answer.getTranslationIds().stream().filter(Objects::nonNull).toList())
				: new ArrayList<Long>();
		if (ids.isEmpty()) {
			for (Map<String, Object> translation : answerTranslations.values()) {
				if (translation != null && Objects.equals(longValue(translation.get("answer_id")), answerId)) {
					Long id = longValue(translation.get("id"));
					if (id != null) {
						ids.add(id);
					}
				}
			}
		}
		return ids;
	}

	/** The answer a translation belongs to, null when the translation is unknown. */
	public Long answerIdOfTranslation(Long translationId) {
		Map<String, Object> translation = answerTranslations.get(String.valueOf(translationId));
		return translation != null ? longValue(translation.get("answer_id")) : null;
	}

	/**
	 * The attachments of an answer, as the overview lists them, in the untyped
	 * form of the answer assets: id, filename, size, preferences.
	 */
	public List<Map<String, Object>> answerAttachments(Long answerId) {
		KnowledgeBaseAnswer answer = answers.get(answerId);
		List<Map<String, Object>> attachments = new ArrayList<Map<String, Object>>();
		if (answer != null && answer.getAttachments() != null) {
			for (Attachment attachment : answer.getAttachments()) {
				if (attachment != null && attachment.getId() != null) {
					Map<String, Object> map = new LinkedHashMap<String, Object>();
					map.put("id", attachment.getId());
					map.put("filename", attachment.getFilename());
					map.put("size", attachment.getSize());
					map.put("preferences", attachment.getPreferences());
					attachments.add(map);
				}
			}
		}
		return attachments;
	}

	public OffsetDateTime answerUpdatedAt(Long answerId) {
		KnowledgeBaseAnswer answer = answers.get(answerId);
		return answer == null ? null : answer.getUpdatedAt() != null ? answer.getUpdatedAt() : answer.getCreatedAt();
	}

	/**
	 * The path of a category from the top of its knowledge base, e.g. "Install /
	 * Network".
	 */
	public String categoryPath(Long categoryId) {
		List<String> titles = new ArrayList<String>();
		java.util.Set<Long> visited = new java.util.HashSet<Long>();
		// the visited set only stops a parent cycle in corrupted data
		for (Long current = categoryId; current != null && visited.add(current);) {
			KnowledgeBaseCategory category = categories.get(current);
			if (category == null) {
				break;
			}
			String title = categoryTitle(current);
			titles.add(0, title != null ? title : "Category " + current);
			current = category.getParentId();
		}
		return String.join(" / ", titles);
	}

	@Override
	public String toString() {
		return "{knowledgeBases=" + knowledgeBases.keySet() + ", categories=" + categories.size() + ", answers="
				+ answers.size() + "}";
	}
}
