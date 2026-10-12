# GeboAiClient.EditableUser

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** |  | 
**sourname** | **String** |  | 
**username** | **String** |  | 
**disabled** | **Boolean** |  | [optional] 
**roles** | **[String]** |  | 
**authProvider** | **String** |  | 
**langCode** | **String** |  | [optional] 
**customInfos** | **{String: Object}** |  | [optional] 

<a name="AuthProviderEnum"></a>
## Enum: AuthProviderEnum

* `local` (value: `"local"`)
* `google` (value: `"google"`)
* `microsoft` (value: `"microsoft"`)
* `microsoftMultitenant` (value: `"microsoft_multitenant"`)
* `awsCognito` (value: `"aws_cognito"`)
* `awsIdentityCenter` (value: `"aws_identity_center"`)
* `keycloak` (value: `"keycloak"`)
* `oauth2Generic` (value: `"oauth2_generic"`)
* `ldap` (value: `"ldap"`)

