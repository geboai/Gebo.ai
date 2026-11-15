/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.ai.util.JsonHelper;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.ClarificationAnswer;
import ai.gebo.architecture.agents.model.ToolCallConfirmationOptions;

/**
 * What the people asked to confirm tool calls answered, as an agent respawned on their
 * answers reads them: for a call to a tool with given arguments, whether it is allowed,
 * refused (with the message they left for the model) or not decided. The arguments are
 * compared as JSON, so a call written with its keys in another order is the same call.
 */
public final class ToolCallConfirmations {

	/** What a person decided on a call. */
	public record Decision(boolean allowed, String message) {
	}

	private record Decided(String toolName, Object arguments, String rawArguments, Decision decision) {
	}

	/** No decision: every call is put to a person. */
	public static final ToolCallConfirmations NONE = new ToolCallConfirmations(List.of());

	private static final JsonHelper JSON = new JsonHelper();
	private final List<Decided> decided;

	private ToolCallConfirmations(List<Decided> decided) {
		this.decided = decided;
	}

	/**
	 * The decisions of the confirmations among the conditions a respawned agent waited
	 * for; the other conditions are not read. A confirmation left unanswered (declined,
	 * expired, gone on without) refuses the call.
	 */
	@SuppressWarnings("rawtypes")
	public static ToolCallConfirmations of(Collection<AgentSuspendedWaitingCondition> resolved) {
		final List<Decided> decided = new ArrayList<Decided>();
		if (resolved != null) {
			for (AgentSuspendedWaitingCondition<?, ?> condition : resolved) {
				if (condition != null && condition.getLlmChosenOptions() instanceof ToolCallConfirmationOptions options) {
					decided.add(new Decided(options.getToolName(), parsed(options.getArguments()), options.getArguments(),
							decisionOf(condition)));
				}
			}
		}
		return decided.isEmpty() ? NONE : new ToolCallConfirmations(decided);
	}

	/** What the answer to a confirmation decides: allowed only when the person chose to. */
	public static Decision decisionOf(AgentSuspendedWaitingCondition<?, ?> condition) {
		if (condition.getInstanceValue() instanceof ClarificationAnswer answer) {
			final boolean allowed = answer.getChosenOptions() != null
					&& answer.getChosenOptions().contains(ToolCallConfirmationOptions.ALLOW);
			final String message = answer.getCustomExplanation() != null && !answer.getCustomExplanation().isBlank()
					? answer.getCustomExplanation().strip()
					: null;
			return new Decision(allowed, message);
		}
		return new Decision(false, null);
	}

	/** The decision on a call to the tool with the arguments; null when nobody decided it. */
	public Decision decisionFor(String toolName, String arguments) {
		final Object parsed = parsed(arguments);
		Decision found = null;
		for (Decided call : decided) {
			if (Objects.equals(call.toolName(), toolName) && sameArguments(call, parsed, arguments)) {
				// the latest answer to the same call wins
				found = call.decision();
			}
		}
		return found;
	}

	private static boolean sameArguments(Decided call, Object parsed, String raw) {
		if (call.arguments() != null && parsed != null) {
			return call.arguments().equals(parsed);
		}
		return Objects.equals(stripped(call.rawArguments()), stripped(raw));
	}

	private static String stripped(String text) {
		return text != null ? text.strip() : null;
	}

	/** The arguments as JSON values (maps compare whatever the order of their keys); null when not JSON. */
	static Object parsed(String arguments) {
		if (arguments == null || arguments.isBlank()) {
			return null;
		}
		try {
			return JSON.fromJson(arguments, Object.class);
		} catch (RuntimeException notJson) {
			return null;
		}
	}
}
