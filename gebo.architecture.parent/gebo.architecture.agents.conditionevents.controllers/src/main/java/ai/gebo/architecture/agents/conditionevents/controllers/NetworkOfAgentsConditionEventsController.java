/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.controllers;

import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.agents.conditionevents.ConditionCorrelationId;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEvent;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventAck;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventCaller;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventView;
import ai.gebo.architecture.agents.conditionevents.services.IConditionEventsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * The REST entry of the events solving the conditions suspended networks of
 * agents wait for: any system holding a user's or an application account's
 * credentials delivers here, naming the condition by its correlation id
 * ({@code <sessionCode>:<conditionId>}) or by the session code and the
 * condition id.
 */
@RestController
@RequestMapping("api/users/NetworkOfAgentsConditionEventsController")
public class NetworkOfAgentsConditionEventsController {
	private final IConditionEventsService service;

	public NetworkOfAgentsConditionEventsController(IConditionEventsService service) {
		this.service = service;
	}

	/** Delivers an event: the answer says at once whether it is kept, and how. */
	@PostMapping(value = "deliverConditionEvent", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ConditionEventAck deliverConditionEvent(@RequestBody @NotNull @Valid ConditionEvent event) {
		return service.deliver(event, caller());
	}

	/** What became of the event kept for a condition; nothing when none is, or the caller may not know. */
	@GetMapping(value = "conditionEventStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	public ConditionEventView conditionEventStatus(@RequestParam(required = false) String correlationId,
			@RequestParam(required = false) String sessionCode, @RequestParam(required = false) String conditionId,
			@RequestParam(required = false) String externalSystem,
			@RequestParam(required = false) String externalSessionKey) {
		final ConditionCorrelationId correlation;
		try {
			// the session named by another system's own key, when neither of Gebo's names is given
			final String session = (correlationId == null || correlationId.isBlank())
					&& (sessionCode == null || sessionCode.isBlank())
							? service.sessionCodeOf(externalSystem, externalSessionKey).orElse(null)
							: sessionCode;
			correlation = ConditionCorrelationId.of(correlationId, session, conditionId);
		} catch (IllegalArgumentException notNamed) {
			return null;
		}
		return service.status(correlation, caller()).orElse(null);
	}

	private static ConditionEventCaller caller() {
		return ConditionEventCaller.authenticated(SecurityContextHolder.getContext().getAuthentication(),
				ConditionEventCaller.REST);
	}
}
