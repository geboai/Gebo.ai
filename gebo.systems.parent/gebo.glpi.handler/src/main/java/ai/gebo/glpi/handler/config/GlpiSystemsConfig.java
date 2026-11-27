/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import ai.gebo.glpi.handler.GGlpiSystem;
import jakarta.validation.Valid;
import lombok.Data;

/**
 * The GLPI systems declared in configuration (ai.gebo.glpi.systems). Kept apart
 * from the prompts configuration sharing the same prefix: only the declared
 * systems are validated.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.glpi")
@Validated
@Data
public class GlpiSystemsConfig {

	private List<@Valid GGlpiSystem> systems = new ArrayList<GGlpiSystem>();
}
