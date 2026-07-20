# GenericOpenAiRankerModelsConfigurationControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGenericOpenAIAPIRankerModelConfig**](GenericOpenAiRankerModelsConfigurationControllerApi.md#deleteGenericOpenAIAPIRankerModelConfig) | **POST** /api/admin/GenerigOpenAIRankerModelsConfigurationController/deleteGenericOpenAIAPIRankerModelConfig | 
[**findGenericOpenAIAPIRankerModelConfigByCode**](GenericOpenAiRankerModelsConfigurationControllerApi.md#findGenericOpenAIAPIRankerModelConfigByCode) | **GET** /api/admin/GenerigOpenAIRankerModelsConfigurationController/findGenericOpenAIAPIRankerModelConfigByCode | 
[**getGenericOpenAIAPIRankerModels**](GenericOpenAiRankerModelsConfigurationControllerApi.md#getGenericOpenAIAPIRankerModels) | **POST** /api/admin/GenerigOpenAIRankerModelsConfigurationController/getGenericOpenAIAPIRankerModels | 
[**getGenericOpenAIRankerModelConfigs**](GenericOpenAiRankerModelsConfigurationControllerApi.md#getGenericOpenAIRankerModelConfigs) | **GET** /api/admin/GenerigOpenAIRankerModelsConfigurationController/getGenericOpenAIRankerModelConfigs | 
[**getGenericOpenAIRankerModelTypes**](GenericOpenAiRankerModelsConfigurationControllerApi.md#getGenericOpenAIRankerModelTypes) | **GET** /api/admin/GenerigOpenAIRankerModelsConfigurationController/getGenericOpenAIRankerModelTypes | 
[**insertGenericOpenAIAPIRankerModelConfig**](GenericOpenAiRankerModelsConfigurationControllerApi.md#insertGenericOpenAIAPIRankerModelConfig) | **POST** /api/admin/GenerigOpenAIRankerModelsConfigurationController/insertGenericOpenAIAPIRankerModelConfig | 
[**updateGenericOpenAIAPIRankerModelConfig**](GenericOpenAiRankerModelsConfigurationControllerApi.md#updateGenericOpenAIAPIRankerModelConfig) | **POST** /api/admin/GenerigOpenAIRankerModelsConfigurationController/updateGenericOpenAIAPIRankerModelConfig | 

<a name="deleteGenericOpenAIAPIRankerModelConfig"></a>
# **deleteGenericOpenAIAPIRankerModelConfig**
> OperationStatusBoolean deleteGenericOpenAIAPIRankerModelConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GenericOpenAiRankerModelsConfigurationControllerApi;


GenericOpenAiRankerModelsConfigurationControllerApi apiInstance = new GenericOpenAiRankerModelsConfigurationControllerApi();
GenericOpenAIAPIRankerModelConfig body = new GenericOpenAIAPIRankerModelConfig(); // GenericOpenAIAPIRankerModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteGenericOpenAIAPIRankerModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiRankerModelsConfigurationControllerApi#deleteGenericOpenAIAPIRankerModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIRankerModelConfig**](GenericOpenAIAPIRankerModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGenericOpenAIAPIRankerModelConfigByCode"></a>
# **findGenericOpenAIAPIRankerModelConfigByCode**
> GenericOpenAIAPIRankerModelConfig findGenericOpenAIAPIRankerModelConfigByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GenericOpenAiRankerModelsConfigurationControllerApi;


GenericOpenAiRankerModelsConfigurationControllerApi apiInstance = new GenericOpenAiRankerModelsConfigurationControllerApi();
Object code = null; // Object | 
try {
    GenericOpenAIAPIRankerModelConfig result = apiInstance.findGenericOpenAIAPIRankerModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiRankerModelsConfigurationControllerApi#findGenericOpenAIAPIRankerModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  |

### Return type

[**GenericOpenAIAPIRankerModelConfig**](GenericOpenAIAPIRankerModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGenericOpenAIAPIRankerModels"></a>
# **getGenericOpenAIAPIRankerModels**
> OperationStatusListGenericOpenAIAPIRankerModelChoice getGenericOpenAIAPIRankerModels(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GenericOpenAiRankerModelsConfigurationControllerApi;


GenericOpenAiRankerModelsConfigurationControllerApi apiInstance = new GenericOpenAiRankerModelsConfigurationControllerApi();
GenericOpenAIAPIRankerModelConfig body = new GenericOpenAIAPIRankerModelConfig(); // GenericOpenAIAPIRankerModelConfig | 
try {
    OperationStatusListGenericOpenAIAPIRankerModelChoice result = apiInstance.getGenericOpenAIAPIRankerModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiRankerModelsConfigurationControllerApi#getGenericOpenAIAPIRankerModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIRankerModelConfig**](GenericOpenAIAPIRankerModelConfig.md)|  |

### Return type

[**OperationStatusListGenericOpenAIAPIRankerModelChoice**](OperationStatusListGenericOpenAIAPIRankerModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getGenericOpenAIRankerModelConfigs"></a>
# **getGenericOpenAIRankerModelConfigs**
> Object getGenericOpenAIRankerModelConfigs()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GenericOpenAiRankerModelsConfigurationControllerApi;


GenericOpenAiRankerModelsConfigurationControllerApi apiInstance = new GenericOpenAiRankerModelsConfigurationControllerApi();
try {
    Object result = apiInstance.getGenericOpenAIRankerModelConfigs();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiRankerModelsConfigurationControllerApi#getGenericOpenAIRankerModelConfigs");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGenericOpenAIRankerModelTypes"></a>
# **getGenericOpenAIRankerModelTypes**
> Object getGenericOpenAIRankerModelTypes()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GenericOpenAiRankerModelsConfigurationControllerApi;


GenericOpenAiRankerModelsConfigurationControllerApi apiInstance = new GenericOpenAiRankerModelsConfigurationControllerApi();
try {
    Object result = apiInstance.getGenericOpenAIRankerModelTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiRankerModelsConfigurationControllerApi#getGenericOpenAIRankerModelTypes");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertGenericOpenAIAPIRankerModelConfig"></a>
# **insertGenericOpenAIAPIRankerModelConfig**
> OperationStatusGenericOpenAIAPIRankerModelConfig insertGenericOpenAIAPIRankerModelConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GenericOpenAiRankerModelsConfigurationControllerApi;


GenericOpenAiRankerModelsConfigurationControllerApi apiInstance = new GenericOpenAiRankerModelsConfigurationControllerApi();
GenericOpenAIAPIRankerModelConfig body = new GenericOpenAIAPIRankerModelConfig(); // GenericOpenAIAPIRankerModelConfig | 
try {
    OperationStatusGenericOpenAIAPIRankerModelConfig result = apiInstance.insertGenericOpenAIAPIRankerModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiRankerModelsConfigurationControllerApi#insertGenericOpenAIAPIRankerModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIRankerModelConfig**](GenericOpenAIAPIRankerModelConfig.md)|  |

### Return type

[**OperationStatusGenericOpenAIAPIRankerModelConfig**](OperationStatusGenericOpenAIAPIRankerModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGenericOpenAIAPIRankerModelConfig"></a>
# **updateGenericOpenAIAPIRankerModelConfig**
> OperationStatusGenericOpenAIAPIRankerModelConfig updateGenericOpenAIAPIRankerModelConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GenericOpenAiRankerModelsConfigurationControllerApi;


GenericOpenAiRankerModelsConfigurationControllerApi apiInstance = new GenericOpenAiRankerModelsConfigurationControllerApi();
GenericOpenAIAPIRankerModelConfig body = new GenericOpenAIAPIRankerModelConfig(); // GenericOpenAIAPIRankerModelConfig | 
try {
    OperationStatusGenericOpenAIAPIRankerModelConfig result = apiInstance.updateGenericOpenAIAPIRankerModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiRankerModelsConfigurationControllerApi#updateGenericOpenAIAPIRankerModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIRankerModelConfig**](GenericOpenAIAPIRankerModelConfig.md)|  |

### Return type

[**OperationStatusGenericOpenAIAPIRankerModelConfig**](OperationStatusGenericOpenAIAPIRankerModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

