/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.llms.abstraction.layer.controllers.model.ConfigurationEntry;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;

/**
 * AI generated comments
 * Controller class responsible for handling chat model configurations.
 * Only accessible by users with ADMIN role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/ChatModelsController")
public class ChatModelsController {

    // Injecting the chat models types repository
    @Autowired
    IGChatModelConfigurationSupportServiceRepositoryPattern chatModelsTypesRepo;

    // Injecting the runtime configuration DAO
    @Autowired
    IGChatModelRuntimeConfigurationDao runtimeDao;

    /**
     * Default constructor for ChatModelsController.
     */
    public ChatModelsController() {

    }

    /**
     * Endpoint to get chat model types.
     *
     * @return a list of available chat model types as JSON.
     */
    @GetMapping(value = "getChatModelTypes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GChatModelType> getChatModelTypes() {
        // Map each model type to its type and return as a list
        List<GModelType> list = chatModelsTypesRepo.map((x) -> {
            return x.getType();
        });
        return new ArrayList(list);
    }

    /**
     * Endpoint to get runtime configured chat models.
     * 
     * @param modelTypeCode optional code to filter chat models by type.
     * @return a list of runtime configured chat models as JSON.
     */
    @GetMapping(value = "getRuntimeConfiguredChatModels", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ConfigurationEntry<GBaseChatModelConfig>> getRuntimeConfiguredChatModels(
            @RequestParam(name = "modelTypeCode", required = false) String modelTypeCode) {
        
        // If modelTypeCode is provided, filter configurations by type code
        if (modelTypeCode != null) {
            List<ConfigurationEntry<GBaseChatModelConfig>> configs = new ArrayList<ConfigurationEntry<GBaseChatModelConfig>>();
            List<IGConfigurableChatModel> elements = this.runtimeDao.findListByPredicate(x -> {
                return x.getType().getCode().equals(modelTypeCode);
            });
            if (elements != null) {
                // Map and collect configurations
                configs = new ArrayList(elements.stream().map(x -> {
                    return new ConfigurationEntry(x.getConfig());
                }).toList());
            }
            return configs;
        } else { 
            // Return all configurations if no type code is provided
            return new ArrayList(this.runtimeDao.getConfigurations().stream().map(x -> {
                return new ConfigurationEntry(x.getConfig());
            }).toList());
        }
    }
}