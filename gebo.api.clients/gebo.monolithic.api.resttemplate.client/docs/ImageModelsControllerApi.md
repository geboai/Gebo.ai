# ImageModelsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getImageModelTypes**](ImageModelsControllerApi.md#getImageModelTypes) | **GET** /api/admin/ImageModelsController/getImageModelTypes | 
[**getRuntimeConfiguredImageModels**](ImageModelsControllerApi.md#getRuntimeConfiguredImageModels) | **GET** /api/admin/ImageModelsController/getRuntimeConfiguredImageModels | 

<a name="getImageModelTypes"></a>
# **getImageModelTypes**
> List&lt;GImageModelType&gt; getImageModelTypes()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ImageModelsControllerApi;


ImageModelsControllerApi apiInstance = new ImageModelsControllerApi();
try {
    List<GImageModelType> result = apiInstance.getImageModelTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ImageModelsControllerApi#getImageModelTypes");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GImageModelType&gt;**](GImageModelType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getRuntimeConfiguredImageModels"></a>
# **getRuntimeConfiguredImageModels**
> List&lt;ConfigurationEntryGBaseImageModelConfig&gt; getRuntimeConfiguredImageModels(modelTypeCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ImageModelsControllerApi;


ImageModelsControllerApi apiInstance = new ImageModelsControllerApi();
String modelTypeCode = "modelTypeCode_example"; // String | 
try {
    List<ConfigurationEntryGBaseImageModelConfig> result = apiInstance.getRuntimeConfiguredImageModels(modelTypeCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ImageModelsControllerApi#getRuntimeConfiguredImageModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **modelTypeCode** | **String**|  | [optional]

### Return type

[**List&lt;ConfigurationEntryGBaseImageModelConfig&gt;**](ConfigurationEntryGBaseImageModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

