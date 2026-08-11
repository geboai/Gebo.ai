# GeboAiClient.GJobStatus

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  | [optional] 
**description** | **String** |  | [optional] 
**userModified** | **String** |  | [optional] 
**userCreated** | **String** |  | [optional] 
**dateModified** | **Date** |  | [optional] 
**dateCreated** | **Date** |  | [optional] 
**jobType** | **String** |  | 
**workflowType** | **String** |  | 
**workflowId** | **String** |  | 
**processing** | **Boolean** |  | [optional] 
**finished** | **Boolean** |  | [optional] 
**error** | **Boolean** |  | [optional] 
**startDateTime** | **Date** |  | [optional] 
**endDateTime** | **Date** |  | [optional] 
**projectEndpointReference** | [**GObjectRefGProjectEndpoint**](GObjectRefGProjectEndpoint.md) |  | 
**knowledgeBaseCode** | **String** |  | 
**projectCode** | **String** |  | 
**parentJobCode** | **String** |  | [optional] 
**workflowStatus** | [**WorkflowStatus**](WorkflowStatus.md) |  | [optional] 

<a name="JobTypeEnum"></a>
## Enum: JobTypeEnum

* `CONTENTS_READING` (value: `"CONTENTS_READING"`)
* `VECTORIZING_CONTENTS` (value: `"VECTORIZING_CONTENTS"`)
* `CONTENTS_READING_VECTORIZING` (value: `"CONTENTS_READING_VECTORIZING"`)

