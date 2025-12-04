# PromptTemplatesControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getDefaultPrompt**](PromptTemplatesControllerApi.md#getDefaultPrompt) | **GET** /api/admin/PromptTemplatesController/getDefaultPrompt | 
[**getDefaultPromptForChatModel**](PromptTemplatesControllerApi.md#getDefaultPromptForChatModel) | **POST** /api/admin/PromptTemplatesController/getDefaultPromptForChatModel | 
[**getDefaultPromptForChatModelReference**](PromptTemplatesControllerApi.md#getDefaultPromptForChatModelReference) | **POST** /api/admin/PromptTemplatesController/getDefaultPromptForChatModelReference | 

<a name="getDefaultPrompt"></a>
# **getDefaultPrompt**
> GPromptConfig getDefaultPrompt(ragPrompt)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.PromptTemplatesControllerApi;


PromptTemplatesControllerApi apiInstance = new PromptTemplatesControllerApi();
Boolean ragPrompt = true; // Boolean | 
try {
    GPromptConfig result = apiInstance.getDefaultPrompt(ragPrompt);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PromptTemplatesControllerApi#getDefaultPrompt");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ragPrompt** | **Boolean**|  |

### Return type

[**GPromptConfig**](GPromptConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getDefaultPromptForChatModel"></a>
# **getDefaultPromptForChatModel**
> GPromptConfig getDefaultPromptForChatModel(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.PromptTemplatesControllerApi;


PromptTemplatesControllerApi apiInstance = new PromptTemplatesControllerApi();
DefaultPromptForChatModelParam body = new DefaultPromptForChatModelParam(); // DefaultPromptForChatModelParam | 
try {
    GPromptConfig result = apiInstance.getDefaultPromptForChatModel(body);
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

[**GPromptConfig**](GPromptConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getDefaultPromptForChatModelReference"></a>
# **getDefaultPromptForChatModelReference**
> GPromptConfig getDefaultPromptForChatModelReference(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.PromptTemplatesControllerApi;


PromptTemplatesControllerApi apiInstance = new PromptTemplatesControllerApi();
DefaultPromptForChatModelReferenceParam body = new DefaultPromptForChatModelReferenceParam(); // DefaultPromptForChatModelReferenceParam | 
try {
    GPromptConfig result = apiInstance.getDefaultPromptForChatModelReference(body);
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

[**GPromptConfig**](GPromptConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

