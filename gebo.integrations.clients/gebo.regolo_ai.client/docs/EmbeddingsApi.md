# EmbeddingsApi

All URIs are relative to *https://api.regolo.ai*

Method | HTTP request | Description
------------- | ------------- | -------------
[**embeddingsEmbeddingsPost**](EmbeddingsApi.md#embeddingsEmbeddingsPost) | **POST** /embeddings | Embeddings
[**embeddingsEnginesModelEmbeddingsPost**](EmbeddingsApi.md#embeddingsEnginesModelEmbeddingsPost) | **POST** /engines/{model}/embeddings | Embeddings
[**embeddingsOpenaiDeploymentsModelEmbeddingsPost**](EmbeddingsApi.md#embeddingsOpenaiDeploymentsModelEmbeddingsPost) | **POST** /openai/deployments/{model}/embeddings | Embeddings
[**embeddingsV1EmbeddingsPost**](EmbeddingsApi.md#embeddingsV1EmbeddingsPost) | **POST** /v1/embeddings | Embeddings

<a name="embeddingsEmbeddingsPost"></a>
# **embeddingsEmbeddingsPost**
> Object embeddingsEmbeddingsPost(model)

Embeddings

Follows the exact same API spec as &#x60;OpenAI&#x27;s Embeddings API https://platform.openai.com/docs/api-reference/embeddings&#x60;  &#x60;&#x60;&#x60;bash curl -X POST https://api.regolo.ai/v1/embeddings  -H \&quot;Content-Type: application/json\&quot;  -H \&quot;Authorization: Bearer sk-1234\&quot;  -d &#x27;{     \&quot;model\&quot;: \&quot;text-embedding-ada-002\&quot;,     \&quot;input\&quot;: \&quot;The quick brown fox jumps over the lazy dog\&quot; }&#x27; &#x60;&#x60;&#x60;

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.EmbeddingsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

EmbeddingsApi apiInstance = new EmbeddingsApi();
Object model = null; // Object | 
try {
    Object result = apiInstance.embeddingsEmbeddingsPost(model);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EmbeddingsApi#embeddingsEmbeddingsPost");
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

<a name="embeddingsEnginesModelEmbeddingsPost"></a>
# **embeddingsEnginesModelEmbeddingsPost**
> Object embeddingsEnginesModelEmbeddingsPost(model)

Embeddings

Follows the exact same API spec as &#x60;OpenAI&#x27;s Embeddings API https://platform.openai.com/docs/api-reference/embeddings&#x60;  &#x60;&#x60;&#x60;bash curl -X POST https://api.regolo.ai/v1/embeddings  -H \&quot;Content-Type: application/json\&quot;  -H \&quot;Authorization: Bearer sk-1234\&quot;  -d &#x27;{     \&quot;model\&quot;: \&quot;text-embedding-ada-002\&quot;,     \&quot;input\&quot;: \&quot;The quick brown fox jumps over the lazy dog\&quot; }&#x27; &#x60;&#x60;&#x60;

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.EmbeddingsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

EmbeddingsApi apiInstance = new EmbeddingsApi();
Object model = null; // Object | 
try {
    Object result = apiInstance.embeddingsEnginesModelEmbeddingsPost(model);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EmbeddingsApi#embeddingsEnginesModelEmbeddingsPost");
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

<a name="embeddingsOpenaiDeploymentsModelEmbeddingsPost"></a>
# **embeddingsOpenaiDeploymentsModelEmbeddingsPost**
> Object embeddingsOpenaiDeploymentsModelEmbeddingsPost(model)

Embeddings

Follows the exact same API spec as &#x60;OpenAI&#x27;s Embeddings API https://platform.openai.com/docs/api-reference/embeddings&#x60;  &#x60;&#x60;&#x60;bash curl -X POST https://api.regolo.ai/v1/embeddings  -H \&quot;Content-Type: application/json\&quot;  -H \&quot;Authorization: Bearer sk-1234\&quot;  -d &#x27;{     \&quot;model\&quot;: \&quot;text-embedding-ada-002\&quot;,     \&quot;input\&quot;: \&quot;The quick brown fox jumps over the lazy dog\&quot; }&#x27; &#x60;&#x60;&#x60;

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.EmbeddingsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

EmbeddingsApi apiInstance = new EmbeddingsApi();
Object model = null; // Object | 
try {
    Object result = apiInstance.embeddingsOpenaiDeploymentsModelEmbeddingsPost(model);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EmbeddingsApi#embeddingsOpenaiDeploymentsModelEmbeddingsPost");
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

<a name="embeddingsV1EmbeddingsPost"></a>
# **embeddingsV1EmbeddingsPost**
> Object embeddingsV1EmbeddingsPost(model)

Embeddings

Follows the exact same API spec as &#x60;OpenAI&#x27;s Embeddings API https://platform.openai.com/docs/api-reference/embeddings&#x60;  &#x60;&#x60;&#x60;bash curl -X POST https://api.regolo.ai/v1/embeddings  -H \&quot;Content-Type: application/json\&quot;  -H \&quot;Authorization: Bearer sk-1234\&quot;  -d &#x27;{     \&quot;model\&quot;: \&quot;text-embedding-ada-002\&quot;,     \&quot;input\&quot;: \&quot;The quick brown fox jumps over the lazy dog\&quot; }&#x27; &#x60;&#x60;&#x60;

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.EmbeddingsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

EmbeddingsApi apiInstance = new EmbeddingsApi();
Object model = null; // Object | 
try {
    Object result = apiInstance.embeddingsV1EmbeddingsPost(model);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EmbeddingsApi#embeddingsV1EmbeddingsPost");
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

