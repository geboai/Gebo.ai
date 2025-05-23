# CustomContentSingle

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | ID of the custom content. |  [optional]
**type** | **String** | The type of custom content. |  [optional]
**status** | [**ContentStatus**](ContentStatus.md) |  |  [optional]
**title** | **String** | Title of the custom content. |  [optional]
**spaceId** | **String** | ID of the space the custom content is in.  Note: This is always returned, regardless of if the custom content has a container that is a space. |  [optional]
**pageId** | **String** | ID of the containing page.  Note: This is only returned if the custom content has a container that is a page. |  [optional]
**blogPostId** | **String** | ID of the containing blog post.  Note: This is only returned if the custom content has a container that is a blog post. |  [optional]
**customContentId** | **String** | ID of the containing custom content.  Note: This is only returned if the custom content has a container that is custom content. |  [optional]
**authorId** | **String** | The account ID of the user who created this custom content originally. |  [optional]
**createdAt** | [**OffsetDateTime**](OffsetDateTime.md) | Date and time when the custom content was created. In format \&quot;YYYY-MM-DDTHH:mm:ss.sssZ\&quot;. |  [optional]
**version** | [**Version**](Version.md) |  |  [optional]
**labels** | [**AttachmentSingleLabels**](AttachmentSingleLabels.md) |  |  [optional]
**properties** | [**AttachmentSingleProperties**](AttachmentSingleProperties.md) |  |  [optional]
**operations** | [**AttachmentSingleOperations**](AttachmentSingleOperations.md) |  |  [optional]
**versions** | [**AttachmentSingleVersions**](AttachmentSingleVersions.md) |  |  [optional]
**body** | [**CustomContentBodySingle**](CustomContentBodySingle.md) |  |  [optional]
**_links** | [**CustomContentLinks**](CustomContentLinks.md) |  |  [optional]
