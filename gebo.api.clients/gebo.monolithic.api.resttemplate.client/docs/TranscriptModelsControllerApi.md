# TranscriptModelsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getRuntimeConfiguredTranscriptModels**](TranscriptModelsControllerApi.md#getRuntimeConfiguredTranscriptModels) | **GET** /api/admin/TranscriptModelsController/getRuntimeConfiguredTranscriptModels | 
[**getTranscriptModelTypes**](TranscriptModelsControllerApi.md#getTranscriptModelTypes) | **GET** /api/admin/TranscriptModelsController/getTranscriptModelTypes | 

<a name="getRuntimeConfiguredTranscriptModels"></a>
# **getRuntimeConfiguredTranscriptModels**
> List&lt;ConfigurationEntryGBaseTranscriptModelConfig&gt; getRuntimeConfiguredTranscriptModels(modelTypeCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.TranscriptModelsControllerApi;


TranscriptModelsControllerApi apiInstance = new TranscriptModelsControllerApi();
String modelTypeCode = "modelTypeCode_example"; // String | 
try {
    List<ConfigurationEntryGBaseTranscriptModelConfig> result = apiInstance.getRuntimeConfiguredTranscriptModels(modelTypeCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TranscriptModelsControllerApi#getRuntimeConfiguredTranscriptModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **modelTypeCode** | **String**|  | [optional]

### Return type

[**List&lt;ConfigurationEntryGBaseTranscriptModelConfig&gt;**](ConfigurationEntryGBaseTranscriptModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getTranscriptModelTypes"></a>
# **getTranscriptModelTypes**
> List&lt;GTranscriptModelType&gt; getTranscriptModelTypes()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.TranscriptModelsControllerApi;


TranscriptModelsControllerApi apiInstance = new TranscriptModelsControllerApi();
try {
    List<GTranscriptModelType> result = apiInstance.getTranscriptModelTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TranscriptModelsControllerApi#getTranscriptModelTypes");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GTranscriptModelType&gt;**](GTranscriptModelType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

