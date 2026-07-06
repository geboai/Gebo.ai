import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { Configuration } from './configuration';
import { HttpClient } from '@angular/common/http';


import { AnthropicChatModelsConfigurationControllerService } from './api/anthropicChatModelsConfigurationController.service';
import { AuthControllerService } from './api/authController.service';
import { AuthProvidersControllerService } from './api/authProvidersController.service';
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
import { GeboAdminPromptUseInfoControllerService } from './api/geboAdminPromptUseInfoController.service';
import { GeboAdminPromptsControllerService } from './api/geboAdminPromptsController.service';
import { GeboAdminRagAutotuneControllerService } from './api/geboAdminRagAutotuneController.service';
import { GeboAdvancedSetupStatusControllerService } from './api/geboAdvancedSetupStatusController.service';
import { GeboAgentAdminControllerService } from './api/geboAgentAdminController.service';
import { GeboAgentsNetworkAdminControllerService } from './api/geboAgentsNetworkAdminController.service';
import { GeboAngularFormGroupMetaInfoControllerService } from './api/geboAngularFormGroupMetaInfoController.service';
import { GeboChatControllerService } from './api/geboChatController.service';
import { GeboChatPipelinesControllerService } from './api/geboChatPipelinesController.service';
import { GeboChatProfileLookupControllerService } from './api/geboChatProfileLookupController.service';
import { GeboCoreAnalisysControllerService } from './api/geboCoreAnalisysController.service';
import { GeboDeepSearchAdminControllerService } from './api/geboDeepSearchAdminController.service';
import { GeboDeepSearchControllerService } from './api/geboDeepSearchController.service';
import { GeboFastChatProfileStatusControllerService } from './api/geboFastChatProfileStatusController.service';
import { GeboFastInstallationSetupControllerService } from './api/geboFastInstallationSetupController.service';
import { GeboFastKnowledgeBaseSetupControllerService } from './api/geboFastKnowledgeBaseSetupController.service';
import { GeboFastLlmsSetupControllerService } from './api/geboFastLlmsSetupController.service';
import { GeboFastVectorStoreSetupControllerService } from './api/geboFastVectorStoreSetupController.service';
import { GeboFastWorkFolderSetupControllerService } from './api/geboFastWorkFolderSetupController.service';
import { GeboLlmGeneratedResourceControllerService } from './api/geboLlmGeneratedResourceController.service';
import { GeboMcpServerAdminControllerService } from './api/geboMcpServerAdminController.service';
import { GeboMcpServerUserControllerService } from './api/geboMcpServerUserController.service';
import { GeboModulesConfigControllerService } from './api/geboModulesConfigController.service';
import { GeboNeo4jModuleSetupControllerService } from './api/geboNeo4jModuleSetupController.service';
import { GeboRagChatControllerService } from './api/geboRagChatController.service';
import { GeboTextToSpeechControllerService } from './api/geboTextToSpeechController.service';
import { GeboTranscriptControllerService } from './api/geboTranscriptController.service';
import { GeboUserChatUploadsControllerService } from './api/geboUserChatUploadsController.service';
import { GeboUserChatsControllerService } from './api/geboUserChatsController.service';
import { GeboUserKnowledgeBaseSemanticSearchControllerService } from './api/geboUserKnowledgeBaseSemanticSearchController.service';
import { GeboVectorStoreConfigurationControllerService } from './api/geboVectorStoreConfigurationController.service';
import { GeneratedAdminApiKeyControllerService } from './api/generatedAdminApiKeyController.service';
import { GeneratedUserApiKeyControllerService } from './api/generatedUserApiKeyController.service';
import { GenericOpenAiRankerModelsConfigurationControllerService } from './api/genericOpenAiRankerModelsConfigurationController.service';
import { GenericOpenAiapiChatModelsConfigurationControllerService } from './api/genericOpenAiapiChatModelsConfigurationController.service';
import { GenericOpenAiapiEmbeddingModelsConfigurationControllerService } from './api/genericOpenAiapiEmbeddingModelsConfigurationController.service';
import { GenericOpenAiapiImageModelsConfigurationControllerService } from './api/genericOpenAiapiImageModelsConfigurationController.service';
import { GenericOpenAiapiTextToSpeechModelsConfigurationControllerService } from './api/genericOpenAiapiTextToSpeechModelsConfigurationController.service';
import { GenericOpenAiapiTranscriptModelsConfigurationControllerService } from './api/genericOpenAiapiTranscriptModelsConfigurationController.service';
import { GitSystemsControllerService } from './api/gitSystemsController.service';
import { GoogleDriveBrowsingControllerService } from './api/googleDriveBrowsingController.service';
import { GoogleDriveSystemsControllerService } from './api/googleDriveSystemsController.service';
import { GoogleSearchConfigurationControllerService } from './api/googleSearchConfigurationController.service';
import { GoogleSearchControllerService } from './api/googleSearchController.service';
import { GoogleVertexChatModelsConfigurationControllerService } from './api/googleVertexChatModelsConfigurationController.service';
import { GoogleVertexEmbeddingModelsConfigurationControllerService } from './api/googleVertexEmbeddingModelsConfigurationController.service';
import { GoogleWorkspaceAccessHandshakeControllerService } from './api/googleWorkspaceAccessHandshakeController.service';
import { GraphRagConfigurationControllerService } from './api/graphRagConfigurationController.service';
import { ImageModelsControllerService } from './api/imageModelsController.service';
import { IngestionFileTypesLibraryControllerService } from './api/ingestionFileTypesLibraryController.service';
import { IntegrationInputControllerService } from './api/integrationInputController.service';
import { IntegrationSystemsControllerService } from './api/integrationSystemsController.service';
import { JiraBrowsingControllerService } from './api/jiraBrowsingController.service';
import { JiraSystemsControllerService } from './api/jiraSystemsController.service';
import { JobLauncherControllerService } from './api/jobLauncherController.service';
import { KnowledgeBaseControllerService } from './api/knowledgeBaseController.service';
import { LanguageResourcesControllerService } from './api/languageResourcesController.service';
import { LlmsUsageAdminLevelControllerService } from './api/llmsUsageAdminLevelController.service';
import { LlmsUsageUserLevelControllerService } from './api/llmsUsageUserLevelController.service';
import { LogViewControllerService } from './api/logViewController.service';
import { McpClientBrowsingControllerService } from './api/mcpClientBrowsingController.service';
import { McpClientConfigControllerService } from './api/mcpClientConfigController.service';
import { McpClientSystemsControllerService } from './api/mcpClientSystemsController.service';
import { MistralAiChatModelsConfigurationControllerService } from './api/mistralAiChatModelsConfigurationController.service';
import { MistralAiEmbeddingModelsConfigurationControllerService } from './api/mistralAiEmbeddingModelsConfigurationController.service';
import { OAuth2AdminControllerService } from './api/oAuth2AdminController.service';
import { Oauth2ModuleStatusControllerService } from './api/oauth2ModuleStatusController.service';
import { OllamaChatModelsConfigurationControllerService } from './api/ollamaChatModelsConfigurationController.service';
import { OllamaEmbeddingModelsConfigurationControllerService } from './api/ollamaEmbeddingModelsConfigurationController.service';
import { OnnxTransformersEmbeddingModelsConfigurationControllerService } from './api/onnxTransformersEmbeddingModelsConfigurationController.service';
import { OpenAiChatModelsConfigurationControllerService } from './api/openAiChatModelsConfigurationController.service';
import { OpenAiEmbeddingModelsConfigurationControllerService } from './api/openAiEmbeddingModelsConfigurationController.service';
import { OpenAiImageModelsConfigurationControllerService } from './api/openAiImageModelsConfigurationController.service';
import { OpenAiTextToSpeechModelsConfigurationControllerService } from './api/openAiTextToSpeechModelsConfigurationController.service';
import { OpenAiTranscriptModelsConfigurationControllerService } from './api/openAiTranscriptModelsConfigurationController.service';
import { ProjectsControllerService } from './api/projectsController.service';
import { PromptTemplatesControllerService } from './api/promptTemplatesController.service';
import { RankerModelsControllerService } from './api/rankerModelsController.service';
import { ReindexingFrequencyOptionsControllerService } from './api/reindexingFrequencyOptionsController.service';
import { SecretsControllerService } from './api/secretsController.service';
import { SharepointBrowsingControllerService } from './api/sharepointBrowsingController.service';
import { SharepointSystemsControllerService } from './api/sharepointSystemsController.service';
import { TextToSpeechModelsControllerService } from './api/textToSpeechModelsController.service';
import { TokenRenewControllerService } from './api/tokenRenewController.service';
import { TranscriptModelsControllerService } from './api/transcriptModelsController.service';
import { UiTextResourcesControllerService } from './api/uiTextResourcesController.service';
import { UserControllerService } from './api/userController.service';
import { UserKnowledgeBaseBrowsingControllerService } from './api/userKnowledgeBaseBrowsingController.service';
import { UserWorkflowsControllerService } from './api/userWorkflowsController.service';
import { UsersAdminControllerService } from './api/usersAdminController.service';
import { UserspaceControllerService } from './api/userspaceController.service';
import { UserspaceUploadControllerService } from './api/userspaceUploadController.service';
import { WorkflowStatsAdminLevelControllerService } from './api/workflowStatsAdminLevelController.service';

