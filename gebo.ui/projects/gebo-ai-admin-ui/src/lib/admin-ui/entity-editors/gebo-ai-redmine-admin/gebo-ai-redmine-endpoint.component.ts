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
import { BrowseParam, GProject, GRedmineProjectEndpoint, GRedmineSystem, JobLauncherControllerService, ProjectsControllerService, RedmineBrowsingControllerService, RedmineSystemsControllerService, VFilesystemReference } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, browsePathObservableCallback, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboActionPerformedEvent, GeboActionType, GeboFormGroupsService, GeboUIActionRequest, GeboUIActionRoutingService, GeboUIOutputForwardingService, loadRootsObservableCallback, reconstructNavigationObservableCallback } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions } from "@openng/optimus-ui/api";
import { map, Observable, of } from "rxjs";
import { doSaveAndPublishCall } from '../utils/save-publish-callback';
import { REDMINE_SYSTEM_TYPE } from "./gebo-ai-redmine-system-admin.component";

/**
 * Editor of a Redmine data source: the Redmine system and the selected
 * projects, sections, issues, wiki pages and files, browsed as a tree.
 */
@Component({
    selector: "gebo-ai-redmine-endpoint-component",
    templateUrl: "gebo-ai-redmine-endpoint.component.html",
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIRedmineModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIRedmineEndpointComponent), multi: false }
    ],
    changeDetection: ChangeDetectionStrategy.Eager,
    standalone: false
})
export class GeboAIRedmineEndpointComponent extends BaseEntityEditingComponent<GRedmineProjectEndpoint> {
    protected override entityName: string = "GRedmineProjectEndpoint";

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
        redmineSystemCode: new FormControl(),
        paths: new FormControl()
    });

    redmineSystemsData: GRedmineSystem[] = [];
    private lastRedmineSystemCode: string = "";

    public loadRootsObservable: loadRootsObservableCallback = () => of({});
    public browsePathObservable: browsePathObservableCallback = (param: BrowseParam) => of({});
    /** Rebuilds the tree of a saved selection, from the projects down to each selected item. */
    public reconstructNavigationObservableCallback: reconstructNavigationObservableCallback = (navigationPoints: VFilesystemReference[]) => {
        return this.redmineBrowsing.getRedmineNavigationStatus(navigationPoints, this.lastRedmineSystemCode);
    };

    newRedmineSystemRequest: GeboUIActionRequest = {
        actionType: GeboActionType.NEW,
        context: {},
        contextType: this.entityName,
        targetType: "GRedmineSystem",
        target: { contentManagementSystemType: REDMINE_SYSTEM_TYPE } as GRedmineSystem
    };

    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private redmineControllerService: RedmineSystemsControllerService,
        private redmineBrowsing: RedmineBrowsingControllerService,
        private projectsController: ProjectsControllerService,
        private JobLauncherControllerService: JobLauncherControllerService,
        private actionsRouter: GeboUIActionRoutingService,
        confirmService: ConfirmationService,
        outputForwardingService?: GeboUIOutputForwardingService
    ) {
        super(injector, geboFormGroupsService, confirmService, actionsRouter, outputForwardingService);
        this.doPeriodicBackendProcessingCheck = true;
        this.formGroup.controls["redmineSystemCode"].valueChanges.subscribe(code => {
            if (code && this.lastRedmineSystemCode !== code) {
                this.lastRedmineSystemCode = code;
                this.loadRootsObservable = () => {
                    return this.redmineBrowsing.getRedmineRoots(code);
                };
                this.browsePathObservable = (param: BrowseParam) => {
                    return this.redmineBrowsing.browseRedminePath(param, code);
                };
            }
        });
    }

    protected override checkBackendProcessing(reference: { className?: string; code?: string; }): Observable<boolean> {
        return this.JobLauncherControllerService.getHasRunningJobs(reference).pipe(map(r => r?.hasRunningJobs === true));
    }

    override ngOnInit(): void {
        super.ngOnInit();
        this.loadRedmineSystems();
    }

    public loadRedmineSystems(): void {
        this.loadingRelatedBackend = true;
        this.redmineControllerService.getRedmineSystems().subscribe({
            next: (systems) => {
                this.redmineSystemsData = systems;
            },
            error: () => {
                this.loadingRelatedBackend = false;
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    protected override onNewData(actualValue: GRedmineProjectEndpoint): void {
    }

    protected override onLoadedPersistentData(actualValue: GRedmineProjectEndpoint): void {
    }

    override findByCode(code: string): Observable<GRedmineProjectEndpoint | null> {
        return this.redmineControllerService.findRedmineEndpointByCode(code);
    }

    override save(value: GRedmineProjectEndpoint): Observable<GRedmineProjectEndpoint> {
        return this.redmineControllerService.updateRedmineEndpoint(value);
    }

    override insert(value: GRedmineProjectEndpoint): Observable<GRedmineProjectEndpoint> {
        return this.redmineControllerService.insertRedmineEndpoint(value);
    }

    override delete(value: GRedmineProjectEndpoint): Observable<boolean> {
        return this.redmineControllerService.deleteRedmineEndpoint(value).pipe(map(() => true));
    }

    override canBeDeleted(value: GRedmineProjectEndpoint): Observable<{ canBeDeleted: boolean; message: string; }> {
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
                            contextType: "GRedmineProjectEndpoint",
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
