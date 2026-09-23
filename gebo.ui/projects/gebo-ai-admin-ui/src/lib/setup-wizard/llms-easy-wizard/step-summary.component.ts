/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

/**
 * Step 4 of the guided setup: the active configuration after the apply, one line per model class,
 * so the user sees what is now in place before closing the wizard. Read only.
 */
import { Component, Input } from "@angular/core";
import { ComponentLLMSStatus, LLMSSetupConfigurationData } from '@Gebo.ai/brain';
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";
import { CLASS_TEXT, MODEL_CLASSES, ModelClassDescriptor } from "./model-classes";

interface SummaryRow {
    descriptor: ModelClassDescriptor;
    label: string;
    configured: boolean;
    currentCode?: string;
    currentProvider?: string;
    /** Providers that support this kind, shown when the class is still missing. */
    providers: string[];
}

@Component({
    selector: "gebo-ai-llms-easy-summary-step",
    templateUrl: "step-summary.component.html",
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboSetupWizardsModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("LLMSEasySummaryStepComponent"), multi: false }
    ]
})
export class LLMSEasySummaryStepComponent {
    private _status?: ComponentLLMSStatus;
    private _configuration?: LLMSSetupConfigurationData;
    @Input() set status(value: ComponentLLMSStatus | undefined) { this._status = value; this.rebuild(); }
    @Input() set configuration(value: LLMSSetupConfigurationData | undefined) { this._configuration = value; this.rebuild(); }

    protected rows: SummaryRow[] = [];
    protected get setupComplete(): boolean { return this._status?.isSetup === true; }

    private rebuild(): void {
        this.rows = MODEL_CLASSES.map(descriptor => {
            const configured = descriptor.statusSetup(this._status) === true;
            return {
                descriptor,
                label: CLASS_TEXT[descriptor.id].label,
                configured,
                currentCode: descriptor.statusCode(this._status),
                currentProvider: descriptor.statusProvider(this._status),
                providers: configured ? [] : this.providersFor(descriptor)
            } as SummaryRow;
        });
    }

    /** Names of the vendors whose library declares a preset of this model kind. */
    private providersFor(descriptor: ModelClassDescriptor): string[] {
        return (this._configuration?.configurations ?? [])
            .filter(c => (c.libraryModel ?? []).some(preset => (preset.type as string) === (descriptor.type as string)))
            .map(c => c.parentModel?.name)
            .filter((name): name is string => !!name);
    }
}
