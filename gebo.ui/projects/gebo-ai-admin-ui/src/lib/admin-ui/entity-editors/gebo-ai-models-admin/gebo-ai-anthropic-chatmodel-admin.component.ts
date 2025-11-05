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
 * This file defines the GeboAIAnthropicChatModelAdminComponent which manages the configuration
 * for Anthropic chat models within the Gebo.ai application. The component allows users to create,
 * edit, and delete Anthropic chat model configurations, including setting model parameters,
 * access controls, and API connections.
 */

import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { FunctionsLookupControllerService, GBaseChatModelChoice, GLookupEntry, GAnthropicChatModelConfig, SecretInfo, SecretsControllerService, AnthropicChatModelsConfigurationControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";

/**
 * Component responsible for managing Anthropic chat model configurations.
 * Extends BaseEntityEditingComponent to provide CRUD functionality for GAnthropicChatModelConfig entities.
 * This component displays a form for editing model settings like temperature, top-p, context length,
 * and manages API connections through secret keys.
 */
@Component({
    selector: "gebo-ai-anthropic-chat-model-admin-component",
    templateUrl: "gebo-ai-anthropic-chatmodel-admin.component.html",
    standalone: false, providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false }, 
        {
        provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIAnthropicChatModelAdminComponent),
        multi: false
    }]
})
export class GeboAIAnthropicChatModelAdminComponent extends BaseEntityEditingComponent<GAnthropicChatModelConfig> {
    /**
     * Entity name used for form identification and processing
     */
    protected override entityName: string = "GAnthropicChatModelConfig";

    /**
     * Allowed types of secrets that can be used with Anthropic models
     */
    allowedTypes: SecretInfo.SecretTypeEnum[] = [SecretInfo.SecretTypeEnum.TOKEN];

    /**
     * Form group containing all the configurable fields for an Anthropic chat model
     */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        modelTypeCode: new FormControl(),
        defaultModel: new FormControl(),
        choosedModel: new FormControl(),
        apiSecretCode: new FormControl(),
        temperature: new FormControl(),
        topP: new FormControl(),
        contextLength: new FormControl(),
        accessibleGroups: new FormControl(),
        accessibleUsers: new FormControl(),
        accessibleToAll: new FormControl(),
        enabledFunctions: new FormControl(),
        defaultModelPrompt: new FormControl()
    });

    /**
     * Maintains the previous value of the form to detect changes
     */
    private oldValue: any = {};

    /**
     * List of available Anthropic models to choose from
     */
    modelChoicesData: GBaseChatModelChoice[] = [];

    /**
     * Observable that provides the list of available secrets for Anthropic context
     */
    identitiesObservable = this.secretControllerService.getSecretsByContextCode("anthropic");

    /**
     * Action request for creating a new secret
     */
    public newSecretAction = newSecretActionRequest("anthropic", this.entityName, this.entity);

    /**
     * List of available functions that can be enabled for the model
     */
    public functionsList: GLookupEntry[] = [];

    /**
     * Constructor that initializes the component and sets up form change listeners
     */
    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private anthropicChatModelConfigService: AnthropicChatModelsConfigurationControllerService,
        private functionsLookupControllerService: FunctionsLookupControllerService,
        private secretControllerService: SecretsControllerService,
        confirmService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmService, geboUIActionRoutingService, outputForwardingService);
        this.formGroup.valueChanges.subscribe(newValue => {
            if (!newValue.baseUrl && !newValue.apiSecretCode) {
                this.modelChoicesData = [];
            } else if (newValue.baseUrl !== this.oldValue.baseUrl || newValue.apiSecretCode !== this.oldValue.apiSecretCode) {
                this.loadModels(newValue);
            }
            this.oldValue = newValue;

        });
        this.manageOperationStatus = true;
    }

    /**
     * Initializes the component and loads the list of available functions
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
     * Loads the available models from Anthropic based on the provided configuration
     * This is called when the API secret or baseUrl changes
     */
    private loadModels(newValue: { baseUrl?: string, apiSecretCode?: string }) {
        this.loadingRelatedBackend = true;
        this.anthropicChatModelConfigService.getAnthropicChatModels(newValue).subscribe({
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
     * Handler called when persistent data is loaded, sets the model type
     * and loads available models from Anthropic
     */
    protected override onLoadedPersistentData(actualValue: GAnthropicChatModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("chat-anthropic");
        this.loadModels(actualValue);
    }

    /**
     * Handler called when creating a new entity, sets default model type
     */
    protected override onNewData(actualValue: GAnthropicChatModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("chat-anthropic");
    }

    /**
     * Retrieves an Anthropic chat model configuration by its code
     */
    override findByCode(code: string): Observable<GAnthropicChatModelConfig | null> {
        return this.anthropicChatModelConfigService.findAnthropicChatModelConfigByCode(code);
    }

    /**
     * Saves an existing Anthropic chat model configuration
     */
    override save(value: GAnthropicChatModelConfig): Observable<GAnthropicChatModelConfig> {
        return this.anthropicChatModelConfigService.updateAnthropicChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    /**
     * Inserts a new Anthropic chat model configuration
     */
    override insert(value: GAnthropicChatModelConfig): Observable<GAnthropicChatModelConfig> {
        return this.anthropicChatModelConfigService.insertAnthropicChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    /**
     * Deletes an Anthropic chat model configuration
     */
    override delete(value: GAnthropicChatModelConfig): Observable<boolean> {
        return this.anthropicChatModelConfigService.deleteAnthropicChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }))
    }

    /**
     * Checks if the given Anthropic chat model configuration can be deleted
     * Currently always returns true as there are no restrictions implemented
     */
    override canBeDeleted(value: GAnthropicChatModelConfig): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

}