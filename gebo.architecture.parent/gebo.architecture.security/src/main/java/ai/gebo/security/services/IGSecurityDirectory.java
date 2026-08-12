/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services;

import java.util.List;
import java.util.Map;

import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.model.UserInfos;

/**
 * The <b>directory</b>: who the users are, which groups they belong to, and
 * whether a password is theirs. Nothing else.
 *
 * <h2>Why this exists</h2>
 * <p>
 * It is the single seam that lets the security services run either in the process
 * that owns the user store or in a process that does not.
 * </p>
 *
 * <p>
 * {@code IGSecurityService} looks like it could never leave the owning service:
 * 9 of its 16 methods take the <i>caller's own domain objects</i> - collections of
 * {@code IGObjectWithSecurity} / {@code IAclGrantedResource}, a {@code GBaseObject}
 * - and those cannot be shipped over a network to be judged elsewhere. But read
 * what they actually do and the picture inverts: they run {@code AclAccessCheck}
 * over <i>local</i> objects, and everything they need from outside is the current
 * user's identity, roles, groups and ACL aliases. Likewise
 * {@code AclGrantedAccessorServiceImpl} touches only {@code IAclAliasesDao} (a
 * per-service replica) and the user's groups.
 * </p>
 *
 * <p>
 * So the authorization <b>algorithm never moves</b> - it keeps running locally,
 * on local objects. Only the directory moves. Swap the implementation and the same
 * {@code GSecurityServiceImpl} and {@code AclGrantedAccessorServiceImpl} classes
 * serve both worlds unchanged:
 * </p>
 * <ul>
 * <li>{@code MongoSecurityDirectory} - reads the user store directly (heimdall,
 * monolith);</li>
 * <li>{@code RestSecurityDirectory} - calls heimdall
 * ({@code gebo.microservices.security.client}), request-scope cached.</li>
 * </ul>
 *
 * <p>
 * The alternative - reimplementing the ACL algorithm inside a client-proxy - would
 * have meant two copies of the rules that decide who may read what. This has one.
 * </p>
 *
 * Gebo.ai comment agent
 */
public interface IGSecurityDirectory {

	/**
	 * The user with this username.
	 *
	 * @param username the username (the user's email / id)
	 * @return the user, or {@code null} if there is no such user
	 */
	UserInfos findUserByUsername(String username);

	/**
	 * The groups a user belongs to.
	 *
	 * @param username the username
	 * @return the user's groups; empty if none
	 */
	List<UsersGroup> findGroupsOfUser(String username);

	/**
	 * Every group known to the platform. Used for an administrator, who sees them all.
	 *
	 * @return all groups
	 */
	List<UsersGroup> findAllGroups();

	/**
	 * Whether a plaintext password is the one belonging to a user - a
	 * re-authentication check ("confirm your password"), never a login.
	 *
	 * <p>
	 * The password itself is verified <b>where it is stored</b>: no password hash ever
	 * leaves the owning service, and the remote implementation sends the presented
	 * password to be checked rather than pulling the hash back to compare it here.
	 * </p>
	 *
	 * @param username the username
	 * @param rawPassword the presented plaintext password
	 * @return {@code true} if it matches
	 */
	boolean checkPassword(String username, String rawPassword);

	/**
	 * Creates the user if it does not already exist, then returns it either way -
	 * the auto-provisioning counterpart of {@link #findUserByUsername} for the
	 * OAuth2 bearer-token (resource-server) authentication path, mirroring what
	 * {@code GOAuth2UserService}/{@code ReactiveGOAuth2UserService} already do for
	 * the interactive {@code oauth2Login} redirect flow via
	 * {@code IGUsersAdminService.createUserIfNotExists} - except that flow only ever
	 * runs on a service that owns the user store (heimdall/monolith), while a bearer
	 * token is validated on every microservice. This method is the seam that lets
	 * the decision ("should this identity be auto-created?", governed by
	 * {@code ai.gebo.security.loginPolicy == TRUST_EVERY_OAUTH_IDENTITY}) stay with
	 * the caller while the actual write always lands on the one real user store,
	 * exactly like every other method here.
	 *
	 * @param username     the username (the user's email / id)
	 * @param attributes   claims/attributes from the token or OAuth2 userinfo
	 *                     response, stored as the new user's custom info
	 * @param authProvider which provider this identity is being provisioned for
	 * @return the existing or newly created user; never {@code null}
	 */
	UserInfos createUserIfNotExists(String username, Map<String, Object> attributes, AuthProvider authProvider);
}
