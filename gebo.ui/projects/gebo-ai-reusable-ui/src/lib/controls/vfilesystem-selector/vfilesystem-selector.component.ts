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
 * This module provides a virtual filesystem selector component for Angular applications.
 * It allows users to browse and select files and folders from a virtual filesystem.
 */

import { ChangeDetectorRef, Component, forwardRef, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { ControlValueAccessor, FormControl, FormGroup, NG_VALUE_ACCESSOR } from "@angular/forms";
import { BrowseParam, GVirtualFilesystemRoot, VirtualFilesystemNavigationNode, VirtualFilesystemNavigationTreeStatus } from "@Gebo.ai/gebo-ai-rest-api";
import { ToastMessageOptions, TreeNode } from "primeng/api";
import { TreeNodeExpandEvent, TreeNodeSelectEvent, TreeNodeUnSelectEvent } from "primeng/tree";
import { of } from "rxjs";
import { loadRootsObservableCallback, browsePathObservableCallback, VFilesystemReference, reconstructNavigationObservableCallback } from "./vfilesystem-types";
import { IOperationStatus } from "../base-entity-editing-component/operation-status";
import { GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, fieldHostComponentName } from "../field-host-component-iface/field-host-component-iface";


/**
 * Extended interface for VFilesystemReference that includes UI selection state information
 * and a unique identifier for tree node management.
 */
interface EnrichedFilesystemReference extends VFilesystemReference {
    uniqueCode: string;
    selected: boolean;
    parentSelected: boolean;
}

/**
 * Utility function to check if a value exists (not null or undefined)
 * @param a - The value to check
 * @returns true if the value exists, false otherwise
 */
function hasValue(a: any): boolean {
    return a ? true : false;
}

/**
 * Converts a VFilesystemReference to a TreeNode with enriched data
 * @param entry - The filesystem reference to convert
 * @param parent - Optional parent node for the tree hierarchy
 * @returns A TreeNode with EnrichedFilesystemReference data
 */
function toEnrichedNode(entry: VFilesystemReference, parent?: TreeNode<EnrichedFilesystemReference>): TreeNode<EnrichedFilesystemReference> {
    let key: string = "";
    if (entry.root.code) {
        key = entry.root.code + "::";
    } else if (entry.root.absolutePath) {
        key = entry.root.absolutePath + "::";
    } else if (entry.root.uri) {
        key = entry.root.uri + "::";
    }
    if (entry.path?.absolutePath) {
        key = key + entry.path.absolutePath;
    }
    const data: EnrichedFilesystemReference = {
        uniqueCode: uniqueKey(entry),
        selected: false,
        parentSelected: false,
        ...entry
    };
    const t: TreeNode<EnrichedFilesystemReference> = {

        label: entry.path ? entry.path.name : entry.root.description,
        leaf: (!entry.path) || (entry.path && entry.path.folder === true) ? false : true,
        data: data,
        icon: (!entry.path) || (entry.path && entry.path.folder === true) ? "pi pi-folder-open" : "pi pi-file-o",
        collapsedIcon: (!entry.path) || (entry.path && entry.path.folder === true) ? "pi pi-folder" : "pi pi-file-o",
        parent: parent,
        key: key
    };
    return t;
}

/**
 * Converts an EnrichedFilesystemReference back to a standard VFilesystemReference
 * by removing UI-specific properties
 * @param entry - The enriched reference to convert
 * @returns A clean VFilesystemReference without UI state properties
 */
function toBackendData(entry: EnrichedFilesystemReference): VFilesystemReference {
    const data: VFilesystemReference = {
        ...entry
    };
    const anyData: any = (data as any);
    anyData.selected = undefined;
    anyData.uniqueCode = undefined;
    return data;
}

/**
 * Generates a unique key for a filesystem reference based on root and path
 * @param entry - The filesystem reference to generate a key for
 * @returns A string that uniquely identifies the reference
 */
function uniqueKey(entry: VFilesystemReference): string {
    const u: string = "ROOT:" + (hasValue(entry.root.absolutePath) ? entry.root.absolutePath : entry.root.code) + (hasValue(entry.path) ? "-PATH:" + entry.path?.absolutePath : "");
    return u;
}

/**
 * Recursively searches for a node in the tree structure
 * @param x - The reference to find
 * @param roots - The tree nodes to search within
 * @returns true if the node is found, false otherwise
 */
function findNode(x: EnrichedFilesystemReference, roots: TreeNode<EnrichedFilesystemReference>[]): boolean {
    let f: boolean = false;
    roots.forEach(y => {
        const rootMatches: boolean = hasValue(y.data?.root) && hasValue(x.root) && ((hasValue(y.data?.root.absolutePath) && hasValue(x.root.absolutePath) && y.data?.root.absolutePath === x.root.absolutePath) || (hasValue(y.data?.root.code) && hasValue(x.root.code) && y.data?.root.code === x.root.code));
        const pathMatches: boolean = (!hasValue(y.data?.path) && !hasValue(x.path)) || (hasValue(y.data?.path) && hasValue(x.path) && hasValue(y.data?.path?.absolutePath) && hasValue(x.path?.absolutePath) && y.data?.path?.absolutePath === x.path?.absolutePath);
        if (rootMatches && pathMatches) {
            f = true;
        }
        if (!f && y.children && findNode(x, y.children) === true) {
            f = true;
        }

    });
    return f;
}

/**
 * Component that provides a tree-based interface for selecting files and folders from a virtual filesystem.
 * Implements ControlValueAccessor for integration with Angular forms.
 */
@Component({
    selector: "gebo-ai-vfilesystem-selector-component",
    templateUrl: "vfilesystem-selector.component.html",
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => VFilesystemSelectorComponent),
            multi: true
        },
        {
            provide: GEBO_AI_MODULE, useValue: "VFilesystemSelectorModule", multi: true
        },
        {
            provide: GEBO_AI_FIELD_HOST, multi: true, useValue: fieldHostComponentName("VFilesystemSelectorComponent")
        }
    ],
    standalone: false
})
export class VFilesystemSelectorComponent implements OnInit, OnChanges, ControlValueAccessor {

