# MoveFieldBean

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**after** | **String** | The ID of the screen tab field after which to place the moved screen tab field. Required if &#x60;position&#x60; isn&#x27;t provided. |  [optional]
**position** | [**PositionEnum**](#PositionEnum) | The named position to which the screen tab field should be moved. Required if &#x60;after&#x60; isn&#x27;t provided. |  [optional]

<a name="PositionEnum"></a>
## Enum: PositionEnum
Name | Value
---- | -----
EARLIER | &quot;Earlier&quot;
LATER | &quot;Later&quot;
FIRST | &quot;First&quot;
LAST | &quot;Last&quot;
