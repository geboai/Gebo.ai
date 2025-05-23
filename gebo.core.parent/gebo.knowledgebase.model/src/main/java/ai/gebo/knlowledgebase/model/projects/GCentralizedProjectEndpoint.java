/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knlowledgebase.model.projects;

import ai.gebo.model.base.GObjectRef;

/**
 * AI generated comments
 * Represents a centralized project endpoint that extends the capabilities of a general project endpoint.
 */
public class GCentralizedProjectEndpoint extends GProjectEndpoint {
    // Reference to a remote project endpoint
    private GObjectRef<GProjectEndpoint> remoteProjectReference = null;

    /**
     * Retrieves the remote project reference.
     *
     * @return a reference to the remote project endpoint
     */
    public GObjectRef<GProjectEndpoint> getRemoteProjectReference() {
        return remoteProjectReference;
    }

    /**
     * Sets the remote project reference.
     *
     * @param remoteProjectReference the reference to set for the remote project endpoint
     */
    public void setRemoteProjectReference(GObjectRef<GProjectEndpoint> remoteProjectReference) {
        this.remoteProjectReference = remoteProjectReference;
    }

    /**
     * Creates a new instance of GCentralizedProjectEndpoint from an existing GProjectEndpoint.
     *
     * @param endpoint the GProjectEndpoint to copy information from
     * @return a new GCentralizedProjectEndpoint with data copied from the provided endpoint
     */
    public static GCentralizedProjectEndpoint of(GProjectEndpoint endpoint) {
        GCentralizedProjectEndpoint out = new GCentralizedProjectEndpoint();
        // Copying build system references from the given endpoint
        out.setBuildSystemsRefs(endpoint.getBuildSystemsRefs());
        // Copying programmed tables from the given endpoint
        out.setProgrammedTables(endpoint.getProgrammedTables());
        // Copying cataloging criteria from the given endpoint
        out.setCatalogingCriteria(endpoint.getCatalogingCriteria());
        // Copying code from the given endpoint
        out.setCode(endpoint.getCode());
        // Copying description from the given endpoint
        out.setDescription(endpoint.getDescription());
        // Copying object space type from the given endpoint
        out.setObjectSpaceType(endpoint.getObjectSpaceType());
        // Copying open zips from the given endpoint
        out.setOpenZips(endpoint.getOpenZips());
        // Copying parent project code from the given endpoint
        out.setParentProjectCode(endpoint.getParentProjectCode());
        // Copying published status from the given endpoint
        out.setPublished(endpoint.getPublished());
        // Copying synchronization settings from the given endpoint
        out.setSynchPeriodically(endpoint.getSynchPeriodically());
        // Copying readonly status from the given endpoint
        out.setReadonly(endpoint.getReadonly());
        // Copying vectorize only extensions from the given endpoint
        out.setVectorizeOnlyExtensions(endpoint.getVectorizeOnlyExtensions());
        // Setting the remote project reference to a new GObjectRef of the endpoint
        out.setRemoteProjectReference(GObjectRef.of(endpoint));
        return out;
    }
}