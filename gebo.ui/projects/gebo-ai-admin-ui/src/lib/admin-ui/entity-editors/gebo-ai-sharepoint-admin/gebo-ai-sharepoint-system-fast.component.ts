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
 * This module provides a component for quickly creating a new Sharepoint/OneDrive system connection.
 * It manages a form for collecting Sharepoint connection details and credentials, and handles
 * the submission process to the backend service.
 */
import { Component, EventEmitter, OnInit, Output } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { AuthProviderDto, FastSharepointSystemInsertRequest, GConfluenceSystem, GSharepointContentManagementSystem, SharepointSystemsControllerService, UserControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { ToastMessageOptions } from "primeng/api";
import { SharepointUrlService } from "./confluence-url.service";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";

/**
 * Component for rapid creation of Sharepoint system connections in the Gebo.ai platform.
 * Provides a form interface for users to enter Sharepoint connection details including 
 * base URI and OAuth2 credentials. Currently supports cloud version of Sharepoint.
 * The component communicates with backend services to create the system connection
 * and emits events to notify parent components of the result.
 */
@Component({
    selector: "gebo-ai-sharepoint-system-fast-component",
    templateUrl: "gebo-ai-sharepoint-system-fast.component.html",
    providers: [SharepointUrlService,
        { provide: GEBO_AI_MODULE, useValue: "GeboAISharepointModule", multi: false },  
        { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAISharepointSystemFastComponent") }
    ], standalone: false
})
export class GeboAISharepointSystemFastComponent implements OnInit {
    /** Flag to indicate if an API operation is in progress */
    public loading: boolean = false;

    /**
     * Form group that manages the Sharepoint system configuration inputs, including:
     * - description: A human-readable name for the system
     * - baseUri: The base URL of the Sharepoint instance
     * - oauth2Credentials: Contains tenant ID, client ID and secret for OAuth authentication
     * - sharepointVersion: The version of Sharepoint being connected to
     */
    public formGroup: FormGroup = new FormGroup({
        description: new FormControl(),
        baseUri: new FormControl(),
        oauth2Credentials: new FormGroup({
            clientId: new FormControl(),
            secret: new FormControl(),
            customAttributes: new FormGroup({ tenantId: new FormControl() })
        }),
        sharepointVersion: new FormControl()
    });

    /** Collection of messages to display to the user after operations */
    userMessages: ToastMessageOptions[] = [];

    /** Available Sharepoint versions for selection in the form */
    sharepointVersions: { code: GSharepointContentManagementSystem.SharepointVersionEnum, description: string }[] = [/*{ code: "ONPREMISE2019", description: "On premise 2019 versions" },*/ { code: "CLOUD_VERSION", description: "Cloud version" }];

    /** Currently selected Sharepoint version, defaults to cloud version */
    currentSharepointVersion?: GSharepointContentManagementSystem.SharepointVersionEnum = "CLOUD_VERSION";

    /** Event emitter that fires when a new Sharepoint system is successfully created */
    @Output() newSharepointSystemEvent: EventEmitter<GConfluenceSystem> = new EventEmitter();

    /** Event emitter that fires when the user cancels the creation process */
    @Output() cancelAction: EventEmitter<boolean> = new EventEmitter();

    /**
     * Constructor initializes the component with necessary services and sets default values
     * 
     * @param sharepointSystemsService Service to handle Sharepoint system API operations
     * @param userControllerService Service to handle user-related operations
     */
    constructor(private sharepointSystemsService: SharepointSystemsControllerService, private userControllerService: UserControllerService) {
        this.formGroup.controls["description"].setValue("Sharepoint/OneDrive system");
        this.formGroup.controls["sharepointVersion"].setValue(this.currentSharepointVersion);
    }
    get oauth2Credentials():FormGroup {
        return this.formGroup.controls["oauth2Credentials"] as FormGroup;
    }
    get customAttributes():FormGroup {
        return this.oauth2Credentials.controls["customAttributes"] as FormGroup;
    }
    /**
     * Conditionally enables or disables a form control based on the provided parameter
     * 
     * @param ctrlName The name of the control to enable or disable
     * @param enabled Boolean value indicating whether to enable (true) or disable (false) the control
     */
    setEnabled(ctrlName: string, enabled: boolean) {
        const ctrl = this.formGroup.controls[ctrlName];
        if (enabled === ctrl.enabled) return;
        if (enabled === true) {
            ctrl.enable();
        } else {
            ctrl.disable();
        }
    }

    /**
     * Lifecycle hook that is called after component initialization.
     * Currently contains no implementation.
     */
    ngOnInit(): void {
    }

    /**
     * Submits the form data to create a new Sharepoint system configuration.
     * Collects form values, calls the API service, and emits the result to parent components.
     * Sets loading state during the operation and processes any error messages received.
     */
    doInsert(): void {
        const data: FastSharepointSystemInsertRequest = this.formGroup.value;
        data.oauth2Credentials.providerName = AuthProviderDto.ProviderEnum.MicrosoftMultitenant;
        this.loading = true;
        this.sharepointSystemsService.fastSharepointConfig(data).subscribe({
            next: (result) => {
                this.userMessages = result.messages as ToastMessageOptions[];
                if (result.result && result.hasErrorMessages !== true) {
                    this.newSharepointSystemEvent.emit(result.result);
                }
            },
            complete: () => {
                this.loading = false;
            }
        });
    }
}