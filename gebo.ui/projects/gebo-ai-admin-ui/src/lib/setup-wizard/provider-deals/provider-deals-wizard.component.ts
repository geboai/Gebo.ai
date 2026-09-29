/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component } from "@angular/core";
import {
    GCurrency, GModelPricingConditions, GProviderApiKey, GProviderCurrency, GProviderDeal, GProviderModelPriceInfo,
    GUserMessage, ProviderDealsControllerService
} from "@Gebo.ai/gebo-ai-rest-api";
import { BaseWizardSectionComponent, fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { forkJoin, Observable, of } from "rxjs";

/** The pseudo API key standing for the configurations running without one (GProviderDeal.NO_API_KEY). */
const NO_API_KEY = "__no-api-key__";

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

/** Everything shown for one provider: its deals, API keys, model prices and default currency. */
interface ProviderGroup {
    providerId: string;
    deals: GProviderDeal[];
    apiKeys: GProviderApiKey[];
    modelPrices: GProviderModelPriceInfo[];
    currency?: GProviderCurrency;
    /** Codes of the API keys the running model configurations use (NO_API_KEY for those without one). */
    usedKeys: Set<string>;
}

/**
 * Setup section maintaining the deals Gebo has with each LLM provider and the
 * prices they give to the provider's models. Every deal is shown, grouped by
 * provider, with a "Provider deal" tab (description, API keys, spending limits)
 * and a "Model prices" tab (the price the configurations running each model with
 * the deal's keys pay); a new deal can be added for a provider some model
 * configuration uses.
 */
@Component({
    selector: "gebo-ai-provider-deals-wizard-component",
    templateUrl: "provider-deals-wizard.component.html",
    standalone: false,
    styles: [`
        .gebo-provider-deals__price { width: 7rem; }
        .gebo-provider-deals__currency { width: 8rem; }
        .gebo-provider-deals__code { font-family: monospace; word-break: break-all; }
    `],
    providers: [{ provide: GEBO_AI_MODULE, useValue: "ProviderDealsWizardModule", multi: false }, {
        provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("ProviderDealsWizardComponent")
    }]
})
export class ProviderDealsWizardComponent extends BaseWizardSectionComponent {
    protected groups: ProviderGroup[] = [];
    protected messages: GUserMessage[] = [];
    /** The bundled ISO 4217 currencies, as select options. */
    protected currencyOptions: { label: string, value: string }[] = [];
    /** Providers of the configured models: those a new deal can be made with. */
    protected configuredProviders: { label: string, value: string }[] = [];
    protected priceEdits: { [rowKey: string]: PriceEdit } = {};
    protected descriptionEdits: { [dealId: string]: string } = {};
    /** The API key chosen to add to each deal. */
    protected keyToAdd: { [dealId: string]: string | undefined } = {};

    // The new deal form.
    protected newDealProvider?: string;
    protected newDealDescription: string = "";
    protected newDealKeys: string[] = [];
    protected newDealKeyOptions: { label: string, value: string }[] = [];

    constructor(setupWizardComunicationService: SetupWizardComunicationService,
        private dealsService: ProviderDealsControllerService) {
        super(setupWizardComunicationService);
    }

    /** Loads every deal grouped by provider, with each provider's keys, prices and currency. */
    public override reloadData(): void {
        this.loading = true;
        forkJoin([this.dealsService.getProviderDeals(), this.dealsService.getConfiguredProviderIds(),
        this.dealsService.getCurrencies()]).subscribe({
            next: ([deals, configured, currencies]) => {
                this.configuredProviders = (configured ?? []).map(x => ({ label: x, value: x }));
                this.currencyOptions = (currencies ?? []).map((x: GCurrency) => ({
                    label: x.code + " - " + x.name, value: x.code ?? ""
                }));
                const providerIds = [...new Set((deals ?? []).map(x => x.providerId ?? ""))].filter(x => x).sort();
                if (!providerIds.length) {
                    this.groups = [];
                    this.loading = false;
                    return;
                }
                forkJoin(providerIds.map(providerId => this.loadGroup(providerId, (deals ?? [])
                    .filter(x => x.providerId === providerId)))).subscribe({
                        next: (groups) => {
                            this.groups = groups;
                            this.priceEdits = {};
                            this.descriptionEdits = {};
                            groups.forEach(group => {
                                group.deals.forEach(deal => { if (deal.id) this.descriptionEdits[deal.id] = deal.description ?? ""; });
                                group.modelPrices.forEach(row => this.priceEdits[this.rowKey(row)] = this.editOf(row, group));
                            });
                            this.loading = false;
                        },
                        error: () => this.loading = false
                    });
            },
            error: () => this.loading = false
        });
    }

    private loadGroup(providerId: string, deals: GProviderDeal[]): Observable<ProviderGroup> {
        return new Observable<ProviderGroup>(subscriber => {
            forkJoin([this.dealsService.getProviderApiKeys(providerId), this.dealsService.getProviderModelPrices(providerId),
            this.dealsService.getProviderCurrency(providerId)]).subscribe({
                next: ([keys, prices, currency]) => {
                    this.messages = [...this.messages, ...(keys.messages ?? []), ...(prices.messages ?? []),
                    ...(currency.messages ?? [])].filter(x => x.severity !== "success");
                    subscriber.next({
                        providerId, deals: deals.sort((a, b) => (a.description ?? "").localeCompare(b.description ?? "")),
                        apiKeys: keys.result ?? [], modelPrices: prices.result ?? [], currency: currency.result,
                        usedKeys: this.usedKeys(prices.result ?? [])
                    });
                    subscriber.complete();
                },
                error: (e) => subscriber.error(e)
            });
        });
    }

    protected rowKey(row: GProviderModelPriceInfo): string {
        return (row.dealId ?? "") + "|" + (row.modelCode ?? "");
    }

    /** The editor starts from the deal's price, else from the provider API's one to complete. */
    private editOf(row: GProviderModelPriceInfo, group: ProviderGroup): PriceEdit {
        const source = row.dealPricing ?? row.providerApiPricing;
        return {
            currencyCode: source?.currencyCode ?? group.currency?.currencyCode ?? "USD",
            inputMtokenPrice: source?.inputMtokenPrice,
            outputMtokenPrice: source?.outputMtokenPrice,
            requestPrice: source?.requestPrice
        };
    }

    /** The model price rows of a deal. */
    protected dealPrices(group: ProviderGroup, deal: GProviderDeal): GProviderModelPriceInfo[] {
        return group.modelPrices.filter(x => x.dealId === deal.id);
    }

    /** The configurations of a provider running with keys no deal covers. */
    protected uncoveredModels(group: ProviderGroup): GProviderModelPriceInfo[] {
        return group.modelPrices.filter(x => !x.dealId);
    }

    protected uncoveredModelCodes(group: ProviderGroup): string {
        return this.uncoveredModels(group).map(x => x.modelCode).join(", ");
    }

    /** The API keys the running model configurations use, NO_API_KEY standing for those without one. */
    private usedKeys(modelPrices: GProviderModelPriceInfo[]): Set<string> {
        return new Set(modelPrices.flatMap(row => row.configurations ?? []).map(config => config.secretCode || NO_API_KEY));
    }

    /** The provider's API keys a deal can take: those it does not cover yet. */
    protected keyOptions(group: ProviderGroup, deal: GProviderDeal): { label: string, value: string }[] {
        return group.apiKeys.filter(x => x.dealId !== deal.id).map(x => ({
            label: this.keyDescription(x, group.usedKeys) + (x.dealId ? " - now in " + this.dealDescription(group, x.dealId) : ""),
            value: x.secretCode ?? ""
        }));
    }

    /** How an API key is shown: "(unused)" when no running model configuration uses it. */
    protected keyDescription(key?: GProviderApiKey, usedKeys?: Set<string>): string {
        if (!key) return "";
        const unused = usedKeys && !usedKeys.has(key.secretCode ?? "") ? " (unused)" : "";
        if (key.secretCode === NO_API_KEY) return "No API key (configurations running without one)" + unused;
        return (key.description ? key.description + " (" + key.secretCode + ")" : key.secretCode ?? "") + unused;
    }

    /** How an API key code of a deal is shown. */
    protected keyLabel(group: ProviderGroup, secretCode?: string): string {
        const key = group.apiKeys.find(x => x.secretCode === secretCode);
        const unused = !group.usedKeys.has(secretCode ?? "") ? " (unused)" : "";
        return key ? this.keyDescription(key, group.usedKeys) : (secretCode === NO_API_KEY ? "No API key" : (secretCode ?? "")) + unused;
    }

    private dealDescription(group: ProviderGroup, dealId?: string): string {
        return group.deals.find(x => x.id === dealId)?.description ?? "";
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

    /** Where the price the configurations pay comes from. */
    protected priceSource(row: GProviderModelPriceInfo): string {
        if (row.dealPricing) {
            return row.dealPricingAutoImported ? "imported from the provider's API" : "set by the admin";
        }
        return row.providerApiPricing ? "no deal price: the provider API's price applies"
            : "no price: the calls of this model are not priced";
    }

    /** Runs a maintenance operation, then reloads; a refused one shows its messages. */
    private run<T>(operation: Observable<DealsOperationStatus<T>>): void {
        this.loading = true;
        operation.subscribe({
            next: (status) => {
                this.messages = status.messages ?? [];
                this.reloadData();
            },
            error: () => this.loading = false
        });
    }

    /** Loads the API keys the new deal can take, when its provider is chosen. */
    protected onNewDealProviderChange(): void {
        this.newDealKeys = [];
        this.newDealKeyOptions = [];
        if (!this.newDealProvider) return;
        const group = this.groups.find(x => x.providerId === this.newDealProvider);
        const loaded: Observable<[DealsOperationStatus<GProviderApiKey[]>, DealsOperationStatus<GProviderModelPriceInfo[]>]> = group
            ? of([{ result: group.apiKeys }, { result: group.modelPrices }])
            : forkJoin([this.dealsService.getProviderApiKeys(this.newDealProvider),
            this.dealsService.getProviderModelPrices(this.newDealProvider)]);
        loaded.subscribe(([keys, prices]) => {
            const used = this.usedKeys(prices.result ?? []);
            this.newDealKeyOptions = (keys.result ?? []).map(x => ({
                label: this.keyDescription(x, used) + (x.dealId && group ? " - now in " + this.dealDescription(group, x.dealId) : ""),
                value: x.secretCode ?? ""
            }));
        });
    }

    /**
     * Creates a deal, moving into it the chosen API keys: the configurations running
     * with them then pay the new deal's model prices.
     */
    protected createDeal(): void {
        if (!this.newDealProvider) return;
        this.run(this.dealsService.createProviderDeal({
            providerId: this.newDealProvider, description: this.newDealDescription, secretCodes: this.newDealKeys
        }));
        this.newDealProvider = undefined;
        this.newDealDescription = "";
        this.newDealKeys = [];
        this.newDealKeyOptions = [];
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

    /** Adds an API key to a deal, moving it from the provider's other deal covering it. */
    protected addKey(deal: GProviderDeal): void {
        const secretCode = deal.id ? this.keyToAdd[deal.id] : undefined;
        if (!deal.id || !secretCode) return;
        this.keyToAdd[deal.id] = undefined;
        this.run(this.dealsService.assignApiKey({ dealId: deal.id, secretCode: secretCode }));
    }

    protected removeKey(deal: GProviderDeal, secretCode: string): void {
        if (!deal.id) return;
        this.run(this.dealsService.removeApiKey({ dealId: deal.id, secretCode: secretCode }));
    }

    /** Sets a provider's default currency; null goes back to the one it declares. */
    protected saveProviderCurrency(group: ProviderGroup, currencyCode: string | null): void {
        this.run(this.dealsService.updateProviderCurrency({ providerId: group.providerId, currencyCode: currencyCode ?? undefined }));
    }

    protected savePrice(row: GProviderModelPriceInfo): void {
        if (!row.dealId || !row.modelCode) return;
        const edit = this.priceEdits[this.rowKey(row)];
        const source = row.dealPricing ?? row.providerApiPricing;
        const pricing: GModelPricingConditions = {
            ...source,
            pricingType: source?.pricingType ?? GModelPricingConditions.PricingTypeEnum.MTOKEN,
            currencyCode: edit.currencyCode,
            inputMtokenPrice: edit.inputMtokenPrice ?? undefined,
            outputMtokenPrice: edit.outputMtokenPrice ?? undefined,
            requestPrice: edit.requestPrice ?? undefined
        };
        this.run(this.dealsService.updateModelPricing({
            dealId: row.dealId, modelCode: row.modelCode, pricingConditions: pricing
        }));
    }

    /** Removes the deal's price: the model goes back to the provider API's price, if any. */
    protected removePrice(row: GProviderModelPriceInfo): void {
        if (!row.dealId || !row.modelCode) return;
        this.run(this.dealsService.updateModelPricing({ dealId: row.dealId, modelCode: row.modelCode }));
    }
}
