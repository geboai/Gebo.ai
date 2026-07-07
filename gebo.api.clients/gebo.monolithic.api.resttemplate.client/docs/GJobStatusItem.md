# GJobStatusItem

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**error** | **Boolean** |  |  [optional]
**description** | **String** |  |  [optional]
**code** | **String** |  |  [optional]
**workflowType** | **String** |  |  [optional]
**workflowId** | **String** |  |  [optional]
**projectEndpointReference** | [**GObjectRefGProjectEndpoint**](GObjectRefGProjectEndpoint.md) |  |  [optional]
**startDateTime** | [**Date**](Date.md) |  |  [optional]
**finished** | **Boolean** |  |  [optional]
**endDateTime** | [**Date**](Date.md) |  |  [optional]
**processing** | **Boolean** |  |  [optional]
**jobType** | [**JobTypeEnum**](#JobTypeEnum) |  |  [optional]

<a name="JobTypeEnum"></a>
## Enum: JobTypeEnum
Name | Value
---- | -----
CONTENTS_READING | &quot;CONTENTS_READING&quot;
VECTORIZING_CONTENTS | &quot;VECTORIZING_CONTENTS&quot;
CONTENTS_READING_VECTORIZING | &quot;CONTENTS_READING_VECTORIZING&quot;
