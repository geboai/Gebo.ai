# TextToSpeechModelsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getRuntimeConfiguredTextToSpeechModels**](TextToSpeechModelsControllerApi.md#getRuntimeConfiguredTextToSpeechModels) | **GET** /api/admin/TextToSpeechModelsController/getRuntimeConfiguredTextToSpeechModels | 
[**getTextToSpeechModelTypes**](TextToSpeechModelsControllerApi.md#getTextToSpeechModelTypes) | **GET** /api/admin/TextToSpeechModelsController/getTextToSpeechModelTypes | 

<a name="getRuntimeConfiguredTextToSpeechModels"></a>
# **getRuntimeConfiguredTextToSpeechModels**
> List&lt;ConfigurationEntryGBaseTextToSpeachModelConfig&gt; getRuntimeConfiguredTextToSpeechModels(modelTypeCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.TextToSpeechModelsControllerApi;


TextToSpeechModelsControllerApi apiInstance = new TextToSpeechModelsControllerApi();
String modelTypeCode = "modelTypeCode_example"; // String | 
try {
    List<ConfigurationEntryGBaseTextToSpeachModelConfig> result = apiInstance.getRuntimeConfiguredTextToSpeechModels(modelTypeCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TextToSpeechModelsControllerApi#getRuntimeConfiguredTextToSpeechModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **modelTypeCode** | **String**|  | [optional]

### Return type

[**List&lt;ConfigurationEntryGBaseTextToSpeachModelConfig&gt;**](ConfigurationEntryGBaseTextToSpeachModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getTextToSpeechModelTypes"></a>
# **getTextToSpeechModelTypes**
> List&lt;GTextToSpeechModelType&gt; getTextToSpeechModelTypes()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.TextToSpeechModelsControllerApi;


TextToSpeechModelsControllerApi apiInstance = new TextToSpeechModelsControllerApi();
try {
    List<GTextToSpeechModelType> result = apiInstance.getTextToSpeechModelTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TextToSpeechModelsControllerApi#getTextToSpeechModelTypes");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GTextToSpeechModelType&gt;**](GTextToSpeechModelType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

