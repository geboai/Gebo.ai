# DocumentContentStreamerControllerApi

All URIs are relative to *http://localhost:13012*

Method | HTTP request | Description
------------- | ------------- | -------------
[**streamDocumentReference**](DocumentContentStreamerControllerApi.md#streamDocumentReference) | **POST** /api/users/DocumentContentStreamerController/streamDocumentReference | 
[**streamSearchResult**](DocumentContentStreamerControllerApi.md#streamSearchResult) | **POST** /api/users/DocumentContentStreamerController/streamSearchResult | 

<a name="streamDocumentReference"></a>
# **streamDocumentReference**
> Object streamDocumentReference(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.awss3.invoker.ApiException;
//import gebo.microservices.api.client.awss3.api.DocumentContentStreamerControllerApi;


DocumentContentStreamerControllerApi apiInstance = new DocumentContentStreamerControllerApi();
GDocumentReferenceStreamRequest body = new GDocumentReferenceStreamRequest(); // GDocumentReferenceStreamRequest | 
try {
    Object result = apiInstance.streamDocumentReference(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DocumentContentStreamerControllerApi#streamDocumentReference");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GDocumentReferenceStreamRequest**](GDocumentReferenceStreamRequest.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/octet-stream

<a name="streamSearchResult"></a>
# **streamSearchResult**
> Object streamSearchResult(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.awss3.invoker.ApiException;
//import gebo.microservices.api.client.awss3.api.DocumentContentStreamerControllerApi;


DocumentContentStreamerControllerApi apiInstance = new DocumentContentStreamerControllerApi();
SearchResultStreamRequest body = new SearchResultStreamRequest(); // SearchResultStreamRequest | 
try {
    Object result = apiInstance.streamSearchResult(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DocumentContentStreamerControllerApi#streamSearchResult");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SearchResultStreamRequest**](SearchResultStreamRequest.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/octet-stream

