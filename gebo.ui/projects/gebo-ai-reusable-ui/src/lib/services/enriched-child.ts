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
 * This module provides interfaces and utility functions for handling enriched file system items
 * and child elements in the Gebo.ai application. It includes functionality for determining
 * types, extracting information, and selecting appropriate icons for UI representation.
 */

import { VDocumentInfo, VFolderInfo } from "@Gebo.ai/gebo-ai-rest-api";

/**
 * Interface representing an enriched child element with additional metadata
 * used for UI representation and hierarchical organization.
 */
export interface EnrichedChild {
    info: { code?: string; description?: string; parentProjectCode?: string; };
    isProject: boolean;
    isProjectEndpoint:boolean; 
    isLeaf: boolean;
    icon:string;
    className?:string;
}

/**
 * Extracts the target type from an EnrichedChild object by parsing the className property.
 * @param data The EnrichedChild object to extract the target type from
 * @returns The extracted target type as a string, or an empty string if className is not defined
 */
export function extractTargetType(data: EnrichedChild): string {
    if (data.className) {
      const lastIndex = data.className?.lastIndexOf(".");
      if (lastIndex && lastIndex > 0) {
        return data.className?.substring(lastIndex+1);
      }
      return data.className;
    }
    return "";
  }

/**
 * Determines if an item is a project endpoint
 * @param item The EnrichedChild to check
 * @returns Boolean indicating if the item is a project endpoint
 */
export function isProjectEndpoint(item: EnrichedChild):boolean {
    return item.isProjectEndpoint===true;
 }

/**
 * Interface representing an enriched virtual filesystem item with additional metadata.
 * Generic type parameter T allows for different info types (VFolderInfo or VDocumentInfo).
 */
export interface EnrichedVFilesystemItem<T> {
    info:T;
    isLeaf: boolean;
    isVirtualFolder:boolean;
    isVirtualFile:boolean;
    uiViewable?:boolean;
    treatAs?:string;
    fileTypeId?:string;
    programmingLanguage?:string;
    className?:string;
    projectEndpointCode?:string;
}

/**
 * Determines the appropriate icon to display for an EnrichedChild object in the UI.
 * @param child The EnrichedChild to get an icon for
 * @returns A string representing the icon class to use
 */
export function getNodeIcon(child: EnrichedChild): string {
    if (child.icon) return child.icon
    /*"pi pi-github";
    if (child.isProject === true) return "pi pi-list-check";
    if (child.isFileEndpoint === true) return "pi pi-folder";
    if (child.isUploadEndpoint === true) return "pi pi-cloud-upload";
    if (child.isConfluenceEndpoint===true) return "pi pi-globe";
    if (child.isSharepointEndpoint===true) return "pi pi-globe";
    if (child.isWebcrawlerEndpoint === true) return "pi pi-cloud";
    if (child.isKnowledgeBase===true) return "pi pi-sitemap";
    if (child.isWiteableOutputEndpoint===true) return "pi  pi-android";
    if (child.isDatabaseEndpoint===true) return "pi pi-database";*/
    return "pi pi-unkown";
}

/**
 * Determines the appropriate icon to display for a virtual filesystem item in the UI.
 * Selects icons based on file type, treatAs property, or item type.
 * 
 * @param child The EnrichedVFilesystemItem to get an icon for
 * @returns A string representing the icon class to use
 */
export function getVFSIcon(child:EnrichedVFilesystemItem<VFolderInfo>|EnrichedVFilesystemItem<VDocumentInfo>):string {

    if (child.treatAs==='sourceCode') return "pi pi-hashtag";
    if (child.treatAs==='word') return "pi pi-file-word";
    if (child.treatAs==='excel') return "pi pi-file-excel"
    if (child.treatAs==='pdf') return "pi pi-file-pdf"
    if (child.isVirtualFile===true) return "pi pi-file";
    if (child.isVirtualFolder===true) return "pi pi-folder";
    if (child.uiViewable===false) {
        return "pi pi-cloud-download";
    }
    // pi-file-word pi-file-excel pi-file-pdf pi-cloud-download pi-hashtag
    return "pi pi-unkown";
}