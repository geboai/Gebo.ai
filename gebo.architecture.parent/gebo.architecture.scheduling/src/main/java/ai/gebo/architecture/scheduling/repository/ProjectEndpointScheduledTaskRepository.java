/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.scheduling.repository;

import org.springframework.data.domain.Page;

import ai.gebo.architecture.scheduling.model.ProjectEndpointScheduledTask;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;

/**
 * Gebo.ai comment agent
 *
 * Repository interface for managing scheduled tasks related to project endpoints.
 * Extends the generic AbstractScheduledTaskRepository for ProjectEndpointScheduledTask entities.
 */
public interface ProjectEndpointScheduledTaskRepository
        extends AbstractScheduledTaskRepository<ProjectEndpointScheduledTask> {

    /**
     * Retrieves a paginated list of scheduled tasks that have been launched, 
     * filtered by the class name and code of the scheduled object.
     *
     * @param className The class name of the scheduled object.
     * @param code The code of the scheduled object.
     * @param pageable Pageable object for pagination information.
     * @return A page of ProjectEndpointScheduledTask objects.
     */
    public Page<ProjectEndpointScheduledTask> findByLaunchedIsTrueAndScheduledObjectClassNameAndScheduledObjectCode(
            String className, String code, org.springframework.data.domain.Pageable pageable);

    /**
     * Retrieves a paginated list of launched scheduled tasks for a given scheduled object.
     *
     * @param scheduledObject The reference to the scheduled object.
     * @param pageable Pageable object for pagination information.
     * @return A page of ProjectEndpointScheduledTask objects.
     */
    public default Page<ProjectEndpointScheduledTask> findByLaunchedIsTrueAndScheduledObject(
            GObjectRef<GProjectEndpoint> scheduledObject, org.springframework.data.domain.Pageable pageable) {
        return findByLaunchedIsTrueAndScheduledObjectClassNameAndScheduledObjectCode(
                scheduledObject.getClassName(), scheduledObject.getCode(), pageable);
    }

    /**
     * Deletes scheduled tasks that have not been launched yet, 
     * filtered by the class name and code of the scheduled object.
     *
     * @param className The class name of the scheduled object.
     * @param code The code of the scheduled object.
     */
    public void deleteByScheduledObjectClassNameAndScheduledObjectCodeAndLaunchedIsFalse(String className, String code);

    /**
     * Deletes scheduled tasks that have not been launched yet for a given scheduled object.
     *
     * @param scheduledObject The reference to the scheduled object.
     */
    public default void deleteByScheduledObjectAndLaunchedIsFalse(GObjectRef<GProjectEndpoint> scheduledObject) {
        deleteByScheduledObjectClassNameAndScheduledObjectCodeAndLaunchedIsFalse(scheduledObject.getClassName(),
                scheduledObject.getCode());
    }
}