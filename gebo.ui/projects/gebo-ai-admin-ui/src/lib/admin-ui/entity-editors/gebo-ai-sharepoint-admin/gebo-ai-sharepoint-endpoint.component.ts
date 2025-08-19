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
 * This file contains the GeboAISharepointEndpointComponent, which is responsible for managing
 * SharePoint project endpoints in the Gebo.ai application. The component extends BaseEntityEditingComponent
 * to provide editing functionality for SharePoint endpoints, including creating, updating, and deleting operations.
 */

import { Component, Injector, Input } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { BrowseParam, GProject, JobLauncherControllerService, ProjectsControllerService, GConfluenceSystem, SecretInfo, SecretsControllerService, GSharepointProjectEndpoint, SharepointBrowsingControllerService, SharepointSystemsControllerService, GSharepointContentManagementSystem, AuthProviderDto } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GeboActionPerformedEvent, GeboActionType, GeboAIFileType, GeboFormGroupsService, GeboUIActionRequest, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions, MessageService } from "primeng/api";
import { UploadEvent } from "primeng/fileupload";
import { forkJoin, map, Observable, of } from "rxjs";
import { doSaveAndPublishCall } from '../utils/save-publish-callback';
import { loadRootsObservableCallback, browsePathObservableCallback } from "@Gebo.ai/reusable-ui";
import { SharepointUrlService } from "./confluence-url.service";

/**
 * Constant defining the SharePoint code identifier used throughout the component
 */
const sharepointCode: string = "sharepoint-module";

/**
 * Component for managing SharePoint endpoints within the Gebo.ai system.
 * This component provides the UI and logic needed for creating, editing, and managing 
 * SharePoint project endpoints, including connection to SharePoint systems, browsing paths,
 * and configuring synchronization settings.
 */
@Component({
    selector: "gebo-ai-sharepoint-endpoint-component",
    templateUrl: "gebo-ai-sharepoint-endpoint.component.html",
    providers: [MessageService, SharepointUrlService],
    standalone: false
})
export class GeboAISharepointEndpointComponent extends BaseEntityEditingComponent<GSharepointProjectEndpoint> {
    /**
     * The entity name used for identification in the component
     */
    protected override entityName: string = "GSharepointProjectEndpoint";

    /**
     * The handshake code used for authentication in file uploads
     */
    public handShakeCode?: string;

    /**
     * Flag to control whether the project can be modified
     */
    @Input() cantModifyProject: boolean = true;

    /**
     * Observable that provides a list of available projects
     */
    projectsObservable: Observable<GProject[]> = this.projectsController.getProjects();

    /**
     * The form group containing all controls for the SharePoint endpoint configuration
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
        sharePointSystemCode: new FormControl(),
        paths: new FormControl(),
        extractedFormat: new FormControl(),
        extractAndSaveContents: new FormControl(),
        sharepointVersion: new FormControl(),
        openZips: new FormControl()

    });

    /**
     * Flag indicating if the endpoint is published
     */
    published: boolean = false;

    /**
     * List of available SharePoint server systems
     */
    sharepointServersData: GSharepointContentManagementSystem[] = [];

    /**
     * Action request configuration for creating a new SharePoint server
     */
    newsharepointServerRequest: GeboUIActionRequest = {
        actionType: GeboActionType.NEW,
        context: {},
        contextType: this.entityName,
        targetType: "GSharepointContentManagementSystem",
        targetFormInputs: { oauth2ForcedProviderName: AuthProviderDto.ProviderEnum.MicrosoftMultitenant },
        target: { contentManagementSystemType: sharepointCode } as GSharepointContentManagementSystem
    };

    /**
     * The current identity context for SharePoint
     */
    private actualIdentityContext: string = sharepointCode;

    /**
     * Flag indicating if there are no accounts or systems available
     */
    public noAccountsAndSystems: boolean = false;

    /**
     * The most recently selected SharePoint system code
     */
    private lastConfluenceSystemCode: string = "";

    /**
     * Available secret identities for SharePoint connections
     */
    identities: SecretInfo[] = [];

    /**
     * Callback for loading root nodes in SharePoint system
     */
    public loadRootsObservable: loadRootsObservableCallback = () => of({});

    /**
     * Callback for browsing paths in SharePoint system
     */
    public browsePathObservable: browsePathObservableCallback = (param: BrowseParam) => of({});

    /**
     * List of available file types for SharePoint
     */
    fileTypesList: GeboAIFileType[] = [];

