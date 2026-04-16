/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.architecture.multithreading.config;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import ai.gebo.architecture.multithreading.GeboThreadManagerImpl;
import ai.gebo.architecture.multithreading.IGThreadPoolConsumer;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * Configuration class for setting up a thread pool executor.
 * <p>
 * AI generated comments
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.multithreading.config")
public class ThreadPoolConfig {

	// Logger for the ThreadPoolConfig class
	static Logger LOGGER = LoggerFactory.getLogger(ThreadPoolConfig.class);

	// List of consumers that will use the thread pool
	@Autowired(required = false)
	List<IGThreadPoolConsumer> consumers;

	// Core number of threads in the pool
	Integer corePoolsSize = 5;

	// Maximum allowed number of threads in the pool
	Integer maxPoolSize = 10;

	// Capacity of the task queue
	Integer queueCapacity = 50;

	int boundedElasticSize = 10;

	int boundedElasticMaxQueue = 10000;

	int executionServiceCore = 8, executionServiceMax = 16, executionServiceQueueSize = 2000;

	/**
	 * Bean for creating a singleton instance of the thread manager.
	 * 
	 * @return a configured instance of IGeboThreadManager
	 */
	@Bean
	@Scope("singleton")
	public IGeboThreadManager geboThreadManager() {
		LOGGER.info("Begin threading pool configuration");
		int nConcurrent = 3;

		// Determine the number of concurrent threads based on the consumers
		if (consumers != null) {
			for (IGThreadPoolConsumer factory : consumers) {
				nConcurrent += factory.getPoolCardinality();
			}
		}

		// Configure core pool size if not set
		if (corePoolsSize == null)
			corePoolsSize = nConcurrent;

		// Configure max pool size if not set and ensure conditions
		if (maxPoolSize == null)
			maxPoolSize = nConcurrent * 2;
		else {
			if (maxPoolSize < nConcurrent * 2) {
				maxPoolSize = nConcurrent * 2;
			}
		}

		// Configure queue capacity if not set and ensure conditions
		if (queueCapacity == null) {
			queueCapacity = nConcurrent * 4;
		} else {
			if (queueCapacity < nConcurrent * 4) {
				queueCapacity = nConcurrent * 4;
			}
		}

		// Logging the final configurations for the thread pool
		LOGGER.info("Initiating thread execution with corePoolSize=" + corePoolsSize + " maxPoolSize=" + maxPoolSize
				+ " queueCapacity=" + queueCapacity);

		// Create and configure the ThreadPoolTaskExecutor
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolsSize); // Minimum number of threads in the pool
		executor.setMaxPoolSize(maxPoolSize); // Maximum number of threads in the pool
		executor.setQueueCapacity(queueCapacity); // Queue capacity for pending tasks
		executor.setThreadNamePrefix("gebo.ai-tpool-"); // Prefix for thread names
		executor.setThreadPriority(Thread.MAX_PRIORITY);
		executor.setWaitForTasksToCompleteOnShutdown(false); // Ensures tasks complete on shutdown
		executor.setAwaitTerminationSeconds(5); // Timeout for waiting for tasks to complete
		executor.initialize(); // Initializes the thread pool

		LOGGER.info("End threading pool configuration");
		Scheduler boundedElastic = Schedulers.newBoundedElastic(boundedElasticSize, // max thread
				boundedElasticMaxQueue, // max task in coda
				"blocking-io-bounded-elastic");
		ThreadFactory tf = new ThreadFactory() {
			final AtomicInteger n = new AtomicInteger(1);

			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r, "executor-service-" + n.getAndIncrement());
				t.setDaemon(true);
				return t;
			}
		};

		ThreadPoolExecutor executorService = new ThreadPoolExecutor(executionServiceCore, executionServiceMax, 60L,
				TimeUnit.SECONDS, new ArrayBlockingQueue<>(executionServiceQueueSize), tf,
				new ThreadPoolExecutor.AbortPolicy() // meglio in reactive
		);
		Scheduler scheduler = Schedulers.fromExecutorService(executorService);
		return new GeboThreadManagerImpl(executor, boundedElastic, executorService, scheduler);
	}

	/**
	 * Gets the core pool size.
	 * 
	 * @return the core pool size
	 */
	public Integer getCorePoolsSize() {
		return corePoolsSize;
	}

	/**
	 * Sets the core pool size.
	 * 
	 * @param corePoolsSize the desired core pool size
	 */
	public void setCorePoolsSize(Integer corePoolsSize) {
		this.corePoolsSize = corePoolsSize;
	}

	/**
	 * Gets the maximum pool size.
	 * 
	 * @return the maximum pool size
	 */
	public Integer getMaxPoolSize() {
		return maxPoolSize;
	}

	/**
	 * Sets the maximum pool size.
	 * 
	 * @param maxPoolSize the desired maximum pool size
	 */
	public void setMaxPoolSize(Integer maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	/**
	 * Gets the queue capacity.
	 * 
	 * @return the queue capacity
	 */
	public Integer getQueueCapacity() {
		return queueCapacity;
	}

	/**
	 * Sets the queue capacity.
	 * 
	 * @param queueCapacity the desired queue capacity
	 */
	public void setQueueCapacity(Integer queueCapacity) {
		this.queueCapacity = queueCapacity;
	}
}