# ConnectCustomFieldValue

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**_type** | [**TypeEnum**](#TypeEnum) | The type of custom field. | 
**fieldID** | **Integer** | The custom field ID. | 
**issueID** | **Integer** | The issue ID. | 
**number** | [**BigDecimal**](BigDecimal.md) | The value of number type custom field when &#x60;_type&#x60; is &#x60;NumberIssueField&#x60;. |  [optional]
**optionID** | **String** | The value of single select and multiselect custom field type when &#x60;_type&#x60; is &#x60;SingleSelectIssueField&#x60; or &#x60;MultiSelectIssueField&#x60;. |  [optional]
**richText** | **String** | The value of richText type custom field when &#x60;_type&#x60; is &#x60;RichTextIssueField&#x60;. |  [optional]
**string** | **String** | The value of string type custom field when &#x60;_type&#x60; is &#x60;StringIssueField&#x60;. |  [optional]
**text** | **String** | The value of of text custom field type when &#x60;_type&#x60; is &#x60;TextIssueField&#x60;. |  [optional]

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
STRINGISSUEFIELD | &quot;StringIssueField&quot;
NUMBERISSUEFIELD | &quot;NumberIssueField&quot;
RICHTEXTISSUEFIELD | &quot;RichTextIssueField&quot;
SINGLESELECTISSUEFIELD | &quot;SingleSelectIssueField&quot;
MULTISELECTISSUEFIELD | &quot;MultiSelectIssueField&quot;
TEXTISSUEFIELD | &quot;TextIssueField&quot;
