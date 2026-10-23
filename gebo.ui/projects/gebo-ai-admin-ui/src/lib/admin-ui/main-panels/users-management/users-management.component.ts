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
import { DataPage, EditableUser, GUserSession, PagedModelGUserSession, PagedModelUserInfos, PagedModelUsersGroup, SessionsAdminControllerService, SessionsQuery, UserInfos, UsersAdminControllerService, UsersGroup } from "@Gebo.ai/gebo-ai-rest-api";
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
        this.loadSessions();
    }

    // The live sessions: tokens in use, by whom and from where, and their revocation
    loadingSessions: boolean = false;
    sessionsPage: DataPage = { page: 0, pageSize: 20 };
    sessionsPaged: PagedModelGUserSession = { content: [] };
    sessionsQuery: SessionsQuery = { username: '', activeWithinMinutes: 15, includeRevoked: false, includeExpired: false };
    sessionKinds = [
        { label: 'Any kind', value: undefined },
        { label: 'Login session', value: 'SESSION' },
        { label: 'API key', value: 'API_KEY' },
        { label: 'External OAuth2 token', value: 'OAUTH2' }
    ];
    /** The session whose revocation waits for a second click, or the user whose sessions do. */
    confirmingSessionCode?: string;
    confirmingUsername?: string;
    sessionsMessage?: string;

    // Flag indicating if users are currently being loaded
    loadingUsers: boolean = false;

    // Flag indicating if groups are currently being loaded
    loadingGroups: boolean = false;

    // Pagination configuration for users
    usersPage: DataPage = { page: 0, pageSize: 20 };

    // Pagination configuration for groups
    groupsPage: DataPage = { page: 0, pageSize: 20 };

    // Container for paginated user information
    usersPaged: PagedModelUserInfos = {
        content: []
    };

    // Container for paginated group information
    groupsPaged: PagedModelUsersGroup = {
        content: []
    };

    // Filter variables for user search
    searchUsername: string = '';
    searchName: string = '';
    searchSourname: string = '';
    searchRole: string = '';

    // Options for role filter dropdown
    rolesList: string[] = ["USER", "ADMIN", "APPLICATION"];

    /**
     * Constructor for the component
     * @param geboAiUserAdminControllerService Service for user administration operations
     * @param actionServices Service for routing UI actions
     */
    constructor(
        private geboAiUserAdminControllerService: UsersAdminControllerService,
        private sessionsAdminService: SessionsAdminControllerService,
        private actionServices: GeboUIActionRoutingService) {
        super();
    }

    /**
     * Initializes the component by loading users and groups data
     */
    ngOnInit(): void {
        this.loadUsers();
        this.loadGroups();
        this.loadSessions();
    }

    /** Reloads the sessions from the first page with the current filters. */
    public searchSessions(): void {
        this.sessionsPage.page = 0;
        this.loadSessions();
    }

    public loadSessions(): void {
        this.loadingSessions = true;
        this.confirmingSessionCode = undefined;
        this.confirmingUsername = undefined;
        const query: SessionsQuery = { ...this.sessionsQuery };
        if (!query.username) {
            delete query.username;
        }
        if (!query.activeWithinMinutes) {
            delete query.activeWithinMinutes;
        }
        this.sessionsAdminService.getSessionsPagedList({ page: this.sessionsPage, query }).subscribe({
            next: (value) => {
                this.sessionsPaged = value;
            },
            error: () => {
                this.sessionsMessage = 'The sessions could not be loaded.';
            },
            complete: () => {
                this.loadingSessions = false;
            }
        });
    }

    public onSessionsPageChange(event: PaginatorState): void {
        this.sessionsPage.page = event.page ?? 0;
        this.sessionsPage.pageSize = event.rows ?? 20;
        this.loadSessions();
    }

    /** The first click asks for a confirmation, the second revokes. */
    public revokeSession(session: GUserSession): void {
        if (!session.code) {
            return;
        }
        if (this.confirmingSessionCode !== session.code) {
            this.confirmingSessionCode = session.code;
            this.confirmingUsername = undefined;
            return;
        }
        this.loadingSessions = true;
        this.sessionsAdminService.revokeSession(session.code).subscribe({
            next: () => {
                this.sessionsMessage = `Session of ${session.username} revoked: its tokens are refused from now on.`;
                this.loadSessions();
            },
            error: () => {
                this.sessionsMessage = 'The session could not be revoked.';
                this.loadingSessions = false;
            }
        });
    }

    /** The first click asks for a confirmation, the second revokes every session of the user, the administrator's own included. */
    public revokeUserSessions(username?: string): void {
        if (!username) {
            return;
        }
        if (this.confirmingUsername !== username) {
            this.confirmingUsername = username;
            this.confirmingSessionCode = undefined;
            return;
        }
        this.loadingSessions = true;
        this.sessionsAdminService.revokeUserSessions(username).subscribe({
            next: (count) => {
                this.sessionsMessage = `${count} session(s) of ${username} revoked.`;
                this.loadSessions();
            },
            error: () => {
                this.sessionsMessage = 'The sessions could not be revoked.';
                this.loadingSessions = false;
            }
        });
    }

    public cancelRevoke(): void {
        this.confirmingSessionCode = undefined;
        this.confirmingUsername = undefined;
    }

    /** A short form of the user agent: the browser or the client, not the whole string. */
    public clientOf(session: GUserSession): string {
        const ua = session.lastUserAgent || '';
        const known = ['Edg/', 'OPR/', 'Firefox/', 'Chrome/', 'Safari/', 'curl/', 'python-requests/', 'okhttp/', 'node'];
        for (const marker of known) {
            const i = ua.indexOf(marker);
            if (i >= 0) {
                return ua.substring(i).split(' ')[0];
            }
        }
        return ua.length > 40 ? ua.substring(0, 40) + '…' : ua;
    }

    /**
     * Resets the active page to 0 and reloads the users list using the current search filters.
     */
    public searchUsers(): void {
        this.usersPage.page = 0;
        this.loadUsers();
    }

    /**
     * Fetches users data from the server based on current pagination settings and search filters
     * Sets loadingUsers flag while operation is in progress
     */
    private loadUsers(): void {
        this.loadingUsers = true;
        const qbeUser: any = {};
        if (this.searchUsername) {
            qbeUser.username = this.searchUsername;
        }
        if (this.searchName) {
            qbeUser.name = this.searchName;
        }
        if (this.searchSourname) {
            qbeUser.sourname = this.searchSourname;
        }
        if (this.searchRole) {
            qbeUser.roles = [this.searchRole];
        }

        this.geboAiUserAdminControllerService.findUserByQbe({
            qbe: qbeUser as EditableUser,
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