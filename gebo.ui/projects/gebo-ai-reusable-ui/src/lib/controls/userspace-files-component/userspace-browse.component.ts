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
 * This file contains the implementation of the GeboAIUserspaceBrowseComponent, a component that 
 * provides a user interface for browsing and selecting files from the user's workspace.
 * It displays files and folders in a tree structure and allows for navigation and selection.
 */

import { Component, EventEmitter, forwardRef, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { ControlValueAccessor, FormControl, FormGroup, NG_VALUE_ACCESSOR, Validators } from "@angular/forms";
import { UserspaceControllerService, UserspaceFileDto, UserspaceFolderDto, UserspaceKnowledgebaseDto } from "@Gebo.ai/gebo-ai-rest-api";
import { GeboActionType, GeboUIActionRequest, GeboUIActionRoutingService, sliceWizard } from "@Gebo.ai/reusable-ui";
import { TreeNode } from "primeng/api";
import { TreeNodeExpandEvent } from "primeng/tree";
import { USERSPACE_UPLOADS_WIZARD } from "./userspace-wizard";

/**
 * Interface representing an item in the userspace tree structure.
 * It can represent a knowledge base, user knowledge base, folder, or file,
 * with the corresponding data attached.
 */
interface UserspaceTreeItem {
    isKnowledgeBase?: boolean;
    isUserKnowledgeBase?: boolean;
    isUserspaceFolder?: boolean;
    isFile?: boolean;
    data: UserspaceKnowledgebaseDto | UserspaceFolderDto | UserspaceFileDto
}

/**
 * Component for browsing and selecting files from the user's workspace.
 * Implements ControlValueAccessor to integrate with Angular forms.
 * Uses a tree structure to represent the hierarchy of knowledge bases, folders, and files.
 */
@Component({
    selector: "gebo-ai-userspace-browser-compoent",
    templateUrl: "userspace-browse.component.html", providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => GeboAIUserspaceBrowseComponent),
            multi: true
        }
    ],
    standalone: false
})
export class GeboAIUserspaceBrowseComponent implements OnInit, OnChanges, ControlValueAccessor {

    /** Knowledge base codes to filter the displayed items */
    @Input() knowledgeBaseCodes: string[] = [];
    /** Flag to indicate whether to show knowledge bases or not */
    @Input() noKnowledgeBases: boolean = false;
    /** Event emitter when the browser is closed */
    @Output() closeMe: EventEmitter<boolean> = new EventEmitter();
    /** Event emitter when the selection is confirmed */
    @Output() confirmed: EventEmitter<boolean> = new EventEmitter();
    /** Array of currently selected files to display */
    public choosedDisplay: UserspaceFileDto[] = [];
    /** Flag indicating if data is being loaded */
    loading: boolean = false;
    /** Root nodes of the tree structure */
    root: TreeNode<UserspaceTreeItem>[] = [];
    /** Form group for handling the selection */
    formGroup: FormGroup = new FormGroup({
        choosed: new FormControl()
    });

    /**
     * Constructor initializes required services
     * @param userspaceControllerService Service for interacting with userspace API
     * @param geboUIActionRoutingService Service for routing UI actions
     */
    constructor(private userspaceControllerService: UserspaceControllerService, private geboUIActionRoutingService: GeboUIActionRoutingService) {

    }

    /**
     * Creates a folder node for the tree structure
     * @param node The userspace item to create a node for
     * @param parent The parent node in the tree
     * @returns A TreeNode representation of the folder
     */
    private createFolderNode(node: UserspaceTreeItem, parent?: TreeNode<UserspaceTreeItem>): TreeNode<UserspaceTreeItem> {
        let label: string = "";
        if (node.isUserKnowledgeBase === true || node.isKnowledgeBase === true) {
            label = (node.data as UserspaceKnowledgebaseDto).description;
        } else if (node.isUserspaceFolder === true) {
            label = (node.data as UserspaceFolderDto).description;
        }
        const item: TreeNode<UserspaceTreeItem> = {
            data: node,
            icon: "pi pi-folder",
            expandedIcon: "pi pi-opened-folder",
            label: label,
            leaf: false,
            parent: parent,
            children: []
        };
        return item;
    }

