# ChatModelsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getChatModelTypes**](ChatModelsControllerApi.md#getChatModelTypes) | **GET** /api/admin/ChatModelsController/getChatModelTypes | 
[**getRuntimeConfiguredChatModels**](ChatModelsControllerApi.md#getRuntimeConfiguredChatModels) | **GET** /api/admin/ChatModelsController/getRuntimeConfiguredChatModels | 

<a name="getChatModelTypes"></a>
# **getChatModelTypes**
> List&lt;GChatModelType&gt; getChatModelTypes()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ChatModelsControllerApi;


ChatModelsControllerApi apiInstance = new ChatModelsControllerApi();
try {
    List<GChatModelType> result = apiInstance.getChatModelTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ChatModelsControllerApi#getChatModelTypes");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GChatModelType&gt;**](GChatModelType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getRuntimeConfiguredChatModels"></a>
# **getRuntimeConfiguredChatModels**
> List&lt;ConfigurationEntry&gt; getRuntimeConfiguredChatModels(modelTypeCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ChatModelsControllerApi;


ChatModelsControllerApi apiInstance = new ChatModelsControllerApi();
String modelTypeCode = "modelTypeCode_example"; // String | 
try {
    List<ConfigurationEntry> result = apiInstance.getRuntimeConfiguredChatModels(modelTypeCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ChatModelsControllerApi#getRuntimeConfiguredChatModels");
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

