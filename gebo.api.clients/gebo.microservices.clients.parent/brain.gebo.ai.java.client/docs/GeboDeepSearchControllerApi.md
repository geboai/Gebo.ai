# GeboDeepSearchControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getDeepSearchDataSources**](GeboDeepSearchControllerApi.md#getDeepSearchDataSources) | **GET** /api/users/GeboDeepSearchController/getDeepSearchDataSources | 

<a name="getDeepSearchDataSources"></a>
# **getDeepSearchDataSources**
> Object getDeepSearchDataSources()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboDeepSearchControllerApi;


GeboDeepSearchControllerApi apiInstance = new GeboDeepSearchControllerApi();
try {
    Object result = apiInstance.getDeepSearchDataSources();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchControllerApi#getDeepSearchDataSources");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

