/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
import { HttpClient } from "@angular/common/http";
import { Component, Inject, Input, OnChanges, SimpleChanges } from "@angular/core";
import { AIResource, AIToolsAndResourcesLibraryInstance, BASE_PATH, GeboChatLibrariesControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";

/**
 * The libraries of tools and resources a conversation holds - a working
 * directory, a sandbox - and the resources of them the user can take away as
 * a file. Type agnostic: which resources can be downloaded is said by their
 * types, and the file comes from the library's own source through the
 * backend.
 */
@Component({
    selector: "gebo-ai-chat-libraries-panel",
    templateUrl: "chat-libraries-panel.component.html",
    standalone: false,
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
    public visible: boolean = false;
    public loading: boolean = false;
    public downloading: string | null = null;

    constructor(
        private service: GeboChatLibrariesControllerService,
        private httpClient: HttpClient,
        @Inject(BASE_PATH) private basePath: string) {
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
            return;
        }
        this.loading = true;
        this.service.getChatLibraryInstance(this.userChatContextCode).subscribe({
            next: (held) => {
                this.instances = held || [];
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

    /** The extension a content type usually has, for a file the backend did not name. */
    private extensionOf(contentType: string | undefined): string {
        const known: { [type: string]: string } = {
            'application/zip': '.zip', 'application/pdf': '.pdf', 'text/plain': '.txt', 'text/csv': '.csv',
            'application/json': '.json', 'text/markdown': '.md', 'text/html': '.html',
            'application/x-tar': '.tar', 'application/gzip': '.gz'
        };
        return known[(contentType || '').split(';')[0].trim()] || '';
    }

    /** Takes the resource away as a file, the way the chat exports an answer. */
    public download(instance: AIToolsAndResourcesLibraryInstance, resource: AIResource): void {
        if (!this.userChatContextCode || !instance.libraryType?.id || !resource.id) {
            return;
        }
        const key = instance.libraryType.id + "/" + resource.id;
        this.downloading = key;
        const url = `${this.basePath}/api/users/GeboChatLibrariesController/downloadChatLibraryResource`;
        this.httpClient.get(url, {
            params: { chatCode: this.userChatContextCode, libraryTypeId: instance.libraryType.id, resourceId: resource.id },
            responseType: 'blob',
            observe: 'response'
        }).subscribe({
            next: (response) => {
                const blob = response.body as Blob;
                const disposition = response.headers.get('Content-Disposition') || '';
                const named = /filename="?([^";]+)"?/.exec(disposition);
                // the name the backend gave, else one from what the file is
                const fileName = named ? named[1] : (key.replace('/', '-') + this.extensionOf(blob.type));
                const fileURL = URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = fileURL;
                a.download = fileName;
                a.style.display = 'none';
                document.body.appendChild(a);
                a.click();
                a.remove();
                URL.revokeObjectURL(fileURL);
                console.debug("[chat-libraries] downloaded " + fileName + " (" + blob.size + " bytes)");
            },
            error: (e) => {
                console.error("[chat-libraries] the resource " + key + " could not be downloaded", e);
                this.downloading = null;
            },
            complete: () => {
                this.downloading = null;
            }
        });
    }
}
