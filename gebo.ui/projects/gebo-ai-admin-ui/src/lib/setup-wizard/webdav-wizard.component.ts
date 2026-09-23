import { Component, Injectable } from "@angular/core";
import { GeboModulesConfigControllerService, GWebdavContentManagementSystem, WebdavSystemsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, fieldHostComponentName, GEBO_AI_FIELD_HOST, GeboActionType, GeboUIActionRequest, GeboUIActionRoutingService, SetupWizardComunicationService, WEBDAB_CMS_MODULE } from "@Gebo.ai/reusable-ui";
import { map, Observable } from "rxjs";
import { GeboRootInstalledModuleService } from "./abstract-module-installed.service";

@Injectable()
export class WebdavStatusService extends AbstractStatusService {
  constructor(private webdavControllerService: WebdavSystemsControllerService) {
    super();
  }

  public override getBooleanStatus(): Observable<boolean> {
    return this.webdavControllerService.getWebdavSystems().pipe(map(c => ((c && c.length > 0) ? true : false)));
  }
}

@Injectable()
export class WebdavInstalledModuleService extends GeboRootInstalledModuleService {
  protected override moduleCode: string = WEBDAB_CMS_MODULE;

  constructor(geboModulesConfigService: GeboModulesConfigControllerService) {
    super(geboModulesConfigService);
  }
}

@Component({
  selector: "gebo-webdav-wizard-component",
  templateUrl: "webdav-wizard.component.html",
  standalone: false,
  providers: [{ provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("WebdavWizardComponent") }]
})
export class WebdavWizardComponent extends BaseWizardSectionComponent {

  public createWebdavWindowOpen: boolean = false;
  public systems: GWebdavContentManagementSystem[] = [];

  constructor(setupWizardComunicationService: SetupWizardComunicationService,
    private webdavControllerService: WebdavSystemsControllerService,
    private geboUIActionsRouter: GeboUIActionRoutingService
  ) {
    super(setupWizardComunicationService)
  }

  public override reloadData(): void {
    this.loading = true;
    this.webdavControllerService.getWebdavSystems().subscribe({
      next: (value) => {
        this.systems = value;
        this.isSetupCompleted = value && value.length > 0;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  editWebdavSystem(value: GWebdavContentManagementSystem) {
    const action: GeboUIActionRequest = {
      actionType: GeboActionType.OPEN,
      context: {},
      contextType: "",
      target: value,
      targetType: "GWebdavContentManagementSystem",
      onActionPerformed: (a) => {
        this.reloadData();
      }
    };
    this.geboUIActionsRouter.routeEvent(action);
  }

  createWebdavSystem() {
    this.createWebdavWindowOpen = true;
  }

  closeDialog() {
    this.createWebdavWindowOpen = false;
    this.reloadData();
  }
}