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
 * This file implements an Angular component for administering Generic OpenAI API Chat models.
 * It provides functionality to create, update, delete, and manage configuration settings for
 * OpenAI API chat models within the Gebo.ai system.
 */

import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { FunctionsLookupControllerService, GBaseChatModelChoice, GenericOpenAIAPIChatModelConfig, GenericOpenAiapiChatModelsConfigurationControllerService, GenericOpenAIChatModelTypeConfig, GLookupEntry, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, BaseEntityEditingComponentAutoDeleteCheck, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRequest, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { forkJoin, map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";
import { isValidUrl } from "../utils/url-ok";

/**
 * Component for administering Generic OpenAI API Chat model configurations.
 * This component extends BaseEntityEditingComponent and provides a user interface 
 * for managing OpenAI API chat model settings including API credentials, model types,
 * context length, temperature, and access permissions.
 */
@Component({
    selector: "gebo-ai-generic-open-ai-api-chat-model-admin-component",
    templateUrl: "gebo-ai-generic-openai-api-chatmodel-admin.component.html",
    standalone: false, providers: [ 
        { provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false }, 
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIGenericOpenAIAPIChatModelAdminComponent),    multi: false  }
    ]
})
export class GeboAIGenericOpenAIAPIChatModelAdminComponent extends BaseEntityEditingComponentAutoDeleteCheck<GenericOpenAIAPIChatModelConfig> {
    /**
     * Entity name used for identification throughout the component
     */
    protected override entityName: string = "GenericOpenAIAPIChatModelConfig";
    
    /**
     * Defines the allowed secret types for this component
     */
    allowedTypes: SecretInfo.SecretTypeEnum[] = [ SecretInfo.SecretTypeEnum.TOKEN];
    
