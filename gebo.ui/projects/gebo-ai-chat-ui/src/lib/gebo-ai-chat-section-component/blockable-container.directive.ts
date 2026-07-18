/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Directive, ElementRef } from "@angular/core";
import { BlockableUI } from "primeng/api";

/**
 * Lets a plain element be used as a p-blockUI [target], so the block mask is
 * scoped to that element instead of falling back to the whole document.body.
 */
@Directive({
    selector: "[geboBlockableContainer]",
    standalone: true,
    exportAs: "geboBlockableContainer"
})
export class GeboBlockableContainerDirective implements BlockableUI {
    constructor(private el: ElementRef<HTMLElement>) { }

    getBlockableElement(): HTMLElement {
        return this.el.nativeElement;
    }
}
