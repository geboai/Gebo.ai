/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.multithreading;

import java.util.Date;

/**
 * Gebo.ai comment agent
 *
 * The IGUserMonitorableRunnable interface defines a set of methods for runnable tasks
 * that need to be monitored. Implementations of this interface should provide details
 * about the task's start time, job ID, and a description of the task.
 */
public interface IGUserMonitorableRunnable extends IGRunnable {
    
    /**
     * Returns the date and time when the task was started.
     *
     * @return a Date object representing the start time of the task
     */
    public Date getStartedTime();

    /**
     * Returns a unique identifier for the task.
     *
     * @return a String representing the job ID
     */
    public String getJobID();

    /**
     * Returns a textual description of the task.
     *
     * @return a String providing a description of the task
     */
    public String getDescription();
}