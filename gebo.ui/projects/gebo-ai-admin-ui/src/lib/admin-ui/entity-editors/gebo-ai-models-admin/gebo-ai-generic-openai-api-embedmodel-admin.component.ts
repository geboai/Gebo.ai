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
 * This module provides a component for administering Generic OpenAI API embedding models.
 * It includes functionality for creating, editing, and managing embedding model configurations
 * with support for different model types, API secrets, and base URLs.
 */
import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GBaseChatModelChoice, GenericOpenAIAPIEmbeddingModelConfig, GenericOpenAiapiEmbeddingModelsConfigurationControllerService, GenericOpenAIEmbeddingModelTypeConfig, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, BaseEntityEditingComponentAutoDeleteCheck, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";
import { isValidUrl } from "../utils/url-ok";

/**
 * Component for managing OpenAI API embedding model configurations.
 * This component extends BaseEntityEditingComponent and provides an interface
 * for managing GenericOpenAIAPIEmbeddingModelConfig entities, including:
 * - Creating new embedding model configurations
 * - Editing existing configurations
 * - Selecting model types and specific models
 * - Configuring API secrets and base URLs
 * - Validating configuration settings
 */
@Component({
    selector: "gebo-ai-generic-open-ai-api-embed-model-admin-component",
    templateUrl: "gebo-ai-generic-openai-api-embedmodel-admin.component.html",
    standalone: false, providers: [ { provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false }, 
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIGenericOpenAIAPIEmbedModelAdminComponent),
        multi: false
    }]
})
export class GeboAIGenericOpenAIAPIEmbedModelAdminComponent extends BaseEntityEditingComponentAutoDeleteCheck<GenericOpenAIAPIEmbeddingModelConfig> {
    /**
     * The name of the entity being managed by this component
     */
    protected override entityName: string = "GenericOpenAIAPIEmbeddingModelConfig";
    
    /**
     * The allowed types of secrets that can be used with this model
     */
    allowedTypes: SecretInfo.SecretTypeEnum[] = [SecretInfo.SecretTypeEnum.TOKEN];
    
