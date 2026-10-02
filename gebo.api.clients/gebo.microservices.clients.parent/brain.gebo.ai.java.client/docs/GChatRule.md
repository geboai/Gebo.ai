# GChatRule

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  |  [optional]
**text** | **String** |  |  [optional]
**scope** | [**ScopeEnum**](#ScopeEnum) |  |  [optional]
**userChatContextCode** | **String** |  |  [optional]
**ownerUsername** | **String** |  |  [optional]
**accessibleToAll** | **Boolean** |  |  [optional]
**accessibleUsers** | **List&lt;String&gt;** |  |  [optional]
**accessibleGroups** | **List&lt;String&gt;** |  |  [optional]
**chatProfileCode** | **String** |  |  [optional]
**pipelineCode** | **String** |  |  [optional]
**enabled** | **Boolean** |  |  [optional]
**sourceUserChatContextCode** | **String** |  |  [optional]
**sourceRequestId** | **String** |  |  [optional]
**createdAt** | [**Date**](Date.md) |  |  [optional]
**modifiedAt** | [**Date**](Date.md) |  |  [optional]
**modifiedBy** | **String** |  |  [optional]
**changes** | [**List&lt;GChatRuleChange&gt;**](GChatRuleChange.md) |  |  [optional]

<a name="ScopeEnum"></a>
## Enum: ScopeEnum
Name | Value
---- | -----
SESSION | &quot;SESSION&quot;
USER | &quot;USER&quot;
SHARED | &quot;SHARED&quot;