    /**
     * Flag indicating whether the component is currently loading data
     */
    public loading: boolean = false;

    /**
     * Whether the component should be in read-only mode
     */
    @Input() readonly = false;

    /**
     * Whether folders can be selected
     */
    @Input() canChooseFolders: boolean = true;

    /**
     * Whether files can be selected
     */
    @Input() canChooseFiles: boolean = true;

    /**
     * Observable callback for loading filesystem roots
     */
    @Input() loadRootsObservable: loadRootsObservableCallback = () => of({});

    /**
     * Observable callback for browsing a path within the filesystem
     */
    @Input() browsePathObservable: browsePathObservableCallback = (param: BrowseParam) => of({});

    /**
     * Optional callback for reconstructing navigation paths
     */
    @Input() reconstructNavigationCallback?: reconstructNavigationObservableCallback;

    /**
     * Whether to allow single or multiple selection
     */
    @Input() selectionMode: "single" | "multiple" = "single";

    /**
     * Placeholder text for the selection input
     */
    @Input() placeholder: string = "Select proper path";

    /**
     * The root nodes of the filesystem tree
     */
    public roots: TreeNode<EnrichedFilesystemReference>[] = [];

    /**
     * Currently selected filesystem references
     */
    public internalValue: EnrichedFilesystemReference[] = [];

    /**
     * Whether the edit dialog is open
     */
    public openEditWindow: boolean = false;

    /**
     * References currently being edited in the dialog
     */
    public editingNodeValues: EnrichedFilesystemReference[] = [];

    /**
     * Toast messages to display to the user
     */
    public messages: ToastMessageOptions[] = [];

    /**
     * Flag to indicate whether tree consistency needs to be checked
     */
    private checkTreeConsistency: boolean = false;

    /**
     * Map of node unique codes to their tree nodes for quick lookup
     */
    private nodesMap: Map<string, TreeNode<EnrichedFilesystemReference>> = new Map();

