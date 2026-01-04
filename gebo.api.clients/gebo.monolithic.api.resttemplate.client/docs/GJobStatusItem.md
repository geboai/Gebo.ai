# GJobStatusItem

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**finished** | **Boolean** |  |  [optional]
**processing** | **Boolean** |  |  [optional]
**endDateTime** | [**Date**](Date.md) |  |  [optional]
**error** | **Boolean** |  |  [optional]
**description** | **String** |  |  [optional]
**code** | **String** |  |  [optional]
**workflowType** | **String** |  |  [optional]
**workflowId** | **String** |  |  [optional]
**jobType** | [**JobTypeEnum**](#JobTypeEnum) |  |  [optional]
**startDateTime** | [**Date**](Date.md) |  |  [optional]
**projectEndpointReference** | [**GObjectRefGProjectEndpoint**](GObjectRefGProjectEndpoint.md) |  |  [optional]

<a name="JobTypeEnum"></a>
## Enum: JobTypeEnum
Name | Value
---- | -----
CONTENTS_READING | &quot;CONTENTS_READING&quot;
VECTORIZING_CONTENTS | &quot;VECTORIZING_CONTENTS&quot;
CONTENTS_READING_VECTORIZING | &quot;CONTENTS_READING_VECTORIZING&quot;
