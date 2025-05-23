/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.buildsystems.abstraction.layer.functions;

import java.util.ArrayList;
import java.util.List;
import ai.gebo.knlowledgebase.model.contents.GPackageManager;

/**
 * Gebo.ai comment agent
 * Represents the parameters used for searching software artifacts, including details about 
 * the package manager, module or group identifier, and artifact identifier.
 */
public class GSoftwareArtifactSearchParameters {
    // The package manager associated with the software artifact
    private GPackageManager packageManager = null;
    // The module or group identifier of the software artifact
    private String moduleIdOrGroupId = null;
    // The artifact identifier
    private String artifactId = null;

    /**
     * Default constructor for creating an instance of GSoftwareArtifactSearchParameters.
     */
    public GSoftwareArtifactSearchParameters() {

    }

    /**
     * Converts the search parameters into a list of strings describing the module/group id
     * and artifact id, if available.
     * 
     * @return a list of string parameters.
     */
    public List<String> toParametersList() {
        List<String> params = new ArrayList<String>();
        // Check if moduleIdOrGroupId is set and add it to the list
        if (moduleIdOrGroupId != null) {
            params.add("module or group id=" + moduleIdOrGroupId);
        }
        // Check if artifactId is set and add it to the list
        if (artifactId != null) {
            params.add("artifact id=" + artifactId);
        }
        return params;
    }

    /**
     * Retrieves the module or group identifier.
     * 
     * @return the module or group identifier as a String.
     */
    public String getModuleIdOrGroupId() {
        return moduleIdOrGroupId;
    }

    /**
     * Sets the module or group identifier.
     * 
     * @param moduleIdOrGroupId the module or group identifier to set.
     */
    public void setModuleIdOrGroupId(String moduleIdOrGroupId) {
        this.moduleIdOrGroupId = moduleIdOrGroupId;
    }

    /**
     * Retrieves the artifact identifier.
     * 
     * @return the artifact identifier as a String.
     */
    public String getArtifactId() {
        return artifactId;
    }

    /**
     * Sets the artifact identifier.
     * 
     * @param artifactId the artifact identifier to set.
     */
    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    /**
     * Retrieves the associated package manager.
     * 
     * @return the package manager as a GPackageManager object.
     */
    public GPackageManager getPackageManager() {
        return packageManager;
    }

    /**
     * Sets the associated package manager.
     * 
     * @param packageManager the package manager to set.
     */
    public void setPackageManager(GPackageManager packageManager) {
        this.packageManager = packageManager;
    }

}