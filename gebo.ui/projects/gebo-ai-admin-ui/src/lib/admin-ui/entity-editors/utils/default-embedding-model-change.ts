/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
import { Injector } from "@angular/core";
import { GeboFastLlmsSetupControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { ExtendedConfirmation, GeboAITranslationService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "@openng/optimus-ui/api";
import { take } from "rxjs";

/**
 * Goes on with a save of an embedding model, asking the user first when the save changes
 * which embedding model is the default one.
 *
 * The knowledge bases that name no embedding model are indexed with the default one, in
 * the vectors collection of that model, and the searches that name none look there: after
 * the default changes, what was indexed with the previous default may no longer be found.
 *
 * The default changes when the default model stops being it, and when a model becomes the
 * default while another one is: with no default embedding model yet nothing is asked.
 *
 * @param injector the injector of the component that saves
 * @param wasDefault whether the model is the default embedding model as it is stored
 * @param willBeDefault whether the model is saved as the default embedding model
 * @param proceed the save
 */
export function saveAskingOnDefaultEmbeddingModelChange(injector: Injector, wasDefault: boolean, willBeDefault: boolean, proceed: () => void): void {
    if (wasDefault === willBeDefault) {
        proceed();
        return;
    }
    const ask = () => {
        const confirmation: ExtendedConfirmation = {
            id: "DefaultEmbeddingModelChangeConfirmation",
            header: "Default embedding model change",
            message: "The default embedding model is changing: contents already ingested with the present default embedding model could become unreachable by the searches. Do you want to save?",
            icon: "pi pi-exclamation-triangle",
            accept: proceed
        };
        injector.get(GeboAITranslationService).translateConfirmation("GeboAiLargeLanguageModelsModule", "DefaultEmbeddingModelChange", confirmation)
            .pipe(take(1)).subscribe(translated => injector.get(ConfirmationService).confirm(translated ?? confirmation));
    };
    if (wasDefault) {
        ask();
        return;
    }
    injector.get(GeboFastLlmsSetupControllerService).getLLMSSetupStatus().pipe(take(1)).subscribe({
        next: (status) => status?.embeddedModelSetup === true ? ask() : proceed(),
        // not knowing whether a default embedding model exists, the user is asked
        error: () => ask()
    });
}
