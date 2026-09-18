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

import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSource;
import jakarta.validation.Valid;
import lombok.Data;

/**
 * The WebDAV data sources a deployment declares in its own configuration, under
 * {@code ai.gebo.webdav.datasources}:
 *
 * <pre>
 * ai.gebo.webdav:
 *   datasources:
 *     - code: corporate-policies
 *       description: Policy library
 *       systemCode: corporate-dav
 *       parentProjectCode: COMPANY-KB
 *       paths:
 *         - path: https://dav.example.com/remote.php/dav/files/admin/Policies
 *           folder: true
 *         - path: https://dav.example.com/remote.php/dav/files/admin/handbook.pdf
 *           folder: false
 * </pre>
 *
 * <p>
 * A WebDAV path is the resource's full href, the same string the module stores
 * for a source assembled by clicking through the browser and the same one it
 * hands to the DAV client. Writing it in full rather than relative to the
 * system's {@code baseUri} is deliberate: it is unambiguous, and it keeps the
 * declaration readable on its own, without resolving it against another entry.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.webdav")
@Validated
@Data
public class WebdavDataSourcesConfig {

	/** {@code ai.gebo.webdav.datasources} */
	private List<@Valid GDeclaredDataSource> datasources = new ArrayList<GDeclaredDataSource>();
}
