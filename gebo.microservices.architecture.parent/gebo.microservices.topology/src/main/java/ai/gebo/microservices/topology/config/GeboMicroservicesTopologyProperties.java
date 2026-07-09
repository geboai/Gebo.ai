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
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;

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

	/**
	 * Microservice ids that participate in the LLM models-replication cache. This
	 * is part of the <b>shared</b> topology configuration: when {@code null} (the
	 * default) the built-in
	 * {@link ai.gebo.microservices.topology.GeboStandardMicroservices#DEFAULT_MODELS_REPLICATION_PARTICIPANTS}
	 * set is used; set it here to override the participant set for the whole
	 * deployment. An explicit empty list disables the cache everywhere. Ids may be
	 * dotted or underscore form.
	 */
	private List<String> modelsReplicationParticipants = null;

	/**
	 * How the {@link ai.gebo.microservices.topology.GeboMicroserviceUrlResolver}
	 * builds the HTTP base url used to contact a microservice over
	 * RestTemplate/WebClient/Feign. Bound from
	 * {@code gebo.microservices.topology.url.*}.
	 */
	@NestedConfigurationProperty
	private Url url = new Url();

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

	public List<String> getModelsReplicationParticipants() {
		return modelsReplicationParticipants;
	}

	public void setModelsReplicationParticipants(List<String> modelsReplicationParticipants) {
		this.modelsReplicationParticipants = modelsReplicationParticipants;
	}

	public Url getUrl() {
		return url;
	}

	public void setUrl(Url url) {
		this.url = url != null ? url : new Url();
	}

	/**
	 * HTTP base-url resolution settings bound from
	 * {@code gebo.microservices.topology.url.*} and applied by
	 * {@link ai.gebo.microservices.topology.GeboMicroserviceUrlResolver}. Example:
	 *
	 * <pre>
	 * gebo:
	 *   microservices:
	 *     topology:
	 *       url:
	 *         strategy: load_balancer        # load_balancer (default) | gateway | direct
	 *         scheme: http
	 *         # gateway strategy only:
	 *         gateway-base-url: http://gateway_gebo_ai:8080
	 *         gateway-path-template: /{microserviceId}
	 *         # direct strategy source and/or per-service pins in any strategy:
	 *         direct:
	 *           brain_gebo_ai: http://localhost:8081
	 *           vectorizator_gebo_ai: http://localhost:8082
	 * </pre>
	 *
	 * Gebo.ai comment agent
	 */
	public static class Url {

		/**
		 * Base-url build strategy: {@code LOAD_BALANCER} (client-side Spring Cloud
		 * LoadBalancer / Feign, the default), {@code GATEWAY} (route through the API
		 * gateway) or {@code DIRECT} (fixed {@link #direct} map). Case-insensitive.
		 */
		private GeboMicroserviceUrlResolver.Strategy strategy = GeboMicroserviceUrlResolver.Strategy.LOAD_BALANCER;

		/** Url scheme, e.g. {@code http} or {@code https}. */
		private String scheme = GeboMicroserviceUrlResolver.DEFAULT_SCHEME;

		/**
		 * Gateway base url for the {@code GATEWAY} strategy (e.g.
		 * {@code http://gateway_gebo_ai:8080}). When blank the load-balanced gateway
		 * ({@code <scheme>://gateway_gebo_ai}) is used.
		 */
		private String gatewayBaseUrl = null;

		/**
		 * Gateway path template for the {@code GATEWAY} strategy; the literal
		 * {@code {microserviceId}} is replaced by the target microservice id.
		 */
		private String gatewayPathTemplate = GeboMicroserviceUrlResolver.DEFAULT_GATEWAY_PATH_TEMPLATE;

		/**
		 * Fixed {@code microserviceId -> base url} map: the source of addresses for
		 * the {@code DIRECT} strategy, and a per-service override that always wins in
		 * the {@code LOAD_BALANCER}/{@code GATEWAY} strategies. Keys may be dotted or
		 * underscore form.
		 */
		private Map<String, String> direct = new LinkedHashMap<>();

		public GeboMicroserviceUrlResolver.Strategy getStrategy() {
			return strategy;
		}

		public void setStrategy(GeboMicroserviceUrlResolver.Strategy strategy) {
			this.strategy = strategy != null ? strategy : GeboMicroserviceUrlResolver.Strategy.LOAD_BALANCER;
		}

		public String getScheme() {
			return scheme;
		}

		public void setScheme(String scheme) {
			this.scheme = scheme;
		}

		public String getGatewayBaseUrl() {
			return gatewayBaseUrl;
		}

		public void setGatewayBaseUrl(String gatewayBaseUrl) {
			this.gatewayBaseUrl = gatewayBaseUrl;
		}

		public String getGatewayPathTemplate() {
			return gatewayPathTemplate;
		}

		public void setGatewayPathTemplate(String gatewayPathTemplate) {
			this.gatewayPathTemplate = gatewayPathTemplate;
		}

		public Map<String, String> getDirect() {
			return direct;
		}

		public void setDirect(Map<String, String> direct) {
			this.direct = direct != null ? direct : new LinkedHashMap<>();
		}
	}
}
