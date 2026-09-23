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
import { BASE_PATH, BrowseParam, FileUploadControllerService, GUploadsProjectEndpoint, GProject, JobLauncherControllerService, ProjectsControllerService, FileUploadsControllerService, UploadedFileInfo, UploadsBrowsingControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, browsePathObservableCallback, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboActionPerformedEvent, GeboActionType, GeboAIFileType, GeboAIRootNotificationService, GeboFormGroupsService, GeboUIActionRequest, GeboUIActionRoutingService, GeboUIOutputForwardingService, loadRootsObservableCallback, reconstructNavigationObservableCallback, ServedFileReference, VFilesystemDeletableReference, VFilesystemReference, VFilesystemSelectorComponent } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions } from "primeng/api";
import { FileBeforeUploadEvent, FileProgressEvent, UploadEvent } from "primeng/fileupload";
import { map, Observable, of, switchMap } from "rxjs";
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

    /** Authentication token for upload operations */
    public handShakeCode?: string;

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
        uploadedContents: new FormControl(),
        contentManagementSystem: new FormControl()
    });

    /** Flag indicating if the endpoint is published */
    published: boolean = false;

    /** List of allowed file extensions for upload */
    filesExtensionsList: String[] = [".zip"];

    /** Flat string representation of allowed file extensions */
    filesExtensionsFlatList: string = ".zip";

    /** List of allowed file types for upload */
    fileTypesList: GeboAIFileType[] = [];

    /** The contents browser of this data source, refreshed after every change */
    @ViewChild("contentsSelector") contentsSelector?: VFilesystemSelectorComponent;

    /**
     * Code of the data source whose contents are being managed. It is only known
     * once the endpoint exists: while creating one the files go through the
     * handshake staging area instead.
     */
    public contentsEndpointCode?: string;

    /** Files signed for deletion in the contents browser, applied on save */
    public pendingDeletions: VFilesystemDeletableReference[] = [];

    /** Files currently held by the data source, used for the contents summary */
    public uploadedFiles: UploadedFileInfo[] = [];

    /**
     * The file the admin asked to open, undefined when the viewer is closed.
     *
     * What is managed here is what the data source physically holds, ingested or
     * not, so the file is served straight out of the folder of the data source
     * instead of through the knowledge base contents controller, which only knows
     * the documents that have already been published.
     */
    public viewedFile?: ServedFileReference;

    /** Loads the only browsing root of this data source: its contents folder */
    public loadRootsObservable: loadRootsObservableCallback = () => {
        return this.contentsEndpointCode
            ? this.uploadsBrowsingService.getUploadsEndpointRoots(this.contentsEndpointCode)
            : of({});
    };

    /** Lists the children of a folder of this data source */
    public browsePathObservable: browsePathObservableCallback = (param: BrowseParam) => {
        return this.contentsEndpointCode
            ? this.uploadsBrowsingService.browseUploadsEndpointPath(param, this.contentsEndpointCode)
            : of({});
    };

    /** Rebuilds the navigation tree down to the given entries */
    public reconstructNavigationObservableCallback: reconstructNavigationObservableCallback = (navigationPoints: VFilesystemReference[]) => {
        return this.contentsEndpointCode
            ? this.uploadsBrowsingService.getUploadsEndpointNavigationStatus(navigationPoints, this.contentsEndpointCode)
            : of({});
    };

    /**
     * Constructor initializes services and sets up subscriptions
     * 
     * @param injector Angular injector for dependency injection
     * @param geboFormGroupsService Service for managing form groups
     * @param uploadsControllerService Service for managing file uploads
     * @param projectsController Service for accessing projects
     * @param JobLauncherControllerService Service for launching background jobs
     * @param actionsRouter Service for routing UI actions
     * @param messageService Service for displaying messages to the user
     * @param uploadControllerService Service for file upload operations
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
        private messageService: GeboAIRootNotificationService,
        private uploadControllerService: FileUploadControllerService,
        private uploadsBrowsingService: UploadsBrowsingControllerService,

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
        this.uploadControllerService.getHandShakeCode().subscribe(
            {
                next: (v) => {
                    this.handShakeCode = v.token;
                }
            }
        );
    }

    /**
     * Handles operations when new data is created
     * 
     * @param actualValue The newly created entity
     */
    protected override onNewData(actualValue: GUploadsProjectEndpoint): void {
        // A data source that does not exist yet owns no contents folder: uploads
        // stay staged under the handshake code until the first save.
        this.contentsEndpointCode = undefined;
        this.pendingDeletions = [];
        this.uploadedFiles = [];
        this.viewedFile = undefined;
    }

    /**
     * Handles operations when persistent data is loaded
     * 
     * @param actualValue The loaded entity
     */
    protected override onLoadedPersistentData(actualValue: GUploadsProjectEndpoint): void {
        this.contentsEndpointCode = actualValue?.code;
        this.pendingDeletions = [];
        this.viewedFile = undefined;
        this.refreshUploadedFiles();
    }

    /**
     * Reloads the summary of the files held by the data source.
     */
    refreshUploadedFiles(): void {
        if (!this.contentsEndpointCode) {
            this.uploadedFiles = [];
            return;
        }
        this.uploadsControllerService.listUploadedFiles(this.contentsEndpointCode).subscribe({
            next: (files) => {
                this.uploadedFiles = files ? files : [];
            },
            error: () => {
                this.uploadedFiles = [];
            }
        });
    }

    /** Number of files currently held by the data source */
    get uploadedFilesCount(): number {
        return this.uploadedFiles.length;
    }

    /**
     * Endpoint the file uploader posts to.
     *
     * While the data source exists the files go straight into its contents folder;
     * during creation there is no folder yet, so they are staged under the
     * handshake code and moved when the data source is saved.
     */
    get uploadUrl(): string {
        return this.contentsEndpointCode
            ? this.baseUrl + '/api/admin/FileUploadController/uploadToEndpoint/' + this.contentsEndpointCode
            : this.baseUrl + '/api/admin/FileUploadController/upload/' + this.handShakeCode;
    }

    /** True when the uploader can be used */
    get canUploadContents(): boolean {
        return this.contentsEndpointCode ? true : (this.handShakeCode ? true : false);
    }

    /** True when the data source already owns a browsable contents folder */
    get hasContentsFolder(): boolean {
        return this.contentsEndpointCode ? true : false;
    }

    /**
     * Opens a file of the data source in the contents viewer.
     *
     * @param reference The entry the contents browser asked to open
     */
    onViewFile(reference: VFilesystemReference): void {
        const path: string | undefined = reference?.path?.absolutePath;
        const name: string | undefined = reference?.path?.name;
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
     * Collects the deletion intents expressed in the contents browser. Nothing is
     * removed here: the files leave the data source when the editing is saved.
     *
     * @param deletions The entries signed for deletion
     */
    onContentsDeletionsChange(deletions: VFilesystemReference[]): void {
        this.pendingDeletions = deletions ? deletions : [];
    }

    /**
     * Entries signed for deletion, addressed by their absolute path so a file
     * nested in a subfolder is not confused with a file of the same name sitting
     * at the root of the data source.
     */
    get pendingDeletionNames(): string[] {
        const names: string[] = [];
        this.pendingDeletions.forEach(x => {
            const reference: string | undefined = x.path?.absolutePath ? x.path.absolutePath : x.path?.name;
            if (reference) {
                names.push(reference);
            }
        });
        return names;
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
        const names: string[] = this.pendingDeletionNames;
        const saved: Observable<GUploadsProjectEndpoint> = this.uploadsControllerService.updateUploadsEndpoint(value);
        if (!names.length || !this.contentsEndpointCode) {
            return saved;
        }
        const endpointCode: string = this.contentsEndpointCode;
        // The deletion runs after the settings update on purpose: the form still
        // carries the uploaded contents list as it was loaded, while the backend
        // deletion prunes it authoritatively and returns the resulting data source.
        return saved.pipe(switchMap(updated => {
            return this.uploadsControllerService.deleteUploadedFiles(names, endpointCode).pipe(map(status => {
                this.assignBackendMessages(status?.messages);
                this.pendingDeletions = [];
                this.refreshUploadedFiles();
                this.contentsSelector?.reload();
                return status?.result ? status.result : updated;
            }));
        }));
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
     * Handles successful file upload events
     * 
     * @param event The upload event
     */
    onBasicUploadAuto(event: UploadEvent) {
        if (this.contentsEndpointCode) {
            // The files are already in the contents folder of the data source: no
            // handshake code has to be carried by the entity, the browser and the
            // summary just have to show them.
            this.refreshUploadedFiles();
            this.contentsSelector?.reload();
        } else {
            this.formGroup.controls["uploadHandshakeCode"].setValue(this.handShakeCode);
        }
        this.messageService.addMessage("GeboAIUploadsModule","GeboAIUploadsEndpointComponent",{id:"FILE_UPLOAD_SUCCESS", severity: 'success', summary: 'Success', detail: 'File Uploaded with success' });
        this.loadingRelatedBackend = false;
    }

    /**
     * Handles events that occur before a file upload starts
     * 
     * @param event The before upload event
     */
    onBeforeUpload(event: FileBeforeUploadEvent) {
        //this.loadingRelatedBackend=true;
        console.log("onBeforeUpload");
    }

    /**
     * Handles file upload progress events
     * 
     * @param event The progress event
     */
    onProgress(event: FileProgressEvent) {
        //throw new Error('Method not implemented.');
        this.loadingRelatedBackend = true;
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