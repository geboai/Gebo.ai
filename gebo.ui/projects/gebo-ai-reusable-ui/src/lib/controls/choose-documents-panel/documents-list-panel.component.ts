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
 * This file contains the GeboAIDocumentsListPanelComponent which provides functionality for displaying
 * and selecting documents from a list. The component allows users to view a list of documents,
 * select one or more documents, and emit the selection back to a parent component.
 */

import { Component, EventEmitter, forwardRef, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { DocumentReferenceView } from "@Gebo.ai/gebo-ai-rest-api";
import { GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboAIFieldHost } from "../field-host-component-iface/field-host-component-iface";

/**
 * A component that displays a list of documents and allows users to select from them.
 * This panel component can be customized with a title and handles the document selection process.
 * It uses Angular's reactive forms to track and validate document selections.
 */
@Component({
    selector: "gebo-ai-documents-list-panel-component",
    templateUrl: "documents-list-panel.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAIChooseDocumentsPanelModule", multi: false },
        {
            provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIDocumentsListPanelComponent),
            multi: false
        }]
})
export class GeboAIDocumentsListPanelComponent implements OnChanges, OnInit, GeboAIFieldHost {

    /** List of documents to display and select from */
    @Input() documentsList: DocumentReferenceView[] = [];

    /** Customizable title for the panel */
    @Input() title: string = "Select documents";

    /** Indicates whether the document list is currently loading */
    @Input() loading: boolean = false;

    /** Event emitter that sends the selected documents to parent components */
    @Output() documentsSelection: EventEmitter<DocumentReferenceView[]> = new EventEmitter();

    /** Form group to handle document selection */
    formGroup: FormGroup = new FormGroup({
        choosenDocuments: new FormControl()
    });

    /**
     * Responds to changes in input properties.
     * If the documentsList input changes, updates the form control value to reflect the new list.
     * 
     * @param changes The input properties that have changed
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.documentsList && changes["documentsList"]) {
            this.formGroup.controls["choosenDocuments"].setValue(this.documentsList);
        }
    }

    /**
     * Initializes the component by setting validators for the form control.
     * Makes document selection required.
     */
    ngOnInit(): void {
        this.formGroup.controls["choosenDocuments"].setValidators(Validators.required);
    }

    /**
     * Handles the submission of selected documents.
     * Emits the selected documents through the documentsSelection output event and
     * clears the documentsList to reset the component state.
     */
    onOkFiles() {
        const vector: DocumentReferenceView[] = this.formGroup.controls["choosenDocuments"].value;
        this.documentsSelection.emit(vector);
        this.documentsList = [];
    }
    getEntityName(): string {
        return "GeboAIDocumentsListPanelComponent";
    }
}