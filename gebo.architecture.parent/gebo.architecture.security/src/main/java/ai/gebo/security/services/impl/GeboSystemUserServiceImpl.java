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
import ai.gebo.security.model.UserInfos;
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
	private final PasswordEncoder passwordEncoder;

	/**
	 * An encoded password whose plaintext was thrown away the instant it was made.
	 * Not a placeholder: it is what makes a password login against the system user
	 * unwinnable rather than merely discouraged.
	 *
	 * <p>
	 * Computed <b>lazily</b>, and that is not an optimisation. The platform's
	 * {@link PasswordEncoder} is {@code GPasswordEncoder}, which is not a hash at all
	 * but a wrapper over the crypting service - and that service is not usable while
	 * beans are still being constructed. Encoding in the constructor therefore threw
	 * ("Problems in the underlying crypting system"), and because the security
	 * directory depends on this bean and half the platform depends on the directory,
	 * it took the entire application context down with it. It is built on first use
	 * instead: by the time anyone tries to authenticate, crypting is up.
	 * </p>
	 */
	private volatile String unusablePassword;

	public GeboSystemUserServiceImpl(GeboSecurityConfig securityConfig, LocalJwtTokenProvider tokenProvider,
			PasswordEncoder passwordEncoder) {
		this.securityConfig = securityConfig;
		this.tokenProvider = tokenProvider;
		this.passwordEncoder = passwordEncoder;
		// Nothing else here on purpose: a constructor that needs another subsystem to be
		// running is a constructor that can refuse to let the application start.
	}

	/**
	 * The system identity's unusable password, encoded once on first demand.
	 *
	 * <p>
	 * {@code GPasswordEncoder.matches(raw, encoded)} decrypts {@code encoded} and
	 * compares it to {@code raw}. So this yields a value that decrypts to a random
	 * UUID nobody - including this process, which discards it - ever knows. No
	 * presented password can equal it.
	 * </p>
	 */
	private String unusablePassword() {
		String password = unusablePassword;
		if (password == null) {
			synchronized (this) {
				password = unusablePassword;
				if (password == null) {
					password = passwordEncoder.encode(UUID.randomUUID().toString());
					unusablePassword = password;
				}
			}
		}
		return password;
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
		// No password here. getUser()/getUserInfos() are read on paths that have no
		// business touching the crypting service - the ACL bootstrap at startup, every
		// user/ACL lookup - and only getUserPrincipal() (the login path) actually needs
		// one. Encoding it here would drag the encoder into all of them.
		return user;
	}

	@Override
	public UserInfos getUserInfos() {
		return new UserInfosImpl(getUser());
	}

	@Override
	public UserPrincipal getUserPrincipal() {
		UserPrincipal principal = UserPrincipal.create(getUser());
		// The only place the password is needed, and so the only place it is built.
		principal.setPassword(unusablePassword());
		return principal;
	}

	@Override
	public String createToken() {
		long expiration = System.currentTimeMillis() + securityConfig.getSystemUser().getTokenExpirationMsec();
		// The very same minting path a human's local login uses - only the subject differs.
		return tokenProvider.createToken(getUsername(), new Date(expiration));
	}
}
