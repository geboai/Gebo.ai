# GoogleVertexEmbeddingModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGoogleVertexEmbeddingModelConfig**](GoogleVertexEmbeddingModelsConfigurationControllerApi.md#deleteGoogleVertexEmbeddingModelConfig) | **POST** /api/admin/GoogleVertexEmbeddingModelsConfigurationController/deleteGoogleVertexEmbeddingModelConfig | 
[**findGoogleVertexEmbeddingModelConfigByCode**](GoogleVertexEmbeddingModelsConfigurationControllerApi.md#findGoogleVertexEmbeddingModelConfigByCode) | **GET** /api/admin/GoogleVertexEmbeddingModelsConfigurationController/findGoogleVertexEmbeddingModelConfigByCode | 
[**getGoogleVertexEmbeddingModels**](GoogleVertexEmbeddingModelsConfigurationControllerApi.md#getGoogleVertexEmbeddingModels) | **POST** /api/admin/GoogleVertexEmbeddingModelsConfigurationController/getGoogleVertexEmbeddingModels | 
[**insertGoogleVertexEmbeddingModelConfig**](GoogleVertexEmbeddingModelsConfigurationControllerApi.md#insertGoogleVertexEmbeddingModelConfig) | **POST** /api/admin/GoogleVertexEmbeddingModelsConfigurationController/insertGoogleVertexEmbeddingModelConfig | 
[**updateGoogleVertexEmbeddingModelConfig**](GoogleVertexEmbeddingModelsConfigurationControllerApi.md#updateGoogleVertexEmbeddingModelConfig) | **POST** /api/admin/GoogleVertexEmbeddingModelsConfigurationController/updateGoogleVertexEmbeddingModelConfig | 

<a name="deleteGoogleVertexEmbeddingModelConfig"></a>
# **deleteGoogleVertexEmbeddingModelConfig**
> OperationStatusBoolean deleteGoogleVertexEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleVertexEmbeddingModelsConfigurationControllerApi;


GoogleVertexEmbeddingModelsConfigurationControllerApi apiInstance = new GoogleVertexEmbeddingModelsConfigurationControllerApi();
GGoogleVertexEmbeddingModelConfig body = new GGoogleVertexEmbeddingModelConfig(); // GGoogleVertexEmbeddingModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteGoogleVertexEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleVertexEmbeddingModelsConfigurationControllerApi#deleteGoogleVertexEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleVertexEmbeddingModelConfig**](GGoogleVertexEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGoogleVertexEmbeddingModelConfigByCode"></a>
# **findGoogleVertexEmbeddingModelConfigByCode**
> GGoogleVertexEmbeddingModelConfig findGoogleVertexEmbeddingModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleVertexEmbeddingModelsConfigurationControllerApi;


GoogleVertexEmbeddingModelsConfigurationControllerApi apiInstance = new GoogleVertexEmbeddingModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GGoogleVertexEmbeddingModelConfig result = apiInstance.findGoogleVertexEmbeddingModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleVertexEmbeddingModelsConfigurationControllerApi#findGoogleVertexEmbeddingModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GGoogleVertexEmbeddingModelConfig**](GGoogleVertexEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGoogleVertexEmbeddingModels"></a>
# **getGoogleVertexEmbeddingModels**
> OperationStatusListGGoogleVertexEmbeddingModelChoice getGoogleVertexEmbeddingModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleVertexEmbeddingModelsConfigurationControllerApi;


GoogleVertexEmbeddingModelsConfigurationControllerApi apiInstance = new GoogleVertexEmbeddingModelsConfigurationControllerApi();
GGoogleVertexEmbeddingModelConfig body = new GGoogleVertexEmbeddingModelConfig(); // GGoogleVertexEmbeddingModelConfig | 
try {
    OperationStatusListGGoogleVertexEmbeddingModelChoice result = apiInstance.getGoogleVertexEmbeddingModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleVertexEmbeddingModelsConfigurationControllerApi#getGoogleVertexEmbeddingModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleVertexEmbeddingModelConfig**](GGoogleVertexEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusListGGoogleVertexEmbeddingModelChoice**](OperationStatusListGGoogleVertexEmbeddingModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertGoogleVertexEmbeddingModelConfig"></a>
# **insertGoogleVertexEmbeddingModelConfig**
> OperationStatusGGoogleVertexEmbeddingModelConfig insertGoogleVertexEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleVertexEmbeddingModelsConfigurationControllerApi;


GoogleVertexEmbeddingModelsConfigurationControllerApi apiInstance = new GoogleVertexEmbeddingModelsConfigurationControllerApi();
GGoogleVertexEmbeddingModelConfig body = new GGoogleVertexEmbeddingModelConfig(); // GGoogleVertexEmbeddingModelConfig | 
try {
    OperationStatusGGoogleVertexEmbeddingModelConfig result = apiInstance.insertGoogleVertexEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleVertexEmbeddingModelsConfigurationControllerApi#insertGoogleVertexEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleVertexEmbeddingModelConfig**](GGoogleVertexEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusGGoogleVertexEmbeddingModelConfig**](OperationStatusGGoogleVertexEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGoogleVertexEmbeddingModelConfig"></a>
# **updateGoogleVertexEmbeddingModelConfig**
> OperationStatusGGoogleVertexEmbeddingModelConfig updateGoogleVertexEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleVertexEmbeddingModelsConfigurationControllerApi;


GoogleVertexEmbeddingModelsConfigurationControllerApi apiInstance = new GoogleVertexEmbeddingModelsConfigurationControllerApi();
GGoogleVertexEmbeddingModelConfig body = new GGoogleVertexEmbeddingModelConfig(); // GGoogleVertexEmbeddingModelConfig | 
try {
    OperationStatusGGoogleVertexEmbeddingModelConfig result = apiInstance.updateGoogleVertexEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleVertexEmbeddingModelsConfigurationControllerApi#updateGoogleVertexEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleVertexEmbeddingModelConfig**](GGoogleVertexEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusGGoogleVertexEmbeddingModelConfig**](OperationStatusGGoogleVertexEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

