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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ai.gebo.acl.AclAccessCheck;
import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.ContentAccessPolicy;
import ai.gebo.acl.IAclAliasesDao;
import ai.gebo.acl.IAclGrantedAccess;
import ai.gebo.acl.IAclGrantedAccessor;
import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.crypting.services.IGeboCryptingService;
import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.model.IGUserSecurityProfile;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.User;
import ai.gebo.security.model.UserInfosImpl;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.repository.UserRepository;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.repository.UsersGroupRepository;
import ai.gebo.security.services.IAclGrantedAccessorService;
import ai.gebo.security.services.IGeboSystemUserService;
import ai.gebo.security.services.IGSecurityService;
import lombok.AllArgsConstructor;

/**
 * Service implementation for security-related functionalities. AI generated
 * comments
 */
@Service
@AllArgsConstructor
public class GSecurityServiceImpl implements IGSecurityService {
	private static final String USER = "user:";
	private static final String GROUP = "group:";
	private final static Logger LOGGER = LoggerFactory.getLogger(GSecurityServiceImpl.class);
	final UserRepository usersRepo;
	final UsersGroupRepository groupsRepo;
	final IAclGrantedAccessorService aclGrantedAccessorService;
	final IAclAliasesDao aclAliasesDao;
	final GeboSecurityConfig securityConfig;
	final IGeboCryptingService cryptService;
	final PasswordEncoder passwordEncoder;
	final IGPersistentObjectManager persistenceManager;
	final IGeboSystemUserService systemUserService;

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
		// The system identity is virtual - it has no Mongo document, so the lookup below
		// would throw "No current user found" for it. Resolving it here is enough to make
		// the WHOLE service work for it: isCurrentUserAdmin(), getCurrentUserGroups(),
		// getCurrentAclGrantedAccessor(), isCanAccess() and the filter* methods all read
		// the current user through this one method. It reports as an enabled ADMIN, so
		// authorization needs no special-casing anywhere else.
		if (systemUserService.isSystemUser(email))
			return systemUserService.getUserInfos();
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

	@Override
	public IAclGrantedAccessor getCurrentAclGrantedAccessor() throws SecurityException {
		UserInfos user = getCurrentUser();
		return aclGrantedAccessorService.fromUser(user);
	}

	@Override
	public IAclGrantedAccessor getCurrentAclGrantedAccessor(AclGrantType grantType) throws SecurityException {
		UserInfos user = getCurrentUser();
		return aclGrantedAccessorService.fromUser(user, grantType);
	}

	@Override
	public boolean isCanDoAction(IAclGrantedResource resource, boolean adminCanDoAll, AclGrantType... grantType)
			throws SecurityException {
		switch (getPlatformContentAccessPolicy()) {
		case GROUP_BASED: {
			return true;
		}
		case ACL_BASED: {
			if (adminCanDoAll && isCurrentUserAdmin())
				return true;
			if (grantType == null || grantType.length == 0l)
				return false;
			for (AclGrantType grant : grantType) {
				boolean ok = AclAccessCheck.hasAccess(aclAliasesDao, getCurrentAclGrantedAccessor(), resource, grant,
						!securityConfig.isUseAcl());
				if (ok)
					return true;
			}
			return false;
		}
		default: {
			return true;
		}
		}

	}

	@Override
	public ContentAccessPolicy getPlatformContentAccessPolicy() {
		return securityConfig.isUseAcl() ? ContentAccessPolicy.ACL_BASED : ContentAccessPolicy.GROUP_BASED;
	}

	@Override
	public <T extends IGObjectWithSecurity & IAclGrantedResource> List<T> filterCanDoAction(Collection<T> objects,
			boolean adminCanDoAll, AclGrantType... grantType) {
		if (adminCanDoAll && isCurrentUserAdmin())
			return new ArrayList<T>(objects);
		switch (getPlatformContentAccessPolicy()) {
		case ACL_BASED: {
			final List<IAclGrantedAccess> accesses = new ArrayList<>();
			IAclGrantedAccessor firstGrantsAcc = null;
			for (AclGrantType grant : grantType) {
				IAclGrantedAccessor grantsAcc = getCurrentAclGrantedAccessor(grant);
				if (firstGrantsAcc == null)
					firstGrantsAcc = grantsAcc;
				accesses.addAll(grantsAcc.getAccesses());
			}
			final IAclGrantedAccessor sampledGrantsAcc = firstGrantsAcc;
			IAclGrantedAccessor jointedAccessor = new IAclGrantedAccessor() {
				@Override
				public String getMainAclUniqueId() {

					return sampledGrantsAcc != null ? sampledGrantsAcc.getMainAclUniqueId() : "UNKNOWN";
				}

				@Override
				public List<IAclGrantedAccess> getAccesses() {

					return accesses;
				}
			};
			List<T> out = objects.stream().filter(object -> {
				for (AclGrantType grant : grantType) {
					if (AclAccessCheck.hasAccess(aclAliasesDao, jointedAccessor, object, grant, false))
						return true;
					else if (grant == AclGrantType.READ && isCanAccess(object, adminCanDoAll)) {
						return true;
					}
				}
				return false;
			}).toList();
			return out;
		}

		default:
			return filterAccessible(objects, adminCanDoAll);
		}

	}

