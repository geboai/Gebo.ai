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
 * This file implements a search component for the Gebo.ai application that allows users to search for documents
 * across various knowledge bases using different search methods including semantic search, filename search, 
 * and directory browsing.
 */

import { Component, EventEmitter, forwardRef, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup, NG_VALUE_ACCESSOR } from "@angular/forms";
import { BrowseParam, DocumentReferenceView, SearchDocumentByNameParam, SemanticQueryParam, UserKnowledgeBaseBrowsingControllerService, VFilesystemReference, VirtualFilesystemNavigationTreeStatus } from "@Gebo.ai/gebo-ai-rest-api";

import { of } from "rxjs";
import { EnrichedDocumentReferenceView, EnrichedDocumentReferenceViewRetrieveService } from "../content-viewer/enriched-document-reference-view.service";
import { browsePathObservableCallback, loadRootsObservableCallback, reconstructNavigationObservableCallback } from "../vfilesystem-selector/vfilesystem-types";
import { IOperationStatus } from "../base-entity-editing-component/operation-status";
import { GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboAIFieldHost } from "../field-host-component-iface/field-host-component-iface";

/**
 * Component that provides document searching functionality through multiple methods:
 * - Semantic search (natural language queries)
 * - Filename search
 * - Directory browsing
 * - Userspace file selection
 *
 * Implements ControlValueAccessor to work as a form control and supports multi-selection of documents.
 */
@Component({
    selector: "gebo-ai-search-documents-component",
    templateUrl: "search-documents.component.html", providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => GeboAISearchDocumentsComponent),
            multi: true
        }, { provide: GEBO_AI_MODULE, useValue: "GeboAIChooseDocumentsPanelModule", multi: false }, {
            provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAISearchDocumentsComponent),
            multi: false
        }
    ],
    standalone: false
})
export class GeboAISearchDocumentsComponent implements OnInit, OnChanges, GeboAIFieldHost {

    /** Knowledge base codes to search within */
    @Input() knowledgeBaseCodes?: string[];

    /** Flag indicating if documents are currently being loaded */
    loadingDocuments: boolean = false;

    /** Flag indicating if semantic search is currently running */
    runningSemanticSearch: boolean = false;

    /** Flag indicating if filename search is currently running */
    runningFileNameSearch: boolean = false;

    /** Observable callback to load root directories */
    loadRootsObservable: loadRootsObservableCallback = () => of({});

    /** Observable callback to browse a specific path */
    browsePathObservable: browsePathObservableCallback = (param: BrowseParam) => of({});

    /** Observable callback to reconstruct navigation points */
    reconstructNavigationObservableCallback: reconstructNavigationObservableCallback = (navigationPoints: VFilesystemReference[]) => {
        const out: IOperationStatus<VirtualFilesystemNavigationTreeStatus[]> = {};
        return of(out);
    };

    /** Form group for browsing documents */
    browseFormGroup: FormGroup = new FormGroup({
        choosenBrowsing: new FormControl()
    });

    /** Form group for semantic search */
    semanticSearchFormGroup: FormGroup = new FormGroup({
        query: new FormControl(),
        knowledgeBaseCodes: new FormControl(),
        topK: new FormControl()
    });

    /** Form group for filename search */
    fileNameSearchFormGroup: FormGroup = new FormGroup({
        filename: new FormControl(),
        knowledgeBaseCodes: new FormControl(),
    });

    /** Form group for userspace files */
    userspaceFilesFormGroup: FormGroup = new FormGroup({
        userspaceFiles: new FormControl()
    });

    /** Form group for output documents */
    outFormGroup: FormGroup = new FormGroup({
        choosedFiles: new FormControl()
    });

    /** Master list of all documents found from various search methods */
    sourceDocuments: EnrichedDocumentReferenceView[] = [];

    /** Documents found via filename search */
    searchByFileNameResultsDocuments: EnrichedDocumentReferenceView[] = [];

    /** Documents found via semantic search */
    semanticSearchResultsDocuments: EnrichedDocumentReferenceView[] = [];

    /** Documents found via directory browsing */
    browsingResultsDocuments: EnrichedDocumentReferenceView[] = [];

    /** Documents found in user workspace */
    userspaceResultsDocuments: EnrichedDocumentReferenceView[] = [];

    /** Final selected documents */
    outputDocuments: EnrichedDocumentReferenceView[] = [];

    /** Event emitter for closing the component */
    @Output() closeMe: EventEmitter<boolean> = new EventEmitter();

    /** Event emitter for when document selection is confirmed */
    @Output() confirmed: EventEmitter<boolean> = new EventEmitter();

