# IssueTypePayload

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**avatarId** | **Long** | The avatar ID of the issue type. Go to https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-avatars/\\#api-rest-api-3-avatar-type-system-get to choose an avatarId existing in Jira |  [optional]
**description** | **String** | The description of the issue type |  [optional]
**hierarchyLevel** | **Integer** | The hierarchy level of the issue type. 0, 1, 2, 3 .. n; Negative values for subtasks |  [optional]
**name** | **String** | The name of the issue type |  [optional]
**onConflict** | [**OnConflictEnum**](#OnConflictEnum) | The conflict strategy to use when the issue type already exists. FAIL - Fail execution, this always needs to be unique; USE - Use the existing entity and ignore new entity parameters |  [optional]
**pcri** | [**ProjectCreateResourceIdentifier**](ProjectCreateResourceIdentifier.md) |  |  [optional]

<a name="OnConflictEnum"></a>
## Enum: OnConflictEnum
Name | Value
---- | -----
FAIL | &quot;FAIL&quot;
USE | &quot;USE&quot;
NEW | &quot;NEW&quot;
