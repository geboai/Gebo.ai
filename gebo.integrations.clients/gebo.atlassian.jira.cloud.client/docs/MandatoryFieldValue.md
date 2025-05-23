# MandatoryFieldValue

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**retain** | **Boolean** | If &#x60;true&#x60;, will try to retain original non-null issue field values on move. |  [optional]
**type** | [**TypeEnum**](#TypeEnum) | Will treat as &#x60;MandatoryFieldValue&#x60; if type is &#x60;raw&#x60; or &#x60;empty&#x60; |  [optional]
**value** | **List&lt;String&gt;** | Value for each field. Provide a &#x60;list of strings&#x60; for non-ADF fields. | 

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
ADF | &quot;adf&quot;
RAW | &quot;raw&quot;
