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
 * This file implements components and services for managing knowledge bases in a Gebo.ai application.
 * It provides functionality for setting up, displaying, and interacting with knowledge bases through a wizard interface.
 */

import { Component, Injectable } from "@angular/core";
import { GeboContentProcessRow, GeboFastKnowledgeBaseSetupControllerService, GeboKnowledgeBaseSetupStatus, GKnowledgeBase, GObjectRefGProjectEndpoint, GProject, JobLauncherControllerService, KnowledgeBaseControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, ChooseDataSourceType, GeboActionPerformedEvent, GeboActionType, GeboUIActionRequest, GeboUIActionRoutingService, SetupWizardComunicationService, sliceWizard } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions } from "primeng/api";
import { forkJoin, map, Observable } from "rxjs";
import { KNOWLEDGEBASE_WIZARD } from "./wizards-navigation";

/**
 * Service responsible for providing the setup status of a knowledge base.
 * Extends AbstractStatusService to integrate with the application's status tracking system.
 */
@Injectable()
export class KnowledgeBasePresentService extends AbstractStatusService {
    /**
     * Creates an instance of KnowledgeBasePresentService.
     * @param knowledgeBaseService - Service for retrieving knowledge base setup information
     */
    constructor(private knowledgeBaseService: GeboFastKnowledgeBaseSetupControllerService) {
        super();
    }

    /**
     * Retrieves the setup status of the knowledge base as a boolean.
     * Overrides the abstract method from the parent class.
     * @returns An Observable that emits true if the knowledge base is set up, false otherwise
     */
    public override getBooleanStatus(): Observable<boolean> {
        return this.knowledgeBaseService.getCompleteKnowledgeBaseSetupStatus().pipe(map(x => x.isSetup === true));
    }
}

/**
 * Component that provides a wizard interface for setting up and managing knowledge bases.
 * Extends BaseWizardSectionComponent to integrate with the application's wizard system.
 */
@Component({
    selector: "gebo-knowledge-base-wizard-component",
    templateUrl: "knowledge-base-wizard.component.html",
    standalone: false
})
export class KnowledgeBaseWizardComponent extends BaseWizardSectionComponent {
    /**
     * Collection of content process rows to be displayed in the wizard
     */
    public rows: GeboContentProcessRow[] = [];
    
    /**
     * Current status of the knowledge base setup process
     */
    public status?: GeboKnowledgeBaseSetupStatus;
    
    /**
     * Creates an instance of KnowledgeBaseWizardComponent.
     * @param setupWizardComunicationService - Service for communicating with the wizard framework
     * @param knowledgeBaseService - Service for retrieving and managing knowledge base information
     * @param JobLauncherControllerService - Service for launching background jobs
     * @param confirmationService - Service for displaying confirmation dialogs
     * @param actionsRoutingService - Service for routing UI actions
     */
    public constructor(setupWizardComunicationService: SetupWizardComunicationService,
        private knowledgeBaseService: GeboFastKnowledgeBaseSetupControllerService,
        private JobLauncherControllerService: JobLauncherControllerService,
        private confirmationService: ConfirmationService,
        private actionsRoutingService: GeboUIActionRoutingService) {
        super(setupWizardComunicationService);
    }
    
    /**
     * Reloads the component data by fetching the latest content process rows and setup status.
     * Overrides the abstract method from the parent class.
     */
    public override reloadData(): void {
        const observables: [Observable<GeboContentProcessRow[]>, Observable<GeboKnowledgeBaseSetupStatus>] = [this.knowledgeBaseService.getContentProcessRows(), this.knowledgeBaseService.getCompleteKnowledgeBaseSetupStatus()];

        this.loading = true;
        forkJoin(observables).subscribe({
            next: (value) => {
                this.rows = value[0];
                this.status = value[1];
                this.isSetupCompleted = this.rows?.filter(x => x.contentsCount)?.length > 0;
            },
            complete: () => {
                this.loading = false;
            }
        });
    }

    /**
     * Initiates the creation of a new knowledge base through the UI action routing system.
     * Sets up a knowledge base with default accessibility to all.
     */
    public newKnowledgeBase() {
        const newKb:GKnowledgeBase={
            accessibleToAll:true
        };
        this.actionsRoutingService.routeEvent({
            actionType: GeboActionType.NEW,
            context: {},
            contextType: "KnowledgeBaseWizardComponent",
            target: newKb,
            targetType: "GKnowledgeBase",
            targetFormInputs: { wizardStepsConfigurations: KNOWLEDGEBASE_WIZARD, actualWizardStepConfigrationId: "newKnowledgeBase" },
            onActionPerformed: (event) => {
                this.reloadData();
            },
            onWizardActionPerformed:(event)=>{
                this.reloadData();
            }
        });
    }
    
    /**
     * Opens an existing knowledge base for editing.
     * @param row - The content process row containing the knowledge base to edit
     */
    public editKnowledgeBase(row: GeboContentProcessRow) {
        if (row.knowledgeBase) {
            this.actionsRoutingService.routeEvent({
                actionType: GeboActionType.OPEN,
                context: {},
                contextType: "KnowledgeBaseWizardComponent",
                target: row.knowledgeBase,
                targetType: "GKnowledgeBase",                
                onActionPerformed: (event) => {
                    this.reloadData()
                },
                onWizardActionPerformed:(event)=>{
                    this.reloadData();
                }
            });
        }
    }
    
