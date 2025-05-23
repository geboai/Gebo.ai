# PermissionPayloadDTO

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**addAddonRole** | **Boolean** | Configuration to generate addon role. Default is false if null |  [optional]
**description** | **String** | The description of the permission scheme |  [optional]
**grants** | [**List&lt;PermissionGrantDTO&gt;**](PermissionGrantDTO.md) | List of permission grants |  [optional]
**name** | **String** | The name of the permission scheme |  [optional]
**onConflict** | [**OnConflictEnum**](#OnConflictEnum) | The strategy to use when there is a conflict with an existing permission scheme. FAIL - Fail execution, this always needs to be unique; USE - Use the existing entity and ignore new entity parameters; NEW - If the entity exist, try and create a new one with a different name |  [optional]
**pcri** | [**ProjectCreateResourceIdentifier**](ProjectCreateResourceIdentifier.md) |  |  [optional]

<a name="OnConflictEnum"></a>
## Enum: OnConflictEnum
Name | Value
---- | -----
FAIL | &quot;FAIL&quot;
USE | &quot;USE&quot;
NEW | &quot;NEW&quot;
