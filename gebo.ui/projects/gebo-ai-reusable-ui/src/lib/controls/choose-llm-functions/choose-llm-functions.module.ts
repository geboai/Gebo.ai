/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




/**
 * Module for handling LLM function selection in GeboAI.
 * AI generated comments
 * 
 * This module consolidates all necessary dependencies for the GeboAIChooseLLMFunctionscomponent,
 * which allows users to select and configure LLM (Large Language Model) functions.
 * It imports various Angular and PrimeNG modules to provide form handling, UI components,
 * and tree selection functionality needed for the component.
 */
import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { GeboAIChooseLLMFunctionscomponent } from "./choose-llm-functions.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { PanelModule } from "primeng/panel";
import { BlockUIModule } from "primeng/blockui";
import { TreeSelectModule } from 'primeng/treeselect';
import { GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";

/**
 * Module that encapsulates the GeboAIChooseLLMFunctionscomponent.
 * 
 * This module imports essential UI libraries for form handling (FormsModule, ReactiveFormsModule),
 * PrimeNG components (PanelModule, BlockUIModule, TreeSelectModule) for the UI interface,
 * and the standard CommonModule for Angular directives.
 * It declares and exports the GeboAIChooseLLMFunctionscomponent so it can be used in other modules.
 */
@NgModule({
    imports: [CommonModule, TreeSelectModule, FormsModule, ReactiveFormsModule, PanelModule, BlockUIModule],
    declarations: [GeboAIChooseLLMFunctionscomponent],
    exports: [GeboAIChooseLLMFunctionscomponent],
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAIChooseLLMFunctionsModule", multi: false }]

})
export class GeboAIChooseLLMFunctionsModule { }