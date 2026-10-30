# BedrockTextToSpeechModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteBedrockTextToSpeechModelConfig**](BedrockTextToSpeechModelsConfigurationControllerApi.md#deleteBedrockTextToSpeechModelConfig) | **POST** /api/admin/BedrockTextToSpeechModelsConfigurationController/deleteBedrockTextToSpeechModelConfig | 
[**findBedrockTextToSpeechModelConfigByCode**](BedrockTextToSpeechModelsConfigurationControllerApi.md#findBedrockTextToSpeechModelConfigByCode) | **GET** /api/admin/BedrockTextToSpeechModelsConfigurationController/findBedrockTextToSpeechModelConfigByCode | 
[**getBedrockTextToSpeechModels**](BedrockTextToSpeechModelsConfigurationControllerApi.md#getBedrockTextToSpeechModels) | **POST** /api/admin/BedrockTextToSpeechModelsConfigurationController/getBedrockTextToSpeechModels | 
[**insertBedrockTextToSpeechModelConfig**](BedrockTextToSpeechModelsConfigurationControllerApi.md#insertBedrockTextToSpeechModelConfig) | **POST** /api/admin/BedrockTextToSpeechModelsConfigurationController/insertBedrockTextToSpeechModelConfig | 
[**updateBedrockTextToSpeechModelConfig**](BedrockTextToSpeechModelsConfigurationControllerApi.md#updateBedrockTextToSpeechModelConfig) | **POST** /api/admin/BedrockTextToSpeechModelsConfigurationController/updateBedrockTextToSpeechModelConfig | 

<a name="deleteBedrockTextToSpeechModelConfig"></a>
# **deleteBedrockTextToSpeechModelConfig**
> OperationStatusBoolean deleteBedrockTextToSpeechModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockTextToSpeechModelsConfigurationControllerApi;


BedrockTextToSpeechModelsConfigurationControllerApi apiInstance = new BedrockTextToSpeechModelsConfigurationControllerApi();
GBedrockTextToSpeechModelConfig body = new GBedrockTextToSpeechModelConfig(); // GBedrockTextToSpeechModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteBedrockTextToSpeechModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockTextToSpeechModelsConfigurationControllerApi#deleteBedrockTextToSpeechModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockTextToSpeechModelConfig**](GBedrockTextToSpeechModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findBedrockTextToSpeechModelConfigByCode"></a>
# **findBedrockTextToSpeechModelConfigByCode**
> GBedrockTextToSpeechModelConfig findBedrockTextToSpeechModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockTextToSpeechModelsConfigurationControllerApi;


BedrockTextToSpeechModelsConfigurationControllerApi apiInstance = new BedrockTextToSpeechModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GBedrockTextToSpeechModelConfig result = apiInstance.findBedrockTextToSpeechModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockTextToSpeechModelsConfigurationControllerApi#findBedrockTextToSpeechModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GBedrockTextToSpeechModelConfig**](GBedrockTextToSpeechModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBedrockTextToSpeechModels"></a>
# **getBedrockTextToSpeechModels**
> OperationStatusListGBedrockTextToSpeechModelChoice getBedrockTextToSpeechModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockTextToSpeechModelsConfigurationControllerApi;


BedrockTextToSpeechModelsConfigurationControllerApi apiInstance = new BedrockTextToSpeechModelsConfigurationControllerApi();
GBedrockTextToSpeechModelConfig body = new GBedrockTextToSpeechModelConfig(); // GBedrockTextToSpeechModelConfig | 
try {
    OperationStatusListGBedrockTextToSpeechModelChoice result = apiInstance.getBedrockTextToSpeechModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockTextToSpeechModelsConfigurationControllerApi#getBedrockTextToSpeechModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockTextToSpeechModelConfig**](GBedrockTextToSpeechModelConfig.md)|  |

### Return type

[**OperationStatusListGBedrockTextToSpeechModelChoice**](OperationStatusListGBedrockTextToSpeechModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertBedrockTextToSpeechModelConfig"></a>
# **insertBedrockTextToSpeechModelConfig**
> OperationStatusGBedrockTextToSpeechModelConfig insertBedrockTextToSpeechModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockTextToSpeechModelsConfigurationControllerApi;


BedrockTextToSpeechModelsConfigurationControllerApi apiInstance = new BedrockTextToSpeechModelsConfigurationControllerApi();
GBedrockTextToSpeechModelConfig body = new GBedrockTextToSpeechModelConfig(); // GBedrockTextToSpeechModelConfig | 
try {
    OperationStatusGBedrockTextToSpeechModelConfig result = apiInstance.insertBedrockTextToSpeechModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockTextToSpeechModelsConfigurationControllerApi#insertBedrockTextToSpeechModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockTextToSpeechModelConfig**](GBedrockTextToSpeechModelConfig.md)|  |

### Return type

[**OperationStatusGBedrockTextToSpeechModelConfig**](OperationStatusGBedrockTextToSpeechModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateBedrockTextToSpeechModelConfig"></a>
# **updateBedrockTextToSpeechModelConfig**
> OperationStatusGBedrockTextToSpeechModelConfig updateBedrockTextToSpeechModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockTextToSpeechModelsConfigurationControllerApi;


BedrockTextToSpeechModelsConfigurationControllerApi apiInstance = new BedrockTextToSpeechModelsConfigurationControllerApi();
GBedrockTextToSpeechModelConfig body = new GBedrockTextToSpeechModelConfig(); // GBedrockTextToSpeechModelConfig | 
try {
    OperationStatusGBedrockTextToSpeechModelConfig result = apiInstance.updateBedrockTextToSpeechModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockTextToSpeechModelsConfigurationControllerApi#updateBedrockTextToSpeechModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockTextToSpeechModelConfig**](GBedrockTextToSpeechModelConfig.md)|  |

### Return type

[**OperationStatusGBedrockTextToSpeechModelConfig**](OperationStatusGBedrockTextToSpeechModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

