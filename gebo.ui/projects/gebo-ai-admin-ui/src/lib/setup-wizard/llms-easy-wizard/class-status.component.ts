/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

/**
 * Shared status marker for a model class across the guided setup steps, so the graphics read the
 * same everywhere: a green check when the class is configured (optionally with the current model
 * and provider), a cross when it is missing - red for a mandatory class, black for an optional one
 * - and, for a missing class, a highlighted list of the providers that support that model kind.
 */
import { Component, Input } from "@angular/core";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";

@Component({
    selector: "gebo-ai-llms-easy-class-status",
    templateUrl: "class-status.component.html",
    standalone: false,
    styles: [`
        .gebo-easy-status-icon { margin-right: 0.4rem; font-size: 1.05rem; vertical-align: middle; }
        .gebo-easy-status-icon--ok { color: #16a34a; }
        .gebo-easy-supported {
            margin-top: 0.5rem;
            padding: 0.5rem 0.75rem;
            border-left: 3px solid var(--gebo-primary-500, #3b82f6);
            background: var(--gebo-surface-subtle, #f8fafc);
            border-radius: 4px;
        }
        .gebo-easy-supported__label {
            display: block;
            margin-bottom: 0.15rem;
            font-weight: 600;
            color: var(--gebo-text-strong, #111827);
        }
        .gebo-easy-supported__names { color: var(--gebo-text, #374151); }
    `],
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboSetupWizardsModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("LLMSEasyClassStatusComponent"), multi: false }
    ]
})
export class LLMSEasyClassStatusComponent {
    /** Whether a model of this class is already configured. */
    @Input() configured: boolean = false;
    /** Mandatory class → the missing cross is red; optional → black. */
    @Input() required: boolean = false;
    /** Shown next to the check when configured. */
    @Input() currentCode?: string;
    @Input() currentProvider?: string;
    /** Names of the providers that support this model kind; shown when the class is missing. */
    @Input() providers: string[] = [];
}
