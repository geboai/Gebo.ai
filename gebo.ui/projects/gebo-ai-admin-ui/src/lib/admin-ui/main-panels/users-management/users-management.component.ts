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
 * This file contains the GeboAIUsersManagementComponent which handles the management of users and user groups.
 * It extends AncestorPanelComponent and implements OnInit and OnChanges interfaces.
 */

import { Component, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { DataPage, EditableUser, PageUserInfos, PageUsersGroup, User, UserInfos, UsersAdminControllerService, UsersGroup } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboActionType, GeboUIActionRequest, GeboUIActionRoutingService } from "@Gebo.ai/reusable-ui";
import { PaginatorState } from "primeng/paginator";
import { AncestorPanelComponent } from "../ancestor-panel/ancestor-admin-panel.component";

/**
 * Component for managing users and user groups in the Gebo.ai application.
 * This component provides functionality to view, create, and edit users and groups.
 */
@Component({
    selector: "users-management-component",
    templateUrl: "users-management.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAIUsersGroupPanelModule", multi: false }, {
        provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIUsersManagementComponent")
    }]
})
export class GeboAIUsersManagementComponent extends AncestorPanelComponent implements OnInit, OnChanges {
    /**
     * Overrides the parent method to reload both users and groups data
     */
    public override reloadViewedData(): void {
        this.loadUsers();
        this.loadGroups();
    }

    // Flag indicating if users are currently being loaded
    loadingUsers: boolean = false;

    // Flag indicating if groups are currently being loaded
    loadingGroups: boolean = false;

    // Pagination configuration for users
    usersPage: DataPage = { page: 0, pageSize: 20 };

    // Pagination configuration for groups
    groupsPage: DataPage = { page: 0, pageSize: 20 };

    // Container for paginated user information
    usersPaged: PageUserInfos = {
        content: []
    };

    // Container for paginated group information
    groupsPaged: PageUsersGroup = {
        content: []
    };

    /**
     * Constructor for the component
     * @param geboAiUserAdminControllerService Service for user administration operations
     * @param actionServices Service for routing UI actions
     */
    constructor(
        private geboAiUserAdminControllerService: UsersAdminControllerService,
        private actionServices: GeboUIActionRoutingService) {
        super();
    }

    /**
     * Initializes the component by loading users and groups data
     */
    ngOnInit(): void {
        this.loadUsers();
        this.loadGroups();
    }

    /**
     * Fetches users data from the server based on current pagination settings
     * Sets loadingUsers flag while operation is in progress
     */
    private loadUsers(): void {
        this.loadingUsers = true;
        this.geboAiUserAdminControllerService.findUserByQbe({
            qbe: {

            } as User,
            page: this.usersPage
        }).subscribe({
            next: (value) => {
                this.usersPaged = value;
            },
            complete: () => {
                this.loadingUsers = false;
            }
        });
    }

    /**
     * Fetches groups data from the server based on current pagination settings
     * Sets loadingGroups flag while operation is in progress
     */
    private loadGroups(): void {
        this.loadingGroups = true;
        this.geboAiUserAdminControllerService.findUsersGroupByQbe({
            qbe: {

            } as UsersGroup,
            page: this.groupsPage
        }).subscribe({
            next: (value) => {
                this.groupsPaged = value;
            },
            complete: () => {
                this.loadingGroups = false;
            }
        });
    }

    /**
     * Lifecycle hook that is called when any data-bound property of a directive changes
     * @param changes Object containing all changed properties with current and previous values
     */
    ngOnChanges(changes: SimpleChanges): void {

    }

    // Currently being edited user
    editedUser?: EditableUser;

    // Mode for user editing: EDIT for existing users, NEW for creating users
    editedUserMode?: "EDIT" | "NEW" = undefined;

    // Flag to control visibility of user editing UI
    openUserEditing: boolean = false;

    /**
     * Prepares the component to create a new user
     * Initializes a new user object with default role and opens the editing UI
     */
    newUser(): void {
        this.editedUser = { roles: ["USER"], name: null, sourname: null, email: null } as any;
        this.editedUserMode = "NEW";
        this.openUserEditing = true;

    }

    /**
     * Opens user editing UI for an existing user
     * @param u User information to edit
     */
    editUser(u: UserInfos) {
        this.editedUser = u as any;
        this.editedUserMode = "EDIT";
        this.openUserEditing = true;
    }

    /**
     * Handles the event when user editing is complete
     * @param evt Event data from the user editing operation
     */
    editedUserChange(evt: any) {
        this.openUserEditing = false;
        this.loadUsers();
    }

    /**
     * Initiates the creation of a new user group
     * Uses the action routing service to trigger the appropriate UI flow
     */
    newGroup(): void {
        const action: GeboUIActionRequest = {
            actionType: GeboActionType.NEW,
            context: {},
            contextType: "Admin",
            target: {

            },
            targetType: "UsersGroup",
            onActionPerformed: (event) => {
                this.loadGroups();
            }
        };
        this.actionServices.routeEvent(action);
    }

    /**
     * Opens editing UI for an existing user group
     * @param g The group to edit
     */
    editGroup(g: UsersGroup) {
        const action: GeboUIActionRequest = {
            actionType: GeboActionType.OPEN,
            context: {},
            contextType: "Admin",
            target: g,
            targetType: "UsersGroup",
            onActionPerformed: (event) => {
                this.loadGroups();
            }
        };
        this.actionServices.routeEvent(action);
    }

    /**
     * Handles pagination changes for the users list
     * @param p New paginator state
     */
    onUsersPageChange(p: PaginatorState) {
        this.usersPage.page = p.page;
        this.usersPage.pageSize = p.rows;
        this.loadUsers();
    }

    /**
     * Handles pagination changes for the groups list
     * @param p New paginator state
     */
    onGroupsPageChange(p: PaginatorState) {
        this.groupsPage.page = p.page;
        this.groupsPage.pageSize = p.rows;
        this.loadGroups();
    }
}