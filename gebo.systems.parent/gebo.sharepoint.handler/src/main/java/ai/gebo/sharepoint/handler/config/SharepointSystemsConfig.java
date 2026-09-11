/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.sharepoint.handler.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import ai.gebo.sharepoint.handler.GSharepointContentManagementSystem;
import jakarta.validation.Valid;
import lombok.Data;

/**
 * The SharePoint systems a deployment declares in its own configuration, under
 * {@code ai.gebo.sharepoint.systems}:
 *
 * <pre>
 * ai.gebo.sharepoint:
 *   systems:
 *     - code: corporate-sharepoint
 *       description: Corporate SharePoint Online
 *       baseUri: https://example.sharepoint.com
 *       sharepointVersion: CLOUD_VERSION
 *       secretCode: msgraph-application
 * </pre>
 *
 * <p>
 * {@code secretCode} references a credential held by the secrets management
 * layer - one declared under {@code ai.gebo.secrets.config} or created in the
 * admin UI - so no credential is ever spelled out here.
 * </p>
 *
 * <p>
 * A separate bean rather than a field on
 * {@link MicrosoftSharepointHandlerConfig}, which binds the same prefix for the
 * prompt library: the two are configured by different people at different times,
 * and only this one is {@code @Validated}. That cascade matters most here -
 * {@link GSharepointContentManagementSystem} marks both {@code secretCode} and
 * {@code sharepointVersion} {@code @NotNull}, so a half-declared system fails
 * the startup instead of the first connection.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.sharepoint")
@Validated
@Data
public class SharepointSystemsConfig {

	/** {@code ai.gebo.sharepoint.systems} */
	private List<@Valid GSharepointContentManagementSystem> systems = new ArrayList<GSharepointContentManagementSystem>();
}
