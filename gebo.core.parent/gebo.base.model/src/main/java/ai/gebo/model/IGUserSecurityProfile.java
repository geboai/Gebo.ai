/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.model;

import java.util.List;

/**
 * AI generated comments
 * The IGUserSecurityProfile interface defines methods
 * for obtaining security-related information for a user.
 */
public interface IGUserSecurityProfile {

    /**
     * Retrieves the username associated with the security profile.
     *
     * @return a String representing the username.
     */
    public String getUsername();

    /**
     * Retrieves the list of roles associated with the user.
     *
     * @return a List of Strings where each string is a user role.
     */
    public List<String> getUserRoles();

    /**
     * Retrieves the list of groups associated with the user.
     *
     * @return a List of Strings where each string is a user group.
     */
    public List<String> getUserGroups();
}