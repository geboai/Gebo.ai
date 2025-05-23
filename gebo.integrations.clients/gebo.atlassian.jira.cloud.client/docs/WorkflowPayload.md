# WorkflowPayload

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**description** | **String** | The description of the workflow |  [optional]
**loopedTransitionContainerLayout** | [**WorkflowStatusLayoutPayload**](WorkflowStatusLayoutPayload.md) |  |  [optional]
**name** | **String** | The name of the workflow |  [optional]
**onConflict** | [**OnConflictEnum**](#OnConflictEnum) | The strategy to use if there is a conflict with another workflow |  [optional]
**pcri** | [**ProjectCreateResourceIdentifier**](ProjectCreateResourceIdentifier.md) |  |  [optional]
**startPointLayout** | [**WorkflowStatusLayoutPayload**](WorkflowStatusLayoutPayload.md) |  |  [optional]
**statuses** | [**List&lt;WorkflowStatusPayload&gt;**](WorkflowStatusPayload.md) | The statuses to be used in the workflow |  [optional]
**transitions** | [**List&lt;TransitionPayload&gt;**](TransitionPayload.md) | The transitions for the workflow |  [optional]

<a name="OnConflictEnum"></a>
## Enum: OnConflictEnum
Name | Value
---- | -----
FAIL | &quot;FAIL&quot;
USE | &quot;USE&quot;
NEW | &quot;NEW&quot;
