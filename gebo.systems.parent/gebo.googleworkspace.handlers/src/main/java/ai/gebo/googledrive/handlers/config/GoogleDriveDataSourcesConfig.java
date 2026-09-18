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

import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSource;
import jakarta.validation.Valid;
import lombok.Data;

/**
 * The Google Drive data sources a deployment declares in its own configuration,
 * under {@code ai.gebo.googleworkspace.datasources}:
 *
 * <pre>
 * ai.gebo.googleworkspace:
 *   datasources:
 *     - code: corporate-handbook
 *       description: Company handbook folder
 *       systemCode: corporate-drive
 *       parentProjectCode: COMPANY-KB
 *       paths:
 *         - path: 0AJv7q2Xk9mLkUk9PVA/1BxY8sQ2fN7pLmRt3KcWv
 *           folder: true
 * </pre>
 *
 * <p>
 * A Google Drive path is {@code <driveId>/<fileId>}, or a bare
 * {@code <driveId>} for the whole shared drive. Drive addresses everything by
 * opaque id and has no server-side notion of a path, so these are ids rather
 * than folder names - the same ids the browser stores for a source an admin
 * assembles by clicking, and the ones visible in a Drive URL.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.googleworkspace")
@Validated
@Data
public class GoogleDriveDataSourcesConfig {

	/** {@code ai.gebo.googleworkspace.datasources} */
	private List<@Valid GDeclaredDataSource> datasources = new ArrayList<GDeclaredDataSource>();
}
