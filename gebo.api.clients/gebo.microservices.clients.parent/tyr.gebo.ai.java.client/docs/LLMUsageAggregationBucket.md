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
**latencyMin** | **Long** |  |  [optional]
**latencyMax** | **Long** |  |  [optional]
**latencyAvg** | **Long** |  |  [optional]

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
