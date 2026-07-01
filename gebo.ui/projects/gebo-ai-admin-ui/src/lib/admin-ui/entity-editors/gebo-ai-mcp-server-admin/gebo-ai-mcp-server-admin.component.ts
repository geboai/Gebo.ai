import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GeboMcpServerAdminControllerService, GeboMCPServerConfig, GObjectRefGProjectEndpoint } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboAIPluggableKnowledgeAdminBaseTreeSearchService, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, TreeNode } from "primeng/api";
import { TreeNodeExpandEvent } from "primeng/tree";
import { map, Observable, of } from "rxjs";

@Component({
    selector: "gebo-ai-mcp-server-admin-component",
    templateUrl: "gebo-ai-mcp-server-admin.component.html",
    standalone: false, providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIMcpServerAdminModule", multi: false },
        {
            provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIMCPServerAdminComponent),
            multi: false
        }
    ]
})
export class GeboAIMCPServerAdminComponent extends BaseEntityEditingComponent<GeboMCPServerConfig> {
    protected override entityName: string = "GeboMCPServerConfig";
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        userModified: new FormControl(),
        userCreated: new FormControl(),
        dateModified: new FormControl(),
        dateCreated: new FormControl(),
        exportedUniqueRelativeUrl: new FormControl(),
        enabled: new FormControl(),
        accessibleGroups: new FormControl(),
        accessibleUsers: new FormControl(),
        accessibleToAll: new FormControl(),
        aclAliases: new FormControl(),
        enabledTools: new FormControl(),
        exportedKnowledgeBasesAsResources: new FormControl(),
        exportedProjectsAsResources: new FormControl(),
        exportedProjectEndpoints: new FormControl(),
        exportedPrompts: new FormControl(),
        agentAsTools: new FormControl(),
        agentNetworkAsTools: new FormControl()
    });

    selectedKbs: string[] = [];
    selectedProjs: string[] = [];
    selectedEndpoints: GObjectRefGProjectEndpoint[] = [];

    resourceTreeRoots: TreeNode[] = [];
    selectedResourceNodes: TreeNode[] = [];

    constructor(injector: Injector,
        geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService: GeboUIOutputForwardingService,
        private service: GeboMcpServerAdminControllerService,
        private browsingService:GeboAIPluggableKnowledgeAdminBaseTreeSearchService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
    }

    protected override onLoadedPersistentData(actualValue: GeboMCPServerConfig): void {
        this.initializeData(actualValue);
    }

    protected override onNewData(actualValue: GeboMCPServerConfig): void {
        this.initializeData(actualValue);
    }

    private initializeData(config: GeboMCPServerConfig) {
        this.selectedKbs = [...(config?.exportedKnowledgeBasesAsResources || [])];
        this.selectedProjs = [...(config?.exportedProjectsAsResources || [])];
        this.selectedEndpoints = [...(config?.exportedProjectEndpoints || [])];
        this.selectedResourceNodes = [];
        this.resourceTreeRoots = [];
        this.loadResourceTree();
    }

    private loadResourceTree() {
        this.loadingRelatedBackend = true;
        this.browsingService.loadKnowledgeBases().subscribe({
            next: (enricheds) => {
                const roots: TreeNode[] = [];
                if (enricheds) {
                    enricheds.forEach(entry => {
                        const isSelected = this.selectedKbs.includes(entry.info.code || "");
                        const node: TreeNode = {
                            label: entry.info.description,
                            icon: entry.icon || "pi pi-sitemap",
                            leaf: entry.isLeaf,
                            data: entry
                        };
                        roots.push(node);
                        if (isSelected) {
                            this.selectedResourceNodes.push(node);
                        }
                    });
                }
                this.resourceTreeRoots = roots;
            },
            error: (err) => {
                console.error("Error loading knowledge bases", err);
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    expandResourceNode(event: TreeNodeExpandEvent) {
        const node = event.node;
        const data = node.data;
        if (!data) return;

        node.loading = true;
        if (data.isKnowledgeBase === true) {
            this.browsingService.loadKnowledgeChilds(data.info).subscribe({
                next: (projects) => {
                    if (projects) {
                        const items: TreeNode[] = [];
                        projects.forEach(entry => {
                            const isSelected = this.selectedProjs.includes(entry.info.code || "");
                            const childNode: TreeNode = {
                                label: entry.info.description,
                                icon: entry.icon || "pi pi-list-check",
                                leaf: entry.isLeaf,
                                data: entry,
                                parent: node
                            };
                            items.push(childNode);
                            if (isSelected) {
                                this.selectedResourceNodes.push(childNode);
                            }
                        });
                        node.children = items;
                    }
                },
                complete: () => {
                    node.loading = false;
                }
            });
        } else if (data.isProject === true) {
            this.browsingService.loadProjectChilds(data.info).subscribe({
                next: (childs) => {
                    if (childs) {
                        const items: TreeNode[] = [];
                        childs.forEach(entry => {
                            let isSelected = false;
                            let icon = entry.icon || "pi pi-file";

                            if (entry.isProject) {
                                isSelected = this.selectedProjs.includes(entry.info.code || "");
                                icon = entry.icon || "pi pi-list-check";
                            } else if (entry.isProjectEndpoint) {
                                isSelected = !!this.selectedEndpoints.find(e => e.code === entry.info.code && e.className === entry.className);
                                icon = entry.icon || "pi pi-globe";
                            }

                            const childNode: TreeNode = {
                                label: entry.info.description,
                                icon: icon,
                                leaf: entry.isLeaf,
                                data: entry,
                                parent: node
                            };
                            items.push(childNode);
                            if (isSelected) {
                                this.selectedResourceNodes.push(childNode);
                            }
                        });
                        node.children = items;
                    }
                },
                complete: () => {
                    node.loading = false;
                }
            });
        } else {
            node.loading = false;
        }
    }

    nodeSelect(event: { node: TreeNode }) {
        const node = event.node;
        const data = node.data;
        if (!data) return;

        if (data.isKnowledgeBase) {
            const code = data.info.code;
            if (code && !this.selectedKbs.includes(code)) {
                this.selectedKbs.push(code);
                this.formGroup.controls['exportedKnowledgeBasesAsResources'].setValue([...this.selectedKbs]);
                this.formGroup.controls['exportedKnowledgeBasesAsResources'].markAsDirty();
            }
        } else if (data.isProject) {
            const code = data.info.code;
            if (code && !this.selectedProjs.includes(code)) {
                this.selectedProjs.push(code);
                this.formGroup.controls['exportedProjectsAsResources'].setValue([...this.selectedProjs]);
                this.formGroup.controls['exportedProjectsAsResources'].markAsDirty();
            }
        } else if (data.isProjectEndpoint) {
            const code = data.info.code;
            const className = data.className;
            const description = data.info.description;
            if (code) {
                const found = this.selectedEndpoints.find(e => e.code === code && e.className === className);
                if (!found) {
                    this.selectedEndpoints.push({ code, className, description });
                    this.formGroup.controls['exportedProjectEndpoints'].setValue([...this.selectedEndpoints]);
                    this.formGroup.controls['exportedProjectEndpoints'].markAsDirty();
                }
            }
        }
    }

    nodeUnselect(event: { node: TreeNode }) {
        const node = event.node;
        const data = node.data;
        if (!data) return;

        if (data.isKnowledgeBase) {
            const code = data.info.code;
            if (code) {
                this.selectedKbs = this.selectedKbs.filter(c => c !== code);
                this.formGroup.controls['exportedKnowledgeBasesAsResources'].setValue([...this.selectedKbs]);
                this.formGroup.controls['exportedKnowledgeBasesAsResources'].markAsDirty();
            }
        } else if (data.isProject) {
            const code = data.info.code;
            if (code) {
                this.selectedProjs = this.selectedProjs.filter(c => c !== code);
                this.formGroup.controls['exportedProjectsAsResources'].setValue([...this.selectedProjs]);
                this.formGroup.controls['exportedProjectsAsResources'].markAsDirty();
            }
        } else if (data.isProjectEndpoint) {
            const code = data.info.code;
            const className = data.className;
            if (code) {
                this.selectedEndpoints = this.selectedEndpoints.filter(e => !(e.code === code && e.className === className));
                this.formGroup.controls['exportedProjectEndpoints'].setValue([...this.selectedEndpoints]);
                this.formGroup.controls['exportedProjectEndpoints'].markAsDirty();
            }
        }
    }

    override findByCode(code: string): Observable<GeboMCPServerConfig | null> {
        return this.service.findMcpServerByCode(code);
    }
    override save(value: GeboMCPServerConfig): Observable<GeboMCPServerConfig> {
        return this.service.updateMcpServer(value);
    }
    override insert(value: GeboMCPServerConfig): Observable<GeboMCPServerConfig> {
        return this.service.insertMcpServer(value);
    }
    override delete(value: GeboMCPServerConfig): Observable<boolean> {
        if (value?.code)
            return this.service.deleteMcpServer(value.code).pipe(map(d => true));
        return of(false);
    }
    override canBeDeleted(value: GeboMCPServerConfig): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

}