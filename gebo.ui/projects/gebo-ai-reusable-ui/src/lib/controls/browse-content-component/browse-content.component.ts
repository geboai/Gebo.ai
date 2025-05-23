/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/*
 * AI generated comments
 * Browse Content Component
 * This component is responsible for displaying external content in an iframe.
 * It takes a URL as input and loads it into an iframe element, allowing users
 * to browse external websites or resources within the application.
 */
import { AfterViewInit, Component, ElementRef, Input, OnChanges, SimpleChanges, ViewChild } from "@angular/core";

@Component({
    selector: "gebo-ai-browse-content-component",
    templateUrl: "browse-content.component.html",
    standalone: false
})
export class BrowseContentComponent implements OnChanges,AfterViewInit {
    
    /**
     * Reference to the iframe element in the template
     * Used to set the src attribute when the URL changes
     */
    @ViewChild("iframeComponent") iframe?:ElementRef<Element>;
    
    /**
     * The title to display for the browsed content
     * Defaults to "Browsing external content"
     */
    @Input() title:string="Browsing external content";
    
    /**
     * The URL of the external content to be loaded in the iframe
     */
    @Input() url?:string;
    
    /**
     * Initializes a new instance of the BrowseContentComponent
     */
    constructor() {

    }
    
    /**
     * Lifecycle hook that is called after the component's view has been initialized
     * Sets the iframe src attribute to the URL if available
     */
    ngAfterViewInit(): void {
        if (this.url){
            
            if (this.iframe) {
                this.iframe.nativeElement.setAttribute("src",this.url);
            }
        }
    }
    
    /**
     * Lifecycle hook that is called when any data-bound property of the component changes
     * Updates the iframe src attribute when the URL input changes
     * @param changes - Object containing all changed properties with current and previous values
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.url && changes["url"]){
            console.log("Forwarding to url:"+this.url);
            if (this.iframe) {
                this.iframe.nativeElement.setAttribute("src",this.url);
            }
        }
    }
}