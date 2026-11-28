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
import { BrowseParam, GProject, GOpenProjectProjectEndpoint, GOpenProjectSystem, JobLauncherControllerService, ProjectsControllerService, OpenProjectBrowsingControllerService, OpenProjectSystemsControllerService, VFilesystemReference } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, browsePathObservableCallback, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboActionPerformedEvent, GeboActionType, GeboFormGroupsService, GeboUIActionRequest, GeboUIActionRoutingService, GeboUIOutputForwardingService, loadRootsObservableCallback, reconstructNavigationObservableCallback } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions } from "@openng/optimus-ui/api";
import { map, Observable, of } from "rxjs";
import { doSaveAndPublishCall } from '../utils/save-publish-callback';
import { OPENPROJECT_SYSTEM_TYPE } from "./gebo-ai-openproject-system-admin.component";

/**
 * Editor of an OpenProject data source: the OpenProject system and the selected
 * projects, sections, work packages, documents, meetings and news, browsed as a tree.
 */
@Component({
    selector: "gebo-ai-openproject-endpoint-component",
    templateUrl: "gebo-ai-openproject-endpoint.component.html",
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIOpenProjectModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIOpenProjectEndpointComponent), multi: false }
    ],
    changeDetection: ChangeDetectionStrategy.Eager,
    standalone: false
})
export class GeboAIOpenProjectEndpointComponent extends BaseEntityEditingComponent<GOpenProjectProjectEndpoint> {
    protected override entityName: string = "GOpenProjectProjectEndpoint";

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
        openProjectSystemCode: new FormControl(),
        paths: new FormControl()
    });

    openProjectSystemsData: GOpenProjectSystem[] = [];
    private lastOpenProjectSystemCode: string = "";

    public loadRootsObservable: loadRootsObservableCallback = () => of({});
    public browsePathObservable: browsePathObservableCallback = (param: BrowseParam) => of({});
    /** Rebuilds the tree of a saved selection, from the projects down to each selected item. */
    public reconstructNavigationObservableCallback: reconstructNavigationObservableCallback = (navigationPoints: VFilesystemReference[]) => {
        return this.openProjectBrowsing.getOpenProjectNavigationStatus(navigationPoints, this.lastOpenProjectSystemCode);
    };

    newOpenProjectSystemRequest: GeboUIActionRequest = {
        actionType: GeboActionType.NEW,
        context: {},
        contextType: this.entityName,
        targetType: "GOpenProjectSystem",
        target: { contentManagementSystemType: OPENPROJECT_SYSTEM_TYPE } as GOpenProjectSystem
    };

    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private openProjectControllerService: OpenProjectSystemsControllerService,
        private openProjectBrowsing: OpenProjectBrowsingControllerService,
        private projectsController: ProjectsControllerService,
        private JobLauncherControllerService: JobLauncherControllerService,
        private actionsRouter: GeboUIActionRoutingService,
        confirmService: ConfirmationService,
        outputForwardingService?: GeboUIOutputForwardingService
    ) {
        super(injector, geboFormGroupsService, confirmService, actionsRouter, outputForwardingService);
        this.doPeriodicBackendProcessingCheck = true;
        this.formGroup.controls["openProjectSystemCode"].valueChanges.subscribe(code => {
            if (code && this.lastOpenProjectSystemCode !== code) {
                this.lastOpenProjectSystemCode = code;
                this.loadRootsObservable = () => {
                    return this.openProjectBrowsing.getOpenProjectRoots(code);
                };
                this.browsePathObservable = (param: BrowseParam) => {
                    return this.openProjectBrowsing.browseOpenProjectPath(param, code);
                };
            }
        });
    }

    protected override checkBackendProcessing(reference: { className?: string; code?: string; }): Observable<boolean> {
        return this.JobLauncherControllerService.getHasRunningJobs(reference).pipe(map(r => r?.hasRunningJobs === true));
    }

    override ngOnInit(): void {
        super.ngOnInit();
        this.loadOpenProjectSystems();
    }

    public loadOpenProjectSystems(): void {
        this.loadingRelatedBackend = true;
        this.openProjectControllerService.getOpenProjectSystems().subscribe({
            next: (systems) => {
                this.openProjectSystemsData = systems;
            },
            error: () => {
                this.loadingRelatedBackend = false;
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    protected override onNewData(actualValue: GOpenProjectProjectEndpoint): void {
    }

    protected override onLoadedPersistentData(actualValue: GOpenProjectProjectEndpoint): void {
    }

    override findByCode(code: string): Observable<GOpenProjectProjectEndpoint | null> {
        return this.openProjectControllerService.findOpenProjectEndpointByCode(code);
    }

    override save(value: GOpenProjectProjectEndpoint): Observable<GOpenProjectProjectEndpoint> {
        return this.openProjectControllerService.updateOpenProjectEndpoint(value);
    }

    override insert(value: GOpenProjectProjectEndpoint): Observable<GOpenProjectProjectEndpoint> {
        return this.openProjectControllerService.insertOpenProjectEndpoint(value);
    }

    override delete(value: GOpenProjectProjectEndpoint): Observable<boolean> {
        return this.openProjectControllerService.deleteOpenProjectEndpoint(value).pipe(map(() => true));
    }

    override canBeDeleted(value: GOpenProjectProjectEndpoint): Observable<{ canBeDeleted: boolean; message: string; }> {
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
                            contextType: "GOpenProjectProjectEndpoint",
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
