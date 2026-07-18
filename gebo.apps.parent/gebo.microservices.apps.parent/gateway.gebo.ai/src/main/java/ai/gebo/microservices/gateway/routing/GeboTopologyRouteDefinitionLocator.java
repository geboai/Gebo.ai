/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.gateway.routing;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.stereotype.Component;

import ai.gebo.microservices.topology.GeboMicroservice;
import ai.gebo.microservices.topology.GeboMicroservicesTopology;
import reactor.core.publisher.Flux;

/**
 * Derives the gateway's backend routes from the shared microservices topology,
 * so a service becomes routable by being declared in the topology map rather
 * than by hand-writing a route for it.
 *
 * <p>
 * One route per topology member:
 * </p>
 *
 * <pre>
 *   /&lt;contextName&gt;/**  --&gt;  lb://&lt;discoveryServiceId&gt;   (no path rewriting)
 *   e.g. /brain/api/x        -&gt;   lb://brain-gebo-ai/brain/api/x
 * </pre>
 *
 * <p>
 * The path segment is the short <b>context name</b>
 * ({@link GeboMicroservice#getContextName()}, e.g. {@code brain}) - the same
 * name the target erogates its own controllers under via its
 * {@code server.servlet.context-path}. The request is forwarded to the
 * backend <b>unchanged</b>: no {@code StripPrefix} filter, because the
 * {@code /<contextName>} segment the gateway matched on is exactly the prefix
 * the backend's own Spring context already expects on every request. Stripping
 * it would misroute every call to a path the backend has never heard of. The
 * {@code lb://} authority carries the hyphenated discovery id, because a URI
 * <i>host</i> cannot contain {@code '_'} (see
 * {@link GeboMicroservice#toDiscoveryServiceId(String)}). This is exactly the
 * addressing {@code GeboMicroserviceUrlResolver}'s GATEWAY strategy already
 * assumes ({@code <gatewayBaseUrl>/{microserviceId}}, expanded to the context
 * name).
 * </p>
 *
 * <h2>Why not the built-in discovery locator</h2>
 * <p>
 * Spring Cloud Gateway ships
 * {@code spring.cloud.gateway.server.webflux.discovery.locator.enabled}, which
 * auto-routes <b>every</b> service registered in Eureka. That is deliberately
 * left off here: it would publish anything that happens to register - including
 * services never meant to sit behind the public edge - at the moment it appears.
 * This locator is the allow-list version of the same idea: the topology map is
 * the allow-list, so only declared Gebo services are exposed, and a rogue or
 * accidental registration is routed nowhere.
 * </p>
 *
 * <h2>What is dynamic and what is not</h2>
 * <ul>
 * <li><b>Instances are fully dynamic.</b> Each route targets {@code lb://}, so
 * Spring Cloud LoadBalancer resolves the live instances of that service from
 * Eureka on every call. Scaling a backend out, replacing it, or restarting it
 * needs nothing from the gateway - new instances are picked up without a
 * restart.</li>
 * <li><b>The service set follows the topology.</b> Adding a microservice means
 * adding it under {@code gebo.microservices.topology.services} in the gateway's
 * application.yml - configuration, not code, and no rebuild. The route set is
 * re-read whenever Spring Cloud publishes a {@code RefreshRoutesEvent} (the
 * discovery heartbeat does this), so this locator is queried again rather than
 * being frozen at startup.</li>
 * </ul>
 *
 * <p>
 * Set {@code gebo.gateway.routing.topology.enabled: false} to switch these
 * routes off entirely and leave the gateway proxying nothing.
 * </p>
 */
@Component
@ConditionalOnProperty(prefix = "gebo.gateway.routing.topology", name = "enabled", havingValue = "true",
		matchIfMissing = true)
public class GeboTopologyRouteDefinitionLocator implements RouteDefinitionLocator {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeboTopologyRouteDefinitionLocator.class);

	/** Route ids are prefixed so they are recognisable in /actuator/gateway/routes. */
	private static final String ROUTE_ID_PREFIX = "topology-";

	private static final String PATH_PREDICATE = "Path";

	private final GeboMicroservicesTopology topology;

	public GeboTopologyRouteDefinitionLocator(GeboMicroservicesTopology topology) {
		this.topology = topology;
		LOGGER.info("Topology-driven gateway routing enabled: {} service(s) reachable as /<contextName>/**",
				topology.microservices().size());
	}

	/**
	 * Re-derives the routes from the topology. Called again on every
	 * {@code RefreshRoutesEvent}, so the route set is never frozen at startup.
	 *
	 * @return one route per topology member
	 */
	@Override
	public Flux<RouteDefinition> getRouteDefinitions() {
		List<RouteDefinition> definitions = new ArrayList<>();
		for (GeboMicroservice microservice : topology.microservices()) {
			definitions.add(toRouteDefinition(microservice));
		}
		return Flux.fromIterable(definitions);
	}

	/**
	 * Builds {@code /<contextName>/** -> lb://<discoveryServiceId>} (no path
	 * rewriting) for one service.
	 *
	 * @param microservice a topology member
	 * @return its route definition
	 */
	private RouteDefinition toRouteDefinition(GeboMicroservice microservice) {
		String contextName = microservice.getContextName();

		RouteDefinition definition = new RouteDefinition();
		definition.setId(ROUTE_ID_PREFIX + contextName);
		// lb:// + the DNS-safe id: the canonical underscore id cannot be a URI host.
		definition.setUri(URI.create(microservice.getLoadBalancerUri()));

		PredicateDefinition path = new PredicateDefinition();
		path.setName(PATH_PREDICATE);
		Map<String, String> pathArgs = new LinkedHashMap<>();
		pathArgs.put("pattern", "/" + contextName + "/**");
		path.setArgs(pathArgs);
		definition.setPredicates(List.of(path));

		// No StripPrefix: the backend's own server.servlet.context-path is
		// "/<contextName>", so the matched segment is exactly what the backend's
		// Spring context expects on every request - forward it unchanged.
		definition.setFilters(List.of());

		return definition;
	}
}
