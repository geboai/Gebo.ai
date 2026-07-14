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

import ai.gebo.security.model.User;
import ai.gebo.security.model.UserPrincipal;
import ai.gebo.security.model.UserInfos;

/**
 * The platform's own identity - who the software <b>is</b> when it acts on its
 * own behalf rather than on a user's - and the one service allowed to mint a
 * token for it.
 *
 * <p>
 * This exists to answer a question that has no good answer otherwise: what
 * credential does a call carry when it is made on <b>no user's</b> thread? LLM
 * clients are constructed at startup and on model replication, MCP connectors
 * reconnect in the background, schedulers run jobs. Such a thread has no
 * {@code SecurityContext} at all, or - under {@code IdentityUtil.doAs} - one
 * holding an authentication that was synthesized locally with <i>null
 * credentials</i>. Either way <b>there is no token to forward</b>: it is not that
 * propagation is inconvenient there, it is that nothing exists to propagate.
 * </p>
 *
 * <p>
 * So instead of forwarding a token, the platform <b>creates</b> one, for this
 * identity, through {@link #createToken()}. The result is an ordinary
 * {@code LOCAL_JWT} that the normal authentication path validates like any other
 * - no bypass, no shared static password-equivalent, and the calls arrive
 * attributable rather than anonymous.
 * </p>
 *
 * <h2>It is a virtual user</h2>
 * <p>
 * It has <b>no Mongo document</b>, and is resolved from configuration wherever a
 * user would normally be loaded. Consequently it:
 * </p>
 * <ul>
 * <li><b>cannot log in</b> - it holds no usable password;</li>
 * <li><b>cannot be created</b> - neither by an admin nor by OAuth2
 * auto-provisioning;</li>
 * <li><b>authenticates only by {@code LOCAL_JWT}</b> - never by OAuth2.</li>
 * </ul>
 *
 * <p>
 * The user/ACL services nonetheless report it as a real, enabled administrator,
 * so authorization works uniformly and nothing has to special-case it.
 * </p>
 *
 * Gebo.ai comment agent
 */
public interface IGeboSystemUserService {

	/**
	 * @return the system identity's username (its JWT subject), e.g.
	 *         {@code heimdall@bifrost.gebo.ai}
	 */
	String getUsername();

	/**
	 * Whether a username denotes the system identity. Compared case-insensitively,
	 * because the login paths lower-case what they are given.
	 *
	 * @param username a username; may be {@code null}
	 * @return {@code true} if it is the system identity
	 */
	boolean isSystemUser(String username);

	/** @return the roles granted to the system identity (by default {@code SYSTEM}, {@code ADMIN}) */
	List<String> getRoles();

	/**
	 * The system identity as a {@link User}. Freshly built from configuration and
	 * <b>never persisted</b> - do not save it.
	 *
	 * @return the (virtual) user
	 */
	User getUser();

	/**
	 * The system identity as {@link UserInfos}, for the user/ACL services.
	 *
	 * @return the (virtual) user infos: enabled, and holding {@link #getRoles()}
	 */
	UserInfos getUserInfos();

	/**
	 * The system identity as a Spring Security principal, for the
	 * {@code UserDetailsService}. Its password is unusable by construction, so no
	 * password login can ever succeed against it.
	 *
	 * @return the principal, with one authority per role
	 */
	UserPrincipal getUserPrincipal();

	/**
	 * Mints a fresh, short-lived {@code LOCAL_JWT} for the system identity.
	 *
	 * <p>
	 * This is the "fallback" for a call that has no caller token to forward. The
	 * token is signed with the same secret and validated by the same path as a user's
	 * token; the only thing special about it is its subject.
	 * </p>
	 *
	 * @return a signed JWT
	 */
	String createToken();
}
