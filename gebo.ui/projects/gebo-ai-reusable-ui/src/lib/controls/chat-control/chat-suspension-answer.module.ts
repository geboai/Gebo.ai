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
import { FormsModule } from "@angular/forms";
import { ButtonModule } from "primeng/button";
import { CheckboxModule } from "primeng/checkbox";
import { RadioButtonModule } from "primeng/radiobutton";
import { TextareaModule } from "primeng/textarea";
import { GeboAIFieldTranslationContainerModule } from "../field-translation-container/field-container.module";
import { GeboAIChatSuspensionAnswerComponent } from "./chat-suspension-answer.component";

/**
 * The form answering the conditions a suspended network of agents waits for: the
 * chat shows it under the answer that asked them, and it is reused wherever they are
 * answered away from the conversation.
 */
@NgModule({
  imports: [CommonModule, FormsModule, ButtonModule, CheckboxModule, RadioButtonModule, TextareaModule,
    GeboAIFieldTranslationContainerModule],
  declarations: [GeboAIChatSuspensionAnswerComponent],
  exports: [GeboAIChatSuspensionAnswerComponent]
})
export class GeboAIChatSuspensionAnswerModule { }
