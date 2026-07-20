/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.tyr;

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
 * Entry point of the Gebo.ai <b>tyr</b> microservice.
 *
 * <p>
 * Built on the bare {@code gebo.microservices.starter} stack (microservices
 * environment, RabbitMQ messaging bridge, shared topology and Eureka discovery
 * client) with no business logic yet - the future home for the JOBS_MASTER
 * workflow/usage-tracking receivers and their admin/reporting controllers.
 * </p>
 */
@SpringBootApplication
@ComponentScan(basePackages = "ai.gebo")
@EnableConfigurationProperties
@EnableAsync
@EnableScheduling
@EnableMongoRepositories(basePackages = "ai.gebo")
public class TyrApplication {

	private static final String ERROR_MESSAGE = "The java property or environment variable GEBO_HOME must be set";
	private static final Logger LOG = LoggerFactory.getLogger(TyrApplication.class);

	public static void main(String[] args) {
		LOG.info("STARTING THE GEBO.AI TYR MICROSERVICE");
		if (EnvironmentHolder.GEBO_HOME_ENVIRONMENT_VALUE == null
				|| EnvironmentHolder.GEBO_HOME_ENVIRONMENT_VALUE.trim().isEmpty()) {
			LOG.error(ERROR_MESSAGE);
			System.err.println(ERROR_MESSAGE);
			System.exit(-1);
		}
		SpringApplication.run(TyrApplication.class, args);
		LOG.info("GEBO.AI TYR MICROSERVICE FINISHED");
	}
}
