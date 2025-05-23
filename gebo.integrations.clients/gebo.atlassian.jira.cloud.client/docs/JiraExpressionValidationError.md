# JiraExpressionValidationError

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**column** | **Integer** | The text column in which the error occurred. |  [optional]
**expression** | **String** | The part of the expression in which the error occurred. |  [optional]
**line** | **Integer** | The text line in which the error occurred. |  [optional]
**message** | **String** | Details about the error. | 
**type** | [**TypeEnum**](#TypeEnum) | The error type. | 

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
SYNTAX | &quot;syntax&quot;
TYPE | &quot;type&quot;
OTHER | &quot;other&quot;
