# OpenAiTranscriptModelsConfigurationControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteOpenAITranscriptModelConfig**](OpenAiTranscriptModelsConfigurationControllerApi.md#deleteOpenAITranscriptModelConfig) | **POST** /api/admin/OpenAITranscriptModelsConfigurationController/deleteOpenAITranscriptModelConfig | 
[**findOpenAITranscriptModelConfigByCode**](OpenAiTranscriptModelsConfigurationControllerApi.md#findOpenAITranscriptModelConfigByCode) | **GET** /api/admin/OpenAITranscriptModelsConfigurationController/findOpenAITranscriptModelConfigByCode | 
[**getOpenAITranscriptModels**](OpenAiTranscriptModelsConfigurationControllerApi.md#getOpenAITranscriptModels) | **POST** /api/admin/OpenAITranscriptModelsConfigurationController/getOpenAITranscriptModels | 
[**insertOpenAITranscriptModelConfig**](OpenAiTranscriptModelsConfigurationControllerApi.md#insertOpenAITranscriptModelConfig) | **POST** /api/admin/OpenAITranscriptModelsConfigurationController/insertOpenAITranscriptModelConfig | 
[**updateOpenAITranscriptModelConfig**](OpenAiTranscriptModelsConfigurationControllerApi.md#updateOpenAITranscriptModelConfig) | **POST** /api/admin/OpenAITranscriptModelsConfigurationController/updateOpenAITranscriptModelConfig | 

<a name="deleteOpenAITranscriptModelConfig"></a>
# **deleteOpenAITranscriptModelConfig**
> OperationStatusBoolean deleteOpenAITranscriptModelConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.OpenAiTranscriptModelsConfigurationControllerApi;


OpenAiTranscriptModelsConfigurationControllerApi apiInstance = new OpenAiTranscriptModelsConfigurationControllerApi();
GOpenAITranscriptModelConfig body = new GOpenAITranscriptModelConfig(); // GOpenAITranscriptModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteOpenAITranscriptModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiTranscriptModelsConfigurationControllerApi#deleteOpenAITranscriptModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAITranscriptModelConfig**](GOpenAITranscriptModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findOpenAITranscriptModelConfigByCode"></a>
# **findOpenAITranscriptModelConfigByCode**
> GOpenAITranscriptModelConfig findOpenAITranscriptModelConfigByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.OpenAiTranscriptModelsConfigurationControllerApi;


OpenAiTranscriptModelsConfigurationControllerApi apiInstance = new OpenAiTranscriptModelsConfigurationControllerApi();
Object code = null; // Object | 
try {
    GOpenAITranscriptModelConfig result = apiInstance.findOpenAITranscriptModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiTranscriptModelsConfigurationControllerApi#findOpenAITranscriptModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  |

### Return type

[**GOpenAITranscriptModelConfig**](GOpenAITranscriptModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getOpenAITranscriptModels"></a>
# **getOpenAITranscriptModels**
> OperationStatusListGOpenAITranscriptModelChoice getOpenAITranscriptModels(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.OpenAiTranscriptModelsConfigurationControllerApi;


OpenAiTranscriptModelsConfigurationControllerApi apiInstance = new OpenAiTranscriptModelsConfigurationControllerApi();
GOpenAITranscriptModelConfig body = new GOpenAITranscriptModelConfig(); // GOpenAITranscriptModelConfig | 
try {
    OperationStatusListGOpenAITranscriptModelChoice result = apiInstance.getOpenAITranscriptModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiTranscriptModelsConfigurationControllerApi#getOpenAITranscriptModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAITranscriptModelConfig**](GOpenAITranscriptModelConfig.md)|  |

### Return type

[**OperationStatusListGOpenAITranscriptModelChoice**](OperationStatusListGOpenAITranscriptModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertOpenAITranscriptModelConfig"></a>
# **insertOpenAITranscriptModelConfig**
> OperationStatusGOpenAITranscriptModelConfig insertOpenAITranscriptModelConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.OpenAiTranscriptModelsConfigurationControllerApi;


OpenAiTranscriptModelsConfigurationControllerApi apiInstance = new OpenAiTranscriptModelsConfigurationControllerApi();
GOpenAITranscriptModelConfig body = new GOpenAITranscriptModelConfig(); // GOpenAITranscriptModelConfig | 
try {
    OperationStatusGOpenAITranscriptModelConfig result = apiInstance.insertOpenAITranscriptModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiTranscriptModelsConfigurationControllerApi#insertOpenAITranscriptModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAITranscriptModelConfig**](GOpenAITranscriptModelConfig.md)|  |

### Return type

[**OperationStatusGOpenAITranscriptModelConfig**](OperationStatusGOpenAITranscriptModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateOpenAITranscriptModelConfig"></a>
# **updateOpenAITranscriptModelConfig**
> OperationStatusGOpenAITranscriptModelConfig updateOpenAITranscriptModelConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.OpenAiTranscriptModelsConfigurationControllerApi;


OpenAiTranscriptModelsConfigurationControllerApi apiInstance = new OpenAiTranscriptModelsConfigurationControllerApi();
GOpenAITranscriptModelConfig body = new GOpenAITranscriptModelConfig(); // GOpenAITranscriptModelConfig | 
try {
    OperationStatusGOpenAITranscriptModelConfig result = apiInstance.updateOpenAITranscriptModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenAiTranscriptModelsConfigurationControllerApi#updateOpenAITranscriptModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAITranscriptModelConfig**](GOpenAITranscriptModelConfig.md)|  |

### Return type

[**OperationStatusGOpenAITranscriptModelConfig**](OperationStatusGOpenAITranscriptModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

