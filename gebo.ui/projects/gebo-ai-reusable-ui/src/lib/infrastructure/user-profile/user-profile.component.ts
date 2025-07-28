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
 * This component is responsible for displaying the current user's profile information.
 * It fetches user profile data upon initialization and provides functionality for 
 * displaying user information and managing password changes.
 */
import { Component, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { LoginService } from "../login/login.service";
import { getAuth } from "../gebo-credentials";
import { AuthResponse, UserInfo } from "@Gebo.ai/gebo-ai-rest-api";

@Component({
    selector: "gebo-ai-current-user-profile",
    templateUrl: "user-profile.component.html",
    standalone: false
})
export class GeboAIUserProfileComponent implements OnInit, OnChanges {
    /** Flag to indicate if data is currently being loaded */
    loading: boolean = false;

    /** Stores the user profile information */
    user?: UserInfo;



    /** Controls the visibility of the change password dialog */
    public changePasswordWindowOpened: boolean = false;

    /**
     * Initializes the component with the LoginService for user data management
     * @param loginService Service that handles user authentication and profile data
     */
    constructor(private loginService: LoginService) {

    }

    /**
     * Lifecycle hook that initializes the component
     * Loads the user profile data from the login service upon component initialization
     * Sets loading flags appropriately during the data fetch process
     */
    ngOnInit(): void {
        this.loading = true;
        this.loginService.loadUserProfile().subscribe({
            next: (value) => {
                this.user = value;
            },
            complete: () => {
                this.loading = false;
            }
        });
    }

    /**
     * Lifecycle hook that responds to changes in input properties
     * Currently not implementing any specific behavior
     * @param changes SimpleChanges object containing current and previous property values
     */
    ngOnChanges(changes: SimpleChanges): void {

    }

}