/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { Injectable } from "@angular/core";
import { ContentMetaInfosControllerService, DocumentReferenceView, GeboUserKnowledgeBaseSemanticSearchControllerService, IngestionFileType, IngestionFileTypesLibraryControllerService, SearchDocumentByNameParam, SemanticQueryParam } from "@Gebo.ai/gebo-ai-rest-api";
import { forkJoin, map, Observable } from "rxjs";
/**
 * AI generated comments
 * This file provides services for retrieving and enriching document references with additional metadata
 * like file type information and appropriate icons for UI rendering.
 */

/**
 * Interface that extends IngestionFileType to include an icon property for UI rendering.
 * This allows file types to be visually represented in the interface.
 */
export interface EnrichedIngestionFileType extends IngestionFileType {
    icon: string;
}

/**
 * Interface that extends DocumentReferenceView to include file type information.
 * This enriched version provides additional metadata about the document's file type.
 */
export interface EnrichedDocumentReferenceView extends DocumentReferenceView {
    fileType?: EnrichedIngestionFileType;

}

/**
 * Service responsible for retrieving document references and enriching them with additional metadata.
 * This service caches file types and provides methods to search documents by name or code,
 * as well as semantic search capabilities.
 */
@Injectable({
    providedIn: "root"
})
export class EnrichedDocumentReferenceViewRetrieveService {

    /**
     * Static cache of file types to avoid repeated API calls
     */
    private static fileTypes: EnrichedIngestionFileType[] = [];

    /**
     * Constructor that injects necessary services for document retrieval and enrichment
     * 
     * @param filetypesControllerService Service for retrieving file type information
     * @param userKnowledgeBaseSemanticSearchService Service for semantic searching
     * @param documentsMetaInfosService Service for accessing document metadata
     */
    public constructor(
        private filetypesControllerService: IngestionFileTypesLibraryControllerService,
        private userKnowledgeBaseSemanticSearchService: GeboUserKnowledgeBaseSemanticSearchControllerService,
        private documentsMetaInfosService: ContentMetaInfosControllerService) {
    }

    /**
     * Determines the appropriate icon to display based on the file type's treatment category
     * 
     * @param child The file type to get an icon for
     * @returns A string representing the PrimeNG icon class to use
     */
    private getFileIcon(child: IngestionFileType): string {

        if (child.treatAs === 'sourceCode') return "pi pi-hashtag";
        if (child.treatAs === 'word') return "pi pi-file-word";
        if (child.treatAs === 'excel') return "pi pi-file-excel"
        if (child.treatAs === 'pdf') return "pi pi-file-pdf"

        if (child.uiViewable === false) {
            return "pi pi-cloud-download";
        }

        return "pi pi-unkown";
    }

    /**
     * Retrieves the enriched file type information based on file extension
     * 
     * @param extension The file extension to look up
     * @returns The matching EnrichedIngestionFileType or undefined if not found
     */
    private static getEnrichedFileType(extension?: string): EnrichedIngestionFileType | undefined {
        if (!extension) return undefined;
        return EnrichedDocumentReferenceViewRetrieveService.fileTypes.find(x => x.extensions?.find(e => e === extension) ? true : false);
    }

    /**
     * Enriches a DocumentReferenceView with file type information
     * 
     * @param d The document reference to enrich
     * @returns An EnrichedDocumentReferenceView with file type data
     */
    private static enrich(d: DocumentReferenceView): EnrichedDocumentReferenceView {
        const object: EnrichedDocumentReferenceView = d;
        object.fileType = this.getEnrichedFileType(object.extension);
        return object;
    }

    /**
     * Searches for documents by name and enriches the results with file type information
     * 
     * @param param The search parameters
     * @returns An Observable of enriched document reference views
     */
    public searchByDocumentName(param: SearchDocumentByNameParam): Observable<EnrichedDocumentReferenceView[]> {
        const searchObservable: Observable<Array<DocumentReferenceView>> = this.documentsMetaInfosService.searchByDocumentName(param);
        if (EnrichedDocumentReferenceViewRetrieveService.fileTypes.length) {
            return searchObservable.pipe(map(r => r.map(EnrichedDocumentReferenceViewRetrieveService.enrich)));
        } else {
            const observables: [Observable<IngestionFileType[]>, Observable<DocumentReferenceView[]>] = [this.filetypesControllerService.getAllFileTypes(), searchObservable];
            return forkJoin(observables).pipe(map((rVector) => {
                EnrichedDocumentReferenceViewRetrieveService.fileTypes = rVector[0] as EnrichedIngestionFileType[];
                return rVector[1].map(EnrichedDocumentReferenceViewRetrieveService.enrich);
            }));
        }

    }

    /**
     * Finds document references by their internal codes and enriches them with file type information
     * 
     * @param internalValue Array of internal codes to search for
     * @returns An Observable of enriched document reference views
     */
    public findDocumentReferenceViewByCode(internalValue: string[]): Observable<EnrichedDocumentReferenceView[]> {
        if (EnrichedDocumentReferenceViewRetrieveService.fileTypes.length) {
            return this.documentsMetaInfosService.findDocumentReferenceViewByCode(internalValue).pipe(map(r => {
                const out: EnrichedDocumentReferenceView[] = r as EnrichedDocumentReferenceView[];
                out.forEach(entry => {
                    entry.fileType = EnrichedDocumentReferenceViewRetrieveService.getEnrichedFileType(entry.extension)
                });
                return out;
            }));
        } else {
            const observables: [Observable<IngestionFileType[]>, Observable<DocumentReferenceView[]>] = [this.filetypesControllerService.getAllFileTypes(), this.documentsMetaInfosService.findDocumentReferenceViewByCode(internalValue)];
            return forkJoin(observables).pipe(map((rVector) => {
                EnrichedDocumentReferenceViewRetrieveService.fileTypes = rVector[0] as EnrichedIngestionFileType[];
                EnrichedDocumentReferenceViewRetrieveService.fileTypes.forEach(entry => {
                    entry.icon = this.getFileIcon(entry);
                });
                const out: EnrichedDocumentReferenceView[] = rVector[1] as EnrichedDocumentReferenceView[];
                out.forEach(entry => {
                    entry.fileType = EnrichedDocumentReferenceViewRetrieveService.getEnrichedFileType(entry.extension)
                });
                return out;
            }))
        }
    }

    /**
     * Performs a semantic search using the provided query parameters
     * 
     * @param body The semantic query parameters
     * @returns An Observable with the semantic search results
     */
    public semanticSearch(body: SemanticQueryParam) {
        return this.userKnowledgeBaseSemanticSearchService.semanticSearch(body)
    }
}