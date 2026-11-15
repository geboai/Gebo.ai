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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
 * The waiting conditions panel of a user: the suspended runs of that user's
 * conversations waiting for a human in the loop or an external condition, and
 * what the user may do of them, go on without some conditions or abandon the
 * run. An administrator acts on any run from here too; the whole list is on the
 * administrators' controller.
 */
@RestController
@RequestMapping("api/users/WaitingConditionsController")
public class WaitingConditionsController {
	static final String ADMIN_ROLE = "ROLE_ADMIN";
	private final IWaitingConditionsService service;

	public WaitingConditionsController(IWaitingConditionsService service) {
		this.service = service;
	}

	/** The suspended runs of the user's conversations still waiting, the latest first. */
	@GetMapping(value = "waitingConditions", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<WaitingBlockView> waitingConditions() {
		return service.waitingFor(username());
	}

	/**
	 * Goes on without the named conditions of the block, or without every one still
	 * waiting when none is named, mandatory or not: the run resumes once nothing
	 * mandatory is left, told who chose so.
	 */
	@PostMapping(value = "goOnWithout", produces = MediaType.APPLICATION_JSON_VALUE)
	public WaitingConditionsActionAck goOnWithout(@RequestParam String blockCode,
			@RequestBody(required = false) List<String> conditionIds) {
		return service.goOnWithout(blockCode, conditionIds, username(), isAdministrator());
	}

	/** Drops the block, never to be respawned: the conversation is free. */
	@PostMapping(value = "abandon", produces = MediaType.APPLICATION_JSON_VALUE)
	public WaitingConditionsActionAck abandon(@RequestParam String blockCode) {
		return service.abandon(blockCode, username(), isAdministrator());
	}

	static String username() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null ? authentication.getName() : null;
	}

	static boolean isAdministrator() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication.getAuthorities() == null) {
			return false;
		}
		for (GrantedAuthority authority : authentication.getAuthorities()) {
			if (authority != null && ADMIN_ROLE.equals(authority.getAuthority())) {
				return true;
			}
		}
		return false;
	}
}
