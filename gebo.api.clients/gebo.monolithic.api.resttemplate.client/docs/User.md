# User

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** |  |  [optional]
**sourname** | **String** |  |  [optional]
**username** | **String** |  |  [optional]
**imageUrl** | **String** |  |  [optional]
**emailVerified** | **Boolean** |  |  [optional]
**disabled** | **Boolean** |  |  [optional]
**provider** | [**ProviderEnum**](#ProviderEnum) |  | 
**roles** | **List&lt;String&gt;** |  |  [optional]

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
