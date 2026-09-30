/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.PipelineType;
import ai.gebo.architecture.agents.services.IGAgenticChatDefaultNetworkOfAgentsService;
import ai.gebo.architecture.agents.services.IGAgenticChatDefaultNetworkOfAgentsService.AgenticChatDefaultNetworkInfo;
import ai.gebo.model.OperationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The chat networks of agents an administrator can choose: the ones choosable for a
 * pipeline type, and the system default of each pipeline type.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(path = "api/admin/AgenticChatDefaultNetworksAdminController")
@AllArgsConstructor
public class AgenticChatDefaultNetworksAdminController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AgenticChatDefaultNetworksAdminController.class);
	private final IGAgenticChatDefaultNetworkOfAgentsService defaultsService;

	@Data
	public static class AgenticChatDefaultNetworkRequest {
		@NotNull
		private PipelineType pipelineType = null;
		private String defaultChatNetworkOfAgents = null;
	}

	@GetMapping(value = "getChoosableChatNetworksOfAgents", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GAgentsNetwork> getChoosableChatNetworksOfAgents(@RequestParam("pipelineType") PipelineType pipelineType) {
		// The whole network: the choice shows its description and suggested purpose.
		List<GAgentsNetwork> networks = defaultsService.getChoosableNetworksOfAgents(pipelineType);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST getChoosableChatNetworksOfAgents(" + pipelineType + ") returned " + networks.size()
					+ " network(s)");
		}
		return networks;
	}

	@GetMapping(value = "getAgenticChatDefaultNetworks", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AgenticChatDefaultNetworkInfo> getAgenticChatDefaultNetworks() {
		return defaultsService.getAgenticChatDefaultNetworks();
	}

	@PostMapping(value = "setAgenticChatDefaultNetwork", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<AgenticChatDefaultNetworkInfo> setAgenticChatDefaultNetwork(
			@RequestBody @NotNull AgenticChatDefaultNetworkRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST setAgenticChatDefaultNetwork(" + request.getPipelineType() + ", "
					+ request.getDefaultChatNetworkOfAgents() + ")");
		}
		try {
			return OperationStatus.of(defaultsService.setAgenticChatDefaultNetwork(request.getPipelineType(),
					request.getDefaultChatNetworkOfAgents()));
		} catch (IllegalArgumentException e) {
			return OperationStatus.ofError("Chat network of agents", e.getMessage());
		}
	}

	@PostMapping(value = "resetAgenticChatDefaultNetwork", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<AgenticChatDefaultNetworkInfo> resetAgenticChatDefaultNetwork(
			@RequestBody @NotNull AgenticChatDefaultNetworkRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST resetAgenticChatDefaultNetwork(" + request.getPipelineType() + ")");
		}
		try {
			return OperationStatus.of(defaultsService.resetAgenticChatDefaultNetwork(request.getPipelineType()));
		} catch (IllegalArgumentException e) {
			return OperationStatus.ofError("Chat network of agents", e.getMessage());
		}
	}
}
