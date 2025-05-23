# JiraWorkflow

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**created** | **String** | The creation date of the workflow. |  [optional]
**description** | **String** | The description of the workflow. |  [optional]
**id** | **String** | The ID of the workflow. |  [optional]
**isEditable** | **Boolean** | Indicates if the workflow can be edited. |  [optional]
**loopedTransitionContainerLayout** | [**WorkflowLayout**](WorkflowLayout.md) |  |  [optional]
**name** | **String** | The name of the workflow. |  [optional]
**scope** | [**WorkflowScope**](WorkflowScope.md) |  |  [optional]
**startPointLayout** | [**WorkflowLayout**](WorkflowLayout.md) |  |  [optional]
**statuses** | [**List&lt;WorkflowReferenceStatus&gt;**](WorkflowReferenceStatus.md) | The statuses referenced in this workflow. |  [optional]
**taskId** | **String** | If there is a current [asynchronous task](#async-operations) operation for this workflow. |  [optional]
**transitions** | [**List&lt;WorkflowTransitions&gt;**](WorkflowTransitions.md) | The transitions of the workflow. Note that a transition can have either the deprecated &#x60;to&#x60;/&#x60;from&#x60; fields or the &#x60;toStatusReference&#x60;/&#x60;links&#x60; fields, but never both nor a combination. |  [optional]
**updated** | **String** | The last edited date of the workflow. |  [optional]
**usages** | [**List&lt;ProjectIssueTypes&gt;**](ProjectIssueTypes.md) | Deprecated. See the [deprecation notice](https://developer.atlassian.com/cloud/jira/platform/changelog/#CHANGE-2298) for details.  Use the optional &#x60;workflows.usages&#x60; expand to get additional information about the projects and issue types associated with the requested workflows. |  [optional]
**version** | [**DocumentVersion**](DocumentVersion.md) |  |  [optional]
