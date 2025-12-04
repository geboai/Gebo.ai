# GenericOpenAiapiChatModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGenericOpenAIAPIChatModelConfig**](GenericOpenAiapiChatModelsConfigurationControllerApi.md#deleteGenericOpenAIAPIChatModelConfig) | **POST** /api/admin/GenericOpenAIAPIChatModelsConfigurationController/deleteGenericOpenAIAPIChatModelConfig | 
[**findGenericOpenAIAPIChatModelConfigByCode**](GenericOpenAiapiChatModelsConfigurationControllerApi.md#findGenericOpenAIAPIChatModelConfigByCode) | **GET** /api/admin/GenericOpenAIAPIChatModelsConfigurationController/findGenericOpenAIAPIChatModelConfigByCode | 
[**getGenericOpenAIAPIChatModels**](GenericOpenAiapiChatModelsConfigurationControllerApi.md#getGenericOpenAIAPIChatModels) | **POST** /api/admin/GenericOpenAIAPIChatModelsConfigurationController/getGenericOpenAIAPIChatModels | 
[**getGenericOpenAIChatModelTypes**](GenericOpenAiapiChatModelsConfigurationControllerApi.md#getGenericOpenAIChatModelTypes) | **GET** /api/admin/GenericOpenAIAPIChatModelsConfigurationController/getGenericOpenAIChatModelTypes | 
[**insertGenericOpenAIAPIChatModelConfig**](GenericOpenAiapiChatModelsConfigurationControllerApi.md#insertGenericOpenAIAPIChatModelConfig) | **POST** /api/admin/GenericOpenAIAPIChatModelsConfigurationController/insertGenericOpenAIAPIChatModelConfig | 
[**updateGenericOpenAIAPIChatModelConfig**](GenericOpenAiapiChatModelsConfigurationControllerApi.md#updateGenericOpenAIAPIChatModelConfig) | **POST** /api/admin/GenericOpenAIAPIChatModelsConfigurationController/updateGenericOpenAIAPIChatModelConfig | 

<a name="deleteGenericOpenAIAPIChatModelConfig"></a>
# **deleteGenericOpenAIAPIChatModelConfig**
> OperationStatusBoolean deleteGenericOpenAIAPIChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiChatModelsConfigurationControllerApi;


GenericOpenAiapiChatModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiChatModelsConfigurationControllerApi();
GenericOpenAIAPIChatModelConfig body = new GenericOpenAIAPIChatModelConfig(); // GenericOpenAIAPIChatModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteGenericOpenAIAPIChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiChatModelsConfigurationControllerApi#deleteGenericOpenAIAPIChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIChatModelConfig**](GenericOpenAIAPIChatModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGenericOpenAIAPIChatModelConfigByCode"></a>
# **findGenericOpenAIAPIChatModelConfigByCode**
> GenericOpenAIAPIChatModelConfig findGenericOpenAIAPIChatModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiChatModelsConfigurationControllerApi;


GenericOpenAiapiChatModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiChatModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GenericOpenAIAPIChatModelConfig result = apiInstance.findGenericOpenAIAPIChatModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiChatModelsConfigurationControllerApi#findGenericOpenAIAPIChatModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GenericOpenAIAPIChatModelConfig**](GenericOpenAIAPIChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGenericOpenAIAPIChatModels"></a>
# **getGenericOpenAIAPIChatModels**
> OperationStatusListGenericOpenAIAPIChatModelChoice getGenericOpenAIAPIChatModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiChatModelsConfigurationControllerApi;


GenericOpenAiapiChatModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiChatModelsConfigurationControllerApi();
GenericOpenAIAPIChatModelConfig body = new GenericOpenAIAPIChatModelConfig(); // GenericOpenAIAPIChatModelConfig | 
try {
    OperationStatusListGenericOpenAIAPIChatModelChoice result = apiInstance.getGenericOpenAIAPIChatModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiChatModelsConfigurationControllerApi#getGenericOpenAIAPIChatModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIChatModelConfig**](GenericOpenAIAPIChatModelConfig.md)|  |

### Return type

[**OperationStatusListGenericOpenAIAPIChatModelChoice**](OperationStatusListGenericOpenAIAPIChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getGenericOpenAIChatModelTypes"></a>
# **getGenericOpenAIChatModelTypes**
> List&lt;GenericOpenAIChatModelTypeConfig&gt; getGenericOpenAIChatModelTypes()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiChatModelsConfigurationControllerApi;


GenericOpenAiapiChatModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiChatModelsConfigurationControllerApi();
try {
    List<GenericOpenAIChatModelTypeConfig> result = apiInstance.getGenericOpenAIChatModelTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiChatModelsConfigurationControllerApi#getGenericOpenAIChatModelTypes");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GenericOpenAIChatModelTypeConfig&gt;**](GenericOpenAIChatModelTypeConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertGenericOpenAIAPIChatModelConfig"></a>
# **insertGenericOpenAIAPIChatModelConfig**
> OperationStatusGenericOpenAIAPIChatModelConfig insertGenericOpenAIAPIChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiChatModelsConfigurationControllerApi;


GenericOpenAiapiChatModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiChatModelsConfigurationControllerApi();
GenericOpenAIAPIChatModelConfig body = new GenericOpenAIAPIChatModelConfig(); // GenericOpenAIAPIChatModelConfig | 
try {
    OperationStatusGenericOpenAIAPIChatModelConfig result = apiInstance.insertGenericOpenAIAPIChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiChatModelsConfigurationControllerApi#insertGenericOpenAIAPIChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIChatModelConfig**](GenericOpenAIAPIChatModelConfig.md)|  |

### Return type

[**OperationStatusGenericOpenAIAPIChatModelConfig**](OperationStatusGenericOpenAIAPIChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGenericOpenAIAPIChatModelConfig"></a>
# **updateGenericOpenAIAPIChatModelConfig**
> OperationStatusGenericOpenAIAPIChatModelConfig updateGenericOpenAIAPIChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GenericOpenAiapiChatModelsConfigurationControllerApi;


GenericOpenAiapiChatModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiChatModelsConfigurationControllerApi();
GenericOpenAIAPIChatModelConfig body = new GenericOpenAIAPIChatModelConfig(); // GenericOpenAIAPIChatModelConfig | 
try {
    OperationStatusGenericOpenAIAPIChatModelConfig result = apiInstance.updateGenericOpenAIAPIChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiChatModelsConfigurationControllerApi#updateGenericOpenAIAPIChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIChatModelConfig**](GenericOpenAIAPIChatModelConfig.md)|  |

### Return type

[**OperationStatusGenericOpenAIAPIChatModelConfig**](OperationStatusGenericOpenAIAPIChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

