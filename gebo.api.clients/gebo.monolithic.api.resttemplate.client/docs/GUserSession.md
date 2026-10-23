# GUserSession

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  |  [optional]
**description** | **String** |  |  [optional]
**userModified** | **String** |  |  [optional]
**userCreated** | **String** |  |  [optional]
**dateModified** | [**Date**](Date.md) |  |  [optional]
**dateCreated** | [**Date**](Date.md) |  |  [optional]
**username** | **String** |  |  [optional]
**kind** | [**KindEnum**](#KindEnum) |  |  [optional]
**authType** | **String** |  |  [optional]
**authProvider** | **String** |  |  [optional]
**firstSeen** | [**Date**](Date.md) |  |  [optional]
**lastSeen** | [**Date**](Date.md) |  |  [optional]
**lastTokenId** | **String** |  |  [optional]
**lastClientIp** | **String** |  |  [optional]
**lastUserAgent** | **String** |  |  [optional]
**expiresAt** | [**Date**](Date.md) |  |  [optional]
**revoked** | **Boolean** |  |  [optional]
**revokedBy** | **String** |  |  [optional]
**revokedAt** | [**Date**](Date.md) |  |  [optional]

<a name="KindEnum"></a>
## Enum: KindEnum
Name | Value
---- | -----
SESSION | &quot;SESSION&quot;
API_KEY | &quot;API_KEY&quot;
OAUTH2 | &quot;OAUTH2&quot;
