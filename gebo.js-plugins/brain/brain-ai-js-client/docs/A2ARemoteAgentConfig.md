# BrainClient.A2ARemoteAgentConfig

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  | [optional] 
**description** | **String** |  | [optional] 
**userModified** | **String** |  | [optional] 
**userCreated** | **String** |  | [optional] 
**dateModified** | **Date** |  | [optional] 
**dateCreated** | **Date** |  | [optional] 
**agentCardUrl** | **String** |  | 
**rpcEndpoint** | **String** |  | [optional] 
**transportType** | **String** |  | 
**authMode** | **String** |  | 
**secretCode** | **String** |  | [optional] 
**oauth2AuthenticatorCode** | **String** |  | [optional] 
**exportingPrefix** | **String** |  | 
**enabled** | **Boolean** |  | [optional] 
**skills** | [**[A2ARemoteSkill]**](A2ARemoteSkill.md) |  | [optional] 
**accessibleGroups** | **[String]** |  | [optional] 
**accessibleUsers** | **[String]** |  | [optional] 
**accessibleToAll** | **Boolean** |  | [optional] 
**aclAliases** | **[Number]** |  | [optional] 

<a name="TransportTypeEnum"></a>
## Enum: TransportTypeEnum

* `JSONRPC` (value: `"JSONRPC"`)
* `REST` (value: `"REST"`)
* `GRPC` (value: `"GRPC"`)


<a name="AuthModeEnum"></a>
## Enum: AuthModeEnum

* `NONE` (value: `"NONE"`)
* `API_KEY` (value: `"API_KEY"`)
* `STATIC_BEARER_TOKEN` (value: `"STATIC_BEARER_TOKEN"`)
* `oAUTH2CLIENTCREDENTIALS` (value: `"OAUTH2_CLIENT_CREDENTIALS"`)
* `oAUTH2AUTHORIZATIONCODEPERUSER` (value: `"OAUTH2_AUTHORIZATION_CODE_PER_USER"`)
* `USER_TOKEN_RELAY` (value: `"USER_TOKEN_RELAY"`)
* `TOKEN_EXCHANGE` (value: `"TOKEN_EXCHANGE"`)

