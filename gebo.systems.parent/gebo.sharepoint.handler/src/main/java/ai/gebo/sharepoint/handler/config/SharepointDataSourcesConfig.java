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

import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSource;
import jakarta.validation.Valid;
import lombok.Data;

/**
 * The OneDrive data sources a deployment declares in its own configuration,
 * under {@code ai.gebo.sharepoint.datasources}:
 *
 * <pre>
 * ai.gebo.sharepoint:
 *   datasources:
 *     - code: corporate-onedrive-policies
 *       description: Policies on the corporate OneDrive
 *       systemCode: corporate-sharepoint
 *       parentProjectCode: COMPANY-KB
 *       paths:
 *         - path: b!xQ3z.../01ABCDEF6Y2GOVW7725BZO354PWSELRRZ
 *           folder: true
 * </pre>
 *
 * <p>
 * A path is {@code <driveId>/<itemId>}, or a bare {@code <driveId>} for the
 * whole drive - the ids Microsoft Graph addresses a drive item by, and the same
 * ones the browser stores for a source an admin assembles by clicking.
 * </p>
 *
 * <h2>Drives only, not sites</h2>
 * <p>
 * The module navigates two kinds of root: a OneDrive drive
 * ({@code ONE-DRIVE:}) and a SharePoint site ({@code SHAREPOINT-SITE:}). Only
 * the first is declarable here. A site root is not a filesystem: under it the
 * module walks lists, list items and site pages, each with its own identity and
 * its own step type, and a {@code path}/{@code folder} pair has nothing to say
 * about which of them is meant. Site-backed sources stay an admin UI job, where
 * the browser shows what is actually there.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.sharepoint")
@Validated
@Data
public class SharepointDataSourcesConfig {

	/** {@code ai.gebo.sharepoint.datasources} */
	private List<@Valid GDeclaredDataSource> datasources = new ArrayList<GDeclaredDataSource>();
}
