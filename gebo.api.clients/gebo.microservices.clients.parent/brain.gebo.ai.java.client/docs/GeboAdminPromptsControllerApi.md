# GeboAdminPromptsControllerApi

All URIs are relative to *http://localhost:13001*

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
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAdminPromptsControllerApi;


GeboAdminPromptsControllerApi apiInstance = new GeboAdminPromptsControllerApi();
GPromptTemplateConfig body = new GPromptTemplateConfig(); // GPromptTemplateConfig | 
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
 **body** | [**GPromptTemplateConfig**](GPromptTemplateConfig.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findPromptConfigByCode"></a>
# **findPromptConfigByCode**
> GPromptTemplateConfig findPromptConfigByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAdminPromptsControllerApi;


GeboAdminPromptsControllerApi apiInstance = new GeboAdminPromptsControllerApi();
Object code = null; // Object | 
try {
    GPromptTemplateConfig result = apiInstance.findPromptConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptsControllerApi#findPromptConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  |

### Return type

[**GPromptTemplateConfig**](GPromptTemplateConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPromptCategories"></a>
# **getPromptCategories**
> Object getPromptCategories()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAdminPromptsControllerApi;


GeboAdminPromptsControllerApi apiInstance = new GeboAdminPromptsControllerApi();
try {
    Object result = apiInstance.getPromptCategories();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptsControllerApi#getPromptCategories");
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

<a name="getPromptConfigByFilter"></a>
# **getPromptConfigByFilter**
> GPromptTemplateConfig getPromptConfigByFilter(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAdminPromptsControllerApi;


GeboAdminPromptsControllerApi apiInstance = new GeboAdminPromptsControllerApi();
PromptFilter body = new PromptFilter(); // PromptFilter | 
try {
    GPromptTemplateConfig result = apiInstance.getPromptConfigByFilter(body);
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

[**GPromptTemplateConfig**](GPromptTemplateConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertPromptConfig"></a>
# **insertPromptConfig**
> GPromptTemplateConfig insertPromptConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAdminPromptsControllerApi;


GeboAdminPromptsControllerApi apiInstance = new GeboAdminPromptsControllerApi();
GPromptTemplateConfig body = new GPromptTemplateConfig(); // GPromptTemplateConfig | 
try {
    GPromptTemplateConfig result = apiInstance.insertPromptConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptsControllerApi#insertPromptConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GPromptTemplateConfig**](GPromptTemplateConfig.md)|  |

### Return type

[**GPromptTemplateConfig**](GPromptTemplateConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updatePromptConfig"></a>
# **updatePromptConfig**
> GPromptTemplateConfig updatePromptConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAdminPromptsControllerApi;


GeboAdminPromptsControllerApi apiInstance = new GeboAdminPromptsControllerApi();
GPromptTemplateConfig body = new GPromptTemplateConfig(); // GPromptTemplateConfig | 
try {
    GPromptTemplateConfig result = apiInstance.updatePromptConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAdminPromptsControllerApi#updatePromptConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GPromptTemplateConfig**](GPromptTemplateConfig.md)|  |

### Return type

[**GPromptTemplateConfig**](GPromptTemplateConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

