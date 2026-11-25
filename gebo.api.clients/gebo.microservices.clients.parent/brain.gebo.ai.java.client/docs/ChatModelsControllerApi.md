# ChatModelsControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**applyModelsMetadataInputModalities**](ChatModelsControllerApi.md#applyModelsMetadataInputModalities) | **POST** /api/admin/ChatModelsController/applyModelsMetadataInputModalities | 
[**getCarriedMediaTypes**](ChatModelsControllerApi.md#getCarriedMediaTypes) | **GET** /api/admin/ChatModelsController/getCarriedMediaTypes | 
[**getChatModelTypes**](ChatModelsControllerApi.md#getChatModelTypes) | **GET** /api/admin/ChatModelsController/getChatModelTypes | 
[**getChatModelsInputs**](ChatModelsControllerApi.md#getChatModelsInputs) | **GET** /api/admin/ChatModelsController/getChatModelsInputs | 
[**getFileKindsSentToModels**](ChatModelsControllerApi.md#getFileKindsSentToModels) | **GET** /api/admin/ChatModelsController/getFileKindsSentToModels | 
[**getRuntimeConfiguredChatModels**](ChatModelsControllerApi.md#getRuntimeConfiguredChatModels) | **GET** /api/admin/ChatModelsController/getRuntimeConfiguredChatModels | 

<a name="applyModelsMetadataInputModalities"></a>
# **applyModelsMetadataInputModalities**
> OperationStatusListChatModelInputs applyModelsMetadataInputModalities()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ChatModelsControllerApi;


ChatModelsControllerApi apiInstance = new ChatModelsControllerApi();
try {
    OperationStatusListChatModelInputs result = apiInstance.applyModelsMetadataInputModalities();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ChatModelsControllerApi#applyModelsMetadataInputModalities");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**OperationStatusListChatModelInputs**](OperationStatusListChatModelInputs.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCarriedMediaTypes"></a>
# **getCarriedMediaTypes**
> GModelCarriedMediaTypes getCarriedMediaTypes(modelTypeCode, modelCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ChatModelsControllerApi;


ChatModelsControllerApi apiInstance = new ChatModelsControllerApi();
String modelTypeCode = "modelTypeCode_example"; // String | 
String modelCode = "modelCode_example"; // String | 
try {
    GModelCarriedMediaTypes result = apiInstance.getCarriedMediaTypes(modelTypeCode, modelCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ChatModelsControllerApi#getCarriedMediaTypes");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **modelTypeCode** | **String**|  |
 **modelCode** | **String**|  | [optional]

### Return type

[**GModelCarriedMediaTypes**](GModelCarriedMediaTypes.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getChatModelTypes"></a>
# **getChatModelTypes**
> List&lt;GChatModelType&gt; getChatModelTypes()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ChatModelsControllerApi;


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

<a name="getChatModelsInputs"></a>
# **getChatModelsInputs**
> List&lt;ChatModelInputs&gt; getChatModelsInputs()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ChatModelsControllerApi;


ChatModelsControllerApi apiInstance = new ChatModelsControllerApi();
try {
    List<ChatModelInputs> result = apiInstance.getChatModelsInputs();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ChatModelsControllerApi#getChatModelsInputs");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;ChatModelInputs&gt;**](ChatModelInputs.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getFileKindsSentToModels"></a>
# **getFileKindsSentToModels**
> List&lt;String&gt; getFileKindsSentToModels()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ChatModelsControllerApi;


ChatModelsControllerApi apiInstance = new ChatModelsControllerApi();
try {
    List<String> result = apiInstance.getFileKindsSentToModels();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ChatModelsControllerApi#getFileKindsSentToModels");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**List&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getRuntimeConfiguredChatModels"></a>
# **getRuntimeConfiguredChatModels**
> List&lt;ConfigurationEntryGBaseChatModelConfig&gt; getRuntimeConfiguredChatModels(modelTypeCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ChatModelsControllerApi;


ChatModelsControllerApi apiInstance = new ChatModelsControllerApi();
String modelTypeCode = "modelTypeCode_example"; // String | 
try {
    List<ConfigurationEntryGBaseChatModelConfig> result = apiInstance.getRuntimeConfiguredChatModels(modelTypeCode);
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

[**List&lt;ConfigurationEntryGBaseChatModelConfig&gt;**](ConfigurationEntryGBaseChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

