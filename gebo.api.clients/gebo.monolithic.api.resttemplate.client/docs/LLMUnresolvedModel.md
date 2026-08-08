# LLMUnresolvedModel

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**type** | [**TypeEnum**](#TypeEnum) |  |  [optional]
**uses** | [**List&lt;UsesEnum&gt;**](#List&lt;UsesEnum&gt;) |  |  [optional]
**serviceHandler** | **String** |  |  [optional]
**requestedModelCode** | **String** |  |  [optional]
**availableChoices** | [**List&lt;GBaseModelChoice&gt;**](GBaseModelChoice.md) |  |  [optional]

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
