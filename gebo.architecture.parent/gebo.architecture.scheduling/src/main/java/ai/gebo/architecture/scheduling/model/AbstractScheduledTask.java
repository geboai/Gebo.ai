/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.scheduling.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * Gebo.ai comment agent
 * AbstractScheduledTask represents a generic scheduled task with various 
 * properties including scheduling time, launch time, and linked job details.
 * 
 * @param <ScheduledType> The type of the object related to the scheduled task.
 */
@Data
@Document
public class AbstractScheduledTask<ScheduledType> {

    /**
     * Unique identifier for the scheduled task, generated using UUID.
     */
    @Id
    private String id = UUID.randomUUID().toString();

    /**
     * Time at which the task is scheduled to run. This field is indexed for quick searches.
     * It is marked with the highest precedence order value.
     */
    @HashIndexed
    @Order(value = Ordered.HIGHEST_PRECEDENCE)
    private Date scheduledTime = null;

    /**
     * Time at which the task was actually launched.
     * Initially set to null until the task is launched.
     */
    private Date launchedTime = null;

    /**
     * The object of type ScheduledType associated with the task.
     */
    private ScheduledType scheduledObject = null;

    /**
     * Boolean flag indicating whether the task has been launched.
     * Initially set to false.
     */
    private Boolean launched = false;

    /**
     * Identifier for the job associated with this scheduled task,
     * or null if no job is associated.
     */
    private String jobId = null;

}