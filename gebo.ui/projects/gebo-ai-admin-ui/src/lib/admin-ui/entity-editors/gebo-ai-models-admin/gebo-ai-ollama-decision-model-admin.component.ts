/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GBaseDecisionModelChoice, GOllamaDecisionModelConfig, OllamaDecisionModelsConfigurationControllerService, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponentAutoDeleteCheck, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";
import { isValidUrl } from "../utils/url-ok";
import { alignChoosedModel } from "../utils/choosed-model-alignment";

/**
 * The editor of an Ollama decision model configuration: a model with the "decision"
 * capability (the d1-omni family) served by a local or lan Ollama server through its
 * System One endpoint. The model list is read from the server; only the models it
 * reports as decision models are offered, so the server must be recent enough (0.40.4
 * or later) to report capabilities.
 */
@Component({
    selector: "gebo-ai-ollama-decision-model-admin-component",
    templateUrl: "gebo-ai-ollama-decision-model-admin.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false }, {
        provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIOllamaDecisionModelAdminComponent),
        multi: false
    }]
})
export class GeboAIOllamaDecisionModelAdminComponent extends BaseEntityEditingComponentAutoDeleteCheck<GOllamaDecisionModelConfig> {
    protected override entityName: string = "GOllamaDecisionModelConfig";

    allowedTypes: SecretInfo.SecretTypeEnum[] = [SecretInfo.SecretTypeEnum.TOKEN];

    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        baseUrl: new FormControl(),
        choosedModel: new FormControl(),
        modelTypeCode: new FormControl(),
        defaultModel: new FormControl(),
        apiSecretCode: new FormControl()
    });

    private oldValue: any = {};

    modelChoicesData: GBaseDecisionModelChoice[] = [];

    identitiesObservable = this.secretControllerService.getSecretsByContextCode("ollama");

    public newSecretAction = newSecretActionRequest("ollama", this.entityName, this.entity);

    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private decisionModelConfigService: OllamaDecisionModelsConfigurationControllerService,
        private secretControllerService: SecretsControllerService,
        confirmService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmService, geboUIActionRoutingService, outputForwardingService);
        this.formGroup.valueChanges.subscribe(newValue => {
            if (!newValue.baseUrl && !newValue.apiSecretCode) {
                this.modelChoicesData = [];
            } else if ((newValue.baseUrl !== this.oldValue.baseUrl && isValidUrl(newValue.baseUrl)) || newValue.apiSecretCode !== this.oldValue.apiSecretCode) {
                this.loadModels(newValue);
            }
            this.oldValue = newValue;
        });
        this.manageOperationStatus = true;
    }

    /** The decision models the Ollama server serves at the base url. */
    private loadModels(newValue: { baseUrl?: string, apiSecretCode?: string }) {
        this.loadingRelatedBackend = true;
        this.decisionModelConfigService.getOllamaDecisionModels(newValue).subscribe({
            next: (r) => {
                this.updateLastOperationStatus(r as any);
                this.modelChoicesData = alignChoosedModel(this.formGroup, r.result);
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    protected override onLoadedPersistentData(actualValue: GOllamaDecisionModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("decision-ollama");
        // the model list loads once, through the value change the form patch emits (see the constructor)
    }

    protected override onNewData(actualValue: GOllamaDecisionModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("decision-ollama");
    }

    override findByCode(code: string): Observable<GOllamaDecisionModelConfig | null> {
        return this.decisionModelConfigService.findOllamaDecisionModelConfigByCode(code);
    }

    override save(value: GOllamaDecisionModelConfig): Observable<GOllamaDecisionModelConfig> {
        return this.decisionModelConfigService.updateOllamaDecisionModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    override insert(value: GOllamaDecisionModelConfig): Observable<GOllamaDecisionModelConfig> {
        return this.decisionModelConfigService.insertOllamaDecisionModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    override delete(value: GOllamaDecisionModelConfig): Observable<boolean> {
        return this.decisionModelConfigService.deleteOllamaDecisionModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }));
    }
}
