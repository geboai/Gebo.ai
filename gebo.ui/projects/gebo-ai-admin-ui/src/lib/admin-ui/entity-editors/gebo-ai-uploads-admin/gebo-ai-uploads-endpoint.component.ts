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
 * This file contains a component responsible for managing uploads endpoints in a Gebo.ai application.
 * It extends BaseEntityEditingComponent to handle CRUD operations for GUploadsProjectEndpoint entities
 * and provides functionality for file uploads, project selection, and publishing.
 */

import { Component, forwardRef, Inject, Injector, Input, ViewChild } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { BASE_PATH, GUploadsProjectEndpoint, GProject, JobLauncherControllerService, ProjectsControllerService, FileUploadsControllerService, UploadedFileNode } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboActionPerformedEvent, GeboActionType, GeboAIFileType, GeboFormGroupsService, GeboUIActionRequest, GeboUIActionRoutingService, GeboUIOutputForwardingService, ServedFileReference } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { GeboAIUploadsFilesComponent } from "./gebo-ai-uploads-files.component";
import { doSaveAndPublishCall } from '../utils/save-publish-callback';

/**
 * Endpoint serving a file physically present in the folder of an uploads data
 * source. It is addressed by url and not through a generated client because the
 * viewer consumes it as a url, the same way the knowledge base contents are
 * served.
 */
const serveContentsUrl: string = "/api/admin/UploadsBrowsingController/serveUploadsEndpointFile";

/**
 * Component for managing uploads endpoints in Gebo.ai
 * 
 * This component provides a user interface for creating, reading, updating, and deleting
 * uploads endpoints. It handles file uploads, project selection, and endpoint configuration.
 * It also manages the publishing process to make the endpoint available for use.
 */
@Component({
    selector: "gebo-ai-uploads-endpoint-component",
    templateUrl: "gebo-ai-uploads-endpoint.component.html",
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIUploadsModule", multi: false },   
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIUploadsEndpointComponent), multi: true
    }],
    standalone: false
})
export class GeboAIUploadsEndpointComponent extends BaseEntityEditingComponent<GUploadsProjectEndpoint> {

    /** Name of the entity type being managed */
    protected override entityName: string = "GUploadsProjectEndpoint";


    /** Base URL for API endpoints */
    public baseUrl: string = "";

    /** Flag controlling whether project can be modified */
    @Input() cantModifyProject: boolean = true;

    /** Observable of available projects to select from */
    projectsObservable: Observable<GProject[]> = this.projectsController.getProjects();

