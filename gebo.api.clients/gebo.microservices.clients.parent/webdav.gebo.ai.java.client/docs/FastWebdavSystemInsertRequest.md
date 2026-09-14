# FastWebdavSystemInsertRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**baseUri** | **String** |  | 
**description** | **String** |  | 
**authType** | [**AuthTypeEnum**](#AuthTypeEnum) |  | 
**username** | **String** |  |  [optional]
**password** | **String** |  |  [optional]
**token** | **String** |  |  [optional]

<a name="AuthTypeEnum"></a>
## Enum: AuthTypeEnum
Name | Value
---- | -----
NONE | &quot;NONE&quot;
BASIC | &quot;BASIC&quot;
DIGEST | &quot;DIGEST&quot;
NTLM | &quot;NTLM&quot;
BEARER_TOKEN | &quot;BEARER_TOKEN&quot;
