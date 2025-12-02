# GraphRagExtractionConfig

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  |  [optional]
**description** | **String** |  |  [optional]
**userModified** | **String** |  |  [optional]
**userCreated** | **String** |  |  [optional]
**dateModified** | [**Date**](Date.md) |  |  [optional]
**dateCreated** | [**Date**](Date.md) |  |  [optional]
**knowledgeBaseCode** | **String** |  |  [optional]
**projectCode** | **String** |  |  [optional]
**defaultConfiguration** | **Boolean** |  |  [optional]
**graphRagAllSources** | **Boolean** |  |  [optional]
**endpoint** | [**GObjectRefGProjectEndpoint**](GObjectRefGProjectEndpoint.md) |  |  [optional]
**extractionPrompt** | **String** |  |  [optional]
**customEntityTypes** | [**List&lt;GraphObjectType&gt;**](GraphObjectType.md) |  |  [optional]
**customEventTypes** | [**List&lt;GraphObjectType&gt;**](GraphObjectType.md) |  |  [optional]
**customRelationTypes** | [**List&lt;GraphObjectType&gt;**](GraphObjectType.md) |  |  [optional]
**usedModelConfiguration** | [**GObjectRefGBaseChatModelConfig**](GObjectRefGBaseChatModelConfig.md) |  |  [optional]
**contentSelectionFilter** | [**GContentSelectionFilter**](GContentSelectionFilter.md) |  |  [optional]
**processEveryDocument** | **Boolean** |  |  [optional]
**extractionFormat** | [**ExtractionFormatEnum**](#ExtractionFormatEnum) |  | 

<a name="ExtractionFormatEnum"></a>
## Enum: ExtractionFormatEnum
Name | Value
---- | -----
JSON | &quot;JSON&quot;
CSV | &quot;CSV&quot;
