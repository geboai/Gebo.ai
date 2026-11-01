# GenericOpenAiapiEmbeddingModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGenericOpenAIAPIEmbeddingModelConfig**](GenericOpenAiapiEmbeddingModelsConfigurationControllerApi.md#deleteGenericOpenAIAPIEmbeddingModelConfig) | **POST** /api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/deleteGenericOpenAIAPIEmbeddingModelConfig | 
[**findGenericOpenAIAPIEmbeddingModelConfigByCode**](GenericOpenAiapiEmbeddingModelsConfigurationControllerApi.md#findGenericOpenAIAPIEmbeddingModelConfigByCode) | **GET** /api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/findGenericOpenAIAPIEmbeddingModelConfigByCode | 
[**getGenericOpenAIAPIEmbeddingModels**](GenericOpenAiapiEmbeddingModelsConfigurationControllerApi.md#getGenericOpenAIAPIEmbeddingModels) | **POST** /api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/getGenericOpenAIAPIEmbeddingModels | 
[**getGenericOpenAIEmbeddingModelTypes**](GenericOpenAiapiEmbeddingModelsConfigurationControllerApi.md#getGenericOpenAIEmbeddingModelTypes) | **GET** /api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/getGenericOpenAIEmbeddingModelTypes | 
[**insertGenericOpenAIAPIEmbeddingModelConfig**](GenericOpenAiapiEmbeddingModelsConfigurationControllerApi.md#insertGenericOpenAIAPIEmbeddingModelConfig) | **POST** /api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/insertGenericOpenAIAPIEmbeddingModelConfig | 
[**updateGenericOpenAIAPIEmbeddingModelConfig**](GenericOpenAiapiEmbeddingModelsConfigurationControllerApi.md#updateGenericOpenAIAPIEmbeddingModelConfig) | **POST** /api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/updateGenericOpenAIAPIEmbeddingModelConfig | 

<a name="deleteGenericOpenAIAPIEmbeddingModelConfig"></a>
# **deleteGenericOpenAIAPIEmbeddingModelConfig**
> OperationStatusBoolean deleteGenericOpenAIAPIEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiEmbeddingModelsConfigurationControllerApi;


GenericOpenAiapiEmbeddingModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiEmbeddingModelsConfigurationControllerApi();
GenericOpenAIAPIEmbeddingModelConfig body = new GenericOpenAIAPIEmbeddingModelConfig(); // GenericOpenAIAPIEmbeddingModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteGenericOpenAIAPIEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiEmbeddingModelsConfigurationControllerApi#deleteGenericOpenAIAPIEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIEmbeddingModelConfig**](GenericOpenAIAPIEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGenericOpenAIAPIEmbeddingModelConfigByCode"></a>
# **findGenericOpenAIAPIEmbeddingModelConfigByCode**
> GenericOpenAIAPIEmbeddingModelConfig findGenericOpenAIAPIEmbeddingModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiEmbeddingModelsConfigurationControllerApi;


GenericOpenAiapiEmbeddingModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiEmbeddingModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GenericOpenAIAPIEmbeddingModelConfig result = apiInstance.findGenericOpenAIAPIEmbeddingModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiEmbeddingModelsConfigurationControllerApi#findGenericOpenAIAPIEmbeddingModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GenericOpenAIAPIEmbeddingModelConfig**](GenericOpenAIAPIEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGenericOpenAIAPIEmbeddingModels"></a>
# **getGenericOpenAIAPIEmbeddingModels**
> OperationStatusListGenericOpenAIAPIEmbeddingModelChoice getGenericOpenAIAPIEmbeddingModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiEmbeddingModelsConfigurationControllerApi;


GenericOpenAiapiEmbeddingModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiEmbeddingModelsConfigurationControllerApi();
GenericOpenAIAPIEmbeddingModelConfig body = new GenericOpenAIAPIEmbeddingModelConfig(); // GenericOpenAIAPIEmbeddingModelConfig | 
try {
    OperationStatusListGenericOpenAIAPIEmbeddingModelChoice result = apiInstance.getGenericOpenAIAPIEmbeddingModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiEmbeddingModelsConfigurationControllerApi#getGenericOpenAIAPIEmbeddingModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIEmbeddingModelConfig**](GenericOpenAIAPIEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusListGenericOpenAIAPIEmbeddingModelChoice**](OperationStatusListGenericOpenAIAPIEmbeddingModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getGenericOpenAIEmbeddingModelTypes"></a>
# **getGenericOpenAIEmbeddingModelTypes**
> List&lt;GenericOpenAIEmbeddingModelTypeConfig&gt; getGenericOpenAIEmbeddingModelTypes()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiEmbeddingModelsConfigurationControllerApi;


GenericOpenAiapiEmbeddingModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiEmbeddingModelsConfigurationControllerApi();
try {
    List<GenericOpenAIEmbeddingModelTypeConfig> result = apiInstance.getGenericOpenAIEmbeddingModelTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiEmbeddingModelsConfigurationControllerApi#getGenericOpenAIEmbeddingModelTypes");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GenericOpenAIEmbeddingModelTypeConfig&gt;**](GenericOpenAIEmbeddingModelTypeConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertGenericOpenAIAPIEmbeddingModelConfig"></a>
# **insertGenericOpenAIAPIEmbeddingModelConfig**
> OperationStatusGenericOpenAIAPIEmbeddingModelConfig insertGenericOpenAIAPIEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiEmbeddingModelsConfigurationControllerApi;


GenericOpenAiapiEmbeddingModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiEmbeddingModelsConfigurationControllerApi();
GenericOpenAIAPIEmbeddingModelConfig body = new GenericOpenAIAPIEmbeddingModelConfig(); // GenericOpenAIAPIEmbeddingModelConfig | 
try {
    OperationStatusGenericOpenAIAPIEmbeddingModelConfig result = apiInstance.insertGenericOpenAIAPIEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiEmbeddingModelsConfigurationControllerApi#insertGenericOpenAIAPIEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIEmbeddingModelConfig**](GenericOpenAIAPIEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusGenericOpenAIAPIEmbeddingModelConfig**](OperationStatusGenericOpenAIAPIEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGenericOpenAIAPIEmbeddingModelConfig"></a>
# **updateGenericOpenAIAPIEmbeddingModelConfig**
> OperationStatusGenericOpenAIAPIEmbeddingModelConfig updateGenericOpenAIAPIEmbeddingModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiEmbeddingModelsConfigurationControllerApi;


GenericOpenAiapiEmbeddingModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiEmbeddingModelsConfigurationControllerApi();
GenericOpenAIAPIEmbeddingModelConfig body = new GenericOpenAIAPIEmbeddingModelConfig(); // GenericOpenAIAPIEmbeddingModelConfig | 
try {
    OperationStatusGenericOpenAIAPIEmbeddingModelConfig result = apiInstance.updateGenericOpenAIAPIEmbeddingModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiEmbeddingModelsConfigurationControllerApi#updateGenericOpenAIAPIEmbeddingModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIEmbeddingModelConfig**](GenericOpenAIAPIEmbeddingModelConfig.md)|  |

### Return type

[**OperationStatusGenericOpenAIAPIEmbeddingModelConfig**](OperationStatusGenericOpenAIAPIEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

