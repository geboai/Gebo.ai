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
 * Exports constants representing different module identifiers used throughout the application.
 * These constants are used to identify various modules that can be plugged into the system.
 */
import { ConfluenceSystemsControllerService, FileSystemsControllerService, FileUploadsControllerService, GConfluenceProjectEndpoint, GFilesystemProjectEndpoint, GGitProjectEndpoint, GitSystemsControllerService, GJiraProjectEndpoint, GoogleDriveSystemsControllerService, GProject, GSharepointProjectEndpoint, GUploadsProjectEndpoint, JiraSystemsControllerService, SharepointSystemsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { GEBO_AI_PLUGGABLE_MODULE_UI_CONFIG, GeboActionType, GeboAIEntitiesSettingWizardConfiguration, GeboAIModulesModule, GeboAIPluggableProjectEndpointModule, GeboAIPluggableProjectEndpointModuleService, GeboUIActionRequest } from "@Gebo.ai/reusable-ui";
import { Observable } from "rxjs";
import { Injectable, NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

export const SHARED_FILESYSTEM_MODULE = "shared-filesystem-module";
export const UPLOADS_MODULE = "uploads-module";
export const GIT_MODULE = "git-module";
export const GOOGLEDRIVE_MODULE = "google-drive-module";
export const CONFLUENCE_MODULE = "confluence-module";
export const JIRA_MODULE = "jira-module";
export const SHAREPOINT_MODULE = "sharepoint-module";

/**
 * Injectable service responsible for managing shared filesystem project endpoints.
 * Implements GeboAIPluggableProjectEndpointModuleService interface to enable pluggable
 * module functionality for shared filesystem data sources.
 * This service provides methods to find existing endpoints for a project and create new endpoints.
 */
@Injectable({
    providedIn: "root"
})
export class GeboAISharedFilesystemModuleProjectEndpointService implements GeboAIPluggableProjectEndpointModuleService {
    constructor(private sharedFilesystemService: FileSystemsControllerService) {

    }
    
    /**
     * Fetches all filesystem endpoints for a given project by its code.
     * @param code - The project code to search endpoints for
     * @returns Observable containing array of endpoints with code, description and parent project code
     */
    findByProjectEndpoints(code: string): Observable<{
        code?: string; description?: string; parentProjectCode?: string;
    }[]> {
        return this.sharedFilesystemService.findFileSystemEndpointsByProject(code);
    }
    
    /**
     * Creates a UI action request for creating a new filesystem endpoint for a project.
     * This method supports wizard-style creation flows by handling step configurations.
     * @param project - The project to create the endpoint for
     * @param wizardStepsConfigurations - Optional configuration for multi-step wizards
     * @param actualWizardStepConfigrationId - Optional ID of the current wizard step
     * @returns GeboUIActionRequest configured for creating a new filesystem endpoint
     */
    byProjectCreateAction(project: GProject, wizardStepsConfigurations?: GeboAIEntitiesSettingWizardConfiguration[], actualWizardStepConfigrationId?: string): GeboUIActionRequest {
        let nextStepsParams: any = undefined;
        let outAction: GeboUIActionRequest;
        if (wizardStepsConfigurations && actualWizardStepConfigrationId) {
            nextStepsParams = {
                wizardStepsConfigurations: wizardStepsConfigurations,
                actualWizardStepConfigrationId: actualWizardStepConfigrationId
            };
        }

        const target: GFilesystemProjectEndpoint = {
            parentProjectCode: project.code
        };
        outAction = {
            actionType: GeboActionType.NEW,
            context: project,
            contextType: "GProject",
            target: target,
            targetType: "GFilesystemProjectEndpoint",
            targetFormInputs: nextStepsParams
        };

        return outAction;
    }


}

/**
 * Injectable service responsible for managing Git repository project endpoints.
 * Implements the GeboAIPluggableProjectEndpointModuleService interface to provide 
 * specific functionality for Git repository data sources within projects.
 */
@Injectable({
    providedIn: "root"
})
export class GeboAIGitModuleProjectEndpointService implements GeboAIPluggableProjectEndpointModuleService {
    constructor(private sharedFilesystemService: GitSystemsControllerService) {

    }
    
    /**
     * Retrieves all Git endpoints associated with a specific project.
     * @param code - The project code to search endpoints for
     * @returns Observable containing array of Git endpoints with their details
     */
    findByProjectEndpoints(code: string): Observable<{
        code?: string; description?: string; parentProjectCode?: string;
    }[]> {
        return this.sharedFilesystemService.findGitEndpointsByProject(code);
    }
    
    /**
     * Creates a UI action request for creating a new Git repository endpoint.
     * Supports wizard-based configuration flow if wizard parameters are provided.
     * @param project - The project to create the Git endpoint for
     * @param wizardStepsConfigurations - Optional wizard steps configuration
     * @param actualWizardStepConfigrationId - Optional current step ID in the wizard
     * @returns GeboUIActionRequest configured for creating a new Git endpoint
     */
    byProjectCreateAction(project: GProject, wizardStepsConfigurations?: GeboAIEntitiesSettingWizardConfiguration[], actualWizardStepConfigrationId?: string): GeboUIActionRequest {
        let nextStepsParams: any = undefined;
        let outAction: GeboUIActionRequest;
        if (wizardStepsConfigurations && actualWizardStepConfigrationId) {
            nextStepsParams = {
                wizardStepsConfigurations: wizardStepsConfigurations,
                actualWizardStepConfigrationId: actualWizardStepConfigrationId
            };
        }

        const target: GGitProjectEndpoint = {
            parentProjectCode: project.code
        };
        outAction = {
            actionType: GeboActionType.NEW,
            context: project,
            contextType: "GProject",
            target: target,
            targetType: "GGitProjectEndpoint",
            targetFormInputs: nextStepsParams
        };

        return outAction;
    }


}

/**
 * Injectable service managing file upload project endpoints.
 * Implements GeboAIPluggableProjectEndpointModuleService to provide functionality
 * for handling file uploads as data sources within projects.
 */
@Injectable({
    providedIn: "root"
})
export class GeboAIUpladsModuleProjectEndpointService implements GeboAIPluggableProjectEndpointModuleService {
    constructor(private uploadsService: FileUploadsControllerService) {

    }
    
    /**
     * Retrieves all upload endpoints associated with a specific project.
     * @param code - The project code to search for endpoints
     * @returns Observable containing array of upload endpoints
     */
    findByProjectEndpoints(code: string): Observable<{
        code?: string; description?: string; parentProjectCode?: string;
    }[]> {
        return this.uploadsService.findUploadsEndpointsByProject(code);
    }
    
    /**
     * Creates an action request for adding a new file upload endpoint to a project.
     * @param project - The project to create the upload endpoint for
     * @param wizardStepsConfigurations - Optional wizard configuration steps
     * @param actualWizardStepConfigrationId - Optional current wizard step ID
     * @returns GeboUIActionRequest configured for creating a new uploads endpoint
     */
    byProjectCreateAction(project: GProject, wizardStepsConfigurations?: GeboAIEntitiesSettingWizardConfiguration[], actualWizardStepConfigrationId?: string): GeboUIActionRequest {
        let nextStepsParams: any = undefined;
        let outAction: GeboUIActionRequest;
        if (wizardStepsConfigurations && actualWizardStepConfigrationId) {
            nextStepsParams = {
                wizardStepsConfigurations: wizardStepsConfigurations,
                actualWizardStepConfigrationId: actualWizardStepConfigrationId
            };
        }

        const target: GUploadsProjectEndpoint = {
            parentProjectCode: project.code
        };
        outAction = {
            actionType: GeboActionType.NEW,
            context: project,
            contextType: "GProject",
            target: target,
            targetType: "GUploadsProjectEndpoint",
            targetFormInputs: nextStepsParams
        };

        return outAction;
    }


}

/**
 * Injectable service responsible for managing SharePoint project endpoints.
 * Implements GeboAIPluggableProjectEndpointModuleService to provide functionality
 * for connecting to Microsoft SharePoint/OneDrive data sources.
 */
@Injectable({
    providedIn: "root"
})
export class GeboAISharepointModuleProjectEndpointService implements GeboAIPluggableProjectEndpointModuleService {
    constructor(private sharepointService: SharepointSystemsControllerService) {

    }
    
    /**
     * Retrieves all SharePoint endpoints associated with a specific project.
     * @param code - The project code to search for endpoints
     * @returns Observable containing array of SharePoint endpoints
     */
    findByProjectEndpoints(code: string): Observable<{
        code?: string; description?: string; parentProjectCode?: string;
    }[]> {
        return this.sharepointService.findSharepointEndpointsByProject(code);
    }
    
    /**
     * Creates an action request for adding a new SharePoint endpoint to a project.
     * @param project - The project to create the SharePoint endpoint for
     * @param wizardStepsConfigurations - Optional wizard configuration steps
     * @param actualWizardStepConfigrationId - Optional current wizard step ID
     * @returns GeboUIActionRequest configured for creating a new SharePoint endpoint
     */
    byProjectCreateAction(project: GProject, wizardStepsConfigurations?: GeboAIEntitiesSettingWizardConfiguration[], actualWizardStepConfigrationId?: string): GeboUIActionRequest {
        let nextStepsParams: any = undefined;
        let outAction: GeboUIActionRequest;
        if (wizardStepsConfigurations && actualWizardStepConfigrationId) {
            nextStepsParams = {
                wizardStepsConfigurations: wizardStepsConfigurations,
                actualWizardStepConfigrationId: actualWizardStepConfigrationId
            };
        }

        const target: GSharepointProjectEndpoint = {
            parentProjectCode: project.code
        };
        outAction = {
            actionType: GeboActionType.NEW,
            context: project,
            contextType: "GProject",
            target: target,
            targetType: "GSharepointProjectEndpoint",
            targetFormInputs: nextStepsParams
        };

        return outAction;
    }


}

/**
 * Injectable service responsible for managing Atlassian Confluence project endpoints.
 * Implements GeboAIPluggableProjectEndpointModuleService to provide functionality
 * for connecting to Confluence data sources.
 */
@Injectable({
    providedIn: "root"
})
export class GeboAIConfluenceModuleProjectEndpointService implements GeboAIPluggableProjectEndpointModuleService {
    constructor(private sharedFilesystemService: ConfluenceSystemsControllerService) {

    }
    
    /**
     * Retrieves all Confluence endpoints associated with a specific project.
     * @param code - The project code to search for endpoints
     * @returns Observable containing array of Confluence endpoints
     */
    findByProjectEndpoints(code: string): Observable<{
        code?: string; description?: string; parentProjectCode?: string;
    }[]> {
        return this.sharedFilesystemService.findConfluenceEndpointsByProject(code);
    }
    
    /**
     * Creates an action request for adding a new Confluence endpoint to a project.
     * @param project - The project to create the Confluence endpoint for
     * @param wizardStepsConfigurations - Optional wizard configuration steps
     * @param actualWizardStepConfigrationId - Optional current wizard step ID
     * @returns GeboUIActionRequest configured for creating a new Confluence endpoint
     */
    byProjectCreateAction(project: GProject, wizardStepsConfigurations?: GeboAIEntitiesSettingWizardConfiguration[], actualWizardStepConfigrationId?: string): GeboUIActionRequest {
        let nextStepsParams: any = undefined;
        let outAction: GeboUIActionRequest;
        if (wizardStepsConfigurations && actualWizardStepConfigrationId) {
            nextStepsParams = {
                wizardStepsConfigurations: wizardStepsConfigurations,
                actualWizardStepConfigrationId: actualWizardStepConfigrationId
            };
        }

        const target: GConfluenceProjectEndpoint = {
            parentProjectCode: project.code
        };
        outAction = {
            actionType: GeboActionType.NEW,
            context: project,
            contextType: "GProject",
            target: target,
            targetType: "GConfluenceProjectEndpoint",
            targetFormInputs: nextStepsParams
        };

        return outAction;
    }


}

/**
 * Injectable service responsible for managing Atlassian Jira project endpoints.
 * Implements GeboAIPluggableProjectEndpointModuleService to provide functionality
 * for connecting to Jira data sources.
 */
@Injectable({
    providedIn: "root"
})
export class GeboAIJiraModuleProjectEndpointService implements GeboAIPluggableProjectEndpointModuleService {
    constructor(private jiraService: JiraSystemsControllerService) {

    }
    
    /**
     * Retrieves all Jira endpoints associated with a specific project.
     * @param code - The project code to search for endpoints
     * @returns Observable containing array of Jira endpoints
     */
    findByProjectEndpoints(code: string): Observable<{
        code?: string; description?: string; parentProjectCode?: string;
    }[]> {
        return this.jiraService.findJiraEndpointsByProject(code);
    }
    
    /**
     * Creates an action request for adding a new Jira endpoint to a project.
     * @param project - The project to create the Jira endpoint for
     * @param wizardStepsConfigurations - Optional wizard configuration steps
     * @param actualWizardStepConfigrationId - Optional current wizard step ID
     * @returns GeboUIActionRequest configured for creating a new Jira endpoint
     */
    byProjectCreateAction(project: GProject, wizardStepsConfigurations?: GeboAIEntitiesSettingWizardConfiguration[], actualWizardStepConfigrationId?: string): GeboUIActionRequest {
        let nextStepsParams: any = undefined;
        let outAction: GeboUIActionRequest;
        if (wizardStepsConfigurations && actualWizardStepConfigrationId) {
            nextStepsParams = {
                wizardStepsConfigurations: wizardStepsConfigurations,
                actualWizardStepConfigrationId: actualWizardStepConfigrationId
            };
        }

        const target: GJiraProjectEndpoint = {
            parentProjectCode: project.code
        };
        outAction = {
            actionType: GeboActionType.NEW,
            context: project,
            contextType: "GProject",
            target: target,
            targetType: "GJiraProjectEndpoint",
            targetFormInputs: nextStepsParams
        };

        return outAction;
    }


}

/**
 * Injectable service responsible for managing Google Drive project endpoints.
 * Implements GeboAIPluggableProjectEndpointModuleService to provide functionality
 * for connecting to Google Drive data sources.
 */
@Injectable({
    providedIn: "root"
})
export class GeboAIGoogleDriveModuleProjectEndpointService implements GeboAIPluggableProjectEndpointModuleService {
    constructor(private jiraService: GoogleDriveSystemsControllerService) {

    }
    
    /**
     * Retrieves all Google Drive endpoints associated with a specific project.
     * @param code - The project code to search for endpoints
     * @returns Observable containing array of Google Drive endpoints
     */
    findByProjectEndpoints(code: string): Observable<{
        code?: string; description?: string; parentProjectCode?: string;
    }[]> {
        return this.jiraService.findGoogleDriveEndpointsByProject(code);
    }
    
    /**
     * Creates an action request for adding a new Google Drive endpoint to a project.
     * @param project - The project to create the Google Drive endpoint for
     * @param wizardStepsConfigurations - Optional wizard configuration steps
     * @param actualWizardStepConfigrationId - Optional current wizard step ID
     * @returns GeboUIActionRequest configured for creating a new Google Drive endpoint
     */
    byProjectCreateAction(project: GProject, wizardStepsConfigurations?: GeboAIEntitiesSettingWizardConfiguration[], actualWizardStepConfigrationId?: string): GeboUIActionRequest {
        let nextStepsParams: any = undefined;
        let outAction: GeboUIActionRequest;
        if (wizardStepsConfigurations && actualWizardStepConfigrationId) {
            nextStepsParams = {
                wizardStepsConfigurations: wizardStepsConfigurations,
                actualWizardStepConfigrationId: actualWizardStepConfigrationId
            };
        }

        const target: any = {
            parentProjectCode: project.code
        };
        outAction = {
            actionType: GeboActionType.NEW,
            context: project,
            contextType: "GProject",
            target: target,
            targetType: "GGoogleDriveProjectEndpoint",
            targetFormInputs: nextStepsParams
        };

        return outAction;
    }


}

/**
 * Configuration object for the shared filesystem module.
 * Defines UI elements, labels, icons and service to be used for this module.
 */
const sharedFilesystemModuleConfigurationBlock: GeboAIPluggableProjectEndpointModule = {
    moduleId: SHARED_FILESYSTEM_MODULE,
    addProjectEndpointicon: "pi pi-folder",
    addProjectEndpointLabel: "+filesystem",
    addProjectEndpointTitle: "add shared filesystem data source",
    projecteEndpointClassName: "ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint",
    service: GeboAISharedFilesystemModuleProjectEndpointService
};

/**
 * Configuration object for the Git module.
 * Defines UI elements, labels, icons and service to be used for Git repository connections.
 */
const gitModuleConfigurationBlock: GeboAIPluggableProjectEndpointModule = {
    moduleId: GIT_MODULE,
    addProjectEndpointicon: "pi pi-git",
    addProjectEndpointLabel: "+git repo",
    addProjectEndpointTitle: "add a git repository data source like bitbucket,github or others",
    projecteEndpointClassName: "ai.gebo.git.content.handler.GGitProjectEndpoint",
    service: GeboAIGitModuleProjectEndpointService
};

/**
 * Configuration object for the uploads module.
 * Defines UI elements, labels, icons and service to be used for file uploads.
 */
const uploadsModuleConfigurationBlock: GeboAIPluggableProjectEndpointModule = {
    moduleId: UPLOADS_MODULE,
    addProjectEndpointicon: "pi pi-cloud-upload",
    addProjectEndpointLabel: "+upload files",
    addProjectEndpointTitle: "add uploaded files data source",
    projecteEndpointClassName: "ai.gebo.uploads.content.handler.GUploadsProjectEndpoint",
    service: GeboAIUpladsModuleProjectEndpointService
};

/**
 * Configuration object for the SharePoint module.
 * Defines UI elements, labels, icons and service to be used for Microsoft SharePoint connections.
 */
const sharepointModuleConfigurationBlock: GeboAIPluggableProjectEndpointModule = {
    moduleId: SHAREPOINT_MODULE,
    addProjectEndpointicon: "pi pi-sharepoint",
    addProjectEndpointLabel: "+sharePoint/oneDrive",
    addProjectEndpointTitle: "add a Microsoft SharePoint/OneDrive data source",
    projecteEndpointClassName: "ai.gebo.sharepoint.handler.GSharepointProjectEndpoint",
    service: GeboAISharepointModuleProjectEndpointService
};

/**
 * Configuration object for the Confluence module.
 * Defines UI elements, labels, icons and service to be used for Atlassian Confluence connections.
 */
const confluenceModuleConfigurationBlock: GeboAIPluggableProjectEndpointModule = {
    moduleId: CONFLUENCE_MODULE,
    addProjectEndpointicon: "pi pi-confluence",
    addProjectEndpointLabel: "+confluence",
    addProjectEndpointTitle: "add an Atlassian Confluence data source",
    projecteEndpointClassName: "ai.gebo.atlassian.confluence.handler.GConfluenceProjectEndpoint",
    service: GeboAIConfluenceModuleProjectEndpointService
};

/**
 * Configuration object for the Jira module.
 * Defines UI elements, labels, icons and service to be used for Atlassian Jira connections.
 */
const jiraModuleConfigurationBlock: GeboAIPluggableProjectEndpointModule = {
    moduleId: JIRA_MODULE,
    addProjectEndpointicon: "pi pi-jira",
    addProjectEndpointLabel: "+jira",
    addProjectEndpointTitle: "add an Atlassian Jira data source",
    projecteEndpointClassName: "ai.gebo.atlassian.jira.handler.GJiraProjectEndpoint",
    service: GeboAIJiraModuleProjectEndpointService
};

/**
 * Configuration object for the Google Drive module.
 * Defines UI elements, labels, icons and service to be used for Google Drive connections.
 */
const googleDriveModuleConfigurationBlock: GeboAIPluggableProjectEndpointModule = {
    moduleId: GOOGLEDRIVE_MODULE,
    addProjectEndpointicon: "pi pi-google",
    addProjectEndpointLabel: "+google drive",
    addProjectEndpointTitle: "add a google drive data source",
    projecteEndpointClassName: "ai.gebo.googledrive.handlers.GGoogleDriveProjectEndpoint",
    service: GeboAIGoogleDriveModuleProjectEndpointService
};

/**
 * NgModule that handles the registration of all module services and configurations.
 * This module is responsible for injecting all the pluggable modules into the application
 * by providing their services and configuration blocks to the dependency injection system.
 */
@NgModule({
    imports: [CommonModule, GeboAIModulesModule],
    providers: [
        GeboAISharedFilesystemModuleProjectEndpointService,
        GeboAIGitModuleProjectEndpointService,
        GeboAIUpladsModuleProjectEndpointService,
        GeboAISharepointModuleProjectEndpointService,
        GeboAIConfluenceModuleProjectEndpointService,
        GeboAIJiraModuleProjectEndpointService,
        GeboAIGoogleDriveModuleProjectEndpointService,
        { provide: GEBO_AI_PLUGGABLE_MODULE_UI_CONFIG, useValue: sharedFilesystemModuleConfigurationBlock, multi: true },
        { provide: GEBO_AI_PLUGGABLE_MODULE_UI_CONFIG, useValue: gitModuleConfigurationBlock, multi: true },
        { provide: GEBO_AI_PLUGGABLE_MODULE_UI_CONFIG, useValue: uploadsModuleConfigurationBlock, multi: true },
        { provide: GEBO_AI_PLUGGABLE_MODULE_UI_CONFIG, useValue: sharepointModuleConfigurationBlock, multi: true },
        { provide: GEBO_AI_PLUGGABLE_MODULE_UI_CONFIG, useValue: confluenceModuleConfigurationBlock, multi: true },
        { provide: GEBO_AI_PLUGGABLE_MODULE_UI_CONFIG, useValue: jiraModuleConfigurationBlock, multi: true },
        { provide: GEBO_AI_PLUGGABLE_MODULE_UI_CONFIG, useValue: googleDriveModuleConfigurationBlock, multi: true }]
})
export class GeboAICommonModulesInjectionsModule {

}