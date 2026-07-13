# ImageModelsControllerApi

All URIs are relative to *http://localhost:13001*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getImageModelTypes**](ImageModelsControllerApi.md#getImageModelTypes) | **GET** /api/admin/ImageModelsController/getImageModelTypes | 
[**getRuntimeConfiguredImageModels**](ImageModelsControllerApi.md#getRuntimeConfiguredImageModels) | **GET** /api/admin/ImageModelsController/getRuntimeConfiguredImageModels | 

<a name="getImageModelTypes"></a>
# **getImageModelTypes**
> Object getImageModelTypes()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ImageModelsControllerApi;


ImageModelsControllerApi apiInstance = new ImageModelsControllerApi();
try {
    Object result = apiInstance.getImageModelTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ImageModelsControllerApi#getImageModelTypes");
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

<a name="getRuntimeConfiguredImageModels"></a>
# **getRuntimeConfiguredImageModels**
> Object getRuntimeConfiguredImageModels(modelTypeCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ImageModelsControllerApi;


ImageModelsControllerApi apiInstance = new ImageModelsControllerApi();
Object modelTypeCode = null; // Object | 
try {
    Object result = apiInstance.getRuntimeConfiguredImageModels(modelTypeCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ImageModelsControllerApi#getRuntimeConfiguredImageModels");
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

