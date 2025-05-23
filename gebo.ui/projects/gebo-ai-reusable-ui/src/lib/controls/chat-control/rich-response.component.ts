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
 * Component for displaying and managing rich responses from Gebo.ai APIs.
 * This component is responsible for rendering various types of rich content
 * that are returned from the Gebo.ai platform, such as formatted text,
 * media contents, interactive elements, etc.
 */
import { Component, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { RichResponse } from "@Gebo.ai/gebo-ai-rest-api";

@Component({
    selector: "gebo-ai-rich-response-viewer-component",
    templateUrl: "rich-response.component.html",
    standalone: false
})
export class GeboAIRichResponseViewerComponent implements OnInit,OnChanges{
  /**
   * Lifecycle hook that is called after component initialization.
   * Currently empty but can be used for initial setup of the component.
   */
  ngOnInit(): void {

  }

  /**
   * Lifecycle hook that is called when any data-bound property of the component changes.
   * Can be used to react to changes in the richResponse input property.
   * 
   * @param changes Object containing the properties that have changed with their current and previous values
   */
  ngOnChanges(changes: SimpleChanges): void {

  }

  /**
   * The rich response object to be displayed by this component.
   * Contains structured data like text, images, cards, or other interactive elements.
   */
  @Input() richResponse?:RichResponse;

}