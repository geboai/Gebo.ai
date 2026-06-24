# GeboChatRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  |  [optional]
**userChatContextCode** | **String** |  |  [optional]
**chatProfileCode** | **String** |  |  [optional]
**chatModelCode** | **String** |  |  [optional]
**streamResponse** | **Boolean** |  |  [optional]
**query** | **String** |  |  [optional]
**rewrittenQuery** | **String** |  |  [optional]
**customRagConfig** | [**GeboRagRequestCustomConfig**](GeboRagRequestCustomConfig.md) |  |  [optional]
**choosedKnowledgeBases** | **List&lt;String&gt;** |  |  [optional]
**chatPipelineProcessId** | **String** |  |  [optional]
**forcedRequestDocuments** | **List&lt;String&gt;** |  |  [optional]
**userUploadedContents** | [**List&lt;UserUploadedContent&gt;**](UserUploadedContent.md) |  |  [optional]
**deepSearchDataSources** | **List&lt;String&gt;** |  |  [optional]
**userIntent** | [**UserIntentEnum**](#UserIntentEnum) |  |  [optional]
**tokensSize** | **Integer** |  |  [optional]

<a name="UserIntentEnum"></a>
## Enum: UserIntentEnum
Name | Value
---- | -----
QA | &quot;QA&quot;
HOWTO | &quot;HOWTO&quot;
DECISION | &quot;DECISION&quot;
SUMMARY | &quot;SUMMARY&quot;
PURE_SEARCH | &quot;PURE_SEARCH&quot;
ANALISYS | &quot;ANALISYS&quot;
UNKNOWN | &quot;UNKNOWN&quot;
