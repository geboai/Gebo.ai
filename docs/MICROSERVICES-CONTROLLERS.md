# Microservices — Controllers by Service

Generated from each running microservice's live `/v3/api-docs` (springdoc), after building every image with `-P docker,swagger-on` and bringing up `dockers/gebo.microservices/docker-compose.yml`. One section per service; controllers are grouped by their springdoc `tag`, which springdoc derives 1:1 from the `@RestController` class name (kebab-case).


## Summary

| Service | Port | Controllers | Endpoints |
|---|---|---|---|
| gateway.gebo.ai | 13000 | 0 | 0 |
| brain.gebo.ai | 13001 | 57 | 226 |
| vectorizator.gebo.ai | 13002 | 9 | 17 |
| graphicator.gebo.ai | 13003 | 8 | 15 |
| chunker.gebo.ai | 13004 | 4 | 16 |
| git.gebo.ai | 13005 | 8 | 25 |
| filesystem.gebo.ai | 13006 | 10 | 33 |
| uploads.gebo.ai | 13007 | 9 | 24 |
| userspace.gebo.ai | 13008 | 9 | 32 |
| sharepoint.gebo.ai | 13009 | 9 | 31 |
| confluence.gebo.ai | 13010 | 9 | 31 |
| jira.gebo.ai | 13011 | 9 | 31 |
| aws-s3.gebo.ai | 13012 | 9 | 29 |
| googledrive.gebo.ai | 13013 | 10 | 32 |
| mcpclient.gebo.ai | 13014 | 10 | 31 |
| integration.gebo.ai | 13015 | 9 | 22 |
| fulltextor.gebo.ai | 13016 | 7 | 13 |
| heimdall.gebo.ai | 13018 | 14 | 55 |

**LLM controllers-review note:** the concrete, mapped LLM admin controllers (`ChatModelsController`, `EmbeddingModelsControllers`, `ImageModelsController`, `RankerModelsController`, `TextToSpeechModelsController`, `TranscriptModelsController`, `ChatModelsLookupController`, `FunctionsLookupController`, previously carried directly inside `gebo.architecture.llms.abstraction.layer`) now live in a sibling `gebo.architecture.llms.abstraction.layer.controllers` module, wired only into `gebo.apps.monolithic.starter` and `brain.gebo.ai`. Each LLM provider driver (openai, mistral, generic-openai-compatible, ollama, onxx-embeddings, anthropic3, google_vertex, deepseek, aws-bedrock) got the same treatment: its admin controllers moved to a `<provider>.controllers` sibling module, aggregated by the new `gebo.llms.controllers.starter`, wired the same way (monolith + brain only). **Effect:** vectorizator and graphicator, which previously exposed these same LLM admin controllers as a side effect of depending on `gebo.microservices.llms.starter`, no longer do — brain is now the sole microservice hosting LLM configuration admin, and it additionally gained every provider-specific admin controller for the first time (previously only the monolith had them, via `gebo.llms.starter`).


## gateway.gebo.ai — port 13000 (`gateway-gebo-ai`)

_Gateway routes to backends via `lb://`; it hosts no controllers of its own — its own `/v3/api-docs` is empty by design._


## brain.gebo.ai — port 13001 (`brain-gebo-ai`)

57 controller(s), 226 endpoint(s):


### `anthropic-chat-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/AnthropicChatModelsConfigurationController/deleteAnthropicChatModelConfig` | deleteAnthropicChatModelConfig |
| GET | `/api/admin/AnthropicChatModelsConfigurationController/findAnthropicChatModelConfigByCode` | findAnthropicChatModelConfigByCode |
| POST | `/api/admin/AnthropicChatModelsConfigurationController/getAnthropicModels` | getAnthropicChatModels |
| POST | `/api/admin/AnthropicChatModelsConfigurationController/insertAnthropicChatModelConfig` | insertAnthropicChatModelConfig |
| POST | `/api/admin/AnthropicChatModelsConfigurationController/updateAnthropicChatModelConfig` | updateAnthropicChatModelConfig |

### `chat-models-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/ChatModelsController/getChatModelTypes` | getChatModelTypes |
| GET | `/api/admin/ChatModelsController/getRuntimeConfiguredChatModels` | getRuntimeConfiguredChatModels |

### `chat-models-lookup-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/ChatModelsLookupController/getChatModelTypesLookup` | getChatModelTypesLookup |
| GET | `/api/users/ChatModelsLookupController/getDefaultChatModel` | getDefaultChatModel |
| GET | `/api/users/ChatModelsLookupController/getRuntimeConfiguredChatModelsLookup` | getRuntimeConfiguredChatModelsLookup |

### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `embedding-models-controllers`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/EmbeddingModelsControllers/getEmbeddingModelTypes` | getEmbeddingModelTypes |
| GET | `/api/admin/EmbeddingModelsControllers/getRuntimeConfiguredEmbeddingModels` | getRuntimeConfiguredEmbeddingModels |

### `functions-lookup-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/FunctionsLookupController/getAllFunctions` | getAllFunctions |
| GET | `/api/admin/FunctionsLookupController/getAllFunctionsTree` | getAllFunctionsTree |
| GET | `/api/admin/FunctionsLookupController/getAllLocalFunctions` | getAllLocalFunctions |
| GET | `/api/admin/FunctionsLookupController/getAllLocalFunctionsTree` | getAllLocalFunctionsTree |

### `gebo-admin-chat-profiles-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GeboAdminChatProfilesConfigurationController/deleteChatProfile` | deleteChatProfile |
| GET | `/api/admin/GeboAdminChatProfilesConfigurationController/findChatProfileConfigurationByCode` | findChatProfileConfigurationByCode |
| POST | `/api/admin/GeboAdminChatProfilesConfigurationController/getAllChatProfileConfiguration` | getAllChatProfileConfiguration |
| POST | `/api/admin/GeboAdminChatProfilesConfigurationController/getChatProfileConfigurationByQbe` | getChatProfileConfigurationByQbe |
| POST | `/api/admin/GeboAdminChatProfilesConfigurationController/insertChatProfile` | insertChatProfile |
| POST | `/api/admin/GeboAdminChatProfilesConfigurationController/updateChatProfile` | updateChatProfile |

### `gebo-admin-prompt-use-info-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/GeboAdminPromptUseController/findAll` | findAll |
| GET | `/api/admin/GeboAdminPromptUseController/findByCode` | findByCode |
| GET | `/api/admin/GeboAdminPromptUseController/findByModule` | findByModule |

### `gebo-admin-prompts-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GeboAdminPromptsController/deletePromptConfig` | deletePromptConfig |
| GET | `/api/admin/GeboAdminPromptsController/findPromptConfigByCode` | findPromptConfigByCode |
| GET | `/api/admin/GeboAdminPromptsController/getPromptCategories` | getPromptCategories |
| POST | `/api/admin/GeboAdminPromptsController/getPromptConfigByFilter` | getPromptConfigByFilter |
| POST | `/api/admin/GeboAdminPromptsController/insertPromptConfig` | insertPromptConfig |
| POST | `/api/admin/GeboAdminPromptsController/updatePromptConfig` | updatePromptConfig |

### `gebo-admin-rag-autotune-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/GeboAdminRagAutotuneController/getLatestComputedVectorStores` | getLatestComputedVectorStores |

### `gebo-advanced-setup-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/GeboAdvancedSetupStatusController/getFirstKnowledgeBaseSetupStatus` | getFirstKnowledgeBaseSetupStatus |
| GET | `/api/admin/GeboAdvancedSetupStatusController/getMinimalContentsSetupStatus` | getMinimalContentsSetupStatus |

### `gebo-agent-admin-controller`
| Method | Path | Operation |
|---|---|---|
| DELETE | `/api/admin/GeboAgentAdminController/deleteAgent` | deleteAgent |
| GET | `/api/admin/GeboAgentAdminController/getAgentByCode` | getAgentByCode |
| GET | `/api/admin/GeboAgentAdminController/getAgents` | getAgents |
| GET | `/api/admin/GeboAgentAdminController/getAgentsChoices` | getAgentsChoices |
| GET | `/api/admin/GeboAgentAdminController/getPromptTemplateByAgentId` | getPromptTemplatesByAgentId |
| POST | `/api/admin/GeboAgentAdminController/insertAgent` | insertAgent |
| POST | `/api/admin/GeboAgentAdminController/updateAgent` | updateAgent |

