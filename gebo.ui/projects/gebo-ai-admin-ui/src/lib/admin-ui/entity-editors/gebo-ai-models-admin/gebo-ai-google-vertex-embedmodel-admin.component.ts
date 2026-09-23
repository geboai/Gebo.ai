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
 * This file defines a component for managing Google Vertex AI embedding model configurations.
 * It extends BaseEntityEditingComponent to provide editing functionality for 
 * Google Vertex embedding model configurations, including CRUD operations,
 * model selection, and secret management integration.
 */
import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GBaseChatModelChoice, GGoogleVertexEmbeddingModelConfig, GoogleVertexEmbeddingModelsConfigurationControllerService, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, BaseEntityEditingComponentAutoDeleteCheck, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";
import { isValidUrl } from "../utils/url-ok";

/**
 * Component responsible for administering Google Vertex AI embedding models.
 * Provides a user interface for creating, updating, and managing Google Vertex
 * embedding model configurations. Handles communication with backend services
 * for CRUD operations and fetching available models.
 */
@Component({
    selector: "gebo-ai-google-vertex-embed-model-admin-component",
    templateUrl: "gebo-ai-google-vertex-embedmodel-admin.component.html",
    standalone: false, providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false }, 
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIGoogleVertexEmbedModelAdminComponent), multi: false  }
    ]
})
export class GeboAIGoogleVertexEmbedModelAdminComponent extends BaseEntityEditingComponentAutoDeleteCheck<GGoogleVertexEmbeddingModelConfig> {
    /**
     * Entity name used for component identification and logging
     */
    protected override entityName: string = "GGoogleVertexEmbeddingModelConfig";
    
    /**
     * Array of allowed secret types for this component, currently only TOKEN type is supported
     */
    allowedTypes: SecretInfo.SecretTypeEnum[] = [SecretInfo.SecretTypeEnum.TOKEN];
    
    /**
     * Form group containing form controls for the embedding model configuration
     */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        modelTypeCode: new FormControl(),
        modelCode: new FormControl(),
        defaultModel: new FormControl(),
        choosedModel: new FormControl(),
        apiSecretCode: new FormControl()
    });
    
    /**
     * Stores previous form values to detect changes
     */
    private oldValue: any = {};
    
    /**
     * Available model choices retrieved from the API
     */
    public modelChoicesData: GBaseChatModelChoice[] = [];
    
    /**
     * Observable that fetches Google Vertex secrets from the backend
     */
    public identitiesObservable = this.secretControllerService.getSecretsByContextCode("google-vertex");
    
    /**
     * Action configuration for creating new secrets for Google Vertex
     */
    public newSecretAction = newSecretActionRequest("google-vertex", this.entityName, this.entity, ["OAUTH2_GOOGLE", "GOOGLE_CLOUD_JSON_CREDENTIALS"]);
    
    /**
     * Component constructor that initializes services and sets up form value change subscription
     * to update models when relevant fields change
     */
    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private openaiEmbedModelConfigService: GoogleVertexEmbeddingModelsConfigurationControllerService,
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
     * Loads available embedding models from Google Vertex AI API
     * based on the provided base URL and API secret code
     * @param newValue Object containing baseUrl and apiSecretCode
     */
    private loadModels(newValue: { baseUrl?: string, apiSecretCode?: string }) {
        this.loadingRelatedBackend = true;
        this.openaiEmbedModelConfigService.getGoogleVertexEmbeddingModels(newValue).subscribe({
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
     * Handles initialization after loading existing data
     * Sets the model type code and loads available models
     * @param actualValue The loaded entity data
     */
    protected override onLoadedPersistentData(actualValue: GGoogleVertexEmbeddingModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("embedding-google-vertex");
        this.loadModels(actualValue);
    }
    
    /**
     * Handles initialization for new entity creation
     * Sets default model type code
     * @param actualValue The new entity template data
     */
    protected override onNewData(actualValue: GGoogleVertexEmbeddingModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("embedding-google-vertex");
    }
    
    /**
     * Fetches a Google Vertex embedding model configuration by its code
     * @param code The unique code identifier of the configuration
     * @returns Observable with the found configuration or null
     */
    override findByCode(code: string): Observable<GGoogleVertexEmbeddingModelConfig | null> {
        return this.openaiEmbedModelConfigService.findGoogleVertexEmbeddingModelConfigByCode(code)
    }

    /**
     * Saves changes to an existing Google Vertex embedding model configuration
     * @param value The updated configuration data
     * @returns Observable with the saved configuration
     */
    override save(value: GGoogleVertexEmbeddingModelConfig): Observable<GGoogleVertexEmbeddingModelConfig> {

        return this.openaiEmbedModelConfigService.updateGoogleVertexEmbeddingModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }
    
    /**
     * Creates a new Google Vertex embedding model configuration
     * @param value The new configuration data
     * @returns Observable with the created configuration
     */
    override insert(value: GGoogleVertexEmbeddingModelConfig): Observable<GGoogleVertexEmbeddingModelConfig> {

        return this.openaiEmbedModelConfigService.insertGoogleVertexEmbeddingModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }
    
    /**
     * Deletes a Google Vertex embedding model configuration
     * @param value The configuration to delete
     * @returns Observable indicating success or failure
     */
    override delete(value: GGoogleVertexEmbeddingModelConfig): Observable<boolean> {
        return this.openaiEmbedModelConfigService.deleteGoogleVertexEmbeddingModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }))
    }
    
    

}