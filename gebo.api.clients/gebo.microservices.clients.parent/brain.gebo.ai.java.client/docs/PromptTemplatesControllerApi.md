# PromptTemplatesControllerApi

All URIs are relative to *http://localhost:13001*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getDefaultPrompt**](PromptTemplatesControllerApi.md#getDefaultPrompt) | **GET** /api/admin/PromptTemplatesController/getDefaultPrompt | 
[**getDefaultPromptForChatModel**](PromptTemplatesControllerApi.md#getDefaultPromptForChatModel) | **POST** /api/admin/PromptTemplatesController/getDefaultPromptForChatModel | 
[**getDefaultPromptForChatModelReference**](PromptTemplatesControllerApi.md#getDefaultPromptForChatModelReference) | **POST** /api/admin/PromptTemplatesController/getDefaultPromptForChatModelReference | 

<a name="getDefaultPrompt"></a>
# **getDefaultPrompt**
> GPromptTemplateConfig getDefaultPrompt(ragPrompt)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.PromptTemplatesControllerApi;


PromptTemplatesControllerApi apiInstance = new PromptTemplatesControllerApi();
Object ragPrompt = null; // Object | 
try {
    GPromptTemplateConfig result = apiInstance.getDefaultPrompt(ragPrompt);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PromptTemplatesControllerApi#getDefaultPrompt");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ragPrompt** | [**Object**](.md)|  |

### Return type

[**GPromptTemplateConfig**](GPromptTemplateConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getDefaultPromptForChatModel"></a>
# **getDefaultPromptForChatModel**
> GPromptTemplateConfig getDefaultPromptForChatModel(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.PromptTemplatesControllerApi;


PromptTemplatesControllerApi apiInstance = new PromptTemplatesControllerApi();
DefaultPromptForChatModelParam body = new DefaultPromptForChatModelParam(); // DefaultPromptForChatModelParam | 
try {
    GPromptTemplateConfig result = apiInstance.getDefaultPromptForChatModel(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PromptTemplatesControllerApi#getDefaultPromptForChatModel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DefaultPromptForChatModelParam**](DefaultPromptForChatModelParam.md)|  |

### Return type

[**GPromptTemplateConfig**](GPromptTemplateConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getDefaultPromptForChatModelReference"></a>
# **getDefaultPromptForChatModelReference**
> GPromptTemplateConfig getDefaultPromptForChatModelReference(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.PromptTemplatesControllerApi;


PromptTemplatesControllerApi apiInstance = new PromptTemplatesControllerApi();
DefaultPromptForChatModelReferenceParam body = new DefaultPromptForChatModelReferenceParam(); // DefaultPromptForChatModelReferenceParam | 
try {
    GPromptTemplateConfig result = apiInstance.getDefaultPromptForChatModelReference(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PromptTemplatesControllerApi#getDefaultPromptForChatModelReference");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DefaultPromptForChatModelReferenceParam**](DefaultPromptForChatModelReferenceParam.md)|  |

### Return type

[**GPromptTemplateConfig**](GPromptTemplateConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

