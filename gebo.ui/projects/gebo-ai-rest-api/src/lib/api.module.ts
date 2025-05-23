/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { Configuration } from './configuration';
import { HttpClient } from '@angular/common/http';


import { AnthropicChatModelsConfigurationControllerService } from './api/anthropicChatModelsConfigurationController.service';
import { AuthControllerService } from './api/authController.service';
import { BuildSystemsControllerService } from './api/buildSystemsController.service';
import { ChatModelsControllerService } from './api/chatModelsController.service';
import { ChatModelsLookupControllerService } from './api/chatModelsLookupController.service';
import { CompanySystemsControllerService } from './api/companySystemsController.service';
import { ConfluenceBrowsingControllerService } from './api/confluenceBrowsingController.service';
import { ConfluenceSystemsControllerService } from './api/confluenceSystemsController.service';
import { ContentMetaInfosControllerService } from './api/contentMetaInfosController.service';
import { ContentsResetControllerService } from './api/contentsResetController.service';
import { DeepseekChatModelsConfigurationControllerService } from './api/deepseekChatModelsConfigurationController.service';
import { EmbeddingModelsControllersService } from './api/embeddingModelsControllers.service';
import { FileSystemSharesSettingControllerService } from './api/fileSystemSharesSettingController.service';
import { FileSystemsBrowsingControllerService } from './api/fileSystemsBrowsingController.service';
import { FileSystemsControllerService } from './api/fileSystemsController.service';
import { FileUploadControllerService } from './api/fileUploadController.service';
import { FileUploadsControllerService } from './api/fileUploadsController.service';
import { FunctionsLookupControllerService } from './api/functionsLookupController.service';
import { GeboAdminChatProfilesConfigurationControllerService } from './api/geboAdminChatProfilesConfigurationController.service';
import { GeboAdminPromptsControllerService } from './api/geboAdminPromptsController.service';
import { GeboAdvancedSetupStatusControllerService } from './api/geboAdvancedSetupStatusController.service';
import { GeboAngularFormGroupMetaInfoControllerService } from './api/geboAngularFormGroupMetaInfoController.service';
import { GeboChatControllerService } from './api/geboChatController.service';
import { GeboChatProfileLookupControllerService } from './api/geboChatProfileLookupController.service';
import { GeboCoreAnalisysControllerService } from './api/geboCoreAnalisysController.service';
import { GeboFastChatProfileStatusControllerService } from './api/geboFastChatProfileStatusController.service';
import { GeboFastInstallationSetupControllerService } from './api/geboFastInstallationSetupController.service';
import { GeboFastKnowledgeBaseSetupControllerService } from './api/geboFastKnowledgeBaseSetupController.service';
import { GeboFastLlmsSetupControllerService } from './api/geboFastLlmsSetupController.service';
import { GeboFastVectorStoreSetupControllerService } from './api/geboFastVectorStoreSetupController.service';
import { GeboFastWorkFolderSetupControllerService } from './api/geboFastWorkFolderSetupController.service';
import { GeboModulesConfigControllerService } from './api/geboModulesConfigController.service';
import { GeboRagChatControllerService } from './api/geboRagChatController.service';
import { GeboUserChatsControllerService } from './api/geboUserChatsController.service';
import { GeboUserKnowledgeBaseSemanticSearchControllerService } from './api/geboUserKnowledgeBaseSemanticSearchController.service';
import { GeboVectorStoreConfigurationControllerService } from './api/geboVectorStoreConfigurationController.service';
import { GenericOpenAiapiChatModelsConfigurationControllerService } from './api/genericOpenAiapiChatModelsConfigurationController.service';
import { GenericOpenAiapiEmbeddingModelsConfigurationControllerService } from './api/genericOpenAiapiEmbeddingModelsConfigurationController.service';
import { GitSystemsControllerService } from './api/gitSystemsController.service';
import { GoogleDriveBrowsingControllerService } from './api/googleDriveBrowsingController.service';
import { GoogleDriveSystemsControllerService } from './api/googleDriveSystemsController.service';
import { GoogleSearchConfigurationControllerService } from './api/googleSearchConfigurationController.service';
import { GoogleSearchControllerService } from './api/googleSearchController.service';
import { GoogleVertexChatModelsConfigurationControllerService } from './api/googleVertexChatModelsConfigurationController.service';
import { GoogleVertexEmbeddingModelsConfigurationControllerService } from './api/googleVertexEmbeddingModelsConfigurationController.service';
import { GoogleWorkspaceAccessHandshakeControllerService } from './api/googleWorkspaceAccessHandshakeController.service';
import { HuggingfaceChatModelsConfigurationControllerService } from './api/huggingfaceChatModelsConfigurationController.service';
import { IngestionFileTypesLibraryControllerService } from './api/ingestionFileTypesLibraryController.service';
import { JiraBrowsingControllerService } from './api/jiraBrowsingController.service';
import { JiraSystemsControllerService } from './api/jiraSystemsController.service';
import { JobLauncherControllerService } from './api/jobLauncherController.service';
import { KnowledgeBaseControllerService } from './api/knowledgeBaseController.service';
import { LanguageResourcesControllerService } from './api/languageResourcesController.service';
import { LogViewControllerService } from './api/logViewController.service';
import { MistralAiChatModelsConfigurationControllerService } from './api/mistralAiChatModelsConfigurationController.service';
import { MistralAiEmbeddingModelsConfigurationControllerService } from './api/mistralAiEmbeddingModelsConfigurationController.service';
import { OllamaChatModelsConfigurationControllerService } from './api/ollamaChatModelsConfigurationController.service';
import { OllamaEmbeddingModelsConfigurationControllerService } from './api/ollamaEmbeddingModelsConfigurationController.service';
import { OnnxTransformersEmbeddingModelsConfigurationControllerService } from './api/onnxTransformersEmbeddingModelsConfigurationController.service';
import { OpenAiChatModelsConfigurationControllerService } from './api/openAiChatModelsConfigurationController.service';
import { OpenAiEmbeddingModelsConfigurationControllerService } from './api/openAiEmbeddingModelsConfigurationController.service';
import { ProjectsControllerService } from './api/projectsController.service';
import { PromptTemplateWizardControllerService } from './api/promptTemplateWizardController.service';
import { PromptTemplatesControllerService } from './api/promptTemplatesController.service';
import { ReindexingFrequencyOptionsControllerService } from './api/reindexingFrequencyOptionsController.service';
import { SecretsControllerService } from './api/secretsController.service';
import { SharepointBrowsingControllerService } from './api/sharepointBrowsingController.service';
import { SharepointSystemsControllerService } from './api/sharepointSystemsController.service';
import { UserControllerService } from './api/userController.service';
import { UserKnowledgeBaseBrowsingControllerService } from './api/userKnowledgeBaseBrowsingController.service';
import { UsersAdminControllerService } from './api/usersAdminController.service';
import { UserspaceControllerService } from './api/userspaceController.service';
import { UserspaceUploadControllerService } from './api/userspaceUploadController.service';

