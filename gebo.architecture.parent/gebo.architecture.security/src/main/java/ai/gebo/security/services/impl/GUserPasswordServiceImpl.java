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
import ai.gebo.security.services.IGUserPasswordService;

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
 * Gebo.ai comment agent
 */
@Service
public class GUserPasswordServiceImpl implements IGUserPasswordService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GUserPasswordServiceImpl.class);

	private final IGeboSecretsAccessService secretsAccessService;

	public GUserPasswordServiceImpl(IGeboSecretsAccessService secretsAccessService) {
		this.secretsAccessService = secretsAccessService;
	}

	@Override
	public void storePassword(String username, String rawPassword) throws GeboCryptSecretException {
		requireUsername(username);
		if (rawPassword == null)
			throw new IllegalArgumentException("A null password cannot be stored for user " + username);
		GeboUsernamePasswordContent content = new GeboUsernamePasswordContent();
		content.setUsername(username);
		content.setPassword(rawPassword);

		String contextCode = IGUserPasswordService.contextCodeOf(username);
		SecretInfo existing = findSecretInfo(username);
		if (existing == null) {
			secretsAccessService.storeSecret(content, descriptionOf(username), contextCode);
		} else {
			// Same code, so every reference that already resolved this secret keeps
			// resolving it - a password change must not mint a second secret and orphan
			// the first, which would then sit in the store holding a live old password.
			secretsAccessService.updateSecret(content, descriptionOf(username), contextCode, existing.getCode());
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
			return false;
		}
	}

	@Override
	public void deletePassword(String username) throws GeboCryptSecretException {
		requireUsername(username);
		// Every secret in the context, not just the first: this is the one operation
		// that must leave nothing behind, and a duplicate left here would keep a deleted
		// user's password alive in the store.
		for (SecretInfo info : passwordSecretsOf(username)) {
			secretsAccessService.deleteSecret(info.getCode());
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
