# ChatModelsLookupControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getChatModelTypesLookup**](ChatModelsLookupControllerApi.md#getChatModelTypesLookup) | **GET** /api/users/ChatModelsLookupController/getChatModelTypesLookup | 
[**getDefaultChatModel**](ChatModelsLookupControllerApi.md#getDefaultChatModel) | **GET** /api/users/ChatModelsLookupController/getDefaultChatModel | 
[**getRuntimeConfiguredChatModelsLookup**](ChatModelsLookupControllerApi.md#getRuntimeConfiguredChatModelsLookup) | **GET** /api/users/ChatModelsLookupController/getRuntimeConfiguredChatModelsLookup | 

<a name="getChatModelTypesLookup"></a>
# **getChatModelTypesLookup**
> List&lt;GLookupEntry&gt; getChatModelTypesLookup()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ChatModelsLookupControllerApi;


ChatModelsLookupControllerApi apiInstance = new ChatModelsLookupControllerApi();
try {
    List<GLookupEntry> result = apiInstance.getChatModelTypesLookup();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ChatModelsLookupControllerApi#getChatModelTypesLookup");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GLookupEntry&gt;**](GLookupEntry.md)

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ChatModelsLookupControllerApi;


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
> List&lt;GLookupEntryRef&gt; getRuntimeConfiguredChatModelsLookup(modelTypeCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ChatModelsLookupControllerApi;


ChatModelsLookupControllerApi apiInstance = new ChatModelsLookupControllerApi();
String modelTypeCode = "modelTypeCode_example"; // String | 
try {
    List<GLookupEntryRef> result = apiInstance.getRuntimeConfiguredChatModelsLookup(modelTypeCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ChatModelsLookupControllerApi#getRuntimeConfiguredChatModelsLookup");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **modelTypeCode** | **String**|  | [optional]

### Return type

[**List&lt;GLookupEntryRef&gt;**](GLookupEntryRef.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

