# GJobStatusItem

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**description** | **String** |  |  [optional]
**error** | **Boolean** |  |  [optional]
**code** | **String** |  |  [optional]
**workflowId** | **String** |  |  [optional]
**workflowType** | **String** |  |  [optional]
**projectEndpointReference** | [**GObjectRefGProjectEndpoint**](GObjectRefGProjectEndpoint.md) |  |  [optional]
**startDateTime** | [**Date**](Date.md) |  |  [optional]
**endDateTime** | [**Date**](Date.md) |  |  [optional]
**finished** | **Boolean** |  |  [optional]
**jobType** | [**JobTypeEnum**](#JobTypeEnum) |  |  [optional]
**processing** | **Boolean** |  |  [optional]

<a name="JobTypeEnum"></a>
## Enum: JobTypeEnum
Name | Value
---- | -----
CONTENTS_READING | &quot;CONTENTS_READING&quot;
VECTORIZING_CONTENTS | &quot;VECTORIZING_CONTENTS&quot;
CONTENTS_READING_VECTORIZING | &quot;CONTENTS_READING_VECTORIZING&quot;
