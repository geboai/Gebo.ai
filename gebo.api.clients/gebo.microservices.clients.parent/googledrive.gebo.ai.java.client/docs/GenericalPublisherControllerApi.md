# GenericalPublisherControllerApi

All URIs are relative to *http://localhost:13013/googledrive*

Method | HTTP request | Description
------------- | ------------- | -------------
[**publishCentralizedEndpoint**](GenericalPublisherControllerApi.md#publishCentralizedEndpoint) | **POST** /api/admin/GenericalPublisherController/publishCentralizedEndpoint | 

<a name="publishCentralizedEndpoint"></a>
# **publishCentralizedEndpoint**
> OperationStatusGJobStatus publishCentralizedEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GenericalPublisherControllerApi;


GenericalPublisherControllerApi apiInstance = new GenericalPublisherControllerApi();
GCentralizedProjectEndpoint body = new GCentralizedProjectEndpoint(); // GCentralizedProjectEndpoint | 
try {
    OperationStatusGJobStatus result = apiInstance.publishCentralizedEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericalPublisherControllerApi#publishCentralizedEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GCentralizedProjectEndpoint**](GCentralizedProjectEndpoint.md)|  |

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

