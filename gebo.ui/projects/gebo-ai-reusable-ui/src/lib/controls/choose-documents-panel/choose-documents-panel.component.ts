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
 * This component provides a panel for selecting and managing documents.
 * It implements ControlValueAccessor to work with Angular forms, allowing it to be used
 * as a form control. The component displays a list of documents, allows viewing document details,
 * and supports adding/removing documents from the selection.
 */
import { Component, forwardRef, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { ControlValueAccessor, FormControl, FormGroup, NG_VALUE_ACCESSOR } from "@angular/forms";
import { EnrichedDocumentReferenceView, EnrichedDocumentReferenceViewRetrieveService } from "../content-viewer/enriched-document-reference-view.service";
import { GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboAIFieldHost } from "../field-host-component-iface/field-host-component-iface";

/**
 * A component that provides a UI panel for choosing and managing documents.
 * It registers itself as a form control through NG_VALUE_ACCESSOR to be usable within Angular forms.
 * The component manages a list of document references and provides various operations like
 * viewing, removing, and selecting documents from knowledge bases.
 */
@Component({
    selector: "gebo-ai-choose-documents-panel-component",
    templateUrl: "choose-documents-panel.component.html",
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => GeboAIChooseDocumentsPanelComponent),
            multi: true
        },
        { provide: GEBO_AI_MODULE, useValue: "GeboAIChooseDocumentsPanelModule", multi: false },
        {
            provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIChooseDocumentsPanelComponent),
            multi: false
        }
    ],
    standalone: false
})
export class GeboAIChooseDocumentsPanelComponent implements OnInit, OnChanges, ControlValueAccessor, GeboAIFieldHost {
    /**
     * Optional array of knowledge base codes to filter document selection
     */
    @Input() knowledgeBaseCodes?: string[];

    /**
     * Flag to set the component in read-only mode
     */
    @Input() readonly: boolean = false;

    /**
     * Flag to disable user interactions with the component
     */
    @Input() interactionDisabled: boolean = false;

    /**
     * Flag to track if documents are currently being loaded
     */
    private loadingDocuments: boolean = false;

    /**
     * Flag to track if file types are currently being loaded
     */
    private loadingFileTypes: boolean = false;

    /**
     * Code of the document currently being viewed
     */
    public currentViewedDocumentCode?: string;

    /**
     * Flag to control the visibility of the search documents window
     */
    public openedSearchDocumentsWindows: boolean = false;

    /**
     * Getter that returns the overall loading state by checking if documents or file types are loading
     */
    public get loading(): boolean {
        return this.loadingDocuments || this.loadingFileTypes;
    }

    /**
     * List of document references displayed in the component
     */
    listedDocuments: EnrichedDocumentReferenceView[] = [];

    /**
     * Form group for managing document selection edits
     */
    editingFormGroup: FormGroup = new FormGroup({
        documentsList: new FormControl()
    });

    /**
     * Constructor for the component
     * @param documentsMetaInfosService Service for retrieving document reference metadata
     */
    public constructor(

        private documentsMetaInfosService: EnrichedDocumentReferenceViewRetrieveService) {
    }
    getEntityName(): string {
        return "GeboAIChooseDocumentsPanelComponent";
    }

    /**
     * Retrieves metadata for documents based on the internal value (document codes)
     * Updates the listedDocuments array with the retrieved document references
     */
    private retrieveDocsMetaData(): void {
        if (this.internalValue && this.internalValue.length) {
            this.loadingDocuments = true;
            this.documentsMetaInfosService.findDocumentReferenceViewByCode(this.internalValue).subscribe({
                next: (docs) => {
                    this.listedDocuments = docs;

                },
                complete: () => {
                    this.loadingDocuments = false;
                }
            });
        } else {
            this.listedDocuments = [];
        }
    }

    /**
     * Internal array of document codes that represents the component's value
     */
    private internalValue: string[] = [];

    /**
     * ControlValueAccessor method to update the internal value from the form model
     * @param obj Array of document codes 
     */
    writeValue(obj: any): void {
        this.internalValue = obj;
        this.retrieveDocsMetaData();
    }

    /**
     * Default change handler function
     */
    private onChange: (v: any) => void = (v: any) => { };

    /**
     * Default touch handler function
     */
    private onTouch: (v: any) => void = (v: any) => { };

    /**
     * ControlValueAccessor method to register the onChange callback
     * @param fn Callback function to be called when the value changes
     */
    registerOnChange(fn: any): void {
        this.onChange = fn;
    }

    /**
     * ControlValueAccessor method to register the onTouch callback
     * @param fn Callback function to be called when the control is touched
     */
    registerOnTouched(fn: any): void {
        this.onTouch = fn;
    }

    /**
     * ControlValueAccessor method to handle the disabled state
     * @param isDisabled Flag indicating if the control should be disabled
     */
    setDisabledState?(isDisabled: boolean): void {
    }

    /**
     * Angular lifecycle hook that runs when the component initializes
     */
    ngOnInit(): void {

    }

    /**
     * Removes a document from the selection
     * @param item Document reference to remove
     */
    doRemoveFile(item: EnrichedDocumentReferenceView) {
        this.listedDocuments = this.listedDocuments.filter(x => x.code !== item.code);
        this.internalValue = this.internalValue.filter(x => x !== item.code);
        this.onChange(this.internalValue);
    }

    /**
     * Handles click events on document items to view their details
     * @param item Document reference that was clicked
     */
    onClickFile(item: EnrichedDocumentReferenceView) {

        this.currentViewedDocumentCode = item.code;
    }

    /**
     * Opens the document selection window and initializes the form with current values
     */
    openSelectDocumentsWindow(): void {
        this.editingFormGroup.patchValue({
            documentsList: this.internalValue
        });
        this.openedSearchDocumentsWindows = true;
    }

    /**
     * Angular lifecycle hook that runs when component inputs change
     * @param changes SimpleChanges object containing the changed properties
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.knowledgeBaseCodes && changes["knowledgeBaseCode"]) {

        }
    }

    /**
     * Applies the selected documents from the search window and updates the component value
     */
    acceptedValues(): void {
        const codes: string[] = this.editingFormGroup.controls["documentsList"].value;
        this.internalValue = codes;
        this.retrieveDocsMetaData();
        this.onChange(this.internalValue);
        this.openedSearchDocumentsWindows = false;
    }

}