### `gebo-agents-network-admin-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GeboAgentsNetworkAdminController/deleteAgentsNetwork` | deleteAgentsNetwork |
| GET | `/api/admin/GeboAgentsNetworkAdminController/getAgentConfigs` | getAgentConfigs |
| GET | `/api/admin/GeboAgentsNetworkAdminController/getAgentConfigsByServiceId` | getAgentConfigsByServiceId |
| GET | `/api/admin/GeboAgentsNetworkAdminController/getAgentServices` | getAgentServices |
| GET | `/api/admin/GeboAgentsNetworkAdminController/getAgentsNetwork` | getAgentsNetwork |
| GET | `/api/admin/GeboAgentsNetworkAdminController/getAgentsNetworkByCode` | getAgentsNetworkByCode |
| GET | `/api/admin/GeboAgentsNetworkAdminController/getCompatibleNextServices` | getCompatibleNextServices |
| GET | `/api/admin/GeboAgentsNetworkAdminController/getCompatiblePreviousServices` | getCompatiblePreviousServices |
| GET | `/api/admin/GeboAgentsNetworkAdminController/getNetworkAdapterServices` | getNetworkAdapterServices |
| POST | `/api/admin/GeboAgentsNetworkAdminController/insertAgentsNetwork` | insertAgentsNetwork |
| POST | `/api/admin/GeboAgentsNetworkAdminController/updateAgentsNetwork` | updateAgentsNetwork |
| POST | `/api/admin/GeboAgentsNetworkAdminController/validateAgentsNetwork` | validateAgentsNetwork |

### `gebo-chat-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/GeboDirectModelChatController/chat` | chat |
| GET | `/api/users/GeboDirectModelChatController/getChatModelMetaInfos` | getChatModelMetaInfos |
| GET | `/api/users/GeboDirectModelChatController/getChatModelUserInfo` | getChatModelUserInfo |
| GET | `/api/users/GeboDirectModelChatController/getProviderCapabilities` | getProviderCapabilities |
| GET | `/api/users/GeboDirectModelChatController/getVisibleKnowledgeBases` | getVisibleKnowledgeBases |
| POST | `/api/users/GeboDirectModelChatController/streamResponse` | streamResponse |

### `gebo-chat-pipelines-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/GeboChatPipelinesController/defaultPersonalPipelinesChatMenu` | getDefaultPersonalPipelinesChatMenu |
| POST | `/api/users/GeboChatPipelinesController/executeChatPipeline` | executeChatPipeline |
| POST | `/api/users/GeboChatPipelinesController/executeDefaultChatPipeline` | executeDefaultChatPipeline |
| GET | `/api/users/GeboChatPipelinesController/personalPipelinesChatMenu` | getPersonalPipelinesChatMenu |
| GET | `/api/users/GeboChatPipelinesController/stopChatPipeline` | stopChatPipeline |
| POST | `/api/users/GeboChatPipelinesController/streamChatPipeline` | streamChatPipeline |
| POST | `/api/users/GeboChatPipelinesController/streamDefaultChatPipeline` | streamDefaultChatPipeline |

### `gebo-chat-profile-lookup-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/GeboChatProfileLookupController/findChatProfileConfigurationLookupByCode` | findChatProfileConfigurationLookupByCode |
| POST | `/api/users/GeboChatProfileLookupController/getAllChatProfileConfigurationLoookup` | getAllChatProfileConfigurationLoookup |
| POST | `/api/users/GeboChatProfileLookupController/getChatProfileConfigurationLookupByQbe` | getChatProfileConfigurationLookupByQbe |

### `gebo-deep-search-admin-controller`
| Method | Path | Operation |
|---|---|---|
| DELETE | `/api/admin/GeboDeepSearchAdminController/deleteDeepSearchConfig` | deleteDeepSearchConfig |
| GET | `/api/admin/GeboDeepSearchAdminController/getConfigurableDataSources` | getConfigurableDataSources |
| GET | `/api/admin/GeboDeepSearchAdminController/getDeepSeachConfigs` | getDeepSeachConfigs |
| GET | `/api/admin/GeboDeepSearchAdminController/getDeepSearchDefaultConfig` | getDeepSearchDefaultConfig |
| GET | `/api/admin/GeboDeepSearchAdminController/getDeepSearchDefaultOrSystemConfig` | getDeepSearchDefaultOrSystemConfig |
| GET | `/api/admin/GeboDeepSearchAdminController/getDeepSearchSystemConfig` | getDeepSearchSystemConfig |
| POST | `/api/admin/GeboDeepSearchAdminController/insertDeepSearchConfig` | insertDeepSearchConfig |
| POST | `/api/admin/GeboDeepSearchAdminController/updateDeepSearchConfig` | updateDeepSearchConfig |

### `gebo-deep-search-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/GeboDeepSearchController/getDeepSearchDataSources` | getDeepSearchDataSources |

### `gebo-fast-chat-profile-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/GeboFastChatProfileStatusController/getChatProfilesSetupStatus` | getChatProfilesSetupStatus |

### `gebo-fast-knowledge-base-setup-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/GeboFastKnowledgeBaseSetupController/getCompleteKnowledgeBaseSetupStatus` | getCompleteKnowledgeBaseSetupStatus |
| GET | `/api/admin/GeboFastKnowledgeBaseSetupController/getContentProcessRows` | getContentProcessRows |

### `gebo-fast-llms-setup-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GeboFastLLMSSetupController/createLLMByAutoconfigure` | createLLMByAutoconfigure |
| POST | `/api/admin/GeboFastLLMSSetupController/createLLMCredentials` | createLLMCredentials |
| POST | `/api/admin/GeboFastLLMSSetupController/createLLMS` | createLLMS |
| GET | `/api/admin/GeboFastLLMSSetupController/getActualLLMSConfiguration` | getActualLLMSConfiguration |
| GET | `/api/admin/GeboFastLLMSSetupController/getLLMSSetupStatus` | getLLMSSetupStatus |
| POST | `/api/admin/GeboFastLLMSSetupController/verifyCredentialsAndDownloadModels` | verifyCredentialsAndDownloadModels |
| POST | `/api/admin/GeboFastLLMSSetupController/verifyVendorCredentialsAndDownloadModels` | verifyVendorCredentialsAndDownloadModels |

### `gebo-fast-vector-store-setup-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GeboFastVectorStoreSetupController/createVectorStoreConfiguration` | createVectorStoreConfiguration |
| GET | `/api/admin/GeboFastVectorStoreSetupController/getVectorStoreStatus` | getVectorStoreStatus |

### `gebo-llm-generated-resource-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/GeboLLMGeneratedResourceController/serveLLMGeneratedContent/{userSessionCode}/{generatedResourceCode}` | serveLLMGeneratedContent |

### `gebo-rag-chat-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/GeboChatController/getChatModelUserInfoByChatProfileCode` | getChatModelUserInfoByChatProfileCode |
| GET | `/api/users/GeboChatController/getChatProfileModelMetaInfos` | getChatProfileModelMetaInfos |
| GET | `/api/users/GeboChatController/getProfileProviderModelCapabilities` | getProfileProviderModelCapabilities |
| GET | `/api/users/GeboChatController/getVisibleKnowledgeBasesByProfileCode` | getVisibleKnowledgeBasesByProfileCode |
| GET | `/api/users/GeboChatController/profiles` | getChatProfiles |
| POST | `/api/users/GeboChatController/ragChat` | ragChat |
| POST | `/api/users/GeboChatController/streamRagResponse` | streamRagResponse |

### `gebo-text-to-speech-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/GeboTextToSpeechController/isEnabled` | isEnabled_1 |
| POST | `/api/users/GeboTextToSpeechController/speechText` | speechText |

### `gebo-transcript-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/GeboTranscriptController/isEnabled` | isEnabled |
| POST | `/api/users/GeboTranscriptController/transcriptText` | transcriptText |

### `gebo-user-chat-uploads-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/GeboUserChatUploadsController/chatSessionUpload/{userSessionCode}` | chatSessionUpload |
| DELETE | `/api/users/GeboUserChatUploadsController/deleteSessionUploads` | deleteSessionUploads |
| GET | `/api/users/GeboUserChatUploadsController/serveContent/{userSessionCode}/{uploadedContentId}` | serveContent |

### `gebo-user-chats-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/GeboUserChatsController/changeChatDescription` | changeChatDescription |
| GET | `/api/users/GeboUserChatsController/createCleanChatByChatProfileCode` | createCleanChatByChatProfileCode |
| GET | `/api/users/GeboUserChatsController/createCleanChatByModelCode` | createCleanChatByModelCode |
| DELETE | `/api/users/GeboUserChatsController/deleteChat` | deleteChat |
| GET | `/api/users/GeboUserChatsController/exportResponse2file` | exportResponse2file |
| GET | `/api/users/GeboUserChatsController/getChatHistory` | getChatHistory |
| GET | `/api/users/GeboUserChatsController/getChatInfosByCode` | getChatInfosByCode |
| POST | `/api/users/GeboUserChatsController/getChatInfosByQbe` | getChatInfosByQbe |
| GET | `/api/users/GeboUserChatsController/getMyChats` | getMyChats |
| GET | `/api/users/GeboUserChatsController/getMyChatsPaged` | getMyChatsPaged |
| GET | `/api/users/GeboUserChatsController/getUIConfig` | getUIConfig |
| GET | `/api/users/GeboUserChatsController/suggestChatDescription` | suggestChatDescription |

