/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, Injectable, ChangeDetectionStrategy } from "@angular/core";
import { GeboModulesConfigControllerService, GGlpiSystem, GlpiSystemsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, fieldHostComponentName, GEBO_AI_FIELD_HOST, GeboActionType, GeboUIActionRequest, GeboUIActionRoutingService, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { map, Observable } from "rxjs";
import { GeboRootInstalledModuleService } from "./abstract-module-installed.service";
import { GLPI_MODULE } from "../admin-ui/gebo-ai-standard-modules-injections.module";

@Injectable()
export class GlpiStatusService extends AbstractStatusService {
  constructor(private glpiControllerService: GlpiSystemsControllerService) {
    super();
  }

  public override getBooleanStatus(): Observable<boolean> {
    return this.glpiControllerService.getGlpiSystems().pipe(map(c => ((c && c.length > 0) ? true : false)));
  }
}

@Injectable()
export class GlpiInstalledModuleService extends GeboRootInstalledModuleService {
  protected override moduleCode: string = GLPI_MODULE;

  constructor(geboModulesConfigService: GeboModulesConfigControllerService) {
    super(geboModulesConfigService);
  }
}

@Component({
  selector: "gebo-glpi-wizard-component",
  templateUrl: "glpi-wizard.component.html",
  standalone: false,
  changeDetection: ChangeDetectionStrategy.Eager,
  providers: [{ provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GlpiWizardComponent") }]
})
export class GlpiWizardComponent extends BaseWizardSectionComponent {

  public createGlpiWindowOpen: boolean = false;
  public systems: GGlpiSystem[] = [];

  constructor(setupWizardComunicationService: SetupWizardComunicationService,
    private glpiControllerService: GlpiSystemsControllerService,
    private geboUIActionsRouter: GeboUIActionRoutingService
  ) {
    super(setupWizardComunicationService)
  }

  public override reloadData(): void {
    this.loading = true;
    this.glpiControllerService.getGlpiSystems().subscribe({
      next: (value) => {
        this.systems = value;
        this.isSetupCompleted = value && value.length > 0;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  editGlpiSystem(value: GGlpiSystem) {
    const action: GeboUIActionRequest = {
      actionType: GeboActionType.OPEN,
      context: {},
      contextType: "",
      target: value,
      targetType: "GGlpiSystem",
      onActionPerformed: (a) => {
        this.reloadData();
      }
    };
    this.geboUIActionsRouter.routeEvent(action);
  }

  createGlpiSystem() {
    this.createGlpiWindowOpen = true;
  }

  closeDialog() {
    this.createGlpiWindowOpen = false;
    this.reloadData();
  }
}
