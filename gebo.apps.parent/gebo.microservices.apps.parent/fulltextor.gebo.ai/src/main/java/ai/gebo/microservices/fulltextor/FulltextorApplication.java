/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.fulltextor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import ai.gebo.architecture.environment.EnvironmentHolder;

/**
 * Entry point of the Gebo.ai <b>full-text</b> processor microservice.
 *
 * <p>
 * Built on the {@code gebo.microservices.starter} stack (microservices
 * environment, RabbitMQ messaging bridge, shared topology and Eureka discovery
 * client) and hosts the {@code gebo.ragsystem.content.fulltext.processor}
 * full-text indexing/search system (OpenSearch-backed). The whole {@code ai.gebo}
 * component tree is scanned so the processor beans, controllers and Mongo
 * repositories are wired.
 * </p>
 */
@SpringBootApplication
@ComponentScan(basePackages = "ai.gebo")
@EnableConfigurationProperties
@EnableAsync
@EnableScheduling
@EnableMongoRepositories(basePackages = "ai.gebo")
public class FulltextorApplication {

	private static final String ERROR_MESSAGE = "The java property or environment variable GEBO_HOME must be set";
	private static final Logger LOG = LoggerFactory.getLogger(FulltextorApplication.class);

	public static void main(String[] args) {
		LOG.info("STARTING THE GEBO.AI FULLTEXT PROCESSOR MICROSERVICE");
		if (EnvironmentHolder.GEBO_HOME_ENVIRONMENT_VALUE == null
				|| EnvironmentHolder.GEBO_HOME_ENVIRONMENT_VALUE.trim().isEmpty()) {
			LOG.error(ERROR_MESSAGE);
			System.err.println(ERROR_MESSAGE);
			System.exit(-1);
		}
		SpringApplication.run(FulltextorApplication.class, args);
		LOG.info("GEBO.AI FULLTEXT PROCESSOR MICROSERVICE FINISHED");
	}
}
