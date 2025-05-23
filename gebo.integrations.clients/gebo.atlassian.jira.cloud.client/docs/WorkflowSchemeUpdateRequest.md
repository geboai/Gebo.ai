# WorkflowSchemeUpdateRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**defaultWorkflowId** | **String** | The ID of the workflow for issue types without having a mapping defined in this workflow scheme. Only used in global-scoped workflow schemes. If the &#x60;defaultWorkflowId&#x60; isn&#x27;t specified, this is set to *Jira Workflow (jira)*. |  [optional]
**description** | **String** | The new description for this workflow scheme. | 
**id** | **String** | The ID of this workflow scheme. | 
**name** | **String** | The new name for this workflow scheme. | 
**statusMappingsByIssueTypeOverride** | [**List&lt;MappingsByIssueTypeOverride&gt;**](MappingsByIssueTypeOverride.md) | Overrides, for the selected issue types, any status mappings provided in &#x60;statusMappingsByWorkflows&#x60;. Status mappings are required when the new workflow for an issue type doesn&#x27;t contain all statuses that the old workflow has. Status mappings can be provided by a combination of &#x60;statusMappingsByWorkflows&#x60; and &#x60;statusMappingsByIssueTypeOverride&#x60;. |  [optional]
**statusMappingsByWorkflows** | [**List&lt;MappingsByWorkflow&gt;**](MappingsByWorkflow.md) | The status mappings by workflows. Status mappings are required when the new workflow for an issue type doesn&#x27;t contain all statuses that the old workflow has. Status mappings can be provided by a combination of &#x60;statusMappingsByWorkflows&#x60; and &#x60;statusMappingsByIssueTypeOverride&#x60;. |  [optional]
**version** | [**DocumentVersion**](DocumentVersion.md) |  | 
**workflowsForIssueTypes** | [**List&lt;WorkflowSchemeAssociation&gt;**](WorkflowSchemeAssociation.md) | Mappings from workflows to issue types. |  [optional]
