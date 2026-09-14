# GeboDeepSearchControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getDeepSearchDataSources**](GeboDeepSearchControllerApi.md#getDeepSearchDataSources) | **GET** /api/users/GeboDeepSearchController/getDeepSearchDataSources | 

<a name="getDeepSearchDataSources"></a>
# **getDeepSearchDataSources**
> List&lt;GBaseObject&gt; getDeepSearchDataSources()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboDeepSearchControllerApi;


GeboDeepSearchControllerApi apiInstance = new GeboDeepSearchControllerApi();
try {
    List<GBaseObject> result = apiInstance.getDeepSearchDataSources();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchControllerApi#getDeepSearchDataSources");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GBaseObject&gt;**](GBaseObject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

