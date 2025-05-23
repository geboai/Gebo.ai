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
 * This file contains a component for administering Mistral AI chat model configurations in the Gebo.ai application.
 * It handles creation, reading, updating, and deletion of Mistral AI chat model configurations, as well as
 * managing related data like secrets, functions, and model choices.
 */

import { Component, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { FunctionsLookupControllerService, GBaseChatModelChoice, GLookupEntry, GMistralChatModelConfig, MistralAiChatModelsConfigurationControllerService, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";
import { isValidUrl } from "../utils/url-ok";

/**
 * Component responsible for managing Mistral AI chat model configurations.
 * Extends BaseEntityEditingComponent to provide CRUD operations for GMistralChatModelConfig entities.
 * Handles form management, model data fetching, and interactions with related services.
 */
@Component({
    selector: "gebo-ai-mistral-ai-chat-model-admin-component",
    templateUrl: "gebo-ai-mistralai-chatmodel-admin.component.html",
    standalone: false
})
export class GeboAIMistralAIChatModelAdminComponent extends BaseEntityEditingComponent<GMistralChatModelConfig> {
    /** The entity name used for identification and operations */
    protected override entityName: string = "GMistralChatModelConfig";
    
    /** List of allowed secret types for this model configuration */
    allowedTypes: SecretInfo.SecretTypeEnum[] = [ SecretInfo.SecretTypeEnum.TOKEN];
    
    /** Form group containing controls for all editable fields of the model configuration */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        modelTypeCode: new FormControl(),
        defaultModel:new FormControl(),
        choosedModel: new FormControl(),
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
    
    /** Keeps track of previous form values to detect changes */
    private oldValue:any={};
    
    /** List of available model choices from Mistral AI */
    modelChoicesData: GBaseChatModelChoice[] = [];
    
    /** Observable for getting identities related to Mistral AI context */
    identitiesObservable = this.secretControllerService.getSecretsByContextCode("mistralai");
    
    /** Action configuration for creating new Mistral AI secrets */
    public newSecretAction= newSecretActionRequest("mistralai",this.entityName,this.entity);
    
    /** List of available functions for the model */
    public functionsList:GLookupEntry[]=[];

    /**
     * Constructor initializes the component and sets up form value change subscription.
     * The subscription refreshes model choices when API secret or base URL changes.
     */
    constructor(injector:Injector,geboFormGroupsService: GeboFormGroupsService,
        private openaiChatModelConfigService: MistralAiChatModelsConfigurationControllerService,
        private functionsLookupControllerService:FunctionsLookupControllerService,
        private secretControllerService: SecretsControllerService,
        confirmService:ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector,geboFormGroupsService, confirmService,geboUIActionRoutingService, outputForwardingService);
        this.formGroup.valueChanges.subscribe(newValue=>{
            if (!newValue.baseUrl && !newValue.apiSecretCode) {
                this.modelChoicesData=[];
            }else if ((newValue.baseUrl!==this.oldValue.baseUrl && isValidUrl(newValue.baseUrl))  || newValue.apiSecretCode!==this.oldValue.apiSecretCode) {
                this.loadModels(newValue);
            }
            this.oldValue=newValue;

        }); 
        this.manageOperationStatus=true;
    }

    /**
     * Initializes the component and loads available functions from the backend.
     * Extends the parent class initialization.
     */
    override ngOnInit(): void {
        super.ngOnInit();
        this.loadingRelatedBackend=true;
        this.functionsLookupControllerService.getAllFunctions().subscribe({
            next:(value)=>{
                this.functionsList=value;
            },
            complete:()=>{
                this.loadingRelatedBackend=false;
            }
        });
    }

    /**
     * Loads available Mistral AI models based on the provided configuration.
     * Sets loading state and updates model choices when data is received.
     * 
     * @param newValue The configuration containing baseUrl and apiSecretCode
     */
    private loadModels(newValue: { baseUrl?: string, apiSecretCode?: string }) {
        this.loadingRelatedBackend = true;
        this.openaiChatModelConfigService.getMistralAIChatModels(newValue).subscribe({
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
     * Handles setup when loading existing model configuration data.
     * Sets the model type code and loads available models.
     * 
     * @param actualValue The loaded model configuration
     */
    protected override onLoadedPersistentData(actualValue: GMistralChatModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("chatmodel-mistral");
        this.loadModels(actualValue);
    }

    /**
     * Handles setup for new model configuration.
     * Sets the default model type code.
     * 
     * @param actualValue The new model configuration
     */
    protected override onNewData(actualValue: GMistralChatModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("chatmodel-mistral");
    }

    /**
     * Retrieves a model configuration by its code.
     * 
     * @param code The unique code of the model configuration
     * @returns An Observable with the found configuration or null
     */
    override findByCode(code: string): Observable<GMistralChatModelConfig | null> {
        return this.openaiChatModelConfigService.findMistralAIChatModelConfigByCode(code);
    }

    /**
     * Saves (updates) an existing model configuration.
     * 
     * @param value The model configuration to save
     * @returns An Observable with the saved configuration
     */
    override save(value: GMistralChatModelConfig): Observable<GMistralChatModelConfig> {
        return this.openaiChatModelConfigService.updateMistralAIChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    /**
     * Inserts a new model configuration.
     * 
     * @param value The model configuration to insert
     * @returns An Observable with the inserted configuration
     */
    override insert(value: GMistralChatModelConfig): Observable<GMistralChatModelConfig> {
        return this.openaiChatModelConfigService.insertMistralAIChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    /**
     * Deletes a model configuration.
     * 
     * @param value The model configuration to delete
     * @returns An Observable indicating success or failure
     */
    override delete(value: GMistralChatModelConfig): Observable<boolean> {
        return this.openaiChatModelConfigService.deleteMistralAIChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }))
    }

    /**
     * Checks if a model configuration can be deleted.
     * Currently always returns true.
     * 
     * @param value The model configuration to check
     * @returns An Observable with the deletion possibility status
     */
    override canBeDeleted(value: GMistralChatModelConfig): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }
}