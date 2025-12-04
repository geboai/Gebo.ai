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
 * This module contains a component for managing Confluence endpoints in the Gebo.ai system.
 * It allows users to create, edit, delete, and publish Confluence endpoints that connect
 * projects to Confluence systems.
 */
import { Component, forwardRef, Injector, Input } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { BrowseParam, GConfluenceProjectEndpoint, GProject, JobLauncherControllerService, ProjectsControllerService, ConfluenceSystemsControllerService, ConfluenceBrowsingControllerService, GConfluenceSystem } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboActionPerformedEvent, GeboActionType, GeboAIFileType, GeboFormGroupsService, GeboUIActionRequest, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions, MessageService } from "primeng/api";
import { UploadEvent } from "primeng/fileupload";
import { map, Observable, of } from "rxjs";
import { doSaveAndPublishCall } from '../utils/save-publish-callback';
import { loadRootsObservableCallback, browsePathObservableCallback } from "@Gebo.ai/reusable-ui";


/**
 * Component responsible for managing Confluence endpoints.
 * This component allows users to create, edit, and manage Confluence endpoints that connect
 * Gebo.ai projects to Confluence systems. It supports configuration of connection parameters,
 * synchronization settings, and publishing operations.
 */
@Component({
    selector: "gebo-ai-confluence-endpoint-component",
    templateUrl: "gebo-ai-confluence-endpoint.component.html",
    providers: [MessageService, { provide: GEBO_AI_MODULE, useValue: "GeboAIConfluenceModule", multi: false }, {
        provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIConfluenceEndpointComponent),
        multi: false
    }],
    standalone: false
})
export class GeboAIConfluenceEndpointComponent extends BaseEntityEditingComponent<GConfluenceProjectEndpoint> {
    /** The entity type name used for identification */
    protected override entityName: string = "GConfluenceProjectEndpoint";
    /** The handshake code used for authentication */
    public handShakeCode?: string;

    /** Flag indicating whether the project can be modified */
    @Input() cantModifyProject: boolean = true;
    /** Observable for retrieving available projects */
    projectsObservable: Observable<GProject[]> = this.projectsController.getProjects();
    /** Form group containing all editable fields for the Confluence endpoint */
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
        confluenceSystemCode: new FormControl(),
        paths: new FormControl(),
        extractedFormat: new FormControl(),
        extractAndSaveContents: new FormControl(),
        confluenceVersion: new FormControl(),
        openZips: new FormControl()

    });
    /** Flag indicating whether the endpoint is published */
    published: boolean = false;
    /** List of available Confluence server systems */
    confluenceServersObservable = this.confluenceControllerService.getConfluenceSystems();
    /** Action request for creating a new Confluence server */
    newConfluenceServerRequest: GeboUIActionRequest = {
        actionType: GeboActionType.NEW,
        context: {},
        contextType: this.entityName,
        targetType: "GConfluenceSystem",
        target: { contentManagementSystemType: "ATLASSIAN-CONFLUENCE" } as GConfluenceSystem
    };

    /** Tracks the last selected Confluence system code */
    private lastConfluenceSystemCode: string = "";

    /** Observable callback for loading Confluence roots */
    public loadRootsObservable: loadRootsObservableCallback = () => of({});
    /** Observable callback for browsing Confluence paths */
    public browsePathObservable: browsePathObservableCallback = (param: BrowseParam) => of({});
    /** List of supported file types */
    fileTypesList: GeboAIFileType[] = [];

    /**
     * Creates an instance of GeboAIConfluenceEndpointComponent.
     * Initializes the component and sets up subscriptions for form control changes.
     */
    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private confluenceControllerService: ConfluenceSystemsControllerService,
        private confluenceBrowsing: ConfluenceBrowsingControllerService,
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
        this.formGroup.controls["confluenceSystemCode"].valueChanges.subscribe(x => {
            if (x && this.lastConfluenceSystemCode !== x) {
                this.lastConfluenceSystemCode = x;
                this.loadRootsObservable = () => {
                    return this.confluenceBrowsing.getConfluenceRoots(this.lastConfluenceSystemCode);
                };
                this.browsePathObservable = (param: BrowseParam) => {
                    return this.confluenceBrowsing.browseConfluencePath(param, this.lastConfluenceSystemCode);
                };


            }
        });
    }

    /**
     * Checks if a backend process is running for the specified reference
     * @param reference The object reference to check for running jobs
     * @returns An Observable with boolean indicating if a process is running
     */
    protected override checkBackendProcessing(reference: { className?: string; code?: string; }): Observable<boolean> {
        return this.JobLauncherControllerService.getHasRunningJobs(reference).pipe(map(r => r?.hasRunningJobs === true));
    }

    /**
     * Initializes the component and checks for existing accounts and systems
     */
    override ngOnInit(): void {
        super.ngOnInit();

    }


    /**
     * Handles initialization of a new entity
     * @param actualValue The new entity value
     */
    protected override onNewData(actualValue: GConfluenceProjectEndpoint): void {

    }

    /**
     * Handles the loaded persistent data
     * @param actualValue The loaded entity value
     */
    protected override onLoadedPersistentData(actualValue: GConfluenceProjectEndpoint): void {

    }

    /**
     * Finds a Confluence endpoint by its code
     * @param code The endpoint code to search for
     * @returns An Observable of the found endpoint or null
     */
    override findByCode(code: string): Observable<GConfluenceProjectEndpoint | null> {
        return this.confluenceControllerService.findConfluenceEndpointsByCode(code);
    }

    /**
     * Saves changes to an existing Confluence endpoint
     * @param value The endpoint to update
     * @returns An Observable of the updated endpoint
     */
    override save(value: GConfluenceProjectEndpoint): Observable<GConfluenceProjectEndpoint> {
        return this.confluenceControllerService.updateConfluenceEndpoint(value);
    }

    /**
     * Inserts a new Confluence endpoint
     * @param value The new endpoint to insert
     * @returns An Observable of the created endpoint
     */
    override insert(value: GConfluenceProjectEndpoint): Observable<GConfluenceProjectEndpoint> {
        return this.confluenceControllerService.insertConfluenceEndpoint(value);
    }

    /**
     * Deletes a Confluence endpoint
     * @param value The endpoint to delete
     * @returns An Observable indicating success
     */
    override delete(value: GConfluenceProjectEndpoint): Observable<boolean> {
        return this.confluenceControllerService.deleteConfluenceEndpoint(value);
    }

    /**
     * Checks if the endpoint can be deleted
     * @param value The endpoint to check
     * @returns An Observable with deletion status and message
     */
    override canBeDeleted(value: GConfluenceProjectEndpoint): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

    /**
     * Handles automatic file upload events
     * @param event The upload event
     */
    onBasicUploadAuto(event: UploadEvent) {
        this.formGroup.controls["uploadHandshakeCode"].setValue(this.handShakeCode);
        this.messageService.add({ severity: 'success', summary: 'Success', detail: 'File Uploaded with success' });
    }

    /**
     * Performs both save and publish operations
     */
    doSaveAndPublish() {
        doSaveAndPublishCall(this);
    }

    /**
     * Publishes the Confluence endpoint by creating a job
     * Saves the entity first, then launches a job to process the endpoint
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
                            contextType: "GConfluenceProjectEndpoint",
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