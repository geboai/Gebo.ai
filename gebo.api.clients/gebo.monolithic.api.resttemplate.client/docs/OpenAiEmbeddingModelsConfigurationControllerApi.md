# OpenAiEmbeddingModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteOpenAIEmbeddingModelConfig**](OpenAiEmbeddingModelsConfigurationControllerApi.md#deleteOpenAIEmbeddingModelConfig) | **POST** /api/admin/OpenAIEmbeddingModelsConfigurationController/deleteOpenAIEmbeddingModelConfig | 
[**findOpenAIEmbeddingModelConfigByCode**](OpenAiEmbeddingModelsConfigurationControllerApi.md#findOpenAIEmbeddingModelConfigByCode) | **GET** /api/admin/OpenAIEmbeddingModelsConfigurationController/findOpenAIEmbeddingModelConfigByCode | 
[**getOpenAIEmbeddingModels**](OpenAiEmbeddingModelsConfigurationControllerApi.md#getOpenAIEmbeddingModels) | **POST** /api/admin/OpenAIEmbeddingModelsConfigurationController/getOpenAIEmbeddingModels | 
[**insertOpenAIEmbeddingModelConfig**](OpenAiEmbeddingModelsConfigurationControllerApi.md#insertOpenAIEmbeddingModelConfig) | **POST** /api/admin/OpenAIEmbeddingModelsConfigurationController/insertOpenAIEmbeddingModelConfig | 
[**updateOpenAIEmbeddingModelConfig**](OpenAiEmbeddingModelsConfigurationControllerApi.md#updateOpenAIEmbeddingModelConfig) | **POST** /api/admin/OpenAIEmbeddingModelsConfigurationController/updateOpenAIEmbeddingModelConfig | 

<a name="deleteOpenAIEmbeddingModelConfig"></a>
# **deleteOpenAIEmbeddingModelConfig**
> OperationStatusBoolean deleteOpenAIEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OpenAiEmbeddingModelsConfigurationControllerApi;


OpenAiEmbeddingModelsConfigurationControllerApi apiInstance = new OpenAiEmbeddingModelsConfigurationControllerApi();
GOpenAIEmbeddingModelConfig body = new GOpenAIEmbeddingModelConfig(); // GOpenAIEmbeddingModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteOpenAIEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiEmbeddingModelsConfigurationControllerApi#deleteOpenAIEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIEmbeddingModelConfig**](GOpenAIEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findOpenAIEmbeddingModelConfigByCode"></a>
# **findOpenAIEmbeddingModelConfigByCode**
> GOpenAIEmbeddingModelConfig findOpenAIEmbeddingModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OpenAiEmbeddingModelsConfigurationControllerApi;


OpenAiEmbeddingModelsConfigurationControllerApi apiInstance = new OpenAiEmbeddingModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GOpenAIEmbeddingModelConfig result = apiInstance.findOpenAIEmbeddingModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiEmbeddingModelsConfigurationControllerApi#findOpenAIEmbeddingModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GOpenAIEmbeddingModelConfig**](GOpenAIEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getOpenAIEmbeddingModels"></a>
# **getOpenAIEmbeddingModels**
> OperationStatusListGOpenAIEmbeddingModelChoice getOpenAIEmbeddingModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OpenAiEmbeddingModelsConfigurationControllerApi;


OpenAiEmbeddingModelsConfigurationControllerApi apiInstance = new OpenAiEmbeddingModelsConfigurationControllerApi();
GOpenAIEmbeddingModelConfig body = new GOpenAIEmbeddingModelConfig(); // GOpenAIEmbeddingModelConfig | 
try {
    OperationStatusListGOpenAIEmbeddingModelChoice result = apiInstance.getOpenAIEmbeddingModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiEmbeddingModelsConfigurationControllerApi#getOpenAIEmbeddingModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIEmbeddingModelConfig**](GOpenAIEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusListGOpenAIEmbeddingModelChoice**](OperationStatusListGOpenAIEmbeddingModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertOpenAIEmbeddingModelConfig"></a>
# **insertOpenAIEmbeddingModelConfig**
> OperationStatusGOpenAIEmbeddingModelConfig insertOpenAIEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OpenAiEmbeddingModelsConfigurationControllerApi;


OpenAiEmbeddingModelsConfigurationControllerApi apiInstance = new OpenAiEmbeddingModelsConfigurationControllerApi();
GOpenAIEmbeddingModelConfig body = new GOpenAIEmbeddingModelConfig(); // GOpenAIEmbeddingModelConfig | 
try {
    OperationStatusGOpenAIEmbeddingModelConfig result = apiInstance.insertOpenAIEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiEmbeddingModelsConfigurationControllerApi#insertOpenAIEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIEmbeddingModelConfig**](GOpenAIEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusGOpenAIEmbeddingModelConfig**](OperationStatusGOpenAIEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateOpenAIEmbeddingModelConfig"></a>
# **updateOpenAIEmbeddingModelConfig**
> OperationStatusGOpenAIEmbeddingModelConfig updateOpenAIEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OpenAiEmbeddingModelsConfigurationControllerApi;


OpenAiEmbeddingModelsConfigurationControllerApi apiInstance = new OpenAiEmbeddingModelsConfigurationControllerApi();
GOpenAIEmbeddingModelConfig body = new GOpenAIEmbeddingModelConfig(); // GOpenAIEmbeddingModelConfig | 
try {
    OperationStatusGOpenAIEmbeddingModelConfig result = apiInstance.updateOpenAIEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiEmbeddingModelsConfigurationControllerApi#updateOpenAIEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIEmbeddingModelConfig**](GOpenAIEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusGOpenAIEmbeddingModelConfig**](OperationStatusGOpenAIEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

