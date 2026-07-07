# GenericOpenAiapiTranscriptModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGenericOpenAIAPITranscriptModelConfig**](GenericOpenAiapiTranscriptModelsConfigurationControllerApi.md#deleteGenericOpenAIAPITranscriptModelConfig) | **POST** /api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/deleteGenericOpenAIAPITranscriptModelConfig | 
[**findGenericOpenAIAPITranscriptModelConfigByCode**](GenericOpenAiapiTranscriptModelsConfigurationControllerApi.md#findGenericOpenAIAPITranscriptModelConfigByCode) | **GET** /api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/findGenericOpenAIAPITranscriptModelConfigByCode | 
[**getGenericOpenAIAPITranscriptModels**](GenericOpenAiapiTranscriptModelsConfigurationControllerApi.md#getGenericOpenAIAPITranscriptModels) | **POST** /api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/getGenericOpenAIAPITranscriptModels | 
[**getGenericOpenAITranscriptModelConfigs**](GenericOpenAiapiTranscriptModelsConfigurationControllerApi.md#getGenericOpenAITranscriptModelConfigs) | **GET** /api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/getGenericOpenAITranscriptModelConfigs | 
[**getGenericOpenAITranscriptModelTypes**](GenericOpenAiapiTranscriptModelsConfigurationControllerApi.md#getGenericOpenAITranscriptModelTypes) | **GET** /api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/getGenericOpenAITranscriptModelTypes | 
[**insertGenericOpenAIAPITranscriptModelConfig**](GenericOpenAiapiTranscriptModelsConfigurationControllerApi.md#insertGenericOpenAIAPITranscriptModelConfig) | **POST** /api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/insertGenericOpenAIAPITranscriptModelConfig | 
[**updateGenericOpenAIAPITranscriptModelConfig**](GenericOpenAiapiTranscriptModelsConfigurationControllerApi.md#updateGenericOpenAIAPITranscriptModelConfig) | **POST** /api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/updateGenericOpenAIAPITranscriptModelConfig | 

<a name="deleteGenericOpenAIAPITranscriptModelConfig"></a>
# **deleteGenericOpenAIAPITranscriptModelConfig**
> OperationStatusBoolean deleteGenericOpenAIAPITranscriptModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiTranscriptModelsConfigurationControllerApi;


GenericOpenAiapiTranscriptModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiTranscriptModelsConfigurationControllerApi();
GenericOpenAIAPITranscriptModelConfig body = new GenericOpenAIAPITranscriptModelConfig(); // GenericOpenAIAPITranscriptModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteGenericOpenAIAPITranscriptModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiTranscriptModelsConfigurationControllerApi#deleteGenericOpenAIAPITranscriptModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPITranscriptModelConfig**](GenericOpenAIAPITranscriptModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGenericOpenAIAPITranscriptModelConfigByCode"></a>
# **findGenericOpenAIAPITranscriptModelConfigByCode**
> GenericOpenAIAPITranscriptModelConfig findGenericOpenAIAPITranscriptModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiTranscriptModelsConfigurationControllerApi;


GenericOpenAiapiTranscriptModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiTranscriptModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GenericOpenAIAPITranscriptModelConfig result = apiInstance.findGenericOpenAIAPITranscriptModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiTranscriptModelsConfigurationControllerApi#findGenericOpenAIAPITranscriptModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GenericOpenAIAPITranscriptModelConfig**](GenericOpenAIAPITranscriptModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGenericOpenAIAPITranscriptModels"></a>
# **getGenericOpenAIAPITranscriptModels**
> OperationStatusListGenericOpenAIAPITranscriptModelChoice getGenericOpenAIAPITranscriptModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiTranscriptModelsConfigurationControllerApi;


GenericOpenAiapiTranscriptModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiTranscriptModelsConfigurationControllerApi();
GenericOpenAIAPITranscriptModelConfig body = new GenericOpenAIAPITranscriptModelConfig(); // GenericOpenAIAPITranscriptModelConfig | 
try {
    OperationStatusListGenericOpenAIAPITranscriptModelChoice result = apiInstance.getGenericOpenAIAPITranscriptModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiTranscriptModelsConfigurationControllerApi#getGenericOpenAIAPITranscriptModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPITranscriptModelConfig**](GenericOpenAIAPITranscriptModelConfig.md)|  |

### Return type

[**OperationStatusListGenericOpenAIAPITranscriptModelChoice**](OperationStatusListGenericOpenAIAPITranscriptModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getGenericOpenAITranscriptModelConfigs"></a>
# **getGenericOpenAITranscriptModelConfigs**
> List&lt;GenericOpenAIAPITranscriptModelConfig&gt; getGenericOpenAITranscriptModelConfigs()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiTranscriptModelsConfigurationControllerApi;


GenericOpenAiapiTranscriptModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiTranscriptModelsConfigurationControllerApi();
try {
    List<GenericOpenAIAPITranscriptModelConfig> result = apiInstance.getGenericOpenAITranscriptModelConfigs();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiTranscriptModelsConfigurationControllerApi#getGenericOpenAITranscriptModelConfigs");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GenericOpenAIAPITranscriptModelConfig&gt;**](GenericOpenAIAPITranscriptModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGenericOpenAITranscriptModelTypes"></a>
# **getGenericOpenAITranscriptModelTypes**
> List&lt;GenericOpenAITranscriptModelType&gt; getGenericOpenAITranscriptModelTypes()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiTranscriptModelsConfigurationControllerApi;


GenericOpenAiapiTranscriptModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiTranscriptModelsConfigurationControllerApi();
try {
    List<GenericOpenAITranscriptModelType> result = apiInstance.getGenericOpenAITranscriptModelTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiTranscriptModelsConfigurationControllerApi#getGenericOpenAITranscriptModelTypes");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GenericOpenAITranscriptModelType&gt;**](GenericOpenAITranscriptModelType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertGenericOpenAIAPITranscriptModelConfig"></a>
# **insertGenericOpenAIAPITranscriptModelConfig**
> OperationStatusGenericOpenAIAPITranscriptModelConfig insertGenericOpenAIAPITranscriptModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiTranscriptModelsConfigurationControllerApi;


GenericOpenAiapiTranscriptModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiTranscriptModelsConfigurationControllerApi();
GenericOpenAIAPITranscriptModelConfig body = new GenericOpenAIAPITranscriptModelConfig(); // GenericOpenAIAPITranscriptModelConfig | 
try {
    OperationStatusGenericOpenAIAPITranscriptModelConfig result = apiInstance.insertGenericOpenAIAPITranscriptModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiTranscriptModelsConfigurationControllerApi#insertGenericOpenAIAPITranscriptModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPITranscriptModelConfig**](GenericOpenAIAPITranscriptModelConfig.md)|  |

### Return type

[**OperationStatusGenericOpenAIAPITranscriptModelConfig**](OperationStatusGenericOpenAIAPITranscriptModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGenericOpenAIAPITranscriptModelConfig"></a>
# **updateGenericOpenAIAPITranscriptModelConfig**
> OperationStatusGenericOpenAIAPITranscriptModelConfig updateGenericOpenAIAPITranscriptModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiTranscriptModelsConfigurationControllerApi;


GenericOpenAiapiTranscriptModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiTranscriptModelsConfigurationControllerApi();
GenericOpenAIAPITranscriptModelConfig body = new GenericOpenAIAPITranscriptModelConfig(); // GenericOpenAIAPITranscriptModelConfig | 
try {
    OperationStatusGenericOpenAIAPITranscriptModelConfig result = apiInstance.updateGenericOpenAIAPITranscriptModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiTranscriptModelsConfigurationControllerApi#updateGenericOpenAIAPITranscriptModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPITranscriptModelConfig**](GenericOpenAIAPITranscriptModelConfig.md)|  |

### Return type

[**OperationStatusGenericOpenAIAPITranscriptModelConfig**](OperationStatusGenericOpenAIAPITranscriptModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

