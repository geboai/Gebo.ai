/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

/**
 * Step 2 of the guided setup: choose a provider and its credentials. It reuses the two existing
 * credential blocks - the provider picker + api key of the easy tab, and the base url +
 * "requires credentials" fields of the expert model-type dialog - so on-premise providers
 * (ollama, vLLM, generic OpenAI) that need a base url work here as well as the cloud ones. It
 * emits the chosen provider and, whenever the credentials settle, the secret and base url the
 * parent uses to download the catalogues.
 */
import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { GeboFastLlmsSetupControllerService, LLMSSetupConfiguration, LLMSSetupConfigurationData, SecretInfo } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboAIValidators, IOperationStatus } from "@Gebo.ai/reusable-ui";
import { Observable, of } from "rxjs";

@Component({
    selector: "gebo-ai-llms-easy-provider-step",
    templateUrl: "step-provider.component.html",
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboSetupWizardsModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("LLMSEasyProviderStepComponent"), multi: false }
    ]
})
export class LLMSEasyProviderStepComponent implements OnChanges {
    @Input() configuration?: LLMSSetupConfigurationData;
    @Input() selectedVendor?: LLMSSetupConfiguration;
    @Output() providerChanged: EventEmitter<LLMSSetupConfiguration | undefined> = new EventEmitter();
    @Output() credentialsChanged: EventEmitter<{ secretId?: string, baseUrl?: string }> = new EventEmitter();

    protected vendors: LLMSSetupConfiguration[] = [];
    protected vendor?: LLMSSetupConfiguration;

    protected formGroup: FormGroup = new FormGroup({
        vendorId: new FormControl(),
        baseUrl: new FormControl(),
        requireApiKeyAniway: new FormControl(false),
        secretId: new FormControl()
    });

    /** Api key context / description reflect the chosen vendor, exactly as the easy tab does. */
    protected secretContext?: string;
    protected secretDescription?: string;

    /** Validates the entered credentials against the provider, downloading its first catalogue. */
    protected validateCredentials: (credentials: SecretInfo) => Observable<IOperationStatus<any>> = (credentials: SecretInfo) => {
        if (!this.vendor?.parentModel.vendorId || !credentials.code) {
            const rv: IOperationStatus<any> = { hasErrorMessages: true, messages: [] };
            return of(rv);
        }
        return this.setupService.verifyVendorCredentialsAndDownloadModels({
            secretId: credentials.code as string,
            vendorId: this.vendor.parentModel.vendorId,
            baseUrl: this.formGroup.controls["baseUrl"].value ?? undefined
        });
    };

    constructor(private setupService: GeboFastLlmsSetupControllerService) {
        this.formGroup.controls["vendorId"].valueChanges.subscribe({ next: (vendorId) => this.onVendorSelected(vendorId) });
        this.formGroup.controls["secretId"].valueChanges.subscribe({ next: () => this.emitCredentials() });
        this.formGroup.controls["baseUrl"].valueChanges.subscribe({
            next: () => {
                if (this.formGroup.controls["baseUrl"].invalid) return;
                this.emitCredentials();
            }
        });
        this.formGroup.controls["requireApiKeyAniway"].valueChanges.subscribe({ next: () => this.applyCredentialGating() });
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes["configuration"]) {
            // Every provider is offered: the guided flow applies through createLLMS, which does not
            // require the provider to declare autoconfigure support.
            this.vendors = this.configuration?.configurations ?? [];
        }
        if (changes["selectedVendor"] && this.selectedVendor && this.formGroup.controls["vendorId"].value !== this.selectedVendor.parentModel.vendorId) {
            this.formGroup.controls["vendorId"].setValue(this.selectedVendor.parentModel.vendorId);
        }
    }

    private onVendorSelected(vendorId?: string): void {
        this.vendor = this.vendors.find(v => v.parentModel.vendorId === vendorId);
        this.formGroup.controls["secretId"].setValue(undefined, { emitEvent: false });
        this.secretContext = this.vendor?.parentModel.apiKeySecretContext ?? "llm-vendor";
        this.secretDescription = vendorId ? vendorId + " credentials" : "credentials";

        const baseCtrl = this.formGroup.controls["baseUrl"];
        if (this.vendor?.parentModel.requiresCustomUrl === true) {
            baseCtrl.setValidators(GeboAIValidators.baseUrl(true));
            baseCtrl.setValue(this.vendor.parentModel.defaultCustomUrl ?? "", { emitEvent: false });
        } else {
            baseCtrl.clearValidators();
            baseCtrl.setValue(undefined, { emitEvent: false });
        }
        baseCtrl.updateValueAndValidity({ emitEvent: false });

        this.providerChanged.emit(this.vendor);
        this.applyCredentialGating();
    }

    /** The api key control is only usable once a base url is valid (when the provider needs one). */
    private applyCredentialGating(): void {
        const info = this.vendor?.parentModel;
        const requiresApiKey = info?.requiresApiKey === true || this.formGroup.controls["requireApiKeyAniway"].value === true;
        const urlKnown = info?.requiresCustomUrl !== true || this.formGroup.controls["baseUrl"].valid;
        const ctrl = this.formGroup.controls["secretId"];
        if (requiresApiKey && urlKnown) {
            ctrl.enable({ emitEvent: false });
        } else {
            ctrl.disable({ emitEvent: false });
        }
        this.emitCredentials();
    }

    private emitCredentials(): void {
        const baseUrl = this.formGroup.controls["baseUrl"].valid ? (this.formGroup.controls["baseUrl"].value ?? undefined) : undefined;
        this.credentialsChanged.emit({ secretId: this.formGroup.controls["secretId"].value ?? undefined, baseUrl });
    }
}
