# GeboFastKnowledgeBaseSetupControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getCompleteKnowledgeBaseSetupStatus**](GeboFastKnowledgeBaseSetupControllerApi.md#getCompleteKnowledgeBaseSetupStatus) | **GET** /api/admin/GeboFastKnowledgeBaseSetupController/getCompleteKnowledgeBaseSetupStatus | 
[**getContentProcessRows**](GeboFastKnowledgeBaseSetupControllerApi.md#getContentProcessRows) | **GET** /api/admin/GeboFastKnowledgeBaseSetupController/getContentProcessRows | 

<a name="getCompleteKnowledgeBaseSetupStatus"></a>
# **getCompleteKnowledgeBaseSetupStatus**
> GeboKnowledgeBaseSetupStatus getCompleteKnowledgeBaseSetupStatus()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboFastKnowledgeBaseSetupControllerApi;


GeboFastKnowledgeBaseSetupControllerApi apiInstance = new GeboFastKnowledgeBaseSetupControllerApi();
try {
    GeboKnowledgeBaseSetupStatus result = apiInstance.getCompleteKnowledgeBaseSetupStatus();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboFastKnowledgeBaseSetupControllerApi#getCompleteKnowledgeBaseSetupStatus");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GeboKnowledgeBaseSetupStatus**](GeboKnowledgeBaseSetupStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getContentProcessRows"></a>
# **getContentProcessRows**
> Object getContentProcessRows()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboFastKnowledgeBaseSetupControllerApi;


GeboFastKnowledgeBaseSetupControllerApi apiInstance = new GeboFastKnowledgeBaseSetupControllerApi();
try {
    Object result = apiInstance.getContentProcessRows();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboFastKnowledgeBaseSetupControllerApi#getContentProcessRows");
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

