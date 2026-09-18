/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the Gebo.ai API Gateway microservice.
 *
 * <p>
 * This service is the edge of the Gebo.ai microservices architecture. It is
 * built on Spring Cloud Gateway (WebFlux server) and uses Spring Cloud
 * LoadBalancer to distribute traffic across the backend service instances.
 * </p>
 *
 * <p>
 * It also answers the clients-topology bootstrap call
 * ({@code /public/ClientsTopologyProviderController}) for the whole
 * microservices installation: being the edge, the gateway is the one url a
 * client knows, so it is where a client asks what to append to it for each
 * generated microservice client - the same relative url the monolith answers it
 * on. That needs two packages outside the gateway's own to be scanned, listed
 * below.
 * </p>
 */
// scanBasePackages rather than a bare @SpringBootApplication - and deliberately
// NOT the "ai.gebo" wildcard the backend services use: the gateway is a thin
// reactive edge, and blanket-scanning would pull in the servlet-oriented
// component trees that happen to ride on its classpath. Only the two packages
// that make up the clients-topology endpoint are added:
//   ai.gebo.architecture.environment.microservices - MicroservicesArchitectureDeclaration
//       (this deployable IS the microservices architecture) and the
//       GClientsTopologyProviderImpl that derives the topology from the
//       GeboMicroservicesTopology bean;
//   ai.gebo.topology_provider - the shared ClientsTopologyProviderController.
// Given as scanBasePackages ON @SpringBootApplication (not a separate
// @ComponentScan, which would REPLACE the annotation's own scan and drop its
// TypeExcludeFilter/AutoConfigurationExcludeFilter defaults).
@SpringBootApplication(scanBasePackages = { "ai.gebo.microservices.gateway",
		"ai.gebo.architecture.environment.microservices", "ai.gebo.topology_provider" })
public class GatewayApplication {

	private static final Logger LOG = LoggerFactory.getLogger(GatewayApplication.class);

	public static void main(String[] args) {
		LOG.info("STARTING THE GEBO.AI GATEWAY");
		SpringApplication.run(GatewayApplication.class, args);
		LOG.info("GEBO.AI GATEWAY FINISHED");
	}
}
