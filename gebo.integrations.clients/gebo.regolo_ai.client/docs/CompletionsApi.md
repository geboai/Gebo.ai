# CompletionsApi

All URIs are relative to *https://api.regolo.ai*

Method | HTTP request | Description
------------- | ------------- | -------------
[**completionCompletionsPost**](CompletionsApi.md#completionCompletionsPost) | **POST** /completions | Completion
[**completionEnginesModelCompletionsPost**](CompletionsApi.md#completionEnginesModelCompletionsPost) | **POST** /engines/{model}/completions | Completion
[**completionOpenaiDeploymentsModelCompletionsPost**](CompletionsApi.md#completionOpenaiDeploymentsModelCompletionsPost) | **POST** /openai/deployments/{model}/completions | Completion
[**completionV1CompletionsPost**](CompletionsApi.md#completionV1CompletionsPost) | **POST** /v1/completions | Completion

<a name="completionCompletionsPost"></a>
# **completionCompletionsPost**
> Object completionCompletionsPost(model)

Completion

Follows the exact same API spec as &#x60;OpenAI&#x27;s Completions API https://platform.openai.com/docs/api-reference/completions&#x60;  &#x60;&#x60;&#x60;bash curl -X POST https://api.regolo.ai/v1/completions  -H \&quot;Content-Type: application/json\&quot;  -H \&quot;Authorization: Bearer sk-1234\&quot;  -d &#x27;{     \&quot;model\&quot;: \&quot;gpt-3.5-turbo-instruct\&quot;,     \&quot;prompt\&quot;: \&quot;Once upon a time\&quot;,     \&quot;max_tokens\&quot;: 50,     \&quot;temperature\&quot;: 0.7 }&#x27; &#x60;&#x60;&#x60;

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.CompletionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

CompletionsApi apiInstance = new CompletionsApi();
Object model = null; // Object | 
try {
    Object result = apiInstance.completionCompletionsPost(model);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CompletionsApi#completionCompletionsPost");
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

<a name="completionEnginesModelCompletionsPost"></a>
# **completionEnginesModelCompletionsPost**
> Object completionEnginesModelCompletionsPost(model)

Completion

Follows the exact same API spec as &#x60;OpenAI&#x27;s Completions API https://platform.openai.com/docs/api-reference/completions&#x60;  &#x60;&#x60;&#x60;bash curl -X POST https://api.regolo.ai/v1/completions  -H \&quot;Content-Type: application/json\&quot;  -H \&quot;Authorization: Bearer sk-1234\&quot;  -d &#x27;{     \&quot;model\&quot;: \&quot;gpt-3.5-turbo-instruct\&quot;,     \&quot;prompt\&quot;: \&quot;Once upon a time\&quot;,     \&quot;max_tokens\&quot;: 50,     \&quot;temperature\&quot;: 0.7 }&#x27; &#x60;&#x60;&#x60;

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.CompletionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

CompletionsApi apiInstance = new CompletionsApi();
Object model = null; // Object | 
try {
    Object result = apiInstance.completionEnginesModelCompletionsPost(model);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CompletionsApi#completionEnginesModelCompletionsPost");
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

<a name="completionOpenaiDeploymentsModelCompletionsPost"></a>
# **completionOpenaiDeploymentsModelCompletionsPost**
> Object completionOpenaiDeploymentsModelCompletionsPost(model)

Completion

Follows the exact same API spec as &#x60;OpenAI&#x27;s Completions API https://platform.openai.com/docs/api-reference/completions&#x60;  &#x60;&#x60;&#x60;bash curl -X POST https://api.regolo.ai/v1/completions  -H \&quot;Content-Type: application/json\&quot;  -H \&quot;Authorization: Bearer sk-1234\&quot;  -d &#x27;{     \&quot;model\&quot;: \&quot;gpt-3.5-turbo-instruct\&quot;,     \&quot;prompt\&quot;: \&quot;Once upon a time\&quot;,     \&quot;max_tokens\&quot;: 50,     \&quot;temperature\&quot;: 0.7 }&#x27; &#x60;&#x60;&#x60;

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.CompletionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

CompletionsApi apiInstance = new CompletionsApi();
Object model = null; // Object | 
try {
    Object result = apiInstance.completionOpenaiDeploymentsModelCompletionsPost(model);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CompletionsApi#completionOpenaiDeploymentsModelCompletionsPost");
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

<a name="completionV1CompletionsPost"></a>
# **completionV1CompletionsPost**
> Object completionV1CompletionsPost(model)

Completion

Follows the exact same API spec as &#x60;OpenAI&#x27;s Completions API https://platform.openai.com/docs/api-reference/completions&#x60;  &#x60;&#x60;&#x60;bash curl -X POST https://api.regolo.ai/v1/completions  -H \&quot;Content-Type: application/json\&quot;  -H \&quot;Authorization: Bearer sk-1234\&quot;  -d &#x27;{     \&quot;model\&quot;: \&quot;gpt-3.5-turbo-instruct\&quot;,     \&quot;prompt\&quot;: \&quot;Once upon a time\&quot;,     \&quot;max_tokens\&quot;: 50,     \&quot;temperature\&quot;: 0.7 }&#x27; &#x60;&#x60;&#x60;

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.CompletionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

CompletionsApi apiInstance = new CompletionsApi();
Object model = null; // Object | 
try {
    Object result = apiInstance.completionV1CompletionsPost(model);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CompletionsApi#completionV1CompletionsPost");
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

