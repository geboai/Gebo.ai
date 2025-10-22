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
 * This module provides a component for controlling access permissions in the application.
 * It allows setting access controls for users and groups, with options to make resources
 * accessible to all users or specific users/groups.
 */

import { Component, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { UserInfos, UsersAdminControllerService, UsersGroup } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST } from "@Gebo.ai/reusable-ui";
import { forkJoin, Observable } from "rxjs";

/**
 * Array of fixed form control names that are always required for the access control functionality.
 * These controls are automatically added to the form group if not present.
 */
const fixedControls: string[] = ["accessibleGroups", "accessibleUsers", "accessibleToAll"];

/**
 * Component that provides UI for managing access control settings.
 * It allows selecting users and groups who should have access to a resource,
 * as well as an option to make the resource accessible to all users.
 */
@Component({
    selector: "gebo-ai-access-control",
    templateUrl: "access-control-group.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIAccessControlComponent") }]
})
export class GeboAIAccessControlComponent implements OnInit, OnChanges {
    /**
     * Form group that contains the access control settings.
     * Expected to contain or receive controls for accessibleGroups, accessibleUsers, and accessibleToAll.
     */
    @Input() formGroup?: FormGroup;

    /**
     * Flag indicating whether the component is currently loading data from the backend.
     */
    loadingRelatedBackend: boolean = false;

    /**
     * Creates an instance of the access control component.
     * @param userAdminControllerService Service for accessing user and group information
     */
    constructor(private userAdminControllerService: UsersAdminControllerService) {

    }

    /**
     * Flag indicating whether the resource is accessible to all users.
     */
    public accessibleToAll: boolean = false;

    /**
     * List of all users in the system that can be granted access.
     */
    users?: UserInfos[] = [];

    /**
     * List of all user groups in the system that can be granted access.
     */
    groups?: UsersGroup[] = [];

    /**
     * Initializes the component by fetching users and groups from the backend.
     * Uses forkJoin to parallelize the API calls for better performance.
     */
    ngOnInit(): void {
        this.loadingRelatedBackend = true;
        const lists: [Observable<UserInfos[]>, Observable<UsersGroup[]>] = [this.userAdminControllerService.getAllUsers(), this.userAdminControllerService.getAllGroups()];
        forkJoin(lists).subscribe({
            next: (data) => {
                this.users = data[0];
                this.groups = data[1];
            }, complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    /**
     * Initializes the form group by ensuring all required controls exist and
     * setting up value change subscriptions to keep the UI state synchronized.
     * If controls don't exist, they are added to the form group.
     */
    private initializeFormGroup(): void {
        fixedControls.forEach(fld => {
            if (!this.formGroup?.controls[fld]) {
                this.formGroup?.addControl(fld, new FormControl());
            }
        });
        this.formGroup?.valueChanges.subscribe(x => {
            if (x) {
                if (x.accessibleToAll === undefined) {
                    this.accessibleToAll = true;
                    this.formGroup?.controls["accessibleToAll"].setValue(true);
                } else {
                    this.accessibleToAll = x.accessibleToAll;

                }
            }
        });
    }

    /**
     * Responds to input changes, particularly when the formGroup input changes.
     * Ensures the form group is properly initialized with the necessary controls.
     * @param changes The changes that occurred on the component's inputs
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.formGroup && changes["formGroup"]) {
            this.initializeFormGroup();
        }
    }
}