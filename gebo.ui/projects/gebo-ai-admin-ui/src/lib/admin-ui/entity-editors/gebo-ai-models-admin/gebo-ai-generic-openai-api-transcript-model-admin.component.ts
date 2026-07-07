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
 * Transcript models. It provides functionality to create, update, delete, and
 * manage configuration settings for OpenAI API transcript models within the
 * Gebo.ai system.
 */
import { Component, forwardRef, Injector, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { GenericOpenAIAPITranscriptModelChoice, GenericOpenAIAPITranscriptModelConfig, GenericOpenAiapiTranscriptModelsConfigurationControllerService, GenericOpenAITranscriptModelType, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRequest, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";
import { isValidUrl } from "../utils/url-ok";

/**
 * Component for administering Generic OpenAI API Transcript model configurations.
 * This component extends BaseEntityEditingComponent and provides a user interface 
 * for managing OpenAI API transcript model settings including API credentials,
 * model types, and access permissions.
 */
@Component({
    selector: "gebo-ai-generic-open-ai-api-transcript-model-admin-component",
    templateUrl: "gebo-ai-generic-openai-api-transcript-model-admin.component.html",
    standalone: false, providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIGenericOpenAIAPITranscriptModelAdminComponent),
        multi: false
    }]
})
export class GeboAIGenericOpenAIAPITranscriptModelAdminComponent extends BaseEntityEditingComponent<GenericOpenAIAPITranscriptModelConfig> implements OnInit {
    /**
     * Entity name used for identification throughout the component
     */
    protected override entityName: string = "GenericOpenAIAPITranscriptModelConfig";

    /**
     * Defines the allowed secret types for this component
     */
    allowedTypes: SecretInfo.SecretTypeEnum[] = [SecretInfo.SecretTypeEnum.TOKEN];

    /**
     * FormGroup defining all the form controls needed for the OpenAI API transcript model configuration
     */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        modelTypeCode: new FormControl(),
        defaultModel: new FormControl(),
        choosedModel: new FormControl(),
        apiSecretCode: new FormControl(),
        baseUrl: new FormControl()
    });

    /**
     * Tracks previous form values for comparison to detect changes
     */
    private oldValue: any = {};

    /**
     * Stores available model choices for the selected API configuration
     */
    modelChoicesData: GenericOpenAIAPITranscriptModelChoice[] = [];

    /**
     * Observable for retrieving available identity/secret information
     */
    public identitiesObservable?: Observable<SecretInfo[]> = of([]);

    /**
     * Action request for creating a new secret
     */
    public newSecretAction?: GeboUIActionRequest;

    /**
     * Available model types for OpenAI API transcript configurations
     */
    public modelTypes?: GenericOpenAITranscriptModelType[];

    /**
     * Currently selected model type
     */
    public modelType?: GenericOpenAITranscriptModelType;

    /**
     * Component constructor that initializes services and sets up form value change subscriptions
     */
    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private openaiTranscriptModelConfigService: GenericOpenAiapiTranscriptModelsConfigurationControllerService,
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

    /**
     * Lifecycle hook that initializes component data including model types
     */
    override ngOnInit(): void {
        super.ngOnInit();
        this.loadingRelatedBackend = true;
        this.openaiTranscriptModelConfigService.getGenericOpenAITranscriptModelTypes().subscribe({
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

    /**
     * Updates model provider information based on the selected model type
     * Sets up identities observable and configures baseUrl if necessary
     * @param actualValue The current model configuration
     */
    private refreshProviderModel(actualValue: GenericOpenAIAPITranscriptModelConfig) {
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

    /**
     * Loads available models from the OpenAI API using the provided configuration
     * @param newValue Object containing baseUrl and apiSecretCode used to authenticate with the API
     */
    private loadModels(newValue: { baseUrl?: string, apiSecretCode?: string }) {
        this.loadingRelatedBackend = true;
        this.openaiTranscriptModelConfigService.getGenericOpenAIAPITranscriptModels(newValue).subscribe({
            next: (r) => {
                this.updateLastOperationStatus(r as any);
                this.modelChoicesData = r.result ? r.result : []
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    /**
     * Called when persistent data is loaded, refreshes provider model and loads available models
     * @param actualValue The loaded model configuration
     */
    protected override onLoadedPersistentData(actualValue: GenericOpenAIAPITranscriptModelConfig): void {
        this.refreshProviderModel(actualValue);
        this.loadModels(actualValue);
    }

    /**
     * Called when creating a new entity, initializes provider model data
     * @param actualValue The new model configuration
     */
    protected override onNewData(actualValue: GenericOpenAIAPITranscriptModelConfig): void {
        this.refreshProviderModel(actualValue);
    }

    /**
     * Retrieves a model configuration by its code
     * @param code The code identifying the model configuration
     * @returns An Observable with the model configuration or null
     */
    override findByCode(code: string): Observable<GenericOpenAIAPITranscriptModelConfig | null> {
        return this.openaiTranscriptModelConfigService.findGenericOpenAIAPITranscriptModelConfigByCode(code);
    }

    /**
     * Saves updates to an existing model configuration
     * @param value The model configuration to update
     * @returns An Observable with the updated model configuration
     */
    override save(value: GenericOpenAIAPITranscriptModelConfig): Observable<GenericOpenAIAPITranscriptModelConfig> {
        return this.openaiTranscriptModelConfigService.updateGenericOpenAIAPITranscriptModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    /**
     * Inserts a new model configuration
     * @param value The model configuration to insert
     * @returns An Observable with the inserted model configuration
     */
    override insert(value: GenericOpenAIAPITranscriptModelConfig): Observable<GenericOpenAIAPITranscriptModelConfig> {
        return this.openaiTranscriptModelConfigService.insertGenericOpenAIAPITranscriptModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    /**
     * Deletes a model configuration
     * @param value The model configuration to delete
     * @returns An Observable indicating success or failure
     */
    override delete(value: GenericOpenAIAPITranscriptModelConfig): Observable<boolean> {
        return this.openaiTranscriptModelConfigService.deleteGenericOpenAIAPITranscriptModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }))
    }

    override canBeDeleted(value: GenericOpenAIAPITranscriptModelConfig): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

}
