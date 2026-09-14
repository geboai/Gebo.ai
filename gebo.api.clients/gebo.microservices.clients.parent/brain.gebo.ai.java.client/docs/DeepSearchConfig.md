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
**manualThreasholdsConfiguration** | **Boolean** |  |  [optional]
**defaultConfig** | **Boolean** |  |  [optional]
**accessibleGroups** | **List&lt;String&gt;** |  |  [optional]
**accessibleUsers** | **List&lt;String&gt;** |  |  [optional]
**accessibleToAll** | **Boolean** |  |  [optional]
**dataSourcesAccesses** | [**List&lt;DeepSearchDataSourceAccess&gt;**](DeepSearchDataSourceAccess.md) |  |  [optional]
**perDataSourceConfigured** | **Boolean** |  |  [optional]

<a name="SearchTypeEnum"></a>
## Enum: SearchTypeEnum
Name | Value
---- | -----
SINGLE_HOP | &quot;SINGLE_HOP&quot;
MULTI_HOP | &quot;MULTI_HOP&quot;
