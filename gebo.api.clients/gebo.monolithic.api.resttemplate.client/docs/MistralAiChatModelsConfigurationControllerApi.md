# MistralAiChatModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteMistralAIChatModelConfig**](MistralAiChatModelsConfigurationControllerApi.md#deleteMistralAIChatModelConfig) | **POST** /api/admin/MistralAIChatModelsConfigurationController/deleteMistralAIChatModelConfig | 
[**findMistralAIChatModelConfigByCode**](MistralAiChatModelsConfigurationControllerApi.md#findMistralAIChatModelConfigByCode) | **GET** /api/admin/MistralAIChatModelsConfigurationController/findMistralAIChatModelConfigByCode | 
[**getMistralAIChatModels**](MistralAiChatModelsConfigurationControllerApi.md#getMistralAIChatModels) | **POST** /api/admin/MistralAIChatModelsConfigurationController/getMistralAIChatModels | 
[**insertMistralAIChatModelConfig**](MistralAiChatModelsConfigurationControllerApi.md#insertMistralAIChatModelConfig) | **POST** /api/admin/MistralAIChatModelsConfigurationController/insertMistralAIChatModelConfig | 
[**updateMistralAIChatModelConfig**](MistralAiChatModelsConfigurationControllerApi.md#updateMistralAIChatModelConfig) | **POST** /api/admin/MistralAIChatModelsConfigurationController/updateMistralAIChatModelConfig | 

<a name="deleteMistralAIChatModelConfig"></a>
# **deleteMistralAIChatModelConfig**
> OperationStatusBoolean deleteMistralAIChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.MistralAiChatModelsConfigurationControllerApi;


MistralAiChatModelsConfigurationControllerApi apiInstance = new MistralAiChatModelsConfigurationControllerApi();
GMistralChatModelConfig body = new GMistralChatModelConfig(); // GMistralChatModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteMistralAIChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MistralAiChatModelsConfigurationControllerApi#deleteMistralAIChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GMistralChatModelConfig**](GMistralChatModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findMistralAIChatModelConfigByCode"></a>
# **findMistralAIChatModelConfigByCode**
> GMistralChatModelConfig findMistralAIChatModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.MistralAiChatModelsConfigurationControllerApi;


MistralAiChatModelsConfigurationControllerApi apiInstance = new MistralAiChatModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GMistralChatModelConfig result = apiInstance.findMistralAIChatModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MistralAiChatModelsConfigurationControllerApi#findMistralAIChatModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GMistralChatModelConfig**](GMistralChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMistralAIChatModels"></a>
# **getMistralAIChatModels**
> OperationStatusListGMistralChatModelChoice getMistralAIChatModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.MistralAiChatModelsConfigurationControllerApi;


MistralAiChatModelsConfigurationControllerApi apiInstance = new MistralAiChatModelsConfigurationControllerApi();
GMistralChatModelConfig body = new GMistralChatModelConfig(); // GMistralChatModelConfig | 
try {
    OperationStatusListGMistralChatModelChoice result = apiInstance.getMistralAIChatModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MistralAiChatModelsConfigurationControllerApi#getMistralAIChatModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GMistralChatModelConfig**](GMistralChatModelConfig.md)|  |

### Return type

[**OperationStatusListGMistralChatModelChoice**](OperationStatusListGMistralChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertMistralAIChatModelConfig"></a>
# **insertMistralAIChatModelConfig**
> OperationStatusGMistralChatModelConfig insertMistralAIChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.MistralAiChatModelsConfigurationControllerApi;


MistralAiChatModelsConfigurationControllerApi apiInstance = new MistralAiChatModelsConfigurationControllerApi();
GMistralChatModelConfig body = new GMistralChatModelConfig(); // GMistralChatModelConfig | 
try {
    OperationStatusGMistralChatModelConfig result = apiInstance.insertMistralAIChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MistralAiChatModelsConfigurationControllerApi#insertMistralAIChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GMistralChatModelConfig**](GMistralChatModelConfig.md)|  |

### Return type

[**OperationStatusGMistralChatModelConfig**](OperationStatusGMistralChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateMistralAIChatModelConfig"></a>
# **updateMistralAIChatModelConfig**
> OperationStatusGMistralChatModelConfig updateMistralAIChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.MistralAiChatModelsConfigurationControllerApi;


MistralAiChatModelsConfigurationControllerApi apiInstance = new MistralAiChatModelsConfigurationControllerApi();
GMistralChatModelConfig body = new GMistralChatModelConfig(); // GMistralChatModelConfig | 
try {
    OperationStatusGMistralChatModelConfig result = apiInstance.updateMistralAIChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MistralAiChatModelsConfigurationControllerApi#updateMistralAIChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GMistralChatModelConfig**](GMistralChatModelConfig.md)|  |

### Return type

[**OperationStatusGMistralChatModelConfig**](OperationStatusGMistralChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

