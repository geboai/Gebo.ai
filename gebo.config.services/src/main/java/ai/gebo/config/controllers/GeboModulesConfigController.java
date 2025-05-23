/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.config.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.config.model.GeboModuleInfo;
import ai.gebo.config.services.IGeboModulesConfigurationHolder;

/**
 * AI generated comments
 * Controller class for handling requests related to Gebo modules configurations.
 */
@RestController
@RequestMapping("api/users/GeboModulesConfigController")
public class GeboModulesConfigController {

    /**
     * Service holder for accessing configuration data of Gebo modules.
     */
    @Autowired
    IGeboModulesConfigurationHolder holder;

    /**
     * Default constructor.
     */
    public GeboModulesConfigController() {

    }

    /**
     * Endpoint for retrieving configuration information of a specific module.
     *
     * @param moduleId the ID of the module to retrieve information for.
     * @return the configuration information of the specified module.
     */
    @GetMapping(value = "getModuleInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public GeboModuleInfo getModuleInfo(@RequestParam("moduleId") String moduleId) {
        GeboModuleInfo info = holder.getModuleConfig(moduleId);
        return info;
    }

    /**
     * Endpoint for retrieving configuration information of all modules.
     *
     * @return a list of configuration information for all available modules.
     */
    @GetMapping(value = "getAllModules", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GeboModuleInfo> getAllModules() {
        // Converts the map of module configurations to a list
        return new ArrayList<GeboModuleInfo>(holder.getFullConfiguration().values());
    }
}