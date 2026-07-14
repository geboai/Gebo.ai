/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.User;
import ai.gebo.security.model.UserInfosImpl;
import ai.gebo.security.model.UserPrincipal;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGeboSystemUserService;

/**
 * The platform's own identity, resolved from
 * {@link GeboSecurityConfig.SystemUser} instead of from Mongo.
 *
 * <p>
 * Everything here follows from one decision: the system user is <b>virtual</b>.
 * It is described by configuration and materialised on demand, so it needs no
 * database row - which is what lets a service authenticate as the platform even
 * before (or without) any user store being reachable.
 * </p>
 *
 * <h2>Why it cannot log in</h2>
 * <p>
 * Its password is a random value generated once, here, at construction and never
 * stored, printed or returned. Nobody - including this process - can present the
 * plaintext, so {@code passwordEncoder.matches(...)} cannot succeed and a
 * password login against it always fails. That is a stronger guarantee than a
 * flag someone can flip: there is no secret to leak, because none was ever
 * chosen.
 * </p>
 *
 * <p>
 * The other two doors are shut elsewhere, at the only places they open:
 * {@code GUsersAdminServiceImpl.insertUser} refuses to create it, and
 * {@code GUsersAdminServiceImpl.createUserIfNotExists} refuses to auto-provision
 * it, which is the hook OAuth2 login would otherwise arrive through. So it can
 * be neither created by an admin nor federated into existence.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Service
public class GeboSystemUserServiceImpl implements IGeboSystemUserService {

	private final GeboSecurityConfig securityConfig;
	private final LocalJwtTokenProvider tokenProvider;

	/**
	 * An encoded password whose plaintext was thrown away the instant it was made.
	 * Not a placeholder: it is what makes a password login against the system user
	 * unwinnable rather than merely discouraged.
	 */
	private final String unusablePassword;

	public GeboSystemUserServiceImpl(GeboSecurityConfig securityConfig, LocalJwtTokenProvider tokenProvider,
			PasswordEncoder passwordEncoder) {
		this.securityConfig = securityConfig;
		this.tokenProvider = tokenProvider;
		// A single UUID (122 random bits) and no more: BCrypt rejects an input longer
		// than 72 bytes outright, so concatenating two would throw here - at bean
		// construction, i.e. it would stop the service from starting at all.
		this.unusablePassword = passwordEncoder.encode(UUID.randomUUID().toString());
	}

	@Override
	public String getUsername() {
		return securityConfig.getSystemUser().getUsername();
	}

	@Override
	public boolean isSystemUser(String username) {
		String systemUsername = getUsername();
		return username != null && systemUsername != null && systemUsername.equalsIgnoreCase(username.trim());
	}

	@Override
	public List<String> getRoles() {
		List<String> roles = securityConfig.getSystemUser().getRoles();
		return roles == null ? List.of() : List.copyOf(roles);
	}

	@Override
	public User getUser() {
		User user = new User();
		user.setUsername(getUsername());
		user.setName("Gebo.ai");
		user.setSourname("System");
		user.setRoles(new ArrayList<>(getRoles()));
		user.setDisabled(false);
		user.setEmailVerified(true);
		// LOCAL_JWT only: the system identity is never federated.
		user.setProvider(AuthProvider.local);
		user.setPassword(unusablePassword);
		return user;
	}

	@Override
	public UserInfos getUserInfos() {
		return new UserInfosImpl(getUser());
	}

	@Override
	public UserPrincipal getUserPrincipal() {
		return UserPrincipal.create(getUser());
	}

	@Override
	public String createToken() {
		long expiration = System.currentTimeMillis() + securityConfig.getSystemUser().getTokenExpirationMsec();
		// The very same minting path a human's local login uses - only the subject differs.
		return tokenProvider.createToken(getUsername(), new Date(expiration));
	}
}
