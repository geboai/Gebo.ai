# DataEndpoint

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  | 
**description** | **String** |  | 
**product** | **String** |  | 
**endpoint** | **String** |  | 
**input** | **Boolean** |  |  [optional]
**output** | **Boolean** |  |  [optional]
**types** | [**List&lt;TypesEnum&gt;**](#List&lt;TypesEnum&gt;) |  | 
**locality** | [**LocalityEnum**](#LocalityEnum) |  |  [optional]
**secretReference** | **String** |  |  [optional]
**personalData** | **Boolean** |  |  [optional]
**retention** | **String** |  |  [optional]
**disposer** | [**GeboComponentInfo**](GeboComponentInfo.md) |  |  [optional]

<a name="List<TypesEnum>"></a>
## Enum: List&lt;TypesEnum&gt;
Name | Value
---- | -----
DOCUMENTS | &quot;DOCUMENTS&quot;
DATABASE | &quot;DATABASE&quot;
VECTORIAL_DATABASE | &quot;VECTORIAL_DATABASE&quot;
GRAPH_DATABASE | &quot;GRAPH_DATABASE&quot;
CHUNK | &quot;CHUNK&quot;
FULLTEXT_INDEX | &quot;FULLTEXT_INDEX&quot;
LLM_ENDPOINT | &quot;LLM_ENDPOINT&quot;
OBJECT_STORAGE | &quot;OBJECT_STORAGE&quot;
MESSAGE_BROKER | &quot;MESSAGE_BROKER&quot;
WEB_SEARCH | &quot;WEB_SEARCH&quot;
LOCAL_FILESYSTEM | &quot;LOCAL_FILESYSTEM&quot;
CHAT_SESSION | &quot;CHAT_SESSION&quot;

<a name="LocalityEnum"></a>
## Enum: LocalityEnum
Name | Value
---- | -----
LOCAL_DEPLOYMENT | &quot;LOCAL_DEPLOYMENT&quot;
SAME_NETWORK | &quot;SAME_NETWORK&quot;
EXTERNAL_PROVIDER | &quot;EXTERNAL_PROVIDER&quot;
