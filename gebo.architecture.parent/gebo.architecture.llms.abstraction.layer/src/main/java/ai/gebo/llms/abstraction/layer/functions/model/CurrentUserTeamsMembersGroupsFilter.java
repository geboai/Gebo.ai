/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.functions.model;

/**
 * Gebo.ai comment agent
 * Represents a filter used to determine the groups that a current user and their team members belong to.
 * The filter can specify a particular group by name or code, or indicate if all groups should be included.
 */
public class CurrentUserTeamsMembersGroupsFilter {
    // Name of the group, can be null if not specified.
    private String groupName = null;
    // Code of the group, can be null if not specified.
    private String groupCode = null;
    // Boolean flag indicating whether all groups should be included in the filter.
    private Boolean allGroups = true;

    /**
     * Returns the name of the group.
     *
     * @return the group name as a String, or null if not set
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Sets the name of the group.
     *
     * @param groupName the name of the group to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * Returns the code of the group.
     *
     * @return the group code as a String, or null if not set
     */
    public String getGroupCode() {
        return groupCode;
    }

    /**
     * Sets the code of the group.
     *
     * @param groupCode the code of the group to set
     */
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    /**
     * Determines if the filter includes all groups.
     *
     * @return true if all groups are included; false otherwise
     */
    public Boolean getAllGroups() {
        return allGroups;
    }

    /**
     * Sets whether all groups should be included in the filter.
     *
     * @param allGroups the flag to set indicating inclusion of all groups
     */
    public void setAllGroups(Boolean allGroups) {
        this.allGroups = allGroups;
    }
}