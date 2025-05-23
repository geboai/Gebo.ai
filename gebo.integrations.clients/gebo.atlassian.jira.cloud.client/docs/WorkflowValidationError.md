# WorkflowValidationError

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** | An error code. |  [optional]
**elementReference** | [**WorkflowElementReference**](WorkflowElementReference.md) |  |  [optional]
**level** | [**LevelEnum**](#LevelEnum) | The validation error level. |  [optional]
**message** | **String** | An error message. |  [optional]
**type** | [**TypeEnum**](#TypeEnum) | The type of element the error or warning references. |  [optional]

<a name="LevelEnum"></a>
## Enum: LevelEnum
Name | Value
---- | -----
WARNING | &quot;WARNING&quot;
ERROR | &quot;ERROR&quot;

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
RULE | &quot;RULE&quot;
STATUS | &quot;STATUS&quot;
STATUS_LAYOUT | &quot;STATUS_LAYOUT&quot;
STATUS_PROPERTY | &quot;STATUS_PROPERTY&quot;
WORKFLOW | &quot;WORKFLOW&quot;
TRANSITION | &quot;TRANSITION&quot;
TRANSITION_PROPERTY | &quot;TRANSITION_PROPERTY&quot;
SCOPE | &quot;SCOPE&quot;
STATUS_MAPPING | &quot;STATUS_MAPPING&quot;
TRIGGER | &quot;TRIGGER&quot;
