# Microservices — Controllers by Service

Generated from each running microservice's live `/v3/api-docs` (springdoc), after building every image with `-P docker,swagger-on` and bringing up `dockers/gebo.microservices/docker-compose.yml`. One section per service; controllers are grouped by their springdoc `tag`, which springdoc derives 1:1 from the `@RestController` class name (kebab-case).

**Base path note:** this checkout serves backends under a `server.servlet.context-path` (e.g. `/brain`); every path below already includes it.

### What changed this round

Regenerated on 16 November 2026 from `feature/spring-platforms-upgrade` (Spring Boot 4.2.0-RC2,
Spring AI 2.1.0-RC2) rebased on develop 1.0.7.0-SNAPSHOT. The previous round was the one that
added the web search providers to `brain`. Since then the following has changed:

- **`brain`: 70 → 99 controllers, 321 → 490 endpoints.**
  - New from this branch: the decision models (`DecisionModelsController`, plus the OpenAI,
    Ollama and generic OpenAI-compatible configuration controllers).
  - New from develop:
    - the agents' waiting conditions (`WaitingConditionsController`,
      `WaitingConditionsAdminController`, `NetworkOfAgentsConditionEventsController`);
    - chat libraries, chat rules (user and admin), chat pipelines tooling and the default
      agentic chat networks;
    - A2A (client configuration and server admin);
    - provider deals, data-flow meta info, language and UI text resources, the Neo4j module
      setup;
    - the Bedrock (6), Google Vertex (2), Mistral AI (2) and DeepSeek model configuration
      controllers.
  - Gone: `GeboAdvancedSetupStatusController` and `GeboVectorStoreConfigurationController`,
    both removed from the code.
- **`heimdall`: 18 → 25 controllers.**
  - Added: the live sessions (`SessionController`, `SessionsAdminController`), the installation
    and users-admin cluster controllers, the system messages (user and admin) and
    `SecurityHeaderDataCompletionController`.
  - Gone: `GeboAdvancedSetupStatusController`.
- **`gateway`: 0 → 1.** It now serves `/public/ClientsTopologyProviderController`, the
  topology the clients read. It is no longer a pure proxy with nothing of its own.
- **Every backend** now exposes `GeboAngularFormGroupMetaInfoController`.
- **Controllers that moved off the content services:**
  - The 14 content and ingestion services no longer expose `IngestionFileTypesLibraryController`.
  - `mcpclient` no longer exposes `McpClientConfigController`.
  - Both were already dropped from their clients on 23 September.
- **Smaller additions:** `graphicator` gains `GraphRagConfigurationController`, `uploads` gains
  `UploadsBrowsingController`, `tyr` gains `GeboModulesConfigController`.

The 42 clients under `gebo.api.clients/gebo.microservices.clients.parent` were regenerated in
the same pass. Only brain, heimdall and tyr changed beyond the generation dates (for tyr, the
`DECISION` model type); the others already matched their services.


## Summary

| Service | Port | Context-path | Controllers | Endpoints |
|---|---|---|---|---|
| gateway.gebo.ai | 13000 | `—` | 1 | 1 |
| brain.gebo.ai | 13001 | `/brain` | 99 | 490 |
| vectorizator.gebo.ai | 13002 | `/vectorizator` | 3 | 6 |
| graphicator.gebo.ai | 13003 | `/graphicator` | 3 | 13 |
| chunker.gebo.ai | 13004 | `/chunker` | 5 | 17 |
| git.gebo.ai | 13005 | `/git` | 7 | 23 |
| filesystem.gebo.ai | 13006 | `/filesystem` | 9 | 31 |
| uploads.gebo.ai | 13007 | `/uploads` | 9 | 31 |
| userspace.gebo.ai | 13008 | `/userspace` | 8 | 30 |
| sharepoint.gebo.ai | 13009 | `/sharepoint` | 9 | 46 |
| confluence.gebo.ai | 13010 | `/confluence` | 9 | 46 |
| jira.gebo.ai | 13011 | `/jira` | 9 | 46 |
| aws-s3.gebo.ai | 13012 | `/aws-s3` | 8 | 27 |
| googledrive.gebo.ai | 13013 | `/googledrive` | 10 | 44 |
| mcpclient.gebo.ai | 13014 | `/mcpclient` | 8 | 22 |
| integration.gebo.ai | 13015 | `/integration` | 8 | 20 |
| fulltextor.gebo.ai | 13016 | `/fulltextor` | 2 | 4 |
| eureka.gebo.ai | 13017 | `—` | 0 | 0 |
| heimdall.gebo.ai | 13018 | `/heimdall` | 25 | 102 |
| tyr.gebo.ai | 13019 | `/tyr` | 10 | 18 |
| webdav.gebo.ai | 13020 | `/webdav` | 8 | 29 |


## gateway.gebo.ai — port 13000 (`gateway-gebo-ai`)

1 controller(s), 1 endpoint(s):


### `clients-topology-provider-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/public/ClientsTopologyProviderController` | getClientsTopology |

## brain.gebo.ai — port 13001 (`brain-gebo-ai`) — context-path `/brain`

99 controller(s), 490 endpoint(s):


### `a-2-a-client-config-controller`
| Method | Path | Operation |
|---|---|---|
| DELETE | `/brain/api/admin/A2AClientConfigController/deleteA2AAgent` | deleteA2AClientConfig |
| GET | `/brain/api/admin/A2AClientConfigController/findByCode` | findByCodeA2AClientConfig |
| POST | `/brain/api/admin/A2AClientConfigController/insertA2AAgent` | insertA2AClientConfig |
| GET | `/brain/api/admin/A2AClientConfigController/list` | listA2AClientConfig |
| POST | `/brain/api/admin/A2AClientConfigController/testAndDiscovery` | testAndDiscoveryA2AClientConfig |
| POST | `/brain/api/admin/A2AClientConfigController/updateA2AAgent` | updateA2AClientConfig |

### `agentic-chat-default-networks-admin-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/AgenticChatDefaultNetworksAdminController/getAgenticChatDefaultNetworks` | getAgenticChatDefaultNetworks |
| GET | `/brain/api/admin/AgenticChatDefaultNetworksAdminController/getChoosableChatNetworksOfAgents` | getChoosableChatNetworksOfAgents |
| GET | `/brain/api/admin/AgenticChatDefaultNetworksAdminController/isAgenticChatNetworksEnabled` | isAgenticChatNetworksEnabled |
| POST | `/brain/api/admin/AgenticChatDefaultNetworksAdminController/resetAgenticChatDefaultNetwork` | resetAgenticChatDefaultNetwork |
| POST | `/brain/api/admin/AgenticChatDefaultNetworksAdminController/setAgenticChatDefaultNetwork` | setAgenticChatDefaultNetwork |

### `anthropic-chat-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/AnthropicChatModelsConfigurationController/deleteAnthropicChatModelConfig` | deleteAnthropicChatModelConfig |
| GET | `/brain/api/admin/AnthropicChatModelsConfigurationController/findAnthropicChatModelConfigByCode` | findAnthropicChatModelConfigByCode |
| POST | `/brain/api/admin/AnthropicChatModelsConfigurationController/getAnthropicModels` | getAnthropicChatModels |
| POST | `/brain/api/admin/AnthropicChatModelsConfigurationController/insertAnthropicChatModelConfig` | insertAnthropicChatModelConfig |
| POST | `/brain/api/admin/AnthropicChatModelsConfigurationController/updateAnthropicChatModelConfig` | updateAnthropicChatModelConfig |

### `bedrock-chat-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/BedrockChatModelsConfigurationController/deleteBedrockChatModelConfig` | deleteBedrockChatModelConfig |
| GET | `/brain/api/admin/BedrockChatModelsConfigurationController/findBedrockChatModelConfigByCode` | findBedrockChatModelConfigByCode |
| POST | `/brain/api/admin/BedrockChatModelsConfigurationController/getBedrockChatModels` | getBedrockChatModels |
| POST | `/brain/api/admin/BedrockChatModelsConfigurationController/insertBedrockChatModelConfig` | insertBedrockChatModelConfig |
| POST | `/brain/api/admin/BedrockChatModelsConfigurationController/updateBedrockChatModelConfig` | updateBedrockChatModelConfig |

### `bedrock-embedding-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/BedrockEmbeddingModelsConfigurationController/deleteBedrockEmbeddingModelConfig` | deleteBedrockEmbeddingModelConfig |
| GET | `/brain/api/admin/BedrockEmbeddingModelsConfigurationController/findBedrockEmbeddingModelConfigByCode` | findBedrockEmbeddingModelConfigByCode |
| POST | `/brain/api/admin/BedrockEmbeddingModelsConfigurationController/getBedrockEmbeddingModels` | getBedrockEmbeddingModels |
| POST | `/brain/api/admin/BedrockEmbeddingModelsConfigurationController/insertBedrockEmbeddingModelConfig` | insertBedrockEmbeddingModelConfig |
| POST | `/brain/api/admin/BedrockEmbeddingModelsConfigurationController/updateBedrockEmbeddingModelConfig` | updateBedrockEmbeddingModelConfig |

### `bedrock-image-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/BedrockImageModelsConfigurationController/deleteBedrockImageModelConfig` | deleteBedrockImageModelConfig |
| GET | `/brain/api/admin/BedrockImageModelsConfigurationController/findBedrockImageModelConfigByCode` | findBedrockImageModelConfigByCode |
| POST | `/brain/api/admin/BedrockImageModelsConfigurationController/getBedrockImageModels` | getBedrockImageModels |
| POST | `/brain/api/admin/BedrockImageModelsConfigurationController/insertBedrockImageModelConfig` | insertBedrockImageModelConfig |
| POST | `/brain/api/admin/BedrockImageModelsConfigurationController/updateBedrockImageModelConfig` | updateBedrockImageModelConfig |

### `bedrock-ranker-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/BedrockRankerModelsConfigurationController/deleteBedrockRankerModelConfig` | deleteBedrockRankerModelConfig |
| GET | `/brain/api/admin/BedrockRankerModelsConfigurationController/findBedrockRankerModelConfigByCode` | findBedrockRankerModelConfigByCode |
| POST | `/brain/api/admin/BedrockRankerModelsConfigurationController/getBedrockRankerModels` | getBedrockRankerModels |
| POST | `/brain/api/admin/BedrockRankerModelsConfigurationController/insertBedrockRankerModelConfig` | insertBedrockRankerModelConfig |
| POST | `/brain/api/admin/BedrockRankerModelsConfigurationController/updateBedrockRankerModelConfig` | updateBedrockRankerModelConfig |

### `bedrock-text-to-speech-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/BedrockTextToSpeechModelsConfigurationController/deleteBedrockTextToSpeechModelConfig` | deleteBedrockTextToSpeechModelConfig |
| GET | `/brain/api/admin/BedrockTextToSpeechModelsConfigurationController/findBedrockTextToSpeechModelConfigByCode` | findBedrockTextToSpeechModelConfigByCode |
| POST | `/brain/api/admin/BedrockTextToSpeechModelsConfigurationController/getBedrockTextToSpeechModels` | getBedrockTextToSpeechModels |
| POST | `/brain/api/admin/BedrockTextToSpeechModelsConfigurationController/insertBedrockTextToSpeechModelConfig` | insertBedrockTextToSpeechModelConfig |
| POST | `/brain/api/admin/BedrockTextToSpeechModelsConfigurationController/updateBedrockTextToSpeechModelConfig` | updateBedrockTextToSpeechModelConfig |

