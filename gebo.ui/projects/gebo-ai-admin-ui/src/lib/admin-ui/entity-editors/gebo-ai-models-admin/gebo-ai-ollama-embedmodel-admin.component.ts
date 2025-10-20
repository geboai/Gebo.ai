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
 * 
 * This file contains a component for managing Ollama embedding models in the Gebo.ai application.
 * It extends BaseEntityEditingComponent to provide CRUD operations for Ollama embedding model configurations.
 */

import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { EmbeddingModelsControllersService, GBaseChatModelChoice, GOllamaEmbeddingModelConfig, OllamaEmbeddingModelsConfigurationControllerService, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";
import { isValidUrl } from "../utils/url-ok";

/**
 * Component responsible for administrating Ollama embedding models.
 * It provides a UI to create, read, update and delete embedding model configurations
 * that connect to Ollama's API.
 */
@Component({
    selector: "gebo-ai-ollama-ai-embed-model-admin-component",
    templateUrl: "gebo-ai-ollama-embedmodel-admin.component.html",
    standalone: false, providers: [{
        provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIOllamaEmbedModelAdminComponent),
        multi: true
    }]
})
export class GeboAIOllamaEmbedModelAdminComponent extends BaseEntityEditingComponent<GOllamaEmbeddingModelConfig> {
    /**
     * The entity name for this component, used for tracking and reference
     */
    protected override entityName: string = "GOllamaEmbeddingModelConfig";
    
    /**
     * Defines the allowed secret types that can be used with Ollama embedding models
     */
    allowedTypes: SecretInfo.SecretTypeEnum[] = [ SecretInfo.SecretTypeEnum.TOKEN];
    
    /**
     * Form group containing form controls for all editable properties of the embedding model
     */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        baseUrl: new FormControl(),
        choosedModel: new FormControl(),
        modelTypeCode: new FormControl(),
        defaultModel:new FormControl(),
        apiSecretCode: new FormControl()        
    });
    
    /**
     * Stores the previous form values to detect changes
     */
    private oldValue:any={};
    
    /**
     * Available model choices fetched from Ollama
     */
    modelChoicesData: GBaseChatModelChoice[] = [];
    
    /**
     * Observable containing available secrets for Ollama context
     */
    identitiesObservable = this.secretControllerService.getSecretsByContextCode("ollama");
    
    /**
     * Action configuration for creating a new secret for Ollama
     */
    public newSecretAction= newSecretActionRequest("ollama",this.entityName,this.entity);
    
    /**
     * Constructor initializes the component and sets up form value change monitoring
     * to automatically load available models when URL or API secret changes
     */
    constructor(injector:Injector,geboFormGroupsService: GeboFormGroupsService,
        private embedModelConfigService: OllamaEmbeddingModelsConfigurationControllerService,
        private embeddingModelsControllerService: EmbeddingModelsControllersService,
        private secretControllerService: SecretsControllerService,
        confirmService:ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector,geboFormGroupsService,confirmService,geboUIActionRoutingService, outputForwardingService);
        this.formGroup.valueChanges.subscribe(newValue=>{
            if (!newValue.baseUrl && !newValue.apiSecretCode) {
                this.modelChoicesData=([]);
            }else if ((newValue.baseUrl!==this.oldValue.baseUrl && isValidUrl(newValue.baseUrl)) || newValue.apiSecretCode!==this.oldValue.apiSecretCode) {
                this.loadModels(newValue);
            }
            this.oldValue=newValue;

        });    
        this.manageOperationStatus=true;
    }
    
    /**
     * Loads available embedding models from Ollama based on the provided base URL and API secret
     * Updates the model choices dropdown with the results
     * 
     * @param newValue Object containing baseUrl and apiSecretCode to connect to Ollama
     */
    private loadModels(newValue: { baseUrl?: string, apiSecretCode?: string }) {
        this.loadingRelatedBackend = true;
        this.embedModelConfigService.getOllamaEmbeddingModels(newValue).subscribe({
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
     * Callback executed when an existing entity is loaded
     * Sets the modelTypeCode and loads available models
     * 
     * @param actualValue The loaded embedding model configuration
     */
    protected override onLoadedPersistentData(actualValue: GOllamaEmbeddingModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("embedding-ollama");
        this.loadModels(actualValue);
    }
    
    /**
     * Callback executed when initializing a new entity
     * Sets the default modelTypeCode for new configurations
     * 
     * @param actualValue The new embedding model configuration
     */
    protected override onNewData(actualValue: GOllamaEmbeddingModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("embedding-ollama");
    }
    
    /**
     * Finds an embedding model configuration by its code
     * 
     * @param code The unique code identifier for the model configuration
     * @returns Observable of the found configuration or null if not found
     */
    override findByCode(code: string): Observable<GOllamaEmbeddingModelConfig | null> {
        return this.embedModelConfigService.findOllamaEmbeddingModelConfigByCode(code)
    }
    
    /**
     * Saves changes to an existing embedding model configuration
     * 
     * @param value The updated embedding model configuration
     * @returns Observable of the saved configuration
     */
    override save(value: GOllamaEmbeddingModelConfig): Observable<GOllamaEmbeddingModelConfig> {
        return this.embedModelConfigService.updateOllamaEmbeddingModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }
    
    /**
     * Inserts a new embedding model configuration
     * 
     * @param value The new embedding model configuration to insert
     * @returns Observable of the inserted configuration
     */
    override insert(value: GOllamaEmbeddingModelConfig): Observable<GOllamaEmbeddingModelConfig> {
        return this.embedModelConfigService.insertOllamaEmbeddingModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }
    
    /**
     * Deletes an embedding model configuration
     * 
     * @param value The embedding model configuration to delete
     * @returns Observable indicating success or failure
     */
    override delete(value: GOllamaEmbeddingModelConfig): Observable<boolean> {
        return this.embedModelConfigService.deleteOllamaEmbeddingModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any) ;
            return result.result === true;
        }))
    }
    
    /**
     * Checks if the embedding model configuration can be deleted
     * Currently always returns true as there are no restrictions on deletion
     * 
     * @param value The embedding model configuration to check
     * @returns Observable containing deletion permission info
     */
    override canBeDeleted(value: GOllamaEmbeddingModelConfig): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

}