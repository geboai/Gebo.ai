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
**contextLength** | **Integer** |  |  [optional]
**topP** | **Double** |  |  [optional]
**accessibleGroups** | **List&lt;String&gt;** |  |  [optional]
**accessibleUsers** | **List&lt;String&gt;** |  |  [optional]
**accessibleToAll** | **Boolean** |  |  [optional]
**enabledFunctions** | **List&lt;String&gt;** |  |  [optional]
**temperature** | **Double** |  |  [optional]
**forUses** | [**List&lt;ForUsesEnum&gt;**](#List&lt;ForUsesEnum&gt;) |  |  [optional]
**features** | [**List&lt;FeaturesEnum&gt;**](#List&lt;FeaturesEnum&gt;) |  |  [optional]
**thinking** | [**ThinkingEnum**](#ThinkingEnum) |  |  [optional]
**maxGeneratedTokens** | **Integer** |  |  [optional]

<a name="List<ForUsesEnum>"></a>
## Enum: List&lt;ForUsesEnum&gt;
Name | Value
---- | -----
CHAT | &quot;CHAT&quot;
INTERNAL_SERVICES | &quot;INTERNAL_SERVICES&quot;

<a name="List<FeaturesEnum>"></a>
## Enum: List&lt;FeaturesEnum&gt;
Name | Value
---- | -----
CHAT | &quot;CHAT&quot;
REASONING | &quot;REASONING&quot;
STRUCTURED_OUTPUT | &quot;STRUCTURED_OUTPUT&quot;
MULTIMEDIA | &quot;MULTIMEDIA&quot;
FUNCTION_CALLING | &quot;FUNCTION_CALLING&quot;

<a name="ThinkingEnum"></a>
## Enum: ThinkingEnum
Name | Value
---- | -----
NO_THINKING | &quot;NO_THINKING&quot;
LOW_THINKING | &quot;LOW_THINKING&quot;
MEDIUM_THINKING | &quot;MEDIUM_THINKING&quot;
HIGH_THINKING | &quot;HIGH_THINKING&quot;
AUTO | &quot;AUTO&quot;
