# GeboAiClient.MCPClientConfig

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  | [optional] 
**description** | **String** |  | [optional] 
**userModified** | **String** |  | [optional] 
**userCreated** | **String** |  | [optional] 
**dateModified** | **Date** |  | [optional] 
**dateCreated** | **Date** |  | [optional] 
**baseUrl** | **String** |  | [optional] 
**mcpEndpoint** | **String** |  | [optional] 
**sseEndpoint** | **String** |  | [optional] 
**secretCode** | **String** |  | [optional] 
**oauth2AuthenticatorCode** | **String** |  | [optional] 
**stdioCommand** | **String** |  | [optional] 
**stdioArgs** | **[String]** |  | [optional] 
**stdioEnvironment** | **{String: String}** |  | [optional] 
**transportType** | **String** |  | 
**authMode** | **String** |  | 
**exportingPrefix** | **String** |  | 
**accessibleGroups** | **[String]** |  | [optional] 
**accessibleUsers** | **[String]** |  | [optional] 
**accessibleToAll** | **Boolean** |  | [optional] 
**aclAliases** | **[Number]** |  | [optional] 
**tools** | [**[MCPTool]**](MCPTool.md) |  | [optional] 
**resources** | [**[MCPResource]**](MCPResource.md) |  | [optional] 
**prompts** | [**[MCPPrompt]**](MCPPrompt.md) |  | [optional] 

<a name="TransportTypeEnum"></a>
## Enum: TransportTypeEnum

* `STREAMABLE_HTTP` (value: `"STREAMABLE_HTTP"`)
* `SSE_LEGACY` (value: `"SSE_LEGACY"`)
* `STDIO` (value: `"STDIO"`)


<a name="AuthModeEnum"></a>
## Enum: AuthModeEnum

* `NONE` (value: `"NONE"`)
* `API_KEY` (value: `"API_KEY"`)
* `STATIC_BEARER_TOKEN` (value: `"STATIC_BEARER_TOKEN"`)
* `oAUTH2CLIENTCREDENTIALS` (value: `"OAUTH2_CLIENT_CREDENTIALS"`)
* `oAUTH2AUTHORIZATIONCODEPERUSER` (value: `"OAUTH2_AUTHORIZATION_CODE_PER_USER"`)
* `USER_TOKEN_RELAY` (value: `"USER_TOKEN_RELAY"`)
* `TOKEN_EXCHANGE` (value: `"TOKEN_EXCHANGE"`)

