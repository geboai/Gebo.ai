# LLMUsageAggregationBucket

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**providerId** | **String** |  |  [optional]
**username** | **String** |  |  [optional]
**model** | **String** |  |  [optional]
**callerStack** | **String** |  |  [optional]
**modelType** | [**ModelTypeEnum**](#ModelTypeEnum) |  |  [optional]
**year** | **Integer** |  |  [optional]
**month** | **Integer** |  |  [optional]
**day** | **Integer** |  |  [optional]
**inputToken** | **Long** |  |  [optional]
**outputToken** | **Long** |  |  [optional]
**totalToken** | **Long** |  |  [optional]
**nrRequests** | **Long** |  |  [optional]
**responseTimeMin** | **Long** |  |  [optional]
**responseTimeMax** | **Long** |  |  [optional]
**responseTimeAvg** | **Long** |  |  [optional]
**timeToFirstTokenMin** | **Long** |  |  [optional]
**timeToFirstTokenMax** | **Long** |  |  [optional]
**timeToFirstTokenAvg** | **Long** |  |  [optional]
**timeToFirstTokenSamples** | **Long** |  |  [optional]
**cost** | **Double** |  |  [optional]
**currencyCode** | **String** |  |  [optional]
**costSamples** | **Long** |  |  [optional]

<a name="ModelTypeEnum"></a>
## Enum: ModelTypeEnum
Name | Value
---- | -----
CHAT | &quot;CHAT&quot;
EMBEDDING | &quot;EMBEDDING&quot;
IMAGE | &quot;IMAGE&quot;
RANKER | &quot;RANKER&quot;
TTS | &quot;TTS&quot;
TRANSCRIPT | &quot;TRANSCRIPT&quot;
