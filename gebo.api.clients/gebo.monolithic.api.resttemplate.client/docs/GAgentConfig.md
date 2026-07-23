# GAgentConfig

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  |  [optional]
**description** | **String** |  |  [optional]
**userModified** | **String** |  |  [optional]
**userCreated** | **String** |  |  [optional]
**dateModified** | [**Date**](Date.md) |  |  [optional]
**dateCreated** | [**Date**](Date.md) |  |  [optional]
**agentType** | [**AgentTypeEnum**](#AgentTypeEnum) |  |  [optional]
**adaptedAgentNetworkCode** | **String** |  |  [optional]
**agentNetworkServiceCode** | **String** |  |  [optional]
**agentServiceId** | **String** |  | 
**mainLoopPromptUseCode** | **String** |  |  [optional]
**customLoopPrompt** | [**GPromptTemplateConfig**](GPromptTemplateConfig.md) |  |  [optional]
**subscribeAllTools** | **Boolean** |  |  [optional]
**agentRoleCode** | **String** |  | 
**useDefaultChatModel** | **Boolean** |  |  [optional]
**useChatModelWithUse** | [**UseChatModelWithUseEnum**](#UseChatModelWithUseEnum) |  |  [optional]
**chatModelReference** | [**GObjectRefGBaseChatModelConfig**](GObjectRefGBaseChatModelConfig.md) |  |  [optional]
**maxLoopIterations** | **Integer** |  | 
**aclAliases** | **List&lt;Integer&gt;** |  |  [optional]
**defaultConfiguration** | **Boolean** |  |  [optional]
**topP** | **Double** |  |  [optional]
**temperature** | **Double** |  |  [optional]
**thinking** | [**ThinkingEnum**](#ThinkingEnum) |  |  [optional]
**readOnly** | **Boolean** |  |  [optional]
**accessibleGroups** | **List&lt;String&gt;** |  |  [optional]
**accessibleUsers** | **List&lt;String&gt;** |  |  [optional]
**accessibleToAll** | **Boolean** |  |  [optional]
**enabledFunctions** | **List&lt;String&gt;** |  |  [optional]

<a name="AgentTypeEnum"></a>
## Enum: AgentTypeEnum
Name | Value
---- | -----
AGENT | &quot;AGENT&quot;
AGENTS_NETWORK | &quot;AGENTS_NETWORK&quot;

<a name="UseChatModelWithUseEnum"></a>
## Enum: UseChatModelWithUseEnum
Name | Value
---- | -----
CHAT | &quot;CHAT&quot;
INTERNAL_SERVICES | &quot;INTERNAL_SERVICES&quot;

<a name="ThinkingEnum"></a>
## Enum: ThinkingEnum
Name | Value
---- | -----
NO_THINKING | &quot;NO_THINKING&quot;
LOW_THINKING | &quot;LOW_THINKING&quot;
MEDIUM_THINKING | &quot;MEDIUM_THINKING&quot;
HIGH_THINKING | &quot;HIGH_THINKING&quot;
AUTO | &quot;AUTO&quot;
