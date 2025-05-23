/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandler;
import ai.gebo.knlowledgebase.model.systems.GBuildSystem;
import ai.gebo.knlowledgebase.model.systems.GBuildSystemType;

/**
 * Controller for managing build systems in the application.
 * This controller provides endpoints to fetch build system types and configurations.
 * AI generated comments
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/BuildSystemsController")
public class BuildSystemsController {

    // List of handlers for different build systems, injected by Spring if available
    @Autowired(required = false)
    List<IGBuildSystemHandler> handlers;

    /**
     * Default constructor for the BuildSystemsController class.
     * Initializes the controller without any specific setup.
     */
    public BuildSystemsController() {

    }

    /**
     * Retrieves a list of available build system types.
     * 
     * @return a list of GBuildSystemType representing the types of build systems handled by the application.
     */
    @GetMapping(value = "getBuildSystemTypes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GBuildSystemType> getBuildSystemTypes() {
        List<GBuildSystemType> types = new ArrayList<GBuildSystemType>();
        if (handlers != null) {
            for (IGBuildSystemHandler handler : handlers) {
                types.add(handler.getHandledSystemType());
            }
        }
        return types;
    }

    /**
     * Retrieves the configurations for a specific build system type, identified by its code.
     * 
     * @param buildSystemTypeCode the code of the build system type to retrieve configurations for.
     * @return a list of GBuildSystem representing the configurations of the specified build system type.
     */
    @GetMapping(value = "getBuildSystemConfigs", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GBuildSystem> getBuildSystemConfigs(@RequestParam("buildSystemTypeCode") String buildSystemTypeCode) {
        List<GBuildSystem> types = new ArrayList<GBuildSystem>();
        if (handlers != null) {
            for (IGBuildSystemHandler handler : handlers) {
                // Check if the handler's system type matches the requested type code
                if (handler.getHandledSystemType().getCode().equals(buildSystemTypeCode)) {
                    return handler.getConfigurations();
                }
            }
        }
        return types;
    }

}