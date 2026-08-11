# GeboAiClient.LLMCreateModelData

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**type** | **String** |  | 
**doModelsLookup** | **Boolean** |  | [optional] 
**serviceHandler** | **String** |  | 
**setAsDefaultModel** | **Boolean** |  | [optional] 
**enableAllFunctions** | **Boolean** |  | [optional] 
**secretId** | **String** |  | [optional] 
**modelCode** | **String** |  | 
**baseUrl** | **String** |  | [optional] 
**contextWindow** | **Number** |  | [optional] 
**uses** | **[String]** |  | [optional] 
**maxGeneratedTokens** | **Number** |  | [optional] 
**thinking** | **String** |  | [optional] 

<a name="TypeEnum"></a>
## Enum: TypeEnum

* `CHAT` (value: `"CHAT"`)
* `EMBEDDING` (value: `"EMBEDDING"`)
* `RANKING` (value: `"RANKING"`)
* `IMAGESGEN` (value: `"IMAGESGEN"`)
* `TTS` (value: `"TTS"`)
* `TRANSCRIPT` (value: `"TRANSCRIPT"`)


<a name="[UsesEnum]"></a>
## Enum: [UsesEnum]

* `CHAT` (value: `"CHAT"`)
* `INTERNAL_SERVICES` (value: `"INTERNAL_SERVICES"`)


<a name="ThinkingEnum"></a>
## Enum: ThinkingEnum

* `NO_THINKING` (value: `"NO_THINKING"`)
* `LOW_THINKING` (value: `"LOW_THINKING"`)
* `MEDIUM_THINKING` (value: `"MEDIUM_THINKING"`)
* `HIGH_THINKING` (value: `"HIGH_THINKING"`)
* `AUTO` (value: `"AUTO"`)