    /**
     * Form group for the selection control
     */
    public formGroup: FormGroup = new FormGroup({
        choosed: new FormControl()
    });

    /**
     * Creates an instance of the VFilesystemSelectorComponent
     * @param checkChanges - Angular's change detector reference for manual change detection
     */
    constructor(private checkChanges: ChangeDetectorRef) {
        this.formGroup.valueChanges.subscribe({
            next: (v) => {
                this.resyncEditingChips();
            }
        });
    }

    /**
     * Adds a node to the lookup map
     * @param node - The node to add to the map
     */
    private addMap(node: TreeNode<EnrichedFilesystemReference>) {
        if (node.data?.uniqueCode) {
            this.nodesMap.set(node.data?.uniqueCode, node);
        }
    }

    /**
     * Returns CSS styles for a tree node based on its selection state
     * @param item - The tree node to style
     * @returns CSS style string
     */
    onlineStyle(item: TreeNode<EnrichedFilesystemReference>): string {
        let style: string = "";
        if (item.data?.selected === true) {
            //style+="color: red;";
        }
        if (item.data?.parentSelected === true) {
            //style+="color: orange;";
        }
        return style;
    }

    /**
     * Determines whether to show a checkbox for a tree node
     * @param item - The tree node to check
     * @returns true if a checkbox should be shown, false otherwise
     */
    showCheckbox(item: TreeNode<EnrichedFilesystemReference>): boolean {
        let p: boolean = false;
        if (item.data?.parentSelected === true) return false;
        if (this.selectionMode === 'multiple') {
            const isFolder: boolean = item.data?.path?.folder === true || (!item.data?.path);
            if (isFolder === true && this.canChooseFolders === true) {
                p = true;
            }
            if (isFolder === false && this.canChooseFiles === true) {
                p = true;
            }
        }
        return p;
    }

    /**
     * Determines whether to show a radio button for a tree node
     * @param item - The tree node to check
     * @returns true if a radio button should be shown, false otherwise
     */
    showRadio(item: TreeNode<EnrichedFilesystemReference>): boolean {
        let p: boolean = false;
        if (this.selectionMode === 'single') {
            const isFolder: boolean = item.data?.path?.folder === true || (!item.data?.path);
            if (isFolder === true && this.canChooseFolders === true) {
                p = true;
            }
            if (isFolder === false && this.canChooseFiles === true) {
                p = true;
            }
        }
        return p;
    }

    /**
     * Opens the edit dialog with the current selection
     */
    openEditMode() {
        this.editingNodeValues = [...this.internalValue];
        let boundToChoosedControl: string[] | string | undefined;
        boundToChoosedControl = undefined;
        if (this.selectionMode === 'multiple') {
            boundToChoosedControl = this.editingNodeValues.map(x => x.uniqueCode) as string[];
        } else {
            if (this.editingNodeValues && this.editingNodeValues.length === 1) {
                boundToChoosedControl = this.editingNodeValues[0].uniqueCode;
            }
        }
        this.formGroup.patchValue({
            choosed: boundToChoosedControl
        });
        if (this.checkTreeConsistency) {
            if (!this.containedInTree(this.editingNodeValues)) {
                this.loadNavigationStatusTree(this.editingNodeValues);
            }
            this.resyncEditingChips();
            this.checkTreeConsistency = false;
        }
        this.openEditWindow = true;
        try {
            this.checkChanges.detectChanges();
        } catch (e) { }
    }

