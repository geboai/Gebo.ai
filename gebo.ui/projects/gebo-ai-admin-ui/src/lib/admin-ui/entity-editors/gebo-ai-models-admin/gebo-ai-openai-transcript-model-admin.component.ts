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
 * This component is responsible for managing OpenAI transcript model configurations.
 * It extends BaseEntityEditingComponent to handle CRUD operations for
 * GOpenAITranscriptModelConfig entities. The component provides UI for creating,
 * editing, and deleting OpenAI transcript model configurations, fetching available
 * models from the OpenAI API, and managing API secret connections.
 */
import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GOpenAITranscriptModelChoice, GOpenAITranscriptModelConfig, OpenAiTranscriptModelsConfigurationControllerService, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponentAutoDeleteCheck, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";
import { isValidUrl } from "../utils/url-ok";

@Component({
    selector: "gebo-ai-open-ai-transcript-model-admin-component",
    templateUrl: "gebo-ai-openai-transcript-model-admin.component.html",
    standalone: false, providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIOpenAITranscriptModelAdminComponent),
        multi: false
    }]

})
export class GeboAIOpenAITranscriptModelAdminComponent extends BaseEntityEditingComponentAutoDeleteCheck<GOpenAITranscriptModelConfig> {
    /**
     * The entity name identifier for the GOpenAITranscriptModelConfig
     */
    protected override entityName: string = "GOpenAITranscriptModelConfig";

    /**
     * Types of secrets allowed for this component - limited to TOKEN type
     */
    allowedTypes: SecretInfo.SecretTypeEnum[] = [SecretInfo.SecretTypeEnum.TOKEN];

    /**
     * Form group to handle the editable properties of the transcript model config
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
     * Keeps track of the previous form values to detect changes
     */
    private oldValue: any = {};

    /**
     * Available model choices data from OpenAI
     */
    modelChoicesData: GOpenAITranscriptModelChoice[] = [];

    /**
     * Observable for retrieving OpenAI identity secrets
     */
    identitiesObservable = this.secretControllerService.getSecretsByContextCode("openai");

    /**
     * Action request for creating a new secret
     */
    public newSecretAction = newSecretActionRequest("openai", this.entityName, this.entity);

    /**
     * Component constructor that initializes the form and sets up value change detection
     * to update models when relevant form fields change
     */
    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private openaiTranscriptModelConfigService: OpenAiTranscriptModelsConfigurationControllerService,
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
    }

    /**
     * Loads available transcript models from OpenAI based on the provided URL and API secret
     * Sets the loading state and updates model choices data once retrieved
     * @param newValue Object containing baseUrl and apiSecretCode
     */
    private loadModels(newValue: { baseUrl?: string, apiSecretCode?: string }) {
        this.loadingRelatedBackend = true;
        this.openaiTranscriptModelConfigService.getOpenAITranscriptModels(newValue).subscribe({
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
     * Handles initializing the form when editing an existing entity
     * Sets model type code and loads available models
     * @param actualValue The existing model configuration being loaded
     */
    protected override onLoadedPersistentData(actualValue: GOpenAITranscriptModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("openai-transcript");
        this.loadModels(actualValue);
    }

    /**
     * Handles initializing the form when creating a new entity
     * Sets the default model type code
     * @param actualValue The new model configuration being created
     */
    protected override onNewData(actualValue: GOpenAITranscriptModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("openai-transcript");
    }

    /**
     * Retrieves a transcript model configuration by its code
     * @param code The unique code of the model configuration to find
     * @returns Observable containing the found configuration or null
     */
    override findByCode(code: string): Observable<GOpenAITranscriptModelConfig | null> {
        return this.openaiTranscriptModelConfigService.findOpenAITranscriptModelConfigByCode(code)
    }

    /**
     * Updates an existing transcript model configuration
     * @param value The model configuration to update
     * @returns Observable containing the updated configuration
     */
    override save(value: GOpenAITranscriptModelConfig): Observable<GOpenAITranscriptModelConfig> {

        return this.openaiTranscriptModelConfigService.updateOpenAITranscriptModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    /**
     * Creates a new transcript model configuration
     * @param value The model configuration to create
     * @returns Observable containing the created configuration
     */
    override insert(value: GOpenAITranscriptModelConfig): Observable<GOpenAITranscriptModelConfig> {

        return this.openaiTranscriptModelConfigService.insertOpenAITranscriptModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    /**
     * Deletes a transcript model configuration
     * @param value The model configuration to delete
     * @returns Observable indicating whether the delete operation was successful
     */
    override delete(value: GOpenAITranscriptModelConfig): Observable<boolean> {
        return this.openaiTranscriptModelConfigService.deleteOpenAITranscriptModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }))
    }



}
