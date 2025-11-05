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
 * This file defines a wrapper component that provides a download link for content from the Gebo.ai system.
 * It handles retrieving content URLs and formatting them for user download.
 */

import { HttpClient } from "@angular/common/http";
import { Component, ElementRef, Inject, Input, OnChanges, OnInit, SimpleChanges, ViewChild } from "@angular/core";
import { getAuthHeader } from "../../infrastructure/gebo-credentials";
import { BASE_PATH, ContentMetaInfo, IngestionFileType } from "@Gebo.ai/gebo-ai-rest-api";

/**
 * External JavaScript function declaration for opening documents in an iframe
 */
declare function geboOpenDocumentJS(url:any,token:any,iframeElement:any,contentType:any):void; 

/**
 * Component that renders a download link for content accessible via the Gebo.ai platform.
 * It creates a simple anchor tag that allows users to download files based on content metadata.
 */
@Component({
    selector: "gebo-ai-download-link-viewer-wrapper",
    template: "<a *ngIf='downloadableContentUrl' [href]='downloadableContentUrl'> Download {{contentMetaInfo?.fileName}} </a>",
    standalone: false
})
export class DownloadLinkViewerWrapperComponent implements OnInit, OnChanges {
    /**
     * Reference to an iframe element that may be used for document display
     */
    @ViewChild("iframeItem") iframe?: ElementRef<Element>;
    
    /**
     * Metadata information about the content to be downloaded
     */
    @Input() public contentMetaInfo?: ContentMetaInfo;
    
    /**
     * Type of file being ingested
     */
    @Input() public ingestionFileType?: IngestionFileType;
    
    /**
     * URL to the external content that will be downloaded
     */
    @Input() public externalContentUrl?: string;
    
    /**
     * Processed URL used for the download link
     */
    public downloadableContentUrl?:string;
    
    /**
     * Data storage for component
     */
    data: any;
    
    /**
     * Content MIME type of the downloadable file
     */
    public contentType?: string;
    
    /**
     * Initializes the component with dependency injection
     * @param path Base API path from the application configuration
     * @param httpClient HTTP client for making API requests
     */
    constructor(@Inject(BASE_PATH) private path: string, private httpClient: HttpClient) {

    }

    /**
     * Fetches the content from the provided URL and creates a downloadable object URL
     * Adds the asDownload parameter to the URL and includes authentication token in the request
     * @param externalContentUrl URL to the content that needs to be downloaded
     */
    async downloadURL(externalContentUrl: string) {
        const url: string = externalContentUrl + "&asDownload=true";
        const object=await fetch(url, {
          headers: getAuthHeader()
        });
       
        this.downloadableContentUrl=URL.createObjectURL(await object.blob());
        
        
      }

    /**
     * Lifecycle hook that initializes the component
     */
    ngOnInit(): void {

    }

    /**
     * Lifecycle hook that responds to changes in component inputs
     * Processes the download URL when content metadata and external URL are available
     * @param changes SimpleChanges object containing changed properties
     */
    ngOnChanges(changes: SimpleChanges) {
        if (this.contentMetaInfo && this.externalContentUrl && changes["externalContentUrl"]) {
            this.contentType = this.contentMetaInfo.contentType;
            console.log("creating link for content type=>" + this.contentType + " url=>" + this.externalContentUrl);
            this.downloadURL(this.externalContentUrl);
            
        }

    }

}