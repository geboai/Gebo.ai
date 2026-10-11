/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.onxx_transformers_embedding.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ai.gebo.architecture.utils.GeboYamlPropertySourceFactory;
import ai.gebo.llms.models.metainfos.GProviderModelsLibrary;

/**
 * The ONNX transformers piece of the models library: its models and what they accept in a request,
 * from {@code bundle-models/onnx-transformers.yml}, collected by the models library DAO with the other
 * providers' ones.
 */
@Configuration
@ConfigurationProperties(value = OnnxTransformersModelsLibrary.PREFIX)
@PropertySource(value = "classpath:/bundle-models/onnx-transformers.yml", factory = GeboYamlPropertySourceFactory.class)
public class OnnxTransformersModelsLibrary extends GProviderModelsLibrary {
	/** The keys its yml carries. */
	public static final String PREFIX = "ai.gebo.models.providers.onnx-transformers";
	/** Its yml. */
	public static final String YML = "bundle-models/onnx-transformers.yml";
}
