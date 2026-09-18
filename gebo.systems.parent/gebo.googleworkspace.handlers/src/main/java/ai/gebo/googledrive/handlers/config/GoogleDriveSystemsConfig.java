/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.googledrive.handlers.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import ai.gebo.googledrive.handlers.GGoogleDriveSystem;
import jakarta.validation.Valid;
import lombok.Data;

/**
 * The Google Drive systems a deployment declares in its own configuration, under
 * {@code ai.gebo.googleworkspace.systems}:
 *
 * <pre>
 * ai.gebo.googleworkspace:
 *   systems:
 *     - code: corporate-drive
 *       description: Corporate Google Drive
 *       driveAccessSecret: google-workspace-service-account
 * </pre>
 *
 * <p>
 * {@code driveAccessSecret} references a Google credential held by the secrets
 * management layer - one declared under
 * {@code ai.gebo.secrets.config.google-cloud-json-credentials} or
 * {@code oauth2-google}, or created in the admin UI - so no credential is ever
 * spelled out here.
 * </p>
 *
 * <p>
 * A separate bean rather than a field on {@link GoogleWorkspaceHandlerConfig},
 * which binds the same prefix for the query-extraction prompt: the two are
 * configured by different people at different times, and only this one is
 * {@code @Validated}, so the cascade reaches the constraints
 * {@link GGoogleDriveSystem} carries.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.googleworkspace")
@Validated
@Data
public class GoogleDriveSystemsConfig {

	/** {@code ai.gebo.googleworkspace.systems} */
	private List<@Valid GGoogleDriveSystem> systems = new ArrayList<GGoogleDriveSystem>();
}
