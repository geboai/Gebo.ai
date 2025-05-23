# UpdateInlineCommentModel

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**version** | [**UpdateFooterCommentModelVersion**](UpdateFooterCommentModelVersion.md) |  |  [optional]
**body** | **OneOfUpdateInlineCommentModelBody** |  |  [optional]
**resolved** | **Boolean** | Resolved state of the comment. Set to true to resolve the comment, set to false to reopen it. If matching the existing state (i.e. true -&gt; resolved or false -&gt; open/reopened) , no change will occur. A dangling comment cannot be updated. |  [optional]
