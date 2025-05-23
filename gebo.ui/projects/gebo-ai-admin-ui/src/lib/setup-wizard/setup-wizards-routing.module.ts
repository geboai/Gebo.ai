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
 * This file defines the routing configuration for the GeboAi setup wizard feature.
 * It creates routes for the setup wizard component and provides a routing module
 * that can be imported by the application.
 */

import { Route, RouterModule, Routes } from "@angular/router";

import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { GeboSetupWizardsModule } from "./setup-wizards.module";
import { SetupWizardsComponent } from "./setup-wizards.component";

/**
 * Routes configuration for the setup wizard feature.
 * Defines two routes:
 * 1. Empty path that loads the SetupWizardsComponent
 * 2. A parameterized route with stepId parameter to navigate to specific wizard steps
 */
const routes:Routes=[
    {
        path:'',
        component:SetupWizardsComponent,
        pathMatch:'full'
    },
    {
        path:'step/{stepId}',
        component:SetupWizardsComponent,
        pathMatch:'full'
    }
];

/**
 * NgModule that provides routing functionality for the GeboAi setup wizard feature.
 * This module imports CommonModule for common Angular directives,
 * GeboSetupWizardsModule for the wizard components, and configures 
 * child routes using RouterModule.forChild.
 */
@NgModule({
    imports:[CommonModule,GeboSetupWizardsModule,RouterModule.forChild(routes)]
})
export class GeboAiSetupRoutingModule {

}