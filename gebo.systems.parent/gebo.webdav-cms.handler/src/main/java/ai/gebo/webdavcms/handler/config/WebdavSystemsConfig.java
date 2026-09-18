/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.webdavcms.handler.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import ai.gebo.webdavcms.handler.GWebdavContentManagementSystem;
import jakarta.validation.Valid;
import lombok.Data;

/**
 * The WebDAV systems a deployment declares in its own configuration, under
 * {@code ai.gebo.webdav.systems}:
 *
 * <pre>
 * ai.gebo.webdav:
 *   systems:
 *     - code: corporate-dav
 *       description: Corporate WebDAV share
 *       baseUri: https://dav.example.com/remote.php/dav
 *       webdavAuthType: BASIC
 *       secretCode: webdav-service-account
 * </pre>
 *
 * <p>
 * {@code secretCode} references a credential held by the secrets management
 * layer - one declared under {@code ai.gebo.secrets.config} or created in the
 * admin UI - so no credential is ever spelled out here.
 * </p>
 *
 * <p>
 * A separate bean rather than a field on {@link WebdavHandlerConfig}, which
 * binds the same prefix for the prompt library: the two are configured by
 * different people at different times, and only this one is {@code @Validated},
 * so the cascade reaches the constraints
 * {@link GWebdavContentManagementSystem} carries.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.webdav")
@Validated
@Data
public class WebdavSystemsConfig {

	/** {@code ai.gebo.webdav.systems} */
	private List<@Valid GWebdavContentManagementSystem> systems = new ArrayList<GWebdavContentManagementSystem>();
}
