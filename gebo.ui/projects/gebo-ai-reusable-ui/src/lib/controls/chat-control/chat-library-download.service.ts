/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
import { HttpClient } from "@angular/common/http";
import { Inject, Injectable } from "@angular/core";
import { BASE_PATH } from "@Gebo.ai/gebo-ai-rest-api";
import { Observable } from "rxjs";

/**
 * Takes a resource of a library a conversation holds away as a file, the way
 * the chat exports an answer: the file comes from the library's own source
 * through the backend, named as the backend names it.
 */
@Injectable({
    providedIn: "root"
})
export class GeboAIChatLibraryDownloadService {

    constructor(private httpClient: HttpClient, @Inject(BASE_PATH) private basePath: string) {
    }

    /** The extension a content type usually has, for a file the backend did not name. */
    public extensionOf(contentType: string | undefined): string {
        const known: { [type: string]: string } = {
            'application/zip': '.zip', 'application/pdf': '.pdf', 'text/plain': '.txt', 'text/csv': '.csv',
            'application/json': '.json', 'text/markdown': '.md', 'text/html': '.html',
            'application/x-tar': '.tar', 'application/gzip': '.gz'
        };
        return known[(contentType || '').split(';')[0].trim()] || '';
    }

    /**
     * Downloads the resource: completes once the file is handed to the browser,
     * fails when the backend has nothing to give.
     */
    public download(chatCode: string, libraryTypeId: string, resourceId: string): Observable<string> {
        const key = libraryTypeId + "/" + resourceId;
        const url = `${this.basePath}/api/users/GeboChatLibrariesController/downloadChatLibraryResource`;
        return new Observable<string>((subscriber) => {
            const request = this.httpClient.get(url, {
                params: { chatCode: chatCode, libraryTypeId: libraryTypeId, resourceId: resourceId },
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
                    subscriber.next(fileName);
                },
                error: (e) => {
                    console.error("[chat-libraries] the resource " + key + " could not be downloaded", e);
                    subscriber.error(e);
                },
                complete: () => subscriber.complete()
            });
            return () => request.unsubscribe();
        });
    }
}
