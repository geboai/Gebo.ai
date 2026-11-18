/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
import { Component, Input, OnChanges, SimpleChanges, ChangeDetectionStrategy } from "@angular/core";
import { AIResource, AIToolsAndResourcesLibraryInstance, ChatWrittenFile, GeboChatLibrariesControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
import { GeboAIChatLibraryDownloadService } from "./chat-library-download.service";

/**
 * The libraries of tools and resources a conversation holds - a working
 * directory, a sandbox - and the resources of them the user can take away as
 * a file. Type agnostic: which resources can be downloaded is said by their
 * types, and the file comes from the library's own source through the
 * backend. The resources the tools of the conversation wrote say in which
 * requests; the files they wrote in a library kept as a whole, like a working
 * directory, are listed under it.
 */
@Component({
    selector: "gebo-ai-chat-libraries-panel",
    templateUrl: "chat-libraries-panel.component.html",
    standalone: false,
    changeDetection: ChangeDetectionStrategy.Eager,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIChatControlModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIChatLibrariesPanelComponent"), multi: false }
    ]
})
export class GeboAIChatLibrariesPanelComponent implements OnChanges {
    /** The conversation whose libraries are shown. */
    @Input() userChatContextCode?: string;
    /** Re-read the libraries when this changes: a run that just ended may have built files. */
    @Input() refreshKey: any = null;
    @Input() disabled: boolean = false;

    public instances: AIToolsAndResourcesLibraryInstance[] = [];
    /** The files the tools of the conversation wrote. */
    public written: ChatWrittenFile[] = [];
    public visible: boolean = false;
    public loading: boolean = false;
    public downloading: string | null = null;

    constructor(
        private service: GeboChatLibrariesControllerService,
        private downloads: GeboAIChatLibraryDownloadService) {
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes['userChatContextCode'] || changes['refreshKey']) {
            this.reload();
        }
    }

    /** What the conversation holds now; nothing when there is no conversation yet. */
    public reload(): void {
        if (!this.userChatContextCode) {
            this.instances = [];
            this.written = [];
            return;
        }
        this.loading = true;
        this.service.getChatLibraryInstance(this.userChatContextCode).subscribe({
            next: (held) => {
                // a library with no resources has nothing to show: its tools are the agents' business
                this.instances = (held || []).filter((instance) => (instance.resources?.length ?? 0) > 0);
                console.debug("[chat-libraries] conversation " + this.userChatContextCode + " holds " + this.instances.length + " library instance(s)");
            },
            error: (e) => {
                console.debug("[chat-libraries] the libraries of conversation " + this.userChatContextCode + " could not be read", e);
                this.instances = [];
            },
            complete: () => {
                this.loading = false;
            }
        });
        const chatCode = this.userChatContextCode;
        this.service.getChatWrittenFiles(chatCode).subscribe({
            next: (written) => {
                this.written = written || [];
            },
            error: (e) => {
                console.debug("[chat-libraries] the files written in conversation " + chatCode + " could not be read", e);
                this.written = [];
            }
        });
    }

    private static slashed(path: string | undefined): string {
        return (path || "").replace(/\\/g, "/");
    }

    /** The file the tools wrote that is this resource, if any. */
    private writtenAs(instance: AIToolsAndResourcesLibraryInstance, resource: AIResource): ChatWrittenFile | undefined {
        const path = GeboAIChatLibrariesPanelComponent.slashed(resource.id);
        return this.written.find((file) => file.location === instance.libraryType?.id
            && GeboAIChatLibrariesPanelComponent.slashed(file.path) === path);
    }

    /** The requests that wrote the resource, as the user numbers them; empty when the tools did not write it. */
    public writtenInRequests(instance: AIToolsAndResourcesLibraryInstance, resource: AIResource): string {
        return (this.writtenAs(instance, resource)?.requests || []).join(", ");
    }

    /** The files the tools wrote in the library that are no resource of their own in it, like a working directory's. */
    public writtenWithin(instance: AIToolsAndResourcesLibraryInstance): ChatWrittenFile[] {
        const resources = new Set(this.resourcesOf(instance).map((resource) => GeboAIChatLibrariesPanelComponent.slashed(resource.id)));
        return this.written.filter((file) => file.location === instance.libraryType?.id
            && !resources.has(GeboAIChatLibrariesPanelComponent.slashed(file.path)));
    }

    public requestsOf(file: ChatWrittenFile): string {
        return (file.requests || []).join(", ");
    }

    public get hasLibraries(): boolean {
        return this.instances.length > 0;
    }

    public resourcesOf(instance: AIToolsAndResourcesLibraryInstance): AIResource[] {
        return instance.resources || [];
    }

    public isDownloadable(resource: AIResource): boolean {
        return resource.resourceType?.downloadable === true;
    }

    public libraryName(instance: AIToolsAndResourcesLibraryInstance): string {
        return instance.libraryType?.description || instance.libraryType?.id || instance.id || "";
    }

    public open(): void {
        this.reload();
        this.visible = true;
    }

    /** Takes the resource away as a file, the way the chat exports an answer. */
    public download(instance: AIToolsAndResourcesLibraryInstance, resource: AIResource): void {
        if (!this.userChatContextCode || !instance.libraryType?.id || !resource.id) {
            return;
        }
        this.downloading = instance.libraryType.id + "/" + resource.id;
        this.downloads.download(this.userChatContextCode, instance.libraryType.id, resource.id).subscribe({
            error: () => this.downloading = null,
            complete: () => this.downloading = null
        });
    }
}
