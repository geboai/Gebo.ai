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
 * This file provides services for validating and checking connectivity to SharePoint URLs.
 * It leverages the Angular HttpClient for connectivity checks and provides interfaces
 * for representing validation and connectivity check results.
 */

import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { GSharepointContentManagementSystem } from "@Gebo.ai/gebo-ai-rest-api";

/**
 * Interface representing the result of SharePoint URL validation.
 * Includes a flag indicating completion status and an optional sanitized URL.
 */
export interface SharepointValidatedUrl { complete: boolean; sanitizedUrl?: string; };

/**
 * Interface representing the result of a SharePoint connectivity check.
 * Contains a boolean flag indicating whether connectivity is successful.
 */
export interface SharepointConnectivityCheck {connectivityOk:boolean;};

/**
 * Service responsible for SharePoint URL validation and connectivity testing.
 * This injectable service is provided at the root level and can be used throughout the application.
 */
@Injectable({ providedIn: "root" })
export class SharepointUrlService {
    /**
     * Creates an instance of SharepointUrlService.
     * @param httpClient Angular's HttpClient for making HTTP requests
     */
    constructor(private httpClient:HttpClient) {

    }

    /**
     * Validates and sanitizes a SharePoint URL.
     * This method attempts to construct a valid URL from the input, extracting
     * the protocol, host, and port components while discarding any path or query parameters.
     * 
     * @param baseUrl The original SharePoint URL to validate
     * @param sharepointVersion The version of SharePoint being used
     * @returns A SharepointValidatedUrl object containing validation status and sanitized URL
     */
    public checkUrl(baseUrl: string, sharepointVersion: GSharepointContentManagementSystem.SharepointVersionEnum): SharepointValidatedUrl {
        const out: SharepointValidatedUrl = {
            complete: false
        };
        if (baseUrl) {
            try {
                const url: URL = new URL(baseUrl);
                const protocol=url.protocol;
                const host=url.host;
                const pathname=url.pathname;
                const port=url.port?url.port:"";
                if (protocol && host && port!=undefined) {
                    const reconstructedUrl=protocol+"://"+host+ (port?":"+port:"");
                    out.complete=true;
                    out.sanitizedUrl=reconstructedUrl;
                }
                
            } catch (e) {
                // Silent catch - returns incomplete status if URL parsing fails
            }
        }
        return out;
    }

    /**
     * Checks connectivity to a SharePoint server using the provided credentials.
     * This method currently returns a hardcoded successful result.
     * 
     * @param baseUrl The SharePoint URL to check connectivity against
     * @param sharepointVersion The version of SharePoint being used
     * @param uid User ID for SharePoint authentication
     * @param password Password for SharePoint authentication
     * @returns A SharepointConnectivityCheck object indicating connectivity status
     */
    public checkConnectivity(baseUrl:string, sharepointVersion: GSharepointContentManagementSystem.SharepointVersionEnum,uid:string,password:string):SharepointConnectivityCheck {
        const out:SharepointConnectivityCheck={connectivityOk:true};
        
        return out;
    }
}