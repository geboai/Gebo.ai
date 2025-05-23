# MandatoryFieldValueForADF

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**retain** | **Boolean** | If &#x60;true&#x60;, will try to retain original non-null issue field values on move. |  [optional]
**type** | [**TypeEnum**](#TypeEnum) | Will treat as &#x60;MandatoryFieldValueForADF&#x60; if type is &#x60;adf&#x60; | 
**value** | **Object** | Value for each field. Accepts Atlassian Document Format (ADF) for rich text fields like &#x60;description&#x60;, &#x60;environments&#x60;. For ADF format details, refer to: [Atlassian Document Format](https://developer.atlassian.com/cloud/jira/platform/apis/document/structure) | 

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
ADF | &quot;adf&quot;
RAW | &quot;raw&quot;
