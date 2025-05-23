/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.multithreading;

/**
 * Gebo.ai comment agent
 * 
 * Interface representing a custom version of Runnable,
 * which provides additional functionalities for starting a shutdown process,
 * checking if a process is running, performing a shutdown,
 * and cleaning up resources.
 */
public interface IGRunnable extends Runnable {

    /**
     * Initiates the shutdown process of the implementing runnable.
     */
    public void startShutdown();

    /**
     * Checks if the runnable task is currently running.
     * 
     * @return true if the task is running, false otherwise.
     */
    public boolean isRunning();

    /**
     * Gracefully shuts down the runnable task.
     */
    public void shutdown();

    /**
     * Default method to clean up resources after shutdown.
     * Can be overridden by implementing classes if specific cleanup is required.
     */
    public default void cleanUp() {
    }
}