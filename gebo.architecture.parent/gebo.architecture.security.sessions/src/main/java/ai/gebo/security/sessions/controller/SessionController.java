/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.sessions.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.SecurityHeaderUtil;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import ai.gebo.security.sessions.model.GUserSession;
import ai.gebo.security.sessions.services.IGUserSessionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

/**
 * The user's own session: the logout revokes it, so the token the browser
 * forgets cannot be used any more by whoever still holds it.
 */
@RestController
@RequestMapping("api/users/SessionController")
@AllArgsConstructor
public class SessionController {

	private final IGUserSessionService sessions;
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

	/**
	 * Revokes the session of the token this request authenticated with.
	 *
	 * @return whether a session was found and revoked
	 */
	@PostMapping(value = "logout", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean logout(HttpServletRequest request) {
		SecurityHeaderData header = SecurityHeaderUtil.getSecurityHeaderData(request);
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		event.setEventType(SecurityAuditTaxonomy.EventType.SESSION_MANAGEMENT);
		event.setCategory(SecurityAuditTaxonomy.Category.SESSION_MANAGEMENT);
		event.setAction(SecurityAuditTaxonomy.Action.SESSION_LOGOUT);
		try {
			GUserSession revoked = header != null && !header.isEmpty()
					? sessions.revokeOfToken(header.getToken(), request.getUserPrincipal() != null
							? request.getUserPrincipal().getName()
							: null)
					: null;
			event.setOutcome(SecurityAuditTaxonomy.Outcome.SUCCESS);
			if (revoked != null) {
				event.setResourceId(revoked.getCode());
			}
			return revoked != null;
		} catch (RuntimeException e) {
			event.setOutcome(SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		} finally {
			securityAuditLoggerService.log(event);
		}
	}
}
