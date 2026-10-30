# DocumentContentStreamerControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**streamDocumentReference**](DocumentContentStreamerControllerApi.md#streamDocumentReference) | **POST** /api/users/DocumentContentStreamerController/streamDocumentReference | 
[**streamSearchResult**](DocumentContentStreamerControllerApi.md#streamSearchResult) | **POST** /api/users/DocumentContentStreamerController/streamSearchResult | 

<a name="streamDocumentReference"></a>
# **streamDocumentReference**
> File streamDocumentReference(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.DocumentContentStreamerControllerApi;


DocumentContentStreamerControllerApi apiInstance = new DocumentContentStreamerControllerApi();
GDocumentReferenceStreamRequest body = new GDocumentReferenceStreamRequest(); // GDocumentReferenceStreamRequest | 
try {
    File result = apiInstance.streamDocumentReference(body);
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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.DocumentContentStreamerControllerApi;


DocumentContentStreamerControllerApi apiInstance = new DocumentContentStreamerControllerApi();
SearchResultStreamRequest body = new SearchResultStreamRequest(); // SearchResultStreamRequest | 
try {
    File result = apiInstance.streamSearchResult(body);
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

[**File**](File.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/octet-stream

