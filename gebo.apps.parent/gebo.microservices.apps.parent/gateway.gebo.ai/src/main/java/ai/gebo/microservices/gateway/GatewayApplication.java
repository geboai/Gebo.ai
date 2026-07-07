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
 */
@SpringBootApplication
public class GatewayApplication {

	private static final Logger LOG = LoggerFactory.getLogger(GatewayApplication.class);

	public static void main(String[] args) {
		LOG.info("STARTING THE GEBO.AI GATEWAY");
		SpringApplication.run(GatewayApplication.class, args);
		LOG.info("GEBO.AI GATEWAY FINISHED");
	}
}
