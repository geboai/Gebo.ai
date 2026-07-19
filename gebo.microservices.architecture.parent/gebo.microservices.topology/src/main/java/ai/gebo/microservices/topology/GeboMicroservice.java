/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.topology;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * An immutable declaration of one Gebo.ai microservice within the topology.
 *
 * <p>
 * The topology is <b>three levels</b>:
 * {@code microserviceId -> messagingModuleId -> [messagingSystemId]}. A
 * microservice hosts one or more messaging modules ({@code GStandardModulesConstraints}
 * ids, e.g. {@code vectorizator-module}), and each module hosts one or more
 * messaging systems - the individual emitter/receiver components whose
 * {@code IGMessagingSystem.getMessagingSystemId()} the code returns (e.g.
 * {@code vectorization-emitter-component}). The broker addresses a component by
 * its {@code moduleId.systemId} complete id.
 * </p>
 *
 * <p>
 * The microservice is identified by its <b>application name</b> - which is,
 * deliberately and by convention, both the microservice id and the Spring Boot
 * {@code spring.application.name}. A microservice's identity is its application
 * name ({@link #equals(Object)} / {@link #hashCode()}).
 * </p>
 *
 * <p>
 * The stored name is <b>normalised to underscores</b> ({@code '.'} &rarr;
 * {@code '_'}, see {@link #normalizeName(String)}), so ids are dot-free
 * (e.g. {@code brain_gebo_ai}). This keeps them safe as YAML map keys (no
 * Spring bracket syntax needed) and as queue names. Passing either
 * {@code brain.gebo.ai} or {@code brain_gebo_ai} yields the same id, so a
 * running service can be discovered from its {@code spring.application.name} by
 * applying the same rule (see
 * {@link GeboMicroservicesTopology#forApplicationName(String)}).
 * </p>
 *
 * <p>
 * <b>Discovery id.</b> The canonical id is deliberately NOT usable as a
 * discovery / LoadBalancer service-id: {@code java.net.URI} follows the RFC and
 * rejects {@code '_'} in a host, so {@code URI.getHost()} on
 * {@code lb://brain_gebo_ai} returns {@code null} and Spring Cloud Gateway
 * fails the route with "Invalid host" (the same applies to a
 * {@code @LoadBalanced} client resolving {@code http://brain_gebo_ai}).
 * Services therefore register under the DNS-safe <b>discovery id</b> -
 * the canonical id with {@code '_'} &rarr; {@code '-'} (e.g.
 * {@code brain-gebo-ai}, see {@link #getDiscoveryServiceId()}) - which each
 * service publishes as its Eureka {@code appname} and, crucially, as its
 * {@code virtual-host-name} (Spring Cloud resolves instances by VIP address,
 * not by app name). The canonical underscore id keeps driving the topology map
 * and the queue names; only the address the network resolves is hyphenated.
 * </p>
 *
 * Gebo.ai comment agent
 */
public final class GeboMicroservice {

	/** Suffix appended to the application name to name the service's external ingress queue (see §6 of the integration design). */
	public static final String INPUT_QUEUE_SUFFIX = ".inputq";

	/** Scheme prefix for a Spring Cloud LoadBalancer backend target (e.g. {@code lb://brain.gebo.ai}). */
	public static final String LOAD_BALANCER_SCHEME = "lb://";

	/** Suffix every Gebo microservice id carries, stripped to get the short context name (e.g. {@code brain-gebo-ai} -> {@code brain}). */
	private static final String DISCOVERY_ID_SUFFIX = "-gebo-ai";

	private final String applicationName;
	/** moduleId -> immutable list of systemIds (may be empty when a module's systems are dynamic/not enumerated). */
	private final Map<String, List<String>> messagingModules;

	/**
	 * @param applicationName the microservice id / {@code spring.application.name}
	 *            / LoadBalancer service-id (e.g. {@code brain.gebo.ai}); required
	 * @param messagingModules {@code messagingModuleId -> [messagingSystemId]};
	 *            may be empty
	 */
	public GeboMicroservice(String applicationName, Map<String, List<String>> messagingModules) {
		this.applicationName = normalizeName(Objects.requireNonNull(applicationName, "applicationName"));
		Map<String, List<String>> copy = new LinkedHashMap<>();
		messagingModules.forEach((moduleId, systemIds) -> copy.put(moduleId,
				systemIds == null ? List.of() : List.copyOf(systemIds)));
		this.messagingModules = Map.copyOf(copy);
	}

	/**
	 * Starts a fluent builder for a microservice.
	 *
	 * @param applicationName the microservice id / application name
	 * @return a new builder
	 */
	public static Builder named(String applicationName) {
		return new Builder(applicationName);
	}

	/**
	 * Normalises a microservice name to its canonical id form by replacing every
	 * {@code '.'} with {@code '_'}. This is the single rule mapping a
	 * (possibly dotted) {@code spring.application.name} to the dot-free id used
	 * as the topology key and the queue base.
	 *
	 * @param name a microservice name (dotted or already normalised); may be null
	 * @return the normalised id, or {@code null} if {@code name} is {@code null}
	 */
	public static String normalizeName(String name) {
		return name == null ? null : name.replace('.', '_');
	}

	/**
	 * Maps any microservice name to the DNS-safe id under which the service is
	 * discovered: dots and underscores both become {@code '-'} (e.g.
	 * {@code brain.gebo.ai} and {@code brain_gebo_ai} alike yield
	 * {@code brain-gebo-ai}).
	 *
	 * <p>
	 * This exists because a host may not contain {@code '_'}: {@code java.net.URI}
	 * returns a {@code null} host for {@code lb://brain_gebo_ai}, which makes
	 * Spring Cloud Gateway reject the route ("Invalid host") and a
	 * {@code @LoadBalanced} client reject the target. The canonical underscore id
	 * remains the topology key and the queue base; this is only the address form.
	 * </p>
	 *
	 * @param name a microservice name (dotted, underscore or already hyphenated);
	 *             may be null
	 * @return the discovery service-id, or {@code null} if {@code name} is
	 *         {@code null}
	 */
	public static String toDiscoveryServiceId(String name) {
		return name == null ? null : normalizeName(name).replace('_', '-');
	}

	/**
	 * The short name this microservice erogates its own controllers under, and
	 * registers as its {@code server.servlet.context-path}: the discovery id
	 * (e.g. {@code brain-gebo-ai}) with the trailing {@code -gebo-ai} stripped
	 * (e.g. {@code brain}). This is the single value shared by three things that
	 * must otherwise be kept in lockstep by hand: the backend's own
	 * {@code server.servlet.context-path}, the gateway's topology route path
	 * segment ({@code /<contextName>/**}), and the path segment
	 * {@link GeboMicroserviceUrlResolver} appends to every base url it resolves -
	 * so a request built by any of the three always lands on a path the backend
	 * actually serves.
	 *
	 * @param name a microservice name (dotted, underscore or already hyphenated);
	 *             may be null
	 * @return the context name, or {@code null} if {@code name} is {@code null}
	 */
	public static String toContextName(String name) {
		String discoveryId = toDiscoveryServiceId(name);
		if (discoveryId == null) {
			return null;
		}
		return discoveryId.endsWith(DISCOVERY_ID_SUFFIX)
				? discoveryId.substring(0, discoveryId.length() - DISCOVERY_ID_SUFFIX.length())
				: discoveryId;
	}

	/**
	 * {@link #toContextName(String)}, prefixed with {@code '/'} - ready to use as
	 * a {@code server.servlet.context-path} value or a route path segment.
	 *
	 * @param name a microservice name (dotted, underscore or already hyphenated);
	 *             may be null
	 * @return the context path (e.g. {@code /brain}), or {@code null} if
	 *         {@code name} is {@code null}
	 */
	public static String toContextPath(String name) {
		String contextName = toContextName(name);
		return contextName == null ? null : "/" + contextName;
	}

	/**
	 * The microservice id, identical to the deployable's {@code spring.application.name}
	 * and to the Spring Cloud LoadBalancer service-id.
	 *
	 * @return the application name (e.g. {@code brain.gebo.ai})
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * Alias for {@link #getApplicationName()}; the microservice id is, by
	 * convention, exactly the microservice application name.
	 *
	 * @return the microservice id
	 */
	public String getMicroserviceId() {
		return applicationName;
	}

	/**
	 * The full second+third topology levels hosted by this microservice:
	 * {@code messagingModuleId -> [messagingSystemId]}.
	 *
	 * @return an immutable module-to-systems map
	 */
	public Map<String, List<String>> getMessagingModules() {
		return messagingModules;
	}

	/**
	 * @return the messaging module ids hosted by this microservice
	 */
	public Set<String> getMessagingModuleIds() {
		return messagingModules.keySet();
	}

	/**
	 * The messaging system ids hosted within a given module of this microservice.
	 *
	 * @param messagingModuleId a module id
	 * @return an immutable list of system ids, empty if the module is absent or
	 *         has no enumerated systems
	 */
	public List<String> getMessagingSystemIds(String messagingModuleId) {
		return messagingModules.getOrDefault(messagingModuleId, List.of());
	}

	/**
	 * The external ingress queue name for this microservice, i.e.
	 * {@code <application-name>.inputq}.
	 *
	 * @return the input queue name (e.g. {@code brain.gebo.ai.inputq})
	 */
	public String getInputQueue() {
		return applicationName + INPUT_QUEUE_SUFFIX;
	}

	/**
	 * The id this microservice is discovered by - its canonical id in DNS-safe
	 * form. This, not {@link #getMicroserviceId()}, is what a {@code lb://} uri or
	 * a {@code @LoadBalanced} target must name, and what the service registers in
	 * Eureka as its {@code appname} / {@code virtual-host-name}.
	 *
	 * @return the discovery service-id (e.g. {@code brain-gebo-ai})
	 */
	public String getDiscoveryServiceId() {
		return toDiscoveryServiceId(applicationName);
	}

	/**
	 * The short context name this microservice erogates under (e.g. {@code brain}).
	 *
	 * @return the context name
	 * @see #toContextName(String)
	 */
	public String getContextName() {
		return toContextName(applicationName);
	}

	/**
	 * The {@code server.servlet.context-path} this microservice is deployed with
	 * (e.g. {@code /brain}).
	 *
	 * @return the context path
	 * @see #toContextPath(String)
	 */
	public String getContextPath() {
		return toContextPath(applicationName);
	}

	/**
	 * The Spring Cloud LoadBalancer target uri for this microservice, i.e.
	 * {@code lb://<discovery-service-id>}, ready to use as a gateway route
	 * {@code uri}. Built on the hyphenated discovery id, never on the canonical
	 * underscore id, which a URI host cannot carry.
	 *
	 * @return the {@code lb://} uri (e.g. {@code lb://brain-gebo-ai})
	 */
	public String getLoadBalancerUri() {
		return LOAD_BALANCER_SCHEME + getDiscoveryServiceId();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof GeboMicroservice other)) {
			return false;
		}
		return applicationName.equals(other.applicationName);
	}

	@Override
	public int hashCode() {
		return applicationName.hashCode();
	}

	@Override
	public String toString() {
		return applicationName + " -> " + messagingModules;
	}

	/**
	 * Fluent builder preserving module declaration order.
	 *
	 * Gebo.ai comment agent
	 */
	public static final class Builder {

		private final String applicationName;
		private final Map<String, List<String>> messagingModules = new LinkedHashMap<>();

		private Builder(String applicationName) {
			this.applicationName = applicationName;
		}

		/**
		 * Declares a module hosted by this microservice and its (statically
		 * known) messaging systems.
		 *
		 * @param messagingModuleId the module id
		 * @param messagingSystemIds the system ids within that module; may be
		 *            empty when the module's systems are dynamic/not enumerated
		 * @return this builder
		 */
		public Builder module(String messagingModuleId, String... messagingSystemIds) {
			messagingModules.put(messagingModuleId, List.of(messagingSystemIds));
			return this;
		}

		/**
		 * @return the immutable microservice
		 */
		public GeboMicroservice build() {
			return new GeboMicroservice(applicationName, messagingModules);
		}
	}
}
