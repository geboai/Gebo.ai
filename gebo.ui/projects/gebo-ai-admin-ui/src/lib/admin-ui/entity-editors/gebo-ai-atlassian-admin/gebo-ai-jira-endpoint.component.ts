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
 * This module contains a component for managing JIRA project endpoints in Gebo.ai.
 * It allows users to create, edit, and delete JIRA project endpoints, as well as
 * interact with JIRA systems and browse JIRA content.
 */

import { Component, forwardRef, Injector, Input } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { BrowseParam, GConfluenceProjectEndpoint, GProject, JobLauncherControllerService, ProjectsControllerService, GConfluenceSystem, SecretInfo, GJiraProjectEndpoint, JiraSystemsControllerService, JiraBrowsingControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GeboActionPerformedEvent, GeboActionType, GeboAIFileType, GeboFormGroupsService, GeboUIActionRequest, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions, MessageService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { doSaveAndPublishCall } from '../utils/save-publish-callback';
import { loadRootsObservableCallback, browsePathObservableCallback } from "@Gebo.ai/reusable-ui";


/**
 * Component for managing Jira project endpoints within the Gebo.ai application.
 * This component extends BaseEntityEditingComponent to provide editing capabilities
 * for GJiraProjectEndpoint entities, including creating, updating, and deleting
 * Jira project endpoints. It also provides functionality for browsing Jira content,
 * connecting to Jira systems, and publishing endpoints.
 */
@Component({
    selector: "gebo-ai-jira-endpoint-component",
    templateUrl: "gebo-ai-jira-endpoint.component.html",
    providers: [MessageService, {
        provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIJiraEndpointComponent),
        multi: true
    }],
    standalone: false
})
export class GeboAIJiraEndpointComponent extends BaseEntityEditingComponent<GJiraProjectEndpoint> {
    /**
     * The name of the entity type this component manages
     */
    protected override entityName: string = "GJiraProjectEndpoint";



    /**
     * Flag that determines if the project field can be modified
     * When true, the project field is read-only
     */
    @Input() cantModifyProject: boolean = true;

    /**
     * Observable for loading available projects from the backend
     */
    projectsObservable: Observable<GProject[]> = this.projectsController.getProjects();