    /**
     * Synchronizes the editing chips with the current form control value
     */
    private resyncEditingChips(): void {
        const actualValue: string | string[] | undefined = this.formGroup.controls["choosed"].value;
        console.log("Ctrl value=>" + JSON.stringify(actualValue));
        if (!actualValue) {
            this.editingNodeValues = [];

        } else {
            if (this.selectionMode === "single") {
                let lookupEntry: EnrichedFilesystemReference | undefined = this.nodesMap.get(actualValue as string)?.data;
                if (!lookupEntry) {
                    lookupEntry = this.editingNodeValues.find(x => x.uniqueCode === actualValue);
                }
                if (lookupEntry) {
                    this.editingNodeValues = [lookupEntry];
                }
            } else {
                let stringArray: string[] = [];
                if (Array.isArray(actualValue)) {
                    stringArray = Array.from(actualValue);
                } else {
                    stringArray = [actualValue];
                }

                const nodeValues: EnrichedFilesystemReference[] = [];
                stringArray.forEach(entry => {
                    let lookupEntry: EnrichedFilesystemReference | undefined = this.nodesMap.get(entry)?.data;
                    if (!lookupEntry) {
                        lookupEntry = this.editingNodeValues.find(x => x.uniqueCode === entry);
                    }
                    if (lookupEntry) {

                        nodeValues.push(lookupEntry);
                    }
                });
                this.editingNodeValues = nodeValues;
            }
        }
        //cleaning child nodes and mark as selected from choosed nodes to all childs
        const vectorValue: string[] = (!actualValue ? [] : (Array.isArray(actualValue) ? Array.from(actualValue) : [actualValue]));
        const contained = vectorValue.filter(x => this.nodesMap.has(x));
        //if all nodes are in the map, indicating that all nodes are built and loaded than run the selection
        //cleaning algorithm
        if (contained && vectorValue && contained.length === vectorValue.length) {
            this.nodesMap.forEach(n => {
                if (n.data) {
                    n.data.selected = false;
                    n.data.parentSelected = false;
                }
            });
            vectorValue.forEach(x => {
                const node = this.nodesMap.get(x);
                if (node?.data) {
                    node.data.selected = true;
                    console.log("Signing as selected:" + node?.data?.uniqueCode);
                } else {
                    console.error("NOT FOUND:" + node?.data?.uniqueCode);
                }
            });
            //now from roots do a visit and put parentSelected on all nodes in the descending hierarchy of a selected Node 
            //those who are parentSelected in the visit and also signed as selected have to be removed from the choosed component
            const removeFromControlUniqueKeys: string[] = this.mantainSelected(this.roots);
            if (removeFromControlUniqueKeys && removeFromControlUniqueKeys.length) {
                const newChoosed: string[] = [];
                vectorValue.forEach(x => {
                    const present: boolean = removeFromControlUniqueKeys.find(y => y === x) ? true : false;
                    if (present === false) {
                        newChoosed.push(x);
                    }
                });
                const tobeSet = this.selectionMode === "multiple" ? newChoosed : (newChoosed.length ? newChoosed[0] : undefined);
                console.log("Ctrl new value=>" + JSON.stringify(tobeSet));
                this.formGroup.controls["choosed"].setValue(tobeSet);
            }
        }
        this.checkChanges.detectChanges();
        this.checkChanges.markForCheck();
    }

    /**
     * Maintains the selection state of all root nodes
     * @param roots - The root nodes to process
     * @returns An array of node IDs that should be removed from selection
     */
    private mantainSelected(roots: TreeNode<EnrichedFilesystemReference>[]): string[] {
        let toRemove: string[] = [];
        if (roots) {
            roots.forEach(root => {
                const thisRootRemovable: string[] = this.mantainTree(root, false);
                thisRootRemovable.forEach(x => {
                    toRemove.push(x);
                });
            })
        }
        return toRemove;
    }

    /**
     * Maintains the selection state of a tree node and its children
     * @param root - The root node to process
     * @param selectionHierarchy - Whether the node is in a selection hierarchy
     * @returns An array of node IDs that should be removed from selection
     */
    private mantainTree(root: TreeNode<EnrichedFilesystemReference>, selectionHierarchy: boolean): string[] {
        //console.log("Invoking maintainTree:"+root.data?.uniqueCode+" selectionHierarchy:"+selectionHierarchy);
        let childsSelectionHierarchy: boolean = selectionHierarchy;
        let removable: string[] = [];
        if (selectionHierarchy === true) {
            if (root.data?.selected === true) {
                //signed as selected but in selectionHierarchy than have to be 
                //removed
                //console.log("Found node:" + root.data.uniqueCode + " to be removed from selection list");
                removable.push(root.data.uniqueCode);
                root.data.selected = false;
                root.data.parentSelected = true;
                childsSelectionHierarchy = true;
            }
        } else {
            if (root.data?.selected === true) {
                root.data.parentSelected = false;
                childsSelectionHierarchy = true;
            }
        }
        if (root.data) {
            root.data.parentSelected = selectionHierarchy;
        }
        //console.log("Node:" + root.data?.uniqueCode + " selected:" + root.data?.selected + " parentSelected:" + root.data?.parentSelected);
        if (root.children && root.children.length) {
            root.children.forEach(child => {
                const childRemovables = this.mantainTree(child, childsSelectionHierarchy);
                childRemovables.forEach(ch => {
                    removable.push(ch);
                });
            });
        }
        return removable;
    }

