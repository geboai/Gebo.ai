/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.eureka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Entry point of the Gebo.ai <b>Eureka</b> service-registry microservice.
 *
 * <p>
 * This is the discovery backbone of the Gebo.ai microservices architecture. Every
 * microservice app (the gateway and the LLMs backends) registers here under its
 * canonical, underscore-form application name (e.g. {@code brain_gebo_ai}), the
 * same id the shared {@code gebo.microservices.topology} exposes. That lets
 * client-side load balancing ({@code @LoadBalanced} {@code RestTemplate}/
 * {@code WebClient}, Feign) and the gateway's {@code lb://} routes resolve a
 * logical microservice id to a live instance.
 * </p>
 *
 * <p>
 * Runs standalone (it neither registers with nor fetches the registry from
 * itself, see {@code application.yml}). For high availability, peer several
 * instances via {@code eureka.client.service-url.defaultZone}.
 * </p>
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

	private static final Logger LOG = LoggerFactory.getLogger(EurekaServerApplication.class);

	public static void main(String[] args) {
		LOG.info("STARTING THE GEBO.AI EUREKA SERVICE REGISTRY");
		SpringApplication.run(EurekaServerApplication.class, args);
		LOG.info("GEBO.AI EUREKA SERVICE REGISTRY FINISHED");
	}
}
