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
 * This module defines the configuration for a wizard interface related to userspace uploads.
 * It provides a step-by-step workflow for creating and managing userspace knowledge bases,
 * folders, and file uploads within the Gebo.ai application.
 */

import { UserspaceFolderDto, UserspaceKnowledgebaseDto } from "@Gebo.ai/gebo-ai-rest-api";
import { GeboActionType, GeboAIEntitiesSettingWizardConfiguration, GeboUIActionRequest } from "@Gebo.ai/reusable-ui";
import { of } from "rxjs";
import { UserspaceFilesUploadModel } from "./userspace-files-upload.service";

/**
 * Configuration array for the userspace uploads wizard.
 * This defines a multi-step wizard with configurations for navigating between steps
 * and handling data transitions between the different screens of the wizard.
 */
export const USERSPACE_UPLOADS_WIZARD: GeboAIEntitiesSettingWizardConfiguration[] = [{
    id: "UserspaceKnowledgebase",
    description: "User space name setting",
    nextStepConfigurationId: "UserspaceFolder",
    nextStepLabel: "User space folder name",
    /**
     * Handles navigation to the next step from the Knowledgebase configuration.
     * Creates a new folder DTO and prepares it for the next step.
     * @param actualComponent - The current component instance
     * @param injector - Angular injector instance
     * @param actualData - The current knowledgebase data
     * @returns An observable of the action request for the next step
     */
    nextStepNavigation(actualComponent, injector, actualData) {
        const knowledgeBase: UserspaceKnowledgebaseDto = actualData;
        let code: string = "";
        if (knowledgeBase.code) {
            code = knowledgeBase.code;
        }
        const folderDto: UserspaceFolderDto = {
            description: "New folder",
            parentUserspaceKnowledgebaseCode: code
        };
        const request: GeboUIActionRequest = {
            actionType: GeboActionType.NEW,
            context: knowledgeBase,
            contextType: "UserspaceKnowledgebaseDto",
            target: folderDto,
            targetType: "UserspaceFolderDto"
        };
        return of(request);
    }
}, {
    id: "UserspaceFolder",
    description: "User space folder name",
    previusStepConfigurationId: "UserspaceKnowledgebase",
    previusStepLabel: "User space name setting",
    /**
     * Handles navigation to the previous step from the folder configuration.
     * Creates a knowledgebase DTO for returning to the parent step.
     * @param actualComponent - The current component instance
     * @param injector - Angular injector instance
     * @param actualData - The current folder data
     * @returns An observable of the action request for the previous step
     */
    previusStepNavigation(actualComponent, injector, actualData) {
        const folderDto: UserspaceFolderDto = actualData;
        const knowledgeBase: UserspaceKnowledgebaseDto = {
            description: "",
            code: folderDto.parentUserspaceKnowledgebaseCode
        };
        const request: GeboUIActionRequest = {
            actionType: GeboActionType.OPEN,
            context: folderDto,
            contextType: "UserspaceFolderDto",
            target: knowledgeBase,
            targetType: "UserspaceKnowledgebaseDto"
        };
        return of(request);
    },
    nextStepConfigurationId:"UserspaceFilesUploadModel",
    nextStepLabel:"Folder modifications",
    /**
     * Handles navigation to the next step from the folder configuration.
     * Creates a files upload model for the selected folder.
     * @param actualComponent - The current component instance
     * @param injector - Angular injector instance
     * @param actualData - The current folder data
     * @returns An observable of the action request for the next step
     */
    nextStepNavigation(actualComponent, injector, actualData) {
        const folderDto: UserspaceFolderDto = actualData;
        const model:UserspaceFilesUploadModel={
            code:folderDto.code,
            description:folderDto.description,
            userspaceFolder:folderDto,
            userspaceFiles:[]
        }
        const request: GeboUIActionRequest = {
            actionType: GeboActionType.OPEN,
            context: folderDto,
            contextType: "UserspaceFolderDto",
            target: model,
            targetType: "UserspaceFilesUploadModel"
        };
        return of(request);
    }
},{
    id:"UserspaceFilesUploadModel",
    description:"User space folder modifications",
    previusStepLabel:"User space folder name",
    previusStepConfigurationId:"UserspaceFolder",
    /**
     * Handles navigation to the previous step from the files upload model.
     * Returns to the folder configuration screen.
     * @param actualComponent - The current component instance
     * @param injector - Angular injector instance
     * @param actualData - The current files upload model
     * @returns An observable of the action request for the previous step
     */
    previusStepNavigation(actualComponent, injector, actualData) {
        const model:UserspaceFilesUploadModel=actualData;
        const folderDto: UserspaceFolderDto =model.userspaceFolder;
        const request: GeboUIActionRequest = {
            actionType: GeboActionType.OPEN,
            context: model,
            contextType: "UserspaceFilesUploadModel",
            target: folderDto,
            targetType: "UserspaceFolderDto"
        };
        return of(request);
    },
    nextStepConfigurationId:"UserspaceFilesUploadModel",
    nextStepIcon:"pi pi-microchip-ai",
    nextStepLabel:"publish!",
    /**
     * Handles the final step navigation (publishing).
     * This prepares the model for final submission or processing.
     * @param actualComponent - The current component instance
     * @param injector - Angular injector instance
     * @param actualData - The current files upload model
     * @returns An observable of the action request for finalizing the wizard
     */
    nextStepNavigation(actualComponent, injector, actualData) {
        const model:UserspaceFilesUploadModel=actualData;
        const request: GeboUIActionRequest = {
            actionType: GeboActionType.OPEN,
            context: model,
            contextType: "UserspaceFilesUploadModel",
            target: model,
            targetType: "UserspaceFilesUploadModel"
        };
        return of(request);
    }
}];