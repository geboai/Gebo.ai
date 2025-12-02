# GAnthropicChatModelConfig

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  |  [optional]
**description** | **String** |  |  [optional]
**userModified** | **String** |  |  [optional]
**userCreated** | **String** |  |  [optional]
**dateModified** | [**Date**](Date.md) |  |  [optional]
**dateCreated** | [**Date**](Date.md) |  |  [optional]
**modelTypeCode** | **String** |  |  [optional]
**defaultModel** | **Boolean** |  |  [optional]
**apiSecretCode** | **String** |  |  [optional]
**choosedModel** | [**GAnthropicChatModelChoice**](GAnthropicChatModelChoice.md) |  |  [optional]
**baseUrl** | **String** |  |  [optional]
**topP** | **Double** |  |  [optional]
**accessibleGroups** | **List&lt;String&gt;** |  |  [optional]
**accessibleUsers** | **List&lt;String&gt;** |  |  [optional]
**accessibleToAll** | **Boolean** |  |  [optional]
**enabledFunctions** | **List&lt;String&gt;** |  |  [optional]
**temperature** | **Double** |  |  [optional]
**contextLength** | **Integer** |  |  [optional]
**defaultModelPrompt** | **String** |  |  [optional]
**forUses** | [**List&lt;ForUsesEnum&gt;**](#List&lt;ForUsesEnum&gt;) |  |  [optional]

<a name="List<ForUsesEnum>"></a>
## Enum: List&lt;ForUsesEnum&gt;
Name | Value
---- | -----
CHAT | &quot;CHAT&quot;
GRAPH_EXTRACTION | &quot;GRAPH_EXTRACTION&quot;