@NgModule({
  imports:      [],
  declarations: [],
  exports:      [],
  providers: [
    AnthropicChatModelsConfigurationControllerService,
    AuthControllerService,
    AuthProvidersControllerService,
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
    GeboAdminPromptUseInfoControllerService,
    GeboAdminPromptsControllerService,
    GeboAdminRagAutotuneControllerService,
    GeboAdvancedSetupStatusControllerService,
    GeboAgentAdminControllerService,
    GeboAgentsNetworkAdminControllerService,
    GeboAngularFormGroupMetaInfoControllerService,
    GeboChatControllerService,
    GeboChatPipelinesControllerService,
    GeboChatProfileLookupControllerService,
    GeboCoreAnalisysControllerService,
    GeboDeepSearchAdminControllerService,
    GeboDeepSearchControllerService,
    GeboFastChatProfileStatusControllerService,
    GeboFastInstallationSetupControllerService,
    GeboFastKnowledgeBaseSetupControllerService,
    GeboFastLlmsSetupControllerService,
    GeboFastVectorStoreSetupControllerService,
    GeboFastWorkFolderSetupControllerService,
    GeboLlmGeneratedResourceControllerService,
    GeboMcpServerAdminControllerService,
    GeboMcpServerUserControllerService,
    GeboModulesConfigControllerService,
    GeboNeo4jModuleSetupControllerService,
    GeboRagChatControllerService,
    GeboTextToSpeechControllerService,
    GeboTranscriptControllerService,
    GeboUserChatUploadsControllerService,
    GeboUserChatsControllerService,
    GeboUserKnowledgeBaseSemanticSearchControllerService,
    GeboVectorStoreConfigurationControllerService,
    GeneratedAdminApiKeyControllerService,
    GeneratedUserApiKeyControllerService,
    GenericOpenAiRankerModelsConfigurationControllerService,
    GenericOpenAiapiChatModelsConfigurationControllerService,
    GenericOpenAiapiEmbeddingModelsConfigurationControllerService,
    GenericOpenAiapiImageModelsConfigurationControllerService,
    GenericOpenAiapiTextToSpeechModelsConfigurationControllerService,
    GenericOpenAiapiTranscriptModelsConfigurationControllerService,
    GitSystemsControllerService,
    GoogleDriveBrowsingControllerService,
    GoogleDriveSystemsControllerService,
    GoogleSearchConfigurationControllerService,
    GoogleSearchControllerService,
    GoogleVertexChatModelsConfigurationControllerService,
    GoogleVertexEmbeddingModelsConfigurationControllerService,
    GoogleWorkspaceAccessHandshakeControllerService,
    GraphRagConfigurationControllerService,
    ImageModelsControllerService,
    IngestionFileTypesLibraryControllerService,
    IntegrationInputControllerService,
    IntegrationSystemsControllerService,
    JiraBrowsingControllerService,
    JiraSystemsControllerService,
    JobLauncherControllerService,
    KnowledgeBaseControllerService,
    LanguageResourcesControllerService,
    LlmsUsageAdminLevelControllerService,
    LlmsUsageUserLevelControllerService,
    LogViewControllerService,
    McpClientBrowsingControllerService,
    McpClientConfigControllerService,
    McpClientSystemsControllerService,
    MistralAiChatModelsConfigurationControllerService,
    MistralAiEmbeddingModelsConfigurationControllerService,
    OAuth2AdminControllerService,
    Oauth2ModuleStatusControllerService,
    OllamaChatModelsConfigurationControllerService,
    OllamaEmbeddingModelsConfigurationControllerService,
    OnnxTransformersEmbeddingModelsConfigurationControllerService,
    OpenAiChatModelsConfigurationControllerService,
    OpenAiEmbeddingModelsConfigurationControllerService,
    OpenAiImageModelsConfigurationControllerService,
    OpenAiTextToSpeechModelsConfigurationControllerService,
    OpenAiTranscriptModelsConfigurationControllerService,
    ProjectsControllerService,
    PromptTemplatesControllerService,
    RankerModelsControllerService,
    ReindexingFrequencyOptionsControllerService,
    SecretsControllerService,
    SharepointBrowsingControllerService,
    SharepointSystemsControllerService,
    TextToSpeechModelsControllerService,
    TokenRenewControllerService,
    TranscriptModelsControllerService,
    UiTextResourcesControllerService,
    UserControllerService,
    UserKnowledgeBaseBrowsingControllerService,
    UserWorkflowsControllerService,
    UsersAdminControllerService,
    UserspaceControllerService,
    UserspaceUploadControllerService,
    WorkflowStatsAdminLevelControllerService ]
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
