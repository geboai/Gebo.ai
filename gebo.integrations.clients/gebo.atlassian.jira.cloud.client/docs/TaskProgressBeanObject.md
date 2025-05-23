# TaskProgressBeanObject

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**description** | **String** | The description of the task. |  [optional]
**elapsedRuntime** | **Long** | The execution time of the task, in milliseconds. | 
**finished** | **Long** | A timestamp recording when the task was finished. |  [optional]
**id** | **String** | The ID of the task. | 
**lastUpdate** | **Long** | A timestamp recording when the task progress was last updated. | 
**message** | **String** | Information about the progress of the task. |  [optional]
**progress** | **Long** | The progress of the task, as a percentage complete. | 
**result** | **Object** | The result of the task execution. |  [optional]
**self** | **String** | The URL of the task. | 
**started** | **Long** | A timestamp recording when the task was started. |  [optional]
**status** | [**StatusEnum**](#StatusEnum) | The status of the task. | 
**submitted** | **Long** | A timestamp recording when the task was submitted. | 
**submittedBy** | **Long** | The ID of the user who submitted the task. | 

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
