# ChunkingParams

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**chunkingPolicy** | [**ChunkingPolicyEnum**](#ChunkingPolicyEnum) |  |  [optional]
**tokensThreashold** | **Integer** |  |  [optional]
**keywordHits** | **Integer** |  |  [optional]
**matchingKeywords** | **List&lt;String&gt;** |  |  [optional]
**chunkingSpecs** | [**List&lt;AbstractChunkingSpecs&gt;**](AbstractChunkingSpecs.md) |  |  [optional]
**enrichWithMetaData** | **Boolean** |  |  [optional]
**tokensPerChunkSet** | **Long** |  |  [optional]
**sampledTokens** | **Long** |  |  [optional]
**samplingMode** | **Boolean** |  |  [optional]

<a name="ChunkingPolicyEnum"></a>
## Enum: ChunkingPolicyEnum
Name | Value
---- | -----
SPLIT_CHUNKS | &quot;SPLIT_CHUNKS&quot;
ONLY_MATCHING_CHUNKS | &quot;ONLY_MATCHING_CHUNKS&quot;
MATCHING_CHUNKS_AFTER_THREASHOLD | &quot;MATCHING_CHUNKS_AFTER_THREASHOLD&quot;
