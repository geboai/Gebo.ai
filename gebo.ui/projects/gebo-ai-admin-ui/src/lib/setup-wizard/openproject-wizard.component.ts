/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, Injectable, ChangeDetectionStrategy } from "@angular/core";
import { GeboModulesConfigControllerService, GOpenProjectSystem, OpenProjectSystemsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, fieldHostComponentName, GEBO_AI_FIELD_HOST, GeboActionType, GeboUIActionRequest, GeboUIActionRoutingService, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { map, Observable } from "rxjs";
import { GeboRootInstalledModuleService } from "./abstract-module-installed.service";
import { OPENPROJECT_MODULE } from "../admin-ui/gebo-ai-standard-modules-injections.module";

@Injectable()
export class OpenProjectStatusService extends AbstractStatusService {
  constructor(private openProjectControllerService: OpenProjectSystemsControllerService) {
    super();
  }

  public override getBooleanStatus(): Observable<boolean> {
    return this.openProjectControllerService.getOpenProjectSystems().pipe(map(c => ((c && c.length > 0) ? true : false)));
  }
}

@Injectable()
export class OpenProjectInstalledModuleService extends GeboRootInstalledModuleService {
  protected override moduleCode: string = OPENPROJECT_MODULE;

  constructor(geboModulesConfigService: GeboModulesConfigControllerService) {
    super(geboModulesConfigService);
  }
}

@Component({
  selector: "gebo-openproject-wizard-component",
  templateUrl: "openproject-wizard.component.html",
  standalone: false,
  changeDetection: ChangeDetectionStrategy.Eager,
  providers: [{ provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("OpenProjectWizardComponent") }]
})
export class OpenProjectWizardComponent extends BaseWizardSectionComponent {

  public createOpenProjectWindowOpen: boolean = false;
  public systems: GOpenProjectSystem[] = [];

  constructor(setupWizardComunicationService: SetupWizardComunicationService,
    private openProjectControllerService: OpenProjectSystemsControllerService,
    private geboUIActionsRouter: GeboUIActionRoutingService
  ) {
    super(setupWizardComunicationService)
  }

  public override reloadData(): void {
    this.loading = true;
    this.openProjectControllerService.getOpenProjectSystems().subscribe({
      next: (value) => {
        this.systems = value;
        this.isSetupCompleted = value && value.length > 0;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  editOpenProjectSystem(value: GOpenProjectSystem) {
    const action: GeboUIActionRequest = {
      actionType: GeboActionType.OPEN,
      context: {},
      contextType: "",
      target: value,
      targetType: "GOpenProjectSystem",
      onActionPerformed: (a) => {
        this.reloadData();
      }
    };
    this.geboUIActionsRouter.routeEvent(action);
  }

  createOpenProjectSystem() {
    this.createOpenProjectWindowOpen = true;
  }

  closeDialog() {
    this.createOpenProjectWindowOpen = false;
    this.reloadData();
  }
}