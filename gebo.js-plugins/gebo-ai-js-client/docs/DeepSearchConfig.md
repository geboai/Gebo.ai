# GeboAiClient.DeepSearchConfig

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  | [optional] 
**description** | **String** |  | [optional] 
**userModified** | **String** |  | [optional] 
**userCreated** | **String** |  | [optional] 
**dateModified** | **Date** |  | [optional] 
**dateCreated** | **Date** |  | [optional] 
**searchType** | **String** |  | 
**ragQueryOptions** | [**RagQueryOptions**](RagQueryOptions.md) |  | [optional] 
**firstHopSimilarityThreashold** | **Number** |  | [optional] 
**secondHopSimilarityThreashold** | **Number** |  | [optional] 
**graphRagTopN** | **Number** |  | [optional] 
**tokensLimit** | **Number** |  | [optional] 
**manualThreasholdsConfiguration** | **Boolean** |  | [optional] 
**defaultConfig** | **Boolean** |  | [optional] 
**accessibleGroups** | **[String]** |  | [optional] 
**accessibleUsers** | **[String]** |  | [optional] 
**accessibleToAll** | **Boolean** |  | [optional] 
**dataSourcesAccesses** | [**[DeepSearchDataSourceAccess]**](DeepSearchDataSourceAccess.md) |  | [optional] 
**perDataSourceConfigured** | **Boolean** |  | [optional] 

<a name="SearchTypeEnum"></a>
## Enum: SearchTypeEnum

* `SINGLE_HOP` (value: `"SINGLE_HOP"`)
* `MULTI_HOP` (value: `"MULTI_HOP"`)

