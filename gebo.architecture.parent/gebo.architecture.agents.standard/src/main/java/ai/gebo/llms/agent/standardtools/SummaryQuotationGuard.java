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
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener.ToolCallExecuted;
import ai.gebo.llms.deepsearch.service.impl.DeepSearchQuotations;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 * Which quotations of an answer to keep, best effort: a deep search's analysis is a
 * summary written by a model, and an answer may put its sentences between quotation
 * marks as if they were a document's words. A quotation the summaries of the
 * request's deep searches have, and no document the tools returned (nor a deep search's
 * verified quotations), is not kept as a quotation; any other is left as it is.
 */
public final class SummaryQuotationGuard {
	private static final Logger LOGGER = LoggerFactory.getLogger(SummaryQuotationGuard.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private final ToolCallsListener calls;
	private final Set<String> deepSearchTools;
	/** The calls already read. */
	private int read = 0;
	private final List<String> summaries = new ArrayList<>();
	private final List<String> documents = new ArrayList<>();

	public SummaryQuotationGuard(ToolCallsListener calls, Set<String> deepSearchTools) {
		this.calls = calls;
		this.deepSearchTools = deepSearchTools != null ? deepSearchTools : Set.of();
	}

	/** Whether the quoted words are kept as a quotation. */
	public synchronized boolean keep(String words) {
		readNewCalls();
		boolean inSummary = false;
		for (String summary : summaries) {
			if (DeepSearchQuotations.contains(summary, words)) {
				inSummary = true;
				break;
			}
		}
		if (!inSummary) {
			return true;
		}
		for (String document : documents) {
			if (DeepSearchQuotations.contains(document, words)) {
				return true;
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("keep(...) a deep search summary's words quoted, no document has them: not a quotation: "
					+ (words.length() <= 80 ? words : words.substring(0, 80) + "…"));
		}
		return false;
	}

	private void readNewCalls() {
		if (calls == null) {
			return;
		}
		final List<ToolCallExecuted> executed = calls.getCalls();
		for (int i = read; i < executed.size(); i++) {
			final ToolCallExecuted call = executed.get(i);
			if (call == null || call.getResult() == null) {
				continue;
			}
			if (deepSearchTools.contains(call.getName())) {
				final JsonNode result = parsed(call.getResult());
				if (result == null) {
					continue;
				}
				final JsonNode analysis = result.get("analysis");
				if (analysis != null && analysis.isString()) {
					summaries.add(DeepSearchQuotations.normalized(analysis.asString()));
				}
				final JsonNode quotes = result.get("quotes");
				if (quotes != null && quotes.isArray()) {
					for (JsonNode quote : quotes) {
						final JsonNode text = quote.get("text");
						if (text != null && text.isString()) {
							documents.add(DeepSearchQuotations.normalized(text.asString()));
						}
					}
				}
			} else {
				final JsonNode result = parsed(call.getResult());
				documents.add(DeepSearchQuotations.normalized(result != null ? texts(result) : call.getResult()));
			}
		}
		read = executed.size();
	}

	private static JsonNode parsed(String json) {
		try {
			return MAPPER.readTree(json);
		} catch (RuntimeException e) {
			// a result cut to the room, or plain text: read as text
			return null;
		}
	}

	/** Every text value of a tool result, joined. */
	private static String texts(JsonNode node) {
		final StringBuilder out = new StringBuilder();
		collect(node, out);
		return out.toString();
	}

	private static void collect(JsonNode node, StringBuilder out) {
		if (node == null) {
			return;
		}
		if (node.isString()) {
			out.append(node.asString()).append('\n');
		} else if (node.isContainer()) {
			for (JsonNode child : node) {
				collect(child, out);
			}
		}
	}
}
