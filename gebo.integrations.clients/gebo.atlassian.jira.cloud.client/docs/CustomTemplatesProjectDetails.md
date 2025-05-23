# CustomTemplatesProjectDetails

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**accessLevel** | [**AccessLevelEnum**](#AccessLevelEnum) | The access level of the project. Only used by team-managed project |  [optional]
**additionalProperties** | **Map&lt;String, String&gt;** | Additional properties of the project |  [optional]
**assigneeType** | [**AssigneeTypeEnum**](#AssigneeTypeEnum) | The default assignee when creating issues in the project |  [optional]
**avatarId** | **Long** | The ID of the project&#x27;s avatar. Use the \\[Get project avatars\\](\\#api-rest-api-3-project-projectIdOrKey-avatar-get) operation to list the available avatars in a project. |  [optional]
**categoryId** | **Long** | The ID of the project&#x27;s category. A complete list of category IDs is found using the [Get all project categories](#api-rest-api-3-projectCategory-get) operation. |  [optional]
**description** | **String** | Brief description of the project |  [optional]
**enableComponents** | **Boolean** | Whether components are enabled for the project. Only used by company-managed project |  [optional]
**key** | **String** | Project keys must be unique and start with an uppercase letter followed by one or more uppercase alphanumeric characters. The maximum length is 10 characters. |  [optional]
**language** | **String** | The default language for the project |  [optional]
**leadAccountId** | **String** | The account ID of the project lead. Either &#x60;lead&#x60; or &#x60;leadAccountId&#x60; must be set when creating a project. Cannot be provided with &#x60;lead&#x60;. |  [optional]
**name** | **String** | Name of the project |  [optional]
**url** | **String** | A link to information about this project, such as project documentation |  [optional]

<a name="AccessLevelEnum"></a>
## Enum: AccessLevelEnum
Name | Value
---- | -----
OPEN | &quot;open&quot;
LIMITED | &quot;limited&quot;
PRIVATE | &quot;private&quot;
FREE | &quot;free&quot;

<a name="AssigneeTypeEnum"></a>
## Enum: AssigneeTypeEnum
Name | Value
---- | -----
PROJECT_DEFAULT | &quot;PROJECT_DEFAULT&quot;
COMPONENT_LEAD | &quot;COMPONENT_LEAD&quot;
PROJECT_LEAD | &quot;PROJECT_LEAD&quot;
UNASSIGNED | &quot;UNASSIGNED&quot;
