/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;

/**
 * Gebo.ai comment agent
 *
 * The AbstractNavigationCoordinates class represents a navigational structure
 * within a virtual filesystem. It maintains a root reference and records
 * browsing steps with the possibility of including custom content associated with
 * each navigation step.
 * 
 * @param <CustomContent> The type of custom content to be associated with
 *                        navigation steps.
 */
public class AbstractNavigationCoordinates<CustomContent> implements Serializable {

    // The root of the virtual filesystem or navigation tree.
    private GVirtualFilesystemRoot root;

    // List of browsing steps recorded as PathInfo objects.
    private List<PathInfo> browsingSteps = new ArrayList<PathInfo>();

    // List of custom content corresponding to each browsing step.
    private List<CustomContent> browsingStepsCustom = new ArrayList<CustomContent>();

    /**
     * Retrieves the root of the virtual filesystem.
     * 
     * @return GVirtualFilesystemRoot the root of the filesystem.
     */
    public GVirtualFilesystemRoot getRoot() {
        return root;
    }

    /**
     * Sets the root of the virtual filesystem.
     * 
     * @param root the GVirtualFilesystemRoot to set as the root.
     */
    public void setRoot(GVirtualFilesystemRoot root) {
        this.root = root;
    }

    /**
     * Retrieves the list of browsing steps.
     * 
     * @return List of PathInfo representing the browsing steps.
     */
    public List<PathInfo> getBrowsingSteps() {
        return browsingSteps;
    }

    /**
     * Sets the list of browsing steps.
     * 
     * @param browsingSteps the List of PathInfo to set.
     */
    public void setBrowsingSteps(List<PathInfo> browsingSteps) {
        this.browsingSteps = browsingSteps;
    }

    /**
     * Retrieves the list of custom content associated with the browsing steps.
     * 
     * @return List of custom content.
     */
    public List<CustomContent> getBrowsingStepsCustom() {
        return browsingStepsCustom;
    }

    /**
     * Sets the list of custom content for the browsing steps.
     * 
     * @param browsingStepsCustom the List of custom content to set.
     */
    public void setBrowsingStepsCustom(List<CustomContent> browsingStepsCustom) {
        this.browsingStepsCustom = browsingStepsCustom;
    }
}