    /**
     * Form group for managing the entity's editable fields
     */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        parentProjectCode: new FormControl(),
        readonly: new FormControl(),
        published: new FormControl(),
        synchPeriodically: new FormControl(),
        vectorizeOnlyExtensions: new FormControl(),
        buildSystemsRefs: new FormControl(),
        catalogingCriteria: new FormControl(),
        programmedTables: new FormControl(),
        synchroStrategy: new FormControl(),
        jiraSystemCode: new FormControl(),
        paths: new FormControl(),
        extractedFormat: new FormControl(),
        extractAndSaveContents: new FormControl(),
        confluenceVersion: new FormControl(),
        openZips: new FormControl()

    });

    /**
     * Flag indicating if the endpoint is published
     */
    published: boolean = false;

    /**
     * Array of available Jira systems
     */
    jiraServersObservable = this.jiraControllerService.getJiraSystems();

    /**
     * Request object for creating a new Jira server
     */
    newjiraServerRequest: GeboUIActionRequest = {
        actionType: GeboActionType.NEW,
        context: {},
        contextType: this.entityName,
        targetType: "GJiraSystem",
        target: { contentManagementSystemType: "ATLASSIAN-JIRA" } as GConfluenceSystem
    };

    /**
     * Identity context identifier for Jira systems
     */
    private actualIdentityContext: string = "ATLASSIAN-JIRA";

    /**
     * Flag indicating if there are no accounts or systems configured
     */
    public noAccountsAndSystems: boolean = false;

    /**
     * Stores the last selected Confluence system code to avoid redundant operations
     */
    private lastConfluenceSystemCode: string = "";

    /**
     * Available identity/secret information for authentication
     */
    public identities: SecretInfo[] = [];

    /**
     * Callback function for loading root elements from Jira
     */
    public loadRootsObservable: loadRootsObservableCallback = () => of({});

    /**
     * Callback function for browsing Jira content by path
     */
    public browsePathObservable: browsePathObservableCallback = (param: BrowseParam) => of({});

    /**
     * List of supported file types
     */
    public fileTypesList: GeboAIFileType[] = [];

    /**
     * Constructor that initializes the component and sets up subscriptions
     * to form control changes and other initialization tasks.
     */
    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private jiraControllerService: JiraSystemsControllerService,
        private jiraBrowsing: JiraBrowsingControllerService,
        private projectsController: ProjectsControllerService,
        private JobLauncherControllerService: JobLauncherControllerService,
        private actionsRouter: GeboUIActionRoutingService,
        private messageService: MessageService,

        confirmService: ConfirmationService,
        outputForwardingService: GeboUIOutputForwardingService
    ) {
        super(injector, geboFormGroupsService, confirmService, actionsRouter, outputForwardingService);
        this.formGroup.controls["published"].valueChanges.subscribe(published => {
            this.published = published;
        });
        this.doPeriodicBackendProcessingCheck = true;
        this.formGroup.controls["jiraSystemCode"].valueChanges.subscribe(x => {
            if (x && this.lastConfluenceSystemCode !== x) {
                this.lastConfluenceSystemCode = x;
                this.loadRootsObservable = () => {
                    return this.jiraBrowsing.getJiraRoots(this.lastConfluenceSystemCode);
                };
                this.browsePathObservable = (param: BrowseParam) => {
                    return this.jiraBrowsing.browseJiraPath(param, this.lastConfluenceSystemCode);
                };


            }
        });
    }

    /**
     * Checks if there are any running backend processes for this entity
     * @param reference The entity reference to check
     * @returns An Observable that resolves to true if there are running jobs, false otherwise
     */
    protected override checkBackendProcessing(reference: { className?: string; code?: string; }): Observable<boolean> {
        return this.JobLauncherControllerService.getHasRunningJobs(reference).pipe(map(r => r?.hasRunningJobs === true));
    }

    /**
     * Lifecycle hook that initializes the component after Angular initializes
     * the data-bound properties
     */
    override ngOnInit(): void {
        super.ngOnInit();
    }



    /**
     * Handles initialization when creating a new entity
     * @param actualValue The new entity instance
     */
    protected override onNewData(actualValue: GConfluenceProjectEndpoint): void {

    }

    /**
     * Handles initialization when loading an existing entity
     * @param actualValue The loaded entity instance
     */
    protected override onLoadedPersistentData(actualValue: GConfluenceProjectEndpoint): void {

    }

    /**
     * Finds a Jira project endpoint by its code
     * @param code The code of the endpoint to find
     * @returns An Observable that resolves to the found entity or null
     */
    override findByCode(code: string): Observable<GConfluenceProjectEndpoint | null> {
        return this.jiraControllerService.findJiraEndpointsByCode(code);
    }

    /**
     * Saves changes to an existing Jira project endpoint
     * @param value The endpoint to update
     * @returns An Observable that resolves to the updated entity
     */
    override save(value: GConfluenceProjectEndpoint): Observable<GConfluenceProjectEndpoint> {
        return this.jiraControllerService.updateJiraEndpoint(value);
    }

    /**
     * Creates a new Jira project endpoint
     * @param value The endpoint to create
     * @returns An Observable that resolves to the created entity
     */
    override insert(value: GConfluenceProjectEndpoint): Observable<GConfluenceProjectEndpoint> {
        return this.jiraControllerService.insertJiraEndpoint(value);
    }

    /**
     * Deletes a Jira project endpoint
     * @param value The endpoint to delete
     * @returns An Observable that resolves to true if deletion was successful
     */
    override delete(value: GConfluenceProjectEndpoint): Observable<boolean> {
        return this.jiraControllerService.deleteJiraEndpoint(value);
    }

    /**
     * Checks if an endpoint can be deleted
     * @param value The endpoint to check
     * @returns An Observable that resolves to an object indicating if deletion is possible
     */
    override canBeDeleted(value: GConfluenceProjectEndpoint): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }



    /**
     * Combined save and publish operation
     * Uses the utility function to perform both operations in sequence
     */
    doSaveAndPublish() {
        doSaveAndPublishCall(this);
    }

    /**
     * Publishes the endpoint by creating a backend job
     * This triggers the synchronization process with Jira
     */
    doPublish(): void {
        const callback: (d: any) => void = (d: any) => {
            const objectReference = this.createBackendObjectReference();
            this.loadingRelatedBackend = true;
            this.JobLauncherControllerService.createJob(objectReference).subscribe({
                next: (jobStatus) => {
                    if (jobStatus.result) {
                        this.periodicBackendProcessingCheck();
                        const action: GeboUIActionRequest = {
                            actionType: GeboActionType.OPEN,
                            context: this.entity ? this.entity : {},
                            contextType: "GJiraProjectEndpoint",
                            target: jobStatus.result,
                            targetType: "GJobStatus",
                            onActionPerformed: (event: GeboActionPerformedEvent) => {

                            }
                        };
                        this.actionsRouter.routeEvent(action);
                        this.cancelAction.emit(true);
                    } else {
                        this.userMessages = jobStatus.messages as ToastMessageOptions[];
                    }
                },
                error: (error) => { },
                complete: () => {
                    this.loadingRelatedBackend = false;
                }
            });
        }
        this.doSave(callback);
    }
}