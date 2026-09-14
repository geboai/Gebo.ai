# DocumentChunk

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**originalDocumentCode** | **String** |  |  [optional]
**id** | **String** |  |  [optional]
**mimeType** | **String** |  |  [optional]
**chunkType** | [**ChunkTypeEnum**](#ChunkTypeEnum) |  |  [optional]
**chunkData** | **String** |  |  [optional]
**metaData** | **Map&lt;String, Object&gt;** |  |  [optional]
**tokensSize** | **Long** |  |  [optional]
**bytesSize** | **Long** |  |  [optional]
**chunkPosition** | **Long** |  |  [optional]
**chunksCount** | **Long** |  |  [optional]
**chunkingSessionId** | **String** |  |  [optional]

<a name="ChunkTypeEnum"></a>
## Enum: ChunkTypeEnum
Name | Value
---- | -----
TEXT | &quot;TEXT&quot;
IMAGE | &quot;IMAGE&quot;
