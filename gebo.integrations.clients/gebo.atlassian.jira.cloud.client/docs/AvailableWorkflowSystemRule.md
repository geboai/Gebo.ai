# AvailableWorkflowSystemRule

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**description** | **String** | The rule description. | 
**incompatibleRuleKeys** | **List&lt;String&gt;** | List of rules that conflict with this one. | 
**isAvailableForInitialTransition** | **Boolean** | Whether the rule can be added added to an initial transition. | 
**isVisible** | **Boolean** | Whether the rule is visible. | 
**name** | **String** | The rule name. | 
**ruleKey** | **String** | The rule key. | 
**ruleType** | [**RuleTypeEnum**](#RuleTypeEnum) | The rule type. | 

<a name="RuleTypeEnum"></a>
## Enum: RuleTypeEnum
Name | Value
---- | -----
CONDITION | &quot;Condition&quot;
VALIDATOR | &quot;Validator&quot;
FUNCTION | &quot;Function&quot;
SCREEN | &quot;Screen&quot;
