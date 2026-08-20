/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.core.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.application.messaging.GeboCurrentApplication;
import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.ComponentMetaInfo;
import ai.gebo.application.messaging.model.GDataFlowReport;
import ai.gebo.application.messaging.model.GModuleMetaInfo;
import lombok.AllArgsConstructor;

/**
 * Admin endpoint exposing this node's <b>data-flow</b> configuration: for every
 * locally hosted messaging component that reports one, where it reads data
 * from, where it writes it to, and what it transforms on the way.
 *
 * <p>
 * Built for the GDPR / NIS2 administrator audit view - which sources feed the
 * installation, which stores retain the result, and which engines and third
 * parties see the content in between. It reports what is actually <em>running</em>:
 * the values come from live beans through
 * {@link IGMessageBroker#getSystemsInfo()}, not from the declared
 * {@code GeboStandardMicroservices} topology, so a component wired differently
 * from its declaration shows up as it really is.
 * </p>
 *
 * <p>
 * <b>Why this lives in {@code gebo.core}.</b> The screen has to work on both
 * deployments, and the existing {@code InternalMessagingTopologyController} is
 * shipped only by {@code gebo.microservices.starter} - it is absent from the
 * monolith, whose {@code gebo.apps.monolithic.starter} pulls no
 * {@code gebo.microservices.*} module. {@code gebo.core} is on both: directly in
 * the monolithic starter, and in every microservice through
 * {@code gebo.ragsystem.starter} / {@code gebo.contentsystems.starter}. On the
 * monolith this single endpoint is the whole answer, because every component
 * lives in one broker in one JVM; under microservices each node answers for
 * itself and the reports are aggregated across the cluster.
 * </p>
 *
 * <p>
 * Secured for {@code ADMIN} only - deliberately stricter than the
 * {@code ADMIN,APPLICATION} of the topology controllers. This response is a
 * complete map of every data store and credential-guarded endpoint in the
 * installation, so it is reconnaissance-grade and is not something a service
 * identity needs. Endpoint locators are credential-free by construction
 * ({@code DataEndpointLocator}, applied in
 * {@code DataEndpoint.setEndpoint(...)}), and a credential is referenced only by
 * the code of its secret, never by value.
 * </p>
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/DataFlowMetaInfoController")
@AllArgsConstructor
public class DataFlowMetaInfoController {

	private final IGMessageBroker messageBroker;

	/**
	 * This node's data-flow configuration: the locally hosted modules, each pruned
	 * to the components that actually report a flow.
	 *
	 * <p>
	 * Two filters are applied. Components whose {@code getDataFlowMetaInfos()}
	 * returns null are dropped, which is most of them - the orchestration plumbing
	 * and the pure routing components have no data-flow configuration of their own.
	 * Components with {@code localSystem == false} are dropped too: those are the
	 * RabbitMQ bridge proxies, which hard-report false
	 * ({@code GAbstractExternalMessageEmitter} / {@code GAbstractExternalMessageReceiver})
	 * and stand for another node's component, not this one's - reporting them here
	 * would double-count every remote endpoint once per node that can reach it.
	 * </p>
	 */
	@GetMapping(value = "getLocalDataFlow", produces = MediaType.APPLICATION_JSON_VALUE)
	public GDataFlowReport getLocalDataFlow() {
		List<GModuleMetaInfo> out = new ArrayList<>();
		List<GModuleMetaInfo> systems = messageBroker.getSystemsInfo();
		if (systems != null) {
			for (GModuleMetaInfo module : systems) {
				if (module == null || module.getComponents() == null) {
					continue;
				}
				List<ComponentMetaInfo> reporting = module.getComponents().stream()
						.filter(c -> c != null && c.isLocalSystem() && c.getDataFlowMetaInfos() != null).toList();
				if (!reporting.isEmpty()) {
					out.add(new GModuleMetaInfo(module.getMessagingModuleId(), new ArrayList<>(reporting)));
				}
			}
		}
		return new GDataFlowReport(nodeId(), new Date(), out);
	}

	private String nodeId() {
		GeboCurrentApplication application = messageBroker.getCurrentApplication();
		return application != null ? application.getId() : null;
	}
}
