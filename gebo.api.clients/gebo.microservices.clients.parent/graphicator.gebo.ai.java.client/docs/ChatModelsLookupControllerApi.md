# ChatModelsLookupControllerApi

All URIs are relative to *http://localhost:13003*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getChatModelTypesLookup**](ChatModelsLookupControllerApi.md#getChatModelTypesLookup) | **GET** /api/users/ChatModelsLookupController/getChatModelTypesLookup | 
[**getDefaultChatModel**](ChatModelsLookupControllerApi.md#getDefaultChatModel) | **GET** /api/users/ChatModelsLookupController/getDefaultChatModel | 
[**getRuntimeConfiguredChatModelsLookup**](ChatModelsLookupControllerApi.md#getRuntimeConfiguredChatModelsLookup) | **GET** /api/users/ChatModelsLookupController/getRuntimeConfiguredChatModelsLookup | 

<a name="getChatModelTypesLookup"></a>
# **getChatModelTypesLookup**
> Object getChatModelTypesLookup()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.ChatModelsLookupControllerApi;


ChatModelsLookupControllerApi apiInstance = new ChatModelsLookupControllerApi();
try {
    Object result = apiInstance.getChatModelTypesLookup();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ChatModelsLookupControllerApi#getChatModelTypesLookup");
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

<a name="getDefaultChatModel"></a>
# **getDefaultChatModel**
> GLookupEntryRefGBaseChatModelConfig getDefaultChatModel()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.ChatModelsLookupControllerApi;


ChatModelsLookupControllerApi apiInstance = new ChatModelsLookupControllerApi();
try {
    GLookupEntryRefGBaseChatModelConfig result = apiInstance.getDefaultChatModel();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ChatModelsLookupControllerApi#getDefaultChatModel");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GLookupEntryRefGBaseChatModelConfig**](GLookupEntryRefGBaseChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getRuntimeConfiguredChatModelsLookup"></a>
# **getRuntimeConfiguredChatModelsLookup**
> Object getRuntimeConfiguredChatModelsLookup(modelTypeCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.ChatModelsLookupControllerApi;


ChatModelsLookupControllerApi apiInstance = new ChatModelsLookupControllerApi();
Object modelTypeCode = null; // Object | 
try {
    Object result = apiInstance.getRuntimeConfiguredChatModelsLookup(modelTypeCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ChatModelsLookupControllerApi#getRuntimeConfiguredChatModelsLookup");
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