    /**
     * Constructor initializes services needed for document searching
     * 
     * @param userKnowledgeBaseBrowsing Service for browsing knowledge base structure
     * @param enrichedDocumentsMetaInfosService Service for retrieving document metadata
     */
    public constructor(
        private userKnowledgeBaseBrowsing: UserKnowledgeBaseBrowsingControllerService,
        private enrichedDocumentsMetaInfosService: EnrichedDocumentReferenceViewRetrieveService) {
    }
    getEntityName(): string {
        return "GeboAISearchDocumentsComponent";
    }
    /**
     * Determines if knowledge bases are available for searching
     * 
     * @returns True if knowledge bases are available, false otherwise
     */
    public get hasKnowledgeBases(): boolean {
        return this.knowledgeBaseCodes && this.knowledgeBaseCodes.length ? true : false;
    }

    /**
     * Retrieves metadata for documents in the main panel based on their codes
     * Updates the outputDocuments array with enriched document information
     */
    private retrieveMainPanelDocsMetaData(): void {
        if (this.internalValue && this.internalValue.length) {
            this.loadingDocuments = true;
            this.enrichedDocumentsMetaInfosService.findDocumentReferenceViewByCode(this.internalValue).subscribe({
                next: (docs) => {
                    this.outputDocuments = docs;
                    this.addSourceDocuments(docs);
                },
                complete: () => {
                    this.loadingDocuments = false;
                }
            });
        } else {
            this.outputDocuments = [];
        }
    }

    /** Internal storage for selected document codes */
    private internalValue: string[] = [];

    /**
     * Implements ControlValueAccessor.writeValue
     * Sets the internal value and retrieves document metadata
     * 
     * @param obj Array of document codes
     */
    writeValue(obj: any): void {
        this.internalValue = obj;
        this.retrieveMainPanelDocsMetaData();
    }

    /** Function called when value changes */
    private onChange: (v: any) => void = (v: any) => { };

    /** Function called when control is touched */
    private onTouch: (v: any) => void = (v: any) => { };

    /**
     * Implements ControlValueAccessor.registerOnChange
     * 
     * @param fn Change handler function
     */
    registerOnChange(fn: any): void {
        this.onChange = fn;
    }

    /**
     * Implements ControlValueAccessor.registerOnTouched
     * 
     * @param fn Touch handler function
     */
    registerOnTouched(fn: any): void {
        this.onTouch = fn;
    }

    /**
     * Implements ControlValueAccessor.setDisabledState
     * 
     * @param isDisabled Whether the control should be disabled
     */
    setDisabledState?(isDisabled: boolean): void {
    }

    /**
     * Initializes the component and sets up form value change subscriptions
     * for browsing and userspace files
     */
    ngOnInit(): void {
        this.browseFormGroup.controls["choosenBrowsing"].valueChanges.subscribe({
            next: (browsedResult: VFilesystemReference[]) => {
                if (browsedResult && browsedResult.length) {
                    const filesList: string[] = browsedResult.map((value: VFilesystemReference) => {
                        let outValue: string = "";
                        if (value.path && value.path.absolutePath && value.path.absolutePath.indexOf("document:") >= 0) {
                            const startIndex = value.path.absolutePath.indexOf("document:");
                            const endIndex = startIndex + "document:".length;
                            outValue = value.path.absolutePath.substring(endIndex);
                        }
                        return outValue;
                    }).filter(x => x);
                    this.loadingDocuments = true;
                    this.enrichedDocumentsMetaInfosService.findDocumentReferenceViewByCode(filesList).subscribe({
                        next: (documentList) => {
                            this.browsingResultsDocuments = documentList;
                            this.addSourceDocuments(this.browsingResultsDocuments);
                        },
                        complete: () => {
                            this.loadingDocuments = false;
                        }
                    });
                }
            }

        });
        this.userspaceFilesFormGroup.controls["userspaceFiles"].valueChanges.subscribe(
            {
                next: (filesList: string[]) => {
                    this.loadingDocuments = true;
                    this.enrichedDocumentsMetaInfosService.findDocumentReferenceViewByCode(filesList).subscribe({
                        next: (documentList) => {
                            this.userspaceResultsDocuments = documentList;
                            this.addSourceDocuments(this.userspaceResultsDocuments);
                        },
                        complete: () => {
                            this.loadingDocuments = false;
                        }
                    });
                }
            }
        );
    }

