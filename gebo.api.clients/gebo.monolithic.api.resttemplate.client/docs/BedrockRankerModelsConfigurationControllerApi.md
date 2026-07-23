# BedrockRankerModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteBedrockRankerModelConfig**](BedrockRankerModelsConfigurationControllerApi.md#deleteBedrockRankerModelConfig) | **POST** /api/admin/BedrockRankerModelsConfigurationController/deleteBedrockRankerModelConfig | 
[**findBedrockRankerModelConfigByCode**](BedrockRankerModelsConfigurationControllerApi.md#findBedrockRankerModelConfigByCode) | **GET** /api/admin/BedrockRankerModelsConfigurationController/findBedrockRankerModelConfigByCode | 
[**getBedrockRankerModels**](BedrockRankerModelsConfigurationControllerApi.md#getBedrockRankerModels) | **POST** /api/admin/BedrockRankerModelsConfigurationController/getBedrockRankerModels | 
[**insertBedrockRankerModelConfig**](BedrockRankerModelsConfigurationControllerApi.md#insertBedrockRankerModelConfig) | **POST** /api/admin/BedrockRankerModelsConfigurationController/insertBedrockRankerModelConfig | 
[**updateBedrockRankerModelConfig**](BedrockRankerModelsConfigurationControllerApi.md#updateBedrockRankerModelConfig) | **POST** /api/admin/BedrockRankerModelsConfigurationController/updateBedrockRankerModelConfig | 

<a name="deleteBedrockRankerModelConfig"></a>
# **deleteBedrockRankerModelConfig**
> OperationStatusBoolean deleteBedrockRankerModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockRankerModelsConfigurationControllerApi;


BedrockRankerModelsConfigurationControllerApi apiInstance = new BedrockRankerModelsConfigurationControllerApi();
GBedrockRankerModelConfig body = new GBedrockRankerModelConfig(); // GBedrockRankerModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteBedrockRankerModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockRankerModelsConfigurationControllerApi#deleteBedrockRankerModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockRankerModelConfig**](GBedrockRankerModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findBedrockRankerModelConfigByCode"></a>
# **findBedrockRankerModelConfigByCode**
> GBedrockRankerModelConfig findBedrockRankerModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockRankerModelsConfigurationControllerApi;


BedrockRankerModelsConfigurationControllerApi apiInstance = new BedrockRankerModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GBedrockRankerModelConfig result = apiInstance.findBedrockRankerModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockRankerModelsConfigurationControllerApi#findBedrockRankerModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GBedrockRankerModelConfig**](GBedrockRankerModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBedrockRankerModels"></a>
# **getBedrockRankerModels**
> OperationStatusListGBedrockRankerModelChoice getBedrockRankerModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockRankerModelsConfigurationControllerApi;


BedrockRankerModelsConfigurationControllerApi apiInstance = new BedrockRankerModelsConfigurationControllerApi();
GBedrockRankerModelConfig body = new GBedrockRankerModelConfig(); // GBedrockRankerModelConfig | 
try {
    OperationStatusListGBedrockRankerModelChoice result = apiInstance.getBedrockRankerModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockRankerModelsConfigurationControllerApi#getBedrockRankerModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockRankerModelConfig**](GBedrockRankerModelConfig.md)|  |

### Return type

[**OperationStatusListGBedrockRankerModelChoice**](OperationStatusListGBedrockRankerModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertBedrockRankerModelConfig"></a>
# **insertBedrockRankerModelConfig**
> OperationStatusGBedrockRankerModelConfig insertBedrockRankerModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockRankerModelsConfigurationControllerApi;


BedrockRankerModelsConfigurationControllerApi apiInstance = new BedrockRankerModelsConfigurationControllerApi();
GBedrockRankerModelConfig body = new GBedrockRankerModelConfig(); // GBedrockRankerModelConfig | 
try {
    OperationStatusGBedrockRankerModelConfig result = apiInstance.insertBedrockRankerModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockRankerModelsConfigurationControllerApi#insertBedrockRankerModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockRankerModelConfig**](GBedrockRankerModelConfig.md)|  |

### Return type

[**OperationStatusGBedrockRankerModelConfig**](OperationStatusGBedrockRankerModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateBedrockRankerModelConfig"></a>
# **updateBedrockRankerModelConfig**
> OperationStatusGBedrockRankerModelConfig updateBedrockRankerModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockRankerModelsConfigurationControllerApi;


BedrockRankerModelsConfigurationControllerApi apiInstance = new BedrockRankerModelsConfigurationControllerApi();
GBedrockRankerModelConfig body = new GBedrockRankerModelConfig(); // GBedrockRankerModelConfig | 
try {
    OperationStatusGBedrockRankerModelConfig result = apiInstance.updateBedrockRankerModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockRankerModelsConfigurationControllerApi#updateBedrockRankerModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockRankerModelConfig**](GBedrockRankerModelConfig.md)|  |

### Return type

[**OperationStatusGBedrockRankerModelConfig**](OperationStatusGBedrockRankerModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

