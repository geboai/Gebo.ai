/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { GeboAIChooseLLMFunctionscomponent } from "./choose-llm-functions.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ButtonModule } from "primeng/button";
import { InputTextModule } from "primeng/inputtext";
import { TooltipModule } from "primeng/tooltip";
import { GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
import { GeboAIFieldTranslationContainerModule } from "../field-translation-container/field-container.module";

@NgModule({
    imports: [CommonModule, FormsModule, ReactiveFormsModule, ButtonModule, InputTextModule, TooltipModule, GeboAIFieldTranslationContainerModule],
    declarations: [GeboAIChooseLLMFunctionscomponent],
    exports: [GeboAIChooseLLMFunctionscomponent],
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAIChooseLLMFunctionsModule", multi: false }]
})
export class GeboAIChooseLLMFunctionsModule { }
