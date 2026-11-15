/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.services;

import java.util.Map;
import java.util.Set;

import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;

/**
 * Fills, on demand, placeholders of a prompt template with what it knows of
 * the request: the time, the user, the conversation, the run. A template that
 * names one of these placeholders gets its value in every model call, from a
 * chat step or from an agent alike; a template that names none renders as if
 * no provider existed. A caller's own value for a placeholder always wins.
 * <p>
 * Declared as a bean, and found by the {@link IGPromptContextProviders}
 * registry.
 */
public interface IGPromptContextProvider {

	/** The placeholder names this provider fills. */
	public Set<String> getPlaceholders();

	/**
	 * The values of the asked placeholders, all among {@link #getPlaceholders()},
	 * for this call; a placeholder left out, or mapped to null, is left to the
	 * others. Never throws for a value it cannot compute: it leaves it out.
	 */
	public Map<String, Object> valuesFor(Set<String> asked, IChatRequestContext context);
}
