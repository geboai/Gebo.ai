# ClientsTopologyProviderControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getClientsTopology**](ClientsTopologyProviderControllerApi.md#getClientsTopology) | **GET** /public/ClientsTopologyProviderController | 

<a name="getClientsTopology"></a>
# **getClientsTopology**
> GeboClientsTopologyInfo getClientsTopology()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ClientsTopologyProviderControllerApi;


ClientsTopologyProviderControllerApi apiInstance = new ClientsTopologyProviderControllerApi();
try {
    GeboClientsTopologyInfo result = apiInstance.getClientsTopology();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientsTopologyProviderControllerApi#getClientsTopology");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GeboClientsTopologyInfo**](GeboClientsTopologyInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

