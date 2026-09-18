# GlobalInternalTopologyControllerApi

All URIs are relative to *http://localhost:13019/tyr*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getGlobalTopology**](GlobalInternalTopologyControllerApi.md#getGlobalTopology) | **GET** /api/admin/GlobalInternalTopologyController/getGlobalTopology | 
[**refresh**](GlobalInternalTopologyControllerApi.md#refresh) | **POST** /api/admin/GlobalInternalTopologyController/refresh | 

<a name="getGlobalTopology"></a>
# **getGlobalTopology**
> List&lt;MicroserviceMetaInfo&gt; getGlobalTopology()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.tyr.invoker.ApiException;
//import gebo.microservices.api.client.tyr.api.GlobalInternalTopologyControllerApi;


GlobalInternalTopologyControllerApi apiInstance = new GlobalInternalTopologyControllerApi();
try {
    List<MicroserviceMetaInfo> result = apiInstance.getGlobalTopology();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlobalInternalTopologyControllerApi#getGlobalTopology");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;MicroserviceMetaInfo&gt;**](MicroserviceMetaInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="refresh"></a>
# **refresh**
> Boolean refresh()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.tyr.invoker.ApiException;
//import gebo.microservices.api.client.tyr.api.GlobalInternalTopologyControllerApi;


GlobalInternalTopologyControllerApi apiInstance = new GlobalInternalTopologyControllerApi();
try {
    Boolean result = apiInstance.refresh();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlobalInternalTopologyControllerApi#refresh");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Boolean**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

