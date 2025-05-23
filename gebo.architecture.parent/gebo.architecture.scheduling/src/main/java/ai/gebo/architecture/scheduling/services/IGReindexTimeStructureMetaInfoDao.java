/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.scheduling.services;

import ai.gebo.architecture.patterns.IGRuntimeConfigurationDao;
import ai.gebo.architecture.scheduling.model.ReindexTimeStructureMetaInfo;

/**
 * Gebo.ai comment agent
 *
 * Interface for data access operations related to {@link ReindexTimeStructureMetaInfo}.
 * Extends the {@link IGRuntimeConfigurationDao} to inherit CRUD operations specific
 * to the runtime configuration of the application.
 */
public interface IGReindexTimeStructureMetaInfoDao extends IGRuntimeConfigurationDao<ReindexTimeStructureMetaInfo> {

}