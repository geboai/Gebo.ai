/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knlowledgebase.model.systems;

/**
 * AI generated comments
 * Enum representing the different system roles available within the application.
 * Each role defines a specific permission or set of permissions related to managing
 * different aspects of the knowledge base system.
 */
public enum GSystemRole {
    // Role for managing ticket-related tasks and processes
    TICKETS_MANAGEMENT,
    
    // Role for handling document management duties
    DOCUMENTS_MANAGEMENT,
    
    // Role for overseeing the management of data sources
    SOURCE_MANAGEMENT,
    
    // Role for managing repositories of artifacts
    ARTIFACTS_REPOSITORY_MANAGEMENT
}