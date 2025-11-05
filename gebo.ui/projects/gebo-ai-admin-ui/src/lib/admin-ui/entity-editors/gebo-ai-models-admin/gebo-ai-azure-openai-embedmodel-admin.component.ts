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
 * This component is responsible for managing Azure/OpenAI embedding model configurations.
 * It extends BaseEntityEditingComponent to handle CRUD operations for GAzureOpenAIEmbeddingModelConfig entities.
 * The component provides UI for creating, editing, and deleting OpenAI embedding model configurations,
 * fetching available models from the OpenAI API, and managing API secret connections.
 */
import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { AzureOpenAiEmbeddingModelsConfigurationControllerService, GBaseChatModelChoice, GAzureOpenAIEmbeddingModelConfig, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";
import { isValidUrl } from "../utils/url-ok";

@Component({
    selector: "gebo-ai-azure-open-ai-embed-model-admin-component",
    templateUrl: "gebo-ai-openai-embedmodel-admin.component.html",
    standalone: false, providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false }, {
        provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIAzureOpenAIEmbedModelAdminComponent),
        multi: false
    }]
})
export class GeboAIAzureOpenAIEmbedModelAdminComponent extends BaseEntityEditingComponent<GAzureOpenAIEmbeddingModelConfig> {
    /**
     * The entity name identifier for the GAzureOpenAIEmbeddingModelConfig
     */
    protected override entityName: string = "GAzureOpenAIEmbeddingModelConfig";
    
    /**
     * Types of secrets allowed for this component - limited to TOKEN type
     */
    allowedTypes: SecretInfo.SecretTypeEnum[] = [SecretInfo.SecretTypeEnum.TOKEN];
    
    /**
     * Form group to handle the editable properties of the embedding model config
     */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        baseUrl: new FormControl(),
        modelTypeCode: new FormControl(),
        modelCode: new FormControl(),
        defaultModel:new FormControl(),
        choosedModel: new FormControl(),
        apiSecretCode: new FormControl()
    });
    
    /**
     * Keeps track of the previous form values to detect changes
     */
    private oldValue: any = {};    
    
    /**
     * Available model choices data from OpenAI
     */
    modelChoicesData: GBaseChatModelChoice[] = [];
    
    /**
     * Observable for retrieving OpenAI identity secrets
     */
    identitiesObservable = this.secretControllerService.getSecretsByContextCode("azure-openai");
    
    /**
     * Action request for creating a new secret
     */
    public newSecretAction= newSecretActionRequest("azure-openai",this.entityName,this.entity);
    
    /**
     * Component constructor that initializes the form and sets up value change detection
     * to update models when relevant form fields change
     */
    constructor(injector:Injector,geboFormGroupsService: GeboFormGroupsService,
        private openaiEmbedModelConfigService: AzureOpenAiEmbeddingModelsConfigurationControllerService,
        private secretControllerService: SecretsControllerService,
        confirmService:ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector,geboFormGroupsService,confirmService,geboUIActionRoutingService, outputForwardingService);
        this.formGroup.valueChanges.subscribe(newValue => {
            if (!newValue.baseUrl && !newValue.apiSecretCode) {
                this.modelChoicesData = [];
            } else if ((newValue.baseUrl!==this.oldValue.baseUrl && isValidUrl(newValue.baseUrl))  || newValue.apiSecretCode !== this.oldValue.apiSecretCode) {
                this.loadModels(newValue);
            }
            this.oldValue = newValue;

        });
    }

    /**
     * Loads available embedding models from OpenAI based on the provided URL and API secret
     * Sets the loading state and updates model choices data once retrieved
     * @param newValue Object containing baseUrl and apiSecretCode
     */
    private loadModels(newValue: { baseUrl?: string, apiSecretCode?: string }) {
        this.loadingRelatedBackend = true;
        this.openaiEmbedModelConfigService.getAzureOpenAIEmbeddingModels(newValue).subscribe({
            next: (r) => {
                this.updateLastOperationStatus(r as any);
                this.modelChoicesData = r.result ? r.result : []
            },
            complete:()=>{
                this.loadingRelatedBackend=false;
            }
        });
    }

    /**
     * Handles initializing the form when editing an existing entity
     * Sets model type code and loads available models
     * @param actualValue The existing model configuration being loaded
     */
    protected override onLoadedPersistentData(actualValue: GAzureOpenAIEmbeddingModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("azure-embedding-OpenAI");
        this.loadModels(actualValue);
    }

    /**
     * Handles initializing the form when creating a new entity
     * Sets the default model type code
     * @param actualValue The new model configuration being created
     */
    protected override onNewData(actualValue: GAzureOpenAIEmbeddingModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("azure-embedding-OpenAI");
    }

    /**
     * Retrieves an embedding model configuration by its code
     * @param code The unique code of the model configuration to find
     * @returns Observable containing the found configuration or null
     */
    override findByCode(code: string): Observable<GAzureOpenAIEmbeddingModelConfig | null> {
        return this.openaiEmbedModelConfigService.findAzureOpenAIEmbeddingModelConfigByCode(code)
    }
    
    /**
     * Updates an existing embedding model configuration
     * @param value The model configuration to update
     * @returns Observable containing the updated configuration
     */
    override save(value: GAzureOpenAIEmbeddingModelConfig): Observable<GAzureOpenAIEmbeddingModelConfig> {
        
        return this.openaiEmbedModelConfigService.updateAzureOpenAIEmbeddingModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    /**
     * Creates a new embedding model configuration
     * @param value The model configuration to create
     * @returns Observable containing the created configuration
     */
    override insert(value: GAzureOpenAIEmbeddingModelConfig): Observable<GAzureOpenAIEmbeddingModelConfig> {
        
        return this.openaiEmbedModelConfigService.insertAzureOpenAIEmbeddingModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    /**
     * Deletes an embedding model configuration
     * @param value The model configuration to delete
     * @returns Observable indicating whether the delete operation was successful
     */
    override delete(value: GAzureOpenAIEmbeddingModelConfig): Observable<boolean> {
        return this.openaiEmbedModelConfigService.deleteAzureOpenAIEmbeddingModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }))
    }

    /**
     * Determines if a model configuration can be deleted
     * Currently always returns true as there are no dependencies to check
     * @param value The model configuration to check
     * @returns Observable containing the delete permission status and message
     */
    override canBeDeleted(value: GAzureOpenAIEmbeddingModelConfig): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

}