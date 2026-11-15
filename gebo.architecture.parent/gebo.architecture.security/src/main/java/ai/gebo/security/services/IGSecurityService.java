/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.security.services;

import java.util.Collection;
import java.util.List;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.IContentAclResource;
import ai.gebo.acl.ContentAccessPolicy;
import ai.gebo.acl.IAclGrantedAccessor;
import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.model.IGUserSecurityProfile;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.model.UserInfos;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Gebo.ai comment agent
 * 
 * Interface for security services related to managing user profiles, user
 * groups, and access control in the application.
 */
public interface IGSecurityService {

	/**
	 * Retrieves the security profile of the current user.
	 *
	 * @return The current user's security profile.
	 */
	public IGUserSecurityProfile getCurrentUserProfile();

	/**
	 * Obtains information about the current user.
	 *
	 * @return Information about the current user.
	 */
	public UserInfos getCurrentUser();

	/**
	 * Retrieves a list of groups that the current user belongs to.
	 *
	 * @return A list of user groups associated with the current user.
	 */
	public List<UsersGroup> getCurrentUserGroups();

	/**
	 * Checks if the current user has administrative privileges.
	 *
	 * @return True if the current user is an administrator, otherwise false.
	 */
	public boolean isCurrentUserAdmin();

	/**
	 * Whether the contents the current user reads are filtered by the ACL aliases:
	 * the platform's content access policy is ACL based and the user is neither an
	 * administrator nor one a product lets bypass the ACL (see
	 * {@link IGContentAclBypass}). The one rule of every content filter: the
	 * searches, the deep searches, the browsing and the reading of the knowledge
	 * bases' documents.
	 */
	public default boolean isContentAclEnforced() {
		return getPlatformContentAccessPolicy() == ContentAccessPolicy.ACL_BASED && !isCurrentUserAdmin()
				&& !isContentAclBypassed();
	}

	/**
	 * Whether a product lets the current user read every content regardless of the
	 * ACL aliases (see {@link IGContentAclBypass}); false when none does.
	 */
	public default boolean isContentAclBypassed() {
		return false;
	}

	/**
	 * Whether the current user passes the ACL check of a resource as an
	 * administrator does: an administrator when the check lets administrators do
	 * all, or a user a product lets read every content (see
	 * {@link IGContentAclBypass}) when the resource is a content of the knowledge
	 * bases ({@link IContentAclResource}) and the grants asked are reads only. A
	 * configuration is never opened by a bypass. The one gate of the ACL framework's
	 * checks ({@link #isCanDoAction}, {@link #filterCanDoAction},
	 * {@link #filterAclCanDoAction}), beside the content filters' one
	 * ({@link #isContentAclEnforced()}).
	 */
	public default boolean readsAsAdministrator(boolean adminCanDoAll, IAclGrantedResource resource,
			AclGrantType... grantType) {
		if (adminCanDoAll && isCurrentUserAdmin()) {
			return true;
		}
		return resource instanceof IContentAclResource && readOnly(grantType) && isContentAclBypassed();
	}

