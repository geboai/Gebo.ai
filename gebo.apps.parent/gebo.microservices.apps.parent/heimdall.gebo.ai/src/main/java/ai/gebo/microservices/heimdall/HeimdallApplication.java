/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.heimdall;

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
 * Entry point of the Gebo.ai <b>heimdall</b> security microservice.
 *
 * <p>
 * Built on the {@code gebo.microservices.starter} stack. It is the keeper of the
 * secrets: the crypting keys live here and this is the only service that ever
 * decrypts secret material. It publishes two distinct surfaces over the same
 * {@code IGeboSecretsAccessService}:
 * </p>
 * <ul>
 * <li>{@code api/admin/SecretsController} - the ADMIN/UI surface, which exposes
 * secret <i>metadata</i> and the create/delete operations, never content;</li>
 * <li>{@code api/cluster/SecretsController} - the service-to-service surface,
 * which exposes the whole interface, content included, and only to microservices
 * currently registered in the discovery registry.</li>
 * </ul>
 *
 * <p>
 * The other services never see either: they depend on
 * {@code gebo.microservices.secrets.client}, which implements the same interface
 * against the cluster surface, so their code is identical whether the secrets are
 * in-process or behind the network.
 * </p>
 *
 * <p>
 * It is a REST-only edge: it owns no messaging module in the topology.
 * </p>
 */
@SpringBootApplication
@ComponentScan(basePackages = "ai.gebo")
@EnableConfigurationProperties
@EnableAsync
@EnableScheduling
@EnableMongoRepositories(basePackages = "ai.gebo")
public class HeimdallApplication {

	private static final String ERROR_MESSAGE = "The java property or environment variable GEBO_HOME must be set";
	private static final Logger LOG = LoggerFactory.getLogger(HeimdallApplication.class);

	public static void main(String[] args) {
		LOG.info("STARTING THE GEBO.AI HEIMDALL SECURITY MICROSERVICE");
		if (EnvironmentHolder.GEBO_HOME_ENVIRONMENT_VALUE == null
				|| EnvironmentHolder.GEBO_HOME_ENVIRONMENT_VALUE.trim().isEmpty()) {
			LOG.error(ERROR_MESSAGE);
			System.err.println(ERROR_MESSAGE);
			System.exit(-1);
		}
		SpringApplication.run(HeimdallApplication.class, args);
		LOG.info("GEBO.AI HEIMDALL SECURITY MICROSERVICE FINISHED");
	}
}
