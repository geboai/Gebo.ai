# GenericOpenAiDecisionModelsConfigurationControllerApi

All URIs are relative to *http://localhost:13999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGenericOpenAIAPIDecisionModelConfig**](GenericOpenAiDecisionModelsConfigurationControllerApi.md#deleteGenericOpenAIAPIDecisionModelConfig) | **POST** /api/admin/GenericOpenAIDecisionModelsConfigurationController/deleteGenericOpenAIAPIDecisionModelConfig | 
[**findGenericOpenAIAPIDecisionModelConfigByCode**](GenericOpenAiDecisionModelsConfigurationControllerApi.md#findGenericOpenAIAPIDecisionModelConfigByCode) | **GET** /api/admin/GenericOpenAIDecisionModelsConfigurationController/findGenericOpenAIAPIDecisionModelConfigByCode | 
[**getGenericOpenAIAPIDecisionModels**](GenericOpenAiDecisionModelsConfigurationControllerApi.md#getGenericOpenAIAPIDecisionModels) | **POST** /api/admin/GenericOpenAIDecisionModelsConfigurationController/getGenericOpenAIAPIDecisionModels | 
[**getGenericOpenAIDecisionModelConfigs**](GenericOpenAiDecisionModelsConfigurationControllerApi.md#getGenericOpenAIDecisionModelConfigs) | **GET** /api/admin/GenericOpenAIDecisionModelsConfigurationController/getGenericOpenAIDecisionModelConfigs | 
[**getGenericOpenAIDecisionModelTypes**](GenericOpenAiDecisionModelsConfigurationControllerApi.md#getGenericOpenAIDecisionModelTypes) | **GET** /api/admin/GenericOpenAIDecisionModelsConfigurationController/getGenericOpenAIDecisionModelTypes | 
[**insertGenericOpenAIAPIDecisionModelConfig**](GenericOpenAiDecisionModelsConfigurationControllerApi.md#insertGenericOpenAIAPIDecisionModelConfig) | **POST** /api/admin/GenericOpenAIDecisionModelsConfigurationController/insertGenericOpenAIAPIDecisionModelConfig | 
[**updateGenericOpenAIAPIDecisionModelConfig**](GenericOpenAiDecisionModelsConfigurationControllerApi.md#updateGenericOpenAIAPIDecisionModelConfig) | **POST** /api/admin/GenericOpenAIDecisionModelsConfigurationController/updateGenericOpenAIAPIDecisionModelConfig | 

<a name="deleteGenericOpenAIAPIDecisionModelConfig"></a>
# **deleteGenericOpenAIAPIDecisionModelConfig**
> OperationStatusBoolean deleteGenericOpenAIAPIDecisionModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiDecisionModelsConfigurationControllerApi;


GenericOpenAiDecisionModelsConfigurationControllerApi apiInstance = new GenericOpenAiDecisionModelsConfigurationControllerApi();
GenericOpenAIAPIDecisionModelConfig body = new GenericOpenAIAPIDecisionModelConfig(); // GenericOpenAIAPIDecisionModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteGenericOpenAIAPIDecisionModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiDecisionModelsConfigurationControllerApi#deleteGenericOpenAIAPIDecisionModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIDecisionModelConfig**](GenericOpenAIAPIDecisionModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGenericOpenAIAPIDecisionModelConfigByCode"></a>
# **findGenericOpenAIAPIDecisionModelConfigByCode**
> GenericOpenAIAPIDecisionModelConfig findGenericOpenAIAPIDecisionModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiDecisionModelsConfigurationControllerApi;


GenericOpenAiDecisionModelsConfigurationControllerApi apiInstance = new GenericOpenAiDecisionModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GenericOpenAIAPIDecisionModelConfig result = apiInstance.findGenericOpenAIAPIDecisionModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiDecisionModelsConfigurationControllerApi#findGenericOpenAIAPIDecisionModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GenericOpenAIAPIDecisionModelConfig**](GenericOpenAIAPIDecisionModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGenericOpenAIAPIDecisionModels"></a>
# **getGenericOpenAIAPIDecisionModels**
> OperationStatusListGenericOpenAIAPIDecisionModelChoice getGenericOpenAIAPIDecisionModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiDecisionModelsConfigurationControllerApi;


GenericOpenAiDecisionModelsConfigurationControllerApi apiInstance = new GenericOpenAiDecisionModelsConfigurationControllerApi();
GenericOpenAIAPIDecisionModelConfig body = new GenericOpenAIAPIDecisionModelConfig(); // GenericOpenAIAPIDecisionModelConfig | 
try {
    OperationStatusListGenericOpenAIAPIDecisionModelChoice result = apiInstance.getGenericOpenAIAPIDecisionModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiDecisionModelsConfigurationControllerApi#getGenericOpenAIAPIDecisionModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIDecisionModelConfig**](GenericOpenAIAPIDecisionModelConfig.md)|  |

### Return type

[**OperationStatusListGenericOpenAIAPIDecisionModelChoice**](OperationStatusListGenericOpenAIAPIDecisionModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getGenericOpenAIDecisionModelConfigs"></a>
# **getGenericOpenAIDecisionModelConfigs**
> List&lt;GenericOpenAIAPIDecisionModelConfig&gt; getGenericOpenAIDecisionModelConfigs()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiDecisionModelsConfigurationControllerApi;


GenericOpenAiDecisionModelsConfigurationControllerApi apiInstance = new GenericOpenAiDecisionModelsConfigurationControllerApi();
try {
    List<GenericOpenAIAPIDecisionModelConfig> result = apiInstance.getGenericOpenAIDecisionModelConfigs();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiDecisionModelsConfigurationControllerApi#getGenericOpenAIDecisionModelConfigs");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GenericOpenAIAPIDecisionModelConfig&gt;**](GenericOpenAIAPIDecisionModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGenericOpenAIDecisionModelTypes"></a>
# **getGenericOpenAIDecisionModelTypes**
> List&lt;GenericOpenAIDecisionModelTypeConfig&gt; getGenericOpenAIDecisionModelTypes()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiDecisionModelsConfigurationControllerApi;


GenericOpenAiDecisionModelsConfigurationControllerApi apiInstance = new GenericOpenAiDecisionModelsConfigurationControllerApi();
try {
    List<GenericOpenAIDecisionModelTypeConfig> result = apiInstance.getGenericOpenAIDecisionModelTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiDecisionModelsConfigurationControllerApi#getGenericOpenAIDecisionModelTypes");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GenericOpenAIDecisionModelTypeConfig&gt;**](GenericOpenAIDecisionModelTypeConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertGenericOpenAIAPIDecisionModelConfig"></a>
# **insertGenericOpenAIAPIDecisionModelConfig**
> OperationStatusGenericOpenAIAPIDecisionModelConfig insertGenericOpenAIAPIDecisionModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiDecisionModelsConfigurationControllerApi;


GenericOpenAiDecisionModelsConfigurationControllerApi apiInstance = new GenericOpenAiDecisionModelsConfigurationControllerApi();
GenericOpenAIAPIDecisionModelConfig body = new GenericOpenAIAPIDecisionModelConfig(); // GenericOpenAIAPIDecisionModelConfig | 
try {
    OperationStatusGenericOpenAIAPIDecisionModelConfig result = apiInstance.insertGenericOpenAIAPIDecisionModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiDecisionModelsConfigurationControllerApi#insertGenericOpenAIAPIDecisionModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIDecisionModelConfig**](GenericOpenAIAPIDecisionModelConfig.md)|  |

### Return type

[**OperationStatusGenericOpenAIAPIDecisionModelConfig**](OperationStatusGenericOpenAIAPIDecisionModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGenericOpenAIAPIDecisionModelConfig"></a>
# **updateGenericOpenAIAPIDecisionModelConfig**
> OperationStatusGenericOpenAIAPIDecisionModelConfig updateGenericOpenAIAPIDecisionModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiDecisionModelsConfigurationControllerApi;


GenericOpenAiDecisionModelsConfigurationControllerApi apiInstance = new GenericOpenAiDecisionModelsConfigurationControllerApi();
GenericOpenAIAPIDecisionModelConfig body = new GenericOpenAIAPIDecisionModelConfig(); // GenericOpenAIAPIDecisionModelConfig | 
try {
    OperationStatusGenericOpenAIAPIDecisionModelConfig result = apiInstance.updateGenericOpenAIAPIDecisionModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiDecisionModelsConfigurationControllerApi#updateGenericOpenAIAPIDecisionModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIDecisionModelConfig**](GenericOpenAIAPIDecisionModelConfig.md)|  |

### Return type

[**OperationStatusGenericOpenAIAPIDecisionModelConfig**](OperationStatusGenericOpenAIAPIDecisionModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