    /**
     * Opens an existing project for editing.
     * @param row - The content process row containing the project to edit
     */
    public editProject(row: GeboContentProcessRow) {
        if (row.project) {
            this.actionsRoutingService.routeEvent({
                actionType: GeboActionType.OPEN,
                context: {},
                contextType: "KnowledgeBaseWizardComponent",
                target: row.project,
                targetType: "GProject",                
                onActionPerformed: (event) => {
                    this.reloadData()
                },
                onWizardActionPerformed:(event)=>{
                    this.reloadData();
                }
            });
        }
    }
    
    /**
     * Initiates the creation of a new project associated with a knowledge base.
     * @param row - The content process row containing the knowledge base to associate with the new project
     */
    public newProject(row: GeboContentProcessRow) {
        const project: GProject = {
            accessibleToAll:true,
            rootKnowledgeBaseCode: row.knowledgeBase?.code
        };
        this.actionsRoutingService.routeEvent({
            actionType: GeboActionType.NEW,
            context: {},
            contextType: "KnowledgeBaseWizardComponent",
            target: project,
            targetType: "GProject",
            targetFormInputs: { wizardStepsConfigurations: sliceWizard(1, KNOWLEDGEBASE_WIZARD), actualWizardStepConfigrationId: "newProject" },
            onActionPerformed: (event) => {
                this.reloadData()
            },
            onWizardActionPerformed:(event)=>{
                this.reloadData();
            }
        });
    }
    
    /**
     * Initiates the creation of a new data source for a project.
     * @param row - The content process row containing the project to associate with the new data source
     */
    public newDataSource(row: GeboContentProcessRow) {
        const chooseDataSource: ChooseDataSourceType = {
            code: "MYNEWCHOOSE",
            projectCode: row.project?.code,
            description: "Choose data source",
            entityType: undefined
        };
        this.actionsRoutingService.routeEvent({
            actionType: GeboActionType.NEW,
            context: {},
            contextType: "KnowledgeBaseWizardComponent",
            target: chooseDataSource,
            targetType: "ChooseDataSourceType",
            targetFormInputs: { wizardStepsConfigurations: sliceWizard(2, KNOWLEDGEBASE_WIZARD), actualWizardStepConfigrationId: "chooseDataSourceType" },
            onActionPerformed: (event) => {
                this.reloadData()
            },
            onWizardActionPerformed:(event)=>{
                this.reloadData();
            }
        });
    }
    
    /**
     * Opens an existing data source for editing.
     * @param row - The content process row containing the data source to edit
     */
    public editDataSource(row: GeboContentProcessRow) {
        if (row.endpoint) {
            this.actionsRoutingService.routeEvent({
                actionType: GeboActionType.OPEN,
                context: {},
                contextType: "KnowledgeBaseWizardComponent",
                target: row.endpoint,
                targetType: this.entityName(row.endpointObjectRef?.className),                
                onActionPerformed: (event) => {
                    this.reloadData()
                },
                onWizardActionPerformed:(event)=>{
                    this.reloadData();
                }
            });
        }
    }
    
    /**
     * Extracts the entity name from a fully qualified class name.
     * @param className - The fully qualified class name
     * @returns The simple entity name (last part of the class name)
     * @throws Error if the class name is undefined
     */
    entityName(className: string | undefined): string {
        if (!className) throw Error("Unknown entity type");
        const lastIndex=className.lastIndexOf(".");
        return lastIndex && lastIndex>0? className.substring(lastIndex+1):className;
    }
    
    /**
     * Publishes a data source to the knowledge base after user confirmation.
     * Creates a job to process the data source and monitors its status.
     * @param row - The content process row containing the data source to publish
     */
    public publish(row: GeboContentProcessRow) {
        const callback: () => void = () => {
            if (row.endpointObjectRef) {
                const objectReference: GObjectRefGProjectEndpoint = row.endpointObjectRef;
                this.loading = true;
                this.JobLauncherControllerService.createJob(objectReference).subscribe({
                    next: (jobStatus) => {
                        if (jobStatus.result) {
                            const action: GeboUIActionRequest = {
                                actionType: GeboActionType.OPEN,
                                context: {},
                                contextType: "KnowledgeBaseWizardComponent",
                                target: jobStatus.result,
                                targetType: "GJobStatus",
                                onActionPerformed: (event: GeboActionPerformedEvent) => {
                                    this.reloadData();
                                }
                            };
                            this.actionsRoutingService.routeEvent(action);
                        } else {
                            this.userMessages = jobStatus.messages as ToastMessageOptions[];
                        }
                    },
                    error: (error) => { },
                    complete: () => {
                        this.loading = false;
                    }
                });
            }
        }
        this.confirmationService.confirm({
            header: "Confirm pubblication",
            message: "Are you shure you want to publish the selected data source in the knowledge base?",
            accept: callback
        })
    }
}