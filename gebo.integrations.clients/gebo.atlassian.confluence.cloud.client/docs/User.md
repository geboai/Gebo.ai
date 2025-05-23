# User

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**displayName** | **String** | Display name of the user. |  [optional]
**timeZone** | **String** | Time zone of the user. Depending on the user&#x27;s privacy setting, this may return null. |  [optional]
**personalSpaceId** | **String** | Space ID of the user&#x27;s personal space. Returns null, if no personal space for the user. |  [optional]
**isExternalCollaborator** | **Boolean** | Whether the user is an external collaborator. |  [optional]
**accountStatus** | [**AccountStatus**](AccountStatus.md) |  |  [optional]
**accountId** | **String** | Account ID of the user. |  [optional]
**email** | **String** | The email address of the user. Depending on the user&#x27;s privacy setting, this may return an empty string. |  [optional]
**accountType** | [**AccountType**](AccountType.md) |  |  [optional]
**publicName** | **String** | Public name of the user. |  [optional]
**profilePicture** | [**Icon**](Icon.md) |  |  [optional]
