# BedrockImageModelsConfigurationControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteBedrockImageModelConfig**](BedrockImageModelsConfigurationControllerApi.md#deleteBedrockImageModelConfig) | **POST** /api/admin/BedrockImageModelsConfigurationController/deleteBedrockImageModelConfig | 
[**findBedrockImageModelConfigByCode**](BedrockImageModelsConfigurationControllerApi.md#findBedrockImageModelConfigByCode) | **GET** /api/admin/BedrockImageModelsConfigurationController/findBedrockImageModelConfigByCode | 
[**getBedrockImageModels**](BedrockImageModelsConfigurationControllerApi.md#getBedrockImageModels) | **POST** /api/admin/BedrockImageModelsConfigurationController/getBedrockImageModels | 
[**insertBedrockImageModelConfig**](BedrockImageModelsConfigurationControllerApi.md#insertBedrockImageModelConfig) | **POST** /api/admin/BedrockImageModelsConfigurationController/insertBedrockImageModelConfig | 
[**updateBedrockImageModelConfig**](BedrockImageModelsConfigurationControllerApi.md#updateBedrockImageModelConfig) | **POST** /api/admin/BedrockImageModelsConfigurationController/updateBedrockImageModelConfig | 

<a name="deleteBedrockImageModelConfig"></a>
# **deleteBedrockImageModelConfig**
> OperationStatusBoolean deleteBedrockImageModelConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.BedrockImageModelsConfigurationControllerApi;


BedrockImageModelsConfigurationControllerApi apiInstance = new BedrockImageModelsConfigurationControllerApi();
GBedrockImageModelConfig body = new GBedrockImageModelConfig(); // GBedrockImageModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteBedrockImageModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockImageModelsConfigurationControllerApi#deleteBedrockImageModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockImageModelConfig**](GBedrockImageModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findBedrockImageModelConfigByCode"></a>
# **findBedrockImageModelConfigByCode**
> GBedrockImageModelConfig findBedrockImageModelConfigByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.BedrockImageModelsConfigurationControllerApi;


BedrockImageModelsConfigurationControllerApi apiInstance = new BedrockImageModelsConfigurationControllerApi();
Object code = null; // Object | 
try {
    GBedrockImageModelConfig result = apiInstance.findBedrockImageModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockImageModelsConfigurationControllerApi#findBedrockImageModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  |

### Return type

[**GBedrockImageModelConfig**](GBedrockImageModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBedrockImageModels"></a>
# **getBedrockImageModels**
> OperationStatusListGBedrockImageModelChoice getBedrockImageModels(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.BedrockImageModelsConfigurationControllerApi;


BedrockImageModelsConfigurationControllerApi apiInstance = new BedrockImageModelsConfigurationControllerApi();
GBedrockImageModelConfig body = new GBedrockImageModelConfig(); // GBedrockImageModelConfig | 
try {
    OperationStatusListGBedrockImageModelChoice result = apiInstance.getBedrockImageModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockImageModelsConfigurationControllerApi#getBedrockImageModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockImageModelConfig**](GBedrockImageModelConfig.md)|  |

### Return type

[**OperationStatusListGBedrockImageModelChoice**](OperationStatusListGBedrockImageModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertBedrockImageModelConfig"></a>
# **insertBedrockImageModelConfig**
> OperationStatusGBedrockImageModelConfig insertBedrockImageModelConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.BedrockImageModelsConfigurationControllerApi;


BedrockImageModelsConfigurationControllerApi apiInstance = new BedrockImageModelsConfigurationControllerApi();
GBedrockImageModelConfig body = new GBedrockImageModelConfig(); // GBedrockImageModelConfig | 
try {
    OperationStatusGBedrockImageModelConfig result = apiInstance.insertBedrockImageModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockImageModelsConfigurationControllerApi#insertBedrockImageModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockImageModelConfig**](GBedrockImageModelConfig.md)|  |

### Return type

[**OperationStatusGBedrockImageModelConfig**](OperationStatusGBedrockImageModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateBedrockImageModelConfig"></a>
# **updateBedrockImageModelConfig**
> OperationStatusGBedrockImageModelConfig updateBedrockImageModelConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.BedrockImageModelsConfigurationControllerApi;


BedrockImageModelsConfigurationControllerApi apiInstance = new BedrockImageModelsConfigurationControllerApi();
GBedrockImageModelConfig body = new GBedrockImageModelConfig(); // GBedrockImageModelConfig | 
try {
    OperationStatusGBedrockImageModelConfig result = apiInstance.updateBedrockImageModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockImageModelsConfigurationControllerApi#updateBedrockImageModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockImageModelConfig**](GBedrockImageModelConfig.md)|  |

### Return type

[**OperationStatusGBedrockImageModelConfig**](OperationStatusGBedrockImageModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

