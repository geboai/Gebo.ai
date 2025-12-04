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
 * 
 * This file defines the GeboAiProjectAdminComponent, which provides an interface for managing
 * project entities within the Gebo.ai system. It extends BaseEntityEditingComponent
 * to provide CRUD (Create, Read, Update, Delete) operations for GProject entities.
 * The component allows users to create, edit, delete, and manage projects, including
 * their relationships with knowledge bases and parent projects.
 */

import { Component, forwardRef, Injector, Input } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { ContentsResetControllerService, GKnowledgeBase, GProject, KnowledgeBaseControllerService, ProjectsControllerService, GObjectRef } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, BaseEntityEditingComponentAutoDeleteCheck, EnrichedChild, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboAIPluggableKnowledgeAdminBaseTreeSearchService, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";

import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";

/**
 * Component for administering Gebo AI projects
 * This component provides a UI for creating, editing, and managing projects within the system.
 * It handles project properties like code, description, parent-child relationships, and access controls.
 * The component also supports project-related operations like content reindexing.
 */
@Component({
    selector: "gebo-ai-project-admin-component",
    templateUrl: "gebo-ai-project-admin.component.html",
    standalone: false, providers: [ 
        { provide: GEBO_AI_MODULE, useValue: "GeboAiKnowledgeBaseModule", multi: false }, 
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAiProjectAdminComponent),  multi: false   }
    ]
})
export class GeboAiProjectAdminComponent extends BaseEntityEditingComponentAutoDeleteCheck<GProject> {
    /**
     * The entity name for this component
     */
    protected override entityName: string = "GProject";

    /**
     * Form group defining the structure and controls for the project edit form
     * Contains fields for all editable properties of a project
     */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        rootKnowledgeBaseCode: new FormControl(),
        parentProjectCode: new FormControl(),
        accessibleGroups: new FormControl(),
        accessibleUsers: new FormControl(),
        accessibleToAll: new FormControl(),
        objectSpaceType: new FormControl()
    }
    );

    /**
     * Flag indicating whether knowledge base editing is enabled
     */
    @Input() editKnowledgebase: boolean = false;

    /**
     * Observable containing all knowledge bases available for selection
     */
    knowledgeBases: Observable<GKnowledgeBase[]> = this.knowledgeBaseControllerService.getKnowledgeBases();

    /**
     * The parent project of the current project, if any
     */
    public parentProject?: GProject;

    /**
     * Array of child entities related to this project
     */
    treeChilds: EnrichedChild[] = [];
    graphRagContext?: { knowledgeBaseCode?: string; projectCode?: string; reference?: GObjectRef; };
    /**
     * Constructor initializing the component with required services
     * Sets up subscriptions to form value changes to update related data
     * 
     * @param injector Angular dependency injector
     * @param knowledgeBaseControllerService Service for knowledge base operations
     * @param treeService Service for handling project hierarchies
     * @param projectsControllerService Service for project CRUD operations
     * @param geboFGService Service for form group management
     * @param contentsResetService Service for resetting/reindexing content
     * @param confirmService Service for displaying confirmation dialogs
     * @param geboUIActionRoutingService Service for routing UI actions
     * @param outForwardingService Optional service for output forwarding
     */
    constructor(
        injector: Injector,
        private knowledgeBaseControllerService: KnowledgeBaseControllerService,
        private treeService: GeboAIPluggableKnowledgeAdminBaseTreeSearchService,
        private projectsControllerService: ProjectsControllerService,
        private geboFGService: GeboFormGroupsService,
        private contentsResetService: ContentsResetControllerService,
        private confirmService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        private outForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFGService, confirmService, geboUIActionRoutingService, outForwardingService);
        this.formGroup.controls["parentProjectCode"].valueChanges.subscribe((parentProjectCode: string) => {
            if (parentProjectCode) {
                this.projectsControllerService.findProjectByCode(parentProjectCode).subscribe(parent => {
                    this.parentProject = parent;
                });
            }
        });
    }

    /**
     * Lifecycle hook called when persistent data is loaded
     * Loads child entities related to the current project
     * 
     * @param actualValue The loaded project data
     */
    protected override onLoadedPersistentData(actualValue: GProject): void {
        this.graphRagContext = {
            knowledgeBaseCode: actualValue?.rootKnowledgeBaseCode,
            projectCode: actualValue?.code
        };
        this.loadingRelatedBackend = true;
        this.treeService.loadProjectChilds(actualValue).subscribe({
            next: (childs) => {
                this.treeChilds = childs;
            },
            error: (error) => { },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    /**
     * Retrieves a project by its code
     * 
     * @param code The unique code of the project to find
     * @returns An Observable containing either the found project or null
     */
    override findByCode(code: string): Observable<GProject | null> {
        return this.projectsControllerService.findProjectByCode(code);
    }

    /**
     * Saves changes to an existing project
     * 
     * @param value The project data to save
     * @returns An Observable containing the updated project
     */
    override save(value: GProject): Observable<GProject> {
        return this.projectsControllerService.updateProject(value);
    }

    /**
     * Deletes a project
     * 
     * @param value The project to delete
     * @returns An Observable indicating success or failure
     */
    override delete(value: GProject): Observable<boolean> {
        return this.projectsControllerService.deleteProject(value).pipe(map(v => true));
    }

    /**
     * Creates a new project
     * 
     * @param value The project data to insert
     * @returns An Observable containing the newly created project
     */
    override insert(value: GProject): Observable<GProject> {
        return this.projectsControllerService.insertProject(value);
    }

   
    /**
     * Triggers a reindexing of all contents associated with the current project
     * Displays a confirmation dialog before proceeding
     */
    doReindex(): void {
        this.confirmService.confirm({
            header: "Reindex contents command",
            message: "Are you shure you want to reindex these contents?",
            accept: () => {
                this.loadingRelatedBackend = true;
                this.contentsResetService.resetContentsIngestion({
                    projectCode: this.entity?.code
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