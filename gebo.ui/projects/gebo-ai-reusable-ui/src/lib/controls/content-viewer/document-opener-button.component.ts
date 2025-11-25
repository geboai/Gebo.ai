/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { Component, Input } from "@angular/core";
import { EnrichedDocumentReferenceView, EnrichedLLMGeneratedResource, EnrichedUserUploadedContentView } from "./enriched-document-reference-view.service";

/**
 * AI generated comments
 * Angular component that provides a button for opening and viewing documents.
 * The component receives a document reference as input and keeps track of the
 * currently viewed document code. It handles document selection actions.
 */
@Component({
    selector: "gebo-ui-document-opener-button",
    templateUrl: "document-opener-button.component.html",
    standalone: false
})
export class GeboUIDocumentOpenerButton {

    /**
     * Input property that accepts an EnrichedDocumentReferenceView object
     * to be displayed and potentially opened by this component.
     */
    @Input() document?: EnrichedDocumentReferenceView;
    @Input() uploadedContent?: EnrichedUserUploadedContentView;
    @Input() generatedContent?: EnrichedLLMGeneratedResource;
    /**
     * Stores the code/identifier of the currently viewed document.
     */
    protected currentViewedDocumentCode?: string;
    protected currentViewedUploadedContent?: EnrichedUserUploadedContentView;
    protected currentViewedGeneratedContent?: EnrichedLLMGeneratedResource;

    /**
     * Handles the click event when a file/document is selected.
     * Updates the currentViewedDocumentCode with the code of the selected document.
     * 
     * @param item The document reference that was clicked/selected
     */
    onClickFile(item: EnrichedDocumentReferenceView) {
        this.currentViewedDocumentCode = item.code;
    }
    onClickUploadedContent(arg0: EnrichedUserUploadedContentView) {
        this.currentViewedUploadedContent = arg0;
    }
    onClickGeneratedContent(arg0: EnrichedLLMGeneratedResource) {
        this.currentViewedGeneratedContent = arg0;
    }
}