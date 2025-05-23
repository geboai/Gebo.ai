/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.fastsetup.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.config.repositories.MongoGeboInstallationRepository;
import ai.gebo.config.GeboConfig;
import ai.gebo.config.model.MongoGeboInstallation;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.fastsetup.model.ComponentEnabledStatus;
import ai.gebo.fastsetup.model.FastWorkDirectorySetupData;
import ai.gebo.fastsetup.model.WorkFolderSetupStatus;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.VFilesystemReference;
import jakarta.validation.Valid;

/**
 * Service for managing the setup of the Gebo working directories.
 * Handles work directory configuration and status checks.
 * 
 * AI generated comments
 */
@Service
public class GeboFastWorkFolderSetupService {

    // Repository for accessing MongoGeboInstallation data
    @Autowired
    MongoGeboInstallationRepository mongoGeboInstallationRepository;

    // Service for accessing Gebo configuration
    @Autowired
    IGGeboConfigService configService;

    /**
     * Constructor for GeboFastWorkFolderSetupService.
     */
    public GeboFastWorkFolderSetupService() {

    }

    /**
     * Checks if the work directory setup configuration is enabled in GeboConfig.
     * 
     * @return ComponentEnabledStatus indicating if work directory setup
     *         configuration is enabled.
     */
    public ComponentEnabledStatus getWorkDirectorySetupEnabled() {
        GeboConfig geboConfig = configService.getGeboConfig();
        ComponentEnabledStatus status = new ComponentEnabledStatus();
        if (geboConfig.getSetupConfiguresWorkdir() != null && geboConfig.getSetupConfiguresWorkdir()) {
            status.isEnabled = true;
        } else {
            status.isEnabled = false;
        }
        return status;
    }

    /**
     * Retrieves the setup status of the work directory.
     * 
     * @return WorkFolderSetupStatus object containing the setup status of the
     *         work directory.
     */
    public WorkFolderSetupStatus getWorkDirectorySetupStatus() {
        WorkFolderSetupStatus status = new WorkFolderSetupStatus();
        Optional<MongoGeboInstallation> geboInstallationOpt = mongoGeboInstallationRepository
                .findById(MongoGeboInstallation.ENTRY);
        GeboConfig geboConfig = configService.getGeboConfig();
        MongoGeboInstallation geboInstallation = geboInstallationOpt.isPresent() ? geboInstallationOpt.get() : null;
        if (geboConfig.getSetupConfiguresWorkdir() != null && geboConfig.getSetupConfiguresWorkdir()) {
            status.isSetup = geboInstallation != null && geboInstallation.getGeboWorkdirectoryPath() != null
                    && geboInstallation.getGeboWorkdirectoryPath().trim().length() > 0;
            status.workDirectory = geboInstallation != null ? geboInstallation.getWorkDirectoryInternalValue() : null;
            if (status.workDirectory == null && geboInstallation != null
                    && geboInstallation.getGeboWorkdirectoryPath() != null) {
                VFilesystemReference reference = VFilesystemReference
                        .from(Path.of(geboInstallation.getGeboWorkdirectoryPath()));
                status.workDirectory = reference;
            }
        } else {
            status.isSetup = configService.isGeboWorkDirectorySet();
            if (status.isSetup) {
                String workdir = configService.getGeboWorkDirectory();
                VFilesystemReference reference = VFilesystemReference.from(Path.of(workdir));
                status.workDirectory = reference;
            }
        }
        return status;

    }

    /**
     * Configures the work directory based on the provided FastWorkDirectorySetupData.
     * Checks for errors such as path validity, existence, writability, and emptiness.
     * Updates MongoGeboInstallation with the work directory path if successful.
     * 
     * @param data The setup data for configuring the work directory.
     * @return OperationStatus containing the WorkFolderSetupStatus and any
     *         messages regarding errors or success.
     */
    public OperationStatus<WorkFolderSetupStatus> configureWorkDirectory(@Valid FastWorkDirectorySetupData data) {
        if (configService.isGeboWorkDirectorySet())
            throw new RuntimeException("Work directory already set");
        Optional<MongoGeboInstallation> geboInstallationOpt = mongoGeboInstallationRepository
                .findById(MongoGeboInstallation.ENTRY);
        MongoGeboInstallation geboInstallation = geboInstallationOpt.isPresent() ? geboInstallationOpt.get() : null;
        if (geboInstallation == null) {
            geboInstallation = new MongoGeboInstallation();
            geboInstallation.setId(MongoGeboInstallation.ENTRY);
        }
        WorkFolderSetupStatus wf = new WorkFolderSetupStatus();
        wf.workDirectory = data.getWorkDirectory();
        OperationStatus<WorkFolderSetupStatus> status = OperationStatus.of(wf);
        List<GUserMessage> messages = new ArrayList<GUserMessage>();
        Path path = data.getWorkDirectory().toPath();
        boolean hasErrors = false;
        
        // Validate path and check for errors
        if (path == null) {
            hasErrors = true;
            messages.add(GUserMessage.errorMessage("Invalid work folder set",
                    "The configured work folder is not a valid filesystem path"));
        } else {
            if (!Files.exists(path)) {
                hasErrors = true;
                messages.add(GUserMessage.errorMessage("Invalid work folder set",
                        "The configured work folder:" + path.toString() + " does not exist"));
            }
            if (!Files.isDirectory(path)) {
                hasErrors = true;
                messages.add(GUserMessage.errorMessage("Invalid work folder set",
                        "The configured work folder:" + path.toString() + " is not a valid directory"));
            }
            if (!Files.isWritable(path)) {
                hasErrors = true;
                messages.add(GUserMessage.errorMessage("Invalid work folder set", "The configured work folder:"
                        + path.toString() + " is not writeable, so it's useles for this application"));
            }
            if (!hasErrors) {
                try {
                    Stream<Path> contents = Files.list(path);
                    List<Path> items = contents.toList();
                    if (!items.isEmpty()) {
                        hasErrors = true;
                        messages.add(GUserMessage.errorMessage("Invalid work folder set", "The configured work folder:"
                                + path.toString() + " must be empty, but there are " + items.size() + " files in it"));
                    }
                } catch (IOException e) {
                    hasErrors = true;
                    messages.add(GUserMessage.errorMessage("Invalid work folder set", "The configured work folder:"
                            + path.toString()
                            + " is not correctly accessible, it must be readable and writeable from this software."));
                } finally {
                }
            }
        }
        
        // Save work directory configuration if no errors found
        if (!hasErrors) {
            geboInstallation.setGeboWorkdirectoryPath(data.getWorkDirectory().path.absolutePath);
            geboInstallation.setWorkDirectoryInternalValue(data.getWorkDirectory());
            mongoGeboInstallationRepository.save(geboInstallation);
            wf.isSetup = true;
        } else {
            status.getMessages().clear();
            status.setMessages(messages);
        }
        return status;
    }
}