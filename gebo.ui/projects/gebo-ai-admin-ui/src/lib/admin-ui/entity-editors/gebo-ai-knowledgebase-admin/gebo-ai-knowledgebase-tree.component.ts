/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { Component, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { GKnowledgeBase, GProject, IngestionFileType, IngestionFileTypesLibraryControllerService, IngestionHandlerConfig, VDocumentInfo } from "@Gebo.ai/gebo-ai-rest-api";
import { EnrichedChild, extractTargetType, fieldHostComponentName, GEBO_AI_FIELD_HOST, GeboActionPerformedEvent, GeboActionType, GeboAIPluggableKnowledgeAdminBaseTreeSearchService, GeboUIActionRequest, GeboUIActionRoutingService, getNodeIcon, getVFSIcon, isProjectEndpoint } from "@Gebo.ai/reusable-ui";
import { TreeNode } from "primeng/api";
import { TreeNodeExpandEvent, TreeNodeSelectEvent } from "primeng/tree";

/**
 * AI generated comments
 * 
 * This component displays a hierarchical tree view of knowledge bases, projects, and files.
 * It provides functionality to navigate through the tree structure, expand nodes to view children,
 * and perform actions on tree nodes such as editing and creating new elements.
 * The tree supports various node types including knowledge bases, projects, project endpoints,
 * virtual folders, and files.
 */
@Component(
  {
    selector: "gebo-ai-knowledgebase-tree-component",
    templateUrl: "gebo-ai-knowledgebase-tree.component.html",
    standalone: false,
    providers: [{
      provide: GEBO_AI_FIELD_HOST, useExisting: fieldHostComponentName("GeboAiKnowledgeBaseTreeComponent"),
      multi: true
    }]
  }
)
export class GeboAiKnowledgeBaseTreeComponent implements OnInit, OnChanges {
  /** Root nodes of the tree */
  public roots: TreeNode[] = [];
  /** Currently selected nodes in the tree */
  public selectedNodes: TreeNode[] = [];
  /** Cache of available ingestion handlers */
  private handlersLibrary: IngestionHandlerConfig[] = [];
  /** Cache of supported file types for ingestion */
  private fileTypesLibrary: IngestionFileType[] = [];
  /** Input data representing the initial tree structure */
  @Input() data: EnrichedChild[] = [];
  /** Flag indicating whether the component is currently loading data */
  public loading: boolean = false;

  /**
   * Constructor initializes required services
   * @param actionServices Service for routing UI actions
   * @param childsSearchService Service for loading child nodes in the tree
   * @param ingestionFileTypesLibraryService Service for fetching ingestion file type information
   */
  constructor(
    private actionServices: GeboUIActionRoutingService,
    private childsSearchService: GeboAIPluggableKnowledgeAdminBaseTreeSearchService,
    private ingestionFileTypesLibraryService: IngestionFileTypesLibraryControllerService) {

  }

  /**
   * Initializes the component by loading ingestion file types and handlers
   * from the server when the component is created
   */
  ngOnInit(): void {
    this.loading = true;
    this.ingestionFileTypesLibraryService.getIngestionReadingModules().subscribe({
      next: (value) => {
        this.handlersLibrary = value;
        if (value) {
          value.forEach(v => {
            v.fileTypes?.forEach(ft => {
              this.fileTypesLibrary.push(ft);
            });
          });
        }
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  /**
   * Responds to changes in input properties, specifically rebuilding
   * the tree roots when the data input changes
   * @param changes The changes that have occurred
   */
  ngOnChanges(changes: SimpleChanges): void {
    if (changes["data"] && this.data) {
      const roots: TreeNode[] = [];
      this.data.forEach(entry => {
        roots.push({
          label: entry.info.description,
          icon: getNodeIcon(entry),
          leaf: false,
          data: entry
        });
      });
      this.roots = roots;
    }
  }

  /**
   * Handles updates to a node's data by triggering node expansion
   * @param node The tree node that has been updated
   */
  onDataUpdate(node: TreeNode) {
    const event: any = {
      node: node
    };
    this.nodeExpand(event);
  }

  /**
   * Handles node expansion events by loading the appropriate child nodes based on node type
   * @param event The expansion event containing the node to expand
   */
  nodeExpand(event: TreeNodeExpandEvent) {
    if (event.node.data.isKnowledgeBase === true) {
      event.node.loading = true;
      this.loading = true;
      this.childsSearchService.loadKnowledgeChilds(
        event.node.data.info
      ).subscribe({
        next: (projects) => {
          if (projects) {
            const items: TreeNode[] = [];
            projects.forEach(entry => {
              items.push({
                label: entry.info.description,
                icon: getNodeIcon(entry),
                leaf: entry.isLeaf,
                data: entry,
                parent: event.node
              })
            });
            event.node.children = items;
            event.node.expanded = true;
          }
        },
        complete: () => {
          this.loading = false;
          event.node.loading = false;
        }
      });
    } else if (event.node.data.isProject === true) {
      const project: GProject = event.node.data.info;

      if (project.rootKnowledgeBaseCode && project.code) {
        this.loading = true;
        event.node.loading = true;
        this.childsSearchService.loadProjectChilds(project).subscribe({
          next: (childs) => {
            if (childs) {
              const items: TreeNode[] = [];
              childs.forEach(entry => {
                items.push({
                  label: entry.info.description,
                  icon: getNodeIcon(entry),
                  leaf: entry.isLeaf,
                  data: entry,
                  parent: event.node
                })
              });
              event.node.children = items;
            }
          },
          complete: () => {
            this.loading = false;
            event.node.loading = false;
          }
        });
      }
    } else if (isProjectEndpoint(event.node.data)) {
      //call root childs
      event.node.loading = true;
      this.loading = true;
      this.childsSearchService.loadRootProjectEndpointChilds(event.node.data).subscribe({
        next: (childs) => {
          if (childs) {
            const items: TreeNode[] = [];
            childs.forEach(entry => {
              if (entry.isVirtualFile === true) {
                const fileType = this.fileTypesLibrary.find(x => x.extensions?.find(w => w === (entry.info as VDocumentInfo).extension));
                entry.programmingLanguage = fileType?.programmingLanguage;
                entry.fileTypeId = fileType?.fileTypeId;
                entry.treatAs = fileType?.treatAs;
                entry.uiViewable = fileType?.uiViewable === true ? true : false;
              }
              items.push({
                label: entry.info.name,
                icon: getVFSIcon(entry),
                leaf: entry.isLeaf,
                data: entry,
                parent: event.node
              })
            });
            event.node.children = items;
          }
        },
        complete: () => {
          this.loading = false;
          event.node.loading = false;
        }
      });

    } else if (event.node.data.isVirtualFolder) {
      event.node.loading = true;
      this.loading = true;
      this.childsSearchService.loadNestedProjectEndpointChilds(event.node.data).subscribe({
        next: (childs) => {
          if (childs) {
            const items: TreeNode[] = [];
            childs.forEach(entry => {
              if (entry.isVirtualFile === true) {
                const fileType = this.fileTypesLibrary.find(x => x.extensions?.find(w => w === (entry.info as VDocumentInfo).extension));
                entry.programmingLanguage = fileType?.programmingLanguage;
                entry.fileTypeId = fileType?.fileTypeId;
                entry.treatAs = fileType?.treatAs;
                entry.uiViewable = fileType?.uiViewable === true ? true : false;
              }
              items.push({
                label: entry.info.name,
                icon: getVFSIcon(entry),
                leaf: entry.isLeaf,
                data: entry,
                parent: event.node
              })
            });
            event.node.children = items;
          }
        },
        complete: () => {
          this.loading = false;
          event.node.loading = false;
        }
      });

    }
  }

  /**
   * Handles node collapse events
   * @param node The collapsed node
   */
  nodeCollapse(node: any) { }

  /**
   * Opens the knowledge base editor for the selected node
   * @param node The knowledge base node to edit
   */
  openEditKnowledgeBase(node: TreeNode): void {
    const data = node.data.info;
    this.actionServices.routeEvent({
      actionType: GeboActionType.OPEN,
      context: {},
      contextType: "GKnowledgeBase",
      target: data,
      targetType: "GKnowledgeBase",
      onActionPerformed: (event: GeboActionPerformedEvent) => {
        this.reloadKnowledgebaseNodes();
      }
    });
  }

  /**
   * Creates a new root project for the selected knowledge base node
   * @param node The knowledge base node to create a project under
   */
  createRootProject(node: TreeNode) {
    const data: GKnowledgeBase = node.data.info
    const project: GProject = {
      rootKnowledgeBaseCode: data.code,
      accessibleToAll: true
    };
    this.actionServices.routeEvent({
      actionType: GeboActionType.NEW,
      context: {},
      contextType: "GKnowledgeBase",
      target: project,
      targetType: "GProject",
      onActionPerformed: (event: GeboActionPerformedEvent) => {
        this.reloadKnowledgeBaseChilds(node);
      }
    });

  }

  /**
   * Opens the project editor for the selected node
   * @param node The project node to edit
   */
  openEditProject(node: TreeNode): void {
    const data: GProject = node.data.info
    this.actionServices.routeEvent({
      actionType: GeboActionType.OPEN,
      context: {},
      contextType: "GKnowledgeBase",
      target: data,
      targetType: "GProject",
      onActionPerformed: (event: GeboActionPerformedEvent) => {
        if (node.parent?.data.isProject === true) {
          this.reloadProjectChilds(node.parent);
        } else {
          this.reloadKnowledgeBaseChilds(node.parent);
        }
      }
    });

  }
  /** Flag indicating whether content viewing is open */
  openContentViewing: boolean = false;
  /** Code of the content being viewed */
  openContentViewingCode: string = "";

  /**
   * Opens the appropriate editor or viewer for a generic node
   * @param node The node to open
   */
  openGenericalNode(node: TreeNode) {
    let viewGenericalContent: boolean = false;
    const data: EnrichedChild = node.data;
    const request: GeboUIActionRequest = {
      actionType: GeboActionType.OPEN,
      context: {},
      contextType: "Unknown",
      target: data.info,
      targetType: "Unkown",
      onActionPerformed: (eventObject) => {
        this.reloadProjectChilds(node.parent);
      }
    };
    if (data.isProject === true) {
      request.targetType = "GProject";
    } else
      if (data.isProjectEndpoint === true) {
        request.targetType = extractTargetType(data);
      }
    {
      viewGenericalContent = node.data.isVirtualFile;
    }
    if (viewGenericalContent !== true) {
      this.actionServices.routeEvent(request);
    } else {

      this.openContentViewingCode = node.data.info.code;
      this.openContentViewing = true;
    }
  }

  /**
   * Handles node selection events
   * @param event The node selection event
   */
  nodeSelect(event: TreeNodeSelectEvent) {

  }

  /**
   * Handles node unselection events
   * @param node The unselected node
   */
  nodeUnselect(node: any) { }

  /**
   * Reloads all knowledge base nodes from the server and updates the tree
   * while preserving expanded states where possible
   */
  private reloadKnowledgebaseNodes(): void {

    this.loading = true;
    this.childsSearchService.loadKnowledgeBases().subscribe({
      next: (enricheds) => {
        //substtute existing, add non existing
        if (this.roots) {
          const remaining: TreeNode[] = [];
          this.roots.forEach(entry => {
            const found = enricheds?.find(x => x.info.code === entry.data.info.code);
            if (found) {
              entry.label = found.info.description;
              entry.data = found;
              remaining.push(entry);
            }
          });
          if (enricheds) {
            enricheds.forEach(enriched => {
              const found = remaining.find(x => x.data.info.code === enriched.info.code);
              if (!found) {
                remaining.push({
                  label: enriched.info.description,
                  icon: getNodeIcon(enriched),
                  leaf: false,
                  data: enriched
                });
              }
            });
          }

          this.roots = remaining;

        }
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  /**
   * Reloads the children of a knowledge base node from the server
   * @param parent The knowledge base parent node whose children should be reloaded
   */
  private reloadKnowledgeBaseChilds(parent: TreeNode<any> | undefined): void {
    if (parent) {
      const knowledgeBase = parent.data.info;
      this.loading = true;
      this.childsSearchService.loadKnowledgeChilds(knowledgeBase).subscribe({
        next: (value) => {
          const actualChilds = parent.children;
          const foundExisting: TreeNode[] = [];
          if (value) {
            value.forEach(entry => {
              const found = actualChilds?.find(ch => ch.data.info.code === entry.info.code);
              if (found) {
                foundExisting.push(found);
                found.data = entry;
                found.label = entry.info.description;
              } else {
                foundExisting.push({
                  label: entry.info.description,
                  icon: getNodeIcon(entry),
                  leaf: entry.isLeaf,
                  data: entry,
                  parent: parent
                });
              }
            });
          }
          parent.children = foundExisting;
          parent.expanded = true;
        },
        complete: () => {
          this.loading = false;
        }

      });
    }
  }

  /**
   * Reloads the children of a project node from the server
   * @param parent The project parent node whose children should be reloaded
   */
  private reloadProjectChilds(parent: TreeNode<any> | undefined): void {
    if (parent) {
      this.loading = true;
      this.childsSearchService.loadProjectChilds(parent.data.info).subscribe({
        next: (childs) => {
          const remaining: TreeNode[] = [];
          if (childs) {

            const actualChilds = parent.children;
            childs.forEach(entry => {
              const found = actualChilds?.find(x => x.data.info.code === entry.info.code);
              if (found) {
                found.data = entry;
                found.label = entry.info.description;
                remaining.push(found);
              } else {
                remaining.push({
                  label: entry.info.description,
                  icon: getNodeIcon(entry),
                  leaf: entry.isLeaf,
                  data: entry,
                  parent: parent
                });
              }
            });

          }
          parent.children = remaining;
          parent.expanded = true;
        },
        complete: () => {
          this.loading = false;
        }
      });
    }
  }

}