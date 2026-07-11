/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.jobs.services;

/**
 * AI generated comments
 * 
 * Interface for scheduling synchronization updates in the Gebo system.
 * This interface defines the contract for services that handle the scheduling
 * of periodic or triggered synchronization operations.
 */
public interface IGGeboScheduledSyncronizationService {
	/**
	 * Schedules updates to be executed according to the implementation's logic.
	 * Implementations should define when and how the synchronization updates occur.
	 */
	public void scheduleUpdates();
}