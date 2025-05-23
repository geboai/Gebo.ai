# CustomContentVersion

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**createdAt** | [**OffsetDateTime**](OffsetDateTime.md) | Date and time when the version was created. In format \&quot;YYYY-MM-DDTHH:mm:ss.sssZ\&quot;. |  [optional]
**message** | **String** | Message associated with the current version. |  [optional]
**number** | **Integer** | The version number. |  [optional]
**minorEdit** | **Boolean** | Describes if this version is a minor version. Email notifications and activity stream updates are not created for minor versions. |  [optional]
**authorId** | **String** | The account ID of the user who created this version. |  [optional]
**custom** | [**VersionedEntity**](VersionedEntity.md) |  |  [optional]
