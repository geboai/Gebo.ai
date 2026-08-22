import { Component, forwardRef, Injector, Input } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { BrowseParam, GProject, JobLauncherControllerService, ProjectsControllerService, GWebdavContentManagementSystem, SecretInfo, SecretsControllerService, GWebdavProjectEndpoint, WebdavBrowsingControllerService, WebdavSystemsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboActionPerformedEvent, GeboActionType, GeboFormGroupsService, GeboUIActionRequest, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions } from "primeng/api";
import { forkJoin, map, Observable, of } from "rxjs";
import { doSaveAndPublishCall } from '../utils/save-publish-callback';
import { loadRootsObservableCallback, browsePathObservableCallback } from "@Gebo.ai/reusable-ui";

const webdavCode: string = "webdab-cms-module";

@Component({
    selector: "gebo-ai-webdav-endpoint-component",
    templateUrl: "gebo-ai-webdav-endpoint.component.html",
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIWebdavModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIWebdavEndpointComponent), multi: false }
    ],
    standalone: false
})
export class GeboAIWebdavEndpointComponent extends BaseEntityEditingComponent<GWebdavProjectEndpoint> {
    protected override entityName: string = "GWebdavProjectEndpoint";

    public handShakeCode?: string;

    @Input() cantModifyProject: boolean = true;

    projectsObservable: Observable<GProject[]> = this.projectsController.getProjects();

    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        personalData: new FormControl(),
        parentProjectCode: new FormControl(),
        readonly: new FormControl(),
        published: new FormControl(),
        synchPeriodically: new FormControl(),
        vectorizeOnlyExtensions: new FormControl(),
        buildSystemsRefs: new FormControl(),
        catalogingCriteria: new FormControl(),
        programmedTables: new FormControl(),
        synchroStrategy: new FormControl(),
        webdavSystemCode: new FormControl(),
        paths: new FormControl()
    });

    published: boolean = false;
    webdavServersData: GWebdavContentManagementSystem[] = [];
    public noAccountsAndSystems: boolean = false;
    private lastWebdavSystemCode: string = "";

    identities: SecretInfo[] = [];

    public loadRootsObservable: loadRootsObservableCallback = () => of({});
    public browsePathObservable: browsePathObservableCallback = (param: BrowseParam) => of({});

    newWebdavServerRequest: GeboUIActionRequest = {
        actionType: GeboActionType.NEW,
        context: {},
        contextType: this.entityName,
        targetType: "GWebdavContentManagementSystem",
        target: { contentManagementSystemType: webdavCode } as GWebdavContentManagementSystem
    };

    private actualIdentityContext: string = webdavCode;

    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private webdavControllerService: WebdavSystemsControllerService,
        private webdavBrowsing: WebdavBrowsingControllerService,
        private secretControllerService: SecretsControllerService,
        private projectsController: ProjectsControllerService,
        private JobLauncherControllerService: JobLauncherControllerService,
        private actionsRouter: GeboUIActionRoutingService,
        confirmService: ConfirmationService,
        outputForwardingService?: GeboUIOutputForwardingService
    ) {
        super(injector, geboFormGroupsService, confirmService, actionsRouter, outputForwardingService);
        this.formGroup.controls["published"].valueChanges.subscribe(published => {
            this.published = published;
        });
        this.doPeriodicBackendProcessingCheck = true;
        this.formGroup.controls["webdavSystemCode"].valueChanges.subscribe(x => {
            if (x && this.lastWebdavSystemCode !== x) {
                this.lastWebdavSystemCode = x;
                this.loadRootsObservable = () => {
                    return this.webdavBrowsing.getWebdavRoots(this.lastWebdavSystemCode);
                };
                this.browsePathObservable = (param: BrowseParam) => {
                    return this.webdavBrowsing.browseWebdavPath(param, this.lastWebdavSystemCode);
                };
            }
        });
    }

    protected override checkBackendProcessing(reference: { className?: string; code?: string; }): Observable<boolean> {
        return this.JobLauncherControllerService.getHasRunningJobs(reference).pipe(map(r => r?.hasRunningJobs === true));
    }

    override ngOnInit(): void {
        super.ngOnInit();
        this.checkExistentAccountAndSystem();
    }

    public checkExistentAccountAndSystem(system?: GWebdavContentManagementSystem) {
        const observables: [Observable<SecretInfo[]>, Observable<GWebdavContentManagementSystem[]>] = [this.secretControllerService.getSecretsByContextCode(this.actualIdentityContext), this.webdavControllerService.getWebdavSystems()];
        this.loadingRelatedBackend = true;
        forkJoin(observables).subscribe({
            next: (data) => {
                this.identities = data[0];
                this.webdavServersData = data[1];
                this.noAccountsAndSystems = (!this.identities || this.identities.length === 0) && (!this.webdavServersData || this.webdavServersData.length === 0);
                if (system && system.code) {
                    this.formGroup.controls["webdavSystemCode"].setValue(system.code);
                }
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    protected override onNewData(actualValue: GWebdavProjectEndpoint): void {
    }

    protected override onLoadedPersistentData(actualValue: GWebdavProjectEndpoint): void {
    }

    override findByCode(code: string): Observable<GWebdavProjectEndpoint | null> {
        return this.webdavControllerService.findWebdavEndpointsByCode(code);
    }

    override save(value: GWebdavProjectEndpoint): Observable<GWebdavProjectEndpoint> {
        return this.webdavControllerService.updateWebdavEndpoint(value);
    }

    override insert(value: GWebdavProjectEndpoint): Observable<GWebdavProjectEndpoint> {
        return this.webdavControllerService.insertWebdavEndpoint(value);
    }

    override delete(value: GWebdavProjectEndpoint): Observable<boolean> {
        return this.webdavControllerService.deleteWebdavEndpoint(value);
    }

    override canBeDeleted(value: GWebdavProjectEndpoint): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

    doSaveAndPublish() {
        doSaveAndPublishCall(this);
    }

    doPublish(): void {
        const callback: (d: any) => void = (d: any) => {
            const objectReference = this.createBackendObjectReference();
            this.loadingRelatedBackend = true;
            this.JobLauncherControllerService.createJob(objectReference).subscribe({
                next: (jobStatus) => {
                    if (jobStatus.result) {
                        this.periodicBackendProcessingCheck();
                        const action: GeboUIActionRequest = {
                            actionType: GeboActionType.OPEN,
                            context: this.entity ? this.entity : {},
                            contextType: "GWebdavProjectEndpoint",
                            target: jobStatus.result,
                            targetType: "GJobStatus",
                            onActionPerformed: (event: GeboActionPerformedEvent) => {
                            }
                        };
                        this.actionsRouter.routeEvent(action);
                        this.cancelAction.emit(true);
                    } else {
                        this.userMessages = jobStatus.messages as ToastMessageOptions[];
                    }
                },
                error: (error) => { },
                complete: () => {
                    this.loadingRelatedBackend = false;
                }
            });
        }
        this.doSave(callback);
    }
}