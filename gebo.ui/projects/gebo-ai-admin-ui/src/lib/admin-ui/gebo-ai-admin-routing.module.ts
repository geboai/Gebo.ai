/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * This file sets up the routing for the GeboAi Admin feature.
 * AI generated comments
 */

import { Route, RouterModule, Routes } from "@angular/router";
import { GeboAiAdminComponent } from "./gebo-ai-admin.component";
import { NgModule } from "@angular/core";
import { GeboAiAdminModule } from "./gebo-ai-admin.module";
import { CommonModule } from "@angular/common";

/**
 * Defines the routes for the GeboAi Admin feature.
 * Currently only contains a single route that maps the empty path
 * to the GeboAiAdminComponent with full path matching.
 */
const routes:Routes=[
    {
        path:'',
        component:GeboAiAdminComponent,
        pathMatch:'full'  // Ensures the path is matched exactly
    }
];

/**
 * Routing module for the GeboAi Admin feature.
 * This module imports required dependencies and configures child routes
 * for the GeboAi Admin feature area.
 */
@NgModule({
    imports:[CommonModule,GeboAiAdminModule,RouterModule.forChild(routes)]
})
export class GeboAiAdminRoutingModule {

}