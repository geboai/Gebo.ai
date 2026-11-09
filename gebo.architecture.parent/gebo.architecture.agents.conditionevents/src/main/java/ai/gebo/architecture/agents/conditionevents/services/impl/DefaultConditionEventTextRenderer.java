/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.services.impl;

import java.util.Map;
import java.util.StringJoiner;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.conditionevents.model.GConditionEvent;
import ai.gebo.architecture.agents.conditionevents.services.IConditionEventTextRenderer;
import ai.gebo.architecture.agents.model.ConditionSource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionDto;
import ai.gebo.llms.chat.pipelines.service.impl.DefaultConditionTransportTranslator;
import ai.gebo.llms.chat.pipelines.service.impl.GAbstractConditionTransportTranslator;

/**
 * Renders any condition: its statement, then what solved it (the value given,
 * declined, or the attributes as they came) and the source.
 */
@Service
public class DefaultConditionEventTextRenderer implements IConditionEventTextRenderer {
	static final String NO_STATEMENT = "A condition the work waited for";
	static final String DECLINED = "declined";

	@Override
	public String getConditionType() {
		return DefaultConditionTransportTranslator.ID;
	}

	@Override
	public String render(AgentSuspendedWaitingConditionDto condition, GConditionEvent event) {
		return statementOf(condition, GAbstractConditionTransportTranslator.STATEMENT) + "\n→ " + answerOf(event)
				+ fromOf(event);
	}

	/** The attribute of the condition named, else its statement, else a generic one. */
	static String statementOf(AgentSuspendedWaitingConditionDto condition, String attribute) {
		final Map<String, Object> attributes = condition != null ? condition.getAttributes() : null;
		Object statement = attributes != null ? attributes.get(attribute) : null;
		if (isBlank(statement) && attributes != null) {
			statement = attributes.get(GAbstractConditionTransportTranslator.STATEMENT);
		}
		return !isBlank(statement) ? String.valueOf(statement).strip() : NO_STATEMENT;
	}

	static boolean isDeclined(GConditionEvent event) {
		final Object declined = event.getAttributes() != null
				? event.getAttributes().get(GAbstractConditionTransportTranslator.DECLINED)
				: null;
		return declined instanceof Boolean b ? b : declined != null && Boolean.parseBoolean(String.valueOf(declined));
	}

	static String fromOf(GConditionEvent event) {
		final String from = sourceLabelOf(event);
		return from != null ? " (from " + from + ")" : "";
	}

	/**
	 * How the history names the source of an event: the label of a source that
	 * says what it is (claimed, or the chat's user); otherwise the system the
	 * event names, as it always did. Null for none.
	 */
	static String sourceLabelOf(GConditionEvent event) {
		if (event.getSource() != null && event.getSource().getKind() != null
				&& event.getSource().getKind() != ConditionSource.Kind.UNKNOWN) {
			return event.getSource().label();
		}
		return event.getSourceSystem() != null && !event.getSourceSystem().isBlank() ? event.getSourceSystem().strip()
				: null;
	}

	static boolean isBlank(Object value) {
		return value == null || String.valueOf(value).isBlank();
	}

	private static String answerOf(GConditionEvent event) {
		if (isDeclined(event)) {
			return DECLINED;
		}
		final Map<String, Object> attributes = event.getAttributes();
		final Object value = attributes != null ? attributes.get(DefaultConditionTransportTranslator.VALUE) : null;
		if (value != null) {
			return String.valueOf(value);
		}
		final StringJoiner all = new StringJoiner(", ");
		if (attributes != null) {
			for (Map.Entry<String, Object> attribute : attributes.entrySet()) {
				all.add(attribute.getKey() + ": " + attribute.getValue());
			}
		}
		return all.length() > 0 ? all.toString() : "solved";
	}
}
