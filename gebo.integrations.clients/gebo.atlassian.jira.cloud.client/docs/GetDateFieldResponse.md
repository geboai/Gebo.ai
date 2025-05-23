# GetDateFieldResponse

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**dateCustomFieldId** | **Long** | A date custom field ID. This is returned if the type is \&quot;DateCustomField\&quot;. |  [optional]
**type** | [**TypeEnum**](#TypeEnum) | The date field type. This is \&quot;DueDate\&quot;, \&quot;TargetStartDate\&quot;, \&quot;TargetEndDate\&quot; or \&quot;DateCustomField\&quot;. | 

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
DUEDATE | &quot;DueDate&quot;
TARGETSTARTDATE | &quot;TargetStartDate&quot;
TARGETENDDATE | &quot;TargetEndDate&quot;
DATECUSTOMFIELD | &quot;DateCustomField&quot;
