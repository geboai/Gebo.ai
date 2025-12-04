# GeboMongoVectorStoreConfig

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  |  [optional]
**product** | [**ProductEnum**](#ProductEnum) |  | 
**qdrantConfig** | [**QdrantConfig**](QdrantConfig.md) |  |  [optional]
**luceneConfig** | [**LuceneConfig**](LuceneConfig.md) |  |  [optional]
**mongoConfig** | [**MongoConfig**](MongoConfig.md) |  |  [optional]
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
