# GeboAiClient.Oauth2ProviderModifiableData

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  | [optional] 
**authProvider** | **String** |  | 
**providerConfiguration** | [**Oauth2ProviderConfig**](Oauth2ProviderConfig.md) |  | [optional] 
**oauth2ClientContent** | [**GeboOauth2SecretContent**](GeboOauth2SecretContent.md) |  | 
**authClientMethod** | **String** |  | [optional] 
**authGrantType** | **String** |  | [optional] 
**configurationType** | **String** |  | 
**description** | **String** |  | 
**readOnly** | **Boolean** |  | 

<a name="AuthProviderEnum"></a>
## Enum: AuthProviderEnum

* `local` (value: `"local"`)
* `google` (value: `"google"`)
* `microsoft` (value: `"microsoft"`)
* `microsoftMultitenant` (value: `"microsoft_multitenant"`)
* `awsCognito` (value: `"aws_cognito"`)
* `oauth2Generic` (value: `"oauth2_generic"`)
* `ldap` (value: `"ldap"`)


<a name="AuthClientMethodEnum"></a>
## Enum: AuthClientMethodEnum

* `CLIENT_SECRET_BASIC` (value: `"CLIENT_SECRET_BASIC"`)
* `CLIENT_SECRET_POST` (value: `"CLIENT_SECRET_POST"`)
* `CLIENT_SECRET_JWT` (value: `"CLIENT_SECRET_JWT"`)
* `PRIVATE_KEY_JWT` (value: `"PRIVATE_KEY_JWT"`)
* `NONE` (value: `"NONE"`)
* `TLS_CLIENT_AUTH` (value: `"TLS_CLIENT_AUTH"`)
* `SELF_SIGNED_TLS_CLIENT_AUTH` (value: `"SELF_SIGNED_TLS_CLIENT_AUTH"`)


<a name="AuthGrantTypeEnum"></a>
## Enum: AuthGrantTypeEnum

* `AUTHORIZATION_CODE` (value: `"AUTHORIZATION_CODE"`)
* `REFRESH_TOKEN` (value: `"REFRESH_TOKEN"`)
* `CLIENT_CREDENTIALS` (value: `"CLIENT_CREDENTIALS"`)
* `PASSWORD` (value: `"PASSWORD"`)
* `JWT_BEARER` (value: `"JWT_BEARER"`)
* `DEVICE_CODE` (value: `"DEVICE_CODE"`)
* `TOKEN_EXCHANGE` (value: `"TOKEN_EXCHANGE"`)


<a name="ConfigurationTypeEnum"></a>
## Enum: ConfigurationTypeEnum

* `AUTHENTICATION` (value: `"AUTHENTICATION"`)
* `INTEGRATION` (value: `"INTEGRATION"`)

