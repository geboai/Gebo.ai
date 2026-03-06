# PipelineUserChatMenuItem

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**optionId** | **String** |  | 
**description** | **String** |  | 
**defaultOption** | **Boolean** |  |  [optional]
**routeOption** | [**RouteOptionEnum**](#RouteOptionEnum) |  |  [optional]
**pipelineId** | **String** |  | 

<a name="RouteOptionEnum"></a>
## Enum: RouteOptionEnum
Name | Value
---- | -----
PURE_LLM_RESPONSE | &quot;PURE_LLM_RESPONSE&quot;
RAG_LLM_RESPONSE | &quot;RAG_LLM_RESPONSE&quot;
DEEP_SEARCH_RESPONSE | &quot;DEEP_SEARCH_RESPONSE&quot;
SHALLOW_SEARCH_RESPONSE | &quot;SHALLOW_SEARCH_RESPONSE&quot;
DEEP_RAG_RESPONSE | &quot;DEEP_RAG_RESPONSE&quot;
TOOLS_USE_RESPONSE | &quot;TOOLS_USE_RESPONSE&quot;
