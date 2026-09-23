/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { GUserMessage } from "@Gebo.ai/gebo-ai-rest-api";
/**
 * @fileoverview Provides utilities for handling operation status and error checking
 * AI generated comments
 */

/**
 * Interface representing the status of an operation with generic result type.
 * This interface allows tracking both the operation result and any associated messages.
 */
export interface IOperationStatus<T> {
    /** The result of the operation, if available */
    result?:T;
    /** List of user messages related to the operation, such as errors, warnings, or info messages */
    messages?:GUserMessage[];
    hasWarnMessages?: boolean;
    hasErrorMessages?: boolean;
}

/**
 * Determines if an operation status contains any error messages.
 * This function checks the messages array of the provided operation status to find any
 * messages with "error" severity.
 * 
 * @param os - The operation status object to check for errors
 * @returns A boolean indicating whether any error messages were found (true) or not (false)
 */
export function errorStatus<T>(os?:IOperationStatus<T> ):boolean {
    let op:boolean=false;
    // Filter messages to find those with error severity
    const erroneusEntries=os?.messages?.filter(x=>x.severity==="error");
    // Set result to true if there are any error messages
    op=erroneusEntries && erroneusEntries.length>0?true:false;
    return op;
}