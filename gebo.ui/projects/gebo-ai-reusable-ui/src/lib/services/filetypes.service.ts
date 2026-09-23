/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { Injectable } from "@angular/core";
import { IngestionFileTypesLibraryControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { map, Observable, of } from "rxjs";

/**
 * AI generated comments
 * Interface representing a file type in the Gebo.ai system.
 * Defines the structure for file type information with a code identifier,
 * description, and optional treatAs property to indicate how the file should be processed.
 */
export interface GeboAIFileType {
        code:string;        // Unique identifier for the file type (typically the extension)
        description:string; // Human-readable description of the file type
        treatAs?:string;    // Optional property indicating how to process this file type
}

/**
 * Service responsible for managing and retrieving file types in the Gebo.ai application.
 * This service caches file types after the first retrieval to improve performance
 * on subsequent calls. It transforms the API response into a simplified GeboAIFileType format.
 */
@Injectable({
    providedIn:"root"
})
export class GeboAIFileTypeService {
    /**
     * Static cache of file types to avoid redundant API calls
     */
    private static types:GeboAIFileType[]=[];

    /**
     * Initializes the file type service with the controller service for API access
     * @param filetypesControllerService Service used to make API requests for file types
     */
    constructor(private filetypesControllerService: IngestionFileTypesLibraryControllerService) {

    }

    /**
     * Retrieves all available file types, either from cache or from the API.
     * On first call, it fetches data from the API, processes it to extract individual
     * file extensions, and populates the cache. Subsequent calls return the cached data.
     * 
     * @returns Observable emitting an array of GeboAIFileType objects
     */
    public getAllFileTypes():Observable<GeboAIFileType[]> {
        if (GeboAIFileTypeService.types.length===0) {
            return this.filetypesControllerService.getAllFileTypes().pipe(map(inputValue=>{
                const types:GeboAIFileType[]=[];
                if (inputValue) {
                    inputValue.forEach(x=>{
                        if (x.extensions) {
                            x.extensions.forEach(extension=>{
                                types.push({
                                    code: extension,                          // Use extension as the code
                                    description: extension+ " "+ x.description, // Combine extension with description
                                    treatAs: x.treatAs                        // Pass through the treatAs property
                                    
                                });
                            })
                        }
                    });
                }
                GeboAIFileTypeService.types=types;
                return GeboAIFileTypeService.types;
            }))
        }
        // Return cached data if available
        return of(GeboAIFileTypeService.types);
    }
}