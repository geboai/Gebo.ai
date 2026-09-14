# DocumentContentStreamerWithCacheControllerApi

All URIs are relative to *http://localhost:13004/chunker*

Method | HTTP request | Description
------------- | ------------- | -------------
[**streamDocumentReference**](DocumentContentStreamerWithCacheControllerApi.md#streamDocumentReference) | **POST** /api/DocumentContentStreamerWithCacheController/streamDocumentReference | 
[**streamSearchResult**](DocumentContentStreamerWithCacheControllerApi.md#streamSearchResult) | **POST** /api/DocumentContentStreamerWithCacheController/streamSearchResult | 

<a name="streamDocumentReference"></a>
# **streamDocumentReference**
> File streamDocumentReference(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.chunker.invoker.ApiException;
//import gebo.microservices.api.client.chunker.api.DocumentContentStreamerWithCacheControllerApi;


DocumentContentStreamerWithCacheControllerApi apiInstance = new DocumentContentStreamerWithCacheControllerApi();
GDocumentReferenceStreamRequest body = new GDocumentReferenceStreamRequest(); // GDocumentReferenceStreamRequest | 
try {
    File result = apiInstance.streamDocumentReference(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DocumentContentStreamerWithCacheControllerApi#streamDocumentReference");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GDocumentReferenceStreamRequest**](GDocumentReferenceStreamRequest.md)|  |

### Return type

[**File**](File.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/octet-stream

<a name="streamSearchResult"></a>
# **streamSearchResult**
> File streamSearchResult(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.chunker.invoker.ApiException;
//import gebo.microservices.api.client.chunker.api.DocumentContentStreamerWithCacheControllerApi;


DocumentContentStreamerWithCacheControllerApi apiInstance = new DocumentContentStreamerWithCacheControllerApi();
SearchResultStreamRequest body = new SearchResultStreamRequest(); // SearchResultStreamRequest | 
try {
    File result = apiInstance.streamSearchResult(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DocumentContentStreamerWithCacheControllerApi#streamSearchResult");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SearchResultStreamRequest**](SearchResultStreamRequest.md)|  |

### Return type

[**File**](File.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/octet-stream

