# BulkOperationProgress

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**created** | [**Date**](Date.md) | A timestamp of when the task was submitted. |  [optional]
**failedAccessibleIssues** | [**Map&lt;String, List&lt;String&gt;&gt;**](List.md) | Map of issue IDs for which the operation failed and that the user has permission to view, to their one or more reasons for failure. These reasons are open-ended text descriptions of the error and are not selected from a predefined list of standard reasons. |  [optional]
**invalidOrInaccessibleIssueCount** | **Integer** | The number of issues that are either invalid or issues that the user doesn&#x27;t have permission to view, regardless of the success or failure of the operation. |  [optional]
**processedAccessibleIssues** | **List&lt;Long&gt;** | List of issue IDs for which the operation was successful and that the user has permission to view. |  [optional]
**progressPercent** | **Long** | Progress of the task as a percentage. |  [optional]
**started** | [**Date**](Date.md) | A timestamp of when the task was started. |  [optional]
**status** | [**StatusEnum**](#StatusEnum) | The status of the task. |  [optional]
**submittedBy** | [**User**](User.md) |  |  [optional]
**taskId** | **String** | The ID of the task. |  [optional]
**totalIssueCount** | **Integer** | The number of issues that the bulk operation was attempted on. |  [optional]
**updated** | [**Date**](Date.md) | A timestamp of when the task progress was last updated. |  [optional]

<a name="StatusEnum"></a>
## Enum: StatusEnum
Name | Value
---- | -----
ENQUEUED | &quot;ENQUEUED&quot;
RUNNING | &quot;RUNNING&quot;
COMPLETE | &quot;COMPLETE&quot;
FAILED | &quot;FAILED&quot;
CANCEL_REQUESTED | &quot;CANCEL_REQUESTED&quot;
CANCELLED | &quot;CANCELLED&quot;
DEAD | &quot;DEAD&quot;
