# GeboAiClient.GraphRagExtractionConfig

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  | [optional] 
**description** | **String** |  | [optional] 
**userModified** | **String** |  | [optional] 
**userCreated** | **String** |  | [optional] 
**dateModified** | **Date** |  | [optional] 
**dateCreated** | **Date** |  | [optional] 
**knowledgeBaseCode** | **String** |  | [optional] 
**projectCode** | **String** |  | [optional] 
**defaultConfiguration** | **Boolean** |  | [optional] 
**graphRagAllSources** | **Boolean** |  | [optional] 
**endpoint** | [**GObjectRefGProjectEndpoint**](GObjectRefGProjectEndpoint.md) |  | [optional] 
**extractionPrompt** | **String** |  | [optional] 
**customEntityTypes** | [**[GraphObjectType]**](GraphObjectType.md) |  | [optional] 
**customEventTypes** | [**[GraphObjectType]**](GraphObjectType.md) |  | [optional] 
**customRelationTypes** | [**[GraphObjectType]**](GraphObjectType.md) |  | [optional] 
**usedModelConfiguration** | [**GObjectRefGBaseChatModelConfig**](GObjectRefGBaseChatModelConfig.md) |  | [optional] 
**contentSelectionFilter** | [**GContentSelectionFilter**](GContentSelectionFilter.md) |  | [optional] 
**processEveryDocument** | **Boolean** |  | [optional] 
**extractionFormat** | **String** |  | 

<a name="ExtractionFormatEnum"></a>
## Enum: ExtractionFormatEnum

* `JSON` (value: `"JSON"`)
* `CSV` (value: `"CSV"`)

