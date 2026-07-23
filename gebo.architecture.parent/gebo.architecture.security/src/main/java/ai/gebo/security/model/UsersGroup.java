/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.security.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Gebo.ai comment agent
 * Represents a group of users. 
 * This class is stored as a document in MongoDB.
 */
@Document
public class UsersGroup {

    /** 
     * The unique identifier for the user group.
     */
    @Id
    String code = null;

    /** 
     * A textual description of the user group.
     */
    String description = null;

    /**
     * A list containing the user IDs that belong to this group.
     */
    List<String> userIds = null;
    @JsonIgnore
    private Map<String, Object> customInfos=new HashMap<>();
    /**
     * Default constructor for UsersGroup.
     */
    public UsersGroup() {
        // Default constructor logic if any
    }

    /**
     * Returns the list of user IDs associated with this group.
     * 
     * @return a List of user IDs
     */
    public List<String> getUserIds() {
        return userIds;
    }

    /**
     * Sets the list of user IDs for this group.
     * 
     * @param userIds a List of user IDs to associate with the group
     */
    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    /**
     * Returns the unique code identifier for this group.
     * 
     * @return the code as a String
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the unique code identifier for this group.
     * 
     * @param code the code as a String
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Returns the description of this user group.
     * 
     * @return the description as a String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description for this user group.
     * 
     * @param description the description as a String
     */
    public void setDescription(String description) {
        this.description = description;
    }

	public Map<String, Object> getCustomInfos() {
		return customInfos;
	}

	public void setCustomInfos(Map<String, Object> customInfos) {
		this.customInfos = customInfos;
	}

}