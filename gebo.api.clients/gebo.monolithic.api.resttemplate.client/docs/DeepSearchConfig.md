# DeepSearchConfig

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  |  [optional]
**description** | **String** |  |  [optional]
**userModified** | **String** |  |  [optional]
**userCreated** | **String** |  |  [optional]
**dateModified** | [**Date**](Date.md) |  |  [optional]
**dateCreated** | [**Date**](Date.md) |  |  [optional]
**searchType** | [**SearchTypeEnum**](#SearchTypeEnum) |  | 
**ragQueryOptions** | [**RagQueryOptions**](RagQueryOptions.md) |  |  [optional]
**firstHopSimilarityThreashold** | **Double** |  |  [optional]
**secondHopSimilarityThreashold** | **Double** |  |  [optional]
**graphRagTopN** | **Integer** |  |  [optional]
**tokensLimit** | **Integer** |  |  [optional]
**documentsParallelism** | **Integer** |  |  [optional]
**manualThreasholdsConfiguration** | **Boolean** |  |  [optional]
**chatModelConfiguration** | [**GObjectRefGBaseChatModelConfig**](GObjectRefGBaseChatModelConfig.md) |  |  [optional]
**defaultConfig** | **Boolean** |  |  [optional]
**chatProfileCode** | **String** |  |  [optional]

<a name="SearchTypeEnum"></a>
## Enum: SearchTypeEnum
Name | Value
---- | -----
SINGLE_HOP | &quot;SINGLE_HOP&quot;
MULTI_HOP | &quot;MULTI_HOP&quot;
