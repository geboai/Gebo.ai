# LLMSVendorInfo

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**vendorId** | **String** |  | 
**requiresCustomUrl** | **Boolean** |  |  [optional]
**defaultCustomUrl** | **String** |  |  [optional]
**requiresApiKey** | **Boolean** |  |  [optional]
**secretType** | [**SecretTypeEnum**](#SecretTypeEnum) |  |  [optional]
**authProvider** | [**AuthProviderEnum**](#AuthProviderEnum) |  |  [optional]
**description** | **String** |  | 
**name** | **String** |  | 
**webSite** | **String** |  | 
**acquireKeyUrl** | **String** |  |  [optional]
**apiKeySecretContext** | **String** |  | 

<a name="SecretTypeEnum"></a>
## Enum: SecretTypeEnum
Name | Value
---- | -----
USERNAME_PASSWORD | &quot;USERNAME_PASSWORD&quot;
TOKEN | &quot;TOKEN&quot;
SSH_KEY | &quot;SSH_KEY&quot;
CUSTOM_SECRET | &quot;CUSTOM_SECRET&quot;
OAUTH2_STANDARD | &quot;OAUTH2_STANDARD&quot;
OAUTH2_GOOGLE | &quot;OAUTH2_GOOGLE&quot;
GOOGLE_CLOUD_JSON_CREDENTIALS | &quot;GOOGLE_CLOUD_JSON_CREDENTIALS&quot;
OAUTH2_AUTHORIZED_CLIENT | &quot;OAUTH2_AUTHORIZED_CLIENT&quot;

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
