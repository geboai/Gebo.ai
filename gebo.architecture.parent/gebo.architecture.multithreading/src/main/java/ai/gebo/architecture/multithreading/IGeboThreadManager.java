/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.multithreading;

import java.util.List;

/**
 * {@code IGeboThreadManager} is an interface for managing the execution of runnable tasks.
 * It provides methods to execute tasks and to monitor running tasks.
 * 
 * Gebo.ai comment agent
 */
public interface IGeboThreadManager {

    /**
     * Executes the given runnable task.
     *
     * @param runnable An instance of {@code IGRunnable} to be executed.
     */
    public void run(IGRunnable runnable);

    /**
     * Executes the given runnable task with a specified callback which is called 
     * after the task execution ends.
     *
     * @param <RunnableType> The type of runnable task that extends {@code IGRunnable}.
     * @param runnable The runnable task to be executed.
     * @param callback The callback to be executed at the end of the runnable.
     */
    public <RunnableType extends IGRunnable> void run(RunnableType runnable,
            IGEndOfRunningCallback<RunnableType> callback);

    /**
     * Retrieves a list of currently running monitorable tasks.
     *
     * @return A list of {@code IGUserMonitorableRunnable} that are currently being executed.
     */
    public List<IGUserMonitorableRunnable> getRunningMonitorableTask();
}