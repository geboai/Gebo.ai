# GJobStatusItem

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**error** | **Boolean** |  |  [optional]
**description** | **String** |  |  [optional]
**jobType** | [**JobTypeEnum**](#JobTypeEnum) |  |  [optional]
**workflowType** | **String** |  |  [optional]
**code** | **String** |  |  [optional]
**endDateTime** | [**Date**](Date.md) |  |  [optional]
**startDateTime** | [**Date**](Date.md) |  |  [optional]
**projectEndpointReference** | [**GObjectRefGProjectEndpoint**](GObjectRefGProjectEndpoint.md) |  |  [optional]
**workflowId** | **String** |  |  [optional]
**processing** | **Boolean** |  |  [optional]
**finished** | **Boolean** |  |  [optional]

<a name="JobTypeEnum"></a>
## Enum: JobTypeEnum
Name | Value
---- | -----
CONTENTS_READING | &quot;CONTENTS_READING&quot;
VECTORIZING_CONTENTS | &quot;VECTORIZING_CONTENTS&quot;
CONTENTS_READING_VECTORIZING | &quot;CONTENTS_READING_VECTORIZING&quot;
