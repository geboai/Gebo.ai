# ContentCreate

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | The ID of the draft content. Required when publishing a draft. |  [optional]
**title** | **String** |  |  [optional]
**type** | **String** | The type of the new content. Custom content types defined by apps are also supported. eg. &#x27;page&#x27;, &#x27;blogpost&#x27;, &#x27;comment&#x27; etc. | 
**space** | [**ContentCreateSpace**](ContentCreateSpace.md) |  |  [optional]
**status** | [**StatusEnum**](#StatusEnum) | The status of the new content. |  [optional]
**container** | [**Map**](Map.md) |  |  [optional]
**ancestors** | [**List&lt;Map&gt;**](Map.md) | The parent content of the new content.  If you are creating a top-level &#x60;page&#x60; or &#x60;comment&#x60;, this can be left blank. If you are creating a child page, this is where the parent page id goes. If you are creating a child comment, this is where the parent comment id goes. Only one parent content id can be specified. |  [optional]
**body** | [**ContentCreateBody**](ContentCreateBody.md) |  |  [optional]

<a name="StatusEnum"></a>
## Enum: StatusEnum
Name | Value
---- | -----
CURRENT | &quot;current&quot;
DELETED | &quot;deleted&quot;
HISTORICAL | &quot;historical&quot;
DRAFT | &quot;draft&quot;
