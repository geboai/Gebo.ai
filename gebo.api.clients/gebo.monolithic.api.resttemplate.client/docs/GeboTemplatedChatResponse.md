# GeboTemplatedChatResponse

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  |  [optional]
**userChatContextCode** | **String** |  |  [optional]
**usedChatModelCode** | **String** |  |  [optional]
**usedChatModelProvider** | **String** |  |  [optional]
**queryResponse** | **Object** |  |  [optional]
**windowOccupation** | [**GeboWorkingMemoryWindowOccupation**](GeboWorkingMemoryWindowOccupation.md) |  |  [optional]
**query** | **String** |  |  [optional]
**backendMessages** | [**List&lt;GUserMessage&gt;**](GUserMessage.md) |  |  [optional]
**forcedDocumentsRef** | [**List&lt;GResponseDocumentRef&gt;**](GResponseDocumentRef.md) |  |  [optional]
**documentsRef** | [**List&lt;GResponseDocumentRef&gt;**](GResponseDocumentRef.md) |  |  [optional]
**calledFunctions** | [**List&lt;CalledFunction&gt;**](CalledFunction.md) |  |  [optional]
**contextWindowStats** | [**ChatModelRequestContextWindowStats**](ChatModelRequestContextWindowStats.md) |  |  [optional]
**generatedResources** | [**List&lt;LLMGeneratedResource&gt;**](LLMGeneratedResource.md) |  |  [optional]
