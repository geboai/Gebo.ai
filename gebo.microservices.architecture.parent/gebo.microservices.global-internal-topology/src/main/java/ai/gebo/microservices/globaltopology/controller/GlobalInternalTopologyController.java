/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.globaltopology.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.application.messaging.model.MicroserviceMetaInfo;
import ai.gebo.microservices.globaltopology.service.IGGlobalInternalTopologyService;

/**
 * Admin endpoint exposing the cached network-wide internal messaging topology
 * ({@code MicroserviceMetaInfo -> GModuleMetaInfo -> ComponentMetaInfo}) and an
 * on-demand refresh trigger. Only present on the coordinator (tyr), since this jar
 * lands only there; picked up by tyr's {@code @ComponentScan("ai.gebo")}.
 *
 * <p>
 * Secured for {@code ADMIN} (human/admin access) and {@code APPLICATION} (the
 * system identity used by the {@code global-internal-topology.client}).
 * </p>
 */
@RestController
@RequestMapping("api/admin/GlobalInternalTopologyController")
@PreAuthorize("hasAnyRole('ADMIN','APPLICATION')")
public class GlobalInternalTopologyController {

	private final IGGlobalInternalTopologyService globalTopologyService;

	public GlobalInternalTopologyController(IGGlobalInternalTopologyService globalTopologyService) {
		this.globalTopologyService = globalTopologyService;
	}

	/** The last fully-collected network-wide topology. */
	@GetMapping(value = "getGlobalTopology", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MicroserviceMetaInfo> getGlobalTopology() {
		return globalTopologyService.getGlobalTopology();
	}

	/**
	 * Re-poll every declared microservice now. Returns {@code true} when the poll was
	 * complete and the cache refreshed, {@code false} when a declared microservice
	 * was down.
	 */
	@PostMapping(value = "refresh", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean refresh() {
		return globalTopologyService.refresh();
	}
}
