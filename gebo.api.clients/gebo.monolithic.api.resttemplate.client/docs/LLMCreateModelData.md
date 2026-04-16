# LLMCreateModelData

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**type** | [**TypeEnum**](#TypeEnum) |  | 
**doModelsLookup** | **Boolean** |  |  [optional]
**serviceHandler** | **String** |  | 
**setAsDefaultModel** | **Boolean** |  |  [optional]
**enableAllFunctions** | **Boolean** |  |  [optional]
**secretId** | **String** |  |  [optional]
**modelCode** | **String** |  | 
**baseUrl** | **String** |  |  [optional]
**contextWindow** | **Integer** |  |  [optional]
**uses** | [**List&lt;UsesEnum&gt;**](#List&lt;UsesEnum&gt;) |  |  [optional]

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
CHAT | &quot;CHAT&quot;
EMBEDDING | &quot;EMBEDDING&quot;

<a name="List<UsesEnum>"></a>
## Enum: List&lt;UsesEnum&gt;
Name | Value
---- | -----
CHAT | &quot;CHAT&quot;
INTERNAL_SERVICES | &quot;INTERNAL_SERVICES&quot;
