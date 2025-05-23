# CustomFieldPayload

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**cfType** | **String** | The type of the custom field |  [optional]
**description** | **String** | The description of the custom field |  [optional]
**name** | **String** | The name of the custom field |  [optional]
**onConflict** | [**OnConflictEnum**](#OnConflictEnum) | The strategy to use when there is a conflict with an existing custom field. FAIL - Fail execution, this always needs to be unique; USE - Use the existing entity and ignore new entity parameters |  [optional]
**pcri** | [**ProjectCreateResourceIdentifier**](ProjectCreateResourceIdentifier.md) |  |  [optional]
**searcherKey** | **String** | The searcher key of the custom field |  [optional]

<a name="OnConflictEnum"></a>
## Enum: OnConflictEnum
Name | Value
---- | -----
FAIL | &quot;FAIL&quot;
USE | &quot;USE&quot;
NEW | &quot;NEW&quot;
