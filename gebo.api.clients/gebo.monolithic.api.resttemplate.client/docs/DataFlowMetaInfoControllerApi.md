# DataFlowMetaInfoControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getLocalDataFlow**](DataFlowMetaInfoControllerApi.md#getLocalDataFlow) | **GET** /api/admin/DataFlowMetaInfoController/getLocalDataFlow | 

<a name="getLocalDataFlow"></a>
# **getLocalDataFlow**
> GDataFlowReport getLocalDataFlow()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.DataFlowMetaInfoControllerApi;


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