	@Override
	public <T extends IAclGrantedResource> List<T> filterAclCanDoAction(Collection<T> objects, boolean adminCanDoAll,
			AclGrantType... grantType) {
		if (adminCanDoAll && isCurrentUserAdmin())
			return new ArrayList<T>(objects);
		switch (getPlatformContentAccessPolicy()) {
		case ACL_BASED: {
			final List<IAclGrantedAccess> accesses = new ArrayList<>();
			IAclGrantedAccessor firstGrantsAcc = null;
			for (AclGrantType grant : grantType) {
				IAclGrantedAccessor grantsAcc = getCurrentAclGrantedAccessor(grant);
				if (firstGrantsAcc == null)
					firstGrantsAcc = grantsAcc;
				accesses.addAll(grantsAcc.getAccesses());
			}
			final IAclGrantedAccessor sampledGrantsAcc = firstGrantsAcc;
			IAclGrantedAccessor jointedAccessor = new IAclGrantedAccessor() {
				@Override
				public String getMainAclUniqueId() {
					return sampledGrantsAcc != null ? sampledGrantsAcc.getMainAclUniqueId() : "UNKNOWN";
				}

				@Override
				public List<IAclGrantedAccess> getAccesses() {

					return accesses;
				}
			};
			List<T> out = objects.stream().filter(object -> {
				for (AclGrantType grant : grantType) {
					if (AclAccessCheck.hasAccess(aclAliasesDao, jointedAccessor, object, grant, false))
						return true;
				}
				return false;
			}).toList();
			return out;
		}
		case GROUP_BASED:
		default: {
			return new ArrayList<>(objects);
		}
		}
	}

	@Override
	public boolean checkActualUserPassword(String confirmpassword) throws GeboCryptSecretException {
		UserInfos currentUser = this.getCurrentUser();
		Optional<User> fullUsr = this.usersRepo.findById(currentUser.getUsername());
		if (fullUsr.isPresent()) {
			String encodedPwd = fullUsr.get().getPassword();
			return passwordEncoder.matches(confirmpassword, encodedPwd);
		} else
			return false;
	}

	@Override
	public <T extends GBaseObject & IAclGrantedResource> void setAclAliases(List<T> aclGrantedObject,
			List<AclInfo> acls) throws GeboPersistenceException {

		List<Integer> acl = new ArrayList<>();
		for (AclInfo aclInfo : acls) {
			if (aclInfo.getGrants() != null) {
				for (AclGrantType grant : aclInfo.getGrants()) {
					List<Integer> aclId = aclAliasesDao
							.findAliasesByAclGrantedUniqueIdAndAclGrantType(aclInfo.getUniqueId(), grant);
					if (aclId == null || aclId.isEmpty()) {
						LOGGER.error("Acl: " + aclInfo.getUniqueId() + " " + grant + " is not existing!!");
					} else
						acl.addAll(aclId);
				}
			}
		}
		if (!acl.isEmpty()) {
			for (T toBeGranted : aclGrantedObject) {
				toBeGranted.setAclAliases(acl);
				this.persistenceManager.update(toBeGranted);				
			}
		} else {
			LOGGER.error("Cannot set an empty acl");
		}

	}

	@Override
	public <T extends GBaseObject & IAclGrantedResource> void setAclAliasesForOwners(List<T> aclGrantedObject,
			List<AclOwnerInfo> acls) throws GeboPersistenceException {

		List<AclInfo> aclsEncoded = new ArrayList<>();
		for (AclOwnerInfo aclOwnerInfo : acls) {
			String uniqueId = null;
			if (aclOwnerInfo.getOwnerType() == null)
				throw new IllegalStateException("ownerType cannot be null here");
			switch (aclOwnerInfo.getOwnerType()) {
			case GROUP: {
				uniqueId = GROUP;
			}
				break;
			case USER: {
				uniqueId = USER;
			}
				break;
			}
			uniqueId += aclOwnerInfo.getOwnerCode();
			AclInfo aclInfo = new AclInfo(uniqueId, aclOwnerInfo.getGrants());
			aclsEncoded.add(aclInfo);
		}
		setAclAliases(aclGrantedObject, aclsEncoded);
	}

}