/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.ollama.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ai.gebo.architecture.utils.GeboYamlPropertySourceFactory;
import ai.gebo.llms.models.metainfos.GProviderModelsLibrary;

/**
 * The Ollama piece of the models library: its models and what they accept in a request,
 * from {@code bundle-models/ollama.yml}, collected by the models library DAO with the other
 * providers' ones.
 */
@Configuration
@ConfigurationProperties(value = OllamaModelsLibrary.PREFIX)
@PropertySource(value = "classpath:/bundle-models/ollama.yml", factory = GeboYamlPropertySourceFactory.class)
public class OllamaModelsLibrary extends GProviderModelsLibrary {
	/** The keys its yml carries. */
	public static final String PREFIX = "ai.gebo.models.providers.ollama";
	/** Its yml. */
	public static final String YML = "bundle-models/ollama.yml";
}
