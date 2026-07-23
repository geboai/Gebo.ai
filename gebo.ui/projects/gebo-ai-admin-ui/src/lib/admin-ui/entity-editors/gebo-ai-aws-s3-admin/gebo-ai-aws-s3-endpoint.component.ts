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
 * This file contains the GeboAIAwsS3EndpointComponent, which is responsible for managing
 * AWS S3 project endpoints in the Gebo.ai application. The component extends BaseEntityEditingComponent
 * to provide editing functionality for AWS S3 endpoints, including creating, updating, and deleting operations,
 * mirroring the SharePoint endpoint component.
 */

import { Component, forwardRef, Injector, Input } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { AwsS3BrowsingControllerService, AwsS3SystemsControllerService, BrowseParam, GAwsS3ProjectEndpoint, GAwsS3System, GProject, JobLauncherControllerService, ProjectsControllerService, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboActionPerformedEvent, GeboActionType, GeboFormGroupsService, GeboUIActionRequest, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions } from "primeng/api";
import { forkJoin, map, Observable, of } from "rxjs";
import { doSaveAndPublishCall } from '../utils/save-publish-callback';
import { loadRootsObservableCallback, browsePathObservableCallback } from "@Gebo.ai/reusable-ui";

/**
 * Constant defining the AWS S3 content handler code identifier used throughout the component
 */
const awsS3Code: string = "aws-s3-handler";

/**
 * Component for managing AWS S3 endpoints within the Gebo.ai system.
 * This component provides the UI and logic needed for creating, editing, and managing
 * AWS S3 project endpoints, including connection to AWS S3 systems, browsing buckets/prefixes,
 * and configuring synchronization settings.
 */
@Component({
    selector: "gebo-ai-aws-s3-endpoint-component",
    templateUrl: "gebo-ai-aws-s3-endpoint.component.html",
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIAwsS3Module", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIAwsS3EndpointComponent), multi: false
    }],
    standalone: false
})
export class GeboAIAwsS3EndpointComponent extends BaseEntityEditingComponent<GAwsS3ProjectEndpoint> {
    /**
     * The entity name used for identification in the component
     */
    protected override entityName: string = "GAwsS3ProjectEndpoint";

    /**
     * Flag to control whether the project can be modified
     */
    @Input() cantModifyProject: boolean = true;

    /**
     * Observable that provides a list of available projects
     */
    projectsObservable: Observable<GProject[]> = this.projectsController.getProjects();

    /**
     * The form group containing all controls for the AWS S3 endpoint configuration
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
        s3SystemCode: new FormControl(),
        paths: new FormControl(),
        extractedFormat: new FormControl(),
        extractAndSaveContents: new FormControl(),
        openZips: new FormControl()

    });

    /**
     * Flag indicating if the endpoint is published
     */
    published: boolean = false;

    /**
     * List of available AWS S3 systems
     */
    awsS3ServersData: GAwsS3System[] = [];

    /**
     * Action request configuration for creating a new AWS S3 system
     */
    newAwsS3ServerRequest: GeboUIActionRequest = {
        actionType: GeboActionType.NEW,
        context: {},
        contextType: this.entityName,
        targetType: "GAwsS3System",
        target: { contentManagementSystemType: awsS3Code } as GAwsS3System
    };

    /**
     * The current identity context for AWS S3
     */
    private actualIdentityContext: string = awsS3Code;

    /**
     * Flag indicating if there are no accounts or systems available
     */
    public noAccountsAndSystems: boolean = false;

    /**
     * The most recently selected AWS S3 system code
     */
    private lastAwsS3SystemCode: string = "";

    /**
     * Available secret identities for AWS S3 connections
     */
    identities: SecretInfo[] = [];

    /**
     * Callback for loading root nodes in the AWS S3 system
     */
    public loadRootsObservable: loadRootsObservableCallback = () => of({});

    /**
     * Callback for browsing paths in the AWS S3 system
     */
    public browsePathObservable: browsePathObservableCallback = (param: BrowseParam) => of({});

