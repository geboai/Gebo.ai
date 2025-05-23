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
 * Module for Confluence integration with the Gebo.ai platform.
 * This file contains services and components for managing Confluence systems.
 */

import { Component, Injectable } from "@angular/core";
import { ConfluenceSystemsControllerService, GConfluenceSystem, GeboModulesConfigControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, CONFLUENCE_MODULE, GeboActionType, GeboUIActionRequest, GeboUIActionRoutingService, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { map, Observable } from "rxjs";
import { GeboRootInstalledModuleService } from "./abstract-module-installed.service";

/**
 * Service responsible for checking the status of Confluence systems.
 * Extends AbstractStatusService to implement status checking functionality.
 */
@Injectable()
export class ConfuenceStatusService extends AbstractStatusService {
  constructor(private confluenceControllerService: ConfluenceSystemsControllerService) {
    super();
  }

  /**
   * Returns an Observable of boolean indicating whether any Confluence systems are configured.
   * Returns true if at least one Confluence system exists, false otherwise.
   * @returns Observable<boolean> - Status of Confluence systems
   */
  public override getBooleanStatus(): Observable<boolean> {
    return this.confluenceControllerService.getConfluenceSystems().pipe(map(c => (c && c.length > 0)));
  }
}

/**
 * Service that manages the installation status of the Confluence module.
 * Extends GeboRootInstalledModuleService with Confluence-specific module code.
 */
@Injectable()
export class ConfluenceInstalledModuleService extends GeboRootInstalledModuleService {
  protected override moduleCode: string=CONFLUENCE_MODULE;
  
  constructor(geboModulesConfigService: GeboModulesConfigControllerService) {
    super(geboModulesConfigService);
  }
}

/**
 * Component that provides a wizard interface for configuring Confluence systems.
 * Allows viewing existing Confluence systems, creating new ones, and editing existing ones.
 */
@Component({
    selector: "gebo-confluence-wizard-component",
    templateUrl: "confluence-wizard.component.html",
    standalone: false
})
export class ConfluenceWizardComponent extends BaseWizardSectionComponent {

  public createConfluenceWindowOpen:boolean=false;
  public systems: GConfluenceSystem[] = [];
  
  constructor(setupWizardComunicationService: SetupWizardComunicationService,
    private confluenceControllerService: ConfluenceSystemsControllerService,
    private geboUIActionsRouter: GeboUIActionRoutingService
  ) {
    super(setupWizardComunicationService)
  }

  /**
   * Reloads Confluence systems data from the server.
   * Sets loading state during data fetch and updates completion status based on results.
   */
  public override reloadData(): void {
    this.loading = true;
    this.confluenceControllerService.getConfluenceSystems().subscribe({
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
   * Opens the edit dialog for a Confluence system.
   * Uses the GeboUIActionRouter to handle the editing action.
   * @param value - The Confluence system to edit
   */
  editConfluenceSystem(value: GConfluenceSystem) {
    const action: GeboUIActionRequest = {
      actionType: GeboActionType.OPEN,
      context: {},
      contextType: "",
      target: value,
      targetType: "GConfluenceSystem",
      onActionPerformed: (a) => {
        this.reloadData();
      }
    };
    this.geboUIActionsRouter.routeEvent(action);
  }

  /**
   * Opens the dialog for creating a new Confluence system.
   * Sets the createConfluenceWindowOpen flag to true to trigger dialog display.
   */
  createConfluenceSystem() {
    this.createConfluenceWindowOpen=true;
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
   * Closes the Confluence system creation dialog and reloads the data.
   */
  closeDialog() {
    this.createConfluenceWindowOpen=false;
    this.reloadData();
  }
};