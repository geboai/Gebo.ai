# CreateUserIfNotExistsRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**username** | **String** |  |  [optional]
**attributes** | **Map&lt;String, Object&gt;** |  |  [optional]
**authProvider** | [**AuthProviderEnum**](#AuthProviderEnum) |  |  [optional]

<a name="AuthProviderEnum"></a>
## Enum: AuthProviderEnum
Name | Value
---- | -----
LOCAL | &quot;local&quot;
GOOGLE | &quot;google&quot;
MICROSOFT | &quot;microsoft&quot;
MICROSOFT_MULTITENANT | &quot;microsoft_multitenant&quot;
AWS_COGNITO | &quot;aws_cognito&quot;
AWS_IDENTITY_CENTER | &quot;aws_identity_center&quot;
KEYCLOAK | &quot;keycloak&quot;
OAUTH2_GENERIC | &quot;oauth2_generic&quot;
LDAP | &quot;ldap&quot;
