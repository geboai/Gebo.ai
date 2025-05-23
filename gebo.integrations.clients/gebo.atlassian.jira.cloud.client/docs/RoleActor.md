# RoleActor

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**actorGroup** | **AllOfRoleActorActorGroup** |  |  [optional]
**actorUser** | **AllOfRoleActorActorUser** |  |  [optional]
**avatarUrl** | **String** | The avatar of the role actor. |  [optional]
**displayName** | **String** | The display name of the role actor. For users, depending on the user’s privacy setting, this may return an alternative value for the user&#x27;s name. |  [optional]
**id** | **Long** | The ID of the role actor. |  [optional]
**name** | **String** | This property is no longer available and will be removed from the documentation soon. See the [deprecation notice](https://developer.atlassian.com/cloud/jira/platform/deprecation-notice-user-privacy-api-migration-guide/) for details. |  [optional]
**type** | [**TypeEnum**](#TypeEnum) | The type of role actor. |  [optional]

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
GROUP_ROLE_ACTOR | &quot;atlassian-group-role-actor&quot;
USER_ROLE_ACTOR | &quot;atlassian-user-role-actor&quot;
