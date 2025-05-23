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
 * This file defines a component for managing users in a setup wizard interface.
 * It extends BaseWizardSectionComponent and provides functionality for 
 * listing, creating, and editing users in the Gebo.ai system.
 */

import { Component } from "@angular/core";
import { UserInfos, UsersAdminControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseWizardSectionComponent, GeboActionType, GeboUIActionRequest, GeboUIActionRoutingService, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";

/**
 * Component responsible for managing users within the setup wizard.
 * This component displays a list of users and provides options to create new users
 * or edit existing ones. It communicates with the backend via the UsersAdminControllerService
 * and handles UI actions through the GeboUIActionRoutingService.
 */
@Component({
    selector: "gebo-ai-users-wizard-component",
    templateUrl: "users-wizard.component.html",
    standalone: false
})
export class UsersWizardComponent extends BaseWizardSectionComponent {
    /**
     * Array to store the user information retrieved from the backend.
     */
    public users: UserInfos[] = [];

    /**
     * Initializes the component with required services.
     * @param setupWizardComunicationService Service for communication within the setup wizard
     * @param usersController Service for making API calls to manage users
     * @param geboUIActionRoutingService Service for routing UI actions
     */
    constructor(setupWizardComunicationService: SetupWizardComunicationService,
        private usersController: UsersAdminControllerService,
        private geboUIActionRoutingService: GeboUIActionRoutingService) {
        super(setupWizardComunicationService);
    }

    /**
     * Overrides the parent method to load user data from the backend.
     * Sets loading state to true while fetching data and updates the users array
     * when data is received.
     */
    public override reloadData(): void {
        this.loading=true;
        this.usersController.getAllUsers().subscribe({
            next: (users) => {
                this.users = users;
            },
            complete: () => {
                this.loading=false;
            }
        });
    }

    /**
     * Triggers the edit functionality for an existing user.
     * @param ui The user information object to be edited
     */
    public editUser(ui:UserInfos){
        this.openUserWindow(ui);
    } 

    /**
     * Initiates the creation of a new user by opening the user edit window
     * with an empty user object.
     */
    public createUser() {
        const user={

        };
        this.openUserWindow(user);
    }

    /**
     * Opens the user editing window with the provided user data.
     * Creates a GeboUIActionRequest to either open an existing user or
     * create a new one, then routes this action through the GeboUIActionRoutingService.
     * Reloads the user data when the action is completed.
     * 
     * @param data The user data to be edited, or an empty object for a new user
     */
    private openUserWindow(data:any) {
        const action: GeboUIActionRequest={
            actionType:data?.username?GeboActionType.OPEN:GeboActionType.NEW,
            context:{},
            contextType:"UsersWizardComponent",
            target:data,
            targetType:"EditableUser",
            onActionPerformed:(e)=>{
                this.reloadData();
            }
        };
        this.geboUIActionRoutingService.routeEvent(action);
    }
}