    /**
     * Loads the root nodes of the tree structure based on the input parameters
     */
    public loadRoots(): void {
        if (this.knowledgeBaseCodes && this.knowledgeBaseCodes.length) {
            this.loading = true;
            this.userspaceControllerService.listChildPersonalKnowledgebases(this.knowledgeBaseCodes).subscribe({
                next: (value) => {
                    const root: TreeNode<UserspaceTreeItem>[] = [];
                    if (value) {
                        value.forEach(x => {
                            if (!x.parentKnowledgebaseCode) {
                                const node: UserspaceTreeItem = {
                                    data: x,
                                    isKnowledgeBase: true
                                }
                                root.push(this.createFolderNode(node, undefined));
                            }
                        });
                        value.forEach(entry => {
                            if (entry.parentKnowledgebaseCode) {
                                const item = root.find(x => x.data?.data.code === entry.parentKnowledgebaseCode)
                                if (item) {
                                    const node: UserspaceTreeItem = {
                                        data: entry,
                                        isUserKnowledgeBase: true
                                    };
                                    item.expanded = true;
                                    item.children?.push(this.createFolderNode(node, item));
                                }
                            }
                        });
                    }
                    this.root = root;
                },
                complete: () => {
                    this.loading = false;
                }
            })
        } else
            if (this.noKnowledgeBases === true) {
                this.loading = true;
                this.userspaceControllerService.getPersonalKnowledgebases().subscribe({
                    next: (value) => {
                        const root: TreeNode<UserspaceTreeItem>[] = [];
                        if (value) {
                            value.forEach(x => {
                                const node: UserspaceTreeItem = {
                                    data: x,
                                    isUserKnowledgeBase: true
                                };
                                root.push(this.createFolderNode(node, undefined));
                            });
                        }
                        this.root = root;
                    },
                    complete: () => {
                        this.loading = false;
                    }
                });
            } else {
                this.root = [];
            }
    }

    /**
     * Initialize the component, set up form validation and subscribe to value changes
     */
    ngOnInit(): void {
        this.formGroup.controls["choosed"].setValidators(Validators.required);
        this.formGroup.controls["choosed"].valueChanges.subscribe((codes: string[]) => {
            this.showChoosedDisplay(codes, undefined);
        });
    }

    /**
     * Updates the display of selected files based on their codes
     * @param codes Array of file codes to display
     * @param confirmedEntries Optional callback to execute after files are found
     */
    private showChoosedDisplay(codes: string[], confirmedEntries?: (found: UserspaceFileDto[]) => void): void {
        if (codes && codes.length) {
            let found: UserspaceFileDto[] = [];
            const alreadyInList = this.choosedDisplay ? this.choosedDisplay.filter(x => codes.find(y => y === x.code) ? true : false) : [];
            const foundOnTree = this.searchTree(codes, this.root);
            if (alreadyInList && alreadyInList.length) {
                alreadyInList.forEach(x => {
                    found.push(x);
                });
            }
            if (foundOnTree && foundOnTree.length) {
                foundOnTree.forEach(x => {
                    const foundEntry = found.find(y => y.code === x.code);
                    if (!foundEntry) {
                        found.push(x);
                    }
                });
            }
            if (found.length === codes.length) {
                this.choosedDisplay = found;
                if (confirmedEntries) {
                    confirmedEntries(this.choosedDisplay);
                }
            } else {
                const unfound: string[] = codes.filter(x => found.find(y => y.code === x) ? false : true);
                this.loading = true;
                this.userspaceControllerService.findUserspaceFileByCodes(unfound).subscribe({
                    next: (list) => {
                        this.choosedDisplay = [...found, ...list];
                        if (confirmedEntries) {
                            confirmedEntries(this.choosedDisplay);
                        }
                    },
                    complete: () => {
                        this.loading = false;
                    }
                })
            }

        } else {
            this.choosedDisplay = [];
        }
    }

    /**
     * Recursively searches the tree for files with the specified codes
     * @param codes Array of file codes to search for
     * @param root Root nodes to start the search from
     * @returns Array of found UserspaceFileDto objects
     */
    private searchTree(codes: string[], root: TreeNode<UserspaceTreeItem>[]): UserspaceFileDto[] {
        let retvals: UserspaceFileDto[] = [];
        if (root) {
            root.forEach(node => {
                const isFile = node.data?.isFile === true;
                if (isFile !== true && node.children) {
                    const foundChilds = this.searchTree(codes, node.children);
                    foundChilds.forEach(x => {
                        retvals.push(x);
                    });
                } else {
                    if (isFile === true && node.data?.data.code) {
                        const isContained: boolean = codes.find(x => x === node.data?.data.code) ? true : false;
                        if (isContained) {
                            retvals.push(node.data.data);
                        }
                    }
                }
            });
        }
        return retvals;
    }

