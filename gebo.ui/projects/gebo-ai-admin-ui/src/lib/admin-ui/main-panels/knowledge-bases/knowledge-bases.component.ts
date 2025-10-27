/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { Component, OnInit } from "@angular/core";
import { GKnowledgeBase, GProject, KnowledgeBaseControllerService, ProjectsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { EnrichedChild, fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboActionType, GeboAIPluggableKnowledgeAdminBaseTreeSearchService, GeboUIActionRoutingService } from "@Gebo.ai/reusable-ui";
import { AncestorPanelComponent } from "../ancestor-panel/ancestor-admin-panel.component";

/**
 * AI generated comments
 * 
 * This component manages the display and interaction of knowledge bases in the Gebo.ai application.
 * It extends the AncestorPanelComponent and provides functionality to load, display, and create
 * knowledge bases, as well as handle related actions like opening projects.
 */
@Component({
    selector: "knowledge-bases-component",
    templateUrl: "knowledge-bases.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_MODULE, useValue: "KnowledgeBasesPanelModule", multi: false },{
        provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("KnowledgeBasesComponent")
       
    }]
})
export class KnowledgeBasesComponent extends AncestorPanelComponent implements OnInit {
    /**
     * Overrides the parent method to reload the viewed data.
     * This method is called when the view needs to refresh its data.
     */
    public override reloadViewedData(): void {
        this.reloadData();
    }


    /**
     * Array to store the knowledge bases with additional metadata through the EnrichedChild interface.
     */
    public knowledgeBases: EnrichedChild[] = [];


    /**
     * Constructor initializes the component with required services.
     * 
     * @param treeService Service for handling knowledge base tree structure
     * @param knowledgeBaseControllerService Service for knowledge base operations
     * @param projectsService Service for project operations
     * @param actionServices Service for routing UI actions
     */
    constructor(
        private treeService: GeboAIPluggableKnowledgeAdminBaseTreeSearchService,
        private knowledgeBaseControllerService: KnowledgeBaseControllerService,
        private projectsService: ProjectsControllerService,
        private actionServices: GeboUIActionRoutingService) {
        super()
    }

    /**
     * Handles opening a project by routing the open action through the action service.
     * 
     * @param data The project to be opened
     */
    openProject(data: GProject) {
        this.actionServices.routeEvent({
            actionType: GeboActionType.OPEN,
            context: {},
            contextType: "GKnowledgeBase",
            target: data,
            targetType: "GProject",
            onActionPerformed: () => {
                this.reloadData();
            }
        });
    }

    /**
     * Loads knowledge bases from the service and updates the component state.
     * This method is called during initialization and when the data needs to be refreshed.
     */
    public reloadData(): void {
        this.treeService.loadKnowledgeBases().subscribe({
            next: (knowledges) => {
                if (knowledges) {
                    this.knowledgeBases = knowledges;
                }
            }
        });
    }

    /**
     * Angular lifecycle hook that initializes the component by loading knowledge bases.
     */
    ngOnInit(): void {
        this.reloadData();
    }

    /**
     * Handles node collapse events in the tree view.
     * Currently implemented as an empty method.
     * 
     * @param node The node that was collapsed
     */
    nodeCollapse(node: any) { }



    /**
     * Creates a new knowledge base by routing a new action through the action service.
     * Initializes a new knowledge base with default settings (accessible to all).
     */
    newKnowledgeBase() {
        const newKb: GKnowledgeBase = {
            accessibleToAll: true
        };
        this.actionServices.routeEvent({
            actionType: GeboActionType.NEW,
            context: {},
            contextType: "GKnowledgeBase",
            target: newKb,
            targetType: "GKnowledgeBase",
            onActionPerformed: () => {
                this.reloadData();
            }
        });
    }

}