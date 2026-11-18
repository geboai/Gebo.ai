# GJobStatusItem

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**description** | **String** |  |  [optional]
**error** | **Boolean** |  |  [optional]
**code** | **String** |  |  [optional]
**startDateTime** | [**Date**](Date.md) |  |  [optional]
**finished** | **Boolean** |  |  [optional]
**processing** | **Boolean** |  |  [optional]
**endDateTime** | [**Date**](Date.md) |  |  [optional]
**projectEndpointReference** | [**GObjectRefGProjectEndpoint**](GObjectRefGProjectEndpoint.md) |  |  [optional]
**workflowType** | **String** |  |  [optional]
**workflowId** | **String** |  |  [optional]
**jobType** | [**JobTypeEnum**](#JobTypeEnum) |  |  [optional]

<a name="JobTypeEnum"></a>
## Enum: JobTypeEnum
Name | Value
---- | -----
CONTENTS_READING | &quot;CONTENTS_READING&quot;
VECTORIZING_CONTENTS | &quot;VECTORIZING_CONTENTS&quot;
CONTENTS_READING_VECTORIZING | &quot;CONTENTS_READING_VECTORIZING&quot;
