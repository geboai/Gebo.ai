# TranscriptModelsControllerApi

All URIs are relative to *http://localhost:13003*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getRuntimeConfiguredTranscriptModels**](TranscriptModelsControllerApi.md#getRuntimeConfiguredTranscriptModels) | **GET** /api/admin/TranscriptModelsController/getRuntimeConfiguredTranscriptModels | 
[**getTranscriptModelTypes**](TranscriptModelsControllerApi.md#getTranscriptModelTypes) | **GET** /api/admin/TranscriptModelsController/getTranscriptModelTypes | 

<a name="getRuntimeConfiguredTranscriptModels"></a>
# **getRuntimeConfiguredTranscriptModels**
> Object getRuntimeConfiguredTranscriptModels(modelTypeCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.TranscriptModelsControllerApi;


TranscriptModelsControllerApi apiInstance = new TranscriptModelsControllerApi();
Object modelTypeCode = null; // Object | 
try {
    Object result = apiInstance.getRuntimeConfiguredTranscriptModels(modelTypeCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TranscriptModelsControllerApi#getRuntimeConfiguredTranscriptModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **modelTypeCode** | [**Object**](.md)|  | [optional]

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getTranscriptModelTypes"></a>
# **getTranscriptModelTypes**
> Object getTranscriptModelTypes()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.TranscriptModelsControllerApi;


TranscriptModelsControllerApi apiInstance = new TranscriptModelsControllerApi();
try {
    Object result = apiInstance.getTranscriptModelTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TranscriptModelsControllerApi#getTranscriptModelTypes");
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

