# ImagesApi

All URIs are relative to *https://api.regolo.ai*

Method | HTTP request | Description
------------- | ------------- | -------------
[**imageGenerationImagesGenerationsPost**](ImagesApi.md#imageGenerationImagesGenerationsPost) | **POST** /images/generations | Image Generation
[**imageGenerationV1ImagesGenerationsPost**](ImagesApi.md#imageGenerationV1ImagesGenerationsPost) | **POST** /v1/images/generations | Image Generation

<a name="imageGenerationImagesGenerationsPost"></a>
# **imageGenerationImagesGenerationsPost**
> Object imageGenerationImagesGenerationsPost(body)

Image Generation

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.ImagesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

ImagesApi apiInstance = new ImagesApi();
ImagesGenerationsBody body = new ImagesGenerationsBody(); // ImagesGenerationsBody | 
try {
    Object result = apiInstance.imageGenerationImagesGenerationsPost(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ImagesApi#imageGenerationImagesGenerationsPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ImagesGenerationsBody**](ImagesGenerationsBody.md)|  |

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="imageGenerationV1ImagesGenerationsPost"></a>
# **imageGenerationV1ImagesGenerationsPost**
> Object imageGenerationV1ImagesGenerationsPost(body)

Image Generation

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.ImagesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

ImagesApi apiInstance = new ImagesApi();
ImagesGenerationsBody1 body = new ImagesGenerationsBody1(); // ImagesGenerationsBody1 | 
try {
    Object result = apiInstance.imageGenerationV1ImagesGenerationsPost(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ImagesApi#imageGenerationV1ImagesGenerationsPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ImagesGenerationsBody1**](ImagesGenerationsBody1.md)|  |

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

