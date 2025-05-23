/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.security.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.model.OperationStatus;
import ai.gebo.security.TokenProvider;
import ai.gebo.security.controller.UserController.UserInfo;
import ai.gebo.security.model.User;
import ai.gebo.security.payload.AuthResponse;
import ai.gebo.security.payload.LoginRequest;
import ai.gebo.security.repository.UserRepository;
import jakarta.validation.Valid;

/**
 * AI generated comments
 * AuthController is responsible for handling authentication related requests.
 * It provides an endpoint for user login and returns authentication tokens.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private TokenProvider tokenProvider;

	/**
	 * Constructs an AuthController with the necessary dependencies.
	 *
	 * @param authenticationManager the authentication manager for authenticating user credentials
	 * @param userRepository the repository for accessing user data
	 * @param passwordEncoder the encoder for encrypting/decrypting passwords
	 * @param tokenProvider the provider for generating authentication tokens
	 */
	public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
			PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider = tokenProvider;
	}

	/**
	 * Authenticates a user based on login credentials and generates a token if successful.
	 *
	 * @param loginRequest the login request containing user credentials
	 * @return the status of the operation containing AuthResponse if successful, error message otherwise
	 */
	@PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		try {
			// Authenticate the user using the provided credentials
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			// Set the authentication in the security context
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Create a token for the authenticated user
			String token = tokenProvider.createToken(authentication);
			AuthResponse response = new AuthResponse(token);

			// Retrieve user information and include it in the response if available
			Optional<User> usr = userRepository.findById(loginRequest.getUsername());
			if (usr.isPresent()) {
				User u = usr.get();
				UserInfo userInfo = new UserInfo();
				userInfo.setUsername(u.getUsername());
				userInfo.setRoles(u.getRoles());
				response.setUserInfo(userInfo);
			}

			// Return operation status with the response
			return OperationStatus.of(response);
		} catch (Throwable th) {
			// Log any exception that occurs during authentication
			LOGGER.error("Authentication process exception", th);
			// Return error status if authentication fails
			return OperationStatus.<AuthResponse>ofError("Cannot authenticate with supplied credentials");
		}
	}
}