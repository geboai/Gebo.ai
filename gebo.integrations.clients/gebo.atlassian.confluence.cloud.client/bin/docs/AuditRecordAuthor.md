# AuditRecordAuthor

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**type** | [**TypeEnum**](#TypeEnum) |  | 
**displayName** | **String** |  | 
**operations** | **Object** |  | 
**username** | **String** |  |  [optional]
**userKey** | **String** |  |  [optional]
**accountId** | **String** |  |  [optional]
**accountType** | **String** |  |  [optional]
**externalCollaborator** | **Boolean** | This is deprecated. Use &#x60;isGuest&#x60; instead. |  [optional]
**isExternalCollaborator** | **Boolean** | This is deprecated. Use &#x60;isGuest&#x60; instead. Whether the user is an external collaborator user |  [optional]
**isGuest** | **Boolean** | Whether the user is a guest user |  [optional]
**publicName** | **String** | The public name or nickname of the user. Will always contain a value. |  [optional]

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
USER | &quot;user&quot;
