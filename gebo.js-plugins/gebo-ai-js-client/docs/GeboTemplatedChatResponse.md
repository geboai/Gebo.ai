# GeboAiClient.GeboTemplatedChatResponse

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  | [optional] 
**userChatContextCode** | **String** |  | [optional] 
**usedChatModelCode** | **String** |  | [optional] 
**usedChatModelProvider** | **String** |  | [optional] 
**queryResponse** | **Object** |  | [optional] 
**windowOccupation** | [**GeboWorkingMemoryWindowOccupation**](GeboWorkingMemoryWindowOccupation.md) |  | [optional] 
**query** | **String** |  | [optional] 
**thinkingOutputs** | **[String]** |  | [optional] 
**backendMessages** | [**[GUserMessage]**](GUserMessage.md) |  | [optional] 
**forcedDocumentsRef** | [**[GResponseDocumentRef]**](GResponseDocumentRef.md) |  | [optional] 
**documentsRef** | [**[GResponseDocumentRef]**](GResponseDocumentRef.md) |  | [optional] 
**calledFunctions** | [**[CalledFunction]**](CalledFunction.md) |  | [optional] 
**contextWindowStats** | [**ChatModelRequestContextWindowStats**](ChatModelRequestContextWindowStats.md) |  | [optional] 
**generatedResources** | [**[LLMGeneratedResource]**](LLMGeneratedResource.md) |  | [optional] 
**pipelineRouterDecisionCode** | **String** |  | [optional] 
**pipelineParams** | **{String: Object}** |  | [optional] 
**deepSearchRequestId** | **String** |  | [optional] 
