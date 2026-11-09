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
import { RouterModule } from "@angular/router";
import { BlockUIModule } from "primeng/blockui";
import { ButtonModule } from "primeng/button";
import { CheckboxModule } from "primeng/checkbox";
import { MessageModule } from "primeng/message";
import { TagModule } from "primeng/tag";
import { GeboAIFieldTranslationContainerModule } from "../field-translation-container/field-container.module";
import { GeboAIWaitingConditionsPanelComponent } from "./waiting-conditions-panel.component";

/**
 * The panel of the runs of agents waiting for a human in the loop or an external
 * condition, to embed anywhere: an administration UI sets {@code administrator}.
 * No routes: see {@code GeboAIWaitingConditionsModule} for the user's page.
 */
@NgModule({
    imports: [CommonModule, FormsModule, RouterModule, BlockUIModule, ButtonModule, CheckboxModule, MessageModule, TagModule,
        GeboAIFieldTranslationContainerModule],
    declarations: [GeboAIWaitingConditionsPanelComponent],
    exports: [GeboAIWaitingConditionsPanelComponent]
})
export class GeboAIWaitingConditionsPanelModule { }
