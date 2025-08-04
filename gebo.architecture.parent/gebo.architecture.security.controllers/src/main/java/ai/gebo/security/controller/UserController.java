/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.security.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.security.CurrentUser;
import ai.gebo.security.UserPrincipal;
import ai.gebo.security.exception.ResourceNotFoundException;
import ai.gebo.security.model.GroupInfo;
import ai.gebo.security.model.User;
import ai.gebo.security.model.UserInfo;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.repository.UserRepository;
import ai.gebo.security.services.IGSecurityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * REST controller for managing user-related operations such as fetching current
 * user details, retrieving user groups, and changing passwords. AI generated
 * comments
 */
@RestController
@PreAuthorize("hasAnyRole('USER','ADMIN')")
@RequestMapping("/api/users/ActualUserController")
public class UserController {

	@Autowired
	private UserRepository userRepository; // Repository for accessing user data
	@Autowired
	private PasswordEncoder passwordEncoder; // Password encoder for handling password encryption
	@Autowired
	private IGSecurityService securityService; // Security service for accessing additional security features

	/**
	 * Retrieves the current user's information based on the provided credentials.
	 *
	 * @param userPrincipal the principal of the current user
	 * @return UserInfo object containing current user details
	 * @throws ResourceNotFoundException if the user is not found
	 */
	@GetMapping(value = "me", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserInfo getCurrentUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null && context.getAuthentication() != null && context.getAuthentication().getName() != null) {
			String userName = context.getAuthentication().getName();
			Optional<User> usr = userRepository.findById(userName);
			if (usr.isPresent()) {
				User u = usr.get();
				UserInfo userInfo = new UserInfo();
				userInfo.setUsername(u.getUsername());
				userInfo.setRoles(u.getRoles());
				return userInfo;
			} else {
				throw new ResourceNotFoundException("User", "id", userName);
			}
		} else
			throw new ResourceNotFoundException("User", "id", "unknown user");
	}

	/**
	 * Retrieves the groups that the current user belongs to.
	 *
	 * @return List of GroupInfo objects representing the user's groups
	 */
	@GetMapping(value = "getMyGroups", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GroupInfo> getMyGroups() {
		List<UsersGroup> glist = securityService.getCurrentUserGroups();

		return glist.stream().map(GroupInfo::of).toList();
	}

	/**
	 * Inner class for encapsulating parameters required for changing a password.
	 */
	public static class ChangePasswordParam {
		@NotNull
		public String username = null;
		@NotNull
		public String oldPassword = null;
		@NotNull
		public String newPassword = null;
		@NotNull
		public String newPassword1 = null;
	}

	/**
	 * Inner class for capturing the response of a password change operation.
	 */
	public static class ChangePasswordResponse {
		public boolean ok = false, wrongPassword = true, newPasswordNeverMatch = true;
	}

	/**
	 * Endpoint to handle password change requests for the current user.
	 *
	 * @param param         the parameters required for changing the password
	 * @param userPrincipal the principal of the current user
	 * @return ChangePasswordResponse object indicating the result of the operation
	 * @throws RuntimeException if the user is not authenticated or existing
	 *                          credentials are invalid
	 */
	@PostMapping(value = "changePassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ChangePasswordResponse changePassword(@Valid @RequestBody ChangePasswordParam param,
			@CurrentUser UserPrincipal userPrincipal) {

		if (param.username != null && userPrincipal != null && param.username.equals(userPrincipal.getUsername())) {
			Optional<User> userOpt = userRepository.findById(userPrincipal.getUsername());
			if (userOpt.isEmpty())
				throw new RuntimeException();
			else {
				ChangePasswordResponse response = new ChangePasswordResponse();
				User user = userOpt.get();
				String encodeOld = passwordEncoder.encode(param.oldPassword);
				response.wrongPassword = !encodeOld.equals(user.getPassword());
				response.newPasswordNeverMatch = !param.newPassword.equals(param.newPassword1);
				if ((!response.wrongPassword) && (!response.newPasswordNeverMatch)) {
					user.setPassword(passwordEncoder.encode(param.newPassword));
					userRepository.save(user);
					response.ok = true;
				}
				return response;
			}
		} else
			throw new RuntimeException();
	}
}