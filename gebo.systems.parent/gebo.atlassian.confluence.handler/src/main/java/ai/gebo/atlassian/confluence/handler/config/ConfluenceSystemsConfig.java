/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.atlassian.confluence.handler.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import ai.gebo.atlassian.confluence.handler.GConfluenceSystem;
import jakarta.validation.Valid;
import lombok.Data;

/**
 * The Confluence systems a deployment declares in its own configuration, under
 * {@code ai.gebo.confluence.systems}:
 *
 * <pre>
 * ai.gebo.confluence:
 *   systems:
 *     - code: corporate-wiki
 *       description: Corporate Confluence
 *       baseUri: https://wiki.example.com
 *       confluenceVersion: CLOUD
 *       secretCode: confluence-service-account
 * </pre>
 *
 * <p>
 * {@code secretCode} references a credential held by the secrets management
 * layer - one declared under {@code ai.gebo.secrets.config} or created in the
 * admin UI - so no credential is ever spelled out here.
 * </p>
 *
 * <p>
 * A separate bean rather than a field on {@link ConfluenceHandlerConfig}, which
 * binds the same prefix for the prompt library: the two are configured by
 * different people at different times, and only this one is {@code @Validated}.
 * The cascade is what carries the startup check into the constraints
 * {@link GConfluenceSystem} carries, so a declaration that could never connect
 * fails the boot instead of the first ingestion.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.confluence")
@Validated
@Data
public class ConfluenceSystemsConfig {

	/** {@code ai.gebo.confluence.systems} */
	private List<@Valid GConfluenceSystem> systems = new ArrayList<GConfluenceSystem>();
}
