# OllamaChatModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteOllamaChatModelConfig**](OllamaChatModelsConfigurationControllerApi.md#deleteOllamaChatModelConfig) | **POST** /api/admin/OllamaChatModelsConfigurationController/deleteOllamaChatModelConfig | 
[**findOllamaChatModelConfigByCode**](OllamaChatModelsConfigurationControllerApi.md#findOllamaChatModelConfigByCode) | **GET** /api/admin/OllamaChatModelsConfigurationController/findOllamaChatModelConfigByCode | 
[**getOllamaChatModels**](OllamaChatModelsConfigurationControllerApi.md#getOllamaChatModels) | **POST** /api/admin/OllamaChatModelsConfigurationController/getOllamaModels | 
[**insertOllamaChatModelConfig**](OllamaChatModelsConfigurationControllerApi.md#insertOllamaChatModelConfig) | **POST** /api/admin/OllamaChatModelsConfigurationController/insertOllamaChatModelConfig | 
[**updateOllamaChatModelConfig**](OllamaChatModelsConfigurationControllerApi.md#updateOllamaChatModelConfig) | **POST** /api/admin/OllamaChatModelsConfigurationController/updateOllamaChatModelConfig | 

<a name="deleteOllamaChatModelConfig"></a>
# **deleteOllamaChatModelConfig**
> OperationStatusBoolean deleteOllamaChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OllamaChatModelsConfigurationControllerApi;


OllamaChatModelsConfigurationControllerApi apiInstance = new OllamaChatModelsConfigurationControllerApi();
GOllamaChatModelConfig body = new GOllamaChatModelConfig(); // GOllamaChatModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteOllamaChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OllamaChatModelsConfigurationControllerApi#deleteOllamaChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOllamaChatModelConfig**](GOllamaChatModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findOllamaChatModelConfigByCode"></a>
# **findOllamaChatModelConfigByCode**
> GOllamaChatModelConfig findOllamaChatModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OllamaChatModelsConfigurationControllerApi;


OllamaChatModelsConfigurationControllerApi apiInstance = new OllamaChatModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GOllamaChatModelConfig result = apiInstance.findOllamaChatModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OllamaChatModelsConfigurationControllerApi#findOllamaChatModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GOllamaChatModelConfig**](GOllamaChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getOllamaChatModels"></a>
# **getOllamaChatModels**
> OperationStatusListGOllamaChatModelChoice getOllamaChatModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OllamaChatModelsConfigurationControllerApi;


OllamaChatModelsConfigurationControllerApi apiInstance = new OllamaChatModelsConfigurationControllerApi();
GOllamaChatModelConfig body = new GOllamaChatModelConfig(); // GOllamaChatModelConfig | 
try {
    OperationStatusListGOllamaChatModelChoice result = apiInstance.getOllamaChatModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OllamaChatModelsConfigurationControllerApi#getOllamaChatModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOllamaChatModelConfig**](GOllamaChatModelConfig.md)|  |

### Return type

[**OperationStatusListGOllamaChatModelChoice**](OperationStatusListGOllamaChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertOllamaChatModelConfig"></a>
# **insertOllamaChatModelConfig**
> OperationStatusGOllamaChatModelConfig insertOllamaChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OllamaChatModelsConfigurationControllerApi;


OllamaChatModelsConfigurationControllerApi apiInstance = new OllamaChatModelsConfigurationControllerApi();
GOllamaChatModelConfig body = new GOllamaChatModelConfig(); // GOllamaChatModelConfig | 
try {
    OperationStatusGOllamaChatModelConfig result = apiInstance.insertOllamaChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OllamaChatModelsConfigurationControllerApi#insertOllamaChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOllamaChatModelConfig**](GOllamaChatModelConfig.md)|  |

### Return type

[**OperationStatusGOllamaChatModelConfig**](OperationStatusGOllamaChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateOllamaChatModelConfig"></a>
# **updateOllamaChatModelConfig**
> OperationStatusGOllamaChatModelConfig updateOllamaChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OllamaChatModelsConfigurationControllerApi;


OllamaChatModelsConfigurationControllerApi apiInstance = new OllamaChatModelsConfigurationControllerApi();
GOllamaChatModelConfig body = new GOllamaChatModelConfig(); // GOllamaChatModelConfig | 
try {
    OperationStatusGOllamaChatModelConfig result = apiInstance.updateOllamaChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OllamaChatModelsConfigurationControllerApi#updateOllamaChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOllamaChatModelConfig**](GOllamaChatModelConfig.md)|  |

### Return type

[**OperationStatusGOllamaChatModelConfig**](OperationStatusGOllamaChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

