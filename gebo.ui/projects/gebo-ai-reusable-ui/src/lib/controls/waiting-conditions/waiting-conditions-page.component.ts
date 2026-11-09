/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component } from "@angular/core";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";

/**
 * The page of the user's runs of agents waiting for a human in the loop or an
 * external condition: the panel, for the logged-in user's own conversations.
 */
@Component({
    selector: "gebo-ai-waiting-conditions-page",
    templateUrl: "waiting-conditions-page.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAIWaitingConditionsModule", multi: false }, { provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIWaitingConditionsPageComponent"), multi: false }]
})
export class GeboAIWaitingConditionsPageComponent {
}
