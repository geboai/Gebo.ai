/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { UserInfos, UsersAdminControllerService, UsersGroup } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { Observable, of } from "rxjs";

/**
 * AI generated comments
 * 
 * Component responsible for managing user groups in the Gebo.ai application.
 * This component extends BaseEntityEditingComponent to provide CRUD operations
 * for UsersGroup entities. It handles form creation, user loading,
 * and API interactions for group management.
 */
@Component({
    selector: "gebo-ai-users-group-component",
    templateUrl: "gebo-ai-group.component.html",
    standalone: false,
    providers: [ 
        { provide: GEBO_AI_MODULE, useValue: "GeboAIUsersGroupModule", multi: false },   
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIGroupComponent),
        multi: false
    }]
})
export class GeboAIGroupComponent extends BaseEntityEditingComponent<UsersGroup> {
    /**
     * Name of the entity this component manages.
     * Used for logging and error messages.
     */
    protected override entityName: string = "UsersGroup";

    /**
     * Form group for managing UsersGroup data inputs.
     * Contains fields for code, description, and userIds.
     */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        userIds: new FormControl()
    });

    /**
     * List of all users in the system.
     * Populated on component initialization to allow user selection for the group.
     */
    users?: UserInfos[] = [];

    /**
     * Initializes the component by calling the parent initialization
     * and loading all users from the backend service.
     */
    override ngOnInit(): void {
        super.ngOnInit();
        this.loadingRelatedBackend = true;
        this.userAdminControllerService.getAllUsers().subscribe({
            next: (array) => {
                this.users = array;
            }, complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    /**
     * Constructor that injects required services.
     * 
     * @param injector Angular injector for dependency resolution
     * @param geboFormGroupsService Service for managing form groups
     * @param confirmService Service for displaying confirmation dialogs
     * @param userAdminControllerService Service for user administration API calls
     * @param geboUIActionRoutingService Service for UI action routing
     * @param outputForwardingService Optional service for output forwarding
     */
    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService, confirmService: ConfirmationService, private userAdminControllerService: UsersAdminControllerService, geboUIActionRoutingService: GeboUIActionRoutingService, outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmService, geboUIActionRoutingService, outputForwardingService)
    }

    /**
     * Finds a user group by its code.
     * 
     * @param code The unique code of the group to find
     * @returns An Observable containing the found group or null if not found
     */
    override findByCode(code: string): Observable<UsersGroup | null> {
        return this.userAdminControllerService.findGroupByCode(code);
    }

    /**
     * Updates an existing user group.
     * 
     * @param value The user group with updated values
     * @returns An Observable containing the updated group
     */
    override save(value: UsersGroup): Observable<UsersGroup> {
        return this.userAdminControllerService.updateGroup(value);
    }

    /**
     * Creates a new user group.
     * 
     * @param value The new user group to create
     * @returns An Observable containing the created group
     */
    override insert(value: UsersGroup): Observable<UsersGroup> {
        return this.userAdminControllerService.insertGroup(value);
    }

    /**
     * Deletes a user group.
     * 
     * @param value The user group to delete
     * @returns An Observable containing a boolean indicating success
     */
    override delete(value: UsersGroup): Observable<boolean> {
        return this.userAdminControllerService.deleteGroup(value);
    }

    /**
     * Checks if a user group can be deleted.
     * Currently always returns true with no validation logic.
     * 
     * @param value The user group to check
     * @returns An Observable containing the deletion possibility info
     */
    override canBeDeleted(value: UsersGroup): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }
}