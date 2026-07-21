# GeboDeepSearchAdminControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteDeepSearchConfig**](GeboDeepSearchAdminControllerApi.md#deleteDeepSearchConfig) | **DELETE** /api/admin/GeboDeepSearchAdminController/deleteDeepSearchConfig | 
[**getConfigurableDataSources**](GeboDeepSearchAdminControllerApi.md#getConfigurableDataSources) | **GET** /api/admin/GeboDeepSearchAdminController/getConfigurableDataSources | 
[**getDeepSeachConfigs**](GeboDeepSearchAdminControllerApi.md#getDeepSeachConfigs) | **GET** /api/admin/GeboDeepSearchAdminController/getDeepSeachConfigs | 
[**getDeepSearchDefaultConfig**](GeboDeepSearchAdminControllerApi.md#getDeepSearchDefaultConfig) | **GET** /api/admin/GeboDeepSearchAdminController/getDeepSearchDefaultConfig | 
[**getDeepSearchDefaultOrSystemConfig**](GeboDeepSearchAdminControllerApi.md#getDeepSearchDefaultOrSystemConfig) | **GET** /api/admin/GeboDeepSearchAdminController/getDeepSearchDefaultOrSystemConfig | 
[**getDeepSearchSystemConfig**](GeboDeepSearchAdminControllerApi.md#getDeepSearchSystemConfig) | **GET** /api/admin/GeboDeepSearchAdminController/getDeepSearchSystemConfig | 
[**insertDeepSearchConfig**](GeboDeepSearchAdminControllerApi.md#insertDeepSearchConfig) | **POST** /api/admin/GeboDeepSearchAdminController/insertDeepSearchConfig | 
[**updateDeepSearchConfig**](GeboDeepSearchAdminControllerApi.md#updateDeepSearchConfig) | **POST** /api/admin/GeboDeepSearchAdminController/updateDeepSearchConfig | 

<a name="deleteDeepSearchConfig"></a>
# **deleteDeepSearchConfig**
> deleteDeepSearchConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboDeepSearchAdminControllerApi;


GeboDeepSearchAdminControllerApi apiInstance = new GeboDeepSearchAdminControllerApi();
DeepSearchConfig body = new DeepSearchConfig(); // DeepSearchConfig | 
try {
    apiInstance.deleteDeepSearchConfig(body);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchAdminControllerApi#deleteDeepSearchConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DeepSearchConfig**](DeepSearchConfig.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="getConfigurableDataSources"></a>
# **getConfigurableDataSources**
> Object getConfigurableDataSources()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboDeepSearchAdminControllerApi;


GeboDeepSearchAdminControllerApi apiInstance = new GeboDeepSearchAdminControllerApi();
try {
    Object result = apiInstance.getConfigurableDataSources();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchAdminControllerApi#getConfigurableDataSources");
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
 - **Accept**: */*

<a name="getDeepSeachConfigs"></a>
# **getDeepSeachConfigs**
> Object getDeepSeachConfigs()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboDeepSearchAdminControllerApi;


GeboDeepSearchAdminControllerApi apiInstance = new GeboDeepSearchAdminControllerApi();
try {
    Object result = apiInstance.getDeepSeachConfigs();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchAdminControllerApi#getDeepSeachConfigs");
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

<a name="getDeepSearchDefaultConfig"></a>
# **getDeepSearchDefaultConfig**
> DeepSearchConfig getDeepSearchDefaultConfig()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboDeepSearchAdminControllerApi;


GeboDeepSearchAdminControllerApi apiInstance = new GeboDeepSearchAdminControllerApi();
try {
    DeepSearchConfig result = apiInstance.getDeepSearchDefaultConfig();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchAdminControllerApi#getDeepSearchDefaultConfig");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**DeepSearchConfig**](DeepSearchConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getDeepSearchDefaultOrSystemConfig"></a>
# **getDeepSearchDefaultOrSystemConfig**
> DeepSearchConfig getDeepSearchDefaultOrSystemConfig()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboDeepSearchAdminControllerApi;


GeboDeepSearchAdminControllerApi apiInstance = new GeboDeepSearchAdminControllerApi();
try {
    DeepSearchConfig result = apiInstance.getDeepSearchDefaultOrSystemConfig();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchAdminControllerApi#getDeepSearchDefaultOrSystemConfig");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**DeepSearchConfig**](DeepSearchConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getDeepSearchSystemConfig"></a>
# **getDeepSearchSystemConfig**
> DeepSearchConfig getDeepSearchSystemConfig()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboDeepSearchAdminControllerApi;


GeboDeepSearchAdminControllerApi apiInstance = new GeboDeepSearchAdminControllerApi();
try {
    DeepSearchConfig result = apiInstance.getDeepSearchSystemConfig();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchAdminControllerApi#getDeepSearchSystemConfig");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**DeepSearchConfig**](DeepSearchConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertDeepSearchConfig"></a>
# **insertDeepSearchConfig**
> DeepSearchConfig insertDeepSearchConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboDeepSearchAdminControllerApi;


GeboDeepSearchAdminControllerApi apiInstance = new GeboDeepSearchAdminControllerApi();
DeepSearchConfig body = new DeepSearchConfig(); // DeepSearchConfig | 
try {
    DeepSearchConfig result = apiInstance.insertDeepSearchConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchAdminControllerApi#insertDeepSearchConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DeepSearchConfig**](DeepSearchConfig.md)|  |

### Return type

[**DeepSearchConfig**](DeepSearchConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateDeepSearchConfig"></a>
# **updateDeepSearchConfig**
> DeepSearchConfig updateDeepSearchConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboDeepSearchAdminControllerApi;


GeboDeepSearchAdminControllerApi apiInstance = new GeboDeepSearchAdminControllerApi();
DeepSearchConfig body = new DeepSearchConfig(); // DeepSearchConfig | 
try {
    DeepSearchConfig result = apiInstance.updateDeepSearchConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchAdminControllerApi#updateDeepSearchConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DeepSearchConfig**](DeepSearchConfig.md)|  |

### Return type

[**DeepSearchConfig**](DeepSearchConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

