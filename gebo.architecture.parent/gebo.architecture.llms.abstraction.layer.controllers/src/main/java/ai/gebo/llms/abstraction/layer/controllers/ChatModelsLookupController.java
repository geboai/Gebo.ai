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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.model.base.GLookupEntry;
import ai.gebo.model.base.GLookupEntryRef;
import ai.gebo.security.services.IGSecurityService;

/**
 * Controller class to manage chat model lookups.
 * Provides endpoints to retrieve various chat model configurations.
 * AI generated comments
 */
@RestController
@RequestMapping("api/users/ChatModelsLookupController")
public class ChatModelsLookupController {

    @Autowired
    IGChatModelConfigurationSupportServiceRepositoryPattern chatModelsTypesRepo;

    @Autowired
    IGChatModelRuntimeConfigurationDao runtimeDao;

    @Autowired
    IGSecurityService securityService;

    /**
     * Default constructor for ChatModelsLookupController.
     */
    public ChatModelsLookupController() {

    }

    /**
     * Retrieves a list of available chat model types and returns them as lookup entries.
     *
     * @return a list of {@link GLookupEntry} representing chat model types.
     */
    @GetMapping(value = "getChatModelTypesLookup", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GLookupEntry> getChatModelTypesLookup() {
        List<GModelType> list = chatModelsTypesRepo.map((x) -> {
            return x.getType();
        });
        return GLookupEntry.of(new ArrayList(list));
    }

    /**
     * Retrieves runtime configured chat models based on an optional model type code.
     * Filters the configurations through a security service to ensure accessibility.
     *
     * @param modelTypeCode the optional code of the model type to filter configurations.
     * @return a list of {@link GLookupEntryRef} with {@link GBaseChatModelConfig} entries.
     */
    @GetMapping(value = "getRuntimeConfiguredChatModelsLookup", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GLookupEntryRef<GBaseChatModelConfig>> getRuntimeConfiguredChatModelsLookup(
            @RequestParam(name = "modelTypeCode", required = false) String modelTypeCode) {
        List<GBaseChatModelConfig> configs = null;
        if (modelTypeCode != null) {
            configs = new ArrayList<GBaseChatModelConfig>();
            List<IGConfigurableChatModel> elements = this.runtimeDao.findListByPredicate(x -> {
                return x.getType().getCode().equals(modelTypeCode);
            });
            if (elements != null) {
                configs = new ArrayList(elements.stream().map(x -> {
                    return (x.getConfig());
                }).toList());
            }

        } else {
            configs = (new ArrayList(this.runtimeDao.getConfigurations().stream().map(x -> {
                return (x.getConfig());
            }).toList()));
        }
        configs = securityService.filterAccessible(configs, true);
        return configs.stream().map(GLookupEntryRef::of).toList();
    }

    /**
     * Retrieves the default chat model configuration.
     *
     * @return a {@link GLookupEntryRef} with the default {@link GBaseChatModelConfig}, or null if no default is set.
     */
    @GetMapping(value = "getDefaultChatModel", produces = MediaType.APPLICATION_JSON_VALUE)
    public GLookupEntryRef<GBaseChatModelConfig> getDefaultChatModel() {
        IGConfigurableChatModel handler = runtimeDao.defaultHandler();
        return handler != null ? GLookupEntryRef.of((GBaseChatModelConfig) handler.getConfig()) : null;
    }
}