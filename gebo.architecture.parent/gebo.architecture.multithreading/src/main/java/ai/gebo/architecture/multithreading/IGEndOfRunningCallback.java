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
 * Interface representing a callback to be executed when an IGRunnable 
 * has completed its execution.
 * 
 * @param <RunnableType> The type of runnable that this callback supports, 
 *                       which must extend from IGRunnable.
 */
public interface IGEndOfRunningCallback<RunnableType extends IGRunnable> {

    /**
     * Method to be called when the specified runnable has finished running.
     * 
     * @param runnable An instance of the runnable that has completed its execution.
     */
    public void onEndOfRunning(RunnableType runnable);
}