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
 * This file contains the GeboAIOllamaChatModelAdminComponent which is responsible for 
 * managing Ollama Chat Model configurations within the Gebo.ai platform. It provides functionality
 * for creating, editing, updating, and deleting Ollama chat model configurations, as well as loading
 * available models from an Ollama server.
 */

import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GBaseChatModelChoice, GOllamaChatModelConfig, OllamaChatModelsConfigurationControllerService, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";
import { isValidUrl } from "../utils/url-ok";

/**
 * Component for administering Ollama Chat Model configurations.
 * This component extends BaseEntityEditingComponent and provides
 * a UI for creating, editing, and managing Ollama chat model configurations.
 * It handles form management, model loading, and CRUD operations for
 * Ollama chat model configurations.
 */
@Component({
    selector: "gebo-ai-ollama-ai-chat-model-admin-component",
    templateUrl: "gebo-ai-ollama-chatmodel-admin.component.html",
    standalone: false, providers: [ { provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false }, {
        provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIOllamaChatModelAdminComponent),
        multi: false
    }]
})
export class GeboAIOllamaChatModelAdminComponent extends BaseEntityEditingComponent<GOllamaChatModelConfig> {
    /**
     * The name of the entity being managed by this component
     */
    protected override entityName: string = "GOllamaChatModelConfig";
    
    /**
     * The types of secrets allowed for Ollama model configuration
     */
    allowedTypes: SecretInfo.SecretTypeEnum[] = [ SecretInfo.SecretTypeEnum.TOKEN];
    
    /**
     * Form group containing controls for all editable properties of an Ollama chat model configuration
     */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        baseUrl: new FormControl(),
        description: new FormControl(),
        modelTypeCode: new FormControl(),
        choosedModel: new FormControl(),
        defaultModel:new FormControl(),
        apiSecretCode: new FormControl(),
        temperature: new FormControl(),
        topP: new FormControl(),
        contextLength: new FormControl(),
        accessibleGroups: new FormControl(), 
        accessibleUsers: new FormControl(), 
        accessibleToAll: new FormControl(),
        enabledFunctions: new FormControl(),
        defaultModelPrompt:new FormControl()
    });
    
    /**
     * Stores the previous form values to detect changes
     */
    private oldValue:any={};
    
    /**
     * List of available chat models retrieved from the Ollama server
     */
    modelChoicesData: GBaseChatModelChoice[] = ([]);
    
    /**
     * Observable for available secrets in the Ollama context
     */
    identitiesObservable = this.secretControllerService.getSecretsByContextCode("ollama");
    
    /**
     * Action configuration for creating a new secret
     */
    public newSecretAction= newSecretActionRequest("ollama",this.entityName,this.entity);
    
    /**
     * Constructor initializes the component and sets up form value change detection
     * to dynamically load models when the baseUrl or apiSecretCode changes.
     */
    constructor(injector:Injector,geboFormGroupsService: GeboFormGroupsService,
        private chatModelConfigService: OllamaChatModelsConfigurationControllerService,        
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
     * Loads the available models from the Ollama server based on the provided baseUrl and apiSecretCode
     * @param newValue Object containing baseUrl and apiSecretCode to connect to the Ollama server
     */
    private loadModels(newValue: { baseUrl?: string, apiSecretCode?: string }) {
        this.loadingRelatedBackend = true;
        this.chatModelConfigService.getOllamaChatModels(newValue).subscribe({
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
     * Handles initialization when loading existing entity data
     * Sets the modelTypeCode and loads available models from the Ollama server
     * @param actualValue The loaded configuration entity
     */
    protected override onLoadedPersistentData(actualValue: GOllamaChatModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("chat-ollama");
        this.loadModels(actualValue);
    }
    
    /**
     * Handles initialization for new entity creation
     * Sets the default modelTypeCode for new Ollama chat models
     * @param actualValue The new configuration entity
     */
    protected override onNewData(actualValue: GOllamaChatModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("chat-ollama");
    }
    
    /**
     * Retrieves an Ollama chat model configuration by its code
     * @param code The unique identifier for the configuration
     * @returns Observable containing the found configuration or null
     */
    override findByCode(code: string): Observable<GOllamaChatModelConfig | null> {
        return this.chatModelConfigService.findOllamaChatModelConfigByCode(code);
    }
    
    /**
     * Saves changes to an existing Ollama chat model configuration
     * @param value The configuration to update
     * @returns Observable with the updated configuration
     */
    override save(value: GOllamaChatModelConfig): Observable<GOllamaChatModelConfig> {
        return this.chatModelConfigService.updateOllamaChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }
    
    /**
     * Creates a new Ollama chat model configuration
     * @param value The new configuration to insert
     * @returns Observable with the created configuration
     */
    override insert(value: GOllamaChatModelConfig): Observable<GOllamaChatModelConfig> {
        return this.chatModelConfigService.insertOllamaChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }
    
    /**
     * Deletes an Ollama chat model configuration
     * @param value The configuration to delete
     * @returns Observable indicating success or failure
     */
    override delete(value: GOllamaChatModelConfig): Observable<boolean> {
        return this.chatModelConfigService.deleteOllamaChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }))
    }
    
    /**
     * Checks if a configuration can be deleted
     * Currently always returns true, but could implement validation logic in the future
     * @param value The configuration to check
     * @returns Observable with deletion permission information
     */
    override canBeDeleted(value: GOllamaChatModelConfig): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

}