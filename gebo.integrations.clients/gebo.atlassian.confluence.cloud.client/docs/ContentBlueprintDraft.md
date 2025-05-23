# ContentBlueprintDraft

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**version** | [**Map**](Map.md) |  | 
**title** | **String** | The title of the content. If you don&#x27;t want to change the title, set this to the current title of the draft. | 
**type** | [**TypeEnum**](#TypeEnum) | The type of content. Set this to &#x60;page&#x60;. | 
**status** | [**StatusEnum**](#StatusEnum) | The status of the content. Set this to &#x60;current&#x60; or omit it altogether. |  [optional]
**space** | [**Map**](Map.md) |  |  [optional]
**ancestors** | [**List&lt;ContentBlueprintDraftAncestors&gt;**](ContentBlueprintDraftAncestors.md) | The new ancestor (i.e. parent page) for the content. If you have specified an ancestor, you must also specify a &#x60;space&#x60; property in the request body for the space that the ancestor is in.  Note, if you specify more than one ancestor, the last ID in the array will be selected as the parent page for the content. |  [optional]

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
PAGE | &quot;page&quot;

<a name="StatusEnum"></a>
## Enum: StatusEnum
Name | Value
---- | -----
CURRENT | &quot;current&quot;
