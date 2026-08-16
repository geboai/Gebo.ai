/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, Injectable } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import {
    BraveSearchConfigurationControllerService,
    GoogleSearchConfigurationControllerService,
    SearxngSearchConfigurationControllerService,
    TavilySearchConfigurationControllerService
} from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, fieldHostComponentName, GEBO_AI_FIELD_HOST, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { forkJoin, map, Observable, of, switchMap } from "rxjs";

/** Provider ids match each handler's getProductId() on the backend. */
export type WebSearchProviderId = "google" | "tavily" | "brave" | "searxng";

/**
 * Setup status for the whole "web search" section: setup when ANY provider is
 * configured (only one can be active at a time - see the wizard).
 */
@Injectable()
export class WebSearchStatusService extends AbstractStatusService {
    constructor(
        private google: GoogleSearchConfigurationControllerService,
        private tavily: TavilySearchConfigurationControllerService,
        private brave: BraveSearchConfigurationControllerService,
        private searxng: SearxngSearchConfigurationControllerService) {
        super();
    }
    public override getBooleanStatus(): Observable<boolean> {
        return forkJoin([
            this.google.getGoogleSearchStatus(),
            this.tavily.getTavilySearchStatus(),
            this.brave.getBraveSearchStatus(),
            this.searxng.getSearxngSearchStatus()
        ]).pipe(map(statuses => statuses.some(s => s?.isSetup === true)));
    }
}

@Component({
    selector: "gebo-ai-web-search-wizard-component",
    templateUrl: "web-search-wizard.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIWebSearchWizardComponent") }]
})
export class GeboAIWebSearchWizardComponent extends BaseWizardSectionComponent {

    /** Which provider is currently active (has stored credentials), if any. */
    protected activeProvider?: WebSearchProviderId;
    /** The provider the admin is currently editing in the form. */
    protected selectedProvider: WebSearchProviderId = "tavily";
    protected editing = false;

    protected readonly providers: { id: WebSearchProviderId; label: string; needsEngineId?: boolean; needsBaseUrl?: boolean; keyOptional?: boolean; hint: string }[] = [
        { id: "google", label: "Google Programmable Search", needsEngineId: true, hint: "Legacy Google Custom Search JSON API (API key + Programmable Search Engine id)." },
        { id: "tavily", label: "Tavily", hint: "LLM-oriented search API returning extracted content. API key only." },
        { id: "brave", label: "Brave Search", hint: "Independent web index. Subscription token only." },
        { id: "searxng", label: "SearXNG (self-hosted)", needsBaseUrl: true, keyOptional: true, hint: "Self-hosted meta-search. Instance URL required; API key only if your instance is protected." }
    ];

    protected formGroup: FormGroup = new FormGroup({
        apiKey: new FormControl(""),
        customSearchEngineId: new FormControl(""),
        baseUrl: new FormControl("")
    });

    constructor(
        setupWizardComunicationService: SetupWizardComunicationService,
        private google: GoogleSearchConfigurationControllerService,
        private tavily: TavilySearchConfigurationControllerService,
        private brave: BraveSearchConfigurationControllerService,
        private searxng: SearxngSearchConfigurationControllerService) {
        super(setupWizardComunicationService);
    }

    protected get selectedMeta() {
        return this.providers.find(p => p.id === this.selectedProvider)!;
    }

    protected providerLabel(id?: WebSearchProviderId): string {
        return this.providers.find(p => p.id === id)?.label ?? "none";
    }

    public override reloadData(): void {
        this.loading = true;
        forkJoin([
            this.google.getGoogleSearchStatus(),
            this.tavily.getTavilySearchStatus(),
            this.brave.getBraveSearchStatus(),
            this.searxng.getSearxngSearchStatus()
        ]).subscribe({
            next: ([g, t, b, s]) => {
                this.activeProvider = g?.isSetup ? "google" : t?.isSetup ? "tavily" : b?.isSetup ? "brave" : s?.isSetup ? "searxng" : undefined;
                if (this.activeProvider) {
                    this.selectedProvider = this.activeProvider;
                }
            },
            complete: () => { this.loading = false; }
        });
    }

