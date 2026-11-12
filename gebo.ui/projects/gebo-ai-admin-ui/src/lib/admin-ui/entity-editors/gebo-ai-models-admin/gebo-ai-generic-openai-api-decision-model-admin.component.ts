/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, forwardRef, Injector, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { GenericOpenAIAPIDecisionModelChoice, GenericOpenAIAPIDecisionModelConfig, GenericOpenAiDecisionModelsConfigurationControllerService, GenericOpenAIDecisionModelTypeConfig, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRequest, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";
import { isValidUrl } from "../utils/url-ok";
import { alignChoosedModel } from "../utils/choosed-model-alignment";

/**
 * The editor of a decision model configuration of an OpenAI compatible provider (the
 * decisionModelProviders of providers.yml): the provider's base url when it declares
 * one, else the one the admin enters; the token when the server wants one; the model
 * among the ones the server lists as decision models.
 */
@Component({
    templateUrl: "gebo-ai-generic-openai-api-decision-model-admin.component.html",
    selector: "gebo-ai-generic-openai-api-decision-model-component",
    standalone: false,
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false },
    {
        provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIGenericOpenAIAPIDecisionModelAdminComponent),
        multi: false
    }]
})
export class GeboAIGenericOpenAIAPIDecisionModelAdminComponent extends BaseEntityEditingComponent<GenericOpenAIAPIDecisionModelConfig> implements OnInit {
    protected override entityName: string = "GenericOpenAIAPIDecisionModelConfig";
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        userModified: new FormControl(),
        userCreated: new FormControl(),
        dateModified: new FormControl(),
        dateCreated: new FormControl(),
        modelTypeCode: new FormControl(),
        defaultModel: new FormControl(),
        apiSecretCode: new FormControl(),
        choosedModel: new FormControl(),
        baseUrl: new FormControl(),
        contextLength: new FormControl()
    });

    private oldValue: any = {};

    modelChoicesData: GenericOpenAIAPIDecisionModelChoice[] = [];

    public identitiesObservable?: Observable<SecretInfo[]> = of([]);

    public newSecretAction?: GeboUIActionRequest;

    /** The decision model providers declared in providers.yml */
    public modelTypes?: GenericOpenAIDecisionModelTypeConfig[];

    /** The provider of the configuration being edited */
    public modelType?: GenericOpenAIDecisionModelTypeConfig;

    constructor(injector: Injector,
        geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService: GeboUIOutputForwardingService,
        private decisionModelsService: GenericOpenAiDecisionModelsConfigurationControllerService,
        private secretControllerService: SecretsControllerService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
        this.manageOperationStatus = true;
        this.formGroup.valueChanges.subscribe(newValue => {
            if (!newValue.baseUrl && !newValue.apiSecretCode) {
                this.modelChoicesData = [];
            } else if ((newValue.baseUrl !== this.oldValue.baseUrl && isValidUrl(newValue.baseUrl)) || newValue.apiSecretCode !== this.oldValue.apiSecretCode) {
                this.loadModels(newValue);
            }
            this.oldValue = newValue;
        });
    }

    override ngOnInit(): void {
        super.ngOnInit();
        this.loadingRelatedBackend = true;
        this.decisionModelsService.getGenericOpenAIDecisionModelTypes().subscribe({
            next: (values) => {
                this.modelTypes = values;
                if (this.entity?.modelTypeCode) {
                    this.modelType = this.modelTypes.find(x => x.code === this.entity?.modelTypeCode);
                }
                // a provider that does not accept anonymous calls needs a token
                if (this.modelType && this.modelType.optionalAuthentication !== true) {
                    this.formGroup.controls["apiSecretCode"].setValidators(Validators.required);
                }
                this.formGroup.updateValueAndValidity();
                if (this.entity)
                    this.refreshProviderModel(this.entity);
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    /** The provider of the configuration: its secrets context, its base url when it has one. */
    private refreshProviderModel(actualValue: GenericOpenAIAPIDecisionModelConfig) {
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

    /** The decision models the provider lists at the base url, once reachable. */
    private loadModels(newValue: { baseUrl?: string, apiSecretCode?: string }) {
        this.loadingRelatedBackend = true;
        this.decisionModelsService.getGenericOpenAIAPIDecisionModels({ ...newValue, modelTypeCode: this.entity?.modelTypeCode }).subscribe({
            next: (r) => {
                this.updateLastOperationStatus(r as any);
                this.modelChoicesData = alignChoosedModel(this.formGroup, r.result);
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    protected override onLoadedPersistentData(actualValue: GenericOpenAIAPIDecisionModelConfig): void {
        this.refreshProviderModel(actualValue);
        // the model list loads once, through the value change the form patch emits (see the constructor)
    }

    protected override onNewData(actualValue: GenericOpenAIAPIDecisionModelConfig): void {
        this.refreshProviderModel(actualValue);
    }

    override findByCode(code: string): Observable<GenericOpenAIAPIDecisionModelConfig | null> {
        return this.decisionModelsService.findGenericOpenAIAPIDecisionModelConfigByCode(code);
    }

    override save(value: GenericOpenAIAPIDecisionModelConfig): Observable<GenericOpenAIAPIDecisionModelConfig> {
        return this.decisionModelsService.updateGenericOpenAIAPIDecisionModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    override insert(value: GenericOpenAIAPIDecisionModelConfig): Observable<GenericOpenAIAPIDecisionModelConfig> {
        return this.decisionModelsService.insertGenericOpenAIAPIDecisionModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    override delete(value: GenericOpenAIAPIDecisionModelConfig): Observable<boolean> {
        return this.decisionModelsService.deleteGenericOpenAIAPIDecisionModelConfig(value).pipe(map(x => {
            this.updateLastOperationStatus(x as any);
            return x.result === true;
        }));
    }

    override canBeDeleted(value: GenericOpenAIAPIDecisionModelConfig): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }
}
