/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.internaltopology.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.ComponentMetaInfo;
import ai.gebo.application.messaging.model.GModuleMetaInfo;

/**
 * Admin endpoint exposing this microservice's <b>local</b> messaging topology.
 *
 * <p>
 * It calls {@link IGMessageBroker#getSystemsInfo()} and returns only the modules
 * that have at least one {@code localSystem == true} component, each pruned to just
 * those local components. That drops the external, rabbitmq-bound components (the
 * {@code IGExternalInterface} emitters/receivers hard-report {@code localSystem ==
 * false}) so callers see only what this node actually hosts in-process.
 * </p>
 *
 * <p>
 * A {@code @RestController} picked up by every backend's {@code @ComponentScan("ai.gebo")}
 * — which is exactly what we want, since this endpoint belongs on every real
 * backend (gateway/eureka never see this jar, added only via
 * {@code gebo.microservices.starter}). Secured for {@code ADMIN} (human/admin
 * access) and {@code APPLICATION} (the internal system-identity poller from the
 * global topology coordinator).
 * </p>
 */
@RestController
@RequestMapping("api/admin/InternalMessagingTopologyController")
@PreAuthorize("hasAnyRole('ADMIN','APPLICATION')")
public class InternalMessagingTopologyController {

	private final IGMessageBroker messageBroker;

	public InternalMessagingTopologyController(IGMessageBroker messageBroker) {
		this.messageBroker = messageBroker;
	}

	/**
	 * The local-only modules of this microservice's broker: modules with at least
	 * one local component, each keeping only its {@code localSystem == true}
	 * components.
	 */
	@GetMapping(value = "getLocalTopology", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GModuleMetaInfo> getLocalTopology() {
		List<GModuleMetaInfo> out = new ArrayList<>();
		List<GModuleMetaInfo> systems = messageBroker.getSystemsInfo();
		if (systems == null) {
			return out;
		}
		for (GModuleMetaInfo module : systems) {
			if (module == null || module.getComponents() == null) {
				continue;
			}
			List<ComponentMetaInfo> localComponents = module.getComponents().stream()
					.filter(c -> c != null && c.isLocalSystem()).toList();
			if (!localComponents.isEmpty()) {
				out.add(new GModuleMetaInfo(module.getMessagingModuleId(), new ArrayList<>(localComponents)));
			}
		}
		return out;
	}
}
