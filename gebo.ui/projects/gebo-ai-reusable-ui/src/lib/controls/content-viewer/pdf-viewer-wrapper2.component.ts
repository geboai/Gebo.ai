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

import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Component, Inject, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { getAuth } from "../../infrastructure/gebo-credentials";
import { BASE_PATH } from "@Gebo.ai/gebo-ai-rest-api";
import { pdfDefaultOptions } from 'ngx-extended-pdf-viewer';

/**
 * Interface defining the structure for PDF source configuration.
 * Allows specifying a URL, HTTP headers, credentials settings, or raw PDF data.
 */
interface PDFSrc {
    url?: string;
    httpHeaders?: any;
    withCredentials?: boolean;
    data?: any;
}

/**
 * Component that wraps the ngx-extended-pdf-viewer to display PDF documents.
 * It handles authentication by automatically adding authorization headers to requests.
 * The component will only render when a PDF source is provided.
 */
@Component({
    selector: "gebo-ai-pdf-viewer-wrapper2",
    template: "<ngx-extended-pdf-viewer *ngIf='pdfSrc'  [authorization]='true' [httpHeaders]='httpHeaders'  [src]='pdfSrc'     style='height: 650px;width: 100%' (error)='onError($event)' ></ngx-extended-pdf-viewer>",
    standalone: false
})
export class PDFViewerWrapper2Component implements OnInit, OnChanges {
    /**
     * Input property for the PDF source URL to be displayed
     */
    @Input() pdfSrc?: string ;
    
    /**
     * Object containing PDF source configuration
     */
    pdfInfos?: PDFSrc;
    
    /**
     * HTTP headers to be sent with PDF requests, including authorization
     */
    httpHeaders?:any;
    
    /**
     * Initializes the component with the necessary dependencies and sets up the PDF viewer
     * @param path Base path for API requests
     * @param httpClient HTTP client for making requests
     */
    constructor(@Inject(BASE_PATH) private path: string, private httpClient: HttpClient) {
        pdfDefaultOptions.assetsFolder = 'assets/bleeding-edge';
        this.httpHeaders={Authorization:"Bearer "+ getAuth()?.accessToken};
        
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
     * Lifecycle hook that runs when component inputs change
     * Detects changes to the pdfSrc input and updates the viewer accordingly
     * @param changes Object containing all changed properties
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.pdfSrc && changes["pdfSrc"]) {
            console.log("PDF Viewer loading "+this.pdfSrc);
            
            /*
            this.httpClient.get<Blob>(this.pdfSrc,{headers:{accept:"application/pdf"}}).subscribe({
                next: (data) => {
                    this.pdfInfos = {
                        data: data
                    };
                    console.log("PDF Viewer loaded=> "+this.pdfSrc);
                },
                error:(err)=>{
                    console.error(err);
                },
                complete:()=>{

                }
            })*/


        }
    }

}