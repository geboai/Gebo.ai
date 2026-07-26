/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ai.gebo.security.model.UserInfos;
import ai.gebo.security.model.UserPrincipal;
import ai.gebo.security.services.IGSecurityDirectory;
import ai.gebo.security.services.IGeboSystemUserService;

/**
 * Resolves a validated token's subject to a {@link UserDetails} through
 * {@link IGSecurityDirectory} instead of {@code UserRepository} directly.
 *
 * <p>
 * {@link CustomUserDetailsService} cannot be used for this: it queries the local
 * Mongo {@code users} collection, which only holds every user on the service that
 * owns it (heimdall, or the monolith). Every other microservice has its own,
 * separately-provisioned Mongo database (e.g. {@code brain-gebo} vs
 * {@code heimdall-gebo}) - a token heimdall issued for a real user validates fine
 * there (same shared signing key), but the subsequent local lookup finds nothing,
 * failing the request with an unlogged {@link UsernameNotFoundException}.
 * {@link IGSecurityDirectory} is precisely the seam built to survive that: it
 * resolves to {@code MongoSecurityDirectory} on the owning service and to
 * {@code RestSecurityDirectory} (a call to heimdall) everywhere else.
 * </p>
 *
 * <p>
 * Only usable for token-based authentication (LOCAL_JWT, OAuth2 JWT/opaque): the
 * directory deliberately never hands back a password (see its javadoc), so the
 * {@link UserPrincipal} this builds carries none - fine here since none of these
 * callers ever compare one, but wrong for {@code DaoAuthenticationProvider}-based
 * password login. That flow keeps using {@link CustomUserDetailsService}.
 * </p>
 */
@Service
public class DirectoryBackedUserDetailsService implements UserDetailsService {

	@Autowired
	IGSecurityDirectory securityDirectory;

	@Autowired
	IGeboSystemUserService systemUserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (systemUserService.isSystemUser(username)) {
			return systemUserService.getUserPrincipal();
		}
		UserInfos userInfos = securityDirectory.findUserByUsername(username);
		if (userInfos == null) {
			throw new UsernameNotFoundException("User not found with email : " + username);
		}
		if (Boolean.TRUE.equals(userInfos.getDisabled())) {
			throw new UsernameNotFoundException("User is disabled : " + username);
		}
		return UserPrincipal.create(userInfos);
	}
}
