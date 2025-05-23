# CreatePermissionHolderRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**type** | [**TypeEnum**](#TypeEnum) | The permission holder type. This must be \&quot;Group\&quot; or \&quot;AccountId\&quot;. | 
**value** | **String** | The permission holder value. This must be a group name if the type is \&quot;Group\&quot; or an account ID if the type is \&quot;AccountId\&quot;. | 

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
GROUP | &quot;Group&quot;
ACCOUNTID | &quot;AccountId&quot;
