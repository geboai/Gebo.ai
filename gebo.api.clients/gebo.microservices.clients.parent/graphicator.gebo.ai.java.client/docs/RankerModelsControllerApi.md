# RankerModelsControllerApi

All URIs are relative to *http://localhost:13003*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getRankerModelTypes**](RankerModelsControllerApi.md#getRankerModelTypes) | **GET** /api/admin/RankerModelsController/getRankerModelTypes | 
[**getRuntimeConfiguredRankerModels**](RankerModelsControllerApi.md#getRuntimeConfiguredRankerModels) | **GET** /api/admin/RankerModelsController/getRuntimeConfiguredRankerModels | 

<a name="getRankerModelTypes"></a>
# **getRankerModelTypes**
> Object getRankerModelTypes()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.RankerModelsControllerApi;


RankerModelsControllerApi apiInstance = new RankerModelsControllerApi();
try {
    Object result = apiInstance.getRankerModelTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RankerModelsControllerApi#getRankerModelTypes");
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

<a name="getRuntimeConfiguredRankerModels"></a>
# **getRuntimeConfiguredRankerModels**
> Object getRuntimeConfiguredRankerModels(modelTypeCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.RankerModelsControllerApi;


RankerModelsControllerApi apiInstance = new RankerModelsControllerApi();
Object modelTypeCode = null; // Object | 
try {
    Object result = apiInstance.getRuntimeConfiguredRankerModels(modelTypeCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RankerModelsControllerApi#getRuntimeConfiguredRankerModels");
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

