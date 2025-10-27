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
 * This file contains the implementation of the Deepseek Chat Model Administration component,
 * which allows users to manage Deepseek chat model configurations in the Gebo.ai platform.
 */

import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { FunctionsLookupControllerService, GBaseChatModelChoice, GLookupEntry, SecretInfo, SecretsControllerService, GDeepseekChatModelConfig, DeepseekChatModelsConfigurationControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";

/**
 * Component for administering Deepseek chat model configurations.
 * This component extends the BaseEntityEditingComponent and provides functionality
 * for creating, updating, and deleting Deepseek chat model configurations.
 * It includes form controls for various configuration parameters and integrates with
 * related services for managing secrets and functions.
 */
@Component({
    selector: "gebo-ai-deepseek-chat-model-admin-component",
    templateUrl: "gebo-ai-deepseek-chatmodel-admin.component.html",
    standalone: false, providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false }, 
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIDeepseekChatModelAdminComponent),  multi: false   }
    ]
})
export class GeboAIDeepseekChatModelAdminComponent extends BaseEntityEditingComponent<GDeepseekChatModelConfig> {
    /**
     * The name of the entity being managed by this component
     */
    protected override entityName: string = "GDeepseekChatModelConfig";
    
    /**
     * Allowed types of secrets for this model (only token-based authentication supported)
     */
    allowedTypes: SecretInfo.SecretTypeEnum[] = [ SecretInfo.SecretTypeEnum.TOKEN];
    
    /**
     * Form group containing all configuration fields for the Deepseek chat model
     */
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
    
    /**
     * Tracks the previous form value to detect changes for model loading
     */
    private oldValue:any={};
    
    /**
     * Available model choices fetched from the Deepseek API
     */
    modelChoicesData: GBaseChatModelChoice[] = [];
    
    /**
     * Observable for Deepseek-related identity secrets
     */
    identitiesObservable = this.secretControllerService.getSecretsByContextCode("deepseek");
    
    /**
     * Action configuration for creating a new secret
     */
    public newSecretAction= newSecretActionRequest("deepseek",this.entityName,this.entity);
    
    /**
     * List of available functions that can be enabled for this model
     */
    public functionsList:GLookupEntry[]=[];

    /**
     * Constructor initializes services and sets up form value change monitoring
     * to automatically load models when relevant configuration changes
     */
    constructor(injector:Injector,geboFormGroupsService: GeboFormGroupsService,
        private deepseekChatModelConfigService: DeepseekChatModelsConfigurationControllerService,
        private functionsLookupControllerService:FunctionsLookupControllerService,
        private secretControllerService: SecretsControllerService,
        confirmService:ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector,geboFormGroupsService, confirmService,geboUIActionRoutingService,outputForwardingService);
        this.formGroup.valueChanges.subscribe(newValue=>{
            if (!newValue.baseUrl && !newValue.apiSecretCode) {
                this.modelChoicesData=[];
            }else if (newValue.baseUrl!==this.oldValue.baseUrl || newValue.apiSecretCode!==this.oldValue.apiSecretCode) {
                this.loadModels(newValue);
            }
            this.oldValue=newValue;

        }); 
        this.manageOperationStatus=true;
    }

    /**
     * Initializes the component and loads available functions
     * from the backend service
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
     * Loads available Deepseek models based on the provided configuration
     * @param newValue The configuration containing baseUrl and apiSecretCode
     */
    private loadModels(newValue: { baseUrl?: string, apiSecretCode?: string }) {
        this.loadingRelatedBackend = true;
        this.deepseekChatModelConfigService.getDeepseekChatModels(newValue).subscribe({
            next: (r) => {
                this.updateLastOperationStatus(r as any);
                this.modelChoicesData = r.result ? r.result : [];
            },
            complete:()=>{
                this.loadingRelatedBackend=false;
            }
        });
    }

    /**
     * Handles initialization when loading existing configuration data
     * @param actualValue The loaded configuration data
     */
    protected override onLoadedPersistentData(actualValue: GDeepseekChatModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("chat-deepseek");
        this.loadModels(actualValue);
    }

    /**
     * Handles initialization for new configuration creation
     * @param actualValue The initial configuration data
     */
    protected override onNewData(actualValue: GDeepseekChatModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("chat-deepseek");
    }

    /**
     * Finds a Deepseek chat model configuration by its code
     * @param code The unique code of the configuration to find
     * @returns Observable containing the found configuration or null
     */
    override findByCode(code: string): Observable<GDeepseekChatModelConfig | null> {
        return this.deepseekChatModelConfigService.findDeepseekChatModelConfigByCode(code);
    }

    /**
     * Saves changes to an existing Deepseek chat model configuration
     * @param value The configuration data to save
     * @returns Observable containing the updated configuration
     */
    override save(value: GDeepseekChatModelConfig): Observable<GDeepseekChatModelConfig> {
        return this.deepseekChatModelConfigService.updateDeepseekChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    /**
     * Creates a new Deepseek chat model configuration
     * @param value The configuration data to insert
     * @returns Observable containing the created configuration
     */
    override insert(value: GDeepseekChatModelConfig): Observable<GDeepseekChatModelConfig> {
        return this.deepseekChatModelConfigService.insertDeepseekChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    /**
     * Deletes a Deepseek chat model configuration
     * @param value The configuration to delete
     * @returns Observable indicating whether deletion was successful
     */
    override delete(value: GDeepseekChatModelConfig): Observable<boolean> {
        return this.deepseekChatModelConfigService.deleteDeepseekChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }))
    }

    /**
     * Determines whether a Deepseek chat model configuration can be deleted
     * @param value The configuration to check
     * @returns Observable containing the deletion possibility status and message
     */
    override canBeDeleted(value: GDeepseekChatModelConfig): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

}