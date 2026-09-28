# GeboAiClient.LLMUsageAggregationBucket

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**providerId** | **String** |  | [optional] 
**username** | **String** |  | [optional] 
**model** | **String** |  | [optional] 
**callerStack** | **String** |  | [optional] 
**modelType** | **String** |  | [optional] 
**year** | **Number** |  | [optional] 
**month** | **Number** |  | [optional] 
**day** | **Number** |  | [optional] 
**inputToken** | **Number** |  | [optional] 
**outputToken** | **Number** |  | [optional] 
**totalToken** | **Number** |  | [optional] 
**nrRequests** | **Number** |  | [optional] 
**responseTimeMin** | **Number** |  | [optional] 
**responseTimeMax** | **Number** |  | [optional] 
**responseTimeAvg** | **Number** |  | [optional] 
**timeToFirstTokenMin** | **Number** |  | [optional] 
**timeToFirstTokenMax** | **Number** |  | [optional] 
**timeToFirstTokenAvg** | **Number** |  | [optional] 
**timeToFirstTokenSamples** | **Number** |  | [optional] 

<a name="ModelTypeEnum"></a>
## Enum: ModelTypeEnum

* `CHAT` (value: `"CHAT"`)
* `EMBEDDING` (value: `"EMBEDDING"`)
* `IMAGE` (value: `"IMAGE"`)
* `RANKER` (value: `"RANKER"`)
* `TTS` (value: `"TTS"`)
* `TRANSCRIPT` (value: `"TRANSCRIPT"`)

