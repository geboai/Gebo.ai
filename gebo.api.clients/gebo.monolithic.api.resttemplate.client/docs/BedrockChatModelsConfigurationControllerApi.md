# BedrockChatModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteBedrockChatModelConfig**](BedrockChatModelsConfigurationControllerApi.md#deleteBedrockChatModelConfig) | **POST** /api/admin/BedrockChatModelsConfigurationController/deleteBedrockChatModelConfig | 
[**findBedrockChatModelConfigByCode**](BedrockChatModelsConfigurationControllerApi.md#findBedrockChatModelConfigByCode) | **GET** /api/admin/BedrockChatModelsConfigurationController/findBedrockChatModelConfigByCode | 
[**getBedrockChatModels**](BedrockChatModelsConfigurationControllerApi.md#getBedrockChatModels) | **POST** /api/admin/BedrockChatModelsConfigurationController/getBedrockChatModels | 
[**insertBedrockChatModelConfig**](BedrockChatModelsConfigurationControllerApi.md#insertBedrockChatModelConfig) | **POST** /api/admin/BedrockChatModelsConfigurationController/insertBedrockChatModelConfig | 
[**updateBedrockChatModelConfig**](BedrockChatModelsConfigurationControllerApi.md#updateBedrockChatModelConfig) | **POST** /api/admin/BedrockChatModelsConfigurationController/updateBedrockChatModelConfig | 

<a name="deleteBedrockChatModelConfig"></a>
# **deleteBedrockChatModelConfig**
> OperationStatusBoolean deleteBedrockChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockChatModelsConfigurationControllerApi;


BedrockChatModelsConfigurationControllerApi apiInstance = new BedrockChatModelsConfigurationControllerApi();
GBedrockChatModelConfig body = new GBedrockChatModelConfig(); // GBedrockChatModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteBedrockChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockChatModelsConfigurationControllerApi#deleteBedrockChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockChatModelConfig**](GBedrockChatModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findBedrockChatModelConfigByCode"></a>
# **findBedrockChatModelConfigByCode**
> GBedrockChatModelConfig findBedrockChatModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockChatModelsConfigurationControllerApi;


BedrockChatModelsConfigurationControllerApi apiInstance = new BedrockChatModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GBedrockChatModelConfig result = apiInstance.findBedrockChatModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockChatModelsConfigurationControllerApi#findBedrockChatModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GBedrockChatModelConfig**](GBedrockChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBedrockChatModels"></a>
# **getBedrockChatModels**
> OperationStatusListGBedrockChatModelChoice getBedrockChatModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockChatModelsConfigurationControllerApi;


BedrockChatModelsConfigurationControllerApi apiInstance = new BedrockChatModelsConfigurationControllerApi();
GBedrockChatModelConfig body = new GBedrockChatModelConfig(); // GBedrockChatModelConfig | 
try {
    OperationStatusListGBedrockChatModelChoice result = apiInstance.getBedrockChatModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockChatModelsConfigurationControllerApi#getBedrockChatModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockChatModelConfig**](GBedrockChatModelConfig.md)|  |

### Return type

[**OperationStatusListGBedrockChatModelChoice**](OperationStatusListGBedrockChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertBedrockChatModelConfig"></a>
# **insertBedrockChatModelConfig**
> OperationStatusGBedrockChatModelConfig insertBedrockChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockChatModelsConfigurationControllerApi;


BedrockChatModelsConfigurationControllerApi apiInstance = new BedrockChatModelsConfigurationControllerApi();
GBedrockChatModelConfig body = new GBedrockChatModelConfig(); // GBedrockChatModelConfig | 
try {
    OperationStatusGBedrockChatModelConfig result = apiInstance.insertBedrockChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockChatModelsConfigurationControllerApi#insertBedrockChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockChatModelConfig**](GBedrockChatModelConfig.md)|  |

### Return type

[**OperationStatusGBedrockChatModelConfig**](OperationStatusGBedrockChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateBedrockChatModelConfig"></a>
# **updateBedrockChatModelConfig**
> OperationStatusGBedrockChatModelConfig updateBedrockChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BedrockChatModelsConfigurationControllerApi;


BedrockChatModelsConfigurationControllerApi apiInstance = new BedrockChatModelsConfigurationControllerApi();
GBedrockChatModelConfig body = new GBedrockChatModelConfig(); // GBedrockChatModelConfig | 
try {
    OperationStatusGBedrockChatModelConfig result = apiInstance.updateBedrockChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BedrockChatModelsConfigurationControllerApi#updateBedrockChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockChatModelConfig**](GBedrockChatModelConfig.md)|  |

### Return type

[**OperationStatusGBedrockChatModelConfig**](OperationStatusGBedrockChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

