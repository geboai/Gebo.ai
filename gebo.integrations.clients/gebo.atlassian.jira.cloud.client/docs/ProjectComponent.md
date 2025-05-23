# ProjectComponent

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**ari** | **String** | Compass component&#x27;s ID. Can&#x27;t be updated. Not required for creating a Project Component. |  [optional]
**assignee** | **AllOfProjectComponentAssignee** | The details of the user associated with &#x60;assigneeType&#x60;, if any. See &#x60;realAssignee&#x60; for details of the user assigned to issues created with this component. |  [optional]
**assigneeType** | [**AssigneeTypeEnum**](#AssigneeTypeEnum) | The nominal user type used to determine the assignee for issues created with this component. See &#x60;realAssigneeType&#x60; for details on how the type of the user, and hence the user, assigned to issues is determined. Can take the following values:   *  &#x60;PROJECT_LEAD&#x60; the assignee to any issues created with this component is nominally the lead for the project the component is in.  *  &#x60;COMPONENT_LEAD&#x60; the assignee to any issues created with this component is nominally the lead for the component.  *  &#x60;UNASSIGNED&#x60; an assignee is not set for issues created with this component.  *  &#x60;PROJECT_DEFAULT&#x60; the assignee to any issues created with this component is nominally the default assignee for the project that the component is in.  Default value: &#x60;PROJECT_DEFAULT&#x60;.   Optional when creating or updating a component. |  [optional]
**description** | **String** | The description for the component. Optional when creating or updating a component. |  [optional]
**id** | **String** | The unique identifier for the component. |  [optional]
**isAssigneeTypeValid** | **Boolean** | Whether a user is associated with &#x60;assigneeType&#x60;. For example, if the &#x60;assigneeType&#x60; is set to &#x60;COMPONENT_LEAD&#x60; but the component lead is not set, then &#x60;false&#x60; is returned. |  [optional]
**lead** | **AllOfProjectComponentLead** | The user details for the component&#x27;s lead user. |  [optional]
**leadAccountId** | **String** | The accountId of the component&#x27;s lead user. The accountId uniquely identifies the user across all Atlassian products. For example, *5b10ac8d82e05b22cc7d4ef5*. |  [optional]
**leadUserName** | **String** | This property is no longer available and will be removed from the documentation soon. See the [deprecation notice](https://developer.atlassian.com/cloud/jira/platform/deprecation-notice-user-privacy-api-migration-guide/) for details. |  [optional]
**metadata** | **Map&lt;String, String&gt;** | Compass component&#x27;s metadata. Can&#x27;t be updated. Not required for creating a Project Component. |  [optional]
**name** | **String** | The unique name for the component in the project. Required when creating a component. Optional when updating a component. The maximum length is 255 characters. |  [optional]
**project** | **String** | The key of the project the component is assigned to. Required when creating a component. Can&#x27;t be updated. |  [optional]
**projectId** | **Long** | The ID of the project the component is assigned to. |  [optional]
**realAssignee** | **AllOfProjectComponentRealAssignee** | The user assigned to issues created with this component, when &#x60;assigneeType&#x60; does not identify a valid assignee. |  [optional]
**realAssigneeType** | [**RealAssigneeTypeEnum**](#RealAssigneeTypeEnum) | The type of the assignee that is assigned to issues created with this component, when an assignee cannot be set from the &#x60;assigneeType&#x60;. For example, &#x60;assigneeType&#x60; is set to &#x60;COMPONENT_LEAD&#x60; but no component lead is set. This property is set to one of the following values:   *  &#x60;PROJECT_LEAD&#x60; when &#x60;assigneeType&#x60; is &#x60;PROJECT_LEAD&#x60; and the project lead has permission to be assigned issues in the project that the component is in.  *  &#x60;COMPONENT_LEAD&#x60; when &#x60;assignee&#x60;Type is &#x60;COMPONENT_LEAD&#x60; and the component lead has permission to be assigned issues in the project that the component is in.  *  &#x60;UNASSIGNED&#x60; when &#x60;assigneeType&#x60; is &#x60;UNASSIGNED&#x60; and Jira is configured to allow unassigned issues.  *  &#x60;PROJECT_DEFAULT&#x60; when none of the preceding cases are true. |  [optional]
**self** | **String** | The URL of the component. |  [optional]

<a name="AssigneeTypeEnum"></a>
## Enum: AssigneeTypeEnum
Name | Value
---- | -----
PROJECT_DEFAULT | &quot;PROJECT_DEFAULT&quot;
COMPONENT_LEAD | &quot;COMPONENT_LEAD&quot;
PROJECT_LEAD | &quot;PROJECT_LEAD&quot;
UNASSIGNED | &quot;UNASSIGNED&quot;

<a name="RealAssigneeTypeEnum"></a>
## Enum: RealAssigneeTypeEnum
Name | Value
---- | -----
PROJECT_DEFAULT | &quot;PROJECT_DEFAULT&quot;
COMPONENT_LEAD | &quot;COMPONENT_LEAD&quot;
PROJECT_LEAD | &quot;PROJECT_LEAD&quot;
UNASSIGNED | &quot;UNASSIGNED&quot;
