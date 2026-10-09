/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.ai.chat.model.ToolContext;

import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;

/**
 * Collects the documents the search tools returned and the deep search tools relied
 * on, for the agent that called them: the agent shares the collector through the
 * tools context of its model calls ({@link #sharedThrough(IChatRequestContext)}), the
 * tools add their documents, and the agent gives them as its answer's documents, so
 * the user sees them among the documents found.
 * <p>
 * It also keeps the names of the documents a tool listed without reading them (the
 * knowledge base documents listing): an answer naming them rests on what this request
 * returned, though they are not documents found for it.
 * <p>
 * Without a collector in the tools context (an agent that does not share one) the
 * tools still answer, their documents being only cited in the answer.
 */
public final class ToolsFoundDocuments {
	/** Tools context key carrying the collector. */
	public static final String TOOLS_CONTEXT_KEY = "geboToolsFoundDocuments";
	/** The prefix of a document's id in the request: "#1", "#2"... */
	public static final String ID_PREFIX = "#";
	private final Map<String, GResponseDocumentRef> byCode = new LinkedHashMap<>();
	private final Set<String> listedNames = new LinkedHashSet<>();
	/** The id of each document in the request, by document code, in the order they came. */
	private final Map<String, String> idsByCode = new LinkedHashMap<>();
	/** The ids of the documents the answer says it rests on; null while it said none. */
	private List<String> answerDocumentIds = null;
	/** Whether the answer needed the sources and was shown without any search. */
	private boolean answeredWithoutSearch = false;

	/** Records that the answer needed the sources and was shown without any search. */
	public synchronized void markAnsweredWithoutSearch() {
		answeredWithoutSearch = true;
	}

	public synchronized boolean isAnsweredWithoutSearch() {
		return answeredWithoutSearch;
	}

	/**
	 * Records the documents, once per document code: each new one gets the next short
	 * id of the request ({@code #1}, {@code #2}...), the one the tools show the model
	 * and the model gives back to say which documents its answer rests on.
	 */
	public synchronized void add(Collection<GResponseDocumentRef> refs) {
		if (refs == null) {
			return;
		}
		for (GResponseDocumentRef ref : refs) {
			if (ref != null && ref.getDocumentCode() != null) {
				byCode.putIfAbsent(ref.getDocumentCode(), ref);
				idsByCode.computeIfAbsent(ref.getDocumentCode(), code -> ID_PREFIX + (idsByCode.size() + 1));
			}
		}
	}

	/** The short id of a document of the request, null when it was never recorded. */
	public synchronized String idOf(String documentCode) {
		return documentCode != null ? idsByCode.get(documentCode) : null;
	}

	/**
	 * Records the ids the answer gives as the documents it rests on (several answer
	 * iterations add theirs).
	 */
	public synchronized void addAnswerDocumentIds(Collection<String> ids) {
		if (answerDocumentIds == null) {
			answerDocumentIds = new ArrayList<>();
		}
		if (ids != null) {
			for (String id : ids) {
				if (id != null && !answerDocumentIds.contains(id)) {
					answerDocumentIds.add(id);
				}
			}
		}
	}

	/** The ids the answer gave as the documents it rests on; null when it gave none. */
	public synchronized List<String> getAnswerDocumentIds() {
		return answerDocumentIds != null ? new ArrayList<>(answerDocumentIds) : null;
	}

	/**
	 * The documents of the given ids, in the order of the ids; the ids no document has
	 * go to {@code unknown}.
	 */
	public synchronized List<GResponseDocumentRef> documentsOf(Collection<String> ids, List<String> unknown) {
		final Map<String, String> codesById = new LinkedHashMap<>();
		for (Map.Entry<String, String> entry : idsByCode.entrySet()) {
			codesById.put(entry.getValue(), entry.getKey());
		}
		final List<GResponseDocumentRef> documents = new ArrayList<>();
		for (String id : ids) {
			final String code = codesById.get(id);
			final GResponseDocumentRef ref = code != null ? byCode.get(code) : null;
			if (ref != null) {
				if (!documents.contains(ref)) {
					documents.add(ref);
				}
			} else if (unknown != null) {
				unknown.add(id);
			}
		}
		return documents;
	}

	/** Records the names of documents a tool listed without reading them. */
	public synchronized void addListed(Collection<String> names) {
		if (names == null) {
			return;
		}
		for (String name : names) {
			if (name != null && !name.isBlank()) {
				listedNames.add(name);
			}
		}
	}

	/** The names of the documents listed so far without being read. */
	public synchronized List<String> getListedNames() {
		return new ArrayList<>(listedNames);
	}

	/** The documents collected so far. */
	public synchronized List<GResponseDocumentRef> getDocuments() {
		return new ArrayList<>(byCode.values());
	}

	/**
	 * The given answer documents completed with the collected ones, once per document
	 * code, the answer's own first.
	 */
	public List<GResponseDocumentRef> mergeInto(List<GResponseDocumentRef> answerDocuments) {
		return mergeInto(answerDocuments, getDocuments());
	}

	/**
	 * The given answer documents completed with the given collected ones, once per
	 * document code, the answer's own first.
	 */
	public static List<GResponseDocumentRef> mergeInto(List<GResponseDocumentRef> answerDocuments,
			List<GResponseDocumentRef> collected) {
		if (collected.isEmpty()) {
			return answerDocuments;
		}
		final Map<String, GResponseDocumentRef> merged = new LinkedHashMap<>();
		final List<GResponseDocumentRef> withoutCode = new ArrayList<>();
		if (answerDocuments != null) {
			for (GResponseDocumentRef ref : answerDocuments) {
				if (ref == null) {
					continue;
				}
				if (ref.getDocumentCode() != null) {
					merged.putIfAbsent(ref.getDocumentCode(), ref);
				} else {
					withoutCode.add(ref);
				}
			}
		}
		for (GResponseDocumentRef ref : collected) {
			merged.putIfAbsent(ref.getDocumentCode(), ref);
		}
		final List<GResponseDocumentRef> out = new ArrayList<>(merged.values());
		out.addAll(withoutCode);
		return out;
	}

	/** The collector a tool context carries, or null. */
	public static ToolsFoundDocuments from(ToolContext toolContext) {
		if (toolContext == null || toolContext.getContext() == null) {
			return null;
		}
		return toolContext.getContext().get(TOOLS_CONTEXT_KEY) instanceof ToolsFoundDocuments collector
				? collector
				: null;
	}

	/**
	 * The given request context, its tools context also carrying this collector:
	 * every other value is still read from the given context.
	 */
	public IChatRequestContext sharedThrough(IChatRequestContext context) {
		return ToolsContextSharing.with(context, TOOLS_CONTEXT_KEY, this);
	}
}
