/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.services.prompts;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import ai.gebo.llms.abstraction.layer.model.GChatRunInfo;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGPromptContextProvider;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;

/**
 * The run, for the prompts that name it: {@code {runOrigin}} (CHAT, HARNESS,
 * A2A, MCP...), {@code {attended}} (yes when a person waits for the answer)
 * and {@code {agentDeliverableCompleteness}}, what the deliverable the user
 * expects asks of the answer, as the pipelines always gave it.
 */
@Component
public class GRunPromptContextProvider implements IGPromptContextProvider {
	public static final String RUN_ORIGIN = "runOrigin";
	public static final String ATTENDED = "attended";
	public static final String AGENT_DELIVERABLE_COMPLETENESS = "agentDeliverableCompleteness";
	static final String USER_INTENT_PIPELINE_INFO = "user-intent";

	@Override
	public Set<String> getPlaceholders() {
		return Set.of(RUN_ORIGIN, ATTENDED, AGENT_DELIVERABLE_COMPLETENESS);
	}

	@Override
	public Map<String, Object> valuesFor(Set<String> asked, IChatRequestContext context) {
		final Map<String, Object> values = new HashMap<String, Object>();
		final GChatRunInfo run = context != null ? context.getRunInfo() : null;
		if (asked.contains(RUN_ORIGIN)) {
			values.put(RUN_ORIGIN, run != null && run.origin() != null ? run.origin() : "UNKNOWN");
		}
		if (asked.contains(ATTENDED)) {
			values.put(ATTENDED, run == null || run.isAttended() ? "yes" : "no");
		}
		if (asked.contains(AGENT_DELIVERABLE_COMPLETENESS)) {
			DeliverableIntent intent = DeliverableIntent.UNKNOWN;
			final Object named = context != null && context.getPipelineInfos() != null
					? context.getPipelineInfos().get(USER_INTENT_PIPELINE_INFO)
					: null;
			if (named instanceof String name) {
				try {
					intent = DeliverableIntent.valueOf(name);
				} catch (IllegalArgumentException unknown) {
					// an intent this version does not know: the unknown one, then
				}
			}
			values.put(AGENT_DELIVERABLE_COMPLETENESS, intent.getAgentDeliverableCompleteness());
		}
		return values;
	}
}
