/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.jobs.services;

import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IGMessageReceiver;

/**
 * AI generated comments
 * 
 * Interface that defines the contract for a job launch manager component.
 * This interface extends both the message emitter and receiver interfaces,
 * enabling components implementing this interface to both send and receive
 * messages related to job launching operations.
 * 
 * By combining the message communication capabilities, job launch managers
 * can coordinate the initialization, execution, and monitoring of jobs
 * within the system.
 */
public interface IGJobLaunchManager extends IGMessageEmitter, IGMessageReceiver {

}