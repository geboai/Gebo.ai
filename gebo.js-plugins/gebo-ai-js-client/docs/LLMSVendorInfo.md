# GeboAiClient.LLMSVendorInfo

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**vendorId** | **String** |  | 
**requiresCustomUrl** | **Boolean** |  | [optional] 
**defaultCustomUrl** | **String** |  | [optional] 
**requiresApiKey** | **Boolean** |  | [optional] 
**secretType** | **String** |  | [optional] 
**authProvider** | **String** |  | [optional] 
**description** | **String** |  | 
**name** | **String** |  | 
**webSite** | **String** |  | 
**acquireKeyUrl** | **String** |  | [optional] 
**apiKeySecretContext** | **String** |  | 
**minContextWindow** | **Number** |  | [optional] 
**supportsAutoconfig** | **Boolean** |  | [optional] 

<a name="SecretTypeEnum"></a>
## Enum: SecretTypeEnum

* `USERNAME_PASSWORD` (value: `"USERNAME_PASSWORD"`)
* `TOKEN` (value: `"TOKEN"`)
* `SSH_KEY` (value: `"SSH_KEY"`)
* `CUSTOM_SECRET` (value: `"CUSTOM_SECRET"`)
* `oAUTH2STANDARD` (value: `"OAUTH2_STANDARD"`)
* `oAUTH2GOOGLE` (value: `"OAUTH2_GOOGLE"`)
* `GOOGLE_CLOUD_JSON_CREDENTIALS` (value: `"GOOGLE_CLOUD_JSON_CREDENTIALS"`)
* `oAUTH2AUTHORIZEDCLIENT` (value: `"OAUTH2_AUTHORIZED_CLIENT"`)
* `AWS_CONNECTION` (value: `"AWS_CONNECTION"`)


<a name="AuthProviderEnum"></a>
## Enum: AuthProviderEnum

* `local` (value: `"local"`)
* `google` (value: `"google"`)
* `microsoft` (value: `"microsoft"`)
* `microsoftMultitenant` (value: `"microsoft_multitenant"`)
* `awsCognito` (value: `"aws_cognito"`)
* `oauth2Generic` (value: `"oauth2_generic"`)
* `ldap` (value: `"ldap"`)