    protected startEditing(): void {
        this.editing = true;
        this.applyValidators();
    }

    protected onProviderChange(): void {
        this.applyValidators();
    }

    private applyValidators(): void {
        const meta = this.selectedMeta;
        this.formGroup.get("apiKey")!.setValidators(meta.keyOptional ? [] : [Validators.required]);
        this.formGroup.get("customSearchEngineId")!.setValidators(meta.needsEngineId ? [Validators.required] : []);
        this.formGroup.get("baseUrl")!.setValidators(meta.needsBaseUrl ? [Validators.required] : []);
        this.formGroup.get("apiKey")!.updateValueAndValidity();
        this.formGroup.get("customSearchEngineId")!.updateValueAndValidity();
        this.formGroup.get("baseUrl")!.updateValueAndValidity();
    }

    /** Deletes every stored credential of one provider (single-active enforcement). */
    private clearProvider(id: WebSearchProviderId): Observable<unknown> {
        switch (id) {
            case "google":
                return this.google.getGoogleSearchApiCredentials().pipe(switchMap(list => list.length
                    ? forkJoin(list.map(c => this.google.deleteGGoogleSearchApiCredentials(c))) : of([])));
            case "tavily":
                return this.tavily.getTavilySearchApiCredentials().pipe(switchMap(list => list.length
                    ? forkJoin(list.map(c => this.tavily.deleteGTavilySearchApiCredentials(c))) : of([])));
            case "brave":
                return this.brave.getBraveSearchApiCredentials().pipe(switchMap(list => list.length
                    ? forkJoin(list.map(c => this.brave.deleteGBraveSearchApiCredentials(c))) : of([])));
            case "searxng":
                return this.searxng.getSearxngSearchApiCredentials().pipe(switchMap(list => list.length
                    ? forkJoin(list.map(c => this.searxng.deleteGSearxngSearchApiCredentials(c))) : of([])));
        }
    }

    private insertSelected(): Observable<unknown> {
        const v = this.formGroup.value;
        switch (this.selectedProvider) {
            case "google":
                return this.google.fastInsertGoogleSearchApiCredentials({ apiKey: v.apiKey, customSearchEngineId: v.customSearchEngineId, enabled: true });
            case "tavily":
                return this.tavily.fastInsertTavilySearchApiCredentials({ apiKey: v.apiKey, enabled: true });
            case "brave":
                return this.brave.fastInsertBraveSearchApiCredentials({ apiKey: v.apiKey, enabled: true });
            case "searxng":
                return this.searxng.fastInsertSearxngSearchApiCredentials({ baseUrl: v.baseUrl, apiKey: v.apiKey, enabled: true });
        }
    }

    /**
     * Enforce single active: clear ALL providers, then store the selected one -
     * so exactly one web-search provider (and therefore one LLM tool) is active.
     */
    protected save(): void {
        this.loading = true;
        forkJoin([
            this.clearProvider("google"),
            this.clearProvider("tavily"),
            this.clearProvider("brave"),
            this.clearProvider("searxng")
        ]).pipe(switchMap(() => this.insertSelected())).subscribe({
            next: () => {
                this.editing = false;
                this.formGroup.reset();
                this.reloadData();
            },
            error: () => { this.loading = false; },
            complete: () => { this.loading = false; }
        });
    }

    /** Deactivate web search entirely (clears every provider). */
    protected clearAll(): void {
        this.loading = true;
        forkJoin([
            this.clearProvider("google"),
            this.clearProvider("tavily"),
            this.clearProvider("brave"),
            this.clearProvider("searxng")
        ]).subscribe({
            next: () => { this.activeProvider = undefined; this.reloadData(); },
            complete: () => { this.loading = false; }
        });
    }

    protected cancel(): void {
        this.editing = false;
        this.formGroup.reset();
    }
}
