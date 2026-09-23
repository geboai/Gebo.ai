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
import { GBedrockRankerModelChoice, GBedrockRankerModelConfig, BedrockRankerModelsConfigurationControllerService, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponentAutoDeleteCheck, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";

/**
 * Component for administering AWS Bedrock reranking model configurations
 * (Amazon Rerank / Cohere Rerank).
 */
@Component({
    selector: "gebo-ai-bedrock-ranker-admin-component",
    templateUrl: "gebo-ai-bedrock-ranker-admin.component.html",
    standalone: false, providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIBedrockRankerAdminComponent), multi: false }
    ]
})
export class GeboAIBedrockRankerAdminComponent extends BaseEntityEditingComponentAutoDeleteCheck<GBedrockRankerModelConfig> {
    protected override entityName: string = "GBedrockRankerModelConfig";

    allowedTypes: SecretInfo.SecretTypeEnum[] = [SecretInfo.SecretTypeEnum.AWSCONNECTION];

    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        modelTypeCode: new FormControl(),
        defaultModel: new FormControl(),
        choosedModel: new FormControl(),
        apiSecretCode: new FormControl(),
        contextLength: new FormControl(),
        maxDocumentsPerRequest: new FormControl(),
        maxDocumentTokens: new FormControl(),
        responseReserveTokens: new FormControl()
    });

    modelChoicesData: GBedrockRankerModelChoice[] = [];
    identitiesObservable = this.secretControllerService.getSecretsByContextCode("aws-bedrock");
    public newSecretAction = newSecretActionRequest("aws-bedrock", this.entityName, this.entity);

    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private bedrockRankerModelConfigService: BedrockRankerModelsConfigurationControllerService,
        private secretControllerService: SecretsControllerService,
        confirmService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmService, geboUIActionRoutingService, outputForwardingService);
        this.manageOperationStatus = true;
        this.loadModels();
    }

    private loadModels() {
        this.loadingRelatedBackend = true;
        this.bedrockRankerModelConfigService.getBedrockRankerModels({}).subscribe({
            next: (r) => {
                this.updateLastOperationStatus(r as any);
                this.modelChoicesData = r.result ? r.result : [];
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    protected override onLoadedPersistentData(actualValue: GBedrockRankerModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("ranker-aws-bedrock");
    }

    protected override onNewData(actualValue: GBedrockRankerModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("ranker-aws-bedrock");
    }

    override findByCode(code: string): Observable<GBedrockRankerModelConfig | null> {
        return this.bedrockRankerModelConfigService.findBedrockRankerModelConfigByCode(code);
    }

    override save(value: GBedrockRankerModelConfig): Observable<GBedrockRankerModelConfig> {
        return this.bedrockRankerModelConfigService.updateBedrockRankerModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    override insert(value: GBedrockRankerModelConfig): Observable<GBedrockRankerModelConfig> {
        return this.bedrockRankerModelConfigService.insertBedrockRankerModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    override delete(value: GBedrockRankerModelConfig): Observable<boolean> {
        return this.bedrockRankerModelConfigService.deleteBedrockRankerModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }));
    }
}
