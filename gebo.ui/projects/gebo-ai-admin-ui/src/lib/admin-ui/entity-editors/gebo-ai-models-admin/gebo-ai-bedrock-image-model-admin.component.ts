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
import { GBedrockImageModelChoice, GBedrockImageModelConfig, BedrockImageModelsConfigurationControllerService, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponentAutoDeleteCheck, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";

/**
 * Component for administering AWS Bedrock image generation model configurations
 * (Amazon Nova Canvas / Titan Image / Stability).
 */
@Component({
    selector: "gebo-ai-bedrock-image-model-admin-component",
    templateUrl: "gebo-ai-bedrock-image-model-admin.component.html",
    standalone: false, providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIBedrockImageModelAdminComponent), multi: false }
    ]
})
export class GeboAIBedrockImageModelAdminComponent extends BaseEntityEditingComponentAutoDeleteCheck<GBedrockImageModelConfig> {
    protected override entityName: string = "GBedrockImageModelConfig";

    allowedTypes: SecretInfo.SecretTypeEnum[] = [SecretInfo.SecretTypeEnum.AWSCONNECTION];

    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        modelTypeCode: new FormControl(),
        defaultModel: new FormControl(),
        choosedModel: new FormControl(),
        apiSecretCode: new FormControl(),
        height: new FormControl(),
        width: new FormControl(),
        cfgScale: new FormControl(),
        seed: new FormControl()
    });

    private oldValue: any = {};
    modelChoicesData: GBedrockImageModelChoice[] = [];
    identitiesObservable = this.secretControllerService.getSecretsByContextCode("aws-bedrock");
    public newSecretAction = newSecretActionRequest("aws-bedrock", this.entityName, this.entity);

    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private bedrockImageModelConfigService: BedrockImageModelsConfigurationControllerService,
        private secretControllerService: SecretsControllerService,
        confirmService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmService, geboUIActionRoutingService, outputForwardingService);
        this.formGroup.valueChanges.subscribe(newValue => {
            if (!newValue.apiSecretCode) {
                this.modelChoicesData = [];
            } else if (newValue.apiSecretCode !== this.oldValue.apiSecretCode) {
                this.loadModels(newValue);
            }
            this.oldValue = newValue;
        });
    }

    private loadModels(newValue: GBedrockImageModelConfig) {
        this.loadingRelatedBackend = true;
        this.bedrockImageModelConfigService.getBedrockImageModels(newValue).subscribe({
            next: (r) => {
                this.updateLastOperationStatus(r as any);
                this.modelChoicesData = r.result ? r.result : [];
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    protected override onLoadedPersistentData(actualValue: GBedrockImageModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("image-generation-aws-bedrock");
        this.loadModels(actualValue);
    }

    protected override onNewData(actualValue: GBedrockImageModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("image-generation-aws-bedrock");
    }

    override findByCode(code: string): Observable<GBedrockImageModelConfig | null> {
        return this.bedrockImageModelConfigService.findBedrockImageModelConfigByCode(code);
    }

    override save(value: GBedrockImageModelConfig): Observable<GBedrockImageModelConfig> {
        return this.bedrockImageModelConfigService.updateBedrockImageModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    override insert(value: GBedrockImageModelConfig): Observable<GBedrockImageModelConfig> {
        return this.bedrockImageModelConfigService.insertBedrockImageModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    override delete(value: GBedrockImageModelConfig): Observable<boolean> {
        return this.bedrockImageModelConfigService.deleteBedrockImageModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }));
    }
}
