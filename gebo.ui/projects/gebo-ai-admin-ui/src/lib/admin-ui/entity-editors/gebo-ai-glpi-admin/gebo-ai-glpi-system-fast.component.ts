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
import { FastGlpiSystemInsertRequest, GGlpiSystem, GlpiSystemsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { ToastMessageOptions } from "@openng/optimus-ui/api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";

/**
 * Fast setup of a GLPI system: the GLPI address, the OAuth client registered in
 * GLPI and the GLPI user; the backend stores both credentials as secrets,
 * tests them and saves the system.
 */
@Component({
    selector: "gebo-ai-glpi-system-fast-component",
    templateUrl: "gebo-ai-glpi-system-fast.component.html",
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIGlpiModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIGlpiSystemFastComponent") }
    ],
    changeDetection: ChangeDetectionStrategy.Eager,
    standalone: false
})
export class GeboAIGlpiSystemFastComponent {
    public loading: boolean = false;

    public formGroup: FormGroup = new FormGroup({
        description: new FormControl("GLPI system", [Validators.required]),
        baseUri: new FormControl("", [Validators.required]),
        clientId: new FormControl("", [Validators.required]),
        clientSecret: new FormControl("", [Validators.required]),
        username: new FormControl("", [Validators.required]),
        password: new FormControl("", [Validators.required])
    });

    userMessages: ToastMessageOptions[] = [];

    @Output() newGlpiSystemEvent: EventEmitter<GGlpiSystem> = new EventEmitter();
    @Output() cancelAction: EventEmitter<boolean> = new EventEmitter();

    constructor(private glpiSystemsService: GlpiSystemsControllerService) {
    }

    doInsert(): void {
        const data: FastGlpiSystemInsertRequest = this.formGroup.value;
        this.loading = true;
        this.glpiSystemsService.fastGlpiConfig(data).subscribe({
            next: (result) => {
                this.userMessages = result.messages as ToastMessageOptions[];
                if (result.result && result.hasErrorMessages !== true) {
                    this.newGlpiSystemEvent.emit(result.result);
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
