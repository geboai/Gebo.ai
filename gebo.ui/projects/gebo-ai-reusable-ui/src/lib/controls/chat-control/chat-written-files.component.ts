/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
import { Component, Input, ChangeDetectionStrategy } from "@angular/core";
import { ChatWrittenFile, GConversationFile } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
import { GeboAIChatLibraryDownloadService } from "./chat-library-download.service";

/**
 * The files the tools wrote while answering one request, under its answer:
 * each by name and where it is, with a download while the conversation still
 * holds it as a file of its own - a document of a library. A file of a library
 * kept as a whole, like a working directory, is only named: the libraries
 * panel takes the whole away.
 */
@Component({
    selector: "gebo-ai-chat-written-files",
    templateUrl: "chat-written-files.component.html",
    standalone: false,
    changeDetection: ChangeDetectionStrategy.Eager,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIChatControlModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIChatWrittenFilesComponent"), multi: false }
    ]
})
export class GeboAIChatWrittenFilesComponent {
    /** The conversation the answer belongs to. */
    @Input() userChatContextCode?: string;
    /** The files the request wrote: the answer's own list. */
    @Input() files?: GConversationFile[] | null;
    /** What the conversation holds now of every file its tools wrote: says which can be downloaded. */
    @Input() conversationFiles?: ChatWrittenFile[] | null;
    @Input() disabled: boolean = false;

    public downloading: string | null = null;

    constructor(private downloads: GeboAIChatLibraryDownloadService) {
    }

    public get written(): GConversationFile[] {
        return (this.files || []).filter((file) => !!file?.location && !!file?.path);
    }

    public keyOf(file: GConversationFile): string {
        return file.location + "/" + file.path;
    }

    /** The file's name, its folders left to the title. */
    public nameOf(file: GConversationFile): string {
        const path = (file.path || "").replace(/\\/g, "/");
        return path.substring(path.lastIndexOf("/") + 1) || path;
    }

    public isDownloadable(file: GConversationFile): boolean {
        const path = (file.path || "").replace(/\\/g, "/");
        return (this.conversationFiles || []).some((held) => held.downloadable === true
            && held.location === file.location && (held.path || "").replace(/\\/g, "/") === path);
    }

    public download(file: GConversationFile): void {
        if (!this.userChatContextCode || !file.location || !file.path) {
            return;
        }
        this.downloading = this.keyOf(file);
        this.downloads.download(this.userChatContextCode, file.location, file.path).subscribe({
            error: () => this.downloading = null,
            complete: () => this.downloading = null
        });
    }
}
