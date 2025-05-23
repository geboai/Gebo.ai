/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer.impl;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.knlowledgebase.model.contents.GLocalEndpointMirror;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;
import ai.gebo.knowledgebase.repositories.LocalEndpointMirrorRepository;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

/**
 * AI generated comments
 * Service implementation for discovering or creating local persistent folders
 * related to project endpoints.
 */
@Service
public class GLocalPersistentFolderDiscoveryServiceImpl implements IGLocalPersistentFolderDiscoveryService {
    
    /** Repository for accessing local endpoint mirror data. */
    @Autowired
    LocalEndpointMirrorRepository repository;
    
    /** Configuration service for accessing Gebo configuration settings. */
    @Autowired
    IGGeboConfigService geboConfig;

    /**
     * Default constructor.
     */
    public GLocalPersistentFolderDiscoveryServiceImpl() {

    }

    /**
     * Retrieves the local persistent folder for a given content management system and project endpoint.
     * If the folder does not exist, it creates both a database entry and the folder.
     *
     * @param contentSystem the content management system associated with the folder.
     * @param projectEndpoint the project endpoint for which the folder is retrieved.
     * @return the absolute path to the local persistent folder.
     * @throws GeboContentHandlerSystemException if the working directory is invalid.
     */
    @Override
    public String getLocalPersistentFolder(GContentManagementSystem contentSystem, GProjectEndpoint projectEndpoint)
            throws GeboContentHandlerSystemException {
        
        // Retrieve the working directory path from the configuration.
        String workingDirectory = geboConfig.getGeboWorkDirectory();

        // Validate if the working directory exists and is indeed a directory.
        if (!new File(workingDirectory).exists() || !new File(workingDirectory).isDirectory())
            throw new GeboContentHandlerSystemException("The configurated work directory " + geboConfig.getGeboWorkDirectory()
                    + " does not exists or is not a directory");
        
        // Create a unique folder code based on the project endpoint class and code.
        String uniqueConfiguredFolderCode = projectEndpoint.getClass().getName() + "[" + projectEndpoint.getCode() + "]";
        
        // Find an existing local endpoint mirror or create a new one if not found.
        Optional<GLocalEndpointMirror> entry = repository.findById(uniqueConfiguredFolderCode);
        GLocalEndpointMirror configuredEndpointMirror = null;
        if (entry.isEmpty()) {
            configuredEndpointMirror = new GLocalEndpointMirror();
            configuredEndpointMirror.setCode(uniqueConfiguredFolderCode);
            configuredEndpointMirror.setDescription("Deployment folder for endpoint of type "
                    + projectEndpoint.getClass().getSimpleName() + " with code " + projectEndpoint.getCode());
            configuredEndpointMirror.setEndPointCode(projectEndpoint.getCode());
            configuredEndpointMirror.setSystemCode(contentSystem.getCode());
            configuredEndpointMirror.setLocalGeboAIWorkDirectoryRelativeMirrorFolder(
                    contentSystem.getContentManagementSystemType() + "/" + projectEndpoint.getCode());
            repository.insert(configuredEndpointMirror); // Insert new configuration into the repository.
        } else {
            configuredEndpointMirror = entry.get(); // Retrieve existing configuration.
        }

        // Construct the path to the persistent folder.
        Path path = Paths.get(workingDirectory,
                configuredEndpointMirror.getLocalGeboAIWorkDirectoryRelativeMirrorFolder());
        String folder = path.toAbsolutePath().toString();

        // Create the folder if it does not exist.
        if (!new File(folder).exists()) {
            new File(folder).mkdirs();
        }
        
        return folder; // Return the absolute path of the persistent folder.
    }

}