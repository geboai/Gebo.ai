# ChatModelsControllerApi

All URIs are relative to *http://localhost:13003*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getChatModelTypes**](ChatModelsControllerApi.md#getChatModelTypes) | **GET** /api/admin/ChatModelsController/getChatModelTypes | 
[**getRuntimeConfiguredChatModels**](ChatModelsControllerApi.md#getRuntimeConfiguredChatModels) | **GET** /api/admin/ChatModelsController/getRuntimeConfiguredChatModels | 

<a name="getChatModelTypes"></a>
# **getChatModelTypes**
> Object getChatModelTypes()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.ChatModelsControllerApi;


ChatModelsControllerApi apiInstance = new ChatModelsControllerApi();
try {
    Object result = apiInstance.getChatModelTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ChatModelsControllerApi#getChatModelTypes");
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

<a name="getRuntimeConfiguredChatModels"></a>
# **getRuntimeConfiguredChatModels**
> Object getRuntimeConfiguredChatModels(modelTypeCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.ChatModelsControllerApi;


ChatModelsControllerApi apiInstance = new ChatModelsControllerApi();
Object modelTypeCode = null; // Object | 
try {
    Object result = apiInstance.getRuntimeConfiguredChatModels(modelTypeCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ChatModelsControllerApi#getRuntimeConfiguredChatModels");
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

