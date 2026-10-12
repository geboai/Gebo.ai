/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.anthropic.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * How a claude model accepts thinking, as the Models API reports it in
 * {@code capabilities.thinking.types} and {@code capabilities.effort}.
 * <p>
 * It travels in {@link GAnthropicChatModelChoice#getModelDetails()} under plain keys,
 * so it is persisted with the chosen model of a configuration and the configuration
 * can be built without asking the api again.
 *
 * @param adaptive     the model accepts {@code thinking.type=adaptive}, steered by an
 *                     effort level
 * @param budget       the model accepts {@code thinking.type=enabled} with a token
 *                     budget
 * @param disabled     the model accepts {@code thinking.type=disabled}: false exactly
 *                     when such a request is refused with a 400
 * @param effortLevels the effort levels the model accepts (low, medium, high, xhigh,
 *                     max), empty when it takes no effort parameter
 */
public record AnthropicThinkingSupport(boolean adaptive, boolean budget, boolean disabled, Set<String> effortLevels) {

	public static final String THINKING_ADAPTIVE = "thinkingAdaptive";
	public static final String THINKING_BUDGET = "thinkingBudget";
	public static final String THINKING_DISABLED = "thinkingDisabled";
	public static final String EFFORT_LEVELS = "effortLevels";

	public AnthropicThinkingSupport {
		effortLevels = effortLevels != null ? Set.copyOf(effortLevels) : Set.of();
	}

	public boolean acceptsEffort(String level) {
		return effortLevels.contains(level);
	}

	/**
	 * Writes this support into a model choice's details.
	 */
	public void writeTo(Map<String, Object> details) {
		details.put(THINKING_ADAPTIVE, adaptive);
		details.put(THINKING_BUDGET, budget);
		details.put(THINKING_DISABLED, disabled);
		details.put(EFFORT_LEVELS, new ArrayList<>(effortLevels));
	}

	/**
	 * Reads the support back from a model choice's details.
	 *
	 * @return the support, or null when the details do not carry it (a choice saved
	 *         before the Models API was read, or built from a bare model code)
	 */
	public static AnthropicThinkingSupport readFrom(Map<String, Object> details) {
		if (details == null || !(details.get(THINKING_ADAPTIVE) instanceof Boolean adaptive)
				|| !(details.get(THINKING_BUDGET) instanceof Boolean budget)) {
			return null;
		}
		boolean disabled = !(details.get(THINKING_DISABLED) instanceof Boolean d) || d;
		Set<String> levels = new LinkedHashSet<>();
		if (details.get(EFFORT_LEVELS) instanceof Collection<?> values) {
			for (Object value : values) {
				if (value != null)
					levels.add(value.toString());
			}
		}
		return new AnthropicThinkingSupport(adaptive, budget, disabled, levels);
	}

	/** The effort levels in the order claude's scale goes, lowest first. */
	public static final List<String> EFFORT_SCALE = List.of("low", "medium", "high", "xhigh", "max");
}
