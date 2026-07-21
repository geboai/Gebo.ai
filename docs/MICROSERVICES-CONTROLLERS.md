# Microservices — Controllers by Service

Generated from each running microservice's live `/v3/api-docs` (springdoc), after building every image with `-P docker,swagger-on` and bringing up `dockers/gebo.microservices/docker-compose.yml`. One section per service; controllers are grouped by their springdoc `tag`, which springdoc derives 1:1 from the `@RestController` class name (kebab-case).

**Base path note:** this checkout serves backends under a `server.servlet.context-path` (e.g. `/brain`); every path below already includes it.

**Workflow step-enablement note (this revision):** tyr gains one controller — `WorkflowParticipantsEnablementController` (`GET /tyr/api/users/WorkflowParticipantsEnablementController/enabledSteps`). It is the authority for which optional standard-workflow steps (embedding / graphextraction / fulltext) are enabled in the installation, computed from the deployment topology. It comes from the new `gebo.microservices.workflow.steps.topology` module, which packages the `IWorkflowStepEnabledHandler` implementations (extracted out of the per-step perform-modules; the monolith gets the config-driven twin `gebo.architecture.workflow.steps`). The same module lands on the **chunker** — which runs the tokenization fan-out — but there it deliberately exposes **no** controller: the chunker gets a tyr-backed resolver that calls this endpoint once per run and caches the answer, so its per-message routing decision is a local lookup. That is why the endpoint appears only on tyr and the chunker's controller count is unchanged.


## Summary

| Service | Port | Context-path | Controllers | Endpoints |
|---|---|---|---|---|
| gateway.gebo.ai | 13000 | `—` | 0 | 0 |
| brain.gebo.ai | 13001 | `/brain` | 51 | 222 |
| vectorizator.gebo.ai | 13002 | `/vectorizator` | 2 | 4 |
| graphicator.gebo.ai | 13003 | `/graphicator` | 2 | 5 |
| chunker.gebo.ai | 13004 | `/chunker` | 4 | 16 |
| git.gebo.ai | 13005 | `/git` | 6 | 22 |
| filesystem.gebo.ai | 13006 | `/filesystem` | 8 | 30 |
| uploads.gebo.ai | 13007 | `/uploads` | 7 | 21 |
| userspace.gebo.ai | 13008 | `/userspace` | 7 | 29 |
| sharepoint.gebo.ai | 13009 | `/sharepoint` | 8 | 45 |
| confluence.gebo.ai | 13010 | `/confluence` | 8 | 45 |
| jira.gebo.ai | 13011 | `/jira` | 8 | 45 |
| aws-s3.gebo.ai | 13012 | `/aws-s3` | 7 | 26 |
| googledrive.gebo.ai | 13013 | `/googledrive` | 9 | 43 |
| mcpclient.gebo.ai | 13014 | `/mcpclient` | 8 | 28 |
| integration.gebo.ai | 13015 | `/integration` | 7 | 19 |
| fulltextor.gebo.ai | 13016 | `—` | 0 | 0 |
| eureka.gebo.ai | 13017 | `—` | 0 | 0 |
| heimdall.gebo.ai | 13018 | `/heimdall` | 14 | 55 |
| tyr.gebo.ai | 13019 | `/tyr` | 5 | 6 |


## gateway.gebo.ai — port 13000 (`gateway-gebo-ai`)

