# GKnowledgeBase

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  |  [optional]
**description** | **String** |  |  [optional]
**userModified** | **String** |  |  [optional]
**userCreated** | **String** |  |  [optional]
**dateModified** | [**Date**](Date.md) |  |  [optional]
**dateCreated** | [**Date**](Date.md) |  |  [optional]
**accessibleGroups** | **List&lt;String&gt;** |  |  [optional]
**accessibleUsers** | **List&lt;String&gt;** |  |  [optional]
**accessibleToAll** | **Boolean** |  |  [optional]
**knowledgeBaseReferences** | **List&lt;String&gt;** |  |  [optional]
**projectsReferences** | **List&lt;String&gt;** |  |  [optional]
**embeddingModelReferences** | [**List&lt;GObjectRef&gt;**](GObjectRef.md) |  |  [optional]
**username** | **String** |  |  [optional]
**parentKnowledgebaseCode** | **String** |  |  [optional]
**objectSpaceType** | [**ObjectSpaceTypeEnum**](#ObjectSpaceTypeEnum) |  |  [optional]

<a name="ObjectSpaceTypeEnum"></a>
## Enum: ObjectSpaceTypeEnum
Name | Value
---- | -----
COMPANY | &quot;COMPANY&quot;
USERSPACE | &quot;USERSPACE&quot;
