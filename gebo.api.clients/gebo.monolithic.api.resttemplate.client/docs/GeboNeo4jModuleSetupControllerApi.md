# GeboNeo4jModuleSetupControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getNeo4jModuleSetupConfig**](GeboNeo4jModuleSetupControllerApi.md#getNeo4jModuleSetupConfig) | **GET** /api/admin/GeboNeo4jModuleSetupController | 

<a name="getNeo4jModuleSetupConfig"></a>
# **getNeo4jModuleSetupConfig**
> GeboNeo4jModuleConfigDto getNeo4jModuleSetupConfig()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboNeo4jModuleSetupControllerApi;


GeboNeo4jModuleSetupControllerApi apiInstance = new GeboNeo4jModuleSetupControllerApi();
try {
    GeboNeo4jModuleConfigDto result = apiInstance.getNeo4jModuleSetupConfig();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboNeo4jModuleSetupControllerApi#getNeo4jModuleSetupConfig");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GeboNeo4jModuleConfigDto**](GeboNeo4jModuleConfigDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

