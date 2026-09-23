/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { GKnowledgeBase } from "@Gebo.ai/gebo-ai-rest-api";

/***
 * AI generated comments
 * Checks to avoid knowledgebase loops
 * 
 * This function filters out knowledge bases that would create loops if referenced by the provided entity.
 * It examines relationships between knowledge bases to prevent circular dependencies.
 * 
 * @param data - Array of knowledge bases to filter
 * @param entity - Optional knowledge base that is being edited/referenced
 * @returns Filtered array of knowledge bases that won't create loops when referenced
 */
export function filterAvodingKBLoops(data: GKnowledgeBase[], entity?: GKnowledgeBase): GKnowledgeBase[] {
    const outdata: GKnowledgeBase[] = [];
    // Filter out the current entity if it exists
    const filterExcludingEntity:GKnowledgeBase[] = entity?.code?data.filter(x=>x.code!==entity.code):data;
    let badList:GKnowledgeBase[] =[];
    if (entity && entity.code) {
        // Get list of knowledge bases that would create loops
        badList = followPotentialKBLoops(entity, filterExcludingEntity);        
    }
    // Only include candidates that aren't in the bad list
    filterExcludingEntity.forEach(candidate => {
        if (!badList.find(x => x.code === candidate.code)) {
            outdata.push(candidate);
        }
    });
    return outdata;
}

/***
 * Check if other set as reference in editingOne will create a loop
 * 
 * This function determines if two knowledge bases have a direct circular reference relationship.
 * It checks if the 'other' knowledge base references the 'editingOne' knowledge base.
 * 
 * @param editingOne - The knowledge base being edited/examined
 * @param other - Another knowledge base to check for potential loops
 * @returns Boolean indicating whether a direct loop would be created
 */
export function arePotentialDirectKBLoop(editingOne: GKnowledgeBase, other: GKnowledgeBase): boolean {
    let related: boolean = false;
    related =  (other.knowledgeBaseReferences?.find(y => y === editingOne.code) ? true : false);
    return related;
}

/*****
 * Groups set of KB being in an indirect loop
 * 
 * This function identifies all knowledge bases that would create direct or indirect loops
 * with the knowledge base being edited. It performs iterative analysis to find all potential
 * circular references through transitive relationships.
 * 
 * @param editingOne - The knowledge base being edited/examined
 * @param others - Array of other knowledge bases to check for potential loops
 * @returns Array of knowledge bases that would create direct or indirect loops
 */
export function followPotentialKBLoops(editingOne: GKnowledgeBase, others: GKnowledgeBase[]): GKnowledgeBase[] {
    const outList: GKnowledgeBase[] = [];
    //add first those nodes in direct loop
    others.forEach(other => {
        if (arePotentialDirectKBLoop(editingOne, other)) {
            outList.push(other);
        }
    });
    let actualLength = 0;
    do {
        //check if items in outlist are potential direct loop with
        //others 
        actualLength = outList.length;
        const toAdd: GKnowledgeBase[] = [];
        others.forEach(other => {
            outList.forEach(inOutList => {
                if (arePotentialDirectKBLoop(other, inOutList) || arePotentialDirectKBLoop(inOutList, other)) {
                    toAdd.push(other);
                }
            });
        });
        toAdd.forEach(entry => {
            outList.push(entry);
        });
    } while (actualLength != outList.length && outList.length<others.length);
    return outList;
}