    /**
     * Constructor initializing the component with required services and setting up subscriptions
     * for form control changes and SharePoint browsing capabilities.
     */
    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private sharepointControllerService: SharepointSystemsControllerService,
        private sharepointBrowsing: SharepointBrowsingControllerService,
        private secretControllerService: SecretsControllerService,
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
        this.formGroup.controls["sharePointSystemCode"].valueChanges.subscribe(x => {
            if (x && this.lastConfluenceSystemCode !== x) {
                this.lastConfluenceSystemCode = x;
                this.loadRootsObservable = () => {
                    return this.sharepointBrowsing.getSharepointRoots(this.lastConfluenceSystemCode);
                };
                this.browsePathObservable = (param: BrowseParam) => {
                    return this.sharepointBrowsing.browseSharepointPath(param, this.lastConfluenceSystemCode);
                };


            }
        });
    }

    /**
     * Checks if there are any backend processes running for the given reference
     * @param reference The object reference to check for running jobs
     * @returns An observable that emits true if jobs are running, false otherwise
     */
    protected override checkBackendProcessing(reference: { className?: string; code?: string; }): Observable<boolean> {
        return this.JobLauncherControllerService.getHasRunningJobs(reference).pipe(map(r => r?.hasRunningJobs === true));
    }

    /**
     * Initializes the component and checks for existing accounts and systems
     */
    override ngOnInit(): void {
        super.ngOnInit();
        this.checkExistentAccountAndSystem();
    }

    /**
     * Checks for and loads existing SharePoint accounts and systems
     * @param system Optional system to set as the selected system after loading
     */
    public checkExistentAccountAndSystem(system?: GConfluenceSystem) {
        const observables: [Observable<SecretInfo[]>, Observable<GSharepointContentManagementSystem[]>] = [this.secretControllerService.getSecretsByContextCode(this.actualIdentityContext), this.sharepointControllerService.getSharepointSystems()];
        this.loadingRelatedBackend = true;
        forkJoin(observables).subscribe({
            next: (data) => {
                this.identities = data[0];
                this.sharepointServersData = data[1];
                this.noAccountsAndSystems = (!this.identities || this.identities.length === 0) && (!this.sharepointServersData || this.sharepointServersData.length === 0);
                if (system && system.code) {
                    this.formGroup.controls["sharePointSystemCode"].setValue(system.code);
                }
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    /**
     * Handles special processing when new data is provided to the component
     * @param actualValue The new SharePoint endpoint data
     */
    protected override onNewData(actualValue: GSharepointProjectEndpoint): void {

    }

    /**
     * Handles special processing when existing data is loaded into the component
     * @param actualValue The loaded SharePoint endpoint data
     */
    protected override onLoadedPersistentData(actualValue: GSharepointProjectEndpoint): void {

    }

    /**
     * Fetches a SharePoint endpoint by its code
     * @param code The code of the endpoint to find
     * @returns An observable with the found endpoint or null
     */
    override findByCode(code: string): Observable<GSharepointProjectEndpoint | null> {
        return this.sharepointControllerService.findSharepointEndpointsByCode(code);
    }

    /**
     * Saves an existing SharePoint endpoint
     * @param value The endpoint data to save
     * @returns An observable with the saved endpoint
     */
    override save(value: GSharepointProjectEndpoint): Observable<GSharepointProjectEndpoint> {
        return this.sharepointControllerService.updateSharepointEndpoint(value);
    }

    /**
     * Inserts a new SharePoint endpoint
     * @param value The endpoint data to insert
     * @returns An observable with the inserted endpoint
     */
    override insert(value: GSharepointProjectEndpoint): Observable<GSharepointProjectEndpoint> {
        return this.sharepointControllerService.insertSharepointEndpoint(value);
    }

    /**
     * Deletes a SharePoint endpoint
     * @param value The endpoint to delete
     * @returns An observable indicating success or failure
     */
    override delete(value: GSharepointProjectEndpoint): Observable<boolean> {
        return this.sharepointControllerService.deleteSharepointEndpoint(value);
    }

    /**
     * Checks if a SharePoint endpoint can be deleted
     * @param value The endpoint to check
     * @returns An observable with deletion status and message
     */
    override canBeDeleted(value: GSharepointProjectEndpoint): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

    /**
     * Handles automatic file upload events
     * @param event The upload event containing file data
     */
    onBasicUploadAuto(event: UploadEvent) {
        this.formGroup.controls["uploadHandshakeCode"].setValue(this.handShakeCode);
        this.messageService.add({ severity: 'success', summary: 'Success', detail: 'File Uploaded with success' });
    }

    /**
     * Performs a save and publish operation on the endpoint
     */
    doSaveAndPublish() {
        doSaveAndPublishCall(this);
    }

    /**
     * Publishes the SharePoint endpoint by creating a background job
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
                            contextType: "GSharepointProjectEndpoint",
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