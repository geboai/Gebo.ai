# OllamaDecisionModelsConfigurationControllerApi

All URIs are relative to *http://localhost:13999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteOllamaDecisionModelConfig**](OllamaDecisionModelsConfigurationControllerApi.md#deleteOllamaDecisionModelConfig) | **POST** /api/admin/OllamaDecisionModelsConfigurationController/deleteOllamaDecisionModelConfig | 
[**findOllamaDecisionModelConfigByCode**](OllamaDecisionModelsConfigurationControllerApi.md#findOllamaDecisionModelConfigByCode) | **GET** /api/admin/OllamaDecisionModelsConfigurationController/findOllamaDecisionModelConfigByCode | 
[**getOllamaDecisionModels**](OllamaDecisionModelsConfigurationControllerApi.md#getOllamaDecisionModels) | **POST** /api/admin/OllamaDecisionModelsConfigurationController/getOllamaDecisionModels | 
[**insertOllamaDecisionModelConfig**](OllamaDecisionModelsConfigurationControllerApi.md#insertOllamaDecisionModelConfig) | **POST** /api/admin/OllamaDecisionModelsConfigurationController/insertOllamaDecisionModelConfig | 
[**updateOllamaDecisionModelConfig**](OllamaDecisionModelsConfigurationControllerApi.md#updateOllamaDecisionModelConfig) | **POST** /api/admin/OllamaDecisionModelsConfigurationController/updateOllamaDecisionModelConfig | 

<a name="deleteOllamaDecisionModelConfig"></a>
# **deleteOllamaDecisionModelConfig**
> OperationStatusBoolean deleteOllamaDecisionModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OllamaDecisionModelsConfigurationControllerApi;


OllamaDecisionModelsConfigurationControllerApi apiInstance = new OllamaDecisionModelsConfigurationControllerApi();
GOllamaDecisionModelConfig body = new GOllamaDecisionModelConfig(); // GOllamaDecisionModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteOllamaDecisionModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OllamaDecisionModelsConfigurationControllerApi#deleteOllamaDecisionModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOllamaDecisionModelConfig**](GOllamaDecisionModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findOllamaDecisionModelConfigByCode"></a>
# **findOllamaDecisionModelConfigByCode**
> GOllamaDecisionModelConfig findOllamaDecisionModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OllamaDecisionModelsConfigurationControllerApi;


OllamaDecisionModelsConfigurationControllerApi apiInstance = new OllamaDecisionModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GOllamaDecisionModelConfig result = apiInstance.findOllamaDecisionModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OllamaDecisionModelsConfigurationControllerApi#findOllamaDecisionModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GOllamaDecisionModelConfig**](GOllamaDecisionModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getOllamaDecisionModels"></a>
# **getOllamaDecisionModels**
> OperationStatusListGOllamaDecisionModelChoice getOllamaDecisionModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OllamaDecisionModelsConfigurationControllerApi;


OllamaDecisionModelsConfigurationControllerApi apiInstance = new OllamaDecisionModelsConfigurationControllerApi();
GOllamaDecisionModelConfig body = new GOllamaDecisionModelConfig(); // GOllamaDecisionModelConfig | 
try {
    OperationStatusListGOllamaDecisionModelChoice result = apiInstance.getOllamaDecisionModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OllamaDecisionModelsConfigurationControllerApi#getOllamaDecisionModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOllamaDecisionModelConfig**](GOllamaDecisionModelConfig.md)|  |

### Return type

[**OperationStatusListGOllamaDecisionModelChoice**](OperationStatusListGOllamaDecisionModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertOllamaDecisionModelConfig"></a>
# **insertOllamaDecisionModelConfig**
> OperationStatusGOllamaDecisionModelConfig insertOllamaDecisionModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OllamaDecisionModelsConfigurationControllerApi;


OllamaDecisionModelsConfigurationControllerApi apiInstance = new OllamaDecisionModelsConfigurationControllerApi();
GOllamaDecisionModelConfig body = new GOllamaDecisionModelConfig(); // GOllamaDecisionModelConfig | 
try {
    OperationStatusGOllamaDecisionModelConfig result = apiInstance.insertOllamaDecisionModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OllamaDecisionModelsConfigurationControllerApi#insertOllamaDecisionModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOllamaDecisionModelConfig**](GOllamaDecisionModelConfig.md)|  |

### Return type

[**OperationStatusGOllamaDecisionModelConfig**](OperationStatusGOllamaDecisionModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateOllamaDecisionModelConfig"></a>
# **updateOllamaDecisionModelConfig**
> OperationStatusGOllamaDecisionModelConfig updateOllamaDecisionModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OllamaDecisionModelsConfigurationControllerApi;


OllamaDecisionModelsConfigurationControllerApi apiInstance = new OllamaDecisionModelsConfigurationControllerApi();
GOllamaDecisionModelConfig body = new GOllamaDecisionModelConfig(); // GOllamaDecisionModelConfig | 
try {
    OperationStatusGOllamaDecisionModelConfig result = apiInstance.updateOllamaDecisionModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OllamaDecisionModelsConfigurationControllerApi#updateOllamaDecisionModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOllamaDecisionModelConfig**](GOllamaDecisionModelConfig.md)|  |

### Return type

[**OperationStatusGOllamaDecisionModelConfig**](OperationStatusGOllamaDecisionModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

