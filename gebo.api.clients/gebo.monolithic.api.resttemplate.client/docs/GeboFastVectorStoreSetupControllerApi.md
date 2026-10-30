# GeboFastVectorStoreSetupControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createVectorStoreConfiguration**](GeboFastVectorStoreSetupControllerApi.md#createVectorStoreConfiguration) | **POST** /api/admin/GeboFastVectorStoreSetupController/createVectorStoreConfiguration | 
[**getVectorStoreStatus**](GeboFastVectorStoreSetupControllerApi.md#getVectorStoreStatus) | **GET** /api/admin/GeboFastVectorStoreSetupController/getVectorStoreStatus | 

<a name="createVectorStoreConfiguration"></a>
# **createVectorStoreConfiguration**
> OperationStatusComponentVectorStoreStatus createVectorStoreConfiguration(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboFastVectorStoreSetupControllerApi;


GeboFastVectorStoreSetupControllerApi apiInstance = new GeboFastVectorStoreSetupControllerApi();
FastVectorStoreSetupData body = new FastVectorStoreSetupData(); // FastVectorStoreSetupData | 
try {
    OperationStatusComponentVectorStoreStatus result = apiInstance.createVectorStoreConfiguration(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboFastVectorStoreSetupControllerApi#createVectorStoreConfiguration");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FastVectorStoreSetupData**](FastVectorStoreSetupData.md)|  |

### Return type

[**OperationStatusComponentVectorStoreStatus**](OperationStatusComponentVectorStoreStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getVectorStoreStatus"></a>
# **getVectorStoreStatus**
> ComponentVectorStoreStatus getVectorStoreStatus()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboFastVectorStoreSetupControllerApi;


GeboFastVectorStoreSetupControllerApi apiInstance = new GeboFastVectorStoreSetupControllerApi();
try {
    ComponentVectorStoreStatus result = apiInstance.getVectorStoreStatus();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboFastVectorStoreSetupControllerApi#getVectorStoreStatus");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**ComponentVectorStoreStatus**](ComponentVectorStoreStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

