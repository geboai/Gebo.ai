# DataFlowMetaInfoControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getLocalDataFlow**](DataFlowMetaInfoControllerApi.md#getLocalDataFlow) | **GET** /api/admin/DataFlowMetaInfoController/getLocalDataFlow | 

<a name="getLocalDataFlow"></a>
# **getLocalDataFlow**
> GDataFlowReport getLocalDataFlow()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.DataFlowMetaInfoControllerApi;


DataFlowMetaInfoControllerApi apiInstance = new DataFlowMetaInfoControllerApi();
try {
    GDataFlowReport result = apiInstance.getLocalDataFlow();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DataFlowMetaInfoControllerApi#getLocalDataFlow");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GDataFlowReport**](GDataFlowReport.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

