# CreateWorkflowDetails

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**description** | **String** | The description of the workflow. The maximum length is 1000 characters. |  [optional]
**name** | **String** | The name of the workflow. The name must be unique. The maximum length is 255 characters. Characters can be separated by a whitespace but the name cannot start or end with a whitespace. | 
**statuses** | [**List&lt;CreateWorkflowStatusDetails&gt;**](CreateWorkflowStatusDetails.md) | The statuses of the workflow. Any status that does not include a transition is added to the workflow without a transition. | 
**transitions** | [**List&lt;CreateWorkflowTransitionDetails&gt;**](CreateWorkflowTransitionDetails.md) | The transitions of the workflow. For the request to be valid, these transitions must:   *  include one *initial* transition.  *  not use the same name for a *global* and *directed* transition.  *  have a unique name for each *global* transition.  *  have a unique &#x27;to&#x27; status for each *global* transition.  *  have unique names for each transition from a status.  *  not have a &#x27;from&#x27; status on *initial* and *global* transitions.  *  have a &#x27;from&#x27; status on *directed* transitions.  All the transition statuses must be included in &#x60;statuses&#x60;. | 
