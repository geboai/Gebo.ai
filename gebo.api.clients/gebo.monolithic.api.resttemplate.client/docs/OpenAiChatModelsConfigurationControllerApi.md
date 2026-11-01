# OpenAiChatModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteOpenAIChatModelConfig**](OpenAiChatModelsConfigurationControllerApi.md#deleteOpenAIChatModelConfig) | **POST** /api/admin/OpenAIModelsConfigurationController/deleteOpenAIChatModelConfig | 
[**findOpenAIChatModelConfigByCode**](OpenAiChatModelsConfigurationControllerApi.md#findOpenAIChatModelConfigByCode) | **GET** /api/admin/OpenAIModelsConfigurationController/findOpenAIChatModelConfigByCode | 
[**getOpenAIChatModels**](OpenAiChatModelsConfigurationControllerApi.md#getOpenAIChatModels) | **POST** /api/admin/OpenAIModelsConfigurationController/getOpenAIChatModels | 
[**insertOpenAIChatModelConfig**](OpenAiChatModelsConfigurationControllerApi.md#insertOpenAIChatModelConfig) | **POST** /api/admin/OpenAIModelsConfigurationController/insertOpenAIChatModelConfig | 
[**updateOpenAIChatModelConfig**](OpenAiChatModelsConfigurationControllerApi.md#updateOpenAIChatModelConfig) | **POST** /api/admin/OpenAIModelsConfigurationController/updateOpenAIChatModelConfig | 

<a name="deleteOpenAIChatModelConfig"></a>
# **deleteOpenAIChatModelConfig**
> OperationStatusBoolean deleteOpenAIChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OpenAiChatModelsConfigurationControllerApi;


OpenAiChatModelsConfigurationControllerApi apiInstance = new OpenAiChatModelsConfigurationControllerApi();
GOpenAIChatModelConfig body = new GOpenAIChatModelConfig(); // GOpenAIChatModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteOpenAIChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiChatModelsConfigurationControllerApi#deleteOpenAIChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIChatModelConfig**](GOpenAIChatModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findOpenAIChatModelConfigByCode"></a>
# **findOpenAIChatModelConfigByCode**
> GOpenAIChatModelConfig findOpenAIChatModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OpenAiChatModelsConfigurationControllerApi;


OpenAiChatModelsConfigurationControllerApi apiInstance = new OpenAiChatModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GOpenAIChatModelConfig result = apiInstance.findOpenAIChatModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiChatModelsConfigurationControllerApi#findOpenAIChatModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GOpenAIChatModelConfig**](GOpenAIChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getOpenAIChatModels"></a>
# **getOpenAIChatModels**
> OperationStatusListGOpenAIChatModelChoice getOpenAIChatModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OpenAiChatModelsConfigurationControllerApi;


OpenAiChatModelsConfigurationControllerApi apiInstance = new OpenAiChatModelsConfigurationControllerApi();
GOpenAIChatModelConfig body = new GOpenAIChatModelConfig(); // GOpenAIChatModelConfig | 
try {
    OperationStatusListGOpenAIChatModelChoice result = apiInstance.getOpenAIChatModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiChatModelsConfigurationControllerApi#getOpenAIChatModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIChatModelConfig**](GOpenAIChatModelConfig.md)|  |

### Return type

[**OperationStatusListGOpenAIChatModelChoice**](OperationStatusListGOpenAIChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertOpenAIChatModelConfig"></a>
# **insertOpenAIChatModelConfig**
> OperationStatusGOpenAIChatModelConfig insertOpenAIChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OpenAiChatModelsConfigurationControllerApi;


OpenAiChatModelsConfigurationControllerApi apiInstance = new OpenAiChatModelsConfigurationControllerApi();
GOpenAIChatModelConfig body = new GOpenAIChatModelConfig(); // GOpenAIChatModelConfig | 
try {
    OperationStatusGOpenAIChatModelConfig result = apiInstance.insertOpenAIChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiChatModelsConfigurationControllerApi#insertOpenAIChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIChatModelConfig**](GOpenAIChatModelConfig.md)|  |

### Return type

[**OperationStatusGOpenAIChatModelConfig**](OperationStatusGOpenAIChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateOpenAIChatModelConfig"></a>
# **updateOpenAIChatModelConfig**
> OperationStatusGOpenAIChatModelConfig updateOpenAIChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OpenAiChatModelsConfigurationControllerApi;


OpenAiChatModelsConfigurationControllerApi apiInstance = new OpenAiChatModelsConfigurationControllerApi();
GOpenAIChatModelConfig body = new GOpenAIChatModelConfig(); // GOpenAIChatModelConfig | 
try {
    OperationStatusGOpenAIChatModelConfig result = apiInstance.updateOpenAIChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiChatModelsConfigurationControllerApi#updateOpenAIChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIChatModelConfig**](GOpenAIChatModelConfig.md)|  |

### Return type

[**OperationStatusGOpenAIChatModelConfig**](OperationStatusGOpenAIChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

