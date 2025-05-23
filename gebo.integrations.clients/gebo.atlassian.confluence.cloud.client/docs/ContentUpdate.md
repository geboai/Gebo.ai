# ContentUpdate

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**version** | [**Map**](Map.md) |  | 
**title** | **String** | The updated title of the content. If you are updating a non-draft &#x60;page&#x60; or &#x60;blogpost&#x60;, title is required. If you are not changing the title, set this field to the the current title. |  [optional]
**type** | **String** | The type of content. Set this to the current type of the content. For example, - page - blogpost - comment - attachment | 
**status** | [**StatusEnum**](#StatusEnum) | The updated status of the content. Note, if you change the status of a page from &#x27;current&#x27; to &#x27;draft&#x27; and it has an existing draft, the existing draft will be deleted in favor of the updated page. |  [optional]
**ancestors** | [**List&lt;Map&gt;**](Map.md) | The new parent for the content. Only one parent content &#x27;id&#x27; can be specified. |  [optional]
**body** | [**ContentUpdateBody**](ContentUpdateBody.md) |  |  [optional]

<a name="StatusEnum"></a>
## Enum: StatusEnum
Name | Value
---- | -----
CURRENT | &quot;current&quot;
TRASHED | &quot;trashed&quot;
DELETED | &quot;deleted&quot;
HISTORICAL | &quot;historical&quot;
DRAFT | &quot;draft&quot;
