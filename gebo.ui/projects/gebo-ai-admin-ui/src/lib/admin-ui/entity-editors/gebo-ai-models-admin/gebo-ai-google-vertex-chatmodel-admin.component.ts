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
 * This file implements a component for managing Google Vertex AI chat model configurations.
 * It extends BaseEntityEditingComponent to provide CRUD operations for GGoogleVertexChatModelConfig entities.
 * The component allows users to configure model settings, select models, manage API secrets, and configure
 * accessibility and function settings.
 */

import { Component, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { FunctionsLookupControllerService, GBaseChatModelChoice, GGoogleVertexChatModelConfig, GLookupEntry, GoogleVertexChatModelsConfigurationControllerService, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";
import { isValidUrl } from "../utils/url-ok";

/**
 * Component responsible for managing Google Vertex AI chat model configurations.
 * Provides UI for creating, editing, and deleting chat model configurations 
 * with specific settings for the Google Vertex AI platform.
 */
@Component({
    selector: "gebo-ai-google-vertex-chat-model-admin-component",
    templateUrl: "gebo-ai-google-vertex-chatmodel-admin.component.html",
    standalone: false
})
export class GeboAIGoogleVertexChatModelAdminComponent extends BaseEntityEditingComponent<GGoogleVertexChatModelConfig> {
    protected override entityName: string = "GGoogleVertexChatModelConfig";
    allowedTypes: SecretInfo.SecretTypeEnum[] = [ SecretInfo.SecretTypeEnum.TOKEN];
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
    private oldValue:any={};
    modelChoicesData: GBaseChatModelChoice[] = [];
    identitiesObservable = this.secretControllerService.getSecretsByContextCode("google-vertex");
    public newSecretAction= newSecretActionRequest("google-vertex",this.entityName,this.entity,["OAUTH2_GOOGLE","GOOGLE_CLOUD_JSON_CREDENTIALS"]);
    public functionsList:GLookupEntry[]=[];
    
    /**
     * Constructor initializes the component with required dependencies and sets up form value change subscription
     * to automatically reload models when baseUrl or apiSecretCode change.
     */
    constructor(injector:Injector,geboFormGroupsService: GeboFormGroupsService,
        private chatModelConfigService: GoogleVertexChatModelsConfigurationControllerService,
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
     * Initializes the component, sets up loading state and fetches the list of available functions
     * that can be enabled for the chat model.
     */
    override ngOnInit(): void {
        super.ngOnInit();
        this.loadingRelatedBackend=true;
        this.manageOperationStatus=true;
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
     * Loads available models from Google Vertex AI based on the provided configuration.
     * Updates the model choices dropdown with the fetched models.
     * 
     * @param newValue Object containing baseUrl and apiSecretCode needed to fetch models
     */
    private loadModels(newValue: { baseUrl?: string, apiSecretCode?: string }) {
        this.loadingRelatedBackend = true;
        this.chatModelConfigService.getGoogleVertexChatModels(newValue).subscribe({
            next: (r) => {
                //this.backendMessages = r.messages as Message[];
                this.updateLastOperationStatus(r as any);
                this.modelChoicesData = r.result ? r.result : []
            },
            complete:()=>{
                this.loadingRelatedBackend=false;
            }
        });
    }
    
    /**
     * Called when loading existing configuration data.
     * Sets the model type code and loads available models.
     * 
     * @param actualValue The loaded configuration data
     */
    protected override onLoadedPersistentData(actualValue: GGoogleVertexChatModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("chatmodel-google-vertex");
        this.loadModels(actualValue);
    }
    
    /**
     * Called when creating a new configuration.
     * Sets the default model type code for Google Vertex AI.
     * 
     * @param actualValue The new configuration data
     */
    protected override onNewData(actualValue: GGoogleVertexChatModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("chatmodel-google-vertex");
    }
    
    /**
     * Retrieves a configuration by its code identifier.
     * 
     * @param code The unique code of the configuration to find
     * @returns An Observable with the found configuration or null
     */
    override findByCode(code: string): Observable<GGoogleVertexChatModelConfig | null> {
        return this.chatModelConfigService.findGoogleVertexChatModelConfigByCode(code);
    }
    
    /**
     * Saves changes to an existing configuration.
     * Updates the operation status based on the server response.
     * 
     * @param value The configuration to save
     * @returns An Observable with the saved configuration
     */
    override save(value: GGoogleVertexChatModelConfig): Observable<GGoogleVertexChatModelConfig> {
        return this.chatModelConfigService.updateGoogleVertexChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }
    
    /**
     * Creates a new configuration.
     * Updates the operation status based on the server response.
     * 
     * @param value The new configuration to create
     * @returns An Observable with the created configuration
     */
    override insert(value: GGoogleVertexChatModelConfig): Observable<GGoogleVertexChatModelConfig> {
        return this.chatModelConfigService.insertGoogleVertexChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }
    
    /**
     * Deletes a configuration.
     * Updates the operation status based on the server response.
     * 
     * @param value The configuration to delete
     * @returns An Observable indicating success or failure
     */
    override delete(value: GGoogleVertexChatModelConfig): Observable<boolean> {
        return this.chatModelConfigService.deleteGoogleVertexChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }))
    }
    
    /**
     * Checks if a configuration can be deleted.
     * Currently always returns true as there are no specific deletion constraints.
     * 
     * @param value The configuration to check
     * @returns An Observable with the deletion check result
     */
    override canBeDeleted(value: GGoogleVertexChatModelConfig): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

}