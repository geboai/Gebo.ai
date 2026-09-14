# GeboAdminRagAutotuneControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getLatestComputedVectorStores**](GeboAdminRagAutotuneControllerApi.md#getLatestComputedVectorStores) | **GET** /api/admin/GeboAdminRagAutotuneController/getLatestComputedVectorStores | 

<a name="getLatestComputedVectorStores"></a>
# **getLatestComputedVectorStores**
> List&lt;AutotuneVectorStoreInfo&gt; getLatestComputedVectorStores()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAdminRagAutotuneControllerApi;


GeboAdminRagAutotuneControllerApi apiInstance = new GeboAdminRagAutotuneControllerApi();
try {
    List<AutotuneVectorStoreInfo> result = apiInstance.getLatestComputedVectorStores();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminRagAutotuneControllerApi#getLatestComputedVectorStores");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;AutotuneVectorStoreInfo&gt;**](AutotuneVectorStoreInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