### `gebo-user-knowledge-base-semantic-search-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/GeboUserKnowledgeBaseSemanticSearchController/semanticSearch` | semanticSearch |

### `gebo-vector-store-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/GeboVectorStoreConfigurationController/getActualVectorStoreConfiguration` | getActualVectorStoreConfiguration |
| POST | `/api/admin/GeboVectorStoreConfigurationController/vectorStoreConfigurationApplyAndSave` | vectorStoreConfigurationApplyAndSave |

### `generic-open-ai-ranker-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GenerigOpenAIRankerModelsConfigurationController/deleteGenericOpenAIAPIRankerModelConfig` | deleteGenericOpenAIAPIRankerModelConfig |
| GET | `/api/admin/GenerigOpenAIRankerModelsConfigurationController/findGenericOpenAIAPIRankerModelConfigByCode` | findGenericOpenAIAPIRankerModelConfigByCode |
| POST | `/api/admin/GenerigOpenAIRankerModelsConfigurationController/getGenericOpenAIAPIRankerModels` | getGenericOpenAIAPIRankerModels |
| GET | `/api/admin/GenerigOpenAIRankerModelsConfigurationController/getGenericOpenAIRankerModelConfigs` | getGenericOpenAIRankerModelConfigs |
| GET | `/api/admin/GenerigOpenAIRankerModelsConfigurationController/getGenericOpenAIRankerModelTypes` | getGenericOpenAIRankerModelTypes |
| POST | `/api/admin/GenerigOpenAIRankerModelsConfigurationController/insertGenericOpenAIAPIRankerModelConfig` | insertGenericOpenAIAPIRankerModelConfig |
| POST | `/api/admin/GenerigOpenAIRankerModelsConfigurationController/updateGenericOpenAIAPIRankerModelConfig` | updateGenericOpenAIAPIRankerModelConfig |

### `generic-open-aiapi-chat-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GenericOpenAIAPIChatModelsConfigurationController/deleteGenericOpenAIAPIChatModelConfig` | deleteGenericOpenAIAPIChatModelConfig |
| GET | `/api/admin/GenericOpenAIAPIChatModelsConfigurationController/findGenericOpenAIAPIChatModelConfigByCode` | findGenericOpenAIAPIChatModelConfigByCode |
| POST | `/api/admin/GenericOpenAIAPIChatModelsConfigurationController/getGenericOpenAIAPIChatModels` | getGenericOpenAIAPIChatModels |
| GET | `/api/admin/GenericOpenAIAPIChatModelsConfigurationController/getGenericOpenAIChatModelTypes` | getGenericOpenAIChatModelTypes |
| POST | `/api/admin/GenericOpenAIAPIChatModelsConfigurationController/insertGenericOpenAIAPIChatModelConfig` | insertGenericOpenAIAPIChatModelConfig |
| POST | `/api/admin/GenericOpenAIAPIChatModelsConfigurationController/updateGenericOpenAIAPIChatModelConfig` | updateGenericOpenAIAPIChatModelConfig |

### `generic-open-aiapi-embedding-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/deleteGenericOpenAIAPIEmbeddingModelConfig` | deleteGenericOpenAIAPIEmbeddingModelConfig |
| GET | `/api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/findGenericOpenAIAPIEmbeddingModelConfigByCode` | findGenericOpenAIAPIEmbeddingModelConfigByCode |
| POST | `/api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/getGenericOpenAIAPIEmbeddingModels` | getGenericOpenAIAPIEmbeddingModels |
| GET | `/api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/getGenericOpenAIEmbeddingModelTypes` | getGenericOpenAIEmbeddingModelTypes |
| POST | `/api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/insertGenericOpenAIAPIEmbeddingModelConfig` | insertGenericOpenAIAPIEmbeddingModelConfig |
| POST | `/api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/updateGenericOpenAIAPIEmbeddingModelConfig` | updateGenericOpenAIAPIEmbeddingModelConfig |

### `generic-open-aiapi-image-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GenericOpenAIAPIImageModelsConfigurationController/deleteGenericOpenAIAPIImageModelConfig` | deleteGenericOpenAIAPIImageModelConfig |
| GET | `/api/admin/GenericOpenAIAPIImageModelsConfigurationController/findGenericOpenAIAPIImageModelConfigByCode` | findGenericOpenAIAPIImageModelConfigByCode |
| POST | `/api/admin/GenericOpenAIAPIImageModelsConfigurationController/getGenericOpenAIAPIImageModels` | getGenericOpenAIAPIImageModels |
| GET | `/api/admin/GenericOpenAIAPIImageModelsConfigurationController/getGenericOpenAIImageModelConfigs` | getGenericOpenAIImageModelConfigs |
| GET | `/api/admin/GenericOpenAIAPIImageModelsConfigurationController/getGenericOpenAIImageModelTypes` | getGenericOpenAIImageModelTypes |
| POST | `/api/admin/GenericOpenAIAPIImageModelsConfigurationController/insertGenericOpenAIAPIImageModelConfig` | insertGenericOpenAIAPIImageModelConfig |
| POST | `/api/admin/GenericOpenAIAPIImageModelsConfigurationController/updateGenericOpenAIAPIImageModelConfig` | updateGenericOpenAIAPIImageModelConfig |

### `generic-open-aiapi-text-to-speech-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/deleteGenericOpenAIAPITextToSpeechModelConfig` | deleteGenericOpenAIAPITextToSpeechModelConfig |
| GET | `/api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/findGenericOpenAIAPITextToSpeechModelConfigByCode` | findGenericOpenAIAPITextToSpeechModelConfigByCode |
| POST | `/api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/getGenericOpenAIAPITextToSpeechModels` | getGenericOpenAIAPITextToSpeechModels |
| GET | `/api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/getGenericOpenAITextToSpeechModelConfigs` | getGenericOpenAITextToSpeechModelConfigs |
| GET | `/api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/getGenericOpenAITextToSpeechModelTypes` | getGenericOpenAITextToSpeechModelTypes |
| POST | `/api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/insertGenericOpenAIAPITextToSpeechModelConfig` | insertGenericOpenAIAPITextToSpeechModelConfig |
| POST | `/api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/updateGenericOpenAIAPITextToSpeechModelConfig` | updateGenericOpenAIAPITextToSpeechModelConfig |

### `generic-open-aiapi-transcript-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/deleteGenericOpenAIAPITranscriptModelConfig` | deleteGenericOpenAIAPITranscriptModelConfig |
| GET | `/api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/findGenericOpenAIAPITranscriptModelConfigByCode` | findGenericOpenAIAPITranscriptModelConfigByCode |
| POST | `/api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/getGenericOpenAIAPITranscriptModels` | getGenericOpenAIAPITranscriptModels |
| GET | `/api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/getGenericOpenAITranscriptModelConfigs` | getGenericOpenAITranscriptModelConfigs |
| GET | `/api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/getGenericOpenAITranscriptModelTypes` | getGenericOpenAITranscriptModelTypes |
| POST | `/api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/insertGenericOpenAIAPITranscriptModelConfig` | insertGenericOpenAIAPITranscriptModelConfig |
| POST | `/api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/updateGenericOpenAIAPITranscriptModelConfig` | updateGenericOpenAIAPITranscriptModelConfig |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `image-models-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/ImageModelsController/getImageModelTypes` | getImageModelTypes |
| GET | `/api/admin/ImageModelsController/getRuntimeConfiguredImageModels` | getRuntimeConfiguredImageModels |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

### `job-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobStatusController/getJobStatus` | getJobStatus |
| GET | `/api/admin/JobStatusController/getJobSummary` | getJobSummary |

### `llms-usage-admin-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/LLMSUsageAdminLevelController/drillDown` | adminDrillDown |

### `llms-usage-user-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/LLMSUsageUserLevelController/drillDown` | userDrillDown |

