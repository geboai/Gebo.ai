/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
import { FormGroup } from "@angular/forms";

/**
 * A model choice as the providers list it: the code identifies the model, the rest
 * (description, meta infos, pricing) is what the provider tells about it today.
 */
export interface ChoosedModelLike {
    code?: string;
}

/**
 * Aligns the model choice held by a model configuration form with the choices the
 * provider lists now.
 *
 * The form stores the whole choice object chosen when the configuration was saved; the
 * provider's list is loaded again every time the form opens, and what it tells about a
 * model (context length, pricing, meta infos) may have changed since. The select shows the
 * stored choice as selected by its code (dataKey), and this function replaces the stored
 * object with the listed one of the same code, so that the next save keeps the current
 * description of the model and not the one of the day the configuration was made.
 *
 * The replacement does not emit a value change: opening a form must not look like an edit.
 *
 * @param formGroup the configuration form
 * @param choices the choices the provider lists now, as the loading call returned them
 * @param controlName the control holding the choice, "choosedModel" in every model admin
 * @returns the choices to offer, never null
 */
export function alignChoosedModel<T extends ChoosedModelLike>(formGroup: FormGroup, choices: T[] | null | undefined,
    controlName: string = "choosedModel"): T[] {
    const listed: T[] = choices ? choices : [];
    const control = formGroup.get(controlName);
    const stored: T | null | undefined = control ? control.value : null;
    if (!control || !stored || !stored.code) {
        return listed;
    }
    const current = listed.find(choice => choice.code === stored.code);
    if (!current) {
        console.debug("[models-admin] the stored model choice " + stored.code + " is not among the " + listed.length
            + " choice(s) the provider lists now: kept as stored");
        return listed;
    }
    if (current !== stored) {
        console.debug("[models-admin] the stored model choice " + stored.code
            + " is aligned with the choice the provider lists now");
        control.setValue(current, { emitEvent: false });
    }
    return listed;
}
