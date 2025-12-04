# GeboVectorStoreConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getActualVectorStoreConfiguration**](GeboVectorStoreConfigurationControllerApi.md#getActualVectorStoreConfiguration) | **GET** /api/admin/GeboVectorStoreConfigurationController/getActualVectorStoreConfiguration | 
[**vectorStoreConfigurationApplyAndSave**](GeboVectorStoreConfigurationControllerApi.md#vectorStoreConfigurationApplyAndSave) | **POST** /api/admin/GeboVectorStoreConfigurationController/vectorStoreConfigurationApplyAndSave | 

<a name="getActualVectorStoreConfiguration"></a>
# **getActualVectorStoreConfiguration**
> GeboMongoVectorStoreConfig getActualVectorStoreConfiguration()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboVectorStoreConfigurationControllerApi;


GeboVectorStoreConfigurationControllerApi apiInstance = new GeboVectorStoreConfigurationControllerApi();
try {
    GeboMongoVectorStoreConfig result = apiInstance.getActualVectorStoreConfiguration();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboVectorStoreConfigurationControllerApi#getActualVectorStoreConfiguration");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GeboMongoVectorStoreConfig**](GeboMongoVectorStoreConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="vectorStoreConfigurationApplyAndSave"></a>
# **vectorStoreConfigurationApplyAndSave**
> OperationStatusGeboMongoVectorStoreConfig vectorStoreConfigurationApplyAndSave(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboVectorStoreConfigurationControllerApi;


GeboVectorStoreConfigurationControllerApi apiInstance = new GeboVectorStoreConfigurationControllerApi();
GeboMongoVectorStoreConfig body = new GeboMongoVectorStoreConfig(); // GeboMongoVectorStoreConfig | 
try {
    OperationStatusGeboMongoVectorStoreConfig result = apiInstance.vectorStoreConfigurationApplyAndSave(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboVectorStoreConfigurationControllerApi#vectorStoreConfigurationApplyAndSave");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GeboMongoVectorStoreConfig**](GeboMongoVectorStoreConfig.md)|  |

### Return type

[**OperationStatusGeboMongoVectorStoreConfig**](OperationStatusGeboMongoVectorStoreConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

