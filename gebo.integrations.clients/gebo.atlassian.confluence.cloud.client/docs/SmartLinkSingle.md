# SmartLinkSingle

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | ID of the Smart Link in the content tree. |  [optional]
**type** | **String** | The content type of the object. |  [optional]
**status** | [**ContentStatus**](ContentStatus.md) |  |  [optional]
**title** | **String** | Title of the Smart Link in the content tree. |  [optional]
**parentId** | **String** | ID of the parent content, or null if there is no parent content. |  [optional]
**parentType** | [**ParentContentType**](ParentContentType.md) |  |  [optional]
**position** | **Integer** | Position of the Smart Link within the given parent page tree. |  [optional]
**authorId** | **String** | The account ID of the user who created this Smart Link in the content tree originally. |  [optional]
**ownerId** | **String** | The account ID of the user who owns this Smart Link in the content tree. |  [optional]
**createdAt** | [**OffsetDateTime**](OffsetDateTime.md) | Date and time when the Smart Link in the content tree was created. In format \&quot;YYYY-MM-DDTHH:mm:ss.sssZ\&quot;. |  [optional]
**embedUrl** | **String** | The embedded URL of the Smart Link. If the Smart Link does not have an embedded URL, this property will not be included in the response. |  [optional]
**version** | [**Version**](Version.md) |  |  [optional]
**_links** | [**SmartLinkLinks**](SmartLinkLinks.md) |  |  [optional]
