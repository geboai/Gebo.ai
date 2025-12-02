# EmbeddingModelsControllersApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getEmbeddingModelTypes**](EmbeddingModelsControllersApi.md#getEmbeddingModelTypes) | **GET** /api/admin/EmbeddingModelsControllers/getEmbeddingModelTypes | 
[**getRuntimeConfiguredEmbeddingModels**](EmbeddingModelsControllersApi.md#getRuntimeConfiguredEmbeddingModels) | **GET** /api/admin/EmbeddingModelsControllers/getRuntimeConfiguredEmbeddingModels | 

<a name="getEmbeddingModelTypes"></a>
# **getEmbeddingModelTypes**
> List&lt;GEmbeddingModelType&gt; getEmbeddingModelTypes()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.EmbeddingModelsControllersApi;


EmbeddingModelsControllersApi apiInstance = new EmbeddingModelsControllersApi();
try {
    List<GEmbeddingModelType> result = apiInstance.getEmbeddingModelTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EmbeddingModelsControllersApi#getEmbeddingModelTypes");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GEmbeddingModelType&gt;**](GEmbeddingModelType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getRuntimeConfiguredEmbeddingModels"></a>
# **getRuntimeConfiguredEmbeddingModels**
> List&lt;ConfigurationEntry&gt; getRuntimeConfiguredEmbeddingModels(modelTypeCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.EmbeddingModelsControllersApi;


EmbeddingModelsControllersApi apiInstance = new EmbeddingModelsControllersApi();
String modelTypeCode = "modelTypeCode_example"; // String | 
try {
    List<ConfigurationEntry> result = apiInstance.getRuntimeConfiguredEmbeddingModels(modelTypeCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EmbeddingModelsControllersApi#getRuntimeConfiguredEmbeddingModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **modelTypeCode** | **String**|  | [optional]

### Return type

[**List&lt;ConfigurationEntry&gt;**](ConfigurationEntry.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

