# WhiteboardSingle

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | ID of the whiteboard. |  [optional]
**type** | **String** | The content type of the object. |  [optional]
**status** | [**ContentStatus**](ContentStatus.md) |  |  [optional]
**title** | **String** | Title of the whiteboard. |  [optional]
**parentId** | **String** | ID of the parent content, or null if there is no parent content. |  [optional]
**parentType** | [**ParentContentType**](ParentContentType.md) |  |  [optional]
**position** | **Integer** | Position of the whiteboard within the given parent page tree. |  [optional]
**authorId** | **String** | The account ID of the user who created this whiteboard originally. |  [optional]
**ownerId** | **String** | The account ID of the user who owns this whiteboard. |  [optional]
**createdAt** | [**OffsetDateTime**](OffsetDateTime.md) | Date and time when the whiteboard was created. In format \&quot;YYYY-MM-DDTHH:mm:ss.sssZ\&quot;. |  [optional]
**version** | [**Version**](Version.md) |  |  [optional]
**_links** | [**WhiteboardLinks**](WhiteboardLinks.md) |  |  [optional]
