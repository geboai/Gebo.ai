/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ai.gebo.security.exception.ResourceNotFoundException;
import ai.gebo.security.model.User;
import ai.gebo.security.repository.UserRepository;

/**
 * Gebo.ai comment agent
 * Custom service to load user-specific data for authentication.
 * Implements UserDetailsService to provide user details to the Spring Security framework.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	// Dependency injection of UserRepository to perform database operations on User entities.
	@Autowired
	UserRepository userRepository;

	/**
	 * Loads a user by their email address for authentication purposes.
	 *
	 * @param email the email of the user to be loaded.
	 * @return UserDetails if user is found and not disabled.
	 * @throws UsernameNotFoundException if user is not found or disabled.
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// Retrieve user by email from the repository
		User user = userRepository.findByUsername(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

		// Check if user is disabled
		if (user.getDisabled() == null || !user.getDisabled()) {
			// Create and return UserPrincipal for authenticated user
			return UserPrincipal.create(user);
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
		// Retrieve user by ID from the repository
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

		// Check if user is disabled
		if (user.getDisabled() == null || !user.getDisabled()) {
			// Create and return UserPrincipal for authenticated user
			return UserPrincipal.create(user);
		} else {
			// Throw exception if user is disabled
			throw new ResourceNotFoundException("User", "id", id);
		}
	}
}