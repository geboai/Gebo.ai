# AzureOpenAiEmbeddingModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteAzureOpenAIEmbeddingModelConfig**](AzureOpenAiEmbeddingModelsConfigurationControllerApi.md#deleteAzureOpenAIEmbeddingModelConfig) | **POST** /api/admin/AzureOpenAIEmbeddingModelsConfigurationController/deleteAzureOpenAIEmbeddingModelConfig | 
[**findAzureOpenAIEmbeddingModelConfigByCode**](AzureOpenAiEmbeddingModelsConfigurationControllerApi.md#findAzureOpenAIEmbeddingModelConfigByCode) | **GET** /api/admin/AzureOpenAIEmbeddingModelsConfigurationController/findAzureOpenAIEmbeddingModelConfigByCode | 
[**getAzureOpenAIEmbeddingModels**](AzureOpenAiEmbeddingModelsConfigurationControllerApi.md#getAzureOpenAIEmbeddingModels) | **POST** /api/admin/AzureOpenAIEmbeddingModelsConfigurationController/getAzureOpenAIEmbeddingModels | 
[**insertAzureOpenAIEmbeddingModelConfig**](AzureOpenAiEmbeddingModelsConfigurationControllerApi.md#insertAzureOpenAIEmbeddingModelConfig) | **POST** /api/admin/AzureOpenAIEmbeddingModelsConfigurationController/insertAzureOpenAIEmbeddingModelConfig | 
[**updateAzureOpenAIEmbeddingModelConfig**](AzureOpenAiEmbeddingModelsConfigurationControllerApi.md#updateAzureOpenAIEmbeddingModelConfig) | **POST** /api/admin/AzureOpenAIEmbeddingModelsConfigurationController/updateAzureOpenAIEmbeddingModelConfig | 

<a name="deleteAzureOpenAIEmbeddingModelConfig"></a>
# **deleteAzureOpenAIEmbeddingModelConfig**
> OperationStatusBoolean deleteAzureOpenAIEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AzureOpenAiEmbeddingModelsConfigurationControllerApi;


AzureOpenAiEmbeddingModelsConfigurationControllerApi apiInstance = new AzureOpenAiEmbeddingModelsConfigurationControllerApi();
GAzureOpenAIEmbeddingModelConfig body = new GAzureOpenAIEmbeddingModelConfig(); // GAzureOpenAIEmbeddingModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteAzureOpenAIEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AzureOpenAiEmbeddingModelsConfigurationControllerApi#deleteAzureOpenAIEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAzureOpenAIEmbeddingModelConfig**](GAzureOpenAIEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findAzureOpenAIEmbeddingModelConfigByCode"></a>
# **findAzureOpenAIEmbeddingModelConfigByCode**
> GAzureOpenAIEmbeddingModelConfig findAzureOpenAIEmbeddingModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AzureOpenAiEmbeddingModelsConfigurationControllerApi;


AzureOpenAiEmbeddingModelsConfigurationControllerApi apiInstance = new AzureOpenAiEmbeddingModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GAzureOpenAIEmbeddingModelConfig result = apiInstance.findAzureOpenAIEmbeddingModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AzureOpenAiEmbeddingModelsConfigurationControllerApi#findAzureOpenAIEmbeddingModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GAzureOpenAIEmbeddingModelConfig**](GAzureOpenAIEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAzureOpenAIEmbeddingModels"></a>
# **getAzureOpenAIEmbeddingModels**
> OperationStatusListGAzureOpenAIEmbeddingModelChoice getAzureOpenAIEmbeddingModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AzureOpenAiEmbeddingModelsConfigurationControllerApi;


AzureOpenAiEmbeddingModelsConfigurationControllerApi apiInstance = new AzureOpenAiEmbeddingModelsConfigurationControllerApi();
GAzureOpenAIEmbeddingModelConfig body = new GAzureOpenAIEmbeddingModelConfig(); // GAzureOpenAIEmbeddingModelConfig | 
try {
    OperationStatusListGAzureOpenAIEmbeddingModelChoice result = apiInstance.getAzureOpenAIEmbeddingModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AzureOpenAiEmbeddingModelsConfigurationControllerApi#getAzureOpenAIEmbeddingModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAzureOpenAIEmbeddingModelConfig**](GAzureOpenAIEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusListGAzureOpenAIEmbeddingModelChoice**](OperationStatusListGAzureOpenAIEmbeddingModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertAzureOpenAIEmbeddingModelConfig"></a>
# **insertAzureOpenAIEmbeddingModelConfig**
> OperationStatusGAzureOpenAIEmbeddingModelConfig insertAzureOpenAIEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AzureOpenAiEmbeddingModelsConfigurationControllerApi;


AzureOpenAiEmbeddingModelsConfigurationControllerApi apiInstance = new AzureOpenAiEmbeddingModelsConfigurationControllerApi();
GAzureOpenAIEmbeddingModelConfig body = new GAzureOpenAIEmbeddingModelConfig(); // GAzureOpenAIEmbeddingModelConfig | 
try {
    OperationStatusGAzureOpenAIEmbeddingModelConfig result = apiInstance.insertAzureOpenAIEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AzureOpenAiEmbeddingModelsConfigurationControllerApi#insertAzureOpenAIEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAzureOpenAIEmbeddingModelConfig**](GAzureOpenAIEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusGAzureOpenAIEmbeddingModelConfig**](OperationStatusGAzureOpenAIEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateAzureOpenAIEmbeddingModelConfig"></a>
# **updateAzureOpenAIEmbeddingModelConfig**
> OperationStatusGAzureOpenAIEmbeddingModelConfig updateAzureOpenAIEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AzureOpenAiEmbeddingModelsConfigurationControllerApi;


AzureOpenAiEmbeddingModelsConfigurationControllerApi apiInstance = new AzureOpenAiEmbeddingModelsConfigurationControllerApi();
GAzureOpenAIEmbeddingModelConfig body = new GAzureOpenAIEmbeddingModelConfig(); // GAzureOpenAIEmbeddingModelConfig | 
try {
    OperationStatusGAzureOpenAIEmbeddingModelConfig result = apiInstance.updateAzureOpenAIEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AzureOpenAiEmbeddingModelsConfigurationControllerApi#updateAzureOpenAIEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAzureOpenAIEmbeddingModelConfig**](GAzureOpenAIEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusGAzureOpenAIEmbeddingModelConfig**](OperationStatusGAzureOpenAIEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

