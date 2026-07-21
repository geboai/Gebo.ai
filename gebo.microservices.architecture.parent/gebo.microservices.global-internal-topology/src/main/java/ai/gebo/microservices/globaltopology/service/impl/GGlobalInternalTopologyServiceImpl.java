/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.globaltopology.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import ai.gebo.application.messaging.model.ComponentMetaInfo;
import ai.gebo.application.messaging.model.GModuleMetaInfo;
import ai.gebo.application.messaging.model.MicroserviceMetaInfo;
import ai.gebo.microservices.globaltopology.client.InternalTopologyPollClient;
import ai.gebo.microservices.globaltopology.service.IGGlobalInternalTopologyService;
import ai.gebo.microservices.topology.GeboMicroservice;
import ai.gebo.microservices.topology.GeboMicroservicesTopology;

/**
 * Polls every declared microservice's {@code InternalMessagingTopologyController}
 * on a schedule and on demand, and caches the network-wide topology when the poll
 * is complete. See {@link IGGlobalInternalTopologyService}.
 */
public class GGlobalInternalTopologyServiceImpl implements IGGlobalInternalTopologyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GGlobalInternalTopologyServiceImpl.class);

	private final GeboMicroservicesTopology topology;
	private final InternalTopologyPollClient pollClient;

	private volatile List<MicroserviceMetaInfo> cachedTopology = List.of();

	public GGlobalInternalTopologyServiceImpl(GeboMicroservicesTopology topology,
			InternalTopologyPollClient pollClient) {
		this.topology = topology;
		this.pollClient = pollClient;
	}

	@Override
	public List<MicroserviceMetaInfo> getGlobalTopology() {
		return cachedTopology;
	}

	/**
	 * Scheduled full poll. Initial delay lets the cluster finish coming up before the
	 * first attempt; fixed delay spaces subsequent runs after the previous completes.
	 */
	@Scheduled(initialDelayString = "${gebo.microservices.global-internal-topology.initial-delay-ms:45000}", fixedDelayString = "${gebo.microservices.global-internal-topology.poll-interval-ms:120000}")
	public void scheduledRefresh() {
		refresh();
	}

	@Override
	public synchronized boolean refresh() {
		List<GeboMicroservice> declared = topology.microservices();
		List<MicroserviceMetaInfo> collected = new ArrayList<>();
		boolean allUp = true;

		for (GeboMicroservice service : declared) {
			String microserviceId = service.getApplicationName();
			try {
				List<GModuleMetaInfo> modules = pollClient.getLocalTopology(microserviceId);
				collected.add(new MicroserviceMetaInfo(microserviceId,
						modules != null ? modules : new ArrayList<>()));
			} catch (RuntimeException ex) {
				allUp = false;
				LOGGER.error("Internal-topology poll: declared microservice '{}' is down or unreachable: {}",
						microserviceId, ex.toString());
			}
		}

		if (!allUp) {
			LOGGER.error("Internal-topology poll incomplete: at least one declared microservice is down; "
					+ "keeping the previous cached global topology ({} microservices).", cachedTopology.size());
			return false;
		}

		reportDuplicateModuleIds(collected);
		this.cachedTopology = List.copyOf(collected);
		LOGGER.info("Internal-topology refreshed: {} microservices, {} local modules total.{}", collected.size(),
				collected.stream().mapToInt(m -> m.getModules().size()).sum(), renderTopology(collected));
		return true;
	}

	/**
	 * A human-readable, indented representation of the whole network topology
	 * ({@code microservice -> module -> component[receiver/emitter]}) for the INFO
	 * log emitted on each successful sampling.
	 */
	private String renderTopology(List<MicroserviceMetaInfo> topology) {
		StringBuilder sb = new StringBuilder();
		sb.append("\nGlobal internal messaging topology:");
		for (MicroserviceMetaInfo microservice : topology) {
			sb.append("\n  ").append(microservice.getMicroserviceId());
			if (microservice.getModules().isEmpty()) {
				sb.append("  (no local modules)");
			}
			for (GModuleMetaInfo module : microservice.getModules()) {
				sb.append("\n    ").append(module.getMessagingModuleId());
				List<ComponentMetaInfo> components = module.getComponents();
				if (components == null || components.isEmpty()) {
					sb.append("  (no local components)");
					continue;
				}
				for (ComponentMetaInfo component : components) {
					sb.append("\n      - ").append(component.getMessagingSystemId()).append(" [")
							.append(component.isReceiver() ? "R" : "-").append(component.isEmitter() ? "E" : "-")
							.append("]");
				}
			}
		}
		return sb.toString();
	}

	/**
	 * A {@code messagingModuleId} must belong to exactly one microservice; ERROR-log
	 * any that shows up on more than one node in the network.
	 */
	private void reportDuplicateModuleIds(List<MicroserviceMetaInfo> collected) {
		Map<String, String> moduleOwner = new HashMap<>();
		for (MicroserviceMetaInfo microservice : collected) {
			for (GModuleMetaInfo module : microservice.getModules()) {
				String moduleId = module.getMessagingModuleId();
				if (moduleId == null) {
					continue;
				}
				String previousOwner = moduleOwner.putIfAbsent(moduleId, microservice.getMicroserviceId());
				if (previousOwner != null && !previousOwner.equals(microservice.getMicroserviceId())) {
					LOGGER.error("Internal-topology: duplicate messagingModuleId '{}' present on both '{}' and '{}' "
							+ "- each module must belong to exactly one microservice.", moduleId, previousOwner,
							microservice.getMicroserviceId());
				}
			}
		}
	}
}
