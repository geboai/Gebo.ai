/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, Input, OnChanges, OnDestroy, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GModelPricingConditions } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";
import { Subscription } from "rxjs";

/**
 * The "Model price" tab of every model configuration editor: views and edits the
 * configuration's pricingConditions control.
 * <p>
 * Until the user edits them it shows, editable, the conditions the models lookup
 * retrieved from the provider's API for the chosen model; the first edit copies them
 * into the configuration, which the editor's save then stores. The configuration's
 * conditions are presented in LLMs configurations, where the admin confirms them as
 * the price of the model in its provider deal.
 */
@Component({
    selector: "gebo-ai-model-pricing",
    templateUrl: "model-pricing.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAIModelPricingModule", multi: false },
    { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIModelPricingComponent") }]
})
export class GeboAIModelPricingComponent implements OnChanges, OnDestroy {
    /** The model configuration's form, holding the choosedModel and pricingConditions controls. */
    @Input() formGroup?: FormGroup;

    /** The conditions being shown and edited. */
    protected edit: GModelPricingConditions = {};
    /** The conditions retrieved from the provider's API for the chosen model, if any. */
    protected providerPricing?: GModelPricingConditions;
    /** Whether the configuration has conditions of its own, rather than the provider's. */
    protected ownPricing: boolean = false;
    protected readonly pricingTypes = [
        { label: "Pay per use (prices per million tokens)", value: GModelPricingConditions.PricingTypeEnum.MTOKEN },
        { label: "Flat monthly fee", value: GModelPricingConditions.PricingTypeEnum.FLAT }
    ];
    private subscription?: Subscription;
    /** The value this component last wrote in the control, not to re-read its own edits. */
    private lastWritten?: GModelPricingConditions;

    ngOnChanges(changes: SimpleChanges): void {
        if (!changes["formGroup"]) {
            return;
        }
        this.subscription?.unsubscribe();
        if (this.formGroup && !this.formGroup.contains("pricingConditions")) {
            this.formGroup.addControl("pricingConditions", new FormControl());
        }
        this.subscription = this.formGroup?.valueChanges.subscribe(() => this.refresh());
        this.refresh();
    }

    ngOnDestroy(): void {
        this.subscription?.unsubscribe();
    }

    private refresh(): void {
        const choosed = this.formGroup?.controls["choosedModel"]?.value;
        this.providerPricing = choosed?.pricingConditions ?? undefined;
        const own: GModelPricingConditions | null | undefined = this.formGroup?.controls["pricingConditions"]?.value;
        if (own) {
            if (own !== this.lastWritten) {
                this.edit = { ...own };
            }
            this.ownPricing = true;
        } else {
            this.ownPricing = false;
            this.lastWritten = undefined;
            this.edit = this.providerPricing ? { ...this.providerPricing }
                : { pricingType: GModelPricingConditions.PricingTypeEnum.MTOKEN, currencyCode: "USD" };
        }
    }

    /** An edit makes the shown conditions the configuration's own, saved with it. */
    protected set(field: keyof GModelPricingConditions, value: any): void {
        (this.edit as any)[field] = value === "" ? undefined : value;
        const control = this.formGroup?.controls["pricingConditions"];
        if (!control) {
            return;
        }
        const written: GModelPricingConditions = { ...this.edit };
        if (written.currencyCode) {
            written.currencyCode = written.currencyCode.trim().toUpperCase();
        }
        this.lastWritten = written;
        control.setValue(written);
        control.markAsDirty();
    }

    /** Drops the configuration's own conditions, going back to the provider's. */
    protected restoreProviderConditions(): void {
        const control = this.formGroup?.controls["pricingConditions"];
        control?.setValue(null);
        control?.markAsDirty();
    }

    protected get isFlat(): boolean {
        return this.edit.pricingType === GModelPricingConditions.PricingTypeEnum.FLAT;
    }
}
