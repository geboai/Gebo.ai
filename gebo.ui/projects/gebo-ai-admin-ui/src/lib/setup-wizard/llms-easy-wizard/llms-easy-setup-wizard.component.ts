/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

/**
 * Root of the guided ("easy") LLMs setup wizard. It replaces, on the same setup section, the
 * flat easy/expert tabs of {@link LLMSetupWizardComponent} with a four step stepper:
 *
 *  0 - Intro:    what the required and suggested model classes are, and their current setting.
 *  1 - Provider: pick a provider and its credentials (base url / api key), then download catalogues.
 *  2 - Models:   per class, keep/modify/skip and choose a model, then apply through createLLMS.
 *  3 - Summary:  the active configuration per class; finishing closes the wizard section.
 *
 * The component owns all shared state (provider, credentials, downloaded catalogues, per class
 * decisions) and the backend calls; the four step components are presentational and talk back
 * through outputs. All backend contracts, DTOs and the catalogue/merge logic are the same the
 * original easy setup uses, so the two flows stay interchangeable at the API level.
 */
import { Component } from "@angular/core";
import {
    ComponentLLMSStatus, GBaseModelChoice, GeboFastLlmsSetupControllerService, GUserMessage,
    LLMCreateModelData, LLMSSetupConfiguration, LLMSSetupConfigurationData
} from "@Gebo.ai/gebo-ai-rest-api";
import { BaseWizardSectionComponent, fieldHostComponentName, GEBO_AI_FIELD_HOST, GeboAITranslationService, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { MenuItem, ToastMessageOptions } from "primeng/api";
import { forkJoin, Observable } from "rxjs";
import { MODEL_CLASSES, ModelChoiceOption, ModelClassDecision, ModelClassDescriptor } from "./model-classes";

/** A chosen model the provider no longer offers, with the live choices to replace it from. */
interface ModelResolution {
    decision: ModelClassDecision;
    requestedModelCode?: string;
    availableChoices: ModelChoiceOption[];
    chosenCode?: string;
}

enum WizardStep { INTRO = 0, PROVIDER = 1, MODELS = 2, SUMMARY = 3 }

@Component({
    selector: "gebo-ai-llms-easy-setup-wizard",
    templateUrl: "llms-easy-setup-wizard.component.html",
    standalone: false,
    // Give each gebo-ai-field label the weight of a section header, so every model class in the
    // guided wizard reads as its own separated block. Scoped with :host so it only affects the
    // fields rendered inside this wizard (and its step components), never gebo-ai-field elsewhere.
    styles: [`
        :host ::ng-deep gebo-ai-field > div > label {
            display: block;
            width: 100%;
            margin-top: var(--gebo-space-2, 0.5rem);
            padding-bottom: var(--gebo-space-1, 0.35rem);
            border-bottom: 2px solid var(--gebo-primary-500, #3b82f6);
            color: var(--gebo-text-strong, #111827);
            font-size: 1.05rem;
            font-weight: 600;
            letter-spacing: 0.01em;
        }
    `],
    providers: [{ provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("LLMSEasySetupWizardComponent") }]
})
export class LLMSEasySetupWizardComponent extends BaseWizardSectionComponent {
    protected readonly Step = WizardStep;
    protected activeStep: WizardStep = WizardStep.INTRO;
    protected steps: MenuItem[] = [];

    protected status?: ComponentLLMSStatus;
    protected actualConfiguration?: LLMSSetupConfigurationData;

    /** Provider chosen in step 1 (its whole config: vendor info + library presets + runtime configs). */
    protected selectedVendor?: LLMSSetupConfiguration;
    protected secretId?: string;
    protected baseUrl?: string;

    /** One decision per model class, rebuilt whenever the provider or its catalogues change. */
    protected decisions: ModelClassDecision[] = [];
    /** Number of catalogue lookups still running: navigation off this step waits for them. */
    protected lookupsRunning: number = 0;
    /** Models the provider refused on apply, offered back for replacement. */
    protected resolutions: ModelResolution[] = [];

    constructor(setupWizardComunicationService: SetupWizardComunicationService,
        private setupService: GeboFastLlmsSetupControllerService,
        private translationService: GeboAITranslationService) {
        super(setupWizardComunicationService);
        // p-steps renders the labels of its model, which the [gebo-ai-label] attribute directive does
        // not reach (it reads element attributes, not the item model). The step labels are translated
        // programmatically through translateMenuItems, keyed by each item id under this entity.
        this.steps = [
            { id: "IntroStep", label: "Model classes" },
            { id: "ProviderStep", label: "Provider" },
            { id: "ModelsStep", label: "Models" },
            { id: "SummaryStep", label: "Summary" }
        ];
    }

    public override ngOnInit(): void {
        super.ngOnInit();
        this.translationService.translateMenuItems("GeboSetupWizardsModule", fieldHostComponentName("LLMSEasySetupWizardComponent").getEntityName(), this.steps)
            .subscribe({ next: (items) => { this.steps = items; } });
    }

    public override reloadData(): void {
        this.loading = true;
        const observables: [Observable<ComponentLLMSStatus>, Observable<LLMSSetupConfigurationData>] =
            [this.setupService.getLLMSSetupStatus(), this.setupService.getActualLLMSConfiguration()];
        forkJoin(observables).subscribe({
            next: (value) => {
                this.status = value[0];
                this.actualConfiguration = value[1];
                this.isSetupCompleted = this.status?.isSetup === true;
                // A provider may already be selected (returning to step 1 after an apply): keep its
                // reference fresh so the runtime configs and the "already configured" flags reflect
                // what the just-run creation produced.
                if (this.selectedVendor) {
                    const vendorId = this.selectedVendor.parentModel.vendorId;
                    this.selectedVendor = this.actualConfiguration?.configurations?.find(x => x.parentModel.vendorId === vendorId);
                    if (this.selectedVendor) {
                        this.rebuildDecisions();
                    }
                }
            },
            complete: () => { this.loading = false; }
        });
    }

    protected get activeIndex(): number { return this.activeStep; }

    // ---- navigation ---------------------------------------------------------

    protected get canGoForward(): boolean {
        if (this.lookupsRunning > 0 || this.loading) return false;
        switch (this.activeStep) {
            case WizardStep.PROVIDER: return this.providerReady;
            case WizardStep.SUMMARY: return true;
            default: return true;
        }
    }
    protected get forwardLabelStep(): WizardStep { return this.activeStep; }

    protected get providerReady(): boolean {
        if (!this.selectedVendor) return false;
        const info = this.selectedVendor.parentModel;
        const urlOk = info.requiresCustomUrl !== true || (this.baseUrl != null && this.baseUrl.length > 0);
        const keyOk = info.requiresApiKey !== true || (this.secretId != null && this.secretId.length > 0);
        return urlOk && keyOk;
    }

    protected goBack(): void {
        if (this.activeStep > WizardStep.INTRO) this.activeStep--;
    }

    protected goForward(): void {
        if (!this.canGoForward) return;
        if (this.activeStep === WizardStep.MODELS) {
            this.applyDecisions();
            return;
        }
        if (this.activeStep === WizardStep.SUMMARY) {
            this.closeWizard();
            return;
        }
        this.activeStep++;
    }

    // ---- step 1: provider + credentials -------------------------------------

    protected onProviderChanged(vendor?: LLMSSetupConfiguration): void {
        this.selectedVendor = vendor;
        this.secretId = undefined;
        this.baseUrl = vendor?.parentModel.requiresCustomUrl === true ? vendor.parentModel.defaultCustomUrl : undefined;
        this.resolutions = [];
        this.rebuildDecisions();
    }

    protected onCredentialsChanged(event: { secretId?: string, baseUrl?: string }): void {
        this.secretId = event.secretId;
        if (event.baseUrl !== undefined) this.baseUrl = event.baseUrl;
        this.rebuildDecisions();
        this.runCatalogueLookups();
    }

    // ---- decisions ----------------------------------------------------------

    /**
     * Builds one decision per model class from the selected provider's library and the current
     * status. Required classes not yet configured are pre-enabled; suggested classes the provider
     * offers are pre-enabled too (the user can switch any off); classes already configured start
     * off, shown as a modifiable existing setting. Choices start from the library presets and are
     * replaced by the provider catalogue once it is downloaded.
     */
    private rebuildDecisions(): void {
        if (!this.selectedVendor) { this.decisions = []; return; }
        const vendor = this.selectedVendor;
        this.decisions = MODEL_CLASSES.map(descriptor => {
            const preset = vendor.libraryModel.find(p => p.type === descriptor.type);
            const offered = preset != null;
            const alreadyConfigured = descriptor.statusSetup(this.status) === true;
            const currentCode = descriptor.statusCode(this.status);
            const currentProvider = descriptor.statusProvider(this.status);
            const preselect = this.preselectCode(descriptor);
            const decision: ModelClassDecision = {
                descriptor, offered, alreadyConfigured, currentCode, currentProvider,
                enabled: offered && !alreadyConfigured,
                modifying: false,
                chosenCode: preselect,
                choices: this.libraryChoices(descriptor),
                enableAllFunctions: false
            };
            return decision;
        });
    }

    /** The preset code preselected for a class: the INTERNAL_SERVICES choice for the service slot, the defaultChoice otherwise. */
    private preselectCode(descriptor: ModelClassDescriptor): string | undefined {
        const preset = this.selectedVendor?.libraryModel.find(p => p.type === descriptor.type);
        if (!preset?.choices?.length) return undefined;
        if (descriptor.id === "SERVICE") {
            const service = preset.choices.find(c => c.uses?.some(u => u === "INTERNAL_SERVICES"));
            if (service?.code) return service.code;
        }
        const def = preset.choices.find(c => c.defaultChoice === true);
        return (def ?? preset.choices[0])?.code;
    }

    /** The library presets of a class, as combo options (used until the provider is queried). */
    private libraryChoices(descriptor: ModelClassDescriptor): ModelChoiceOption[] {
        const preset = this.selectedVendor?.libraryModel.find(p => p.type === descriptor.type);
        let choices = preset?.choices ?? [];
        if (descriptor.id === "SERVICE") {
            // The service slot is a chat model; prefer the ones the library tags for internal
            // services, but fall back to the whole chat list so the user is never left empty.
            const service = choices.filter(c => c.uses?.some(u => u === "INTERNAL_SERVICES"));
            if (service.length) choices = service;
        } else if (descriptor.id === "CHAT") {
            const chat = choices.filter(c => c.uses?.some(u => u === "CHAT"));
            if (chat.length) choices = chat;
        }
        return choices.map(c => ({ code: c.code, description: c.description || c.code, fromPreset: true }));
    }

    /**
     * Downloads, once per distinct backend model type, the models the provider really offers, and
     * hands them to every decision of that type (the two chat slots share the CHAT catalogue). A
     * type the library excludes from lookup, or a lookup the provider refuses, keeps the library
     * presets: a catalogue we could not read proves nothing about a model.
     */
    private runCatalogueLookups(): void {
        if (!this.selectedVendor) return;
        const info = this.selectedVendor.parentModel;
        if (info.requiresApiKey === true && !this.secretId) return;
        const requestedVendorId = info.vendorId;
        const doneTypes: LLMCreateModelData.TypeEnum[] = [];
        this.selectedVendor.libraryModel.filter(p => p.doModelsLookup === true && p.serviceHandler).forEach(preset => {
            const type = preset.type;
            if (!type || doneTypes.indexOf(type) >= 0) return;
            doneTypes.push(type);
            this.lookupsRunning++;
            this.setupService.verifyCredentialsAndDownloadModels({
                type: type, serviceHandler: preset.serviceHandler as string,
                secretId: this.secretId, baseUrl: this.baseUrl
            }).subscribe({
                next: (operationStatus) => {
                    // A late answer from a provider that has since been switched must not land here.
                    if (requestedVendorId !== this.selectedVendor?.parentModel.vendorId) return;
                    if (operationStatus.hasErrorMessages !== true && operationStatus.result) {
                        this.applyLookedUpModels(type, operationStatus.result);
                    }
                },
                error: () => { this.lookupsRunning--; },
                complete: () => { this.lookupsRunning--; }
            });
        });
    }

    /** Fills every decision of a type with what the provider offers, revoking a preselection it dropped. */
    private applyLookedUpModels(type: LLMCreateModelData.TypeEnum, models: GBaseModelChoice[]): void {
        this.decisions.filter(d => d.descriptor.type === type).forEach(decision => {
            decision.choices = this.describeModels(models, decision);
            if (decision.chosenCode && !models.some(m => m.code?.toLowerCase() === decision.chosenCode?.toLowerCase())) {
                // The preselected model is gone from the provider: clear it and, when the class was
                // meant to be applied, force it open so the user picks a replacement.
                decision.chosenCode = undefined;
                if (decision.enabled) decision.modifying = true;
            }
        });
    }

    /** Merges the provider catalogue with the library: library entries keep their description and lead. */
    private describeModels(models: GBaseModelChoice[], decision: ModelClassDecision): ModelChoiceOption[] {
        const library = this.selectedVendor?.libraryModel.find(p => p.type === decision.descriptor.type)?.choices ?? [];
        const described: ModelChoiceOption[] = models.map(model => {
            const lib = library.find(x => x.code && model.code && x.code.toLowerCase() === (model.code as string).toLowerCase());
            return { code: model.code, description: lib?.description || model.description || model.code, fromPreset: lib ? true : false };
        });
        return [...described.filter(x => x.fromPreset), ...described.filter(x => !x.fromPreset)];
    }

    // ---- step 3: apply ------------------------------------------------------

    /**
     * Turns the decisions into the LLMCreateModelData list and sends it. Only classes the user
     * enabled and that are an actual change are sent: a new class, or an existing one the user
     * opened to modify. Classes left untouched or switched off produce nothing.
     */
    private applyDecisions(): void {
        const requests = this.decisions
            .filter(d => d.enabled && d.chosenCode && d.offered && (!d.alreadyConfigured || d.modifying))
            .map(d => this.buildCreateModelData(d))
            .filter((x): x is LLMCreateModelData => x != null);
        if (requests.length === 0) {
            // Nothing to change: go straight to the summary, honouring "don't set anything now".
            this.userMessages = [];
            this.activeStep = WizardStep.SUMMARY;
            return;
        }
        this.sendCreate(requests);
    }

    private buildCreateModelData(decision: ModelClassDecision): LLMCreateModelData | undefined {
        const preset = this.selectedVendor?.libraryModel.find(p => p.type === decision.descriptor.type);
        if (!preset || !decision.chosenCode) return undefined;
        const isService = decision.descriptor.id === "SERVICE";
        return {
            modelCode: decision.chosenCode,
            type: decision.descriptor.type,
            uses: decision.descriptor.uses,
            serviceHandler: preset.serviceHandler,
            doModelsLookup: preset.doModelsLookup === true,
            // The service (internal) chat model is always non-default; every other class is set as
            // the default of its kind, which also overrides a previous default when modifying.
            setAsDefaultModel: isService ? false : true,
            secretId: this.secretId,
            baseUrl: this.baseUrl,
            enableAllFunctions: decision.descriptor.id === "CHAT" ? decision.enableAllFunctions === true : false
        };
    }

    private sendCreate(requests: LLMCreateModelData[]): void {
        this.loading = true;
        this.setupService.createLLMS(requests).subscribe({
            next: (operationStatus) => {
                const unresolved = operationStatus?.result?.unresolved ?? [];
                this.buildResolutions(unresolved, requests);
                // error===true messages block the step; warnings (e.g. an unresolved model) are shown
                // too, next to the resolution picker they refer to.
                this.userMessages = (operationStatus?.messages ?? []) as ToastMessageOptions[];
                if (operationStatus?.hasErrorMessages === true) {
                    return; // stay on the step, surface the errors
                }
                if (unresolved.length > 0) {
                    return; // stay on the step so the user replaces the unavailable models
                }
                // Applied cleanly: refresh status/config and move to the summary.
                this.activeStep = WizardStep.SUMMARY;
                this.reloadData();
            },
            complete: () => { this.loading = false; }
        });
    }

    private buildResolutions(unresolved: { type?: LLMCreateModelData.TypeEnum, uses?: Array<LLMCreateModelData.UsesEnum>, requestedModelCode?: string, availableChoices?: GBaseModelChoice[] }[], requests: LLMCreateModelData[]): void {
        this.resolutions = unresolved.map(u => {
            const isService = (u.uses ?? []).some(x => x === "INTERNAL_SERVICES");
            const decision = this.decisions.find(d => d.descriptor.type === u.type
                && (d.descriptor.id === "SERVICE") === isService) ?? this.decisions[0];
            return {
                decision,
                requestedModelCode: u.requestedModelCode,
                availableChoices: (u.availableChoices ?? []).map(c => ({ code: c.code, description: c.description || c.code })),
                chosenCode: undefined
            } as ModelResolution;
        });
    }

    protected get canResolve(): boolean {
        return this.resolutions.length > 0 && this.resolutions.some(r => r.chosenCode != null);
    }

    protected resolveAndApply(): void {
        const requests: LLMCreateModelData[] = [];
        this.resolutions.forEach(r => {
            if (r.chosenCode) {
                const built = this.buildCreateModelData({ ...r.decision, chosenCode: r.chosenCode });
                if (built) requests.push(built);
            }
        });
        this.resolutions = [];
        if (requests.length) this.sendCreate(requests);
    }
}
