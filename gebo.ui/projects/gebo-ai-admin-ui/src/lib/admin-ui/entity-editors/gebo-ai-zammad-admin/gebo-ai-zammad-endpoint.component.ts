/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, forwardRef, Injector, Input, ChangeDetectionStrategy } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { BrowseParam, GProject, GZammadProjectEndpoint, GZammadSystem, JobLauncherControllerService, ProjectsControllerService, ZammadBrowsingControllerService, ZammadSystemsControllerService, VFilesystemReference } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, browsePathObservableCallback, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboActionPerformedEvent, GeboActionType, GeboFormGroupsService, GeboUIActionRequest, GeboUIActionRoutingService, GeboUIOutputForwardingService, loadRootsObservableCallback, reconstructNavigationObservableCallback } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions } from "@openng/optimus-ui/api";
import { map, Observable, of } from "rxjs";
import { doSaveAndPublishCall } from '../utils/save-publish-callback';
import { ZAMMAD_SYSTEM_TYPE } from "./gebo-ai-zammad-system-admin.component";

/**
 * Editor of a Zammad data source: the Zammad system and the selected groups,
 * tickets, knowledge bases, categories and answers, browsed as a tree.
 */
@Component({
    selector: "gebo-ai-zammad-endpoint-component",
    templateUrl: "gebo-ai-zammad-endpoint.component.html",
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIZammadModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIZammadEndpointComponent), multi: false }
    ],
    changeDetection: ChangeDetectionStrategy.Eager,
    standalone: false
})
export class GeboAIZammadEndpointComponent extends BaseEntityEditingComponent<GZammadProjectEndpoint> {
    protected override entityName: string = "GZammadProjectEndpoint";

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
        openZips: new FormControl(),
        zammadSystemCode: new FormControl(),
        paths: new FormControl()
    });

    zammadSystemsData: GZammadSystem[] = [];
    private lastZammadSystemCode: string = "";

    public loadRootsObservable: loadRootsObservableCallback = () => of({});
    public browsePathObservable: browsePathObservableCallback = (param: BrowseParam) => of({});
    /** Rebuilds the tree of a saved selection, from the groups and knowledge bases down to each selected item. */
    public reconstructNavigationObservableCallback: reconstructNavigationObservableCallback = (navigationPoints: VFilesystemReference[]) => {
        return this.zammadBrowsing.getZammadNavigationStatus(navigationPoints, this.lastZammadSystemCode);
    };

    newZammadSystemRequest: GeboUIActionRequest = {
        actionType: GeboActionType.NEW,
        context: {},
        contextType: this.entityName,
        targetType: "GZammadSystem",
        target: { contentManagementSystemType: ZAMMAD_SYSTEM_TYPE } as GZammadSystem
    };

    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private zammadControllerService: ZammadSystemsControllerService,
        private zammadBrowsing: ZammadBrowsingControllerService,
        private projectsController: ProjectsControllerService,
        private JobLauncherControllerService: JobLauncherControllerService,
        private actionsRouter: GeboUIActionRoutingService,
        confirmService: ConfirmationService,
        outputForwardingService?: GeboUIOutputForwardingService
    ) {
        super(injector, geboFormGroupsService, confirmService, actionsRouter, outputForwardingService);
        this.doPeriodicBackendProcessingCheck = true;
        this.formGroup.controls["zammadSystemCode"].valueChanges.subscribe(code => {
            if (code && this.lastZammadSystemCode !== code) {
                this.lastZammadSystemCode = code;
                this.loadRootsObservable = () => {
                    return this.zammadBrowsing.getZammadRoots(code);
                };
                this.browsePathObservable = (param: BrowseParam) => {
                    return this.zammadBrowsing.browseZammadPath(param, code);
                };
            }
        });
    }

    protected override checkBackendProcessing(reference: { className?: string; code?: string; }): Observable<boolean> {
        return this.JobLauncherControllerService.getHasRunningJobs(reference).pipe(map(r => r?.hasRunningJobs === true));
    }

    override ngOnInit(): void {
        super.ngOnInit();
        this.loadZammadSystems();
    }

    public loadZammadSystems(): void {
        this.loadingRelatedBackend = true;
        this.zammadControllerService.getZammadSystems().subscribe({
            next: (systems) => {
                this.zammadSystemsData = systems;
            },
            error: () => {
                this.loadingRelatedBackend = false;
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    protected override onNewData(actualValue: GZammadProjectEndpoint): void {
    }

    protected override onLoadedPersistentData(actualValue: GZammadProjectEndpoint): void {
    }

    override findByCode(code: string): Observable<GZammadProjectEndpoint | null> {
        return this.zammadControllerService.findZammadEndpointByCode(code);
    }

    override save(value: GZammadProjectEndpoint): Observable<GZammadProjectEndpoint> {
        return this.zammadControllerService.updateZammadEndpoint(value);
    }

    override insert(value: GZammadProjectEndpoint): Observable<GZammadProjectEndpoint> {
        return this.zammadControllerService.insertZammadEndpoint(value);
    }

    override delete(value: GZammadProjectEndpoint): Observable<boolean> {
        return this.zammadControllerService.deleteZammadEndpoint(value).pipe(map(() => true));
    }

    override canBeDeleted(value: GZammadProjectEndpoint): Observable<{ canBeDeleted: boolean; message: string; }> {
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
                            contextType: "GZammadProjectEndpoint",
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
                error: () => {
                    this.loadingRelatedBackend = false;
                },
                complete: () => {
                    this.loadingRelatedBackend = false;
                }
            });
        }
        this.saveUnlessReadonly(callback);
    }
}
