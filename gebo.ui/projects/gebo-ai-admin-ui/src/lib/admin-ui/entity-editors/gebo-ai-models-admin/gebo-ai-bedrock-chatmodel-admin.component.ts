/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { FunctionsLookupControllerService, GBaseChatModelChoice, GLookupEntry, SecretInfo, SecretsControllerService, GBedrockChatModelConfig, BedrockChatModelsConfigurationControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponentAutoDeleteCheck, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";

/**
 * Component for administering AWS Bedrock chat model configurations (Converse API).
 */
@Component({
    selector: "gebo-ai-bedrock-chat-model-admin-component",
    templateUrl: "gebo-ai-bedrock-chatmodel-admin.component.html",
    standalone: false, providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIBedrockChatModelAdminComponent), multi: false }
    ]
})
export class GeboAIBedrockChatModelAdminComponent extends BaseEntityEditingComponentAutoDeleteCheck<GBedrockChatModelConfig> {
    protected override entityName: string = "GBedrockChatModelConfig";

    /** AWS credentials are stored as an access-key/secret-key username/password pair. */
    allowedTypes: SecretInfo.SecretTypeEnum[] = [SecretInfo.SecretTypeEnum.USERNAMEPASSWORD];

    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        modelTypeCode: new FormControl(),
        region: new FormControl(),
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
        defaultModelPrompt: new FormControl(),
        forUses: new FormControl(),
        thinking: new FormControl(),
        maxGeneratedTokens: new FormControl()
    });

    private oldValue: any = {};

    modelChoicesData: GBaseChatModelChoice[] = [];

    identitiesObservable = this.secretControllerService.getSecretsByContextCode("aws-bedrock");

    public newSecretAction = newSecretActionRequest("aws-bedrock", this.entityName, this.entity);

    public functionsList: GLookupEntry[] = [];

    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private bedrockChatModelConfigService: BedrockChatModelsConfigurationControllerService,
        private functionsLookupControllerService: FunctionsLookupControllerService,
        private secretControllerService: SecretsControllerService,
        confirmService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmService, geboUIActionRoutingService, outputForwardingService);
        this.formGroup.valueChanges.subscribe(newValue => {
            if (!newValue.region && !newValue.apiSecretCode) {
                this.modelChoicesData = [];
            } else if (newValue.region !== this.oldValue.region || newValue.apiSecretCode !== this.oldValue.apiSecretCode) {
                this.loadModels(newValue);
            }
            this.oldValue = newValue;
        });
        this.manageOperationStatus = true;
    }

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

    private loadModels(newValue: GBedrockChatModelConfig) {
        this.loadingRelatedBackend = true;
        this.bedrockChatModelConfigService.getBedrockChatModels(newValue).subscribe({
            next: (r) => {
                this.updateLastOperationStatus(r as any);
                this.modelChoicesData = r.result ? r.result : [];
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    protected override onLoadedPersistentData(actualValue: GBedrockChatModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("chat-aws-bedrock");
        this.loadModels(actualValue);
    }

    protected override onNewData(actualValue: GBedrockChatModelConfig): void {
        this.formGroup.controls["modelTypeCode"].setValue("chat-aws-bedrock");
    }

    override findByCode(code: string): Observable<GBedrockChatModelConfig | null> {
        return this.bedrockChatModelConfigService.findBedrockChatModelConfigByCode(code);
    }

    override save(value: GBedrockChatModelConfig): Observable<GBedrockChatModelConfig> {
        return this.bedrockChatModelConfigService.updateBedrockChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    override insert(value: GBedrockChatModelConfig): Observable<GBedrockChatModelConfig> {
        return this.bedrockChatModelConfigService.insertBedrockChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }

    override delete(value: GBedrockChatModelConfig): Observable<boolean> {
        return this.bedrockChatModelConfigService.deleteBedrockChatModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result as any);
            return result.result === true;
        }));
    }
}
