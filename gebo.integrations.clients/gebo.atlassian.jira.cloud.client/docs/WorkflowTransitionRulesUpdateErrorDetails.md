# WorkflowTransitionRulesUpdateErrorDetails

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**ruleUpdateErrors** | [**Map&lt;String, List&lt;String&gt;&gt;**](List.md) | A list of transition rule update errors, indexed by the transition rule ID. Any transition rule that appears here wasn&#x27;t updated. | 
**updateErrors** | **List&lt;String&gt;** | The list of errors that specify why the workflow update failed. The workflow was not updated if the list contains any entries. | 
**workflowId** | [**WorkflowId**](WorkflowId.md) |  | 
