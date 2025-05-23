# JqlQueryFieldEntityProperty

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**entity** | **String** | The object on which the property is set. | 
**key** | **String** | The key of the property. | 
**path** | **String** | The path in the property value to query. | 
**type** | [**TypeEnum**](#TypeEnum) | The type of the property value extraction. Not available if the extraction for the property is not registered on the instance with the [Entity property](https://developer.atlassian.com/cloud/jira/platform/modules/entity-property/) module. |  [optional]

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
NUMBER | &quot;number&quot;
STRING | &quot;string&quot;
TEXT | &quot;text&quot;
DATE | &quot;date&quot;
USER | &quot;user&quot;
