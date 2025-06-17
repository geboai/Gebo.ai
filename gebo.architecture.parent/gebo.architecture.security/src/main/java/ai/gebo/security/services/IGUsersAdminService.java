/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.security.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.gebo.security.model.EditableUser;
import ai.gebo.security.model.User;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.repository.UserRepository.UserInfos;

/**
 * Gebo.ai comment agent
 * 
 * An interface for administering users and groups within the application.
 * Provides capabilities to manage user and group information including 
 * creation, update, retrieval, and deletion.
 */
public interface IGUsersAdminService {
    
    /**
     * Inserts a new user into the system.
     * 
     * @param user The EditableUser object containing user details.
     * @param password The password for the new user.
     * @return The inserted EditableUser object.
     */
    public EditableUser insertUser(EditableUser user, String password);

    /**
     * Updates an existing user's information.
     * 
     * @param user The EditableUser object containing updated user details.
     * @return The updated EditableUser object.
     */
    public EditableUser updateUser(EditableUser user);

    /**
     * Retrieves a user by their username.
     * 
     * @param email The username (email) of the user to find.
     * @return The EditableUser object if the user is found.
     */
    public EditableUser findUserByUsername(String email);

    /**
     * Deletes a user from the system.
     * 
     * @param user The EditableUser object representing the user to delete.
     */
    public void deleteUser(EditableUser user);

    /**
     * Finds users based on a query-by-example (QBE).
     * 
     * @param qbe The User object serving as the QBE.
     * @param pageable The Pageable object for pagination information.
     * @return A Page of UserInfos that match the QBE.
     */
    public Page<UserInfos> findUserByQbe(User qbe, Pageable pageable);

    /**
     * Inserts a new user group into the system.
     * 
     * @param group The UsersGroup object containing group details.
     * @return The inserted UsersGroup object.
     */
    public UsersGroup insertGroup(UsersGroup group);

    /**
     * Finds a group by its code.
     * 
     * @param code The code of the group to find.
     * @return The UsersGroup object if the group is found.
     */
    public UsersGroup findGroupByCode(String code);

    /**
     * Updates an existing group's information.
     * 
     * @param group The UsersGroup object containing updated group details.
     * @return The updated UsersGroup object.
     */
    public UsersGroup updateGroup(UsersGroup group);

    /**
     * Deletes a group from the system.
     * 
     * @param group The UsersGroup object representing the group to delete.
     */
    public void deleteGroup(UsersGroup group);

    /**
     * Finds user groups based on a query-by-example (QBE).
     * 
     * @param qbe The UsersGroup object serving as the QBE.
     * @param pageable The Pageable object for pagination information.
     * @return A Page of UsersGroups that match the QBE.
     */
    public Page<UsersGroup> findUsersGroupByQbe(UsersGroup qbe, Pageable pageable);

    /**
     * Retrieves a list of all user groups in the system.
     * 
     * @return A List of all UsersGroup objects.
     */
    public List<UsersGroup> getAllGroups();

    /**
     * Retrieves a list of all users in the system.
     * 
     * @return A List of all UserInfos objects.
     */
    public List<UserInfos> getAllUsers();

	public void createUserIfNotExists(String email, Map<String, Object> attributes);
}