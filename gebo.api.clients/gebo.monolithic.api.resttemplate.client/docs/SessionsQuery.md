# SessionsQuery

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**username** | **String** |  |  [optional]
**kind** | [**KindEnum**](#KindEnum) |  |  [optional]
**activeWithinMinutes** | **Integer** |  |  [optional]
**includeRevoked** | **Boolean** |  |  [optional]
**includeExpired** | **Boolean** |  |  [optional]

<a name="KindEnum"></a>
## Enum: KindEnum
Name | Value
---- | -----
SESSION | &quot;SESSION&quot;
API_KEY | &quot;API_KEY&quot;
OAUTH2 | &quot;OAUTH2&quot;