    /**
     * Refreshes a node by simulating its parent's expand event
     * @param node The node to refresh
     * @param evt The mouse event that triggered the refresh
     */
    refreshThis(node: TreeNode<UserspaceTreeItem>,evt:MouseEvent): void {
        if (node.parent) {
            const fakeEvent: TreeNodeExpandEvent = {
                node: node.parent,
                originalEvent:evt

            };
            this.nodeExpand(fakeEvent);
        }
    }

    /**
     * Responds to changes in component inputs
     * @param changes The changed input properties
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.knowledgeBaseCodes && changes["knowledgeBaseCodes"]) {
            this.loadRoots();
        }
        if (this.noKnowledgeBases === true && changes["noKnowledgeBases"]) {
            this.loadRoots();
        }
    }

    /**
     * Opens a folder in the UI action system
     * @param item The tree node representing the folder to open
     */
    openFolder(item: TreeNode<UserspaceTreeItem>) {
        if (item.data?.data) {
            const folderDto = item.data?.data;
            const action: GeboUIActionRequest = {
                actionType: GeboActionType.OPEN,
                context: {},
                contextType: "UserspaceBrowser",
                target: folderDto,
                targetType: "UserspaceFolderDto",
                targetFormInputs: { wizardStepsConfigurations: sliceWizard(1, USERSPACE_UPLOADS_WIZARD), actualWizardStepConfigrationId: "UserspaceFolder" },
                onActionPerformed: (event) => {
                    this.loadRoots();
                }
            };
            this.geboUIActionRoutingService.routeEvent(action);
        }
    }

    /**
     * Creates a new child folder under the selected knowledge base
     * @param item The tree node representing the parent knowledge base
     */
    newChildFolder(item: TreeNode<UserspaceTreeItem>) {
        const kb: UserspaceKnowledgebaseDto = item.data?.data as UserspaceKnowledgebaseDto;
        if (kb && kb.code) {
            const folderDto: UserspaceFolderDto = {
                description: "New folder",
                parentUserspaceKnowledgebaseCode: kb.code
            };
            const action: GeboUIActionRequest = {
                actionType: GeboActionType.NEW,
                context: {},
                contextType: "UserspaceBrowser",
                target: folderDto,
                targetType: "UserspaceFolderDto",
                targetFormInputs: { wizardStepsConfigurations: sliceWizard(1, USERSPACE_UPLOADS_WIZARD), actualWizardStepConfigrationId: "UserspaceFolder" },
                onActionPerformed: (event) => {
                    this.loadRoots();
                }
            };
            this.geboUIActionRoutingService.routeEvent(action);
        }
    }

    /**
     * Creates a new child knowledge base under the selected knowledge base
     * @param item The tree node representing the parent knowledge base
     */
    newChildKnowledgebase(item: TreeNode<UserspaceTreeItem>) {
        const kb: UserspaceKnowledgebaseDto = item.data?.data as UserspaceKnowledgebaseDto;
        const newKb: UserspaceKnowledgebaseDto = {
            description: "New knowledge base",
            parentKnowledgebaseCode: kb.code
        };
        const action: GeboUIActionRequest = {
            actionType: GeboActionType.NEW,
            context: {},
            contextType: "UserspaceBrowser",
            target: newKb,
            targetType: "UserspaceKnowledgebaseDto",
            targetFormInputs: { wizardStepsConfigurations: USERSPACE_UPLOADS_WIZARD, actualWizardStepConfigrationId: "UserspaceKnowledgebase" },
            onActionPerformed: (event) => {
                this.loadRoots();
            }
        };
        this.geboUIActionRoutingService.routeEvent(action);
    }

    /**
     * Opens the edit interface for a knowledge base
     * @param item The tree node representing the knowledge base to edit
     */
    editChildKnowledgebase(item: TreeNode<UserspaceTreeItem>) {
        const kb: UserspaceKnowledgebaseDto = item.data?.data as UserspaceKnowledgebaseDto;
        const action: GeboUIActionRequest = {
            actionType: GeboActionType.OPEN,
            context: {},
            contextType: "UserspaceBrowser",
            target: kb,
            targetType: "UserspaceKnowledgebaseDto",
            onActionPerformed: (event) => {
                this.loadRoots();
            }
        };
        this.geboUIActionRoutingService.routeEvent(action);
    }