    /**
     * FormGroup defining all the form controls needed for the OpenAI API model configuration
     */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        modelTypeCode: new FormControl(),
        defaultModel:new FormControl(),
        choosedModel: new FormControl(),
        apiSecretCode: new FormControl(),
        contextLength: new FormControl(),
        temperature: new FormControl(),
        topP: new FormControl(),
        accessibleGroups: new FormControl(), 
        accessibleUsers: new FormControl(), 
        accessibleToAll: new FormControl(),
        enabledFunctions: new FormControl(),
        defaultModelPrompt:new FormControl(),
        baseUrl:new FormControl(),
        unautenticated:new FormControl()

    });
    
    /**
     * Tracks previous form values for comparison to detect changes
     */
    private oldValue:any={};
    
    /**
     * Stores available model choices for the selected API configuration
     */
    modelChoicesData: GBaseChatModelChoice[] = [];
    
    /**
     * Observable for retrieving available identity/secret information
     */
    public identitiesObservable?:Observable<SecretInfo[]> = of([]);
    
    /**
     * Action request for creating a new secret
     */
    public newSecretAction?:GeboUIActionRequest;
    
    /**
     * List of available functions that can be enabled for the model
     */
    public functionsList:GLookupEntry[]=[];
    
    /**
     * Available model types for OpenAI API configurations
     */
    public modelTypes?: GenericOpenAIChatModelTypeConfig[];
    
    /**
     * Currently selected model type
     */
    public modelType?:GenericOpenAIChatModelTypeConfig;

    /**
     * Component constructor that initializes services and sets up form value change subscriptions
     */
    constructor(injector:Injector,geboFormGroupsService: GeboFormGroupsService,
        private openaiChatModelConfigService: GenericOpenAiapiChatModelsConfigurationControllerService,
        private functionsLookupControllerService:FunctionsLookupControllerService,
        private secretControllerService: SecretsControllerService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        confirmService:ConfirmationService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector,geboFormGroupsService, confirmService,geboUIActionRoutingService, outputForwardingService);
        this.formGroup.valueChanges.subscribe(newValue=>{
            if (!newValue.baseUrl && !newValue.apiSecretCode) {
                this.modelChoicesData=[];
            }else if ((newValue.baseUrl!==this.oldValue.baseUrl && isValidUrl(newValue.baseUrl) )|| newValue.apiSecretCode!==this.oldValue.apiSecretCode) {
                this.loadModels(newValue);
            }
            this.oldValue=newValue;

        }); 
        this.manageOperationStatus=true;
    }

    /**
     * Lifecycle hook that initializes component data including functions list and model types
     */
    override ngOnInit(): void {
        super.ngOnInit();
        
        const obs:[Observable<GLookupEntry[]>,Observable<GenericOpenAIChatModelTypeConfig[]>]=[this.functionsLookupControllerService.getAllFunctions(),this.openaiChatModelConfigService.getGenericOpenAIChatModelTypes()];
        this.loadingRelatedBackend=true;
        forkJoin(obs).subscribe({
            next:(values)=>{
                this.functionsList=values[0];
                this.modelTypes=values[1];
                if (this.modelType?.optionalAuthentication===true) {
                    this.formGroup.controls["apiSecretCode"].setValidators(Validators.required);
                }
                this.formGroup.updateValueAndValidity();
                if (this.entity)
                this.refreshProviderModel(this.entity);
            },
            complete:()=>{
                this.loadingRelatedBackend=false;
            }
        })
        
    }

    /**
     * Updates model provider information based on the selected model type
     * Sets up identities observable and configures baseUrl if necessary
     * @param actualValue The current model configuration
     */
    private refreshProviderModel(actualValue: GenericOpenAIAPIChatModelConfig) {
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
     * Loads available models from the OpenAI API using the provided configuration
     * @param newValue Object containing baseUrl and apiSecretCode used to authenticate with the API
     */
    private loadModels(newValue: { baseUrl?: string, apiSecretCode?: string }) {
        this.loadingRelatedBackend = true;
        this.openaiChatModelConfigService.getGenericOpenAIAPIChatModels(newValue).subscribe({
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
     * Called when persistent data is loaded, refreshes provider model and loads available models
     * @param actualValue The loaded model configuration
     */
    protected override onLoadedPersistentData(actualValue: GenericOpenAIAPIChatModelConfig): void {
        this.refreshProviderModel(actualValue);
        this.loadModels(actualValue);
    }

    /**
     * Called when creating a new entity, initializes provider model data
     * @param actualValue The new model configuration
     */
    protected override onNewData(actualValue: GenericOpenAIAPIChatModelConfig): void {
        this.refreshProviderModel(actualValue);
    }

    /**
     * Retrieves a model configuration by its code
     * @param code The code identifying the model configuration
     * @returns An Observable with the model configuration or null
     */
    override findByCode(code: string): Observable<GenericOpenAIAPIChatModelConfig | null> {
        return this.openaiChatModelConfigService.findGenericOpenAIAPIChatModelConfigByCode(code);
    }

    /**
     * Saves updates to an existing model configuration
     * @param value The model configuration to update
     * @returns An Observable with the updated model configuration
     */
    override save(value: GenericOpenAIAPIChatModelConfig): Observable<GenericOpenAIAPIChatModelConfig> {
        return this.openaiChatModelConfigService.updateGenericOpenAIAPIChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result );
            if (!result?.result) return value;
            return result.result;
        }));
    }

    /**
     * Inserts a new model configuration
     * @param value The model configuration to insert
     * @returns An Observable with the inserted model configuration
     */
    override insert(value: GenericOpenAIAPIChatModelConfig): Observable<GenericOpenAIAPIChatModelConfig> {
        return this.openaiChatModelConfigService.insertGenericOpenAIAPIChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result );
            if (!result?.result) return value;
            return result.result;
        }));
    }

    /**
     * Deletes a model configuration
     * @param value The model configuration to delete
     * @returns An Observable indicating success or failure
     */
    override delete(value: GenericOpenAIAPIChatModelConfig): Observable<boolean> {
        return this.openaiChatModelConfigService.deleteGenericOpenAIAPIChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }))
    }

   

}