### `bedrock-transcript-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/BedrockTranscriptModelsConfigurationController/deleteBedrockTranscriptModelConfig` | deleteBedrockTranscriptModelConfig |
| GET | `/brain/api/admin/BedrockTranscriptModelsConfigurationController/findBedrockTranscriptModelConfigByCode` | findBedrockTranscriptModelConfigByCode |
| POST | `/brain/api/admin/BedrockTranscriptModelsConfigurationController/getBedrockTranscriptModels` | getBedrockTranscriptModels |
| POST | `/brain/api/admin/BedrockTranscriptModelsConfigurationController/insertBedrockTranscriptModelConfig` | insertBedrockTranscriptModelConfig |
| POST | `/brain/api/admin/BedrockTranscriptModelsConfigurationController/updateBedrockTranscriptModelConfig` | updateBedrockTranscriptModelConfig |

### `brave-search-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/BraveSearchConfigurationController/deleteGBraveSearchApiCredentials` | deleteGBraveSearchApiCredentials |
| POST | `/brain/api/admin/BraveSearchConfigurationController/fastInsertBraveSearchApiCredentials` | fastInsertBraveSearchApiCredentials |
| GET | `/brain/api/admin/BraveSearchConfigurationController/getBraveSearchApiCredentials` | getBraveSearchApiCredentials |
| GET | `/brain/api/admin/BraveSearchConfigurationController/getBraveSearchStatus` | getBraveSearchStatus |
| POST | `/brain/api/admin/BraveSearchConfigurationController/insertGBraveSearchApiCredentials` | insertGBraveSearchApiCredentials |
| GET | `/brain/api/admin/BraveSearchConfigurationController/searchGBraveSearchApiCredentialsByCode` | searchGBraveSearchApiCredentialsByCode |
| POST | `/brain/api/admin/BraveSearchConfigurationController/updateGBraveSearchApiCredentials` | updateGBraveSearchApiCredentials |

### `build-systems-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/BuildSystemsController/getBuildSystemConfigs` | getBuildSystemConfigs |
| GET | `/brain/api/admin/BuildSystemsController/getBuildSystemTypes` | getBuildSystemTypes |

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

### `company-systems-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/CompanySystemsController/getContentSystem` | getContentSystem |
| GET | `/brain/api/admin/CompanySystemsController/getContentSystemType` | getContentSystemType |
| GET | `/brain/api/admin/CompanySystemsController/getContentSystemTypes()` | getContentSystemTypes |
| GET | `/brain/api/admin/CompanySystemsController/getContentSystems` | getContentSystems |
| GET | `/brain/api/admin/CompanySystemsController/getProjectEndpoint` | getProjectEndpoint |
| POST | `/brain/api/admin/CompanySystemsController/getProjectEndpointByObjectRef` | getProjectEndpointByObjectRef |
| POST | `/brain/api/admin/CompanySystemsController/getProjectEndpointSystemInfos` | getProjectEndpointSystemInfos |

### `content-meta-infos-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/users/ContentMetaInfosController/findDocumentReferenceViewByCode` | findDocumentReferenceViewByCode |
| GET | `/brain/api/users/ContentMetaInfosController/getContentMetaInfos` | getContentMetaInfos |
| GET | `/brain/api/users/ContentMetaInfosController/getContentObject` | getContentObject |
| POST | `/brain/api/users/ContentMetaInfosController/searchByDocumentName` | searchByDocumentName |
| POST | `/brain/api/users/ContentMetaInfosController/searchByDocumentNamePaged` | searchByDocumentNamePaged |

### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `data-flow-meta-info-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/DataFlowMetaInfoController/getLocalDataFlow` | getLocalDataFlow |

### `decision-models-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/DecisionModelsController/getDecisionModelTypes` | getDecisionModelTypes |
| GET | `/brain/api/admin/DecisionModelsController/getRuntimeConfiguredDecisionModels` | getRuntimeConfiguredDecisionModels |

