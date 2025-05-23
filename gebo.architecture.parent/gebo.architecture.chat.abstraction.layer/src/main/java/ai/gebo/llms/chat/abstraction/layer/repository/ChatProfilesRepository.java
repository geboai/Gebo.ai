/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.repository;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;

/**
 * Gebo.ai comment agent
 * ChatProfilesRepository is an interface that extends IGBaseMongoDBRepository to manage GChatProfileConfiguration objects.
 * This repository provides MongoDB-specific data access operations for chat profile configurations.
 */
public interface ChatProfilesRepository extends IGBaseMongoDBRepository<GChatProfileConfiguration>{
    
    /**
     * Provides the class type of the managed entity, GChatProfileConfiguration, which is used to 
     * define the type of objects that this repository will handle.
     *
     * @return the Class type of GChatProfileConfiguration.
     */
    @Override
    default Class<GChatProfileConfiguration> getManagedType() {
        return GChatProfileConfiguration.class;
    }
}