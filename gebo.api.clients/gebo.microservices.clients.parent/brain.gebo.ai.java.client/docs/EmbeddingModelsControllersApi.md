# EmbeddingModelsControllersApi

All URIs are relative to *http://localhost:13001*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getEmbeddingModelTypes**](EmbeddingModelsControllersApi.md#getEmbeddingModelTypes) | **GET** /api/admin/EmbeddingModelsControllers/getEmbeddingModelTypes | 
[**getRuntimeConfiguredEmbeddingModels**](EmbeddingModelsControllersApi.md#getRuntimeConfiguredEmbeddingModels) | **GET** /api/admin/EmbeddingModelsControllers/getRuntimeConfiguredEmbeddingModels | 

<a name="getEmbeddingModelTypes"></a>
# **getEmbeddingModelTypes**
> Object getEmbeddingModelTypes()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.EmbeddingModelsControllersApi;


EmbeddingModelsControllersApi apiInstance = new EmbeddingModelsControllersApi();
try {
    Object result = apiInstance.getEmbeddingModelTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EmbeddingModelsControllersApi#getEmbeddingModelTypes");
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

<a name="getRuntimeConfiguredEmbeddingModels"></a>
# **getRuntimeConfiguredEmbeddingModels**
> Object getRuntimeConfiguredEmbeddingModels(modelTypeCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.EmbeddingModelsControllersApi;


EmbeddingModelsControllersApi apiInstance = new EmbeddingModelsControllersApi();
Object modelTypeCode = null; // Object | 
try {
    Object result = apiInstance.getRuntimeConfiguredEmbeddingModels(modelTypeCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EmbeddingModelsControllersApi#getRuntimeConfiguredEmbeddingModels");
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

