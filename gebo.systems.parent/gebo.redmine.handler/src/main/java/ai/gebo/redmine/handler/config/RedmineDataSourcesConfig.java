/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSource;
import jakarta.validation.Valid;
import lombok.Data;

/**
 * Redmine data sources declared in the application configuration
 * (ai.gebo.redmine.datasources), seeded as read-only records at startup.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.redmine")
@Validated
@Data
public class RedmineDataSourcesConfig {
	private List<@Valid GDeclaredDataSource> datasources = new ArrayList<GDeclaredDataSource>();
}
