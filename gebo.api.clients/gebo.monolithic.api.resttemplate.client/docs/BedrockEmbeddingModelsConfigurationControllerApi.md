# BedrockEmbeddingModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteBedrockEmbeddingModelConfig**](BedrockEmbeddingModelsConfigurationControllerApi.md#deleteBedrockEmbeddingModelConfig) | **POST** /api/admin/BedrockEmbeddingModelsConfigurationController/deleteBedrockEmbeddingModelConfig | 
[**findBedrockEmbeddingModelConfigByCode**](BedrockEmbeddingModelsConfigurationControllerApi.md#findBedrockEmbeddingModelConfigByCode) | **GET** /api/admin/BedrockEmbeddingModelsConfigurationController/findBedrockEmbeddingModelConfigByCode | 
[**getBedrockEmbeddingModels**](BedrockEmbeddingModelsConfigurationControllerApi.md#getBedrockEmbeddingModels) | **POST** /api/admin/BedrockEmbeddingModelsConfigurationController/getBedrockEmbeddingModels | 
[**insertBedrockEmbeddingModelConfig**](BedrockEmbeddingModelsConfigurationControllerApi.md#insertBedrockEmbeddingModelConfig) | **POST** /api/admin/BedrockEmbeddingModelsConfigurationController/insertBedrockEmbeddingModelConfig | 
[**updateBedrockEmbeddingModelConfig**](BedrockEmbeddingModelsConfigurationControllerApi.md#updateBedrockEmbeddingModelConfig) | **POST** /api/admin/BedrockEmbeddingModelsConfigurationController/updateBedrockEmbeddingModelConfig | 

<a name="deleteBedrockEmbeddingModelConfig"></a>
# **deleteBedrockEmbeddingModelConfig**
> OperationStatusBoolean deleteBedrockEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockEmbeddingModelsConfigurationControllerApi;


BedrockEmbeddingModelsConfigurationControllerApi apiInstance = new BedrockEmbeddingModelsConfigurationControllerApi();
GBedrockEmbeddingModelConfig body = new GBedrockEmbeddingModelConfig(); // GBedrockEmbeddingModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteBedrockEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockEmbeddingModelsConfigurationControllerApi#deleteBedrockEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockEmbeddingModelConfig**](GBedrockEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findBedrockEmbeddingModelConfigByCode"></a>
# **findBedrockEmbeddingModelConfigByCode**
> GBedrockEmbeddingModelConfig findBedrockEmbeddingModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockEmbeddingModelsConfigurationControllerApi;


BedrockEmbeddingModelsConfigurationControllerApi apiInstance = new BedrockEmbeddingModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GBedrockEmbeddingModelConfig result = apiInstance.findBedrockEmbeddingModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockEmbeddingModelsConfigurationControllerApi#findBedrockEmbeddingModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GBedrockEmbeddingModelConfig**](GBedrockEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBedrockEmbeddingModels"></a>
# **getBedrockEmbeddingModels**
> OperationStatusListGBedrockEmbeddingModelChoice getBedrockEmbeddingModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockEmbeddingModelsConfigurationControllerApi;


BedrockEmbeddingModelsConfigurationControllerApi apiInstance = new BedrockEmbeddingModelsConfigurationControllerApi();
GBedrockEmbeddingModelConfig body = new GBedrockEmbeddingModelConfig(); // GBedrockEmbeddingModelConfig | 
try {
    OperationStatusListGBedrockEmbeddingModelChoice result = apiInstance.getBedrockEmbeddingModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockEmbeddingModelsConfigurationControllerApi#getBedrockEmbeddingModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockEmbeddingModelConfig**](GBedrockEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusListGBedrockEmbeddingModelChoice**](OperationStatusListGBedrockEmbeddingModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertBedrockEmbeddingModelConfig"></a>
# **insertBedrockEmbeddingModelConfig**
> OperationStatusGBedrockEmbeddingModelConfig insertBedrockEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockEmbeddingModelsConfigurationControllerApi;


BedrockEmbeddingModelsConfigurationControllerApi apiInstance = new BedrockEmbeddingModelsConfigurationControllerApi();
GBedrockEmbeddingModelConfig body = new GBedrockEmbeddingModelConfig(); // GBedrockEmbeddingModelConfig | 
try {
    OperationStatusGBedrockEmbeddingModelConfig result = apiInstance.insertBedrockEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockEmbeddingModelsConfigurationControllerApi#insertBedrockEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockEmbeddingModelConfig**](GBedrockEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusGBedrockEmbeddingModelConfig**](OperationStatusGBedrockEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateBedrockEmbeddingModelConfig"></a>
# **updateBedrockEmbeddingModelConfig**
> OperationStatusGBedrockEmbeddingModelConfig updateBedrockEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockEmbeddingModelsConfigurationControllerApi;


BedrockEmbeddingModelsConfigurationControllerApi apiInstance = new BedrockEmbeddingModelsConfigurationControllerApi();
GBedrockEmbeddingModelConfig body = new GBedrockEmbeddingModelConfig(); // GBedrockEmbeddingModelConfig | 
try {
    OperationStatusGBedrockEmbeddingModelConfig result = apiInstance.updateBedrockEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockEmbeddingModelsConfigurationControllerApi#updateBedrockEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockEmbeddingModelConfig**](GBedrockEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusGBedrockEmbeddingModelConfig**](OperationStatusGBedrockEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

