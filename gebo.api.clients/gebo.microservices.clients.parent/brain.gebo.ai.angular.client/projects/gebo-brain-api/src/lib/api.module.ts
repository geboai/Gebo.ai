import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { Configuration } from './configuration';
import { HttpClient } from '@angular/common/http';


import { AnthropicChatModelsConfigurationControllerService } from './api/anthropicChatModelsConfigurationController.service';
import { BuildSystemsControllerService } from './api/buildSystemsController.service';
import { ChatModelsControllerService } from './api/chatModelsController.service';
import { ChatModelsLookupControllerService } from './api/chatModelsLookupController.service';
import { CompanySystemsControllerService } from './api/companySystemsController.service';
import { ContentMetaInfosControllerService } from './api/contentMetaInfosController.service';
import { ContentsResetControllerService } from './api/contentsResetController.service';
import { DocumentContentStreamerControllerService } from './api/documentContentStreamerController.service';
import { EmbeddingModelsControllersService } from './api/embeddingModelsControllers.service';
import { FunctionsLookupControllerService } from './api/functionsLookupController.service';
import { GeboAdminChatProfilesConfigurationControllerService } from './api/geboAdminChatProfilesConfigurationController.service';
import { GeboAdminPromptUseInfoControllerService } from './api/geboAdminPromptUseInfoController.service';
import { GeboAdminPromptsControllerService } from './api/geboAdminPromptsController.service';
import { GeboAdminRagAutotuneControllerService } from './api/geboAdminRagAutotuneController.service';
import { GeboAdvancedSetupStatusControllerService } from './api/geboAdvancedSetupStatusController.service';
import { GeboAgentAdminControllerService } from './api/geboAgentAdminController.service';
import { GeboAgentsNetworkAdminControllerService } from './api/geboAgentsNetworkAdminController.service';
import { GeboChatControllerService } from './api/geboChatController.service';
import { GeboChatPipelinesControllerService } from './api/geboChatPipelinesController.service';
import { GeboChatProfileLookupControllerService } from './api/geboChatProfileLookupController.service';
import { GeboDeepSearchAdminControllerService } from './api/geboDeepSearchAdminController.service';
import { GeboDeepSearchControllerService } from './api/geboDeepSearchController.service';
import { GeboFastChatProfileStatusControllerService } from './api/geboFastChatProfileStatusController.service';
import { GeboFastKnowledgeBaseSetupControllerService } from './api/geboFastKnowledgeBaseSetupController.service';
import { GeboFastLlmsSetupControllerService } from './api/geboFastLlmsSetupController.service';
import { GeboFastVectorStoreSetupControllerService } from './api/geboFastVectorStoreSetupController.service';
import { GeboLlmGeneratedResourceControllerService } from './api/geboLlmGeneratedResourceController.service';
import { GeboMcpServerAdminControllerService } from './api/geboMcpServerAdminController.service';
import { GeboMcpServerUserControllerService } from './api/geboMcpServerUserController.service';
import { GeboRagChatControllerService } from './api/geboRagChatController.service';
import { GeboTextToSpeechControllerService } from './api/geboTextToSpeechController.service';
import { GeboTranscriptControllerService } from './api/geboTranscriptController.service';
import { GeboUserChatUploadsControllerService } from './api/geboUserChatUploadsController.service';
import { GeboUserChatsControllerService } from './api/geboUserChatsController.service';
import { GeboUserKnowledgeBaseSemanticSearchControllerService } from './api/geboUserKnowledgeBaseSemanticSearchController.service';
import { GeboVectorStoreConfigurationControllerService } from './api/geboVectorStoreConfigurationController.service';
import { GenericOpenAiRankerModelsConfigurationControllerService } from './api/genericOpenAiRankerModelsConfigurationController.service';
import { GenericOpenAiapiChatModelsConfigurationControllerService } from './api/genericOpenAiapiChatModelsConfigurationController.service';
import { GenericOpenAiapiEmbeddingModelsConfigurationControllerService } from './api/genericOpenAiapiEmbeddingModelsConfigurationController.service';
import { GenericOpenAiapiImageModelsConfigurationControllerService } from './api/genericOpenAiapiImageModelsConfigurationController.service';
import { GenericOpenAiapiTextToSpeechModelsConfigurationControllerService } from './api/genericOpenAiapiTextToSpeechModelsConfigurationController.service';
import { GenericOpenAiapiTranscriptModelsConfigurationControllerService } from './api/genericOpenAiapiTranscriptModelsConfigurationController.service';
import { GenericalPublisherControllerService } from './api/genericalPublisherController.service';
import { GoogleSearchConfigurationControllerService } from './api/googleSearchConfigurationController.service';
import { GoogleSearchControllerService } from './api/googleSearchController.service';
import { ImageModelsControllerService } from './api/imageModelsController.service';
import { IngestionFileTypesLibraryControllerService } from './api/ingestionFileTypesLibraryController.service';
import { InternalMessagingTopologyControllerService } from './api/internalMessagingTopologyController.service';
import { JobLauncherControllerService } from './api/jobLauncherController.service';
import { KnowledgeBaseControllerService } from './api/knowledgeBaseController.service';
import { LogViewControllerService } from './api/logViewController.service';
import { McpClientConfigControllerService } from './api/mcpClientConfigController.service';
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
import { TextToSpeechModelsControllerService } from './api/textToSpeechModelsController.service';
import { TranscriptModelsControllerService } from './api/transcriptModelsController.service';
import { UserKnowledgeBaseBrowsingControllerService } from './api/userKnowledgeBaseBrowsingController.service';

