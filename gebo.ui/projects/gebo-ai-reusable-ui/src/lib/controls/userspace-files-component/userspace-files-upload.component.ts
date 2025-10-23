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
 * This file contains a component for uploading files to a userspace in the Gebo.ai system.
 * It extends BaseEntityEditingComponent to provide editing capabilities for UserspaceFilesUploadModel.
 */

import { Component, Inject, Injector } from "@angular/core";
import { AbstractControl, FormControl, FormGroup, ValidatorFn } from "@angular/forms";

import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { UserspaceFilesUploadModel, UserspaceFilesUploadModelService } from "./userspace-files-upload.service";
import { FileBeforeUploadEvent, FileProgressEvent, FileUploadEvent } from "primeng/fileupload";
import { BASE_PATH, IngestionFileTypesLibraryControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { HttpEventType } from "@angular/common/http";
import { BaseEntityEditingComponent } from "../base-entity-editing-component/base-entity-editing.component";
import { GeboFormGroupsService } from "../../architecture/gebo-form-groups.service";
import { GeboUIActionRoutingService } from "../../architecture/gebo-ui-action-routing.service";
import { GeboUIOutputForwardingService } from "../../architecture/gebo-ui-output-forwarding.service";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST } from "../field-host-component-iface/field-host-component-iface";

/**
 * Component responsible for managing file uploads to user workspace.
 * This component handles the upload process, validation of file types,
 * and provides user feedback during the upload process.
 */
@Component({
    selector: "gebo-ui-userspace-fileuploads-component",
    templateUrl: "userspace-files-upload.component.html",
    providers: [UserspaceFilesUploadModelService, {
        provide: GEBO_AI_FIELD_HOST, multi: true, useValue: fieldHostComponentName("GeboAIUserspaceFilesUploadComponent")
    }],
    standalone: false
})
export class GeboAIUserspaceFilesUploadComponent extends BaseEntityEditingComponent<UserspaceFilesUploadModel> {

    /**
     * The entity name used for identification in the parent component.
     */
    protected override entityName: string = "UserspaceFilesUploadModel";

    /**
     * Flat list of allowed file extensions separated by commas.
     */
    filesExtensionsFlatList: string = "";

    /**
     * Form group for managing the upload form controls.
     */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        userspaceFolder: new FormControl(),
        userspaceFiles: new FormControl()
    });

    /**
     * Form group for managing file deletion.
     */
    deleteFormGroup: FormGroup<any> = new FormGroup({
        choosed: new FormControl()
    });

    /**
     * Flag indicating whether the server is currently executing a process.
     * Used to prevent concurrent operations.
     */
    private serverExecuting: boolean = true;

    /**
     * Component constructor that initializes services and dependencies.
     * @param injector The Angular injector service
     * @param geboFormGroupsService Service for managing form groups
     * @param confirmationService Service for handling user confirmations
     * @param geboUIActionRoutingService Service for UI action routing
     * @param userspaceService Service for userspace file operations
     * @param baseUrl Base URL for API calls
     * @param contentTypeService Service for handling file type information
     * @param outputForwardingService Optional service for forwarding outputs
     */
    constructor(injector: Injector,
        geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        private userspaceService: UserspaceFilesUploadModelService,
        @Inject(BASE_PATH)
        public baseUrl: string,
        private contentTypeService: IngestionFileTypesLibraryControllerService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
        this.manageOperationStatus = true;
    }

    /**
     * Periodically checks if the backend is busy processing files.
     * Sets the serverExecuting flag based on the response.
     */
    private checkBusyBackend() {
        if (this.entity?.userspaceFolder) {
            this.userspaceService.pollStatus(this.entity?.userspaceFolder).subscribe({
                next: (statusValue) => {
                    this.serverExecuting = statusValue.underPubishingAlgorithm === true;

                },
                complete: () => {
                    setTimeout(() => {
                        this.checkBusyBackend();
                    }, 15000);
                }

            })
        }
    }

    /**
     * Initializes the component, sets up validators, and loads file type information.
     * Also starts the periodic background check for server status.
     */
    override ngOnInit(): void {
        super.ngOnInit();
        const executingAlreadyValidator: ValidatorFn = (control: AbstractControl) => {
            if (this.serverExecuting === false) {
                return null;
            } else {
                return {
                    running: "Already running"
                };
            }
        }
        this.formGroup.setValidators(executingAlreadyValidator);
        this.loadingRelatedBackend = true;
        this.contentTypeService.getAllFileTypes().subscribe({
            next: (value) => {
                const extensions: string[] = [];
                value.forEach(x => {
                    if (x.extensions) {
                        x.extensions.forEach(e => {
                            extensions.push(e);
                        });
                    }
                });
                let extstring: string = "";
                extensions.forEach((e, i) => {
                    extstring += e;
                    if (i < extensions.length - 1) {
                        extstring += ",";
                    }
                });
                this.filesExtensionsFlatList = extstring;
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
        this.checkBusyBackend();
    }

    /**
     * Called when persistent data is loaded from the backend.
     * @param actualValue The loaded UserspaceFilesUploadModel
     */
    protected override onLoadedPersistentData(actualValue: UserspaceFilesUploadModel): void {

    }

    /**
     * Finds an upload model by its code identifier.
     * @param code The unique code of the upload model
     * @returns An observable of the found model or null
     */
    override findByCode(code: string): Observable<UserspaceFilesUploadModel | null> {
        return this.userspaceService.findByCode(code);
    }

    /**
     * Publishes the upload model to the server.
     * @param value The upload model to publish
     * @returns An observable of the published model
     */
    private publish(value: UserspaceFilesUploadModel): Observable<UserspaceFilesUploadModel> {
        return this.userspaceService.publish(value).pipe(map(r => {
            this.updateLastOperationStatus(r);
            return r.result ? r.result : value;
        }));
    }

    /**
     * Saves changes to an existing upload model.
     * @param value The upload model to save
     * @returns An observable of the saved model
     */
    override save(value: UserspaceFilesUploadModel): Observable<UserspaceFilesUploadModel> {
        return this.publish(value);
    }

    /**
     * Inserts a new upload model.
     * @param value The upload model to insert
     * @returns An observable of the inserted model
     */
    override insert(value: UserspaceFilesUploadModel): Observable<UserspaceFilesUploadModel> {
        return this.publish(value);
    }

    /**
     * Delete operation (not implemented).
     * @param value The upload model to delete
     * @throws Error as this method is not implemented
     */
    override delete(value: UserspaceFilesUploadModel): Observable<boolean> {
        throw new Error("Method not implemented.");
    }

    /**
     * Checks if a model can be deleted.
     * @param value The upload model to check
     * @returns An observable containing deletion status and message
     */
    override canBeDeleted(value: UserspaceFilesUploadModel): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

    /**
     * Handles the completion of the automatic upload process.
     * @param event The upload event data
     */
    onBasicUploadAuto(event: FileUploadEvent) {
        //this.doReloadByCode();
        console.log("onBasicUploadAuto");
        this.loadingRelatedBackend = false;
        this.doReloadByCode();
    }

    /**
     * Handles the event before the upload begins.
     * @param event The before upload event data
     */
    onBeforeUpload(event: FileBeforeUploadEvent) {
        console.log("onBeforeUpload");
        this.loadingRelatedBackend = true;
    }

    /**
     * Tracks upload progress and handles different HTTP event types.
     * @param event The progress event data
     */
    onProgress(event: FileProgressEvent) {
        if (event.originalEvent.type === HttpEventType.Sent) {
            console.log("onProgress sent");
        }
    }
}