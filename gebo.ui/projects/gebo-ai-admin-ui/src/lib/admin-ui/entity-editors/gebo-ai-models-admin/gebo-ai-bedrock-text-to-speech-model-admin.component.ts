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
import { GBedrockTextToSpeechModelChoice, GBedrockTextToSpeechModelConfig, BedrockTextToSpeechModelsConfigurationControllerService, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponentAutoDeleteCheck, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";

/**
 * Component for administering AWS (Amazon Polly) text-to-speech model configurations.
 */
@Component({
    selector: "gebo-ai-bedrock-text-to-speech-model-admin-component",
    templateUrl: "gebo-ai-bedrock-text-to-speech-model-admin.component.html",
    standalone: false, providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIBedrockTextToSpeechModelAdminComponent), multi: false }
    ]
})
export class GeboAIBedrockTextToSpeechModelAdminComponent extends BaseEntityEditingComponentAutoDeleteCheck<GBedrockTextToSpeechModelConfig> {
    protected override entityName: string = "GBedrockTextToSpeechModelConfig";

    allowedTypes: SecretInfo.SecretTypeEnum[] = [SecretInfo.SecretTypeEnum.USERNAMEPASSWORD];

    engineOptions = [
        { label: "Neural", value: "neural" },
        { label: "Standard", value: "standard" },
        { label: "Long-form", value: "long-form" },
        { label: "Generative", value: "generative" }
    ];

    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        modelTypeCode: new FormControl(),
        region: new FormControl(),
        engine: new FormControl(),
        defaultModel: new FormControl(),
        choosedModel: new FormControl(),
        apiSecretCode: new FormControl()
    });

    private oldValue: any = {};
    modelChoicesData: GBedrockTextToSpeechModelChoice[] = [];
    identitiesObservable = this.secretControllerService.getSecretsByContextCode("aws-bedrock");
    public newSecretAction = newSecretActionRequest("aws-bedrock", this.entityName, this.entity);

    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private bedrockTextToSpeechModelConfigService: BedrockTextToSpeechModelsConfigurationControllerService,
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

    private loadModels(newValue: GBedrockTextToSpeechModelConfig) {
        this.loadingRelatedBackend = true;
        this.bedrockTextToSpeechModelConfigService.getBedrockTextToSpeechModels(newValue).subscribe({
            next: (r) => {
                this.updateLastOperationStatus(r as any);
                this.modelChoicesData = r.result ? r.result : [];
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    protected override onLoadedPersistentData(actualValue: GBedrockTextToSpeechModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("aws-polly-tts");
        this.loadModels(actualValue);
    }

    protected override onNewData(actualValue: GBedrockTextToSpeechModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("aws-polly-tts");
    }

    override findByCode(code: string): Observable<GBedrockTextToSpeechModelConfig | null> {
        return this.bedrockTextToSpeechModelConfigService.findBedrockTextToSpeechModelConfigByCode(code);
    }

    override save(value: GBedrockTextToSpeechModelConfig): Observable<GBedrockTextToSpeechModelConfig> {
        return this.bedrockTextToSpeechModelConfigService.updateBedrockTextToSpeechModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    override insert(value: GBedrockTextToSpeechModelConfig): Observable<GBedrockTextToSpeechModelConfig> {
        return this.bedrockTextToSpeechModelConfigService.insertBedrockTextToSpeechModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    override delete(value: GBedrockTextToSpeechModelConfig): Observable<boolean> {
        return this.bedrockTextToSpeechModelConfigService.deleteBedrockTextToSpeechModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }));
    }
}
