/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

/**
 * Step 1 of the guided setup: explains the required and suggested model classes, each with a
 * plain description of its purpose, and shows the current setting of every class (the configured
 * default and the provider that owns it, or that it is not configured yet). Read only.
 */
import { Component, Input } from "@angular/core";
import { ComponentLLMSStatus, LLMSSetupConfigurationData } from '@Gebo.ai/brain';
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";
import { CLASS_TEXT, MODEL_CLASSES, ModelClassDescriptor } from "./model-classes";

interface ClassRow {
    descriptor: ModelClassDescriptor;
    label: string;
    description: string;
    configured: boolean;
    currentCode?: string;
    currentProvider?: string;
    /** Names of the providers whose library offers this model kind (for an unconfigured class). */
    providers: string[];
}

@Component({
    selector: "gebo-ai-llms-easy-intro-step",
    templateUrl: "step-intro.component.html",
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboSetupWizardsModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("LLMSEasyIntroStepComponent"), multi: false }
    ]
})
export class LLMSEasyIntroStepComponent {
    private _status?: ComponentLLMSStatus;
    private _configuration?: LLMSSetupConfigurationData;
    @Input() set status(value: ComponentLLMSStatus | undefined) { this._status = value; this.rebuild(); }
    @Input() set configuration(value: LLMSSetupConfigurationData | undefined) { this._configuration = value; this.rebuild(); }

    protected readonly CLASS_TEXT = CLASS_TEXT;
    protected requiredRows: ClassRow[] = [];
    protected suggestedRows: ClassRow[] = [];

    private rebuild(): void {
        const rows = MODEL_CLASSES.map(descriptor => {
            const configured = descriptor.statusSetup(this._status) === true;
            return {
                descriptor,
                label: CLASS_TEXT[descriptor.id].label,
                description: CLASS_TEXT[descriptor.id].description,
                configured,
                currentCode: descriptor.statusCode(this._status),
                currentProvider: descriptor.statusProvider(this._status),
                // Which providers offer this kind, so an unconfigured class shows where it can come from.
                providers: configured ? [] : this.providersFor(descriptor)
            } as ClassRow;
        });
        this.requiredRows = rows.filter(r => r.descriptor.required);
        this.suggestedRows = rows.filter(r => !r.descriptor.required);
    }

    /** Names of the vendors whose library declares a preset of this model kind. */
    private providersFor(descriptor: ModelClassDescriptor): string[] {
        return (this._configuration?.configurations ?? [])
            .filter(c => (c.libraryModel ?? []).some(preset => (preset.type as string) === (descriptor.type as string)))
            .map(c => c.parentModel?.name)
            .filter((name): name is string => !!name);
    }
}
