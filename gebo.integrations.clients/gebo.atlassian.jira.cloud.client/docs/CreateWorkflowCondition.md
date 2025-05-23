# CreateWorkflowCondition

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**conditions** | [**List&lt;CreateWorkflowCondition&gt;**](CreateWorkflowCondition.md) | The list of workflow conditions. |  [optional]
**_configuration** | **Map&lt;String, Object&gt;** | EXPERIMENTAL. The configuration of the transition rule. |  [optional]
**operator** | [**OperatorEnum**](#OperatorEnum) | The compound condition operator. |  [optional]
**type** | **String** | The type of the transition rule. |  [optional]

<a name="OperatorEnum"></a>
## Enum: OperatorEnum
Name | Value
---- | -----
AND | &quot;AND&quot;
OR | &quot;OR&quot;
