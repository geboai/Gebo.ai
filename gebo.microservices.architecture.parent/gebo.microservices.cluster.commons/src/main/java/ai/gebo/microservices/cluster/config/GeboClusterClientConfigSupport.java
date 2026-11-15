/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.cluster.config;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.webclient.WebClientCustomizer;
import org.springframework.web.reactive.function.client.WebClient;

import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.microservices.topology.GeboMicroservicesTopology;
import ai.gebo.microservices.topology.config.GeboMicroservicesTopologyProperties;

/**
 * What the auto-configurations of the cluster clients (the security and ACL clients
 * calling heimdall) share: the WebClient builder they start from, and the address
 * of the service that owns the data they read.
 *
 * <p>
 * The owner's address comes from one of two places, chosen by
 * {@code gebo.microservices.topology.enabled}:
 * </p>
 * <ul>
 * <li><b>topology on</b> (the default, the full microservices architecture): the
 * topology's {@link GeboMicroserviceUrlResolver}, so the address follows the
 * deployment's addressing strategy (load balancer, gateway, direct entries) and is
 * called through a {@code @LoadBalanced} WebClient;</li>
 * <li><b>topology off</b> (a service deployed beside a monolith): the client's own
 * {@code base-url}, called through a plain WebClient, with no discovery.</li>
 * </ul>
 *
 * Gebo.ai comment agent
 */
public final class GeboClusterClientConfigSupport {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeboClusterClientConfigSupport.class);

	private GeboClusterClientConfigSupport() {
	}

	/**
	 * A WebClient builder made as Spring Boot makes its own: with the application's
	 * customizers applied. With tracing, that is the observation filter that gives
	 * each call a span and sends the current trace (the traceparent header) to the
	 * service called. Spring Boot's builder bean itself cannot be injected by the
	 * clients: it is a WebClient.Builder like theirs, and theirs would be a candidate
	 * for its own injection.
	 *
	 * @param customizers the application's WebClient customizers
	 * @return a new builder, customized
	 */
	public static WebClient.Builder customizedWebClientBuilder(ObjectProvider<WebClientCustomizer> customizers) {
		WebClient.Builder builder = WebClient.builder();
		customizers.orderedStream().forEach(customizer -> customizer.customize(builder));
		return builder;
	}

	/**
	 * The resolver a cluster client builds its owner's urls with.
	 *
	 * <p>
	 * With the topology on, the topology's own resolver, and {@code baseUrl} is
	 * ignored - with a warning when it is set, since someone meant something by it.
	 * With the topology off there is no such resolver, and {@code baseUrl} is the
	 * owner's address: the resolver returned then holds that single direct entry,
	 * so the client builds its urls the same way in both cases.
	 * </p>
	 *
	 * @param topologyResolver the topology's resolver, absent when the topology is off
	 * @param ownerMicroserviceId id of the microservice that owns the data (e.g.
	 *            {@code heimdall_gebo_ai})
	 * @param baseUrl the owner's base url, context path included, for the topology off
	 * @param baseUrlProperty the property {@code baseUrl} is bound from, for the messages
	 * @return the resolver of the owner's base url
	 * @throws IllegalStateException with the topology off and no {@code baseUrl}: the
	 *             client would have nowhere to call, and the service must not start as
	 *             if it had
	 */
	public static GeboMicroserviceUrlResolver ownerUrlResolver(
			ObjectProvider<GeboMicroserviceUrlResolver> topologyResolver, String ownerMicroserviceId, String baseUrl,
			String baseUrlProperty) {
		boolean hasBaseUrl = baseUrl != null && !baseUrl.isBlank();
		GeboMicroserviceUrlResolver fromTopology = topologyResolver.getIfAvailable();
		if (fromTopology != null) {
			if (hasBaseUrl) {
				LOGGER.warn("{}={} is ignored: the topology is on, and '{}' is reached at the address it resolves",
						baseUrlProperty, baseUrl, ownerMicroserviceId);
			}
			LOGGER.debug("'{}' is reached through the topology ({} strategy)", ownerMicroserviceId,
					fromTopology.getStrategy());
			return fromTopology;
		}
		if (!hasBaseUrl) {
			throw new IllegalStateException("The topology is switched off (" + GeboMicroservicesTopologyProperties.PREFIX
					+ "." + GeboMicroservicesTopologyProperties.ENABLED + "=false), so " + baseUrlProperty
					+ " must give the base url of '" + ownerMicroserviceId + "', context path included");
		}
		String address = baseUrl.trim();
		LOGGER.info("The topology is switched off: '{}' is reached at {} ({})", ownerMicroserviceId, address,
				baseUrlProperty);
		return GeboMicroserviceUrlResolver.direct(GeboMicroservicesTopology.of(List.of()),
				Map.of(ownerMicroserviceId, address));
	}
}
