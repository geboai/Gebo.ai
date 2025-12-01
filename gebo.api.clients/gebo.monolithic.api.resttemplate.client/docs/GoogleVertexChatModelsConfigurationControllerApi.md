# GoogleVertexChatModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGoogleVertexChatModelConfig**](GoogleVertexChatModelsConfigurationControllerApi.md#deleteGoogleVertexChatModelConfig) | **POST** /api/admin/GoogleVertexModelsConfigurationController/deleteGoogleVertexChatModelConfig | 
[**findGoogleVertexChatModelConfigByCode**](GoogleVertexChatModelsConfigurationControllerApi.md#findGoogleVertexChatModelConfigByCode) | **GET** /api/admin/GoogleVertexModelsConfigurationController/findGoogleVertexChatModelConfigByCode | 
[**getGoogleVertexChatModels**](GoogleVertexChatModelsConfigurationControllerApi.md#getGoogleVertexChatModels) | **POST** /api/admin/GoogleVertexModelsConfigurationController/getGoogleVertexChatModels | 
[**insertGoogleVertexChatModelConfig**](GoogleVertexChatModelsConfigurationControllerApi.md#insertGoogleVertexChatModelConfig) | **POST** /api/admin/GoogleVertexModelsConfigurationController/insertGoogleVertexChatModelConfig | 
[**updateGoogleVertexChatModelConfig**](GoogleVertexChatModelsConfigurationControllerApi.md#updateGoogleVertexChatModelConfig) | **POST** /api/admin/GoogleVertexModelsConfigurationController/updateGoogleVertexChatModelConfig | 

<a name="deleteGoogleVertexChatModelConfig"></a>
# **deleteGoogleVertexChatModelConfig**
> OperationStatusBoolean deleteGoogleVertexChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleVertexChatModelsConfigurationControllerApi;


GoogleVertexChatModelsConfigurationControllerApi apiInstance = new GoogleVertexChatModelsConfigurationControllerApi();
GGoogleVertexChatModelConfig body = new GGoogleVertexChatModelConfig(); // GGoogleVertexChatModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteGoogleVertexChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleVertexChatModelsConfigurationControllerApi#deleteGoogleVertexChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleVertexChatModelConfig**](GGoogleVertexChatModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGoogleVertexChatModelConfigByCode"></a>
# **findGoogleVertexChatModelConfigByCode**
> GGoogleVertexChatModelConfig findGoogleVertexChatModelConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleVertexChatModelsConfigurationControllerApi;


GoogleVertexChatModelsConfigurationControllerApi apiInstance = new GoogleVertexChatModelsConfigurationControllerApi();
String code = "code_example"; // String | 
try {
    GGoogleVertexChatModelConfig result = apiInstance.findGoogleVertexChatModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleVertexChatModelsConfigurationControllerApi#findGoogleVertexChatModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GGoogleVertexChatModelConfig**](GGoogleVertexChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGoogleVertexChatModels"></a>
# **getGoogleVertexChatModels**
> OperationStatusListGGoogleVertexChatModelChoice getGoogleVertexChatModels(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleVertexChatModelsConfigurationControllerApi;


GoogleVertexChatModelsConfigurationControllerApi apiInstance = new GoogleVertexChatModelsConfigurationControllerApi();
GGoogleVertexChatModelConfig body = new GGoogleVertexChatModelConfig(); // GGoogleVertexChatModelConfig | 
try {
    OperationStatusListGGoogleVertexChatModelChoice result = apiInstance.getGoogleVertexChatModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleVertexChatModelsConfigurationControllerApi#getGoogleVertexChatModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleVertexChatModelConfig**](GGoogleVertexChatModelConfig.md)|  |

### Return type

[**OperationStatusListGGoogleVertexChatModelChoice**](OperationStatusListGGoogleVertexChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertGoogleVertexChatModelConfig"></a>
# **insertGoogleVertexChatModelConfig**
> OperationStatusGGoogleVertexChatModelConfig insertGoogleVertexChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleVertexChatModelsConfigurationControllerApi;


GoogleVertexChatModelsConfigurationControllerApi apiInstance = new GoogleVertexChatModelsConfigurationControllerApi();
GGoogleVertexChatModelConfig body = new GGoogleVertexChatModelConfig(); // GGoogleVertexChatModelConfig | 
try {
    OperationStatusGGoogleVertexChatModelConfig result = apiInstance.insertGoogleVertexChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleVertexChatModelsConfigurationControllerApi#insertGoogleVertexChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleVertexChatModelConfig**](GGoogleVertexChatModelConfig.md)|  |

### Return type

[**OperationStatusGGoogleVertexChatModelConfig**](OperationStatusGGoogleVertexChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGoogleVertexChatModelConfig"></a>
# **updateGoogleVertexChatModelConfig**
> OperationStatusGGoogleVertexChatModelConfig updateGoogleVertexChatModelConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleVertexChatModelsConfigurationControllerApi;


GoogleVertexChatModelsConfigurationControllerApi apiInstance = new GoogleVertexChatModelsConfigurationControllerApi();
GGoogleVertexChatModelConfig body = new GGoogleVertexChatModelConfig(); // GGoogleVertexChatModelConfig | 
try {
    OperationStatusGGoogleVertexChatModelConfig result = apiInstance.updateGoogleVertexChatModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleVertexChatModelsConfigurationControllerApi#updateGoogleVertexChatModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleVertexChatModelConfig**](GGoogleVertexChatModelConfig.md)|  |

### Return type

[**OperationStatusGGoogleVertexChatModelConfig**](OperationStatusGGoogleVertexChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

