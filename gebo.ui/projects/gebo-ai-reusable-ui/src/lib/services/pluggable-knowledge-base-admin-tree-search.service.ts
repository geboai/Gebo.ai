/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { ChildVirtualFSParam, GKnowledgeBase, GProject, KnowledgeBaseControllerService, ProjectsControllerService, VDocumentInfo, VFolderInfo } from "@Gebo.ai/gebo-ai-rest-api";
import { forkJoin, map, Observable, of } from "rxjs";
import { EnrichedChild, EnrichedVFilesystemItem } from "./enriched-child";
import { Injectable } from "@angular/core";
import { MenuItem } from "primeng/api";

import { GeboAIModulesService } from "./gebo-ai-modules.service";
import { GeboAIPluggableProjectEndpointsService } from "./pluggable-project-endpoint";
import { GeboActionPerformedEvent, GeboActionType, GeboUIActionRequest } from "../architecture/actions.model";
import { GeboAIEntitiesSettingWizardConfiguration } from "../controls/base-entity-editing-component/entities-modification-wizard";
import { GeboAITranslationService } from "../controls/field-translation-container/gebo-translation.service";
import { findMatchingTranlations, UIExistingText } from "../controls/field-translation-container/text-language-resources";

/**
 * AI generated comments
 * This service provides functionality for navigating and managing knowledge bases, projects, and their endpoints
 * in a tree structure. It handles loading different levels of the knowledge hierarchy, from knowledge bases down to
 * virtual files and folders, and generates context menus for adding new items to projects.
 */
@Injectable({ providedIn: "root" })
export class GeboAIPluggableKnowledgeAdminBaseTreeSearchService {

    /**
     * Initializes the service with required dependencies to access knowledge bases, projects, 
     * and project endpoints.
     * 
     * @param modulesService Service that provides configuration and module information
     * @param knowledgeBaseService Service for accessing knowledge base data
     * @param projectService Service for managing projects
     * @param endpointsService Service for managing project endpoints
     */
    constructor(
        private modulesService: GeboAIModulesService,
        private knowledgeBaseService: KnowledgeBaseControllerService,
        private projectService: ProjectsControllerService,
        private geboTranslationService: GeboAITranslationService,
        private endpointsService: GeboAIPluggableProjectEndpointsService) {

    }

    /**
     * Retrieves all knowledge bases and transforms them into enriched child objects
     * that can be displayed in the tree structure.
     * 
     * @returns Observable of enriched knowledge base objects
     */
    loadKnowledgeBases(): Observable<EnrichedChild[]> {
        return this.knowledgeBaseService.getKnowledgeBases().pipe(map(returned => returned?.map(x => ({ info: x, isKnowledgeBase: true, isLeaf: false, icon: "pi pi-sitemap", className: "", isProject: false, isProjectEndpoint: false } as EnrichedChild))));
    }

    /**
     * Loads the child projects belonging to a specific knowledge base.
     * 
     * @param knowledgebase The knowledge base to retrieve child projects for
     * @returns Observable of enriched project objects that are children of the knowledge base
     */
    loadKnowledgeChilds(knowledgebase: GKnowledgeBase): Observable<EnrichedChild[]> {
        if (knowledgebase?.code) {
            return this.projectService.findRootProjects(
                knowledgebase.code
            ).pipe(map(returned => returned.map(value => ({ info: value, isProject: true, isLeaf: false, icon: "pi pi-list-check" } as EnrichedChild))));
        }
        else return of([]);
    }

    /**
     * Loads both child projects and project endpoints for a given project.
     * Combines the results from multiple services to create a comprehensive list
     * of all children elements.
     * 
     * @param project The project to retrieve children for
     * @returns Observable of enriched child objects that are direct descendants of the project
     */
    loadProjectChilds(project: GProject): Observable<EnrichedChild[]> {
        if (project.rootKnowledgeBaseCode && project.code) {
            const config = this.modulesService.getConfig();
            const _cl: string[] = [""];

            const projectsObservable = this.projectService.findChildProjects(project.rootKnowledgeBaseCode, project.code).pipe(map(returned => returned.map(x => ({ info: x, isProject: true, isLeaf: false, className: "" } as EnrichedChild))));
            const endpointsObservable = this.endpointsService.findByProjectEndpoints(project.code).pipe(map(returned => returned.map(x => ({ info: x, isProjectEndpoint: true, isProject: false, isLeaf: false, className: x.className, icon: x.icon } as EnrichedChild))));

            const services: Observable<EnrichedChild[]>[] = [projectsObservable, endpointsObservable];


            return forkJoin(services).pipe(map((returned) => {
                const out: EnrichedChild[] = [];
                if (returned) {
                    returned.forEach((vector, i) => {
                        vector.forEach(entry => {
                            out.push(entry);
                        });
                    });
                }
                return out;
            }))
        } else return of([]);
    }

