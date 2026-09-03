/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services;

import ai.gebo.crypting.services.GeboCryptSecretException;

/**
 * Where a local user's login password lives: the <b>secret store</b>, never the
 * user document.
 *
 * <h2>Why the password left {@code User}</h2>
 * <p>
 * {@code User} used to carry an encrypted {@code password} field. That put a
 * credential in the same Mongo collection every user lookup reads - the ACL
 * bootstrap, the admin user list, query-by-example, the OAuth2 provisioning path -
 * so a projection mistake, a log of the entity, or a serializer that ignored
 * {@code @JsonIgnore} leaked it. It also meant the platform had two independent
 * ways to hold a credential at rest: {@code GPasswordEncoder} for this one field,
 * and {@code IGeboSecretsAccessService} for everything else - the latter being the
 * one that knows about key rotation and external (vault) storage.
 * </p>
 *
 * <p>
 * There is now one. Every local password is a {@code USERNAME_PASSWORD} secret,
 * stored under the context code {@code "user:<username>"} - the same
 * {@code user:} convention {@code IAclGrantedAccessorService.getUniqueId} uses to
 * name a user - and that context code is also how it is found again. The secret
 * store encrypts the content itself, so what this interface hands back and takes
 * in is the <b>plaintext</b> password.
 * </p>
 *
 * <h2>Where it runs</h2>
 * <p>
 * Anywhere: the implementation only ever talks to
 * {@code ai.gebo.secrets.services.IGeboSecretsAccessService}, which is already the
 * local store on a service that hosts secrets and a REST client to heimdall on every
 * other one. In practice its callers - {@code MongoSecurityDirectory},
 * {@code GUsersAdminServiceImpl}, {@code CustomUserDetailsService} - are all on a
 * service that owns the user store anyway.
 * </p>
 *
 * <p>
 * A username is used <b>verbatim</b> as the context suffix: callers pass the
 * username as it is persisted (the Mongo {@code _id}), so that two accounts that
 * differ only by case can never collide on one secret.
 * </p>
 *
 * Gebo.ai comment agent
 */
public interface IGUserPasswordService {

	/** Prefix of the context code a user's password secret is filed under. */
	String USER_CONTEXT_PREFIX = "user:";

	/**
	 * The context code a user's password secret is stored under - {@code
	 * "user:<username>"}.
	 *
	 * @param username the username, exactly as persisted
	 * @return the context code
	 */
	static String contextCodeOf(String username) {
		return USER_CONTEXT_PREFIX + username;
	}

	/**
	 * Stores (or replaces) the user's password.
	 *
	 * @param username    the username, exactly as persisted
	 * @param rawPassword the plaintext password; the secret store encrypts it
	 * @throws GeboCryptSecretException if the secret store cannot write it
	 */
	void storePassword(String username, String rawPassword) throws GeboCryptSecretException;

	/**
	 * The user's plaintext password, or {@code null} if the user has none - a
	 * federated identity, or an account whose password was never set.
	 *
	 * @param username the username, exactly as persisted
	 * @return the plaintext password, or {@code null}
	 * @throws GeboCryptSecretException if the secret store cannot be read
	 */
	String findRawPassword(String username) throws GeboCryptSecretException;

	/**
	 * Whether a presented plaintext password is the user's.
	 *
	 * <p>
	 * Never throws: a user with no password, or a secret store that cannot answer,
	 * is a failed match, not a failed request.
	 * </p>
	 *
	 * @param username    the username, exactly as persisted
	 * @param rawPassword the presented plaintext password
	 * @return {@code true} if it matches
	 */
	boolean matches(String username, String rawPassword);

	/**
	 * Whether the user has a password at all.
	 *
	 * @param username the username, exactly as persisted
	 * @return {@code true} if a password secret exists for the user
	 */
	boolean hasPassword(String username);

	/**
	 * Removes the user's password secret. A no-op if there is none, so that
	 * deleting a user twice - or one that never had a password - is not an error.
	 *
	 * @param username the username, exactly as persisted
	 * @throws GeboCryptSecretException if the secret store cannot delete it
	 */
	void deletePassword(String username) throws GeboCryptSecretException;
}