    /**
     * Filters out success messages from the message array
     * @param m - The array of messages to filter
     * @returns An array of non-success messages
     */
    private removeSuccess(m: ToastMessageOptions[]): ToastMessageOptions[] {
        if (!m) return [];
        return m.filter(m => m.severity && m.severity.toLowerCase() !== 'success');
    }

    /**
     * Loads the navigation tree for a set of nodes
     * @param nodes - The nodes to load the navigation tree for
     */
    private loadNavigationStatusTree(nodes: EnrichedFilesystemReference[]) {
        if (this.reconstructNavigationCallback && nodes && nodes.length) {
            const refs: VFilesystemReference[] = nodes;
            this.loading = true;
            this.nodesMap = new Map();
            this.reconstructNavigationCallback(refs).subscribe({
                next: (treeValues: IOperationStatus<VirtualFilesystemNavigationTreeStatus[]>) => {
                    if (treeValues.result && treeValues.result.length) {
                        this.reassignRoots(treeValues.result);
                    }
                    this.resyncEditingChips();
                    this.messages = this.removeSuccess(treeValues.messages as ToastMessageOptions[]);
                },
                complete: () => {
                    this.loading = false;
                }
            });
        }
    }

    /**
     * Reassigns the root nodes based on a navigation tree status
     * @param result - The navigation tree status to use
     */
    private reassignRoots(result: VirtualFilesystemNavigationTreeStatus[]) {
        const newroots: TreeNode<EnrichedFilesystemReference>[] = [];
        result.forEach(x => {
            if (x.root) {
                const thisRoot: EnrichedFilesystemReference = {
                    root: x.root,
                    path: undefined,
                    selected: false,
                    parentSelected: false,
                    uniqueCode: uniqueKey({
                        root: x.root,
                        path: undefined
                    })
                }
                const thisNode: TreeNode<EnrichedFilesystemReference> = toEnrichedNode(thisRoot);
                this.addMap(thisNode);
                if (x.childs) {
                    thisNode.children = this.transformChilds(x.childs, thisNode);
                }
                newroots.push(thisNode);
            }
        });
        this.roots = newroots;
        this.resyncEditingChips();
    }

    /**
     * Transforms child nodes into tree nodes
     * @param childs - The child nodes to transform
     * @param parentNode - The parent node
     * @returns An array of tree nodes
     */
    private transformChilds(childs: VirtualFilesystemNavigationNode[], parentNode: TreeNode<EnrichedFilesystemReference>): TreeNode<EnrichedFilesystemReference>[] {
        const nodes: TreeNode<EnrichedFilesystemReference>[] = [];
        if (childs && childs.length) {
            childs.forEach(x => {
                const thisNode: EnrichedFilesystemReference = { ...x.value, selected: false, uniqueCode: uniqueKey(x.value), parentSelected: false, };
                const thisTreeNode: TreeNode<EnrichedFilesystemReference> = toEnrichedNode(thisNode, parentNode);
                this.addMap(thisTreeNode);
                if ((x as any).childs) {
                    thisTreeNode.children = this.transformChilds((x as any).childs, thisTreeNode);
                }
                nodes.push(thisTreeNode);
            });
        }
        this.resyncEditingChips();
        return nodes;
    }

