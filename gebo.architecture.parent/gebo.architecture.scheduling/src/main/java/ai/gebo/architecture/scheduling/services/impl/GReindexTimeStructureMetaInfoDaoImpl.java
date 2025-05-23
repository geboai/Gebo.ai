/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.scheduling.services.impl;

import org.springframework.stereotype.Service;
import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.scheduling.model.ReindexTimeStructureMetaInfo;
import ai.gebo.architecture.scheduling.services.IGReindexTimeStructureMetaInfoDao;

/**
 * Gebo.ai comment agent
 * 
 * Service implementation for handling ReindexTimeStructureMetaInfo data access.
 * Extends the GAbstractRuntimeConfigurationDao to leverage common DAO functionalities.
 * Implements the IGReindexTimeStructureMetaInfoDao interface for reindex time structure meta-information specific operations.
 */
@Service
public class GReindexTimeStructureMetaInfoDaoImpl extends GAbstractRuntimeConfigurationDao<ReindexTimeStructureMetaInfo>
        implements IGReindexTimeStructureMetaInfoDao {

    /**
     * Constructor initializes the DAO with a specific configuration.
     * Invokes the super class constructor with required parameters.
     */
    public GReindexTimeStructureMetaInfoDaoImpl() {
        super(ReindexTimeStructureMetaInfo.ALL, null);
    }

    /**
     * Finds a ReindexTimeStructureMetaInfo object by its frequency code.
     *
     * @param code the frequency code of the ReindexTimeStructureMetaInfo to find.
     * @return the corresponding ReindexTimeStructureMetaInfo object or null if not found.
     */
    @Override
    public ReindexTimeStructureMetaInfo findByCode(String code) {
        // Uses a predicate to filter the collection by matching frequency code.
        return findByPredicate(x -> {
            return x.getFrequency().name().equals(code);
        });
    }
}