### `deepseek-chat-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/DeepseekChatModelsConfigurationController/deleteDeepseekChatModelConfig` | deleteDeepseekChatModelConfig |
| GET | `/brain/api/admin/DeepseekChatModelsConfigurationController/findDeepseekChatModelConfigByCode` | findDeepseekChatModelConfigByCode |
| POST | `/brain/api/admin/DeepseekChatModelsConfigurationController/getDeepseekModels` | getDeepseekChatModels |
| POST | `/brain/api/admin/DeepseekChatModelsConfigurationController/insertDeepseekChatModelConfig` | insertDeepseekChatModelConfig |
| POST | `/brain/api/admin/DeepseekChatModelsConfigurationController/updateDeepseekChatModelConfig` | updateDeepseekChatModelConfig |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/brain/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `embedding-models-controllers`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/EmbeddingModelsControllers/getEmbeddingModelTypes` | getEmbeddingModelTypes |
| GET | `/brain/api/admin/EmbeddingModelsControllers/getRuntimeConfiguredEmbeddingModels` | getRuntimeConfiguredEmbeddingModels |

### `functions-lookup-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/FunctionsLookupController/getAIToolsAndResourcesLibraryType` | getAIToolsAndResourcesLibraryType |
| GET | `/brain/api/admin/FunctionsLookupController/getAllFunctions` | getAllFunctions |
| GET | `/brain/api/admin/FunctionsLookupController/getAllFunctionsTree` | getAllFunctionsTree |
| GET | `/brain/api/admin/FunctionsLookupController/getAllLocalFunctions` | getAllLocalFunctions |
| GET | `/brain/api/admin/FunctionsLookupController/getAllLocalFunctionsTree` | getAllLocalFunctionsTree |
| GET | `/brain/api/admin/FunctionsLookupController/getToolSourceInfos` | getToolSourceInfos |

### `gebo-a-2-a-server-admin-controller`
| Method | Path | Operation |
|---|---|---|
| DELETE | `/brain/api/admin/GeboA2AServerAdminController/deleteA2AServer` | deleteA2AServer |
| GET | `/brain/api/admin/GeboA2AServerAdminController/findAll` | findAllA2AServer |
| GET | `/brain/api/admin/GeboA2AServerAdminController/findByCode` | findByCodeA2AServer |
| GET | `/brain/api/admin/GeboA2AServerAdminController/getExportKinds` | getExportKindsA2AServer |
| POST | `/brain/api/admin/GeboA2AServerAdminController/insertA2AServer` | insertA2AServer |
| POST | `/brain/api/admin/GeboA2AServerAdminController/updateA2AServer` | updateA2AServer |

### `gebo-admin-chat-pipelines-tooling-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/GeboAdminChatPipelinesToolingController/getPipelinesTooling` | getPipelinesTooling |
| POST | `/brain/api/admin/GeboAdminChatPipelinesToolingController/saveStepTooling` | saveStepTooling |

### `gebo-admin-chat-profiles-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/GeboAdminChatProfilesConfigurationController/deleteChatProfile` | deleteChatProfile |
| GET | `/brain/api/admin/GeboAdminChatProfilesConfigurationController/findChatProfileConfigurationByCode` | findChatProfileConfigurationByCode |
| POST | `/brain/api/admin/GeboAdminChatProfilesConfigurationController/getAllChatProfileConfiguration` | getAllChatProfileConfiguration |
| POST | `/brain/api/admin/GeboAdminChatProfilesConfigurationController/getChatProfileConfigurationByQbe` | getChatProfileConfigurationByQbe |
| POST | `/brain/api/admin/GeboAdminChatProfilesConfigurationController/insertChatProfile` | insertChatProfile |
| POST | `/brain/api/admin/GeboAdminChatProfilesConfigurationController/updateChatProfile` | updateChatProfile |

### `gebo-admin-chat-rules-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/GeboAdminChatRulesController/checkSharedRuleConflicts` | checkSharedRuleConflicts |
| POST | `/brain/api/admin/GeboAdminChatRulesController/createSharedRule` | createSharedRule |
| DELETE | `/brain/api/admin/GeboAdminChatRulesController/deleteSharedRule` | deleteSharedRule |
| GET | `/brain/api/admin/GeboAdminChatRulesController/getSharedRules` | getSharedRules |
| POST | `/brain/api/admin/GeboAdminChatRulesController/updateSharedRule` | updateSharedRule |

### `gebo-admin-prompt-use-info-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/GeboAdminPromptUseController/findAll` | findAllPromptUseInfo |
| GET | `/brain/api/admin/GeboAdminPromptUseController/findByCode` | findByCode |
| GET | `/brain/api/admin/GeboAdminPromptUseController/findByModule` | findByModule |

### `gebo-admin-prompts-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/GeboAdminPromptsController/deletePromptConfig` | deletePromptConfig |
| GET | `/brain/api/admin/GeboAdminPromptsController/findGPromptUseInfoByUseCode` | findGPromptUseInfoByUseCode |
| GET | `/brain/api/admin/GeboAdminPromptsController/findPromptConfigByAgentId` | findPromptConfigByAgentId |
| GET | `/brain/api/admin/GeboAdminPromptsController/findPromptConfigByCode` | findPromptConfigByCode |
| GET | `/brain/api/admin/GeboAdminPromptsController/getAllPromptConfigsLightList` | getAllPromptConfigsLightList |
| GET | `/brain/api/admin/GeboAdminPromptsController/getAllowedPromptPlaceholders` | getAllowedPromptPlaceholders |
| GET | `/brain/api/admin/GeboAdminPromptsController/getKnownPromptPlaceholders` | getKnownPromptPlaceholders |
| GET | `/brain/api/admin/GeboAdminPromptsController/getPromptCategories` | getPromptCategories |
| POST | `/brain/api/admin/GeboAdminPromptsController/getPromptConfigByFilter` | getPromptConfigByFilter |
| POST | `/brain/api/admin/GeboAdminPromptsController/insertPromptConfig` | insertPromptConfig |
| GET | `/brain/api/admin/GeboAdminPromptsController/isPromptTemplateEditingEnabled` | isPromptTemplateEditingEnabled |
| POST | `/brain/api/admin/GeboAdminPromptsController/updatePromptConfig` | updatePromptConfig |

### `gebo-admin-rag-autotune-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/GeboAdminRagAutotuneController/getLatestComputedVectorStores` | getLatestComputedVectorStores |

### `gebo-agent-admin-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/GeboAgentAdminController/checkAgentToolCollisions` | checkAgentToolCollisions |
| POST | `/brain/api/admin/GeboAgentAdminController/copyAgent` | copyAgent |
| DELETE | `/brain/api/admin/GeboAgentAdminController/deleteAgent` | deleteAgent |
| GET | `/brain/api/admin/GeboAgentAdminController/getAgentByCode` | getAgentByCode |
| GET | `/brain/api/admin/GeboAgentAdminController/getAgentRoles` | getAgentRoles |
| GET | `/brain/api/admin/GeboAgentAdminController/getAgents` | getAgents |
| GET | `/brain/api/admin/GeboAgentAdminController/getAgentsChoices` | getAgentsChoices |
| GET | `/brain/api/admin/GeboAgentAdminController/getPromptTemplateByAgentId` | getPromptTemplatesByAgentId |
| POST | `/brain/api/admin/GeboAgentAdminController/insertAgent` | insertAgent |
| POST | `/brain/api/admin/GeboAgentAdminController/updateAgent` | updateAgent |

### `gebo-agents-network-admin-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/GeboAgentsNetworkAdminController/copyAgentsNetwork` | copyAgentsNetwork |
| POST | `/brain/api/admin/GeboAgentsNetworkAdminController/deleteAgentsNetwork` | deleteAgentsNetwork |
| GET | `/brain/api/admin/GeboAgentsNetworkAdminController/getAgentConfigs` | getAgentConfigs |
| GET | `/brain/api/admin/GeboAgentsNetworkAdminController/getAgentConfigsByServiceId` | getAgentConfigsByServiceId |
| GET | `/brain/api/admin/GeboAgentsNetworkAdminController/getAgentServices` | getAgentServices |
| GET | `/brain/api/admin/GeboAgentsNetworkAdminController/getAgentTypesCompatibility` | getAgentTypesCompatibility |
| POST | `/brain/api/admin/GeboAgentsNetworkAdminController/getAgentsMountedTools` | getAgentsMountedTools |
| GET | `/brain/api/admin/GeboAgentsNetworkAdminController/getAgentsNetwork` | getAgentsNetwork |
| GET | `/brain/api/admin/GeboAgentsNetworkAdminController/getAgentsNetworkByCode` | getAgentsNetworkByCode |
| GET | `/brain/api/admin/GeboAgentsNetworkAdminController/getCompatibleNextServices` | getCompatibleNextServices |
| GET | `/brain/api/admin/GeboAgentsNetworkAdminController/getCompatiblePreviousServices` | getCompatiblePreviousServices |
| GET | `/brain/api/admin/GeboAgentsNetworkAdminController/getNetworkAdapterServices` | getNetworkAdapterServices |
| GET | `/brain/api/admin/GeboAgentsNetworkAdminController/getNetworkServiceFactories` | getNetworkServiceFactories |
| POST | `/brain/api/admin/GeboAgentsNetworkAdminController/insertAgentsNetwork` | insertAgentsNetwork |
| POST | `/brain/api/admin/GeboAgentsNetworkAdminController/updateAgentsNetwork` | updateAgentsNetwork |
| POST | `/brain/api/admin/GeboAgentsNetworkAdminController/validateAgentsNetwork` | validateAgentsNetwork |

### `gebo-angular-form-group-meta-info-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/AngularFormGroupController/checkDeletableByGObjectRef` | checkDeletableByGObjectRef |
| POST | `/brain/api/admin/AngularFormGroupController/checkDeletableBySimpleGObjectRef` | checkDeletableBySimpleObjectRef |
| GET | `/brain/api/admin/AngularFormGroupController/getFormGroupsMetaInfos` | getFormGroupsMetaInfos |

### `gebo-chat-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/users/GeboDirectModelChatController/chat` | chat |
| GET | `/brain/api/users/GeboDirectModelChatController/getChatModelMetaInfos` | getChatModelMetaInfos |
| GET | `/brain/api/users/GeboDirectModelChatController/getChatModelUserInfo` | getChatModelUserInfo |
| GET | `/brain/api/users/GeboDirectModelChatController/getProviderCapabilities` | getProviderCapabilities |
| GET | `/brain/api/users/GeboDirectModelChatController/getVisibleKnowledgeBases` | getVisibleKnowledgeBases |
| POST | `/brain/api/users/GeboDirectModelChatController/streamResponse` | streamResponse |

### `gebo-chat-libraries-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/users/GeboChatLibrariesController/downloadChatLibraryResource` | downloadChatLibraryResource |
| GET | `/brain/api/users/GeboChatLibrariesController/getChatLibraryInstance` | getChatLibraryInstance |

### `gebo-chat-pipelines-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/users/GeboChatPipelinesController/defaultPersonalPipelinesChatMenu` | getDefaultPersonalPipelinesChatMenu |
| POST | `/brain/api/users/GeboChatPipelinesController/executeChatPipeline` | executeChatPipeline |
| POST | `/brain/api/users/GeboChatPipelinesController/executeDefaultChatPipeline` | executeDefaultChatPipeline |
| POST | `/brain/api/users/GeboChatPipelinesController/executeDefaultRespawnAgenticChat` | executeDefaultRespawnAgenticChat |
| POST | `/brain/api/users/GeboChatPipelinesController/executeRespawnAgenticChat` | executeRespawnAgenticChat |
| GET | `/brain/api/users/GeboChatPipelinesController/pendingNetworkOfAgentsSuspension` | pendingNetworkOfAgentsSuspension |
| GET | `/brain/api/users/GeboChatPipelinesController/personalPipelinesChatMenu` | getPersonalPipelinesChatMenu |
| GET | `/brain/api/users/GeboChatPipelinesController/stopChatPipeline` | stopChatPipeline |
| POST | `/brain/api/users/GeboChatPipelinesController/streamChatPipeline` | streamChatPipeline |
| POST | `/brain/api/users/GeboChatPipelinesController/streamDefaultChatPipeline` | streamDefaultChatPipeline |
| POST | `/brain/api/users/GeboChatPipelinesController/streamDefaultRespawnAgenticChat` | streamDefaultRespawnAgenticChat |
| POST | `/brain/api/users/GeboChatPipelinesController/streamRespawnAgenticChat` | streamRespawnAgenticChat |

### `gebo-chat-profile-lookup-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/users/GeboChatProfileLookupController/findChatProfileConfigurationLookupByCode` | findChatProfileConfigurationLookupByCode |
| POST | `/brain/api/users/GeboChatProfileLookupController/getAllChatProfileConfigurationLoookup` | getAllChatProfileConfigurationLoookup |
| POST | `/brain/api/users/GeboChatProfileLookupController/getChatProfileConfigurationLookupByQbe` | getChatProfileConfigurationLookupByQbe |

### `gebo-chat-rules-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/users/GeboChatRulesController/checkRuleConflicts` | checkRuleConflicts |
| POST | `/brain/api/users/GeboChatRulesController/createRule` | createRule |
| DELETE | `/brain/api/users/GeboChatRulesController/deleteRule` | deleteRule |
| GET | `/brain/api/users/GeboChatRulesController/getChatRules` | getChatRules |
| GET | `/brain/api/users/GeboChatRulesController/getMyRules` | getMyRules |
| GET | `/brain/api/users/GeboChatRulesController/getSharedRulesAppliedToMe` | getSharedRulesAppliedToMe |
| GET | `/brain/api/users/GeboChatRulesController/proposeRules` | proposeRules |
| POST | `/brain/api/users/GeboChatRulesController/updateRule` | updateRule |

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

### `gebo-mcp-server-admin-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/GeboMCPServerAdminController/deleteMcpServer` | deleteMcpServer |
| GET | `/brain/api/admin/GeboMCPServerAdminController/findMcpServerByCode` | findMcpServerByCode |
| GET | `/brain/api/admin/GeboMCPServerAdminController/getAllMcpServers` | getAllMcpServers |
| POST | `/brain/api/admin/GeboMCPServerAdminController/getMcpServerPagedList` | getMcpServerPagedList |
| POST | `/brain/api/admin/GeboMCPServerAdminController/insertMcpServer` | insertMcpServer |
| POST | `/brain/api/admin/GeboMCPServerAdminController/setMcpServerAccessAcls` | setMcpServerAccessAcls |
| POST | `/brain/api/admin/GeboMCPServerAdminController/updateMcpServer` | updateMcpServer |

### `gebo-mcp-server-user-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/user/GeboMCPServerUserController/findAccessibleMcpServerByCode` | findAccessibleMcpServerByCode |
| GET | `/brain/api/user/GeboMCPServerUserController/getUsersCanAccessMcpServersList` | getUsersCanAccessMcpServersList |
| GET | `/brain/api/user/GeboMCPServerUserController/listAccessibleMcpServers` | listAccessibleMcpServers |

### `gebo-neo-4j-module-setup-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/GeboNeo4jModuleSetupController` | getNeo4jModuleSetupConfig |

### `gebo-rag-chat-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/users/GeboChatController/getChatModelUserInfoByChatProfileCode` | getChatModelUserInfoByChatProfileCode |
| GET | `/brain/api/users/GeboChatController/getChatProfileModelMetaInfos` | getChatProfileModelMetaInfos |
| GET | `/brain/api/users/GeboChatController/getProfileProviderModelCapabilities` | getProfileProviderModelCapabilities |
| GET | `/brain/api/users/GeboChatController/getVisibleKnowledgeBasesByProfileCode` | getVisibleKnowledgeBasesByProfileCode |
| GET | `/brain/api/users/GeboChatController/profiles` | getChatProfiles |
| POST | `/brain/api/users/GeboChatController/ragChat` | ragChat |
| POST | `/brain/api/users/GeboChatController/resolveForcedDocumentsRef` | resolveForcedDocumentsRef |
| POST | `/brain/api/users/GeboChatController/streamRagResponse` | streamRagResponse |

### `gebo-text-to-speech-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/users/GeboTextToSpeechController/isEnabled` | isEnabledTextToSpeech |
| POST | `/brain/api/users/GeboTextToSpeechController/speechText` | speechText |

### `gebo-transcript-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/users/GeboTranscriptController/isEnabled` | isEnabledTranscript |
| POST | `/brain/api/users/GeboTranscriptController/transcriptText` | transcriptText |

### `gebo-user-chat-uploads-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/users/GeboUserChatUploadsController/chatSessionUpload/{userSessionCode}` | chatSessionUpload |
| DELETE | `/brain/api/users/GeboUserChatUploadsController/deleteSessionUploads/{userSessionCode}` | deleteSessionUploads |
| GET | `/brain/api/users/GeboUserChatUploadsController/serveContent/{userSessionCode}/{uploadedContentId}` | serveContent |

### `gebo-user-chats-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/users/GeboUserChatsController/branchChat` | branchChat |
| POST | `/brain/api/users/GeboUserChatsController/changeChatDescription` | changeChatDescription |
| GET | `/brain/api/users/GeboUserChatsController/createCleanChatByChatProfileCode` | createCleanChatByChatProfileCode |
| GET | `/brain/api/users/GeboUserChatsController/createCleanChatByDefaultModel` | createCleanChatByDefaultModel |
| GET | `/brain/api/users/GeboUserChatsController/createCleanChatByModelCode` | createCleanChatByModelCode |
| DELETE | `/brain/api/users/GeboUserChatsController/deleteChat` | deleteChat |
| GET | `/brain/api/users/GeboUserChatsController/exportResponse2file` | exportResponse2file |
| GET | `/brain/api/users/GeboUserChatsController/getAnswerFeedbacks` | getAnswerFeedbacks |
| GET | `/brain/api/users/GeboUserChatsController/getChatHistory` | getChatHistory |
| GET | `/brain/api/users/GeboUserChatsController/getChatInfosByCode` | getChatInfosByCode |
| POST | `/brain/api/users/GeboUserChatsController/getChatInfosByQbe` | getChatInfosByQbe |
| GET | `/brain/api/users/GeboUserChatsController/getMyChats` | getMyChats |
| GET | `/brain/api/users/GeboUserChatsController/getMyChatsByContextCode` | getMyChatsByContextCode |
| GET | `/brain/api/users/GeboUserChatsController/getMyChatsPaged` | getMyChatsPaged |
| GET | `/brain/api/users/GeboUserChatsController/getUIConfig` | getUIConfig |
| GET | `/brain/api/users/GeboUserChatsController/isMinimalLLMSSetupDone` | isMinimalLLMSSetupDone |
| DELETE | `/brain/api/users/GeboUserChatsController/removeAnswerFeedback` | removeAnswerFeedback |
| POST | `/brain/api/users/GeboUserChatsController/setAnswerFeedback` | setAnswerFeedback |
| GET | `/brain/api/users/GeboUserChatsController/suggestChatDescription` | suggestChatDescription |

### `gebo-user-knowledge-base-semantic-search-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/users/GeboUserKnowledgeBaseSemanticSearchController/semanticSearch` | semanticSearch |

### `generic-open-ai-decision-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/GenericOpenAIDecisionModelsConfigurationController/deleteGenericOpenAIAPIDecisionModelConfig` | deleteGenericOpenAIAPIDecisionModelConfig |
| GET | `/brain/api/admin/GenericOpenAIDecisionModelsConfigurationController/findGenericOpenAIAPIDecisionModelConfigByCode` | findGenericOpenAIAPIDecisionModelConfigByCode |
| POST | `/brain/api/admin/GenericOpenAIDecisionModelsConfigurationController/getGenericOpenAIAPIDecisionModels` | getGenericOpenAIAPIDecisionModels |
| GET | `/brain/api/admin/GenericOpenAIDecisionModelsConfigurationController/getGenericOpenAIDecisionModelConfigs` | getGenericOpenAIDecisionModelConfigs |
| GET | `/brain/api/admin/GenericOpenAIDecisionModelsConfigurationController/getGenericOpenAIDecisionModelTypes` | getGenericOpenAIDecisionModelTypes |
| POST | `/brain/api/admin/GenericOpenAIDecisionModelsConfigurationController/insertGenericOpenAIAPIDecisionModelConfig` | insertGenericOpenAIAPIDecisionModelConfig |
| POST | `/brain/api/admin/GenericOpenAIDecisionModelsConfigurationController/updateGenericOpenAIAPIDecisionModelConfig` | updateGenericOpenAIAPIDecisionModelConfig |

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

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

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

### `google-vertex-chat-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/GoogleVertexModelsConfigurationController/deleteGoogleVertexChatModelConfig` | deleteGoogleVertexChatModelConfig |
| GET | `/brain/api/admin/GoogleVertexModelsConfigurationController/findGoogleVertexChatModelConfigByCode` | findGoogleVertexChatModelConfigByCode |
| POST | `/brain/api/admin/GoogleVertexModelsConfigurationController/getGoogleVertexChatModels` | getGoogleVertexChatModels |
| POST | `/brain/api/admin/GoogleVertexModelsConfigurationController/insertGoogleVertexChatModelConfig` | insertGoogleVertexChatModelConfig |
| POST | `/brain/api/admin/GoogleVertexModelsConfigurationController/updateGoogleVertexChatModelConfig` | updateGoogleVertexChatModelConfig |

### `google-vertex-embedding-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/GoogleVertexEmbeddingModelsConfigurationController/deleteGoogleVertexEmbeddingModelConfig` | deleteGoogleVertexEmbeddingModelConfig |
| GET | `/brain/api/admin/GoogleVertexEmbeddingModelsConfigurationController/findGoogleVertexEmbeddingModelConfigByCode` | findGoogleVertexEmbeddingModelConfigByCode |
| POST | `/brain/api/admin/GoogleVertexEmbeddingModelsConfigurationController/getGoogleVertexEmbeddingModels` | getGoogleVertexEmbeddingModels |
| POST | `/brain/api/admin/GoogleVertexEmbeddingModelsConfigurationController/insertGoogleVertexEmbeddingModelConfig` | insertGoogleVertexEmbeddingModelConfig |
| POST | `/brain/api/admin/GoogleVertexEmbeddingModelsConfigurationController/updateGoogleVertexEmbeddingModelConfig` | updateGoogleVertexEmbeddingModelConfig |

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

### `internal-messaging-topology-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/InternalMessagingTopologyController/getLocalTopology` | getLocalTopology |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/brain/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/brain/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

### `knowledge-base-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/KnowledgeBaseController/deleteKnowledgeBase` | deleteKnowledgeBase |
| GET | `/brain/api/admin/KnowledgeBaseController/findKnowledgeBaseByCode` | findKnowledgeBaseByCode |
| POST | `/brain/api/admin/KnowledgeBaseController/findKnowledgeBasesByQbe` | findKnowledgeBasesByQbe |
| GET | `/brain/api/admin/KnowledgeBaseController/getChildKnowledgeBases` | getChildKnowledgeBases |
| GET | `/brain/api/admin/KnowledgeBaseController/getKnowledgeBases` | getKnowledgeBases |
| POST | `/brain/api/admin/KnowledgeBaseController/insertKnowledgeBase` | insertKnowledgeBase |
| POST | `/brain/api/admin/KnowledgeBaseController/updateKnowledgeBase` | updateKnowledgeBase |

### `language-resources-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/users/LanguageResourcesController` | getAllResourcesByLanguage |
| GET | `/brain/api/users/LanguageResourcesController/getUIComponentLabels` | getUIComponentLabels |
| POST | `/brain/api/users/LanguageResourcesController/update` | update |

### `log-view-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/LogViewController/deleteJobStatus` | deleteJobStatus |
| POST | `/brain/api/admin/LogViewController/getJobMessagesPaged` | getJobMessagesPaged |
| POST | `/brain/api/admin/LogViewController/getJobsEntries` | getJobsEntries |
| POST | `/brain/api/admin/LogViewController/getJobsEntriesForClassName` | getJobsEntriesForClassName |
| POST | `/brain/api/admin/LogViewController/getJobsEntriesForJobType` | getJobsEntriesForJobType |
| POST | `/brain/api/admin/LogViewController/getJobsEntriesForProjectEndpoint` | getJobsEntriesForProjectEndpointLogView |

### `mcp-client-config-controller`
| Method | Path | Operation |
|---|---|---|
| DELETE | `/brain/api/admin/McpClientConfigController/deleteMCPClientConfig` | deleteMCPClientConfig |
| GET | `/brain/api/admin/McpClientConfigController/findMCPClientConfigByCode` | findMCPClientConfigByCode |
| POST | `/brain/api/admin/McpClientConfigController/findMCPClientConfigByQbe` | findMCPClientConfigByQbe |
| POST | `/brain/api/admin/McpClientConfigController/insertMCPClientConfig` | insertMCPClientConfig |
| POST | `/brain/api/admin/McpClientConfigController/listMCPClientConfig` | listMCPClientConfig |
| POST | `/brain/api/admin/McpClientConfigController/testAndDiscovery` | testAndDiscovery |
| POST | `/brain/api/admin/McpClientConfigController/updateMCPClientConfig` | updateMCPClientConfig |

### `mistral-ai-chat-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/MistralAIChatModelsConfigurationController/deleteMistralAIChatModelConfig` | deleteMistralAIChatModelConfig |
| GET | `/brain/api/admin/MistralAIChatModelsConfigurationController/findMistralAIChatModelConfigByCode` | findMistralAIChatModelConfigByCode |
| POST | `/brain/api/admin/MistralAIChatModelsConfigurationController/getMistralAIChatModels` | getMistralAIChatModels |
| POST | `/brain/api/admin/MistralAIChatModelsConfigurationController/insertMistralAIChatModelConfig` | insertMistralAIChatModelConfig |
| POST | `/brain/api/admin/MistralAIChatModelsConfigurationController/updateMistralAIChatModelConfig` | updateMistralAIChatModelConfig |

### `mistral-ai-embedding-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/MistralAIEmbeddingModelsConfigurationController/deleteMistralAIEmbeddingModelConfig` | deleteMistralAIEmbeddingModelConfig |
| GET | `/brain/api/admin/MistralAIEmbeddingModelsConfigurationController/findMistralAIEmbeddingModelConfigByCode` | findMistralAIEmbeddingModelConfigByCode |
| POST | `/brain/api/admin/MistralAIEmbeddingModelsConfigurationController/getMistralAIEmbeddingModels` | getMistralAIEmbeddingModels |
| POST | `/brain/api/admin/MistralAIEmbeddingModelsConfigurationController/insertMistralAIEmbeddingModelConfig` | insertMistralAIEmbeddingModelConfig |
| POST | `/brain/api/admin/MistralAIEmbeddingModelsConfigurationController/updateMistralAIEmbeddingModelConfig` | updateMistralAIEmbeddingModelConfig |

### `network-of-agents-condition-events-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/users/NetworkOfAgentsConditionEventsController/conditionEventStatus` | conditionEventStatus |
| POST | `/brain/api/users/NetworkOfAgentsConditionEventsController/deliverConditionEvent` | deliverConditionEvent |

### `ollama-chat-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/OllamaChatModelsConfigurationController/deleteOllamaChatModelConfig` | deleteOllamaChatModelConfig |
| GET | `/brain/api/admin/OllamaChatModelsConfigurationController/findOllamaChatModelConfigByCode` | findOllamaChatModelConfigByCode |
| POST | `/brain/api/admin/OllamaChatModelsConfigurationController/getOllamaModels` | getOllamaChatModels |
| POST | `/brain/api/admin/OllamaChatModelsConfigurationController/insertOllamaChatModelConfig` | insertOllamaChatModelConfig |
| POST | `/brain/api/admin/OllamaChatModelsConfigurationController/updateOllamaChatModelConfig` | updateOllamaChatModelConfig |

### `ollama-decision-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/OllamaDecisionModelsConfigurationController/deleteOllamaDecisionModelConfig` | deleteOllamaDecisionModelConfig |
| GET | `/brain/api/admin/OllamaDecisionModelsConfigurationController/findOllamaDecisionModelConfigByCode` | findOllamaDecisionModelConfigByCode |
| POST | `/brain/api/admin/OllamaDecisionModelsConfigurationController/getOllamaDecisionModels` | getOllamaDecisionModels |
| POST | `/brain/api/admin/OllamaDecisionModelsConfigurationController/insertOllamaDecisionModelConfig` | insertOllamaDecisionModelConfig |
| POST | `/brain/api/admin/OllamaDecisionModelsConfigurationController/updateOllamaDecisionModelConfig` | updateOllamaDecisionModelConfig |

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

### `open-ai-decision-models-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/OpenAIDecisionModelsConfigurationController/deleteOpenAIDecisionModelConfig` | deleteOpenAIDecisionModelConfig |
| GET | `/brain/api/admin/OpenAIDecisionModelsConfigurationController/findOpenAIDecisionModelConfigByCode` | findOpenAIDecisionModelConfigByCode |
| POST | `/brain/api/admin/OpenAIDecisionModelsConfigurationController/getOpenAIDecisionModels` | getOpenAIDecisionModels |
| POST | `/brain/api/admin/OpenAIDecisionModelsConfigurationController/insertOpenAIDecisionModelConfig` | insertOpenAIDecisionModelConfig |
| POST | `/brain/api/admin/OpenAIDecisionModelsConfigurationController/updateOpenAIDecisionModelConfig` | updateOpenAIDecisionModelConfig |

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

### `projects-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/ProjectsController/deleteProject` | deleteProject |
| GET | `/brain/api/admin/ProjectsController/findChildProjects` | findChildProjects |
| GET | `/brain/api/admin/ProjectsController/findOtherKnowledgeBaseIncludableProjects` | findOtherKnowledgeBaseIncludableProjects |
| GET | `/brain/api/admin/ProjectsController/findProjectByCode` | findProjectByCode |
| GET | `/brain/api/admin/ProjectsController/findRootProjects` | findRootProjects |
| POST | `/brain/api/admin/ProjectsController/getChildDocuments` | getChildDocuments |
| POST | `/brain/api/admin/ProjectsController/getChildFolders` | getChildFolders |
| GET | `/brain/api/admin/ProjectsController/getProjects` | getProjects |
| POST | `/brain/api/admin/ProjectsController/getRootDocuments` | getRootDocuments |
| POST | `/brain/api/admin/ProjectsController/getRootFolders` | getRootFolders |
| POST | `/brain/api/admin/ProjectsController/insertProject` | insertProject |
| POST | `/brain/api/admin/ProjectsController/searchProjects` | searchProjects |
| POST | `/brain/api/admin/ProjectsController/searchProjectsByQbe` | searchProjectsByQbe |
| POST | `/brain/api/admin/ProjectsController/updateProject` | updateProject |

### `prompt-templates-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/PromptTemplatesController/getDefaultPrompt` | getDefaultPrompt |
| POST | `/brain/api/admin/PromptTemplatesController/getDefaultPromptForChatModel` | getDefaultPromptForChatModel |
| POST | `/brain/api/admin/PromptTemplatesController/getDefaultPromptForChatModelReference` | getDefaultPromptForChatModelReference |

### `provider-deals-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/ProviderDealsController/assignApiKey` | assignApiKey |
| POST | `/brain/api/admin/ProviderDealsController/createProviderDeal` | createProviderDeal |
| POST | `/brain/api/admin/ProviderDealsController/deleteProviderDeal` | deleteProviderDeal |
| GET | `/brain/api/admin/ProviderDealsController/getConfiguredProviderIds` | getConfiguredProviderIds |
| GET | `/brain/api/admin/ProviderDealsController/getCurrencies` | getCurrencies |
| GET | `/brain/api/admin/ProviderDealsController/getProviderApiKeys` | getProviderApiKeys |
| GET | `/brain/api/admin/ProviderDealsController/getProviderCurrency` | getProviderCurrency |
| GET | `/brain/api/admin/ProviderDealsController/getProviderDeal` | getProviderDeal |
| GET | `/brain/api/admin/ProviderDealsController/getProviderDealProviderIds` | getProviderDealProviderIds |
| GET | `/brain/api/admin/ProviderDealsController/getProviderDeals` | getProviderDeals |
| GET | `/brain/api/admin/ProviderDealsController/getProviderModelPrices` | getProviderModelPrices |
| POST | `/brain/api/admin/ProviderDealsController/refreshImportedLimits` | refreshImportedLimits |
| POST | `/brain/api/admin/ProviderDealsController/removeApiKey` | removeApiKey |
| POST | `/brain/api/admin/ProviderDealsController/updateDescription` | updateDescription |
| POST | `/brain/api/admin/ProviderDealsController/updateFlatConditions` | updateFlatConditions |
| POST | `/brain/api/admin/ProviderDealsController/updateModelPricing` | updateModelPricing |
| POST | `/brain/api/admin/ProviderDealsController/updateProviderCurrency` | updateProviderCurrency |
| POST | `/brain/api/admin/ProviderDealsController/updateSpendingLimits` | updateSpendingLimits |

### `ranker-models-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/api/admin/RankerModelsController/getRankerModelTypes` | getRankerModelTypes |
| GET | `/brain/api/admin/RankerModelsController/getRuntimeConfiguredRankerModels` | getRuntimeConfiguredRankerModels |

### `searxng-search-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/SearxngSearchConfigurationController/deleteGSearxngSearchApiCredentials` | deleteGSearxngSearchApiCredentials |
| POST | `/brain/api/admin/SearxngSearchConfigurationController/fastInsertSearxngSearchApiCredentials` | fastInsertSearxngSearchApiCredentials |
| GET | `/brain/api/admin/SearxngSearchConfigurationController/getSearxngSearchApiCredentials` | getSearxngSearchApiCredentials |
| GET | `/brain/api/admin/SearxngSearchConfigurationController/getSearxngSearchStatus` | getSearxngSearchStatus |
| POST | `/brain/api/admin/SearxngSearchConfigurationController/insertGSearxngSearchApiCredentials` | insertGSearxngSearchApiCredentials |
| GET | `/brain/api/admin/SearxngSearchConfigurationController/searchGSearxngSearchApiCredentialsByCode` | searchGSearxngSearchApiCredentialsByCode |
| POST | `/brain/api/admin/SearxngSearchConfigurationController/updateGSearxngSearchApiCredentials` | updateGSearxngSearchApiCredentials |

### `serpapi-search-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/SerpapiSearchConfigurationController/deleteGSerpapiSearchApiCredentials` | deleteGSerpapiSearchApiCredentials |
| POST | `/brain/api/admin/SerpapiSearchConfigurationController/fastInsertSerpapiSearchApiCredentials` | fastInsertSerpapiSearchApiCredentials |
| GET | `/brain/api/admin/SerpapiSearchConfigurationController/getSerpapiSearchApiCredentials` | getSerpapiSearchApiCredentials |
| GET | `/brain/api/admin/SerpapiSearchConfigurationController/getSerpapiSearchStatus` | getSerpapiSearchStatus |
| POST | `/brain/api/admin/SerpapiSearchConfigurationController/insertGSerpapiSearchApiCredentials` | insertGSerpapiSearchApiCredentials |
| GET | `/brain/api/admin/SerpapiSearchConfigurationController/searchGSerpapiSearchApiCredentialsByCode` | searchGSerpapiSearchApiCredentialsByCode |
| POST | `/brain/api/admin/SerpapiSearchConfigurationController/updateGSerpapiSearchApiCredentials` | updateGSerpapiSearchApiCredentials |

### `tavily-search-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/TavilySearchConfigurationController/deleteGTavilySearchApiCredentials` | deleteGTavilySearchApiCredentials |
| POST | `/brain/api/admin/TavilySearchConfigurationController/fastInsertTavilySearchApiCredentials` | fastInsertTavilySearchApiCredentials |
| GET | `/brain/api/admin/TavilySearchConfigurationController/getTavilySearchApiCredentials` | getTavilySearchApiCredentials |
| GET | `/brain/api/admin/TavilySearchConfigurationController/getTavilySearchStatus` | getTavilySearchStatus |
| POST | `/brain/api/admin/TavilySearchConfigurationController/insertGTavilySearchApiCredentials` | insertGTavilySearchApiCredentials |
| GET | `/brain/api/admin/TavilySearchConfigurationController/searchGTavilySearchApiCredentialsByCode` | searchGTavilySearchApiCredentialsByCode |
| POST | `/brain/api/admin/TavilySearchConfigurationController/updateGTavilySearchApiCredentials` | updateGTavilySearchApiCredentials |

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

### `ui-text-resources-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/brain/public/UITextResourcesController` | getI18n |
| GET | `/brain/public/UITextResourcesController/getUiTextResourcesModule` | getUiTextResourcesModule |
| POST | `/brain/public/UITextResourcesController/updateUIExistingTexts` | updateUIExistingTexts |

### `user-knowledge-base-browsing-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/user/UserKnowledgeBaseBrowsingController/browseKnowledgeBasePath` | browseKnowledgeBasePath |
| GET | `/brain/api/user/UserKnowledgeBaseBrowsingController/getAccessibleRootKnowledgeBases` | getAccessibleRootKnowledgeBases |
| POST | `/brain/api/user/UserKnowledgeBaseBrowsingController/getKnowledgeBaseNavigationStatus` | getKnowledgeBaseNavigationStatus |
| GET | `/brain/api/user/UserKnowledgeBaseBrowsingController/getKnowledgeBaseRoots` | getKnowledgeBaseRoots |
| POST | `/brain/api/user/UserKnowledgeBaseBrowsingController/getVisibleKnowledgeBaseByCodes` | getVisibleKnowledgeBaseByCodes |

### `waiting-conditions-admin-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/admin/WaitingConditionsAdminController/abandon` | abandon_1 |
| GET | `/brain/api/admin/WaitingConditionsAdminController/allWaitingConditions` | allWaitingConditions |
| POST | `/brain/api/admin/WaitingConditionsAdminController/goOnWithout` | goOnWithout_1 |

### `waiting-conditions-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/brain/api/users/WaitingConditionsController/abandon` | abandon |
| POST | `/brain/api/users/WaitingConditionsController/goOnWithout` | goOnWithout |
| GET | `/brain/api/users/WaitingConditionsController/waitingConditions` | waitingConditions |

## vectorizator.gebo.ai — port 13002 (`vectorizator-gebo-ai`) — context-path `/vectorizator`

3 controller(s), 6 endpoint(s):


### `gebo-angular-form-group-meta-info-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/vectorizator/api/admin/AngularFormGroupController/checkDeletableByGObjectRef` | checkDeletableByGObjectRef |
| POST | `/vectorizator/api/admin/AngularFormGroupController/checkDeletableBySimpleGObjectRef` | checkDeletableBySimpleObjectRef |
| GET | `/vectorizator/api/admin/AngularFormGroupController/getFormGroupsMetaInfos` | getFormGroupsMetaInfos |

### `gebo-core-analisys-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/vectorizator/api/admin/GeboCoreAnalisysController/drillDown` | coreDrillDown |
| GET | `/vectorizator/api/admin/GeboCoreAnalisysController/getTopLevelKnowledgeBaseCategory` | getTopLevelKnowledgeBaseCategory |

### `internal-messaging-topology-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/vectorizator/api/admin/InternalMessagingTopologyController/getLocalTopology` | getLocalTopology |

## graphicator.gebo.ai — port 13003 (`graphicator-gebo-ai`) — context-path `/graphicator`

3 controller(s), 13 endpoint(s):


### `gebo-angular-form-group-meta-info-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/graphicator/api/admin/AngularFormGroupController/checkDeletableByGObjectRef` | checkDeletableByGObjectRef |
| POST | `/graphicator/api/admin/AngularFormGroupController/checkDeletableBySimpleGObjectRef` | checkDeletableBySimpleObjectRef |
| GET | `/graphicator/api/admin/AngularFormGroupController/getFormGroupsMetaInfos` | getFormGroupsMetaInfos |

### `graph-rag-configuration-controller`
| Method | Path | Operation |
|---|---|---|
| DELETE | `/graphicator/api/admin/GraphRagConfigurationController/deleteGraphRagExtractionConfig` | deleteGraphRagExtractionConfig |
| GET | `/graphicator/api/admin/GraphRagConfigurationController/findGraphRagExtractionConfigByCode` | findGraphRagExtractionConfigByCode |
| GET | `/graphicator/api/admin/GraphRagConfigurationController/findGraphRagExtractionConfigByKnowledgeBase` | findGraphRagExtractionConfigByKnowledgeBase |
| GET | `/graphicator/api/admin/GraphRagConfigurationController/findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode` | findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode |
| POST | `/graphicator/api/admin/GraphRagConfigurationController/findGraphRagExtractionConfigByProjectEndpointGObjectRef` | findGraphRagExtractionConfigByProjectEndpointGObjectRef |
| GET | `/graphicator/api/admin/GraphRagConfigurationController/getDefaultGraphRagExtractionConfig` | getDefaultGraphRagExtractionConfig |
| GET | `/graphicator/api/admin/GraphRagConfigurationController/getSystemGraphRagExtractionConfig` | getSystemGraphRagExtractionConfig |
| POST | `/graphicator/api/admin/GraphRagConfigurationController/instertGraphRagExtractionConfig` | instertGraphRagExtractionConfig |
| POST | `/graphicator/api/admin/GraphRagConfigurationController/saveGraphRagExtractionConfig` | saveGraphRagExtractionConfig |

### `internal-messaging-topology-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/graphicator/api/admin/InternalMessagingTopologyController/getLocalTopology` | getLocalTopology |

## chunker.gebo.ai — port 13004 (`chunker-gebo-ai`) — context-path `/chunker`

5 controller(s), 17 endpoint(s):


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

### `gebo-angular-form-group-meta-info-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/chunker/api/admin/AngularFormGroupController/checkDeletableByGObjectRef` | checkDeletableByGObjectRef |
| POST | `/chunker/api/admin/AngularFormGroupController/checkDeletableBySimpleGObjectRef` | checkDeletableBySimpleObjectRef |
| GET | `/chunker/api/admin/AngularFormGroupController/getFormGroupsMetaInfos` | getFormGroupsMetaInfos |

### `internal-messaging-topology-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/chunker/api/admin/InternalMessagingTopologyController/getLocalTopology` | getLocalTopology |

## git.gebo.ai — port 13005 (`git-gebo-ai`) — context-path `/git`

7 controller(s), 23 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/git/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/git/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/git/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `gebo-angular-form-group-meta-info-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/git/api/admin/AngularFormGroupController/checkDeletableByGObjectRef` | checkDeletableByGObjectRef |
| POST | `/git/api/admin/AngularFormGroupController/checkDeletableBySimpleGObjectRef` | checkDeletableBySimpleObjectRef |
| GET | `/git/api/admin/AngularFormGroupController/getFormGroupsMetaInfos` | getFormGroupsMetaInfos |

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

### `internal-messaging-topology-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/git/api/admin/InternalMessagingTopologyController/getLocalTopology` | getLocalTopology |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/git/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/git/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/git/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

## filesystem.gebo.ai — port 13006 (`filesystem-gebo-ai`) — context-path `/filesystem`

9 controller(s), 31 endpoint(s):


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

### `gebo-angular-form-group-meta-info-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/filesystem/api/admin/AngularFormGroupController/checkDeletableByGObjectRef` | checkDeletableByGObjectRef |
| POST | `/filesystem/api/admin/AngularFormGroupController/checkDeletableBySimpleGObjectRef` | checkDeletableBySimpleObjectRef |
| GET | `/filesystem/api/admin/AngularFormGroupController/getFormGroupsMetaInfos` | getFormGroupsMetaInfos |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/filesystem/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `internal-messaging-topology-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/filesystem/api/admin/InternalMessagingTopologyController/getLocalTopology` | getLocalTopology |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/filesystem/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/filesystem/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/filesystem/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

## uploads.gebo.ai — port 13007 (`uploads-gebo-ai`) — context-path `/uploads`

9 controller(s), 31 endpoint(s):


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
| POST | `/uploads/api/admin/FileUploadController/upload/{handShakeCode}` | uploadUploadsEndpoint |
| POST | `/uploads/api/admin/FileUploadController/uploadToEndpoint/{endpointCode}` | uploadToEndpoint |

### `file-uploads-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/uploads/api/admin/FileUploadsController/createUploadsFolder` | createUploadsFolder |
| POST | `/uploads/api/admin/FileUploadsController/deleteUploadedFiles` | deleteUploadedFiles |
| POST | `/uploads/api/admin/FileUploadsController/deleteUploadsEndpoint` | deleteUploadsEndpoint |
| GET | `/uploads/api/admin/FileUploadsController/findUploadsEndpointsByProject` | findUploadsEndpointsByProject |
| POST | `/uploads/api/admin/FileUploadsController/findUploadsEndpointsByQbe` | findUploadsEndpointsByQbe |
| GET | `/uploads/api/admin/FileUploadsController/getFileSystemSystemTypes` | getUploadsSystemTypes |
| GET | `/uploads/api/admin/FileUploadsController/getUploadableFilesExtensions` | getUploadableFilesExtensions |
| GET | `/uploads/api/admin/FileUploadsController/getUploadsSystems` | getUploadsSystems |
| POST | `/uploads/api/admin/FileUploadsController/insertUploadsEndpoint` | insertUploadsEndpoint |
| GET | `/uploads/api/admin/FileUploadsController/listUploadedFiles` | listUploadedFiles |
| GET | `/uploads/api/admin/FileUploadsController/listUploadedFilesTree` | listUploadedFilesTree |
| POST | `/uploads/api/admin/FileUploadsController/publishUploadsEndpoint` | publishUploadsEndpoint |
| POST | `/uploads/api/admin/FileUploadsController/updateUploadsEndpoint` | updateUploadsEndpoint |

### `gebo-angular-form-group-meta-info-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/uploads/api/admin/AngularFormGroupController/checkDeletableByGObjectRef` | checkDeletableByGObjectRef |
| POST | `/uploads/api/admin/AngularFormGroupController/checkDeletableBySimpleGObjectRef` | checkDeletableBySimpleObjectRef |
| GET | `/uploads/api/admin/AngularFormGroupController/getFormGroupsMetaInfos` | getFormGroupsMetaInfos |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/uploads/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `internal-messaging-topology-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/uploads/api/admin/InternalMessagingTopologyController/getLocalTopology` | getLocalTopology |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/uploads/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/uploads/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/uploads/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

### `uploads-browsing-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/uploads/api/admin/UploadsBrowsingController/browseUploadsEndpointPath` | browseUploadsEndpointPath |
| POST | `/uploads/api/admin/UploadsBrowsingController/getUploadsEndpointNavigationStatus` | getUploadsEndpointNavigationStatus |
| GET | `/uploads/api/admin/UploadsBrowsingController/getUploadsEndpointRoots` | getUploadsEndpointRoots |
| GET | `/uploads/api/admin/UploadsBrowsingController/serveUploadsEndpointFile` | serveUploadsEndpointFile |

## userspace.gebo.ai — port 13008 (`userspace-gebo-ai`) — context-path `/userspace`

8 controller(s), 30 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/userspace/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/userspace/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/userspace/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `gebo-angular-form-group-meta-info-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/userspace/api/admin/AngularFormGroupController/checkDeletableByGObjectRef` | checkDeletableByGObjectRef |
| POST | `/userspace/api/admin/AngularFormGroupController/checkDeletableBySimpleGObjectRef` | checkDeletableBySimpleObjectRef |
| GET | `/userspace/api/admin/AngularFormGroupController/getFormGroupsMetaInfos` | getFormGroupsMetaInfos |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/userspace/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `internal-messaging-topology-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/userspace/api/admin/InternalMessagingTopologyController/getLocalTopology` | getLocalTopology |

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
| POST | `/userspace/api/user/UserspaceUploadController/upload/{userspaceFolderCode}` | uploadUserspace |

## sharepoint.gebo.ai — port 13009 (`sharepoint-gebo-ai`) — context-path `/sharepoint`

9 controller(s), 46 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/sharepoint/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/sharepoint/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/sharepoint/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `gebo-angular-form-group-meta-info-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/sharepoint/api/admin/AngularFormGroupController/checkDeletableByGObjectRef` | checkDeletableByGObjectRef |
| POST | `/sharepoint/api/admin/AngularFormGroupController/checkDeletableBySimpleGObjectRef` | checkDeletableBySimpleObjectRef |
| GET | `/sharepoint/api/admin/AngularFormGroupController/getFormGroupsMetaInfos` | getFormGroupsMetaInfos |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/sharepoint/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `internal-messaging-topology-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/sharepoint/api/admin/InternalMessagingTopologyController/getLocalTopology` | getLocalTopology |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/sharepoint/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/sharepoint/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/sharepoint/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

### `share-point-search-service-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/sharepoint/api/users/SharePointSearchServiceController/aggregate` | restAggregateSharePoint |
| POST | `/sharepoint/api/users/SharePointSearchServiceController/createCustomTemplateParamsMap` | restCreateCustomTemplateParamsMapSharePoint |
| POST | `/sharepoint/api/users/SharePointSearchServiceController/extractRelatedAnalisysReferences` | restExtractRelatedAnalisysReferencesSharePoint |
| GET | `/sharepoint/api/users/SharePointSearchServiceController/findSystemById` | restFindSystemByIdSharePoint |
| POST | `/sharepoint/api/users/SharePointSearchServiceController/findSystemBySearchResult` | restFindSystemBySearchResultSharePoint |
| GET | `/sharepoint/api/users/SharePointSearchServiceController/getCachedCatalogues` | restGetCachedCataloguesSharePoint |
| GET | `/sharepoint/api/users/SharePointSearchServiceController/getCataloguesListSample` | restGetCataloguesListSampleSharePoint |
| GET | `/sharepoint/api/users/SharePointSearchServiceController/getDescription` | restGetDescriptionSharePoint |
| GET | `/sharepoint/api/users/SharePointSearchServiceController/getId` | restGetIdSharePoint |
| GET | `/sharepoint/api/users/SharePointSearchServiceController/getMessagingModuleId` | restGetMessagingModuleIdSharePoint |
| GET | `/sharepoint/api/users/SharePointSearchServiceController/getNativePromptTemplateUseCode` | restGetNativePromptTemplateUseCodeSharePoint |
| GET | `/sharepoint/api/users/SharePointSearchServiceController/getProductId` | restGetProductIdSharePoint |
| GET | `/sharepoint/api/users/SharePointSearchServiceController/getQueriesGenerationPromptUseCode` | restGetQueriesGenerationPromptUseCodeSharePoint |
| GET | `/sharepoint/api/users/SharePointSearchServiceController/getSearchableSystems` | restGetSearchableSystemsSharePoint |
| GET | `/sharepoint/api/users/SharePointSearchServiceController/isEnabled` | restIsEnabledSharePoint |
| POST | `/sharepoint/api/users/SharePointSearchServiceController/nativeSearch` | restNativeSearchSharePoint |
| POST | `/sharepoint/api/users/SharePointSearchServiceController/search` | restSearchSharePoint |

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

9 controller(s), 46 endpoint(s):


### `confluence-browsing-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/confluence/api/admin/ConfluenceBrowsingController/browseConfluencePath` | browseConfluencePath |
| POST | `/confluence/api/admin/ConfluenceBrowsingController/getConfluenceNavigationStatus` | getConfluenceNavigationStatus |
| GET | `/confluence/api/admin/ConfluenceBrowsingController/getConfluenceRoots` | getConfluenceRoots |

### `confluence-search-service-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/confluence/api/users/ConfluenceSearchServiceController/aggregate` | restAggregateConfluence |
| POST | `/confluence/api/users/ConfluenceSearchServiceController/createCustomTemplateParamsMap` | restCreateCustomTemplateParamsMapConfluence |
| POST | `/confluence/api/users/ConfluenceSearchServiceController/extractRelatedAnalisysReferences` | restExtractRelatedAnalisysReferencesConfluence |
| GET | `/confluence/api/users/ConfluenceSearchServiceController/findSystemById` | restFindSystemByIdConfluence |
| POST | `/confluence/api/users/ConfluenceSearchServiceController/findSystemBySearchResult` | restFindSystemBySearchResultConfluence |
| GET | `/confluence/api/users/ConfluenceSearchServiceController/getCachedCatalogues` | restGetCachedCataloguesConfluence |
| GET | `/confluence/api/users/ConfluenceSearchServiceController/getCataloguesListSample` | restGetCataloguesListSampleConfluence |
| GET | `/confluence/api/users/ConfluenceSearchServiceController/getDescription` | restGetDescriptionConfluence |
| GET | `/confluence/api/users/ConfluenceSearchServiceController/getId` | restGetIdConfluence |
| GET | `/confluence/api/users/ConfluenceSearchServiceController/getMessagingModuleId` | restGetMessagingModuleIdConfluence |
| GET | `/confluence/api/users/ConfluenceSearchServiceController/getNativePromptTemplateUseCode` | restGetNativePromptTemplateUseCodeConfluence |
| GET | `/confluence/api/users/ConfluenceSearchServiceController/getProductId` | restGetProductIdConfluence |
| GET | `/confluence/api/users/ConfluenceSearchServiceController/getQueriesGenerationPromptUseCode` | restGetQueriesGenerationPromptUseCodeConfluence |
| GET | `/confluence/api/users/ConfluenceSearchServiceController/getSearchableSystems` | restGetSearchableSystemsConfluence |
| GET | `/confluence/api/users/ConfluenceSearchServiceController/isEnabled` | restIsEnabledConfluence |
| POST | `/confluence/api/users/ConfluenceSearchServiceController/nativeSearch` | restNativeSearchConfluence |
| POST | `/confluence/api/users/ConfluenceSearchServiceController/search` | restSearchConfluence |

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

### `gebo-angular-form-group-meta-info-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/confluence/api/admin/AngularFormGroupController/checkDeletableByGObjectRef` | checkDeletableByGObjectRef |
| POST | `/confluence/api/admin/AngularFormGroupController/checkDeletableBySimpleGObjectRef` | checkDeletableBySimpleObjectRef |
| GET | `/confluence/api/admin/AngularFormGroupController/getFormGroupsMetaInfos` | getFormGroupsMetaInfos |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/confluence/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `internal-messaging-topology-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/confluence/api/admin/InternalMessagingTopologyController/getLocalTopology` | getLocalTopology |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/confluence/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/confluence/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/confluence/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

## jira.gebo.ai — port 13011 (`jira-gebo-ai`) — context-path `/jira`

9 controller(s), 46 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/jira/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/jira/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/jira/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `gebo-angular-form-group-meta-info-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/jira/api/admin/AngularFormGroupController/checkDeletableByGObjectRef` | checkDeletableByGObjectRef |
| POST | `/jira/api/admin/AngularFormGroupController/checkDeletableBySimpleGObjectRef` | checkDeletableBySimpleObjectRef |
| GET | `/jira/api/admin/AngularFormGroupController/getFormGroupsMetaInfos` | getFormGroupsMetaInfos |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/jira/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `internal-messaging-topology-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/jira/api/admin/InternalMessagingTopologyController/getLocalTopology` | getLocalTopology |

### `jira-browsing-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/jira/api/admin/JiraBrowsingController/browseJiraPath` | browseJiraPath |
| POST | `/jira/api/admin/JiraBrowsingController/getJiraNavigationStatus` | getJiraNavigationStatus |
| GET | `/jira/api/admin/JiraBrowsingController/getJiraRoots` | getJiraRoots |

### `jira-search-service-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/jira/api/users/JiraSearchServiceController/aggregate` | restAggregateJira |
| POST | `/jira/api/users/JiraSearchServiceController/createCustomTemplateParamsMap` | restCreateCustomTemplateParamsMapJira |
| POST | `/jira/api/users/JiraSearchServiceController/extractRelatedAnalisysReferences` | restExtractRelatedAnalisysReferencesJira |
| GET | `/jira/api/users/JiraSearchServiceController/findSystemById` | restFindSystemByIdJira |
| POST | `/jira/api/users/JiraSearchServiceController/findSystemBySearchResult` | restFindSystemBySearchResultJira |
| GET | `/jira/api/users/JiraSearchServiceController/getCachedCatalogues` | restGetCachedCataloguesJira |
| GET | `/jira/api/users/JiraSearchServiceController/getCataloguesListSample` | restGetCataloguesListSampleJira |
| GET | `/jira/api/users/JiraSearchServiceController/getDescription` | restGetDescriptionJira |
| GET | `/jira/api/users/JiraSearchServiceController/getId` | restGetIdJira |
| GET | `/jira/api/users/JiraSearchServiceController/getMessagingModuleId` | restGetMessagingModuleIdJira |
| GET | `/jira/api/users/JiraSearchServiceController/getNativePromptTemplateUseCode` | restGetNativePromptTemplateUseCodeJira |
| GET | `/jira/api/users/JiraSearchServiceController/getProductId` | restGetProductIdJira |
| GET | `/jira/api/users/JiraSearchServiceController/getQueriesGenerationPromptUseCode` | restGetQueriesGenerationPromptUseCodeJira |
| GET | `/jira/api/users/JiraSearchServiceController/getSearchableSystems` | restGetSearchableSystemsJira |
| GET | `/jira/api/users/JiraSearchServiceController/isEnabled` | restIsEnabledJira |
| POST | `/jira/api/users/JiraSearchServiceController/nativeSearch` | restNativeSearchJira |
| POST | `/jira/api/users/JiraSearchServiceController/search` | restSearchJira |

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

8 controller(s), 27 endpoint(s):


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

### `gebo-angular-form-group-meta-info-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/aws-s3/api/admin/AngularFormGroupController/checkDeletableByGObjectRef` | checkDeletableByGObjectRef |
| POST | `/aws-s3/api/admin/AngularFormGroupController/checkDeletableBySimpleGObjectRef` | checkDeletableBySimpleObjectRef |
| GET | `/aws-s3/api/admin/AngularFormGroupController/getFormGroupsMetaInfos` | getFormGroupsMetaInfos |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/aws-s3/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `internal-messaging-topology-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/aws-s3/api/admin/InternalMessagingTopologyController/getLocalTopology` | getLocalTopology |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/aws-s3/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/aws-s3/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/aws-s3/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

## googledrive.gebo.ai — port 13013 (`googledrive-gebo-ai`) — context-path `/googledrive`

10 controller(s), 44 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/googledrive/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/googledrive/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/googledrive/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `gebo-angular-form-group-meta-info-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/googledrive/api/admin/AngularFormGroupController/checkDeletableByGObjectRef` | checkDeletableByGObjectRef |
| POST | `/googledrive/api/admin/AngularFormGroupController/checkDeletableBySimpleGObjectRef` | checkDeletableBySimpleObjectRef |
| GET | `/googledrive/api/admin/AngularFormGroupController/getFormGroupsMetaInfos` | getFormGroupsMetaInfos |

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
| POST | `/googledrive/api/users/GoogleDriveSearchServiceController/aggregate` | restAggregateGoogleDrive |
| POST | `/googledrive/api/users/GoogleDriveSearchServiceController/extractRelatedAnalisysReferences` | restExtractRelatedAnalisysReferencesGoogleDrive |
| GET | `/googledrive/api/users/GoogleDriveSearchServiceController/findSystemById` | restFindSystemByIdGoogleDrive |
| POST | `/googledrive/api/users/GoogleDriveSearchServiceController/findSystemBySearchResult` | restFindSystemBySearchResultGoogleDrive |
| GET | `/googledrive/api/users/GoogleDriveSearchServiceController/getCachedCatalogues` | restGetCachedCataloguesGoogleDrive |
| GET | `/googledrive/api/users/GoogleDriveSearchServiceController/getCataloguesListSample` | restGetCataloguesListSampleGoogleDrive |
| GET | `/googledrive/api/users/GoogleDriveSearchServiceController/getDescription` | restGetDescriptionGoogleDrive |
| GET | `/googledrive/api/users/GoogleDriveSearchServiceController/getId` | restGetIdGoogleDrive |
| GET | `/googledrive/api/users/GoogleDriveSearchServiceController/getMessagingModuleId` | restGetMessagingModuleIdGoogleDrive |
| GET | `/googledrive/api/users/GoogleDriveSearchServiceController/getProductId` | restGetProductIdGoogleDrive |
| GET | `/googledrive/api/users/GoogleDriveSearchServiceController/getQueriesGenerationPromptUseCode` | restGetQueriesGenerationPromptUseCodeGoogleDrive |
| GET | `/googledrive/api/users/GoogleDriveSearchServiceController/getSearchableSystems` | restGetSearchableSystemsGoogleDrive |
| GET | `/googledrive/api/users/GoogleDriveSearchServiceController/isEnabled` | restIsEnabledGoogleDrive |
| POST | `/googledrive/api/users/GoogleDriveSearchServiceController/search` | restSearchGoogleDrive |

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

### `internal-messaging-topology-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/googledrive/api/admin/InternalMessagingTopologyController/getLocalTopology` | getLocalTopology |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/googledrive/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/googledrive/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/googledrive/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

## mcpclient.gebo.ai — port 13014 (`mcpclient-gebo-ai`) — context-path `/mcpclient`

8 controller(s), 22 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/mcpclient/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/mcpclient/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/mcpclient/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `gebo-angular-form-group-meta-info-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/mcpclient/api/admin/AngularFormGroupController/checkDeletableByGObjectRef` | checkDeletableByGObjectRef |
| POST | `/mcpclient/api/admin/AngularFormGroupController/checkDeletableBySimpleGObjectRef` | checkDeletableBySimpleObjectRef |
| GET | `/mcpclient/api/admin/AngularFormGroupController/getFormGroupsMetaInfos` | getFormGroupsMetaInfos |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/mcpclient/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `internal-messaging-topology-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/mcpclient/api/admin/InternalMessagingTopologyController/getLocalTopology` | getLocalTopology |

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

8 controller(s), 20 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/integration/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/integration/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/integration/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `gebo-angular-form-group-meta-info-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/integration/api/admin/AngularFormGroupController/checkDeletableByGObjectRef` | checkDeletableByGObjectRef |
| POST | `/integration/api/admin/AngularFormGroupController/checkDeletableBySimpleGObjectRef` | checkDeletableBySimpleObjectRef |
| GET | `/integration/api/admin/AngularFormGroupController/getFormGroupsMetaInfos` | getFormGroupsMetaInfos |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/integration/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `integration-input-controller`
| Method | Path | Operation |
|---|---|---|
| PUT | `/integration/api/application/IntegrationInputController/publishContents` | publishContents |
| GET | `/integration/api/application/IntegrationInputController/publishSync` | publishSync |
| POST | `/integration/api/application/IntegrationInputController/spoolDocument` | spoolDocumentEnvelope |
| PUT | `/integration/api/application/IntegrationInputController/spoolDocument` | spoolDocumentFile |

### `integration-systems-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/integration/api/admin/IntegrationSystemsController/deleteIntegrationProjectEndpoint` | deleteIntegrationProjectEndpoint |
| GET | `/integration/api/admin/IntegrationSystemsController/findIntegrationEndpointsByProject` | findIntegrationEndpointsByProject |
| POST | `/integration/api/admin/IntegrationSystemsController/insertIntegrationProjectEndpoint` | insertIntegrationProjectEndpoint |
| POST | `/integration/api/admin/IntegrationSystemsController/publishIntegrationProjectEndpoint` | publishIntegrationProjectEndpoint |
| POST | `/integration/api/admin/IntegrationSystemsController/updateIntegrationProjectEndpoint` | updateIntegrationProjectEndpoint |

### `internal-messaging-topology-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/integration/api/admin/InternalMessagingTopologyController/getLocalTopology` | getLocalTopology |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/integration/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/integration/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/integration/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

## fulltextor.gebo.ai — port 13016 (`fulltextor-gebo-ai`) — context-path `/fulltextor`

2 controller(s), 4 endpoint(s):


### `gebo-angular-form-group-meta-info-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/fulltextor/api/admin/AngularFormGroupController/checkDeletableByGObjectRef` | checkDeletableByGObjectRef |
| POST | `/fulltextor/api/admin/AngularFormGroupController/checkDeletableBySimpleGObjectRef` | checkDeletableBySimpleObjectRef |
| GET | `/fulltextor/api/admin/AngularFormGroupController/getFormGroupsMetaInfos` | getFormGroupsMetaInfos |

### `internal-messaging-topology-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/fulltextor/api/admin/InternalMessagingTopologyController/getLocalTopology` | getLocalTopology |

## eureka.gebo.ai — port 13017

_The Eureka **registry** itself; it is not a `swagger-on` service and exposes no `/v3/api-docs` — this is the registry dashboard/REST API (`/eureka/apps`), not a Gebo controller._


## heimdall.gebo.ai — port 13018 (`heimdall-gebo-ai`) — context-path `/heimdall`

25 controller(s), 102 endpoint(s):


### `acl-aliases-cluster-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/heimdall/api/cluster/AclController/addAcl` | infrastructureAddAcl |
| GET | `/heimdall/api/cluster/AclController/findAcl` | infrastructureFindAcl |
| POST | `/heimdall/api/cluster/AclController/findAlias` | infrastructureFindAlias |
| GET | `/heimdall/api/cluster/AclController/findAliasesByAclGrantedUniqueId` | infrastructureFindAliasesByAclGrantedUniqueId |
| GET | `/heimdall/api/cluster/AclController/findAliasesByAclGrantedUniqueIdAndAclGrantType` | infrastructureFindAliasesByAclGrantedUniqueIdAndAclGrantType |
| POST | `/heimdall/api/cluster/AclController/findAliasesByAclGrantedUniqueIdIn` | infrastructureFindAliasesByAclGrantedUniqueIdIn |
| POST | `/heimdall/api/cluster/AclController/findAliasesByAclGrantedUniqueIdInAndAclGrantType` | infrastructureFindAliasesByAclGrantedUniqueIdInAndAclGrantType |
| DELETE | `/heimdall/api/cluster/AclController/removeAcl` | infrastructureRemoveAcl |

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

### `gebo-angular-form-group-meta-info-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/heimdall/api/admin/AngularFormGroupController/checkDeletableByGObjectRef` | checkDeletableByGObjectRef |
| POST | `/heimdall/api/admin/AngularFormGroupController/checkDeletableBySimpleGObjectRef` | checkDeletableBySimpleObjectRef |
| GET | `/heimdall/api/admin/AngularFormGroupController/getFormGroupsMetaInfos` | getFormGroupsMetaInfos |

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

### `installation-cluster-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/heimdall/public/InstallationController/getInstallationId` | getInstallationId |

### `internal-messaging-topology-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/heimdall/api/admin/InternalMessagingTopologyController/getLocalTopology` | getLocalTopology |

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

### `secrets-cluster-controller`
| Method | Path | Operation |
|---|---|---|
| DELETE | `/heimdall/api/cluster/SecretsController/deleteSecret` | infrastructureDeleteSecret |
| GET | `/heimdall/api/cluster/SecretsController/getAllSecretsId` | infrastructureGetAllSecretsId |
| GET | `/heimdall/api/cluster/SecretsController/getSecretContentById` | infrastructureGetSecretContentById |
| GET | `/heimdall/api/cluster/SecretsController/getSecretInfoByContextCode` | infrastructureGetSecretInfoByContextCode |
| GET | `/heimdall/api/cluster/SecretsController/getSecretInfoById` | infrastructureGetSecretInfoById |
| POST | `/heimdall/api/cluster/SecretsController/storeSecret` | infrastructureStoreSecret |
| POST | `/heimdall/api/cluster/SecretsController/updateSecret` | infrastructureUpdateSecret |

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

### `security-directory-cluster-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/heimdall/api/cluster/SecurityController/checkPassword` | infrastructureCheckPassword |
| POST | `/heimdall/api/cluster/SecurityController/createUserIfNotExists` | infrastructureCreateUserIfNotExists |
| GET | `/heimdall/api/cluster/SecurityController/findAllGroups` | infrastructureFindAllGroups |
| GET | `/heimdall/api/cluster/SecurityController/findGroupsOfUser` | infrastructureFindGroupsOfUser |
| GET | `/heimdall/api/cluster/SecurityController/findUserByUsername` | infrastructureFindUserByUsername |

### `security-header-data-completion-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/heimdall/api/users/SecurityHeaderDataCompletionController/complete` | complete |

### `session-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/heimdall/api/users/SessionController/logout` | logout |

### `sessions-admin-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/heimdall/api/admin/SessionsAdminController/getActiveSessionsCount` | getActiveSessionsCount |
| POST | `/heimdall/api/admin/SessionsAdminController/getSessionsPagedList` | getSessionsPagedList |
| POST | `/heimdall/api/admin/SessionsAdminController/revokeSession` | revokeSession |
| POST | `/heimdall/api/admin/SessionsAdminController/revokeUserSessions` | revokeUserSessions |

### `system-messages-admin-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/heimdall/api/admin/SystemMessagesAdminController/getAllSystemMessages` | getAllSystemMessages |

### `system-messages-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/heimdall/api/users/SystemMessagesController/dismissSystemMessage` | dismissSystemMessage |
| GET | `/heimdall/api/users/SystemMessagesController/getMySystemMessages` | getMySystemMessages |

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

### `users-admin-cluster-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/heimdall/api/cluster/SecurityController/UsersAdmin/changePassword` | infrastructureChangePassword |
| POST | `/heimdall/api/cluster/SecurityController/UsersAdmin/createUserIfNotExists` | infrastructureCreateUserIfNotExists_1 |
| POST | `/heimdall/api/cluster/SecurityController/UsersAdmin/deleteGroup` | infrastructureDeleteGroup |
| POST | `/heimdall/api/cluster/SecurityController/UsersAdmin/deleteUser` | infrastructureDeleteUser |
| POST | `/heimdall/api/cluster/SecurityController/UsersAdmin/findEditableUserByQbe` | infrastructureFindUserByQbe_1 |
| GET | `/heimdall/api/cluster/SecurityController/UsersAdmin/findGroupByCode` | infrastructureFindGroupByCode |
| POST | `/heimdall/api/cluster/SecurityController/UsersAdmin/findUserByQbe` | infrastructureFindUserByQbe |
| GET | `/heimdall/api/cluster/SecurityController/UsersAdmin/findUserByUsername` | infrastructureFindUserByUsername_1 |
| POST | `/heimdall/api/cluster/SecurityController/UsersAdmin/findUsersGroupByQbe` | infrastructureFindUsersGroupByQbe |
| GET | `/heimdall/api/cluster/SecurityController/UsersAdmin/getAllGroups` | infrastructureGetAllGroups |
| GET | `/heimdall/api/cluster/SecurityController/UsersAdmin/getAllUsers` | infrastructureGetAllUsers |
| POST | `/heimdall/api/cluster/SecurityController/UsersAdmin/insertGroup` | infrastructureInsertGroup |
| POST | `/heimdall/api/cluster/SecurityController/UsersAdmin/insertUser` | infrastructureInsertUser |
| POST | `/heimdall/api/cluster/SecurityController/UsersAdmin/updateGroup` | infrastructureUpdateGroup |
| POST | `/heimdall/api/cluster/SecurityController/UsersAdmin/updateUser` | infrastructureUpdateUser |

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

10 controller(s), 18 endpoint(s):


### `gebo-angular-form-group-meta-info-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/tyr/api/admin/AngularFormGroupController/checkDeletableByGObjectRef` | checkDeletableByGObjectRef |
| POST | `/tyr/api/admin/AngularFormGroupController/checkDeletableBySimpleGObjectRef` | checkDeletableBySimpleObjectRef |
| GET | `/tyr/api/admin/AngularFormGroupController/getFormGroupsMetaInfos` | getFormGroupsMetaInfos |

### `gebo-modules-config-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/tyr/api/users/GeboModulesConfigController/getAllModules` | getAllModules |
| GET | `/tyr/api/users/GeboModulesConfigController/getModuleInfo` | getModuleInfo |

### `global-internal-topology-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/tyr/api/admin/GlobalInternalTopologyController/getGlobalTopology` | getGlobalTopology |
| POST | `/tyr/api/admin/GlobalInternalTopologyController/refresh` | refresh |

### `internal-messaging-topology-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/tyr/api/admin/InternalMessagingTopologyController/getLocalTopology` | getLocalTopology |

### `job-status-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/tyr/api/admin/JobStatusController/getJobStatus` | getJobStatus |
| GET | `/tyr/api/admin/JobStatusController/getJobSummary` | getJobSummary |
| POST | `/tyr/api/admin/JobStatusController/getJobsEntriesForProjectEndpoint` | getJobsEntriesForProjectEndpointJobStatus |

### `llms-usage-admin-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/tyr/api/admin/LLMSUsageAdminLevelController/drillDown` | adminDrillDown |

### `llms-usage-user-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/tyr/api/users/LLMSUsageUserLevelController/drillDown` | userDrillDown |

### `reindexing-frequency-options-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/tyr/api/users/ReindexingFrequencyOptionsController/displayTimeValues` | displayTimeValues |
| GET | `/tyr/api/users/ReindexingFrequencyOptionsController/getAllTimeStructureMetaInfos` | getAllTimeStructureMetaInfos |
| GET | `/tyr/api/users/ReindexingFrequencyOptionsController/getTimeStructureMetaInfo` | getTimeStructureMetaInfo |

### `workflow-participants-enablement-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/tyr/api/users/WorkflowParticipantsEnablementController/enabledSteps` | enabledSteps |

### `workflow-stats-admin-level-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/tyr/api/admin/WorkflowStatsAdminLevelController/drillDown` | workflowDrillDown |

## webdav.gebo.ai — port 13020 (`webdav-gebo-ai`) — context-path `/webdav`

8 controller(s), 29 endpoint(s):


### `contents-reset-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/webdav/api/admin/ContentsResetController/resetContentsIngestion` | resetContentsIngestion |

### `document-content-streamer-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/webdav/api/users/DocumentContentStreamerController/streamDocumentReference` | streamDocumentReference |
| POST | `/webdav/api/users/DocumentContentStreamerController/streamSearchResult` | streamSearchResult |

### `gebo-angular-form-group-meta-info-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/webdav/api/admin/AngularFormGroupController/checkDeletableByGObjectRef` | checkDeletableByGObjectRef |
| POST | `/webdav/api/admin/AngularFormGroupController/checkDeletableBySimpleGObjectRef` | checkDeletableBySimpleObjectRef |
| GET | `/webdav/api/admin/AngularFormGroupController/getFormGroupsMetaInfos` | getFormGroupsMetaInfos |

### `generical-publisher-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/webdav/api/admin/GenericalPublisherController/publishCentralizedEndpoint` | publishCentralizedEndpoint |

### `internal-messaging-topology-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/webdav/api/admin/InternalMessagingTopologyController/getLocalTopology` | getLocalTopology |

### `job-launcher-controller`
| Method | Path | Operation |
|---|---|---|
| GET | `/webdav/api/admin/JobLauncherController/abortJob` | abortJob |
| POST | `/webdav/api/admin/JobLauncherController/createJob` | createJob |
| POST | `/webdav/api/admin/JobLauncherController/getHasRunningJobs` | getHasRunningJobs |

### `webdav-browsing-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/webdav/api/admin/WebdavBrowsingController/browseWebdavPath` | browseWebdavPath |
| POST | `/webdav/api/admin/WebdavBrowsingController/getWebdavNavigationStatus` | getWebdavNavigationStatus |
| GET | `/webdav/api/admin/WebdavBrowsingController/getWebdavRoots` | getWebdavRoots |

### `webdav-systems-controller`
| Method | Path | Operation |
|---|---|---|
| POST | `/webdav/api/admin/WebdavSystemsController/deleteWebdavEndpoint` | deleteWebdavEndpoint |
| POST | `/webdav/api/admin/WebdavSystemsController/deleteWebdavSystem` | deleteWebdavSystem |
| POST | `/webdav/api/admin/WebdavSystemsController/fastWebdavConfig` | fastWebdavConfig |
| GET | `/webdav/api/admin/WebdavSystemsController/findWebdavEndpointsByCode` | findWebdavEndpointsByCode |
| GET | `/webdav/api/admin/WebdavSystemsController/findWebdavEndpointsByProject` | findWebdavEndpointsByProject |
| POST | `/webdav/api/admin/WebdavSystemsController/findWebdavEndpointsByQbe` | findWebdavEndpointsByQbe |
| GET | `/webdav/api/admin/WebdavSystemsController/findWebdavSystemByCode` | findWebdavSystemByCode |
| GET | `/webdav/api/admin/WebdavSystemsController/getWebdavSystemType` | getWebdavSystemTypes |
| GET | `/webdav/api/admin/WebdavSystemsController/getWebdavSystems` | getWebdavSystems |
| POST | `/webdav/api/admin/WebdavSystemsController/insertWebdavEndpoint` | insertWebdavEndpoint |
| POST | `/webdav/api/admin/WebdavSystemsController/insertWebdavSystem` | insertWebdavSystem |
| POST | `/webdav/api/admin/WebdavSystemsController/publishWebdavEndpoint` | publishWebdavEndpoint |
| POST | `/webdav/api/admin/WebdavSystemsController/testWebdavSystem` | testWebdavSystem |
| POST | `/webdav/api/admin/WebdavSystemsController/updateWebdavEndpoint` | updateWebdavEndpoint |
| POST | `/webdav/api/admin/WebdavSystemsController/updateWebdavSystem` | updateWebdavSystem |