    /**
     * Handles the expansion of a tree node, loading its children as needed
     * @param item The expand event containing the node being expanded
     */
    nodeExpand(item: TreeNodeExpandEvent) {
        const node: TreeNode<UserspaceTreeItem> = item.node;
        if (node.data?.isUserKnowledgeBase === true && node.data.data.code) {
            this.loading = true;
            this.userspaceControllerService.listUserspaceFolders(node.data.data.code).subscribe({
                next: (value) => {
                    if (value) {
                        const childs = value.map(x => {
                            const childnode: UserspaceTreeItem = {
                                data: x,
                                isUserspaceFolder: true
                            };
                            return this.createFolderNode(childnode, node);
                        });
                        node.children = childs;
                    }
                },
                complete: () => {
                    this.loading = false;
                }

            });
        } else if (node.data?.isUserspaceFolder === true && node.data.data.code) {

            const folder: UserspaceFolderDto = node.data.data as UserspaceFolderDto;
            if (folder.uploadCode) {
                this.loading = true;
                this.userspaceControllerService.listUserspaceFiles(folder.uploadCode).subscribe({
                    next: (value) => {
                        if (value) {
                            const childs = value.map(x => {

                                return this.createFileNode(x, node);
                            });
                            node.children = childs;
                        }
                    },
                    complete: () => {
                        this.loading = false;
                    }
                });
            }
        }
    }

    /**
     * Creates a file node for the tree structure
     * @param x The file DTO to create a node for
     * @param parent The parent node in the tree
     * @returns A TreeNode representation of the file
     */
    createFileNode(x: UserspaceFileDto, parent: TreeNode<UserspaceTreeItem>): TreeNode<UserspaceTreeItem> {
        const childnode: UserspaceTreeItem = {
            data: x,
            isFile: true
        };
        const node: TreeNode<UserspaceTreeItem> = {
            data: childnode,
            leaf: true,
            label: x.name,
            parent: parent,
            icon: "pi pi-file"
        };
        return node;
    }

    /**
     * Confirms the current selection and emits the appropriate events
     */
    public confirmEditing(): void {
        let values: string[] | undefined = this.formGroup.controls["choosed"].value;
        if (!values) {
            values = [];
        }
        this.onChange(values);
        this.closeMe.emit(true);
        this.confirmed.emit(true);
    }

    /**
     * Writes a value to the control - part of ControlValueAccessor implementation
     * @param obj The value to write to the control
     */
    writeValue(obj: any): void {
        let codes: string[] = [];
        if (obj) {
            if (Array.isArray(obj)) {
                codes = Array.from(obj);
            } else {
                codes = [obj];
            }
        }
        this.showChoosedDisplay(codes, (foundFiles) => {
            if (foundFiles) {
                this.formGroup.controls["choosed"].setValue(foundFiles.map(x => x.code));
            } else {
                this.formGroup.controls["choosed"].setValue([]);
            }
        });

    }

    /** Function to call when the value changes */
    private onChange: (v: any) => void = (v: any) => { };

    /**
     * Registers a callback function for value changes - part of ControlValueAccessor implementation
     * @param fn The callback function
     */
    registerOnChange(fn: any): void {
        this.onChange = fn;
    }

    /** Function to call when the control is touched */
    private onTouched: (v: any) => void = (v: any) => { };

    /**
     * Registers a callback function for touched events - part of ControlValueAccessor implementation
     * @param fn The callback function
     */
    registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }

    /**
     * Sets the disabled state of the control - part of ControlValueAccessor implementation
     * @param isDisabled Whether the control should be disabled
     */
    setDisabledState?(isDisabled: boolean): void {

    }

    /**
     * Removes a selected file from the current selection
     * @param file The file to remove from selection
     */
    removeChip(file: UserspaceFileDto) {
        const code = file.code;
        if (code) {
            const codes: string[] | undefined = this.formGroup.controls["choosed"].value;
            if (codes) {
                this.formGroup.controls["choosed"].setValue(codes.filter(x => x !== code));
            }
        }
    }
}