# OrderOfCustomFieldOptions

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**after** | **String** | The ID of the custom field option or cascading option to place the moved options after. Required if &#x60;position&#x60; isn&#x27;t provided. |  [optional]
**customFieldOptionIds** | **List&lt;String&gt;** | A list of IDs of custom field options to move. The order of the custom field option IDs in the list is the order they are given after the move. The list must contain custom field options or cascading options, but not both. | 
**position** | [**PositionEnum**](#PositionEnum) | The position the custom field options should be moved to. Required if &#x60;after&#x60; isn&#x27;t provided. |  [optional]

<a name="PositionEnum"></a>
## Enum: PositionEnum
Name | Value
---- | -----
FIRST | &quot;First&quot;
LAST | &quot;Last&quot;