    /** Form group containing all form controls for the entity */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        parentCode: new FormControl(),
        description: new FormControl(),
        personalData: new FormControl(),
        parentProjectCode: new FormControl(),
        readonly: new FormControl(),
        published: new FormControl(),
        synchPeriodically: new FormControl(),
        buildSystemsRefs: new FormControl(),
        vectorizeOnlyExtensions: new FormControl(),
        openZips: new FormControl(),
        uploadHandshakeCode: new FormControl(),
        contentManagementSystem: new FormControl()
    });

    /** Flag indicating if the endpoint is published */
    published: boolean = false;

    /** List of allowed file extensions for upload */
    filesExtensionsList: string[] = [".zip"];

    /** Flat string representation of allowed file extensions */
    filesExtensionsFlatList: string = ".zip";

    /** List of allowed file types for upload */
    fileTypesList: GeboAIFileType[] = [];

    /** The "Uploaded files" tab: the drop zone and the tree of the files */
    @ViewChild("filesManager") filesManager?: GeboAIUploadsFilesComponent;

    /**
     * Code of the data source whose files are being managed. It is only known once
     * the data source is saved: files can be added from then on, into its folder.
     */
    public contentsEndpointCode?: string;

    /**
     * The file the admin asked to open, undefined when the viewer is closed.
     *
     * What is managed here is what the data source physically holds, ingested or
     * not, so the file is served straight out of the folder of the data source
     * instead of through the knowledge base contents controller, which only knows
     * the documents that have already been published.
     */
    public viewedFile?: ServedFileReference;




    /**
     * Constructor initializes services and sets up subscriptions
     * 
     * @param injector Angular injector for dependency injection
     * @param geboFormGroupsService Service for managing form groups
     * @param uploadsControllerService Service for managing file uploads
     * @param projectsController Service for accessing projects
     * @param JobLauncherControllerService Service for launching background jobs
     * @param actionsRouter Service for routing UI actions
     * @param confirmService Service for confirmation dialogs
     * @param path Base API path
     * @param outputForwardingService Service for forwarding outputs
     */
    constructor(injector: Injector,
        geboFormGroupsService: GeboFormGroupsService,
        private uploadsControllerService: FileUploadsControllerService,
        private projectsController: ProjectsControllerService,
        private JobLauncherControllerService: JobLauncherControllerService,
        private actionsRouter: GeboUIActionRoutingService,
        confirmService: ConfirmationService,
        @Inject(BASE_PATH) path: string,
        outputForwardingService?: GeboUIOutputForwardingService
    ) {
        super(injector, geboFormGroupsService, confirmService, actionsRouter, outputForwardingService);
        this.formGroup.controls["published"].valueChanges.subscribe(published => {
            this.published = published;
        });
        if (path) {
            this.baseUrl = path;
        }
        this.doPeriodicBackendProcessingCheck = true;
    }

    /**
     * Checks if backend processing is running for the given reference
     * 
     * @param reference Object reference to check for running jobs
     * @returns Observable that emits true if processing is running, false otherwise
     */
    protected override checkBackendProcessing(reference: { className?: string; code?: string; }): Observable<boolean> {
        return this.JobLauncherControllerService.getHasRunningJobs(reference).pipe(map(r => r?.hasRunningJobs === true));
    }

    /**
     * Initializes the component, loading necessary data from backend services
     */
    override ngOnInit(): void {
        super.ngOnInit();
        this.loadingRelatedBackend = true;
        this.uploadsControllerService.getUploadableFilesExtensions().subscribe({
            next: (list) => {
                if (list) {
                    list.push(".zip");
                    this.filesExtensionsList = list;
                    let extensionsFlat: string = "";
                    list.forEach((x, i) => {
                        extensionsFlat += x;
                        if ((i + 1) < list.length) {
                            extensionsFlat += ",";
                        }

                    });
                    this.filesExtensionsFlatList = extensionsFlat;
                }
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    /**
     * Handles operations when new data is created
     * 
     * @param actualValue The newly created entity
     */
    protected override onNewData(actualValue: GUploadsProjectEndpoint): void {
        // A data source that does not exist yet owns no folder: files are added
        // once it is saved.
        this.contentsEndpointCode = undefined;
        this.viewedFile = undefined;
    }

    /**
     * Handles operations when persistent data is loaded
     * 
     * @param actualValue The loaded entity
     */
    protected override onLoadedPersistentData(actualValue: GUploadsProjectEndpoint): void {
        this.contentsEndpointCode = actualValue?.code;
        this.viewedFile = undefined;
    }


    /**
     * Opens a file of the data source in the contents viewer.
     *
     * @param file The file of the data source to open
     */
    onViewFile(file: UploadedFileNode): void {
        const path: string | undefined = file?.relativePath;
        const name: string | undefined = file?.name;
        if (!this.contentsEndpointCode || !path || !name) return;
        this.viewedFile = {
            url: this.baseUrl + serveContentsUrl
                + "?endpointCode=" + encodeURIComponent(this.contentsEndpointCode)
                + "&path=" + encodeURIComponent(path),
            fileName: name
        };
    }

    /**
     * Closes the contents viewer.
     */
    closeViewedFile(): void {
        this.viewedFile = undefined;
    }


    /**
     * Finds an uploads endpoint by its code
     * 
     * @param code The code to search for
     * @returns Observable that emits the found entity or null
     */
    override findByCode(code: string): Observable<GUploadsProjectEndpoint | null> {
        return this.uploadsControllerService.findUploadsEndpointsByQbe({ code: code }).pipe(map(v => v && v.length ? v[0] : null));
    }

    /**
     * Saves an existing uploads endpoint
     * 
     * @param value The entity to save
     * @returns Observable that emits the saved entity
     */
    override save(value: GUploadsProjectEndpoint): Observable<GUploadsProjectEndpoint> {
        return this.uploadsControllerService.updateUploadsEndpoint(value);
    }

    /**
     * Inserts a new uploads endpoint
     * 
     * @param value The entity to insert
     * @returns Observable that emits the inserted entity
     */
    override insert(value: GUploadsProjectEndpoint): Observable<GUploadsProjectEndpoint> {
        return this.uploadsControllerService.insertUploadsEndpoint(value);
    }

    /**
     * Deletes an uploads endpoint
     * 
     * @param value The entity to delete
     * @returns Observable that emits true if deletion was successful
     */
    override delete(value: GUploadsProjectEndpoint): Observable<boolean> {
        return this.uploadsControllerService.deleteUploadsEndpoint(value);
    }

    /**
     * Checks if an entity can be deleted
     * 
     * @param value The entity to check
     * @returns Observable that emits an object indicating if deletion is allowed and any message
     */
    override canBeDeleted(value: GUploadsProjectEndpoint): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }


    /**
     * Saves the entity and publishes it in one operation
     */
    doSaveAndPublish() {
        doSaveAndPublishCall(this);
    }

    /**
     * Publishes the uploads endpoint by creating a backend job
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
                            contextType: "GUploadsProjectEndpoint",
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