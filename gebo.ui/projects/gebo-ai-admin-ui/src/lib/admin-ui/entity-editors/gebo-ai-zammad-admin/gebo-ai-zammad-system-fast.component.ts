/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, EventEmitter, Output, ChangeDetectionStrategy } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { FastZammadSystemInsertRequest, GZammadSystem, ZammadSystemsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { ToastMessageOptions } from "@openng/optimus-ui/api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";

/**
 * Fast setup of a Zammad system: server, description and an access token,
 * which is stored as a secret and checked against the server before the
 * system is saved.
 */
@Component({
    selector: "gebo-ai-zammad-system-fast-component",
    templateUrl: "gebo-ai-zammad-system-fast.component.html",
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIZammadModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIZammadSystemFastComponent") }
    ],
    changeDetection: ChangeDetectionStrategy.Eager,
    standalone: false
})
export class GeboAIZammadSystemFastComponent {
    public loading: boolean = false;

    public formGroup: FormGroup = new FormGroup({
        description: new FormControl("Zammad system", [Validators.required]),
        baseUri: new FormControl("", [Validators.required]),
        accessToken: new FormControl("", [Validators.required])
    });

    userMessages: ToastMessageOptions[] = [];

    @Output() newZammadSystemEvent: EventEmitter<GZammadSystem> = new EventEmitter();
    @Output() cancelAction: EventEmitter<boolean> = new EventEmitter();

    constructor(private zammadSystemsService: ZammadSystemsControllerService) {
    }

    doInsert(): void {
        const data: FastZammadSystemInsertRequest = this.formGroup.value;
        this.loading = true;
        this.zammadSystemsService.fastZammadConfig(data).subscribe({
            next: (result) => {
                this.userMessages = result.messages as ToastMessageOptions[];
                if (result.result && result.hasErrorMessages !== true) {
                    this.newZammadSystemEvent.emit(result.result);
                }
            },
            error: () => {
                this.loading = false;
            },
            complete: () => {
                this.loading = false;
            }
        });
    }
}
