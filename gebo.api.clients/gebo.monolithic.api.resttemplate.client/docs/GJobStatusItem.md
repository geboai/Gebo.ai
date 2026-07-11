# GJobStatusItem

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**description** | **String** |  |  [optional]
**workflowId** | **String** |  |  [optional]
**error** | **Boolean** |  |  [optional]
**code** | **String** |  |  [optional]
**startDateTime** | [**Date**](Date.md) |  |  [optional]
**jobType** | [**JobTypeEnum**](#JobTypeEnum) |  |  [optional]
**processing** | **Boolean** |  |  [optional]
**finished** | **Boolean** |  |  [optional]
**endDateTime** | [**Date**](Date.md) |  |  [optional]
**workflowType** | **String** |  |  [optional]
**projectEndpointReference** | [**GObjectRefGProjectEndpoint**](GObjectRefGProjectEndpoint.md) |  |  [optional]

<a name="JobTypeEnum"></a>
## Enum: JobTypeEnum
Name | Value
---- | -----
CONTENTS_READING | &quot;CONTENTS_READING&quot;
VECTORIZING_CONTENTS | &quot;VECTORIZING_CONTENTS&quot;
CONTENTS_READING_VECTORIZING | &quot;CONTENTS_READING_VECTORIZING&quot;
