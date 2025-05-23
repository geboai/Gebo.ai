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
 * Validates if a string value is considered valid (not undefined, not null, and truthy).
 * 
 * @param s - The string value to validate
 * @returns true if the string is valid, false otherwise
 */
function isValidValue(s: string): boolean {
    return s !== undefined && s !== null && s ? true : false;
}

/**
 * Validates if a string represents a valid URL by checking if it can be parsed
 * into a URL object with valid host, protocol, and port properties.
 * 
 * @param baseUrl - The URL string to validate
 * @returns true if the URL is valid, false otherwise
 */
export function isValidUrl(baseUrl: string): boolean {
    try {
        const url = new URL(baseUrl);
        return url && isValidValue(url.host) && isValidValue(url.protocol) && isValidValue(url.port) ? true : false;
    } catch (e) {
        return false;
    }
}