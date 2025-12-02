# DeepseekChatModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteDeepseekChatModelConfig**](DeepseekChatModelsConfigurationControllerApi.md#deleteDeepseekChatModelConfig) | **POST** /api/admin/DeepseekChatModelsConfigurationController/deleteDeepseekChatModelConfig | 
[**findDeepseekChatModelConfigByCode**](DeepseekChatModelsConfigurationControllerApi.md#findDeepseekChatModelConfigByCode) | **GET** /api/admin/DeepseekChatModelsConfigurationController/findDeepseekChatModelConfigByCode | 
[**getDeepseekChatModels**](DeepseekChatModelsConfigurationControllerApi.md#getDeepseekChatModels) | **POST** /api/admin/DeepseekChatModelsConfigurationController/getDeepseekModels | 
[**insertDeepseekChatModelConfig**](DeepseekChatModelsConfigurationControllerApi.md#insertDeepseekChatModelConfig) | **POST** /api/admin/DeepseekChatModelsConfigurationController/insertDeepseekChatModelConfig | 
[**updateDeepseekChatModelConfig**](DeepseekChatModelsConfigurationControllerApi.md#updateDeepseekChatModelConfig) | **POST** /api/admin/DeepseekChatModelsConfigurationController/updateDeepseekChatModelConfig | 

<a name="deleteDeepseekChatModelConfig"></a>
# **deleteDeepseekChatModelConfig**
> OperationStatusBoolean deleteDeepseekChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.DeepseekChatModelsConfigurationControllerApi;


DeepseekChatModelsConfigurationControllerApi apiInstance = new DeepseekChatModelsConfigurationControllerApi();
GDeepseekChatModelConfig body = new GDeepseekChatModelConfig(); // GDeepseekChatModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteDeepseekChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DeepseekChatModelsConfigurationControllerApi#deleteDeepseekChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GDeepseekChatModelConfig**](GDeepseekChatModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findDeepseekChatModelConfigByCode"></a>
# **findDeepseekChatModelConfigByCode**
> GDeepseekChatModelConfig findDeepseekChatModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.DeepseekChatModelsConfigurationControllerApi;


DeepseekChatModelsConfigurationControllerApi apiInstance = new DeepseekChatModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GDeepseekChatModelConfig result = apiInstance.findDeepseekChatModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DeepseekChatModelsConfigurationControllerApi#findDeepseekChatModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GDeepseekChatModelConfig**](GDeepseekChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getDeepseekChatModels"></a>
# **getDeepseekChatModels**
> OperationStatusListGDeepseekChatModelChoice getDeepseekChatModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.DeepseekChatModelsConfigurationControllerApi;


DeepseekChatModelsConfigurationControllerApi apiInstance = new DeepseekChatModelsConfigurationControllerApi();
GDeepseekChatModelConfig body = new GDeepseekChatModelConfig(); // GDeepseekChatModelConfig | 
try {
    OperationStatusListGDeepseekChatModelChoice result = apiInstance.getDeepseekChatModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DeepseekChatModelsConfigurationControllerApi#getDeepseekChatModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GDeepseekChatModelConfig**](GDeepseekChatModelConfig.md)|  |

### Return type

[**OperationStatusListGDeepseekChatModelChoice**](OperationStatusListGDeepseekChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertDeepseekChatModelConfig"></a>
# **insertDeepseekChatModelConfig**
> OperationStatusGDeepseekChatModelConfig insertDeepseekChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.DeepseekChatModelsConfigurationControllerApi;


DeepseekChatModelsConfigurationControllerApi apiInstance = new DeepseekChatModelsConfigurationControllerApi();
GDeepseekChatModelConfig body = new GDeepseekChatModelConfig(); // GDeepseekChatModelConfig | 
try {
    OperationStatusGDeepseekChatModelConfig result = apiInstance.insertDeepseekChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DeepseekChatModelsConfigurationControllerApi#insertDeepseekChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GDeepseekChatModelConfig**](GDeepseekChatModelConfig.md)|  |

### Return type

[**OperationStatusGDeepseekChatModelConfig**](OperationStatusGDeepseekChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateDeepseekChatModelConfig"></a>
# **updateDeepseekChatModelConfig**
> OperationStatusGDeepseekChatModelConfig updateDeepseekChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.DeepseekChatModelsConfigurationControllerApi;


DeepseekChatModelsConfigurationControllerApi apiInstance = new DeepseekChatModelsConfigurationControllerApi();
GDeepseekChatModelConfig body = new GDeepseekChatModelConfig(); // GDeepseekChatModelConfig | 
try {
    OperationStatusGDeepseekChatModelConfig result = apiInstance.updateDeepseekChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DeepseekChatModelsConfigurationControllerApi#updateDeepseekChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GDeepseekChatModelConfig**](GDeepseekChatModelConfig.md)|  |

### Return type

[**OperationStatusGDeepseekChatModelConfig**](OperationStatusGDeepseekChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

