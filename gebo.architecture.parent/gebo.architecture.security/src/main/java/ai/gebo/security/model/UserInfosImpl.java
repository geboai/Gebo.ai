/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.security.model;

import java.util.List;

import ai.gebo.security.repository.UserRepository.UserInfos;

/**
 * Gebo.ai comment agent
 * An implementation of the UserInfos interface that holds user information such as email, name, surname, roles, and disability status.
 */
public class UserInfosImpl implements UserInfos {
    // User's email and serves as the username
    String email, name, sourname;
    // List of roles associated with the user
    List<String> roles;
    // Indicates whether the user is disabled
    Boolean disabled;

    /**
     * Constructs a UserInfosImpl object from a User object.
     * 
     * @param user the User object to retrieve information from
     */
    public UserInfosImpl(User user) {
        email = user.getUsername(); // Initialize email with the user's username
        name = user.getName(); // Initialize name with the user's name
        sourname = user.getSourname(); // Initialize surname with the user's surname
        roles = user.getRoles(); // Initialize roles with the user's roles
        disabled = user.getDisabled(); // Initialize disabled status with the user's disabled status
    }

    @Override
    public String getUsername() {
        // Returns the email of the user, which acts as the username
        return email;
    }

    @Override
    public String getName() {
        // Returns the name of the user
        return name;
    }

    @Override
    public String getSourname() {
        // Returns the surname of the user
        return sourname;
    }

    @Override
    public Boolean getDisabled() {
        // Returns whether the user is disabled
        return disabled;
    }

    @Override
    public List<String> getRoles() {
        // Returns the roles associated with the user
        return roles;
    }
}