/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { GeboAiChatSectionComponent } from "./gebo-ai-rag-chat-section.component";
import { GeboAiChatModule } from "./gebo-ai-rag-chat-section.module";

/**
 * AI generated comments
 * 
 * Defines the routing configuration for the Gebo AI Chat feature.
 * This routes array contains a single route definition that maps the empty path ('') 
 * to the GeboAiChatSectionComponent with full path matching.
 */
const routes:Routes=[
    {path:'',
     component: GeboAiChatSectionComponent,
     pathMatch:"full"
    }
];

/**
 * Routing module for the Gebo AI Chat feature.
 * This NgModule imports the main GeboAiChatModule and sets up the child routes
 * using Angular's RouterModule.forChild() method. The module provides the necessary
 * routing configuration to navigate to the chat section component when the base
 * path is accessed.
 */
@NgModule({
    imports:[GeboAiChatModule, RouterModule.forChild(routes)]
    
})
export class GeboAiChatRoutingModule {

}