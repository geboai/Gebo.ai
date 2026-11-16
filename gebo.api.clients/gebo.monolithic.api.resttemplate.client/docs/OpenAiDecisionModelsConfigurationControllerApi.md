# OpenAiDecisionModelsConfigurationControllerApi

All URIs are relative to *http://localhost:13999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteOpenAIDecisionModelConfig**](OpenAiDecisionModelsConfigurationControllerApi.md#deleteOpenAIDecisionModelConfig) | **POST** /api/admin/OpenAIDecisionModelsConfigurationController/deleteOpenAIDecisionModelConfig | 
[**findOpenAIDecisionModelConfigByCode**](OpenAiDecisionModelsConfigurationControllerApi.md#findOpenAIDecisionModelConfigByCode) | **GET** /api/admin/OpenAIDecisionModelsConfigurationController/findOpenAIDecisionModelConfigByCode | 
[**getOpenAIDecisionModels**](OpenAiDecisionModelsConfigurationControllerApi.md#getOpenAIDecisionModels) | **POST** /api/admin/OpenAIDecisionModelsConfigurationController/getOpenAIDecisionModels | 
[**insertOpenAIDecisionModelConfig**](OpenAiDecisionModelsConfigurationControllerApi.md#insertOpenAIDecisionModelConfig) | **POST** /api/admin/OpenAIDecisionModelsConfigurationController/insertOpenAIDecisionModelConfig | 
[**updateOpenAIDecisionModelConfig**](OpenAiDecisionModelsConfigurationControllerApi.md#updateOpenAIDecisionModelConfig) | **POST** /api/admin/OpenAIDecisionModelsConfigurationController/updateOpenAIDecisionModelConfig | 

<a name="deleteOpenAIDecisionModelConfig"></a>
# **deleteOpenAIDecisionModelConfig**
> OperationStatusBoolean deleteOpenAIDecisionModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OpenAiDecisionModelsConfigurationControllerApi;


OpenAiDecisionModelsConfigurationControllerApi apiInstance = new OpenAiDecisionModelsConfigurationControllerApi();
GOpenAIDecisionModelConfig body = new GOpenAIDecisionModelConfig(); // GOpenAIDecisionModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteOpenAIDecisionModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiDecisionModelsConfigurationControllerApi#deleteOpenAIDecisionModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIDecisionModelConfig**](GOpenAIDecisionModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findOpenAIDecisionModelConfigByCode"></a>
# **findOpenAIDecisionModelConfigByCode**
> GOpenAIDecisionModelConfig findOpenAIDecisionModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OpenAiDecisionModelsConfigurationControllerApi;


OpenAiDecisionModelsConfigurationControllerApi apiInstance = new OpenAiDecisionModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GOpenAIDecisionModelConfig result = apiInstance.findOpenAIDecisionModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiDecisionModelsConfigurationControllerApi#findOpenAIDecisionModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GOpenAIDecisionModelConfig**](GOpenAIDecisionModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getOpenAIDecisionModels"></a>
# **getOpenAIDecisionModels**
> OperationStatusListGOpenAIDecisionModelChoice getOpenAIDecisionModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OpenAiDecisionModelsConfigurationControllerApi;


OpenAiDecisionModelsConfigurationControllerApi apiInstance = new OpenAiDecisionModelsConfigurationControllerApi();
GOpenAIDecisionModelConfig body = new GOpenAIDecisionModelConfig(); // GOpenAIDecisionModelConfig | 
try {
    OperationStatusListGOpenAIDecisionModelChoice result = apiInstance.getOpenAIDecisionModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiDecisionModelsConfigurationControllerApi#getOpenAIDecisionModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIDecisionModelConfig**](GOpenAIDecisionModelConfig.md)|  |

### Return type

[**OperationStatusListGOpenAIDecisionModelChoice**](OperationStatusListGOpenAIDecisionModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertOpenAIDecisionModelConfig"></a>
# **insertOpenAIDecisionModelConfig**
> OperationStatusGOpenAIDecisionModelConfig insertOpenAIDecisionModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OpenAiDecisionModelsConfigurationControllerApi;


OpenAiDecisionModelsConfigurationControllerApi apiInstance = new OpenAiDecisionModelsConfigurationControllerApi();
GOpenAIDecisionModelConfig body = new GOpenAIDecisionModelConfig(); // GOpenAIDecisionModelConfig | 
try {
    OperationStatusGOpenAIDecisionModelConfig result = apiInstance.insertOpenAIDecisionModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiDecisionModelsConfigurationControllerApi#insertOpenAIDecisionModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIDecisionModelConfig**](GOpenAIDecisionModelConfig.md)|  |

### Return type

[**OperationStatusGOpenAIDecisionModelConfig**](OperationStatusGOpenAIDecisionModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateOpenAIDecisionModelConfig"></a>
# **updateOpenAIDecisionModelConfig**
> OperationStatusGOpenAIDecisionModelConfig updateOpenAIDecisionModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OpenAiDecisionModelsConfigurationControllerApi;


OpenAiDecisionModelsConfigurationControllerApi apiInstance = new OpenAiDecisionModelsConfigurationControllerApi();
GOpenAIDecisionModelConfig body = new GOpenAIDecisionModelConfig(); // GOpenAIDecisionModelConfig | 
try {
    OperationStatusGOpenAIDecisionModelConfig result = apiInstance.updateOpenAIDecisionModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiDecisionModelsConfigurationControllerApi#updateOpenAIDecisionModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIDecisionModelConfig**](GOpenAIDecisionModelConfig.md)|  |

### Return type

[**OperationStatusGOpenAIDecisionModelConfig**](OperationStatusGOpenAIDecisionModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

