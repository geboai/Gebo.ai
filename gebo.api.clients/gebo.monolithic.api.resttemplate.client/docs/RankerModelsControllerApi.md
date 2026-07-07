# RankerModelsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getRankerModelTypes**](RankerModelsControllerApi.md#getRankerModelTypes) | **GET** /api/admin/RankerModelsController/getRankerModelTypes | 
[**getRuntimeConfiguredRankerModels**](RankerModelsControllerApi.md#getRuntimeConfiguredRankerModels) | **GET** /api/admin/RankerModelsController/getRuntimeConfiguredRankerModels | 

<a name="getRankerModelTypes"></a>
# **getRankerModelTypes**
> List&lt;GRankerModelType&gt; getRankerModelTypes()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.RankerModelsControllerApi;


RankerModelsControllerApi apiInstance = new RankerModelsControllerApi();
try {
    List<GRankerModelType> result = apiInstance.getRankerModelTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RankerModelsControllerApi#getRankerModelTypes");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GRankerModelType&gt;**](GRankerModelType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getRuntimeConfiguredRankerModels"></a>
# **getRuntimeConfiguredRankerModels**
> List&lt;ConfigurationEntryGBaseRankerModelConfig&gt; getRuntimeConfiguredRankerModels(modelTypeCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.RankerModelsControllerApi;


RankerModelsControllerApi apiInstance = new RankerModelsControllerApi();
String modelTypeCode = "modelTypeCode_example"; // String | 
try {
    List<ConfigurationEntryGBaseRankerModelConfig> result = apiInstance.getRuntimeConfiguredRankerModels(modelTypeCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RankerModelsControllerApi#getRuntimeConfiguredRankerModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **modelTypeCode** | **String**|  | [optional]

### Return type

[**List&lt;ConfigurationEntryGBaseRankerModelConfig&gt;**](ConfigurationEntryGBaseRankerModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

