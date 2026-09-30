# GAgentsNetwork

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  |  [optional]
**description** | **String** |  |  [optional]
**userModified** | **String** |  |  [optional]
**userCreated** | **String** |  |  [optional]
**dateModified** | [**Date**](Date.md) |  |  [optional]
**dateCreated** | [**Date**](Date.md) |  |  [optional]
**maxLoopIteration** | **Integer** |  |  [optional]
**accessibleToAll** | **Boolean** |  |  [optional]
**accessibleUsers** | **List&lt;String&gt;** |  |  [optional]
**accessibleGroups** | **List&lt;String&gt;** |  |  [optional]
**aclAliases** | **List&lt;Integer&gt;** |  |  [optional]
**agentsNetworkServiceFactoryId** | **String** |  | 
**scenarioDescription** | **String** |  | 
**agents** | [**List&lt;AgentNetworkParticipant&gt;**](AgentNetworkParticipant.md) |  | 
**readOnly** | **Boolean** |  |  [optional]
**suggestedPurpose** | **String** |  |  [optional]
**choosableForPipelineTypes** | [**List&lt;ChoosableForPipelineTypesEnum&gt;**](#List&lt;ChoosableForPipelineTypesEnum&gt;) |  |  [optional]

<a name="List<ChoosableForPipelineTypesEnum>"></a>
## Enum: List&lt;ChoosableForPipelineTypesEnum&gt;
Name | Value
---- | -----
RAG_PIPELINE | &quot;RAG_PIPELINE&quot;
PURE_CHAT_PIPELINE | &quot;PURE_CHAT_PIPELINE&quot;
