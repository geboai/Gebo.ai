# GeboAdminChatPipelinesToolingControllerApi

All URIs are relative to *http://localhost:13999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getPipelinesTooling**](GeboAdminChatPipelinesToolingControllerApi.md#getPipelinesTooling) | **GET** /api/admin/GeboAdminChatPipelinesToolingController/getPipelinesTooling | 
[**saveStepTooling**](GeboAdminChatPipelinesToolingControllerApi.md#saveStepTooling) | **POST** /api/admin/GeboAdminChatPipelinesToolingController/saveStepTooling | 

<a name="getPipelinesTooling"></a>
# **getPipelinesTooling**
> List&lt;PipelineToolingInfo&gt; getPipelinesTooling()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminChatPipelinesToolingControllerApi;


GeboAdminChatPipelinesToolingControllerApi apiInstance = new GeboAdminChatPipelinesToolingControllerApi();
try {
    List<PipelineToolingInfo> result = apiInstance.getPipelinesTooling();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminChatPipelinesToolingControllerApi#getPipelinesTooling");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;PipelineToolingInfo&gt;**](PipelineToolingInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="saveStepTooling"></a>
# **saveStepTooling**
> PipelineStepToolingInfo saveStepTooling(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminChatPipelinesToolingControllerApi;


GeboAdminChatPipelinesToolingControllerApi apiInstance = new GeboAdminChatPipelinesToolingControllerApi();
PipelineStepToolingUpdate body = new PipelineStepToolingUpdate(); // PipelineStepToolingUpdate | 
try {
    PipelineStepToolingInfo result = apiInstance.saveStepTooling(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminChatPipelinesToolingControllerApi#saveStepTooling");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PipelineStepToolingUpdate**](PipelineStepToolingUpdate.md)|  |

### Return type

[**PipelineStepToolingInfo**](PipelineStepToolingInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

