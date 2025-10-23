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
 * 
 * The DocumentRefComponent is responsible for displaying document references in the Gebo.ai chat interface.
 * It handles rendering documents with appropriate icons based on their type, allows documents to be opened
 * for viewing, and supports selecting documents in a list (via the "choosed" property).
 * The component emits events when a document is selected to be added to a chosen items collection.
 */
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { GResponseDocumentRef } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST } from "../field-host-component-iface/field-host-component-iface";

@Component({
    selector: "gebo-ai-chat-docref",
    templateUrl: "document-ref.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("DocumentRefComponent"), multi: true }]
})
export class DocumentRefComponent implements OnInit, OnChanges {
    /**
     * The document reference to be displayed by this component
     */
    @Input() ref?: GResponseDocumentRef;

    /**
     * Indicates if this document has been selected in the current context
     */
    @Input() choosed: boolean = false;

    /**
     * Controls visibility of the document viewing window
     */
    public openDocumentWindow: boolean = false;

    /**
     * Stores the document code to be displayed when the document is opened
     */
    public openDocumentCode: string = "";

    /**
     * CSS class for the document icon, based on document type
     */
    public contentCssClass: string = "pi pi-file-o";

    /**
     * Display name for the document, derived from name, shortCode, or documentCode
     */
    public name?: string = "";

    /**
     * Event emitted when this document is selected by the user
     */
    @Output() addToChoosed: EventEmitter<GResponseDocumentRef> = new EventEmitter();

    /**
     * Component initialization lifecycle hook
     */
    ngOnInit(): void {

    }

    /**
     * Handles changes to component inputs
     * Updates icon and name when the document reference changes
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.ref && changes["ref"]) {
            this.contentCssClass = this.getIcon(this.ref);
            this.name = this.ref.name ? this.ref.name : this.ref.shortCode ? this.ref.shortCode : this.ref.documentCode;
        }
    }

    /**
     * Opens the document viewer if the document has a valid document code
     * @param g The document reference to display
     */
    showDocument(g: GResponseDocumentRef) {
        if (g.documentCode) {
            this.openDocumentCode = g.documentCode;
            this.openDocumentWindow = true;
        }
    }

    /**
     * Closes the document viewer and clears the document code
     */
    closeDocument(): void {
        this.openDocumentWindow = false;
        this.openDocumentCode = "";

    }

    /**
     * Determines the appropriate icon to use based on document type
     * @param child The document reference
     * @returns A CSS class string for the appropriate icon
     */
    getIcon(child: GResponseDocumentRef): string {

        if (child.geboTreatAs === 'sourceCode') return "pi pi-hashtag";
        if (child.geboTreatAs === 'word') return "pi pi-file-word";
        if (child.geboTreatAs === 'excel') return "pi pi-file-excel"
        if (child.geboTreatAs === 'pdf') return "pi pi-file-pdf"
        return "pi pi-file";
    }
}