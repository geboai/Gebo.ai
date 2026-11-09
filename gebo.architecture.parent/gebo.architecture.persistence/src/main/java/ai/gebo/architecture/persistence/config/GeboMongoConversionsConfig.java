/*
 * Copyright (c) 2025+ Gebo.ai
 * Licensed under the Mozilla Public License 2.0 (MPL 2.0) with Data Protection Clauses.
 * See LICENSE file in the project root for full license information.
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 */
package ai.gebo.architecture.persistence.config;

import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import ai.gebo.architecture.persistence.IGMongoConverter;

/**
 * The contributed converters ({@link IGMongoConverter}) when Spring Boot
 * configures Mongo, that is when Gebo's {@link MongoConfig} is off: this
 * conversions bean takes the place of Boot's empty one. With {@link MongoConfig}
 * on, it registers them itself.
 */
@Configuration
@ConditionalOnProperty(prefix = "ai.gebo.mongodb", name = "enabled", havingValue = "false", matchIfMissing = true)
public class GeboMongoConversionsConfig {

	@Bean
	MongoCustomConversions mongoCustomConversions(ObjectProvider<IGMongoConverter> converters) {
		return new MongoCustomConversions(contributed(converters));
	}

	static List<?> contributed(ObjectProvider<IGMongoConverter> converters) {
		return converters.orderedStream().toList();
	}
}
