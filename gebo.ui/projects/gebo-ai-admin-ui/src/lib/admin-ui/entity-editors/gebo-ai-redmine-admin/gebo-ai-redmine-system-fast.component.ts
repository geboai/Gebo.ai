/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, EventEmitter, Output, ChangeDetectionStrategy } from "@angular/core";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { FastRedmineSystemInsertRequest, GRedmineSystem, RedmineSystemsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { ToastMessageOptions } from "@openng/optimus-ui/api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";

/**
 * Fast setup of a Redmine system: server, description and the credentials,
 * which are stored as a secret and checked against the server before the
 * system is saved.
 */
@Component({
    selector: "gebo-ai-redmine-system-fast-component",
    templateUrl: "gebo-ai-redmine-system-fast.component.html",
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIRedmineModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIRedmineSystemFastComponent") }
    ],
    changeDetection: ChangeDetectionStrategy.Eager,
    standalone: false
})
export class GeboAIRedmineSystemFastComponent {
    public loading: boolean = false;

    public formGroup: FormGroup = new FormGroup({
        description: new FormControl("Redmine system", [Validators.required]),
        baseUri: new FormControl("", [Validators.required]),
        authType: new FormControl(null, [Validators.required]),
        apiKey: new FormControl(),
        username: new FormControl(),
        password: new FormControl()
    });

    userMessages: ToastMessageOptions[] = [];

    authTypes: { code: FastRedmineSystemInsertRequest.AuthTypeEnum, description: string }[] = [
        { code: "API_KEY", description: "API key (My account, API access key)" },
        { code: "USERNAME_PASSWORD", description: "Username and password" }
    ];

    @Output() newRedmineSystemEvent: EventEmitter<GRedmineSystem> = new EventEmitter();
    @Output() cancelAction: EventEmitter<boolean> = new EventEmitter();

    constructor(private redmineSystemsService: RedmineSystemsControllerService) {
        this.formGroup.controls["authType"].valueChanges
            .pipe(takeUntilDestroyed())
            .subscribe((type) => this.applyRequiredValidators(type));
        this.formGroup.controls["authType"].setValue("API_KEY");
    }

    // [required] on the field markup only draws the asterisk: the validators of the
    // credential fields follow the chosen authentication.
    private applyRequiredValidators(type?: FastRedmineSystemInsertRequest.AuthTypeEnum): void {
        const apiKey = this.formGroup.controls["apiKey"];
        const username = this.formGroup.controls["username"];
        const password = this.formGroup.controls["password"];
        if (type === "USERNAME_PASSWORD") {
            apiKey.clearValidators();
            username.setValidators([Validators.required]);
            password.setValidators([Validators.required]);
        } else {
            apiKey.setValidators([Validators.required]);
            username.clearValidators();
            password.clearValidators();
        }
        apiKey.updateValueAndValidity();
        username.updateValueAndValidity();
        password.updateValueAndValidity();
    }

    get isApiKey(): boolean {
        return this.formGroup.controls["authType"].value !== "USERNAME_PASSWORD";
    }

    doInsert(): void {
        const data: FastRedmineSystemInsertRequest = this.formGroup.value;
        this.loading = true;
        this.redmineSystemsService.fastRedmineConfig(data).subscribe({
            next: (result) => {
                this.userMessages = result.messages as ToastMessageOptions[];
                if (result.result && result.hasErrorMessages !== true) {
                    this.newRedmineSystemEvent.emit(result.result);
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
