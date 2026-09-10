# Order

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**direction** | [**DirectionEnum**](#DirectionEnum) |  |  [optional]
**property** | **String** |  |  [optional]
**ignoreCase** | **Boolean** |  |  [optional]
**nullHandling** | [**NullHandlingEnum**](#NullHandlingEnum) |  |  [optional]
**ascending** | **Boolean** |  |  [optional]
**descending** | **Boolean** |  |  [optional]

<a name="DirectionEnum"></a>
## Enum: DirectionEnum
Name | Value
---- | -----
ASC | &quot;ASC&quot;
DESC | &quot;DESC&quot;

<a name="NullHandlingEnum"></a>
## Enum: NullHandlingEnum
Name | Value
---- | -----
NATIVE | &quot;NATIVE&quot;
NULLS_FIRST | &quot;NULLS_FIRST&quot;
NULLS_LAST | &quot;NULLS_LAST&quot;
