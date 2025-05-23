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
 * @file translable.module.ts
 * This module provides functionality for translation within an Angular application.
 * It wraps the GeboAITranslableDirective which likely handles text translation
 * functionality and makes it available for use in other parts of the application.
 */

import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { GeboAITranslableDirective } from "./translable.directive";

/**
 * Angular module that provides translation capabilities to components.
 * This module imports common Angular modules needed for forms processing
 * and declares and exports the GeboAITranslableDirective, making it
 * available for use in any component that imports this module.
 */
@NgModule({
    imports:[CommonModule,ReactiveFormsModule,FormsModule],
    declarations:[GeboAITranslableDirective],
    exports:[GeboAITranslableDirective]

})
export class TranslableModule {}