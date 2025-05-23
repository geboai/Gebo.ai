# SecurityLevelMemberPayload

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**parameter** | **String** | Defines the value associated with the type. For reporter this would be \\{\&quot;null\&quot;\\}; for users this would be the names of specific users); for group this would be group names like \\{\&quot;administrators\&quot;, \&quot;jira-administrators\&quot;, \&quot;jira-users\&quot;\\} |  [optional]
**type** | [**TypeEnum**](#TypeEnum) | The type of the security level member |  [optional]

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
GROUP | &quot;group&quot;
REPORTER | &quot;reporter&quot;
USERS | &quot;users&quot;
