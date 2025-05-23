/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.maven.buildsystem.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemConfigurationDao;
import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;
import ai.gebo.knlowledgebase.model.systems.GBuildSystem;
import ai.gebo.maven.buildsystem.handler.config.GBaseMavenBuildSystemConfig;
import ai.gebo.maven.buildsystem.handler.config.MavenBuildSystemsConfiguration;

/**
 * AI generated comments
 * This class provides a runtime configuration DAO for Maven-based build systems.
 * It extends the {@link GAbstractRuntimeConfigurationDao} to leverage its functionality for managing configuration sources.
 * Implements {@link IGBuildSystemConfigurationDao} for compatibility with build system configuration management.
 */
@Service
public class GMavenConfigsRuntimeConfigurationDao
        extends GAbstractRuntimeConfigurationDao<GBuildSystem<GBaseMavenBuildSystemConfig>>
        implements IGBuildSystemConfigurationDao<GBaseMavenBuildSystemConfig> {

    /**
     * Constructs an instance of GMavenConfigsRuntimeConfigurationDao with specified configuration and optional dynamic source.
     *
     * @param config The Maven build systems configuration providing access to build system configurations.
     * @param source An optional dynamic configuration source for the build systems.
     *               If not provided, configuration will rely on static setup.
     */
    public GMavenConfigsRuntimeConfigurationDao(@Autowired MavenBuildSystemsConfiguration config,
                                                @Autowired(required = false) IGDynamicConfigurationSource<GBuildSystem<GBaseMavenBuildSystemConfig>> source) {
        super(config.getSystems(), source);
    }


}