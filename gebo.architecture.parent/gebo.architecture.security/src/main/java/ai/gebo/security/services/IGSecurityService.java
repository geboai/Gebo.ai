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

import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.model.IGUserSecurityProfile;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.repository.UserRepository.UserInfos;

/**
 * Gebo.ai comment agent
 * 
 * Interface for security services related to managing user profiles,
 * user groups, and access control in the application.
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
     * Determines if the current user has access to a specified object.
     *
     * @param object An object with security parameters.
     * @param adminCanDoAll A flag indicating if administrators have full access.
     * @return True if the user can access the object, otherwise false.
     */
    public boolean isCanAccess(IGObjectWithSecurity object, boolean adminCanDoAll);

    /**
     * Filters a collection of objects to include only those accessible by the current user.
     *
     * @param <T> The type of objects with security constraints.
     * @param objects A collection of objects to be filtered.
     * @param adminCanDoAll A flag indicating if administrators have full access.
     * @return A list of objects that the current user can access.
     */
    public <T extends IGObjectWithSecurity> List<T> filterAccessible(Collection<T> objects, boolean adminCanDoAll);
}