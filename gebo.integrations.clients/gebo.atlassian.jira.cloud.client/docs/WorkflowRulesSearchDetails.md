# WorkflowRulesSearchDetails

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**invalidRules** | [**List&lt;UUID&gt;**](UUID.md) | List of workflow rule IDs that do not belong to the workflow or can not be found. |  [optional]
**validRules** | [**List&lt;WorkflowTransitionRules&gt;**](WorkflowTransitionRules.md) | List of valid workflow transition rules. |  [optional]
**workflowEntityId** | [**UUID**](UUID.md) | The workflow ID. |  [optional]
