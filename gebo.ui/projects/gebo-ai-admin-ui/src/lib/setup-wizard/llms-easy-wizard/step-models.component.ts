/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

/**
 * Step 3 of the guided setup: for the provider chosen in step 2, one row per model class.
 *  - a class the provider does not offer shows "provider does not offer this model type";
 *  - a class already configured shows its current model as a label with a "Modify" switch that
 *    opens a combo to change it;
 *  - a class not configured shows a "Configure" switch and, when on, a combo of the models the
 *    provider offers (library presets first, with their readable description).
 * Every row can be switched off ("don't set it now"). The decisions are the same array the parent
 * holds, so the switches and combos here feed straight into its createLLMS call.
 */
import { Component, Input } from "@angular/core";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";
import { CLASS_TEXT, ModelClassDecision } from "./model-classes";

@Component({
    selector: "gebo-ai-llms-easy-models-step",
    templateUrl: "step-models.component.html",
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboSetupWizardsModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("LLMSEasyModelsStepComponent"), multi: false }
    ]
})
export class LLMSEasyModelsStepComponent {
    @Input() decisions: ModelClassDecision[] = [];
    @Input() lookupsRunning: number = 0;
    protected readonly CLASS_TEXT = CLASS_TEXT;

    /** Opening "Modify" on an already configured class also arms it for the apply. */
    protected onModifyToggled(decision: ModelClassDecision): void {
        decision.enabled = decision.modifying;
    }
}