    /**
     * Checks if nodes are contained in the tree
     * @param nodes - The nodes to check
     * @returns true if all nodes are contained in the tree, false otherwise
     */
    private containedInTree(nodes: EnrichedFilesystemReference[]): boolean {
        let contained: boolean = false;
        let matchingNodes: EnrichedFilesystemReference[] = nodes;
        if (this.roots && this.roots.length) {
            matchingNodes = this.containedInTreeNodes(this.roots, matchingNodes);
        }
        return contained;
    }

    /**
     * Finds nodes that are not contained in the tree
     * @param roots - The root nodes to search in
     * @param matchingNodes - The nodes to check
     * @returns An array of nodes that are not in the tree
     */
    private containedInTreeNodes(roots: TreeNode<EnrichedFilesystemReference>[], matchingNodes: EnrichedFilesystemReference[]): EnrichedFilesystemReference[] {
        if (roots && roots.length && matchingNodes && matchingNodes.length) {
            const nonMatching: EnrichedFilesystemReference[] = matchingNodes.filter(x => { return !findNode(x, roots) });
            return nonMatching;
        }
        return [];
    }

    /**
     * Angular lifecycle hook that initializes the component
     */
    ngOnInit(): void {
    }

    /**
     * Angular lifecycle hook that responds to input changes
     * @param changes - The changes that occurred
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["loadRootsObservable"]) {
            this.loadRoots();
        }
    }

    /**
     * ControlValueAccessor method that writes a value to the component
     * @param obj - The value to write
     */
    writeValue(obj: any): void {
        let snode: TreeNode<EnrichedFilesystemReference>[] = [];
        let internal: EnrichedFilesystemReference[] = [];
        if (obj) {
            if (Array.isArray(obj)) {
                internal = Array.from(obj);
            } else {
                internal = [obj];
            }
            if (internal && internal.length) {
                internal.forEach(x => {
                    x.uniqueCode = uniqueKey(x);
                });
            }
            if (this.selectionMode === "single") {
                if (internal.length > 1) {
                    internal = [internal[0]];
                }
            }
            this.internalValue = internal;
            snode = this.internalValue.map(x => toEnrichedNode(x));
        }
        this.checkTreeConsistency = true;
    }

    /**
     * Change handler function for ControlValueAccessor
     */
    onChange: (p: any) => void = (p: any) => { };

    /**
     * ControlValueAccessor method to register a change handler
     * @param fn - The function to call when the value changes
     */
    registerOnChange(fn: any): void {
        this.onChange = fn;
    }

    /**
     * Touch handler function for ControlValueAccessor
     */
    onTouched: (p: any) => void = (p: any) => { };

