/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { BrowseParam, DataPage, GProject, JobLauncherControllerService, MCPClientConfig, McpClientBrowsingControllerService, McpClientConfigControllerService, McpClientSystemsControllerService, MCPClientProjectEndpoint, ProjectsControllerService, VFilesystemReference } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, browsePathObservableCallback, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboActionPerformedEvent, GeboActionType, GeboFormGroupsService, GeboUIActionRequest, GeboUIActionRoutingService, GeboUIOutputForwardingService, loadRootsObservableCallback, reconstructNavigationObservableCallback } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ToastMessageOptions } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { doSaveAndPublishCall } from '../utils/save-publish-callback';

/**
 * Editor for {@link MCPClientProjectEndpoint} entities — an MCP server seen as a
 * "virtual drive" data source.
 * <p>
 * The operator first picks the MCP client configuration (managed by the
 * mcp-clients module) that provides the connection/credentials; the chosen
 * configuration then drives the virtual filesystem selector, which browses the
 * MCP server's resources so a subset (or the whole server) can be selected as the
 * endpoint's {@code paths}. Mirrors {@code GeboAIFileSystemEndpointComponent}.
 */
@Component({
    selector: "gebo-ai-mcpclient-endpoint-component",
    templateUrl: "gebo-ai-mcpclient-endpoint.component.html",
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIMCPClientModule", multi: false },
        {
            provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIMCPClientEndpointComponent),
            multi: false
        }],
    standalone: false
})
export class GeboAIMCPClientEndpointComponent extends BaseEntityEditingComponent<MCPClientProjectEndpoint> {
    protected override entityName: string = "MCPClientProjectEndpoint";

    /** Observable for retrieving available projects (parent project chooser). */
    projectsObservable: Observable<GProject[]> = this.projectsController.getProjects();

    /** Observable of the MCP client configurations available to bind this drive to. */
    mcpConfigsObservable: Observable<MCPClientConfig[]> = this.mcpConfigController
        .listMCPClientConfig({ page: 0, pageSize: 1000 } as DataPage)
        .pipe(map(paged => (paged && paged.content ? paged.content : [])));

    /** Code of the MCP client configuration currently selected (drives browsing). */
    selectedConfigCode?: string;

    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        personalData: new FormControl(),
        parentProjectCode: new FormControl(),
        mcpClientConfigCode: new FormControl(),
        published: new FormControl(),
        paths: new FormControl(),
        programmedTables: new FormControl()
    });

    /** Tracks the published state of the endpoint. */
    published: boolean = false;

    /** Loads the MCP server root(s) for the currently selected configuration. */
    public loadRootsObservable: loadRootsObservableCallback = () => {
        return this.mcpBrowsing.getMCPClientRoots(this.selectedConfigCode as string);
    };

    /** Browses a path under the selected MCP server, returning its resources. */
    public browsePathObservable: browsePathObservableCallback = (param: BrowseParam) => {
        return this.mcpBrowsing.browseMCPClientPath(param, this.selectedConfigCode as string);
    };

    /** Rebuilds the navigation status of previously stored references. */
    public reconstructNavigationObservableCallback: reconstructNavigationObservableCallback = (navigationPoints: VFilesystemReference[]) => {
        return this.mcpBrowsing.getMCPClientNavigationStatus(navigationPoints, this.selectedConfigCode as string);
    };

    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private mcpSystemsService: McpClientSystemsControllerService,
        private mcpBrowsing: McpClientBrowsingControllerService,
        private mcpConfigController: McpClientConfigControllerService,
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
        // The chosen MCP configuration determines which server the virtual drive
        // browses; keep it in sync so the browsing callbacks target the right server.
        this.formGroup.controls["mcpClientConfigCode"].valueChanges.subscribe(configCode => {
            this.selectedConfigCode = configCode;
        });
        this.doPeriodicBackendProcessingCheck = true;
    }

    protected override checkBackendProcessing(reference: { className?: string; code?: string; }): Observable<boolean> {
        return this.JobLauncherControllerService.getHasRunningJobs(reference).pipe(map(r => r?.hasRunningJobs === true));
    }

    override ngOnInit(): void {
        super.ngOnInit();
    }

    protected override onNewData(actualValue: MCPClientProjectEndpoint): void {
        this.selectedConfigCode = actualValue?.mcpClientConfigCode;
    }

    protected override onLoadedPersistentData(actualValue: MCPClientProjectEndpoint): void {
        this.selectedConfigCode = actualValue?.mcpClientConfigCode;
    }

    override findByCode(code: string): Observable<MCPClientProjectEndpoint | null> {
        return this.mcpSystemsService.findMCPClientEndpointsByCode(code).pipe(map(v => v ? v : null));
    }

    override save(value: MCPClientProjectEndpoint): Observable<MCPClientProjectEndpoint> {
        return this.mcpSystemsService.updateMCPClientEndpoint(value);
    }

    override insert(value: MCPClientProjectEndpoint): Observable<MCPClientProjectEndpoint> {
        return this.mcpSystemsService.insertMCPClientEndpoint(value);
    }

    override delete(value: MCPClientProjectEndpoint): Observable<boolean> {
        return this.mcpSystemsService.deleteMCPClientEndpoint(value).pipe(map(() => true));
    }

    override canBeDeleted(value: MCPClientProjectEndpoint): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

    doSaveAndPublish() {
        doSaveAndPublishCall(this);
    }

    /**
     * Saves then triggers the ingestion job for this MCP endpoint and opens its
     * job status view (mirrors the filesystem endpoint publish flow).
     */
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
                            contextType: "MCPClientProjectEndpoint",
                            target: jobStatus.result,
                            targetType: "GJobStatus",
                            onActionPerformed: (event: GeboActionPerformedEvent) => {
                            }
                        };
                        this.cancelAction.emit(true);
                        this.actionsRouter.routeEvent(action);
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
