# ModelProviderCapabilities

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**configurationCode** | **String** |  |  [optional]
**supportsTranscript** | **Boolean** |  |  [optional]
**supportsSpeech** | **Boolean** |  |  [optional]
**supportsStructuredOutput** | **Boolean** |  |  [optional]
**supportsFunctionCalls** | **Boolean** |  |  [optional]
**functionsTreeList** | [**List&lt;ToolCategoriesTree&gt;**](ToolCategoriesTree.md) |  |  [optional]
**inputModalities** | [**List&lt;InputModalitiesEnum&gt;**](#List&lt;InputModalitiesEnum&gt;) |  |  [optional]
**inputMimeTypes** | **List&lt;String&gt;** |  |  [optional]

<a name="List<InputModalitiesEnum>"></a>
## Enum: List&lt;InputModalitiesEnum&gt;
Name | Value
---- | -----
TEXT | &quot;TEXT&quot;
IMAGE | &quot;IMAGE&quot;
AUDIO | &quot;AUDIO&quot;
VIDEO | &quot;VIDEO&quot;
DOCUMENT | &quot;DOCUMENT&quot;
