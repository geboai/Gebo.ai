# User

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**type** | [**TypeEnum**](#TypeEnum) |  | 
**username** | **String** |  |  [optional]
**userKey** | **String** |  |  [optional]
**accountId** | **String** |  |  [optional]
**accountType** | [**AccountTypeEnum**](#AccountTypeEnum) | The account type of the user, may return empty string if unavailable. App is if the user is a bot user created on behalf of an Atlassian app. |  [optional]
**email** | **String** | The email address of the user. Depending on the user&#x27;s privacy setting, this may return an empty string. |  [optional]
**publicName** | **String** | The public name or nickname of the user. Will always contain a value. |  [optional]
**profilePicture** | [**Icon**](Icon.md) |  |  [optional]
**displayName** | **String** | The displays name of the user. Depending on the user&#x27;s privacy setting, this may be the same as publicName. |  [optional]
**timeZone** | **String** | This displays user time zone. Depending on the user&#x27;s privacy setting, this may return null. |  [optional]
**externalCollaborator** | **Boolean** | This is deprecated. Use &#x60;isGuest&#x60; instead to find out whether the user is a guest user. |  [optional]
**isExternalCollaborator** | **Boolean** | This is deprecated. Use &#x60;isGuest&#x60; instead to find out whether the user is a guest user. |  [optional]
**isGuest** | **Boolean** | Whether the user is a guest user |  [optional]
**operations** | [**List&lt;OperationCheckResult&gt;**](OperationCheckResult.md) |  |  [optional]
**details** | [**UserDetails**](UserDetails.md) |  |  [optional]
**personalSpace** | [**Space**](Space.md) |  |  [optional]
**_expandable** | [**BulkUserLookupExpandable**](BulkUserLookupExpandable.md) |  |  [optional]
**_links** | [**GenericLinks**](GenericLinks.md) |  |  [optional]

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
KNOWN | &quot;known&quot;
UNKNOWN | &quot;unknown&quot;
ANONYMOUS | &quot;anonymous&quot;
USER | &quot;user&quot;

<a name="AccountTypeEnum"></a>
## Enum: AccountTypeEnum
Name | Value
---- | -----
ATLASSIAN | &quot;atlassian&quot;
APP | &quot;app&quot;
EMPTY | &quot;&quot;
