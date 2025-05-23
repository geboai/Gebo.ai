# SharePermissionInputBean

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**accountId** | **String** | The user account ID that the filter is shared with. For a request, specify the &#x60;accountId&#x60; property for the user. |  [optional]
**groupId** | **String** | The ID of the group, which uniquely identifies the group across all Atlassian products.For example, *952d12c3-5b5b-4d04-bb32-44d383afc4b2*. Cannot be provided with &#x60;groupname&#x60;. |  [optional]
**groupname** | **String** | The name of the group to share the filter with. Set &#x60;type&#x60; to &#x60;group&#x60;. Please note that the name of a group is mutable, to reliably identify a group use &#x60;groupId&#x60;. |  [optional]
**projectId** | **String** | The ID of the project to share the filter with. Set &#x60;type&#x60; to &#x60;project&#x60;. |  [optional]
**projectRoleId** | **String** | The ID of the project role to share the filter with. Set &#x60;type&#x60; to &#x60;projectRole&#x60; and the &#x60;projectId&#x60; for the project that the role is in. |  [optional]
**rights** | **Integer** | The rights for the share permission. |  [optional]
**type** | [**TypeEnum**](#TypeEnum) | The type of the share permission.Specify the type as follows:   *  &#x60;user&#x60; Share with a user.  *  &#x60;group&#x60; Share with a group. Specify &#x60;groupname&#x60; as well.  *  &#x60;project&#x60; Share with a project. Specify &#x60;projectId&#x60; as well.  *  &#x60;projectRole&#x60; Share with a project role in a project. Specify &#x60;projectId&#x60; and &#x60;projectRoleId&#x60; as well.  *  &#x60;global&#x60; Share globally, including anonymous users. If set, this type overrides all existing share permissions and must be deleted before any non-global share permissions is set.  *  &#x60;authenticated&#x60; Share with all logged-in users. This shows as &#x60;loggedin&#x60; in the response. If set, this type overrides all existing share permissions and must be deleted before any non-global share permissions is set. | 

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
USER | &quot;user&quot;
PROJECT | &quot;project&quot;
GROUP | &quot;group&quot;
PROJECTROLE | &quot;projectRole&quot;
GLOBAL | &quot;global&quot;
AUTHENTICATED | &quot;authenticated&quot;