### `ollama-chat-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/OllamaChatModelsConfigurationController/deleteOllamaChatModelConfig` | deleteOllamaChatModelConfig |
| GET | `/api/admin/OllamaChatModelsConfigurationController/findOllamaChatModelConfigByCode` | findOllamaChatModelConfigByCode |
| POST | `/api/admin/OllamaChatModelsConfigurationController/getOllamaModels` | getOllamaChatModels |
| POST | `/api/admin/OllamaChatModelsConfigurationController/insertOllamaChatModelConfig` | insertOllamaChatModelConfig |
| POST | `/api/admin/OllamaChatModelsConfigurationController/updateOllamaChatModelConfig` | updateOllamaChatModelConfig |

### `ollama-embedding-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/OllamaEmbeddingModelsConfigurationController/deleteOllamaEmbeddingModelConfig` | deleteOllamaEmbeddingModelConfig |
| GET | `/api/admin/OllamaEmbeddingModelsConfigurationController/findOllamaEmbeddingModelConfigByCode` | findOllamaEmbeddingModelConfigByCode |
| POST | `/api/admin/OllamaEmbeddingModelsConfigurationController/getOllamaEmbeddingModels` | getOllamaEmbeddingModels |
| POST | `/api/admin/OllamaEmbeddingModelsConfigurationController/insertOllamaEmbeddingModelConfig` | insertOllamaEmbeddingModelConfig |
| POST | `/api/admin/OllamaEmbeddingModelsConfigurationController/updateOllamaEmbeddingModelConfig` | updateOllamaEmbeddingModelConfig |

### `onnx-transformers-embedding-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/ONNXTransformersEmbeddingModelsConfigurationController/deleteONNXTransformersEmbeddingModelConfig` | deleteONNXTransformersEmbeddingModelConfig |
| GET | `/api/admin/ONNXTransformersEmbeddingModelsConfigurationController/findONNXTransformersEmbeddingModelConfigByCode` | findONNXTransformersEmbeddingModelConfigByCode |
| POST | `/api/admin/ONNXTransformersEmbeddingModelsConfigurationController/getONNXTransformersEmbeddingModels` | getONNXTransformersEmbeddingModels |
| POST | `/api/admin/ONNXTransformersEmbeddingModelsConfigurationController/insertONNXTransformersEmbeddingModelConfig` | insertONNXTransformersEmbeddingModelConfig |
| POST | `/api/admin/ONNXTransformersEmbeddingModelsConfigurationController/updateONNXTransformersEmbeddingModelConfig` | updateONNXTransformersEmbeddingModelConfig |

### `open-ai-chat-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/OpenAIModelsConfigurationController/deleteOpenAIChatModelConfig` | deleteOpenAIChatModelConfig |
| GET | `/api/admin/OpenAIModelsConfigurationController/findOpenAIChatModelConfigByCode` | findOpenAIChatModelConfigByCode |
| POST | `/api/admin/OpenAIModelsConfigurationController/getOpenAIChatModels` | getOpenAIChatModels |
| POST | `/api/admin/OpenAIModelsConfigurationController/insertOpenAIChatModelConfig` | insertOpenAIChatModelConfig |
| POST | `/api/admin/OpenAIModelsConfigurationController/updateOpenAIChatModelConfig` | updateOpenAIChatModelConfig |

### `open-ai-embedding-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/OpenAIEmbeddingModelsConfigurationController/deleteOpenAIEmbeddingModelConfig` | deleteOpenAIEmbeddingModelConfig |
| GET | `/api/admin/OpenAIEmbeddingModelsConfigurationController/findOpenAIEmbeddingModelConfigByCode` | findOpenAIEmbeddingModelConfigByCode |
| POST | `/api/admin/OpenAIEmbeddingModelsConfigurationController/getOpenAIEmbeddingModels` | getOpenAIEmbeddingModels |
| POST | `/api/admin/OpenAIEmbeddingModelsConfigurationController/insertOpenAIEmbeddingModelConfig` | insertOpenAIEmbeddingModelConfig |
| POST | `/api/admin/OpenAIEmbeddingModelsConfigurationController/updateOpenAIEmbeddingModelConfig` | updateOpenAIEmbeddingModelConfig |

### `open-ai-image-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/OpenAIImageModelsConfigurationController/deleteOpenAIImageModelConfig` | deleteOpenAIImageModelConfig |
| GET | `/api/admin/OpenAIImageModelsConfigurationController/findOpenAIImageModelConfigByCode` | findOpenAIImageModelConfigByCode |
| POST | `/api/admin/OpenAIImageModelsConfigurationController/getOpenAIImageModels` | getOpenAIImageModels |
| POST | `/api/admin/OpenAIImageModelsConfigurationController/insertOpenAIImageModelConfig` | insertOpenAIImageModelConfig |
| POST | `/api/admin/OpenAIImageModelsConfigurationController/updateOpenAIImageModelConfig` | updateOpenAIImageModelConfig |

### `open-ai-text-to-speech-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/OpenAITextToSpeechModelsConfigurationController/deleteOpenAITextToSpeechModelConfig` | deleteOpenAITextToSpeechModelConfig |
| GET | `/api/admin/OpenAITextToSpeechModelsConfigurationController/findOpenAITextToSpeechModelConfigByCode` | findOpenAITextToSpeechModelConfigByCode |
| POST | `/api/admin/OpenAITextToSpeechModelsConfigurationController/getOpenAITextToSpeechModels` | getOpenAITextToSpeechModels |
| POST | `/api/admin/OpenAITextToSpeechModelsConfigurationController/insertOpenAITextToSpeechModelConfig` | insertOpenAITextToSpeechModelConfig |
| POST | `/api/admin/OpenAITextToSpeechModelsConfigurationController/updateOpenAITextToSpeechModelConfig` | updateOpenAITextToSpeechModelConfig |

### `open-ai-transcript-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/OpenAITranscriptModelsConfigurationController/deleteOpenAITranscriptModelConfig` | deleteOpenAITranscriptModelConfig |
| GET | `/api/admin/OpenAITranscriptModelsConfigurationController/findOpenAITranscriptModelConfigByCode` | findOpenAITranscriptModelConfigByCode |
| POST | `/api/admin/OpenAITranscriptModelsConfigurationController/getOpenAITranscriptModels` | getOpenAITranscriptModels |
| POST | `/api/admin/OpenAITranscriptModelsConfigurationController/insertOpenAITranscriptModelConfig` | insertOpenAITranscriptModelConfig |
| POST | `/api/admin/OpenAITranscriptModelsConfigurationController/updateOpenAITranscriptModelConfig` | updateOpenAITranscriptModelConfig |

### `prompt-templates-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/PromptTemplatesController/getDefaultPrompt` | getDefaultPrompt |
| POST | `/api/admin/PromptTemplatesController/getDefaultPromptForChatModel` | getDefaultPromptForChatModel |
| POST | `/api/admin/PromptTemplatesController/getDefaultPromptForChatModelReference` | getDefaultPromptForChatModelReference |

### `ranker-models-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/RankerModelsController/getRankerModelTypes` | getRankerModelTypes |
| GET | `/api/admin/RankerModelsController/getRuntimeConfiguredRankerModels` | getRuntimeConfiguredRankerModels |

### `text-to-speech-models-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/TextToSpeechModelsController/getRuntimeConfiguredTextToSpeechModels` | getRuntimeConfiguredTextToSpeechModels |
| GET | `/api/admin/TextToSpeechModelsController/getTextToSpeechModelTypes` | getTextToSpeechModelTypes |

### `transcript-models-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/TranscriptModelsController/getRuntimeConfiguredTranscriptModels` | getRuntimeConfiguredTranscriptModels |
| GET | `/api/admin/TranscriptModelsController/getTranscriptModelTypes` | getTranscriptModelTypes |

### `workflow-stats-admin-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/WorkflowStatsAdminLevelController/drillDown` | workflowDrillDown |

## vectorizator.gebo.ai — port 13002 (`vectorizator-gebo-ai`)

9 controller(s), 17 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `gebo-core-analisys-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GeboCoreAnalisysController/drillDown` | coreDrillDown |
| GET | `/api/admin/GeboCoreAnalisysController/getTopLevelKnowledgeBaseCategory` | getTopLevelKnowledgeBaseCategory |

### `gebo-vector-store-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/GeboVectorStoreConfigurationController/getActualVectorStoreConfiguration` | getActualVectorStoreConfiguration |
| POST | `/api/admin/GeboVectorStoreConfigurationController/vectorStoreConfigurationApplyAndSave` | vectorStoreConfigurationApplyAndSave |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

### `job-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobStatusController/getJobStatus` | getJobStatus |
| GET | `/api/admin/JobStatusController/getJobSummary` | getJobSummary |

### `workflow-stats-admin-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/WorkflowStatsAdminLevelController/drillDown` | workflowDrillDown |

## graphicator.gebo.ai — port 13003 (`graphicator-gebo-ai`)

