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
 * This component handles the Google Workspace access integration for Gebo.ai.
 * It manages the authentication flow between the user's Google Workspace account 
 * and the Gebo.ai application, handling both initial authentication and cases
 * where credentials already exist.
 */
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { GGoogleDriveSystem, GoogleWorkspaceAccessHandshakeControllerService, StartGooglWorkspaceAccessRequest, StartGooglWorkspaceAccessRespose, UserControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST } from "@Gebo.ai/reusable-ui";
import { ToastMessageOptions } from "primeng/api";

@Component({
    selector: "gebo-ai-google-workspace-access-component",
    templateUrl: "gebo-ai-google-workspace-access.component.html",
    standalone: false, providers: [{
        provide: GEBO_AI_FIELD_HOST, multi: true, useValue: fieldHostComponentName("GeboAIGoogleWorkspaceAccessComponent")
    }]
})
export class GeboAIGoogleWorkspaceAccessComponent implements OnInit, OnChanges {
    /**
     * Form group to manage user input for the Google Workspace access
     */
    formGroup: FormGroup = new FormGroup({
        user: new FormControl(Validators.required)
    });

    /**
     * Flag to indicate whether a loading process is active
     */
    loading: boolean = false;

    /**
     * Stores the response data from the Google Workspace access handshake
     */
    handshakeData?: StartGooglWorkspaceAccessRespose;

    /**
     * Flag to indicate whether the access authorization process has started
     */
    public accessStarted: boolean = false;

    /**
     * URL to navigate to on successful handshake
     */
    @Input() uiSuccessForward: string = "";

    /**
     * URL to navigate to on handshake failure
     */
    @Input() uiErrorForward: string = "";

    /**
     * Event emitter triggered when user already has an existing Google account connected
     */
    @Output() alreadyExistingAccount: EventEmitter<true> = new EventEmitter();

    /**
     * Collection of user-facing messages to display
     */
    userMessages: ToastMessageOptions[] = [];

    /**
     * Constructor initializes the component with necessary services
     * @param googleWorkspaceAccessHandshakeService Service to handle Google Workspace access
     * @param usersControllo Service to get user information
     * @param router Angular router service for navigation
     * @param activatedRoute Current activated route
     */
    constructor(
        private googleWorkspaceAccessHandshakeService: GoogleWorkspaceAccessHandshakeControllerService,
        private usersControllo: UserControllerService,
        private router: Router,
        private activatedRoute: ActivatedRoute) {

    }

    /**
     * Lifecycle hook that initializes the component.
     * Gets the current user and sets up initial form data and messages.
     */
    ngOnInit(): void {
        this.loading = true;
        this.usersControllo.getCurrentUser().subscribe({
            next: (u) => {
                const _formData = { user: u.username };
                this.formGroup.patchValue(_formData);
                this.userMessages = [{
                    summary: "Google workspaces access",
                    detail: "Confirm or change the google account used to grant integration between Gebo.ai and google workspaces",
                    severity: "info"
                }];
            },
            complete: () => {
                this.loading = false;
            }
        });
    }

    /**
     * Lifecycle hook that responds to changes in component inputs
     * @param changes SimpleChanges object containing changed properties
     */
    ngOnChanges(changes: SimpleChanges): void {

    }

    /**
     * Initiates the Google Workspace access handshake process.
     * Handles both new integrations and cases where credentials already exist.
     * If successful, either emits an event for existing accounts or redirects
     * the user to the Google authorization page.
     */
    doTryAccess() {
        this.loading = true;
        const req: StartGooglWorkspaceAccessRequest = this.formGroup.value;
        req.uiSuccessForward = this.uiSuccessForward;
        req.uiErrorForward = this.uiErrorForward;
        this.googleWorkspaceAccessHandshakeService.tryGoogleWorkspaceAccess(req).subscribe({
            next: (handshakeStartData) => {
                this.handshakeData = handshakeStartData;
                if (this.handshakeData.alreadyOwningCredentials === true) {
                    this.userMessages = [{
                        summary: "Google workspaces access OK",
                        detail: "The google workspace credentials for this user are already present and valid",
                        severity: "success"
                    }];
                    this.alreadyExistingAccount.emit(true);
                } else {
                    this.userMessages = [{
                        summary: "Google workspaces access confirm",
                        detail: "Please confirm the integration access between Gebo.ai and google workspace",
                        severity: "info"
                    }];
                    this.accessStarted = true;
                    if (handshakeStartData.forwardUrl) {
                        document.location.href = (handshakeStartData.forwardUrl);
                    }

                }
            },
            complete: () => {
                this.loading = false;
            }
        });
    }

}