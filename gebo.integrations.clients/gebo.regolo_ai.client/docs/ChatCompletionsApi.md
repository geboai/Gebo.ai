# ChatCompletionsApi

All URIs are relative to *https://api.regolo.ai*

Method | HTTP request | Description
------------- | ------------- | -------------
[**chatCompletionChatCompletionsPost**](ChatCompletionsApi.md#chatCompletionChatCompletionsPost) | **POST** /chat/completions | Chat Completion
[**chatCompletionEnginesModelChatCompletionsPost**](ChatCompletionsApi.md#chatCompletionEnginesModelChatCompletionsPost) | **POST** /engines/{model}/chat/completions | Chat Completion
[**chatCompletionOpenaiDeploymentsModelChatCompletionsPost**](ChatCompletionsApi.md#chatCompletionOpenaiDeploymentsModelChatCompletionsPost) | **POST** /openai/deployments/{model}/chat/completions | Chat Completion
[**chatCompletionV1ChatCompletionsPost**](ChatCompletionsApi.md#chatCompletionV1ChatCompletionsPost) | **POST** /v1/chat/completions | Chat Completion

<a name="chatCompletionChatCompletionsPost"></a>
# **chatCompletionChatCompletionsPost**
> Object chatCompletionChatCompletionsPost(model)

Chat Completion

Follows the exact same API spec as &#x60;OpenAI&#x27;s Chat API https://platform.openai.com/docs/api-reference/chat&#x60;  &#x60;&#x60;&#x60;bash curl -X POST https://api.regolo.ai/v1/chat/completions  -H \&quot;Content-Type: application/json\&quot;  -H \&quot;Authorization: Bearer sk-1234\&quot;  -d &#x27;{     \&quot;model\&quot;: \&quot;gpt-4o\&quot;,     \&quot;messages\&quot;: [         {             \&quot;role\&quot;: \&quot;user\&quot;,             \&quot;content\&quot;: \&quot;Hello!\&quot;         }     ] }&#x27; &#x60;&#x60;&#x60;

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.ChatCompletionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

ChatCompletionsApi apiInstance = new ChatCompletionsApi();
Object model = null; // Object | 
try {
    Object result = apiInstance.chatCompletionChatCompletionsPost(model);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ChatCompletionsApi#chatCompletionChatCompletionsPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **model** | [**Object**](.md)|  | [optional]

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="chatCompletionEnginesModelChatCompletionsPost"></a>
# **chatCompletionEnginesModelChatCompletionsPost**
> Object chatCompletionEnginesModelChatCompletionsPost(model)

Chat Completion

Follows the exact same API spec as &#x60;OpenAI&#x27;s Chat API https://platform.openai.com/docs/api-reference/chat&#x60;  &#x60;&#x60;&#x60;bash curl -X POST https://api.regolo.ai/v1/chat/completions  -H \&quot;Content-Type: application/json\&quot;  -H \&quot;Authorization: Bearer sk-1234\&quot;  -d &#x27;{     \&quot;model\&quot;: \&quot;gpt-4o\&quot;,     \&quot;messages\&quot;: [         {             \&quot;role\&quot;: \&quot;user\&quot;,             \&quot;content\&quot;: \&quot;Hello!\&quot;         }     ] }&#x27; &#x60;&#x60;&#x60;

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.ChatCompletionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

ChatCompletionsApi apiInstance = new ChatCompletionsApi();
Object model = null; // Object | 
try {
    Object result = apiInstance.chatCompletionEnginesModelChatCompletionsPost(model);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ChatCompletionsApi#chatCompletionEnginesModelChatCompletionsPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **model** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="chatCompletionOpenaiDeploymentsModelChatCompletionsPost"></a>
# **chatCompletionOpenaiDeploymentsModelChatCompletionsPost**
> Object chatCompletionOpenaiDeploymentsModelChatCompletionsPost(model)

Chat Completion

Follows the exact same API spec as &#x60;OpenAI&#x27;s Chat API https://platform.openai.com/docs/api-reference/chat&#x60;  &#x60;&#x60;&#x60;bash curl -X POST https://api.regolo.ai/v1/chat/completions  -H \&quot;Content-Type: application/json\&quot;  -H \&quot;Authorization: Bearer sk-1234\&quot;  -d &#x27;{     \&quot;model\&quot;: \&quot;gpt-4o\&quot;,     \&quot;messages\&quot;: [         {             \&quot;role\&quot;: \&quot;user\&quot;,             \&quot;content\&quot;: \&quot;Hello!\&quot;         }     ] }&#x27; &#x60;&#x60;&#x60;

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.ChatCompletionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

ChatCompletionsApi apiInstance = new ChatCompletionsApi();
Object model = null; // Object | 
try {
    Object result = apiInstance.chatCompletionOpenaiDeploymentsModelChatCompletionsPost(model);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ChatCompletionsApi#chatCompletionOpenaiDeploymentsModelChatCompletionsPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **model** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="chatCompletionV1ChatCompletionsPost"></a>
# **chatCompletionV1ChatCompletionsPost**
> Object chatCompletionV1ChatCompletionsPost(model)

Chat Completion

Follows the exact same API spec as &#x60;OpenAI&#x27;s Chat API https://platform.openai.com/docs/api-reference/chat&#x60;  &#x60;&#x60;&#x60;bash curl -X POST https://api.regolo.ai/v1/chat/completions  -H \&quot;Content-Type: application/json\&quot;  -H \&quot;Authorization: Bearer sk-1234\&quot;  -d &#x27;{     \&quot;model\&quot;: \&quot;gpt-4o\&quot;,     \&quot;messages\&quot;: [         {             \&quot;role\&quot;: \&quot;user\&quot;,             \&quot;content\&quot;: \&quot;Hello!\&quot;         }     ] }&#x27; &#x60;&#x60;&#x60;

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.ChatCompletionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

ChatCompletionsApi apiInstance = new ChatCompletionsApi();
Object model = null; // Object | 
try {
    Object result = apiInstance.chatCompletionV1ChatCompletionsPost(model);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ChatCompletionsApi#chatCompletionV1ChatCompletionsPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **model** | [**Object**](.md)|  | [optional]

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

