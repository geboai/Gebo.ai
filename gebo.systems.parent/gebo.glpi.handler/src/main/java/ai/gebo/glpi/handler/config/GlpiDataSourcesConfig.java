/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSource;
import jakarta.validation.Valid;
import lombok.Data;

/**
 * The GLPI data sources declared in configuration (ai.gebo.glpi.datasources).
 * A declared path is written in the GLPI navigation encoding, the entity
 * first: ENTITY:0, ENTITY:0|TICKETS, ENTITY:0|TICKETS|TICKET:1042, ENTITY:0|KB,
 * ENTITY:0|KB|KBARTICLE:15.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.glpi")
@Validated
@Data
public class GlpiDataSourcesConfig {

	private List<@Valid GDeclaredDataSource> datasources = new ArrayList<GDeclaredDataSource>();
}
