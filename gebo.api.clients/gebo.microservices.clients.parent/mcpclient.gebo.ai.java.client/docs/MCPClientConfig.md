# MCPClientConfig

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**code** | **String** |  |  [optional]
**description** | **String** |  |  [optional]
**userModified** | **String** |  |  [optional]
**userCreated** | **String** |  |  [optional]
**dateModified** | [**Date**](Date.md) |  |  [optional]
**dateCreated** | [**Date**](Date.md) |  |  [optional]
**baseUrl** | **String** |  |  [optional]
**mcpEndpoint** | **String** |  |  [optional]
**sseEndpoint** | **String** |  |  [optional]
**secretCode** | **String** |  |  [optional]
**oauth2AuthenticatorCode** | **String** |  |  [optional]
**stdioCommand** | **String** |  |  [optional]
**stdioArgs** | **List&lt;String&gt;** |  |  [optional]
**stdioEnvironment** | **Map&lt;String, String&gt;** |  |  [optional]
**transportType** | [**TransportTypeEnum**](#TransportTypeEnum) |  | 
**authMode** | [**AuthModeEnum**](#AuthModeEnum) |  | 
**exportingPrefix** | **String** |  | 
**accessibleGroups** | **List&lt;String&gt;** |  |  [optional]
**accessibleUsers** | **List&lt;String&gt;** |  |  [optional]
**accessibleToAll** | **Boolean** |  |  [optional]
**aclAliases** | **List&lt;Integer&gt;** |  |  [optional]
**tools** | [**List&lt;MCPTool&gt;**](MCPTool.md) |  |  [optional]
**resources** | [**List&lt;MCPResource&gt;**](MCPResource.md) |  |  [optional]
**prompts** | [**List&lt;MCPPrompt&gt;**](MCPPrompt.md) |  |  [optional]

<a name="TransportTypeEnum"></a>
## Enum: TransportTypeEnum
Name | Value
---- | -----
STREAMABLE_HTTP | &quot;STREAMABLE_HTTP&quot;
SSE_LEGACY | &quot;SSE_LEGACY&quot;
STDIO | &quot;STDIO&quot;

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
