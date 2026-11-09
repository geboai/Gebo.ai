/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.conditionevents.model.GConditionEvent;
import ai.gebo.architecture.agents.conditionevents.services.IConditionEventTextRenderer;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionDto;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.NetworkOfAgentsSuspendedState;

/**
 * The text of a respawn started by events: what stands as the user's message of
 * the new interaction in the conversation's history. Each solved condition, in
 * the order the block asked them, says either what its source said, marked with
 * the source, or what its renderer makes of the condition and the value. The
 * agents are not told this text: the respawn tells each agent the values it
 * waited for.
 */
@Service
public class ConditionEventTexts {
	static final String NOTHING_SAID = "The conditions the work waited for were solved.";
	private final Map<String, IConditionEventTextRenderer> renderers = new HashMap<String, IConditionEventTextRenderer>();
	private final IConditionEventTextRenderer fallback = new DefaultConditionEventTextRenderer();

	public ConditionEventTexts(List<IConditionEventTextRenderer> renderers) {
		if (renderers != null) {
			for (IConditionEventTextRenderer renderer : renderers) {
				if (renderer != null && renderer.getConditionType() != null) {
					this.renderers.put(renderer.getConditionType(), renderer);
				}
			}
		}
	}

	/**
	 * @param state  the pending state of the block respawned
	 * @param events the events of the respawn
	 */
	public String queryOf(NetworkOfAgentsSuspendedState state, List<GConditionEvent> events) {
		final Map<String, GConditionEvent> byCondition = new HashMap<String, GConditionEvent>();
		for (GConditionEvent event : events) {
			byCondition.put(event.getConditionId(), event);
		}
		final StringJoiner text = new StringJoiner("\n\n");
		if (state != null && state.getConditions() != null) {
			for (AgentSuspendedWaitingConditionDto condition : state.getConditions()) {
				final GConditionEvent event = byCondition.remove(condition.getConditionId());
				if (event != null) {
					text.add(lineOf(condition, event));
				}
			}
		}
		// what the state no longer shows still says what it solved
		for (GConditionEvent event : byCondition.values()) {
			text.add(lineOf(null, event));
		}
		return text.length() > 0 ? text.toString() : NOTHING_SAID;
	}

	private String lineOf(AgentSuspendedWaitingConditionDto condition, GConditionEvent event) {
		if (event.getText() != null && !event.getText().isBlank()) {
			return (event.getSourceSystem() != null && !event.getSourceSystem().isBlank()
					? "[from " + event.getSourceSystem().strip() + "] "
					: "") + event.getText().strip();
		}
		final String type = condition != null ? condition.getConditionType() : event.getConditionType();
		return renderers.getOrDefault(type, fallback).render(condition, event);
	}
}
