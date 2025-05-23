# JiraStatus

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**description** | **String** | The description of the status. |  [optional]
**id** | **String** | The ID of the status. |  [optional]
**name** | **String** | The name of the status. |  [optional]
**scope** | [**StatusScope**](StatusScope.md) |  |  [optional]
**statusCategory** | [**StatusCategoryEnum**](#StatusCategoryEnum) | The category of the status. |  [optional]
**usages** | [**List&lt;ProjectIssueTypes&gt;**](ProjectIssueTypes.md) | Deprecated. See the [deprecation notice](https://developer.atlassian.com/cloud/jira/platform/changelog/#CHANGE-2298) for details.  Projects and issue types where the status is used. Only available if the &#x60;usages&#x60; expand is requested. |  [optional]
**workflowUsages** | [**List&lt;WorkflowUsages&gt;**](WorkflowUsages.md) | Deprecated. See the [deprecation notice](https://developer.atlassian.com/cloud/jira/platform/changelog/#CHANGE-2298) for details.  The workflows that use this status. Only available if the &#x60;workflowUsages&#x60; expand is requested. |  [optional]

<a name="StatusCategoryEnum"></a>
## Enum: StatusCategoryEnum
Name | Value
---- | -----
TODO | &quot;TODO&quot;
IN_PROGRESS | &quot;IN_PROGRESS&quot;
DONE | &quot;DONE&quot;
