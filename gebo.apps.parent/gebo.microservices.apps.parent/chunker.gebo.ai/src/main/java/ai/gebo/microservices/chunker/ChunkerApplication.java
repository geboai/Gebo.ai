/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.chunker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import ai.gebo.architecture.environment.EnvironmentHolder;

/**
 * Entry point of the Gebo.ai <b>brain</b> LLMs microservice.
 *
 * <p>
 * Built on the {@code gebo.microservices.llms.starter} stack: it hosts the LLM
 * abstraction layer, participates in the models-replication Hazelcast cache
 * (its {@code spring.application.name} {@code brain.gebo.ai} is in the shared
 * participant set) and bridges messaging over RabbitMQ. The whole {@code ai.gebo}
 * component tree is scanned so those beans are wired.
 * </p>
 */
@SpringBootApplication
// basePackages is widened to "ai.gebo" (chunker's own production beans live
// outside its microservice-specific package), but @ComponentScan given DIRECTLY on
// the class replaces - rather than merges with - the one @SpringBootApplication
// supplies, so its default excludeFilters (TypeExcludeFilter,
// AutoConfigurationExcludeFilter) are lost unless restated here. Without
// AutoConfigurationExcludeFilter, plain component-scanning picks up every
// @AutoConfiguration class under "ai.gebo" (they are @Configuration too) as an
// ordinary bean, processed in classpath-scan order rather than through
// @EnableAutoConfiguration's deferred, @AutoConfigureAfter/Before-sorted import -
// silently defeating that ordering for the cluster/client auto-configurations this
// service depends on.
@ComponentScan(basePackages = "ai.gebo",
		excludeFilters = { @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
				@ComponentScan.Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
@EnableConfigurationProperties
@EnableAsync
@EnableScheduling
@EnableMongoRepositories(basePackages = "ai.gebo")
public class ChunkerApplication {

	private static final String ERROR_MESSAGE = "The java property or environment variable GEBO_HOME must be set";
	private static final Logger LOG = LoggerFactory.getLogger(ChunkerApplication.class);

	public static void main(String[] args) {
		LOG.info("STARTING THE GEBO.AI BRAIN MICROSERVICE");
		if (EnvironmentHolder.GEBO_HOME_ENVIRONMENT_VALUE == null
				|| EnvironmentHolder.GEBO_HOME_ENVIRONMENT_VALUE.trim().isEmpty()) {
			LOG.error(ERROR_MESSAGE);
			System.err.println(ERROR_MESSAGE);
			System.exit(-1);
		}
		SpringApplication.run(ChunkerApplication.class, args);
		LOG.info("GEBO.AI BRAIN MICROSERVICE FINISHED");
	}
}
