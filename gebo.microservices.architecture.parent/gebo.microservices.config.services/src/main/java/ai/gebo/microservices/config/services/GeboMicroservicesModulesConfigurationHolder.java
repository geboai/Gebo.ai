/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.config.services;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import ai.gebo.config.model.GeboModuleInfo;
import ai.gebo.config.services.IGeboModulesConfigurationHolder;
import ai.gebo.config.services.impl.GeboModulesConfigurationHolderImpl;
import ai.gebo.microservices.topology.GeboMicroservice;
import ai.gebo.microservices.topology.GeboMicroservicesTopology;

/**
 * Answers "which modules are enabled?" from the CLUSTER rather than from static
 * configuration.
 *
 * <p>
 * {@link GeboModulesConfigurationHolderImpl} answers it from {@code GeboConfig}'s
 * {@code modulesConfig} plus the standard/community defaults. In the monolith that is
 * correct - every module lives in the one JVM, so "configured" and "running" are the
 * same statement. In a cluster they are not: a module is real only when some
 * microservice hosts it <em>and</em> that microservice is up.
 * </p>
 *
 * <p>
 * So enablement here is the conjunction of the two authoritative sources:
 * </p>
 * <ul>
 * <li>{@link GeboMicroservicesTopology#messagingModuleToMicroservice()} - who hosts
 * which {@code messagingModuleId} (the static shape of the deployment);</li>
 * <li>{@link DiscoveryClient} - which of those services currently has a registered
 * instance (the live membership).</li>
 * </ul>
 *
 * <p>
 * A module absent from the topology is reported disabled even if configuration enables
 * it: nothing in this cluster serves it, and telling the setup wizards otherwise sends
 * the user to a screen whose backend does not exist. A module present in the topology
 * whose service is down is likewise disabled rather than hidden - it is a member that
 * happens to be unavailable, and the distinction matters to whoever is reading the
 * screen.
 * </p>
 *
 * <p>
 * Registered {@link Primary} so it wins injection over the static holder wherever both
 * are on the classpath; {@code GeboModulesConfigController} is consumed unchanged from
 * {@code gebo.config.services}. The static holder is still constructed and is delegated
 * to for the module CATALOGUE (ids, integration addresses, the standard-module set) -
 * only the enablement verdict is recomputed.
 * </p>
 */
@Service
@Primary
public class GeboMicroservicesModulesConfigurationHolder implements IGeboModulesConfigurationHolder {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GeboMicroservicesModulesConfigurationHolder.class);

	/**
	 * Injected by its concrete type on purpose: injecting the interface here would be
	 * ambiguous (this class is itself an implementation) and, being {@link Primary},
	 * self-referential.
	 */
	private final GeboModulesConfigurationHolderImpl staticHolder;
	private final GeboMicroservicesTopology topology;
	private final DiscoveryClient discoveryClient;

	public GeboMicroservicesModulesConfigurationHolder(GeboModulesConfigurationHolderImpl staticHolder,
			GeboMicroservicesTopology topology, DiscoveryClient discoveryClient) {
		this.staticHolder = staticHolder;
		this.topology = topology;
		this.discoveryClient = discoveryClient;
		LOGGER.info("Cluster-aware modules configuration active: module enablement is resolved from the"
				+ " topology ({} messaging module(s) declared) and live service discovery",
				topology.messagingModuleToMicroservice().size());
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The returned map is a fresh copy: {@link GeboModulesConfigurationHolderImpl}
	 * hands out its LIVE map and the {@link GeboModuleInfo} values inside it, so
	 * flipping {@code enabled} in place would corrupt the static holder for every other
	 * reader. Each entry is copied before its verdict is set.
	 * </p>
	 */
	@Override
	public Map<String, GeboModuleInfo> getFullConfiguration() {
		final Map<String, GeboModuleInfo> resolved = new LinkedHashMap<String, GeboModuleInfo>();
		// Start from the catalogue the static holder knows, with every module disabled:
		// in a cluster nothing is enabled until the topology says someone serves it.
		final Map<String, GeboModuleInfo> catalogue = staticHolder.getFullConfiguration();
		if (catalogue != null) {
			catalogue.forEach((moduleId, info) -> resolved.put(moduleId, copyDisabled(moduleId, info)));
		}
		// Then enable exactly the topology members whose service is currently registered.
		topology.messagingModuleToMicroservice().forEach((messagingModuleId, microserviceId) -> {
			final GeboModuleInfo info = resolved.computeIfAbsent(messagingModuleId,
					id -> copyDisabled(id, null));
			final boolean active = isActive(microserviceId);
			info.setEnabled(active);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Module {} hosted by {} -> {}", messagingModuleId, microserviceId,
						active ? "ENABLED (service registered)" : "disabled (no registered instance)");
			}
		});
		return resolved;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * Resolved through {@link #getFullConfiguration()} rather than from the static
	 * holder, so a single module is judged by exactly the same cluster rule as the
	 * whole set. Returns {@code null} for an unknown id, matching the static holder's
	 * contract.
	 * </p>
	 */
	@Override
	public GeboModuleInfo getModuleConfig(String messagingModuleId) {
		if (messagingModuleId == null) {
			return null;
		}
		return getFullConfiguration().get(messagingModuleId);
	}

	/**
	 * A copy of one catalogue entry, disabled. Never returns the argument instance -
	 * see {@link #getFullConfiguration()} on why sharing it would be a bug.
	 *
	 * @param moduleId the messaging module id the entry is keyed by
	 * @param source   the static holder's entry, or {@code null} for a module the
	 *                 topology declares but the static catalogue does not know
	 * @return a fresh, disabled {@link GeboModuleInfo}
	 */
	private GeboModuleInfo copyDisabled(String moduleId, GeboModuleInfo source) {
		final GeboModuleInfo copy = new GeboModuleInfo();
		copy.setMessagingModuleId(source != null && source.getMessagingModuleId() != null
				? source.getMessagingModuleId()
				: moduleId);
		if (source != null && source.getModuleAddress() != null) {
			copy.setModuleAddress(source.getModuleAddress());
		}
		copy.setEnabled(false);
		return copy;
	}

	/**
	 * Whether a topology member currently has at least one instance registered in
	 * service discovery.
	 *
	 * <p>
	 * The topology keys members by the canonical underscore id
	 * ({@code brain_gebo_ai}); discovery registers them under the DNS-safe hyphenated
	 * id ({@code brain-gebo-ai}), which is what
	 * {@link GeboMicroservice#toDiscoveryServiceId(String)} converts to. Querying
	 * discovery with the raw topology id finds nothing at all - silently reporting the
	 * whole cluster as down.
	 * </p>
	 *
	 * @param microserviceId the canonical topology id of the hosting service
	 * @return {@code true} when discovery knows at least one instance of it
	 */
	private boolean isActive(String microserviceId) {
		if (microserviceId == null || microserviceId.isBlank()) {
			return false;
		}
		try {
			final String discoveryServiceId = GeboMicroservice.toDiscoveryServiceId(microserviceId);
			return !discoveryClient.getInstances(discoveryServiceId).isEmpty();
		} catch (Throwable failure) {
			// Discovery being unreachable is not a reason to claim a module is enabled:
			// fail closed, and say why once rather than per module.
			LOGGER.warn("Service discovery could not be queried for microservice {}, reporting its modules"
					+ " as disabled", microserviceId, failure);
			return false;
		}
	}
}
