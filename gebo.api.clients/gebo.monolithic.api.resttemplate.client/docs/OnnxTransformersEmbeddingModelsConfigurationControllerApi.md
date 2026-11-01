# OnnxTransformersEmbeddingModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteONNXTransformersEmbeddingModelConfig**](OnnxTransformersEmbeddingModelsConfigurationControllerApi.md#deleteONNXTransformersEmbeddingModelConfig) | **POST** /api/admin/ONNXTransformersEmbeddingModelsConfigurationController/deleteONNXTransformersEmbeddingModelConfig | 
[**findONNXTransformersEmbeddingModelConfigByCode**](OnnxTransformersEmbeddingModelsConfigurationControllerApi.md#findONNXTransformersEmbeddingModelConfigByCode) | **GET** /api/admin/ONNXTransformersEmbeddingModelsConfigurationController/findONNXTransformersEmbeddingModelConfigByCode | 
[**getONNXTransformersEmbeddingModels**](OnnxTransformersEmbeddingModelsConfigurationControllerApi.md#getONNXTransformersEmbeddingModels) | **POST** /api/admin/ONNXTransformersEmbeddingModelsConfigurationController/getONNXTransformersEmbeddingModels | 
[**insertONNXTransformersEmbeddingModelConfig**](OnnxTransformersEmbeddingModelsConfigurationControllerApi.md#insertONNXTransformersEmbeddingModelConfig) | **POST** /api/admin/ONNXTransformersEmbeddingModelsConfigurationController/insertONNXTransformersEmbeddingModelConfig | 
[**updateONNXTransformersEmbeddingModelConfig**](OnnxTransformersEmbeddingModelsConfigurationControllerApi.md#updateONNXTransformersEmbeddingModelConfig) | **POST** /api/admin/ONNXTransformersEmbeddingModelsConfigurationController/updateONNXTransformersEmbeddingModelConfig | 

<a name="deleteONNXTransformersEmbeddingModelConfig"></a>
# **deleteONNXTransformersEmbeddingModelConfig**
> OperationStatusBoolean deleteONNXTransformersEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OnnxTransformersEmbeddingModelsConfigurationControllerApi;


OnnxTransformersEmbeddingModelsConfigurationControllerApi apiInstance = new OnnxTransformersEmbeddingModelsConfigurationControllerApi();
GONNXTransformersEmbeddingModelConfig body = new GONNXTransformersEmbeddingModelConfig(); // GONNXTransformersEmbeddingModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteONNXTransformersEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OnnxTransformersEmbeddingModelsConfigurationControllerApi#deleteONNXTransformersEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GONNXTransformersEmbeddingModelConfig**](GONNXTransformersEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findONNXTransformersEmbeddingModelConfigByCode"></a>
# **findONNXTransformersEmbeddingModelConfigByCode**
> GONNXTransformersEmbeddingModelConfig findONNXTransformersEmbeddingModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OnnxTransformersEmbeddingModelsConfigurationControllerApi;


OnnxTransformersEmbeddingModelsConfigurationControllerApi apiInstance = new OnnxTransformersEmbeddingModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GONNXTransformersEmbeddingModelConfig result = apiInstance.findONNXTransformersEmbeddingModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OnnxTransformersEmbeddingModelsConfigurationControllerApi#findONNXTransformersEmbeddingModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GONNXTransformersEmbeddingModelConfig**](GONNXTransformersEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getONNXTransformersEmbeddingModels"></a>
# **getONNXTransformersEmbeddingModels**
> OperationStatusListGONNXTransformersEmbeddingModelChoice getONNXTransformersEmbeddingModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OnnxTransformersEmbeddingModelsConfigurationControllerApi;


OnnxTransformersEmbeddingModelsConfigurationControllerApi apiInstance = new OnnxTransformersEmbeddingModelsConfigurationControllerApi();
GONNXTransformersEmbeddingModelConfig body = new GONNXTransformersEmbeddingModelConfig(); // GONNXTransformersEmbeddingModelConfig | 
try {
    OperationStatusListGONNXTransformersEmbeddingModelChoice result = apiInstance.getONNXTransformersEmbeddingModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OnnxTransformersEmbeddingModelsConfigurationControllerApi#getONNXTransformersEmbeddingModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GONNXTransformersEmbeddingModelConfig**](GONNXTransformersEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusListGONNXTransformersEmbeddingModelChoice**](OperationStatusListGONNXTransformersEmbeddingModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertONNXTransformersEmbeddingModelConfig"></a>
# **insertONNXTransformersEmbeddingModelConfig**
> OperationStatusGONNXTransformersEmbeddingModelConfig insertONNXTransformersEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OnnxTransformersEmbeddingModelsConfigurationControllerApi;


OnnxTransformersEmbeddingModelsConfigurationControllerApi apiInstance = new OnnxTransformersEmbeddingModelsConfigurationControllerApi();
GONNXTransformersEmbeddingModelConfig body = new GONNXTransformersEmbeddingModelConfig(); // GONNXTransformersEmbeddingModelConfig | 
try {
    OperationStatusGONNXTransformersEmbeddingModelConfig result = apiInstance.insertONNXTransformersEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OnnxTransformersEmbeddingModelsConfigurationControllerApi#insertONNXTransformersEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GONNXTransformersEmbeddingModelConfig**](GONNXTransformersEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusGONNXTransformersEmbeddingModelConfig**](OperationStatusGONNXTransformersEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateONNXTransformersEmbeddingModelConfig"></a>
# **updateONNXTransformersEmbeddingModelConfig**
> OperationStatusGONNXTransformersEmbeddingModelConfig updateONNXTransformersEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OnnxTransformersEmbeddingModelsConfigurationControllerApi;


OnnxTransformersEmbeddingModelsConfigurationControllerApi apiInstance = new OnnxTransformersEmbeddingModelsConfigurationControllerApi();
GONNXTransformersEmbeddingModelConfig body = new GONNXTransformersEmbeddingModelConfig(); // GONNXTransformersEmbeddingModelConfig | 
try {
    OperationStatusGONNXTransformersEmbeddingModelConfig result = apiInstance.updateONNXTransformersEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OnnxTransformersEmbeddingModelsConfigurationControllerApi#updateONNXTransformersEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GONNXTransformersEmbeddingModelConfig**](GONNXTransformersEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusGONNXTransformersEmbeddingModelConfig**](OperationStatusGONNXTransformersEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