    /**
     * Form group containing all editable fields for the embedding model configuration
     */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        modelTypeCode: new FormControl(),
        modelCode: new FormControl(),
        defaultModel: new FormControl(),
        choosedModel: new FormControl(),
        apiSecretCode: new FormControl(),
        baseUrl: new FormControl(),
        unautenticated: new FormControl()
    });
    
    /**
     * Stores the previous form values to detect changes
     */
    private oldValue: any = {};
    
    /**
     * Available model choices for the selected configuration
     */
    modelChoicesData: GBaseChatModelChoice[] = [];
    
    /**
     * List of available model types
     */
    public modelTypes?:GenericOpenAIEmbeddingModelTypeConfig[];
    
    /**
     * Currently selected model type
     */
    public modelType?:GenericOpenAIEmbeddingModelTypeConfig;
    
    /**
     * Observable that provides available secrets for authentication
     */
    identitiesObservable:Observable<SecretInfo[]> = of([]);
    
    /**
     * Action configuration for creating a new secret
     */
    public newSecretAction = newSecretActionRequest("generic-openai-api", this.entityName, this.entity);
    
    /**
     * Constructor initializes the component and sets up form value change handlers
     * to react to configuration changes like updating the baseUrl or apiSecretCode.
     * 
     * @param injector Angular injector for dependency injection
     * @param geboFormGroupsService Service for form group management
     * @param openaiEmbedModelConfigService Service for OpenAI embedding model configuration
     * @param confirmService Service for confirmation dialogs
     * @param secretControllerService Service for managing secrets
     * @param geboUIActionRoutingService Service for UI action routing
     * @param outputForwardingService Optional service for output forwarding
     */
    constructor(injector:Injector,geboFormGroupsService: GeboFormGroupsService,
        private openaiEmbedModelConfigService: GenericOpenAiapiEmbeddingModelsConfigurationControllerService,
        confirmService: ConfirmationService,
        private secretControllerService: SecretsControllerService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector,geboFormGroupsService, confirmService,geboUIActionRoutingService, outputForwardingService);
        this.formGroup.valueChanges.subscribe(newValue => {
            if (!newValue.baseUrl && !newValue.apiSecretCode) {
                this.modelChoicesData = [];
            } else if ((newValue.baseUrl !== this.oldValue.baseUrl &&   isValidUrl(newValue.baseUrl) ) || newValue.apiSecretCode !== this.oldValue.apiSecretCode) {
                this.loadModels(newValue);
            }
            this.oldValue = newValue;
         });
         this.manageOperationStatus=true;
    }
    
    /**
     * Refreshes the provider model data based on the selected model type.
     * Updates the identities observable and new secret action with appropriate provider ID.
     * Sets the baseUrl if not already specified.
     * 
     * @param actualValue The current embedding model configuration
     */
    private refreshProviderModel(actualValue: GenericOpenAIAPIEmbeddingModelConfig) {
        if (actualValue?.modelTypeCode && this.modelTypes) {
            this.modelType=this.modelTypes.find(x=>x.code===actualValue?.modelTypeCode);
            if (this.modelType?.providerId) {
              this.identitiesObservable=this.secretControllerService.getSecretsByContextCode(this.modelType?.providerId);
              this.newSecretAction= newSecretActionRequest(this.modelType?.providerId,this.entityName,this.entity);
              if ((!this.formGroup.value?.baseUrl) && this.modelType?.baseUrl) {
                 this.formGroup.controls["baseUrl"].setValue(this.modelType?.baseUrl);
              }
            }
        }
        
    }
    
    /**
     * Initializes the component and loads model types from the backend
     */
    override ngOnInit(): void {
        super.ngOnInit();
        this.loadingRelatedBackend=true;
        this.openaiEmbedModelConfigService.getGenericOpenAIEmbeddingModelTypes().subscribe({
            next:(values)=>{
                this.modelTypes=values;
                if (this.entity)
                this.refreshProviderModel(this.entity);
            },
            complete:()=>{
                this.loadingRelatedBackend=false;
            }
        })
    }
    
    /**
     * Loads secrets information for the current configuration
     * This method appears to be a placeholder for future implementation
     * 
     * @param actualValue The current embedding model configuration
     */
    private loadSecretsInfos(actualValue:GenericOpenAIAPIEmbeddingModelConfig) {
        
    }
    
    /**
     * Loads available models based on the provided base URL and API secret code
     * 
     * @param newValue Object containing baseUrl and apiSecretCode
     */
    private loadModels(newValue: { baseUrl?: string, apiSecretCode?: string }) {
        this.loadingRelatedBackend = true;
        this.openaiEmbedModelConfigService.getGenericOpenAIAPIEmbeddingModels(newValue).subscribe({
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
     * Handles setup when loading persistent data from the backend
     * 
     * @param actualValue The loaded embedding model configuration
     */
    protected override onLoadedPersistentData(actualValue: GenericOpenAIAPIEmbeddingModelConfig): void {
        this.loadSecretsInfos(actualValue);
        this.refreshProviderModel(actualValue);
        this.loadModels(actualValue);
    }
    
    /**
     * Handles setup when creating new data
     * 
     * @param actualValue The new embedding model configuration
     */
    protected override onNewData(actualValue: GenericOpenAIAPIEmbeddingModelConfig): void {
        this.loadSecretsInfos(actualValue);
        this.refreshProviderModel(actualValue);
    }
    
    /**
     * Finds a configuration by its code
     * 
     * @param code The code to search for
     * @returns Observable with the found configuration or null
     */
    override findByCode(code: string): Observable<GenericOpenAIAPIEmbeddingModelConfig | null> {
        return this.openaiEmbedModelConfigService.findGenericOpenAIAPIEmbeddingModelConfigByCode(code)
    }

    /**
     * Saves an existing configuration
     * 
     * @param value The configuration to save
     * @returns Observable with the saved configuration
     */
    override save(value: GenericOpenAIAPIEmbeddingModelConfig): Observable<GenericOpenAIAPIEmbeddingModelConfig> {

        return this.openaiEmbedModelConfigService.updateGenericOpenAIAPIEmbeddingModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }
    
    /**
     * Inserts a new configuration
     * 
     * @param value The configuration to insert
     * @returns Observable with the inserted configuration
     */
    override insert(value: GenericOpenAIAPIEmbeddingModelConfig): Observable<GenericOpenAIAPIEmbeddingModelConfig> {

        return this.openaiEmbedModelConfigService.insertGenericOpenAIAPIEmbeddingModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }
    
    /**
     * Deletes a configuration
     * 
     * @param value The configuration to delete
     * @returns Observable with the delete operation result
     */
    override delete(value: GenericOpenAIAPIEmbeddingModelConfig): Observable<boolean> {
        return this.openaiEmbedModelConfigService.deleteGenericOpenAIAPIEmbeddingModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }))
    }
    
    

}