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

import jakarta.validation.constraints.NotNull;

/**
 * Represents an editable version of a user with fields that can be modified.
 * 
 * Gebo.ai comment agent
 */
public class EditableUser {

    /**
     * Constructs an EditableUser by copying properties from an existing User object.
     *
     * @param u the User object whose details are to be copied.
     */
    public EditableUser(User u) {
        this.name = u.getName();
        this.disabled = u.getDisabled();
        this.username = u.getUsername();
        this.roles = u.getRoles();
        this.sourname = u.getSourname();
    }

    @NotNull
    private String name = null;

    @NotNull
    private String sourname = null;

    @NotNull
    private String username;

    private Boolean disabled = null;

    @NotNull
    private List<String> roles = null;

    /**
     * Default constructor for creating an empty EditableUser instance.
     */
    public EditableUser() {

    }

    /**
     * Retrieves the name of the user.
     * 
     * @return the user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * 
     * @param name the new name to be assigned to the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the surname of the user.
     * 
     * @return the user's surname.
     */
    public String getSourname() {
        return sourname;
    }

    /**
     * Sets the surname of the user.
     * 
     * @param sourname the new surname to be assigned to the user.
     */
    public void setSourname(String sourname) {
        this.sourname = sourname;
    }

    /**
     * Retrieves the username of the user.
     * 
     * @return the user's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     * 
     * @param username the new username to be assigned to the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Checks if the user account is disabled.
     * 
     * @return true if the user is disabled, otherwise false.
     */
    public Boolean getDisabled() {
        return disabled;
    }

    /**
     * Sets the disabled status of the user account.
     * 
     * @param disabled the new disabled status.
     */
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * Retrieves the list of roles assigned to the user.
     * 
     * @return a list of role names assigned to the user.
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * Sets the list of roles for the user.
     * 
     * @param roles the new list of roles to be assigned.
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}