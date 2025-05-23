# Transition

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**description** | **String** | The description of the transition. | 
**from** | **List&lt;String&gt;** | The statuses the transition can start from. | 
**id** | **String** | The ID of the transition. | 
**name** | **String** | The name of the transition. | 
**properties** | **Map&lt;String, Object&gt;** | The properties of the transition. |  [optional]
**rules** | [**WorkflowRules**](WorkflowRules.md) |  |  [optional]
**screen** | [**TransitionScreenDetails**](TransitionScreenDetails.md) |  |  [optional]
**to** | **String** | The status the transition goes to. | 
**type** | [**TypeEnum**](#TypeEnum) | The type of the transition. | 

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
GLOBAL | &quot;global&quot;
INITIAL | &quot;initial&quot;
DIRECTED | &quot;directed&quot;
