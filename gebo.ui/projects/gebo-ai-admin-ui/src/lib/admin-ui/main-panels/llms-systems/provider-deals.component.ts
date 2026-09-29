/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, OnInit } from "@angular/core";
import {
    GModelPricingConditions, GProviderApiKey, GProviderDeal, GProviderModelPriceInfo, GUserMessage,
    ProviderDealsControllerService
} from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";
import { forkJoin, Observable } from "rxjs";

/** The price editor of one (deal, model) row. */
interface PriceEdit {
    currencyCode?: string;
    inputMtokenPrice?: number;
    outputMtokenPrice?: number;
    requestPrice?: number;
}

/** What every provider deals operation answers: a result, or messages explaining why not. */
interface DealsOperationStatus<T> {
    result?: T;
    messages?: Array<GUserMessage>;
    hasErrorMessages?: boolean;
}

/**
 * Maintenance of the deals Gebo has with each LLM provider and of the prices they
 * give to the provider's models: the API keys each deal covers, its spending limits
 * (imported from the provider when its API exposes them) and, per model, the price
 * overriding or completing the one saved with the model configuration.
 */
@Component({
    selector: "gebo-ai-provider-deals-component",
    templateUrl: "provider-deals.component.html",
    standalone: false,
    styles: [`
        .gebo-provider-deals__price { width: 7rem; }
        .gebo-provider-deals__currency { width: 4.5rem; }
        .gebo-provider-deals__code { font-family: monospace; word-break: break-all; }
    `],
    providers: [{ provide: GEBO_AI_MODULE, useValue: "LlmsPanelModule", multi: false }, {
        provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("ProviderDealsComponent")
    }]
})
export class ProviderDealsComponent implements OnInit {
    protected loading: boolean = false;
    protected providers: { label: string, value: string }[] = [];
    protected providerId?: string;
    protected deals: GProviderDeal[] = [];
    protected dealOptions: { label: string, value: string }[] = [];
    protected apiKeys: GProviderApiKey[] = [];
    protected modelPrices: GProviderModelPriceInfo[] = [];
    protected priceEdits: { [rowKey: string]: PriceEdit } = {};
    protected descriptionEdits: { [dealId: string]: string } = {};
    protected newDealDescription: string = "";
    /** The API keys to move into the new deal. */
    protected newDealKeys: string[] = [];
    protected keyOptions: { label: string, value: string }[] = [];
    protected messages: GUserMessage[] = [];

    constructor(private dealsService: ProviderDealsControllerService) {
    }

    ngOnInit(): void {
        this.loading = true;
        this.dealsService.getProviderDealProviderIds().subscribe({
            next: (providers) => {
                this.providers = (providers ?? []).map(x => ({ label: x, value: x }));
                this.loading = false;
            },
            error: () => this.loading = false
        });
    }

    protected onProviderChange(): void {
        this.messages = [];
        this.reload();
    }

    /** Loads the deals, keys and model prices of the chosen provider. */
    protected reload(): void {
        const providerId = this.providerId;
        if (!providerId) {
            this.deals = [];
            this.apiKeys = [];
            this.modelPrices = [];
            return;
        }
        this.loading = true;
        forkJoin([this.dealsService.getProviderDeals(providerId), this.dealsService.getProviderApiKeys(providerId),
        this.dealsService.getProviderModelPrices(providerId)]).subscribe({
            next: ([deals, keys, prices]) => {
                this.deals = deals ?? [];
                this.dealOptions = this.deals.map(x => ({ label: x.description ?? x.id ?? "", value: x.id ?? "" }));
                this.descriptionEdits = {};
                this.deals.forEach(x => { if (x.id) this.descriptionEdits[x.id] = x.description ?? ""; });
                this.apiKeys = keys.result ?? [];
                this.keyOptions = this.apiKeys.map(x => ({
                    label: (x.description ? x.description + " (" + x.secretCode + ")" : x.secretCode ?? "")
                        + (x.dealId ? " - " + this.dealDescription(x.dealId) : ""),
                    value: x.secretCode ?? ""
                }));
                this.modelPrices = prices.result ?? [];
                this.priceEdits = {};
                this.modelPrices.forEach(row => this.priceEdits[this.rowKey(row)] = this.editOf(row));
                this.messages = [...(keys.messages ?? []), ...(prices.messages ?? [])];
                this.loading = false;
            },
            error: () => this.loading = false
        });
    }

    protected rowKey(row: GProviderModelPriceInfo): string {
        return (row.dealId ?? "") + "|" + (row.configCode ?? "");
    }

    /** The editor starts from the deal's price, else from the configured one to complete. */
    private editOf(row: GProviderModelPriceInfo): PriceEdit {
        const source = row.dealPricing ?? row.configuredPricing;
        return {
            currencyCode: source?.currencyCode ?? "USD",
            inputMtokenPrice: source?.inputMtokenPrice,
            outputMtokenPrice: source?.outputMtokenPrice,
            requestPrice: source?.requestPrice
        };
    }

    protected formatPricing(pricing?: GModelPricingConditions): string {
        if (!pricing) {
            return "-";
        }
        const parts: string[] = [];
        if (pricing.inputMtokenPrice != null) parts.push("in " + pricing.inputMtokenPrice);
        if (pricing.outputMtokenPrice != null) parts.push("out " + pricing.outputMtokenPrice);
        let text = parts.length ? parts.join(" / ") + " per Mtok" : "";
        if (pricing.requestPrice != null) text += (text ? ", " : "") + pricing.requestPrice + " per request";
        if (pricing.monthlyFlatCost != null) text += (text ? ", " : "") + pricing.monthlyFlatCost + " monthly";
        return (pricing.currencyCode ?? "") + " " + (text || "-");
    }

