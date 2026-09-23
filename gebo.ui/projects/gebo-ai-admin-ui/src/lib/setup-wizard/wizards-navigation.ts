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
 * This file defines a configuration array for a knowledge base creation wizard in Gebo.ai.
 * It contains the step-by-step process including knowledge base creation, project creation,
 * data source selection, data source creation, and publication.
 */

import { Injector } from "@angular/core";
import { GJobStatus, GKnowledgeBase, GObjectRef, GObjectRefGProjectEndpoint, GProject, GProjectEndpoint, JobLauncherControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, ChooseDataSourceType, GeboActionType, GeboAIEntitiesSettingWizardConfiguration, GeboUIActionRequest } from "@Gebo.ai/reusable-ui";
import { map, Observable, of } from "rxjs";

/**
 * Configuration array that defines the wizard flow for creating a knowledge base.
 * Each object in the array represents a step in the wizard with navigation logic
 * between steps, including how data is passed between steps.
 */
export const KNOWLEDGEBASE_WIZARD: GeboAIEntitiesSettingWizardConfiguration[] = [{
    id: "newKnowledgeBase",
    description: "Create new knowledge base",
    nextStepConfigurationId: "newProject",
    
    /**
     * Handles navigation from knowledge base creation to project creation.
     * Takes the created knowledge base data and prepares a project with reference to it.
     * 
     * @param actualComponent The component handling the current step
     * @param injector Angular injector for service access
     * @param actualData Current knowledge base data
     * @returns Observable with action to create a new project
     */
    nextStepNavigation: (actualComponent: BaseEntityEditingComponent<any>,injector:Injector, actualData?: any) => {
        const thisStepData: GKnowledgeBase = actualData;
        const project: GProject = {
            rootKnowledgeBaseCode: thisStepData.code
        };
        const action: GeboUIActionRequest = {
            actionType: GeboActionType.NEW,
            context: actualData,
            contextType: "GKnowledgeBase",
            targetType: "GProject",
            target: project
        };
        return of(action);
    }
}, {
    id: "newProject",
    description: "Create new project",
    previusStepConfigurationId: "newKnowledgeBase",
    
    /**
     * Handles navigation back to knowledge base creation from project creation.
     * Takes the project data and retrieves the associated knowledge base.
     * 
     * @param actualComponent The component handling the current step
     * @param injector Angular injector for service access
     * @param actualData Current project data
     * @returns Observable with action to open the knowledge base
     */
    previusStepNavigation: (actualComponent: BaseEntityEditingComponent<any>,injector:Injector, actualData?: any) => {

        const project: GProject = actualData;
        const thisStepData: GKnowledgeBase = { code: project?.rootKnowledgeBaseCode };
        const action: GeboUIActionRequest = {
            actionType: GeboActionType.OPEN,
            context: project,
            contextType: "GProject",
            targetType: "GKnowledgeBase",
            target: thisStepData
        };
        return of(action);
    },
    nextStepConfigurationId: "chooseDataSourceType",
    
    /**
     * Handles navigation from project creation to data source type selection.
     * Prepares a data source type selection object with project reference.
     * 
     * @param actualComponent The component handling the current step
     * @param injector Angular injector for service access
     * @param actualData Current project data
     * @returns Observable with action to open the data source type selection
     */
    nextStepNavigation: (actualComponent: BaseEntityEditingComponent<any>,injector:Injector, actualData?: any) => {
        const project:GProject=actualData;
        const dataSourceObject: ChooseDataSourceType = {
            code:"MYCHOICESTEP",
            projectCode: project?.code
        };
        const action: GeboUIActionRequest = {
            actionType: GeboActionType.OPEN,
            context: actualData,
            contextType: "GProject",
            targetType: "ChooseDataSourceType",
            target: dataSourceObject
        };
        return of(action);
    }
}, {
    id: "chooseDataSourceType",
    description: "Choose data source type",
    previusStepConfigurationId: "newProject",
    /**
     * Handles navigation back to project creation from data source type selection.
     * Retrieves the project using the code stored in the data source type.
     * 
     * @param actualComponent The component handling the current step
     * @param injector Angular injector for service access
     * @param actualData Current data source type selection data
     * @returns Observable with action to open the project
     */
    previusStepNavigation: (actualComponent: BaseEntityEditingComponent<any>,injector:Injector, actualData?: any) => {
        const ds:ChooseDataSourceType=actualData;
        const dataSourceObject: GProject = {
            code: ds?.projectCode
        };
        const action: GeboUIActionRequest = {
            actionType: GeboActionType.OPEN,
            context: actualData,
            contextType: "ChooseDataSourceType",
            targetType: "GProject",
            target: dataSourceObject
        };
        return of(action);
    },
    nextStepConfigurationId: "newDataSource",
    /**
     * Handles navigation from data source type selection to data source creation.
     * Creates a new project endpoint with reference to the parent project.
     * 
     * @param actualComponent The component handling the current step
     * @param injector Angular injector for service access
     * @param actualData Current data source type selection data
     * @returns Observable with action to create a new data source
     */
    nextStepNavigation: (actualComponent: BaseEntityEditingComponent<any>,injector:Injector, actualData?: any) => {
        const thisStepData:ChooseDataSourceType=actualData;
        const contextType: string = thisStepData?.entityType ? thisStepData.entityType : "GProjectEndpoint";
        const dataSourceObject: GProjectEndpoint = {
            description:undefined,
            parentProjectCode: actualData?.projectCode
        };
        const action: GeboUIActionRequest = {
            actionType: GeboActionType.NEW,
            context: actualData,
            contextType: "ChooseDataSourceType",
            targetType: contextType,
            target: dataSourceObject
        };
        return of(action);
    }
}, {
    id: "newDataSource",
    description: "Create new data source",
    nextStepConfigurationId: "publishDataSource",
    nextStepLabel: "publish",
    nextStepIcon:"pi pi-microchip-ai",
    /**
     * Handles navigation from data source creation to publication.
     * Creates a job to publish the data source using the job launcher service.
     * 
     * @param actualComponent The component handling the current step
     * @param injector Angular injector for service access
     * @param actualData Current data source data
     * @returns Observable with action to create a job status for publishing
     */
    nextStepNavigation: (actualComponent: BaseEntityEditingComponent<any>,injector:Injector, actualData?: any) => {
        const reference = actualComponent.objectReference;
        const jobStatusService=injector.get(JobLauncherControllerService);
        const param:GObjectRef={
            className:reference?.className,
            code:reference?.code,
            description:"Publishing " + actualData?.description
        }
        const observable=jobStatusService.createJob(param);
        return observable.pipe(map(r=>{
            const action: GeboUIActionRequest = {
                actionType: GeboActionType.NEW,
                context: actualData,
                contextType: "GProject",
                targetType: "GJobStatus",
                target: r.result?r.result:{}
            };
            return action;
        }));
    },
    previusStepConfigurationId:"chooseDataSourceType",
    /**
     * Handles navigation back to data source type selection from data source creation.
     * Creates a data source type object with reference to the parent project.
     * 
     * @param actualComponent The component handling the current step
     * @param injector Angular injector for service access
     * @param actualData Current data source data
     * @returns Observable with action to open the data source type selection
     */
    previusStepNavigation: (actualComponent: BaseEntityEditingComponent<any>,injector:Injector, actualData?: any) => {
        const endpoint: GProjectEndpoint = actualData;
        const dataSourceObject: ChooseDataSourceType = {
            code:"MYCHOICE",
            projectCode: endpoint?.parentProjectCode,
            entityType: actualComponent.actualEntityName,
            description:""
        };
        const action: GeboUIActionRequest = {
            actionType: GeboActionType.OPEN,
            context: actualData,
            contextType: actualComponent.actualEntityName,
            targetType: "ChooseDataSourceType",
            target: dataSourceObject
        };
        return of(action);
    }
}, {
    id: "publishDataSource",
    description: "Publish data source"
}];