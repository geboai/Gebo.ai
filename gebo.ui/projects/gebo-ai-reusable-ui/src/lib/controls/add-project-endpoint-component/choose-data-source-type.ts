/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * AI generated comments
 * Interface representing the data source type selection options.
 * Contains optional properties that define identification and description
 * information for a specific data source type, including its code, 
 * description, associated project code, and entity type.
 */
export interface ChooseDataSourceType {
    /** Unique identifier code for the data source type */
    code?: string;
    /** Human-readable description of the data source type */
    description?: string;
    /** Code of the project this data source type belongs to */
    projectCode?: string;
    /** Type of entity this data source represents */
    entityType?: string;
}