8 controller(s), 15 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `gebo-vector-store-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/GeboVectorStoreConfigurationController/getActualVectorStoreConfiguration` | getActualVectorStoreConfiguration |
| POST | `/api/admin/GeboVectorStoreConfigurationController/vectorStoreConfigurationApplyAndSave` | vectorStoreConfigurationApplyAndSave |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

### `job-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobStatusController/getJobStatus` | getJobStatus |
| GET | `/api/admin/JobStatusController/getJobSummary` | getJobSummary |

### `workflow-stats-admin-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/WorkflowStatsAdminLevelController/drillDown` | workflowDrillDown |

## chunker.gebo.ai — port 13004 (`chunker-gebo-ai`)

4 controller(s), 16 endpoint(s):


### `document-content-streamer-with-cache-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/DocumentContentStreamerWithCacheController/streamDocumentReference` | streamDocumentReference |
| POST | `/api/DocumentContentStreamerWithCacheController/streamSearchResult` | streamSearchResult |

### `documents-cache-service-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/DocumentsCacheServiceController/streamDocument` | streamDocument |

### `documents-chunk-service-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/DocumentsChunkServiceController/createChunkingSession` | createChunkingSession |
| POST | `/api/DocumentsChunkServiceController/disposeChunkingSession` | disposeChunkingSession |
| POST | `/api/DocumentsChunkServiceController/getCachedChunkSet` | getCachedChunkSet |
| POST | `/api/DocumentsChunkServiceController/getChunkSet` | getChunkSet |
| POST | `/api/DocumentsChunkServiceController/getNextChunkSet` | getNextChunkSet |
| POST | `/api/DocumentsChunkServiceController/prepareChunks` | prepareChunks |
| GET | `/api/DocumentsChunkServiceController/retrieveChunkingSession` | retrieveChunkingSession |
| POST | `/api/DocumentsChunkServiceController/streamChunks` | streamChunks |
| POST | `/api/DocumentsChunkServiceController/streamChunksBatch` | streamChunksBatch |
| POST | `/api/DocumentsChunkServiceController/streamChunksReactive` | streamChunksReactive |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

## git.gebo.ai — port 13005 (`git-gebo-ai`)

8 controller(s), 25 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `git-systems-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GITSystemsController/deleteGitEndpoint` | deleteGitEndpoint |
| POST | `/api/admin/GITSystemsController/deleteGitSystem` | deleteGitSystem |
| GET | `/api/admin/GITSystemsController/findGitEndpointsByProject` | findGitEndpointsByProject |
| POST | `/api/admin/GITSystemsController/findGitEndpointsByQbe` | findGitEndpointsByQbe |
| POST | `/api/admin/GITSystemsController/getBranchesList` | getBranchesList |
| GET | `/api/admin/GITSystemsController/getGitSystemTypes` | getGitSystemTypes |
| GET | `/api/admin/GITSystemsController/getGitSystems` | getGitSystems |
| POST | `/api/admin/GITSystemsController/insertGitEndpoint` | insertGitEndpoint |
| POST | `/api/admin/GITSystemsController/insertGitSystem` | insertGitSystem |
| POST | `/api/admin/GITSystemsController/publishGitEndpoint` | publishGitEndpoint |
| POST | `/api/admin/GITSystemsController/updateGitEndpoint` | updateGitEndpoint |
| POST | `/api/admin/GITSystemsController/updateGitSystem` | updateGitSystem |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

### `job-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobStatusController/getJobStatus` | getJobStatus |
| GET | `/api/admin/JobStatusController/getJobSummary` | getJobSummary |

### `workflow-stats-admin-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/WorkflowStatsAdminLevelController/drillDown` | workflowDrillDown |

## filesystem.gebo.ai — port 13006 (`filesystem-gebo-ai`)

10 controller(s), 33 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `file-system-shares-setting-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/FileSystemSharesSettingController/checkCanBeInsertedFileSystemShareReference` | checkCanBeInsertedFileSystemShareReference |
| POST | `/api/admin/FileSystemSharesSettingController/deleteFileSystemShareReference` | deleteFileSystemShareReference |
| GET | `/api/admin/FileSystemSharesSettingController/getFileSystemShareReferenceByCode` | getFileSystemShareReferenceByCode |
| POST | `/api/admin/FileSystemSharesSettingController/getGFileSystemNodeChildrens` | getGFileSystemNodeChildrens |
| POST | `/api/admin/FileSystemSharesSettingController/getGFileSystemNodeNavigationStatus` | getGFileSystemNodeNavigationStatus |
| GET | `/api/admin/FileSystemSharesSettingController/getRootGFileSystemNodes` | getRootGFileSystemNodes |
| GET | `/api/admin/FileSystemSharesSettingController/getSharedFileSystemsActualConfiguration` | getSharedFileSystemsActualConfiguration |
| POST | `/api/admin/FileSystemSharesSettingController/getUsedFilesystemShares` | getUsedFilesystemShares |
| POST | `/api/admin/FileSystemSharesSettingController/insertFileSystemShareReference` | insertFileSystemShareReference |

### `file-systems-browsing-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/FileSystemsBrowsingController/browseSharedFilesystemRootsPath` | browseSharedFilesystemRootsPath |
| POST | `/api/admin/FileSystemsBrowsingController/getSharedFilesystemNavigationStatus` | getSharedFilesystemNavigationStatus |
| GET | `/api/admin/FileSystemsBrowsingController/getSharedFilesystemRoots` | getSharedFilesystemRoots |

### `file-systems-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/FileSystemsController/deleteFilesystemEndpoint` | deleteFilesystemEndpoint |
| GET | `/api/admin/FileSystemsController/findFileSystemEndpointsByProject` | findFileSystemEndpointsByProject |
| POST | `/api/admin/FileSystemsController/findFileSystemEndpointsByQbe` | findFileSystemEndpointsByQbe |
| GET | `/api/admin/FileSystemsController/getFileSystemSystemTypes` | getFileSystemSystemTypes |
| GET | `/api/admin/FileSystemsController/getFileSystemSystems` | getFileSystemSystems |
| POST | `/api/admin/FileSystemsController/insertFilesystemEndpoint` | insertFilesystemEndpoint |
| POST | `/api/admin/FileSystemsController/publishFilesystemEndpoint` | publishFilesystemEndpoint |
| POST | `/api/admin/FileSystemsController/updateFilesystemEndpoint` | updateFilesystemEndpoint |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

### `job-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobStatusController/getJobStatus` | getJobStatus |
| GET | `/api/admin/JobStatusController/getJobSummary` | getJobSummary |

### `workflow-stats-admin-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/WorkflowStatsAdminLevelController/drillDown` | workflowDrillDown |

## uploads.gebo.ai — port 13007 (`uploads-gebo-ai`)

9 controller(s), 24 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `file-upload-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/FileUploadController/getHandShakeCode` | getHandShakeCode |
| POST | `/api/admin/FileUploadController/upload/{handShakeCode}` | upload |

### `file-uploads-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/FileUploadsController/deleteUploadsEndpoint` | deleteUploadsEndpoint |
| GET | `/api/admin/FileUploadsController/findUploadsEndpointsByProject` | findUploadsEndpointsByProject |
| POST | `/api/admin/FileUploadsController/findUploadsEndpointsByQbe` | findUploadsEndpointsByQbe |
| GET | `/api/admin/FileUploadsController/getFileSystemSystemTypes` | getFileSystemSystemTypes |
| GET | `/api/admin/FileUploadsController/getUploadableFilesExtensions` | getUploadableFilesExtensions |
| GET | `/api/admin/FileUploadsController/getUploadsSystems` | getUploadsSystems |
| POST | `/api/admin/FileUploadsController/insertUploadsEndpoint` | insertUploadsEndpoint |
| POST | `/api/admin/FileUploadsController/publishUploadsEndpoint` | publishUploadsEndpoint |
| POST | `/api/admin/FileUploadsController/updateUploadsEndpoint` | updateUploadsEndpoint |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

### `job-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobStatusController/getJobStatus` | getJobStatus |
| GET | `/api/admin/JobStatusController/getJobSummary` | getJobSummary |

### `workflow-stats-admin-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/WorkflowStatsAdminLevelController/drillDown` | workflowDrillDown |

## userspace.gebo.ai — port 13008 (`userspace-gebo-ai`)

9 controller(s), 32 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

### `job-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobStatusController/getJobStatus` | getJobStatus |
| GET | `/api/admin/JobStatusController/getJobSummary` | getJobSummary |

