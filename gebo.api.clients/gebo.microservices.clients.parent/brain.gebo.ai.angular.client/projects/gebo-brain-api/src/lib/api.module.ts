import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { Configuration } from './configuration';
import { HttpClient } from '@angular/common/http';


import { ChatModelsControllerService } from './api/chatModelsController.service';
import { ChatModelsLookupControllerService } from './api/chatModelsLookupController.service';
import { ContentsResetControllerService } from './api/contentsResetController.service';
import { DocumentContentStreamerControllerService } from './api/documentContentStreamerController.service';
import { EmbeddingModelsControllersService } from './api/embeddingModelsControllers.service';
import { FunctionsLookupControllerService } from './api/functionsLookupController.service';
import { GeboAdminChatProfilesConfigurationControllerService } from './api/geboAdminChatProfilesConfigurationController.service';
import { GeboAdminPromptUseInfoControllerService } from './api/geboAdminPromptUseInfoController.service';
import { GeboAdminPromptsControllerService } from './api/geboAdminPromptsController.service';
import { GeboAdminRagAutotuneControllerService } from './api/geboAdminRagAutotuneController.service';
import { GeboAgentAdminControllerService } from './api/geboAgentAdminController.service';
import { GeboAgentsNetworkAdminControllerService } from './api/geboAgentsNetworkAdminController.service';
import { GeboChatControllerService } from './api/geboChatController.service';
import { GeboChatPipelinesControllerService } from './api/geboChatPipelinesController.service';
import { GeboChatProfileLookupControllerService } from './api/geboChatProfileLookupController.service';
import { GeboDeepSearchAdminControllerService } from './api/geboDeepSearchAdminController.service';
import { GeboDeepSearchControllerService } from './api/geboDeepSearchController.service';
import { GeboFastLlmsSetupControllerService } from './api/geboFastLlmsSetupController.service';
import { GeboLlmGeneratedResourceControllerService } from './api/geboLlmGeneratedResourceController.service';
import { GeboRagChatControllerService } from './api/geboRagChatController.service';
import { GeboTextToSpeechControllerService } from './api/geboTextToSpeechController.service';
import { GeboTranscriptControllerService } from './api/geboTranscriptController.service';
import { GeboUserChatUploadsControllerService } from './api/geboUserChatUploadsController.service';
import { GeboUserChatsControllerService } from './api/geboUserChatsController.service';
import { GeboUserKnowledgeBaseSemanticSearchControllerService } from './api/geboUserKnowledgeBaseSemanticSearchController.service';
import { GeboVectorStoreConfigurationControllerService } from './api/geboVectorStoreConfigurationController.service';
import { GenericalPublisherControllerService } from './api/genericalPublisherController.service';
import { ImageModelsControllerService } from './api/imageModelsController.service';
import { IngestionFileTypesLibraryControllerService } from './api/ingestionFileTypesLibraryController.service';
import { JobLauncherControllerService } from './api/jobLauncherController.service';
import { JobStatusControllerService } from './api/jobStatusController.service';
import { LlmsUsageAdminLevelControllerService } from './api/llmsUsageAdminLevelController.service';
import { LlmsUsageUserLevelControllerService } from './api/llmsUsageUserLevelController.service';
import { PromptTemplatesControllerService } from './api/promptTemplatesController.service';
import { RankerModelsControllerService } from './api/rankerModelsController.service';
import { TextToSpeechModelsControllerService } from './api/textToSpeechModelsController.service';
import { TranscriptModelsControllerService } from './api/transcriptModelsController.service';
import { WorkflowStatsAdminLevelControllerService } from './api/workflowStatsAdminLevelController.service';

@NgModule({
  imports:      [],
  declarations: [],
  exports:      [],
  providers: [
    ChatModelsControllerService,
    ChatModelsLookupControllerService,
    ContentsResetControllerService,
    DocumentContentStreamerControllerService,
    EmbeddingModelsControllersService,
    FunctionsLookupControllerService,
    GeboAdminChatProfilesConfigurationControllerService,
    GeboAdminPromptUseInfoControllerService,
    GeboAdminPromptsControllerService,
    GeboAdminRagAutotuneControllerService,
    GeboAgentAdminControllerService,
    GeboAgentsNetworkAdminControllerService,
    GeboChatControllerService,
    GeboChatPipelinesControllerService,
    GeboChatProfileLookupControllerService,
    GeboDeepSearchAdminControllerService,
    GeboDeepSearchControllerService,
    GeboFastLlmsSetupControllerService,
    GeboLlmGeneratedResourceControllerService,
    GeboRagChatControllerService,
    GeboTextToSpeechControllerService,
    GeboTranscriptControllerService,
    GeboUserChatUploadsControllerService,
    GeboUserChatsControllerService,
    GeboUserKnowledgeBaseSemanticSearchControllerService,
    GeboVectorStoreConfigurationControllerService,
    GenericalPublisherControllerService,
    ImageModelsControllerService,
    IngestionFileTypesLibraryControllerService,
    JobLauncherControllerService,
    JobStatusControllerService,
    LlmsUsageAdminLevelControllerService,
    LlmsUsageUserLevelControllerService,
    PromptTemplatesControllerService,
    RankerModelsControllerService,
    TextToSpeechModelsControllerService,
    TranscriptModelsControllerService,
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
