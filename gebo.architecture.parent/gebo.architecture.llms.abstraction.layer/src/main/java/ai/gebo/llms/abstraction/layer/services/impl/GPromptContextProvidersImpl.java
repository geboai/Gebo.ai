/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGPromptContextProvider;
import ai.gebo.llms.abstraction.layer.services.IGPromptContextProviders;
import ai.gebo.llms.abstraction.layer.services.IGPromptPlaceholdersDeclaration;

/**
 * The registry of the prompt context providers found in the application, asked
 * by every model call for the placeholders a template names and nobody filled.
 */
@Service
public class GPromptContextProvidersImpl implements IGPromptContextProviders {
	private static final Logger LOGGER = LoggerFactory.getLogger(GPromptContextProvidersImpl.class);
	/** What the renderer fills on every call (see GAbstractConfigurableChatModel, BaseLLMSInvokingService). */
	static final Set<String> BUILT_IN = Set.of(IChatRequestContext.USER_QUESTION_PROMPT_PARAM,
			IChatRequestContext.DOCUMENTS_PROMPT_PARAM, IChatRequestContext.CONSOLIDATED_HISTORY_PROMPT_PARAM,
			IChatRequestContext.USER_LANGUAGE_PROMPT_PARAM, IChatRequestContext.CONSOLIDATED_SUMMARY_PROMPT_PARAM,
			"format");

	private final List<IGPromptContextProvider> providers;
	private final Set<String> declared;

	public GPromptContextProvidersImpl(ObjectProvider<IGPromptContextProvider> providers,
			ObjectProvider<IGPromptPlaceholdersDeclaration> declarations) {
		this.providers = providers != null ? providers.stream().toList() : List.of();
		final Set<String> declaredNames = new LinkedHashSet<String>();
		if (declarations != null) {
			declarations.stream().forEach(declaration -> {
				if (declaration.declaredPlaceholders() != null) {
					declaredNames.addAll(declaration.declaredPlaceholders());
				}
			});
		}
		this.declared = Set.copyOf(declaredNames);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Prompt context providers: " + this.providers.size() + " filling " + knownPlaceholders());
		}
	}

	@Override
	public Map<String, Object> complete(GPromptTemplateConfig prompt, Map<String, Object> params,
			IChatRequestContext context) {
		final Map<String, Object> completed = params != null ? new HashMap<String, Object>(params)
				: new HashMap<String, Object>();
		if (prompt == null || providers.isEmpty()) {
			return completed;
		}
		final Set<String> asked = new HashSet<String>(prompt.getPlaceholders().keySet());
		asked.removeAll(completed.keySet());
		asked.removeAll(BUILT_IN);
		if (asked.isEmpty()) {
			return completed;
		}
		for (IGPromptContextProvider provider : providers) {
			final Set<String> owned = new HashSet<String>(asked);
			owned.retainAll(provider.getPlaceholders() != null ? provider.getPlaceholders() : Set.of());
			if (owned.isEmpty()) {
				continue;
			}
			try {
				final Map<String, Object> values = provider.valuesFor(owned, context);
				if (values != null) {
					for (String name : owned) {
						final Object value = values.get(name);
						if (value != null) {
							completed.put(name, value);
							asked.remove(name);
						}
					}
				}
			} catch (RuntimeException notProvided) {
				LOGGER.warn("The prompt context provider " + provider.getClass().getName() + " failed for " + owned
						+ ": the placeholders are left to the others", notProvided);
			}
		}
		if (!asked.isEmpty() && LOGGER.isDebugEnabled()) {
			LOGGER.debug("Prompt " + prompt.getPromptUse() + " names placeholders nobody filled: " + asked);
		}
		return completed;
	}

	@Override
	public Set<String> builtInPlaceholders() {
		return BUILT_IN;
	}

	@Override
	public Set<String> knownPlaceholders() {
		final Set<String> known = new LinkedHashSet<String>();
		for (IGPromptContextProvider provider : providers) {
			if (provider.getPlaceholders() != null) {
				known.addAll(provider.getPlaceholders());
			}
		}
		known.addAll(declared);
		return known;
	}
}
