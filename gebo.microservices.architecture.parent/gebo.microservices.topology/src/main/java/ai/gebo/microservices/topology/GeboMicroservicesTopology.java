/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.topology;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * An immutable, validated view of the Gebo.ai microservices topology
 * ({@code microserviceId -> messagingModuleId -> [messagingSystemId]}).
 *
 * <p>
 * From a set of {@link GeboMicroservice} declarations it materialises:
 * </p>
 * <ul>
 * <li>{@link #microserviceToMessagingModules()} - the full three-level map;</li>
 * <li>{@link #messagingModuleToMicroservice()} - the reverse routing table
 * (moduleId -&gt; microserviceId) used by the external messaging bridge to decide
 * which {@code <service>.inputq} a message targeting a module is published to;</li>
 * <li>{@link #messagingModuleToSystems()} - moduleId -&gt; its system ids.</li>
 * </ul>
 *
 * <p>
 * Construction validates: unique application name; each messaging module hosted
 * by exactly one microservice; and no duplicate system id within a module. It
 * fails fast with a descriptive error, so a mis-declared deployment/third-party
 * override cannot silently hijack another service's routing.
 * </p>
 *
 * <p>
 * In a Spring deployment the effective instance is produced by
 * {@link ai.gebo.microservices.topology.config.GeboMicroservicesTopologyAutoConfiguration}
 * (built-in defaults merged with {@code application.yml}). Outside Spring,
 * {@link #defaults()} returns the built-in topology.
 * </p>
 *
 * Gebo.ai comment agent
 */
public final class GeboMicroservicesTopology {

	private final List<GeboMicroservice> microservices;
	private final Map<String, Map<String, List<String>>> microserviceToModules;
	private final Map<String, String> moduleToMicroservice;
	private final Map<String, List<String>> moduleToSystems;
	private final Map<String, GeboMicroservice> byApplicationName;

	private GeboMicroservicesTopology(Collection<GeboMicroservice> services) {
		List<GeboMicroservice> orderedServices = new ArrayList<>();
		Map<String, Map<String, List<String>>> microserviceToModulesTmp = new LinkedHashMap<>();
		Map<String, String> moduleToMicroserviceTmp = new LinkedHashMap<>();
		Map<String, List<String>> moduleToSystemsTmp = new LinkedHashMap<>();
		Map<String, GeboMicroservice> byApplicationNameTmp = new LinkedHashMap<>();

		for (GeboMicroservice service : services) {
			String microserviceId = service.getApplicationName();

			if (byApplicationNameTmp.putIfAbsent(microserviceId, service) != null) {
				throw new IllegalStateException(
						"Duplicate microservice application name in topology: '" + microserviceId + "'.");
			}
			orderedServices.add(service);
			microserviceToModulesTmp.put(microserviceId, service.getMessagingModules());

			service.getMessagingModules().forEach((moduleId, systemIds) -> {
				String previousOwner = moduleToMicroserviceTmp.putIfAbsent(moduleId, microserviceId);
				if (previousOwner != null) {
					throw new IllegalStateException("Messaging module '" + moduleId
							+ "' is assigned to more than one microservice: '" + previousOwner + "' and '"
							+ microserviceId + "'. Each module must belong to exactly one microservice.");
				}
				Set<String> uniqueSystems = new LinkedHashSet<>();
				for (String systemId : systemIds) {
					if (!uniqueSystems.add(systemId)) {
						throw new IllegalStateException("Duplicate messaging system id '" + systemId + "' within module '"
								+ moduleId + "' (microservice '" + microserviceId + "').");
					}
				}
				moduleToSystemsTmp.put(moduleId, systemIds);
			});
		}

		this.microservices = List.copyOf(orderedServices);
		this.microserviceToModules = Collections.unmodifiableMap(microserviceToModulesTmp);
		this.moduleToMicroservice = Map.copyOf(moduleToMicroserviceTmp);
		this.moduleToSystems = Collections.unmodifiableMap(moduleToSystemsTmp);
		this.byApplicationName = Map.copyOf(byApplicationNameTmp);
	}

	/**
	 * Builds a validated topology from the given microservice declarations.
	 *
	 * @param services the microservices; must satisfy the topology invariants
	 * @return an immutable topology
	 * @throws IllegalStateException if an invariant is violated
	 */
	public static GeboMicroservicesTopology of(Collection<GeboMicroservice> services) {
		return new GeboMicroservicesTopology(services);
	}

	/**
	 * The built-in Gebo topology ({@link GeboStandardMicroservices#DEFAULTS}),
	 * with no {@code application.yml} overrides applied. Useful outside a Spring
	 * context.
	 *
	 * @return the default topology
	 */
	public static GeboMicroservicesTopology defaults() {
		return of(GeboStandardMicroservices.DEFAULTS);
	}

	/**
	 * @return an immutable list of every microservice in this topology, in
	 *         declaration/merge order
	 */
	public List<GeboMicroservice> microservices() {
		return microservices;
	}

	/**
	 * The full three-level topology map:
	 * {@code microserviceId -> (messagingModuleId -> [messagingSystemId])}.
	 *
	 * @return an immutable nested map
	 */
	public Map<String, Map<String, List<String>>> microserviceToMessagingModules() {
		return microserviceToModules;
	}

	/**
	 * The reverse routing table: for each {@code messagingModuleId}, the
	 * {@code microserviceId} that hosts it.
	 *
	 * @return an immutable {@code messagingModuleId -> microserviceId} map
	 */
	public Map<String, String> messagingModuleToMicroservice() {
		return moduleToMicroservice;
	}

	/**
	 * For each {@code messagingModuleId}, its declared {@code messagingSystemId}s
	 * (flattened across all microservices).
	 *
	 * @return an immutable {@code messagingModuleId -> [messagingSystemId]} map
	 */
	public Map<String, List<String>> messagingModuleToSystems() {
		return moduleToSystems;
	}

	/**
	 * Looks up a microservice by its application name / microservice id.
	 *
	 * @param microserviceId the application name (e.g. {@code brain.gebo.ai})
	 * @return the matching microservice, if any
	 */
	public Optional<GeboMicroservice> forMicroserviceId(String microserviceId) {
		return Optional.ofNullable(byApplicationName.get(microserviceId));
	}

	/**
	 * Resolves a microservice from a (possibly dotted) application name -
	 * e.g. a running service's {@code spring.application.name} - by applying the
	 * {@code '.'} &rarr; {@code '_'} normalisation rule
	 * ({@link GeboMicroservice#normalizeName(String)}) before lookup. This is how
	 * a service discovers <b>itself</b> in the topology.
	 *
	 * @param applicationName the application name (e.g. {@code brain.gebo.ai} or
	 *            {@code brain_gebo_ai})
	 * @return the matching microservice, if any
	 */
	public Optional<GeboMicroservice> forApplicationName(String applicationName) {
		return forMicroserviceId(GeboMicroservice.normalizeName(applicationName));
	}

	/**
	 * Resolves the microservice that hosts a given messaging module.
	 *
	 * @param messagingModuleId a module id
	 * @return the owning microservice, if the module is mapped
	 */
	public Optional<GeboMicroservice> forMessagingModuleId(String messagingModuleId) {
		String microserviceId = moduleToMicroservice.get(messagingModuleId);
		return microserviceId == null ? Optional.empty() : forMicroserviceId(microserviceId);
	}

	/**
	 * Resolves the external ingress queue ({@code <service>.inputq}) a message
	 * targeting the given module must be delivered to.
	 *
	 * @param messagingModuleId a module id
	 * @return the target input queue, if the module is mapped
	 */
	public Optional<String> inputQueueForMessagingModuleId(String messagingModuleId) {
		return forMessagingModuleId(messagingModuleId).map(GeboMicroservice::getInputQueue);
	}
}
