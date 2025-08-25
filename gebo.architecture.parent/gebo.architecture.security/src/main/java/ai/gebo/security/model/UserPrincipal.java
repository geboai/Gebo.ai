/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.security.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * AI generated comments
 * Implementation of UserDetails interface representing a user's principal information needed for authentication.
 */
public class UserPrincipal implements UserDetails {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    /**
     * Constructs a UserPrincipal with specified username, password, and authorities.
     *
     * @param email       the email of the user, used as the username.
     * @param password    the password of the user.
     * @param authorities the authorities granted to the user.
     */
    public UserPrincipal(String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = email;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Creates a UserPrincipal from a given User object.
     *
     * @param user the User object.
     * @return a UserPrincipal instance with roles converted to authorities.
     */
    public static UserPrincipal create(User user) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        if (user.getRoles() != null) {
            List<String> roles = user.getRoles();
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role)); // Convert roles to granted authorities
            }
        }

        return new UserPrincipal(user.getUsername(), user.getPassword(), authorities);
    }

    /**
     * Creates a UserPrincipal from a given User object and attributes map.
     *
     * @param user       the User object.
     * @param attributes additional attributes associated with the user.
     * @return a UserPrincipal instance with the given attributes.
     */
    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Always return true for non-expired account
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Always return true for non-locked account
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Always return true for credentials non-expired
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Always return true for enabled user
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Gets the additional attributes of the user.
     *
     * @return a map containing user attributes.
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * Sets additional attributes for the user.
     *
     * @param attributes the attributes to set.
     */
    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    /**
     * Sets the username for the user.
     *
     * @param email the new username to set.
     */
    public void setUsername(String email) {
        this.username = email;
    }

    /**
     * Sets the password for the user.
     *
     * @param password the new password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the authorities for the user.
     *
     * @param authorities the new authorities to set.
     */
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

}