# ComponentVectorStoreStatus

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**isSetup** | **Boolean** |  |  [optional]
**product** | [**ProductEnum**](#ProductEnum) |  |  [optional]
**qdrantConfig** | [**QdrantConfig**](QdrantConfig.md) |  |  [optional]
**redisConfig** | [**RedisConfig**](RedisConfig.md) |  |  [optional]

<a name="ProductEnum"></a>
## Enum: ProductEnum
Name | Value
---- | -----
MONGO | &quot;MONGO&quot;
QDRANT | &quot;QDRANT&quot;
LUCENE | &quot;LUCENE&quot;
REDIS | &quot;REDIS&quot;
TEST | &quot;TEST&quot;
