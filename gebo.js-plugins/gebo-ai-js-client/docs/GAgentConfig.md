# GeboAiClient.GAgentConfig

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  | [optional] 
**description** | **String** |  | [optional] 
**userModified** | **String** |  | [optional] 
**userCreated** | **String** |  | [optional] 
**dateModified** | **Date** |  | [optional] 
**dateCreated** | **Date** |  | [optional] 
**agentType** | **String** |  | [optional] 
**adaptedAgentNetworkCode** | **String** |  | [optional] 
**agentNetworkServiceCode** | **String** |  | [optional] 
**agentServiceId** | **String** |  | 
**mainLoopPromptUseCode** | **String** |  | [optional] 
**customLoopPrompt** | [**GPromptTemplateConfig**](GPromptTemplateConfig.md) |  | [optional] 
**subscribeAllTools** | **Boolean** |  | [optional] 
**agentRoleCode** | **String** |  | 
**useDefaultChatModel** | **Boolean** |  | [optional] 
**useChatModelWithUse** | **String** |  | [optional] 
**chatModelReference** | [**GObjectRefGBaseChatModelConfig**](GObjectRefGBaseChatModelConfig.md) |  | [optional] 
**maxLoopIterations** | **Number** |  | 
**aclAliases** | **[Number]** |  | [optional] 
**defaultConfiguration** | **Boolean** |  | [optional] 
**topP** | **Number** |  | [optional] 
**temperature** | **Number** |  | [optional] 
**thinking** | **String** |  | [optional] 
**readOnly** | **Boolean** |  | [optional] 
**accessibleGroups** | **[String]** |  | [optional] 
**accessibleUsers** | **[String]** |  | [optional] 
**accessibleToAll** | **Boolean** |  | [optional] 
**enabledFunctions** | **[String]** |  | [optional] 

<a name="AgentTypeEnum"></a>
## Enum: AgentTypeEnum

* `AGENT` (value: `"AGENT"`)
* `AGENTS_NETWORK` (value: `"AGENTS_NETWORK"`)


<a name="UseChatModelWithUseEnum"></a>
## Enum: UseChatModelWithUseEnum

* `CHAT` (value: `"CHAT"`)
* `INTERNAL_SERVICES` (value: `"INTERNAL_SERVICES"`)


<a name="ThinkingEnum"></a>
## Enum: ThinkingEnum

* `NO_THINKING` (value: `"NO_THINKING"`)
* `LOW_THINKING` (value: `"LOW_THINKING"`)
* `MEDIUM_THINKING` (value: `"MEDIUM_THINKING"`)
* `HIGH_THINKING` (value: `"HIGH_THINKING"`)
* `AUTO` (value: `"AUTO"`)

