# UserPermission

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**deprecatedKey** | **Boolean** | Indicate whether the permission key is deprecated. Note that deprecated keys cannot be used in the &#x60;permissions parameter of Get my permissions. Deprecated keys are not returned by Get all permissions.&#x60; |  [optional]
**description** | **String** | The description of the permission. |  [optional]
**havePermission** | **Boolean** | Whether the permission is available to the user in the queried context. |  [optional]
**id** | **String** | The ID of the permission. Either &#x60;id&#x60; or &#x60;key&#x60; must be specified. Use [Get all permissions](#api-rest-api-3-permissions-get) to get the list of permissions. |  [optional]
**key** | **String** | The key of the permission. Either &#x60;id&#x60; or &#x60;key&#x60; must be specified. Use [Get all permissions](#api-rest-api-3-permissions-get) to get the list of permissions. |  [optional]
**name** | **String** | The name of the permission. |  [optional]
**type** | [**TypeEnum**](#TypeEnum) | The type of the permission. |  [optional]

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
GLOBAL | &quot;GLOBAL&quot;
PROJECT | &quot;PROJECT&quot;
