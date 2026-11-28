/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import ai.gebo.zammad.handler.GZammadSystem;
import jakarta.validation.Valid;
import lombok.Data;

/**
 * Zammad systems declared in the application configuration
 * (ai.gebo.zammad.systems): they win over a stored system with the same code
 * and cannot be changed from the UI.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.zammad")
@Validated
@Data
public class ZammadSystemsConfig {
	private List<@Valid GZammadSystem> systems = new ArrayList<GZammadSystem>();
}
