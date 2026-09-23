# AnthropicChatModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteAnthropicChatModelConfig**](AnthropicChatModelsConfigurationControllerApi.md#deleteAnthropicChatModelConfig) | **POST** /api/admin/AnthropicChatModelsConfigurationController/deleteAnthropicChatModelConfig | 
[**findAnthropicChatModelConfigByCode**](AnthropicChatModelsConfigurationControllerApi.md#findAnthropicChatModelConfigByCode) | **GET** /api/admin/AnthropicChatModelsConfigurationController/findAnthropicChatModelConfigByCode | 
[**getAnthropicChatModels**](AnthropicChatModelsConfigurationControllerApi.md#getAnthropicChatModels) | **POST** /api/admin/AnthropicChatModelsConfigurationController/getAnthropicModels | 
[**insertAnthropicChatModelConfig**](AnthropicChatModelsConfigurationControllerApi.md#insertAnthropicChatModelConfig) | **POST** /api/admin/AnthropicChatModelsConfigurationController/insertAnthropicChatModelConfig | 
[**updateAnthropicChatModelConfig**](AnthropicChatModelsConfigurationControllerApi.md#updateAnthropicChatModelConfig) | **POST** /api/admin/AnthropicChatModelsConfigurationController/updateAnthropicChatModelConfig | 

<a name="deleteAnthropicChatModelConfig"></a>
# **deleteAnthropicChatModelConfig**
> OperationStatusBoolean deleteAnthropicChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AnthropicChatModelsConfigurationControllerApi;


AnthropicChatModelsConfigurationControllerApi apiInstance = new AnthropicChatModelsConfigurationControllerApi();
GAnthropicChatModelConfig body = new GAnthropicChatModelConfig(); // GAnthropicChatModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteAnthropicChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AnthropicChatModelsConfigurationControllerApi#deleteAnthropicChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAnthropicChatModelConfig**](GAnthropicChatModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findAnthropicChatModelConfigByCode"></a>
# **findAnthropicChatModelConfigByCode**
> GAnthropicChatModelConfig findAnthropicChatModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AnthropicChatModelsConfigurationControllerApi;


AnthropicChatModelsConfigurationControllerApi apiInstance = new AnthropicChatModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GAnthropicChatModelConfig result = apiInstance.findAnthropicChatModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AnthropicChatModelsConfigurationControllerApi#findAnthropicChatModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GAnthropicChatModelConfig**](GAnthropicChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAnthropicChatModels"></a>
# **getAnthropicChatModels**
> OperationStatusListGAnthropicChatModelChoice getAnthropicChatModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AnthropicChatModelsConfigurationControllerApi;


AnthropicChatModelsConfigurationControllerApi apiInstance = new AnthropicChatModelsConfigurationControllerApi();
GAnthropicChatModelConfig body = new GAnthropicChatModelConfig(); // GAnthropicChatModelConfig | 
try {
    OperationStatusListGAnthropicChatModelChoice result = apiInstance.getAnthropicChatModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AnthropicChatModelsConfigurationControllerApi#getAnthropicChatModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAnthropicChatModelConfig**](GAnthropicChatModelConfig.md)|  |

### Return type

[**OperationStatusListGAnthropicChatModelChoice**](OperationStatusListGAnthropicChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertAnthropicChatModelConfig"></a>
# **insertAnthropicChatModelConfig**
> OperationStatusGAnthropicChatModelConfig insertAnthropicChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AnthropicChatModelsConfigurationControllerApi;


AnthropicChatModelsConfigurationControllerApi apiInstance = new AnthropicChatModelsConfigurationControllerApi();
GAnthropicChatModelConfig body = new GAnthropicChatModelConfig(); // GAnthropicChatModelConfig | 
try {
    OperationStatusGAnthropicChatModelConfig result = apiInstance.insertAnthropicChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AnthropicChatModelsConfigurationControllerApi#insertAnthropicChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAnthropicChatModelConfig**](GAnthropicChatModelConfig.md)|  |

### Return type

[**OperationStatusGAnthropicChatModelConfig**](OperationStatusGAnthropicChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateAnthropicChatModelConfig"></a>
# **updateAnthropicChatModelConfig**
> OperationStatusGAnthropicChatModelConfig updateAnthropicChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AnthropicChatModelsConfigurationControllerApi;


AnthropicChatModelsConfigurationControllerApi apiInstance = new AnthropicChatModelsConfigurationControllerApi();
GAnthropicChatModelConfig body = new GAnthropicChatModelConfig(); // GAnthropicChatModelConfig | 
try {
    OperationStatusGAnthropicChatModelConfig result = apiInstance.updateAnthropicChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AnthropicChatModelsConfigurationControllerApi#updateAnthropicChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAnthropicChatModelConfig**](GAnthropicChatModelConfig.md)|  |

### Return type

[**OperationStatusGAnthropicChatModelConfig**](OperationStatusGAnthropicChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

