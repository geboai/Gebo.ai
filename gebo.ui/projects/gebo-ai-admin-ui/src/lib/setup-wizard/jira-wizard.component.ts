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
 * This file contains services and components for managing Jira systems in the Gebo.ai application.
 * It includes status checking, module installation verification, and UI components for the Jira setup wizard.
 */

import { Component, Injectable } from "@angular/core";
import { ConfluenceSystemsControllerService, GConfluenceSystem, GeboModulesConfigControllerService, GJiraSystem, JiraSystemsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, CONFLUENCE_MODULE, fieldHostComponentName, GEBO_AI_FIELD_HOST, GeboActionType, GeboUIActionRequest, GeboUIActionRoutingService, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { map, Observable } from "rxjs";
import { GeboRootInstalledModuleService } from "./abstract-module-installed.service";

/**
 * Service that checks the status of Jira systems.
 * Extends AbstractStatusService to provide a standardized way to check if any Jira systems are configured.
 */
@Injectable()
export class JiraStatusService extends AbstractStatusService {
  constructor(private confluenceControllerService: JiraSystemsControllerService) {
    super();
  }

  /**
   * Retrieves the status of Jira systems.
   * @returns An Observable that emits true if at least one Jira system is configured, false otherwise.
   */
  public override getBooleanStatus(): Observable<boolean> {
    return this.confluenceControllerService.getJiraSystems().pipe(map(c => (c && c.length > 0)));
  }
}

/**
 * Service that verifies if the Jira module is installed.
 * Extends GeboRootInstalledModuleService to handle module-specific installation checks.
 */
@Injectable()
export class JiraInstalledModuleService extends GeboRootInstalledModuleService {
  protected override moduleCode: string = CONFLUENCE_MODULE;

  constructor(geboModulesConfigService: GeboModulesConfigControllerService) {
    super(geboModulesConfigService);
  }
}

/**
 * Component that provides a wizard interface for setting up and managing Jira systems.
 * Extends BaseWizardSectionComponent to integrate with the overall setup wizard flow.
 * Allows users to create, view, and edit Jira system configurations.
 */
@Component({
  selector: "gebo-jira-wizard-component",
  templateUrl: "jira-wizard.component.html",
  standalone: false,
  providers: [{ provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("JiraWizardComponent") }]
})
export class JiraWizardComponent extends BaseWizardSectionComponent {

  public createConfluenceWindowOpen: boolean = false;
  public systems: GJiraSystem[] = [];

  constructor(setupWizardComunicationService: SetupWizardComunicationService,
    private confluenceControllerService: JiraSystemsControllerService,
    private geboUIActionsRouter: GeboUIActionRoutingService
  ) {
    super(setupWizardComunicationService)
  }

  /**
   * Reloads the Jira systems data from the backend.
   * Updates the systems array and sets the completion status based on whether any systems exist.
   * Sets loading state during the data fetch operation.
   */
  public override reloadData(): void {
    this.loading = true;
    this.confluenceControllerService.getJiraSystems().subscribe({
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
   * Opens the edit dialog for a specific Jira system.
   * Uses the GeboUIActionRoutingService to route the edit action.
   * @param value The Jira system to edit
   */
  editJiraSystem(value: GJiraSystem) {
    const action: GeboUIActionRequest = {
      actionType: GeboActionType.OPEN,
      context: {},
      contextType: "",
      target: value,
      targetType: "GJiraSystem",
      onActionPerformed: (a) => {
        this.reloadData();
      }
    };
    this.geboUIActionsRouter.routeEvent(action);
  }

  /**
   * Opens the dialog for creating a new Jira system.
   */
  createJiraSystem() {
    this.createConfluenceWindowOpen = true;

  }

  /**
   * Closes the create/edit dialog and refreshes the data.
   */
  closeDialog() {
    this.createConfluenceWindowOpen = false;
    this.reloadData();
  }
};