# DetailedVersion

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**number** | **Integer** | The current version number. |  [optional]
**authorId** | **String** | The account ID of the user who created this version. |  [optional]
**message** | **String** | Message associated with the current version. |  [optional]
**createdAt** | [**OffsetDateTime**](OffsetDateTime.md) | Date and time when the version was created. In format \&quot;YYYY-MM-DDTHH:mm:ss.sssZ\&quot;. |  [optional]
**minorEdit** | **Boolean** | Describes if this version is a minor version. Email notifications and activity stream updates are not created for minor versions. |  [optional]
**contentTypeModified** | **Boolean** | Describes if the content type is modified in this version (e.g. page to blog) |  [optional]
**collaborators** | **List&lt;String&gt;** | The account IDs of users that collaborated on this version. |  [optional]
**prevVersion** | **Integer** | The version number of the version prior to this current content update. |  [optional]
**nextVersion** | **Integer** | The version number of the version after this current content update. |  [optional]
