# GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGenericOpenAIAPITextToSpeechModelConfig**](GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi.md#deleteGenericOpenAIAPITextToSpeechModelConfig) | **POST** /api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/deleteGenericOpenAIAPITextToSpeechModelConfig | 
[**findGenericOpenAIAPITextToSpeechModelConfigByCode**](GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi.md#findGenericOpenAIAPITextToSpeechModelConfigByCode) | **GET** /api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/findGenericOpenAIAPITextToSpeechModelConfigByCode | 
[**getGenericOpenAIAPITextToSpeechModels**](GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi.md#getGenericOpenAIAPITextToSpeechModels) | **POST** /api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/getGenericOpenAIAPITextToSpeechModels | 
[**getGenericOpenAITextToSpeechModelConfigs**](GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi.md#getGenericOpenAITextToSpeechModelConfigs) | **GET** /api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/getGenericOpenAITextToSpeechModelConfigs | 
[**getGenericOpenAITextToSpeechModelTypes**](GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi.md#getGenericOpenAITextToSpeechModelTypes) | **GET** /api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/getGenericOpenAITextToSpeechModelTypes | 
[**insertGenericOpenAIAPITextToSpeechModelConfig**](GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi.md#insertGenericOpenAIAPITextToSpeechModelConfig) | **POST** /api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/insertGenericOpenAIAPITextToSpeechModelConfig | 
[**updateGenericOpenAIAPITextToSpeechModelConfig**](GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi.md#updateGenericOpenAIAPITextToSpeechModelConfig) | **POST** /api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/updateGenericOpenAIAPITextToSpeechModelConfig | 

<a name="deleteGenericOpenAIAPITextToSpeechModelConfig"></a>
# **deleteGenericOpenAIAPITextToSpeechModelConfig**
> OperationStatusBoolean deleteGenericOpenAIAPITextToSpeechModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi;


GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi();
GenericOpenAIAPITextToSpeechModelConfig body = new GenericOpenAIAPITextToSpeechModelConfig(); // GenericOpenAIAPITextToSpeechModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteGenericOpenAIAPITextToSpeechModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi#deleteGenericOpenAIAPITextToSpeechModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPITextToSpeechModelConfig**](GenericOpenAIAPITextToSpeechModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGenericOpenAIAPITextToSpeechModelConfigByCode"></a>
# **findGenericOpenAIAPITextToSpeechModelConfigByCode**
> GenericOpenAIAPITextToSpeechModelConfig findGenericOpenAIAPITextToSpeechModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi;


GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GenericOpenAIAPITextToSpeechModelConfig result = apiInstance.findGenericOpenAIAPITextToSpeechModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi#findGenericOpenAIAPITextToSpeechModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GenericOpenAIAPITextToSpeechModelConfig**](GenericOpenAIAPITextToSpeechModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGenericOpenAIAPITextToSpeechModels"></a>
# **getGenericOpenAIAPITextToSpeechModels**
> OperationStatusListGenericOpenAIAPITextToSpeechModelChoice getGenericOpenAIAPITextToSpeechModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi;


GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi();
GenericOpenAIAPITextToSpeechModelConfig body = new GenericOpenAIAPITextToSpeechModelConfig(); // GenericOpenAIAPITextToSpeechModelConfig | 
try {
    OperationStatusListGenericOpenAIAPITextToSpeechModelChoice result = apiInstance.getGenericOpenAIAPITextToSpeechModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi#getGenericOpenAIAPITextToSpeechModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPITextToSpeechModelConfig**](GenericOpenAIAPITextToSpeechModelConfig.md)|  |

### Return type

[**OperationStatusListGenericOpenAIAPITextToSpeechModelChoice**](OperationStatusListGenericOpenAIAPITextToSpeechModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getGenericOpenAITextToSpeechModelConfigs"></a>
# **getGenericOpenAITextToSpeechModelConfigs**
> List&lt;GenericOpenAIAPITextToSpeechModelConfig&gt; getGenericOpenAITextToSpeechModelConfigs()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi;


GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi();
try {
    List<GenericOpenAIAPITextToSpeechModelConfig> result = apiInstance.getGenericOpenAITextToSpeechModelConfigs();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi#getGenericOpenAITextToSpeechModelConfigs");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GenericOpenAIAPITextToSpeechModelConfig&gt;**](GenericOpenAIAPITextToSpeechModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGenericOpenAITextToSpeechModelTypes"></a>
# **getGenericOpenAITextToSpeechModelTypes**
> List&lt;GenericOpenAITextToSpeechModelType&gt; getGenericOpenAITextToSpeechModelTypes()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi;


GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi();
try {
    List<GenericOpenAITextToSpeechModelType> result = apiInstance.getGenericOpenAITextToSpeechModelTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi#getGenericOpenAITextToSpeechModelTypes");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GenericOpenAITextToSpeechModelType&gt;**](GenericOpenAITextToSpeechModelType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertGenericOpenAIAPITextToSpeechModelConfig"></a>
# **insertGenericOpenAIAPITextToSpeechModelConfig**
> OperationStatusGenericOpenAIAPITextToSpeechModelConfig insertGenericOpenAIAPITextToSpeechModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi;


GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi();
GenericOpenAIAPITextToSpeechModelConfig body = new GenericOpenAIAPITextToSpeechModelConfig(); // GenericOpenAIAPITextToSpeechModelConfig | 
try {
    OperationStatusGenericOpenAIAPITextToSpeechModelConfig result = apiInstance.insertGenericOpenAIAPITextToSpeechModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi#insertGenericOpenAIAPITextToSpeechModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPITextToSpeechModelConfig**](GenericOpenAIAPITextToSpeechModelConfig.md)|  |

### Return type

[**OperationStatusGenericOpenAIAPITextToSpeechModelConfig**](OperationStatusGenericOpenAIAPITextToSpeechModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGenericOpenAIAPITextToSpeechModelConfig"></a>
# **updateGenericOpenAIAPITextToSpeechModelConfig**
> OperationStatusGenericOpenAIAPITextToSpeechModelConfig updateGenericOpenAIAPITextToSpeechModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi;


GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi();
GenericOpenAIAPITextToSpeechModelConfig body = new GenericOpenAIAPITextToSpeechModelConfig(); // GenericOpenAIAPITextToSpeechModelConfig | 
try {
    OperationStatusGenericOpenAIAPITextToSpeechModelConfig result = apiInstance.updateGenericOpenAIAPITextToSpeechModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi#updateGenericOpenAIAPITextToSpeechModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPITextToSpeechModelConfig**](GenericOpenAIAPITextToSpeechModelConfig.md)|  |

### Return type

[**OperationStatusGenericOpenAIAPITextToSpeechModelConfig**](OperationStatusGenericOpenAIAPITextToSpeechModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

