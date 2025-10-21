/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




/* AI generated comments */
import { Component, forwardRef, Injector, Input } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { BrowseParam, FileSystemsBrowsingControllerService, FileSystemsControllerService, GFilesystemProjectEndpoint, GProject, JobLauncherControllerService, ProjectsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, browsePathObservableCallback, GEBO_AI_FIELD_HOST, GeboActionPerformedEvent, GeboActionType, GeboAIFileType, GeboFormGroupsService, GeboUIActionRequest, GeboUIActionRoutingService, GeboUIOutputForwardingService, loadRootsObservableCallback, reconstructNavigationObservableCallback, VFilesystemReference } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions, MessageService } from "primeng/api";
import { UploadEvent } from "primeng/fileupload";
import { map, Observable, of } from "rxjs";
import { doSaveAndPublishCall } from '../utils/save-publish-callback';

/**
 * Component for managing filesystem endpoints within Gebo.ai.
 * Handles creation, editing, and management of GFilesystemProjectEndpoint entities.
 * Provides UI functionality for browsing file systems, uploading files, and managing endpoint configuration.
 * Extends BaseEntityEditingComponent to handle common entity editing operations.
 */
@Component({
    selector: "gebo-ai-filesystem-endpoint-component",
    templateUrl: "gebo-ai-filesystem-endpoint.component.html",
    providers: [MessageService, {
        provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIFileSystemEndpointComponent),
        multi: true
    }],
    standalone: false
})
export class GeboAIFileSystemEndpointComponent extends BaseEntityEditingComponent<GFilesystemProjectEndpoint> {
    protected override entityName: string = "GFilesystemProjectEndpoint";
    public handShakeCode?: string;

    /** Controls whether the project can be modified */
    @Input() cantModifyProject: boolean = true;

    /** Observable for retrieving available projects */
    projectsObservable: Observable<GProject[]> = this.projectsController.getProjects();

    /** Form group for managing form controls */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        parentCode: new FormControl(),
        description: new FormControl(),
        parentProjectCode: new FormControl(),
        readonly: new FormControl(),
        published: new FormControl(),
        synchPeriodically: new FormControl(),
        buildSystemsRefs: new FormControl(),
        path: new FormControl(),
        catalogingCriteria: new FormControl(),
        contentManagementSystem: new FormControl(),
        openZips: new FormControl(),
        vectorizeOnlyExtensions: new FormControl(),
        programmedTables: new FormControl()
    });

    /** Tracks the published state of the endpoint */
    published: boolean = false;

    /** Callback for loading filesystem roots */
    public loadRootsObservable: loadRootsObservableCallback = () => { return this.filesystemsBrowsing.getSharedFilesystemRoots(); }

    /** Callback for browsing filesystem paths */
    public browsePathObservable: browsePathObservableCallback = (param: BrowseParam) => { return this.filesystemsBrowsing.browseSharedFilesystemRootsPath(param) };

    /** Callback for reconstructing navigation based on navigation points */
    public reconstructNavigationObservableCallback: reconstructNavigationObservableCallback = (navigationPoints: VFilesystemReference[]) => { return this.filesystemsBrowsing.getSharedFilesystemNavigationStatus(navigationPoints); }

    /** List of file types available for selection */
    fileTypesList: GeboAIFileType[] = [];

    /**
     * Constructor for the component, initializes services and parent class.
     * Sets up subscription to published value changes and enables backend processing check.
     */
    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private filesystemsControllerService: FileSystemsControllerService,
        private filesystemsBrowsing: FileSystemsBrowsingControllerService,
        private projectsController: ProjectsControllerService,
        private JobLauncherControllerService: JobLauncherControllerService,
        private actionsRouter: GeboUIActionRoutingService,
        private messageService: MessageService,

        confirmService: ConfirmationService,
        outputForwardingService?: GeboUIOutputForwardingService
    ) {
        super(injector, geboFormGroupsService, confirmService, actionsRouter, outputForwardingService);
        this.formGroup.controls["published"].valueChanges.subscribe(published => {
            this.published = published;
        });
        this.doPeriodicBackendProcessingCheck = true;
    }

    /**
     * Checks if there are any running backend jobs for the entity
     * @param reference The reference object to check for running jobs
     * @returns An Observable<boolean> indicating if there are running jobs
     */
    protected override checkBackendProcessing(reference: { className?: string; code?: string; }): Observable<boolean> {
        return this.JobLauncherControllerService.getHasRunningJobs(reference).pipe(map(r => r?.hasRunningJobs === true));
    }

    /**
     * Lifecycle hook for component initialization
     * Calls parent initialization method
     */
    override ngOnInit(): void {
        super.ngOnInit();
    }

    /**
     * Handles actions when new data is created
     * @param actualValue The new filesystem endpoint data
     */
    protected override onNewData(actualValue: GFilesystemProjectEndpoint): void {

    }

    /**
     * Handles actions when persistent data is loaded
     * @param actualValue The loaded filesystem endpoint data
     */
    protected override onLoadedPersistentData(actualValue: GFilesystemProjectEndpoint): void {

    }

    /**
     * Finds a filesystem endpoint by its code
     * @param code The code to search for
     * @returns An Observable with the found endpoint or null
     */
    override findByCode(code: string): Observable<GFilesystemProjectEndpoint | null> {
        return this.filesystemsControllerService.findFileSystemEndpointsByQbe({ code: code }).pipe(map(v => v && v.length ? v[0] : null));
    }

    /**
     * Saves an existing filesystem endpoint
     * @param value The endpoint to save
     * @returns An Observable with the saved endpoint
     */
    override save(value: GFilesystemProjectEndpoint): Observable<GFilesystemProjectEndpoint> {
        return this.filesystemsControllerService.updateFilesystemEndpoint(value);
    }

    /**
     * Inserts a new filesystem endpoint
     * @param value The endpoint to insert
     * @returns An Observable with the inserted endpoint
     */
    override insert(value: GFilesystemProjectEndpoint): Observable<GFilesystemProjectEndpoint> {
        return this.filesystemsControllerService.insertFilesystemEndpoint(value);
    }

    /**
     * Deletes a filesystem endpoint
     * @param value The endpoint to delete
     * @returns An Observable indicating success or failure
     */
    override delete(value: GFilesystemProjectEndpoint): Observable<boolean> {
        return this.filesystemsControllerService.deleteFilesystemEndpoint(value);
    }

    /**
     * Checks if the endpoint can be deleted
     * @param value The endpoint to check
     * @returns An Observable with the result and any message
     */
    override canBeDeleted(value: GFilesystemProjectEndpoint): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

    /**
     * Handles file upload events
     * @param event The upload event
     */
    onBasicUploadAuto(event: UploadEvent) {
        this.formGroup.controls["uploadHandshakeCode"].setValue(this.handShakeCode);
        this.messageService.add({ severity: 'success', summary: 'Success', detail: 'File Uploaded with success' });
    }

    /**
     * Performs both save and publish operations
     * Uses the utility function doSaveAndPublishCall
     */
    doSaveAndPublish() {
        doSaveAndPublishCall(this);
    }

    /**
     * Performs the publish operation for the endpoint
     * Creates a job to process the endpoint and opens the job status view
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
                            contextType: "GFilesystemProjectEndpoint",
                            target: jobStatus.result,
                            targetType: "GJobStatus",
                            onActionPerformed: (event: GeboActionPerformedEvent) => {

                            }
                        };
                        this.cancelAction.emit(true);
                        this.actionsRouter.routeEvent(action);
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