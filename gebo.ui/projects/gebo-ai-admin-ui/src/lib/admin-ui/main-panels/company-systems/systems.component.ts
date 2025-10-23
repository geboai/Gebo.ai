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
 * This file contains the SystemsComponent which manages the integration of content management systems
 * and file system shares within the Gebo.ai application. It extends the AncestorPanelComponent and 
 * provides functionality to view, create, and manage different system integrations.
 */

import { Component, Host, OnInit, Self } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { CompanySystemsControllerService, FileSystemSharesSettingControllerService, GContentManagementSystem, GGitContentManagementSystem, GGoogleSearchApiCredentials, SharedFilesystemUIConfig } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GeboActionPerformedEvent, GeboActionPerformedType, GeboActionType, GeboUIActionRoutingService } from "@Gebo.ai/reusable-ui";
import { AncestorPanelComponent } from "../ancestor-panel/ancestor-admin-panel.component";
import { forkJoin, Observable } from "rxjs";

/**
 * SystemsComponent is responsible for managing content management systems and file sharing configurations.
 * This component provides interfaces to view, add, and edit Git servers, Google search configurations,
 * and filesystem configurations. It extends AncestorPanelComponent to maintain hierarchy consistency.
 */
@Component({
    selector: "systems-component",
    templateUrl: "systems.component.html",
    standalone: false,
    providers: [{
        provide: GEBO_AI_FIELD_HOST, multi: true, useValue: fieldHostComponentName("SystemsComponent")
    }]
})
export class SystemsComponent extends AncestorPanelComponent implements OnInit {
    /**
     * Overrides the parent method to reload system data when needed
     */
    public override reloadViewedData(): void {
        this.loadSystems();
    }

    /** Array to store content management systems */
    systems: GContentManagementSystem[] = [];

    /** Configuration for shared filesystem UI */
    filesystemUIConfig?: SharedFilesystemUIConfig;

    /** Flag to indicate loading state */
    public loading: boolean = false;

    /** Flag to control filesystem configuration window visibility */
    public showFilesystemConfigurationWindow: boolean = false;

    /** Form group for collecting Git server information */
    formGroup: FormGroup = new FormGroup({
        newGitServer: new FormControl(),
        newGitEndpoint: new FormControl()
    });

    /**
     * Constructor initializes the component with required services
     * @param companySystemsControllerService Service to interact with company systems API
     * @param fileSystemSharesSettingControllerService Service to interact with filesystem shares API
     * @param actionServices Service for routing UI actions
     */
    constructor(private companySystemsControllerService: CompanySystemsControllerService,
        private fileSystemSharesSettingControllerService: FileSystemSharesSettingControllerService,
        private actionServices: GeboUIActionRoutingService) {
        super();
    }

    /**
     * Initializes the component and loads systems data
     */
    ngOnInit(): void {
        this.loadSystems();

    }

    /**
     * Loads content management systems and filesystem configuration data
     * by making parallel API calls and joins the results
     */
    loadSystems(): void {
        this.loading = true;

        const services: [Observable<Array<GContentManagementSystem>>, Observable<SharedFilesystemUIConfig>] = [this.companySystemsControllerService.getContentSystems(), this.fileSystemSharesSettingControllerService.getSharedFileSystemsActualConfiguration()];
        forkJoin(services).subscribe(
            {
                next: (value) => {
                    this.systems = value[0];
                    this.filesystemUIConfig = value[1];
                },
                complete: () => {
                    this.loading = false;
                }
            }
        );
    }

    /**
     * Resets the systems by reloading data from the server
     */
    resetSystems(): void {

        this.loadSystems();
    }

    /**
     * Opens a dialog to create a new Git server integration
     * Routes a new action event to create a Git content management system
     */
    newGitServer(): void {
        this.actionServices.routeEvent(
            {
                actionType: GeboActionType.NEW,
                context: {},
                contextType: "GContentManagementSystemGSystemIntegrationConfig",
                target: {},
                targetType: "GGitContentManagementSystem",
                onActionPerformed: (event: GeboActionPerformedEvent) => {
                    switch (event.actionType) {
                        case GeboActionPerformedType.CLOSING_WINDOW: { }; break;
                        default: {
                            this.loadSystems();
                        }; break;
                    }
                }
            }
        );
    }

    /**
     * Opens a dialog to edit an existing Git server integration
     * @param data The Git content management system data to be edited
     */
    editGitServer(data: GGitContentManagementSystem) {
        this.actionServices.routeEvent(
            {
                actionType: GeboActionType.OPEN,
                context: {},
                contextType: "GContentManagementSystemGSystemIntegrationConfig",
                target: data,
                targetType: "GGitContentManagementSystem",
                onActionPerformed: (event: GeboActionPerformedEvent) => {
                    switch (event.actionType) {
                        case GeboActionPerformedType.CLOSING_WINDOW: { }; break;
                        default: {
                            this.loadSystems();
                        }; break;
                    }
                }
            }
        );
    }

    /**
     * Opens Google Search API configuration dialog
     * Configures Google search integration with the system
     */
    public openGoogleSearchConfiguration(): void {
        const data: any = {
            code: "GOOGLE-SEARCH-API-CONFIG",
            description: "Google search api configuration"
        };
        this.actionServices.routeEvent(
            {
                actionType: GeboActionType.OPEN_OR_NEW,
                context: {},
                contextType: "GContentManagementSystemGSystemIntegrationConfig",
                target: data,
                targetType: "GGoogleSearchApiCredentials",
                onActionPerformed: (event: GeboActionPerformedEvent) => {
                    switch (event.actionType) {
                        case GeboActionPerformedType.CLOSING_WINDOW: { }; break;
                        default: {
                            this.loadSystems();
                        }; break;
                    }
                }
            }
        );
    }

    /**
     * Shows the filesystem configuration window
     * Controls the visibility of the filesystem configuration interface
     */
    public openFilesystemsConfiguration(): void {
        this.showFilesystemConfigurationWindow = true;
    }

}