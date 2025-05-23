# WorkflowTransitions

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**actions** | [**List&lt;WorkflowRuleConfiguration&gt;**](WorkflowRuleConfiguration.md) | The post-functions of the transition. |  [optional]
**conditions** | [**ConditionGroupConfiguration**](ConditionGroupConfiguration.md) |  |  [optional]
**customIssueEventId** | **String** | The custom event ID of the transition. |  [optional]
**description** | **String** | The description of the transition. |  [optional]
**id** | **String** | The ID of the transition. |  [optional]
**links** | [**List&lt;WorkflowTransitionLinks&gt;**](WorkflowTransitionLinks.md) | The statuses the transition can start from, and the mapping of ports between the statuses. |  [optional]
**name** | **String** | The name of the transition. |  [optional]
**properties** | **Map&lt;String, String&gt;** | The properties of the transition. |  [optional]
**toStatusReference** | **String** | The status the transition goes to. |  [optional]
**transitionScreen** | [**WorkflowRuleConfiguration**](WorkflowRuleConfiguration.md) |  |  [optional]
**triggers** | [**List&lt;WorkflowTrigger&gt;**](WorkflowTrigger.md) | The triggers of the transition. |  [optional]
**type** | [**TypeEnum**](#TypeEnum) | The transition type. |  [optional]
**validators** | [**List&lt;WorkflowRuleConfiguration&gt;**](WorkflowRuleConfiguration.md) | The validators of the transition. |  [optional]

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
INITIAL | &quot;INITIAL&quot;
GLOBAL | &quot;GLOBAL&quot;
DIRECTED | &quot;DIRECTED&quot;
