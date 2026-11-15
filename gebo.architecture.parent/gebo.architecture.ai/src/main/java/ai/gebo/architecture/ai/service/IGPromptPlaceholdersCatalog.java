/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.service;

import java.util.Set;

/**
 * The placeholders a prompt template may name and be sure somebody fills: the
 * ones the renderer fills on every call, and the ones the prompt context
 * providers fill when a template names them. A prompt saved with a placeholder
 * nobody fills is refused at save time, instead of failing every call made
 * with it.
 */
public interface IGPromptPlaceholdersCatalog {

	/** The placeholders the renderer itself fills on every call. */
	public Set<String> builtInPlaceholders();

	/** The placeholders filled on demand by the providers, and those declared by the agents. */
	public Set<String> knownPlaceholders();
}