_Gateway routes to backends via `lb://`; it hosts no controllers of its own — its own `/v3/api-docs` is empty by design (it proxies/aggregates the backends' specs at `/api-docs/<service>` when `swagger-on` is active)._


## brain.gebo.ai — port 13001 (`brain-gebo-ai`) — context-path `/brain`

51 controller(s), 222 endpoint(s):


### `anthropic-chat-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/AnthropicChatModelsConfigurationController/deleteAnthropicChatModelConfig` | deleteAnthropicChatModelConfig |
| GET | `/brain/api/admin/AnthropicChatModelsConfigurationController/findAnthropicChatModelConfigByCode` | findAnthropicChatModelConfigByCode |
| POST | `/brain/api/admin/AnthropicChatModelsConfigurationController/getAnthropicModels` | getAnthropicChatModels |
| POST | `/brain/api/admin/AnthropicChatModelsConfigurationController/insertAnthropicChatModelConfig` | insertAnthropicChatModelConfig |
| POST | `/brain/api/admin/AnthropicChatModelsConfigurationController/updateAnthropicChatModelConfig` | updateAnthropicChatModelConfig |

### `chat-models-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/ChatModelsController/getChatModelTypes` | getChatModelTypes |
| GET | `/brain/api/admin/ChatModelsController/getRuntimeConfiguredChatModels` | getRuntimeConfiguredChatModels |

### `chat-models-lookup-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/users/ChatModelsLookupController/getChatModelTypesLookup` | getChatModelTypesLookup |
| GET | `/brain/api/users/ChatModelsLookupController/getDefaultChatModel` | getDefaultChatModel |
| GET | `/brain/api/users/ChatModelsLookupController/getRuntimeConfiguredChatModelsLookup` | getRuntimeConfiguredChatModelsLookup |

### `embedding-models-controllers`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/EmbeddingModelsControllers/getEmbeddingModelTypes` | getEmbeddingModelTypes |
| GET | `/brain/api/admin/EmbeddingModelsControllers/getRuntimeConfiguredEmbeddingModels` | getRuntimeConfiguredEmbeddingModels |

### `functions-lookup-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/FunctionsLookupController/getAllFunctions` | getAllFunctions |
| GET | `/brain/api/admin/FunctionsLookupController/getAllFunctionsTree` | getAllFunctionsTree |
| GET | `/brain/api/admin/FunctionsLookupController/getAllLocalFunctions` | getAllLocalFunctions |
| GET | `/brain/api/admin/FunctionsLookupController/getAllLocalFunctionsTree` | getAllLocalFunctionsTree |

### `gebo-admin-chat-profiles-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/GeboAdminChatProfilesConfigurationController/deleteChatProfile` | deleteChatProfile |
| GET | `/brain/api/admin/GeboAdminChatProfilesConfigurationController/findChatProfileConfigurationByCode` | findChatProfileConfigurationByCode |
| POST | `/brain/api/admin/GeboAdminChatProfilesConfigurationController/getAllChatProfileConfiguration` | getAllChatProfileConfiguration |
| POST | `/brain/api/admin/GeboAdminChatProfilesConfigurationController/getChatProfileConfigurationByQbe` | getChatProfileConfigurationByQbe |
| POST | `/brain/api/admin/GeboAdminChatProfilesConfigurationController/insertChatProfile` | insertChatProfile |
| POST | `/brain/api/admin/GeboAdminChatProfilesConfigurationController/updateChatProfile` | updateChatProfile |

### `gebo-admin-prompt-use-info-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/GeboAdminPromptUseController/findAll` | findAll |
| GET | `/brain/api/admin/GeboAdminPromptUseController/findByCode` | findByCode |
| GET | `/brain/api/admin/GeboAdminPromptUseController/findByModule` | findByModule |

### `gebo-admin-prompts-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/GeboAdminPromptsController/deletePromptConfig` | deletePromptConfig |
| GET | `/brain/api/admin/GeboAdminPromptsController/findPromptConfigByCode` | findPromptConfigByCode |
| GET | `/brain/api/admin/GeboAdminPromptsController/getPromptCategories` | getPromptCategories |
| POST | `/brain/api/admin/GeboAdminPromptsController/getPromptConfigByFilter` | getPromptConfigByFilter |
| POST | `/brain/api/admin/GeboAdminPromptsController/insertPromptConfig` | insertPromptConfig |
| POST | `/brain/api/admin/GeboAdminPromptsController/updatePromptConfig` | updatePromptConfig |

### `gebo-admin-rag-autotune-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/GeboAdminRagAutotuneController/getLatestComputedVectorStores` | getLatestComputedVectorStores |

### `gebo-advanced-setup-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/GeboAdvancedSetupStatusController/getFirstKnowledgeBaseSetupStatus` | getFirstKnowledgeBaseSetupStatus |
| GET | `/brain/api/admin/GeboAdvancedSetupStatusController/getMinimalContentsSetupStatus` | getMinimalContentsSetupStatus |

### `gebo-agent-admin-controller`
| Method | Path | Operation |
|---|---|---|
| DELETE | `/brain/api/admin/GeboAgentAdminController/deleteAgent` | deleteAgent |
| GET | `/brain/api/admin/GeboAgentAdminController/getAgentByCode` | getAgentByCode |
| GET | `/brain/api/admin/GeboAgentAdminController/getAgents` | getAgents |
| GET | `/brain/api/admin/GeboAgentAdminController/getAgentsChoices` | getAgentsChoices |
| GET | `/brain/api/admin/GeboAgentAdminController/getPromptTemplateByAgentId` | getPromptTemplatesByAgentId |
| POST | `/brain/api/admin/GeboAgentAdminController/insertAgent` | insertAgent |
| POST | `/brain/api/admin/GeboAgentAdminController/updateAgent` | updateAgent |

### `gebo-agents-network-admin-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/GeboAgentsNetworkAdminController/deleteAgentsNetwork` | deleteAgentsNetwork |
| GET | `/brain/api/admin/GeboAgentsNetworkAdminController/getAgentConfigs` | getAgentConfigs |
| GET | `/brain/api/admin/GeboAgentsNetworkAdminController/getAgentConfigsByServiceId` | getAgentConfigsByServiceId |
| GET | `/brain/api/admin/GeboAgentsNetworkAdminController/getAgentServices` | getAgentServices |
| GET | `/brain/api/admin/GeboAgentsNetworkAdminController/getAgentsNetwork` | getAgentsNetwork |
| GET | `/brain/api/admin/GeboAgentsNetworkAdminController/getAgentsNetworkByCode` | getAgentsNetworkByCode |
| GET | `/brain/api/admin/GeboAgentsNetworkAdminController/getCompatibleNextServices` | getCompatibleNextServices |
| GET | `/brain/api/admin/GeboAgentsNetworkAdminController/getCompatiblePreviousServices` | getCompatiblePreviousServices |
| GET | `/brain/api/admin/GeboAgentsNetworkAdminController/getNetworkAdapterServices` | getNetworkAdapterServices |
| POST | `/brain/api/admin/GeboAgentsNetworkAdminController/insertAgentsNetwork` | insertAgentsNetwork |
| POST | `/brain/api/admin/GeboAgentsNetworkAdminController/updateAgentsNetwork` | updateAgentsNetwork |
| POST | `/brain/api/admin/GeboAgentsNetworkAdminController/validateAgentsNetwork` | validateAgentsNetwork |

### `gebo-chat-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/users/GeboDirectModelChatController/chat` | chat |
| GET | `/brain/api/users/GeboDirectModelChatController/getChatModelMetaInfos` | getChatModelMetaInfos |
| GET | `/brain/api/users/GeboDirectModelChatController/getChatModelUserInfo` | getChatModelUserInfo |
| GET | `/brain/api/users/GeboDirectModelChatController/getProviderCapabilities` | getProviderCapabilities |
| GET | `/brain/api/users/GeboDirectModelChatController/getVisibleKnowledgeBases` | getVisibleKnowledgeBases |
| POST | `/brain/api/users/GeboDirectModelChatController/streamResponse` | streamResponse |

### `gebo-chat-pipelines-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/users/GeboChatPipelinesController/defaultPersonalPipelinesChatMenu` | getDefaultPersonalPipelinesChatMenu |
| POST | `/brain/api/users/GeboChatPipelinesController/executeChatPipeline` | executeChatPipeline |
| POST | `/brain/api/users/GeboChatPipelinesController/executeDefaultChatPipeline` | executeDefaultChatPipeline |
| GET | `/brain/api/users/GeboChatPipelinesController/personalPipelinesChatMenu` | getPersonalPipelinesChatMenu |
| GET | `/brain/api/users/GeboChatPipelinesController/stopChatPipeline` | stopChatPipeline |
| POST | `/brain/api/users/GeboChatPipelinesController/streamChatPipeline` | streamChatPipeline |
| POST | `/brain/api/users/GeboChatPipelinesController/streamDefaultChatPipeline` | streamDefaultChatPipeline |

### `gebo-chat-profile-lookup-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/users/GeboChatProfileLookupController/findChatProfileConfigurationLookupByCode` | findChatProfileConfigurationLookupByCode |
| POST | `/brain/api/users/GeboChatProfileLookupController/getAllChatProfileConfigurationLoookup` | getAllChatProfileConfigurationLoookup |
| POST | `/brain/api/users/GeboChatProfileLookupController/getChatProfileConfigurationLookupByQbe` | getChatProfileConfigurationLookupByQbe |

### `gebo-deep-search-admin-controller`
| Method | Path | Operation |
|---|---|---|
| DELETE | `/brain/api/admin/GeboDeepSearchAdminController/deleteDeepSearchConfig` | deleteDeepSearchConfig |
| GET | `/brain/api/admin/GeboDeepSearchAdminController/getConfigurableDataSources` | getConfigurableDataSources |
| GET | `/brain/api/admin/GeboDeepSearchAdminController/getDeepSeachConfigs` | getDeepSeachConfigs |
| GET | `/brain/api/admin/GeboDeepSearchAdminController/getDeepSearchDefaultConfig` | getDeepSearchDefaultConfig |
| GET | `/brain/api/admin/GeboDeepSearchAdminController/getDeepSearchDefaultOrSystemConfig` | getDeepSearchDefaultOrSystemConfig |
| GET | `/brain/api/admin/GeboDeepSearchAdminController/getDeepSearchSystemConfig` | getDeepSearchSystemConfig |
| POST | `/brain/api/admin/GeboDeepSearchAdminController/insertDeepSearchConfig` | insertDeepSearchConfig |
| POST | `/brain/api/admin/GeboDeepSearchAdminController/updateDeepSearchConfig` | updateDeepSearchConfig |

### `gebo-deep-search-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/users/GeboDeepSearchController/getDeepSearchDataSources` | getDeepSearchDataSources |

### `gebo-fast-chat-profile-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/GeboFastChatProfileStatusController/getChatProfilesSetupStatus` | getChatProfilesSetupStatus |

### `gebo-fast-knowledge-base-setup-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/GeboFastKnowledgeBaseSetupController/getCompleteKnowledgeBaseSetupStatus` | getCompleteKnowledgeBaseSetupStatus |
| GET | `/brain/api/admin/GeboFastKnowledgeBaseSetupController/getContentProcessRows` | getContentProcessRows |

### `gebo-fast-llms-setup-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/GeboFastLLMSSetupController/createLLMByAutoconfigure` | createLLMByAutoconfigure |
| POST | `/brain/api/admin/GeboFastLLMSSetupController/createLLMCredentials` | createLLMCredentials |
| POST | `/brain/api/admin/GeboFastLLMSSetupController/createLLMS` | createLLMS |
| GET | `/brain/api/admin/GeboFastLLMSSetupController/getActualLLMSConfiguration` | getActualLLMSConfiguration |
| GET | `/brain/api/admin/GeboFastLLMSSetupController/getLLMSSetupStatus` | getLLMSSetupStatus |
| POST | `/brain/api/admin/GeboFastLLMSSetupController/verifyCredentialsAndDownloadModels` | verifyCredentialsAndDownloadModels |
| POST | `/brain/api/admin/GeboFastLLMSSetupController/verifyVendorCredentialsAndDownloadModels` | verifyVendorCredentialsAndDownloadModels |

### `gebo-fast-vector-store-setup-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/GeboFastVectorStoreSetupController/createVectorStoreConfiguration` | createVectorStoreConfiguration |
| GET | `/brain/api/admin/GeboFastVectorStoreSetupController/getVectorStoreStatus` | getVectorStoreStatus |

### `gebo-llm-generated-resource-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/users/GeboLLMGeneratedResourceController/serveLLMGeneratedContent/{userSessionCode}/{generatedResourceCode}` | serveLLMGeneratedContent |

### `gebo-rag-chat-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/users/GeboChatController/getChatModelUserInfoByChatProfileCode` | getChatModelUserInfoByChatProfileCode |
| GET | `/brain/api/users/GeboChatController/getChatProfileModelMetaInfos` | getChatProfileModelMetaInfos |
| GET | `/brain/api/users/GeboChatController/getProfileProviderModelCapabilities` | getProfileProviderModelCapabilities |
| GET | `/brain/api/users/GeboChatController/getVisibleKnowledgeBasesByProfileCode` | getVisibleKnowledgeBasesByProfileCode |
| GET | `/brain/api/users/GeboChatController/profiles` | getChatProfiles |
| POST | `/brain/api/users/GeboChatController/ragChat` | ragChat |
| POST | `/brain/api/users/GeboChatController/streamRagResponse` | streamRagResponse |

### `gebo-text-to-speech-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/users/GeboTextToSpeechController/isEnabled` | isEnabled_1 |
| POST | `/brain/api/users/GeboTextToSpeechController/speechText` | speechText |

### `gebo-transcript-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/users/GeboTranscriptController/isEnabled` | isEnabled |
| POST | `/brain/api/users/GeboTranscriptController/transcriptText` | transcriptText |

### `gebo-user-chat-uploads-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/users/GeboUserChatUploadsController/chatSessionUpload/{userSessionCode}` | chatSessionUpload |
| DELETE | `/brain/api/users/GeboUserChatUploadsController/deleteSessionUploads` | deleteSessionUploads |
| GET | `/brain/api/users/GeboUserChatUploadsController/serveContent/{userSessionCode}/{uploadedContentId}` | serveContent |

### `gebo-user-chats-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/users/GeboUserChatsController/changeChatDescription` | changeChatDescription |
| GET | `/brain/api/users/GeboUserChatsController/createCleanChatByChatProfileCode` | createCleanChatByChatProfileCode |
| GET | `/brain/api/users/GeboUserChatsController/createCleanChatByModelCode` | createCleanChatByModelCode |
| DELETE | `/brain/api/users/GeboUserChatsController/deleteChat` | deleteChat |
| GET | `/brain/api/users/GeboUserChatsController/exportResponse2file` | exportResponse2file |
| GET | `/brain/api/users/GeboUserChatsController/getChatHistory` | getChatHistory |
| GET | `/brain/api/users/GeboUserChatsController/getChatInfosByCode` | getChatInfosByCode |
| POST | `/brain/api/users/GeboUserChatsController/getChatInfosByQbe` | getChatInfosByQbe |
| GET | `/brain/api/users/GeboUserChatsController/getMyChats` | getMyChats |
| GET | `/brain/api/users/GeboUserChatsController/getMyChatsPaged` | getMyChatsPaged |
| GET | `/brain/api/users/GeboUserChatsController/getUIConfig` | getUIConfig |
| GET | `/brain/api/users/GeboUserChatsController/suggestChatDescription` | suggestChatDescription |

### `gebo-user-knowledge-base-semantic-search-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/users/GeboUserKnowledgeBaseSemanticSearchController/semanticSearch` | semanticSearch |

### `gebo-vector-store-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/GeboVectorStoreConfigurationController/getActualVectorStoreConfiguration` | getActualVectorStoreConfiguration |
| POST | `/brain/api/admin/GeboVectorStoreConfigurationController/vectorStoreConfigurationApplyAndSave` | vectorStoreConfigurationApplyAndSave |

### `generic-open-ai-ranker-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/GenerigOpenAIRankerModelsConfigurationController/deleteGenericOpenAIAPIRankerModelConfig` | deleteGenericOpenAIAPIRankerModelConfig |
| GET | `/brain/api/admin/GenerigOpenAIRankerModelsConfigurationController/findGenericOpenAIAPIRankerModelConfigByCode` | findGenericOpenAIAPIRankerModelConfigByCode |
| POST | `/brain/api/admin/GenerigOpenAIRankerModelsConfigurationController/getGenericOpenAIAPIRankerModels` | getGenericOpenAIAPIRankerModels |
| GET | `/brain/api/admin/GenerigOpenAIRankerModelsConfigurationController/getGenericOpenAIRankerModelConfigs` | getGenericOpenAIRankerModelConfigs |
| GET | `/brain/api/admin/GenerigOpenAIRankerModelsConfigurationController/getGenericOpenAIRankerModelTypes` | getGenericOpenAIRankerModelTypes |
| POST | `/brain/api/admin/GenerigOpenAIRankerModelsConfigurationController/insertGenericOpenAIAPIRankerModelConfig` | insertGenericOpenAIAPIRankerModelConfig |
| POST | `/brain/api/admin/GenerigOpenAIRankerModelsConfigurationController/updateGenericOpenAIAPIRankerModelConfig` | updateGenericOpenAIAPIRankerModelConfig |

### `generic-open-aiapi-chat-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/GenericOpenAIAPIChatModelsConfigurationController/deleteGenericOpenAIAPIChatModelConfig` | deleteGenericOpenAIAPIChatModelConfig |
| GET | `/brain/api/admin/GenericOpenAIAPIChatModelsConfigurationController/findGenericOpenAIAPIChatModelConfigByCode` | findGenericOpenAIAPIChatModelConfigByCode |
| POST | `/brain/api/admin/GenericOpenAIAPIChatModelsConfigurationController/getGenericOpenAIAPIChatModels` | getGenericOpenAIAPIChatModels |
| GET | `/brain/api/admin/GenericOpenAIAPIChatModelsConfigurationController/getGenericOpenAIChatModelTypes` | getGenericOpenAIChatModelTypes |
| POST | `/brain/api/admin/GenericOpenAIAPIChatModelsConfigurationController/insertGenericOpenAIAPIChatModelConfig` | insertGenericOpenAIAPIChatModelConfig |
| POST | `/brain/api/admin/GenericOpenAIAPIChatModelsConfigurationController/updateGenericOpenAIAPIChatModelConfig` | updateGenericOpenAIAPIChatModelConfig |

### `generic-open-aiapi-embedding-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/deleteGenericOpenAIAPIEmbeddingModelConfig` | deleteGenericOpenAIAPIEmbeddingModelConfig |
| GET | `/brain/api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/findGenericOpenAIAPIEmbeddingModelConfigByCode` | findGenericOpenAIAPIEmbeddingModelConfigByCode |
| POST | `/brain/api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/getGenericOpenAIAPIEmbeddingModels` | getGenericOpenAIAPIEmbeddingModels |
| GET | `/brain/api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/getGenericOpenAIEmbeddingModelTypes` | getGenericOpenAIEmbeddingModelTypes |
| POST | `/brain/api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/insertGenericOpenAIAPIEmbeddingModelConfig` | insertGenericOpenAIAPIEmbeddingModelConfig |
| POST | `/brain/api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/updateGenericOpenAIAPIEmbeddingModelConfig` | updateGenericOpenAIAPIEmbeddingModelConfig |

### `generic-open-aiapi-image-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/GenericOpenAIAPIImageModelsConfigurationController/deleteGenericOpenAIAPIImageModelConfig` | deleteGenericOpenAIAPIImageModelConfig |
| GET | `/brain/api/admin/GenericOpenAIAPIImageModelsConfigurationController/findGenericOpenAIAPIImageModelConfigByCode` | findGenericOpenAIAPIImageModelConfigByCode |
| POST | `/brain/api/admin/GenericOpenAIAPIImageModelsConfigurationController/getGenericOpenAIAPIImageModels` | getGenericOpenAIAPIImageModels |
| GET | `/brain/api/admin/GenericOpenAIAPIImageModelsConfigurationController/getGenericOpenAIImageModelConfigs` | getGenericOpenAIImageModelConfigs |
| GET | `/brain/api/admin/GenericOpenAIAPIImageModelsConfigurationController/getGenericOpenAIImageModelTypes` | getGenericOpenAIImageModelTypes |
| POST | `/brain/api/admin/GenericOpenAIAPIImageModelsConfigurationController/insertGenericOpenAIAPIImageModelConfig` | insertGenericOpenAIAPIImageModelConfig |
| POST | `/brain/api/admin/GenericOpenAIAPIImageModelsConfigurationController/updateGenericOpenAIAPIImageModelConfig` | updateGenericOpenAIAPIImageModelConfig |

### `generic-open-aiapi-text-to-speech-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/deleteGenericOpenAIAPITextToSpeechModelConfig` | deleteGenericOpenAIAPITextToSpeechModelConfig |
| GET | `/brain/api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/findGenericOpenAIAPITextToSpeechModelConfigByCode` | findGenericOpenAIAPITextToSpeechModelConfigByCode |
| POST | `/brain/api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/getGenericOpenAIAPITextToSpeechModels` | getGenericOpenAIAPITextToSpeechModels |
| GET | `/brain/api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/getGenericOpenAITextToSpeechModelConfigs` | getGenericOpenAITextToSpeechModelConfigs |
| GET | `/brain/api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/getGenericOpenAITextToSpeechModelTypes` | getGenericOpenAITextToSpeechModelTypes |
| POST | `/brain/api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/insertGenericOpenAIAPITextToSpeechModelConfig` | insertGenericOpenAIAPITextToSpeechModelConfig |
| POST | `/brain/api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/updateGenericOpenAIAPITextToSpeechModelConfig` | updateGenericOpenAIAPITextToSpeechModelConfig |

### `generic-open-aiapi-transcript-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/deleteGenericOpenAIAPITranscriptModelConfig` | deleteGenericOpenAIAPITranscriptModelConfig |
| GET | `/brain/api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/findGenericOpenAIAPITranscriptModelConfigByCode` | findGenericOpenAIAPITranscriptModelConfigByCode |
| POST | `/brain/api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/getGenericOpenAIAPITranscriptModels` | getGenericOpenAIAPITranscriptModels |
| GET | `/brain/api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/getGenericOpenAITranscriptModelConfigs` | getGenericOpenAITranscriptModelConfigs |
| GET | `/brain/api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/getGenericOpenAITranscriptModelTypes` | getGenericOpenAITranscriptModelTypes |
| POST | `/brain/api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/insertGenericOpenAIAPITranscriptModelConfig` | insertGenericOpenAIAPITranscriptModelConfig |
| POST | `/brain/api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/updateGenericOpenAIAPITranscriptModelConfig` | updateGenericOpenAIAPITranscriptModelConfig |

### `google-search-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/GoogleSearchConfigurationController/deleteGGoogleSearchApiCredentials` | deleteGGoogleSearchApiCredentials |
| POST | `/brain/api/admin/GoogleSearchConfigurationController/fastInsertGoogleSearchApiCredentials` | fastInsertGoogleSearchApiCredentials |
| GET | `/brain/api/admin/GoogleSearchConfigurationController/getGoogleSearchApiCredentials` | getGoogleSearchApiCredentials |
| GET | `/brain/api/admin/GoogleSearchConfigurationController/getGoogleSearchStatus` | getGoogleSearchStatus |
| POST | `/brain/api/admin/GoogleSearchConfigurationController/insertGGoogleSearchApiCredentials` | insertGGoogleSearchApiCredentials |
| GET | `/brain/api/admin/GoogleSearchConfigurationController/searchGGoogleSearchApiCredentialsByCode` | searchGGoogleSearchApiCredentialsByCode |
| POST | `/brain/api/admin/GoogleSearchConfigurationController/updateGGoogleSearchApiCredentials` | updateGGoogleSearchApiCredentials |

### `google-search-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/users/GoogleSearchController/googleSearch` | googleSearch |

### `image-models-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/ImageModelsController/getImageModelTypes` | getImageModelTypes |
| GET | `/brain/api/admin/ImageModelsController/getRuntimeConfiguredImageModels` | getRuntimeConfiguredImageModels |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/brain/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/brain/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `ollama-chat-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/OllamaChatModelsConfigurationController/deleteOllamaChatModelConfig` | deleteOllamaChatModelConfig |
| GET | `/brain/api/admin/OllamaChatModelsConfigurationController/findOllamaChatModelConfigByCode` | findOllamaChatModelConfigByCode |
| POST | `/brain/api/admin/OllamaChatModelsConfigurationController/getOllamaModels` | getOllamaChatModels |
| POST | `/brain/api/admin/OllamaChatModelsConfigurationController/insertOllamaChatModelConfig` | insertOllamaChatModelConfig |
| POST | `/brain/api/admin/OllamaChatModelsConfigurationController/updateOllamaChatModelConfig` | updateOllamaChatModelConfig |

### `ollama-embedding-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/OllamaEmbeddingModelsConfigurationController/deleteOllamaEmbeddingModelConfig` | deleteOllamaEmbeddingModelConfig |
| GET | `/brain/api/admin/OllamaEmbeddingModelsConfigurationController/findOllamaEmbeddingModelConfigByCode` | findOllamaEmbeddingModelConfigByCode |
| POST | `/brain/api/admin/OllamaEmbeddingModelsConfigurationController/getOllamaEmbeddingModels` | getOllamaEmbeddingModels |
| POST | `/brain/api/admin/OllamaEmbeddingModelsConfigurationController/insertOllamaEmbeddingModelConfig` | insertOllamaEmbeddingModelConfig |
| POST | `/brain/api/admin/OllamaEmbeddingModelsConfigurationController/updateOllamaEmbeddingModelConfig` | updateOllamaEmbeddingModelConfig |

### `onnx-transformers-embedding-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/ONNXTransformersEmbeddingModelsConfigurationController/deleteONNXTransformersEmbeddingModelConfig` | deleteONNXTransformersEmbeddingModelConfig |
| GET | `/brain/api/admin/ONNXTransformersEmbeddingModelsConfigurationController/findONNXTransformersEmbeddingModelConfigByCode` | findONNXTransformersEmbeddingModelConfigByCode |
| POST | `/brain/api/admin/ONNXTransformersEmbeddingModelsConfigurationController/getONNXTransformersEmbeddingModels` | getONNXTransformersEmbeddingModels |
| POST | `/brain/api/admin/ONNXTransformersEmbeddingModelsConfigurationController/insertONNXTransformersEmbeddingModelConfig` | insertONNXTransformersEmbeddingModelConfig |
| POST | `/brain/api/admin/ONNXTransformersEmbeddingModelsConfigurationController/updateONNXTransformersEmbeddingModelConfig` | updateONNXTransformersEmbeddingModelConfig |

### `open-ai-chat-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/OpenAIModelsConfigurationController/deleteOpenAIChatModelConfig` | deleteOpenAIChatModelConfig |
| GET | `/brain/api/admin/OpenAIModelsConfigurationController/findOpenAIChatModelConfigByCode` | findOpenAIChatModelConfigByCode |
| POST | `/brain/api/admin/OpenAIModelsConfigurationController/getOpenAIChatModels` | getOpenAIChatModels |
| POST | `/brain/api/admin/OpenAIModelsConfigurationController/insertOpenAIChatModelConfig` | insertOpenAIChatModelConfig |
| POST | `/brain/api/admin/OpenAIModelsConfigurationController/updateOpenAIChatModelConfig` | updateOpenAIChatModelConfig |

### `open-ai-embedding-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/OpenAIEmbeddingModelsConfigurationController/deleteOpenAIEmbeddingModelConfig` | deleteOpenAIEmbeddingModelConfig |
| GET | `/brain/api/admin/OpenAIEmbeddingModelsConfigurationController/findOpenAIEmbeddingModelConfigByCode` | findOpenAIEmbeddingModelConfigByCode |
| POST | `/brain/api/admin/OpenAIEmbeddingModelsConfigurationController/getOpenAIEmbeddingModels` | getOpenAIEmbeddingModels |
| POST | `/brain/api/admin/OpenAIEmbeddingModelsConfigurationController/insertOpenAIEmbeddingModelConfig` | insertOpenAIEmbeddingModelConfig |
| POST | `/brain/api/admin/OpenAIEmbeddingModelsConfigurationController/updateOpenAIEmbeddingModelConfig` | updateOpenAIEmbeddingModelConfig |

### `open-ai-image-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/OpenAIImageModelsConfigurationController/deleteOpenAIImageModelConfig` | deleteOpenAIImageModelConfig |
| GET | `/brain/api/admin/OpenAIImageModelsConfigurationController/findOpenAIImageModelConfigByCode` | findOpenAIImageModelConfigByCode |
| POST | `/brain/api/admin/OpenAIImageModelsConfigurationController/getOpenAIImageModels` | getOpenAIImageModels |
| POST | `/brain/api/admin/OpenAIImageModelsConfigurationController/insertOpenAIImageModelConfig` | insertOpenAIImageModelConfig |
| POST | `/brain/api/admin/OpenAIImageModelsConfigurationController/updateOpenAIImageModelConfig` | updateOpenAIImageModelConfig |

### `open-ai-text-to-speech-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/OpenAITextToSpeechModelsConfigurationController/deleteOpenAITextToSpeechModelConfig` | deleteOpenAITextToSpeechModelConfig |
| GET | `/brain/api/admin/OpenAITextToSpeechModelsConfigurationController/findOpenAITextToSpeechModelConfigByCode` | findOpenAITextToSpeechModelConfigByCode |
| POST | `/brain/api/admin/OpenAITextToSpeechModelsConfigurationController/getOpenAITextToSpeechModels` | getOpenAITextToSpeechModels |
| POST | `/brain/api/admin/OpenAITextToSpeechModelsConfigurationController/insertOpenAITextToSpeechModelConfig` | insertOpenAITextToSpeechModelConfig |
| POST | `/brain/api/admin/OpenAITextToSpeechModelsConfigurationController/updateOpenAITextToSpeechModelConfig` | updateOpenAITextToSpeechModelConfig |

### `open-ai-transcript-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/OpenAITranscriptModelsConfigurationController/deleteOpenAITranscriptModelConfig` | deleteOpenAITranscriptModelConfig |
| GET | `/brain/api/admin/OpenAITranscriptModelsConfigurationController/findOpenAITranscriptModelConfigByCode` | findOpenAITranscriptModelConfigByCode |
| POST | `/brain/api/admin/OpenAITranscriptModelsConfigurationController/getOpenAITranscriptModels` | getOpenAITranscriptModels |
| POST | `/brain/api/admin/OpenAITranscriptModelsConfigurationController/insertOpenAITranscriptModelConfig` | insertOpenAITranscriptModelConfig |
| POST | `/brain/api/admin/OpenAITranscriptModelsConfigurationController/updateOpenAITranscriptModelConfig` | updateOpenAITranscriptModelConfig |

### `prompt-templates-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/PromptTemplatesController/getDefaultPrompt` | getDefaultPrompt |
| POST | `/brain/api/admin/PromptTemplatesController/getDefaultPromptForChatModel` | getDefaultPromptForChatModel |
| POST | `/brain/api/admin/PromptTemplatesController/getDefaultPromptForChatModelReference` | getDefaultPromptForChatModelReference |

### `ranker-models-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/RankerModelsController/getRankerModelTypes` | getRankerModelTypes |
| GET | `/brain/api/admin/RankerModelsController/getRuntimeConfiguredRankerModels` | getRuntimeConfiguredRankerModels |

### `text-to-speech-models-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/TextToSpeechModelsController/getRuntimeConfiguredTextToSpeechModels` | getRuntimeConfiguredTextToSpeechModels |
| GET | `/brain/api/admin/TextToSpeechModelsController/getTextToSpeechModelTypes` | getTextToSpeechModelTypes |

### `transcript-models-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/TranscriptModelsController/getRuntimeConfiguredTranscriptModels` | getRuntimeConfiguredTranscriptModels |
| GET | `/brain/api/admin/TranscriptModelsController/getTranscriptModelTypes` | getTranscriptModelTypes |

## vectorizator.gebo.ai — port 13002 (`vectorizator-gebo-ai`) — context-path `/vectorizator`

2 controller(s), 4 endpoint(s):


### `gebo-core-analisys-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/vectorizator/api/admin/GeboCoreAnalisysController/drillDown` | coreDrillDown |
| GET | `/vectorizator/api/admin/GeboCoreAnalisysController/getTopLevelKnowledgeBaseCategory` | getTopLevelKnowledgeBaseCategory |

### `gebo-vector-store-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/vectorizator/api/admin/GeboVectorStoreConfigurationController/getActualVectorStoreConfiguration` | getActualVectorStoreConfiguration |
| POST | `/vectorizator/api/admin/GeboVectorStoreConfigurationController/vectorStoreConfigurationApplyAndSave` | vectorStoreConfigurationApplyAndSave |

## graphicator.gebo.ai — port 13003 (`graphicator-gebo-ai`) — context-path `/graphicator`

2 controller(s), 5 endpoint(s):


### `gebo-vector-store-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/graphicator/api/admin/GeboVectorStoreConfigurationController/getActualVectorStoreConfiguration` | getActualVectorStoreConfiguration |
| POST | `/graphicator/api/admin/GeboVectorStoreConfigurationController/vectorStoreConfigurationApplyAndSave` | vectorStoreConfigurationApplyAndSave |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/graphicator/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/graphicator/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/graphicator/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

## chunker.gebo.ai — port 13004 (`chunker-gebo-ai`) — context-path `/chunker`

4 controller(s), 16 endpoint(s):


### `document-content-streamer-with-cache-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/chunker/api/DocumentContentStreamerWithCacheController/streamDocumentReference` | streamDocumentReference |
| POST | `/chunker/api/DocumentContentStreamerWithCacheController/streamSearchResult` | streamSearchResult |

### `documents-cache-service-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/chunker/api/DocumentsCacheServiceController/streamDocument` | streamDocument |

### `documents-chunk-service-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/chunker/api/DocumentsChunkServiceController/createChunkingSession` | createChunkingSession |
| POST | `/chunker/api/DocumentsChunkServiceController/disposeChunkingSession` | disposeChunkingSession |
| POST | `/chunker/api/DocumentsChunkServiceController/getCachedChunkSet` | getCachedChunkSet |
| POST | `/chunker/api/DocumentsChunkServiceController/getChunkSet` | getChunkSet |
| POST | `/chunker/api/DocumentsChunkServiceController/getNextChunkSet` | getNextChunkSet |
| POST | `/chunker/api/DocumentsChunkServiceController/prepareChunks` | prepareChunks |
| GET | `/chunker/api/DocumentsChunkServiceController/retrieveChunkingSession` | retrieveChunkingSession |
| POST | `/chunker/api/DocumentsChunkServiceController/streamChunks` | streamChunks |
| POST | `/chunker/api/DocumentsChunkServiceController/streamChunksBatch` | streamChunksBatch |
| POST | `/chunker/api/DocumentsChunkServiceController/streamChunksReactive` | streamChunksReactive |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/chunker/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/chunker/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/chunker/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

## git.gebo.ai — port 13005 (`git-gebo-ai`) — context-path `/git`

6 controller(s), 22 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/git/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/git/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/git/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/git/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `git-systems-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/git/api/admin/GITSystemsController/deleteGitEndpoint` | deleteGitEndpoint |
| POST | `/git/api/admin/GITSystemsController/deleteGitSystem` | deleteGitSystem |
| GET | `/git/api/admin/GITSystemsController/findGitEndpointsByProject` | findGitEndpointsByProject |
| POST | `/git/api/admin/GITSystemsController/findGitEndpointsByQbe` | findGitEndpointsByQbe |
| POST | `/git/api/admin/GITSystemsController/getBranchesList` | getBranchesList |
| GET | `/git/api/admin/GITSystemsController/getGitSystemTypes` | getGitSystemTypes |
| GET | `/git/api/admin/GITSystemsController/getGitSystems` | getGitSystems |
| POST | `/git/api/admin/GITSystemsController/insertGitEndpoint` | insertGitEndpoint |
| POST | `/git/api/admin/GITSystemsController/insertGitSystem` | insertGitSystem |
| POST | `/git/api/admin/GITSystemsController/publishGitEndpoint` | publishGitEndpoint |
| POST | `/git/api/admin/GITSystemsController/updateGitEndpoint` | updateGitEndpoint |
| POST | `/git/api/admin/GITSystemsController/updateGitSystem` | updateGitSystem |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/git/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/git/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/git/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/git/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/git/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/git/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

## filesystem.gebo.ai — port 13006 (`filesystem-gebo-ai`) — context-path `/filesystem`

8 controller(s), 30 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/filesystem/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/filesystem/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/filesystem/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `file-system-shares-setting-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/filesystem/api/admin/FileSystemSharesSettingController/checkCanBeInsertedFileSystemShareReference` | checkCanBeInsertedFileSystemShareReference |
| POST | `/filesystem/api/admin/FileSystemSharesSettingController/deleteFileSystemShareReference` | deleteFileSystemShareReference |
| GET | `/filesystem/api/admin/FileSystemSharesSettingController/getFileSystemShareReferenceByCode` | getFileSystemShareReferenceByCode |
| POST | `/filesystem/api/admin/FileSystemSharesSettingController/getGFileSystemNodeChildrens` | getGFileSystemNodeChildrens |
| POST | `/filesystem/api/admin/FileSystemSharesSettingController/getGFileSystemNodeNavigationStatus` | getGFileSystemNodeNavigationStatus |
| GET | `/filesystem/api/admin/FileSystemSharesSettingController/getRootGFileSystemNodes` | getRootGFileSystemNodes |
| GET | `/filesystem/api/admin/FileSystemSharesSettingController/getSharedFileSystemsActualConfiguration` | getSharedFileSystemsActualConfiguration |
| POST | `/filesystem/api/admin/FileSystemSharesSettingController/getUsedFilesystemShares` | getUsedFilesystemShares |
| POST | `/filesystem/api/admin/FileSystemSharesSettingController/insertFileSystemShareReference` | insertFileSystemShareReference |

### `file-systems-browsing-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/filesystem/api/admin/FileSystemsBrowsingController/browseSharedFilesystemRootsPath` | browseSharedFilesystemRootsPath |
| POST | `/filesystem/api/admin/FileSystemsBrowsingController/getSharedFilesystemNavigationStatus` | getSharedFilesystemNavigationStatus |
| GET | `/filesystem/api/admin/FileSystemsBrowsingController/getSharedFilesystemRoots` | getSharedFilesystemRoots |

### `file-systems-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/filesystem/api/admin/FileSystemsController/deleteFilesystemEndpoint` | deleteFilesystemEndpoint |
| GET | `/filesystem/api/admin/FileSystemsController/findFileSystemEndpointsByProject` | findFileSystemEndpointsByProject |
| POST | `/filesystem/api/admin/FileSystemsController/findFileSystemEndpointsByQbe` | findFileSystemEndpointsByQbe |
| GET | `/filesystem/api/admin/FileSystemsController/getFileSystemSystemTypes` | getFileSystemSystemTypes |
| GET | `/filesystem/api/admin/FileSystemsController/getFileSystemSystems` | getFileSystemSystems |
| POST | `/filesystem/api/admin/FileSystemsController/insertFilesystemEndpoint` | insertFilesystemEndpoint |
| POST | `/filesystem/api/admin/FileSystemsController/publishFilesystemEndpoint` | publishFilesystemEndpoint |
| POST | `/filesystem/api/admin/FileSystemsController/updateFilesystemEndpoint` | updateFilesystemEndpoint |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/filesystem/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/filesystem/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/filesystem/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/filesystem/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/filesystem/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/filesystem/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/filesystem/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

## uploads.gebo.ai — port 13007 (`uploads-gebo-ai`) — context-path `/uploads`

7 controller(s), 21 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/uploads/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/uploads/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/uploads/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `file-upload-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/uploads/api/admin/FileUploadController/getHandShakeCode` | getHandShakeCode |
| POST | `/uploads/api/admin/FileUploadController/upload/{handShakeCode}` | upload |

### `file-uploads-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/uploads/api/admin/FileUploadsController/deleteUploadsEndpoint` | deleteUploadsEndpoint |
| GET | `/uploads/api/admin/FileUploadsController/findUploadsEndpointsByProject` | findUploadsEndpointsByProject |
| POST | `/uploads/api/admin/FileUploadsController/findUploadsEndpointsByQbe` | findUploadsEndpointsByQbe |
| GET | `/uploads/api/admin/FileUploadsController/getFileSystemSystemTypes` | getFileSystemSystemTypes |
| GET | `/uploads/api/admin/FileUploadsController/getUploadableFilesExtensions` | getUploadableFilesExtensions |
| GET | `/uploads/api/admin/FileUploadsController/getUploadsSystems` | getUploadsSystems |
| POST | `/uploads/api/admin/FileUploadsController/insertUploadsEndpoint` | insertUploadsEndpoint |
| POST | `/uploads/api/admin/FileUploadsController/publishUploadsEndpoint` | publishUploadsEndpoint |
| POST | `/uploads/api/admin/FileUploadsController/updateUploadsEndpoint` | updateUploadsEndpoint |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/uploads/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/uploads/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/uploads/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/uploads/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/uploads/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/uploads/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/uploads/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

## userspace.gebo.ai — port 13008 (`userspace-gebo-ai`) — context-path `/userspace`

7 controller(s), 29 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/userspace/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/userspace/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/userspace/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/userspace/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/userspace/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/userspace/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/userspace/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/userspace/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/userspace/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/userspace/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

### `userspace-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/userspace/api/user/UserspaceController/deleteUserKnowledgebase` | deleteUserKnowledgebase |
| POST | `/userspace/api/user/UserspaceController/deleteUserspaceFiles` | deleteUserspaceFiles |
| POST | `/userspace/api/user/UserspaceController/deleteUserspaceFolder` | deleteUserspaceFolder |
| GET | `/userspace/api/user/UserspaceController/findUserKnowledgebaseByCode` | findUserKnowledgebaseByCode |
| POST | `/userspace/api/user/UserspaceController/findUserspaceFileByCodes` | findUserspaceFileByCodes |
| GET | `/userspace/api/user/UserspaceController/findUserspaceFolderByCode` | findUserspaceFolderByCode |
| GET | `/userspace/api/user/UserspaceController/getPersonalKnowledgebases` | getPersonalKnowledgebases |
| POST | `/userspace/api/user/UserspaceController/getPublishingStatus` | getPublishingStatus |
| POST | `/userspace/api/user/UserspaceController/listChildPersonalKnowledgebases` | listChildPersonalKnowledgebases |
| GET | `/userspace/api/user/UserspaceController/listUserspaceFiles` | listUserspaceFiles |
| GET | `/userspace/api/user/UserspaceController/listUserspaceFolders` | listUserspaceFolders |
| POST | `/userspace/api/user/UserspaceController/newUserKnowledgebase` | newUserKnowledgebase |
| POST | `/userspace/api/user/UserspaceController/newUserspaceFolder` | newUserspaceFolder |
| POST | `/userspace/api/user/UserspaceController/publishFolder` | publishFolder |
| POST | `/userspace/api/user/UserspaceController/publishUserspaceProjectEndpoint` | publishUserspaceProjectEndpoint |
| POST | `/userspace/api/user/UserspaceController/transferUploadsToUserSpaceAndPublish` | transferUploadsToUserSpaceAndPublish |
| POST | `/userspace/api/user/UserspaceController/updateUserKnowledgebase` | updateUserKnowledgebase |
| POST | `/userspace/api/user/UserspaceController/updateUserspaceFolder` | updateUserspaceFolder |

### `userspace-upload-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/userspace/api/user/UserspaceUploadController/upload/{userspaceFolderCode}` | upload |

## sharepoint.gebo.ai — port 13009 (`sharepoint-gebo-ai`) — context-path `/sharepoint`

8 controller(s), 45 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/sharepoint/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/sharepoint/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/sharepoint/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/sharepoint/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/sharepoint/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/sharepoint/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/sharepoint/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/sharepoint/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/sharepoint/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/sharepoint/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

### `share-point-search-service-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/sharepoint/api/users/SharePointSearchServiceController/aggregate` | restAggregate |
| POST | `/sharepoint/api/users/SharePointSearchServiceController/createCustomTemplateParamsMap` | restCreateCustomTemplateParamsMap |
| POST | `/sharepoint/api/users/SharePointSearchServiceController/extractRelatedAnalisysReferences` | restExtractRelatedAnalisysReferences |
| GET | `/sharepoint/api/users/SharePointSearchServiceController/findSystemById` | restFindSystemById |
| POST | `/sharepoint/api/users/SharePointSearchServiceController/findSystemBySearchResult` | restFindSystemBySearchResult |
| GET | `/sharepoint/api/users/SharePointSearchServiceController/getCachedCatalogues` | restGetCachedCatalogues |
| GET | `/sharepoint/api/users/SharePointSearchServiceController/getCataloguesListSample` | restGetCataloguesListSample |
| GET | `/sharepoint/api/users/SharePointSearchServiceController/getDescription` | restGetDescription |
| GET | `/sharepoint/api/users/SharePointSearchServiceController/getId` | restGetId |
| GET | `/sharepoint/api/users/SharePointSearchServiceController/getMessagingModuleId` | restGetMessagingModuleId |
| GET | `/sharepoint/api/users/SharePointSearchServiceController/getNativePromptTemplateUseCode` | restGetNativePromptTemplateUseCode |
| GET | `/sharepoint/api/users/SharePointSearchServiceController/getProductId` | restGetProductId |
| GET | `/sharepoint/api/users/SharePointSearchServiceController/getQueriesGenerationPromptUseCode` | restGetQueriesGenerationPromptUseCode |
| GET | `/sharepoint/api/users/SharePointSearchServiceController/getSearchableSystems` | restGetSearchableSystems |
| GET | `/sharepoint/api/users/SharePointSearchServiceController/isEnabled` | restIsEnabled |
| POST | `/sharepoint/api/users/SharePointSearchServiceController/nativeSearch` | restNativeSearch |
| POST | `/sharepoint/api/users/SharePointSearchServiceController/search` | restSearch |

### `sharepoint-browsing-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/sharepoint/api/admin/SharepointBrowsingController/browseSharepointPath` | browseSharepointPath |
| POST | `/sharepoint/api/admin/SharepointBrowsingController/getSharepointNavigationStatus` | getSharepointNavigationStatus |
| GET | `/sharepoint/api/admin/SharepointBrowsingController/getSharepointRoots` | getSharepointRoots |

### `sharepoint-systems-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/sharepoint/api/admin/SharepointSystemsController/deleteSharepointEndpoint` | deleteSharepointEndpoint |
| POST | `/sharepoint/api/admin/SharepointSystemsController/deleteSharepointSystem` | deleteSharepointSystem |
| POST | `/sharepoint/api/admin/SharepointSystemsController/fastSharepointConfig` | fastSharepointConfig |
| GET | `/sharepoint/api/admin/SharepointSystemsController/findSharepointEndpointsByCode` | findSharepointEndpointsByCode |
| GET | `/sharepoint/api/admin/SharepointSystemsController/findSharepointEndpointsByProject` | findSharepointEndpointsByProject |
| POST | `/sharepoint/api/admin/SharepointSystemsController/findSharepointEndpointsByQbe` | findSharepointEndpointsByQbe |
| GET | `/sharepoint/api/admin/SharepointSystemsController/findSharepointSystemByCode` | findSharepointSystemByCode |
| GET | `/sharepoint/api/admin/SharepointSystemsController/getSharepointSystemType` | getSharepointSystemTypes |
| GET | `/sharepoint/api/admin/SharepointSystemsController/getSharepointSystems` | getSharepointSystems |
| POST | `/sharepoint/api/admin/SharepointSystemsController/insertSharepointEndpoint` | insertSharepointEndpoint |
| POST | `/sharepoint/api/admin/SharepointSystemsController/insertSharepointSystem` | insertSharepointSystem |
| POST | `/sharepoint/api/admin/SharepointSystemsController/publishSharepointEndpoint` | publishSharepointEndpoint |
| POST | `/sharepoint/api/admin/SharepointSystemsController/testSharepointSystem` | testSharepointSystem |
| POST | `/sharepoint/api/admin/SharepointSystemsController/updateSharepointEndpoint` | updateSharepointEndpoint |
| POST | `/sharepoint/api/admin/SharepointSystemsController/updateSharepointSystem` | updateSharepointSystem |

## confluence.gebo.ai — port 13010 (`confluence-gebo-ai`) — context-path `/confluence`

8 controller(s), 45 endpoint(s):


### `confluence-browsing-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/confluence/api/admin/ConfluenceBrowsingController/browseConfluencePath` | browseConfluencePath |
| POST | `/confluence/api/admin/ConfluenceBrowsingController/getConfluenceNavigationStatus` | getConfluenceNavigationStatus |
| GET | `/confluence/api/admin/ConfluenceBrowsingController/getConfluenceRoots` | getConfluenceRoots |

### `confluence-search-service-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/confluence/api/users/ConfluenceSearchServiceController/aggregate` | restAggregate |
| POST | `/confluence/api/users/ConfluenceSearchServiceController/createCustomTemplateParamsMap` | restCreateCustomTemplateParamsMap |
| POST | `/confluence/api/users/ConfluenceSearchServiceController/extractRelatedAnalisysReferences` | restExtractRelatedAnalisysReferences |
| GET | `/confluence/api/users/ConfluenceSearchServiceController/findSystemById` | restFindSystemById |
| POST | `/confluence/api/users/ConfluenceSearchServiceController/findSystemBySearchResult` | restFindSystemBySearchResult |
| GET | `/confluence/api/users/ConfluenceSearchServiceController/getCachedCatalogues` | restGetCachedCatalogues |
| GET | `/confluence/api/users/ConfluenceSearchServiceController/getCataloguesListSample` | restGetCataloguesListSample |
| GET | `/confluence/api/users/ConfluenceSearchServiceController/getDescription` | restGetDescription |
| GET | `/confluence/api/users/ConfluenceSearchServiceController/getId` | restGetId |
| GET | `/confluence/api/users/ConfluenceSearchServiceController/getMessagingModuleId` | restGetMessagingModuleId |
| GET | `/confluence/api/users/ConfluenceSearchServiceController/getNativePromptTemplateUseCode` | restGetNativePromptTemplateUseCode |
| GET | `/confluence/api/users/ConfluenceSearchServiceController/getProductId` | restGetProductId |
| GET | `/confluence/api/users/ConfluenceSearchServiceController/getQueriesGenerationPromptUseCode` | restGetQueriesGenerationPromptUseCode |
| GET | `/confluence/api/users/ConfluenceSearchServiceController/getSearchableSystems` | restGetSearchableSystems |
| GET | `/confluence/api/users/ConfluenceSearchServiceController/isEnabled` | restIsEnabled |
| POST | `/confluence/api/users/ConfluenceSearchServiceController/nativeSearch` | restNativeSearch |
| POST | `/confluence/api/users/ConfluenceSearchServiceController/search` | restSearch |

### `confluence-systems-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/confluence/api/admin/ConfluenceSystemsController/deleteConfluenceEndpoint` | deleteConfluenceEndpoint |
| POST | `/confluence/api/admin/ConfluenceSystemsController/deleteConfluenceSystem` | deleteConfluenceSystem |
| POST | `/confluence/api/admin/ConfluenceSystemsController/fastConfluenceConfig` | fastConfluenceConfig |
| GET | `/confluence/api/admin/ConfluenceSystemsController/findConfluenceEndpointsByCode` | findConfluenceEndpointsByCode |
| GET | `/confluence/api/admin/ConfluenceSystemsController/findConfluenceEndpointsByProject` | findConfluenceEndpointsByProject |
| POST | `/confluence/api/admin/ConfluenceSystemsController/findConfluenceEndpointsByQbe` | findConfluenceEndpointsByQbe |
| GET | `/confluence/api/admin/ConfluenceSystemsController/findConfluenceSystemByCode` | findConfluenceSystemByCode |
| GET | `/confluence/api/admin/ConfluenceSystemsController/getConfluenceSystemType` | getConfluenceSystemTypes |
| GET | `/confluence/api/admin/ConfluenceSystemsController/getConfluenceSystems` | getConfluenceSystems |
| POST | `/confluence/api/admin/ConfluenceSystemsController/insertConfluenceEndpoint` | insertConfluenceEndpoint |
| POST | `/confluence/api/admin/ConfluenceSystemsController/insertConfluenceSystem` | insertConfluenceSystem |
| POST | `/confluence/api/admin/ConfluenceSystemsController/publishConfluenceEndpoint` | publishConfluenceEndpoint |
| POST | `/confluence/api/admin/ConfluenceSystemsController/testConfluenceSystem` | testConfluenceSystem |
| POST | `/confluence/api/admin/ConfluenceSystemsController/updateConfluenceEndpoint` | updateConfluenceEndpoint |
| POST | `/confluence/api/admin/ConfluenceSystemsController/updateConfluenceSystem` | updateConfluenceSystem |

### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/confluence/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/confluence/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/confluence/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/confluence/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/confluence/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/confluence/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/confluence/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/confluence/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/confluence/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/confluence/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

## jira.gebo.ai — port 13011 (`jira-gebo-ai`) — context-path `/jira`

8 controller(s), 45 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/jira/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/jira/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/jira/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/jira/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/jira/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/jira/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/jira/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `jira-browsing-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/jira/api/admin/JiraBrowsingController/browseJiraPath` | browseJiraPath |
| POST | `/jira/api/admin/JiraBrowsingController/getJiraNavigationStatus` | getJiraNavigationStatus |
| GET | `/jira/api/admin/JiraBrowsingController/getJiraRoots` | getJiraRoots |

### `jira-search-service-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/jira/api/users/JiraSearchServiceController/aggregate` | restAggregate |
| POST | `/jira/api/users/JiraSearchServiceController/createCustomTemplateParamsMap` | restCreateCustomTemplateParamsMap |
| POST | `/jira/api/users/JiraSearchServiceController/extractRelatedAnalisysReferences` | restExtractRelatedAnalisysReferences |
| GET | `/jira/api/users/JiraSearchServiceController/findSystemById` | restFindSystemById |
| POST | `/jira/api/users/JiraSearchServiceController/findSystemBySearchResult` | restFindSystemBySearchResult |
| GET | `/jira/api/users/JiraSearchServiceController/getCachedCatalogues` | restGetCachedCatalogues |
| GET | `/jira/api/users/JiraSearchServiceController/getCataloguesListSample` | restGetCataloguesListSample |
| GET | `/jira/api/users/JiraSearchServiceController/getDescription` | restGetDescription |
| GET | `/jira/api/users/JiraSearchServiceController/getId` | restGetId |
| GET | `/jira/api/users/JiraSearchServiceController/getMessagingModuleId` | restGetMessagingModuleId |
| GET | `/jira/api/users/JiraSearchServiceController/getNativePromptTemplateUseCode` | restGetNativePromptTemplateUseCode |
| GET | `/jira/api/users/JiraSearchServiceController/getProductId` | restGetProductId |
| GET | `/jira/api/users/JiraSearchServiceController/getQueriesGenerationPromptUseCode` | restGetQueriesGenerationPromptUseCode |
| GET | `/jira/api/users/JiraSearchServiceController/getSearchableSystems` | restGetSearchableSystems |
| GET | `/jira/api/users/JiraSearchServiceController/isEnabled` | restIsEnabled |
| POST | `/jira/api/users/JiraSearchServiceController/nativeSearch` | restNativeSearch |
| POST | `/jira/api/users/JiraSearchServiceController/search` | restSearch |

### `jira-systems-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/jira/api/admin/JiraSystemsController/deleteJiraEndpoint` | deleteJiraEndpoint |
| POST | `/jira/api/admin/JiraSystemsController/deleteJiraSystem` | deleteJiraSystem |
| POST | `/jira/api/admin/JiraSystemsController/fastJiraConfig` | fastJiraConfig |
| GET | `/jira/api/admin/JiraSystemsController/findJiraEndpointsByCode` | findJiraEndpointsByCode |
| GET | `/jira/api/admin/JiraSystemsController/findJiraEndpointsByProject` | findJiraEndpointsByProject |
| POST | `/jira/api/admin/JiraSystemsController/findJiraEndpointsByQbe` | findJiraEndpointsByQbe |
| GET | `/jira/api/admin/JiraSystemsController/findJiraSystemByCode` | findJiraSystemByCode |
| GET | `/jira/api/admin/JiraSystemsController/getJiraSystemType` | getJiraSystemTypes |
| GET | `/jira/api/admin/JiraSystemsController/getJiraSystems` | getJiraSystems |
| POST | `/jira/api/admin/JiraSystemsController/insertJiraEndpoint` | insertJiraEndpoint |
| POST | `/jira/api/admin/JiraSystemsController/insertJiraSystem` | insertJiraSystem |
| POST | `/jira/api/admin/JiraSystemsController/publishJiraEndpoint` | publishJiraEndpoint |
| POST | `/jira/api/admin/JiraSystemsController/testJiraSystem` | testJiraSystem |
| POST | `/jira/api/admin/JiraSystemsController/updateJiraEndpoint` | updateJiraEndpoint |
| POST | `/jira/api/admin/JiraSystemsController/updateJiraSystem` | updateJiraSystem |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/jira/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/jira/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/jira/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

## aws-s3.gebo.ai — port 13012 (`aws-s3-gebo-ai`) — context-path `/aws-s3`

7 controller(s), 26 endpoint(s):


### `aws-s-3-browsing-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/aws-s3/api/admin/AwsS3BrowsingController/browseAwsS3Path` | browseAwsS3Path |
| GET | `/aws-s3/api/admin/AwsS3BrowsingController/getAwsS3Roots` | getAwsS3Roots |

### `aws-s-3-systems-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/aws-s3/api/admin/AwsS3SystemsController/deleteAwsS3ProjectEndpoint` | deleteAwsS3ProjectEndpoint |
| POST | `/aws-s3/api/admin/AwsS3SystemsController/deleteAwsS3System` | deleteAwsS3System |
| POST | `/aws-s3/api/admin/AwsS3SystemsController/fastAwsS3Config` | fastAwsS3Config |
| GET | `/aws-s3/api/admin/AwsS3SystemsController/findAwsS3EndpointsByProject` | findAwsS3EndpointsByProject |
| POST | `/aws-s3/api/admin/AwsS3SystemsController/findAwsS3EndpointsByQbe` | findAwsS3EndpointsByQbe |
| GET | `/aws-s3/api/admin/AwsS3SystemsController/findAwsS3ProjectEndpointByCode` | findAwsS3ProjectEndpointByCode |
| GET | `/aws-s3/api/admin/AwsS3SystemsController/findAwsS3SystemByCode` | findAwsS3SystemByCode |
| GET | `/aws-s3/api/admin/AwsS3SystemsController/getAwsS3SystemType` | getAwsS3SystemType |
| GET | `/aws-s3/api/admin/AwsS3SystemsController/getAwsS3Systems` | getAwsS3Systems |
| POST | `/aws-s3/api/admin/AwsS3SystemsController/insertAwsS3ProjectEndpoint` | insertAwsS3ProjectEndpoint |
| POST | `/aws-s3/api/admin/AwsS3SystemsController/insertAwsS3System` | insertAwsS3System |
| POST | `/aws-s3/api/admin/AwsS3SystemsController/publishAwsS3ProjectEndpoint` | publishAwsS3ProjectEndpoint |
| POST | `/aws-s3/api/admin/AwsS3SystemsController/updateAwsS3ProjectEndpoint` | updateAwsS3ProjectEndpoint |
| POST | `/aws-s3/api/admin/AwsS3SystemsController/updateAwsS3System` | updateAwsS3System |

### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/aws-s3/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/aws-s3/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/aws-s3/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/aws-s3/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/aws-s3/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/aws-s3/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/aws-s3/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/aws-s3/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/aws-s3/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/aws-s3/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

## googledrive.gebo.ai — port 13013 (`googledrive-gebo-ai`) — context-path `/googledrive`

9 controller(s), 43 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/googledrive/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/googledrive/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/googledrive/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/googledrive/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `google-drive-browsing-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/googledrive/api/admin/GoogleDriveBrowsingController/browseGoogleDrivePath` | browseGoogleDrivePath |
| GET | `/googledrive/api/admin/GoogleDriveBrowsingController/getGoogleDriveRoots` | getGoogleDriveRoots |

### `google-drive-search-service-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/googledrive/api/users/GoogleDriveSearchServiceController/aggregate` | restAggregate |
| POST | `/googledrive/api/users/GoogleDriveSearchServiceController/extractRelatedAnalisysReferences` | restExtractRelatedAnalisysReferences |
| GET | `/googledrive/api/users/GoogleDriveSearchServiceController/findSystemById` | restFindSystemById |
| POST | `/googledrive/api/users/GoogleDriveSearchServiceController/findSystemBySearchResult` | restFindSystemBySearchResult |
| GET | `/googledrive/api/users/GoogleDriveSearchServiceController/getCachedCatalogues` | restGetCachedCatalogues |
| GET | `/googledrive/api/users/GoogleDriveSearchServiceController/getCataloguesListSample` | restGetCataloguesListSample |
| GET | `/googledrive/api/users/GoogleDriveSearchServiceController/getDescription` | restGetDescription |
| GET | `/googledrive/api/users/GoogleDriveSearchServiceController/getId` | restGetId |
| GET | `/googledrive/api/users/GoogleDriveSearchServiceController/getMessagingModuleId` | restGetMessagingModuleId |
| GET | `/googledrive/api/users/GoogleDriveSearchServiceController/getProductId` | restGetProductId |
| GET | `/googledrive/api/users/GoogleDriveSearchServiceController/getQueriesGenerationPromptUseCode` | restGetQueriesGenerationPromptUseCode |
| GET | `/googledrive/api/users/GoogleDriveSearchServiceController/getSearchableSystems` | restGetSearchableSystems |
| GET | `/googledrive/api/users/GoogleDriveSearchServiceController/isEnabled` | restIsEnabled |
| POST | `/googledrive/api/users/GoogleDriveSearchServiceController/search` | restSearch |

### `google-drive-systems-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/googledrive/api/admin/GoogleDriveSystemsController/deleteGoogleDriveProjectEndpoint` | deleteGoogleDriveProjectEndpoint |
| POST | `/googledrive/api/admin/GoogleDriveSystemsController/deleteGoogleDriveSystem` | deleteGoogleDriveSystem |
| POST | `/googledrive/api/admin/GoogleDriveSystemsController/fastGoogleDriveConfig` | fastGoogleDriveConfig |
| GET | `/googledrive/api/admin/GoogleDriveSystemsController/findGoogleDriveEndpointsByProject` | findGoogleDriveEndpointsByProject |
| POST | `/googledrive/api/admin/GoogleDriveSystemsController/findGoogleDriveEndpointsByQbe` | findGoogleDriveEndpointsByQbe |
| GET | `/googledrive/api/admin/GoogleDriveSystemsController/findGoogleDriveProjectEndpointByCode` | findGoogleDriveProjectEndpointByCode |
| GET | `/googledrive/api/admin/GoogleDriveSystemsController/findGoogleDriveSystemByCode` | findGoogleDriveSystemByCode |
| GET | `/googledrive/api/admin/GoogleDriveSystemsController/getGoogleDriveSystemType` | getGoogleDriveSystemType |
| GET | `/googledrive/api/admin/GoogleDriveSystemsController/getGoogleDriveSystems` | getGoogleDriveSystems |
| POST | `/googledrive/api/admin/GoogleDriveSystemsController/insertGoogleDriveProjectEndpoint` | insertGoogleDriveProjectEndpoint |
| POST | `/googledrive/api/admin/GoogleDriveSystemsController/insertGoogleDriveSystem` | insertGoogleDriveSystem |
| POST | `/googledrive/api/admin/GoogleDriveSystemsController/publishGoogleDriveProjectEndpoint` | publishGoogleDriveProjectEndpoint |
| POST | `/googledrive/api/admin/GoogleDriveSystemsController/updateGoogleDriveProjectEndpoint` | updateGoogleDriveProjectEndpoint |
| POST | `/googledrive/api/admin/GoogleDriveSystemsController/updateGoogleDriveSystem` | updateGoogleDriveSystem |

### `google-workspace-access-handshake-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/googledrive/api/users/start-workspace-access` | tryGoogleWorkspaceAccess |
| GET | `/googledrive/oauth2/google-workspace-redirect` | googleWorkspaceRedirect |
| GET | `/googledrive/oauth2/start-workspace-access-go` | startWorkspaceAccess |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/googledrive/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/googledrive/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/googledrive/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/googledrive/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/googledrive/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/googledrive/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

## mcpclient.gebo.ai — port 13014 (`mcpclient-gebo-ai`) — context-path `/mcpclient`

8 controller(s), 28 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/mcpclient/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/mcpclient/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/mcpclient/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/mcpclient/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/mcpclient/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/mcpclient/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/mcpclient/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/mcpclient/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/mcpclient/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/mcpclient/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

### `mcp-client-browsing-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/mcpclient/api/admin/MCPClientBrowsingController/browseMCPClientPath` | browseMCPClientPath |
| POST | `/mcpclient/api/admin/MCPClientBrowsingController/getMCPClientNavigationStatus` | getMCPClientNavigationStatus |
| GET | `/mcpclient/api/admin/MCPClientBrowsingController/getMCPClientRoots` | getMCPClientRoots |

### `mcp-client-config-controller`
| Method | Path | Operation |
|---|---|---|
| DELETE | `/mcpclient/api/admin/McpClientConfigController/deleteMCPClientConfig` | deleteMCPClientConfig |
| GET | `/mcpclient/api/admin/McpClientConfigController/findMCPClientConfigByCode` | findMCPClientConfigByCode |
| POST | `/mcpclient/api/admin/McpClientConfigController/findMCPClientConfigByQbe` | findMCPClientConfigByQbe |
| POST | `/mcpclient/api/admin/McpClientConfigController/insertMCPClientConfig` | insertMCPClientConfig |
| POST | `/mcpclient/api/admin/McpClientConfigController/listMCPClientConfig` | listMCPClientConfig |
| POST | `/mcpclient/api/admin/McpClientConfigController/testAndDiscovery` | testAndDiscovery |
| POST | `/mcpclient/api/admin/McpClientConfigController/updateMCPClientConfig` | updateMCPClientConfig |

### `mcp-client-systems-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/mcpclient/api/admin/MCPClientSystemsController/deleteMCPClientEndpoint` | deleteMCPClientEndpoint |
| GET | `/mcpclient/api/admin/MCPClientSystemsController/findMCPClientEndpointsByCode` | findMCPClientEndpointsByCode |
| GET | `/mcpclient/api/admin/MCPClientSystemsController/findMCPClientEndpointsByProject` | findMCPClientEndpointsByProject |
| POST | `/mcpclient/api/admin/MCPClientSystemsController/findMCPClientEndpointsByQbe` | findMCPClientEndpointsByQbe |
| GET | `/mcpclient/api/admin/MCPClientSystemsController/getMCPClientSystemType` | getMCPClientSystemType |
| POST | `/mcpclient/api/admin/MCPClientSystemsController/insertMCPClientEndpoint` | insertMCPClientEndpoint |
| POST | `/mcpclient/api/admin/MCPClientSystemsController/publishMCPClientEndpoint` | publishMCPClientEndpoint |
| POST | `/mcpclient/api/admin/MCPClientSystemsController/updateMCPClientEndpoint` | updateMCPClientEndpoint |

## integration.gebo.ai — port 13015 (`integration-gebo-ai`) — context-path `/integration`

7 controller(s), 19 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/integration/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/integration/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/integration/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/integration/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/integration/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/integration/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/integration/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `integration-input-controller`
| Method | Path | Operation |
|---|---|---|
| PUT | `/integration/api/application/IntegrationInputController/publishContents` | publishContents |
| GET | `/integration/api/application/IntegrationInputController/publishSync` | publishSync |
| POST | `/integration/api/application/IntegrationInputController/spoolDocument` | spoolDocument |
| PUT | `/integration/api/application/IntegrationInputController/spoolDocument` | spoolDocument_1 |

### `integration-systems-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/integration/api/admin/IntegrationSystemsController/deleteIntegrationProjectEndpoint` | deleteIntegrationProjectEndpoint |
| GET | `/integration/api/admin/IntegrationSystemsController/findIntegrationEndpointsByProject` | findIntegrationEndpointsByProject |
| POST | `/integration/api/admin/IntegrationSystemsController/insertIntegrationProjectEndpoint` | insertIntegrationProjectEndpoint |
| POST | `/integration/api/admin/IntegrationSystemsController/publishIntegrationProjectEndpoint` | publishIntegrationProjectEndpoint |
| POST | `/integration/api/admin/IntegrationSystemsController/updateIntegrationProjectEndpoint` | updateIntegrationProjectEndpoint |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/integration/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/integration/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/integration/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

## fulltextor.gebo.ai — port 13016 (`fulltextor-gebo-ai`)

_No spec captured — service was not reachable when this doc was generated._


## eureka.gebo.ai — port 13017

_The Eureka **registry** itself; it is not a `swagger-on` service and exposes no `/v3/api-docs` — this is the registry dashboard/REST API (`/eureka/apps`), not a Gebo controller._


## heimdall.gebo.ai — port 13018 (`heimdall-gebo-ai`) — context-path `/heimdall`

14 controller(s), 55 endpoint(s):


### `auth-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/heimdall/auth/login` | authenticateUser |

### `auth-providers-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/heimdall/public/AuthProvidersController/getProviderClientConfig` | getProviderClientConfig |
| GET | `/heimdall/public/AuthProvidersController/listAuthProviders` | listAuthProviders |
| GET | `/heimdall/public/AuthProvidersController/listAvailableProvidersConfig` | listAvailableProvidersConfig |

### `gebo-advanced-setup-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/heimdall/api/admin/GeboAdvancedSetupStatusController/getFirstKnowledgeBaseSetupStatus` | getFirstKnowledgeBaseSetupStatus |
| GET | `/heimdall/api/admin/GeboAdvancedSetupStatusController/getMinimalContentsSetupStatus` | getMinimalContentsSetupStatus |

### `gebo-fast-installation-setup-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/heimdall/public/GeboFastSetupController/createSetup` | createSetup |
| GET | `/heimdall/public/GeboFastSetupController/getInstallationStatus` | getInstallationStatus |

### `gebo-fast-work-folder-setup-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/heimdall/api/admin/GeboFastWorkFolderSetupController/configureWorkDirectory` | configureWorkDirectory |
| GET | `/heimdall/api/admin/GeboFastWorkFolderSetupController/getWorkDirectorySetupEnabled` | getWorkDirectorySetupEnabled |
| GET | `/heimdall/api/admin/GeboFastWorkFolderSetupController/getWorkDirectorySetupStatus` | getWorkDirectorySetupStatus |

### `generated-admin-api-key-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/heimdall/api/admin/GeneratedAdminApiKeyController/deleteAdminGeneratedApiKey` | deleteAdminGeneratedApiKey |
| POST | `/heimdall/api/admin/GeneratedAdminApiKeyController/generateAdminGeneratedApiKey` | generateAdminGeneratedApiKey |
| POST | `/heimdall/api/admin/GeneratedAdminApiKeyController/getAdminGeneratedApiKeyPagedList` | getAdminGeneratedApiKeyPagedList |
| GET | `/heimdall/api/admin/GeneratedAdminApiKeyController/isAdminGeneratedApiKeyGenerationAllowed` | isAdminGeneratedApiKeyGenerationAllowed |

### `generated-user-api-key-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/heimdall/api/users/GeneratedUserApiKeyController/deleteUserGeneratedApiKey` | deleteUserGeneratedApiKey |
| POST | `/heimdall/api/users/GeneratedUserApiKeyController/generateUserGeneratedApiKey` | generateUserGeneratedApiKey |
| POST | `/heimdall/api/users/GeneratedUserApiKeyController/getUserGeneratedApiKeyPagedList` | getUserGeneratedApiKeyPagedList |
| GET | `/heimdall/api/users/GeneratedUserApiKeyController/isUserGeneratedApiKeyGenerationAllowed` | isUserGeneratedApiKeyGenerationAllowed |

### `o-auth-2-admin-controller`
| Method | Path | Operation |
|---|---|---|
| DELETE | `/heimdall/api/admin/OAuth2AdminController/deleteOauth2ProviderRegistration` | deleteOauth2ProviderRegistration |
| GET | `/heimdall/api/admin/OAuth2AdminController/findOauth2ProviderRegistrationByRegistrationId` | findOauth2ProviderRegistrationByRegistrationId |
| GET | `/heimdall/api/admin/OAuth2AdminController/getProviders` | getProviders |
| POST | `/heimdall/api/admin/OAuth2AdminController/insertOauth2ProviderRegistration` | insertOauth2ProviderRegistration |
| POST | `/heimdall/api/admin/OAuth2AdminController/updateOauth2ProviderRegistration` | updateOauth2ProviderRegistration |

### `oauth-2-module-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/heimdall/api/admin/Oauth2ModuleStatusController` | getStatus |

### `secrets-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/heimdall/api/admin/SecretsController/createAWSConnectionSecret` | createAWSConnectionSecret |
| POST | `/heimdall/api/admin/SecretsController/createCustomSecret` | createCustomSecret |
| POST | `/heimdall/api/admin/SecretsController/createGoogleJsonCredentialsSecret` | createGoogleJsonCredentialsSecret |
| POST | `/heimdall/api/admin/SecretsController/createGoogleOauth2Secret` | createGoogleOauth2Secret |
| POST | `/heimdall/api/admin/SecretsController/createOauth2StandardSecret` | createOauth2StandardSecret |
| POST | `/heimdall/api/admin/SecretsController/createSshKeySecret` | createSshKeySecret |
| POST | `/heimdall/api/admin/SecretsController/createTokenSecret` | createTokenSecret |
| POST | `/heimdall/api/admin/SecretsController/createUsernamePasswordSecret` | createUsernamePasswordSecret |
| DELETE | `/heimdall/api/admin/SecretsController/deleteSecret` | deleteSecret |
| GET | `/heimdall/api/admin/SecretsController/getSecretsByContextCode` | getSecretsByContextCode |

### `token-renew-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/heimdall/api/users/TokenRenewController/renew` | renew |

### `user-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/heimdall/api/users/ActualUserController/changePassword` | changePassword |
| GET | `/heimdall/api/users/ActualUserController/getMyGroups` | getMyGroups |
| GET | `/heimdall/api/users/ActualUserController/me` | getCurrentUser |

### `user-workflows-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/heimdall/public/UserWorkflowsController/getUserWorkflowsConfig` | getUserWorkflowsConfig |
| POST | `/heimdall/public/UserWorkflowsController/startUserWorkflow` | startUserWorkflow |
| POST | `/heimdall/public/UserWorkflowsController/userChangePasswordWithTicket` | userChangePasswordWithTicket |

### `users-admin-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/heimdall/api/admin/UsersAdminController/changeUserPassword` | changeUserPassword |
| POST | `/heimdall/api/admin/UsersAdminController/deleteGroup` | deleteGroup |
| POST | `/heimdall/api/admin/UsersAdminController/deleteUser` | deleteUser |
| GET | `/heimdall/api/admin/UsersAdminController/findGroupByCode` | findGroupByCode |
| POST | `/heimdall/api/admin/UsersAdminController/findUserByQbe` | findUserByQbe |
| GET | `/heimdall/api/admin/UsersAdminController/findUserByUsername` | findUserByUsername |
| POST | `/heimdall/api/admin/UsersAdminController/findUsersGroupByQbe` | findUsersGroupByQbe |
| GET | `/heimdall/api/admin/UsersAdminController/getAllGroups` | getAllGroups |
| GET | `/heimdall/api/admin/UsersAdminController/getAllUsers` | getAllUsers |
| POST | `/heimdall/api/admin/UsersAdminController/insertGroup` | insertGroup |
| POST | `/heimdall/api/admin/UsersAdminController/insertUser` | insertUser |
| POST | `/heimdall/api/admin/UsersAdminController/updateGroup` | updateGroup |
| POST | `/heimdall/api/admin/UsersAdminController/updateUser` | updateUser |

## tyr.gebo.ai — port 13019 (`tyr-gebo-ai`) — context-path `/tyr`

5 controller(s), 6 endpoint(s):


### `job-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/tyr/api/admin/JobStatusController/getJobStatus` | getJobStatus |
| GET | `/tyr/api/admin/JobStatusController/getJobSummary` | getJobSummary |

### `llms-usage-admin-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/tyr/api/admin/LLMSUsageAdminLevelController/drillDown` | adminDrillDown |

### `llms-usage-user-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/tyr/api/users/LLMSUsageUserLevelController/drillDown` | userDrillDown |

### `workflow-participants-enablement-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/tyr/api/users/WorkflowParticipantsEnablementController/enabledSteps` | enabledSteps |

### `workflow-stats-admin-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/tyr/api/admin/WorkflowStatsAdminLevelController/drillDown` | workflowDrillDown |
