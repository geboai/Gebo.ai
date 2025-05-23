/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.model.GModuleTrafficInfo;
import ai.gebo.architecture.patterns.model.GModuleUseInfo;
import ai.gebo.architecture.patterns.model.GModuleUseInfo.MInfoType;
import ai.gebo.architecture.patterns.model.GModuleUseInfo.ModuleType;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.model.EmbeddingTrafficInfo;

@Component
@Scope("singleton")
public class GEmbeddingModelRuntimeConfigurationDaoImpl
        extends GAbstractRuntimeConfigurationDao<IGConfigurableEmbeddingModel>
        implements IGEmbeddingModelRuntimeConfigurationDao, ApplicationListener<ContextRefreshedEvent> {

    // AI generated comments
    // Logger for this class
    static Logger LOGGER = LoggerFactory.getLogger(GEmbeddingModelRuntimeConfigurationDaoImpl.class);
    
    // ObjectMapper for handling JSON processing
    static ObjectMapper mapper = new ObjectMapper();

    // Repository pattern for managing embedding model configuration support services
    @Autowired
    IGEmbeddingModelConfigurationSupportServiceRepositoryPattern supportRepoPattern;

    // Manager for persistent object operations
    @Autowired
    IGPersistentObjectManager persistentObjectManager;

    /**
     * Constructor initializes the superclass with an empty list and null handler.
     */
    public GEmbeddingModelRuntimeConfigurationDaoImpl() {
        super(new ArrayList(), null);
    }

    /**
     * Finds a configurable embedding model by its code.
     *
     * @param code the code of the embedding model to find
     * @return the found embedding model or null if not found
     */
    @Override
    public IGConfigurableEmbeddingModel findByCode(String code) {
        return this.findByPredicate(x -> {
            return x.getCode() != null && code != null && x.getCode().equals(code);
        });
    }

    /**
     * Handles the application event of context refresh to initialize embedding models dynamically.
     *
     * @param event the context refreshed event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOGGER.info("Begin initializing embedding models dynamically");
        try {
            // Retrieve and iterate over configurable embedding model configurations
            List<GBaseEmbeddingModelConfig> configs = persistentObjectManager
                    .findAllExtendingType(GBaseEmbeddingModelConfig.class);
            for (GBaseEmbeddingModelConfig config : configs) {
                addRuntimeByConfig(config);
            }
        } catch (GeboPersistenceException | LLMConfigException e) {
            String msg = "FATAL EMBEDDING MODELS INITIALIZATION EXCEPTION";
            LOGGER.error(msg, e);
            throw new RuntimeException(msg, e);
        }

        LOGGER.info("End initializing embedding models dynamically");
    }

    /**
     * Adds a configurable embedding model to the static configurations list.
     *
     * @param element the embedding model to add
     */
    @Override
    public void add(IGConfigurableEmbeddingModel element) {
        this.staticConfigs.add(element);
    }

    /**
     * Adds a runtime by configuration.
     *
     * @param config the embedding model configuration
     * @throws LLMConfigException if the configuration is invalid or not found
     */
    @Override
    public void addRuntimeByConfig(GBaseEmbeddingModelConfig config) throws LLMConfigException {
        IGEmbeddingModelConfigurationSupportService handler = supportRepoPattern.findImplementation(x -> {
            return x.getType().getCode().equals(config.getModelTypeCode());
        });
        if (handler == null) {
            LOGGER.error("Received in configuration an embedding model with type=>" + config.getModelTypeCode()
                    + " that is not found");
            throw new LLMConfigException("Cannot find embedding model with type=>" + config.getModelTypeCode());
        }
        try {
            LOGGER.info("Initializing embedding model with configuration:" + mapper.writeValueAsString(config));
        } catch (JsonProcessingException e) {
            // Error during JSON processing, no action required here
        }
        IGConfigurableEmbeddingModel embedModel = handler.create(config);
        LOGGER.info("Initialized chatModel successfully");
        this.staticConfigs.add(embedModel);
    }

    /**
     * Deletes a configurable embedding model by code.
     *
     * @param code the code of the embedding model to delete
     * @throws LLMConfigException if deletion fails
     */
    @Override
    public void deleteByCode(String code) throws LLMConfigException {
        IGConfigurableEmbeddingModel item = this.findByCode(code);
        if (item != null) {
            staticConfigs.remove(item);
            // Delete operation for the item
            item.delete();
        }
    }

    /**
     * Retrieves module use information for all configured embedding models.
     *
     * @return the list of module use information
     */
    @Override
    public List<GModuleUseInfo> getModuleUseInfo() {
        List<GModuleUseInfo> use = new ArrayList<GModuleUseInfo>();

        for (IGConfigurableEmbeddingModel igConfigurableModel : staticConfigs) {
            GModuleUseInfo useItem = new GModuleUseInfo();
            useItem.setModuleId(GStandardModulesConstraints.CORE_MODULE);
            useItem.setHandlerId(igConfigurableModel.getType().getCode());
            GBaseModelChoice choosedModel = (igConfigurableModel.getConfig()).getChoosedModel();
            String specsCode = choosedModel != null ? choosedModel.getCode() : null;
            useItem.setSpecsCode(specsCode);
            useItem.setUsed(true);
            useItem.setInfoType(MInfoType.SETUP);
            useItem.setConfigNumbers(1);
            useItem.setModuleType(ModuleType.LLMS);
            use.add(useItem);
        }
        return use;
    }

    /**
     * Retrieves module traffic information starting from a given date.
     *
     * @param minDate the starting date for traffic information filtering
     * @return the list of module traffic information
     */
    @Override
    public List<GModuleTrafficInfo> getModuleTrafficInfo(Date minDate) {
        List<GModuleTrafficInfo> trafficInfos = new ArrayList<GModuleTrafficInfo>();
        for (IGConfigurableEmbeddingModel igConfigurableModel : staticConfigs) {
            EmbeddingTrafficInfo traffic = igConfigurableModel.getSampledBytesOfTraffic();
            if (traffic != null && traffic.bytesCount != 0l) {
                GModuleTrafficInfo useItem = new GModuleTrafficInfo();
                useItem.setModuleId(GStandardModulesConstraints.CORE_MODULE);
                useItem.setHandlerId(igConfigurableModel.getType().getCode());
                GBaseModelChoice choosedModel = (igConfigurableModel.getConfig()).getChoosedModel();
                String specsCode = choosedModel != null ? choosedModel.getCode() : null;
                useItem.setSpecsCode(specsCode);
                useItem.setUsed(true);
                useItem.setInfoType(MInfoType.RUNNING);
                useItem.setConfigNumbers(1);
                useItem.setTrafficSampleStart(minDate);
                useItem.setTrafficUnity("KBYTE");
                useItem.setModuleType(ModuleType.LLMS);
                double doubleValue = traffic.bytesCount;
                // Convert bytes to kilobytes for traffic accounting
                useItem.setTrafficAccounting(doubleValue / 1024.0);
                trafficInfos.add(useItem);
            }
        }
        return trafficInfos;
    }
}