@NgModule({
  imports:      [],
  declarations: [],
  exports:      [],
  providers: [
    AnthropicChatModelsConfigurationControllerService,
    BuildSystemsControllerService,
    ChatModelsControllerService,
    ChatModelsLookupControllerService,
    CompanySystemsControllerService,
    ContentMetaInfosControllerService,
    ContentsResetControllerService,
    DocumentContentStreamerControllerService,
    EmbeddingModelsControllersService,
    FunctionsLookupControllerService,
    GeboAdminChatProfilesConfigurationControllerService,
    GeboAdminPromptUseInfoControllerService,
    GeboAdminPromptsControllerService,
    GeboAdminRagAutotuneControllerService,
    GeboAdvancedSetupStatusControllerService,
    GeboAgentAdminControllerService,
    GeboAgentsNetworkAdminControllerService,
    GeboChatControllerService,
    GeboChatPipelinesControllerService,
    GeboChatProfileLookupControllerService,
    GeboDeepSearchAdminControllerService,
    GeboDeepSearchControllerService,
    GeboFastChatProfileStatusControllerService,
    GeboFastKnowledgeBaseSetupControllerService,
    GeboFastLlmsSetupControllerService,
    GeboFastVectorStoreSetupControllerService,
    GeboLlmGeneratedResourceControllerService,
    GeboMcpServerAdminControllerService,
    GeboMcpServerUserControllerService,
    GeboRagChatControllerService,
    GeboTextToSpeechControllerService,
    GeboTranscriptControllerService,
    GeboUserChatUploadsControllerService,
    GeboUserChatsControllerService,
    GeboUserKnowledgeBaseSemanticSearchControllerService,
    GeboVectorStoreConfigurationControllerService,
    GenericOpenAiRankerModelsConfigurationControllerService,
    GenericOpenAiapiChatModelsConfigurationControllerService,
    GenericOpenAiapiEmbeddingModelsConfigurationControllerService,
    GenericOpenAiapiImageModelsConfigurationControllerService,
    GenericOpenAiapiTextToSpeechModelsConfigurationControllerService,
    GenericOpenAiapiTranscriptModelsConfigurationControllerService,
    GenericalPublisherControllerService,
    GoogleSearchConfigurationControllerService,
    GoogleSearchControllerService,
    ImageModelsControllerService,
    IngestionFileTypesLibraryControllerService,
    InternalMessagingTopologyControllerService,
    JobLauncherControllerService,
    KnowledgeBaseControllerService,
    LogViewControllerService,
    McpClientConfigControllerService,
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
    TextToSpeechModelsControllerService,
    TranscriptModelsControllerService,
    UserKnowledgeBaseBrowsingControllerService ]
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
