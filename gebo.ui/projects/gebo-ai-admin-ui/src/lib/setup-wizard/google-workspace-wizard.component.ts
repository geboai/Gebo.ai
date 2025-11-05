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
 * This module provides services and components for managing Google Workspace integration with Gebo.ai.
 * It includes status monitoring, installation services, and a wizard component for setup.
 */

import { Component, Injectable } from "@angular/core";
import { GeboModulesConfigControllerService, GGoogleDriveSystem, GoogleDriveSystemsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, fieldHostComponentName, GEBO_AI_FIELD_HOST, GeboActionType, GeboUIActionRequest, GeboUIActionRoutingService, GOOGLEDRIVE_MODULE, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { map, Observable } from "rxjs";
import { GeboRootInstalledModuleService } from "./abstract-module-installed.service";

/**
 * Service that provides status information about Google Workspace connections.
 * Extends AbstractStatusService to integrate with the Gebo status monitoring system.
 * Determines if there are any configured Google Drive systems available.
 */
@Injectable()
export class GoogleWorkspacesStatusService extends AbstractStatusService {
  constructor(private googleDriveControllerService: GoogleDriveSystemsControllerService) {
    super();
  }

  /**
   * Returns an Observable boolean indicating whether any Google Drive systems are configured.
   * Returns true if at least one Google Drive system exists, false otherwise.
   * @returns Observable<boolean> - True if at least one Google Drive system exists
   */
  public override getBooleanStatus(): Observable<boolean> {
    return this.googleDriveControllerService.getGoogleDriveSystems().pipe(map(c => ((c && c.length > 0) ? true : false)));
  }
}

/**
 * Service to handle the installation status of the Google Workspaces module.
 * Extends the GeboRootInstalledModuleService to provide specific functionality for the Google Drive module.
 * Used to check if the Google Drive module is installed and configured in the system.
 */
@Injectable()
export class GoogleWorkspacesInstalledModuleService extends GeboRootInstalledModuleService {
  protected override moduleCode: string = GOOGLEDRIVE_MODULE;

  constructor(geboModulesConfigService: GeboModulesConfigControllerService) {
    super(geboModulesConfigService);
  }
}

/**
 * Component that provides a wizard interface for setting up Google Workspace integration.
 * This component allows users to view, create, and edit Google Drive system configurations.
 * It extends BaseWizardSectionComponent to integrate with the overall setup wizard flow.
 */
@Component({
  selector: "gebo-google-workspace-wizard-component",
  templateUrl: "google-workspace-wizard.component.html",
  standalone: false,
  providers: [{ provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GoogleWorkspacesWizardComponent") }]
})
export class GoogleWorkspacesWizardComponent extends BaseWizardSectionComponent {

  public createGoogleDriveWorkspacesWindowOpen: boolean = false;
  public systems: GGoogleDriveSystem[] = [];

  constructor(setupWizardComunicationService: SetupWizardComunicationService,
    private googleDriveControllerService: GoogleDriveSystemsControllerService,
    private geboUIActionsRouter: GeboUIActionRoutingService
  ) {
    super(setupWizardComunicationService)
  }

  /**
   * Reloads the Google Drive systems data from the backend.
   * Updates the systems array and determines if setup is completed based on if any systems exist.
   * Sets loading state appropriately during the data fetch operation.
   */
  public override reloadData(): void {
    this.loading = true;
    this.googleDriveControllerService.getGoogleDriveSystems().subscribe({
      next: (value) => {
        this.systems = value;
        this.isSetupCompleted = value && value.length > 0;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  /**
   * Opens the edit interface for an existing Google Drive system.
   * Routes a UI action request to open the system for editing.
   * @param value - The GGoogleDriveSystem to edit
   */
  editSystem(value: GGoogleDriveSystem) {
    const action: GeboUIActionRequest = {
      actionType: GeboActionType.OPEN,
      context: {},
      contextType: "",
      target: value,
      targetType: "GGoogleDriveSystem",
      onActionPerformed: (a) => {
        this.reloadData();
      }
    };
    this.geboUIActionsRouter.routeEvent(action);
  }

  /**
   * Opens the dialog for creating a new Google Drive system.
   * Sets the createGoogleDriveWorkspacesWindowOpen flag to true to display the creation dialog.
   */
  createSystem() {
    this.createGoogleDriveWorkspacesWindowOpen = true;

  }

  /**
   * Closes the Google Drive system creation dialog.
   * Reloads the data to refresh the systems list after the dialog is closed.
   */
  closeDialog() {
    this.createGoogleDriveWorkspacesWindowOpen = false;
    this.reloadData();
  }
};