# RagQueryOptions

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**topK** | **Integer** |  |  [optional]
**similarityThreashold** | **Double** |  |  [optional]
**maxTokens** | **Long** |  |  [optional]
**completeness** | [**CompletenessEnum**](#CompletenessEnum) |  |  [optional]

<a name="CompletenessEnum"></a>
## Enum: CompletenessEnum
Name | Value
---- | -----
STRICT_QUERY_RELATED | &quot;STRICT_QUERY_RELATED&quot;
FULL_DOCUMENTS | &quot;FULL_DOCUMENTS&quot;
MAX_TOKENS | &quot;MAX_TOKENS&quot;
