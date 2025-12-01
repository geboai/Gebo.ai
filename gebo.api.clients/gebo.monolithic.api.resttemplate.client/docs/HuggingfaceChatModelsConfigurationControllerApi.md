# HuggingfaceChatModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteHuggingfaceChatModelConfig**](HuggingfaceChatModelsConfigurationControllerApi.md#deleteHuggingfaceChatModelConfig) | **POST** /api/admin/HuggingfaceChatModelsConfigurationController/deleteHuggingfaceChatModelConfig | 
[**findHuggingfaceChatModelConfigByCode**](HuggingfaceChatModelsConfigurationControllerApi.md#findHuggingfaceChatModelConfigByCode) | **GET** /api/admin/HuggingfaceChatModelsConfigurationController/findHuggingfaceChatModelConfigByCode | 
[**getHuggingfaceChatModels**](HuggingfaceChatModelsConfigurationControllerApi.md#getHuggingfaceChatModels) | **POST** /api/admin/HuggingfaceChatModelsConfigurationController/getHuggingfaceModels | 
[**insertHuggingfaceChatModelConfig**](HuggingfaceChatModelsConfigurationControllerApi.md#insertHuggingfaceChatModelConfig) | **POST** /api/admin/HuggingfaceChatModelsConfigurationController/insertHuggingfaceChatModelConfig | 
[**updateHuggingfaceChatModelConfig**](HuggingfaceChatModelsConfigurationControllerApi.md#updateHuggingfaceChatModelConfig) | **POST** /api/admin/HuggingfaceChatModelsConfigurationController/updateHuggingfaceChatModelConfig | 

<a name="deleteHuggingfaceChatModelConfig"></a>
# **deleteHuggingfaceChatModelConfig**
> OperationStatusBoolean deleteHuggingfaceChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.HuggingfaceChatModelsConfigurationControllerApi;


HuggingfaceChatModelsConfigurationControllerApi apiInstance = new HuggingfaceChatModelsConfigurationControllerApi();
GHuggingfaceChatModelConfig body = new GHuggingfaceChatModelConfig(); // GHuggingfaceChatModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteHuggingfaceChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling HuggingfaceChatModelsConfigurationControllerApi#deleteHuggingfaceChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GHuggingfaceChatModelConfig**](GHuggingfaceChatModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findHuggingfaceChatModelConfigByCode"></a>
# **findHuggingfaceChatModelConfigByCode**
> GHuggingfaceChatModelConfig findHuggingfaceChatModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.HuggingfaceChatModelsConfigurationControllerApi;


HuggingfaceChatModelsConfigurationControllerApi apiInstance = new HuggingfaceChatModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GHuggingfaceChatModelConfig result = apiInstance.findHuggingfaceChatModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling HuggingfaceChatModelsConfigurationControllerApi#findHuggingfaceChatModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GHuggingfaceChatModelConfig**](GHuggingfaceChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getHuggingfaceChatModels"></a>
# **getHuggingfaceChatModels**
> OperationStatusListGHuggingfaceChatModelChoice getHuggingfaceChatModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.HuggingfaceChatModelsConfigurationControllerApi;


HuggingfaceChatModelsConfigurationControllerApi apiInstance = new HuggingfaceChatModelsConfigurationControllerApi();
GHuggingfaceChatModelConfig body = new GHuggingfaceChatModelConfig(); // GHuggingfaceChatModelConfig | 
try {
    OperationStatusListGHuggingfaceChatModelChoice result = apiInstance.getHuggingfaceChatModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling HuggingfaceChatModelsConfigurationControllerApi#getHuggingfaceChatModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GHuggingfaceChatModelConfig**](GHuggingfaceChatModelConfig.md)|  |

### Return type

[**OperationStatusListGHuggingfaceChatModelChoice**](OperationStatusListGHuggingfaceChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertHuggingfaceChatModelConfig"></a>
# **insertHuggingfaceChatModelConfig**
> OperationStatusGHuggingfaceChatModelConfig insertHuggingfaceChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.HuggingfaceChatModelsConfigurationControllerApi;


HuggingfaceChatModelsConfigurationControllerApi apiInstance = new HuggingfaceChatModelsConfigurationControllerApi();
GHuggingfaceChatModelConfig body = new GHuggingfaceChatModelConfig(); // GHuggingfaceChatModelConfig | 
try {
    OperationStatusGHuggingfaceChatModelConfig result = apiInstance.insertHuggingfaceChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling HuggingfaceChatModelsConfigurationControllerApi#insertHuggingfaceChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GHuggingfaceChatModelConfig**](GHuggingfaceChatModelConfig.md)|  |

### Return type

[**OperationStatusGHuggingfaceChatModelConfig**](OperationStatusGHuggingfaceChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateHuggingfaceChatModelConfig"></a>
# **updateHuggingfaceChatModelConfig**
> OperationStatusGHuggingfaceChatModelConfig updateHuggingfaceChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.HuggingfaceChatModelsConfigurationControllerApi;


HuggingfaceChatModelsConfigurationControllerApi apiInstance = new HuggingfaceChatModelsConfigurationControllerApi();
GHuggingfaceChatModelConfig body = new GHuggingfaceChatModelConfig(); // GHuggingfaceChatModelConfig | 
try {
    OperationStatusGHuggingfaceChatModelConfig result = apiInstance.updateHuggingfaceChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling HuggingfaceChatModelsConfigurationControllerApi#updateHuggingfaceChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GHuggingfaceChatModelConfig**](GHuggingfaceChatModelConfig.md)|  |

### Return type

[**OperationStatusGHuggingfaceChatModelConfig**](OperationStatusGHuggingfaceChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

