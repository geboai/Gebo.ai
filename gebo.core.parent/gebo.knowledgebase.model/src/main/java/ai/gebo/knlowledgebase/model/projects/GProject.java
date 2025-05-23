/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knlowledgebase.model.projects;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.contents.ObjectSpaceType;
import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.model.annotations.EntityDescription;
import ai.gebo.model.annotations.GObjectReference;
import ai.gebo.model.base.GBaseVersionableObject;

/**
 * AI generated comments
 * Represents a project or item entity documented for use with MongoDB.
 * This class extends GBaseVersionableObject and implements IGObjectWithSecurity.
 */
@Document
@EntityDescription(description = "Project/item")
public class GProject extends GBaseVersionableObject implements IGObjectWithSecurity {

    // List of group IDs or names that have access to this project.
    private List<String> accessibleGroups = null;

    // List of user IDs or usernames that have access to this project.
    private List<String> accessibleUsers = null;

    // Indicates whether the project is accessible to all users.
    private Boolean accessibleToAll = null;

    // Reference to the root knowledge base associated with this project.
    @GObjectReference(referencedType = GKnowledgeBase.class)
    private String rootKnowledgeBaseCode = null;

    // Type of object space associated with this project.
    private ObjectSpaceType objectSpaceType = null;

    /**
     * Serialization identifier for ensuring class consistency during serialization.
     */
    private static final long serialVersionUID = 5109012601323753322L;

    // Code of the parent project, if any.
    private String parentProjectCode = null;

    /**
     * @return the code of the parent project.
     */
    public String getParentProjectCode() {
        return parentProjectCode;
    }

    /**
     * @param parentProjectCode to set for the project.
     */
    public void setParentProjectCode(String parentProjectCode) {
        this.parentProjectCode = parentProjectCode;
    }

    /**
     * @return the code of the root knowledge base linked to this project.
     */
    public String getRootKnowledgeBaseCode() {
        return rootKnowledgeBaseCode;
    }

    /**
     * @param rootKnowledgeBaseCode to set for linking to the root knowledge base.
     */
    public void setRootKnowledgeBaseCode(String rootKnowledgeBaseCode) {
        this.rootKnowledgeBaseCode = rootKnowledgeBaseCode;
    }

    /**
     * @return the list of groups that can access this project.
     */
    public List<String> getAccessibleGroups() {
        return accessibleGroups;
    }

    /**
     * @param accessibleGroups to set for access permissions to this project.
     */
    public void setAccessibleGroups(List<String> accessibleGroups) {
        this.accessibleGroups = accessibleGroups;
    }

    /**
     * @return the list of users that can access this project.
     */
    public List<String> getAccessibleUsers() {
        return accessibleUsers;
    }

    /**
     * @param accessibleUsers to set for access permissions to this project.
     */
    public void setAccessibleUsers(List<String> accessibleUsers) {
        this.accessibleUsers = accessibleUsers;
    }

    /**
     * @return true if the project is accessible to all users; otherwise, false.
     */
    public Boolean getAccessibleToAll() {
        return accessibleToAll;
    }

    /**
     * @param accessibleToAll to set the visibility of this project to all users.
     */
    public void setAccessibleToAll(Boolean accessibleToAll) {
        this.accessibleToAll = accessibleToAll;
    }

    /**
     * @return the object space type associated with this project.
     */
    public ObjectSpaceType getObjectSpaceType() {
        return objectSpaceType;
    }

    /**
     * @param objectSpaceType to set for this project.
     */
    public void setObjectSpaceType(ObjectSpaceType objectSpaceType) {
        this.objectSpaceType = objectSpaceType;
    }
}