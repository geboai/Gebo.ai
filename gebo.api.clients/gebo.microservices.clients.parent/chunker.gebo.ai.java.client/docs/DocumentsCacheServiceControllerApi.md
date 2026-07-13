# DocumentsCacheServiceControllerApi

All URIs are relative to *http://localhost:13004*

Method | HTTP request | Description
------------- | ------------- | -------------
[**streamDocument**](DocumentsCacheServiceControllerApi.md#streamDocument) | **POST** /api/DocumentsCacheServiceController/streamDocument | 

<a name="streamDocument"></a>
# **streamDocument**
> Object streamDocument(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.chunker.invoker.ApiException;
//import gebo.microservices.api.client.chunker.api.DocumentsCacheServiceControllerApi;


DocumentsCacheServiceControllerApi apiInstance = new DocumentsCacheServiceControllerApi();
StreamDocumentRequest body = new StreamDocumentRequest(); // StreamDocumentRequest | 
try {
    Object result = apiInstance.streamDocument(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DocumentsCacheServiceControllerApi#streamDocument");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**StreamDocumentRequest**](StreamDocumentRequest.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/octet-stream

