# LLMCreateModelData

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**type** | [**TypeEnum**](#TypeEnum) |  | 
**doModelsLookup** | **Boolean** |  |  [optional]
**serviceHandler** | **String** |  | 
**setAsDefaultModel** | **Boolean** |  |  [optional]
**enableAllFunctions** | **Boolean** |  |  [optional]
**secretId** | **String** |  |  [optional]
**modelCode** | **String** |  | 
**baseUrl** | **String** |  |  [optional]
**contextWindow** | **Integer** |  |  [optional]
**uses** | [**List&lt;UsesEnum&gt;**](#List&lt;UsesEnum&gt;) |  |  [optional]
**maxGeneratedTokens** | **Integer** |  |  [optional]
**thinking** | [**ThinkingEnum**](#ThinkingEnum) |  |  [optional]

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
CHAT | &quot;CHAT&quot;
EMBEDDING | &quot;EMBEDDING&quot;
RANKING | &quot;RANKING&quot;
IMAGESGEN | &quot;IMAGESGEN&quot;
TTS | &quot;TTS&quot;
TRANSCRIPT | &quot;TRANSCRIPT&quot;

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
