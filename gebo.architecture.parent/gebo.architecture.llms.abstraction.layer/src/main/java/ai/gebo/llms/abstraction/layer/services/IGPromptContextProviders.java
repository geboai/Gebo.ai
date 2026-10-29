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

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptPlaceholdersCatalog;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;

/**
 * The registry of the {@link IGPromptContextProvider prompt context providers}:
 * what every model call asks, before rendering a template, for the
 * placeholders the template names and the caller did not fill.
 */
public interface IGPromptContextProviders extends IGPromptPlaceholdersCatalog {

	/**
	 * The parameters of a call, completed: the caller's own, plus the values of the
	 * placeholders the template names that the caller left unfilled and a provider
	 * fills. Never null.
	 */
	public Map<String, Object> complete(GPromptTemplateConfig prompt, Map<String, Object> params,
			IChatRequestContext context);
}
