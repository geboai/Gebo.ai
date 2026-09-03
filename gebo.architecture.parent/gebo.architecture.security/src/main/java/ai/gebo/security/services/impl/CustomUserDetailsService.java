/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.security.services.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.security.exception.ResourceNotFoundException;
import ai.gebo.security.model.User;
import ai.gebo.security.model.UserPrincipal;
import ai.gebo.security.repository.UserRepository;
import ai.gebo.security.services.IGUserPasswordService;
import ai.gebo.security.services.IGeboSystemUserService;

/**
 * Gebo.ai comment agent
 * Custom service to load user-specific data for authentication.
 * Implements UserDetailsService to provide user details to the Spring Security framework.
 */
@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);

	// Dependency injection of UserRepository to perform database operations on User entities.
	@Autowired
	UserRepository userRepository;

	// The platform's own identity, which lives in configuration rather than Mongo.
	@Autowired
	IGeboSystemUserService systemUserService;

	// The password is no longer a field of the user document: it is a secret filed
	// under "user:<username>". This is the one UserDetailsService that resolves it,
	// because it is the one behind DaoAuthenticationProvider - every token path uses
	// DirectoryBackedUserDetailsService, which never needs a password and so never
	// pays for this lookup on a per-request basis.
	@Autowired
	IGUserPasswordService userPasswordService;

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * Loads a user by their email address for authentication purposes.
	 *
	 * @param email the email of the user to be loaded.
	 * @return UserDetails if user is found and not disabled.
	 * @throws UsernameNotFoundException if user is not found or disabled.
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// The system identity has no Mongo document by design, so it must be resolved
		// before the repository is consulted - this is the point at which a validated
		// system LOCAL_JWT becomes an authenticated principal. Its password is unusable,
		// so admitting it here does NOT open a password login (see GeboSystemUserServiceImpl).
		if (systemUserService.isSystemUser(email)) {
			return systemUserService.getUserPrincipal();
		}
		// Retrieve user by email from the repository
		User user = userRepository.findByUsername(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

		// Check if user is disabled
		if (user.getDisabled() == null || !user.getDisabled()) {
			// Create and return UserPrincipal for authenticated user
			return UserPrincipal.create(user, encodedPasswordOf(user));
		} else {
			// Throw exception if user is disabled
			throw new ResourceNotFoundException("User", "id", email);
		}
	}

	/**
	 * Loads a user by their unique identifier.
	 *
	 * @param id the ID of the user to be loaded.
	 * @return UserDetails if user is found and not disabled.
	 * @throws ResourceNotFoundException if user is not found or disabled.
	 */
	public UserDetails loadUserById(String id) {
		// Same reasoning as loadUserByUsername: no Mongo document exists for it.
		if (systemUserService.isSystemUser(id)) {
			return systemUserService.getUserPrincipal();
		}
		// Retrieve user by ID from the repository
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		
		// Check if user is disabled
		if (user.getDisabled() == null || !user.getDisabled()) {
			// Create and return UserPrincipal for authenticated user
			return UserPrincipal.create(user, encodedPasswordOf(user));
		} else {
			// Throw exception if user is disabled
			throw new ResourceNotFoundException("User", "id", id);
		}
	}

	/**
	 * The user's password in the form {@code DaoAuthenticationProvider} expects:
	 * read from the secret store as plaintext, then encoded, because
	 * {@code UserDetails.getPassword()} is contractually the <i>encoded</i> password and
	 * {@code GPasswordEncoder.matches} is what will be handed it.
	 *
	 * <p>
	 * A user with no password secret - a federated identity, or an account created
	 * before its password was set - gets an encoding of a fresh random value rather
	 * than {@code null}. That is not a placeholder: it keeps
	 * {@code DaoAuthenticationProvider} on its ordinary comparison path (which then
	 * fails, as it must) instead of the shorter "no credentials" one, so a
	 * password-less account is not distinguishable from a wrong password by timing or
	 * by the message that comes back. Same reasoning as the system identity's unusable
	 * password in {@code GeboSystemUserServiceImpl}.
	 * </p>
	 */
	private String encodedPasswordOf(User user) {
		String raw = null;
		try {
			raw = userPasswordService.findRawPassword(user.getUsername());
		} catch (GeboCryptSecretException | RuntimeException e) {
			// Deliberately not rethrown: this must degrade to "authentication fails", not to
			// a 500 on a login form. Logged loudly because it is an infrastructure fault.
			LOGGER.warn("Cannot read the password secret of user {} - password login will fail for it",
					user.getUsername(), e);
		}
		return passwordEncoder.encode(raw != null ? raw : UUID.randomUUID().toString());
	}
}