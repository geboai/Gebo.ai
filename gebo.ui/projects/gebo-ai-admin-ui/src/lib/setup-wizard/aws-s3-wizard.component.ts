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
 * This file contains components and services related to AWS S3 integration in the Gebo.ai application.
 * It includes services for checking AWS S3 status, managing module installation, and a wizard component
 * for AWS S3 configuration, mirroring the SharePoint setup wizard.
 */

import { Component, Injectable } from "@angular/core";
import { AwsS3SystemsControllerService, GAwsS3System, GeboModulesConfigControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, AWS_S3_MODULE, BaseWizardSectionComponent, fieldHostComponentName, GEBO_AI_FIELD_HOST, GeboActionType, GeboUIActionRequest, GeboUIActionRoutingService, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { map, Observable } from "rxjs";
import { GeboRootInstalledModuleService } from "./abstract-module-installed.service";

/**
 * Service that checks the status of AWS S3 integration.
 * Extends AbstractStatusService to provide status information about AWS S3 systems.
 * The status is determined by checking if any AWS S3 systems are registered.
 */
@Injectable()
export class AwsS3StatusService extends AbstractStatusService {
  constructor(private awsS3ControllerService: AwsS3SystemsControllerService) {
    super();
  }

  /**
   * Retrieves the boolean status of AWS S3 integration.
   * @returns An Observable that emits true if at least one AWS S3 system is configured, false otherwise.
   */
  public override getBooleanStatus(): Observable<boolean> {
    return this.awsS3ControllerService.getAwsS3Systems().pipe(map(c => ((c && c.length > 0) ? true : false)));
  }
}

/**
 * Service that manages the installation status of the AWS S3 module.
 * Extends GeboRootInstalledModuleService to handle module-specific operations.
 */
@Injectable()
export class AwsS3InstalledModuleService extends GeboRootInstalledModuleService {
  /**
   * The unique identifier for the AWS S3 module
   */
  protected override moduleCode: string = AWS_S3_MODULE;

  constructor(geboModulesConfigService: GeboModulesConfigControllerService) {
    super(geboModulesConfigService);
  }
}

/**
 * Component that provides a wizard interface for AWS S3 configuration.
 * Extends BaseWizardSectionComponent to integrate with the setup wizard flow.
 * Allows users to view, create, and edit AWS S3 system connections.
 */
@Component({
  selector: "gebo-aws-s3-wizard-component",
  templateUrl: "aws-s3-wizard.component.html",
  standalone: false,
  providers: [{ provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("AwsS3WizardComponent") }]

})
export class AwsS3WizardComponent extends BaseWizardSectionComponent {

  /**
   * Flag that controls the visibility of the AWS S3 creation dialog
   */
  public createAwsS3WindowOpen: boolean = false;

  /**
   * List of configured AWS S3 systems
   */
  public systems: GAwsS3System[] = [];

  constructor(setupWizardComunicationService: SetupWizardComunicationService,
    private awsS3ControllerService: AwsS3SystemsControllerService,
    private geboUIActionsRouter: GeboUIActionRoutingService
  ) {
    super(setupWizardComunicationService)
  }

  /**
   * Loads or refreshes AWS S3 systems data from the backend.
   * Updates the systems array and determines if setup is completed.
   * Sets loading flags appropriately during the process.
   */
  public override reloadData(): void {
    this.loading = true;
    this.awsS3ControllerService.getAwsS3Systems().subscribe({
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
   * Opens the edit dialog for a specific AWS S3 system.
   * Routes an action to the appropriate handler using the action routing service.
   * @param value The AWS S3 system to edit
   */
  editAwsS3System(value: GAwsS3System) {
    const action: GeboUIActionRequest = {
      actionType: GeboActionType.OPEN,
      context: {},
      contextType: "",
      target: value,
      targetType: "GAwsS3System",
      onActionPerformed: (a) => {
        this.reloadData();
      }
    };
    this.geboUIActionsRouter.routeEvent(action);
  }

  /**
   * Opens the dialog for creating a new AWS S3 system.
   * Sets the createAwsS3WindowOpen flag to show the creation dialog.
   */
  createAwsS3System() {
    this.createAwsS3WindowOpen = true;
  }

  /**
   * Closes the AWS S3 creation dialog and reloads the data.
   * This ensures the list of systems is updated after potential changes.
   */
  closeDialog() {
    this.createAwsS3WindowOpen = false;
    this.reloadData();
  }
};
