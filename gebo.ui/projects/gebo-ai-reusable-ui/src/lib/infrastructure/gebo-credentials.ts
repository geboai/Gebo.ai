/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




/**
 * @file Authentication utility for Gebo.ai
 * AI generated comments
 * This file provides functions to interact with authentication credentials
 * stored in local storage.
 */

import { AuthResponse, SecurityHeaderData } from "@Gebo.ai/gebo-ai-rest-api";
export const AUTHORIZATION_HEADER: string = "Authorization";
export const AUTHORIZATION_TYPE_HEADER: string = "X-AuthType";
export const AUTHORIZATION_PROVIDER_ID_HEADER: string = "X-Authprovider-id";
export const AUTHORIZATION_TENANT_ID_HEADER: string = "X-tenant-id";

/**
 * Constant used as the key for storing Gebo.ai credentials in local storage
 */
export const geboCredenditalString: string = "gebo.ai.credentials";

/**
 * Retrieves authentication data from local storage
 * 
 * This function attempts to read Gebo.ai credentials from local storage, parse them
 * from JSON into an AuthResponse object, and return them. If no credentials
 * are found in local storage, it returns undefined.
 * 
 * @returns {AuthResponse | undefined} The parsed authentication response or undefined if not found
 */
export function getAuth(): SecurityHeaderData | undefined {
  const value = localStorage.getItem(geboCredenditalString);
  if (value) {
    const r: SecurityHeaderData = JSON.parse(value);
    return r;
  }
  return undefined;
}
/*************************
 * Returns the http standard UI auth httpheader object
 */
export function getAuthHeader(): any {
  const auth = getAuth();
  if (auth && auth?.token) {
    const outValue: any = {};
    outValue[AUTHORIZATION_HEADER] = "Bearer " + auth.token;
    if (auth.authType)
      outValue[AUTHORIZATION_TYPE_HEADER] = auth.authType;
    if (auth.authTenantId)
      outValue[AUTHORIZATION_TENANT_ID_HEADER] = auth.authTenantId;
    if (auth.authTenantId)
      outValue[AUTHORIZATION_PROVIDER_ID_HEADER] = auth.authProviderId;
    return outValue;
  } else return undefined;
}
export function resetAuth() {
  localStorage.removeItem(geboCredenditalString);
}
export function saveAuth(value: SecurityHeaderData) {
  localStorage.setItem(geboCredenditalString, JSON.stringify(value));
}