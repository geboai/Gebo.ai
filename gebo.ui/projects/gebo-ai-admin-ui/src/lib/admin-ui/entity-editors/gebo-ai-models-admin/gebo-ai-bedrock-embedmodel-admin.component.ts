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
import { GBaseChatModelChoice, GBedrockEmbeddingModelConfig, BedrockEmbeddingModelsConfigurationControllerService, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponentAutoDeleteCheck, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";

/**
 * Component for administering AWS Bedrock embedding model configurations
 * (Amazon Titan / Cohere).
 */
@Component({
    selector: "gebo-ai-bedrock-embed-model-admin-component",
    templateUrl: "gebo-ai-bedrock-embedmodel-admin.component.html",
    standalone: false, providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIBedrockEmbedModelAdminComponent), multi: false }
    ]
})
export class GeboAIBedrockEmbedModelAdminComponent extends BaseEntityEditingComponentAutoDeleteCheck<GBedrockEmbeddingModelConfig> {
    protected override entityName: string = "GBedrockEmbeddingModelConfig";

    allowedTypes: SecretInfo.SecretTypeEnum[] = [SecretInfo.SecretTypeEnum.USERNAMEPASSWORD];

    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        modelTypeCode: new FormControl(),
        region: new FormControl(),
        defaultModel: new FormControl(),
        choosedModel: new FormControl(),
        apiSecretCode: new FormControl()
    });

    private oldValue: any = {};

    modelChoicesData: GBaseChatModelChoice[] = [];

    identitiesObservable = this.secretControllerService.getSecretsByContextCode("aws-bedrock");

    public newSecretAction = newSecretActionRequest("aws-bedrock", this.entityName, this.entity);

    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private bedrockEmbedModelConfigService: BedrockEmbeddingModelsConfigurationControllerService,
        private secretControllerService: SecretsControllerService,
        confirmService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmService, geboUIActionRoutingService, outputForwardingService);
        this.formGroup.valueChanges.subscribe(newValue => {
            if (!newValue.region && !newValue.apiSecretCode) {
                this.modelChoicesData = [];
            } else if (newValue.region !== this.oldValue.region || newValue.apiSecretCode !== this.oldValue.apiSecretCode) {
                this.loadModels(newValue);
            }
            this.oldValue = newValue;
        });
    }

    private loadModels(newValue: GBedrockEmbeddingModelConfig) {
        this.loadingRelatedBackend = true;
        this.bedrockEmbedModelConfigService.getBedrockEmbeddingModels(newValue).subscribe({
            next: (r) => {
                this.updateLastOperationStatus(r as any);
                this.modelChoicesData = r.result ? r.result : [];
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    protected override onLoadedPersistentData(actualValue: GBedrockEmbeddingModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("embedding-aws-bedrock");
        this.loadModels(actualValue);
    }

    protected override onNewData(actualValue: GBedrockEmbeddingModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("embedding-aws-bedrock");
    }

    override findByCode(code: string): Observable<GBedrockEmbeddingModelConfig | null> {
        return this.bedrockEmbedModelConfigService.findBedrockEmbeddingModelConfigByCode(code);
    }

    override save(value: GBedrockEmbeddingModelConfig): Observable<GBedrockEmbeddingModelConfig> {
        return this.bedrockEmbedModelConfigService.updateBedrockEmbeddingModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    override insert(value: GBedrockEmbeddingModelConfig): Observable<GBedrockEmbeddingModelConfig> {
        return this.bedrockEmbedModelConfigService.insertBedrockEmbeddingModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    override delete(value: GBedrockEmbeddingModelConfig): Observable<boolean> {
        return this.bedrockEmbedModelConfigService.deleteBedrockEmbeddingModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }));
    }
}
