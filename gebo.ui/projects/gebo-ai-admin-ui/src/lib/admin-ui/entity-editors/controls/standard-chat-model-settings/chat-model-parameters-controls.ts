/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { FormGroup } from "@angular/forms";
import { Observable, of, Subject, Subscription } from "rxjs";
import { catchError, switchMap } from "rxjs/operators";
import { ChatModelsControllerService, GBaseChatModelConfig, GChatModelParametersSupport, GChatModelParametersSupportRequest, GSamplingParameterSupport } from "@Gebo.ai/gebo-ai-rest-api";

/** A thinking choice of the editors' list. */
export type ThinkingOptionItem = { code: GBaseChatModelConfig.ThinkingEnum, description: string };

/** The names of the controls a form holds the parameters in. */
export type ChatModelParametersControlNames = { temperature: string, topP: string, thinking: string };

const DEFAULT_NAMES: ChatModelParametersControlNames = { temperature: "temperature", topP: "topP", thinking: "thinking" };

/** The chosen model, as the forms hold it: its code, its metadata and the provider's details. */
export type ChosenChatModel = { code?: string, metaInfos?: any, modelDetails?: { [key: string]: any } };

/**
 * Keeps the temperature, top_p and thinking controls of a chat model or agent form as the
 * model's integration tells (ChatModelsController.getChatModelParametersSupport): a
 * control is disabled while the model never gets its parameter - a disabled control is
 * left out of the saved value, so the configuration is saved without it - and enabled
 * again when it does; the temperature and top_p of a model taking them only with the
 * thinking off follow the thinking control. Only the controls disabled here are enabled
 * again, a form disabled as a whole stays so. A model whose integration tells nothing
 * (unknown) leaves every control as it is.
 */
export class ChatModelParametersControls {
    /** What the chosen model takes, undefined while unknown. */
    public support?: GChatModelParametersSupport;
    private readonly disabledHere = new Set<string>();
    private readonly requests = new Subject<GChatModelParametersSupportRequest | undefined>();
    private readonly subscriptions: Subscription[] = [];
    private lastKey?: string;

    /**
     * @param fallbackThinking the thinking applying when the form sets none: an agent's
     *                         empty thinking leaves its model's own
     */
    constructor(private formGroup: FormGroup, private controller: ChatModelsControllerService,
        private changed: () => void,
        private names: ChatModelParametersControlNames = DEFAULT_NAMES,
        private fallbackThinking?: () => string | undefined) {
        this.subscriptions.push(this.requests.pipe(switchMap(request => this.load(request))).subscribe(support => {
            this.support = support;
            this.sync();
            this.changed();
        }));
        const thinking = this.formGroup.controls[this.names.thinking];
        if (thinking) {
            this.subscriptions.push(thinking.valueChanges.subscribe(() => {
                this.sync();
                this.changed();
            }));
        }
    }

    private load(request?: GChatModelParametersSupportRequest): Observable<GChatModelParametersSupport | undefined> {
        if (!request?.modelTypeCode || !request.modelCode) {
            return of(undefined);
        }
        return this.controller.getChatModelParametersSupport(request).pipe(catchError(() => of(undefined)));
    }

    /** Asks what the model of a type takes; nothing is asked again for the same model. */
    public chosen(modelTypeCode?: string, model?: ChosenChatModel): void {
        const key = modelTypeCode + "|" + model?.code;
        if (key === this.lastKey) return;
        this.lastKey = key;
        this.requests.next(modelTypeCode && model?.code ? {
            modelTypeCode: modelTypeCode,
            modelCode: model.code,
            metaInfos: model.metaInfos,
            modelDetails: model.modelDetails
        } : undefined);
    }

    public destroy(): void {
        this.subscriptions.forEach(s => s.unsubscribe());
    }

    private get known(): boolean {
        return this.support?.known === true;
    }

    private get thinkingValue(): string | undefined {
        return this.formGroup.controls[this.names.thinking]?.value ?? this.fallbackThinking?.();
    }

    /** The thinking the form itself holds. */
    private get formThinking(): string | undefined {
        return this.formGroup.controls[this.names.thinking]?.value;
    }

    /** Whether the model gets the parameter now, by its availability and the thinking chosen. */
    private sent(parameter?: GSamplingParameterSupport): boolean {
        const availability: string | undefined = parameter?.availability as any;
        if (availability === "NEVER") return false;
        if (availability === "WITHOUT_THINKING") return this.thinkingValue === "NO_THINKING";
        return true;
    }

    /** Enables and disables the controls as the model takes their parameters. */
    public sync(): void {
        const known = this.known;
        this.setEnabled(this.names.temperature, !known || this.sent(this.support?.temperature));
        this.setEnabled(this.names.topP, !known || this.sent(this.support?.topP));
        this.setEnabled(this.names.thinking, !known || this.support?.thinkingAvailable !== false);
    }

    private setEnabled(name: string, enabled: boolean): void {
        const control = this.formGroup.controls[name];
        if (!control) return;
        if (!enabled && control.enabled) {
            control.disable({ emitEvent: false });
            this.disabledHere.add(name);
        } else if (enabled && control.disabled && this.disabledHere.has(name)) {
            control.enable({ emitEvent: false });
            this.disabledHere.delete(name);
        }
    }

    /** The thinking choices to offer: the ones that reach the model, each with what it sends. */
    public thinkingOptions(all: ThinkingOptionItem[]): ThinkingOptionItem[] {
        if (!this.known) return all;
        const offered = this.support?.thinkingOptions ?? [];
        return all.filter(o => offered.some(x => (x.option as any) === o.code)).map(o => {
            const sentAs = offered.find(x => (x.option as any) === o.code)?.sentAs;
            return { code: o.code, description: sentAs ? o.description + " (" + sentAs + ")" : o.description };
        });
    }

    /** Whether the thinking the form holds is one the model gets nothing for. */
    public get thinkingNotSent(): boolean {
        const value = this.formThinking;
        if (!this.known || !value || this.support?.thinkingAvailable === false) return false;
        return !(this.support?.thinkingOptions ?? []).some(x => (x.option as any) === value);
    }

    /** The least value of a parameter, the model's when told. */
    public min(parameter: "temperature" | "topP", fallback: number): number {
        const told = this.known ? this.support?.[parameter]?.min : undefined;
        return told !== undefined && told !== null ? told : fallback;
    }

    /** The greatest value of a parameter, the model's when told. */
    public max(parameter: "temperature" | "topP", fallback: number): number {
        const told = this.known ? this.support?.[parameter]?.max : undefined;
        return told !== undefined && told !== null ? told : fallback;
    }

    /** Whether the model never gets the parameter. */
    public never(parameter: "temperature" | "topP"): boolean {
        return this.known && (this.support?.[parameter]?.availability as any) === "NEVER";
    }

    /** Whether the model gets the parameter only with the thinking disabled. */
    public withoutThinkingOnly(parameter: "temperature" | "topP"): boolean {
        return this.known && (this.support?.[parameter]?.availability as any) === "WITHOUT_THINKING";
    }

    /** Whether an empty parameter leaves the provider's default (false: Gebo sends its deterministic 0). */
    public providerDefaultWhenEmpty(parameter: "temperature" | "topP"): boolean {
        return !this.known || this.support?.[parameter]?.providerDefaultWhenEmpty !== false;
    }

    /** Whether no thinking reaches the model. */
    public get thinkingUnavailable(): boolean {
        return this.known && this.support?.thinkingAvailable === false;
    }
}