    /**
     * Handles changes to component inputs, particularly knowledgeBaseCodes
     * Updates form controls and observable callbacks when knowledge base codes change
     * 
     * @param changes SimpleChanges object containing changed properties
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.knowledgeBaseCodes && changes["knowledgeBaseCodes"]) {
            this.semanticSearchFormGroup.controls["knowledgeBaseCodes"].setValue(this.knowledgeBaseCodes);
            this.fileNameSearchFormGroup.controls["knowledgeBaseCodes"].setValue(this.knowledgeBaseCodes);
            const kb = this.knowledgeBaseCodes;
            this.loadRootsObservable = () => {
                return this.userKnowledgeBaseBrowsing.getKnowledgeBaseRoots(kb);
            }
            this.browsePathObservable = (param: BrowseParam) => {
                return this.userKnowledgeBaseBrowsing.browseKnowledgeBasePath(param, kb);
            }
            this.reconstructNavigationObservableCallback = (navigationPoints: VFilesystemReference[]) => {
                return this.userKnowledgeBaseBrowsing.getKnowledgeBaseNavigationStatus(navigationPoints, kb);
            };
        }
    }

    /**
     * Executes a semantic search based on the query in the semantic search form
     * Updates semanticSearchResultsDocuments with the results
     */
    doSemanticSearch() {
        const fgValue: SemanticQueryParam = this.semanticSearchFormGroup.value;
        if (fgValue.query && fgValue.knowledgeBaseCodes) {
            this.runningSemanticSearch = true;
            this.enrichedDocumentsMetaInfosService.semanticSearch(fgValue).subscribe({
                next: (values) => {
                    this.loadingDocuments = true;
                    this.enrichedDocumentsMetaInfosService.findDocumentReferenceViewByCode(values).subscribe({
                        next: (documentList) => {
                            this.semanticSearchResultsDocuments = documentList;
                            this.addSourceDocuments(this.semanticSearchResultsDocuments);
                        },
                        complete: () => {
                            this.loadingDocuments = false;
                        }
                    });


                },
                complete: () => {
                    this.runningSemanticSearch = false;
                }
            });
        }
    }

    /**
     * Appends document codes to the internal value and refreshes document metadata
     * 
     * @param values Document codes to append
     */
    appendResults(values: string[]) {
        this.internalValue = [...this.internalValue, ...values];
        this.retrieveMainPanelDocsMetaData();
    }

    /**
     * Executes a filename search based on the filename in the search form
     * Updates searchByFileNameResultsDocuments with the results
     */
    doSearchByFileName() {
        const param: SearchDocumentByNameParam = this.fileNameSearchFormGroup.value;
        this.runningFileNameSearch = true;
        this.enrichedDocumentsMetaInfosService.searchByDocumentName(param).subscribe(
            {
                next: (values) => {
                    this.searchByFileNameResultsDocuments = values ? values : [];
                    this.addSourceDocuments(this.searchByFileNameResultsDocuments);
                },
                complete: () => {
                    this.runningSemanticSearch = false;
                }
            }
        );
    }

    /**
     * Adds documents to the source documents collection if they aren't already present
     * Ensures no duplicates are added
     * 
     * @param docsList List of documents to add
     */
    addSourceDocuments(docsList: DocumentReferenceView[]) {
        if (docsList) {
            const additionalCodes: string[] = [];
            const additionalDocs: DocumentReferenceView[] = [];
            docsList.forEach(x => {
                if (x && x.code) {
                    const found = this.sourceDocuments?.find(y => y.code === x.code);
                    if (!found) {
                        additionalCodes.push(x.code);
                        additionalDocs.push(x);
                    }
                }
            });
            if (additionalCodes && additionalCodes.length) {
                if (!this.sourceDocuments) {
                    //this.internalValue=[];
                    this.sourceDocuments = [];
                }
                //this.internalValue=[...this.internalValue,...additionalCodes];
                this.sourceDocuments = [...this.sourceDocuments, ...additionalDocs];
                if (!this.outputDocuments) {
                    this.outputDocuments = [];
                }
                this.outputDocuments = [...this.outputDocuments, ...additionalDocs];
            }
        }
    }

    /**
     * Confirms selected documents and emits them
     * 
     * @param eventList List of selected documents
     */
    confirmDocuments(eventList: DocumentReferenceView[]) {
        const docsList: string[] = eventList.map(x => x.code) as string[];
        this.onChange(docsList);
        this.confirmed.emit(true);
    }

    /**
     * Confirms documents from the pick list and emits the selection
     * Updates the internal value and calls the onChange handler
     */
    confirmDocumentsPickList() {
        if (this.outputDocuments) {
            const docsList: string[] = this.outputDocuments.map(x => x.code) as string[];
            this.internalValue = docsList;
            this.onChange(docsList);
        } else {
            this.internalValue = [];
            this.onChange([]);
        }
        this.confirmed.emit(true);
    }
}