    /**
     * Constructor initializing the component with required services and setting up subscriptions
     * for form control changes and AWS S3 browsing capabilities.
     */
    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private awsS3ControllerService: AwsS3SystemsControllerService,
        private awsS3Browsing: AwsS3BrowsingControllerService,
        private secretControllerService: SecretsControllerService,
        private projectsController: ProjectsControllerService,
        private JobLauncherControllerService: JobLauncherControllerService,
        private actionsRouter: GeboUIActionRoutingService,
        confirmService: ConfirmationService,
        outputForwardingService?: GeboUIOutputForwardingService
    ) {
        super(injector, geboFormGroupsService, confirmService, actionsRouter, outputForwardingService);
        this.formGroup.controls["published"].valueChanges.subscribe(published => {
            this.published = published;
        });
        this.doPeriodicBackendProcessingCheck = true;
        this.formGroup.controls["s3SystemCode"].valueChanges.subscribe(x => {
            if (x && this.lastAwsS3SystemCode !== x) {
                this.lastAwsS3SystemCode = x;
                this.loadRootsObservable = () => {
                    return this.awsS3Browsing.getAwsS3Roots(this.lastAwsS3SystemCode);
                };
                this.browsePathObservable = (param: BrowseParam) => {
                    return this.awsS3Browsing.browseAwsS3Path(param, this.lastAwsS3SystemCode);
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
     * Checks for and loads existing AWS S3 accounts and systems
     * @param system Optional system to set as the selected system after loading
     */
    public checkExistentAccountAndSystem(system?: GAwsS3System) {
        const observables: [Observable<SecretInfo[]>, Observable<GAwsS3System[]>] = [this.secretControllerService.getSecretsByContextCode(this.actualIdentityContext), this.awsS3ControllerService.getAwsS3Systems()];
        this.loadingRelatedBackend = true;
        forkJoin(observables).subscribe({
            next: (data) => {
                this.identities = data[0];
                this.awsS3ServersData = data[1];
                this.noAccountsAndSystems = (!this.identities || this.identities.length === 0) && (!this.awsS3ServersData || this.awsS3ServersData.length === 0);
                if (system && system.code) {
                    this.formGroup.controls["s3SystemCode"].setValue(system.code);
                }
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    /**
     * Handles special processing when new data is provided to the component
     * @param actualValue The new AWS S3 endpoint data
     */
    protected override onNewData(actualValue: GAwsS3ProjectEndpoint): void {

    }

    /**
     * Handles special processing when existing data is loaded into the component
     * @param actualValue The loaded AWS S3 endpoint data
     */
    protected override onLoadedPersistentData(actualValue: GAwsS3ProjectEndpoint): void {

    }

    /**
     * Fetches an AWS S3 endpoint by its code
     * @param code The code of the endpoint to find
     * @returns An observable with the found endpoint or null
     */
    override findByCode(code: string): Observable<GAwsS3ProjectEndpoint | null> {
        return this.awsS3ControllerService.findAwsS3ProjectEndpointByCode(code);
    }

    /**
     * Saves an existing AWS S3 endpoint
     * @param value The endpoint data to save
     * @returns An observable with the saved endpoint
     */
    override save(value: GAwsS3ProjectEndpoint): Observable<GAwsS3ProjectEndpoint> {
        return this.awsS3ControllerService.updateAwsS3ProjectEndpoint(value);
    }

    /**
     * Inserts a new AWS S3 endpoint
     * @param value The endpoint data to insert
     * @returns An observable with the inserted endpoint
     */
    override insert(value: GAwsS3ProjectEndpoint): Observable<GAwsS3ProjectEndpoint> {
        return this.awsS3ControllerService.insertAwsS3ProjectEndpoint(value);
    }

    /**
     * Deletes an AWS S3 endpoint
     * @param value The endpoint to delete
     * @returns An observable indicating success or failure
     */
    override delete(value: GAwsS3ProjectEndpoint): Observable<boolean> {
        return this.awsS3ControllerService.deleteAwsS3ProjectEndpoint(value) as unknown as Observable<boolean>;
    }

    /**
     * Checks if an AWS S3 endpoint can be deleted
     * @param value The endpoint to check
     * @returns An observable with deletion status and message
     */
    override canBeDeleted(value: GAwsS3ProjectEndpoint): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

    /**
     * Performs a save and publish operation on the endpoint
     */
    doSaveAndPublish() {
        doSaveAndPublishCall(this);
    }

    /**
     * Publishes the AWS S3 endpoint by creating a background job
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
                            contextType: "GAwsS3ProjectEndpoint",
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