### `userspace-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/user/UserspaceController/deleteUserKnowledgebase` | deleteUserKnowledgebase |
| POST | `/api/user/UserspaceController/deleteUserspaceFiles` | deleteUserspaceFiles |
| POST | `/api/user/UserspaceController/deleteUserspaceFolder` | deleteUserspaceFolder |
| GET | `/api/user/UserspaceController/findUserKnowledgebaseByCode` | findUserKnowledgebaseByCode |
| POST | `/api/user/UserspaceController/findUserspaceFileByCodes` | findUserspaceFileByCodes |
| GET | `/api/user/UserspaceController/findUserspaceFolderByCode` | findUserspaceFolderByCode |
| GET | `/api/user/UserspaceController/getPersonalKnowledgebases` | getPersonalKnowledgebases |
| POST | `/api/user/UserspaceController/getPublishingStatus` | getPublishingStatus |
| POST | `/api/user/UserspaceController/listChildPersonalKnowledgebases` | listChildPersonalKnowledgebases |
| GET | `/api/user/UserspaceController/listUserspaceFiles` | listUserspaceFiles |
| GET | `/api/user/UserspaceController/listUserspaceFolders` | listUserspaceFolders |
| POST | `/api/user/UserspaceController/newUserKnowledgebase` | newUserKnowledgebase |
| POST | `/api/user/UserspaceController/newUserspaceFolder` | newUserspaceFolder |
| POST | `/api/user/UserspaceController/publishFolder` | publishFolder |
| POST | `/api/user/UserspaceController/publishUserspaceProjectEndpoint` | publishUserspaceProjectEndpoint |
| POST | `/api/user/UserspaceController/transferUploadsToUserSpaceAndPublish` | transferUploadsToUserSpaceAndPublish |
| POST | `/api/user/UserspaceController/updateUserKnowledgebase` | updateUserKnowledgebase |
| POST | `/api/user/UserspaceController/updateUserspaceFolder` | updateUserspaceFolder |

### `userspace-upload-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/user/UserspaceUploadController/upload/{userspaceFolderCode}` | upload |

### `workflow-stats-admin-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/WorkflowStatsAdminLevelController/drillDown` | workflowDrillDown |

## sharepoint.gebo.ai — port 13009 (`sharepoint-gebo-ai`)

9 controller(s), 31 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

### `job-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobStatusController/getJobStatus` | getJobStatus |
| GET | `/api/admin/JobStatusController/getJobSummary` | getJobSummary |

### `sharepoint-browsing-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/SharepointBrowsingController/browseSharepointPath` | browseSharepointPath |
| POST | `/api/admin/SharepointBrowsingController/getSharepointNavigationStatus` | getSharepointNavigationStatus |
| GET | `/api/admin/SharepointBrowsingController/getSharepointRoots` | getSharepointRoots |

### `sharepoint-systems-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/SharepointSystemsController/deleteSharepointEndpoint` | deleteSharepointEndpoint |
| POST | `/api/admin/SharepointSystemsController/deleteSharepointSystem` | deleteSharepointSystem |
| POST | `/api/admin/SharepointSystemsController/fastSharepointConfig` | fastSharepointConfig |
| GET | `/api/admin/SharepointSystemsController/findSharepointEndpointsByCode` | findSharepointEndpointsByCode |
| GET | `/api/admin/SharepointSystemsController/findSharepointEndpointsByProject` | findSharepointEndpointsByProject |
| POST | `/api/admin/SharepointSystemsController/findSharepointEndpointsByQbe` | findSharepointEndpointsByQbe |
| GET | `/api/admin/SharepointSystemsController/findSharepointSystemByCode` | findSharepointSystemByCode |
| GET | `/api/admin/SharepointSystemsController/getSharepointSystemType` | getSharepointSystemTypes |
| GET | `/api/admin/SharepointSystemsController/getSharepointSystems` | getSharepointSystems |
| POST | `/api/admin/SharepointSystemsController/insertSharepointEndpoint` | insertSharepointEndpoint |
| POST | `/api/admin/SharepointSystemsController/insertSharepointSystem` | insertSharepointSystem |
| POST | `/api/admin/SharepointSystemsController/publishSharepointEndpoint` | publishSharepointEndpoint |
| POST | `/api/admin/SharepointSystemsController/testSharepointSystem` | testSharepointSystem |
| POST | `/api/admin/SharepointSystemsController/updateSharepointEndpoint` | updateSharepointEndpoint |
| POST | `/api/admin/SharepointSystemsController/updateSharepointSystem` | updateSharepointSystem |

### `workflow-stats-admin-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/WorkflowStatsAdminLevelController/drillDown` | workflowDrillDown |

## confluence.gebo.ai — port 13010 (`confluence-gebo-ai`)

9 controller(s), 31 endpoint(s):


### `confluence-browsing-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/ConfluenceBrowsingController/browseConfluencePath` | browseConfluencePath |
| POST | `/api/admin/ConfluenceBrowsingController/getConfluenceNavigationStatus` | getConfluenceNavigationStatus |
| GET | `/api/admin/ConfluenceBrowsingController/getConfluenceRoots` | getConfluenceRoots |

### `confluence-systems-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/ConfluenceSystemsController/deleteConfluenceEndpoint` | deleteConfluenceEndpoint |
| POST | `/api/admin/ConfluenceSystemsController/deleteConfluenceSystem` | deleteConfluenceSystem |
| POST | `/api/admin/ConfluenceSystemsController/fastConfluenceConfig` | fastConfluenceConfig |
| GET | `/api/admin/ConfluenceSystemsController/findConfluenceEndpointsByCode` | findConfluenceEndpointsByCode |
| GET | `/api/admin/ConfluenceSystemsController/findConfluenceEndpointsByProject` | findConfluenceEndpointsByProject |
| POST | `/api/admin/ConfluenceSystemsController/findConfluenceEndpointsByQbe` | findConfluenceEndpointsByQbe |
| GET | `/api/admin/ConfluenceSystemsController/findConfluenceSystemByCode` | findConfluenceSystemByCode |
| GET | `/api/admin/ConfluenceSystemsController/getConfluenceSystemType` | getConfluenceSystemTypes |
| GET | `/api/admin/ConfluenceSystemsController/getConfluenceSystems` | getConfluenceSystems |
| POST | `/api/admin/ConfluenceSystemsController/insertConfluenceEndpoint` | insertConfluenceEndpoint |
| POST | `/api/admin/ConfluenceSystemsController/insertConfluenceSystem` | insertConfluenceSystem |
| POST | `/api/admin/ConfluenceSystemsController/publishConfluenceEndpoint` | publishConfluenceEndpoint |
| POST | `/api/admin/ConfluenceSystemsController/testConfluenceSystem` | testConfluenceSystem |
| POST | `/api/admin/ConfluenceSystemsController/updateConfluenceEndpoint` | updateConfluenceEndpoint |
| POST | `/api/admin/ConfluenceSystemsController/updateConfluenceSystem` | updateConfluenceSystem |

### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

### `job-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobStatusController/getJobStatus` | getJobStatus |
| GET | `/api/admin/JobStatusController/getJobSummary` | getJobSummary |

### `workflow-stats-admin-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/WorkflowStatsAdminLevelController/drillDown` | workflowDrillDown |

## jira.gebo.ai — port 13011 (`jira-gebo-ai`)

9 controller(s), 31 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `jira-browsing-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/JiraBrowsingController/browseJiraPath` | browseJiraPath |
| POST | `/api/admin/JiraBrowsingController/getJiraNavigationStatus` | getJiraNavigationStatus |
| GET | `/api/admin/JiraBrowsingController/getJiraRoots` | getJiraRoots |

### `jira-systems-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/JiraSystemsController/deleteJiraEndpoint` | deleteJiraEndpoint |
| POST | `/api/admin/JiraSystemsController/deleteJiraSystem` | deleteJiraSystem |
| POST | `/api/admin/JiraSystemsController/fastJiraConfig` | fastJiraConfig |
| GET | `/api/admin/JiraSystemsController/findJiraEndpointsByCode` | findJiraEndpointsByCode |
| GET | `/api/admin/JiraSystemsController/findJiraEndpointsByProject` | findJiraEndpointsByProject |
| POST | `/api/admin/JiraSystemsController/findJiraEndpointsByQbe` | findJiraEndpointsByQbe |
| GET | `/api/admin/JiraSystemsController/findJiraSystemByCode` | findJiraSystemByCode |
| GET | `/api/admin/JiraSystemsController/getJiraSystemType` | getJiraSystemTypes |
| GET | `/api/admin/JiraSystemsController/getJiraSystems` | getJiraSystems |
| POST | `/api/admin/JiraSystemsController/insertJiraEndpoint` | insertJiraEndpoint |
| POST | `/api/admin/JiraSystemsController/insertJiraSystem` | insertJiraSystem |
| POST | `/api/admin/JiraSystemsController/publishJiraEndpoint` | publishJiraEndpoint |
| POST | `/api/admin/JiraSystemsController/testJiraSystem` | testJiraSystem |
| POST | `/api/admin/JiraSystemsController/updateJiraEndpoint` | updateJiraEndpoint |
| POST | `/api/admin/JiraSystemsController/updateJiraSystem` | updateJiraSystem |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

