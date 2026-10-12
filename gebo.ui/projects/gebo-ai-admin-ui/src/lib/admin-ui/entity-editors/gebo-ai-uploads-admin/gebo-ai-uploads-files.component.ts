/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { AfterViewInit, Component, ElementRef, EventEmitter, Inject, Input, NgZone, OnChanges, OnDestroy, Optional, Output, SimpleChanges, ViewChild } from "@angular/core";
import { HttpClient, HttpEventType } from "@angular/common/http";
import { BASE_PATH, FileUploadsControllerService, UploadedFileNode } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboAIRootNotificationService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, ConfirmEventType, ToastMessageOptions, TreeNode } from "primeng/api";
import { FileUpload, FileUploadHandlerEvent } from "primeng/fileupload";
import { lastValueFrom } from "rxjs";

/** A file to upload, with the path it keeps under the target folder. */
interface PendingUploadFile {
    file: File;
    relativePath: string;
}

/**
 * What the server accepts in one request, batches stay below it: 100MB
 * (spring.servlet.multipart), and 50 parts (Tomcat's max-part-count default),
 * each file taking two (the file and its path) beside the target folder's one.
 */
const MAX_BATCH_BYTES: number = 80 * 1024 * 1024;
const MAX_BATCH_FILES: number = 20;
const MAX_FILE_BYTES: number = 100 * 1024 * 1024;

/**
 * The "Uploaded files" tab of an uploads data source: a drop zone, in the upper
 * area, to add files and whole folders into a folder of the data source; in the
 * lower area the tree of the files the data source holds, each told published
 * when it is already part of the knowledge base, to view, delete, or upload
 * into. What the next publish ingests is exactly what this tree shows: the
 * folder of the data source.
 */
@Component({
    selector: "gebo-ai-uploads-files-component",
    templateUrl: "gebo-ai-uploads-files.component.html",
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIUploadsModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIUploadsFilesComponent") }
    ],
    standalone: false
})
export class GeboAIUploadsFilesComponent implements OnChanges, AfterViewInit, OnDestroy {
    /** Code of the data source; undefined until it is saved, when nothing can be added. */
    @Input() endpointCode?: string;
    /** The extensions (with the dot) the data source ingests, zip included. */
    @Input() acceptedExtensions: string[] = [];
    /** True while the data source is being processed: nothing can change meanwhile. */
    @Input() disabled: boolean = false;
    /** A file of the data source the user wants to open, by its path relative to the data source. */
    @Output() viewFile: EventEmitter<UploadedFileNode> = new EventEmitter<UploadedFileNode>();
    /** The files of the data source changed. */
    @Output() filesChanged: EventEmitter<UploadedFileNode> = new EventEmitter<UploadedFileNode>();

    @ViewChild("fileUpload") fileUpload?: FileUpload;
    @ViewChild("dropArea") dropArea?: ElementRef<HTMLElement>;
    @ViewChild("folderInput") folderInput?: ElementRef<HTMLInputElement>;

    /** The tree of the data source, the root being the data source folder. */
    protected root?: UploadedFileNode;
    protected treeNodes: TreeNode<UploadedFileNode>[] = [];
    protected loadingTree: boolean = false;
    /** The folder receiving the uploads, relative to the data source; "" for its root. */
    protected targetFolder: string = "";
    protected uploading: boolean = false;
    protected uploadProgress: number = 0;
    protected uploadLabel: string = "";
    protected messages: ToastMessageOptions[] = [];
    /** The new folder dialog. */
    protected newFolderVisible: boolean = false;
    protected newFolderParent: string = "";
    protected newFolderName: string = "";
    private expandedPaths: Set<string> = new Set<string>();
    private readonly folderDropListener = (event: DragEvent) => this.onDropCapture(event);