@NgModule({
  imports:      [],
  declarations: [],
  exports:      [],
  providers: [
    AnthropicChatModelsConfigurationControllerService,
    AuthControllerService,
    BuildSystemsControllerService,
    ChatModelsControllerService,
    ChatModelsLookupControllerService,
    CompanySystemsControllerService,
    ConfluenceBrowsingControllerService,
    ConfluenceSystemsControllerService,
    ContentMetaInfosControllerService,
    ContentsResetControllerService,
    DeepseekChatModelsConfigurationControllerService,
    EmbeddingModelsControllersService,
    FileSystemSharesSettingControllerService,
    FileSystemsBrowsingControllerService,
    FileSystemsControllerService,
    FileUploadControllerService,
    FileUploadsControllerService,
    FunctionsLookupControllerService,
    GeboAdminChatProfilesConfigurationControllerService,
    GeboAdminPromptsControllerService,
    GeboAdvancedSetupStatusControllerService,
    GeboAngularFormGroupMetaInfoControllerService,
    GeboChatControllerService,
    GeboChatProfileLookupControllerService,
    GeboCoreAnalisysControllerService,
    GeboFastChatProfileStatusControllerService,
    GeboFastInstallationSetupControllerService,
    GeboFastKnowledgeBaseSetupControllerService,
    GeboFastLlmsSetupControllerService,
    GeboFastVectorStoreSetupControllerService,
    GeboFastWorkFolderSetupControllerService,
    GeboModulesConfigControllerService,
    GeboRagChatControllerService,
    GeboUserChatsControllerService,
    GeboUserKnowledgeBaseSemanticSearchControllerService,
    GeboVectorStoreConfigurationControllerService,
    GenericOpenAiapiChatModelsConfigurationControllerService,
    GenericOpenAiapiEmbeddingModelsConfigurationControllerService,
    GitSystemsControllerService,
    GoogleDriveBrowsingControllerService,
    GoogleDriveSystemsControllerService,
    GoogleSearchConfigurationControllerService,
    GoogleSearchControllerService,
    GoogleVertexChatModelsConfigurationControllerService,
    GoogleVertexEmbeddingModelsConfigurationControllerService,
    GoogleWorkspaceAccessHandshakeControllerService,
    HuggingfaceChatModelsConfigurationControllerService,
    IngestionFileTypesLibraryControllerService,
    JiraBrowsingControllerService,
    JiraSystemsControllerService,
    JobLauncherControllerService,
    KnowledgeBaseControllerService,
    LanguageResourcesControllerService,
    LogViewControllerService,
    MistralAiChatModelsConfigurationControllerService,
    MistralAiEmbeddingModelsConfigurationControllerService,
    OllamaChatModelsConfigurationControllerService,
    OllamaEmbeddingModelsConfigurationControllerService,
    OnnxTransformersEmbeddingModelsConfigurationControllerService,
    OpenAiChatModelsConfigurationControllerService,
    OpenAiEmbeddingModelsConfigurationControllerService,
    ProjectsControllerService,
    PromptTemplateWizardControllerService,
    PromptTemplatesControllerService,
    ReindexingFrequencyOptionsControllerService,
    SecretsControllerService,
    SharepointBrowsingControllerService,
    SharepointSystemsControllerService,
    UserControllerService,
    UserKnowledgeBaseBrowsingControllerService,
    UsersAdminControllerService,
    UserspaceControllerService,
    UserspaceUploadControllerService ]
})
export class ApiModule {
    public static forRoot(configurationFactory: () => Configuration): ModuleWithProviders<ApiModule> {
        return {
            ngModule: ApiModule,
            providers: [ { provide: Configuration, useFactory: configurationFactory } ]
        };
    }

    constructor( @Optional() @SkipSelf() parentModule: ApiModule,
                 @Optional() http: HttpClient) {
        if (parentModule) {
            throw new Error('ApiModule is already loaded. Import in your base AppModule only.');
        }
        if (!http) {
            throw new Error('You need to import the HttpClientModule in your AppModule! \n' +
            'See also https://github.com/angular/angular/issues/20575');
        }
    }
}
