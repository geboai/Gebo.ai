# PageSingle

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | ID of the page. |  [optional]
**status** | [**ContentStatus**](ContentStatus.md) |  |  [optional]
**title** | **String** | Title of the page. |  [optional]
**spaceId** | **String** | ID of the space the page is in. |  [optional]
**parentId** | **String** | ID of the parent page, or null if there is no parent page. |  [optional]
**parentType** | [**ParentContentType**](ParentContentType.md) |  |  [optional]
**position** | **Integer** | Position of child page within the given parent page tree. |  [optional]
**authorId** | **String** | The account ID of the user who created this page originally. |  [optional]
**ownerId** | **String** | The account ID of the user who owns this page. |  [optional]
**lastOwnerId** | **String** | The account ID of the user who owned this page previously, or null if there is no previous owner. |  [optional]
**createdAt** | [**OffsetDateTime**](OffsetDateTime.md) | Date and time when the page was created. In format \&quot;YYYY-MM-DDTHH:mm:ss.sssZ\&quot;. |  [optional]
**version** | [**Version**](Version.md) |  |  [optional]
**body** | [**BodySingle**](BodySingle.md) |  |  [optional]
**labels** | [**AttachmentSingleLabels**](AttachmentSingleLabels.md) |  |  [optional]
**properties** | [**AttachmentSingleProperties**](AttachmentSingleProperties.md) |  |  [optional]
**operations** | [**AttachmentSingleOperations**](AttachmentSingleOperations.md) |  |  [optional]
**likes** | [**BlogPostSingleLikes**](BlogPostSingleLikes.md) |  |  [optional]
**versions** | [**AttachmentSingleVersions**](AttachmentSingleVersions.md) |  |  [optional]
**isFavoritedByCurrentUser** | **Boolean** | Whether the page has been favorited by the current user. |  [optional]
**_links** | [**AbstractPageLinks**](AbstractPageLinks.md) |  |  [optional]
