# OpenAiTextToSpeechModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteOpenAITextToSpeechModelConfig**](OpenAiTextToSpeechModelsConfigurationControllerApi.md#deleteOpenAITextToSpeechModelConfig) | **POST** /api/admin/OpenAITextToSpeechModelsConfigurationController/deleteOpenAITextToSpeechModelConfig | 
[**findOpenAITextToSpeechModelConfigByCode**](OpenAiTextToSpeechModelsConfigurationControllerApi.md#findOpenAITextToSpeechModelConfigByCode) | **GET** /api/admin/OpenAITextToSpeechModelsConfigurationController/findOpenAITextToSpeechModelConfigByCode | 
[**getOpenAITextToSpeechModels**](OpenAiTextToSpeechModelsConfigurationControllerApi.md#getOpenAITextToSpeechModels) | **POST** /api/admin/OpenAITextToSpeechModelsConfigurationController/getOpenAITextToSpeechModels | 
[**insertOpenAITextToSpeechModelConfig**](OpenAiTextToSpeechModelsConfigurationControllerApi.md#insertOpenAITextToSpeechModelConfig) | **POST** /api/admin/OpenAITextToSpeechModelsConfigurationController/insertOpenAITextToSpeechModelConfig | 
[**updateOpenAITextToSpeechModelConfig**](OpenAiTextToSpeechModelsConfigurationControllerApi.md#updateOpenAITextToSpeechModelConfig) | **POST** /api/admin/OpenAITextToSpeechModelsConfigurationController/updateOpenAITextToSpeechModelConfig | 

<a name="deleteOpenAITextToSpeechModelConfig"></a>
# **deleteOpenAITextToSpeechModelConfig**
> OperationStatusBoolean deleteOpenAITextToSpeechModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OpenAiTextToSpeechModelsConfigurationControllerApi;


OpenAiTextToSpeechModelsConfigurationControllerApi apiInstance = new OpenAiTextToSpeechModelsConfigurationControllerApi();
GOpenAITextToSpeechModelConfig body = new GOpenAITextToSpeechModelConfig(); // GOpenAITextToSpeechModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteOpenAITextToSpeechModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiTextToSpeechModelsConfigurationControllerApi#deleteOpenAITextToSpeechModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAITextToSpeechModelConfig**](GOpenAITextToSpeechModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findOpenAITextToSpeechModelConfigByCode"></a>
# **findOpenAITextToSpeechModelConfigByCode**
> GOpenAITextToSpeechModelConfig findOpenAITextToSpeechModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OpenAiTextToSpeechModelsConfigurationControllerApi;


OpenAiTextToSpeechModelsConfigurationControllerApi apiInstance = new OpenAiTextToSpeechModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GOpenAITextToSpeechModelConfig result = apiInstance.findOpenAITextToSpeechModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiTextToSpeechModelsConfigurationControllerApi#findOpenAITextToSpeechModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GOpenAITextToSpeechModelConfig**](GOpenAITextToSpeechModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getOpenAITextToSpeechModels"></a>
# **getOpenAITextToSpeechModels**
> OperationStatusListGOpenAITextToSpeechModelChoice getOpenAITextToSpeechModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OpenAiTextToSpeechModelsConfigurationControllerApi;


OpenAiTextToSpeechModelsConfigurationControllerApi apiInstance = new OpenAiTextToSpeechModelsConfigurationControllerApi();
GOpenAITextToSpeechModelConfig body = new GOpenAITextToSpeechModelConfig(); // GOpenAITextToSpeechModelConfig | 
try {
    OperationStatusListGOpenAITextToSpeechModelChoice result = apiInstance.getOpenAITextToSpeechModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiTextToSpeechModelsConfigurationControllerApi#getOpenAITextToSpeechModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAITextToSpeechModelConfig**](GOpenAITextToSpeechModelConfig.md)|  |

### Return type

[**OperationStatusListGOpenAITextToSpeechModelChoice**](OperationStatusListGOpenAITextToSpeechModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertOpenAITextToSpeechModelConfig"></a>
# **insertOpenAITextToSpeechModelConfig**
> OperationStatusGOpenAITextToSpeechModelConfig insertOpenAITextToSpeechModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OpenAiTextToSpeechModelsConfigurationControllerApi;


OpenAiTextToSpeechModelsConfigurationControllerApi apiInstance = new OpenAiTextToSpeechModelsConfigurationControllerApi();
GOpenAITextToSpeechModelConfig body = new GOpenAITextToSpeechModelConfig(); // GOpenAITextToSpeechModelConfig | 
try {
    OperationStatusGOpenAITextToSpeechModelConfig result = apiInstance.insertOpenAITextToSpeechModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiTextToSpeechModelsConfigurationControllerApi#insertOpenAITextToSpeechModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAITextToSpeechModelConfig**](GOpenAITextToSpeechModelConfig.md)|  |

### Return type

[**OperationStatusGOpenAITextToSpeechModelConfig**](OperationStatusGOpenAITextToSpeechModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateOpenAITextToSpeechModelConfig"></a>
# **updateOpenAITextToSpeechModelConfig**
> OperationStatusGOpenAITextToSpeechModelConfig updateOpenAITextToSpeechModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OpenAiTextToSpeechModelsConfigurationControllerApi;


OpenAiTextToSpeechModelsConfigurationControllerApi apiInstance = new OpenAiTextToSpeechModelsConfigurationControllerApi();
GOpenAITextToSpeechModelConfig body = new GOpenAITextToSpeechModelConfig(); // GOpenAITextToSpeechModelConfig | 
try {
    OperationStatusGOpenAITextToSpeechModelConfig result = apiInstance.updateOpenAITextToSpeechModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiTextToSpeechModelsConfigurationControllerApi#updateOpenAITextToSpeechModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAITextToSpeechModelConfig**](GOpenAITextToSpeechModelConfig.md)|  |

### Return type

[**OperationStatusGOpenAITextToSpeechModelConfig**](OperationStatusGOpenAITextToSpeechModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

