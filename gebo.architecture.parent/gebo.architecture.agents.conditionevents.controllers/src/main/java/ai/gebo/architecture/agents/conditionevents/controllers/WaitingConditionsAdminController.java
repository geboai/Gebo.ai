/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.agents.conditionevents.model.WaitingBlockView;
import ai.gebo.architecture.agents.conditionevents.model.WaitingConditionsActionAck;
import ai.gebo.architecture.agents.conditionevents.services.IWaitingConditionsService;

/**
 * The waiting conditions panel of the administrators: every suspended run
 * waiting for a human in the loop or an external condition, whoever owns its
 * conversation, and the same actions on any of them. The respawn still runs as
 * the conversation's owner; the agent is told the administrator chose.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/WaitingConditionsAdminController")
public class WaitingConditionsAdminController {
	private final IWaitingConditionsService service;

	public WaitingConditionsAdminController(IWaitingConditionsService service) {
		this.service = service;
	}

	/** Every suspended run still waiting, the latest first. */
	@GetMapping(value = "allWaitingConditions", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<WaitingBlockView> allWaitingConditions() {
		return service.allWaiting();
	}

	/** Goes on without the named conditions of any block, or without every one still waiting. */
	@PostMapping(value = "goOnWithout", produces = MediaType.APPLICATION_JSON_VALUE)
	public WaitingConditionsActionAck goOnWithout(@RequestParam String blockCode,
			@RequestBody(required = false) List<String> conditionIds) {
		return service.goOnWithout(blockCode, conditionIds, WaitingConditionsController.username(), true);
	}

	/** Drops any block, never to be respawned: its conversation is free. */
	@PostMapping(value = "abandon", produces = MediaType.APPLICATION_JSON_VALUE)
	public WaitingConditionsActionAck abandon(@RequestParam String blockCode) {
		return service.abandon(blockCode, WaitingConditionsController.username(), true);
	}
}
