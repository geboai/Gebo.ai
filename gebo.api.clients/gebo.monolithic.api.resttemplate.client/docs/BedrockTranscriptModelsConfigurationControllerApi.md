# BedrockTranscriptModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteBedrockTranscriptModelConfig**](BedrockTranscriptModelsConfigurationControllerApi.md#deleteBedrockTranscriptModelConfig) | **POST** /api/admin/BedrockTranscriptModelsConfigurationController/deleteBedrockTranscriptModelConfig | 
[**findBedrockTranscriptModelConfigByCode**](BedrockTranscriptModelsConfigurationControllerApi.md#findBedrockTranscriptModelConfigByCode) | **GET** /api/admin/BedrockTranscriptModelsConfigurationController/findBedrockTranscriptModelConfigByCode | 
[**getBedrockTranscriptModels**](BedrockTranscriptModelsConfigurationControllerApi.md#getBedrockTranscriptModels) | **POST** /api/admin/BedrockTranscriptModelsConfigurationController/getBedrockTranscriptModels | 
[**insertBedrockTranscriptModelConfig**](BedrockTranscriptModelsConfigurationControllerApi.md#insertBedrockTranscriptModelConfig) | **POST** /api/admin/BedrockTranscriptModelsConfigurationController/insertBedrockTranscriptModelConfig | 
[**updateBedrockTranscriptModelConfig**](BedrockTranscriptModelsConfigurationControllerApi.md#updateBedrockTranscriptModelConfig) | **POST** /api/admin/BedrockTranscriptModelsConfigurationController/updateBedrockTranscriptModelConfig | 

<a name="deleteBedrockTranscriptModelConfig"></a>
# **deleteBedrockTranscriptModelConfig**
> OperationStatusBoolean deleteBedrockTranscriptModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockTranscriptModelsConfigurationControllerApi;


BedrockTranscriptModelsConfigurationControllerApi apiInstance = new BedrockTranscriptModelsConfigurationControllerApi();
GBedrockTranscriptModelConfig body = new GBedrockTranscriptModelConfig(); // GBedrockTranscriptModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteBedrockTranscriptModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockTranscriptModelsConfigurationControllerApi#deleteBedrockTranscriptModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockTranscriptModelConfig**](GBedrockTranscriptModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findBedrockTranscriptModelConfigByCode"></a>
# **findBedrockTranscriptModelConfigByCode**
> GBedrockTranscriptModelConfig findBedrockTranscriptModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockTranscriptModelsConfigurationControllerApi;


BedrockTranscriptModelsConfigurationControllerApi apiInstance = new BedrockTranscriptModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GBedrockTranscriptModelConfig result = apiInstance.findBedrockTranscriptModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockTranscriptModelsConfigurationControllerApi#findBedrockTranscriptModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GBedrockTranscriptModelConfig**](GBedrockTranscriptModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBedrockTranscriptModels"></a>
# **getBedrockTranscriptModels**
> OperationStatusListGBedrockTranscriptModelChoice getBedrockTranscriptModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockTranscriptModelsConfigurationControllerApi;


BedrockTranscriptModelsConfigurationControllerApi apiInstance = new BedrockTranscriptModelsConfigurationControllerApi();
GBedrockTranscriptModelConfig body = new GBedrockTranscriptModelConfig(); // GBedrockTranscriptModelConfig | 
try {
    OperationStatusListGBedrockTranscriptModelChoice result = apiInstance.getBedrockTranscriptModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockTranscriptModelsConfigurationControllerApi#getBedrockTranscriptModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockTranscriptModelConfig**](GBedrockTranscriptModelConfig.md)|  |

### Return type

[**OperationStatusListGBedrockTranscriptModelChoice**](OperationStatusListGBedrockTranscriptModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertBedrockTranscriptModelConfig"></a>
# **insertBedrockTranscriptModelConfig**
> OperationStatusGBedrockTranscriptModelConfig insertBedrockTranscriptModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockTranscriptModelsConfigurationControllerApi;


BedrockTranscriptModelsConfigurationControllerApi apiInstance = new BedrockTranscriptModelsConfigurationControllerApi();
GBedrockTranscriptModelConfig body = new GBedrockTranscriptModelConfig(); // GBedrockTranscriptModelConfig | 
try {
    OperationStatusGBedrockTranscriptModelConfig result = apiInstance.insertBedrockTranscriptModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockTranscriptModelsConfigurationControllerApi#insertBedrockTranscriptModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockTranscriptModelConfig**](GBedrockTranscriptModelConfig.md)|  |

### Return type

[**OperationStatusGBedrockTranscriptModelConfig**](OperationStatusGBedrockTranscriptModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateBedrockTranscriptModelConfig"></a>
# **updateBedrockTranscriptModelConfig**
> OperationStatusGBedrockTranscriptModelConfig updateBedrockTranscriptModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockTranscriptModelsConfigurationControllerApi;


BedrockTranscriptModelsConfigurationControllerApi apiInstance = new BedrockTranscriptModelsConfigurationControllerApi();
GBedrockTranscriptModelConfig body = new GBedrockTranscriptModelConfig(); // GBedrockTranscriptModelConfig | 
try {
    OperationStatusGBedrockTranscriptModelConfig result = apiInstance.updateBedrockTranscriptModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockTranscriptModelsConfigurationControllerApi#updateBedrockTranscriptModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockTranscriptModelConfig**](GBedrockTranscriptModelConfig.md)|  |

### Return type

[**OperationStatusGBedrockTranscriptModelConfig**](OperationStatusGBedrockTranscriptModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

