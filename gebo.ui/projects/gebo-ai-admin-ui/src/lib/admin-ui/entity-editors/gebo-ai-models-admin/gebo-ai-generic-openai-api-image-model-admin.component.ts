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
 * This file implements an Angular component for administering Generic OpenAI API
 * Image models.
 */
import { Component, forwardRef, Injector, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { GenericOpenAIAPIImageModelChoice, GenericOpenAIAPIImageModelConfig, GenericOpenAiapiImageModelsConfigurationControllerService, GenericOpenAIImageModelTypeConfig, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRequest, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";
import { isValidUrl } from "../utils/url-ok";

@Component({
    selector: "gebo-ai-generic-open-ai-api-image-model-admin-component",
    templateUrl: "gebo-ai-generic-openai-api-image-model-admin.component.html",
    standalone: false, providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIGenericOpenAIAPIImageModelAdminComponent),
        multi: false
    }]
})
export class GeboAIGenericOpenAIAPIImageModelAdminComponent extends BaseEntityEditingComponent<GenericOpenAIAPIImageModelConfig> implements OnInit {
    protected override entityName: string = "GenericOpenAIAPIImageModelConfig";
    allowedTypes: SecretInfo.SecretTypeEnum[] = [SecretInfo.SecretTypeEnum.TOKEN];

    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        modelTypeCode: new FormControl(),
        defaultModel: new FormControl(),
        choosedModel: new FormControl(),
        apiSecretCode: new FormControl(),
        baseUrl: new FormControl()
    });

    private oldValue: any = {};
    modelChoicesData: GenericOpenAIAPIImageModelChoice[] = [];
    public identitiesObservable?: Observable<SecretInfo[]> = of([]);
    public newSecretAction?: GeboUIActionRequest;
    public modelTypes?: GenericOpenAIImageModelTypeConfig[];
    public modelType?: GenericOpenAIImageModelTypeConfig;

    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private openaiImageModelConfigService: GenericOpenAiapiImageModelsConfigurationControllerService,
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

    override ngOnInit(): void {
        super.ngOnInit();
        this.loadingRelatedBackend = true;
        this.openaiImageModelConfigService.getGenericOpenAIImageModelTypes().subscribe({
            next: (values) => {
                this.modelTypes = values;
                if (this.entity?.modelTypeCode) {
                    this.modelType = this.modelTypes.find(x => x.code === this.entity?.modelTypeCode);
                }
                if (this.modelType?.optionalAuthentication === true) {
                    this.formGroup.controls["apiSecretCode"].setValidators(Validators.required);
                }
                this.formGroup.updateValueAndValidity();
                if (this.entity)
                    this.refreshProviderModel(this.entity);
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        })
    }

    private refreshProviderModel(actualValue: GenericOpenAIAPIImageModelConfig) {
        if (actualValue?.modelTypeCode && this.modelTypes) {
            this.modelType = this.modelTypes.find(x => x.code === actualValue?.modelTypeCode);
            if (this.modelType?.providerId) {
                this.identitiesObservable = this.secretControllerService.getSecretsByContextCode(this.modelType?.providerId);
                this.newSecretAction = newSecretActionRequest(this.modelType?.providerId, this.entityName, this.entity);
                if ((!this.formGroup.value?.baseUrl) && this.modelType?.baseUrl) {
                    this.formGroup.controls["baseUrl"].setValue(this.modelType?.baseUrl);
                }
            }
        }
    }

    private loadModels(newValue: { baseUrl?: string, apiSecretCode?: string }) {
        this.loadingRelatedBackend = true;
        this.openaiImageModelConfigService.getGenericOpenAIAPIImageModels(newValue).subscribe({
            next: (r) => {
                this.updateLastOperationStatus(r as any);
                this.modelChoicesData = r.result ? r.result : []
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    protected override onLoadedPersistentData(actualValue: GenericOpenAIAPIImageModelConfig): void {
        this.refreshProviderModel(actualValue);
        this.loadModels(actualValue);
    }

    protected override onNewData(actualValue: GenericOpenAIAPIImageModelConfig): void {
        this.refreshProviderModel(actualValue);
    }

    override findByCode(code: string): Observable<GenericOpenAIAPIImageModelConfig | null> {
        return this.openaiImageModelConfigService.findGenericOpenAIAPIImageModelConfigByCode(code);
    }

    override save(value: GenericOpenAIAPIImageModelConfig): Observable<GenericOpenAIAPIImageModelConfig> {
        return this.openaiImageModelConfigService.updateGenericOpenAIAPIImageModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    override insert(value: GenericOpenAIAPIImageModelConfig): Observable<GenericOpenAIAPIImageModelConfig> {
        return this.openaiImageModelConfigService.insertGenericOpenAIAPIImageModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    override delete(value: GenericOpenAIAPIImageModelConfig): Observable<boolean> {
        return this.openaiImageModelConfigService.deleteGenericOpenAIAPIImageModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }))
    }

    override canBeDeleted(value: GenericOpenAIAPIImageModelConfig): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

}
