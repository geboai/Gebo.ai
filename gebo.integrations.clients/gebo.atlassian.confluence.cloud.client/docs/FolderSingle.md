# FolderSingle

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | ID of the folder. |  [optional]
**type** | **String** | The content type of the object. |  [optional]
**status** | [**ContentStatus**](ContentStatus.md) |  |  [optional]
**title** | **String** | Title of the folder. |  [optional]
**parentId** | **String** | ID of the parent content, or null if there is no parent content. |  [optional]
**parentType** | [**ParentContentType**](ParentContentType.md) |  |  [optional]
**position** | **Integer** | Position of the folder within the given parent page tree. |  [optional]
**authorId** | **String** | The account ID of the user who created this folder. |  [optional]
**ownerId** | **String** | The account ID of the user who owns this folder. |  [optional]
**createdAt** | [**OffsetDateTime**](OffsetDateTime.md) | Date and time when the folder was created. In format \&quot;YYYY-MM-DDTHH:mm:ss.sssZ\&quot;. |  [optional]
**version** | [**Version**](Version.md) |  |  [optional]
**_links** | [**FolderLinks**](FolderLinks.md) |  |  [optional]
