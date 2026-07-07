/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.topology.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Deployment / third-party overrides of the microservices topology, bound from
 * {@code gebo.microservices.topology.*} in {@code application.yml}.
 *
 * <p>
 * The topology is three levels:
 * {@code microserviceId -> messagingModuleId -> [messagingSystemId]}. Microservice
 * ids are the dot-free, underscore form (e.g. {@code brain_gebo_ai}), so they are
 * plain YAML map keys - no Spring bracket syntax needed. Example - add a
 * third-party service and override an existing one:
 * </p>
 *
 * <pre>
 * gebo:
 *   microservices:
 *     topology:
 *       include-defaults: true          # default; false = start from an empty map
 *       services:
 *         myservice_gebo_ai:
 *           my-custom-module: [ my-custom-component ]
 *         vectorizator_gebo_ai:         # overrides the whole default entry for this service
 *           vectorizator-module: [ vectorization-component, vectorization-emitter-component ]
 *           rag-threashold-autotune-module: [ rag-threashold-autotune-component ]
 * </pre>
 *
 * Gebo.ai comment agent
 */
@ConfigurationProperties(prefix = "gebo.microservices.topology")
public class GeboMicroservicesTopologyProperties {

	/**
	 * Whether to seed the topology with the built-in Gebo services
	 * ({@code GeboStandardMicroservices.DEFAULTS}) before applying
	 * {@link #services}. When {@code false} the topology is defined solely by
	 * {@link #services}.
	 */
	private boolean includeDefaults = true;

	/**
	 * Topology overrides keyed by microservice id (= application name =
	 * LoadBalancer service-id), each mapping messaging module id to its system
	 * ids. Applied on top of the defaults: a new microservice key adds a service,
	 * an existing key replaces that service's whole module-to-systems map.
	 */
	private Map<String, Map<String, List<String>>> services = new LinkedHashMap<>();

	public boolean isIncludeDefaults() {
		return includeDefaults;
	}

	public void setIncludeDefaults(boolean includeDefaults) {
		this.includeDefaults = includeDefaults;
	}

	public Map<String, Map<String, List<String>>> getServices() {
		return services;
	}

	public void setServices(Map<String, Map<String, List<String>>> services) {
		this.services = services;
	}
}
