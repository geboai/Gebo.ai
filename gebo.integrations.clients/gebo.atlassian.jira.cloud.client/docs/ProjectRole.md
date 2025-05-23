# ProjectRole

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**actors** | [**List&lt;RoleActor&gt;**](RoleActor.md) | The list of users who act in this role. |  [optional]
**admin** | **Boolean** | Whether this role is the admin role for the project. |  [optional]
**currentUserRole** | **Boolean** | Whether the calling user is part of this role. |  [optional]
**_default** | **Boolean** | Whether this role is the default role for the project |  [optional]
**description** | **String** | The description of the project role. |  [optional]
**id** | **Long** | The ID of the project role. |  [optional]
**name** | **String** | The name of the project role. |  [optional]
**roleConfigurable** | **Boolean** | Whether the roles are configurable for this project. |  [optional]
**scope** | **AllOfProjectRoleScope** | The scope of the role. Indicated for roles associated with [next-gen projects](https://confluence.atlassian.com/x/loMyO). |  [optional]
**self** | **String** | The URL the project role details. |  [optional]
**translatedName** | **String** | The translated name of the project role. |  [optional]
