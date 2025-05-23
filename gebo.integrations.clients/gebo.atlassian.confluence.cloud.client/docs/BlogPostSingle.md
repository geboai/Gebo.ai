# BlogPostSingle

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | ID of the blog post. |  [optional]
**status** | [**BlogPostContentStatus**](BlogPostContentStatus.md) |  |  [optional]
**title** | **String** | Title of the blog post. |  [optional]
**spaceId** | **String** | ID of the space the blog post is in. |  [optional]
**authorId** | **String** | The account ID of the user who created this blog post originally. |  [optional]
**createdAt** | [**OffsetDateTime**](OffsetDateTime.md) | Date and time when the blog post was created. In format \&quot;YYYY-MM-DDTHH:mm:ss.sssZ\&quot;. |  [optional]
**version** | [**Version**](Version.md) |  |  [optional]
**body** | [**BodySingle**](BodySingle.md) |  |  [optional]
**labels** | [**AttachmentSingleLabels**](AttachmentSingleLabels.md) |  |  [optional]
**properties** | [**AttachmentSingleProperties**](AttachmentSingleProperties.md) |  |  [optional]
**operations** | [**AttachmentSingleOperations**](AttachmentSingleOperations.md) |  |  [optional]
**likes** | [**BlogPostSingleLikes**](BlogPostSingleLikes.md) |  |  [optional]
**versions** | [**AttachmentSingleVersions**](AttachmentSingleVersions.md) |  |  [optional]
**isFavoritedByCurrentUser** | **Boolean** | Whether the blog post has been favorited by the current user. |  [optional]
**_links** | [**AbstractPageLinks**](AbstractPageLinks.md) |  |  [optional]
