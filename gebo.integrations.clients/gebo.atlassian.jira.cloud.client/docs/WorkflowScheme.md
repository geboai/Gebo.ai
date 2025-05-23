# WorkflowScheme

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**defaultWorkflow** | **String** | The name of the default workflow for the workflow scheme. The default workflow has *All Unassigned Issue Types* assigned to it in Jira. If &#x60;defaultWorkflow&#x60; is not specified when creating a workflow scheme, it is set to *Jira Workflow (jira)*. |  [optional]
**description** | **String** | The description of the workflow scheme. |  [optional]
**draft** | **Boolean** | Whether the workflow scheme is a draft or not. |  [optional]
**id** | **Long** | The ID of the workflow scheme. |  [optional]
**issueTypeMappings** | **Map&lt;String, String&gt;** | The issue type to workflow mappings, where each mapping is an issue type ID and workflow name pair. Note that an issue type can only be mapped to one workflow in a workflow scheme. |  [optional]
**issueTypes** | [**Map&lt;String, IssueTypeDetails&gt;**](IssueTypeDetails.md) | The issue types available in Jira. |  [optional]
**lastModified** | **String** | The date-time that the draft workflow scheme was last modified. A modification is a change to the issue type-project mappings only. This property does not apply to non-draft workflows. |  [optional]
**lastModifiedUser** | **AllOfWorkflowSchemeLastModifiedUser** | The user that last modified the draft workflow scheme. A modification is a change to the issue type-project mappings only. This property does not apply to non-draft workflows. |  [optional]
**name** | **String** | The name of the workflow scheme. The name must be unique. The maximum length is 255 characters. Required when creating a workflow scheme. |  [optional]
**originalDefaultWorkflow** | **String** | For draft workflow schemes, this property is the name of the default workflow for the original workflow scheme. The default workflow has *All Unassigned Issue Types* assigned to it in Jira. |  [optional]
**originalIssueTypeMappings** | **Map&lt;String, String&gt;** | For draft workflow schemes, this property is the issue type to workflow mappings for the original workflow scheme, where each mapping is an issue type ID and workflow name pair. Note that an issue type can only be mapped to one workflow in a workflow scheme. |  [optional]
**self** | **String** |  |  [optional]
**updateDraftIfNeeded** | **Boolean** | Whether to create or update a draft workflow scheme when updating an active workflow scheme. An active workflow scheme is a workflow scheme that is used by at least one project. The following examples show how this property works:   *  Update an active workflow scheme with &#x60;updateDraftIfNeeded&#x60; set to &#x60;true&#x60;: If a draft workflow scheme exists, it is updated. Otherwise, a draft workflow scheme is created.  *  Update an active workflow scheme with &#x60;updateDraftIfNeeded&#x60; set to &#x60;false&#x60;: An error is returned, as active workflow schemes cannot be updated.  *  Update an inactive workflow scheme with &#x60;updateDraftIfNeeded&#x60; set to &#x60;true&#x60;: The workflow scheme is updated, as inactive workflow schemes do not require drafts to update.  Defaults to &#x60;false&#x60;. |  [optional]
