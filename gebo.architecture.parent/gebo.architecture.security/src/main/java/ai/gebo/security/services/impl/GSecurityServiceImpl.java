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
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.model.IGUserSecurityProfile;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.security.model.User;
import ai.gebo.security.model.UserInfosImpl;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.repository.UserRepository;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.repository.UsersGroupRepository;
import ai.gebo.security.services.IGSecurityService;

/**
 * Service implementation for security-related functionalities. AI generated
 * comments
 */
@Service
public class GSecurityServiceImpl implements IGSecurityService {
	@Autowired
	UserRepository usersRepo;
	@Autowired
	UsersGroupRepository groupsRepo;

	/**
	 * Constructor for GSecurityServiceImpl.
	 */
	public GSecurityServiceImpl() {

	}

	/**
	 * Retrieves the current authenticated user's information.
	 *
	 * @return The UserInfos of the current user.
	 * @throws RuntimeException if security context or user is not properly set.
	 */
	@Override
	public UserInfos getCurrentUser() {

		String email = SecurityUtils.getCurrentUserEmail();
		if (email == null)
			throw new RuntimeException("No principal name set");
		Optional<User> user = usersRepo.findById(email);
		if (user.isEmpty())
			throw new RuntimeException("No current user found");
		UserInfos usr = new UserInfosImpl(user.get());
		if (usr.getDisabled() != null && usr.getDisabled())
			throw new RuntimeException("Current user " + email + " is disabled");
		return usr;
	}

	/**
	 * Retrieves the groups associated with the current user.
	 *
	 * @return List of UsersGroup associated with the current user.
	 * @throws RuntimeException if the current user is disabled.
	 */
	@Override
	public List<UsersGroup> getCurrentUserGroups() {
		UserInfos user = getCurrentUser();
		if (user.getDisabled() != null && user.getDisabled())
			throw new RuntimeException("Current user is disabled");
		List<String> roles = user.getRoles();
		boolean isAdmin = roles != null && roles.contains("ADMIN");
		if (isAdmin)
			return groupsRepo.findAll();
		return groupsRepo.findByUserIdsIn(user.getUsername());
	}

	/**
	 * Checks if the current user has admin privileges.
	 *
	 * @return true if the current user is an admin, false otherwise.
	 * @throws RuntimeException if the current user is disabled.
	 */
	@Override
	public boolean isCurrentUserAdmin() {
		UserInfos user = getCurrentUser();
		if (user.getDisabled() != null && user.getDisabled())
			throw new RuntimeException("Current user is disabled");
		List<String> roles = user.getRoles();
		return roles != null && roles.contains("ADMIN");
	}

	/**
	 * Determines if the current user can access a given object.
	 *
	 * @param object        The object to check access for.
	 * @param adminCanDoAll Whether admins can access all objects regardless of
	 *                      other settings.
	 * @return true if the user can access the object, false otherwise.
	 * @throws RuntimeException if the current user is disabled.
	 */
	@Override
	public boolean isCanAccess(IGObjectWithSecurity object, boolean adminCanDoAll) {
		UserInfos user = getCurrentUser();
		if (user.getDisabled() != null && user.getDisabled())
			throw new RuntimeException("Current user is disabled");
		boolean accessibleFromAll = object.getAccessibleToAll() != null && object.getAccessibleToAll();
		if (accessibleFromAll)
			return true;
		List<String> roles = user.getRoles();
		boolean isAdmin = roles != null && roles.contains("ADMIN");
		if (isAdmin && adminCanDoAll)
			return true;
		if (object.getAccessibleUsers() != null && object.getAccessibleUsers().contains(user.getUsername()))
			return true;
		List<UsersGroup> groups = groupsRepo.findByUserIdsIn(user.getUsername());
		if (object.getAccessibleGroups() != null) {
			for (UsersGroup usersGroup : groups) {
				if (object.getAccessibleGroups().contains(usersGroup.getCode()))
					return true;
			}
		}
		return false;
	}

	/**
	 * Filters a collection of objects, returning only those accessible by the
	 * current user.
	 *
	 * @param objects       The collection of objects to filter.
	 * @param adminCanDoAll Whether admins can access all objects.
	 * @param <T>           The type of objects in the collection, must implement
	 *                      IGObjectWithSecurity.
	 * @return A list of accessible objects.
	 */
	@Override
	public <T extends IGObjectWithSecurity> List<T> filterAccessible(Collection<T> objects, boolean adminCanDoAll) {

		List<T> outObjects = new ArrayList<T>();
		for (T t : objects) {
			if (isCanAccess(t, adminCanDoAll))
				outObjects.add(t);
		}
		return outObjects;
	}

	/**
	 * Retrieves the security profile of the current user.
	 *
	 * @return The IGUserSecurityProfile of the current user.
	 */
	@Override
	public IGUserSecurityProfile getCurrentUserProfile() {
		final UserInfos user = getCurrentUser();
		final List<UsersGroup> groups = getCurrentUserGroups();
		return new GUserSecurityProfileImpl(user.getUsername(), user.getRoles(),
				groups != null ? groups.stream().map(x -> x.getCode()).toList() : new ArrayList<String>());
	}

	/**
	 * Implementation of IGUserSecurityProfile that holds security details of the
	 * user.
	 */
	private static class GUserSecurityProfileImpl implements IGUserSecurityProfile {
		final String username;
		final List<String> userRoles;
		final List<String> userGroups;

		/**
		 * Constructs a GUserSecurityProfileImpl with the specified details.
		 *
		 * @param username   The username of the user.
		 * @param userRoles  The roles assigned to the user.
		 * @param userGroups The groups the user belongs to.
		 */
		GUserSecurityProfileImpl(String username, List<String> userRoles, List<String> userGroups) {
			this.username = username;
			this.userRoles = userRoles;
			this.userGroups = userGroups;
		}

		/**
		 * @return The username of the user.
		 */
		@Override
		public String getUsername() {
			return username;
		}

		/**
		 * @return The roles assigned to the user.
		 */
		@Override
		public List<String> getUserRoles() {
			return userRoles;
		}

		/**
		 * @return The groups the user belongs to.
		 */
		@Override
		public List<String> getUserGroups() {
			return userGroups;
		}

	}

	@Override
	public void checkBeingCreator(GBaseObject o) throws SecurityException {
		if (o.getUserCreated() == null || o.getUserCreated().trim().length() == 0)
			throw new SecurityException("Object not owned");
		UserInfos user = getCurrentUser();
		if (user != null && user.getUsername().equals(o.getUserCreated()))
			return;
		throw new SecurityException("The actual user is not the owner of this object");
	}

}