    /**
     * ControlValueAccessor method to register a touch handler
     * @param fn - The function to call when the control is touched
     */
    registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }

    /**
     * ControlValueAccessor method to set the disabled state
     * @param isDisabled - Whether the control should be disabled
     */
    setDisabledState?(isDisabled: boolean): void {
    }

    /**
     * Loads the root nodes of the filesystem
     */
    loadRoots() {
        this.loading = true;
        this.loadRootsObservable().subscribe({
            next: (values) => {
                const roots: TreeNode<EnrichedFilesystemReference>[] = [];
                this.messages = this.removeSuccess(values?.messages as ToastMessageOptions[]);
                if (values?.result) {
                    values?.result.forEach(entry => {
                        const ref: EnrichedFilesystemReference = {
                            root: entry,
                            path: undefined,
                            parentSelected: false,
                            selected: false,
                            uniqueCode: uniqueKey({ root: entry })
                        }
                        const thisTreeNode = toEnrichedNode(ref);
                        roots.push(thisTreeNode);
                        this.addMap(thisTreeNode);
                    });
                    /*if (this.internalValue) {
                        this.internalValue = this.internalValue.filter(x => values?.result?.find(y => (y.code === x.root.code) || (y.absolutePath === x.root.absolutePath)));
                    }*/
                }
                this.roots = roots;
                this.resyncEditingChips();
            }, complete: () => {
                this.loading = false;
            }
        });
    }

    /**
     * Finds the root node of a tree node
     * @param item - The tree node to find the root of
     * @returns The root of the tree node
     */
    private findRoot(item: TreeNode): GVirtualFilesystemRoot {
        let root: GVirtualFilesystemRoot;
        if (!item.parent) {
            root = item.data.root;
        } else {
            root = this.findRoot(item.parent);
        }
        return root;
    }

    /**
     * Handles a node expand event
     * @param event - The expand event
     */
    nodeExpand(event: TreeNodeExpandEvent) {
        const rootNode = this.findRoot(event.node);
        const hierarchySelected: boolean = event.node.data?.selected === true || event.node.data.parentSelected === true;
        const browseParam: BrowseParam = {
            root: event.node.data.root,
            path: event.node.data.path
        };
        this.loading = true;
        this.browsePathObservable(browseParam).subscribe({
            next: (paths) => {
                const childs: TreeNode<VFilesystemReference>[] = [];
                this.messages = this.removeSuccess(paths?.messages as ToastMessageOptions[]);
                if (paths?.result) {
                    paths?.result?.forEach(entry => {
                        const ref: VFilesystemReference = {
                            root: rootNode,
                            path: entry
                        }
                        const thisTreeNode = toEnrichedNode(ref, event.node);
                        if (thisTreeNode.data) {
                            thisTreeNode.data.parentSelected = hierarchySelected;
                        }
                        childs.push(thisTreeNode);
                        this.addMap(thisTreeNode);
                    });
                }
                event.node.children = childs;
                this.resyncEditingChips();
            },
            complete: () => {
                this.loading = false;
            }
        });
    }

    /**
     * Handles a node select event
     * @param event - The select event
     */
    nodeSelect(event: TreeNodeSelectEvent) {
    }

    /**
     * Confirms the editing and updates the component value
     */
    confirmEditing(): void {
        const newInternalvalue: EnrichedFilesystemReference[] = [];
        if (this.editingNodeValues && this.editingNodeValues.length) {
            this.editingNodeValues.forEach(x => {
                if (x)
                    newInternalvalue.push(x);
            });
        }
        this.internalValue = newInternalvalue;
        this.openEditWindow = false;
        let out: VFilesystemReference[] | VFilesystemReference | undefined = undefined;
        if (this.selectionMode === "single") {
            out = (this.internalValue.length ? toBackendData(this.internalValue[0]) : undefined);
        } else {
            out = this.internalValue.map(toBackendData);
        }
        this.onChange(out);
    }

    /**
     * Handles a node unselect event
     * @param event - The unselect event
     */
    nodeUnselect(event: TreeNodeUnSelectEvent) {
    }

    /**
     * Handles a node collapse event
     * @param node - The node that was collapsed
     */
    nodeCollapse(node: any) { }

    /**
     * Removes an item from the main selection panel
     * @param item - The item to remove
     */
    removeFromMainPanel(item: EnrichedFilesystemReference): void {
        this.internalValue = this.internalValue.filter(x => x.uniqueCode !== item.uniqueCode);
        let out: VFilesystemReference[] | VFilesystemReference | undefined = undefined;
        if (this.selectionMode === "single") {
            out = (this.internalValue.length ? toBackendData(this.internalValue[0]) : undefined);
        } else {
            out = this.internalValue.map(toBackendData);
        }
        this.onChange(out);
    }

    /**
     * Removes an item from the edit panel
     * @param item - The item to remove
     */
    removeFromEditPanel(item: EnrichedFilesystemReference): void {
        const actualValues: string[] | string | undefined | null = this.formGroup.controls["choosed"].value;
        if (actualValues) {
            const asArray: string[] = Array.isArray(actualValues) ? Array.from(actualValues) : [actualValues];
            const newArray: string[] = asArray.filter(x => x !== item.uniqueCode);
            if (this.selectionMode === "multiple") {
                this.formGroup.controls["choosed"].setValue(newArray);
            } else {
                this.formGroup.controls["choosed"].setValue(newArray && newArray.length ? newArray[0] : undefined);
            }
        }
    }
}