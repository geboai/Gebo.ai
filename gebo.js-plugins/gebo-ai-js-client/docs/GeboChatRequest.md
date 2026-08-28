# GeboAiClient.GeboChatRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  | [optional] 
**userChatContextCode** | **String** |  | [optional] 
**chatProfileCode** | **String** |  | [optional] 
**chatModelCode** | **String** |  | [optional] 
**streamResponse** | **Boolean** |  | [optional] 
**query** | **String** |  | [optional] 
**rewrittenQuery** | **String** |  | [optional] 
**customRagConfig** | [**GeboRagRequestCustomConfig**](GeboRagRequestCustomConfig.md) |  | [optional] 
**choosedKnowledgeBases** | **[String]** |  | [optional] 
**chatPipelineProcessId** | **String** |  | [optional] 
**forcedRequestDocuments** | **[String]** |  | [optional] 
**userUploadedContents** | [**[UserUploadedContent]**](UserUploadedContent.md) |  | [optional] 
**deepSearchDataSources** | **[String]** |  | [optional] 
**userIntent** | **String** |  | [optional] 
**tokensSize** | **Number** |  | [optional] 

<a name="UserIntentEnum"></a>
## Enum: UserIntentEnum

* `QA` (value: `"QA"`)
* `HOWTO` (value: `"HOWTO"`)
* `DECISION` (value: `"DECISION"`)
* `SUMMARY` (value: `"SUMMARY"`)
* `PURE_SEARCH` (value: `"PURE_SEARCH"`)
* `ANALISYS` (value: `"ANALISYS"`)
* `IMAGE_GENERATION` (value: `"IMAGE_GENERATION"`)
* `UNKNOWN` (value: `"UNKNOWN"`)

