# RolePayload

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**defaultActors** | [**List&lt;ProjectCreateResourceIdentifier&gt;**](ProjectCreateResourceIdentifier.md) | The default actors for the role. By adding default actors, the role will be added to any future projects created |  [optional]
**description** | **String** | The description of the role |  [optional]
**name** | **String** | The name of the role |  [optional]
**onConflict** | [**OnConflictEnum**](#OnConflictEnum) | The strategy to use when there is a conflict with an existing project role. FAIL - Fail execution, this always needs to be unique; USE - Use the existing entity and ignore new entity parameters |  [optional]
**pcri** | [**ProjectCreateResourceIdentifier**](ProjectCreateResourceIdentifier.md) |  |  [optional]
**type** | [**TypeEnum**](#TypeEnum) | The type of the role. Only used by project-scoped project |  [optional]

<a name="OnConflictEnum"></a>
## Enum: OnConflictEnum
Name | Value
---- | -----
FAIL | &quot;FAIL&quot;
USE | &quot;USE&quot;
NEW | &quot;NEW&quot;

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
HIDDEN | &quot;HIDDEN&quot;
VIEWABLE | &quot;VIEWABLE&quot;
EDITABLE | &quot;EDITABLE&quot;
