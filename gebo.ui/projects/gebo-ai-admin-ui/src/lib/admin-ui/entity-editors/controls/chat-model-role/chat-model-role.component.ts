/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, Input, OnChanges, OnDestroy, SimpleChanges } from "@angular/core";
import { FormGroup } from "@angular/forms";
import { GBaseChatModelConfig } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";
import { Subscription } from "rxjs";

/**
 * The role of a chat model in the platform: the system default service model, the
 * system default chat model or a chat model used where it is explicitly configured.
 */
export type ChatModelRole = "SERVICE" | "DEFAULT_CHAT" | "CHAT";

/** The model uses and default flag a role stands for. */
export function chatModelRoleValues(role: ChatModelRole): { forUses: GBaseChatModelConfig.ForUsesEnum[], defaultModel: boolean } {
    switch (role) {
        case "SERVICE": return { forUses: ["INTERNAL_SERVICES"], defaultModel: false };
        case "DEFAULT_CHAT": return { forUses: ["CHAT"], defaultModel: true };
        default: return { forUses: ["CHAT"], defaultModel: false };
    }
}

/** The role a chat model configuration holds, from its model uses and default flag. */
export function chatModelRoleOf(forUses?: GBaseChatModelConfig.ForUsesEnum[] | null, defaultModel?: boolean | null): ChatModelRole {
    if (defaultModel === true) {
        return "DEFAULT_CHAT";
    }
    if (forUses && forUses.some(use => use === "INTERNAL_SERVICES")) {
        return "SERVICE";
    }
    return "CHAT";
}

/**
 * Chooses the role of a chat model in a radio panel: (A) system default service model,
 * (B) system default chat model, (C) chat model. It edits the forUses and defaultModel
 * controls of the chat model configuration form it is given; the platform keeps one
 * system default service model and one system default chat model, the model losing a
 * role becoming a chat model.
 */
@Component({
    selector: "gebo-ai-chat-model-role-component",
    templateUrl: "chat-model-role.component.html",
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIChatModelRoleModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIChatModelRoleComponent") }
    ]
})
export class GeboAIChatModelRoleComponent implements OnChanges, OnDestroy {
    /** The chat model configuration form, holding the forUses and defaultModel controls. */
    @Input() formGroup?: FormGroup;
    protected role: ChatModelRole = "CHAT";
    protected disabled: boolean = false;
    private subscription?: Subscription;

    ngOnChanges(changes: SimpleChanges): void {
        if (changes["formGroup"]) {
            this.subscription?.unsubscribe();
            this.readRole();
            if (this.formGroup) {
                // the configuration is loaded after the form is built: its role follows it
                this.subscription = this.formGroup.valueChanges.subscribe(() => this.readRole());
                const statusSubscription = this.formGroup.statusChanges.subscribe(() => this.readRole());
                this.subscription.add(statusSubscription);
            }
        }
    }

    ngOnDestroy(): void {
        this.subscription?.unsubscribe();
    }

    private readRole(): void {
        const forUses = this.formGroup?.get("forUses");
        const defaultModel = this.formGroup?.get("defaultModel");
        this.role = chatModelRoleOf(forUses?.value, defaultModel?.value);
        this.disabled = !this.formGroup || this.formGroup.disabled || !!forUses?.disabled;
    }

    protected chooseRole(role: ChatModelRole): void {
        if (!this.formGroup || this.disabled) {
            return;
        }
        this.role = role;
        const values = chatModelRoleValues(role);
        this.formGroup.patchValue(values);
        this.formGroup.get("forUses")?.markAsDirty();
        this.formGroup.get("defaultModel")?.markAsDirty();
    }
}
