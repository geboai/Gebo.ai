# GeboAdvancedSetupStatusControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getFirstKnowledgeBaseSetupStatus**](GeboAdvancedSetupStatusControllerApi.md#getFirstKnowledgeBaseSetupStatus) | **GET** /api/admin/GeboAdvancedSetupStatusController/getFirstKnowledgeBaseSetupStatus | 
[**getMinimalContentsSetupStatus**](GeboAdvancedSetupStatusControllerApi.md#getMinimalContentsSetupStatus) | **GET** /api/admin/GeboAdvancedSetupStatusController/getMinimalContentsSetupStatus | 

<a name="getFirstKnowledgeBaseSetupStatus"></a>
# **getFirstKnowledgeBaseSetupStatus**
> ComponentSetupStatus getFirstKnowledgeBaseSetupStatus()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdvancedSetupStatusControllerApi;


GeboAdvancedSetupStatusControllerApi apiInstance = new GeboAdvancedSetupStatusControllerApi();
try {
    ComponentSetupStatus result = apiInstance.getFirstKnowledgeBaseSetupStatus();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdvancedSetupStatusControllerApi#getFirstKnowledgeBaseSetupStatus");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**ComponentSetupStatus**](ComponentSetupStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMinimalContentsSetupStatus"></a>
# **getMinimalContentsSetupStatus**
> ComponentSetupStatus getMinimalContentsSetupStatus()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdvancedSetupStatusControllerApi;


GeboAdvancedSetupStatusControllerApi apiInstance = new GeboAdvancedSetupStatusControllerApi();
try {
    ComponentSetupStatus result = apiInstance.getMinimalContentsSetupStatus();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdvancedSetupStatusControllerApi#getMinimalContentsSetupStatus");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**ComponentSetupStatus**](ComponentSetupStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

