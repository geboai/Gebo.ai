# InternalMessagingTopologyControllerApi

All URIs are relative to *http://localhost:13018/heimdall*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getLocalTopology**](InternalMessagingTopologyControllerApi.md#getLocalTopology) | **GET** /api/admin/InternalMessagingTopologyController/getLocalTopology | 

<a name="getLocalTopology"></a>
# **getLocalTopology**
> Object getLocalTopology()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.InternalMessagingTopologyControllerApi;


InternalMessagingTopologyControllerApi apiInstance = new InternalMessagingTopologyControllerApi();
try {
    Object result = apiInstance.getLocalTopology();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling InternalMessagingTopologyControllerApi#getLocalTopology");
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

