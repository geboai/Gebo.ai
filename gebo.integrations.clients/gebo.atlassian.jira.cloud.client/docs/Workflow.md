# Workflow

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**created** | [**Date**](Date.md) | The creation date of the workflow. |  [optional]
**description** | **String** | The description of the workflow. | 
**hasDraftWorkflow** | **Boolean** | Whether the workflow has a draft version. |  [optional]
**id** | [**PublishedWorkflowId**](PublishedWorkflowId.md) |  | 
**isDefault** | **Boolean** | Whether this is the default workflow. |  [optional]
**operations** | [**WorkflowOperations**](WorkflowOperations.md) |  |  [optional]
**projects** | [**List&lt;ProjectDetails&gt;**](ProjectDetails.md) | The projects the workflow is assigned to, through workflow schemes. |  [optional]
**schemes** | [**List&lt;WorkflowSchemeIdName&gt;**](WorkflowSchemeIdName.md) | The workflow schemes the workflow is assigned to. |  [optional]
**statuses** | [**List&lt;WorkflowStatus&gt;**](WorkflowStatus.md) | The statuses of the workflow. |  [optional]
**transitions** | [**List&lt;Transition&gt;**](Transition.md) | The transitions of the workflow. |  [optional]
**updated** | [**Date**](Date.md) | The last edited date of the workflow. |  [optional]
