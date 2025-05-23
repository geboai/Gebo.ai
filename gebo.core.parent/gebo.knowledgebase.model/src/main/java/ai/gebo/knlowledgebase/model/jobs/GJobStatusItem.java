/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knlowledgebase.model.jobs;

import java.util.Date;

import ai.gebo.knlowledgebase.model.jobs.GJobStatus.JobType;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;

/**
 * AI generated comments
 * Represents the status of a job item in the system.
 * Provides methods to retrieve key status information about a job.
 */
public interface GJobStatusItem {
    
    /**
     * Retrieves the unique code associated with the job.
     * 
     * @return A string representing the job's unique code.
     */
    public String getCode();

    /**
     * Gets the type of job being performed.
     * 
     * @return The job type as an enumerated value.
     */
    public JobType getJobType();

    /**
     * Provides a description of the job.
     * 
     * @return A string containing the job's description.
     */
    public String getDescription();

    /**
     * Returns the start date and time of the job.
     * 
     * @return A Date object indicating when the job started.
     */
    public Date getStartDateTime();

    /**
     * Returns the end date and time of the job.
     * 
     * @return A Date object indicating when the job ended.
     */
    public Date getEndDateTime();

    /**
     * Indicates if there was an error during the job's execution.
     * 
     * @return A Boolean value: true if an error occurred, otherwise false.
     */
    public Boolean getError();

    /**
     * Checks if the job is currently being processed.
     * 
     * @return A Boolean value: true if the job is in progress, otherwise false.
     */
    public Boolean getProcessing();

    /**
     * Determines if the job has finished processing.
     * 
     * @return A Boolean value: true if the job is finished, otherwise false.
     */
    public Boolean getFinished();

    /**
     * Retrieves a reference to the project endpoint associated with the job.
     * 
     * @return A GObjectRef containing a reference to the GProjectEndpoint.
     */
    public GObjectRef<GProjectEndpoint> getProjectEndpointReference();
}