### `job-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobStatusController/getJobStatus` | getJobStatus |
| GET | `/api/admin/JobStatusController/getJobSummary` | getJobSummary |

### `workflow-stats-admin-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/WorkflowStatsAdminLevelController/drillDown` | workflowDrillDown |

## aws-s3.gebo.ai — port 13012 (`aws-s3-gebo-ai`)

9 controller(s), 29 endpoint(s):


### `aws-s-3-browsing-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/AwsS3BrowsingController/browseAwsS3Path` | browseAwsS3Path |
| GET | `/api/admin/AwsS3BrowsingController/getAwsS3Roots` | getAwsS3Roots |

### `aws-s-3-systems-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/AwsS3SystemsController/deleteAwsS3ProjectEndpoint` | deleteAwsS3ProjectEndpoint |
| POST | `/api/admin/AwsS3SystemsController/deleteAwsS3System` | deleteAwsS3System |
| POST | `/api/admin/AwsS3SystemsController/fastAwsS3Config` | fastAwsS3Config |
| GET | `/api/admin/AwsS3SystemsController/findAwsS3EndpointsByProject` | findAwsS3EndpointsByProject |
| POST | `/api/admin/AwsS3SystemsController/findAwsS3EndpointsByQbe` | findAwsS3EndpointsByQbe |
| GET | `/api/admin/AwsS3SystemsController/findAwsS3ProjectEndpointByCode` | findAwsS3ProjectEndpointByCode |
| GET | `/api/admin/AwsS3SystemsController/findAwsS3SystemByCode` | findAwsS3SystemByCode |
| GET | `/api/admin/AwsS3SystemsController/getAwsS3SystemType` | getAwsS3SystemType |
| GET | `/api/admin/AwsS3SystemsController/getAwsS3Systems` | getAwsS3Systems |
| POST | `/api/admin/AwsS3SystemsController/insertAwsS3ProjectEndpoint` | insertAwsS3ProjectEndpoint |
| POST | `/api/admin/AwsS3SystemsController/insertAwsS3System` | insertAwsS3System |
| POST | `/api/admin/AwsS3SystemsController/publishAwsS3ProjectEndpoint` | publishAwsS3ProjectEndpoint |
| POST | `/api/admin/AwsS3SystemsController/updateAwsS3ProjectEndpoint` | updateAwsS3ProjectEndpoint |
| POST | `/api/admin/AwsS3SystemsController/updateAwsS3System` | updateAwsS3System |

### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

### `job-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobStatusController/getJobStatus` | getJobStatus |
| GET | `/api/admin/JobStatusController/getJobSummary` | getJobSummary |

### `workflow-stats-admin-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/WorkflowStatsAdminLevelController/drillDown` | workflowDrillDown |

## googledrive.gebo.ai — port 13013 (`googledrive-gebo-ai`)

10 controller(s), 32 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `google-drive-browsing-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GoogleDriveBrowsingController/browseGoogleDrivePath` | browseGoogleDrivePath |
| GET | `/api/admin/GoogleDriveBrowsingController/getGoogleDriveRoots` | getGoogleDriveRoots |

### `google-drive-systems-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GoogleDriveSystemsController/deleteGoogleDriveProjectEndpoint` | deleteGoogleDriveProjectEndpoint |
| POST | `/api/admin/GoogleDriveSystemsController/deleteGoogleDriveSystem` | deleteGoogleDriveSystem |
| POST | `/api/admin/GoogleDriveSystemsController/fastGoogleDriveConfig` | fastGoogleDriveConfig |
| GET | `/api/admin/GoogleDriveSystemsController/findGoogleDriveEndpointsByProject` | findGoogleDriveEndpointsByProject |
| POST | `/api/admin/GoogleDriveSystemsController/findGoogleDriveEndpointsByQbe` | findGoogleDriveEndpointsByQbe |
| GET | `/api/admin/GoogleDriveSystemsController/findGoogleDriveProjectEndpointByCode` | findGoogleDriveProjectEndpointByCode |
| GET | `/api/admin/GoogleDriveSystemsController/findGoogleDriveSystemByCode` | findGoogleDriveSystemByCode |
| GET | `/api/admin/GoogleDriveSystemsController/getGoogleDriveSystemType` | getGoogleDriveSystemType |
| GET | `/api/admin/GoogleDriveSystemsController/getGoogleDriveSystems` | getGoogleDriveSystems |
| POST | `/api/admin/GoogleDriveSystemsController/insertGoogleDriveProjectEndpoint` | insertGoogleDriveProjectEndpoint |
| POST | `/api/admin/GoogleDriveSystemsController/insertGoogleDriveSystem` | insertGoogleDriveSystem |
| POST | `/api/admin/GoogleDriveSystemsController/publishGoogleDriveProjectEndpoint` | publishGoogleDriveProjectEndpoint |
| POST | `/api/admin/GoogleDriveSystemsController/updateGoogleDriveProjectEndpoint` | updateGoogleDriveProjectEndpoint |
| POST | `/api/admin/GoogleDriveSystemsController/updateGoogleDriveSystem` | updateGoogleDriveSystem |

### `google-workspace-access-handshake-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/start-workspace-access` | tryGoogleWorkspaceAccess |
| GET | `/oauth2/google-workspace-redirect` | googleWorkspaceRedirect |
| GET | `/oauth2/start-workspace-access-go` | startWorkspaceAccess |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

### `job-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobStatusController/getJobStatus` | getJobStatus |
| GET | `/api/admin/JobStatusController/getJobSummary` | getJobSummary |

### `workflow-stats-admin-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/WorkflowStatsAdminLevelController/drillDown` | workflowDrillDown |

## mcpclient.gebo.ai — port 13014 (`mcpclient-gebo-ai`)

10 controller(s), 31 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

### `job-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobStatusController/getJobStatus` | getJobStatus |
| GET | `/api/admin/JobStatusController/getJobSummary` | getJobSummary |

### `mcp-client-browsing-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/MCPClientBrowsingController/browseMCPClientPath` | browseMCPClientPath |
| POST | `/api/admin/MCPClientBrowsingController/getMCPClientNavigationStatus` | getMCPClientNavigationStatus |
| GET | `/api/admin/MCPClientBrowsingController/getMCPClientRoots` | getMCPClientRoots |

### `mcp-client-config-controller`
| Method | Path | Operation |
|---|---|---|
| DELETE | `/api/admin/McpClientConfigController/deleteMCPClientConfig` | deleteMCPClientConfig |
| GET | `/api/admin/McpClientConfigController/findMCPClientConfigByCode` | findMCPClientConfigByCode |
| POST | `/api/admin/McpClientConfigController/findMCPClientConfigByQbe` | findMCPClientConfigByQbe |
| POST | `/api/admin/McpClientConfigController/insertMCPClientConfig` | insertMCPClientConfig |
| POST | `/api/admin/McpClientConfigController/listMCPClientConfig` | listMCPClientConfig |
| POST | `/api/admin/McpClientConfigController/testAndDiscovery` | testAndDiscovery |
| POST | `/api/admin/McpClientConfigController/updateMCPClientConfig` | updateMCPClientConfig |

### `mcp-client-systems-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/MCPClientSystemsController/deleteMCPClientEndpoint` | deleteMCPClientEndpoint |
| GET | `/api/admin/MCPClientSystemsController/findMCPClientEndpointsByCode` | findMCPClientEndpointsByCode |
| GET | `/api/admin/MCPClientSystemsController/findMCPClientEndpointsByProject` | findMCPClientEndpointsByProject |
| POST | `/api/admin/MCPClientSystemsController/findMCPClientEndpointsByQbe` | findMCPClientEndpointsByQbe |
| GET | `/api/admin/MCPClientSystemsController/getMCPClientSystemType` | getMCPClientSystemType |
| POST | `/api/admin/MCPClientSystemsController/insertMCPClientEndpoint` | insertMCPClientEndpoint |
| POST | `/api/admin/MCPClientSystemsController/publishMCPClientEndpoint` | publishMCPClientEndpoint |
| POST | `/api/admin/MCPClientSystemsController/updateMCPClientEndpoint` | updateMCPClientEndpoint |

