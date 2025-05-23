/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { GVirtualFilesystemRoot, PathInfo, BrowseParam, GUserMessage, VirtualFilesystemNavigationTreeStatus } from "@Gebo.ai/gebo-ai-rest-api";
import { IOperationStatus } from "@Gebo.ai/reusable-ui";
import { Observable } from "rxjs";
/**
 * AI generated comments
 * This file defines interfaces and types for the virtual filesystem in Gebo.ai.
 * It includes data structures for filesystem roots, path information, and navigation.
 */

/**
 * Interface representing the response when retrieving virtual filesystem roots.
 * Contains an optional array of filesystem roots and any associated messages.
 */
export interface VFilesystemRootsResponse {
    result?: Array<GVirtualFilesystemRoot>;
    messages?: Array<GUserMessage>;
};

/**
 * Interface representing path information listing from the virtual filesystem.
 * Contains an optional array of path information objects and any associated messages.
 */
export interface VFilesystemListPathInfo { 
    result?: Array<PathInfo>;
    messages?: Array<GUserMessage>;
};

/**
 * Interface representing a reference to a location in the virtual filesystem.
 * Consists of a root filesystem object and an optional path information object.
 */
export interface VFilesystemReference {
    root: GVirtualFilesystemRoot;
    path?: PathInfo;
}

/**
 * Type definition for a callback function that returns an Observable for loading
 * filesystem roots, with no parameters required.
 */
export type loadRootsObservableCallback = () => Observable<VFilesystemRootsResponse>;

/**
 * Type definition for a callback function that returns an Observable for browsing
 * a specific path within the filesystem, requiring a BrowseParam as input.
 */
export type browsePathObservableCallback = (param: BrowseParam) => Observable<VFilesystemListPathInfo>;

/**
 * Type definition for a callback function that returns an Observable for reconstructing
 * the navigation tree from a set of reference points in the filesystem.
 * Takes an array of VFilesystemReference objects as input.
 */
export type reconstructNavigationObservableCallback = (navigationPoints:VFilesystemReference[])=> Observable<IOperationStatus<VirtualFilesystemNavigationTreeStatus[]>>;