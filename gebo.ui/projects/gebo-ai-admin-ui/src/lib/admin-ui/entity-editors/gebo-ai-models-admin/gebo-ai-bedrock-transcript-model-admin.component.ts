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
import { GBedrockTranscriptModelChoice, GBedrockTranscriptModelConfig, BedrockTranscriptModelsConfigurationControllerService, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponentAutoDeleteCheck, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";

/**
 * Component for administering AWS (Amazon Transcribe) transcript model configurations.
 */
@Component({
    selector: "gebo-ai-bedrock-transcript-model-admin-component",
    templateUrl: "gebo-ai-bedrock-transcript-model-admin.component.html",
    standalone: false, providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIBedrockTranscriptModelAdminComponent), multi: false }
    ]
})
export class GeboAIBedrockTranscriptModelAdminComponent extends BaseEntityEditingComponentAutoDeleteCheck<GBedrockTranscriptModelConfig> {
    protected override entityName: string = "GBedrockTranscriptModelConfig";

    allowedTypes: SecretInfo.SecretTypeEnum[] = [SecretInfo.SecretTypeEnum.USERNAMEPASSWORD];

    mediaEncodingOptions = [
        { label: "PCM", value: "pcm" },
        { label: "OGG Opus", value: "ogg-opus" },
        { label: "FLAC", value: "flac" }
    ];

    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        modelTypeCode: new FormControl(),
        region: new FormControl(),
        languageCode: new FormControl(),
        sampleRateHertz: new FormControl(),
        mediaEncoding: new FormControl(),
        defaultModel: new FormControl(),
        choosedModel: new FormControl(),
        apiSecretCode: new FormControl()
    });

    private oldValue: any = {};
    modelChoicesData: GBedrockTranscriptModelChoice[] = [];
    identitiesObservable = this.secretControllerService.getSecretsByContextCode("aws-bedrock");
    public newSecretAction = newSecretActionRequest("aws-bedrock", this.entityName, this.entity);

    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private bedrockTranscriptModelConfigService: BedrockTranscriptModelsConfigurationControllerService,
        private secretControllerService: SecretsControllerService,
        confirmService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmService, geboUIActionRoutingService, outputForwardingService);
        this.formGroup.valueChanges.subscribe(newValue => {
            this.oldValue = newValue;
        });
        this.loadModels();
    }

    private loadModels() {
        this.loadingRelatedBackend = true;
        this.bedrockTranscriptModelConfigService.getBedrockTranscriptModels({}).subscribe({
            next: (r) => {
                this.updateLastOperationStatus(r as any);
                this.modelChoicesData = r.result ? r.result : [];
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    protected override onLoadedPersistentData(actualValue: GBedrockTranscriptModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("aws-transcribe");
    }

    protected override onNewData(actualValue: GBedrockTranscriptModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("aws-transcribe");
    }

    override findByCode(code: string): Observable<GBedrockTranscriptModelConfig | null> {
        return this.bedrockTranscriptModelConfigService.findBedrockTranscriptModelConfigByCode(code);
    }

    override save(value: GBedrockTranscriptModelConfig): Observable<GBedrockTranscriptModelConfig> {
        return this.bedrockTranscriptModelConfigService.updateBedrockTranscriptModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    override insert(value: GBedrockTranscriptModelConfig): Observable<GBedrockTranscriptModelConfig> {
        return this.bedrockTranscriptModelConfigService.insertBedrockTranscriptModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    override delete(value: GBedrockTranscriptModelConfig): Observable<boolean> {
        return this.bedrockTranscriptModelConfigService.deleteBedrockTranscriptModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }));
    }
}
