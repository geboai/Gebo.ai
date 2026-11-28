# DataEndpointAccess

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**grantedBy** | **String** |  |  [optional]
**scope** | **String** |  |  [optional]
**mechanism** | [**MechanismEnum**](#MechanismEnum) |  |  [optional]
**grant** | **String** |  |  [optional]
**accessibleToAll** | **Boolean** |  |  [optional]
**users** | **List&lt;String&gt;** |  |  [optional]
**groups** | **List&lt;String&gt;** |  |  [optional]
**aclAliases** | **List&lt;Integer&gt;** |  |  [optional]
**aclEntries** | [**List&lt;AclEntry&gt;**](AclEntry.md) |  |  [optional]
**administrators** | **Boolean** |  |  [optional]
**note** | **String** |  |  [optional]

<a name="MechanismEnum"></a>
## Enum: MechanismEnum
Name | Value
---- | -----
USERS_GROUPS | &quot;USERS_GROUPS&quot;
CONTENT | &quot;CONTENT&quot;
ACL_ONLY | &quot;ACL_ONLY&quot;
