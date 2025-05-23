# InlineCommentModel

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | ID of the comment. |  [optional]
**status** | [**ContentStatus**](ContentStatus.md) |  |  [optional]
**title** | **String** | Title of the comment. |  [optional]
**blogPostId** | **String** | ID of the blog post containing the comment if the comment is on a blog post. |  [optional]
**pageId** | **String** | ID of the page containing the comment if the comment is on a page. |  [optional]
**parentCommentId** | **String** | ID of the parent comment if the comment is a reply. |  [optional]
**version** | [**Version**](Version.md) |  |  [optional]
**body** | [**BodySingle**](BodySingle.md) |  |  [optional]
**resolutionLastModifierId** | **String** | Atlassian Account ID of last person who modified the resolve state of the comment. Null until comment is resolved or reopened. |  [optional]
**resolutionLastModifiedAt** | [**OffsetDateTime**](OffsetDateTime.md) | Timestamp of the last modification to the comment&#x27;s resolution status. Null until comment is resolved or reopened. |  [optional]
**resolutionStatus** | [**InlineCommentResolutionStatus**](InlineCommentResolutionStatus.md) |  |  [optional]
**properties** | [**InlineCommentModelProperties**](InlineCommentModelProperties.md) |  |  [optional]
**operations** | [**AttachmentSingleOperations**](AttachmentSingleOperations.md) |  |  [optional]
**likes** | [**BlogPostSingleLikes**](BlogPostSingleLikes.md) |  |  [optional]
**versions** | [**AttachmentSingleVersions**](AttachmentSingleVersions.md) |  |  [optional]
**_links** | [**CommentLinks**](CommentLinks.md) |  |  [optional]
