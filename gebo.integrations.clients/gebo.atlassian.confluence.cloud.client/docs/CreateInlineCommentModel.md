# CreateInlineCommentModel

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**blogPostId** | **String** | ID of the containing blog post, if intending to create a top level footer comment. Do not provide if creating a reply. |  [optional]
**pageId** | **String** | ID of the containing page, if intending to create a top level footer comment. Do not provide if creating a reply. |  [optional]
**parentCommentId** | **String** | ID of the parent comment, if intending to create a reply. Do not provide if creating a top level comment. |  [optional]
**body** | **OneOfCreateInlineCommentModelBody** |  |  [optional]
**inlineCommentProperties** | [**CreateInlineCommentModelInlineCommentProperties**](CreateInlineCommentModelInlineCommentProperties.md) |  |  [optional]
