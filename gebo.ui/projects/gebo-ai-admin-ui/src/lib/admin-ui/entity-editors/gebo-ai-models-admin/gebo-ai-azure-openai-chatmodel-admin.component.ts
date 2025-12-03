/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




/*
 * AI generated comments
 * This module manages Azure/OpenAI chat model configurations in the Gebo.ai application.
 * It provides an interface for creating, editing, and deleting OpenAI chat model
 * configurations with associated settings like API keys, model selection, and
 * access controls.
 */
import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { AzureOpenAiChatModelsConfigurationControllerService, FunctionsLookupControllerService, GAzureOpenAIChatModelConfig, GBaseChatModelChoice, GLookupEntry, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, BaseEntityEditingComponentAutoDeleteCheck, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";
import { isValidUrl } from "../utils/url-ok";

/**
 * Component for managing OpenAI chat model configurations.
 * Provides UI for creating, updating, and deleting OpenAI chat model configurations.
 * Extends BaseEntityEditingComponent to use common entity editing functionality.
 */
@Component({
    selector: "gebo-ai-azure-open-ai-chat-model-admin-component",
    templateUrl: "gebo-ai-openai-chatmodel-admin.component.html",
    standalone: false, providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false }, {
        provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIAzureOpenAIChatModelAdminComponent),
        multi: false
    }]
})
export class GeboAIAzureOpenAIChatModelAdminComponent extends BaseEntityEditingComponentAutoDeleteCheck<GAzureOpenAIChatModelConfig> {
    /**
     * Name of the entity type being managed by this component
     */
    protected override entityName: string = "GAzureOpenAIChatModelConfig";

    /**
     * The allowed secret types for OpenAI API credentials
     */
    allowedTypes: SecretInfo.SecretTypeEnum[] = [SecretInfo.SecretTypeEnum.TOKEN];

    /**
     * Form group to manage the OpenAI chat model configuration fields
     */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        modelTypeCode: new FormControl(),
        defaultModel: new FormControl(),
        choosedModel: new FormControl(),
        apiSecretCode: new FormControl(),
        temperature: new FormControl(),
        baseUrl: new FormControl(),
        topP: new FormControl(),
        contextLength: new FormControl(),
        accessibleGroups: new FormControl(),
        accessibleUsers: new FormControl(),
        accessibleToAll: new FormControl(),
        enabledFunctions: new FormControl(),
        defaultModelPrompt: new FormControl()
    });

    /**
     * Stores previous form values to detect changes
     */
    private oldValue: any = {};

    /**
     * Available OpenAI model choices for selection
     */
    modelChoicesData: GBaseChatModelChoice[] = [];

    /**
     * Observable for retrieving OpenAI secrets
     */
    identitiesObservable = this.secretControllerService.getSecretsByContextCode("azure-openai");

    /**
     * Action for creating a new OpenAI secret
     */
    public newSecretAction = newSecretActionRequest("azure-openai", this.entityName, this.entity);

    /**
     * List of available functions for the chat model
     */
    public functionsList: GLookupEntry[] = [];

    /**
     * Constructor initializes the component with necessary services and sets up form change handling
     */
    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private openaiChatModelConfigService: AzureOpenAiChatModelsConfigurationControllerService,
        private functionsLookupControllerService: FunctionsLookupControllerService,
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
     * Initializes the component and loads available functions
     */
    override ngOnInit(): void {
        super.ngOnInit();
        this.loadingRelatedBackend = true;
        this.functionsLookupControllerService.getAllFunctions().subscribe({
            next: (value) => {
                this.functionsList = value;
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    /**
     * Loads available OpenAI models based on provided configuration
     * @param newValue Object containing baseUrl and/or apiSecretCode
     */
    private loadModels(newValue: { baseUrl?: string, apiSecretCode?: string }) {
        this.loadingRelatedBackend = true;
        this.openaiChatModelConfigService.getAzureOpenAIChatModels(newValue).subscribe({
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
     * Handles when persistent data is loaded into the component
     * @param actualValue The loaded OpenAI chat model configuration
     */
    protected override onLoadedPersistentData(actualValue: GAzureOpenAIChatModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("azure-chatgpt-OpenAI");
        this.loadModels(actualValue);
    }

    /**
     * Handles initialization for new data
     * @param actualValue Default values for new OpenAI chat model configuration
     */
    protected override onNewData(actualValue: GAzureOpenAIChatModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("azure-chatgpt-OpenAI");
    }

    /**
     * Finds an OpenAI chat model configuration by code
     * @param code The code to search for
     * @returns Observable with the matching configuration or null
     */
    override findByCode(code: string): Observable<GAzureOpenAIChatModelConfig | null> {
        return this.openaiChatModelConfigService.findAzureOpenAIChatModelConfigByCode(code);
    }

    /**
     * Saves an existing OpenAI chat model configuration
     * @param value The configuration to save
     * @returns Observable with the updated configuration
     */
    override save(value: GAzureOpenAIChatModelConfig): Observable<GAzureOpenAIChatModelConfig> {
        return this.openaiChatModelConfigService.updateAzureOpenAIChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    /**
     * Inserts a new OpenAI chat model configuration
     * @param value The new configuration to insert
     * @returns Observable with the inserted configuration
     */
    override insert(value: GAzureOpenAIChatModelConfig): Observable<GAzureOpenAIChatModelConfig> {
        return this.openaiChatModelConfigService.insertAzureOpenAIChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    /**
     * Deletes an OpenAI chat model configuration
     * @param value The configuration to delete
     * @returns Observable with the deletion result
     */
    override delete(value: GAzureOpenAIChatModelConfig): Observable<boolean> {
        return this.openaiChatModelConfigService.deleteAzureOpenAIChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }))
    }

   
}