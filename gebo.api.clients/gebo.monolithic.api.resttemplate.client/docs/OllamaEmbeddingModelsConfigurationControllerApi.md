# OllamaEmbeddingModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteOllamaEmbeddingModelConfig**](OllamaEmbeddingModelsConfigurationControllerApi.md#deleteOllamaEmbeddingModelConfig) | **POST** /api/admin/OllamaEmbeddingModelsConfigurationController/deleteOllamaEmbeddingModelConfig | 
[**findOllamaEmbeddingModelConfigByCode**](OllamaEmbeddingModelsConfigurationControllerApi.md#findOllamaEmbeddingModelConfigByCode) | **GET** /api/admin/OllamaEmbeddingModelsConfigurationController/findOllamaEmbeddingModelConfigByCode | 
[**getOllamaEmbeddingModels**](OllamaEmbeddingModelsConfigurationControllerApi.md#getOllamaEmbeddingModels) | **POST** /api/admin/OllamaEmbeddingModelsConfigurationController/getOllamaEmbeddingModels | 
[**insertOllamaEmbeddingModelConfig**](OllamaEmbeddingModelsConfigurationControllerApi.md#insertOllamaEmbeddingModelConfig) | **POST** /api/admin/OllamaEmbeddingModelsConfigurationController/insertOllamaEmbeddingModelConfig | 
[**updateOllamaEmbeddingModelConfig**](OllamaEmbeddingModelsConfigurationControllerApi.md#updateOllamaEmbeddingModelConfig) | **POST** /api/admin/OllamaEmbeddingModelsConfigurationController/updateOllamaEmbeddingModelConfig | 

<a name="deleteOllamaEmbeddingModelConfig"></a>
# **deleteOllamaEmbeddingModelConfig**
> OperationStatusBoolean deleteOllamaEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OllamaEmbeddingModelsConfigurationControllerApi;


OllamaEmbeddingModelsConfigurationControllerApi apiInstance = new OllamaEmbeddingModelsConfigurationControllerApi();
GOllamaEmbeddingModelConfig body = new GOllamaEmbeddingModelConfig(); // GOllamaEmbeddingModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteOllamaEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OllamaEmbeddingModelsConfigurationControllerApi#deleteOllamaEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOllamaEmbeddingModelConfig**](GOllamaEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findOllamaEmbeddingModelConfigByCode"></a>
# **findOllamaEmbeddingModelConfigByCode**
> GOllamaEmbeddingModelConfig findOllamaEmbeddingModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OllamaEmbeddingModelsConfigurationControllerApi;


OllamaEmbeddingModelsConfigurationControllerApi apiInstance = new OllamaEmbeddingModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GOllamaEmbeddingModelConfig result = apiInstance.findOllamaEmbeddingModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OllamaEmbeddingModelsConfigurationControllerApi#findOllamaEmbeddingModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GOllamaEmbeddingModelConfig**](GOllamaEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getOllamaEmbeddingModels"></a>
# **getOllamaEmbeddingModels**
> OperationStatusListGOllamaEmbeddingModelChoice getOllamaEmbeddingModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OllamaEmbeddingModelsConfigurationControllerApi;


OllamaEmbeddingModelsConfigurationControllerApi apiInstance = new OllamaEmbeddingModelsConfigurationControllerApi();
GOllamaEmbeddingModelConfig body = new GOllamaEmbeddingModelConfig(); // GOllamaEmbeddingModelConfig | 
try {
    OperationStatusListGOllamaEmbeddingModelChoice result = apiInstance.getOllamaEmbeddingModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OllamaEmbeddingModelsConfigurationControllerApi#getOllamaEmbeddingModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOllamaEmbeddingModelConfig**](GOllamaEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusListGOllamaEmbeddingModelChoice**](OperationStatusListGOllamaEmbeddingModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertOllamaEmbeddingModelConfig"></a>
# **insertOllamaEmbeddingModelConfig**
> OperationStatusGOllamaEmbeddingModelConfig insertOllamaEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OllamaEmbeddingModelsConfigurationControllerApi;


OllamaEmbeddingModelsConfigurationControllerApi apiInstance = new OllamaEmbeddingModelsConfigurationControllerApi();
GOllamaEmbeddingModelConfig body = new GOllamaEmbeddingModelConfig(); // GOllamaEmbeddingModelConfig | 
try {
    OperationStatusGOllamaEmbeddingModelConfig result = apiInstance.insertOllamaEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OllamaEmbeddingModelsConfigurationControllerApi#insertOllamaEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOllamaEmbeddingModelConfig**](GOllamaEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusGOllamaEmbeddingModelConfig**](OperationStatusGOllamaEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateOllamaEmbeddingModelConfig"></a>
# **updateOllamaEmbeddingModelConfig**
> OperationStatusGOllamaEmbeddingModelConfig updateOllamaEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OllamaEmbeddingModelsConfigurationControllerApi;


OllamaEmbeddingModelsConfigurationControllerApi apiInstance = new OllamaEmbeddingModelsConfigurationControllerApi();
GOllamaEmbeddingModelConfig body = new GOllamaEmbeddingModelConfig(); // GOllamaEmbeddingModelConfig | 
try {
    OperationStatusGOllamaEmbeddingModelConfig result = apiInstance.updateOllamaEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OllamaEmbeddingModelsConfigurationControllerApi#updateOllamaEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOllamaEmbeddingModelConfig**](GOllamaEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusGOllamaEmbeddingModelConfig**](OperationStatusGOllamaEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

