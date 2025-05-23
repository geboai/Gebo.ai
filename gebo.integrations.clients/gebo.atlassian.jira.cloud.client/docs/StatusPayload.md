# StatusPayload

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**description** | **String** | The description of the status |  [optional]
**name** | **String** | The name of the status |  [optional]
**onConflict** | [**OnConflictEnum**](#OnConflictEnum) | The conflict strategy for the status already exists. FAIL - Fail execution, this always needs to be unique; USE - Use the existing entity and ignore new entity parameters; NEW - Create a new entity |  [optional]
**pcri** | [**ProjectCreateResourceIdentifier**](ProjectCreateResourceIdentifier.md) |  |  [optional]
**statusCategory** | [**StatusCategoryEnum**](#StatusCategoryEnum) | The status category of the status. The value is case-sensitive. |  [optional]

<a name="OnConflictEnum"></a>
## Enum: OnConflictEnum
Name | Value
---- | -----
FAIL | &quot;FAIL&quot;
USE | &quot;USE&quot;
NEW | &quot;NEW&quot;

<a name="StatusCategoryEnum"></a>
## Enum: StatusCategoryEnum
Name | Value
---- | -----
TODO | &quot;TODO&quot;
IN_PROGRESS | &quot;IN_PROGRESS&quot;
DONE | &quot;DONE&quot;
