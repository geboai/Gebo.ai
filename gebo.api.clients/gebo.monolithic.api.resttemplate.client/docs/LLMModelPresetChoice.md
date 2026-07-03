# LLMModelPresetChoice

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  | 
**description** | **String** |  |  [optional]
**defaultChoice** | **Boolean** |  |  [optional]
**contextWindow** | **Integer** |  |  [optional]
**uses** | [**List&lt;UsesEnum&gt;**](#List&lt;UsesEnum&gt;) |  |  [optional]
**maxGeneratedTokens** | **Integer** |  |  [optional]
**thinking** | [**ThinkingEnum**](#ThinkingEnum) |  |  [optional]

<a name="List<UsesEnum>"></a>
## Enum: List&lt;UsesEnum&gt;
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
