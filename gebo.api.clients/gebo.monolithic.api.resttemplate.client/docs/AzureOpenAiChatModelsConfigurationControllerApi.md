# AzureOpenAiChatModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteAzureOpenAIChatModelConfig**](AzureOpenAiChatModelsConfigurationControllerApi.md#deleteAzureOpenAIChatModelConfig) | **POST** /api/admin/AzureOpenAIModelsConfigurationController/deleteAzureOpenAIChatModelConfig | 
[**findAzureOpenAIChatModelConfigByCode**](AzureOpenAiChatModelsConfigurationControllerApi.md#findAzureOpenAIChatModelConfigByCode) | **GET** /api/admin/AzureOpenAIModelsConfigurationController/findAzureOpenAIChatModelConfigByCode | 
[**getAzureOpenAIChatModels**](AzureOpenAiChatModelsConfigurationControllerApi.md#getAzureOpenAIChatModels) | **POST** /api/admin/AzureOpenAIModelsConfigurationController/getAzureOpenAIChatModels | 
[**insertAzureOpenAIChatModelConfig**](AzureOpenAiChatModelsConfigurationControllerApi.md#insertAzureOpenAIChatModelConfig) | **POST** /api/admin/AzureOpenAIModelsConfigurationController/insertAzureOpenAIChatModelConfig | 
[**updateAzureOpenAIChatModelConfig**](AzureOpenAiChatModelsConfigurationControllerApi.md#updateAzureOpenAIChatModelConfig) | **POST** /api/admin/AzureOpenAIModelsConfigurationController/updateAzureOpenAIChatModelConfig | 

<a name="deleteAzureOpenAIChatModelConfig"></a>
# **deleteAzureOpenAIChatModelConfig**
> OperationStatusBoolean deleteAzureOpenAIChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AzureOpenAiChatModelsConfigurationControllerApi;


AzureOpenAiChatModelsConfigurationControllerApi apiInstance = new AzureOpenAiChatModelsConfigurationControllerApi();
GAzureOpenAIChatModelConfig body = new GAzureOpenAIChatModelConfig(); // GAzureOpenAIChatModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteAzureOpenAIChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AzureOpenAiChatModelsConfigurationControllerApi#deleteAzureOpenAIChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAzureOpenAIChatModelConfig**](GAzureOpenAIChatModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findAzureOpenAIChatModelConfigByCode"></a>
# **findAzureOpenAIChatModelConfigByCode**
> GAzureOpenAIChatModelConfig findAzureOpenAIChatModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AzureOpenAiChatModelsConfigurationControllerApi;


AzureOpenAiChatModelsConfigurationControllerApi apiInstance = new AzureOpenAiChatModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GAzureOpenAIChatModelConfig result = apiInstance.findAzureOpenAIChatModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AzureOpenAiChatModelsConfigurationControllerApi#findAzureOpenAIChatModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GAzureOpenAIChatModelConfig**](GAzureOpenAIChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAzureOpenAIChatModels"></a>
# **getAzureOpenAIChatModels**
> OperationStatusListGAzureOpenAIChatModelChoice getAzureOpenAIChatModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AzureOpenAiChatModelsConfigurationControllerApi;


AzureOpenAiChatModelsConfigurationControllerApi apiInstance = new AzureOpenAiChatModelsConfigurationControllerApi();
GAzureOpenAIChatModelConfig body = new GAzureOpenAIChatModelConfig(); // GAzureOpenAIChatModelConfig | 
try {
    OperationStatusListGAzureOpenAIChatModelChoice result = apiInstance.getAzureOpenAIChatModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AzureOpenAiChatModelsConfigurationControllerApi#getAzureOpenAIChatModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAzureOpenAIChatModelConfig**](GAzureOpenAIChatModelConfig.md)|  |

### Return type

[**OperationStatusListGAzureOpenAIChatModelChoice**](OperationStatusListGAzureOpenAIChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertAzureOpenAIChatModelConfig"></a>
# **insertAzureOpenAIChatModelConfig**
> OperationStatusGAzureOpenAIChatModelConfig insertAzureOpenAIChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AzureOpenAiChatModelsConfigurationControllerApi;


AzureOpenAiChatModelsConfigurationControllerApi apiInstance = new AzureOpenAiChatModelsConfigurationControllerApi();
GAzureOpenAIChatModelConfig body = new GAzureOpenAIChatModelConfig(); // GAzureOpenAIChatModelConfig | 
try {
    OperationStatusGAzureOpenAIChatModelConfig result = apiInstance.insertAzureOpenAIChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AzureOpenAiChatModelsConfigurationControllerApi#insertAzureOpenAIChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAzureOpenAIChatModelConfig**](GAzureOpenAIChatModelConfig.md)|  |

### Return type

[**OperationStatusGAzureOpenAIChatModelConfig**](OperationStatusGAzureOpenAIChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateAzureOpenAIChatModelConfig"></a>
# **updateAzureOpenAIChatModelConfig**
> OperationStatusGAzureOpenAIChatModelConfig updateAzureOpenAIChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AzureOpenAiChatModelsConfigurationControllerApi;


AzureOpenAiChatModelsConfigurationControllerApi apiInstance = new AzureOpenAiChatModelsConfigurationControllerApi();
GAzureOpenAIChatModelConfig body = new GAzureOpenAIChatModelConfig(); // GAzureOpenAIChatModelConfig | 
try {
    OperationStatusGAzureOpenAIChatModelConfig result = apiInstance.updateAzureOpenAIChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AzureOpenAiChatModelsConfigurationControllerApi#updateAzureOpenAIChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAzureOpenAIChatModelConfig**](GAzureOpenAIChatModelConfig.md)|  |

### Return type

[**OperationStatusGAzureOpenAIChatModelConfig**](OperationStatusGAzureOpenAIChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

