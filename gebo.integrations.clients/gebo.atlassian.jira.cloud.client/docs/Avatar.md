# Avatar

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**fileName** | **String** | The file name of the avatar icon. Returned for system avatars. |  [optional]
**id** | **String** | The ID of the avatar. | 
**isDeletable** | **Boolean** | Whether the avatar can be deleted. |  [optional]
**isSelected** | **Boolean** | Whether the avatar is used in Jira. For example, shown as a project&#x27;s avatar. |  [optional]
**isSystemAvatar** | **Boolean** | Whether the avatar is a system avatar. |  [optional]
**owner** | **String** | The owner of the avatar. For a system avatar the owner is null (and nothing is returned). For non-system avatars this is the appropriate identifier, such as the ID for a project or the account ID for a user. |  [optional]
**urls** | **Map&lt;String, String&gt;** | The list of avatar icon URLs. |  [optional]
