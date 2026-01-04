# GeboDeepSearchAdminControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteDeepSearchConfig**](GeboDeepSearchAdminControllerApi.md#deleteDeepSearchConfig) | **DELETE** /api/admin/GeboDeepSearchAdminController/deleteDeepSearchConfig | 
[**findDeepSearchDefaultConfigByCode**](GeboDeepSearchAdminControllerApi.md#findDeepSearchDefaultConfigByCode) | **GET** /api/admin/GeboDeepSearchAdminController/findDeepSearchDefaultConfigByCode | 
[**getDeepSeachConfigs**](GeboDeepSearchAdminControllerApi.md#getDeepSeachConfigs) | **GET** /api/admin/GeboDeepSearchAdminController/getDeepSeachConfigs | 
[**getDeepSearchDefaultConfig**](GeboDeepSearchAdminControllerApi.md#getDeepSearchDefaultConfig) | **GET** /api/admin/GeboDeepSearchAdminController/getDeepSearchDefaultConfig | 
[**getDeepSearchSystemConfig**](GeboDeepSearchAdminControllerApi.md#getDeepSearchSystemConfig) | **GET** /api/admin/GeboDeepSearchAdminController/getDeepSearchSystemConfig | 
[**insertDeepSearchConfig**](GeboDeepSearchAdminControllerApi.md#insertDeepSearchConfig) | **POST** /api/admin/GeboDeepSearchAdminController/insertDeepSearchConfig | 
[**updateDeepSearchConfig**](GeboDeepSearchAdminControllerApi.md#updateDeepSearchConfig) | **POST** /api/admin/GeboDeepSearchAdminController/updateDeepSearchConfig | 

<a name="deleteDeepSearchConfig"></a>
# **deleteDeepSearchConfig**
> deleteDeepSearchConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchAdminControllerApi;


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

<a name="findDeepSearchDefaultConfigByCode"></a>
# **findDeepSearchDefaultConfigByCode**
> DeepSearchConfig findDeepSearchDefaultConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchAdminControllerApi;


GeboDeepSearchAdminControllerApi apiInstance = new GeboDeepSearchAdminControllerApi();
String code = "code_example"; // String | 
try {
    DeepSearchConfig result = apiInstance.findDeepSearchDefaultConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchAdminControllerApi#findDeepSearchDefaultConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**DeepSearchConfig**](DeepSearchConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getDeepSeachConfigs"></a>
# **getDeepSeachConfigs**
> List&lt;DeepSearchConfig&gt; getDeepSeachConfigs(chatProfileCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchAdminControllerApi;


GeboDeepSearchAdminControllerApi apiInstance = new GeboDeepSearchAdminControllerApi();
String chatProfileCode = "chatProfileCode_example"; // String | 
try {
    List<DeepSearchConfig> result = apiInstance.getDeepSeachConfigs(chatProfileCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboDeepSearchAdminControllerApi#getDeepSeachConfigs");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **chatProfileCode** | **String**|  | [optional]

### Return type

[**List&lt;DeepSearchConfig&gt;**](DeepSearchConfig.md)

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchAdminControllerApi;


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

<a name="getDeepSearchSystemConfig"></a>
# **getDeepSearchSystemConfig**
> DeepSearchConfig getDeepSearchSystemConfig()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchAdminControllerApi;


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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchAdminControllerApi;


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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboDeepSearchAdminControllerApi;


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

