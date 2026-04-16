# DeepSearchRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  |  [optional]
**description** | **String** |  |  [optional]
**userModified** | **String** |  |  [optional]
**userCreated** | **String** |  |  [optional]
**dateModified** | [**Date**](Date.md) |  |  [optional]
**dateCreated** | [**Date**](Date.md) |  |  [optional]
**username** | **String** |  | 
**query** | **String** |  | 
**knowledgeBases** | **List&lt;String&gt;** |  |  [optional]
**userChatContextCode** | **String** |  |  [optional]
**chatRequestCode** | **String** |  |  [optional]
**deepSearchDataSources** | **List&lt;String&gt;** |  |  [optional]
**userIntent** | [**UserIntentEnum**](#UserIntentEnum) |  |  [optional]

<a name="UserIntentEnum"></a>
## Enum: UserIntentEnum
Name | Value
---- | -----
QA | &quot;QA&quot;
REPORT | &quot;REPORT&quot;
HOWTO | &quot;HOWTO&quot;
DECISION | &quot;DECISION&quot;
SUMMARY | &quot;SUMMARY&quot;
UNKNOWN | &quot;UNKNOWN&quot;
