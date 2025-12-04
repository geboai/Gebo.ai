# AuthProviderDto

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**provider** | [**ProviderEnum**](#ProviderEnum) |  | 
**type** | [**TypeEnum**](#TypeEnum) |  | 
**description** | **String** |  | 
**multitenant** | **Boolean** |  | 
**customAttributes** | [**List&lt;Oauth2CustomAttribute&gt;**](Oauth2CustomAttribute.md) |  | 

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

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
LOCAL_JWT | &quot;LOCAL_JWT&quot;
OAUTH2 | &quot;OAUTH2&quot;
LDAP | &quot;LDAP&quot;
