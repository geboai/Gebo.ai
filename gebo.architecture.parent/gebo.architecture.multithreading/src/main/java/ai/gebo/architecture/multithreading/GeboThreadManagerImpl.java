/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.multithreading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Implementation of the IGeboThreadManager interface for managing
 * multithreading tasks using a ThreadPoolTaskExecutor.
 * This class also listens for the ContextClosedEvent to handle shutdown.
 * 
 * @author Gebo
 * @version 1.0
 * 
 * AI generated comments
 */
public class GeboThreadManagerImpl implements IGeboThreadManager, ApplicationListener<ContextClosedEvent> {
    // Logger for logging events specific to GeboThreadManagerImpl
    static Logger LOGGER = LoggerFactory.getLogger(GeboThreadManagerImpl.class);

    // Executor service for creating and managing threads
    ThreadPoolTaskExecutor executor = null;

    // Map to store running tasks against their identifiers
    Map<String, RunnableWrapper> map = new HashMap<String, RunnableWrapper>();

    // Counter map to keep track of instances of each runnable type
    Map<String, Integer> cntr = new HashMap<String, Integer>();

    /**
     * Constructor to initialize the ThreadPoolTaskExecutor.
     *
     * @param executor the ThreadPoolTaskExecutor to be used for task execution
     */
    public GeboThreadManagerImpl(ThreadPoolTaskExecutor executor) {
        this.executor = executor;
    }

    /**
     * Wrapper class for Runnables to manage their execution and lifecycle.
     */
    class RunnableWrapper implements Runnable {
        // Logger for logging events specific to RunnableWrapper
        static Logger LOGGER = LoggerFactory.getLogger(RunnableWrapper.class);

        // The runnable task to be executed
        final IGRunnable runnable;

        // Flag to indicate if the runnable task is user monitorable
        final boolean userMonitorable;

        // Unique identifier for each runnable instance
        String id = UUID.randomUUID().toString();

        // Optional callback to be executed at the end of the runnable's execution
        private IGEndOfRunningCallback callback = null;

        /**
         * Constructor to initialize a wrapper with a given runnable.
         *
         * @param r the IGRunnable to be wrapped
         */
        RunnableWrapper(IGRunnable r) {
            this.runnable = r;
            Integer postfix = null;
            synchronized (cntr) {
                postfix = cntr.get(r.getClass().getSimpleName());
                if (postfix == null) {
                    cntr.put(r.getClass().getSimpleName(), postfix = 0);
                } else {
                    cntr.put(r.getClass().getSimpleName(), postfix = postfix + 1);
                }
            }
            this.id = r.getClass().getSimpleName() + "-" + postfix;
            this.userMonitorable = r instanceof IGUserMonitorableRunnable;
        }

        /**
         * Constructor to initialize a wrapper with a given runnable and callback.
         *
         * @param r the IGRunnable to be wrapped
         * @param callback the callback to be invoked at the end of running
         * @param <RunnableType> the type of runnable
         */
        public <RunnableType extends IGRunnable> RunnableWrapper(RunnableType r,
                IGEndOfRunningCallback<RunnableType> callback) {
            this.runnable = r;
            this.callback = callback;
            Integer postfix = null;
            synchronized (cntr) {
                postfix = cntr.get(r.getClass().getSimpleName());
                if (postfix == null) {
                    cntr.put(r.getClass().getSimpleName(), postfix = 0);
                } else {
                    cntr.put(r.getClass().getSimpleName(), postfix = postfix + 1);
                }
            }
            this.id = r.getClass().getSimpleName() + "-" + postfix;
            this.userMonitorable = r instanceof IGUserMonitorableRunnable;
        }

        /**
         * Executes the wrapped runnable, and calls the callback if provided.
         */
        @Override
        public void run() {
            try {
                LOGGER.info("Begin running =>" + id);
                runnable.run();
                if (this.callback != null) {
                    try {
                        this.callback.onEndOfRunning(runnable);
                    } catch (Throwable th) {
                        // Ignore any exception thrown by callback
                    }
                }
                LOGGER.info("End running =>" + id);
            } finally {
                GeboThreadManagerImpl.this.exited(this, runnable);
            }
        }

        /**
         * Gets the runnable associated with this wrapper.
         *
         * @return the runnable
         */
        public IGRunnable getRunnable() {
            return runnable;
        }

        /**
         * Checks if the runnable is user monitorable.
         *
         * @return true if user monitorable, false otherwise
         */
        public boolean isUserMonitorable() {
            return userMonitorable;
        }

        /**
         * Gets the unique identifier of this wrapper.
         *
         * @return the identifier
         */
        public String getId() {
            return id;
        }

    }

    /**
     * Executes a given runnable in a new thread.
     *
     * @param runnable the IGRunnable to be executed
     */
    @Override
    public void run(IGRunnable runnable) {
        RunnableWrapper wrapper = new RunnableWrapper(runnable);
        map.put(wrapper.id, wrapper);
        Thread thread = executor.createThread(wrapper);
        thread.start();
    }

    /**
     * Cleans up resources after a runnable has finished its execution.
     *
     * @param runnableWrapper the wrapper of the runnable that has exited
     * @param runnable the runnable that has exited
     */
    void exited(RunnableWrapper runnableWrapper, IGRunnable runnable) {
        map.remove(runnableWrapper.id);
        runnable.cleanUp();
        LOGGER.info("Still running==>" + map.keySet());
    }

    /**
     * Handles the ContextClosedEvent by initiating shutdown of all running tasks.
     *
     * @param event the event triggered upon closing the application context
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        for (RunnableWrapper wrapper : map.values()) {
            wrapper.runnable.startShutdown();
        }
    }

    /**
     * Retrieves a list of all currently running user monitorable tasks.
     *
     * @return a list of IGUserMonitorableRunnable instances
     */
    @Override
    public List<IGUserMonitorableRunnable> getRunningMonitorableTask() {
        final List<RunnableWrapper> wrappers = new ArrayList<GeboThreadManagerImpl.RunnableWrapper>(map.values());
        return wrappers.stream().filter(x -> x.isUserMonitorable()).map(y -> {
            return (IGUserMonitorableRunnable) y.getRunnable();
        }).toList();
    }

    /**
     * Executes a given runnable in a new thread with a callback to be invoked 
     * upon completion.
     *
     * @param runnable the IGRunnable to be executed
     * @param callback the callback to be executed upon runnable completion
     * @param <RunnableType> the type of runnable
     */
    @Override
    public <RunnableType extends IGRunnable> void run(RunnableType runnable,
            IGEndOfRunningCallback<RunnableType> callback) {
        RunnableWrapper wrapper = new RunnableWrapper(runnable, callback);
        map.put(wrapper.id, wrapper);
        Thread thread = executor.createThread(wrapper);
        thread.start();
    }

}