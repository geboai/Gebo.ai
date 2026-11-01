# MistralAiEmbeddingModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteMistralAIEmbeddingModelConfig**](MistralAiEmbeddingModelsConfigurationControllerApi.md#deleteMistralAIEmbeddingModelConfig) | **POST** /api/admin/MistralAIEmbeddingModelsConfigurationController/deleteMistralAIEmbeddingModelConfig | 
[**findMistralAIEmbeddingModelConfigByCode**](MistralAiEmbeddingModelsConfigurationControllerApi.md#findMistralAIEmbeddingModelConfigByCode) | **GET** /api/admin/MistralAIEmbeddingModelsConfigurationController/findMistralAIEmbeddingModelConfigByCode | 
[**getMistralAIEmbeddingModels**](MistralAiEmbeddingModelsConfigurationControllerApi.md#getMistralAIEmbeddingModels) | **POST** /api/admin/MistralAIEmbeddingModelsConfigurationController/getMistralAIEmbeddingModels | 
[**insertMistralAIEmbeddingModelConfig**](MistralAiEmbeddingModelsConfigurationControllerApi.md#insertMistralAIEmbeddingModelConfig) | **POST** /api/admin/MistralAIEmbeddingModelsConfigurationController/insertMistralAIEmbeddingModelConfig | 
[**updateMistralAIEmbeddingModelConfig**](MistralAiEmbeddingModelsConfigurationControllerApi.md#updateMistralAIEmbeddingModelConfig) | **POST** /api/admin/MistralAIEmbeddingModelsConfigurationController/updateMistralAIEmbeddingModelConfig | 

<a name="deleteMistralAIEmbeddingModelConfig"></a>
# **deleteMistralAIEmbeddingModelConfig**
> OperationStatusBoolean deleteMistralAIEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.MistralAiEmbeddingModelsConfigurationControllerApi;


MistralAiEmbeddingModelsConfigurationControllerApi apiInstance = new MistralAiEmbeddingModelsConfigurationControllerApi();
GMistralEmbeddingModelConfig body = new GMistralEmbeddingModelConfig(); // GMistralEmbeddingModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteMistralAIEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MistralAiEmbeddingModelsConfigurationControllerApi#deleteMistralAIEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GMistralEmbeddingModelConfig**](GMistralEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findMistralAIEmbeddingModelConfigByCode"></a>
# **findMistralAIEmbeddingModelConfigByCode**
> GMistralEmbeddingModelConfig findMistralAIEmbeddingModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.MistralAiEmbeddingModelsConfigurationControllerApi;


MistralAiEmbeddingModelsConfigurationControllerApi apiInstance = new MistralAiEmbeddingModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GMistralEmbeddingModelConfig result = apiInstance.findMistralAIEmbeddingModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MistralAiEmbeddingModelsConfigurationControllerApi#findMistralAIEmbeddingModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GMistralEmbeddingModelConfig**](GMistralEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMistralAIEmbeddingModels"></a>
# **getMistralAIEmbeddingModels**
> OperationStatusListGMistralEmbeddingModelChoice getMistralAIEmbeddingModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.MistralAiEmbeddingModelsConfigurationControllerApi;


MistralAiEmbeddingModelsConfigurationControllerApi apiInstance = new MistralAiEmbeddingModelsConfigurationControllerApi();
GMistralEmbeddingModelConfig body = new GMistralEmbeddingModelConfig(); // GMistralEmbeddingModelConfig | 
try {
    OperationStatusListGMistralEmbeddingModelChoice result = apiInstance.getMistralAIEmbeddingModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MistralAiEmbeddingModelsConfigurationControllerApi#getMistralAIEmbeddingModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GMistralEmbeddingModelConfig**](GMistralEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusListGMistralEmbeddingModelChoice**](OperationStatusListGMistralEmbeddingModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertMistralAIEmbeddingModelConfig"></a>
# **insertMistralAIEmbeddingModelConfig**
> OperationStatusGMistralEmbeddingModelConfig insertMistralAIEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.MistralAiEmbeddingModelsConfigurationControllerApi;


MistralAiEmbeddingModelsConfigurationControllerApi apiInstance = new MistralAiEmbeddingModelsConfigurationControllerApi();
GMistralEmbeddingModelConfig body = new GMistralEmbeddingModelConfig(); // GMistralEmbeddingModelConfig | 
try {
    OperationStatusGMistralEmbeddingModelConfig result = apiInstance.insertMistralAIEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MistralAiEmbeddingModelsConfigurationControllerApi#insertMistralAIEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GMistralEmbeddingModelConfig**](GMistralEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusGMistralEmbeddingModelConfig**](OperationStatusGMistralEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateMistralAIEmbeddingModelConfig"></a>
# **updateMistralAIEmbeddingModelConfig**
> OperationStatusGMistralEmbeddingModelConfig updateMistralAIEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.MistralAiEmbeddingModelsConfigurationControllerApi;


MistralAiEmbeddingModelsConfigurationControllerApi apiInstance = new MistralAiEmbeddingModelsConfigurationControllerApi();
GMistralEmbeddingModelConfig body = new GMistralEmbeddingModelConfig(); // GMistralEmbeddingModelConfig | 
try {
    OperationStatusGMistralEmbeddingModelConfig result = apiInstance.updateMistralAIEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MistralAiEmbeddingModelsConfigurationControllerApi#updateMistralAIEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GMistralEmbeddingModelConfig**](GMistralEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusGMistralEmbeddingModelConfig**](OperationStatusGMistralEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

