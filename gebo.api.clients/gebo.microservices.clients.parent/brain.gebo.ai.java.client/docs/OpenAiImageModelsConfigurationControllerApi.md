# OpenAiImageModelsConfigurationControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteOpenAIImageModelConfig**](OpenAiImageModelsConfigurationControllerApi.md#deleteOpenAIImageModelConfig) | **POST** /api/admin/OpenAIImageModelsConfigurationController/deleteOpenAIImageModelConfig | 
[**findOpenAIImageModelConfigByCode**](OpenAiImageModelsConfigurationControllerApi.md#findOpenAIImageModelConfigByCode) | **GET** /api/admin/OpenAIImageModelsConfigurationController/findOpenAIImageModelConfigByCode | 
[**getOpenAIImageModels**](OpenAiImageModelsConfigurationControllerApi.md#getOpenAIImageModels) | **POST** /api/admin/OpenAIImageModelsConfigurationController/getOpenAIImageModels | 
[**insertOpenAIImageModelConfig**](OpenAiImageModelsConfigurationControllerApi.md#insertOpenAIImageModelConfig) | **POST** /api/admin/OpenAIImageModelsConfigurationController/insertOpenAIImageModelConfig | 
[**updateOpenAIImageModelConfig**](OpenAiImageModelsConfigurationControllerApi.md#updateOpenAIImageModelConfig) | **POST** /api/admin/OpenAIImageModelsConfigurationController/updateOpenAIImageModelConfig | 

<a name="deleteOpenAIImageModelConfig"></a>
# **deleteOpenAIImageModelConfig**
> OperationStatusBoolean deleteOpenAIImageModelConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.OpenAiImageModelsConfigurationControllerApi;


OpenAiImageModelsConfigurationControllerApi apiInstance = new OpenAiImageModelsConfigurationControllerApi();
GOpenAIImageModelConfig body = new GOpenAIImageModelConfig(); // GOpenAIImageModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteOpenAIImageModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiImageModelsConfigurationControllerApi#deleteOpenAIImageModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIImageModelConfig**](GOpenAIImageModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findOpenAIImageModelConfigByCode"></a>
# **findOpenAIImageModelConfigByCode**
> GOpenAIImageModelConfig findOpenAIImageModelConfigByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.OpenAiImageModelsConfigurationControllerApi;


OpenAiImageModelsConfigurationControllerApi apiInstance = new OpenAiImageModelsConfigurationControllerApi();
Object code = null; // Object | 
try {
    GOpenAIImageModelConfig result = apiInstance.findOpenAIImageModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiImageModelsConfigurationControllerApi#findOpenAIImageModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  |

### Return type

[**GOpenAIImageModelConfig**](GOpenAIImageModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getOpenAIImageModels"></a>
# **getOpenAIImageModels**
> OperationStatusListGOpenAIImageModelChoice getOpenAIImageModels(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.OpenAiImageModelsConfigurationControllerApi;


OpenAiImageModelsConfigurationControllerApi apiInstance = new OpenAiImageModelsConfigurationControllerApi();
GOpenAIImageModelConfig body = new GOpenAIImageModelConfig(); // GOpenAIImageModelConfig | 
try {
    OperationStatusListGOpenAIImageModelChoice result = apiInstance.getOpenAIImageModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiImageModelsConfigurationControllerApi#getOpenAIImageModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIImageModelConfig**](GOpenAIImageModelConfig.md)|  |

### Return type

[**OperationStatusListGOpenAIImageModelChoice**](OperationStatusListGOpenAIImageModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertOpenAIImageModelConfig"></a>
# **insertOpenAIImageModelConfig**
> OperationStatusGOpenAIImageModelConfig insertOpenAIImageModelConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.OpenAiImageModelsConfigurationControllerApi;


OpenAiImageModelsConfigurationControllerApi apiInstance = new OpenAiImageModelsConfigurationControllerApi();
GOpenAIImageModelConfig body = new GOpenAIImageModelConfig(); // GOpenAIImageModelConfig | 
try {
    OperationStatusGOpenAIImageModelConfig result = apiInstance.insertOpenAIImageModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiImageModelsConfigurationControllerApi#insertOpenAIImageModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIImageModelConfig**](GOpenAIImageModelConfig.md)|  |

### Return type

[**OperationStatusGOpenAIImageModelConfig**](OperationStatusGOpenAIImageModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateOpenAIImageModelConfig"></a>
# **updateOpenAIImageModelConfig**
> OperationStatusGOpenAIImageModelConfig updateOpenAIImageModelConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.OpenAiImageModelsConfigurationControllerApi;


OpenAiImageModelsConfigurationControllerApi apiInstance = new OpenAiImageModelsConfigurationControllerApi();
GOpenAIImageModelConfig body = new GOpenAIImageModelConfig(); // GOpenAIImageModelConfig | 
try {
    OperationStatusGOpenAIImageModelConfig result = apiInstance.updateOpenAIImageModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiImageModelsConfigurationControllerApi#updateOpenAIImageModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIImageModelConfig**](GOpenAIImageModelConfig.md)|  |

### Return type

[**OperationStatusGOpenAIImageModelConfig**](OperationStatusGOpenAIImageModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

