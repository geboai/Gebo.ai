# FooterCommentModel

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | ID of the comment. |  [optional]
**status** | [**ContentStatus**](ContentStatus.md) |  |  [optional]
**title** | **String** | Title of the comment. |  [optional]
**blogPostId** | **String** | ID of the blog post containing the comment if the comment is on a blog post. |  [optional]
**pageId** | **String** | ID of the page containing the comment if the comment is on a page. |  [optional]
**attachmentId** | **String** | ID of the attachment containing the comment if the comment is on an attachment. |  [optional]
**customContentId** | **String** | ID of the custom content containing the comment if the comment is on a custom content. |  [optional]
**parentCommentId** | **String** | ID of the parent comment if the comment is a reply. |  [optional]
**version** | [**Version**](Version.md) |  |  [optional]
**properties** | [**AttachmentSingleProperties**](AttachmentSingleProperties.md) |  |  [optional]
**operations** | [**AttachmentSingleOperations**](AttachmentSingleOperations.md) |  |  [optional]
**likes** | [**BlogPostSingleLikes**](BlogPostSingleLikes.md) |  |  [optional]
**versions** | [**AttachmentSingleVersions**](AttachmentSingleVersions.md) |  |  [optional]
**body** | [**BodySingle**](BodySingle.md) |  |  [optional]
**_links** | [**CommentLinks**](CommentLinks.md) |  |  [optional]
