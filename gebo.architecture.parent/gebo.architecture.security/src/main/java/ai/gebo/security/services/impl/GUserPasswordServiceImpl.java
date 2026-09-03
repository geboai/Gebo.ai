/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.model.SecretInfo;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.IGUserPasswordService;
import ai.gebo.security.services.SecurityAuditTaxonomy;

/**
 * The secret-store implementation of {@link IGUserPasswordService} - the only one.
 *
 * <p>
 * A password is a {@code USERNAME_PASSWORD} secret filed under the context code
 * {@code "user:<username>"}, and that context code is how it is found again: the
 * secret's own generated id is never recorded anywhere else, so nothing has to be
 * kept in sync with the user document. That is deliberate - the user document is
 * exactly what this change took the credential out of.
 * </p>
 *
 * <p>
 * A context code holds at most one password secret. Should a store somehow end up
 * with several (two racing first-time writes on separate nodes), the first is used
 * and the situation is logged rather than guessed at: both would have been written
 * by a legitimate {@code storePassword}, so neither is "wrong", but silently
 * alternating between them would look like a password that intermittently stops
 * working.
 * </p>
 *
 * <h2>Auditing</h2>
 * <p>
 * Every credential write and removal raises a {@code userAdministration} security
 * event on the Wazuh-ingested "security-log" appender
 * ({@link IGSecurityAuditLoggerService}). This is the last tier before the store, and
 * the only one every path passes through: the admin UI, a user changing their own
 * password, a redeemed reset ticket, user creation, user deletion, the installation
 * bootstrap and OAuth2 auto-provisioning all end up here, and several of those have
 * no instrumentation of their own. The higher tiers record <i>who wanted</i> the
 * change ({@code passwordChangeSelf} / {@code passwordChangeAdmin} /
 * {@code passwordResetTicket}); these record that a stored credential actually moved,
 * and correlate to them by {@code correlationId}.
 * </p>
 *
 * <p>
 * The underlying {@code IGeboSecretsAccessService} raises its own
 * {@code secretManagement} events for the same writes. They are not redundant: a
 * {@code secretCreate} says a secret appeared, but only the events raised here say
 * that the secret <b>is a login credential, and whose</b> - the secret's own event
 * carries an opaque generated id, not a username.
 * </p>
 *
 * <p>
 * A password never reaches an event - not in {@code details}, not in a message. The
 * failure branches log the exception's message, which comes from the secret store and
 * never contains the content it was asked to store.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Service
public class GUserPasswordServiceImpl implements IGUserPasswordService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GUserPasswordServiceImpl.class);

	/** {@code resourceType} of every event raised here: a user's login credential. */
	private static final String RESOURCE_TYPE_USER_PASSWORD = "userPassword";

	private final IGeboSecretsAccessService secretsAccessService;
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

	public GUserPasswordServiceImpl(IGeboSecretsAccessService secretsAccessService,
			IGSecurityAuditLoggerService securityAuditLoggerService) {
		this.secretsAccessService = secretsAccessService;
		this.securityAuditLoggerService = securityAuditLoggerService;
	}

	/**
	 * Fills in and emits an audit event.
	 *
	 * <p>
	 * Takes an already-created {@link SecurityEvent} rather than calling
	 * {@code newSecurityEvent()} itself, so that the caller-stack it captures points at
	 * the real operation ({@code storePassword}, {@code deletePassword}, ...) instead of
	 * at this helper.
	 * </p>
	 *
	 * <p>
	 * The username is the {@code resourceId}; the password is nowhere.
	 * </p>
	 */
	private void logPasswordEvent(SecurityEvent event, String action, String username, String outcome) {
		event.setEventType(SecurityAuditTaxonomy.EventType.USER_ADMINISTRATION);
		event.setCategory(SecurityAuditTaxonomy.Category.USER_ADMINISTRATION);
		event.setAction(action);
		event.setResourceType(RESOURCE_TYPE_USER_PASSWORD);
		event.setResourceId(username);
		event.setOutcome(outcome);
		securityAuditLoggerService.log(event);
	}

	@Override
	public void storePassword(String username, String rawPassword) throws GeboCryptSecretException {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		try {
			requireUsername(username);
			if (rawPassword == null)
				throw new IllegalArgumentException("A null password cannot be stored for user " + username);
			GeboUsernamePasswordContent content = new GeboUsernamePasswordContent();
			content.setUsername(username);
			content.setPassword(rawPassword);

			String contextCode = IGUserPasswordService.contextCodeOf(username);
			SecretInfo existing = findSecretInfo(username);
			// "A credential was created for an account that had none" and "an existing
			// credential was replaced" are different things to a SIEM: the second, on an
			// account the actor does not own, is the shape of an account takeover.
			event.getDetails().put("replacedExisting", existing != null);
			if (existing == null) {
				secretsAccessService.storeSecret(content, descriptionOf(username), contextCode);
			} else {
				// Same code, so every reference that already resolved this secret keeps
				// resolving it - a password change must not mint a second secret and orphan
				// the first, which would then sit in the store holding a live old password.
				secretsAccessService.updateSecret(content, descriptionOf(username), contextCode, existing.getCode());
			}
			logPasswordEvent(event, SecurityAuditTaxonomy.Action.PASSWORD_SECRET_STORE, username,
					SecurityAuditTaxonomy.Outcome.SUCCESS);
		} catch (GeboCryptSecretException | RuntimeException e) {
			event.getDetails().put("error", e.getMessage());
			logPasswordEvent(event, SecurityAuditTaxonomy.Action.PASSWORD_SECRET_STORE, username,
					SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	@Override
	public String findRawPassword(String username) throws GeboCryptSecretException {
		SecretInfo info = findSecretInfo(username);
		if (info == null)
			return null;
		AbstractGeboSecretContent content = secretsAccessService.getSecretContentById(info.getCode());
		if (!(content instanceof GeboUsernamePasswordContent))
			throw new GeboCryptSecretException("The password secret of user " + username + " (code=" + info.getCode()
					+ ") is not a USERNAME_PASSWORD content");
		return ((GeboUsernamePasswordContent) content).getPassword();
	}

	@Override
	public boolean matches(String username, String rawPassword) {
		if (rawPassword == null)
			return false;
		String stored;
		try {
			stored = findRawPassword(username);
		} catch (GeboCryptSecretException | RuntimeException e) {
			// A store that cannot answer is a failed authentication, not a 500: the caller
			// is a login or a "confirm your password" check. Logged at warn because it is
			// an infrastructure fault masquerading as a wrong password.
			LOGGER.warn("Cannot read the password secret of user {} - treating the check as a mismatch", username, e);
			// ... and audited for exactly the same reason: to the caller, and therefore to
			// the authentication events it goes on to raise, this is indistinguishable from
			// a wrong password. Without this event, a secret store that has gone unreadable
			// looks in the security log like a burst of failed logins. A successful or
			// genuinely-wrong check is NOT audited here: those are the authentication
			// events' job, and this runs on every login.
			SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
			event.getDetails().put("error", e.getMessage());
			event.getDetails().put("reportedAs", "mismatch");
			logPasswordEvent(event, SecurityAuditTaxonomy.Action.PASSWORD_SECRET_READ, username,
					SecurityAuditTaxonomy.Outcome.FAILURE);
			return false;
		}
		if (stored == null)
			return false;
		return MessageDigest.isEqual(stored.getBytes(StandardCharsets.UTF_8),
				rawPassword.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public boolean hasPassword(String username) {
		try {
			return findSecretInfo(username) != null;
		} catch (GeboCryptSecretException | RuntimeException e) {
			LOGGER.warn("Cannot look up the password secret of user {}", username, e);
			// Same reasoning as matches(): an unreadable store is reported to the caller as
			// "this account has no password", which is a security-relevant answer to give
			// wrongly.
			SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
			event.getDetails().put("error", e.getMessage());
			event.getDetails().put("reportedAs", "noPassword");
			logPasswordEvent(event, SecurityAuditTaxonomy.Action.PASSWORD_SECRET_READ, username,
					SecurityAuditTaxonomy.Outcome.FAILURE);
			return false;
		}
	}

	@Override
	public void deletePassword(String username) throws GeboCryptSecretException {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		int deleted = 0;
		try {
			requireUsername(username);
			// Every secret in the context, not just the first: this is the one operation
			// that must leave nothing behind, and a duplicate left here would keep a deleted
			// user's password alive in the store.
			for (SecretInfo info : passwordSecretsOf(username)) {
				secretsAccessService.deleteSecret(info.getCode());
				deleted++;
			}
			// Zero is the documented no-op (a federated account, or a second delete), and
			// more than one means the duplicate-secret condition findSecretInfo() warns
			// about was real - both worth being able to see in the log.
			event.getDetails().put("deletedSecrets", deleted);
			logPasswordEvent(event, SecurityAuditTaxonomy.Action.PASSWORD_SECRET_DELETE, username,
					SecurityAuditTaxonomy.Outcome.SUCCESS);
		} catch (GeboCryptSecretException | RuntimeException e) {
			// The count so far: a failure part-way through leaves the account with some of
			// its credentials still live, which is the case that needs investigating.
			event.getDetails().put("deletedSecrets", deleted);
			event.getDetails().put("error", e.getMessage());
			logPasswordEvent(event, SecurityAuditTaxonomy.Action.PASSWORD_SECRET_DELETE, username,
					SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	private SecretInfo findSecretInfo(String username) throws GeboCryptSecretException {
		List<SecretInfo> found = passwordSecretsOf(username);
		if (found.isEmpty())
			return null;
		if (found.size() > 1) {
			LOGGER.error("User {} has {} password secrets under context '{}'; using '{}'. This should not happen - "
					+ "the extra ones must be removed by hand.", username, found.size(),
					IGUserPasswordService.contextCodeOf(username), found.get(0).getCode());
		}
		return found.get(0);
	}

	private List<SecretInfo> passwordSecretsOf(String username) throws GeboCryptSecretException {
		requireUsername(username);
		List<SecretInfo> infos = secretsAccessService
				.getSecretInfoByContextCode(IGUserPasswordService.contextCodeOf(username));
		if (infos == null)
			return List.of();
		// Filtered by type rather than trusted wholesale: the "user:<username>" context
		// is a naming convention shared with the ACL layer, so nothing stops some future
		// feature from filing a secret of its own against a user.
		return infos.stream().filter(i -> i.getSecretType() == GeboSecretType.USERNAME_PASSWORD).toList();
	}

	private String descriptionOf(String username) {
		return "Login password of user " + username;
	}

	private void requireUsername(String username) {
		if (username == null || username.trim().isEmpty())
			throw new IllegalArgumentException("A user password secret needs a username");
	}
}
