# GenericOpenAiapiImageModelsConfigurationControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGenericOpenAIAPIImageModelConfig**](GenericOpenAiapiImageModelsConfigurationControllerApi.md#deleteGenericOpenAIAPIImageModelConfig) | **POST** /api/admin/GenericOpenAIAPIImageModelsConfigurationController/deleteGenericOpenAIAPIImageModelConfig | 
[**findGenericOpenAIAPIImageModelConfigByCode**](GenericOpenAiapiImageModelsConfigurationControllerApi.md#findGenericOpenAIAPIImageModelConfigByCode) | **GET** /api/admin/GenericOpenAIAPIImageModelsConfigurationController/findGenericOpenAIAPIImageModelConfigByCode | 
[**getGenericOpenAIAPIImageModels**](GenericOpenAiapiImageModelsConfigurationControllerApi.md#getGenericOpenAIAPIImageModels) | **POST** /api/admin/GenericOpenAIAPIImageModelsConfigurationController/getGenericOpenAIAPIImageModels | 
[**getGenericOpenAIImageModelConfigs**](GenericOpenAiapiImageModelsConfigurationControllerApi.md#getGenericOpenAIImageModelConfigs) | **GET** /api/admin/GenericOpenAIAPIImageModelsConfigurationController/getGenericOpenAIImageModelConfigs | 
[**getGenericOpenAIImageModelTypes**](GenericOpenAiapiImageModelsConfigurationControllerApi.md#getGenericOpenAIImageModelTypes) | **GET** /api/admin/GenericOpenAIAPIImageModelsConfigurationController/getGenericOpenAIImageModelTypes | 
[**insertGenericOpenAIAPIImageModelConfig**](GenericOpenAiapiImageModelsConfigurationControllerApi.md#insertGenericOpenAIAPIImageModelConfig) | **POST** /api/admin/GenericOpenAIAPIImageModelsConfigurationController/insertGenericOpenAIAPIImageModelConfig | 
[**updateGenericOpenAIAPIImageModelConfig**](GenericOpenAiapiImageModelsConfigurationControllerApi.md#updateGenericOpenAIAPIImageModelConfig) | **POST** /api/admin/GenericOpenAIAPIImageModelsConfigurationController/updateGenericOpenAIAPIImageModelConfig | 

<a name="deleteGenericOpenAIAPIImageModelConfig"></a>
# **deleteGenericOpenAIAPIImageModelConfig**
> OperationStatusBoolean deleteGenericOpenAIAPIImageModelConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GenericOpenAiapiImageModelsConfigurationControllerApi;


GenericOpenAiapiImageModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiImageModelsConfigurationControllerApi();
GenericOpenAIAPIImageModelConfig body = new GenericOpenAIAPIImageModelConfig(); // GenericOpenAIAPIImageModelConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteGenericOpenAIAPIImageModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiImageModelsConfigurationControllerApi#deleteGenericOpenAIAPIImageModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIImageModelConfig**](GenericOpenAIAPIImageModelConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGenericOpenAIAPIImageModelConfigByCode"></a>
# **findGenericOpenAIAPIImageModelConfigByCode**
> GenericOpenAIAPIImageModelConfig findGenericOpenAIAPIImageModelConfigByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GenericOpenAiapiImageModelsConfigurationControllerApi;


GenericOpenAiapiImageModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiImageModelsConfigurationControllerApi();
Object code = null; // Object | 
try {
    GenericOpenAIAPIImageModelConfig result = apiInstance.findGenericOpenAIAPIImageModelConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiImageModelsConfigurationControllerApi#findGenericOpenAIAPIImageModelConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  |

### Return type

[**GenericOpenAIAPIImageModelConfig**](GenericOpenAIAPIImageModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGenericOpenAIAPIImageModels"></a>
# **getGenericOpenAIAPIImageModels**
> OperationStatusListGenericOpenAIAPIImageModelChoice getGenericOpenAIAPIImageModels(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GenericOpenAiapiImageModelsConfigurationControllerApi;


GenericOpenAiapiImageModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiImageModelsConfigurationControllerApi();
GenericOpenAIAPIImageModelConfig body = new GenericOpenAIAPIImageModelConfig(); // GenericOpenAIAPIImageModelConfig | 
try {
    OperationStatusListGenericOpenAIAPIImageModelChoice result = apiInstance.getGenericOpenAIAPIImageModels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiImageModelsConfigurationControllerApi#getGenericOpenAIAPIImageModels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIImageModelConfig**](GenericOpenAIAPIImageModelConfig.md)|  |

### Return type

[**OperationStatusListGenericOpenAIAPIImageModelChoice**](OperationStatusListGenericOpenAIAPIImageModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getGenericOpenAIImageModelConfigs"></a>
# **getGenericOpenAIImageModelConfigs**
> Object getGenericOpenAIImageModelConfigs()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GenericOpenAiapiImageModelsConfigurationControllerApi;


GenericOpenAiapiImageModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiImageModelsConfigurationControllerApi();
try {
    Object result = apiInstance.getGenericOpenAIImageModelConfigs();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiImageModelsConfigurationControllerApi#getGenericOpenAIImageModelConfigs");
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

<a name="getGenericOpenAIImageModelTypes"></a>
# **getGenericOpenAIImageModelTypes**
> Object getGenericOpenAIImageModelTypes()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GenericOpenAiapiImageModelsConfigurationControllerApi;


GenericOpenAiapiImageModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiImageModelsConfigurationControllerApi();
try {
    Object result = apiInstance.getGenericOpenAIImageModelTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiImageModelsConfigurationControllerApi#getGenericOpenAIImageModelTypes");
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

<a name="insertGenericOpenAIAPIImageModelConfig"></a>
# **insertGenericOpenAIAPIImageModelConfig**
> OperationStatusGenericOpenAIAPIImageModelConfig insertGenericOpenAIAPIImageModelConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GenericOpenAiapiImageModelsConfigurationControllerApi;


GenericOpenAiapiImageModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiImageModelsConfigurationControllerApi();
GenericOpenAIAPIImageModelConfig body = new GenericOpenAIAPIImageModelConfig(); // GenericOpenAIAPIImageModelConfig | 
try {
    OperationStatusGenericOpenAIAPIImageModelConfig result = apiInstance.insertGenericOpenAIAPIImageModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiImageModelsConfigurationControllerApi#insertGenericOpenAIAPIImageModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIImageModelConfig**](GenericOpenAIAPIImageModelConfig.md)|  |

### Return type

[**OperationStatusGenericOpenAIAPIImageModelConfig**](OperationStatusGenericOpenAIAPIImageModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGenericOpenAIAPIImageModelConfig"></a>
# **updateGenericOpenAIAPIImageModelConfig**
> OperationStatusGenericOpenAIAPIImageModelConfig updateGenericOpenAIAPIImageModelConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GenericOpenAiapiImageModelsConfigurationControllerApi;


GenericOpenAiapiImageModelsConfigurationControllerApi apiInstance = new GenericOpenAiapiImageModelsConfigurationControllerApi();
GenericOpenAIAPIImageModelConfig body = new GenericOpenAIAPIImageModelConfig(); // GenericOpenAIAPIImageModelConfig | 
try {
    OperationStatusGenericOpenAIAPIImageModelConfig result = apiInstance.updateGenericOpenAIAPIImageModelConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GenericOpenAiapiImageModelsConfigurationControllerApi#updateGenericOpenAIAPIImageModelConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIImageModelConfig**](GenericOpenAIAPIImageModelConfig.md)|  |

### Return type

[**OperationStatusGenericOpenAIAPIImageModelConfig**](OperationStatusGenericOpenAIAPIImageModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