    constructor(private uploadsService: FileUploadsControllerService,
        private http: HttpClient,
        private confirmationService: ConfirmationService,
        private notifications: GeboAIRootNotificationService,
        private zone: NgZone,
        @Optional() @Inject(BASE_PATH) private basePath: string) {
        this.basePath = basePath ? basePath : "";
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes["endpointCode"]) {
            this.targetFolder = "";
            this.expandedPaths.clear();
            this.reload();
        }
    }

    ngAfterViewInit(): void {
        // A dropped folder has to be read before PrimeNG takes the drop, which only
        // sees files: the drop is caught on its way down, in the capture phase.
        this.dropArea?.nativeElement.addEventListener("drop", this.folderDropListener, true);
    }

    ngOnDestroy(): void {
        this.dropArea?.nativeElement.removeEventListener("drop", this.folderDropListener, true);
    }

    get canAddFiles(): boolean {
        return !!this.endpointCode && !this.disabled && !this.uploading;
    }

    get acceptList(): string {
        return this.acceptedExtensions.join(",");
    }

    get targetFolderLabel(): string {
        return this.targetFolder ? this.targetFolder : "/";
    }

    /** Reloads the tree of the data source. */
    reload(): void {
        if (!this.endpointCode) {
            this.root = undefined;
            this.treeNodes = [];
            return;
        }
        this.loadingTree = true;
        this.uploadsService.listUploadedFilesTree(this.endpointCode).subscribe({
            next: (root) => {
                this.root = root;
                this.treeNodes = (root?.children || []).map(x => this.toTreeNode(x));
                if (this.targetFolder && !this.findNode(root, this.targetFolder)) {
                    this.targetFolder = "";
                }
                this.filesChanged.emit(root);
            },
            error: () => {
                this.messages = [{ id: "LIST_FILES_FAILED", severity: "error", summary: "Cannot read the files", detail: "The files of this data source cannot be listed" }];
                this.loadingTree = false;
            },
            complete: () => this.loadingTree = false
        });
    }

    private toTreeNode(node: UploadedFileNode): TreeNode<UploadedFileNode> {
        return {
            data: node,
            key: node.relativePath,
            leaf: !node.folder,
            expanded: node.folder && this.expandedPaths.has(node.relativePath || ""),
            children: node.folder ? (node.children || []).map(x => this.toTreeNode(x)) : undefined
        };
    }

    private findNode(node: UploadedFileNode | undefined, relativePath: string): UploadedFileNode | undefined {
        if (!node) return undefined;
        if (node.relativePath === relativePath) return node;
        for (const child of node.children || []) {
            const found = this.findNode(child, relativePath);
            if (found) return found;
        }
        return undefined;
    }

    protected onNodeExpand(event: { node: TreeNode<UploadedFileNode> }): void {
        if (event.node?.data?.relativePath) this.expandedPaths.add(event.node.data.relativePath);
    }

    protected onNodeCollapse(event: { node: TreeNode<UploadedFileNode> }): void {
        if (event.node?.data?.relativePath) this.expandedPaths.delete(event.node.data.relativePath);
    }

    /** Makes a folder of the data source the target of the next uploads. */
    protected setTarget(folder: UploadedFileNode | undefined): void {
        this.targetFolder = folder?.relativePath || "";
        if (folder?.relativePath) this.expandedPaths.add(folder.relativePath);
    }

    // ------------------------------------------------------------- uploading

    /** Files chosen or dropped through PrimeNG: kept as they are, under the target folder. */
    protected onUploadHandler(event: FileUploadHandlerEvent): void {
        const files: PendingUploadFile[] = (event.files || []).map(file => ({ file, relativePath: file.name }));
        this.fileUpload?.clear();
        this.upload(files);
    }

    /** Opens the browser's folder picker. */
    protected browseFolder(): void {
        if (this.canAddFiles) this.folderInput?.nativeElement.click();
    }

    /** A folder picked: its files keep the folders they are in, the picked one included. */
    protected onFolderPicked(event: Event): void {
        const input = event.target as HTMLInputElement;
        const files: PendingUploadFile[] = Array.from(input.files || [])
            .map(file => ({ file, relativePath: (file as any).webkitRelativePath || file.name }));
        input.value = "";
        this.upload(files);
    }

    /** A drop holding folders: read here, whole, instead of by PrimeNG. */
    private onDropCapture(event: DragEvent): void {
        const items = event.dataTransfer?.items;
        if (!items || !this.canAddFiles) return;
        const entries: FileSystemEntry[] = [];
        for (let i = 0; i < items.length; i++) {
            const entry = items[i].kind === "file" ? items[i].webkitGetAsEntry() : null;
            if (entry) entries.push(entry);
        }
        if (!entries.some(x => x.isDirectory)) {
            // only files: PrimeNG takes them, with its own checks
            return;
        }
        event.preventDefault();
        event.stopPropagation();
        this.fileUpload?.onDragLeave(event);
        this.readEntries(entries).then(files => this.upload(files));
    }

    private async readEntries(entries: FileSystemEntry[]): Promise<PendingUploadFile[]> {
        const files: PendingUploadFile[] = [];
        for (const entry of entries) {
            await this.readEntry(entry, "", files);
        }
        return files;
    }

    private async readEntry(entry: FileSystemEntry, parentPath: string, files: PendingUploadFile[]): Promise<void> {
        const path = parentPath ? parentPath + "/" + entry.name : entry.name;
        if (entry.isFile) {
            const file: File = await new Promise<File>((resolve, reject) => (entry as FileSystemFileEntry).file(resolve, reject));
            files.push({ file, relativePath: path });
        } else if (entry.isDirectory) {
            const reader = (entry as FileSystemDirectoryEntry).createReader();
            // a reader gives the entries in chunks, until an empty one
            while (true) {
                const chunk: FileSystemEntry[] = await new Promise<FileSystemEntry[]>((resolve, reject) => reader.readEntries(resolve, reject));
                if (!chunk.length) break;
                for (const child of chunk) {
                    await this.readEntry(child, path, files);
                }
            }
        }
    }

    private isAccepted(file: File): boolean {
        if (!this.acceptedExtensions.length) return true;
        const dot = file.name.lastIndexOf(".");
        const extension = dot >= 0 ? file.name.substring(dot).toLowerCase() : "";
        return this.acceptedExtensions.some(x => x.toLowerCase() === extension);
    }

    /** Sends the files to the target folder, in batches the server accepts. */
    private async upload(files: PendingUploadFile[]): Promise<void> {
        if (!this.endpointCode || !files.length) return;
        const skippedType = files.filter(x => !this.isAccepted(x.file));
        const skippedSize = files.filter(x => this.isAccepted(x.file) && x.file.size > MAX_FILE_BYTES);
        let accepted = files.filter(x => this.isAccepted(x.file) && x.file.size <= MAX_FILE_BYTES);
        // the notifications show a new list of messages: built here, set once
        const messages: ToastMessageOptions[] = [];
        if (skippedType.length) {
            messages.push({ id: "UPLOAD_SKIPPED_TYPE", severity: "warn", summary: skippedType.length + " file(s) not uploaded", detail: "Their type cannot be ingested: " + skippedType.slice(0, 5).map(x => x.relativePath).join(", ") + (skippedType.length > 5 ? "…" : "") });
        }
        if (skippedSize.length) {
            messages.push({ id: "UPLOAD_SKIPPED_SIZE", severity: "warn", summary: skippedSize.length + " file(s) not uploaded", detail: "They are larger than 100MB: " + skippedSize.slice(0, 5).map(x => x.relativePath).join(", ") });
        }
        if (!accepted.length) {
            this.notify(messages);
            return;
        }
        // a file already in the data source is replaced only once the user agrees
        const existing = accepted.filter(x => this.existsInTarget(x.relativePath));
        if (existing.length) {
            const choice = await this.confirmReplace(existing);
            if (choice === "cancel") {
                this.notify(messages);
                return;
            }
            if (choice === "skip") {
                accepted = accepted.filter(x => !existing.includes(x));
                messages.push({ id: "UPLOAD_SKIPPED_EXISTING", severity: "info", summary: existing.length + " existing file(s) kept", detail: "They were not replaced: " + existing.slice(0, 5).map(x => x.relativePath).join(", ") + (existing.length > 5 ? "…" : "") });
                if (!accepted.length) {
                    this.notify(messages);
                    return;
                }
            }
        }
        const batches: PendingUploadFile[][] = [];
        let batch: PendingUploadFile[] = [], batchBytes = 0;
        for (const item of accepted) {
            if (batch.length && (batch.length >= MAX_BATCH_FILES || batchBytes + item.file.size > MAX_BATCH_BYTES)) {
                batches.push(batch);
                batch = [];
                batchBytes = 0;
            }
            batch.push(item);
            batchBytes += item.file.size;
        }
        batches.push(batch);
        const totalBytes = accepted.reduce((sum, x) => sum + x.file.size, 0) || 1;
        let sentBytes = 0, uploaded = 0;
        this.uploading = true;
        this.uploadProgress = 0;
        const url = this.basePath + "/api/admin/FileUploadController/uploadToEndpoint/" + encodeURIComponent(this.endpointCode);
        try {
            for (const current of batches) {
                this.uploadLabel = "Uploading " + (uploaded + 1) + "-" + (uploaded + current.length) + " of " + accepted.length;
                const form = new FormData();
                current.forEach(x => {
                    form.append("files[]", x.file, x.file.name);
                    form.append("relativePaths[]", x.relativePath);
                });
                if (this.targetFolder) form.append("folder", this.targetFolder);
                const batchBytes = current.reduce((sum, x) => sum + x.file.size, 0);
                await new Promise<void>((resolve, reject) => {
                    let answered = false;
                    this.http.post(url, form, { reportProgress: true, observe: "events" }).subscribe({
                        next: (event) => {
                            if (event.type === HttpEventType.UploadProgress) {
                                const sent = event.total ? batchBytes * event.loaded / event.total : 0;
                                this.uploadProgress = Math.min(100, Math.round(100 * (sentBytes + sent) / totalBytes));
                            } else if (event.type === HttpEventType.Response) {
                                answered = event.ok;
                            }
                        },
                        error: reject,
                        // a batch is uploaded only when the server answered it: an error an
                        // interceptor turned into a plain completion is a failure too
                        complete: () => answered ? resolve() : reject(new Error("The upload was not accepted"))
                    });
                });
                sentBytes += batchBytes;
                uploaded += current.length;
            }
            messages.push({ id: "UPLOAD_DONE", severity: "success", summary: uploaded + " file(s) uploaded", detail: "Into " + this.targetFolderLabel + ": publish the data source to add them to the knowledge base" });
        } catch (error) {
            messages.push({ id: "UPLOAD_FAILED", severity: "error", summary: "Upload failed", detail: uploaded + " of " + accepted.length + " file(s) were uploaded before the error" });
        } finally {
            // the upload goes on across awaits: its end is brought back into Angular
            this.zone.run(() => {
                this.notify(messages);
                this.uploading = false;
                this.uploadLabel = "";
                if (this.targetFolder) this.expandedPaths.add(this.targetFolder);
                this.reload();
            });
        }
    }

    /** Shows the messages of an upload, straight through the notifications service. */
    private notify(messages: ToastMessageOptions[]): void {
        if (messages.length) {
            this.notifications.addMessages("GeboAIUploadsModule", "GeboAIUploadsFilesComponent", messages as any);
        }
    }

    /** True when the data source already holds a file at this path under the target folder. */
    private existsInTarget(relativePath: string): boolean {
        const path = this.targetFolder ? this.targetFolder + "/" + relativePath : relativePath;
        const node = this.findNode(this.root, path);
        return !!node && !node.folder;
    }

    /** Asks whether the files already in the data source are replaced, skipped, or the upload cancelled. */
    private confirmReplace(existing: PendingUploadFile[]): Promise<"replace" | "skip" | "cancel"> {
        const list = existing.slice(0, 10).map(x => x.relativePath).join(", ") + (existing.length > 10 ? ", …" : "");
        return new Promise(resolve => {
            this.zone.run(() => this.confirmationService.confirm({
                header: "Replace existing files",
                message: existing.length + " file(s) already exist in " + this.targetFolderLabel + " and will be replaced: " + list
                    + ". Their published contents are updated at the next publish.",
                icon: "pi pi-exclamation-triangle",
                acceptLabel: "Replace",
                rejectLabel: "Skip existing",
                accept: () => resolve("replace"),
                reject: (type: ConfirmEventType) => resolve(type === ConfirmEventType.REJECT ? "skip" : "cancel")
            }));
        });
    }

    // ------------------------------------------------------------- tree actions

    protected openNewFolder(parent: UploadedFileNode | undefined): void {
        this.newFolderParent = parent?.relativePath || "";
        this.newFolderName = "";
        this.newFolderVisible = true;
    }

    protected createFolder(): void {
        const name = (this.newFolderName || "").trim();
        if (!this.endpointCode || !name) return;
        const path = this.newFolderParent ? this.newFolderParent + "/" + name : name;
        this.uploadsService.createUploadsFolder(this.endpointCode, path).subscribe({
            next: (status) => {
                if (status?.result?.relativePath) {
                    this.newFolderVisible = false;
                    if (this.newFolderParent) this.expandedPaths.add(this.newFolderParent);
                    this.targetFolder = status.result.relativePath;
                    this.reload();
                } else {
                    this.messages = (status?.messages || []) as ToastMessageOptions[];
                }
            }
        });
    }

    protected confirmDelete(node: UploadedFileNode): void {
        if (!this.endpointCode || !node.relativePath) return;
        const what = node.folder ? "the folder " + node.relativePath + " and the " + (node.filesCount || 0) + " file(s) it holds" : "the file " + node.relativePath;
        const published = node.publishedFilesCount ? " Its published contents leave the knowledge base at the next publish." : "";
        this.confirmationService.confirm({
            header: "Delete from the data source",
            message: "Delete " + what + "?" + published,
            icon: "pi pi-exclamation-triangle",
            accept: () => {
                this.uploadsService.deleteUploadedFiles([node.relativePath!], this.endpointCode!).subscribe({
                    next: (status) => {
                        this.messages = (status?.messages || []) as ToastMessageOptions[];
                        if (this.targetFolder === node.relativePath || this.targetFolder.startsWith(node.relativePath + "/")) {
                            this.targetFolder = "";
                        }
                        this.reload();
                    }
                });
            }
        });
    }

    protected formatSize(bytes: number | undefined): string {
        const value = bytes || 0;
        if (value < 1024) return value + " B";
        const units = ["KB", "MB", "GB"];
        let size = value / 1024, unit = 0;
        while (size >= 1024 && unit < units.length - 1) {
            size /= 1024;
            unit++;
        }
        return size.toFixed(size < 10 ? 1 : 0) + " " + units[unit];
    }
}
