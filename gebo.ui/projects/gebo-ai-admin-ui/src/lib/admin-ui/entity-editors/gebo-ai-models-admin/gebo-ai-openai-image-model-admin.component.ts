/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */



/**
 * AI generated comments
 * This component is responsible for managing OpenAI image model configurations.
 * It extends BaseEntityEditingComponent to handle CRUD operations for
 * GOpenAIImageModelConfig entities.
 */
import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GOpenAIImageModelChoice, GOpenAIImageModelConfig, OpenAiImageModelsConfigurationControllerService, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponentAutoDeleteCheck, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";
import { isValidUrl } from "../utils/url-ok";

@Component({
    selector: "gebo-ai-open-ai-image-model-admin-component",
    templateUrl: "gebo-ai-openai-image-model-admin.component.html",
    standalone: false, providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIOpenAIImageModelAdminComponent),
        multi: false
    }]

})
export class GeboAIOpenAIImageModelAdminComponent extends BaseEntityEditingComponentAutoDeleteCheck<GOpenAIImageModelConfig> {
    protected override entityName: string = "GOpenAIImageModelConfig";
    allowedTypes: SecretInfo.SecretTypeEnum[] = [SecretInfo.SecretTypeEnum.TOKEN];

    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        modelTypeCode: new FormControl(),
        modelCode: new FormControl(),
        defaultModel: new FormControl(),
        choosedModel: new FormControl(),
        apiSecretCode: new FormControl()
    });

    private oldValue: any = {};
    modelChoicesData: GOpenAIImageModelChoice[] = [];
    identitiesObservable = this.secretControllerService.getSecretsByContextCode("openai");
    public newSecretAction = newSecretActionRequest("openai", this.entityName, this.entity);

    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private openaiImageModelConfigService: OpenAiImageModelsConfigurationControllerService,
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

    private loadModels(newValue: { baseUrl?: string, apiSecretCode?: string }) {
        this.loadingRelatedBackend = true;
        this.openaiImageModelConfigService.getOpenAIImageModels(newValue).subscribe({
            next: (r) => {
                this.updateLastOperationStatus(r as any);
                this.modelChoicesData = r.result ? r.result : []
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    protected override onLoadedPersistentData(actualValue: GOpenAIImageModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("openai-image");
        this.loadModels(actualValue);
    }

    protected override onNewData(actualValue: GOpenAIImageModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("openai-image");
    }

    override findByCode(code: string): Observable<GOpenAIImageModelConfig | null> {
        return this.openaiImageModelConfigService.findOpenAIImageModelConfigByCode(code)
    }

    override save(value: GOpenAIImageModelConfig): Observable<GOpenAIImageModelConfig> {
        return this.openaiImageModelConfigService.updateOpenAIImageModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    override insert(value: GOpenAIImageModelConfig): Observable<GOpenAIImageModelConfig> {
        return this.openaiImageModelConfigService.insertOpenAIImageModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    override delete(value: GOpenAIImageModelConfig): Observable<boolean> {
        return this.openaiImageModelConfigService.deleteOpenAIImageModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }))
    }



}
