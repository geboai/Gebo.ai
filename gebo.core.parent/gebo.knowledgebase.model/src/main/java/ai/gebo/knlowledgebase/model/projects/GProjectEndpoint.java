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

import ai.gebo.knlowledgebase.model.contents.ObjectSpaceType;
import ai.gebo.knlowledgebase.model.scheduling.ReindexingProgrammedTable;
import ai.gebo.knlowledgebase.model.systems.BuildSystemRef;
import ai.gebo.model.annotations.GObjectReference;
import ai.gebo.model.base.GBaseObject;

/**
 * GProjectEndpoint represents the endpoint configuration for a specific project
 * within the knowledge base system.
 * AI generated comments
 */
public class GProjectEndpoint extends GBaseObject {

    /**
     * FilesSynchronizationStrategy defines strategies for synchronizing files.
     */
    public static enum FilesSynchronizationStrategy {
        SIZE_AND_TIMESTAMP_AND_HASH_CHECK, HASH_CHECK
    }

    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = -8881032775757831188L;

    @GObjectReference(referencedType = GProject.class)
    private String parentProjectCode = null; // The code of the parent project
    private Boolean readonly = null; // Flag indicating if the project is read-only
    private Boolean published = null; // Flag indicating if the project is published
    private Boolean synchPeriodically = null; // Flag for periodic synchronization
    private Boolean openZips = null; // Flag for opening zip files
    private List<BuildSystemRef> buildSystemsRefs = null; // List of build system references
    private String catalogingCriteria = null; // Criteria for cataloging the project
    private List<ReindexingProgrammedTable> programmedTables = null; // Scheduled tables for reindexing
    private List<String> vectorizeOnlyExtensions = null; // File extensions to vectorize
    public FilesSynchronizationStrategy synchroStrategy = null; // Synchronization strategy
    private ObjectSpaceType objectSpaceType = null; // The type of object space

    /**
     * Retrieves the parent project code.
     * 
     * @return the parent project code
     */
    public String getParentProjectCode() {
        return parentProjectCode;
    }

    /**
     * Sets the parent project code.
     * 
     * @param parentProjectCode the new parent project code
     */
    public void setParentProjectCode(String parentProjectCode) {
        this.parentProjectCode = parentProjectCode;
    }

    /**
     * Gets the read-only status.
     * 
     * @return true if read-only, false otherwise
     */
    public Boolean getReadonly() {
        return readonly;
    }

    /**
     * Sets the read-only status.
     * 
     * @param readonly true to mark as read-only
     */
    public void setReadonly(Boolean readonly) {
        this.readonly = readonly;
    }

    /**
     * Gets the list of build system references.
     * 
     * @return list of build system references
     */
    public List<BuildSystemRef> getBuildSystemsRefs() {
        return buildSystemsRefs;
    }

    /**
     * Sets the list of build system references.
     * 
     * @param buildSystemsRefs list of build system references
     */
    public void setBuildSystemsRefs(List<BuildSystemRef> buildSystemsRefs) {
        this.buildSystemsRefs = buildSystemsRefs;
    }

    /**
     * Checks if the project is published.
     * 
     * @return true if published, false otherwise
     */
    public Boolean getPublished() {
        return published;
    }

    /**
     * Sets the published status of the project.
     * 
     * @param published true to mark as published
     */
    public void setPublished(Boolean published) {
        this.published = published;
    }

    /**
     * Checks if the project synchronizes periodically.
     * 
     * @return true if synchronization is periodic, false otherwise
     */
    public Boolean getSynchPeriodically() {
        return synchPeriodically;
    }

    /**
     * Sets the periodic synchronization status.
     * 
     * @param synchPeriodically true to enable periodic synchronization
     */
    public void setSynchPeriodically(Boolean synchPeriodically) {
        this.synchPeriodically = synchPeriodically;
    }

    /**
     * Gets the file synchronization strategy.
     * 
     * @return the synchronization strategy
     */
    public FilesSynchronizationStrategy getSynchroStrategy() {
        return synchroStrategy;
    }

    /**
     * Sets the file synchronization strategy.
     * 
     * @param synchroStrategy the strategy to be set
     */
    public void setSynchroStrategy(FilesSynchronizationStrategy synchroStrategy) {
        this.synchroStrategy = synchroStrategy;
    }

    /**
     * Creates a clone of the GProjectEndpoint object.
     * 
     * @return a cloned instance of GProjectEndpoint
     */
    public GProjectEndpoint clone() {
        GProjectEndpoint e = new GProjectEndpoint();
        e.buildSystemsRefs = buildSystemsRefs;
        e.parentProjectCode = parentProjectCode;
        e.published = published;
        e.synchPeriodically = synchPeriodically;
        e.setCode(getCode());
        e.setDescription(getDescription());
        e.catalogingCriteria = catalogingCriteria;
        e.parentProjectCode = parentProjectCode;
        e.programmedTables = programmedTables;
        return e;
    }

    /**
     * Gets the cataloging criteria.
     * 
     * @return the cataloging criteria
     */
    public String getCatalogingCriteria() {
        return catalogingCriteria;
    }

    /**
     * Sets the cataloging criteria.
     * 
     * @param catalogingCriteria the criteria to be set
     */
    public void setCatalogingCriteria(String catalogingCriteria) {
        this.catalogingCriteria = catalogingCriteria;
    }

    /**
     * Retrieves the list of programmed tables for reindexing.
     * 
     * @return list of reindexing programmed tables
     */
    public List<ReindexingProgrammedTable> getProgrammedTables() {
        return programmedTables;
    }

    /**
     * Sets the scheduled tables for reindexing.
     * 
     * @param programmedTables the list of tables to set
     */
    public void setProgrammedTables(List<ReindexingProgrammedTable> programmedTables) {
        this.programmedTables = programmedTables;
    }

    /**
     * Checks if zip files are set to open.
     * 
     * @return true if open zips is enabled, false otherwise
     */
    public Boolean getOpenZips() {
        return openZips;
    }

    /**
     * Sets the open zips flag.
     * 
     * @param openZips true to enable opening zip files
     */
    public void setOpenZips(Boolean openZips) {
        this.openZips = openZips;
    }

    /**
     * Retrieves the list of file extensions that should only be vectorized.
     * 
     * @return list of file extensions for vectorization
     */
    public List<String> getVectorizeOnlyExtensions() {
        return vectorizeOnlyExtensions;
    }

    /**
     * Sets the file extensions for vectorization.
     * 
     * @param ingestOnlyExtensions list of file extensions to be vectorized
     */
    public void setVectorizeOnlyExtensions(List<String> ingestOnlyExtensions) {
        this.vectorizeOnlyExtensions = ingestOnlyExtensions;
    }

    /**
     * Gets the type of object space.
     * 
     * @return the object space type
     */
    public ObjectSpaceType getObjectSpaceType() {
        return objectSpaceType;
    }

    /**
     * Sets the type of object space.
     * 
     * @param objectSpaceType the object space type to be set
     */
    public void setObjectSpaceType(ObjectSpaceType objectSpaceType) {
        this.objectSpaceType = objectSpaceType;
    }

}