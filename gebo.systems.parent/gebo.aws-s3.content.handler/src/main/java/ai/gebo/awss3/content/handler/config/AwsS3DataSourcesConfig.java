/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.awss3.content.handler.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSource;
import jakarta.validation.Valid;
import lombok.Data;

/**
 * The AWS S3 data sources a deployment declares in its own configuration, under
 * {@code ai.gebo.awss3.datasources}:
 *
 * <pre>
 * ai.gebo.awss3:
 *   datasources:
 *     - code: corporate-reports
 *       description: Published reports
 *       systemCode: corporate-buckets
 *       parentProjectCode: COMPANY-KB
 *       paths:
 *         - path: corporate-docs/reports/2026/
 *           folder: true
 *         - path: corporate-docs/reports/summary.pdf
 *           folder: false
 * </pre>
 *
 * <p>
 * An S3 path is {@code <bucket>/<key>} - the bucket being the navigation root
 * and the key the prefix or object under it - or a bare {@code <bucket>} for the
 * whole bucket.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.awss3")
@Validated
@Data
public class AwsS3DataSourcesConfig {

	/** {@code ai.gebo.awss3.datasources} */
	private List<@Valid GDeclaredDataSource> datasources = new ArrayList<GDeclaredDataSource>();
}
