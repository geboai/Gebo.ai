# IssueBulkEditField

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**description** | **String** | Description of the field. |  [optional]
**fieldOptions** | [**List&lt;IssueBulkOperationsFieldOption&gt;**](IssueBulkOperationsFieldOption.md) | A list of options related to the field, applicable in contexts where multiple selections are allowed. |  [optional]
**id** | **String** | The unique ID of the field. |  [optional]
**isRequired** | **Boolean** | Indicates whether the field is mandatory for the operation. |  [optional]
**multiSelectFieldOptions** | [**List&lt;MultiSelectFieldOptionsEnum&gt;**](#List&lt;MultiSelectFieldOptionsEnum&gt;) | Specifies supported actions (like add, replace, remove) on multi-select fields via an enum. |  [optional]
**name** | **String** | The display name of the field. |  [optional]
**searchUrl** | **String** | A URL to fetch additional data for the field |  [optional]
**type** | **String** | The type of the field. |  [optional]
**unavailableMessage** | **String** | A message indicating why the field is unavailable for editing. |  [optional]

<a name="List<MultiSelectFieldOptionsEnum>"></a>
## Enum: List&lt;MultiSelectFieldOptionsEnum&gt;
Name | Value
---- | -----
ADD | &quot;ADD&quot;
REMOVE | &quot;REMOVE&quot;
REPLACE | &quot;REPLACE&quot;
REMOVE_ALL | &quot;REMOVE_ALL&quot;
