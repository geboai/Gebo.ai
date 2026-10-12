/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.models.metainfos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.core.io.ClassPathResource;

import lombok.Data;

/**
 * One provider's piece of the models library: what Gebo knows of its models (modelsInfo)
 * and what they accept in a request (parameterRules). The module implementing the
 * provider declares a subclass bound to its own yml, as the prompt libraries do:
 *
 * <pre>
 * &#64;Configuration
 * &#64;ConfigurationProperties(value = "ai.gebo.models.providers.openai")
 * &#64;PropertySource(value = "classpath:/bundle-models/openai.yml", factory = GeboYamlPropertySourceFactory.class)
 * public class OpenAIModelsLibrary extends GProviderModelsLibrary {
 * }
 * </pre>
 *
 * and the models library DAO collects every such bean: the abstraction layer knows no
 * provider, and a provider's entries are there whenever its module is.
 * <p>
 * Each yml carries its own prefix: the property sources are one environment, so two
 * segments under one prefix would bind a single list. A rule applies to its own
 * providerId only; within a segment the first rule covering a model wins, specific models
 * before family patterns.
 */
@Data
public class GProviderModelsLibrary {
	/** The provider's models. */
	private List<ModelMetaInfo> modelsInfo = new ArrayList<ModelMetaInfo>();
	/** What the provider's models accept in a request. */
	private List<ModelParameterRules> parameterRules = new ArrayList<ModelParameterRules>();

	/**
	 * The segment a classpath yml carries under the prefix, bound as Spring binds the
	 * provider beans: what the modules' tests read their own segment with.
	 */
	public static <T extends GProviderModelsLibrary> T load(String classpathYml, String prefix, Class<T> type)
			throws IOException {
		YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
		yaml.setResources(new ClassPathResource(classpathYml));
		Properties properties = yaml.getObject();
		if (properties == null || properties.isEmpty())
			throw new IOException("No models library segment in " + classpathYml);
		return new Binder(new MapConfigurationPropertySource(properties)).bind(prefix, type)
				.orElseThrow(() -> new IOException("No " + prefix + " in " + classpathYml));
	}
}
