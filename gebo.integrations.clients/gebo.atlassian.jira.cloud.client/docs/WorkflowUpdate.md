# WorkflowUpdate

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**defaultStatusMappings** | [**List&lt;StatusMigration&gt;**](StatusMigration.md) | The mapping of old to new status ID. |  [optional]
**description** | **String** | The new description for this workflow. |  [optional]
**id** | **String** | The ID of this workflow. | 
**loopedTransitionContainerLayout** | [**WorkflowLayout**](WorkflowLayout.md) |  |  [optional]
**startPointLayout** | [**WorkflowLayout**](WorkflowLayout.md) |  |  [optional]
**statusMappings** | [**List&lt;StatusMappingDTO&gt;**](StatusMappingDTO.md) | The mapping of old to new status ID for a specific project and issue type. |  [optional]
**statuses** | [**List&lt;StatusLayoutUpdate&gt;**](StatusLayoutUpdate.md) | The statuses associated with this workflow. | 
**transitions** | [**List&lt;TransitionUpdateDTO&gt;**](TransitionUpdateDTO.md) | The transitions of this workflow. | 
**version** | [**DocumentVersion**](DocumentVersion.md) |  | 
