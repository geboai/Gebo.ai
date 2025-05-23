/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knlowledgebase.model.contents;

import java.util.List;

/**
 * Represents a tree structure of software artifact dependencies.
 * Each node in the tree corresponds to a software artifact and
 * can have a parent and a list of dependencies (children).
 * <br>
 * <b>AI generated comments</b>
 */
public class GDependencyTree extends GSoftwareArtifact {
    // Constant identifier for the Gebo artifact archetype
    public static final String GEBO_ARTIFACT_ARCHETYPE_ID = "GEBO_ARTIFACT_DESC";
    
    // Reference to the parent artifact in the dependency tree
    private GDependencyTree parentArtifact = null;

    // List of dependencies (child artifacts) of this artifact
    private List<GDependencyTree> dependencies = null;

    /**
     * Default constructor for a GDependencyTree.
     * Initializes an instance without any parent or dependencies.
     */
    public GDependencyTree() {
    
    }

    /**
     * Constructs a GDependencyTree with the specified deliverable.
     * @param deliverable the software artifact to initialize this dependency tree with
     */
    public GDependencyTree(GSoftwareArtifact deliverable) {
        super(deliverable);
    }

    /**
     * Gets the parent artifact of this dependency tree.
     * @return the parent artifact
     */
    public GDependencyTree getParentArtifact() {
        return parentArtifact;
    }

    /**
     * Sets the parent artifact for this dependency tree.
     * @param parentArtifact the parent artifact to set
     */
    public void setParentArtifact(GDependencyTree parentArtifact) {
        this.parentArtifact = parentArtifact;
    }
    
    /**
     * Gets the list of dependencies (child artifacts) of this dependency tree.
     * @return the list of dependencies
     */
    public List<GDependencyTree> getDependencies() {
        return dependencies;
    }
    
    /**
     * Sets the list of dependencies (child artifacts) for this dependency tree.
     * @param dependencies the list of dependencies to set
     */
    public void setDependencies(List<GDependencyTree> dependencies) {
        this.dependencies = dependencies;
    }
}