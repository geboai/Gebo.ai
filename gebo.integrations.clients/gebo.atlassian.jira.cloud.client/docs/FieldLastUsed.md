# FieldLastUsed

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**type** | [**TypeEnum**](#TypeEnum) | Last used value type:   *  *TRACKED*: field is tracked and a last used date is available.  *  *NOT\\_TRACKED*: field is not tracked, last used date is not available.  *  *NO\\_INFORMATION*: field is tracked, but no last used date is available. |  [optional]
**value** | [**Date**](Date.md) | The date when the value of the field last changed. |  [optional]

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
TRACKED | &quot;TRACKED&quot;
NOT_TRACKED | &quot;NOT_TRACKED&quot;
NO_INFORMATION | &quot;NO_INFORMATION&quot;
