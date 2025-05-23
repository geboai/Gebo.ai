/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion;

import ai.gebo.architecture.patterns.IGRuntimeConfigurationDao;
import ai.gebo.system.ingestion.model.IngestionHandlerConfig;

/**
 * AI generated comments
 * 
 * Interface for the Data Access Object that handles ingestion handler configurations.
 * This interface extends IGRuntimeConfigurationDao, specifying that it manages
 * runtime configurations specifically for IngestionHandlerConfig objects.
 * It provides methods to access and manipulate ingestion handler configurations
 * in the data store.
 */
public interface IGIngestionHandlerConfigDao extends IGRuntimeConfigurationDao<IngestionHandlerConfig>{

}