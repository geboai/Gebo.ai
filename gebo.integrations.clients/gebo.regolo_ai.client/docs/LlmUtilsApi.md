# LlmUtilsApi

All URIs are relative to *https://api.regolo.ai*

Method | HTTP request | Description
------------- | ------------- | -------------
[**supportedOpenaiParamsUtilsSupportedOpenaiParamsGet**](LlmUtilsApi.md#supportedOpenaiParamsUtilsSupportedOpenaiParamsGet) | **GET** /utils/supported_openai_params | Supported Openai Params
[**tokenCounterUtilsTokenCounterPost**](LlmUtilsApi.md#tokenCounterUtilsTokenCounterPost) | **POST** /utils/token_counter | Token Counter

<a name="supportedOpenaiParamsUtilsSupportedOpenaiParamsGet"></a>
# **supportedOpenaiParamsUtilsSupportedOpenaiParamsGet**
> Object supportedOpenaiParamsUtilsSupportedOpenaiParamsGet(model)

Supported Openai Params

Returns supported openai params for a given litellm model name   e.g. &#x60;gpt-4&#x60; vs &#x60;gpt-3.5-turbo&#x60;   Example curl:  &#x60;&#x60;&#x60; curl -X GET --location &#x27;https://api.regolo.ai/utils/supported_openai_params?model&#x3D;gpt-3.5-turbo-16k&#x27;         --header &#x27;Authorization: Bearer sk-1234&#x27; &#x60;&#x60;&#x60;

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.LlmUtilsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

LlmUtilsApi apiInstance = new LlmUtilsApi();
Object model = null; // Object | 
try {
    Object result = apiInstance.supportedOpenaiParamsUtilsSupportedOpenaiParamsGet(model);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LlmUtilsApi#supportedOpenaiParamsUtilsSupportedOpenaiParamsGet");
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

<a name="tokenCounterUtilsTokenCounterPost"></a>
# **tokenCounterUtilsTokenCounterPost**
> TokenCountResponse tokenCounterUtilsTokenCounterPost(body)

Token Counter

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.LlmUtilsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

LlmUtilsApi apiInstance = new LlmUtilsApi();
TokenCountRequest body = new TokenCountRequest(); // TokenCountRequest | 
try {
    TokenCountResponse result = apiInstance.tokenCounterUtilsTokenCounterPost(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LlmUtilsApi#tokenCounterUtilsTokenCounterPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**TokenCountRequest**](TokenCountRequest.md)|  |

### Return type

[**TokenCountResponse**](TokenCountResponse.md)

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

