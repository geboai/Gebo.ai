# Task

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | ID of the task. |  [optional]
**localId** | **String** | Local ID of the task. This ID is local to the corresponding page or blog post. |  [optional]
**spaceId** | **String** | ID of the space the task is in. |  [optional]
**pageId** | **String** | ID of the page the task is in. |  [optional]
**blogPostId** | **String** | ID of the blog post the task is in. |  [optional]
**status** | [**StatusEnum**](#StatusEnum) | Status of the task. |  [optional]
**body** | [**TaskBodySingle**](TaskBodySingle.md) |  |  [optional]
**createdBy** | **String** | Account ID of the user who created this task. |  [optional]
**assignedTo** | **String** | Account ID of the user to whom this task is assigned. |  [optional]
**completedBy** | **String** | Account ID of the user who completed this task. |  [optional]
**createdAt** | [**OffsetDateTime**](OffsetDateTime.md) | Date and time when the task was created. In format \&quot;YYYY-MM-DDTHH:mm:ss.sssZ\&quot;. |  [optional]
**updatedAt** | [**OffsetDateTime**](OffsetDateTime.md) | Date and time when the task was updated. In format \&quot;YYYY-MM-DDTHH:mm:ss.sssZ\&quot;. |  [optional]
**dueAt** | [**OffsetDateTime**](OffsetDateTime.md) | Date and time when the task is due. In format \&quot;YYYY-MM-DDTHH:mm:ss.sssZ\&quot;. |  [optional]
**completedAt** | [**OffsetDateTime**](OffsetDateTime.md) | Date and time when the task was completed. In format \&quot;YYYY-MM-DDTHH:mm:ss.sssZ\&quot;. |  [optional]

<a name="StatusEnum"></a>
## Enum: StatusEnum
Name | Value
---- | -----
COMPLETE | &quot;complete&quot;
INCOMPLETE | &quot;incomplete&quot;