    /**
     * Loads root-level virtual folders and documents for a project endpoint.
     * Combines folder and document lists into a single collection of enriched
     * filesystem items.
     * 
     * @param item The project endpoint to retrieve root folders and documents for
     * @returns Observable of enriched virtual filesystem items (folders and documents)
     */
    loadRootProjectEndpointChilds(item: EnrichedChild): Observable<(EnrichedVFilesystemItem<VFolderInfo> | EnrichedVFilesystemItem<VDocumentInfo>)[]> {
        if (item.className && item.info.code) {
            const param: any = {
                className: item.className,
                code: item.info.code
            };
            return forkJoin([this.projectService.getRootFolders(param), this.projectService.getRootDocuments(param)]).pipe(map(vect => {
                const outData: (EnrichedVFilesystemItem<VFolderInfo> | EnrichedVFilesystemItem<VDocumentInfo>)[] = [];
                if (vect && vect.length) {
                    if (vect[0]) {
                        vect[0].forEach(e => {
                            outData.push({
                                info: e,
                                isVirtualFolder: true,
                                isVirtualFile: false,
                                isLeaf: false
                            });
                        });
                    }
                    if (vect[1]) {
                        vect[1].forEach(e => {
                            outData.push({
                                info: e,
                                isVirtualFolder: false,
                                isVirtualFile: true,
                                isLeaf: true
                            });
                        });
                    }
                }
                return outData;
            }));
        } else return of([]);
    }

    /**
     * Loads child folders and documents within a virtual folder.
     * Similar to the root loading function but operates on nested folders.
     * 
     * @param item The virtual folder to retrieve children for
     * @returns Observable of enriched virtual filesystem items (folders and documents)
     */
    loadNestedProjectEndpointChilds(item: EnrichedVFilesystemItem<VFolderInfo>): Observable<(EnrichedVFilesystemItem<VFolderInfo> | EnrichedVFilesystemItem<VDocumentInfo>)[]> {
        if (item.info?.endpointRef) {
            const param: ChildVirtualFSParam = {
                endpointRef: item.info.endpointRef,
                folder: item.info
            };
            return forkJoin([this.projectService.getChildFolders(param), this.projectService.getChildDocuments(param)]).pipe(map(vect => {
                const outData: (EnrichedVFilesystemItem<VFolderInfo> | EnrichedVFilesystemItem<VDocumentInfo>)[] = [];
                if (vect && vect.length) {
                    if (vect[0]) {
                        vect[0].forEach(e => {
                            outData.push({
                                info: e,
                                isVirtualFolder: true,
                                isVirtualFile: false,
                                isLeaf: false
                            });
                        });
                    }
                    if (vect[1]) {
                        vect[1].forEach(e => {
                            outData.push({
                                info: e,
                                isVirtualFolder: false,
                                isVirtualFile: true,
                                isLeaf: true
                            });
                        });
                    }
                }
                return outData;
            }));
        } else return of([]);
    }

