# FunctionReferenceData

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**displayName** | **String** | The display name of the function. |  [optional]
**isList** | [**IsListEnum**](#IsListEnum) | Whether the function can take a list of arguments. |  [optional]
**supportsListAndSingleValueOperators** | [**SupportsListAndSingleValueOperatorsEnum**](#SupportsListAndSingleValueOperatorsEnum) | Whether the function supports both single and list value operators. |  [optional]
**types** | **List&lt;String&gt;** | The data types returned by the function. |  [optional]
**value** | **String** | The function identifier. |  [optional]

<a name="IsListEnum"></a>
## Enum: IsListEnum
Name | Value
---- | -----
TRUE | &quot;true&quot;
FALSE | &quot;false&quot;

<a name="SupportsListAndSingleValueOperatorsEnum"></a>
## Enum: SupportsListAndSingleValueOperatorsEnum
Name | Value
---- | -----
TRUE | &quot;true&quot;
FALSE | &quot;false&quot;
