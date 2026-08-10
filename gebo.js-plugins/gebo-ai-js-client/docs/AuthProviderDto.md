# GeboAiClient.AuthProviderDto

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**provider** | **String** |  | 
**type** | **String** |  | 
**description** | **String** |  | 
**multitenant** | **Boolean** |  | 
**customAttributes** | [**[Oauth2CustomAttribute]**](Oauth2CustomAttribute.md) |  | 

<a name="ProviderEnum"></a>
## Enum: ProviderEnum

* `local` (value: `"local"`)
* `google` (value: `"google"`)
* `microsoft` (value: `"microsoft"`)
* `microsoftMultitenant` (value: `"microsoft_multitenant"`)
* `awsCognito` (value: `"aws_cognito"`)
* `oauth2Generic` (value: `"oauth2_generic"`)
* `ldap` (value: `"ldap"`)


<a name="TypeEnum"></a>
## Enum: TypeEnum

* `LOCAL_JWT` (value: `"LOCAL_JWT"`)
* `oAUTH2` (value: `"OAUTH2"`)
* `LDAP` (value: `"LDAP"`)

