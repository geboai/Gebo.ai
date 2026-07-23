# JobWorkflowStepSummary

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**workflowType** | **String** |  |  [optional]
**workflowId** | **String** |  |  [optional]
**workflowStepId** | **String** |  |  [optional]
**startDateTime** | [**Date**](Date.md) |  |  [optional]
**endDateTime** | [**Date**](Date.md) |  |  [optional]
**batchDocumentsInput** | **Long** |  |  [optional]
**batchDiscardedInput** | **Long** |  |  [optional]
**batchSentToNextStep** | **Long** |  |  [optional]
**chunksProcessed** | **Long** |  |  [optional]
**tokensProcessed** | **Long** |  |  [optional]
**batchDocumentsProcessingErrors** | **Long** |  |  [optional]
**batchDocumentsProcessed** | **Long** |  |  [optional]
**errorChunks** | **Long** |  |  [optional]
**errorTokens** | **Long** |  |  [optional]
**timesamples** | [**List&lt;JobWorkflowStepSummaryTimeSlotStats&gt;**](JobWorkflowStepSummaryTimeSlotStats.md) |  |  [optional]
