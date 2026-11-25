# ChatModelInputs

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  |  [optional]
**description** | **String** |  |  [optional]
**modelTypeCode** | **String** |  |  [optional]
**modelCode** | **String** |  |  [optional]
**inputModalities** | [**List&lt;InputModalitiesEnum&gt;**](#List&lt;InputModalitiesEnum&gt;) |  |  [optional]
**metadataInputModalities** | [**List&lt;MetadataInputModalitiesEnum&gt;**](#List&lt;MetadataInputModalitiesEnum&gt;) |  |  [optional]
**carriedMediaTypes** | [**GModelCarriedMediaTypes**](GModelCarriedMediaTypes.md) |  |  [optional]
**effectiveInputMimeTypes** | **List&lt;String&gt;** |  |  [optional]
**declaredNotCarried** | [**List&lt;DeclaredNotCarriedEnum&gt;**](#List&lt;DeclaredNotCarriedEnum&gt;) |  |  [optional]

<a name="List<InputModalitiesEnum>"></a>
## Enum: List&lt;InputModalitiesEnum&gt;
Name | Value
---- | -----
TEXT | &quot;TEXT&quot;
IMAGE | &quot;IMAGE&quot;
AUDIO | &quot;AUDIO&quot;
VIDEO | &quot;VIDEO&quot;
DOCUMENT | &quot;DOCUMENT&quot;

<a name="List<MetadataInputModalitiesEnum>"></a>
## Enum: List&lt;MetadataInputModalitiesEnum&gt;
Name | Value
---- | -----
TEXT | &quot;TEXT&quot;
IMAGE | &quot;IMAGE&quot;
AUDIO | &quot;AUDIO&quot;
VIDEO | &quot;VIDEO&quot;
DOCUMENT | &quot;DOCUMENT&quot;

<a name="List<DeclaredNotCarriedEnum>"></a>
## Enum: List&lt;DeclaredNotCarriedEnum&gt;
Name | Value
---- | -----
TEXT | &quot;TEXT&quot;
IMAGE | &quot;IMAGE&quot;
AUDIO | &quot;AUDIO&quot;
VIDEO | &quot;VIDEO&quot;
DOCUMENT | &quot;DOCUMENT&quot;
