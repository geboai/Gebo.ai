# GPromptTemplateConfig

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  |  [optional]
**description** | **String** |  |  [optional]
**systemPromptTemplate** | **String** |  |  [optional]
**userPromptTemplate** | **String** |  | 
**chatHistory** | [**ChatHistoryEnum**](#ChatHistoryEnum) |  | 
**contextDocuments** | [**ContextDocumentsEnum**](#ContextDocumentsEnum) |  | 
**toolsCalling** | [**ToolsCallingEnum**](#ToolsCallingEnum) |  | 
**langCode** | **String** |  |  [optional]
**promptUse** | **String** |  | 
**modelProvider** | **String** |  |  [optional]
**modelCode** | **String** |  |  [optional]
**promptCategory** | **String** |  |  [optional]
**tokensSize** | **Integer** |  |  [optional]
**configDeclarated** | **Boolean** |  |  [optional]
**agentPrompt** | **Boolean** |  |  [optional]
**agentId** | **String** |  |  [optional]

<a name="ChatHistoryEnum"></a>
## Enum: ChatHistoryEnum
Name | Value
---- | -----
REQUIRED | &quot;REQUIRED&quot;
NOT_REQUIRED | &quot;NOT_REQUIRED&quot;

<a name="ContextDocumentsEnum"></a>
## Enum: ContextDocumentsEnum
Name | Value
---- | -----
REQUIRED | &quot;REQUIRED&quot;
NOT_REQUIRED | &quot;NOT_REQUIRED&quot;

<a name="ToolsCallingEnum"></a>
## Enum: ToolsCallingEnum
Name | Value
---- | -----
REQUIRED | &quot;REQUIRED&quot;
NOT_REQUIRED | &quot;NOT_REQUIRED&quot;
