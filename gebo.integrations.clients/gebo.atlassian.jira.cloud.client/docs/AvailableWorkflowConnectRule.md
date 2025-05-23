# AvailableWorkflowConnectRule

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**addonKey** | **String** | The add-on providing the rule. |  [optional]
**createUrl** | **String** | The URL creation path segment defined in the Connect module. |  [optional]
**description** | **String** | The rule description. |  [optional]
**editUrl** | **String** | The URL edit path segment defined in the Connect module. |  [optional]
**moduleKey** | **String** | The module providing the rule. |  [optional]
**name** | **String** | The rule name. |  [optional]
**ruleKey** | **String** | The rule key. |  [optional]
**ruleType** | [**RuleTypeEnum**](#RuleTypeEnum) | The rule type. |  [optional]
**viewUrl** | **String** | The URL view path segment defined in the Connect module. |  [optional]

<a name="RuleTypeEnum"></a>
## Enum: RuleTypeEnum
Name | Value
---- | -----
CONDITION | &quot;Condition&quot;
VALIDATOR | &quot;Validator&quot;
FUNCTION | &quot;Function&quot;
SCREEN | &quot;Screen&quot;
