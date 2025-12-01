# Oauth2ProviderModifiableData

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  |  [optional]
**authProvider** | [**AuthProviderEnum**](#AuthProviderEnum) |  | 
**providerConfiguration** | [**Oauth2ProviderConfig**](Oauth2ProviderConfig.md) |  |  [optional]
**oauth2ClientContent** | [**GeboOauth2SecretContent**](GeboOauth2SecretContent.md) |  | 
**authClientMethod** | [**AuthClientMethodEnum**](#AuthClientMethodEnum) |  |  [optional]
**authGrantType** | [**AuthGrantTypeEnum**](#AuthGrantTypeEnum) |  |  [optional]
**configurationType** | [**ConfigurationTypeEnum**](#ConfigurationTypeEnum) |  | 
**description** | **String** |  | 
**readOnly** | **Boolean** |  | 

<a name="AuthProviderEnum"></a>
## Enum: AuthProviderEnum
Name | Value
---- | -----
LOCAL | &quot;local&quot;
GOOGLE | &quot;google&quot;
MICROSOFT | &quot;microsoft&quot;
MICROSOFT_MULTITENANT | &quot;microsoft_multitenant&quot;
AWS_COGNITO | &quot;aws_cognito&quot;
OAUTH2_GENERIC | &quot;oauth2_generic&quot;
LDAP | &quot;ldap&quot;

<a name="AuthClientMethodEnum"></a>
## Enum: AuthClientMethodEnum
Name | Value
---- | -----
CLIENT_SECRET_BASIC | &quot;CLIENT_SECRET_BASIC&quot;
CLIENT_SECRET_POST | &quot;CLIENT_SECRET_POST&quot;
CLIENT_SECRET_JWT | &quot;CLIENT_SECRET_JWT&quot;
PRIVATE_KEY_JWT | &quot;PRIVATE_KEY_JWT&quot;
NONE | &quot;NONE&quot;
TLS_CLIENT_AUTH | &quot;TLS_CLIENT_AUTH&quot;
SELF_SIGNED_TLS_CLIENT_AUTH | &quot;SELF_SIGNED_TLS_CLIENT_AUTH&quot;

<a name="AuthGrantTypeEnum"></a>
## Enum: AuthGrantTypeEnum
Name | Value
---- | -----
AUTHORIZATION_CODE | &quot;AUTHORIZATION_CODE&quot;
REFRESH_TOKEN | &quot;REFRESH_TOKEN&quot;
CLIENT_CREDENTIALS | &quot;CLIENT_CREDENTIALS&quot;
PASSWORD | &quot;PASSWORD&quot;
JWT_BEARER | &quot;JWT_BEARER&quot;
DEVICE_CODE | &quot;DEVICE_CODE&quot;
TOKEN_EXCHANGE | &quot;TOKEN_EXCHANGE&quot;

<a name="ConfigurationTypeEnum"></a>
## Enum: ConfigurationTypeEnum
Name | Value
---- | -----
AUTHENTICATION | &quot;AUTHENTICATION&quot;
INTEGRATION | &quot;INTEGRATION&quot;
