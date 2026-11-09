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
import { RouterModule, Routes } from "@angular/router";
import { PanelModule } from "primeng/panel";
import { GeboAIFieldTranslationContainerModule } from "../field-translation-container/field-container.module";
import { GeboAIWaitingConditionsPageComponent } from "./waiting-conditions-page.component";
import { GeboAIWaitingConditionsPanelModule } from "./waiting-conditions-panel.module";

/** The user's page: the panel of their own waiting runs. */
const routes: Routes = [{ path: "", component: GeboAIWaitingConditionsPageComponent }];

/**
 * The page of the runs of agents waiting for a human in the loop or an external
 * condition, for a user: an application lazy-loads it (for instance at
 * {@code ui/waiting-conditions}). The panel alone is in
 * {@link GeboAIWaitingConditionsPanelModule}.
 */
@NgModule({
    imports: [CommonModule, RouterModule.forChild(routes), PanelModule, GeboAIFieldTranslationContainerModule,
        GeboAIWaitingConditionsPanelModule],
    declarations: [GeboAIWaitingConditionsPageComponent],
    exports: [GeboAIWaitingConditionsPageComponent]
})
export class GeboAIWaitingConditionsModule { }
