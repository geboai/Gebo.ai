/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * @file browse-content.module.ts
 * @description AI generated comments
 * This module provides components for browsing and viewing content in the application.
 * It sets up the necessary dependencies and exports components that allow users to browse
 * content and view HTML content in a structured way. The module leverages PrimeNG's panel
 * component for UI presentation.
 */

import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { PanelModule } from "primeng/panel";
import { BrowseContentComponent } from "./browse-content.component";
import { GeboAIViewHtmlComponent } from "./view-html-content.component";

/**
 * @NgModule BrowseContentModule
 * @description Angular module that encapsulates content browsing functionality.
 * This module imports common Angular modules and PrimeNG's Panel module for UI components.
 * It declares and exports two main components:
 * - BrowseContentComponent: Likely handles the main content browsing interface
 * - GeboAIViewHtmlComponent: Specialized component for viewing HTML content
 * 
 * Note that BrowserModule is imported but not used in the imports array, which might 
 * indicate it's meant for a different module or is a leftover import.
 */
@NgModule({
    imports:[CommonModule,PanelModule],
    declarations:[BrowseContentComponent,GeboAIViewHtmlComponent],
    exports:[BrowseContentComponent,GeboAIViewHtmlComponent]
})
export class BrowseContentModule {}