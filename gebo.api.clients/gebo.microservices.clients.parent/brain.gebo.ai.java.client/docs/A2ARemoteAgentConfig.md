# A2ARemoteAgentConfig

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  |  [optional]
**description** | **String** |  |  [optional]
**userModified** | **String** |  |  [optional]
**userCreated** | **String** |  |  [optional]
**dateModified** | [**Date**](Date.md) |  |  [optional]
**dateCreated** | [**Date**](Date.md) |  |  [optional]
**agentCardUrl** | **String** |  | 
**rpcEndpoint** | **String** |  |  [optional]
**transportType** | [**TransportTypeEnum**](#TransportTypeEnum) |  | 
**authMode** | [**AuthModeEnum**](#AuthModeEnum) |  | 
**secretCode** | **String** |  |  [optional]
**oauth2AuthenticatorCode** | **String** |  |  [optional]
**exportingPrefix** | **String** |  | 
**enabled** | **Boolean** |  |  [optional]
**skills** | [**List&lt;A2ARemoteSkill&gt;**](A2ARemoteSkill.md) |  |  [optional]
**accessibleGroups** | **List&lt;String&gt;** |  |  [optional]
**accessibleUsers** | **List&lt;String&gt;** |  |  [optional]
**accessibleToAll** | **Boolean** |  |  [optional]
**aclAliases** | **List&lt;Integer&gt;** |  |  [optional]

<a name="TransportTypeEnum"></a>
## Enum: TransportTypeEnum
Name | Value
---- | -----
JSONRPC | &quot;JSONRPC&quot;
REST | &quot;REST&quot;
GRPC | &quot;GRPC&quot;

<a name="AuthModeEnum"></a>
## Enum: AuthModeEnum
Name | Value
---- | -----
NONE | &quot;NONE&quot;
API_KEY | &quot;API_KEY&quot;
STATIC_BEARER_TOKEN | &quot;STATIC_BEARER_TOKEN&quot;
OAUTH2_CLIENT_CREDENTIALS | &quot;OAUTH2_CLIENT_CREDENTIALS&quot;
OAUTH2_AUTHORIZATION_CODE_PER_USER | &quot;OAUTH2_AUTHORIZATION_CODE_PER_USER&quot;
USER_TOKEN_RELAY | &quot;USER_TOKEN_RELAY&quot;
TOKEN_EXCHANGE | &quot;TOKEN_EXCHANGE&quot;
