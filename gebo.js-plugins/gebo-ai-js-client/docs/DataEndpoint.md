# GeboAiClient.DataEndpoint

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  | 
**description** | **String** |  | 
**product** | **String** |  | 
**endpoint** | **String** |  | 
**input** | **Boolean** |  | [optional] 
**output** | **Boolean** |  | [optional] 
**types** | **[String]** |  | 
**locality** | **String** |  | [optional] 
**secretReference** | **String** |  | [optional] 
**personalData** | **Boolean** |  | [optional] 
**retention** | **String** |  | [optional] 
**disposer** | [**GeboComponentInfo**](GeboComponentInfo.md) |  | [optional] 

<a name="[TypesEnum]"></a>
## Enum: [TypesEnum]

* `DOCUMENTS` (value: `"DOCUMENTS"`)
* `DATABASE` (value: `"DATABASE"`)
* `VECTORIAL_DATABASE` (value: `"VECTORIAL_DATABASE"`)
* `GRAPH_DATABASE` (value: `"GRAPH_DATABASE"`)
* `CHUNK` (value: `"CHUNK"`)
* `FULLTEXT_INDEX` (value: `"FULLTEXT_INDEX"`)
* `LLM_ENDPOINT` (value: `"LLM_ENDPOINT"`)
* `OBJECT_STORAGE` (value: `"OBJECT_STORAGE"`)
* `MESSAGE_BROKER` (value: `"MESSAGE_BROKER"`)
* `WEB_SEARCH` (value: `"WEB_SEARCH"`)
* `LOCAL_FILESYSTEM` (value: `"LOCAL_FILESYSTEM"`)
* `CHAT_SESSION` (value: `"CHAT_SESSION"`)


<a name="LocalityEnum"></a>
## Enum: LocalityEnum

* `LOCAL_DEPLOYMENT` (value: `"LOCAL_DEPLOYMENT"`)
* `SAME_NETWORK` (value: `"SAME_NETWORK"`)
* `EXTERNAL_PROVIDER` (value: `"EXTERNAL_PROVIDER"`)

