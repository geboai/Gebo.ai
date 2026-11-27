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
import { BrowseParam, GGlpiProjectEndpoint, GGlpiSystem, GlpiBrowsingControllerService, GlpiSystemsControllerService, GProject, JobLauncherControllerService, ProjectsControllerService, VFilesystemReference } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, browsePathObservableCallback, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboActionPerformedEvent, GeboActionType, GeboFormGroupsService, GeboUIActionRequest, GeboUIActionRoutingService, GeboUIOutputForwardingService, loadRootsObservableCallback, reconstructNavigationObservableCallback } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions } from "@openng/optimus-ui/api";
import { map, Observable, of } from "rxjs";
import { GLPI_SYSTEM_TYPE } from "./gebo-ai-glpi-system-admin.component";

/**
 * Editor of a GLPI data source: the GLPI system, and the entities, sections,
 * tickets and knowledge base articles selected in its tree.
 */
@Component({
    selector: "gebo-ai-glpi-endpoint-component",
    templateUrl: "gebo-ai-glpi-endpoint.component.html",
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIGlpiModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIGlpiEndpointComponent), multi: false }
    ],
    changeDetection: ChangeDetectionStrategy.Eager,
    standalone: false
})
export class GeboAIGlpiEndpointComponent extends BaseEntityEditingComponent<GGlpiProjectEndpoint> {
    protected override entityName: string = "GGlpiProjectEndpoint";

    @Input() cantModifyProject: boolean = true;

    projectsObservable: Observable<GProject[]> = this.projectsController.getProjects();
    glpiSystemsObservable: Observable<GGlpiSystem[]> = this.glpiControllerService.getGlpiSystems();

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
        glpiSystemCode: new FormControl(),
        paths: new FormControl()
    });

    published: boolean = false;
    private lastGlpiSystemCode: string = "";

    public loadRootsObservable: loadRootsObservableCallback = () => of({});
    public browsePathObservable: browsePathObservableCallback = (param: BrowseParam) => of({});
    public reconstructNavigationObservableCallback: reconstructNavigationObservableCallback = (navigationPoints: VFilesystemReference[]) => {
        return this.glpiBrowsing.getGlpiNavigationStatus(navigationPoints, this.lastGlpiSystemCode);
    };

    newGlpiSystemRequest: GeboUIActionRequest = {
        actionType: GeboActionType.NEW,
        context: {},
        contextType: this.entityName,
        targetType: "GGlpiSystem",
        target: { contentManagementSystemType: GLPI_SYSTEM_TYPE } as GGlpiSystem
    };

    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private glpiControllerService: GlpiSystemsControllerService,
        private glpiBrowsing: GlpiBrowsingControllerService,
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
        // The chosen GLPI system determines which installation the tree browses
        this.formGroup.controls["glpiSystemCode"].valueChanges.subscribe(code => {
            if (code && this.lastGlpiSystemCode !== code) {
                this.lastGlpiSystemCode = code;
                this.loadRootsObservable = () => {
                    return this.glpiBrowsing.getGlpiRoots(this.lastGlpiSystemCode);
                };
                this.browsePathObservable = (param: BrowseParam) => {
                    return this.glpiBrowsing.browseGlpiPath(param, this.lastGlpiSystemCode);
                };
            }
        });
    }

    protected override checkBackendProcessing(reference: { className?: string; code?: string; }): Observable<boolean> {
        return this.JobLauncherControllerService.getHasRunningJobs(reference).pipe(map(r => r?.hasRunningJobs === true));
    }

    override findByCode(code: string): Observable<GGlpiProjectEndpoint | null> {
        return this.glpiControllerService.findGlpiEndpointsByCode(code);
    }

    override save(value: GGlpiProjectEndpoint): Observable<GGlpiProjectEndpoint> {
        return this.glpiControllerService.updateGlpiEndpoint(value);
    }

    override insert(value: GGlpiProjectEndpoint): Observable<GGlpiProjectEndpoint> {
        return this.glpiControllerService.insertGlpiEndpoint(value);
    }

    override delete(value: GGlpiProjectEndpoint): Observable<boolean> {
        return this.glpiControllerService.deleteGlpiEndpoint(value);
    }

    override canBeDeleted(value: GGlpiProjectEndpoint): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
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
                            contextType: "GGlpiProjectEndpoint",
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
        this.saveUnlessReadonly(callback);
    }
}
