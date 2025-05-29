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
 * Abstract class that represents a service capable of being run in its own thread.
 * Provides a template for resource management and execution control.
 * 
 * Gebo.ai Commentor: AI generated comments
 */
public abstract class GRunnableService implements IGRunnable {

	// Indicates whether the service is currently running
	protected boolean running = true;

	// Monitor object used for synchronizing thread execution
	protected Object monitorObject = new Object();

	// Timeout period for waiting operations, -1 indicates no timeout
	protected long timeout = -1l;

	/**
	 * Constructor for GRunnableService. Initializes a new instance.
	 */
	public GRunnableService() {

	}

	/**
	 * Method that serves as the main execution loop for the service.
	 * It opens resources, waits for parameters, and performs service actions.
	 */
	@Override
	public void run() {
		this.openResources();
		boolean lastWasTimeout = false;
		while (running) {
			boolean runService = false;
			synchronized (monitorObject) {
				// Check and wait if parameters are not present
				if (!areParametersPresent()) {
					if (timeout > 0l && !lastWasTimeout) {
						try {
							monitorObject.wait(timeout);
							lastWasTimeout = !areParametersPresent();
						} catch (InterruptedException e) {
							running = false;
						}
					} else {
						try {
							monitorObject.wait();
						} catch (InterruptedException e) {
							running = false;
						}
						lastWasTimeout = true;
					}
				}
				// Perform critical operations if parameters are present
				if (areParametersPresent()) {
					criticalSection();
					runService = true;
				}
			}
			// Execute the service if the conditions are met
			if (runService) {
				doService();
			}
		}
		this.closeResources();
	}

	/**
	 * Synchronously runs the service by opening resources, checking parameters,
	 * and performing the service if applicable.
	 */
	protected void doSyncRun() {
		synchronized (monitorObject) {
			openResources();
			if (areParametersPresent()) {
				criticalSection();
				doService();
			}
			closeResources();
		}
	}

	/**
	 * Notifies the waiting thread to resume execution.
	 */
	protected void unlockExecution() {
		synchronized (monitorObject) {
			monitorObject.notify();
		}
	}

	/**
	 * Checks if the necessary parameters for service execution are present.
	 * 
	 * @return true if parameters are present, false otherwise.
	 */
	protected abstract boolean areParametersPresent();

	/**
	 * Opens the necessary resources required for the service.
	 */
	protected abstract void openResources();

	/**
	 * Executes critical operations that should be synchronized, like preparing data.
	 */
	protected abstract void criticalSection();

	/**
	 * Executes the primary operation or service logic.
	 */
	protected abstract void doService();

	/**
	 * Closes or releases any resources that were opened for the service.
	 */
	protected abstract void closeResources();

	/**
	 * Actions to perform when a timeout occurs during waiting.
	 */
	protected abstract void onTimout();

	/**
	 * Checks if the service is currently running.
	 * 
	 * @return true if the service is running, false otherwise.
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Initiates shutdown of the service by setting the running state to false.
	 */
	public void startShutdown() {
		this.running = false;
	}

}