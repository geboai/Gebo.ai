/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.sessions.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.utils.DataPage;
import ai.gebo.security.model.UserInfos;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import ai.gebo.security.sessions.model.GUserSession;
import ai.gebo.security.sessions.services.IGUserSessionService;
import ai.gebo.security.sessions.services.IGUserSessionService.SessionsQuery;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The live sessions, for administrators: who is logged in or calling with a
 * key or an external token, since when and from where, and their revocation.
 * An administrator may revoke any session, another administrator's and their
 * own included.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/SessionsAdminController")
@AllArgsConstructor
public class SessionsAdminController {

	private final IGUserSessionService sessions;
	private final IGSecurityService securityService;
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

	/** The listing request: the page and the filters. */
	@Data
	public static class SessionsPageRequest {
		@NotNull
		private DataPage page = new DataPage();
		private SessionsQuery query = new SessionsQuery();
	}

	@PostMapping(value = "getSessionsPagedList", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Page<GUserSession> getSessionsPagedList(@Valid @RequestBody SessionsPageRequest request) {
		return sessions.list(request.getQuery(), request.getPage().toPageable());
	}

	@GetMapping(value = "getActiveSessionsCount", produces = MediaType.APPLICATION_JSON_VALUE)
	public long getActiveSessionsCount(@RequestParam(value = "withinMinutes", defaultValue = "15") int withinMinutes) {
		return sessions.countActive(withinMinutes);
	}

	/** Revokes one session: its tokens are refused from the next request. */
	@PostMapping(value = "revokeSession", produces = MediaType.APPLICATION_JSON_VALUE)
	public GUserSession revokeSession(@RequestParam("code") String code) {
		SecurityEvent event = newEvent(SecurityAuditTaxonomy.Action.SESSION_REVOKE, code);
		try {
			GUserSession revoked = sessions.revoke(code, currentUsername());
			event.setOutcome(revoked != null ? SecurityAuditTaxonomy.Outcome.SUCCESS
					: SecurityAuditTaxonomy.Outcome.FAILURE);
			if (revoked != null) {
				event.getDetails().put("username", revoked.getUsername());
				event.getDetails().put("kind", String.valueOf(revoked.getKind()));
			}
			return revoked;
		} catch (RuntimeException e) {
			event.setOutcome(SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		} finally {
			securityAuditLoggerService.log(event);
		}
	}

	/** Revokes every live session of the user; how many were revoked. */
	@PostMapping(value = "revokeUserSessions", produces = MediaType.APPLICATION_JSON_VALUE)
	public int revokeUserSessions(@RequestParam("username") String username) {
		SecurityEvent event = newEvent(SecurityAuditTaxonomy.Action.SESSION_REVOKE_ALL_OF_USER, username);
		try {
			int revoked = sessions.revokeAllOf(username, currentUsername());
			event.getDetails().put("revoked", revoked);
			event.setOutcome(SecurityAuditTaxonomy.Outcome.SUCCESS);
			return revoked;
		} catch (RuntimeException e) {
			event.setOutcome(SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		} finally {
			securityAuditLoggerService.log(event);
		}
	}

	private String currentUsername() {
		UserInfos user = securityService.getCurrentUser();
		return user != null ? user.getUsername() : null;
	}

	private SecurityEvent newEvent(String action, String resourceId) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		event.setEventType(SecurityAuditTaxonomy.EventType.SESSION_MANAGEMENT);
		event.setCategory(SecurityAuditTaxonomy.Category.SESSION_MANAGEMENT);
		event.setAction(action);
		event.setResourceId(resourceId);
		return event;
	}
}