    /**
     * Generates context menu items for adding subprojects or project endpoints to a project.
     * Based on provided configuration, creates a list of menu items with appropriate handlers.
     * 
     * @param subProjectAddEnabled Flag indicating if adding subprojects is allowed
     * @param data The project to add items to
     * @param actionConsumer Function that handles action requests
     * @param actionsCallback Callback function for action completion events
     * @param wizardStepsConfigurations Optional configuration for wizard steps
     * @param actualWizardStepConfigrationId Optional ID of the current wizard step
     * @returns Array of menu items for adding content to a project
     */
    public generateAddToProjectMenuEnglish(subProjectAddEnabled: boolean, data: GProject, actionConsumer: (action: GeboUIActionRequest) => void, actionsCallback: (event: GeboActionPerformedEvent) => void, wizardStepsConfigurations?: GeboAIEntitiesSettingWizardConfiguration[], actualWizardStepConfigrationId?: string): MenuItem[] {
        const items: MenuItem[] = [];
        if (subProjectAddEnabled === true) {
            items.push({
                id: "GProject",
                icon: "pi pi-list-check",
                label: "+subproject or item",
                title: "add subproject or item",
                command: () => {
                    let nextStepsParams: any = undefined;
                    if (wizardStepsConfigurations && actualWizardStepConfigrationId) {
                        nextStepsParams = {
                            wizardStepsConfigurations: wizardStepsConfigurations,
                            actualWizardStepConfigrationId: actualWizardStepConfigrationId
                        };
                    }
                    if (data?.code) {
                        const target: GProject = {
                            parentProjectCode: data.code,
                            rootKnowledgeBaseCode: data.rootKnowledgeBaseCode,
                            accessibleToAll: true
                        };
                        actionConsumer({
                            actionType: GeboActionType.NEW,
                            context: data,
                            contextType: "GProject",
                            target: target,
                            targetType: "GProject",
                            targetFormInputs: nextStepsParams,
                            onActionPerformed: actionsCallback
                        });

                    }
                }
            });
        }
        const options = this.endpointsService.getUIProjectEndpointAddOptions(data, actionsCallback);
        if (options) {
            options.forEach(option => {
                items.push({
                    id: option.projecteEndpointClassName.replaceAll(".", "_"),
                    icon: option.addProjectEndpointicon,
                    label: option.addProjectEndpointLabel,
                    title: option.addProjectEndpointTitle,
                    command: () => {
                        let nextStepsParams: any = undefined;
                        if (wizardStepsConfigurations && actualWizardStepConfigrationId) {
                            nextStepsParams = {
                                wizardStepsConfigurations: wizardStepsConfigurations,
                                actualWizardStepConfigrationId: actualWizardStepConfigrationId
                            };
                        }
                        if (data?.code) {
                            const actionRequest = option.actionRequestByProject(data);
                            actionConsumer(actionRequest);
                        }
                    }
                });
            });
        }
        return items;
    }
    public generateAddToProjectMenu(subProjectAddEnabled: boolean, data: GProject, actionConsumer: (action: GeboUIActionRequest) => void, actionsCallback: (event: GeboActionPerformedEvent) => void, wizardStepsConfigurations?: GeboAIEntitiesSettingWizardConfiguration[], actualWizardStepConfigrationId?: string): Observable<MenuItem[]> {
        const menuItems = this.generateAddToProjectMenuEnglish(subProjectAddEnabled, data, actionConsumer, actionsCallback, wizardStepsConfigurations, actualWizardStepConfigrationId);
        const resources: UIExistingText[] = [];
        if (menuItems) {
            menuItems.forEach(x => {
                if (x.label && x.id) {
                    resources.push({
                        moduleId: "AddProjectMenuModule",
                        entityId: "AddProjectMenuComponent",
                        componentId: x.id,
                        fieldId: "label",
                        key: "label",
                        text: x.label
                    });
                }
                if (x.title && x.id) {
                    resources.push({
                        moduleId: "AddProjectMenuModule",
                        entityId: "AddProjectMenuComponent",
                        componentId: x.id,
                        fieldId: "help",
                        key: "help",
                        text: x.title
                    });
                }
                if (x.tooltip && x.id) {
                    resources.push({
                        moduleId: "AddProjectMenuModule",
                        entityId: "AddProjectMenuComponent",
                        componentId: x.id,
                        fieldId: "tooltip",
                        key: "tooltip",
                        text: x.tooltip
                    });
                }
            });
        }
        return this.geboTranslationService.translateOnActualLanguage(resources).pipe(map(languageResource => {
            if (languageResource) {
                const translations = findMatchingTranlations(resources, languageResource);
                if (translations && translations.length) {
                    menuItems.forEach(menuEntry => {

                        const found = translations.filter(translation => menuEntry.id === translation.componentId);
                        if (found) {
                            found.forEach(fieldTranslation => {
                                if (fieldTranslation.translation)
                                    (menuEntry as any)[fieldTranslation.fieldId] = fieldTranslation.translation;
                            });
                        }

                    });
                }
            }
            return menuItems;
        }));
    }

}