/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.ai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Runtime switch governing whether prompt templates may be edited (saved or
 * deleted) from the admin UI. Defaults to {@code false}: editing a prompt
 * template changes how the LLMs decide and answer, so a deployment must opt in
 * explicitly via {@code ai.gebo.prompt-templates.editingEnabled=true}. When
 * false the UI keeps the editor read only (both save and delete disabled).
 */
@Configuration
@ConfigurationProperties(prefix = "ai.gebo.prompt-templates")
@Data
public class GPromptTemplateEditingConfig {
	private boolean editingEnabled = false;
}
