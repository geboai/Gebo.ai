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
 * This file defines a component for fast configuration of Google Drive integration.
 * It handles the creation of Google Drive system configurations through a form interface.
 */

import { Component, EventEmitter, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from "@angular/forms";
import { FastGoogleDriveSystemInsert, GGoogleDriveSystem, GoogleDriveSystemsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST } from "@Gebo.ai/reusable-ui";
import { ToastMessageOptions } from "primeng/api";

/**
 * Custom validator function that checks if a string is valid JSON.
 * Returns a validation error if the string cannot be parsed as JSON.
 * @param control The form control to validate
 * @returns ValidationErrors object with jsonContent error or null if valid
 */
const jsonValidator: ValidatorFn = (control: AbstractControl) => {
    let out: ValidationErrors | null = { "jsonContent": "Invalid json" };
    const value = control.value;
    try {
        const object = JSON.parse(value);
        if (object) {
            out = null;
        }
    } catch (e) { }
    return out;
}

/**
 * Component that provides a form interface for quickly setting up a Google Drive integration.
 * It collects a description and JSON credentials, then sends them to the API to create 
 * a new Google Drive system configuration.
 */
@Component({
    selector: "gebo-ai-google-drive-fast-component",
    templateUrl: "gebo-ai-google-drive-fast.component.html",
    standalone: false, providers: [{
        provide: GEBO_AI_FIELD_HOST, multi: true, useValue: fieldHostComponentName("GeboAIGoogleDriveFastComponent")
    }]
})
export class GeboAIGoogleDriveFastComponent implements OnInit, OnChanges {
    /** Flag to indicate if an API operation is in progress */
    public loading: boolean = false;

    /** Collection of messages to display to the user */
    public userMessages: ToastMessageOptions[] = [];

    /** Event emitter that triggers when the user cancels the operation */
    @Output() public cancelAction: EventEmitter<boolean> = new EventEmitter();

    /** Event emitter that triggers when a new Google Drive system is successfully created */
    @Output() public insertedGoogleDriveSystemEvent: EventEmitter<GGoogleDriveSystem> = new EventEmitter();

    /** Form group containing the description and JSON content fields */
    public formGroup: FormGroup = new FormGroup({
        description: new FormControl(null, [Validators.required]),
        jsonContent: new FormControl(null, [Validators.required, jsonValidator])
    });

    /**
     * Constructor that injects the Google Drive systems controller service
     * @param googleDriveController Service for interacting with Google Drive API
     */
    constructor(private googleDriveController: GoogleDriveSystemsControllerService) {

    }

    /**
     * Lifecycle hook that runs when the component is initialized
     */
    ngOnInit(): void {

    }

    /**
     * Lifecycle hook that runs when component inputs change
     * @param changes Simple changes object containing current and previous values
     */
    ngOnChanges(changes: SimpleChanges): void {

    }

    /**
     * Handles the cancel action by emitting a cancel event
     */
    doCancel(): void {
        this.cancelAction.emit(true);
    }

    /**
     * Handles form submission by preparing and sending the Google Drive system 
     * configuration data to the API, then emitting the result if successful
     */
    doInsert(): void {
        this.loading = true;
        const value: { description: string, jsonContent: string } = this.formGroup.value;
        const data: FastGoogleDriveSystemInsert = {
            description: value.description,
            googleJsonCredentials: {
                jsonContent: value.jsonContent
            }
        };
        this.googleDriveController.fastGoogleDriveConfig(data).subscribe({
            next: (value) => {
                this.userMessages = value.messages as ToastMessageOptions[];
                if (value.hasErrorMessages !== true) {
                    this.insertedGoogleDriveSystemEvent.emit(value.result);
                }
            },
            complete: () => {
                this.loading = false;
            }

        })
    }

}