    protected formatLimits(deal: GProviderDeal): string {
        const limits = deal.spendingLimits;
        if (!limits) {
            return "-";
        }
        const parts: string[] = [];
        if (limits.dailySpendingLimit != null) parts.push(limits.dailySpendingLimit + " daily");
        if (limits.weeklySpendingLimit != null) parts.push(limits.weeklySpendingLimit + " weekly");
        if (limits.monthlySpendingLimit != null) parts.push(limits.monthlySpendingLimit + " monthly");
        if (limits.totalSpendingLimit != null) parts.push(limits.totalSpendingLimit + " total");
        return parts.length ? (limits.currencyCode ?? "") + " " + parts.join(", ") : "unlimited";
    }

    protected dealDescription(dealId?: string): string {
        return this.deals.find(x => x.id === dealId)?.description ?? "";
    }

    /** Runs a maintenance operation, then reloads; a refused one shows its messages. */
    private run<T>(operation: Observable<DealsOperationStatus<T>>): void {
        this.loading = true;
        operation.subscribe({
            next: (status) => {
                this.messages = status.messages ?? [];
                this.loading = false;
                this.reload();
            },
            error: () => this.loading = false
        });
    }

    /**
     * Creates a deal, moving into it the chosen API keys together with the prices
     * their deals give to the configurations running with them.
     */
    protected createDeal(): void {
        if (!this.providerId) return;
        this.run(this.dealsService.createProviderDeal({
            providerId: this.providerId, description: this.newDealDescription, secretCodes: this.newDealKeys
        }));
        this.newDealDescription = "";
        this.newDealKeys = [];
    }

    protected saveDescription(deal: GProviderDeal): void {
        if (!deal.id) return;
        this.run(this.dealsService.updateDescription({ dealId: deal.id, description: this.descriptionEdits[deal.id] }));
    }

    protected deleteDeal(deal: GProviderDeal): void {
        if (!deal.id) return;
        this.run(this.dealsService.deleteProviderDeal({ dealId: deal.id }));
    }

    protected refreshLimits(deal: GProviderDeal): void {
        if (!deal.id) return;
        this.run(this.dealsService.refreshImportedLimits({ dealId: deal.id }));
    }

    protected assignKey(key: GProviderApiKey, dealId: string): void {
        if (!dealId || !key.secretCode || dealId === key.dealId) return;
        this.run(this.dealsService.assignApiKey({ dealId: dealId, secretCode: key.secretCode }));
    }

    protected removeKey(key: GProviderApiKey): void {
        if (!key.dealId || !key.secretCode) return;
        this.run(this.dealsService.removeApiKey({ dealId: key.dealId, secretCode: key.secretCode }));
    }

    protected savePrice(row: GProviderModelPriceInfo): void {
        if (!row.dealId || !row.configCode) return;
        const edit = this.priceEdits[this.rowKey(row)];
        const source = row.dealPricing ?? row.configuredPricing;
        const pricing: GModelPricingConditions = {
            ...source,
            pricingType: source?.pricingType ?? GModelPricingConditions.PricingTypeEnum.MTOKEN,
            currencyCode: edit.currencyCode?.trim().toUpperCase(),
            inputMtokenPrice: edit.inputMtokenPrice ?? undefined,
            outputMtokenPrice: edit.outputMtokenPrice ?? undefined,
            requestPrice: edit.requestPrice ?? undefined
        };
        this.run(this.dealsService.updateModelPricing({
            dealId: row.dealId, configCode: row.configCode, pricingConditions: pricing
        }));
    }

    /** Confirms the configuration's own pricing as the deal's price of the configuration. */
    protected confirmConfiguredPrice(row: GProviderModelPriceInfo): void {
        if (!row.dealId || !row.configCode || !row.configuredPricing) return;
        this.run(this.dealsService.updateModelPricing({
            dealId: row.dealId, configCode: row.configCode, pricingConditions: row.configuredPricing
        }));
    }

    /** Whether the deal already gives the configuration its configured pricing. */
    protected isConfiguredPriceConfirmed(row: GProviderModelPriceInfo): boolean {
        return this.samePricing(row.dealPricing, row.configuredPricing);
    }

    private samePricing(a?: GModelPricingConditions, b?: GModelPricingConditions): boolean {
        if (!a || !b) return false;
        const fields: (keyof GModelPricingConditions)[] = ["pricingType", "currencyCode", "inputMtokenPrice",
            "outputMtokenPrice", "requestPrice", "monthlyFlatCost", "monthlyTrafficLimits", "dailyTrafficLimits"];
        return fields.every(f => (a[f] ?? null) === (b[f] ?? null));
    }

    protected sourceLabel(row: GProviderModelPriceInfo): string {
        switch (row.configuredPricingSource) {
            case GProviderModelPriceInfo.ConfiguredPricingSourceEnum.CONFIGURATION: return "set in the configuration";
            case GProviderModelPriceInfo.ConfiguredPricingSourceEnum.PROVIDERAPI: return "retrieved from the provider's API";
            default: return "no conditions";
        }
    }

    /** Removes the deal's price: the configuration goes back to its own pricing. */
    protected removePrice(row: GProviderModelPriceInfo): void {
        if (!row.dealId || !row.configCode) return;
        this.run(this.dealsService.updateModelPricing({ dealId: row.dealId, configCode: row.configCode }));
    }
}
