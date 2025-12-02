# Oauth2ProviderConfig

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**provider** | [**ProviderEnum**](#ProviderEnum) |  | 
**authorizationUri** | **String** |  | 
**tokenUri** | **String** |  | 
**userInfoUri** | **String** |  | 
**userNameAttribute** | **String** |  | 
**introspectionUri** | **String** |  |  [optional]
**issuerUri** | **String** |  |  [optional]
**jwkSetUri** | **String** |  |  [optional]

<a name="ProviderEnum"></a>
## Enum: ProviderEnum
Name | Value
---- | -----
LOCAL | &quot;local&quot;
GOOGLE | &quot;google&quot;
MICROSOFT | &quot;microsoft&quot;
MICROSOFT_MULTITENANT | &quot;microsoft_multitenant&quot;
AWS_COGNITO | &quot;aws_cognito&quot;
OAUTH2_GENERIC | &quot;oauth2_generic&quot;
LDAP | &quot;ldap&quot;
