/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




/**
 * AI generated comments
 * This file implements a Knowledge Base administration component that extends BaseEntityEditingComponent
 * from the Gebo.ai reusable UI library. The component allows users to manage knowledge bases
 * with functionalities like creating, updating, deleting, and linking to projects.
 */

import { Component, forwardRef, Injector, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { ContentsResetControllerService, EmbeddingModelsControllersService, GKnowledgeBase, GObjectRef, GProject, KnowledgeBaseControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, EnrichedChild, GEBO_AI_FIELD_HOST, GeboActionPerformedEvent, GeboActionPerformedType, GeboActionType, GeboAIPluggableKnowledgeAdminBaseTreeSearchService, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { forkJoin, map, Observable, of } from "rxjs";


/**
 * Component for administering knowledge bases in the Gebo.ai system.
 * Provides a UI for creating, viewing, editing, and deleting knowledge bases,
 * as well as managing associated projects and embedding models.
 */
@Component({
    selector: "gebo-ai-knowledgebase-admin-component",
    templateUrl: "gebo-ai-knowledgebase-admin.component.html",
    standalone: false, providers: [{
    provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAiKnowledgeBaseAdminComponent),
    multi: true
  }]
})
export class GeboAiKnowledgeBaseAdminComponent extends BaseEntityEditingComponent<GKnowledgeBase> {
    /**
     * Name of the entity type being managed by this component
     */
    protected override entityName: string = "GKnowledgeBase";

    /**
     * Form group containing all editable fields of the knowledge base
     */
    formGroup: FormGroup = new FormGroup(
        {
            code: new FormControl(),
            parentCode: new FormControl(),
            description: new FormControl(),
            parentObjectType: new FormControl(),
            contentType: new FormControl(),
            objectType: new FormControl(),
            rootObjectCode: new FormControl(),
            knowledgeBaseReferences: new FormControl(),
            embeddingModelReferences: new FormControl(),
            accessibleGroups: new FormControl(),
            accessibleUsers: new FormControl(),
            accessibleToAll: new FormControl(),
            objectSpaceType: new FormControl()
        }
    );

    /**
     * List of child projects under this knowledge base
     */
    public rootChildProjects: EnrichedChild[] = [];

    /**
     * Available embedding models that can be associated with this knowledge base
     */
    public modelsChoicesSource: { description?: string, code?: string, className?: string; }[] = [];

    /**
     * All knowledge bases in the system (excluding the current one)
     */
    allKnowledgeBaseData: GKnowledgeBase[] = [];
    graphRagContext?: { knowledgeBaseCode?: string; projectCode?: string; reference?: GObjectRef; };

    /**
     * Initializes the component with required services and dependencies
     */
    constructor(
        injector: Injector,
        private treeSearchService: GeboAIPluggableKnowledgeAdminBaseTreeSearchService,
        private knowledgeBaseControllerService: KnowledgeBaseControllerService,
        private embeddingConfigurationsControllerService: EmbeddingModelsControllersService,
        private geboFGService: GeboFormGroupsService,
        private contentsResetService: ContentsResetControllerService,
        private confirmService: ConfirmationService,

        private actionServices: GeboUIActionRoutingService, private outForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFGService, confirmService, actionServices, outForwardingService);

    }

    /**
     * Returns a comma-separated string of all referenced knowledge base descriptions
     */
    public get kbDescriptions(): string {
        let outValue: string = "";
        if (this.allKnowledgeBaseData && this.allKnowledgeBaseData.length && this.entity && this.entity.knowledgeBaseReferences) {
            this.entity.knowledgeBaseReferences.forEach(ov => {
                const foundThis = this.allKnowledgeBaseData.find(x => x.code === ov);
                outValue = outValue + (outValue.length ? "," : "") + (foundThis ? foundThis?.description : "");
            });
        }
        if (outValue.length === 0) {
            outValue = "No other knowledge base references";
        }
        return outValue;
    }
    protected override onLoadedPersistentData(actualValue: GKnowledgeBase): void {
        super.onLoadedPersistentData(actualValue);
        this.graphRagContext={
            knowledgeBaseCode: actualValue?.code
        };
    }
    /**
     * Initializes the component by loading embedding models and knowledge bases
     */
    override ngOnInit(): void {
        super.ngOnInit();
        this.loadingRelatedBackend = true;

        forkJoin([this.embeddingConfigurationsControllerService.getRuntimeConfiguredEmbeddingModels(), this.knowledgeBaseControllerService.getKnowledgeBases()]).subscribe({
            next: recvd => {
                const values = recvd[0];
                this.allKnowledgeBaseData = recvd[1].filter(x => x.code !== this.entity?.code);
                const modelsChoicesSource: { description?: string, code?: string, className?: string }[] = [];
                values.forEach(entry => {
                    const config = entry.configuration;
                    const reference = entry.objectReference;
                    modelsChoicesSource.push({
                        code: reference?.code,
                        description: config?.description,
                        className: reference?.className
                    });
                });
                this.modelsChoicesSource = modelsChoicesSource;
            }, error: (error) => {

            }, complete: () => {
                this.loadingRelatedBackend = false;
            }
        })
    }

    /**
     * Responds to changes in the component's inputs, loading projects when the entity changes
     */
    override ngOnChanges(changes: SimpleChanges): void {
        super.ngOnChanges(changes);
        if (changes["entity"] && this.entity && this.entity.code) {
            this.loadProjects();
        }
    }

    /**
     * Loads all projects associated with the current knowledge base
     */
    public loadProjects(): void {
        const thisEntity: GKnowledgeBase = this.entity?.code ? this.entity : this.formGroup.value;
        if (thisEntity && thisEntity.code) {
            this.loadingRelatedBackend = true;
            this.treeSearchService.loadKnowledgeChilds(thisEntity).subscribe({
                next: projects => {
                    this.rootChildProjects = projects;
                },
                complete: () => {
                    this.loadingRelatedBackend = false;
                }
            });
        }
    }

    /**
     * Creates a new project under the current knowledge base
     */
    newProject(): void {
        this.actionServices.routeEvent(
            {
                actionType: GeboActionType.NEW,
                context: this.entity ? this.entity : {},
                contextType: "GKnowledgebase",
                target: {
                    code: null,
                    description: null,
                    rootKnowledgeBaseCode: this.formGroup.controls["code"].value
                } as any,
                targetType: "GProject",
                onActionPerformed: (event: GeboActionPerformedEvent) => {
                    switch (event.actionType) {
                        case GeboActionPerformedType.CLOSING_WINDOW: { }; break;
                        default: {
                            this.loadProjects();
                        }; break;
                    }
                }
            }
        );

    }

    /**
     * Opens an existing project for editing
     * @param data The project to open
     */
    openProject(data: GProject) {
        this.actionServices.routeEvent(
            {
                actionType: GeboActionType.OPEN,
                context: this.entity ? this.entity : {},
                contextType: "GKnowledgebase",
                target: data,
                targetType: "GProject",
                onActionPerformed: (event: GeboActionPerformedEvent) => {
                    switch (event.actionType) {
                        case GeboActionPerformedType.CLOSING_WINDOW: { }; break;
                        default: {
                            this.loadProjects();
                        }; break;
                    }
                }
            }
        );
    }

    /**
     * Fetches a knowledge base by its code
     * @param code The unique code of the knowledge base
     * @returns Observable containing the found knowledge base or null
     */
    override findByCode(code: string): Observable<GKnowledgeBase | null> {
        return this.knowledgeBaseControllerService.findKnowledgeBaseByCode(code);
    }

    /**
     * Saves changes to an existing knowledge base
     * @param value The knowledge base with updated values
     * @returns Observable containing the updated knowledge base
     */
    override save(value: GKnowledgeBase): Observable<GKnowledgeBase> {

        return this.knowledgeBaseControllerService.updateKnowledgeBase(value);
    }

    /**
     * Creates a new knowledge base
     * @param value The knowledge base data to insert
     * @returns Observable containing the newly created knowledge base
     */
    override insert(value: GKnowledgeBase): Observable<GKnowledgeBase> {

        return this.knowledgeBaseControllerService.insertKnowledgeBase(value);
    }

    /**
     * Deletes a knowledge base
     * @param value The knowledge base to delete
     * @returns Observable indicating success (true) or failure
     */
    override delete(value: GKnowledgeBase): Observable<boolean> {

        return this.knowledgeBaseControllerService.deleteKnowledgeBase(value).pipe(map(v => true));
    }

    /**
     * Determines if a knowledge base can be deleted
     * @param value The knowledge base to check
     * @returns Observable containing deletion status and a message
     */
    override canBeDeleted(value: GKnowledgeBase): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

    /**
     * Triggers reindexing of the knowledge base contents after confirmation
     */
    doReindex(): void {
        this.confirmService.confirm({
            header: "Reindex contents command",
            message: "Are you shure you want to reindex these contents?",
            accept: () => {
                this.loadingRelatedBackend = true;
                this.contentsResetService.resetContentsIngestion({
                    knowledgeBaseCode: this.entity?.code
                }).subscribe({
                    next: (value) => {

                    },
                    complete: () => {
                        this.loadingRelatedBackend = false;
                    }
                })
            }
        })
    }
}