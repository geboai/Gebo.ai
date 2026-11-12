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
import { GBaseDecisionModelChoice, GOpenAIDecisionModelConfig, OpenAiDecisionModelsConfigurationControllerService, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponentAutoDeleteCheck, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";
import { isValidUrl } from "../utils/url-ok";
import { alignChoosedModel } from "../utils/choosed-model-alignment";

/**
 * The editor of an OpenAI decision model configuration: a small dedicated model of the
 * OpenAI Decisions API answering yes/no, choice and score questions, used by the
 * services for the judgements a chat model is too slow or costly for (the relevance
 * of the retrieved fragments). The token selects the account, the model list comes
 * from the OpenAI models endpoint.
 */
@Component({
    selector: "gebo-ai-open-ai-decision-model-admin-component",
    templateUrl: "gebo-ai-openai-decision-model-admin.component.html",
    standalone: false, providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false },
        {
            provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIOpenAIDecisionModelAdminComponent),
            multi: false
        }]
})
export class GeboAIOpenAIDecisionModelAdminComponent extends BaseEntityEditingComponentAutoDeleteCheck<GOpenAIDecisionModelConfig> {
    protected override entityName: string = "GOpenAIDecisionModelConfig";

    allowedTypes: SecretInfo.SecretTypeEnum[] = [SecretInfo.SecretTypeEnum.TOKEN];

    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        modelTypeCode: new FormControl(),
        defaultModel: new FormControl(),
        choosedModel: new FormControl(),
        apiSecretCode: new FormControl()
    });

    private oldValue: any = {};

    modelChoicesData: GBaseDecisionModelChoice[] = [];

    identitiesObservable = this.secretControllerService.getSecretsByContextCode("openai");

    public newSecretAction = newSecretActionRequest("openai", this.entityName, this.entity);

    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private decisionModelConfigService: OpenAiDecisionModelsConfigurationControllerService,
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
    }

    /** The decision models the account offers, once a token is chosen. */
    private loadModels(newValue: { baseUrl?: string, apiSecretCode?: string }) {
        this.loadingRelatedBackend = true;
        this.decisionModelConfigService.getOpenAIDecisionModels(newValue).subscribe({
            next: (r) => {
                this.updateLastOperationStatus(r as any);
                this.modelChoicesData = alignChoosedModel(this.formGroup, r.result);
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    protected override onLoadedPersistentData(actualValue: GOpenAIDecisionModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("decision-OpenAI");
        // the model list loads once, through the value change the form patch emits (see the constructor)
    }

    protected override onNewData(actualValue: GOpenAIDecisionModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("decision-OpenAI");
    }

    override findByCode(code: string): Observable<GOpenAIDecisionModelConfig | null> {
        return this.decisionModelConfigService.findOpenAIDecisionModelConfigByCode(code);
    }

    override save(value: GOpenAIDecisionModelConfig): Observable<GOpenAIDecisionModelConfig> {
        return this.decisionModelConfigService.updateOpenAIDecisionModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    override insert(value: GOpenAIDecisionModelConfig): Observable<GOpenAIDecisionModelConfig> {
        return this.decisionModelConfigService.insertOpenAIDecisionModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    override delete(value: GOpenAIDecisionModelConfig): Observable<boolean> {
        return this.decisionModelConfigService.deleteOpenAIDecisionModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }));
    }
}
