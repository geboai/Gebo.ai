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

import { ChangeDetectorRef, Component, EventEmitter, forwardRef, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { ControlValueAccessor, FormControl, FormGroup, NG_VALUE_ACCESSOR } from "@angular/forms";
import { BrowseParam, GSharepointProjectEndpoint, GVirtualFilesystemRoot, VirtualFilesystemNavigationNode, VirtualFilesystemNavigationTreeStatus } from "@Gebo.ai/gebo-ai-rest-api";
import { ToastMessageOptions, TreeNode } from "primeng/api";
import { TreeNodeExpandEvent, TreeNodeSelectEvent, TreeNodeUnSelectEvent } from "primeng/tree";
import { of } from "rxjs";
import { loadRootsObservableCallback, browsePathObservableCallback, VFilesystemDeletableReference, VFilesystemReference, reconstructNavigationObservableCallback } from "./vfilesystem-types";
import { IOperationStatus } from "../base-entity-editing-component/operation-status";
import { GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, fieldHostComponentName } from "../field-host-component-iface/field-host-component-iface";

const iconsMapping:{[key:string]:string}={
    GGitProjectEndpoint:"pi pi-git",
    GGoogleDriveProjectEndpoint:"pi pi-google-drive",
    /*GFilesystemProjectEndpoint:"pi pi-folder",*/
    GJiraProjectEndpoint:"pi pi-jira",
    GConfluenceProjectEndpoint:"pi pi-confluence",
    GSharepointProjectEndpoint:"pi pi-sharepoint",
    GUploadsProjectEndpoint:"pi pi-cloud-upload",
    GUserspaceProjectEndpoint:"pi pi-user",
    project:"pi pi-list-check",
    knowledgebase:"pi pi-sitemap"
};
const stdFolderOpened="pi pi-folder-open";
const stdFolderClosed="pi pi-folder";

/**
 * Icon per file extension, so a listing is readable at a glance instead of
 * showing the same generic sheet for every entry. Extensions not listed here
 * fall back to the generic file icon.
 */
const fileIconsByExtension: { [key: string]: string } = {
    ".pdf": "pi pi-file-pdf",
    ".doc": "pi pi-file-word",
    ".docx": "pi pi-file-word",
    ".odt": "pi pi-file-word",
    ".rtf": "pi pi-file-word",
    ".xls": "pi pi-file-excel",
    ".xlsx": "pi pi-file-excel",
    ".ods": "pi pi-file-excel",
    ".csv": "pi pi-file-excel",
    ".zip": "pi pi-box",
    ".gz": "pi pi-box",
    ".tar": "pi pi-box",
    ".7z": "pi pi-box",
    ".rar": "pi pi-box",
    ".png": "pi pi-image",
    ".jpg": "pi pi-image",
    ".jpeg": "pi pi-image",
    ".gif": "pi pi-image",
    ".svg": "pi pi-image",
    ".bmp": "pi pi-image",
    ".webp": "pi pi-image",
    ".mp3": "pi pi-volume-up",
    ".wav": "pi pi-volume-up",
    ".m4a": "pi pi-volume-up",
    ".mp4": "pi pi-video",
    ".avi": "pi pi-video",
    ".mov": "pi pi-video",
    ".txt": "pi pi-align-left",
    ".md": "pi pi-align-left",
    ".log": "pi pi-align-left",
    ".html": "pi pi-code",
    ".htm": "pi pi-code",
    ".xml": "pi pi-code",
    ".json": "pi pi-code",
    ".yaml": "pi pi-code",
    ".yml": "pi pi-code",
    ".java": "pi pi-code",
    ".ts": "pi pi-code",
    ".js": "pi pi-code",
    ".py": "pi pi-code",
    ".sql": "pi pi-database"
};
const stdFileIcon = "pi pi-file-o";

/**
 * Returns the icon of a non folder entry, chosen on its extension.
 * @param entry - The filesystem reference to get the icon for
 * @returns The icon css classes
 */
function icon_file(entry: VFilesystemReference): string {
    const name: string | undefined = entry.path?.name;
    if (name) {
        const lastDot: number = name.lastIndexOf(".");
        if (lastDot >= 0) {
            const icon: string | undefined = fileIconsByExtension[name.substring(lastDot).toLowerCase()];
            if (icon) {
                return icon;
            }
        }
    }
    return stdFileIcon;
}

/**
 * Orders the children of a node the way a file manager does: folders first,
 * then files, both alphabetically and case insensitively. The browsing services
 * return whatever order the remote system yields (`Files.list` order on the
 * server filesystem), which makes long listings hard to scan.
 * @param nodes - The nodes to sort, sorted in place
 * @returns The same array, sorted
 */
function sortNodes(nodes: TreeNode<EnrichedFilesystemReference>[]): TreeNode<EnrichedFilesystemReference>[] {
    return nodes.sort((a, b) => {
        const aFolder: boolean = isFolderReference(a.data);
        const bFolder: boolean = isFolderReference(b.data);
        if (aFolder !== bFolder) {
            return aFolder ? -1 : 1;
        }
        const aLabel: string = (a.label ? a.label : "").toLowerCase();
        const bLabel: string = (b.label ? b.label : "").toLowerCase();
        return aLabel.localeCompare(bLabel);
    });
}

/**
 * Tells whether a reference points to a folder or to a root, both of which are
 * browsable containers.
 * @param entry - The reference to check
 * @returns true when the entry can contain other entries
 */
function isFolderReference(entry?: VFilesystemReference): boolean {
    if (!entry) return false;
    return (!entry.path) || entry.path.folder === true;
}
function icon_folder_opened(node?:VFilesystemReference):string {
    let out:string|undefined=undefined;
    let iconKey:string|undefined=node?.path?node.path.iconKey:node?.root?.iconKey;
    if (iconKey) {
        out=iconsMapping[iconKey];
    }
    if (!out) {
        out=stdFolderOpened;
    }
    return out;
}
function icon_folder_closed(node?:VFilesystemReference):string {
    let out:string|undefined=undefined;
    let iconKey:string|undefined=node?.path?node.path.iconKey:node?.root?.iconKey;
    if (iconKey) {
        out=iconsMapping[iconKey];
    }
    if (!out) {
        out=stdFolderClosed;
    }
    return out;
}

/**
 * Extended interface for VFilesystemReference that includes UI selection state information
 * and a unique identifier for tree node management.
 */
interface EnrichedFilesystemReference extends VFilesystemReference {
    uniqueCode: string;
    selected: boolean;
    parentSelected: boolean;
    /** True when the user asked to delete this entry on the remote system. */
    markedForDeletion?: boolean;
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
    const uniqueCode: string = uniqueKey(entry);
    const data: EnrichedFilesystemReference = {
        uniqueCode: uniqueCode,
        selected: false,
        parentSelected: false,
        ...entry
    };
    const folder: boolean = isFolderReference(entry);
    const t: TreeNode<EnrichedFilesystemReference> = {

        label: entry.path ? entry.path.name : entry.root.description,
        leaf: folder ? false : true,
        data: data,
        icon: folder ? icon_folder_opened(entry) : icon_file(entry),
        collapsedIcon: folder ? icon_folder_closed(entry) : icon_file(entry),
        parent: parent,
        // The node key and the lookup key of nodesMap must be the same string:
        // keeping two identity schemes (the old key was built from root code and
        // absolute path with different rules than uniqueKey) made PrimeNG and the
        // component disagree on which node a value refers to.
        key: uniqueCode
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
    const data: any = {
        ...entry
    };
    // Every ui only field has to leave the payload: they used to be blanked one by
    // one and parentSelected was forgotten, so it ended up persisted in the
    // entities holding a reference.
    delete data.selected;
    delete data.parentSelected;
    delete data.uniqueCode;
    delete data.markedForDeletion;
    return data as VFilesystemReference;
}

/**
 * Converts an EnrichedFilesystemReference into the payload of an entry the user
 * asked to delete on the remote system.
 * @param entry - The enriched reference to convert
 * @returns A VFilesystemDeletableReference flagged for deletion
 */
function toDeletionData(entry: EnrichedFilesystemReference): VFilesystemDeletableReference {
    const data: VFilesystemDeletableReference = toBackendData(entry);
    data.markedForDeletion = true;
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
    styleUrl: "vfilesystem-selector.component.scss",
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => VFilesystemSelectorComponent),
            multi: true
        },
        {
            provide: GEBO_AI_MODULE, useValue: "VFilesystemSelectorModule", multi: false
        },
        {
            provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("VFilesystemSelectorComponent")
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
     * Enables signing remote entries for deletion.
     *
     * When false (the default) the control behaves and emits exactly as it always
     * did, a {@link VFilesystemReference} or an array of them depending on
     * {@link selectionMode}. When true a trash toggle appears on every deletable
     * entry and the emitted array also carries the marked entries, flagged with
     * `markedForDeletion: true` (see {@link VFilesystemDeletableReference}).
     *
     * The control never deletes anything: it reports the intent, the host applies
     * it against its own backend when its entity is saved.
     */
    @Input() enableDeletion: boolean = false;

    /**
     * Whether folders may be signed for deletion when {@link enableDeletion} is
     * active. Off by default because deleting a folder removes everything under
     * it, which most hosts do not want to expose.
     */
    @Input() canDeleteFolders: boolean = false;

    /**
     * Emits the entries currently signed for deletion whenever the user confirms
     * the editing. Hosts that keep the deletion marks out of their form value can
     * listen here instead of reading the control value.
     */
    @Output() deletionsChange: EventEmitter<VFilesystemReference[]> = new EventEmitter<VFilesystemReference[]>();

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
     * Entries signed for deletion in the main panel, by unique code. This is the
     * committed state, mirrored into the control value.
     */
    public deletionMarks: Map<string, EnrichedFilesystemReference> = new Map();

    /**
     * Entries signed for deletion inside the dialog, discarded when the user
     * cancels the editing and promoted to {@link deletionMarks} when confirmed.
     */
    public editingDeletionMarks: Map<string, EnrichedFilesystemReference> = new Map();

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
     * Determines whether the deletion toggle has to be shown for a tree node.
     * Roots are never deletable: they are the browsing scope, not content.
     * @param item - The tree node to check
     * @returns true when the entry can be signed for deletion
     */
    showDeleteToggle(item: TreeNode<EnrichedFilesystemReference>): boolean {
        if (this.enableDeletion !== true || this.readonly === true) return false;
        if (!item.data?.path) return false;
        return item.data.path.folder === true ? this.canDeleteFolders === true : true;
    }

    /**
     * Tells whether a tree node is currently signed for deletion in the dialog.
     * @param item - The tree node to check
     * @returns true when the entry is signed for deletion
     */
    isMarkedForDeletion(item: TreeNode<EnrichedFilesystemReference>): boolean {
        const code: string | undefined = item.data?.uniqueCode;
        return code ? this.editingDeletionMarks.has(code) : false;
    }

    /**
     * Signs a tree node for deletion, or removes an existing mark.
     * @param item - The tree node to toggle
     */
    toggleDeletionMark(item: TreeNode<EnrichedFilesystemReference>): void {
        if (!this.showDeleteToggle(item)) return;
        const code: string | undefined = item.data?.uniqueCode;
        if (!code || !item.data) return;
        if (this.editingDeletionMarks.has(code)) {
            this.editingDeletionMarks.delete(code);
        } else {
            this.editingDeletionMarks.set(code, item.data);
            // An entry cannot be selected and deleted at the same time: signing it
            // for deletion drops it from the selection.
            this.removeFromEditPanel(item.data);
        }
        this.checkChanges.markForCheck();
    }

    /**
     * Removes a deletion mark from the dialog panel.
     * @param item - The entry to unmark
     */
    removeEditingDeletionMark(item: EnrichedFilesystemReference): void {
        this.editingDeletionMarks.delete(item.uniqueCode);
        this.checkChanges.markForCheck();
    }

    /**
     * Removes a committed deletion mark from the main panel, emitting the updated
     * value.
     * @param item - The entry to unmark
     */
    removeDeletionMark(item: EnrichedFilesystemReference): void {
        if (this.readonly === true) return;
        this.deletionMarks.delete(item.uniqueCode);
        this.emitValue();
    }

    /**
     * The entries signed for deletion inside the dialog, for the chips panel.
     */
    get editingDeletions(): EnrichedFilesystemReference[] {
        return Array.from(this.editingDeletionMarks.values());
    }

    /**
     * The committed entries signed for deletion, for the main panel.
     */
    get deletions(): EnrichedFilesystemReference[] {
        return Array.from(this.deletionMarks.values());
    }

    /**
     * Opens the edit dialog with the current selection
     */
    openEditMode() {
        if (!this.roots || this.roots.length === 0) {
            // ngOnChanges only reloads roots when the loadRootsObservable input
            // reference itself changes; if that happened before this component
            // was first rendered (e.g. an already-selected parent system), the
            // initial load can silently run against a stale/no-op callback.
            // Refreshing on open guarantees the currently bound loader is used.
            this.loadRoots();
        }
        this.editingNodeValues = [...this.internalValue];
        this.editingDeletionMarks = new Map(this.deletionMarks);
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
            this.formGroup.controls["choosed"].setValue(tobeSet);
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
        return sortNodes(nodes);
    }

    /**
     * Checks if nodes are contained in the tree
     * @param nodes - The nodes to check
     * @returns true if all nodes are contained in the tree, false otherwise
     */
    private containedInTree(nodes: EnrichedFilesystemReference[]): boolean {
        if (!nodes || !nodes.length) return true;
        if (!this.roots || !this.roots.length) return false;
        // The result of the lookup used to be computed and then dropped, the method
        // always answering false: every opening of the dialog paid a full navigation
        // reconstruction round trip even when the tree already held the entries.
        return this.containedInTreeNodes(this.roots, nodes).length === 0;
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
        let incoming: EnrichedFilesystemReference[] = [];
        this.deletionMarks = new Map();
        if (obj) {
            if (Array.isArray(obj)) {
                incoming = Array.from(obj);
            } else {
                incoming = [obj];
            }
            incoming.forEach(x => {
                x.uniqueCode = uniqueKey(x);
            });
            // Entries flagged for deletion are restored as marks, not as selection:
            // a value written back by the host (or replayed from a saved form) has
            // to reopen showing the same intents the user expressed.
            const marked: EnrichedFilesystemReference[] = incoming.filter(x => x.markedForDeletion === true);
            marked.forEach(x => this.deletionMarks.set(x.uniqueCode, x));
            let internal: EnrichedFilesystemReference[] = incoming.filter(x => x.markedForDeletion !== true);
            if (this.selectionMode === "single") {
                if (internal.length > 1) {
                    internal = [internal[0]];
                }
            }
            this.internalValue = internal;
        } else {
            this.internalValue = [];
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
                this.roots = sortNodes(roots);
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
        // Children already fetched during this session are kept: re-browsing on
        // every expand made the tree flicker, lost the marks placed on the loaded
        // entries and hit the remote system once per click. reload() is the way to
        // ask for fresh contents.
        if (event.node.children && event.node.children.length) {
            return;
        }
        const rootNode = this.findRoot(event.node);
        const hierarchySelected: boolean = event.node.data?.selected === true || event.node.data.parentSelected === true;
        const browseParam: BrowseParam = {
            root: event.node.data.root,
            path: event.node.data.path
        };
        // Per node spinner instead of the modal padlock over the whole dialog: the
        // rest of the tree stays usable while a folder is being listed.
        event.node.loading = true;
        this.browsePathObservable(browseParam).subscribe({
            next: (paths) => {
                const childs: TreeNode<EnrichedFilesystemReference>[] = [];
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
                event.node.children = sortNodes(childs);
                this.resyncEditingChips();
            },
            complete: () => {
                event.node.loading = false;
                this.checkChanges.markForCheck();
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
     * Toggles node selection programmatically when clicking labels
     * @param node - The node to toggle selection for
     */
    toggleNodeSelection(node: TreeNode<EnrichedFilesystemReference>) {
        if (this.readonly) return;
        const code = node.data?.uniqueCode;
        if (!code) return;

        if (this.selectionMode === 'multiple') {
            if (!this.showCheckbox(node)) return;
            const actualValues: string[] = this.formGroup.controls["choosed"].value || [];
            if (actualValues.includes(code)) {
                this.formGroup.controls["choosed"].setValue(actualValues.filter(x => x !== code));
            } else {
                this.formGroup.controls["choosed"].setValue([...actualValues, code]);
            }
        } else {
            if (!this.showRadio(node)) return;
            const actualValue = this.formGroup.controls["choosed"].value;
            if (actualValue === code) {
                this.formGroup.controls["choosed"].setValue(undefined);
            } else {
                this.formGroup.controls["choosed"].setValue(code);
            }
        }
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
        this.deletionMarks = new Map(this.editingDeletionMarks);
        this.openEditWindow = false;
        this.emitValue();
    }

    /**
     * Builds the outgoing value and notifies the form and the deletions output.
     *
     * With deletion disabled the payload is the historical one, so every existing
     * host keeps receiving what it always received. With deletion enabled the
     * emitted array is the union of the selected entries and of the ones signed
     * for deletion, the latter flagged with `markedForDeletion`.
     */
    private emitValue(): void {
        let out: VFilesystemReference[] | VFilesystemReference | undefined = undefined;
        if (this.enableDeletion === true) {
            const payload: VFilesystemDeletableReference[] = this.internalValue.map(toBackendData);
            this.deletionMarks.forEach(entry => {
                payload.push(toDeletionData(entry));
            });
            out = payload;
            this.deletionsChange.emit(this.deletions.map(toDeletionData));
        } else if (this.selectionMode === "single") {
            out = (this.internalValue.length ? toBackendData(this.internalValue[0]) : undefined);
        } else {
            out = this.internalValue.map(toBackendData);
        }
        this.onChange(out);
    }

    /**
     * Reloads the browsing roots, dropping every cached child listing.
     *
     * Hosts call it after having actually applied the deletions (or added new
     * contents) on their backend, so the tree stops showing entries that no longer
     * exist on the remote system.
     */
    public reload(): void {
        this.nodesMap = new Map();
        // Reloading means "this is what the remote system holds now", so the
        // pending deletion intents no longer have a subject: keeping them would
        // leave the panel showing entries that have just been removed, and would
        // make the host apply them a second time on its next save.
        const hadMarks: boolean = this.deletionMarks.size > 0;
        this.deletionMarks = new Map();
        this.editingDeletionMarks = new Map();
        this.roots = [];
        this.loadRoots();
        if (hadMarks) {
            this.emitValue();
        }
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
        this.emitValue();
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