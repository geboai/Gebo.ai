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
 * This file contains components and services related to SharePoint integration in the Gebo.ai application.
 * It includes services for checking SharePoint status, managing module installation, and a wizard component
 * for SharePoint configuration.
 */

import { Component, Injectable } from "@angular/core";
import { GeboModulesConfigControllerService, GSharepointContentManagementSystem, SharepointSystemsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, fieldHostComponentName, GEBO_AI_FIELD_HOST, GeboActionType, GeboUIActionRequest, GeboUIActionRoutingService, SetupWizardComunicationService, SHAREPOINT_MODULE } from "@Gebo.ai/reusable-ui";
import { map, Observable } from "rxjs";
import { GeboRootInstalledModuleService } from "./abstract-module-installed.service";

/**
 * Service that checks the status of SharePoint integration.
 * Extends AbstractStatusService to provide status information about SharePoint systems.
 * The status is determined by checking if any SharePoint systems are registered.
 */
@Injectable()
export class SharepointStatusService extends AbstractStatusService {
  constructor(private sharePointControllerService: SharepointSystemsControllerService) {
    super();
  }

  /**
   * Retrieves the boolean status of SharePoint integration.
   * @returns An Observable that emits true if at least one SharePoint system is configured, false otherwise.
   */
  public override getBooleanStatus(): Observable<boolean> {
    return this.sharePointControllerService.getSharepointSystems().pipe(map(c => ((c && c.length > 0) ? true : false)));
  }
}

/**
 * Service that manages the installation status of the SharePoint module.
 * Extends GeboRootInstalledModuleService to handle module-specific operations.
 */
@Injectable()
export class SharepointInstalledModuleService extends GeboRootInstalledModuleService {
  /**
   * The unique identifier for the SharePoint module
   */
  protected override moduleCode: string = SHAREPOINT_MODULE;

  constructor(geboModulesConfigService: GeboModulesConfigControllerService) {
    super(geboModulesConfigService);
  }
}

/**
 * Component that provides a wizard interface for SharePoint configuration.
 * Extends BaseWizardSectionComponent to integrate with the setup wizard flow.
 * Allows users to view, create, and edit SharePoint system connections.
 */
@Component({
  selector: "gebo-sharepoint-wizard-component",
  templateUrl: "sharepoint-wizard.component.html",
  standalone: false,
  providers: [{ provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("SharepointWizardComponent") }]

})
export class SharepointWizardComponent extends BaseWizardSectionComponent {

  /**
   * Flag that controls the visibility of the SharePoint creation dialog
   */
  public createSharepointWindowOpen: boolean = false;

  /**
   * List of configured SharePoint systems
   */
  public systems: GSharepointContentManagementSystem[] = [];

  constructor(setupWizardComunicationService: SetupWizardComunicationService,
    private sharepointControllerService: SharepointSystemsControllerService,
    private geboUIActionsRouter: GeboUIActionRoutingService
  ) {
    super(setupWizardComunicationService)
  }

  /**
   * Loads or refreshes SharePoint systems data from the backend.
   * Updates the systems array and determines if setup is completed.
   * Sets loading flags appropriately during the process.
   */
  public override reloadData(): void {
    this.loading = true;
    this.sharepointControllerService.getSharepointSystems().subscribe({
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
   * Opens the edit dialog for a specific SharePoint system.
   * Routes an action to the appropriate handler using the action routing service.
   * @param value The SharePoint system to edit
   */
  editSharepointSystem(value: GSharepointContentManagementSystem) {
    const action: GeboUIActionRequest = {
      actionType: GeboActionType.OPEN,
      context: {},
      contextType: "",
      target: value,
      targetType: "GSharepointContentManagementSystem",
      onActionPerformed: (a) => {
        this.reloadData();
      }
    };
    this.geboUIActionsRouter.routeEvent(action);
  }

  /**
   * Opens the dialog for creating a new SharePoint system.
   * Sets the createSharepointWindowOpen flag to show the creation dialog.
   */
  createSharepointSystem() {
    this.createSharepointWindowOpen = true;
    /*
    const value: GConfluenceSystem = {};
    const action: GeboUIActionRequest = {
      actionType: GeboActionType.NEW,
      context: {},
      contextType: "",
      target: value,
      targetType: "GConfluenceSystem",
      onActionPerformed: (a) => {
        this.reloadData();
      }
    };
    this.geboUIActionsRouter.routeEvent(action); */
  }

  /**
   * Closes the SharePoint creation dialog and reloads the data.
   * This ensures the list of systems is updated after potential changes.
   */
  closeDialog() {
    this.createSharepointWindowOpen = false;
    this.reloadData();
  }
};