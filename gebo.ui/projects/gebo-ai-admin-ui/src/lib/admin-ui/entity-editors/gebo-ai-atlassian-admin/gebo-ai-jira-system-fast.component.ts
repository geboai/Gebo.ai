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
 * This component handles the fast configuration of a Jira system in Gebo.ai.
 * It provides a form interface for users to input Jira system details and quickly 
 * set up a new Jira integration. The component manages form state, loading indicators,
 * and communication with backend services.
 */
import { Component, EventEmitter, OnInit, Output } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { FastJiraSystemInsertRequest, GJiraSystem, JiraSystemsControllerService, UserControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST } from "@Gebo.ai/reusable-ui";
import { ToastMessageOptions } from "primeng/api";


@Component({
    selector: "gebo-ai-jira-system-fast-component",
    templateUrl: "gebo-ai-jira-system-fast.component.html",
    standalone: false,
    providers: [{
        provide: GEBO_AI_FIELD_HOST, multi: true, useValue: fieldHostComponentName("GeboAIJiraSystemFastComponent")
    }]
})
export class GeboAIJiraSystemFastComponent implements OnInit {
    /** Indicates whether the component is currently loading data */
    public loading: boolean = false;

    /** Form group that manages the Jira system configuration fields */
    public formGroup: FormGroup = new FormGroup({
        description: new FormControl(),
        baseUri: new FormControl(),
        username: new FormControl(),
        password: new FormControl(),
        token: new FormControl(),
        confluenceVersion: new FormControl()
    });

    /** Collection of messages to display to the user after operations */
    userMessages: ToastMessageOptions[] = [];

    /** Event emitter that broadcasts a newly created Jira system */
    @Output() newJiraSystemEvent: EventEmitter<GJiraSystem> = new EventEmitter();

    /** Event emitter that broadcasts when the creation process is cancelled */
    @Output() cancelAction: EventEmitter<boolean> = new EventEmitter();

    /**
     * Component constructor that initializes services and default form values
     * @param confluenceService Service for Jira system operations
     * @param userControllerService Service for user-related operations
     */
    constructor(private confluenceService: JiraSystemsControllerService, private userControllerService: UserControllerService) {
        this.formGroup.controls["description"].setValue("Jira system");
    }

    /**
     * Enables or disables a specific form control
     * @param ctrlName The name of the control to enable/disable
     * @param enabled Boolean indicating whether to enable (true) or disable (false) the control
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
     * Lifecycle hook that initializes the component
     * Fetches current user data and populates the username field
     */
    ngOnInit(): void {
        this.loading = true;
        this.userControllerService.getCurrentUser().subscribe({
            next: (value) => {
                this.formGroup.controls["username"].setValue(value.username);
            },
            complete: () => {
                this.loading = false;
            }
        })
    }

    /**
     * Submits the form data to create a new Jira system
     * Emits an event with the new system on successful creation
     */
    doInsert(): void {
        const data: FastJiraSystemInsertRequest = this.formGroup.value;
        this.loading = true;
        this.confluenceService.fastJiraConfig(data).subscribe({
            next: (result) => {
                this.userMessages = result.messages as ToastMessageOptions[];
                if (result.result && result.hasErrorMessages !== true) {
                    this.newJiraSystemEvent.emit(result.result);
                }
            },
            complete: () => {
                this.loading = false;
            }
        });
    }
}