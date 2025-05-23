/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * AI generated comments
 * 
 * Service class responsible for managing file system operations and providing 
 * information about endpoint usage related to file system shares.
 */
package ai.gebo.filesystem.content.handler.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.IGReadableContentsFormatHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.multithreading.IGEntityProcessingRunnableFactoryRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.scheduling.services.IGSchedulingTimeService;
import ai.gebo.filesystem.content.handler.GFileSystemShareReference;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.filesystem.content.handler.IGFileSystemShareReferenceRuntimeDao;
import ai.gebo.filesystem.content.handler.impl.GFilesystemChangesHandlingService;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.virtualfs.VFilesystemReference;

@Service
public class FileSystemsManagementService {
    /** Persistence manager for database operations */
    @Autowired
    IGPersistentObjectManager persistentObjectManager;
    
    /** DAO for file system share reference operations */
    @Autowired
    IGFileSystemShareReferenceRuntimeDao fsShareDao;

    /**
     * Default constructor for FileSystemsManagementService
     */
    public FileSystemsManagementService() {

    }

    /**
     * Determines how many project endpoints are using a specific file system share.
     * 
     * @param shareCode The unique code identifying the file system share
     * @return The count of endpoints using this share, or -1 if the share doesn't exist
     * @throws GeboPersistenceException If there's an error accessing the persistence layer
     */
    public Long getUsedEndpointsCount(String shareCode) throws GeboPersistenceException {
        GFileSystemShareReference entry = fsShareDao.findByCode(shareCode);
        if (entry == null || entry.getReference() == null)
            return -1l;
        Long howMany = 0l;
        final Path entryPath = entry.getReference().toPath();
        List<GFilesystemProjectEndpoint> endpoints = persistentObjectManager.findAll(GFilesystemProjectEndpoint.class);
        if (endpoints != null) {
            howMany = endpoints.stream().filter(x -> {
                boolean matching = false;
                for (VFilesystemReference _entry : x.getPath()) {
                    // Convert each endpoint path reference to a Path object for comparison
                    Path thisPath = Path
                            .of(_entry.path != null ? _entry.path.absolutePath : _entry.root.getAbsolutePath());
                    matching = matching || thisPath.startsWith(entryPath);
                }

                return matching;

            }).count();
        }
        return howMany;
    }

}