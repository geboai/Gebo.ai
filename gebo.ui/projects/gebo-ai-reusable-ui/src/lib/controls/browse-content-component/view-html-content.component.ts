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
 * This component allows rendering HTML content dynamically in an Angular application.
 * It safely injects HTML content provided as an input into the DOM.
 * The component handles both initial rendering and updates to the HTML content.
 */
import { AfterViewInit, Component, ElementRef, Input, OnChanges, SimpleChanges, ViewChild } from "@angular/core";

/**
 * A component that renders HTML content passed to it as a string.
 * The component uses a simple div container and injects the HTML content into it.
 * It implements OnChanges to react to input changes and AfterViewInit to handle
 * the initial rendering after the view is initialized.
 */
@Component({
    selector: "gebo-ai-html-viewer-component",
    template: "<div class='col-12' #thisDiv ></div>",
    standalone: false
})
export class GeboAIViewHtmlComponent implements OnChanges, AfterViewInit {
  /**
   * Flag to track whether HTML has been injected to prevent multiple injections
   */
  private injectedHtml: boolean = false;
  
  /**
   * Lifecycle hook that is called after the component's view has been initialized.
   * Attempts to inject the HTML content if available.
   */
  ngAfterViewInit(): void {
    this.tryInjectHtml();
  }
  
  /**
   * Reference to the container div element where HTML will be injected
   */
  @ViewChild("thisDiv") thisDiv?: ElementRef<Element>;
  
  /**
   * The HTML string to be rendered in the component
   */
  @Input() htmlCode?: string;
  
  /**
   * Lifecycle hook that is called when any data-bound property of the component changes.
   * Attempts to inject the HTML content when htmlCode input changes.
   * @param changes SimpleChanges object containing current and previous values of the changed properties
   */
  ngOnChanges(changes: SimpleChanges): void {
    this.tryInjectHtml();
  }
  
  /**
   * Injects the HTML content into the container element if both the HTML code
   * and the container element are available, and if the HTML hasn't already been injected.
   * Sets the injectedHtml flag to true to prevent multiple injections.
   */
  private tryInjectHtml() {
    if (this.htmlCode && this.thisDiv) {
      if (!this.injectedHtml) {
         this.thisDiv.nativeElement.innerHTML = this.htmlCode;
      }
      this.injectedHtml = true;
    }
  }
}