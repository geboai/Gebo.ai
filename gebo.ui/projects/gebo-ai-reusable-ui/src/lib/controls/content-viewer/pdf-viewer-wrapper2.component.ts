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
 * This module provides a wrapper component for PDF viewing using ngx-extended-pdf-viewer.
 * It handles authentication and PDF rendering with proper headers.
 */

import { HttpClient } from "@angular/common/http";
import { Component, Inject, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from "@angular/core";
import { Subscription } from "rxjs";
import { getAuth, getHttpHeaders } from "../../infrastructure/gebo-credentials";
import { BASE_PATH } from "@Gebo.ai/gebo-ai-rest-api";
import { pdfDefaultOptions } from 'ngx-extended-pdf-viewer';

/**
 * Component that wraps the ngx-extended-pdf-viewer to display PDF documents.
 *
 * The contents protected by Gebo.ai are fetched by this component and handed over
 * to the viewer as bytes, instead of letting the viewer request the url itself.
 * This is the way the rest of the content viewing area works (see the download
 * link and the web viewer wrappers) and it is what guarantees the authorization
 * token reaches the backend:
 *
 * - the token is read when the document is actually loaded, so a token renewed
 *   after this component was created is honoured;
 * - the request goes through the Angular HttpClient, hence through the standard
 *   authentication interceptor of the application;
 * - no credentialed cross origin request is issued. The viewer asked for the
 *   url on its own with `withCredentials` turned on, and a credentialed request
 *   is refused by the browser whenever the backend answers
 *   `Access-Control-Allow-Origin: *`, which is what GeboAICorsFilter does: the
 *   request was then blocked before being sent and the backend never saw the
 *   token at all (this happens when the UI is served by the development server
 *   on another port than the backend).
 *
 * The whole file is kept in memory while it is shown, which is the same trade off
 * the download link wrapper already makes, and it lets the viewer work with the
 * content controllers whether they support range requests or not.
 *
 * The component will only render when a PDF has been loaded.
 */
@Component({
    selector: "gebo-ai-pdf-viewer-wrapper2",
    template: "@if (pdfData) {<ngx-extended-pdf-viewer  [src]='pdfData'  [filenameForDownload]='fileName'     style='height: 650px;width: 100%' (pdfLoadingFailed)='onError($event)' ></ngx-extended-pdf-viewer>}",
    standalone: false
})
export class PDFViewerWrapper2Component implements OnInit, OnChanges, OnDestroy {
    /**
     * Input property for the PDF source URL to be displayed
     */
    @Input() pdfSrc?: string ;

    /**
     * Name proposed to the user when the document is downloaded from the viewer.
     * The viewer guesses it from the url when it is not given, which the urls of
     * the Gebo.ai content controllers cannot provide.
     */
    @Input() fileName?: string;

    /**
     * Contents of the document being shown, loaded by {@link loadPdf}
     */
    protected pdfData?: Uint8Array;

    /**
     * Loading of the document currently in progress, kept to drop it when the
     * source changes or the component goes away
     */
    private loading?: Subscription;

    /**
     * Initializes the component with the necessary dependencies and sets up the PDF viewer
     * @param path Base path for API requests
     * @param httpClient HTTP client for making requests
     */
    constructor(@Inject(BASE_PATH) private path: string, private httpClient: HttpClient) {
        pdfDefaultOptions.assetsFolder = 'assets/bleeding-edge';
    }
    
    /**
     * Handles errors that occur during PDF loading or rendering
     * @param error The error object from the PDF viewer
     */
    onError(error:any) {
        console.error("PDF viewer error:"+error);
    }
    
    /**
     * Lifecycle hook that runs when the component initializes
     */
    ngOnInit(): void {
        console.log("PDF Viewer init");
    }

    /**
     * Fetches the document with the authorization token of the logged user and
     * hands its bytes over to the viewer.
     *
     * @param url Url serving the document, protected by Gebo.ai
     */
    private loadPdf(url: string) {
        const authenticated: boolean = getAuth()?.token ? true : false;
        console.log("PDF Viewer loading " + url + " authenticated:" + authenticated);
        if (!authenticated) {
            console.error("PDF Viewer: no Gebo.ai credentials available, the content request will be refused: " + url);
        }
        this.loading?.unsubscribe();
        this.loading = this.httpClient.get(url, { headers: getHttpHeaders(), responseType: "arraybuffer" }).subscribe({
            next: (content) => {
                this.pdfData = new Uint8Array(content);
                console.log("PDF Viewer loaded " + url + " bytes:" + this.pdfData.length);
            },
            error: (error) => {
                this.pdfData = undefined;
                console.error("PDF Viewer cannot load " + url, error);
            }
        });
    }

    /**
     * Lifecycle hook that runs when component inputs change
     * Detects changes to the pdfSrc input and loads the document accordingly
     * @param changes Object containing all changed properties
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["pdfSrc"]) {
            this.pdfData = undefined;
            if (this.pdfSrc) {
                this.loadPdf(this.pdfSrc);
            } else {
                console.log("PDF Viewer source cleared");
                this.loading?.unsubscribe();
                this.loading = undefined;
            }
        }
    }

    /**
     * Lifecycle hook that runs when the component is destroyed, dropping a
     * loading still in progress
     */
    ngOnDestroy(): void {
        this.loading?.unsubscribe();
        this.loading = undefined;
    }

}
