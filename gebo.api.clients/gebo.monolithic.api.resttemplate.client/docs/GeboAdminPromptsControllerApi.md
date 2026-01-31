# GeboAdminPromptsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deletePromptConfig**](GeboAdminPromptsControllerApi.md#deletePromptConfig) | **POST** /api/admin/GeboAdminPromptsController/deletePromptConfig | 
[**findPromptConfigByCode**](GeboAdminPromptsControllerApi.md#findPromptConfigByCode) | **GET** /api/admin/GeboAdminPromptsController/findPromptConfigByCode | 
[**getPromptCategories**](GeboAdminPromptsControllerApi.md#getPromptCategories) | **GET** /api/admin/GeboAdminPromptsController/getPromptCategories | 
[**getPromptConfigByFilter**](GeboAdminPromptsControllerApi.md#getPromptConfigByFilter) | **POST** /api/admin/GeboAdminPromptsController/getPromptConfigByFilter | 
[**insertPromptConfig**](GeboAdminPromptsControllerApi.md#insertPromptConfig) | **POST** /api/admin/GeboAdminPromptsController/insertPromptConfig | 
[**updatePromptConfig**](GeboAdminPromptsControllerApi.md#updatePromptConfig) | **POST** /api/admin/GeboAdminPromptsController/updatePromptConfig | 

<a name="deletePromptConfig"></a>
# **deletePromptConfig**
> deletePromptConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminPromptsControllerApi;


GeboAdminPromptsControllerApi apiInstance = new GeboAdminPromptsControllerApi();
GPromptConfig body = new GPromptConfig(); // GPromptConfig | 
try {
    apiInstance.deletePromptConfig(body);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptsControllerApi#deletePromptConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GPromptConfig**](GPromptConfig.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findPromptConfigByCode"></a>
# **findPromptConfigByCode**
> GPromptConfig findPromptConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminPromptsControllerApi;


GeboAdminPromptsControllerApi apiInstance = new GeboAdminPromptsControllerApi();
String code = "code_example"; // String | 
try {
    GPromptConfig result = apiInstance.findPromptConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptsControllerApi#findPromptConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GPromptConfig**](GPromptConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPromptCategories"></a>
# **getPromptCategories**
> List&lt;String&gt; getPromptCategories()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminPromptsControllerApi;


GeboAdminPromptsControllerApi apiInstance = new GeboAdminPromptsControllerApi();
try {
    List<String> result = apiInstance.getPromptCategories();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptsControllerApi#getPromptCategories");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**List&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPromptConfigByFilter"></a>
# **getPromptConfigByFilter**
> GPromptConfig getPromptConfigByFilter(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminPromptsControllerApi;


GeboAdminPromptsControllerApi apiInstance = new GeboAdminPromptsControllerApi();
PromptFilter body = new PromptFilter(); // PromptFilter | 
try {
    GPromptConfig result = apiInstance.getPromptConfigByFilter(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptsControllerApi#getPromptConfigByFilter");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PromptFilter**](PromptFilter.md)|  |

### Return type

[**GPromptConfig**](GPromptConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertPromptConfig"></a>
# **insertPromptConfig**
> GPromptConfig insertPromptConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminPromptsControllerApi;


GeboAdminPromptsControllerApi apiInstance = new GeboAdminPromptsControllerApi();
GPromptConfig body = new GPromptConfig(); // GPromptConfig | 
try {
    GPromptConfig result = apiInstance.insertPromptConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptsControllerApi#insertPromptConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GPromptConfig**](GPromptConfig.md)|  |

### Return type

[**GPromptConfig**](GPromptConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updatePromptConfig"></a>
# **updatePromptConfig**
> GPromptConfig updatePromptConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAdminPromptsControllerApi;


GeboAdminPromptsControllerApi apiInstance = new GeboAdminPromptsControllerApi();
GPromptConfig body = new GPromptConfig(); // GPromptConfig | 
try {
    GPromptConfig result = apiInstance.updatePromptConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptsControllerApi#updatePromptConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GPromptConfig**](GPromptConfig.md)|  |

### Return type

[**GPromptConfig**](GPromptConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

