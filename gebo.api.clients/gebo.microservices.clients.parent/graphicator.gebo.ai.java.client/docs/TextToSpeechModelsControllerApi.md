# TextToSpeechModelsControllerApi

All URIs are relative to *http://localhost:13003*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getRuntimeConfiguredTextToSpeechModels**](TextToSpeechModelsControllerApi.md#getRuntimeConfiguredTextToSpeechModels) | **GET** /api/admin/TextToSpeechModelsController/getRuntimeConfiguredTextToSpeechModels | 
[**getTextToSpeechModelTypes**](TextToSpeechModelsControllerApi.md#getTextToSpeechModelTypes) | **GET** /api/admin/TextToSpeechModelsController/getTextToSpeechModelTypes | 

<a name="getRuntimeConfiguredTextToSpeechModels"></a>
# **getRuntimeConfiguredTextToSpeechModels**
> Object getRuntimeConfiguredTextToSpeechModels(modelTypeCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.TextToSpeechModelsControllerApi;


TextToSpeechModelsControllerApi apiInstance = new TextToSpeechModelsControllerApi();
Object modelTypeCode = null; // Object | 
try {
    Object result = apiInstance.getRuntimeConfiguredTextToSpeechModels(modelTypeCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TextToSpeechModelsControllerApi#getRuntimeConfiguredTextToSpeechModels");
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

<a name="getTextToSpeechModelTypes"></a>
# **getTextToSpeechModelTypes**
> Object getTextToSpeechModelTypes()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.TextToSpeechModelsControllerApi;


TextToSpeechModelsControllerApi apiInstance = new TextToSpeechModelsControllerApi();
try {
    Object result = apiInstance.getTextToSpeechModelTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TextToSpeechModelsControllerApi#getTextToSpeechModelTypes");
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

