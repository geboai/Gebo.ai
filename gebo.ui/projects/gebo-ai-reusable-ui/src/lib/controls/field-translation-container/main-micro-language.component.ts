/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { ChangeDetectionStrategy, Component, input, output, signal, viewChild } from "@angular/core";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
import { GeboAIMainLanguageChoiceComponent } from "./main-language-choice.component";

/**
 * Minimal, circular language switcher: a flag-sized round button showing the language
 * currently in use, which pops the full language list open when clicked.
 *
 * It owns no language logic of its own — the embedded
 * {@link GeboAIMainLanguageChoiceComponent} does all of it (resolve the startup language
 * from local storage or the browser, drive
 * {@link GeboAITranslationService.changeActualLanguage}, and persist the new choice back
 * to local storage under `gebo.ai.lang`). This component only renders the compact button
 * and toggles that component's popover, so it can be dropped into places with no room for
 * the full-width select — the office assistant panel footer, a toolbar, a status bar.
 *
 * The flag is drawn with the shared `<langCode>-flag` CSS classes, as the rest of the
 * language UI does. Those live in `src/styles/flags.scss`, which is shipped with the
 * package and can be added on its own to a host's global styles — the office plugins do
 * exactly that, rather than pulling in the whole library `styles.scss`. A host that
 * includes neither sees the upper-cased language code, which the (then image-less) flag
 * layer leaves visible.
 */
@Component({
    selector: "gebo-ai-main-micro-language",
    templateUrl: "main-micro-language.component.html",
    styleUrl: "main-micro-language.component.css",
    standalone: false,
    changeDetection: ChangeDetectionStrategy.OnPush,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIMultilanguageModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIMainMicroLanguageComponent"), multi: false }
    ]
})
export class GeboAIMainMicroLanguageComponent {
    /**
     * Where the language popover is attached. It defaults to the document body so the
     * list is not clipped by the (typically small) container hosting the button.
     */
    readonly appendTo = input<any>("body");
    /** Emits the language code in use, once at startup and on every change. */
    readonly languageChange = output<string>();

    private readonly mainLanguageChoice = viewChild(GeboAIMainLanguageChoiceComponent);

    /** Language code currently in use, as reported by the embedded choice component. */
    protected readonly currentLanguageCode = signal<string>("en");

    /** Opens/closes the language list of the embedded choice component. */
    protected toggle(event: Event): void {
        this.mainLanguageChoice()?.languageChoice?.toggle(event);
    }

    protected onLanguageChange(langCode: string): void {
        if (!langCode) return;
        this.currentLanguageCode.set(langCode);
        this.languageChange.emit(langCode);
    }
}
