/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.system.messages.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.model.OperationStatus;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.system.messages.model.GSystemMessage;
import ai.gebo.system.messages.services.IGSystemMessagesService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**
 * The system messages of the logged-in user: administrators receive the messages
 * routed to administrators and to everybody, the other users those routed to
 * users and to everybody.
 */
@RestController
@PreAuthorize("hasAnyRole('USER','ADMIN')")
@RequestMapping("/api/users/SystemMessagesController")
public class SystemMessagesController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private final IGSystemMessagesService systemMessagesService;
	private final IGSecurityService securityService;

	public SystemMessagesController(IGSystemMessagesService systemMessagesService,
			IGSecurityService securityService) {
		this.systemMessagesService = systemMessagesService;
		this.securityService = securityService;
	}

	/**
	 * A request to hide a system message.
	 */
	public static class DismissSystemMessageRequest {
		@NotBlank
		private String messageId = null;

		public String getMessageId() {
			return messageId;
		}

		public void setMessageId(String messageId) {
			this.messageId = messageId;
		}
	}

	/**
	 * @return the messages to show to the logged-in user, most severe first
	 */
	@GetMapping(value = "getMySystemMessages", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GSystemMessage> getMySystemMessages() {
		String username = securityService.getCurrentUser().getUsername();
		boolean administrator = securityService.isCurrentUserAdmin();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getMySystemMessages() for " + username + " administrator=" + administrator);
		}
		return systemMessagesService.findVisibleFor(username, administrator);
	}

	/**
	 * Hides a message for the logged-in user, until its content changes.
	 *
	 * @param request the message to hide
	 * @return true when hidden; false when the message does not exist, is not
	 *         routed to the user or cannot be dismissed
	 */
	@PostMapping(value = "dismissSystemMessage", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> dismissSystemMessage(@Valid @RequestBody DismissSystemMessageRequest request) {
		String username = securityService.getCurrentUser().getUsername();
		boolean dismissed = systemMessagesService.dismiss(username, securityService.isCurrentUserAdmin(),
				request.getMessageId());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("dismissSystemMessage(" + request.getMessageId() + ") by " + username + " -> " + dismissed);
		}
		return OperationStatus.of(dismissed);
	}
}
