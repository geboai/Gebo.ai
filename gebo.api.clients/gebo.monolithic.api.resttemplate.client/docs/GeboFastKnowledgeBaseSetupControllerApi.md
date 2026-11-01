# GeboFastKnowledgeBaseSetupControllerApi

All URIs are relative to *http://localhost:12999*

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboFastKnowledgeBaseSetupControllerApi;


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
> List&lt;GeboContentProcessRow&gt; getContentProcessRows()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboFastKnowledgeBaseSetupControllerApi;


GeboFastKnowledgeBaseSetupControllerApi apiInstance = new GeboFastKnowledgeBaseSetupControllerApi();
try {
    List<GeboContentProcessRow> result = apiInstance.getContentProcessRows();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboFastKnowledgeBaseSetupControllerApi#getContentProcessRows");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GeboContentProcessRow&gt;**](GeboContentProcessRow.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

