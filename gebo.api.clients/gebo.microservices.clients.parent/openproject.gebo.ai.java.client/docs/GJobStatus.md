# GJobStatus

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  |  [optional]
**description** | **String** |  |  [optional]
**userModified** | **String** |  |  [optional]
**userCreated** | **String** |  |  [optional]
**dateModified** | [**Date**](Date.md) |  |  [optional]
**dateCreated** | [**Date**](Date.md) |  |  [optional]
**jobType** | [**JobTypeEnum**](#JobTypeEnum) |  | 
**workflowType** | **String** |  | 
**workflowId** | **String** |  | 
**processing** | **Boolean** |  |  [optional]
**finished** | **Boolean** |  |  [optional]
**error** | **Boolean** |  |  [optional]
**startDateTime** | [**Date**](Date.md) |  |  [optional]
**endDateTime** | [**Date**](Date.md) |  |  [optional]
**projectEndpointReference** | [**GObjectRefGProjectEndpoint**](GObjectRefGProjectEndpoint.md) |  | 
**knowledgeBaseCode** | **String** |  | 
**projectCode** | **String** |  | 
**parentJobCode** | **String** |  |  [optional]
**workflowStatus** | [**WorkflowStatus**](WorkflowStatus.md) |  |  [optional]

<a name="JobTypeEnum"></a>
## Enum: JobTypeEnum
Name | Value
---- | -----
CONTENTS_READING | &quot;CONTENTS_READING&quot;
VECTORIZING_CONTENTS | &quot;VECTORIZING_CONTENTS&quot;
CONTENTS_READING_VECTORIZING | &quot;CONTENTS_READING_VECTORIZING&quot;
