/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging.orchestration;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Gebo.ai comment agent
 * This class handles the invocation of timeout callbacks for registered
 * ThreadMessageReceiverMultiplexer instances. It runs as a singleton component
 * and periodically checks each multiplexer for timeouts.
 */
@Component
@Scope("singleton")
public class TimeoutCallbackHandler {
    // Logger to capture and log events within this class
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeoutCallbackHandler.class);
    
    // A list to hold instances of ThreadMessageReceiverMultiplexer
    private List<ThreadMessageReceiverMultiplexer> multiplexers = new ArrayList<ThreadMessageReceiverMultiplexer>();

    /**
     * Adds a new ThreadMessageReceiverMultiplexer to the list for timeout
     * monitoring.
     * 
     * @param m the ThreadMessageReceiverMultiplexer instance to be added
     */
    public void add(ThreadMessageReceiverMultiplexer m) {
        synchronized (multiplexers) {
            // Adding a new multiplexer to the list in a thread-safe manner
            multiplexers.add(m);
        }
    }

    /**
     * Constructor for TimeoutCallbackHandler.
     * Initializes a new instance of the handler.
     */
    public TimeoutCallbackHandler() {
        
    }

    /**
     * Scheduled method that periodically invokes the callbackTick method on each
     * registered ThreadMessageReceiverMultiplexer. It is called every 20 seconds
     * after an initial delay of 10 seconds.
     */
    @Scheduled(initialDelay = 10 * 1000, fixedRate = 20 * 1000)
    public void checkTimeouts() {
        // Iterate over each multiplexer and invoke its timeout callback
        for (ThreadMessageReceiverMultiplexer multiplexer : multiplexers) {
            try {
                multiplexer.callbackTick();
            } catch (Throwable th) {
                // Log error or handle it appropriately if necessary
            }
        }
    }
}