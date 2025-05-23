/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Interface representing an object with security features.
 * Provides methods for accessing ownership, accessibility, and predicates to determine object accessibility.
 * 
 * AI generated comments
 */
public interface IGObjectWithSecurity {

    /**
     * Retrieves the owner of the object.
     * 
     * @return the owner as a String
     */
    public String owner();

    /**
     * Checks if the object is accessible to all users.
     * 
     * @return true if accessible to all, otherwise false
     */
    public Boolean getAccessibleToAll();

    /**
     * Retrieves a list of users who have access to the object.
     * 
     * @return List of accessible user identifiers
     */
    public List<String> getAccessibleUsers();

    /**
     * Retrieves a list of groups that have access to the object.
     * 
     * @return List of accessible group identifiers
     */
    public List<String> getAccessibleGroups();

    /**
     * Generates a predicate to evaluate whether an object of type OS is accessible
     * based on the provided user security profile.
     * 
     * @param <OS>    the type that extends IGObjectWithSecurity
     * @param profile the user security profile used to check access permissions
     * @return a Predicate that determines if the object is accessible
     */
    public static <OS extends IGObjectWithSecurity> Predicate<OS> getAccessiblePredicate(
            final IGUserSecurityProfile profile) {

        // Map to track group access based on the user profile
        final Map<String, Boolean> groups = new HashMap<String, Boolean>();
        // Map to track role access (not used in current logic)
        final Map<String, Boolean> roles = new HashMap<String, Boolean>();
        
        // Populate groups map with user's groups from the profile
        final List<String> groupsList = profile.getUserGroups();
        if (groupsList != null) {
            groupsList.stream().forEach(x -> {
                groups.put(x, true);
            });
        }

        // Define predicate for accessibility based on user and group access
        final Predicate<OS> outPredicate = (OS object) -> {
            if (profile == null)
                return false; // No access if profile is null

            boolean accessible = object.getAccessibleToAll() != null && object.getAccessibleToAll();

            if (!accessible) {
                // Check user-specific accessibility
                if (object.getAccessibleUsers() != null && !object.getAccessibleUsers().isEmpty()) {
                    accessible = object.getAccessibleUsers().contains(profile.getUsername());
                }
                // Check group-specific accessibility
                if (!accessible && object.getAccessibleGroups() != null && !object.getAccessibleGroups().isEmpty()) {
                    accessible = object.getAccessibleGroups().stream().anyMatch((x -> groups.containsKey(x)));
                }
            }
            return accessible;
        };

        return outPredicate;
    }

    /**
     * Determines if the provided user profile matches the owner of the object.
     * 
     * @param profile the user security profile to check
     * @return true if the profile's username matches the owner, otherwise false
     */
    public default boolean isOwner(IGUserSecurityProfile profile) {
        String owner = owner();
        return owner != null && owner.equals(profile.getUsername());
    }
}