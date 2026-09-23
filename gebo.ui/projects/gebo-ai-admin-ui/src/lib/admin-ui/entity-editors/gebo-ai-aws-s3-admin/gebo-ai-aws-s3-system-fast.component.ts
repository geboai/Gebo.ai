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
 * This module provides a component for quickly creating a new AWS S3 content system connection.
 * It manages a form for collecting the AWS connection details (access key, secret key and region)
 * and an optional custom endpoint, and handles the submission process to the backend service,
 * mirroring the SharePoint fast setup component.
 */
import { Component, EventEmitter, OnInit, Output } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { AwsS3SystemsControllerService, FastAwsS3SystemInsertRequest, GAwsS3System, GeboAwsConnectionCredentials } from "@Gebo.ai/gebo-ai-rest-api";
import { ToastMessageOptions } from "primeng/api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";

/**
 * Component for rapid creation of AWS S3 system connections in the Gebo.ai platform.
 * Provides a form interface for users to enter the AWS connection details (access key id,
 * secret access key and region) plus an optional custom S3-compatible endpoint.
 * The component communicates with backend services to create the system connection
 * and emits events to notify parent components of the result.
 */
@Component({
    selector: "gebo-ai-aws-s3-system-fast-component",
    templateUrl: "gebo-ai-aws-s3-system-fast.component.html",
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIAwsS3Module", multi: false },
        { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIAwsS3SystemFastComponent") }
    ], standalone: false
})
export class GeboAIAwsS3SystemFastComponent implements OnInit {
    /** Flag to indicate if an API operation is in progress */
    public loading: boolean = false;

    /**
     * Form group that manages the AWS S3 system configuration inputs, including:
     * - description: A human-readable name for the system
     * - awsEndpoint: An optional custom S3-compatible endpoint
     * - awsConnectionCredentials: Contains the access key id, secret access key and region
     */
    public formGroup: FormGroup = new FormGroup({
        description: new FormControl(),
        awsEndpoint: new FormControl(),
        awsConnectionCredentials: new FormGroup({
            accessKeyId: new FormControl(),
            secretAccessKey: new FormControl(),
            region: new FormControl()
        })
    });

    /** Collection of messages to display to the user after operations */
    userMessages: ToastMessageOptions[] = [];

    /** Selectable AWS regions (value = AWS region code). */
    public awsRegionOptions: { label: string, value: GeboAwsConnectionCredentials.RegionEnum }[] =
        Object.values(GeboAwsConnectionCredentials.RegionEnum).map(region => ({ label: region, value: region }));

    /** Event emitter that fires when a new AWS S3 system is successfully created */
    @Output() newAwsS3SystemEvent: EventEmitter<GAwsS3System> = new EventEmitter();

    /** Event emitter that fires when the user cancels the creation process */
    @Output() cancelAction: EventEmitter<boolean> = new EventEmitter();

    /**
     * Constructor initializes the component with necessary services and sets default values
     *
     * @param awsS3SystemsService Service to handle AWS S3 system API operations
     */
    constructor(private awsS3SystemsService: AwsS3SystemsControllerService) {
        this.formGroup.controls["description"].setValue("AWS S3 system");
    }

    get awsConnectionCredentials(): FormGroup {
        return this.formGroup.controls["awsConnectionCredentials"] as FormGroup;
    }

    /**
     * Lifecycle hook that is called after component initialization.
     */
    ngOnInit(): void {
    }

    /**
     * Submits the form data to create a new AWS S3 system configuration.
     * Collects form values, calls the API service, and emits the result to parent components.
     */
    doInsert(): void {
        const data: FastAwsS3SystemInsertRequest = this.formGroup.value;
        this.loading = true;
        this.awsS3SystemsService.fastAwsS3Config(data).subscribe({
            next: (result) => {
                this.userMessages = result.messages as ToastMessageOptions[];
                if (result.result && result.hasErrorMessages !== true) {
                    this.newAwsS3SystemEvent.emit(result.result);
                }
            },
            complete: () => {
                this.loading = false;
            }
        });
    }
}