### `workflow-stats-admin-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/WorkflowStatsAdminLevelController/drillDown` | workflowDrillDown |

## integration.gebo.ai — port 13015 (`integration-gebo-ai`)

9 controller(s), 22 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `integration-input-controller`
| Method | Path | Operation |
|---|---|---|
| PUT | `/api/application/IntegrationInputController/publishContents` | publishContents |
| GET | `/api/application/IntegrationInputController/publishSync` | publishSync |
| POST | `/api/application/IntegrationInputController/spoolDocument` | spoolDocument |
| PUT | `/api/application/IntegrationInputController/spoolDocument` | spoolDocument_1 |

### `integration-systems-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/IntegrationSystemsController/deleteIntegrationProjectEndpoint` | deleteIntegrationProjectEndpoint |
| GET | `/api/admin/IntegrationSystemsController/findIntegrationEndpointsByProject` | findIntegrationEndpointsByProject |
| POST | `/api/admin/IntegrationSystemsController/insertIntegrationProjectEndpoint` | insertIntegrationProjectEndpoint |
| POST | `/api/admin/IntegrationSystemsController/publishIntegrationProjectEndpoint` | publishIntegrationProjectEndpoint |
| POST | `/api/admin/IntegrationSystemsController/updateIntegrationProjectEndpoint` | updateIntegrationProjectEndpoint |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

### `job-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobStatusController/getJobStatus` | getJobStatus |
| GET | `/api/admin/JobStatusController/getJobSummary` | getJobSummary |

### `workflow-stats-admin-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/WorkflowStatsAdminLevelController/drillDown` | workflowDrillDown |

## fulltextor.gebo.ai — port 13016 (`fulltextor-gebo-ai`)

7 controller(s), 13 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `ingestion-file-types-library-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/IngestionFileTypesLibraryController/getAllFileTypes` | getAllFileTypes |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension` | getIngestionFileTypeByExtension |
| GET | `/api/users/IngestionFileTypesLibraryController/getIngestionReadingModules` | getIngestionReadingModules |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

### `job-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/JobStatusController/getJobStatus` | getJobStatus |
| GET | `/api/admin/JobStatusController/getJobSummary` | getJobSummary |

### `workflow-stats-admin-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/WorkflowStatsAdminLevelController/drillDown` | workflowDrillDown |

## eureka.gebo.ai — port 13017

_No spec captured (not polled)._


## heimdall.gebo.ai — port 13018 (`heimdall-gebo-ai`)

14 controller(s), 55 endpoint(s):


### `auth-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/auth/login` | authenticateUser |

### `auth-providers-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/public/AuthProvidersController/getProviderClientConfig` | getProviderClientConfig |
| GET | `/public/AuthProvidersController/listAuthProviders` | listAuthProviders |
| GET | `/public/AuthProvidersController/listAvailableProvidersConfig` | listAvailableProvidersConfig |

### `gebo-advanced-setup-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/GeboAdvancedSetupStatusController/getFirstKnowledgeBaseSetupStatus` | getFirstKnowledgeBaseSetupStatus |
| GET | `/api/admin/GeboAdvancedSetupStatusController/getMinimalContentsSetupStatus` | getMinimalContentsSetupStatus |

### `gebo-fast-installation-setup-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/public/GeboFastSetupController/createSetup` | createSetup |
| GET | `/public/GeboFastSetupController/getInstallationStatus` | getInstallationStatus |

### `gebo-fast-work-folder-setup-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GeboFastWorkFolderSetupController/configureWorkDirectory` | configureWorkDirectory |
| GET | `/api/admin/GeboFastWorkFolderSetupController/getWorkDirectorySetupEnabled` | getWorkDirectorySetupEnabled |
| GET | `/api/admin/GeboFastWorkFolderSetupController/getWorkDirectorySetupStatus` | getWorkDirectorySetupStatus |

### `generated-admin-api-key-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/GeneratedAdminApiKeyController/deleteAdminGeneratedApiKey` | deleteAdminGeneratedApiKey |
| POST | `/api/admin/GeneratedAdminApiKeyController/generateAdminGeneratedApiKey` | generateAdminGeneratedApiKey |
| POST | `/api/admin/GeneratedAdminApiKeyController/getAdminGeneratedApiKeyPagedList` | getAdminGeneratedApiKeyPagedList |
| GET | `/api/admin/GeneratedAdminApiKeyController/isAdminGeneratedApiKeyGenerationAllowed` | isAdminGeneratedApiKeyGenerationAllowed |

### `generated-user-api-key-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/GeneratedUserApiKeyController/deleteUserGeneratedApiKey` | deleteUserGeneratedApiKey |
| POST | `/api/users/GeneratedUserApiKeyController/generateUserGeneratedApiKey` | generateUserGeneratedApiKey |
| POST | `/api/users/GeneratedUserApiKeyController/getUserGeneratedApiKeyPagedList` | getUserGeneratedApiKeyPagedList |
| GET | `/api/users/GeneratedUserApiKeyController/isUserGeneratedApiKeyGenerationAllowed` | isUserGeneratedApiKeyGenerationAllowed |

### `o-auth-2-admin-controller`
| Method | Path | Operation |
|---|---|---|
| DELETE | `/api/admin/OAuth2AdminController/deleteOauth2ProviderRegistration` | deleteOauth2ProviderRegistration |
| GET | `/api/admin/OAuth2AdminController/findOauth2ProviderRegistrationByRegistrationId` | findOauth2ProviderRegistrationByRegistrationId |
| GET | `/api/admin/OAuth2AdminController/getProviders` | getProviders |
| POST | `/api/admin/OAuth2AdminController/insertOauth2ProviderRegistration` | insertOauth2ProviderRegistration |
| POST | `/api/admin/OAuth2AdminController/updateOauth2ProviderRegistration` | updateOauth2ProviderRegistration |

### `oauth-2-module-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/admin/Oauth2ModuleStatusController` | getStatus |

### `secrets-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/SecretsController/createAWSConnectionSecret` | createAWSConnectionSecret |
| POST | `/api/admin/SecretsController/createCustomSecret` | createCustomSecret |
| POST | `/api/admin/SecretsController/createGoogleJsonCredentialsSecret` | createGoogleJsonCredentialsSecret |
| POST | `/api/admin/SecretsController/createGoogleOauth2Secret` | createGoogleOauth2Secret |
| POST | `/api/admin/SecretsController/createOauth2StandardSecret` | createOauth2StandardSecret |
| POST | `/api/admin/SecretsController/createSshKeySecret` | createSshKeySecret |
| POST | `/api/admin/SecretsController/createTokenSecret` | createTokenSecret |
| POST | `/api/admin/SecretsController/createUsernamePasswordSecret` | createUsernamePasswordSecret |
| DELETE | `/api/admin/SecretsController/deleteSecret` | deleteSecret |
| GET | `/api/admin/SecretsController/getSecretsByContextCode` | getSecretsByContextCode |

### `token-renew-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/api/users/TokenRenewController/renew` | renew |

### `user-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/users/ActualUserController/changePassword` | changePassword |
| GET | `/api/users/ActualUserController/getMyGroups` | getMyGroups |
| GET | `/api/users/ActualUserController/me` | getCurrentUser |

### `user-workflows-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/public/UserWorkflowsController/getUserWorkflowsConfig` | getUserWorkflowsConfig |
| POST | `/public/UserWorkflowsController/startUserWorkflow` | startUserWorkflow |
| POST | `/public/UserWorkflowsController/userChangePasswordWithTicket` | userChangePasswordWithTicket |

### `users-admin-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/api/admin/UsersAdminController/changeUserPassword` | changeUserPassword |
| POST | `/api/admin/UsersAdminController/deleteGroup` | deleteGroup |
| POST | `/api/admin/UsersAdminController/deleteUser` | deleteUser |
| GET | `/api/admin/UsersAdminController/findGroupByCode` | findGroupByCode |
| POST | `/api/admin/UsersAdminController/findUserByQbe` | findUserByQbe |
| GET | `/api/admin/UsersAdminController/findUserByUsername` | findUserByUsername |
| POST | `/api/admin/UsersAdminController/findUsersGroupByQbe` | findUsersGroupByQbe |
| GET | `/api/admin/UsersAdminController/getAllGroups` | getAllGroups |
| GET | `/api/admin/UsersAdminController/getAllUsers` | getAllUsers |
| POST | `/api/admin/UsersAdminController/insertGroup` | insertGroup |
| POST | `/api/admin/UsersAdminController/insertUser` | insertUser |
| POST | `/api/admin/UsersAdminController/updateGroup` | updateGroup |
| POST | `/api/admin/UsersAdminController/updateUser` | updateUser |
