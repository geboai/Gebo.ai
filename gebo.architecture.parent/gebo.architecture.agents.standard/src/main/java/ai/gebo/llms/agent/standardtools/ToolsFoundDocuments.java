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
import java.util.List;
import java.util.Map;

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
 * Without a collector in the tools context (an agent that does not share one) the
 * tools still answer, their documents being only cited in the answer.
 */
public final class ToolsFoundDocuments {
	/** Tools context key carrying the collector. */
	public static final String TOOLS_CONTEXT_KEY = "geboToolsFoundDocuments";
	private final Map<String, GResponseDocumentRef> byCode = new LinkedHashMap<>();

	/** Records the documents, once per document code. */
	public synchronized void add(Collection<GResponseDocumentRef> refs) {
		if (refs == null) {
			return;
		}
		for (GResponseDocumentRef ref : refs) {
			if (ref != null && ref.getDocumentCode() != null) {
				byCode.putIfAbsent(ref.getDocumentCode(), ref);
			}
		}
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
		final List<GResponseDocumentRef> collected = getDocuments();
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
