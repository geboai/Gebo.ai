# BulkUserLookup

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**type** | [**TypeEnum**](#TypeEnum) |  | 
**username** | **String** |  |  [optional]
**userKey** | **String** |  |  [optional]
**accountId** | **String** |  | 
**accountType** | **String** | The account type of the user, may return empty string if unavailable. | 
**email** | **String** | The email address of the user. Depending on the user&#x27;s privacy setting, this may return an empty string. | 
**publicName** | **String** | The public name or nickname of the user. Will always contain a value. | 
**profilePicture** | [**Icon**](Icon.md) |  | 
**displayName** | **String** | The displays name of the user. Depending on the user&#x27;s privacy setting, this may be the same as publicName. | 
**timeZone** | **String** | This displays user time zone. Depending on the user&#x27;s privacy setting, this may return null. |  [optional]
**isExternalCollaborator** | **Boolean** | This is deprecated. Use &#x60;isGuest&#x60; instead to find out whether the user is a guest user. |  [optional]
**isGuest** | **Boolean** | Whether the user is a guest user |  [optional]
**operations** | [**List&lt;OperationCheckResult&gt;**](OperationCheckResult.md) |  |  [optional]
**details** | [**UserDetails**](UserDetails.md) |  |  [optional]
**personalSpace** | [**Space**](Space.md) |  |  [optional]
**_expandable** | [**BulkUserLookupExpandable**](BulkUserLookupExpandable.md) |  | 
**_links** | [**GenericLinks**](GenericLinks.md) |  | 

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
KNOWN | &quot;known&quot;
UNKNOWN | &quot;unknown&quot;
ANONYMOUS | &quot;anonymous&quot;
USER | &quot;user&quot;
