# EditableUser

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** |  | 
**sourname** | **String** |  | 
**username** | **String** |  | 
**disabled** | **Boolean** |  |  [optional]
**roles** | **List&lt;String&gt;** |  | 
**authProvider** | [**AuthProviderEnum**](#AuthProviderEnum) |  | 

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
