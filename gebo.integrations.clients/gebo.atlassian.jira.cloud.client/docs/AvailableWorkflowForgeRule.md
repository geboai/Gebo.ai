# AvailableWorkflowForgeRule

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**description** | **String** | The rule description. |  [optional]
**id** | **String** | The unique ARI of the forge rule type. |  [optional]
**name** | **String** | The rule name. |  [optional]
**ruleKey** | **String** | The rule key. |  [optional]
**ruleType** | [**RuleTypeEnum**](#RuleTypeEnum) | The rule type. |  [optional]

<a name="RuleTypeEnum"></a>
## Enum: RuleTypeEnum
Name | Value
---- | -----
CONDITION | &quot;Condition&quot;
VALIDATOR | &quot;Validator&quot;
FUNCTION | &quot;Function&quot;
SCREEN | &quot;Screen&quot;
