/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.fastsetup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.fastsetup.model.ComponentSetupStatus;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;

/**
 * Service for setting up and checking the status of chat profiles within the Gebo framework.
 * AI generated comments
 */
@Service
public class GeboFastChatProfileSetupService {

    // Manager for handling persistent objects, injected by Spring's Dependency Injection
    @Autowired
    IGPersistentObjectManager objectManager;

    /**
     * Determines the setup status of chat profiles by checking if any GChatProfileConfiguration
     * objects are present in the database.
     *
     * @return ComponentSetupStatus object indicating if the setup is complete (true if at least one configuration exists)
     * @throws GeboPersistenceException if there is an error accessing the persistent storage
     */
    public ComponentSetupStatus getChatProfilesSetupStatus() throws GeboPersistenceException {
        // Initialize a new object to store the setup status
        ComponentSetupStatus setupped = new ComponentSetupStatus();
        
        // Count the number of GChatProfileConfiguration objects persisted in the database
        long count = objectManager.countAll(GChatProfileConfiguration.class);
        
        // Determine if the setup process is complete (i.e., more than zero chat profile configurations exist)
        setupped.isSetup = count > 0l;
        
        return setupped;
    }
}