	/** Whether the grants asked are reads only; none asked is not a read. */
	public static boolean readOnly(AclGrantType... grantType) {
		if (grantType == null || grantType.length == 0) {
			return false;
		}
		for (AclGrantType grant : grantType) {
			if (grant != AclGrantType.READ) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Determines if the current user has access to a specified object.
	 *
	 * @param object        An object with security parameters.
	 * @param adminCanDoAll A flag indicating if administrators have full access.
	 * @return True if the user can access the object, otherwise false.
	 */
	public boolean isCanAccess(IGObjectWithSecurity object, boolean adminCanDoAll);

	public default <T extends IGObjectWithSecurity & IAclGrantedResource> boolean isCanDo(T object,
			boolean adminCanDoAll, AclGrantType... grantType) {
		switch (getPlatformContentAccessPolicy()) {
		case ACL_BASED: {
			return isCanDoAction(object, adminCanDoAll, grantType);
		}
		default:
			return isCanAccess(object, adminCanDoAll);
		}
	}

	public <T extends IGObjectWithSecurity & IAclGrantedResource> List<T> filterCanDoAction(Collection<T> objects,
			boolean adminCanDoAll, AclGrantType... grantType);

	public <T extends IAclGrantedResource> List<T> filterAclCanDoAction(Collection<T> objects, boolean adminCanDoAll,
			AclGrantType... grantType);

	/**
	 * Filters a collection of objects to include only those accessible by the
	 * current user.
	 *
	 * @param <T>           The type of objects with security constraints.
	 * @param objects       A collection of objects to be filtered.
	 * @param adminCanDoAll A flag indicating if administrators have full access.
	 * @return A list of objects that the current user can access.
	 */
	public <T extends IGObjectWithSecurity> List<T> filterAccessible(Collection<T> objects, boolean adminCanDoAll);

	/*******************************************************************************
	 * If the actual user matches the one creating the actual object does nothing
	 * otherwise throws a SecurityException
	 * 
	 * @param o
	 * @throws SecurityException
	 */
	public void checkBeingCreator(GBaseObject o) throws SecurityException;

	/**
	 * Checks that the current user may act on an object they may not have created:
	 * its creator passes, and so does whoever the platform's usual checks let
	 * through for the given action - the users, groups and everyone the object
	 * names, or its ACLs under the ACL-based policy (see
	 * {@link #isCanDo(IGObjectWithSecurity, boolean, AclGrantType...)}).
	 * Administrators are not let through on their role alone: an object its creator
	 * keeps to themselves stays theirs.
	 *
	 * @param object    the object, with the security fields the platform grants by
	 * @param grantType the action, as the ACLs name it
	 * @throws SecurityException when the current user is neither its creator nor
	 *                           granted the action
	 */
	public default <T extends GBaseObject & IGObjectWithSecurity & IAclGrantedResource> void checkBeingCreatorOrGranted(
			T object, AclGrantType... grantType) throws SecurityException {
		try {
			checkBeingCreator(object);
			return;
		} catch (SecurityException notTheCreator) {
			// not theirs: the usual checks decide
		}
		if (isCanDo(object, false, grantType)) {
			return;
		}
		throw new SecurityException("The actual user is neither the owner of this object nor granted access to it");
	}

	public IAclGrantedAccessor getCurrentAclGrantedAccessor() throws SecurityException;

	public IAclGrantedAccessor getCurrentAclGrantedAccessor(AclGrantType grantType) throws SecurityException;

	public boolean isCanDoAction(IAclGrantedResource resource, boolean adminCanDoAll, AclGrantType... grantType)
			throws SecurityException;

	@Getter
	@AllArgsConstructor
	public static class AclInfo {
		private final String uniqueId;
		private final AclGrantType[] grants;
	}

	public <T extends GBaseObject & IAclGrantedResource> void setAclAliases(List<T> aclGrantedObject,
			List<AclInfo> acls) throws GeboPersistenceException;

	public default <T extends GBaseObject & IAclGrantedResource> void setAclAliases(T aclGrantedObject,
			List<AclInfo> acls) throws GeboPersistenceException {
		setAclAliases(List.of(aclGrantedObject), acls);
	}

	@Getter
	@AllArgsConstructor
	public static class AclOwnerInfo {
		public static enum OwnerType {
			GROUP, USER
		}

		private final OwnerType ownerType;
		private final String ownerCode;
		private final AclGrantType[] grants;
	}

	public <T extends GBaseObject & IAclGrantedResource> void setAclAliasesForOwners(List<T> aclGrantedObject,
			List<AclOwnerInfo> acls) throws GeboPersistenceException;

	public default <T extends GBaseObject & IAclGrantedResource> void setAclAliasesForOwners(T aclGrantedObject,
			List<AclOwnerInfo> acls) throws GeboPersistenceException {
		setAclAliasesForOwners(List.of(aclGrantedObject), acls);
	}

	public ContentAccessPolicy getPlatformContentAccessPolicy();

	public boolean checkActualUserPassword(String confirmpassword) throws GeboCryptSecretException;
}