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
import { cloneTreeNode, EnrichedChild, extractTargetType, fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboActionPerformedEvent, GeboActionType, GeboAIPluggableKnowledgeAdminBaseTreeSearchService, GeboUIActionRequest, GeboUIActionRoutingService, getNodeIcon, getVFSIcon, isProjectEndpoint, refreshTreeBranch, treeNodeTrackBy } from "@Gebo.ai/reusable-ui";
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
    providers: [
      { provide: GEBO_AI_MODULE, useValue: "GeboAiKnowledgeBaseModule", multi: false }, 
      { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAiKnowledgeBaseTreeComponent")  }
    ]
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
   * Node tracking of the tree. The nodes of a branch are rebuilt every time its
   * contents change, so tracking them by key is what keeps the untouched views in
   * place instead of throwing away the whole branch.
   */
  public trackBy = treeNodeTrackBy;

  /**
   * Builds the key of a node, unique among the whole tree, out of the key of its
   * parent and of the code of the entry it shows.
   * @param parent The parent node, undefined for a root
   * @param code The code of the entry
   * @param fallback A value to use when the entry carries no code
   * @returns The key of the node
   */
  private nodeKey(parent: TreeNode | undefined, code: string | undefined, fallback: string | number): string {
    return (parent && parent.key ? parent.key : "") + "/" + (code ? code : "#" + fallback);
  }

  /**
   * Hands the tree the branch of a node whose contents have just changed.
   *
   * PrimeNG renders the tree with OnPush nodes: filling `children` from an http
   * callback is invisible to it until the branch is handed over as new node
   * objects, which is why an expansion used to need a second click to show up and
   * why the toggler kept the icon it had when the request was fired.
   * @param node The node that changed
   */
  private refreshBranch(node: TreeNode): void {
    this.roots = refreshTreeBranch(this.roots, node);
  }

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
      this.data.forEach((entry, index) => {
        roots.push({
          key: this.nodeKey(undefined, entry.info.code, index),
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
    // A child has just been created under this node: its contents are reloaded and
    // the branch is left open, so the new element shows up without the user having
    // to collapse and expand the node again.
    node.expanded = true;
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
    const node: TreeNode = event.node;
    if (node.data.isKnowledgeBase === true) {
      this.startLoading(node);
      this.childsSearchService.loadKnowledgeChilds(
        node.data.info
      ).subscribe({
        next: (projects) => {
          if (projects) {
            node.children = projects.map((entry, index) => this.toChildNode(node, entry, index));
          }
        },
        error: () => {
          this.endLoading(node);
        },
        complete: () => {
          this.endLoading(node);
        }
      });
    } else if (node.data.isProject === true) {
      const project: GProject = node.data.info;

      if (project.rootKnowledgeBaseCode && project.code) {
        this.startLoading(node);
        this.childsSearchService.loadProjectChilds(project).subscribe({
          next: (childs) => {
            if (childs) {
              node.children = childs.map((entry, index) => this.toChildNode(node, entry, index));
            }
          },
          error: () => {
            this.endLoading(node);
          },
          complete: () => {
            this.endLoading(node);
          }
        });
      }
    } else if (isProjectEndpoint(node.data)) {
      //call root childs
      this.startLoading(node);
      this.childsSearchService.loadRootProjectEndpointChilds(node.data).subscribe({
        next: (childs) => {
          if (childs) {
            node.children = childs.map((entry, index) => this.toVFSChildNode(node, entry, index));
          }
        },
        error: () => {
          this.endLoading(node);
        },
        complete: () => {
          this.endLoading(node);
        }
      });

    } else if (node.data.isVirtualFolder) {
      this.startLoading(node);
      this.childsSearchService.loadNestedProjectEndpointChilds(node.data).subscribe({
        next: (childs) => {
          if (childs) {
            node.children = childs.map((entry, index) => this.toVFSChildNode(node, entry, index));
          }
        },
        error: () => {
          this.endLoading(node);
        },
        complete: () => {
          this.endLoading(node);
        }
      });

    }
  }

  /**
   * Marks a node as the one being listed. Only the toggler of the node shows the
   * wait, the rest of the tree staying usable: locking the whole panel on every
   * expansion made the tree swallow the clicks that came next.
   * @param node The node whose children are being loaded
   */
  private startLoading(node: TreeNode): void {
    node.loading = true;
  }

  /**
   * Ends the wait of a node and hands its branch, children included, back to the
   * tree so that it gets painted.
   * @param node The node whose children have been loaded
   */
  private endLoading(node: TreeNode): void {
    node.loading = false;
    this.refreshBranch(node);
  }

  /**
   * Builds the node of a knowledge base child: a project or a project endpoint.
   * @param parent The node the child belongs to
   * @param entry The child to show
   * @param index The position of the child among its siblings
   * @returns The node of the child
   */
  private toChildNode(parent: TreeNode, entry: EnrichedChild, index: number): TreeNode {
    return {
      key: this.nodeKey(parent, entry.info.code, index),
      label: entry.info.description,
      icon: getNodeIcon(entry),
      leaf: entry.isLeaf,
      data: entry,
      parent: parent
    };
  }

  /**
   * Builds the node of a virtual filesystem entry. A folder gets the open and the
   * closed icon instead of a single one, so that its state can be read off the
   * icon the same way as off the toggler.
   * @param parent The node the entry belongs to
   * @param entry The entry to show
   * @param index The position of the entry among its siblings
   * @returns The node of the entry
   */
  private toVFSChildNode(parent: TreeNode, entry: any, index: number): TreeNode {
    if (entry.isVirtualFile === true) {
      const fileType = this.fileTypesLibrary.find(x => x.extensions?.find(w => w === (entry.info as VDocumentInfo).extension));
      entry.programmingLanguage = fileType?.programmingLanguage;
      entry.fileTypeId = fileType?.fileTypeId;
      entry.treatAs = fileType?.treatAs;
      entry.uiViewable = fileType?.uiViewable === true ? true : false;
    }
    const node: TreeNode = {
      key: this.nodeKey(parent, entry.info.code, index),
      label: entry.info.name,
      leaf: entry.isLeaf,
      data: entry,
      parent: parent
    };
    if (entry.isVirtualFolder === true) {
      node.collapsedIcon = "pi pi-folder";
      node.expandedIcon = "pi pi-folder-open";
    } else {
      node.icon = getVFSIcon(entry);
    }
    return node;
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
              // A copy, not the node itself: the tree only repaints a node whose
              // identity changed, so reusing it would keep the old description on
              // screen.
              const updated: TreeNode = cloneTreeNode(entry);
              updated.label = found.info.description;
              updated.data = found;
              updated.icon = getNodeIcon(found);
              remaining.push(updated);
            }
          });
          if (enricheds) {
            enricheds.forEach((enriched, index) => {
              const found = remaining.find(x => x.data.info.code === enriched.info.code);
              if (!found) {
                remaining.push({
                  key: this.nodeKey(undefined, enriched.info.code, index),
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
            value.forEach((entry, index) => {
              const found = actualChilds?.find(ch => ch.data.info.code === entry.info.code);
              if (found) {
                // The already shown children are copied as well: the tree keeps the
                // views whose node did not change, so an updated description would
                // otherwise stay as it was.
                const updated: TreeNode = cloneTreeNode(found);
                updated.data = entry;
                updated.label = entry.info.description;
                updated.icon = getNodeIcon(entry);
                updated.parent = parent;
                foundExisting.push(updated);
              } else {
                foundExisting.push(this.toChildNode(parent, entry, index));
              }
            });
          }
          parent.children = foundExisting;
          parent.expanded = true;
        },
        complete: () => {
          this.loading = false;
          this.refreshBranch(parent);
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
            childs.forEach((entry, index) => {
              const found = actualChilds?.find(x => x.data.info.code === entry.info.code);
              if (found) {
                const updated: TreeNode = cloneTreeNode(found);
                updated.data = entry;
                updated.label = entry.info.description;
                updated.icon = getNodeIcon(entry);
                updated.parent = parent;
                remaining.push(updated);
              } else {
                remaining.push(this.toChildNode(parent, entry, index));
              }
            });

          }
          parent.children = remaining;
          parent.expanded = true;
        },
        complete: () => {
          this.loading = false;
          this.refreshBranch(parent);
        }
      });
    }
  }

}