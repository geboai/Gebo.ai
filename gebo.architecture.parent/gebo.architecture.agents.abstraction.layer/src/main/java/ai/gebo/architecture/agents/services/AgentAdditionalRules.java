/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.services;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;

/**
 * What goes in the {@code {additionalRulesSlot}} of an agent's prompt
 * ({@link AgentPromptTemplateParams#ADDITIONAL_RULES_SLOT_TEMPLATE_PARAM}): the
 * rules of the libraries a run really holds, an empty text when there are none.
 * <p>
 * A prompt saved before the slot existed does not name it: when there are rules
 * for it, the copy the run works on gets the slot at the end of its system
 * template, and what is saved stays as it is.
 */
public final class AgentAdditionalRules {
	private static final Logger LOGGER = LoggerFactory.getLogger(AgentAdditionalRules.class);
	private static final String SLOT = "{" + AgentPromptTemplateParams.ADDITIONAL_RULES_SLOT_TEMPLATE_PARAM + "}";
	private static final String NEWLINE = "\r\n";

	private AgentAdditionalRules() {
	}

	/**
	 * The rules of the given libraries, each once, in their order; an empty text,
	 * never null, when none has any.
	 */
	public static String of(Collection<AIToolsAndResourcesLibraryType> libraries) {
		final Set<String> rules = new LinkedHashSet<String>();
		if (libraries != null) {
			for (AIToolsAndResourcesLibraryType library : libraries) {
				if (library != null && library.getAgentRules() != null && !library.getAgentRules().isBlank()) {
					rules.add(library.getAgentRules().strip());
				}
			}
		}
		return String.join(NEWLINE + NEWLINE, rules);
	}

	/**
	 * The prompt to run with the given rules: the prompt itself when it names the slot
	 * or there are no rules, otherwise a copy with the slot closing its system template.
	 */
	public static GPromptTemplateConfig withSlot(GPromptTemplateConfig prompt, String rules) {
		if (prompt == null || rules == null || rules.isBlank() || prompt.getPlaceholders()
				.containsKey(AgentPromptTemplateParams.ADDITIONAL_RULES_SLOT_TEMPLATE_PARAM)) {
			return prompt;
		}
		final GPromptTemplateConfig patched = prompt.copy();
		final String system = patched.getSystemPromptTemplate();
		patched.setSystemPromptTemplate(
				system != null && !system.isBlank() ? system + NEWLINE + NEWLINE + SLOT : SLOT);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Prompt use:" + prompt.getPromptUse()
					+ " does not name the additional rules slot: the run's copy gets it at the end of its system template");
		}
		return patched;
	}
}
