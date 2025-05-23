# UserDetails

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**accountId** | **String** | The account ID of the user, which uniquely identifies the user across all Atlassian products. For example, *5b10ac8d82e05b22cc7d4ef5*. |  [optional]
**accountType** | **String** | The type of account represented by this user. This will be one of &#x27;atlassian&#x27; (normal users), &#x27;app&#x27; (application user) or &#x27;customer&#x27; (Jira Service Desk customer user) |  [optional]
**active** | **Boolean** | Whether the user is active. |  [optional]
**avatarUrls** | **AllOfUserDetailsAvatarUrls** | The avatars of the user. |  [optional]
**displayName** | **String** | The display name of the user. Depending on the user’s privacy settings, this may return an alternative value. |  [optional]
**emailAddress** | **String** | The email address of the user. Depending on the user’s privacy settings, this may be returned as null. |  [optional]
**key** | **String** | This property is no longer available and will be removed from the documentation soon. See the [deprecation notice](https://developer.atlassian.com/cloud/jira/platform/deprecation-notice-user-privacy-api-migration-guide/) for details. |  [optional]
**name** | **String** | This property is no longer available and will be removed from the documentation soon. See the [deprecation notice](https://developer.atlassian.com/cloud/jira/platform/deprecation-notice-user-privacy-api-migration-guide/) for details. |  [optional]
**self** | **String** | The URL of the user. |  [optional]
**timeZone** | **String** | The time zone specified in the user&#x27;s profile. Depending on the user’s privacy settings, this may be returned as null. |  [optional]
